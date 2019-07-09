package com.slliver.entity;

import com.slliver.base.entity.LoanData;
import com.slliver.base.entity.LoanDetail;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @Description: 极速贷
 * @author: slliver
 * @date: 2018/3/12 16:32
 * @version: 1.0
 */
@Table(name = "loan_data")
public class ApiLoanData extends LoanData {
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
    private List<ApiLoanDetail> applyConditions;

    /**
     * 所需材料
     */
    @Transient
    private List<ApiLoanDetail> reqMaterials;

    /**
     * 提前还款说明
     */
    @Transient
    private List<ApiLoanDetail> earlyRepayments;

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

    public List<ApiLoanDetail> getApplyConditions() {
        return applyConditions;
    }

    public void setApplyConditions(List<ApiLoanDetail> applyConditions) {
        this.applyConditions = applyConditions;
    }

    public List<ApiLoanDetail> getReqMaterials() {
        return reqMaterials;
    }

    public void setReqMaterials(List<ApiLoanDetail> reqMaterials) {
        this.reqMaterials = reqMaterials;
    }

    public List<ApiLoanDetail> getEarlyRepayments() {
        return earlyRepayments;
    }

    public void setEarlyRepayments(List<ApiLoanDetail> earlyRepayments) {
        this.earlyRepayments = earlyRepayments;
    }
}
