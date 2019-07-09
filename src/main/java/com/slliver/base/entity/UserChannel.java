package com.slliver.base.entity;

import com.slliver.base.domain.BaseDomain;
import java.util.Date;
import javax.persistence.*;

@Table(name = "user_channel")
public class UserChannel extends BaseDomain {
    /**
     * 用户pkid
     */
    @Column(name = "user_pkid")
    private String userPkid;

    /**
     * 渠道PKID
     */
    @Column(name = "channel_pkid")
    private String channelPkid;

    /**
     * 渠道编号
     */
    @Column(name = "channel_code")
    private String channelCode;

    /**
     * 备注
     */
    private String remark;

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

    /**
     * 获取banner名称
     *
     * @return channel_pkid - banner名称
     */
    public String getChannelPkid() {
        return channelPkid;
    }

    /**
     * 设置banner名称
     *
     * @param channelPkid banner名称
     */
    public void setChannelPkid(String channelPkid) {
        this.channelPkid = channelPkid;
    }


    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
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
}