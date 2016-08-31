package co.com.directv.sdii.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Modela un Mail para ser enviado por GestorDeCorreo
 * @author Leonardo Cardozo Cadavid
 *
 */
public class MailMessage {
	
	private List<String> tos;
	private String subject;
	private String message;
	private File[] attachment;

	private MailMessage(){
		tos = new ArrayList<String>();
	}
	
	public MailMessage(String to, String subject) {
		this();
		this.tos.add(to);
		this.subject = subject;
	}


	public MailMessage(String to, String subject, String message) {
		this();
		this.tos.add(to);
		this.subject = subject;
		this.message = message;
	}
	
	
	
	
	public MailMessage(String to, String subject, String message,File[] attachment) {
		this();
		this.tos.add(to);
		this.subject = subject;
		this.message = message;
		this.attachment = attachment;
	}

	public List<String> getTos() {
		return tos;
	}

	public void addTo(String mail) {
		this.tos.add(mail);
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public File[] getAttachment() {
		return attachment;
	}
	public void setAttachment(File[] attachment) {
		this.attachment = attachment;
	}
	
	
}
