package com.charity.util;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

public class SpringEncryptor {

	final static String salt = KeyGenerators.string().generateKey();
	final static String password = "I AM SHERLOCKED";

	public static String encrypt(String text) {
		System.out.println("password: " + password);
		System.out.println("salt: " + salt);
		TextEncryptor encryptor = Encryptors.text(password, salt);
		return encryptor.encrypt(text);
	}

	public static String decrypt(String text) {
		TextEncryptor decryptor = Encryptors.text(password, salt);
		return decryptor.decrypt(text);
	}

	public static void main(String[] args) {
		System.out.println(encrypt("12345"));
	}

}
