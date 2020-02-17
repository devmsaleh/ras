package com.charity.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

public class Encryptor {

	final static String key = "C+YaVwM3UlVvjhOlQzis2w==";
	// final static String key = "pXyx1NqcJDacb+GVrn4BgqWmUMpiGzb0n1oimr90vss=";

	public static String encrypt(String value) {
		byte[] raw;
		String encryptedString;
		SecretKeySpec skeySpec;
		byte[] encryptText = value.getBytes();
		Cipher cipher;
		try {
			raw = Base64.decodeBase64(key);
			skeySpec = new SecretKeySpec(raw, "AES");
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			encryptedString = Base64.encodeBase64String(cipher.doFinal(encryptText));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return encryptedString;
	}

	private byte[] encrypt(byte[] inpBytes, SecretKey key, String xform) throws Exception {
		Cipher cipher = Cipher.getInstance(xform);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(inpBytes);
	}

	private static byte[] decrypt(byte[] inpBytes, SecretKey key, String xform) throws Exception {
		Cipher cipher = Cipher.getInstance(xform);
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(inpBytes);
	}

	public static String decrypt(String input) {
		if (StringUtils.isBlank(input))
			return input;
		input = input.trim().replaceAll("\\s", "+");
		String xform = "AES";
		String result = null;
		SecretKeySpec skeySpec = new SecretKeySpec(Base64.decodeBase64(key.getBytes()), "AES");
		try {
			byte[] decBytes = decrypt(Base64.decodeBase64(input.getBytes("UTF-8")), skeySpec, xform);
			result = new String(decBytes, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;

	}

	public byte[] encrypt(byte[] inpBytes) throws Exception {
		String xform = "AES";
		SecretKeySpec skeySpec = new SecretKeySpec(Base64.decodeBase64(key.getBytes()), "AES");
		Cipher cipher = Cipher.getInstance(xform);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		return cipher.doFinal(inpBytes);
	}

	public byte[] decrypt(byte[] inpBytes) throws Exception {
		String xform = "AES";
		SecretKeySpec skeySpec = new SecretKeySpec(Base64.decodeBase64(key.getBytes()), "AES");
		Cipher cipher = Cipher.getInstance(xform);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		return cipher.doFinal(inpBytes);
	}

	public static String generateSecretKey() throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		return Base64.encodeBase64String(keyGen.generateKey().getEncoded());

	}

	public String encryptParam(String input) {
		try {
			return URLEncoder.encode(encrypt(input.trim()), "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}

	public String decryptParam(String input) {
		String decoded = "";
		try {
			// sometimes browser sends the + sign as + instead of %2B which makes issues
			decoded = URLDecoder.decode(input.toString().trim().replace("+", "%2B"), "UTF-8");
			String decrypted = decrypt(decoded);
			if (StringUtils.isBlank(decrypted)) {
				throw new Exception("UNABLE TO decryptParam: " + input);
			}
			return decrypted;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String generateRandomNumber(int n) {
		long min = (long) Math.pow(10, n - 1);
		return String.valueOf(ThreadLocalRandom.current().nextLong(min, min * 10));
	}

	public static void main(String[] args) throws Exception {
		System.out.println(encrypt("123"));
	}
}