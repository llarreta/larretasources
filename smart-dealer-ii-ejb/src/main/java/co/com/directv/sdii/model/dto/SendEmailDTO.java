package co.com.directv.sdii.model.dto;

import java.io.File;
import java.util.Collection;

public class SendEmailDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1552304792528093986L;
	
	private String newsType;
	private Collection<String> recipient;
	private String newsObservation;
	private String newsSourceUser;
	private String newsDocument;
	private File[] attachmentFiles;
	
	public File[] getAttachmentFiles() {
		return attachmentFiles;
	}
	public void setAttachmentFiles(File[] attachmentFiles) {
		this.attachmentFiles = attachmentFiles;
	}
	public String getNewsType() {
		return newsType;
	}
	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}
	public Collection<String> getRecipient() {
		return recipient;
	}
	public void setRecipient(Collection<String> recipient) {
		this.recipient = recipient;
	}
	public String getNewsObservation() {
		return newsObservation;
	}
	public void setNewsObservation(String newsObservation) {
		this.newsObservation = newsObservation;
	}
	public String getNewsSourceUser() {
		return newsSourceUser;
	}
	public void setNewsSourceUser(String newsSourceUser) {
		this.newsSourceUser = newsSourceUser;
	}
	public String getNewsDocument() {
		return newsDocument;
	}
	public void setNewsDocument(String newsDocument) {
		this.newsDocument = newsDocument;
	}
	
}
