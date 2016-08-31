package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 28/06/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class CustomerWorkOrderByStatusDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6287143018842312729L;
	
	private String typeCode;
	private String typeName;
	private String description;
	private String statusCode;
	private String statusName;
	private String involves;
	private String involvesName;
	private Date interactionDate;
	
	public CustomerWorkOrderByStatusDTO() {
		super();
	}
	
	public CustomerWorkOrderByStatusDTO(String typeCode, String typeName,
			String description, String statusCode, String statusName,
			String involves, String involvesName, Date interactionDate) {
		super();
		this.typeCode = typeCode;
		this.typeName = typeName;
		this.description = description;
		this.statusCode = statusCode;
		this.statusName = statusName;
		this.involves = involves;
		this.involvesName = involvesName;
		this.interactionDate = interactionDate;
	}
	
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getInvolves() {
		return involves;
	}
	public void setInvolves(String involves) {
		this.involves = involves;
	}
	public String getInvolvesName() {
		return involvesName;
	}
	public void setInvolvesName(String involvesName) {
		this.involvesName = involvesName;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getInteractionDate() {
		return interactionDate;
	}
	
	public void setInteractionDate(Date interactionDate) {
		this.interactionDate = interactionDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
