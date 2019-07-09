package com.slliver.base.domain;

/**
 * @Description: 查询条件不继承分页
 * @author: slliver
 * @date: 2019/2/14 9:16
 * @version: 1.0
 */
public class BaseSearchConditionWithoutPagination {

    private String name;
    private String phone;
    private String code;
    // 渠道号
    private String channelNo;
    private String startTime;
    private String endTime;
    // 设备类型
    private String device;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
