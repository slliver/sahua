package com.slliver.base.entity;

import com.slliver.base.domain.BaseDomain;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "channel")
public class Channel extends BaseDomain {

    /**
     * 渠道编码
     */
    private String code;

    /**
     * banner名称
     */
    private String name;

    /**
     * 级别：如果是渠道商的话就是0；剩下的对应代理级别，比如1：一级代理
     */
    private Short level;

    @Column(name = "parent_pkid")
    private String parentPkid;

    /**
     * 标识是渠道还是代理；10-渠道；20-代理，现阶段都是渠道
     */
    private Short type;

    /**
     * 负责人(渠道、代理)
     */
    @Column(name = "charge_user")
    private String chargeUser;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 跳转地址
     */
    private String url;

    /**
     * 备注
     */
    private String remark;

    @Column(name = "scale")
    private int scale;

    @Column(name = "group_num")
    private int groupNum;

    @Column(name = "start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date startTime;

    @Column(name = "end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date endTime;


    /**
     * app推广连接
     */
    @Column(name = "app_url")
    private String appUrl;

    /**
     * 预留字段
     */
    private String reserved2;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
     * 获取级别：如果是渠道商的话就是0；剩下的对应代理级别，比如1：一级代理
     *
     * @return level - 级别：如果是渠道商的话就是0；剩下的对应代理级别，比如1：一级代理
     */
    public Short getLevel() {
        return level;
    }

    /**
     * 设置级别：如果是渠道商的话就是0；剩下的对应代理级别，比如1：一级代理
     *
     * @param level 级别：如果是渠道商的话就是0；剩下的对应代理级别，比如1：一级代理
     */
    public void setLevel(Short level) {
        this.level = level;
    }

    /**
     * @return parent_pkid
     */
    public String getParentPkid() {
        return parentPkid;
    }

    /**
     * @param parentPkid
     */
    public void setParentPkid(String parentPkid) {
        this.parentPkid = parentPkid;
    }

    /**
     * 获取标识是渠道还是代理；10-渠道；20-代理，现阶段都是渠道
     *
     * @return type - 标识是渠道还是代理；10-渠道；20-代理，现阶段都是渠道
     */
    public Short getType() {
        return type;
    }

    /**
     * 设置标识是渠道还是代理；10-渠道；20-代理，现阶段都是渠道
     *
     * @param type 标识是渠道还是代理；10-渠道；20-代理，现阶段都是渠道
     */
    public void setType(Short type) {
        this.type = type;
    }

    /**
     * 获取负责人(渠道、代理)
     *
     * @return charge_user - 负责人(渠道、代理)
     */
    public String getChargeUser() {
        return chargeUser;
    }

    /**
     * 设置负责人(渠道、代理)
     *
     * @param chargeUser 负责人(渠道、代理)
     */
    public void setChargeUser(String chargeUser) {
        this.chargeUser = chargeUser;
    }

    /**
     * 获取联系电话
     *
     * @return phone - 联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置联系电话
     *
     * @param phone 联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
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

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public int getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}