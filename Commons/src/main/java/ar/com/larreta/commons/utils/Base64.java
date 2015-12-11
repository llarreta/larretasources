package ar.com.larreta.commons.utils;

import ar.com.larreta.commons.AppObject;

public interface Base64 extends AppObject {
	
	/**
	 * Codifica el texto en base 64
	 * @param text
	 * @return
	 */
	public String encode (String text);

	/**
	 * Decodifica el texto en base 64
	 * @param text
	 * @return
	 */
	public String decode (String text);
	
	/**
	 * Reintentos de encryptacion que pueda desencriptarse
	 * @param text
	 * @param index
	 * @return
	 */
	public String encrypt(String text);

	/**
	 * Decodifica y desencrypta el texto
	 * @param text
	 * @return
	 */
	public String decrypt (String text);
	
	/**
	 * Retorna un nuevo token
	 * @return
	 */
	public String getToken();
	
}
