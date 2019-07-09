package com.slliver.web;

import com.slliver.base.controller.WebBaseController;
import com.slliver.common.domain.AjaxRichResult;
import com.slliver.entity.ApiIndexMessage;
import com.slliver.service.IndexMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/22 13:45
 * @version: 1.0
 */
@Controller
@RequestMapping("/message")
public class IndexMessageController extends WebBaseController<ApiIndexMessage> {

    @Autowired
    private IndexMessageService messageService;

    @GetMapping(value = "edit")
    public String edit(Model model) {
        ApiIndexMessage message = this.messageService.selectIndex();
        model.addAttribute("message", message);
        return getViewPath("edit");
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public AjaxRichResult update(ApiIndexMessage message) {
        AjaxRichResult result = new AjaxRichResult();

        try {
            this.messageService.update(message);
        } catch (Exception e) {
            logger.error("保存记录报错");
        }
        result.setSucceedMsg("修改成功");
        return result;
    }

    @Override
    protected String getPath() {
        return "/message";
    }
}
