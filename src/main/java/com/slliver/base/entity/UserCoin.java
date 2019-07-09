package com.slliver.base.entity;

import com.slliver.base.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "user_coin")
public class UserCoin extends BaseDomain {
    /**
     * 用户pkid
     */
    @Column(name = "user_pkid")
    private String userPkid;

    @Column(name = "user_amount")
    private Long userAmount;

    @Column(name = "ali_account")
    private String aliAccount;

    @Column(name = "ali_realname")
    private String aliRealname;

    @Column(name = "invate_code")
    private String invateCode;

    @Column(name = "invater")
    private String invater;

	@Column(name = "invate_date")
	private Date invateDate;

	public Date getInvateDate() {
		return invateDate;
	}

	public void setInvateDate(Date invateDate) {
		this.invateDate = invateDate;
	}

	public String getInvateCode() {
        return invateCode;
    }

    public void setInvateCode(String invateCode) {
        this.invateCode = invateCode;
    }

    public String getInvater() {
        return invater;
    }

    public void setInvater(String invater) {
        this.invater = invater;
    }

    /**
     * 获取渠道编号
     *
     * @return user_pkid - 渠道编号
     */
    public String getUserPkid() {
        return userPkid;
    }

    /**
     * 设置渠道编号
     *
     * @param userPkid 渠道编号
     */
    public void setUserPkid(String userPkid) {
        this.userPkid = userPkid;
    }

    public Long getUserAmount() {
        return userAmount;
    }

    public void setUserAmount(Long userAmount) {
        this.userAmount = userAmount;
    }

    public String getAliAccount() {
        return aliAccount;
    }

    public void setAliAccount(String aliAccount) {
        this.aliAccount = aliAccount;
    }

    public String getAliRealname() {
        return aliRealname;
    }

    public void setAliRealname(String aliRealname) {
        this.aliRealname = aliRealname;
    }
}