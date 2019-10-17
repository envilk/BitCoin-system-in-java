package no.hvl.dat159.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;


public class HashUtil {

	public static String pubKeyToAddress(PublicKey pk) {
		return EncodingUtil.bytesToBase64(sha256(pk.getEncoded()));
	}

	public static byte[] sha256(String s) {
		try {
			return sha256(s.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] sha256(byte[] input) {
		try {
			return MessageDigest.getInstance("SHA-256").digest(input);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
