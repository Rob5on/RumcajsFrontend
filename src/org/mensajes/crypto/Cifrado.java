package org.mensajes.crypto;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
//BE------------------------------------------
public class Cifrado {

	// Set a default password
	private static String KEY = "g�'90��p�p�8486-,-.,<2";
	
	private static SecretKeySpec secretKey;
	private static byte[] key;
	
	// If the user wants to put another password, it is changed
	public Cifrado(String key) {
		KEY = key;
	}
	
	// Converts the key type String to the type SecretKeySpec
	private static void setKey() {
		
		MessageDigest sha = null;
		
		try {
			key = KEY.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Encrypts the message with the key.
	public static String cifrarMSG(String msg) {
		String ret;
		
		try {
			setKey();
			Cipher cifrado = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cifrado.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cifrado.doFinal(msg.getBytes()));
		} catch (Exception e) {
			System.err.println("Encryption failed");
		}
		
		return null;
	}
	
	// Decrypt the message with the password
	public static String descifrarMSG(String msg) {
		
		try {
			setKey();
			Cipher cifrado = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cifrado.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cifrado.doFinal(Base64.getDecoder().decode(msg)));
		} catch (Exception e) {
			System.err.println("Decryption failed");
			JOptionPane.showMessageDialog(null, "Decryption key is not correct "," Error decrypting", JOptionPane.ERROR_MESSAGE);
		}
		
		return null;
	}

}
