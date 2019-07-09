package ${basePackage}.service;

import com.github.pagehelper.PageHelper;
import com.zhongwang.ibmp.base.common.HmBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhongwang.ibmp.browser.common.constants.Constants;
import com.zhongwang.sys.core.common.paging.PageWapper;
import org.springframework.stereotype.Service;
import ${basePackage}.entity.${modelNameUpperCamel};
import ${basePackage}.dao.${modelNameUpperCamel}Mapper;


/**
 * @Description: 基于通用MyBatis Mapper插件的Service接口的实现
 * @author: ${author}
 * @version: 1.0
 */
@Service
public class ${modelNameUpperCamel}Service extends HmBaseService<${modelNameUpperCamel}>{

    @Autowired
    protected ${modelNameUpperCamel}Mapper mapper;

    public PageWapper<${modelNameUpperCamel}> selectListByPage(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        PageHelper.startPage(1, Constants.WEB_PAGE_SIZE);
        return new PageWapper<>();
    }
}
