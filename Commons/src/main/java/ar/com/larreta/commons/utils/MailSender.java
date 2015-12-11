package ar.com.larreta.commons.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import ar.com.larreta.commons.AppConfigData;
import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.exceptions.AppException;

public class MailSender extends JavaMailSenderImpl implements AppObject{

	public static final String HTML_CONTENT_TYPE = "text/html; charset=utf-8";
	
	 private static final String PROPERTY_NAME_MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
	 private static final String PROPERTY_NAME_MAIL_SMTP_AUTH = "mail.smtp.auth";
	 private static final String PROPERTY_NAME_MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	 private static final String PROPERTY_NAME_MAIL_DEBUG = "mail.debug";
	 private static final String PROPERTY_NAME_MAIL_TIMEOUT = "mail.timeout";

	protected AppConfigData appConfigData;
	
	private AppObject appObject = new AppObjectImpl(getClass());
	
	public MailSender(AppConfigData appConfigData) {
		super();
		this.appConfigData = appConfigData;
	    setHost(appConfigData.getMailHost());
	    setPort(appConfigData.getMailPort());
	    setUsername(appConfigData.decryptedMailUser());
	    setPassword(appConfigData.decryptedPasswordUserMail());
	
	    setJavaMailProperties(getMailProperties());
	}


	
	public Properties getMailProperties() {
	    Properties properties = new Properties();
	    properties.setProperty(PROPERTY_NAME_MAIL_TRANSPORT_PROTOCOL, appConfigData.getProperty(PROPERTY_NAME_MAIL_TRANSPORT_PROTOCOL));
	    properties.setProperty(PROPERTY_NAME_MAIL_SMTP_AUTH, appConfigData.getProperty(PROPERTY_NAME_MAIL_SMTP_AUTH));
	    properties.setProperty(PROPERTY_NAME_MAIL_SMTP_STARTTLS_ENABLE, appConfigData.getProperty(PROPERTY_NAME_MAIL_SMTP_STARTTLS_ENABLE));
	    properties.setProperty(PROPERTY_NAME_MAIL_DEBUG, appConfigData.getProperty(PROPERTY_NAME_MAIL_DEBUG));
	    properties.setProperty(PROPERTY_NAME_MAIL_TIMEOUT, appConfigData.getProperty(PROPERTY_NAME_MAIL_TIMEOUT));
	    return properties;
	}
	
	public void send(String content, String contentType, String subject, String from, Collection<String> to){
		try {
			MimeMessage mime = createMimeMessage();
			mime.setContent(content, contentType);
			mime.setSubject(subject);
			mime.setFrom(new InternetAddress(from));
			mime.setRecipients(Message.RecipientType.TO, getTo(to));
			send(mime);
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));
		}
		
	}

	public Address[] getTo(Collection<String> to)
			throws AddressException {
		Iterator<String> emails = to.iterator();
		Address[] addresses = new InternetAddress[to.size()];
		Integer index = 0;
		while (emails.hasNext()) {
			String actual = (String) emails.next();
			addresses[0] = new InternetAddress(actual);
			index++;
		}
		return addresses;
	}
	
	public Logger getLog(){
		return appObject.getLog();
	}

	public void setLog(Logger log){
		appObject.setLog(log);
	}
	
	/**
	 * Arranca a contabilizar estadisticas
	 * @param mark
	 * @return
	 */
	public Long statisticsStart(String mark){
		return appObject.statisticsStart(mark);
	} 
	
	/**
	 * Finaliza de contabilizar estadisticas
	 * @param id
	 */
	public void statisticsStop(Long id){
		appObject.statisticsStop(id);
	}

}
