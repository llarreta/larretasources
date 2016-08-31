/**
 * 
 */
package co.com.directv.sdii.common.util;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;

import org.apache.log4j.Logger;

/**
 * Esta clase contiene los métodos necesarios para encriptar y
 * desencriptar la clave para el uso del servidor de correo y 
 * servicion SMS.
 * 
 * @author Jimmy Vélez Muñoz
 */

public final class PassSecurity {

	private static Logger log;

	static {
		log = Logger.getLogger(PassSecurity.class);
	}
	
	/**
	 * Constructor for Crypt.
	 */
	private PassSecurity() {
		super();
	}

	/**
	 * Este m�todo se encarga de desencriptar una clave encriptada
	 * a partir de una llave generada previamente.
	 * 
	 * @author jvelezmu
	 * @param keyFile
	 *            Archivo que contiene la llave
	 * @param pass
	 *            Clave encriptada
	 * @return result
	 */
	public static String desencrypt(String keyFile, String pass) {
		log.debug("== Inicia desencrypt ==");
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key;
		String result = null;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					keyFile));
			key = (Key) in.readObject();
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			byte[] raw = decoder.decodeBuffer(pass);
			byte[] stringBytes = cipher.doFinal(raw);
			result = new String(stringBytes, "UTF8");
			in.close();
		} catch (Throwable e) {
			log.error("Error ===> ", e);
		}
		log.debug("== Fin desencrypt ==");
		return result;
	}
}
