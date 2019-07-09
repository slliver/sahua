package com.slliver.base.domain;

import com.slliver.common.Constant;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/12 16:43
 * @version: 1.0
 */
public class BaseCondition extends BaseDomain {
    /**
     * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
     */
    private static final long serialVersionUID = -599422106987807665L;

    private Integer pageNum = 1;

    private Integer pageSize = Constant.WEB_PAGE_SIZE;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
