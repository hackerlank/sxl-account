package com.sohugame.sxl.crypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	private static String ALGORITHM = "AES";
	private static byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
	
	public static byte[] generateKey(String pswd) {
		byte[] bytes = null; 
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
			keyGen.init(128, new SecureRandom(pswd.getBytes()));
			SecretKey secretKey = keyGen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGORITHM);
			bytes = key.getEncoded();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
	/**
	 * 加密
	 * @param content - 需要加密的内容
	 * @param password - 加密密码
	 * @return
	 */
	public static byte[] encrypt(String content, byte[] keyEncode) {
		try {
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(keyEncode, ALGORITHM);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);// 初始化
			return cipher.doFinal(byteContent);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] content, byte[] enCodeFormat) {
		try {
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGORITHM);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);// 初始化
			return cipher.doFinal(content);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String s = "{\"serverList\":{\"2\":{\"socketPort\":30006,\"name\":\"内网59\",\"state\":2,\"httpPort\":8001,\"ip\":\"192.168.1.59\"},\"1\":{\"socketPort\":30006,\"name\":\"内网250\",\"state\":2,\"httpPort\":8001,\"ip\":\"192.168.1.250\"}},\"skey\":\"100488\",\"recommend\":1}";
		byte[] encode = generateKey("eitgame-wxj");
		for (int a=0;a<encode.length;a++) {
			System.out.print(encode[a]+ ",");
		}
		//byte[] s1 = encrypt(s, encode);
		//System.out.println(Hex.parseByte2HexStr(s1));
		//System.out.println(new String(decrypt(s1, encode), "UTF-8"));
		
//		long begin = System.currentTimeMillis();
//		long seed = 1353557690200L;
//		Rand r = new Rand(seed);
		//for (int i=0;i<10;i++) {
//			r.nextBytes(AES.iv);
//			byte[] tmp = encrypt(s, encode);
			//System.out.println(Hex.parseByte2HexStr(tmp));
//			System.out.println(s.length());
//			System.out.println(Base64.encode(tmp).length());
			//System.out.println(new String(decrypt(tmp, encode)));
		//}
//		System.out.println(System.currentTimeMillis()-begin);
	}
}
