package com.slliver.base.controller;

import com.slliver.base.domain.BaseDomain;
import com.slliver.common.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/8 13:43
 * @version: 1.0
 */
public class ApiBaseController<T extends BaseDomain> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 得到32位的uuid
     *
     * @return
     */
    protected String get32UUID() {
        return UuidUtil.get32UUID();
    }
}
