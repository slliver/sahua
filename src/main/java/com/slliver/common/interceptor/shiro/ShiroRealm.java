package com.slliver.common.interceptor.shiro;

import com.slliver.common.Constant;
import com.slliver.common.spring.SpringContextHolder;
import com.slliver.common.utils.CipherUtil;
import com.slliver.entity.ApiUser;
import com.slliver.service.ApiUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Description: 自定义授权Realm
 * @author: slliver
 * @date: 2018/2/1 14:14
 * @version: 1.0
 */
public class ShiroRealm extends AuthorizingRealm {

    private static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
    /**
     * 账户类服务层注入
     */
    @Autowired
    private ApiUserService apiUserService;

    /*
     * 登录信息和用户验证信息验证(non-Javadoc)
     * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        MultiCompanyUserToken token = (MultiCompanyUserToken) authcToken;
        AuthenticationInfo authenticationInfo = null;
        String userName = new String(token.getUsername());//用户名
        String password = new String(token.getPassword());//密码
        ApiUser user = apiUserService.selectByUserName(userName);//通过登录名 寻找用户
        if (user != null) {
            //组合username,两次迭代，对密码进行加密
            String pwdEncrypt = CipherUtil.createPwdEncrypt(userName, password, user.getSalt());
            if (user.getPassword().equals(pwdEncrypt)) {
                authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(), password, getName());
                this.setSession(Constant.SESSION_KEY.USER, user);
                // 设置登陆用户pkid
                this.setSession(Constant.SESSION_KEY.LOGIN_KEY, user.getPkid());
                return authenticationInfo;
            } else {
                throw new IncorrectCredentialsException(); /*错误认证异常*/
            }
        } else {
            throw new UnknownAccountException(); /*找不到帐号异常*/
        }
    }

    /*
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法(non-Javadoc)
     * 1、subject.hasRole(“admin”) 或 subject.isPermitted(“admin”)：自己去调用这个是否有什么角色或者是否有什么权限的时候；
     * 2、@RequiresRoles("admin") ：在方法上加注解的时候；
     * 3、[@shiro.hasPermission name = "admin"][/@shiro.hasPermission]：在页面上加shiro标签的时候，即进这个页面的时候扫描到有这个标签的时候。
     * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
        // (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            doClearCache(principals);
            SecurityUtils.getSubject().logout();
            return null;
        }


        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     *
     * @see
     */
    private void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }

    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(Object principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }


    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser extends ApiUser implements Serializable {
        private static final long serialVersionUID = 1778589964992915025L;

        public ShiroUser(String loginName) {
            super();
            this.setUserName(loginName);
        }


        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return super.getUserName();
        }

        /**
         * 重载hashCode,只计算loginName;
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(super.getUserName());
        }

        /**
         * 重载equals,只计算loginName;
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ShiroUser other = (ShiroUser) obj;
            if (super.getUserName() == null) {
                if (other.getUserName() != null)
                    return false;
            } else if (!super.getUserName().equals(other.getUserName()))
                return false;
            return true;
        }
    }

    /**
     * 通过登录名移除权限缓存
     *
     * @param loginName
     * @return boolean
     */
    public static boolean removeAuthCacheByLoginName(String loginName) {
        try {
            SpringContextHolder.getBean(ShiroRealm.class).clearCachedAuthorizationInfo(new ShiroUser(loginName));
            logger.info("" + loginName);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }


    /**
     * 更新用户授权信息缓存.
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 更新用户信息缓存.
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    /**
     * 清除用户信息缓存.
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }
    /**
     * 清空所有缓存
     */
    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 清空所有认证缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
