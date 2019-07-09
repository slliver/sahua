package com.slliver.common.interceptor.locker;

import com.slliver.common.interceptor.locker.annotation.VersionLocker;
import com.slliver.common.interceptor.locker.cache.Cache;
import com.slliver.common.interceptor.locker.cache.LocalVersionLockerCache;
import com.slliver.common.interceptor.locker.cache.VersionLockerCache;
import com.slliver.common.interceptor.locker.util.PluginUtil;
import com.slliver.common.utils.ReflectUtil;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.*;

/**
 * @Description: <p>MyBatis乐观锁插件<br>
 * @author: slliver
 * @date: 2018/1/31 17:46
 * @version: 1.0
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class OptimisticLocker implements Interceptor {

    private static final Log log = LogFactory.getLog(OptimisticLocker.class);

    private static VersionLocker trueLocker;
    private static VersionLocker falseLocker;

    static {
        try {
            trueLocker = OptimisticLocker.class.getDeclaredMethod("versionValueTrue").getAnnotation(VersionLocker.class);
            falseLocker = OptimisticLocker.class.getDeclaredMethod("versionValueFalse").getAnnotation(VersionLocker.class);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("The plugin init faild.", e);
        }
    }

    private Properties props = null;
    private VersionLockerCache versionLockerCache = new LocalVersionLockerCache();

    @VersionLocker(true)
    private void versionValueTrue() {
    }

    @VersionLocker(false)
    private void versionValueFalse() {
    }

    @Override
    public Object intercept(Invocation invocation) throws Exception {
        // 取得配置文件乐观锁字段
        String versionColumn;
        String versionField;
        if (null == props || props.isEmpty()) {
            versionColumn = "version";
            versionField = "version";
        } else {
            versionColumn = props.getProperty("versionColumn", "version");
            versionField = props.getProperty("versionField", "version");
        }
        String interceptMethod = invocation.getMethod().getName();
        if (!"prepare".equals(interceptMethod)) {
            return invocation.proceed();
        }
        StatementHandler handler = (StatementHandler) PluginUtil.processTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(handler);
        MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        SqlCommandType sqlCmdType = ms.getSqlCommandType();
        // 如果Sql不是update，直接返回
        if (sqlCmdType != SqlCommandType.UPDATE) {
            return invocation.proceed();
        }
        // 取得更新Sql
        BoundSql boundSql = handler.getBoundSql();
        // 如果标示为不校验乐观锁，直接返回
        VersionLocker vl = getVersionLocker(ms, boundSql);
        if (null != vl && !vl.value()) {
            return invocation.proceed();
        }
        // 取得校验字段原始值
        Object originalVersion = null;
        try {
            originalVersion = metaObject.getValue("delegate.boundSql.parameterObject." + versionField);
        } catch (Exception e) {
            // 如果参数错误，不要抛出异常
            e.printStackTrace();
        }
        if (null == originalVersion) {
            return invocation.proceed();
        }
        String originalSql = boundSql.getSql();
        if (log.isDebugEnabled()) {
            log.debug("==> originalSql: " + originalSql);
        }
        // 元Sql，加入版本控制字段
        originalSql = addVersionToSql(originalSql, versionColumn, originalVersion, boundSql);
        metaObject.setValue("delegate.boundSql.sql", originalSql);
        if (log.isDebugEnabled()) {
            log.debug("==> originalSql after add version: " + originalSql);
        }
        return invocation.proceed();
    }

    /**
     * 元Sql加入版本控制字段
     * @param originalSql
     * @param versionColumnName
     * @param originalVersion
     * @param boundSql
     * @return
     */
    private String addVersionToSql(String originalSql, String versionColumnName, Object originalVersion, BoundSql boundSql) {
        try {
            Statement stmt = CCJSqlParserUtil.parse(originalSql);
            if (!(stmt instanceof Update)) {
                return originalSql;
            }
            Update update = (Update) stmt;
            // 如果元Sql带有flag_version字段则删除
            List<Column> columns = update.getColumns();
            for (int i = 0; i < columns.size(); i++) {
                Column col = columns.get(i);
                if (StringUtils.equalsIgnoreCase(versionColumnName, col.getColumnName())) {
                    columns.remove(i);
                    update.getExpressions().remove(i);
                    boundSql.getParameterMappings().remove(i);
                    break;
                }
            }
            // 元Sql加入flag_version = flag_version + 1表达
            buildVersionExpression(update, versionColumnName);
            // 元Sql的Where加入 AND flag_version = old
            Expression where = update.getWhere();
            if (originalVersion != null) {
                if (where != null) {
                    AndExpression and = new AndExpression(where, buildVersionEquals(versionColumnName, originalVersion));
                    update.setWhere(and);
                } else {
                    update.setWhere(buildVersionEquals(versionColumnName, originalVersion));
                }
            }
            return stmt.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return originalSql;
        }
    }

    /**
     * 元Sql加入flag_version = flag_version + 1表达
     * @param update
     * @param versionColumnName
     */
    private void buildVersionExpression(Update update, String versionColumnName) {

        List<Column> columns = update.getColumns();
        Column versionColumn = new Column();
        versionColumn.setColumnName(versionColumnName);
        columns.add(versionColumn);

        List<Expression> expressions = update.getExpressions();
        Addition add = new Addition();

        Function ifNullFun = new Function();
        ifNullFun.setName("IFNULL");
        List<Expression> ifExpParam = new ArrayList<Expression>();
        ifExpParam.add(versionColumn);
        ifExpParam.add(new LongValue(0));
        ExpressionList ifNullExpList = new ExpressionList();
        ifNullExpList.setExpressions(ifExpParam);
        ifNullFun.setParameters(ifNullExpList);
        add.setLeftExpression(ifNullFun);
        add.setRightExpression(new LongValue(1));
        expressions.add(add);
    }

    /**
     * where条件加入版本字段
     * @param versionColumnName
     * @param originalVersion
     * @return
     */
    private Expression buildVersionEquals(String versionColumnName, Object originalVersion) {
        EqualsTo equal = new EqualsTo();
        Column column = new Column();
        column.setColumnName(versionColumnName);
        equal.setLeftExpression(column);
        LongValue val = new LongValue(originalVersion.toString());
        equal.setRightExpression(val);
        return equal;
    }

    /**
     * 得到
     * @param ms
     * @param boundSql
     * @return
     */
    private VersionLocker getVersionLocker(MappedStatement ms, BoundSql boundSql) {

        Class<?>[] paramCls = null;
        Object paramObj = boundSql.getParameterObject();

        /******************下面处理参数只能按照下面3个的顺序***********************/
        /******************Process param must order by below ***********************/
        // 1、处理@Param标记的参数
        // 1、Process @Param param
        if (paramObj instanceof MapperMethod.ParamMap<?>) {
            MapperMethod.ParamMap<?> mmp = (MapperMethod.ParamMap<?>) paramObj;
            if (null != mmp && !mmp.isEmpty()) {
                paramCls = new Class<?>[mmp.size() / 2];
                int mmpLen = mmp.size() / 2;
                for (int i = 0; i < mmpLen; i++) {
                    Object index = mmp.get("param" + (i + 1));
                    paramCls[i] = index.getClass();
                    if (List.class.isAssignableFrom(paramCls[i])) {
                        return falseLocker;
                    }
                }
            }

            // 2、处理Map类型参数
            // 2、Process Map param
        } else if (paramObj instanceof Map) {//不支持批量
            @SuppressWarnings("rawtypes")
            Map map = (Map) paramObj;
            if (map.get("list") != null || map.get("array") != null) {
                return falseLocker;
            } else {
                paramCls = new Class<?>[] { Map.class };
            }
            // 3、处理POJO实体对象类型的参数
            // 3、Process POJO entity param
        } else {
            paramCls = new Class<?>[] { paramObj.getClass() };
        }

        Cache.MethodSignature vm = new Cache.MethodSignature(ms.getId(), paramCls);
        VersionLocker versionLocker = versionLockerCache.getVersionLocker(vm);
        if (null != versionLocker) {
            return versionLocker;
        }

        Class<?> mapper = getMapper(ms);
        if (mapper != null) {
            Method m = ReflectUtil.getDeclaredMethod(mapper, getMapperShortId(ms), paramCls);
            // 如果没有取得@VersionLocker注释，默认开启乐观锁
            if (null == m) {
                versionLocker = trueLocker;
            } else {
                // 取得@VersionLocker注释
                versionLocker = m.getAnnotation(VersionLocker.class);
                if (null == versionLocker) {
                    versionLocker = trueLocker;
                }
            }
            if (!versionLockerCache.containMethodSignature(vm)) {
                versionLockerCache.cacheMethod(vm, versionLocker);
            }
            return versionLocker;
        } else {
            throw new RuntimeException("Config info error, maybe you have not config the Mapper interface");
        }
    }

    /**
     * getMapper
     * @param ms
     * @return
     */
    private Class<?> getMapper(MappedStatement ms) {
        String namespace = getMapperNamespace(ms);
        Collection<Class<?>> mappers = ms.getConfiguration().getMapperRegistry().getMappers();
        for (Class<?> clazz : mappers) {
            if (clazz.getName().equals(namespace)) {
                return clazz;
            }
        }
        return null;
    }

    /**
     * getMapperNamespace
     * @param ms
     * @return
     */
    private String getMapperNamespace(MappedStatement ms) {
        String id = ms.getId();
        int pos = id.lastIndexOf(".");
        return id.substring(0, pos);
    }

    /**
     * getMapperShortId
     * @param ms
     * @return
     */
    private String getMapperShortId(MappedStatement ms) {
        String id = ms.getId();
        int pos = id.lastIndexOf(".");
        return id.substring(pos + 1);
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler || target instanceof ParameterHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        if (null != properties && !properties.isEmpty()){
            props = properties;
        }
    }
}
