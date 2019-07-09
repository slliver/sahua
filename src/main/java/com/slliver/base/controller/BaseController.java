package com.slliver.base.controller;

import com.slliver.base.domain.BaseDomain;
import com.slliver.common.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/5 13:42
 * @version: 1.0
 */
public class BaseController<T extends BaseDomain> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 得到request对象
     */
    protected HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 得到32位的uuid
     *
     * @return
     */
    protected String get32UUID() {
        return UuidUtil.get32UUID();
    }


}
