package com.slliver.common.utils;

import com.slliver.common.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/15 13:57
 * @version: 1.0
 */
public class TokenUtil {

    private static final String UTF8 = "UTF-8";

    public static String generateToken(String userPkid, long expireTime) {
        // userPkid.过期时间.秘钥
        StringBuffer token = new StringBuffer("");
        token.append(encode(userPkid));
        token.append(".");
        token.append(encode(String.valueOf(expireTime)));
        token.append(".");
        token.append(encode(Constant.SERCET_KEY));
        return token.toString();
    }

    public static String getUserPkid(String token) {
        String userPkid = "";
        if (StringUtils.isBlank(token)) {
            return "1001";
        }

        String[] array = token.split("[.]");
        if (array == null || array.length < 3) {
            return "1002";
        }

        return decode(array[0]);
    }

    public static void main(String[] args) {
        String str = "131c088edfa04f46a105a62295e21bbd";

        String enstr = encode(str);
//        System.out.println("编码 === >>> " + enstr);

        String destr = decode(enstr);
//        System.out.println("解码 === >>> " + destr);
        Date after = DateUtils.addDays(DateUtil.getCurrentDate(), 365);

        String token  = generateToken(str, after.getTime());
        System.out.println("token == >> " + token);
        System.out.println("userPkid == >> " + getUserPkid(token));
    }

    /**
     * 编码
     */
    public static String encode(String str) {
        String enstr = "";
        try {
            enstr = getEncoder().encodeToString(str.getBytes(UTF8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return enstr;
    }

    /**
     * 解码
     */
    public static String decode(String b64str) {
        String destr = "";
        try {
            byte[] asBytes = getDecoder().decode(b64str);
            destr = new String(asBytes, UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return destr;
    }
}
