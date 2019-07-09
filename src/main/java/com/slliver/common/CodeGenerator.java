package com.slliver.common;

import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @Description: 代码生成工具beta版本, 暂时不支持多表同时生成
 * @author: slliver
 * @date: 2018/11/14 17:14
 * @version: 1.0
 */
public class CodeGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeGenerator.class);

    //from constructor or keep default
    private String propertiesPath = "/applicaption-generator.properties";

    // 生成代码的配置文件
    private String baseConfigPath = "/generatorConfig-bsae.xml";
    private String configPath = "/generatorConfig-other.xml";
    // freemarker模板存放路径
    private static String FREEMARKER_TEMPLATE_PATH = "";
    // fields with default value
    private static final String BLANK_STRING = "";
    // 文件生成日期
    private final String DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());//@date
    private final List<String> WARNINGS = new ArrayList<>();
    private final DefaultShellCallback CALLBACK = new DefaultShellCallback(true);

    // from configuration
    private String AUTHOR;//@author
    private String CONTEXTID;
    private String PROJECT_PATH;
    //private final String TEMPLATE_FILE_PATH;
    // 当前表名
    private String TABLE_NAME;
    // 当前表的前缀
    private String TABLE_PREFIX;
    private String BASE_PACKAGE;
    private String BASEDOMAIN_PACKAGE;
    private String BASEDOMAIN_MAPPER_PACKAGE;

    private String CONTROLLER_FTL;
    private String JAVA_PATH;
    private String RESOURCES_PATH;
    private String BASE_PACKAGE_PATH;
    private String PACKAGE_PATH_SERVICE;
    private String PACKAGE_PATH_ENTITY;
    private String PACKAGE_PATH_ENTITY_MAPPER;
    // private final String PACKAGE_PATH_SERVICE_IMPL;
    private String PACKAGE_PATH_CONTROLLER;
    private String JDBC_DIVER_CLASS_NAME;
    private String JDBC_URL;
    private String JDBC_USERNAME;
    private String JDBC_PASSWORD;

    // mbg config
    private Configuration baseConfig;
    private Context baseContext;

    private Configuration config;
    private Context context;


    // freemarker config
    private freemarker.template.Configuration freemarkerCfg;
    private boolean need_rest;
    private boolean baseDealed = false;
    private boolean dealed = false;

    public CodeGenerator() {
        init();
    }

    public CodeGenerator(String propertiesPath, String mgbXmlPath) {
        this.propertiesPath = propertiesPath;
        this.configPath = mgbXmlPath;
        init();
    }

    private void init() {
        // ClassPathResource()加载是的resources目录下的配置文件
        Resource resource = new ClassPathResource(propertiesPath);
        Properties props = new Properties();
        try {
            props.load(resource.getInputStream());
            TABLE_PREFIX = props.getProperty("table.prefix");
            TABLE_NAME = props.getProperty("table.name");
            AUTHOR = props.getProperty("gen.author");
//            CONTEXTID = props.getProperty("gen.context.id").trim();
            PROJECT_PATH = props.getProperty("project.path").trim();
            // 模板文件存放目录
            FREEMARKER_TEMPLATE_PATH = props.getProperty("freemaker.template.path").trim();
            File projPathFile = new File(PROJECT_PATH);
            if (projPathFile.exists() == false) {
                projPathFile.mkdirs();
            }
            //TEMPLATE_FILE_PATH = PROJECT_PATH + "/src/test/resources/generator/template";
            BASE_PACKAGE = props.getProperty("gen.basepackage").trim();
            BASEDOMAIN_PACKAGE = props.getProperty("domain.basepackage").trim();
            BASEDOMAIN_MAPPER_PACKAGE = props.getProperty("domain.mapper.basepackage").trim();
            need_rest = Boolean.parseBoolean(props.getProperty("rest").trim());
            CONTROLLER_FTL = "controller" + (need_rest ? "-restful" : "") + ".ftl"; // controller.ftl
            JAVA_PATH = props.getProperty("java.path").trim();
            File javaPathFile = new File(PROJECT_PATH, JAVA_PATH);
            if (javaPathFile.exists() == false) {
                javaPathFile.mkdirs();
            }
            RESOURCES_PATH = props.getProperty("resources.path").trim();
            File resourcePathFile = new File(PROJECT_PATH, RESOURCES_PATH);
            if (resourcePathFile.exists() == false) {
                resourcePathFile.mkdirs();
            }
            BASE_PACKAGE_PATH = "/" + BASE_PACKAGE.replaceAll("\\.", "/") + "/";//项目基础包路径
            // 生成实体存放目录
            PACKAGE_PATH_ENTITY = BASE_PACKAGE_PATH + "entity/";
            // 生成的实体Mapper(和对应的Mapper.xml)文件存放目录存放路径
            PACKAGE_PATH_ENTITY_MAPPER = BASE_PACKAGE_PATH + "dao/";
            // 生成的Service存放路径;
            PACKAGE_PATH_SERVICE = BASE_PACKAGE_PATH + "service/";
            // 生成的非Base的Mapper(和对应的Mapper.xml)文件存放目录
            // PACKAGE_PATH_SERVICE_IMPL = BASE_PACKAGE_PATH + "/service/impl/";//生成的Service实现存放路径 ;
            if (need_rest) {
                PACKAGE_PATH_CONTROLLER = BASE_PACKAGE_PATH + "/api/";//生成的API存放路径;
            } else {
                PACKAGE_PATH_CONTROLLER = BASE_PACKAGE_PATH + "/web/";//生成的Controller存放路径;
            }

            JDBC_DIVER_CLASS_NAME = props.getProperty("jdbc.driver").trim();
            JDBC_URL = props.getProperty("jdbc.url").trim();
            JDBC_USERNAME = props.getProperty("jdbc.username").trim();
            JDBC_PASSWORD = props.getProperty("jdbc.password").trim();


            ConfigurationParser cp = new ConfigurationParser(WARNINGS);
            // 配置文件
            baseConfig = cp.parseConfiguration(new ClassPathResource(baseConfigPath).getInputStream());
            config = cp.parseConfiguration(new ClassPathResource(configPath).getInputStream());
            // 获取base部分的容器配置
            baseContext = baseConfig.getContext("baseContext");
            // 获取其他部分的容器配置
            context = config.getContext("otherContext");

            //load jdbc driver
            Class.forName(JDBC_DIVER_CLASS_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();//直接抛出运行时异常
        }
    }


    /**
     * 生成子类的配置
     */
    public void generateMapper() {
        dealTables();
        try {
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, CALLBACK, WARNINGS);
            myBatisGenerator.generate(null);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("base 代码成功生成，路径：" + PROJECT_PATH);
            }
        } catch (InvalidConfigurationException | SQLException | IOException | InterruptedException e) {
            throw new RuntimeException();//直接抛出运行时异常
        }
    }


    /**
     * 生成父类的配置
     */
    public void generateBaseMapper() {
        dealBaseTables();
        try {
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(baseConfig, CALLBACK, WARNINGS);
            myBatisGenerator.generate(null);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("代码成功生成，路径：" + PROJECT_PATH);
            }
        } catch (InvalidConfigurationException | SQLException | IOException | InterruptedException e) {
            // 直接抛出运行时异常
            throw new RuntimeException();
        }
    }

    private void dealBaseTables() {
        if (!baseDealed) {
            List<TableConfiguration> tcs = baseContext.getTableConfigurations();
            Set<TableConfiguration> newTcs = new HashSet<>();
            for (TableConfiguration tc : tcs) {
                dealBaseTable(newTcs, tc);
            }
            tcs.clear();
            tcs.addAll(newTcs);
            baseDealed = true;
        }
    }

    /**
     * 解析所有table配置，正确地和数据库中的表对应起来，形成新的TableConfiguration列表
     */
    private void dealTables() {
        if (!dealed) {
            List<TableConfiguration> tcs = context.getTableConfigurations();
            Set<TableConfiguration> newTcs = new HashSet<>();
            Set<String> dbTableNameSet = getDbTableNames();
            for (TableConfiguration tc : tcs) {
                dealTable(newTcs, dbTableNameSet, tc);
            }
            tcs.clear();
            tcs.addAll(newTcs);
            dealed = true;
        }
    }


    /**
     * 模板文件的路径
     *
     * @param templateDir
     */
    public void genServiceAndController(String templateDir) {
        dealTables();
        List<TableConfiguration> tableConfigs = context.getTableConfigurations();
        for (TableConfiguration conf : tableConfigs) {
            String domainName = conf.getDomainObjectName();
            // 实体前缀
            String entityPrefix = this.getFirstToUpperCase(TABLE_PREFIX);
            try {
                //build config
                buildFreemarkerConfiguration(templateDir);
                //prepare data used in template
                Map<String, Object> data = new HashMap<>();
                data.put("date", DATE);
                data.put("author", AUTHOR);

                // 对应的数据库表名
                data.put("tableName", conf.getTableName());
                // 数据库表映射的实体
                data.put("domainName", domainName);
                // 映射的实体继承的base实体
                String baseDomainName = entityPrefix + domainName;
                data.put("baseDomainName", baseDomainName);
                // 映射的实体继承的base实体所在包路径
                data.put("baseDomainPackage", BASEDOMAIN_PACKAGE);
                data.put("baseRequestMapping", tableNameConvertMappingPath(conf.getTableName()));
                data.put("modelNameUpperCamel", domainName);//??
                String modelNameLowerCamel = domainName.substring(0, 1).toLowerCase() + domainName.substring(1);
                data.put("modelNameLowerCamel", modelNameLowerCamel);
                data.put("basePackage", BASE_PACKAGE);

                // 生成对应的entity
                String entityName = domainName + ".java";
                File domainFile = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_ENTITY + entityName);
                System.out.println(domainFile);
                if (!domainFile.getParentFile().exists()) {
                    domainFile.getParentFile().mkdirs();
                }

                freemarkerCfg.getTemplate("entity.ftl").process(data, new FileWriter(domainFile));
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("路径：" + PROJECT_PATH);
                    LOGGER.debug(domainName + "生成成功");
                }

                // 生成对应的entityMapper 比如：DriverMapper

                String mapperName = domainName + "Mapper";
                String entityMapperName = mapperName + ".java";
                data.put("baseDomainMapperPackage", BASEDOMAIN_MAPPER_PACKAGE);
                data.put("baseDomainMapper", mapperName);
                String mapperPath = PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_ENTITY_MAPPER + entityMapperName;

                /**
                 File entityMapperFile = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_ENTITY_MAPPER + entityMapperName);
                 if (!entityMapperFile.getParentFile().exists()) {
                 entityMapperFile.getParentFile().mkdirs();
                 }

                 freemarkerCfg.getTemplate("entityMapper.ftl").process(data, new FileWriter(entityMapperFile));
                 if (LOGGER.isDebugEnabled()) {
                 LOGGER.debug("路径：" + PROJECT_PATH);
                 LOGGER.debug(entityMapperName + "生成成功");
                 }
                 **/

                // 生成Mapper.xml文件
                String xmlMapperName = mapperName + ".xml";
                // mapper.xml文件存放路径
                data.put("domainMapperPackage", BASE_PACKAGE);
                // 生成的mapper.xml的名字，比如DriverMapper.xml
                // 这里的mapperName是没有继承的那个类的前缀的比如当前项目的ibmp,所以我们要动态生成
                String baseMapperName = entityPrefix + mapperName;
                data.put("baseMapperName", baseMapperName);
                data.put("baseDomainMapperXml", mapperName);
                File entityMapperXmlFile = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_ENTITY_MAPPER + xmlMapperName);
                if (!entityMapperXmlFile.getParentFile().exists()) {
                    entityMapperXmlFile.getParentFile().mkdirs();
                }
                freemarkerCfg.getTemplate("mapperXml.ftl").process(data, new FileWriter(entityMapperXmlFile));

                // 生成service文件
                String javaFileName = domainName + "Service.java";
                File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE + javaFileName);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                freemarkerCfg.getTemplate("service.ftl").process(data, new FileWriter(file));

                // 生成controller文件
                File controllerFile = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CONTROLLER + domainName + (need_rest ? "API" : "Controller") + ".java");
                if (!controllerFile.getParentFile().exists()) {
                    controllerFile.getParentFile().mkdirs();
                }

                // 数据库表明前最好加一个标识符，这是CodeGenerator的固定写法
                freemarkerCfg.getTemplate(CONTROLLER_FTL).process(data, new FileWriter(controllerFile));
            } catch (Exception e) {
                // 直接抛出运行时异常
                throw new RuntimeException();
            }
        }
    }


    private void buildFreemarkerConfiguration(String TEMPLATE_FILE_PATH) throws IOException {
        if (freemarkerCfg != null) {
            return;
        }
        freemarkerCfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        freemarkerCfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
        freemarkerCfg.setDefaultEncoding("UTF-8");
        freemarkerCfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
    }

    /**
     * 只连一次数据库获得所有的表名，获取表名后关闭数据库连接
     *
     * @return
     */
    private Set<String> getDbTableNames() {
        Set<String> dbTableNameSet = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);) {
            DatabaseMetaData dbMetaData = connection.getMetaData();
            ResultSet rs = dbMetaData.getTables(null, null, null, null);
            while (rs.next()) {
                final String tableName = rs.getString("TABLE_NAME");
                dbTableNameSet.add(tableName);
            }
        } catch (Exception e) {
            LOGGER.error(JDBC_URL + "::" + JDBC_USERNAME + "::" + JDBC_PASSWORD, e);
            throw new RuntimeException();//直接抛出运行时异常
        }
        return dbTableNameSet;
    }

    /**
     * 根据原Table配置中是否有通配符来解析数据库中的表名，将符合要求的表配置后加入新配置容器
     *
     * @param newTcs         新Table配置容器
     * @param dbTableNameSet 数据库表名容器
     * @param oldTc          原Table配置
     */
    private void dealTable(Set<TableConfiguration> newTcs, Set<String> dbTableNameSet, TableConfiguration oldTc) {
        String tcTableName = oldTc.getTableName();
        // 分两类，带通配符和不带通配符
        if (tcTableName.contains("*")) {
            Iterator<String> dbTableNameIter = dbTableNameSet.iterator();
            while (dbTableNameIter.hasNext()) {
                String dbTableName = dbTableNameIter.next();
                // 配置中表名为*，则所有表都符合要求
                if (tcTableName.equals("*")) {
                    newTcs.add(configTable(dbTableName, null, oldTc.getGeneratedKey()));
                } else {
                    int starIndex = tcTableName.indexOf("*");
                    //截取前缀
                    String prefix = tcTableName.substring(0, starIndex);
                    //满足前缀要求
                    if (dbTableName.startsWith(prefix)) {
                        newTcs.add(configTable(dbTableName, prefix, oldTc.getGeneratedKey()));
                    }
                }
            }
        } else { // xml中配置的就是要使用的表名和domain
            // 如果不存在*说明是单表，直接生成当前表的service和controller
            if (!newTcs.contains(tcTableName)) {
                //newTcs.add(oldTc);
                newTcs.add(configTable(tcTableName, TABLE_PREFIX, oldTc.getGeneratedKey()));
            } else {
                LOGGER.warn("数据库中不存在此表：%s", tcTableName);
            }
        }

    }

    private void dealBaseTable(Set<TableConfiguration> newTcs, TableConfiguration oldTc) {
        String tcTableName = oldTc.getTableName();
        if (!newTcs.contains(tcTableName)) {
            newTcs.add(configTable(tcTableName, "", oldTc.getGeneratedKey()));
        } else {
            LOGGER.warn("数据库中不存在此表：%s", tcTableName);
        }
    }

    /**
     * 配置新的TableConfiguration
     *
     * @param tableName    原生表名
     * @param tablePrefix  待去除的前缀
     * @param generatedKey 主键生成方式
     * @return
     */
    private TableConfiguration configTable(String tableName, String tablePrefix, GeneratedKey generatedKey) {
        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        tableConfiguration.setGeneratedKey(generatedKey);
        // 表前缀
        if (StringUtils.isNotEmpty(tablePrefix)) {
            String domainObjectName = tableName.replaceFirst(tablePrefix, BLANK_STRING);
            tableConfiguration.setDomainObjectName(tableNameConvertUpperCamel(domainObjectName));
        }
        return tableConfiguration;
    }

    private static String tableNameConvertUpperCamel(String tableName) {
        String camel = tableNameConvertLowerCamel(tableName);
        return camel.substring(0, 1).toUpperCase() + camel.substring(1);

    }

    private static String tableNameConvertLowerCamel(String tableName) {
        StringBuilder result = new StringBuilder();
        if (tableName != null && tableName.length() > 0) {
            boolean flag = false;
            for (int i = 0; i < tableName.length(); i++) {
                char ch = tableName.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }


    private static String tableNameConvertMappingPath(String tableName) {
        return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
    }

    /**
     * 对表的前缀进行首字母大写转换
     * @param prefix
     * @return
     */
    private String getFirstToUpperCase(String prefix) {
        if (StringUtils.isBlank(prefix)) {
            return "";
        }
        int length = prefix.length();
        return prefix.substring(0, 1).toUpperCase() + prefix.substring(1, length);
    }


    private class TableInfo {
        String tableName;
        String tablePrefix;
        String columnPrefix;

        public TableInfo(String tableName) {
            this.tableName = tableName;
        }

        public TableInfo(String tableName, String tablePrefix, String columnPrefix) {
            this.tableName = tableName;
            this.tablePrefix = tablePrefix;
            this.columnPrefix = columnPrefix;
        }

    }

    public static void main(String[] args) {
        CodeGenerator codeGenerator = new CodeGenerator();

        codeGenerator.generateBaseMapper();
        codeGenerator.generateMapper();
        codeGenerator.genServiceAndController(FREEMARKER_TEMPLATE_PATH);
        LOGGER.info("代码生成成功....");
    }
}
