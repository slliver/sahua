package com.slliver.web;

import com.slliver.base.controller.WebBaseController;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.common.Constant;
import com.slliver.common.domain.AjaxRichResult;
import com.slliver.common.paging.PageWapper;
import com.slliver.entity.*;
import com.slliver.service.ApiUserCoinActionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 渠道管理
 * @author: slliver
 * @date: 2018/11/20 13:36
 * @version: 1.0
 */
@RequestMapping("event")
@Controller
public class UserCoinEventController extends WebBaseController<ApiUserCoin> {

    @Autowired
    private ApiUserCoinActionService apiUserCoinActionService;


    @RequestMapping("/list")
    public String list(Model model, BaseSearchCondition condition) {
        PageWapper<ApiUserCoinAction> pageWapper = apiUserCoinActionService.obtainEventList(condition);
        model.addAttribute("dataList", pageWapper.getList());
        model.addAttribute("pagnation", pageWapper.getPagingHtml());
        model.addAttribute("condition", condition);
        return getViewPath("list");
    }

    @GetMapping(value = "add")
    public String add(Model model) {
        // 获取nginx服务器的地址
        model.addAttribute("serverPath", Constant.SERVER_IMAGE_ADDRESS);
        model.addAttribute("serverAppPath", Constant.SERVER_APP_ADDRESS);
        return getViewPath("add");
    }

    @RequestMapping("/save")
    @ResponseBody
    public AjaxRichResult save(ApiUserCoinAction coinAction) {
        AjaxRichResult result = new AjaxRichResult();

        if (StringUtils.isBlank(coinAction.getAction())) {
            result.setFailMsg("事件编码为空");
            return result;
        }

        if (StringUtils.isBlank(coinAction.getActionName())) {
            result.setFailMsg("事件名称为空");
            return result;
        }

        if (coinAction.getMinPower() > coinAction.getMaxPower()) {
            result.setFailMsg("金币数错误");
            return result;
        }

        int count = apiUserCoinActionService.add(coinAction);
        if (count > 0) {
            result.setSucceedMsg("添加成功");
        } else {
            result.setFailMsg("添加失败");
        }
        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public AjaxRichResult update(ApiUserCoinAction coinAction) {
        AjaxRichResult result = new AjaxRichResult();

        if (StringUtils.isBlank(coinAction.getAction())) {
            result.setFailMsg("事件编码为空");
            return result;
        }

        if (StringUtils.isBlank(coinAction.getActionName())) {
            result.setFailMsg("事件名称为空");
            return result;
        }

        if (coinAction.getMinPower() > coinAction.getMaxPower()) {
            result.setFailMsg("金币数错误");
            return result;
        }

        int count = apiUserCoinActionService.update(coinAction);
        if (count > 0) {
            result.setSucceedMsg("添加成功");
        } else {
            result.setFailMsg("添加失败");
        }
        return result;
    }

    @GetMapping(value = "{pkid}/edit")
    public String edit(@PathVariable String pkid, Model model) {
        // 获取nginx服务器的地址
        model.addAttribute("serverPath", Constant.SERVER_IMAGE_ADDRESS);
        model.addAttribute("serverAppPath", Constant.SERVER_APP_ADDRESS);
        model.addAttribute("event", apiUserCoinActionService.obtainEventViapkid(pkid));
        return getViewPath("edit");
    }

    @RequestMapping(value = "{pkid}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable String pkid){
        apiUserCoinActionService.delete(pkid);
        return "redirect:/event/list";
    }

        @Override
    protected String getPath() {
        return "/event";
    }
}
