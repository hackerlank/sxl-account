package com.sohugame.sxl.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	public static String digest(String plaintext) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		byte[] bytes = md5.digest(plaintext.getBytes());
		StringBuilder ret = new StringBuilder(bytes.length << 1);
		for (int i = 0; i < bytes.length; i++) {
			ret.append(Character.forDigit((bytes[i] >> 4) & 0xf, 16));
			ret.append(Character.forDigit(bytes[i] & 0xf, 16));
		}
		return ret.toString();
	}
}
