package com.slliver.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {

	public static final String PRIVATE_KEY = "ASDFG!@#4567*()hjkl";

	public static String toMD5(String text) {
		//获取摘要器 MessageDigest
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			//通过摘要器对字符串的二进制字节数组进行hash计算
			byte[] digest = messageDigest.digest(text.getBytes());

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < digest.length; i++) {
				//循环每个字符 将计算结果转化为正整数;
				int digestInt = digest[i] & 0xff;
				//将10进制转化为较短的16进制
				String hexString = Integer.toHexString(digestInt);
				//转化结果如果是个位数会省略0,因此判断并补0
				if (hexString.length() < 2) {
					sb.append(0);
				}
				//将循环结果添加到缓冲区
				sb.append(hexString);
			}
			//返回整个结果
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
}
