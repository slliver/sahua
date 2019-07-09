package com.slliver.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Random;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/5 14:02
 * @version: 1.0
 */
public class CipherUtil {

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * cipher password 加密成MD5
     *
     * @param inputString
     * @return
     */
    public static String generatePassword(String inputString) {
        return encodeByMD5(inputString);
    }

    /**
     * validate password
     *
     * @param password
     * @param inputString
     * @return
     */
    public static boolean validatePassword(String password, String inputString) {
        if (password.equals(encodeByMD5(inputString))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * encode
     *
     * @param originString
     * @return
     */
    public static String encodeByMD5(String originString) {
        if (originString != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] results = md.digest(originString.getBytes());
                String resultString = byteArrayToHexString(results);
                return resultString.toUpperCase();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static byte[] encodeByMD5(byte[] originBytes) {
        if (originBytes != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] results = md.digest(originBytes);
                String resultString = byteArrayToHexString(results);
                return resultString.toUpperCase().getBytes();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * change the Byte[] to hex string
     *
     * @param b
     * @return
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * change a byte to hex string
     *
     * @param b
     * @return
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * base64进制加密
     *
     * @param str
     * @return
     */
    public static String encrytBase64(String str) {
        return Base64.encodeToString(str.getBytes());
    }

    /**
     * base64进制解密
     *
     * @param str
     * @return
     */
    public static String decryptBase64(String str) {
        return Base64.decodeToString(str);
    }

    /**
     * 16进制加密
     *
     * @param str
     * @return
     */
    public static String encrytHex(String str) {
        return Hex.encodeToString(str.getBytes());
    }

    /**
     * 16进制解密
     *
     * @param str
     * @return
     */
    public static String decryptHex(String str) {
        return new String(Hex.decode(str));
    }

    public static String generateKey() {
        AesCipherService aesCipherService = new AesCipherService();
        return Base64.encodeToString(aesCipherService.generateNewKey().getEncoded());
    }

    /**
     * 生成盐
     *
     * @return
     */
    public static String createSalt() {
        return (new SecureRandomNumberGenerator()).nextBytes().toHex();
    }

    /**
     * 组合username,两次迭代，对密码进行加密
     *
     * @param username 用户名
     * @param password 密码
     * @param salt     盐
     * @return
     */
    public static String createPwdEncrypt(String username, String password, String salt) {
        return new Md5Hash(password, username + salt, 2).toBase64();
    }

    /**
     * 生成随机数字和字母
     *
     * @param length 生成长度
     * @return
     */
    public static String createRandomString(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    public static String encodeBase64(String x_str, String x_encoding) {
        try {
            return encodeBase64(x_str, x_encoding, x_encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("UnsupportedEncoding: " + x_encoding);
        }
    }

    private static int B(byte[] x_bytes, int x_pos) {
        if (x_pos >= x_bytes.length)
            return 0;
        return x_bytes[x_pos];
    }

    public static String encodeBase64(String x_str, String x_encoding, String x_encoding2)
            throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(x_str)) {
            return x_str;
        }
        if (StringUtils.isEmpty(x_encoding)) {
            x_encoding = "iso-8859-1";
        }
        byte[] p_bytes = x_str.getBytes(x_encoding);
        StringBuffer p_buff = new StringBuffer();
        p_buff.append("=?");
        if (StringUtils.isEmpty(x_encoding2)) {
            p_buff.append(x_encoding.toLowerCase());
        } else {
            p_buff.append(x_encoding2.toLowerCase());
        }
        p_buff.append("?B?");
        for (int i = 0; i < p_bytes.length; i += 3) {
            int b1 = B(p_bytes, i);
            int b2 = B(p_bytes, i + 1);
            int b3 = B(p_bytes, i + 2);
            p_buff.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt((b1 & 0xFF) >>> 2));
            p_buff.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
                    .charAt((b1 & 0x3) << 4 | (b2 & 0xFF) >>> 4));
            if ((b2 == 0) && (b3 == 0)) {
                p_buff.append("==");
            } else {
                p_buff.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
                        .charAt((b2 & 0xF) << 2 | (b3 & 0xFF) >>> 6));
                if (b3 == 0) {
                    p_buff.append("=");
                } else
                    p_buff.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(b3 & 0x3F));
            }
        }
        p_buff.append("?=");
        return p_buff.toString();
    }

    public static String encodeBase64b(byte[] x_bytes) {
        return Base64.decode(x_bytes).toString();
    }
}
