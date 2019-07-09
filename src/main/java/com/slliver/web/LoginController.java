package com.slliver.web;

import com.slliver.base.controller.WebBaseController;
import com.slliver.base.domain.BaseDomain;
import com.slliver.common.Constant;
import com.slliver.common.domain.AjaxRichResult;
import com.slliver.common.interceptor.shiro.MultiCompanyUserToken;
import com.slliver.common.utils.CipherUtil;
import com.slliver.common.utils.ContextUtil;
import com.slliver.entity.ApiUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/5 16:15
 * @version: 1.0
 */
@Controller
@CrossOrigin
public class LoginController extends WebBaseController<BaseDomain> {

    @GetMapping(value = "/loginIndex")
    public String loginIndex(HttpServletRequest request) {
        ApiUser sessionUser = (ApiUser) WebUtils.getSessionAttribute(request, Constant.SESSION_KEY.USER);
        if(sessionUser == null){
            return getViewPath("login");
        }

        return "redirect:/index/index";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public AjaxRichResult login(ApiUser user) {
        AjaxRichResult result = new AjaxRichResult();
        String userName = user.getUserName();
        String password = user.getPassword();
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            result.setFailMsg("用户名或者密码为空，请重新输入");
            return result;
        }

        String errorMessage = "";
        Subject currentUser = SecurityUtils.getSubject();
        // shiro加入身份验证
        MultiCompanyUserToken token = new MultiCompanyUserToken(userName, password.toUpperCase());
        // 加入记住我的功能
        token.setRememberMe(true);

        try {
            // 如果没有登陆过，当然要进行login操作
            if (!currentUser.isAuthenticated()) {
                currentUser.login(token);
            } else {
                //已经登录的，回退到登录页面重新登录，分情况处理
                //如果是输入了不同的账号想重新登录的，也执行login动作。
                if (!userName.equals(ContextUtil.getCurrentUser().getUserName())) {
                    //先把旧用户注销
                    currentUser.logout();
                    //再用新用户信息登录
                    currentUser.login(token);
                }
            }
            //记录登录日志
            logger.info("系统登录：登录人=" + userName, Constant.SYS_LOG.DATABASE_LOG, Constant.SYS_MODULES.SYS);
        } catch (UnknownAccountException uae) {
            errorMessage = "用户名密码错误";// 用户名或密码有误
        } catch (IncorrectCredentialsException ice) {
            errorMessage = "密码错误"; // 密码错误
        } catch (LockedAccountException lae) {
            errorMessage = "当前用户未激活";// 未激活
        } catch (ExcessiveAttemptsException eae) {
            errorMessage = "您输入的密码错误次数太多";// 错误次数过多
        } catch (AuthenticationException ae) {
            errorMessage = "验证未通过";// 验证未通过
        }

        // 验证是否登录成功
        if (!currentUser.isAuthenticated()) {
            token.clear();
        }

        if (StringUtils.isNotEmpty(errorMessage)) {
            result.setFailMsg(errorMessage);
            return result;
        }

        result.setSucceedMsg("登录成功");
        return result;
    }

    /**
     * 帐号注销
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpSession session) {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        session = request.getSession(true);
        removeSessionAttributes(session);
        return "redirect:loginIndex";
    }

    private void removeSessionAttributes(HttpSession session) {
        //逐渐积累要清空的session属性。
        session.removeAttribute(Constant.SESSION_KEY.USER);
        session.removeAttribute(Constant.SESSION_KEY.MENULIST);
    }

    @Override
    protected String getPath() {
        return "/login";
    }
}
