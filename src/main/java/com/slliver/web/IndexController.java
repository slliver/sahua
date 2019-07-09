package com.slliver.web;

import com.slliver.base.controller.WebBaseController;
import com.slliver.base.domain.BaseDomain;
import com.slliver.common.Constant;
import com.slliver.common.domain.AjaxRichResult;
import com.slliver.common.utils.CipherUtil;
import com.slliver.common.utils.ContextUtil;
import com.slliver.entity.ApiUser;
import com.slliver.service.ApiUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/13 17:54
 * @version: 1.0
 */
@Controller
@RequestMapping("index")
public class IndexController extends WebBaseController<BaseDomain> {

    @Autowired
    private ApiUserService userService;

    @RequestMapping("index")
    public String index() {
        return getViewPath("index");
    }


    @RequestMapping("profile")
    public String profile() {
        return getViewPath("profile");
    }

    @PostMapping(value = "/password")
    @ResponseBody
    public AjaxRichResult password(ApiUser user) {
        AjaxRichResult result = new AjaxRichResult();
        String password = user.getPassword();
        String newPassword = user.getNewPassword();
        String conPassowrd = user.getConfirmPassword();
        if (StringUtils.isBlank(password)) {
            result.setFailMsg("当前密码不能为空");
            return result;
        }

        ApiUser currentUser = ContextUtil.getCurrentUser();
        String userName = currentUser.getUserName();
        String pwrsMD5 = CipherUtil.generatePassword(password);
        String salt = currentUser.getSalt();

        // 获取转换后的当前用户的密码
        String currPassword = CipherUtil.createPwdEncrypt(userName, pwrsMD5, salt);
        if (!Objects.equals(currPassword, currentUser.getPassword())) {
            result.setFailMsg("输入的当前密码错误");
            return result;
        }

        if (StringUtils.isBlank(newPassword)) {
            result.setFailMsg("新密码不能为空");
            return result;
        }

        if (StringUtils.isBlank(conPassowrd)) {
            result.setFailMsg("确认新密码不能为空");
            return result;
        }

        if (!Objects.equals(newPassword, conPassowrd)) {
            result.setFailMsg("新密码和确认新密码输入不一致");
            return result;
        }


        String newPwrsMD5 = CipherUtil.generatePassword(newPassword);
        String newSalt = CipherUtil.createSalt();
        String updatePassword = CipherUtil.createPwdEncrypt(userName, newPwrsMD5, newSalt);

        ApiUser apiUser = this.userService.selectByPkid(currentUser.getPkid());
        apiUser.setPassword(updatePassword);
        apiUser.setSalt(newSalt);
        userService.update(apiUser);
        result.setSucceedMsg("密码修改成功，请重新登录");

        return result;
    }

    @Override
    protected String getPath() {
        return "/index";
    }
}
