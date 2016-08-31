package co.com.directv.sdii.common.util;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.EmailMessageException;

@Local
public interface MailSenderLocal {

	/**
	 * Metodo encargado de enviar un correo electronico desde el correo configurado en HSP+
	 * @param mailMessage Mensaje que se desea enviar
	 * @param countryId pais que se tendra en cuenta para extraer el parametro del sistema del correo electronico
	 * @param isHtmlText
	 * @throws EmailMessageException
	 */
	public void send(MailMessage mailMessage, Long countryId, boolean isHtmlText) throws EmailMessageException;

	/**
	 * Envia una lista de mensajes via correo electronico
	 * @param mailMessages Mensajes que se desean enviar
	 * @param countryId pais que se tendra en cuenta para extraer el parametro del sistema del correo electronico
	 * @param isHtmlText
	 * @throws EmailMessageException
	 */
	public void send(List<MailMessage> mailMessages, Long countryId, boolean isHtmlText) throws EmailMessageException;

}