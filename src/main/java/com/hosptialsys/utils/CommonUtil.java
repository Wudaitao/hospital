package com.hosptialsys.utils;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 
 * @fuction ：常用工具类的封装
 * @author  ：NONWORD
 * @version ：2019年8月21日 上午9:13:25
 */
public class CommonUtil {

	/**
	 * 生成UUID 用来标识一笔订单，也可以用时间戳来标识
	 */
	public static String generateUUID() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
		return uuid;
	}
	
	public static String MD5(String data) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] array = md5.digest(data.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (byte item : array) {
				sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString().toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public static String getFormatedSystemTime() {
		// 获取指定格式的系统时间
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		String ans = dateFormat.format(now);
		return ans;
	}
}
