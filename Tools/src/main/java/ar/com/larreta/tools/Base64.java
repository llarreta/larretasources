package ar.com.larreta.tools;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Base64 {

	private static final Logger LOGGER = Logger.getLogger(Base64.class);
	
	@Value("${base64.encryption.password}")
	private String encriptionPassword;
	
	@Value("${base64.encryption.countDown}")
	private String countDown;
	private Integer countDownValue;
	
	public Base64(){}
	
	@PostConstruct
	public void initialize(){
		countDownValue = new Integer(countDown);
	}
	
	private BasicTextEncryptor getEncryptor(){
		BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
		basicTextEncryptor.setPassword(encriptionPassword);
		return basicTextEncryptor;
	} 
	
	/**
	 * Codifica el texto en base 64
	 * @param text
	 * @return
	 */
	public String encode (String text){
		byte[] toEncode = text.getBytes();
		return encode(toEncode);
	}

	public String encode(byte[] toEncode) {
		byte[]   bytesEncoded = org.apache.commons.codec.binary.Base64.encodeBase64(toEncode);
		return new String(bytesEncoded);
	}

	/**
	 * Decodifica el texto en base 64
	 * @param text
	 * @return
	 */
	public String decode (String text){
		byte[] toDecode = text.getBytes();
		return decode(toDecode);
	}

	public String decode(byte[] toDecode) {
		byte[]   bytesEncoded = org.apache.commons.codec.binary.Base64.decodeBase64(toDecode);
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
		return encrypt(text, countDownValue);
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
