package com.qainfotech.security;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DataEncryptorTest {

	@Test
	public void test1_Encrypt() throws Exception
	{
		String password = "password";
		String encrpt = DataEncryptor.encrypt(password);
		Assert.assertEquals("BF8qTu+IK1nHsr28lzvjpQ==", encrpt);
	}
}
