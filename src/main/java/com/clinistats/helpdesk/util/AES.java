package com.clinistats.helpdesk.util;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.clinistats.helpdesk.exception.ValidationException;

/**
 * @Author Bhanu Teja
 * 
 *
 * 
 */

@Component
public class AES {
	private static final String SECRET_KEY = "clinistats";
	private static final String SALT = "EncDecpass!@#$";

	public String encrypt(String strToEncrypt) {
		try {

			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			// IvParameterSpec ivspec = new IvParameterSpec(iv);
			GCMParameterSpec ivspec = new GCMParameterSpec(128, iv);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 128);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			return Base64.getUrlEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			throw new ValidationException("Error While Encrypting:");
			// System.out.println("Error while encrypting: " + e);
		}
	}

	public String decrypt(String strToDecrypt) {
		try {
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			// IvParameterSpec ivspec = new IvParameterSpec(iv);
			GCMParameterSpec ivspec = new GCMParameterSpec(128, iv);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 128);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
			return new String(cipher.doFinal(Base64.getUrlDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			// System.out.println("Error while decrypting: " + e.toString());
			throw new ValidationException("Error While Decrypting:");
		}
	}

}