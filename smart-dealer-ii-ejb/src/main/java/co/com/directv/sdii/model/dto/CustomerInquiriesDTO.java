package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 03/07/2012
 * @author aharker <a href="mailto:aharker@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class CustomerInquiriesDTO implements Serializable{
	
	/**
	 * 
	 */
	
	private String category;
	private Date date;
	private String method;
	private String description;
	private String reason;
	private String user;
	
	private static final long serialVersionUID = 6287143018842312729L;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public CustomerInquiriesDTO(String category, Date date, String method,
			String description, String reason, String user) {
		super();
		this.category = category;
		this.date = date;
		this.method = method;
		this.description = description;
		this.reason = reason;
		this.user = user;
	}

	public CustomerInquiriesDTO() {
		super();
	}
	
}
