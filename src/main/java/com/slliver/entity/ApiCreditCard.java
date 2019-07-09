package com.slliver.entity;

import com.slliver.base.entity.CreditCard;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/13 13:17
 * @version: 1.0
 */
@Table(name = "credit_card")
public class ApiCreditCard extends CreditCard {

    /**
     * 图片地址
     */
    @Transient
    private String httpUrl;
    /**
     * 原始的banner_pkid
     */
    @Transient
    private String originalBannerPkid;

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public String getOriginalBannerPkid() {
        return originalBannerPkid;
    }

    public void setOriginalBannerPkid(String originalBannerPkid) {
        this.originalBannerPkid = originalBannerPkid;
    }
}
