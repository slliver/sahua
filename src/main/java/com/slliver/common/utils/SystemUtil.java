package com.slliver.common.utils;

import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

/**
 * @Description: 关于操作系统的一些方法
 * @author: slliver
 * @date: 2018/12/5 10:27
 * @version: 1.0
 */
public final class SystemUtil {


    public static void main(String[] args) throws IOException {
        String content = "<span>this is span</span>";
        System.out.println(StringEscapeUtils.unescapeHtml4(content));
    }

}
