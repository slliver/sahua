package com.slliver.base.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.slliver.base.domain.BaseDomain;
import java.util.Date;
import javax.persistence.*;
@Table(name = "banner")
public class Banner extends BaseDomain {

    /**
     * 比如：wv9szgrm_4hzbakwz.jpg
     */
    @Column(name = "logo_pkid")
    private String logoPkid;

    /**
     * banner名称
     */
    private String name;

    /**
     * 业务类型：1-极速贷；2-信用卡; 3-帮帮
     */
    @Column(name = "bussiness_type")
    private String bussinessType;

    /**
     * 对应的业务pkid
     */
    @Column(name = "bussiness_pkid")
    private String bussinessPkid;

    /**
     * 跳转类型：json、web
     */
    @Column(name = "forward_type")
    private String forwardType;

    /**
     * 跳转地址
     */
    private String url;

    /**
     * 备注
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String remark;

    /**
     * 预留字段
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reserved1;

    /**
     * 预留字段
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reserved2;

    /**
     * 获取比如：wv9szgrm_4hzbakwz.jpg
     *
     * @return logo_pkid - 比如：wv9szgrm_4hzbakwz.jpg
     */
    public String getLogoPkid() {
        return logoPkid;
    }

    /**
     * 设置比如：wv9szgrm_4hzbakwz.jpg
     *
     * @param logoPkid 比如：wv9szgrm_4hzbakwz.jpg
     */
    public void setLogoPkid(String logoPkid) {
        this.logoPkid = logoPkid;
    }

    /**
     * 获取banner名称
     *
     * @return name - banner名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置banner名称
     *
     * @param name banner名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取业务类型：1-极速贷；2-信用卡; 3-帮帮
     *
     * @return bussiness_type - 业务类型：1-极速贷；2-信用卡; 3-帮帮
     */
    public String getBussinessType() {
        return bussinessType;
    }

    /**
     * 设置业务类型：1-极速贷；2-信用卡; 3-帮帮
     *
     * @param bussinessType 业务类型：1-极速贷；2-信用卡; 3-帮帮
     */
    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public String getBussinessPkid() {
        return bussinessPkid;
    }

    public void setBussinessPkid(String bussinessPkid) {
        this.bussinessPkid = bussinessPkid;
    }

    /**
     * 获取跳转类型：json、web
     *
     * @return forward_type - 跳转类型：json、web
     */
    public String getForwardType() {
        return forwardType;
    }

    /**
     * 设置跳转类型：json、web
     *
     * @param forwardType 跳转类型：json、web
     */
    public void setForwardType(String forwardType) {
        this.forwardType = forwardType;
    }

    /**
     * 获取跳转地址
     *
     * @return url - 跳转地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置跳转地址
     *
     * @param url 跳转地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取预留字段
     *
     * @return reserved1 - 预留字段
     */
    public String getReserved1() {
        return reserved1;
    }

    /**
     * 设置预留字段
     *
     * @param reserved1 预留字段
     */
    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    /**
     * 获取预留字段
     *
     * @return reserved2 - 预留字段
     */
    public String getReserved2() {
        return reserved2;
    }

    /**
     * 设置预留字段
     *
     * @param reserved2 预留字段
     */
    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }
}