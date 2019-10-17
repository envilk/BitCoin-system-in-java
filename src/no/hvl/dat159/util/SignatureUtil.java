package no.hvl.dat159.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SignatureUtil {
	
	public static KeyPair generateRandomDSAKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
			keyGen.initialize(512);
			return keyGen.generateKeyPair();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] signWithDSA(PrivateKey privateKey, String message) {
		try {
			Signature dsa = Signature.getInstance("DSA");
			dsa.initSign(privateKey);
			dsa.update(message.getBytes());
			return dsa.sign();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean verifyWithDSA(PublicKey publicKey, String message, byte[] signature) {
		try {
			Signature dsa = Signature.getInstance("DSA");
			dsa.initVerify(publicKey);
			dsa.update(message.getBytes());
			return dsa.verify(signature);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String base64EncodeKey(Key key) {
		return EncodingUtil.bytesToBase64(key.getEncoded());
	}

	public static PublicKey base64DecodePublicKey(String base64Key) {
		byte[] bytes = Base64.getDecoder().decode(base64Key);
		try {
			return KeyFactory.getInstance("DSA")
					.generatePublic(new X509EncodedKeySpec(bytes));
			
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static PrivateKey base64DecodePrivateKey(String base64Key) {
		byte[] bytes = Base64.getDecoder().decode(base64Key);
		try {
			return KeyFactory.getInstance("DSA")
					.generatePrivate(new PKCS8EncodedKeySpec(bytes));
			
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}
