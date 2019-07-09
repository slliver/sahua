package com.slliver.common.utils;

import com.slliver.common.Constant;
import com.slliver.entity.ApiUser;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/13 15:23
 * @version: 1.0
 */
public class ContextUtil {
    private static Logger logger = LoggerFactory.getLogger(ContextUtil.class);

    /**
     * 获取当前对象的拷贝
     */
    public static ApiUser getCurrentUser() {
        ApiUser customer = null;
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        if (null != session) {
            Object obj = session.getAttribute(Constant.SESSION_KEY.USER);
            if (null != obj && obj instanceof ApiUser) {
                try {
                    /**
                     * 复制一份对象，防止被错误操作
                     */
                    customer = (ApiUser) BeanUtils.cloneBean(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return customer;
    }

    /**
     * 获取当前user pkid
     *
     * @return
     */
    public static String getCurrentUserPkid() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        if (null != session) {
            Object obj = session.getAttribute(Constant.SESSION_KEY.USER);
            if (null != obj && obj instanceof ApiUser) {
                return ((ApiUser)obj).getPkid();
            }else {
                //throw new RQServiceException("user object in session is null");
                logger.info("getCurrentUserPkid : user object in session is null");
            }
        } else {
            //throw new ZWServiceException("Session is null");
            logger.info("getCurrentUserPkid : Session is null");
        }

        return "admin";
    }
}
