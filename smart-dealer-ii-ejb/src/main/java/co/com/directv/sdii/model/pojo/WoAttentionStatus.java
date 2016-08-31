/**
 * Creado 3/12/2010 18:14:32
 */
package co.com.directv.sdii.model.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 3/12/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class WoAttentionStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -228443085333202110L;
	
	private Long id;
	
	private Long woId;
	
	private AttentionStatus attentionStatus;
	
	private String messageCode;
	
	private String message;
	
	private Date attentionDate;
	
	private String recordStatus;
	
	

	public WoAttentionStatus(Long id, Long woId,
			AttentionStatus attentionStatus, String messageCode,
			String message, Date attentionDate, String recordStatus) {
		super();
		this.id = id;
		this.woId = woId;
		this.attentionStatus = attentionStatus;
		this.messageCode = messageCode;
		this.message = message;
		this.attentionDate = attentionDate;
		this.recordStatus = recordStatus;
	}
	
	

	public WoAttentionStatus() {
		super();
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWoId() {
		return woId;
	}

	public void setWoId(Long woId) {
		this.woId = woId;
	}

	public AttentionStatus getAttentionStatus() {
		return attentionStatus;
	}

	public void setAttentionStatus(AttentionStatus attentionStatus) {
		this.attentionStatus = attentionStatus;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getAttentionDate() {
		return attentionDate;
	}

	public void setAttentionDate(Date attentionDate) {
		this.attentionDate = attentionDate;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

}
