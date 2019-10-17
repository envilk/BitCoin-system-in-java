package no.hvl.dat159.util;

import java.util.Base64;

public class EncodingUtil {

	public static String bytesToBinary(byte[] bytes) {
		String binaryString = "";
		for (byte b : bytes) {
			binaryString += String
					.format("%8s", Integer.toBinaryString(b & 0xFF))
					.replace(' ', '0');
		}
		return binaryString;
	}

	public static String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
	    for (byte b : bytes) {
	        sb.append(String.format("%02x", b));
	    }
	    return sb.toString();
	}

	public static String bytesToBase64(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

}
