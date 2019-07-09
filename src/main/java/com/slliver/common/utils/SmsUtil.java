package com.slliver.common.utils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/9 12:43
 * @version: 1.0
 */
public class SmsUtil {
    private final static Logger logger = LoggerFactory.getLogger(SmsUtil.class);

    // 验证码过期时间5分钟
    public static final int CODE_EXPIRE_MINUTE = 5;

    /**
     * 随机生成4位小写验证码
     */
    public static String generateRandomCode() {
//        String[] beforeShuffle = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String[] beforeShuffle = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }

        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(1, 7).toLowerCase();
        return result;
    }

    public static String generateRandomCode(int start,int end){
        String[] beforeShuffle = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(start, end);
        return result;
    }

    public static void main(String[] args) {
//        String phone = "18600620828,18041551380,18040127055";
//        String sendPhone = "13840986132,13555957682,18640922550,18040127055";
        String code = generateRandomCode();
        send("18040127055", code);
        // String code = generateRandomCode();
//        for(int x = 0; x < 500; x ++){
            //System.out.println(code);
//        }

    }

    // 发送地址
    public static final String SMS_UTF8_URL = "http://223.223.187.13:8080/eums/sms/utf8/send.do";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String COMMAND = "pubMtxRes";
    public static final String SIGN = "撒花";
    public static final String LOGIN = "jiedai";
    public static final String PASSWORD = "8rxds9q3";
    private final static String CHARSET_UTF8 = "UTF-8";


    /**
     * 错误代码
     */
    public final static Map<String, String> submitMap = new HashMap<>();

    static {
        submitMap.put("success", "发送成功");

        submitMap.put("101", "缺少name参数");
        submitMap.put("102", "缺少seed参数");
        submitMap.put("103", "缺少key参数");
        submitMap.put("104", "缺少dest参数");
        submitMap.put("105", "缺少content参数");
        submitMap.put("106", "seed错误");
        submitMap.put("107", "key错误");
        submitMap.put("108", "ext错误");
        submitMap.put("109", "内容超长");
        submitMap.put("110", "模板未备案");
        submitMap.put("111", "发送内容无签名");

        submitMap.put("201", "无对应账户");
        submitMap.put("202", "账户暂停");
        submitMap.put("203", "账户删除");
        submitMap.put("204", "账户IP没备案");
        submitMap.put("205", "账户无余额");
        submitMap.put("206", "密码错误");

        submitMap.put("301", "无对应产品");
        submitMap.put("302", "产品暂停");
        submitMap.put("303", "产品删除");
        submitMap.put("304", "产品不在服务时间");
        submitMap.put("305", "无匹配通道");
        submitMap.put("306", "通道暂停");
        submitMap.put("307", "通道已删除");
        submitMap.put("308", "通道不在服务时间");
        submitMap.put("309", "未提供短信服务");
        submitMap.put("310", "未提供彩信服务");

        submitMap.put("401", "屏蔽词");
        submitMap.put("500", "查询间隔太短");

        submitMap.put("999", "其他错误");

    }

    /**
     * 单条发送
     *
     * @param phone
     * @param code
     */
    public static String send(String phone, String code) {
        Map<String, String> params = getParams(phone, code);
        String url = getUrl(SMS_UTF8_URL, params);
        return get(url);
    }

    /**
     * 下行短信请求.
     */
    public static String get(String url) {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
        Response response = null;
        // 返回代码
        String code = "999";
        try {
            Request request = new Request.Builder()
                    .url(url)//请求接口。如果需要传参拼接到接口后面。
                    .build();//创建Request 对象
            response = client.newCall(request).execute();//得到Response 对象
            // 请求成功
            if (response.isSuccessful()) {
                // 发送成功
                if (response.code() == 200) {
                    byte[] bytes = response.body().bytes();
                    String result = new String(bytes, CHARSET_UTF8);
                    String[] array = result.split(":");
                    code = array[0];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("发送结果 == >> " + submitMap.get(code));
        return code;
    }




    private static String getSeed() {
        return DateFormatUtils.format(DateUtil.getCurrentDate(), "yyyyMMddHHmmss");
    }

    private static String getKey(String password, String seed) {
        String first = DigestUtils.md5Hex(password).toLowerCase();
        String key = DigestUtils.md5Hex(first + seed).toLowerCase();
        return key;
    }

    private static String getUrl(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(url);
        if (url.indexOf("?") < 0) {
            sb.append('?');
        }
        for (String name : params.keySet()) {
            sb.append('&');
            sb.append(name);
            sb.append('=');
            sb.append(String.valueOf(params.get(name)));
        }

        return sb.toString();
    }

    private static Map<String, String> getParams(String phone, String code) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("name", LOGIN);
        String seed = getSeed();
        params.put("seed", seed);
        params.put("key", getKey(PASSWORD, seed));
        params.put("dest", phone);
        params.put("content", "[" + SIGN + "] 您的验证码是" + code + "，有效时间" + CODE_EXPIRE_MINUTE + "分钟。");
        return params;
    }
}
