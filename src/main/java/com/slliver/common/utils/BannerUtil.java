package com.slliver.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/16 16:01
 * @version: 1.0
 */
public class BannerUtil {

    public static Map<String, String> bussnessMap = new LinkedHashMap<>();

    static {
        bussnessMap.put("1", "极速贷");
        bussnessMap.put("2", "信用卡");
        bussnessMap.put("3", "帮帮");
        bussnessMap.put("4", "贷款大全");
    }

    public static Map<String, String> getBussnessMap() {
        return bussnessMap;
    }

    public static String getBussinessValue(String key) {
        return bussnessMap.get(key);
    }

    public static Map<String, String> forwardMap = new LinkedHashMap<>();

    static {
        forwardMap.put("JSON", "JSON");
        forwardMap.put("WEB", "WEB");
    }

    public static Map<String, String> getForwardMap() {
        return forwardMap;
    }

    public static String getForwardValue(String key) {
        return forwardMap.get(key);
    }
}
