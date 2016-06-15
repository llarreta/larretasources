package ar.com.larreta.commons.utils.impl;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.stereotype.Component;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.utils.Base64;

@Component(Base64Impl.BASE64)
public class Base64Impl extends AppObjectImpl implements Base64 {
	
	public static final String BASE64 = "base64";

	public static final String ENCRYPTION_PASSWORD = "aj8NxAK0wT894RwExkEpk0EikOlgztoFuIlaRrUBKkM=";
	
	public Base64Impl(){
	}
	
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
				getLog().error("El texto encryptado generado " + encrypted + ", no puede desencriptarse.");
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
	
	/**
	 * Retorna un nuevo token
	 * @return
	 */
	public String getToken(){
		return encode(getEncryptor().encrypt(AppManager.getInstance().getAppConfig().getLockApp().nextIdentifier().toString()));
	}
	
}
