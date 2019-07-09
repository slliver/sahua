package com.slliver.base.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.slliver.base.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "loan_data")
public class LoanData extends BaseDomain {
    /**
     * 商家logo
     */
    @Column(name = "logo_pkid")
    private String logoPkid;

    /**
     * 商家名称
     */
    @Column(name = "org_name")
    private String orgName;

    /**
     * 商家简介
     */
    private String des;

    /**
     * 借款金额
     */
    @Column(name = "loan_amount")
    private String loanAmount;

    /**
     * 借款期限
     */
    @Column(name = "loan_time")
    private String loanTime;

    /**
     * 参考月利率
     */
    @Column(name = "monthly_interest_rate")
    private String monthlyInterestRate;

    /**
     * 参考日利率
     */
    @Column(name = "day_interest_rate")
    private String dayInterestRate;

    /**
     * 参考通过率
     */
    @Column(name = "pass_rate")
    private String passRate;

    /**
     * 最快放款速度
     */
    @Column(name = "fastest_speed")
    private String fastestSpeed;

    /**
     * 申请人数
     */
    @Column(name = "total_apply")
    private Integer totalApply;

    /**
     * 所属标签
     */
    private String label;

    /**
     * 跳转地址
     */
    private String url;

    /**
     * 预留字段
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "banner_pkid")
    private String bannerPkid;

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
    private String reserved2;

    /**
     * 预留字段
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reserved3;

    /**
     * 预留字段
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reserved4;

    /**
     * 预留字段
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reserved5;

    /**
     * 获取商家logo
     *
     * @return logo_pkid - 商家logo
     */
    public String getLogoPkid() {
        return logoPkid;
    }

    /**
     * 设置商家logo
     *
     * @param logoPkid 商家logo
     */
    public void setLogoPkid(String logoPkid) {
        this.logoPkid = logoPkid;
    }

    /**
     * 获取商家名称
     *
     * @return org_name - 商家名称
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * 设置商家名称
     *
     * @param orgName 商家名称
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 获取商家简介
     *
     * @return des - 商家简介
     */
    public String getDes() {
        return des;
    }

    /**
     * 设置商家简介
     *
     * @param des 商家简介
     */
    public void setDes(String des) {
        this.des = des;
    }

    /**
     * 获取借款金额
     *
     * @return loan_amount - 借款金额
     */
    public String getLoanAmount() {
        return loanAmount;
    }

    /**
     * 设置借款金额
     *
     * @param loanAmount 借款金额
     */
    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    /**
     * 获取借款期限
     *
     * @return loan_time - 借款期限
     */
    public String getLoanTime() {
        return loanTime;
    }

    /**
     * 设置借款期限
     *
     * @param loanTime 借款期限
     */
    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime;
    }

    /**
     * 获取参考月利率
     *
     * @return monthly_interest_rate - 参考月利率
     */
    public String getMonthlyInterestRate() {
        return monthlyInterestRate;
    }

    /**
     * 设置参考月利率
     *
     * @param monthlyInterestRate 参考月利率
     */
    public void setMonthlyInterestRate(String monthlyInterestRate) {
        this.monthlyInterestRate = monthlyInterestRate;
    }

    public String getDayInterestRate() {
        return dayInterestRate;
    }

    public void setDayInterestRate(String dayInterestRate) {
        this.dayInterestRate = dayInterestRate;
    }

    /**
     * 获取参考通过率
     *
     * @return pass_rate - 参考通过率
     */
    public String getPassRate() {
        return passRate;
    }

    /**
     * 设置参考通过率
     *
     * @param passRate 参考通过率
     */
    public void setPassRate(String passRate) {
        this.passRate = passRate;
    }

    public String getFastestSpeed() {
        return fastestSpeed;
    }

    public void setFastestSpeed(String fastestSpeed) {
        this.fastestSpeed = fastestSpeed;
    }

    /**
     * 获取人情人数
     *
     * @return total_apply - 人情人数
     */
    public Integer getTotalApply() {
        return totalApply;
    }

    /**
     * 设置人情人数
     *
     * @param totalApply 人情人数
     */
    public void setTotalApply(Integer totalApply) {
        this.totalApply = totalApply;
    }

    /**
     * 获取所属标签
     *
     * @return label - 所属标签
     */
    public String getLabel() {
        return label;
    }

    /**
     * 设置所属标签
     *
     * @param label 所属标签
     */
    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

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

    /**
     * 获取预留字段
     *
     * @return reserved3 - 预留字段
     */
    public String getReserved3() {
        return reserved3;
    }

    /**
     * 设置预留字段
     *
     * @param reserved3 预留字段
     */
    public void setReserved3(String reserved3) {
        this.reserved3 = reserved3;
    }

    /**
     * 获取预留字段
     *
     * @return reserved4 - 预留字段
     */
    public String getReserved4() {
        return reserved4;
    }

    /**
     * 设置预留字段
     *
     * @param reserved4 预留字段
     */
    public void setReserved4(String reserved4) {
        this.reserved4 = reserved4;
    }

    /**
     * 获取预留字段
     *
     * @return reserved5 - 预留字段
     */
    public String getReserved5() {
        return reserved5;
    }

    /**
     * 设置预留字段
     *
     * @param reserved5 预留字段
     */
    public void setReserved5(String reserved5) {
        this.reserved5 = reserved5;
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