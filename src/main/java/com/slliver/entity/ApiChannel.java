package com.slliver.entity;

import com.slliver.base.entity.Channel;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/11/20 13:32
 * @version: 1.0
 */
@Table(name = "channel")
public class ApiChannel extends Channel {

    @Transient
    private String checked;

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

}
