package com.slliver.web;

import com.slliver.base.controller.WebBaseController;
import com.slliver.common.domain.AjaxRichResult;
import com.slliver.entity.ApiIndexMessage;
import com.slliver.entity.ApiMessageText;
import com.slliver.service.IndexMessageService;
import com.slliver.service.MessageTextService;
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
@RequestMapping("/messagetext")
public class MessageTextController extends WebBaseController<ApiIndexMessage> {

    @Autowired
    private MessageTextService messageTextService;

    @GetMapping(value = "edit")
    public String edit(Model model) {
        ApiMessageText message = this.messageTextService.selectIndex();
        model.addAttribute("message", message);
        return getViewPath("edit");
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public AjaxRichResult update(ApiMessageText message) {
        AjaxRichResult result = new AjaxRichResult();

        ApiMessageText msg = this.messageTextService.selectIndex();
        if (msg == null) {
            int count = messageTextService.insert(message);
            if (count > 0) {
                result.setSucceedMsg("添加成功");
            } else {
                result.setSucceedMsg("修改失败");
            }
        } else {
            try {
                this.messageTextService.update(message);
            } catch (Exception e) {
                logger.error("保存记录报错");
            }
            result.setSucceedMsg("修改成功");
        }
        return result;
    }

    @Override
    protected String getPath() {
        return "/messagetext";
    }
}
