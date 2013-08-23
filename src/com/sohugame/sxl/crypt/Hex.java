package com.sohugame.sxl.crypt;

public class Hex {
	
	public static String parseByte2HexStr(byte[] buf) {
		StringBuilder sbuf = new StringBuilder();
		for (int i=0;i<buf.length;i++) {
			String hex = Integer.toHexString(buf[i]&0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sbuf.append(hex);
		}
		return sbuf.toString();
	}
	
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) return null;
		byte[] result = new byte[hexStr.length()/2];
		for (int i=0;i<hexStr.length()/2;i++) {
			int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
			int low = Integer.parseInt(hexStr.substring(i*2+1,i*2+2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
}