package cn.com.secbuy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName: SaltedMD5
 * @Description: TODO(MD5加密)
 * @author fan
 * @date 2015年3月2日 上午12:39:48
 * @version V1.0
 */
public class MD5Secure {

	/**
	 * MD5加密字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String secure(String tatgetStr) {
		String generatedStr = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(tatgetStr.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedStr = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedStr;
	}
}
