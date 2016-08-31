/**
 * Creado 05/04/2011 10:00:03
 */
package co.com.directv.sdii.ws.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsula la información de respuesta del envio de correos electrónicos
 * 
 * Fecha de Creación: 05/04/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class ResponseSendMailDTO {

	/**
	 * Lista con los correos de los destinatarios a los cuales se les envió correo
	 */
	private List<String> recipientsMail;

	public List<String> getRecipientsMail() {
		return recipientsMail;
	}

	public void setRecipientsMail(List<String> recipientsMail) {
		this.recipientsMail = recipientsMail;
	}

	public ResponseSendMailDTO() {
		super();
		recipientsMail = new ArrayList<String>();
	}
	
	
	
}
