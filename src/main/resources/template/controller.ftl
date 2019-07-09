package ${basePackage}.web;

import com.zhongwang.ibmp.base.common.HmBaseController;
import com.zhongwang.ibmp.browser.common.constants.PathConstant;
import ${basePackage}.entity.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import com.zhongwang.sys.core.common.paging.PageWapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 功能详细描述
 * @author: ${author}
 * @date: ${date}
 * @version: 1.0
 */

@RequestMapping("${baseRequestMapping}")
@Controller
public class ${modelNameUpperCamel}Controller extends HmBaseController<${modelNameUpperCamel}> {

    @Autowired
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(Model model, ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        PageWapper<${modelNameUpperCamel}> pageWapper = this.${modelNameLowerCamel}Service.selectListByPage(${modelNameLowerCamel});
        model.addAttribute("list", pageWapper.getList());
        model.addAttribute("pagnation", pageWapper.getPagingHtml());
        return getViewPath("list");
    }

    @Override
    protected String getPath() {
        return PathConstant.PATH_BASE_PREFIX + "demo";
    }
}
