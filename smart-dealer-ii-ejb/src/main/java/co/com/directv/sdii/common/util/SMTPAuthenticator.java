package co.com.directv.sdii.common.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 
 * 
 * Esta clase permite crear un Autenticador para los envíos de los email.
 * Es utilizada por la clase GestorCorreo.
 *
 * @author Jimmy Vélez Muñoz
 * 
 */
public class SMTPAuthenticator extends Authenticator {
	private String fUser;

	private String fPassword;

	public SMTPAuthenticator(String user, String password) {
		fUser = user;
		fPassword = password;
	}

    @Override
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(fUser, fPassword);
	}

}
