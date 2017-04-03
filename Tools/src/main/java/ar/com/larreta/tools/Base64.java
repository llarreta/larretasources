package ar.com.larreta.tools;

import org.apache.log4j.Logger;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.BasicTextEncryptor;

public class Base64 {

	private static final Logger LOGGER = Logger.getLogger(Base64.class);
	
	//FIXME: Seria interesante externalizar este valor
	public static final String ENCRYPTION_PASSWORD = "aj8NxAK0wT894RwExkEpk0EikOlgztoFuIlaRrUBKkM=";
	
	public Base64(){}
	
	private BasicTextEncryptor getEncryptor(){
		BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
		basicTextEncryptor.setPassword(ENCRYPTION_PASSWORD);
		return basicTextEncryptor;
	} 
	
	/**
	 * Codifica el texto en base 64
	 * @param text
	 * @return
	 */
	public String encode (String text){
		byte[]   bytesEncoded = org.apache.commons.codec.binary.Base64.encodeBase64(text .getBytes());
		return new String(bytesEncoded);
	}

	/**
	 * Decodifica el texto en base 64
	 * @param text
	 * @return
	 */
	public String decode (String text){
		byte[]   bytesEncoded = org.apache.commons.codec.binary.Base64.decodeBase64(text .getBytes());
		return new String(bytesEncoded);
	}
	
	/**
	 * Encripta y codifica el texto en base 64
	 * Se asegura que se pueda desencriptar con reintentos
	 * @param text
	 * @return
	 */
	private String encrypt (String text, Integer countdown){
		String encrypted = encode(getEncryptor().encrypt(text));
		//Validamos que pueda desencriptarse
		try{
			decrypt(encrypted);
		} catch (EncryptionOperationNotPossibleException e){
			// Si por algun motivo no puede desencriptarse entonces obtenemos otra encriptacion
			if (countdown>0){
				encrypted = encrypt(text, --countdown);
			} else {
				LOGGER.error("El texto encryptado generado " + encrypted + ", no puede desencriptarse.");
			}
		}
		return encrypted; 
	}
	
	/**
	 * Reintentos de encryptacion que pueda desencriptarse
	 * @param text
	 * @param index
	 * @return
	 */
	public String encrypt(String text){
		return encrypt(text, 3);
	}

	/**
	 * Decodifica y desencrypta el texto
	 * @param text
	 * @return
	 */
	public String decrypt (String text){
		return getEncryptor().decrypt(decode(text));
	}
	
}
