package com.slliver.common.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/5 14:07
 * @version: 1.0
 */
public class JsonUtil {

    private static Gson gson = new Gson();

    public static Gson getGsonInstance() {
        if (gson == null) gson = new Gson();
        return gson;
    }

    /**
     * 对象转换成json字符串
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return getGsonInstance().toJson(obj);
    }

    /**
     * json字符串转成对象
     *
     * @param str
     * @param type
     * @return
     */
    public static <T> T fromJson(String str, Type type) {
        return getGsonInstance().fromJson(str, type);
    }

    /**
     * json字符串转成对象
     *
     * @param str
     * @param type
     * @return
     */
    public static <T> T fromJson(String str, Class<T> type) {
        return getGsonInstance().fromJson(str, type);
    }
}
