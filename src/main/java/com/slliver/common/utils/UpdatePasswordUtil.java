package com.slliver.common.utils;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/11/19 11:18
 * @version: 1.0
 */
public class UpdatePasswordUtil {

    public static void main(String[] args) {
        String userName = "robin";
        String pwrsMD5 = CipherUtil.generatePassword("123456");
        String salt = CipherUtil.createSalt();
        String password = CipherUtil.createPwdEncrypt(userName, pwrsMD5, salt);
        System.out.println("salt ==> " + salt);
        System.out.println("password ==> " + password);
    }

}
