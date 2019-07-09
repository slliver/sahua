package com.slliver.common.paging;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/9 9:42
 * @version: 1.0
 */
public class PageWapper<T> extends PageInfo<T> {
    private static final long serialVersionUID = 715646219862236689L;

    public PageWapper(List<T> list) {
        super(list);
    }
    public PageWapper() {
        super();
    }
    public String getPagingHtml() {
        StringBuilder pagingHtml = new StringBuilder();
        pagingHtml.append("<ul class=\"pagination\">");
        if (this.isHasPreviousPage()) {
            pagingHtml.append("<li><a href=\"javascript:;\" onclick=\"_toPage(").append(1).append(")\">首页</a></li>");
            pagingHtml.append("<li><a href=\"javascript:;\" onclick=\"_toPage(").append(this.getPrePage()).append(")\">上一页</a></li>");
        } else {
            pagingHtml.append("<li class=\"disabled\"><a href=\"javascript:;\">首页</a></li>");
            pagingHtml.append("<li class=\"disabled\"><a href=\"javascript:;\">上一页</a></li>");
        }

        int pageNum = this.getPageNum();
        if (this.getNavigatepageNums() != null) {
            for (int navNum : this.getNavigatepageNums()) {
                if (navNum == pageNum) {
                    pagingHtml.append("<li class=\"active\"><a href=\"javascript:;\">").append(navNum).append("</a></li>");
                } else {
                    pagingHtml.append("<li><a href=\"javascript:;\" onclick=\"_toPage(").append(navNum).append(")\">").append(navNum).append("</a></li>");
                }
            }
        }
        if (this.isHasNextPage()) {
            pagingHtml.append("<li><a href=\"javascript:;\" onclick=\"_toPage(").append(this.getNextPage()).append(")\">下一页</a></li>");
            pagingHtml.append("<li><a href=\"javascript:;\" onclick=\"_toPage(").append(this.getPages()).append(")\">末页</a></li>");
        } else {
            pagingHtml.append("<li class=\"disabled\"><a href=\"javascript:;\">下一页</a></li>");
            pagingHtml.append("<li class=\"disabled\"><a href=\"javascript:;\">末页</a></li>");
        }

        pagingHtml.append("<li class=\"disabled disablednone\"><a style=\"margin-left: 2px ! important;letter-spacing:2px ! important;\" href=\"##\">每页" + this.getPageSize() + "条</a></li>");
        pagingHtml.append("<li class=\"disabled disablednone\"><a style=\"margin-left: 2px ! important;letter-spacing:2px ! important;\" href=\"##\">共" + this.getPages() + "页</a></li>");
        pagingHtml.append("<li class=\"disabled disablednone\"><a style=\"margin-left: 3px ! important;letter-spacing:2px ! important;\" href=\"##\">共" + this.getTotal() + "条</a>" + "</li>");
        pagingHtml.append("</ul>");
        return pagingHtml.toString();
    }

    @SuppressWarnings("rawtypes")
    public Map getDataTableObject(int draw) {
        return ImmutableMap.of("draw",draw, "data", this.getList(),
                "recordsTotal",this.getTotal(), "recordsFiltered",this.getTotal());
    }
    @SuppressWarnings("rawtypes")
    public Map getDataTableObject() {
        return ImmutableMap.of("data", this.getList());
    }
}
