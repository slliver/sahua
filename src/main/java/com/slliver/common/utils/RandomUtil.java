package com.slliver.common.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/15 10:51
 * @version: 1.0
 */
public class RandomUtil {

    public static String random(int num) {
        char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'N', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
        return RandomStringUtils.random(num, codeSequence);
    }

    public static String randomNum(int num) {
        char[] codeSequence = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        return RandomStringUtils.random(num, codeSequence);
    }

    public static String randomUUID() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.toUpperCase();
        uuid = uuid.replaceAll("-", "_");
        return uuid;
    }

    public static void main(String[] args) {
        System.out.println(random(4));
    }
}
