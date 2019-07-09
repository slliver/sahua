package com.slliver.common.constant;

import java.util.LinkedHashMap;

/**
 * @Description: 适用设备
 * @author: slliver
 * @date: 2018/11/19 14:00
 * @version: 1.0
 */
public class DeviceContant {

    public static final LinkedHashMap<Short, String> deviceMap = new LinkedHashMap<>();

    static {

        deviceMap.put((short) 0, "全部");
        deviceMap.put((short) 1, "苹果");
        deviceMap.put((short) 2, "安卓");
        deviceMap.put((short) 3, "下线");
    }

    public static LinkedHashMap<Short, String> getDeviceMap() {
        return deviceMap;
    }

    public static String getDeviceName(Short key) {
        return deviceMap.get(key);
    }
}
