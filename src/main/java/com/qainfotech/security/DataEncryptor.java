package com.qainfotech.security;

/**
 * @author : Nimit Jain
 * DataEncryptor class is used to encryt and decryt data using AES 128 algo.
 */

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DataEncryptor {

	private static final String ALGO = "AES";
	private static final byte[] keyValue = new byte[] { 'Q', 'a', 'i', 'f', 't', 'e', 'c', 'H', 'W', 'a', 'l', 'm', 'a',
			'R', 't', 'N' };
	

	/**
	 * encrypt method is used for Encryption of the data variable passed.
	 * @param data 
	 * @return encryptedValue
	 * @throws Exception
	 */
	public static String encrypt(String data) throws Exception {
		String encryptedValue = null;
		try {
			Key key = generateKey();
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.ENCRYPT_MODE, key);
			byte[] encVal = c.doFinal(data.getBytes());
			encryptedValue = new BASE64Encoder().encode(encVal);
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return encryptedValue;
	}

	/**
	 * decrypt method is for decryption of the encryptedData
	 * @param encryptedData
	 * @return decryptedValue
	 * @throws Exception
	 */
	public static String decrypt(String encryptedData) throws Exception {
		String decryptedValue = null;
		try {
			Key key = generateKey();
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.DECRYPT_MODE, key);
			byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
			byte[] decValue = c.doFinal(decordedValue);
			decryptedValue = new String(decValue);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return decryptedValue;

	}

	/**
	 * generateKey is the method which is helping in generating the key.
	 * @return key
	 * @throws Exception
	 */
	private static Key generateKey() throws Exception {
		Key key = null;
		try
		{
			key = new SecretKeySpec(keyValue, ALGO);			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return key;
	}

}