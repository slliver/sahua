package com.slliver.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 错误码对照表
 * @author: slliver
 * @date: 2018/3/29 10:53
 * @version: 1.0
 */
public class ValidationConstant {
    // 校验成功
    public static final String SUCCESS = "9999";
    // 校验失败
    public static final String FAIL = "8888";
    // 注册失败
    public static final String REGISTER_FAIL = "8887";
    // 渠道号不存在
    public static final String INVALID_CHANNEL = "8886";

    // 手机号码为空
    public static final String PHONE_NULL = "1001";
    // 验证码为空
    public static final String CODE_NULL = "1002";

    // 手机号码已经注册
    public static final String PHOME_HAS_REGISTER = "2001";
    // 手机号码没有注册
    public static final String PHOME_NOT_REGISTER = "2002";

    // 验证码错误
    public static final String CODE_ERROR = "3001";
    // 验证码过期
    public static final String CODE_EXPIRE = "3002";
    // 您今日获取的验证码次数已经超过最大次数
    public static final String CODE_MAX_COUNT = "3003";
    // 验证码在有效期内
    public static final String CODE_NOT_EXPIRE = "3004";

    public static Map<String, String> statusCodeMap = Collections.synchronizedMap(new HashMap<>());

    static {
        statusCodeMap.put(SUCCESS, "校验成功");
        statusCodeMap.put(FAIL, "校验失败");
        statusCodeMap.put(REGISTER_FAIL, "校验失败");
        statusCodeMap.put(INVALID_CHANNEL, "渠道号不存在");
        statusCodeMap.put(PHONE_NULL, "手机号码不能为空");
        statusCodeMap.put(CODE_NULL, "验证码不能为空");
        statusCodeMap.put(PHOME_HAS_REGISTER, "手机号码已经注册");
        statusCodeMap.put(PHOME_NOT_REGISTER, "手机号码没有注册");
        statusCodeMap.put(CODE_ERROR, "验证码错误");
        statusCodeMap.put(CODE_EXPIRE, "验证码已经过期");
        statusCodeMap.put(CODE_MAX_COUNT, "您今日获取的验证码次数已经超过最大次数");
        statusCodeMap.put(CODE_NOT_EXPIRE, "验证码在有效期内");
    }

    public static String getStatusCodeMessage(String key){
        return statusCodeMap.get(key);
    }
}
