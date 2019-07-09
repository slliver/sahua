package com.slliver.base.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 基础信息类父类
 * @author: slliver
 * @date: 2018/3/5 15:15
 * @version: 1.0
 */
public class BaseDomain implements Serializable {

    private static final long serialVersionUID = 2029854766923310451L;

    /**
     * UUID形式的主键字段
     */
    @Id
    @Column(name = "pkid")
    @GeneratedValue(generator = "JDBC")
    protected String pkid;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "flag_sort")
    protected Short flagSort;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "flag_delete")
    protected Short flagDelete;
    /**
     * 并发版本号
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "flag_version")
    protected Long flagVersion;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "make_time")
    protected Date makeTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "make_user")
    protected String makeUser;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "modify_time")
    protected Date modifyTime;



    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "modify_user")
    protected String modifyUser;

    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public Short getFlagSort() {
        return flagSort;
    }

    public void setFlagSort(Short flagSort) {
        this.flagSort = flagSort;
    }

    public Short getFlagDelete() {
        return flagDelete;
    }

    public void setFlagDelete(Short flagDelete) {
        this.flagDelete = flagDelete;
    }

    public Long getFlagVersion() {
        return flagVersion;
    }

    public void setFlagVersion(Long flagVersion) {
        this.flagVersion = flagVersion;
    }

    public Date getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(Date makeTime) {
        this.makeTime = makeTime;
    }

    public String getMakeUser() {
        return makeUser;
    }

    public void setMakeUser(String makeUser) {
        this.makeUser = makeUser;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BaseDomain)) return false;

        BaseDomain that = (BaseDomain) o;

        return new EqualsBuilder()
                .append(getPkid(), that.getPkid())
                .append(getFlagSort(), that.getFlagSort())
                .append(getFlagDelete(), that.getFlagDelete())
                .append(getFlagVersion(), that.getFlagVersion())
                .append(getMakeTime(), that.getMakeTime())
                .append(getModifyTime(), that.getModifyTime())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getPkid())
                .append(getFlagSort())
                .append(getFlagDelete())
                .append(getFlagVersion())
                .append(getMakeTime())
                .append(getModifyTime())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "BaseDomain{" +
                "pkid='" + pkid + '\'' +
                ", flagSort=" + flagSort +
                ", flagDelete=" + flagDelete +
                ", flagVersion=" + flagVersion +
                ", makeTime=" + makeTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
