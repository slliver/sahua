package com.slliver.common.tag;

import com.slliver.common.constant.DeviceContant;
import com.slliver.common.utils.BannerUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/16 16:12
 * @version: 1.0
 */
public class WebFunction {

    public static String getBussinessValue(String input) {
        return BannerUtil.getBussinessValue(input);
    }

    public static String getForwardValue(String input) {
        return BannerUtil.getForwardValue(input);
    }


    /**
     * 如果字符串为空则显示默认的值
     * @param input
     * @param defaultValue 默认值
     * @return
     */
    public static String defaultValue(String input, String defaultValue) {
        if (StringUtils.isBlank(input)) {
            return defaultValue;
        }

        return input;
    }

    public static String substring(String value, int length) {
        if (StringUtils.isBlank(value)) {
            return "";
        }
        if (value.length() < length) {
            return value;
        }
        return value.substring(0, length) + "...";
    }

    /**
     * 适用设备类型
     * @param key
     * @return
     */
    public static String getDeviceValue(Short key) {
        if (key == null) {
            return "";
        }
        return DeviceContant.getDeviceName(key);
    }
}
