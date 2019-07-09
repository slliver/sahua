package com.slliver.base.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.slliver.base.domain.BaseDomain;
import java.util.Date;
import javax.persistence.*;

@Table(name = "credit_card")
public class CreditCard extends BaseDomain {
    /**
     * 比如：wv9szgrm_4hzbakwz.jpg
     */
    @Column(name = "logo_pkid")
    private String logoPkid;

    /**
     * 发行方名称：招商银行、建设银行...
     */
    @Column(name = "bank_name")
    private String bankName;

    /**
     * 信用卡名称
     */
    @Column(name = "card_name")
    private String cardName;

    /**
     * 发行方简介
     */
    @Column(name = "bank_des")
    private String bankDes;

    /**
     * 所属标签:额度高、下款快
     */
    private String label;

    /**
     * 跳转地址
     */
    private String url;

    @Column(name = "banner_pkid")
    private String bannerPkid;

    /**
     * 备注
     */
    private String remark;

    /**
     * 适用设备(0-全部；1-ios；2-android)
     */
    private Short device;

    /**
     *  优先级
     */
    private Short priority;

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
     * 获取发行方名称：招商银行、建设银行...
     *
     * @return bank_name - 发行方名称：招商银行、建设银行...
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 设置发行方名称：招商银行、建设银行...
     *
     * @param bankName 发行方名称：招商银行、建设银行...
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    /**
     * 获取发行方简介
     *
     * @return bank_des - 发行方简介
     */
    public String getBankDes() {
        return bankDes;
    }

    /**
     * 设置发行方简介
     *
     * @param bankDes 发行方简介
     */
    public void setBankDes(String bankDes) {
        this.bankDes = bankDes;
    }

    /**
     * 获取所属标签:额度高、下款快
     *
     * @return label - 所属标签:额度高、下款快
     */
    public String getLabel() {
        return label;
    }

    /**
     * 设置所属标签:额度高、下款快
     *
     * @param label 所属标签:额度高、下款快
     */
    public void setLabel(String label) {
        this.label = label;
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

    public String getBannerPkid() {
        return bannerPkid;
    }

    public void setBannerPkid(String bannerPkid) {
        this.bannerPkid = bannerPkid;
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

    public Short getDevice() {
        return device;
    }

    public void setDevice(Short device) {
        this.device = device;
    }

    public Short getPriority() {
        return priority;
    }

    public void setPriority(Short priority) {
        this.priority = priority;
    }
}