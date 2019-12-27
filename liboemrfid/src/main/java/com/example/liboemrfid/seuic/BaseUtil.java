package com.example.liboemrfid.seuic;

public class BaseUtil {

	/**
	 * Copy the array at the specified location
	 */
	public static void memcpy(byte[] bytesTo, byte[] bytesFrom, int len) {
		memcpy(bytesTo, 0, bytesFrom, 0, len);
	}

	/**
	 * 
	 */
	public static void memcpy(byte[] bytesTo, int startIndexTo, byte[] bytesFrom, int startIndexFrom, int len) {
		while (len-- > 0) {
			bytesTo[startIndexTo++] = bytesFrom[startIndexFrom++];
		}
	}

	/**
	 * Copy the array from the start
	 */
	public static boolean memcmp(byte[] bytes1, byte[] bytes2, int len) {

		if (len < 1) {
			return false;
		}

		int startIndex = 0;
		while (len-- > 0) {
			if (bytes1[startIndex] != bytes2[startIndex]) {
				return false;
			}
			startIndex++;
		}
		return true;
	}

	/**
	 * byte array to hexadecimal string
	 */
	public static String getHexString(byte[] b, int length) {
		return getHexString(b, length, "");
	}

	/**
	 * Convert the specified splitter
	 */
	public static String getHexString(byte[] b, int length, String split) {
		StringBuilder hex = new StringBuilder("");
		String temp = null;
		for (int i = 0; i < length; i++) {
			temp = Integer.toHexString(b[i] & 0xFF);
			if (temp.length() == 1) {
				temp = '0' + temp;
			}
			hex.append(temp + split);
		}
		return hex.toString().trim().toUpperCase();
	}

	/**
	 * String to hexadecimal array
	 */
	public static byte[] getHexByteArray(String hexString) {
		byte[] buffer = new byte[hexString.length() / 2];
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			buffer[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return buffer;
	}

	//1223 begin
	public static String string2HexStr(String str){
		str.replace(" ", "");
		return BaseUtil.str2HexStr(str);
	}
	//1223 end
	/**
	 * String to hexadecimal array (Specified length)
	 */
	public static int getHexByteArray(String hexString, byte[] buffer, int nSize) {

		hexString.replace(" ", "");
		if (nSize > hexString.length() / 2) {
			nSize = hexString.length() / 2;
			if (hexString.length() == 1) {
				nSize = 1;
				String str = "0";
				hexString = str + hexString;
			}
		}
		char[] hexChars = hexString.toCharArray();
		for (int i = 0; i < nSize; i++) {
			int pos = i * 2;
			buffer[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return nSize;
	}

	/**
	 * 
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * byte array to hexadecimal string
	 */
	public static String ByteArrayToString(byte[] bt_ary) {
		StringBuilder sb = new StringBuilder();
		if (bt_ary != null)
			for (byte b : bt_ary) {
				sb.append(String.format("%02X ", b));
			}
		return sb.toString();
	}









	/**
	 * 16进制字符串转化为字母ASCALL码
	 *
	 * @param hex 要转化的16进制数，用空格隔开
	 *            如：53 68 61 64 6f 77
	 * @return ASCALL码
	 */
	public static String convertHexToAsCall(String hex) {
		StringBuilder sb = new StringBuilder();
		String[] split = hex.split(" ");
		for (String str : split) {
			int i = Integer.parseInt(str, 16);
			if (i < 0x20 || i == 0x7F) {//过滤特殊字符
				continue;
			}
			sb.append((char) i);
		}
		return sb.toString();
	}

	/**
	 * 16进制数组转String
	 *
	 * @param data byte数组
	 * @return string
	 */
	public static String formatHex2String(byte[] data) {
		final StringBuilder stringBuilder = new StringBuilder(data.length);
		for (byte byteChar : data)
			stringBuilder.append(String.format("%02X ", byteChar));
		return stringBuilder.toString();
	}

	/**
	 * 16进制字符串转换为Byte值
	 *
	 * @param src Byte字符串，每个Byte之间没有分隔符，eg:616C6B
	 * @return byte[]
	 */
	public static byte[] hexStr2Bytes(String src) {
		int m = 0, n = 0;
		int l = src.length() / 2;
		System.out.println(l);
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			m = i * 2 + 1;
			n = m + 1;
			ret[i] = Byte.decode("0x" + src.substring(i * 2, m) + src.substring(m, n));
		}
		return ret;
	}

	/**
	 * 字符串转换成十六进制字符串
	 *
	 * @param str 待转换的ASCII字符串
	 * @return String 每个Byte之间没有分隔，如: [616C6B]
	 */
	public static String str2HexStr(String str) {

		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;

		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
//            sb.append(' ');
		}
		return sb.toString().trim();
	}

	//byte 数组与 int 的相互转换
	public static int byteArrayToInt(byte[] b) {
		return   b[3] & 0xFF |
				(b[2] & 0xFF) << 8 |
				(b[1] & 0xFF) << 16 |
				(b[0] & 0xFF) << 24;
	}

	public static byte[] intToByteArray(int a) {
		return new byte[] {
				(byte) ((a >> 24) & 0xFF),
				(byte) ((a >> 16) & 0xFF),
				(byte) ((a >> 8) & 0xFF),
				(byte) (a & 0xFF)
		};
	}
}
