package com.slliver.entity;

import com.slliver.base.entity.Banner;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Description: 横幅banner
 * @author: slliver
 * @date: 2018/3/16 15:48
 * @version: 1.0
 */
@Table(name = "banner")
public class ApiBanner extends Banner {

    @Transient
    private String httpUrl;

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }
}
