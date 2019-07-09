package com.slliver.entity;

import com.slliver.base.entity.LoanDaquanData;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @Description: 贷款大全
 * @author: slliver
 * @date: 2019/7/8 9:10
 * @version: 1.0
 */
@Table(name = "loan_daquan_data")
public class ApiLoanDaquanData extends LoanDaquanData {
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

    /**
     * 申请条件
     */
    @Transient
    private List<ApiLoanDaquanDetail> applyConditions;

    /**
     * 所需材料
     */
    @Transient
    private List<ApiLoanDaquanDetail> reqMaterials;

    /**
     * 提前还款说明
     */
    @Transient
    private List<ApiLoanDaquanDetail> earlyRepayments;

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

    public List<ApiLoanDaquanDetail> getApplyConditions() {
        return applyConditions;
    }

    public void setApplyConditions(List<ApiLoanDaquanDetail> applyConditions) {
        this.applyConditions = applyConditions;
    }

    public List<ApiLoanDaquanDetail> getReqMaterials() {
        return reqMaterials;
    }

    public void setReqMaterials(List<ApiLoanDaquanDetail> reqMaterials) {
        this.reqMaterials = reqMaterials;
    }

    public List<ApiLoanDaquanDetail> getEarlyRepayments() {
        return earlyRepayments;
    }

    public void setEarlyRepayments(List<ApiLoanDaquanDetail> earlyRepayments) {
        this.earlyRepayments = earlyRepayments;
    }
}
