package ar.com.larreta.commons.utils;

import org.apache.log4j.Logger;

import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.utils.impl.Base64Impl;



public class TestCryptographer {
	
	private static final Logger LOGGER = Logger.getLogger(TestCryptographer.class);
	
	public static void main(String[] args){
		try {
			
			Base64 base64 = new Base64Impl();

			encryptTest(base64, "screens");
			encryptTest(base64, "trace");
			encryptTest(base64, "colegio");
			encryptTest(base64, "user4");
			encryptTest(base64, "user5");
			encryptTest(base64, "user6");
			encryptTest(base64, "user7");
			
			decryptTest(base64, "a29qcHl2dVdjYmNyYnBnTVJGWlloUT09");
			decryptTest(base64, "OTVid1Z3STY1a2dvZEs5NDlLMzBsdz09");
		
		} catch (Exception e) {
			LOGGER.error(AppException.getStackTrace(e));
		}

	}

	private static void encryptTest(Base64 base64, String original) {
		String encripted = base64.encrypt(original);
		LOGGER.info("Original:" + original + " => " + encripted);
	}
	
	private static void decryptTest(Base64 base64, String encrypted) {
		String original = base64.decrypt(encrypted);
		LOGGER.info("Encrypted:" + encrypted + " => " + original);
	}

}
