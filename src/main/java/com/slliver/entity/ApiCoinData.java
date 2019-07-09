package com.slliver.entity;

import com.slliver.base.entity.LoanData;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @Description: 极速贷
 * @author: slliver
 * @date: 2018/3/12 16:32
 * @version: 1.0
 */

public class ApiCoinData {

    private String title;       //标题
    private String content;     //描述
    private String buttonTitle; //按钮文字
    private String url;     //跳转url
    private String iid;     //跳转动作id   0  大转盘，1. 阅读新闻。2. 看视频。 3.玩游戏。  4. 做任务。5.邀请
    private String imgStr;  //图片

    public ApiCoinData(String title, String content, String buttonTitle, String url, String iid, String imgStr) {
        this.title = title;
        this.content = content;
        this.buttonTitle = buttonTitle;
        this.url = url;
        this.iid = iid;
        this.imgStr = imgStr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getButtonTitle() {
        return buttonTitle;
    }

    public void setButtonTitle(String buttonTitle) {
        this.buttonTitle = buttonTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }
}
