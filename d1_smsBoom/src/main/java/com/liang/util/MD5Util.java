package com.liang.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;

public class MD5Util {
	
	
	public static void main(String[] args) {
		
		System.out.println("uuid:"+java.util.UUID.randomUUID());
			
		// 原文
				String plaintext = "123";
			//  plaintext = "123456";
				System.out.println("原始：" + plaintext);
				System.out.println("普通MD5后：" + MD5Util.MD5(plaintext));
		 
				// 获取加盐后的MD5值
				String ciphertext = MD5Util.generate(plaintext);
				System.out.println("加盐后MD5：" + ciphertext);
				System.out.println(MD5Util.verify(plaintext,"a7ab9296ec3122bd9f019431c1a65670636f69963316250a"));
	}
	
	/**
	 * 普通MD5
	 * @author daniel
	 * @time 2016-6-11 下午8:00:28
	 * @param inStr
	 * @return
	 */
	public static String MD5(String input) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return "check jdk";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = input.toCharArray();
		byte[] byteArray = new byte[charArray.length];
 
		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
 
	 
	 
	 
	/**
	 * 加盐MD5
	 * @author daniel
	 * @time 2016-6-11 下午8:45:04
	 * @param password
	 * @return
	 */
		public static String generate(String password) {
			Random r = new Random();
	 		StringBuilder sb = new StringBuilder(16);
	 		sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
	 		int len = sb.length();
	 		if (len < 16) {
	 			for (int i = 0; i < 16 - len; i++) {
	 				sb.append("0");
	 			}
	 		}
	 		String salt = sb.toString();
	 		System.err.println("盐值："+salt);
	 		password = md5Hex(password + salt);
	 		char[] cs = new char[48];
	 		for (int i = 0; i < 48; i += 3) {
	 			cs[i] = password.charAt(i / 3 * 2);
	 			char c = salt.charAt(i / 3);
	 			cs[i + 1] = c;
	 			cs[i + 2] = password.charAt(i / 3 * 2 + 1);
	 		}
			return new String(cs);
		}
		/**
		 * 校验加盐后是否和原文一致
		 * @author daniel
		 * @time 2016-6-11 下午8:45:39
		 * @param password
		 * @param md5
		 * @return
		 */
		public static boolean verify(String password, String md5) {
	 		char[] cs1 = new char[32];
			char[] cs2 = new char[16];
			for (int i = 0; i < 48; i += 3) {
				cs1[i / 3 * 2] = md5.charAt(i);
				cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
				cs2[i / 3] = md5.charAt(i + 1);
			}
			String salt = new String(cs2);
			System.err.println("判断密码的盐值："+salt);
			return md5Hex(password + salt).equals(new String(cs1));
		}
		/**
		 * 获取十六进制字符串形式的MD5摘要
		 */
		private static String md5Hex(String src) {
			try {
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				byte[] bs = md5.digest(src.getBytes());
				return new String(new Hex().encode(bs));
			} catch (Exception e) {
				return null;
			}
		}
		public static String getRandomChar(int length) { // 生成随机字符串
			char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
					'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
			Random random = new Random();
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < length; i++) {
				buffer.append(chr[random.nextInt(36)]);
			}
			return buffer.toString();
		}
}
