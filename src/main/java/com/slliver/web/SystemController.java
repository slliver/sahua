package com.slliver.web;

import com.slliver.base.controller.WebBaseController;
import com.slliver.common.Constant;
import com.slliver.common.PathConstant;
import com.slliver.entity.ApiUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description: 处理一些特殊的请求
 * @author: slliver
 * @date: 2018/11/22 16:47
 * @version: 1.0
 */
@Controller
@RequestMapping("/")
public class SystemController extends WebBaseController {

    @RequestMapping("")
    public String index(Model model, HttpServletRequest request, HttpSession session){
        ApiUser sessionUser = (ApiUser) WebUtils.getSessionAttribute(request, Constant.SESSION_KEY.USER);
        if(sessionUser == null){
            return getViewPath("login");
        }

        return "redirect:/index/index";
    }

    @RequestMapping("error")
    public String error(Model model,HttpServletRequest request,HttpSession session){
        return "error";
    }

    @Override
    protected String getPath() {
        return "/login";
    }
}
