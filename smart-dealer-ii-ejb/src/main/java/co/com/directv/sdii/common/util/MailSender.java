package co.com.directv.sdii.common.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.EmailMessageException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;

/**
 * Esta clase permite enviar emails utilizando la API de Javamail.
 * 
 * @author Jimmy Velez Mu�oz
 * @author Leonardo Cardozo
 */
@Stateless(mappedName="ejb/MailSenderLocal", name="MailSenderLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MailSender implements MailSenderLocal {

	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
	private static Logger log;

	static {
		log = Logger.getLogger(MailSender.class);
	}

	private MimeMessage msg;
	
	public MailSender() {}

	/**
	 * Configura las propiedades para enviar el correo
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @throws EmailMessageException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws PropertiesException 
	 */
	private void configure(List<MailMessage> mailMessages, Long countryId, boolean isHtmlText) throws MessagingException, UnsupportedEncodingException, EmailMessageException, DAOServiceException, DAOSQLException, PropertiesException {
		
		log.debug("== Inicia GestorCorreo/GestorCorreo ==");
		
		SystemParameter hostParameter = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_MAIL_SMTP_HOST.getCodeEntity(), countryId);
		SystemParameter protocolParameter = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_MAIL_TRANSPORT_PROTOCOL.getCodeEntity(), countryId);
		SystemParameter portParameter = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_MAIL_SMTP_PORT.getCodeEntity(), countryId);
		SystemParameter authParameter = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_MAIL_SMTP_AUTH.getCodeEntity(), countryId);
		SystemParameter senderNickParameter = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_MAIL_SENDERNICK.getCodeEntity(), countryId);
		SystemParameter senderPassParameter = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_MAIL_SENDERPASS.getCodeEntity(), countryId);

		

		if(hostParameter == null 
		   || protocolParameter == null 
		   || portParameter == null 
		   || authParameter == null 
		   || senderNickParameter == null 
		   || senderPassParameter == null){
			throw new EmailMessageException("No estan los par�metros minimos para enviar mails");
		}
		
		Properties props = System.getProperties();  
		props.setProperty("mail.smtp.port", portParameter.getValue());  
		props.setProperty("mail.smtp.socketFactory.port",  portParameter.getValue());
		props.setProperty("mail.smtp.host", hostParameter.getValue());
		props.setProperty("mail.smtp.starttls.enable", "true");  
		props.setProperty("mail.smtp.auth", authParameter.getValue());
		props.setProperty("mail.imaps.ssl.trust", "*");
		

		log.info("mail.smtp.port["+ portParameter.getValue());
		log.info("mail.smtp.socketFactory.port["+ portParameter.getValue());
		log.info("mail.smtp.host["+ hostParameter.getValue());
		log.info("mail.smtp.starttls.enable["+ "true");
		log.info("mail.smtp.auth["+ authParameter.getValue());
		
		log.info("senderNickParameter.getValue()["+ senderNickParameter.getValue());
		log.info("senderPassParameter.getValue()["+ senderPassParameter.getValue());
		
		Session smtpSession = null;
		
		if( Boolean.valueOf( authParameter.getValue() ) ){
			Authenticator auth = new SMTPAuthenticator(senderNickParameter.getValue(), senderPassParameter.getValue());
			smtpSession = Session.getInstance(props, auth);
		} else {
			smtpSession = Session.getInstance(props);
		}
		  
		smtpSession.setDebug(false);
		final String encoding = "UTF-8"; 
		
		for (MailMessage mailMessage : mailMessages) {
			if(mailMessage==null || mailMessage.getTos() == null || mailMessage.getTos().size() ==0){
				throw new EmailMessageException("Mensaje a ser enviado es nulo");
			}
			
			msg= new MimeMessage(smtpSession);  
			msg.setFrom(new InternetAddress(senderNickParameter.getValue()));  
			
			InternetAddress[] recipients = new InternetAddress[mailMessage.getTos().size()];
			for (int i = 0; i < recipients.length; i++) {
				recipients[i] = new InternetAddress(mailMessage.getTos().get(i));
			}
			
			msg.addRecipients(Message.RecipientType.TO, recipients);  
			msg.setSubject(mailMessage.getSubject(), encoding);  
			if(isHtmlText){
				msg.setContent(mailMessage.getMessage(), "text/html");
			}else{
				msg.setText(mailMessage.getMessage(), encoding);
			}
			
			// Soporte para archivos adjuntos
	        if (mailMessage.getAttachment() != null && mailMessage.getAttachment().length > 0) {
	        	Multipart mp = new MimeMultipart();
	        	MimeBodyPart mbpMessageText = new MimeBodyPart();
	            mbpMessageText.setText(mailMessage.getMessage(), "UTF-8");
	            mp.addBodyPart(mbpMessageText);

	            for (int i = 0; i < mailMessage.getAttachment().length; i++) {
	                MimeBodyPart mbpAttachment = new MimeBodyPart();
	                FileDataSource fds = new FileDataSource((mailMessage.getAttachment()[i]).getAbsolutePath());
	                mbpAttachment.setDataHandler(new DataHandler(fds));
	                mbpAttachment.setFileName(MimeUtility.encodeText(mailMessage.getAttachment()[i].getName(),"iso-8859-1","Q"));
	                mp.addBodyPart(mbpAttachment);
				}
	            msg.setContent(mp);
	        }
	        
	        log.info("Enviando correo..." + msg.getSender());
	        javax.mail.Transport.send(msg);
	        log.info("Correo enviado.");        
		}	
	}
	
	/**
	 * Envia el mensaje.
	 * @throws EmailMessageException 
	 */
	public void send(MailMessage mailMessage, Long countryId, boolean isHtmlText) throws EmailMessageException {
		log.debug("== Inicia enviaMensaje/GestorCorreo ==");
		try {
			List<MailMessage> mailMessages = new ArrayList<MailMessage>();
			mailMessages.add(mailMessage);
			configure(mailMessages, countryId, isHtmlText);
		} catch (Exception ex) {
			log.debug("== Error send(MailMessage mailMessage)/MailSender ==");
			throw new EmailMessageException(ErrorBusinessMessages.EMAIL_SENDING_ERROR.getCode(),ErrorBusinessMessages.EMAIL_SENDING_ERROR.getMessage());
		}
		log.debug("== Fin enviaMensaje/GestorCorreo ==");
	}
	
	/**
	 * Envia una lista de mensajes.
	 * @throws EmailMessageException 
	 */
	public void send(List<MailMessage> mailMessages, Long countryId, boolean isHtmlText) throws EmailMessageException {
		log.debug("== Inicia enviaMensaje/GestorCorreo ==");
		try {
			configure(mailMessages, countryId, isHtmlText);
		} catch (Exception ex) {
			log.debug("== Error send(List<MailMessage> mailMessages)/MailSender ==", ex);
			throw new EmailMessageException(ErrorBusinessMessages.EMAIL_SENDING_ERROR.getCode(),ErrorBusinessMessages.EMAIL_SENDING_ERROR.getMessage());
		}
		log.debug("== Fin enviaMensaje/GestorCorreo ==");
	}
	
}
