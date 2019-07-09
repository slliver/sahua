package com.slliver.base.entity;

import com.slliver.base.domain.BaseDomain;
import java.util.Date;
import javax.persistence.*;

@Table(name = "index_message")
public class IndexMessage extends BaseDomain {
    @Id
    @GeneratedValue(generator = "JDBC")
    private String pkid;

    /**
     * 累积申请人数
     */
    @Column(name = "total_num")
    private Integer totalNum;

    /**
     * 今日申请人数
     */
    @Column(name = "today_num")
    private Integer todayNum;

    /**
     * 谁在极速贷借款多少
     */
    @Column(name = "loan_num")
    private Integer loanNum;

    /**
     * 绑定的对应业务的pkid
     */
    private String reserved1;

    /**
     * 预留字段
     */
    private String reserved2;

    /**
     * 【系统提供】需要手工排序时使用
     */
    @Column(name = "flag_sort")
    private Short flagSort;

    /**
     * 【系统提供】0-正常；1-逻辑删除。几乎全部查询的where条件都要加上这个条件，默认由框架自动填充为0，开发人员可以手工覆盖
            
     */
    @Column(name = "flag_delete")
    private Short flagDelete;

    /**
     * 【系统提供】取System.currentTimeMillis()值填充，并发修改时检查
     */
    @Column(name = "flag_version")
    private Long flagVersion;

    /**
     * 【系统提供】由框架自动填充当前登录人pk_id
     */
    @Column(name = "make_user")
    private String makeUser;

    /**
     * 【系统提供】由框架自动填充当前系统时间
     */
    @Column(name = "make_time")
    private Date makeTime;

    /**
     * 【系统提供】由框架自动填充当前系统时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * @return pkid
     */
    public String getPkid() {
        return pkid;
    }

    /**
     * @param pkid
     */
    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    /**
     * 获取累积申请人数
     *
     * @return total_num - 累积申请人数
     */
    public Integer getTotalNum() {
        return totalNum;
    }

    /**
     * 设置累积申请人数
     *
     * @param totalNum 累积申请人数
     */
    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    /**
     * 获取今日申请人数
     *
     * @return today_num - 今日申请人数
     */
    public Integer getTodayNum() {
        return todayNum;
    }

    /**
     * 设置今日申请人数
     *
     * @param todayNum 今日申请人数
     */
    public void setTodayNum(Integer todayNum) {
        this.todayNum = todayNum;
    }

    /**
     * 获取谁在极速贷借款多少
     *
     * @return loan_num - 谁在极速贷借款多少
     */
    public Integer getLoanNum() {
        return loanNum;
    }

    /**
     * 设置谁在极速贷借款多少
     *
     * @param loanNum 谁在极速贷借款多少
     */
    public void setLoanNum(Integer loanNum) {
        this.loanNum = loanNum;
    }

    /**
     * 获取绑定的对应业务的pkid
     *
     * @return reserved1 - 绑定的对应业务的pkid
     */
    public String getReserved1() {
        return reserved1;
    }

    /**
     * 设置绑定的对应业务的pkid
     *
     * @param reserved1 绑定的对应业务的pkid
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

    /**
     * 获取【系统提供】需要手工排序时使用
     *
     * @return flag_sort - 【系统提供】需要手工排序时使用
     */
    public Short getFlagSort() {
        return flagSort;
    }

    /**
     * 设置【系统提供】需要手工排序时使用
     *
     * @param flagSort 【系统提供】需要手工排序时使用
     */
    public void setFlagSort(Short flagSort) {
        this.flagSort = flagSort;
    }

    /**
     * 获取【系统提供】0-正常；1-逻辑删除。几乎全部查询的where条件都要加上这个条件，默认由框架自动填充为0，开发人员可以手工覆盖
            
     *
     * @return flag_delete - 【系统提供】0-正常；1-逻辑删除。几乎全部查询的where条件都要加上这个条件，默认由框架自动填充为0，开发人员可以手工覆盖
            
     */
    public Short getFlagDelete() {
        return flagDelete;
    }

    /**
     * 设置【系统提供】0-正常；1-逻辑删除。几乎全部查询的where条件都要加上这个条件，默认由框架自动填充为0，开发人员可以手工覆盖
            
     *
     * @param flagDelete 【系统提供】0-正常；1-逻辑删除。几乎全部查询的where条件都要加上这个条件，默认由框架自动填充为0，开发人员可以手工覆盖
            
     */
    public void setFlagDelete(Short flagDelete) {
        this.flagDelete = flagDelete;
    }

    /**
     * 获取【系统提供】取System.currentTimeMillis()值填充，并发修改时检查
     *
     * @return flag_version - 【系统提供】取System.currentTimeMillis()值填充，并发修改时检查
     */
    public Long getFlagVersion() {
        return flagVersion;
    }

    /**
     * 设置【系统提供】取System.currentTimeMillis()值填充，并发修改时检查
     *
     * @param flagVersion 【系统提供】取System.currentTimeMillis()值填充，并发修改时检查
     */
    public void setFlagVersion(Long flagVersion) {
        this.flagVersion = flagVersion;
    }

    /**
     * 获取【系统提供】由框架自动填充当前登录人pk_id
     *
     * @return make_user - 【系统提供】由框架自动填充当前登录人pk_id
     */
    public String getMakeUser() {
        return makeUser;
    }

    /**
     * 设置【系统提供】由框架自动填充当前登录人pk_id
     *
     * @param makeUser 【系统提供】由框架自动填充当前登录人pk_id
     */
    public void setMakeUser(String makeUser) {
        this.makeUser = makeUser;
    }

    /**
     * 获取【系统提供】由框架自动填充当前系统时间
     *
     * @return make_time - 【系统提供】由框架自动填充当前系统时间
     */
    public Date getMakeTime() {
        return makeTime;
    }

    /**
     * 设置【系统提供】由框架自动填充当前系统时间
     *
     * @param makeTime 【系统提供】由框架自动填充当前系统时间
     */
    public void setMakeTime(Date makeTime) {
        this.makeTime = makeTime;
    }

    /**
     * 获取【系统提供】由框架自动填充当前系统时间
     *
     * @return modify_time - 【系统提供】由框架自动填充当前系统时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置【系统提供】由框架自动填充当前系统时间
     *
     * @param modifyTime 【系统提供】由框架自动填充当前系统时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}