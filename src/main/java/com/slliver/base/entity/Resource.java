package com.slliver.base.entity;

import com.slliver.base.domain.BaseDomain;
import java.util.Date;
import javax.persistence.*;

public class Resource extends BaseDomain {
    /**
     * 比如：wv9szgrm_4hzbakwz.jpg
     */
    @Column(name = "name_system")
    private String nameSystem;

    /**
     * 数据字典定义：比如图片、文档、视频
     */
    private String type;

    /**
     * 比如：wallpaper_5221918.jpg
     */
    @Column(name = "name_original")
    private String nameOriginal;

    /**
     * 比如：avatar/20170301/，avatar是模块名称，20170301是日期，可以自己定规则
     */
    private String folder;

    /**
     * wv9szgrm_4hzbakwz这样是为了如果是图片的话，去合适的缩略图，比如wv9szgrm_4hzbakwz_100，100就是缩略图的尺寸
     */
    @Column(name = "simple_name")
    private String simpleName;

    /**
     * 比如：jpg,xls,不带文件后缀的点
     */
    private String extention;

    /**
     * avatar/20160422/wv9szgrm_4hzbakwz.jpg，前面可以加上系统配置的地址，比如/var/nfs/data+url
     */
    private String url;

    private Long size;

    private String remark;

    /**
     * 预留字段
     */
    private String reserved1;

    /**
     * 预留字段
     */
    private String reserved2;

    /**
     * 获取比如：wv9szgrm_4hzbakwz.jpg
     *
     * @return name_system - 比如：wv9szgrm_4hzbakwz.jpg
     */
    public String getNameSystem() {
        return nameSystem;
    }

    /**
     * 设置比如：wv9szgrm_4hzbakwz.jpg
     *
     * @param nameSystem 比如：wv9szgrm_4hzbakwz.jpg
     */
    public void setNameSystem(String nameSystem) {
        this.nameSystem = nameSystem;
    }

    /**
     * 获取数据字典定义：比如图片、文档、视频
     *
     * @return type - 数据字典定义：比如图片、文档、视频
     */
    public String getType() {
        return type;
    }

    /**
     * 设置数据字典定义：比如图片、文档、视频
     *
     * @param type 数据字典定义：比如图片、文档、视频
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取比如：wallpaper_5221918.jpg
     *
     * @return name_original - 比如：wallpaper_5221918.jpg
     */
    public String getNameOriginal() {
        return nameOriginal;
    }

    /**
     * 设置比如：wallpaper_5221918.jpg
     *
     * @param nameOriginal 比如：wallpaper_5221918.jpg
     */
    public void setNameOriginal(String nameOriginal) {
        this.nameOriginal = nameOriginal;
    }

    /**
     * 获取比如：avatar/20170301/，avatar是模块名称，20170301是日期，可以自己定规则
     *
     * @return folder - 比如：avatar/20170301/，avatar是模块名称，20170301是日期，可以自己定规则
     */
    public String getFolder() {
        return folder;
    }

    /**
     * 设置比如：avatar/20170301/，avatar是模块名称，20170301是日期，可以自己定规则
     *
     * @param folder 比如：avatar/20170301/，avatar是模块名称，20170301是日期，可以自己定规则
     */
    public void setFolder(String folder) {
        this.folder = folder;
    }

    /**
     * 获取wv9szgrm_4hzbakwz这样是为了如果是图片的话，去合适的缩略图，比如wv9szgrm_4hzbakwz_100，100就是缩略图的尺寸
     *
     * @return simple_name - wv9szgrm_4hzbakwz这样是为了如果是图片的话，去合适的缩略图，比如wv9szgrm_4hzbakwz_100，100就是缩略图的尺寸
     */
    public String getSimpleName() {
        return simpleName;
    }

    /**
     * 设置wv9szgrm_4hzbakwz这样是为了如果是图片的话，去合适的缩略图，比如wv9szgrm_4hzbakwz_100，100就是缩略图的尺寸
     *
     * @param simpleName wv9szgrm_4hzbakwz这样是为了如果是图片的话，去合适的缩略图，比如wv9szgrm_4hzbakwz_100，100就是缩略图的尺寸
     */
    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    /**
     * 获取比如：jpg,xls,不带文件后缀的点
     *
     * @return extention - 比如：jpg,xls,不带文件后缀的点
     */
    public String getExtention() {
        return extention;
    }

    /**
     * 设置比如：jpg,xls,不带文件后缀的点
     *
     * @param extention 比如：jpg,xls,不带文件后缀的点
     */
    public void setExtention(String extention) {
        this.extention = extention;
    }

    /**
     * 获取avatar/20160422/wv9szgrm_4hzbakwz.jpg，前面可以加上系统配置的地址，比如/var/nfs/data+url
     *
     * @return url - avatar/20160422/wv9szgrm_4hzbakwz.jpg，前面可以加上系统配置的地址，比如/var/nfs/data+url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置avatar/20160422/wv9szgrm_4hzbakwz.jpg，前面可以加上系统配置的地址，比如/var/nfs/data+url
     *
     * @param url avatar/20160422/wv9szgrm_4hzbakwz.jpg，前面可以加上系统配置的地址，比如/var/nfs/data+url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return size
     */
    public Long getSize() {
        return size;
    }

    /**
     * @param size
     */
    public void setSize(Long size) {
        this.size = size;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取预留字段
     *
     * @return reserved1 - 预留字段
     */
    public String getReserved1() {
        return reserved1;
    }

    /**
     * 设置预留字段
     *
     * @param reserved1 预留字段
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
}