package co.com.directv.sdii.model.dto;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

/**
 * Permite encapsular informacion para la capa de presentacion de los contacts de IBS
 * 
 * Fecha de Creación: 3/12/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class IbsContactDTO implements  java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5156950544368618404L;
	
	private String ibsContactCode;
	private String ibsContactReasonCode;
	private String ibsContactReasonName;
	private String description;
	private Date creationDate;
	private String contactStatusCode;
	private String contactStatusName;
	private String characteristicValue;
	private String category;
	private String subCategory;
	
	public IbsContactDTO() {
		super();
	}

	public IbsContactDTO(String ibsContactCode, String ibsContactReasonCode,
			String ibsContactReasonName, String description, Date creationDate,
			String contactStatusCode, String contactStatusName,
			String characteristicValue,String category,String subCategory) {
		super();
		this.ibsContactCode = ibsContactCode;
		this.ibsContactReasonCode = ibsContactReasonCode;
		this.ibsContactReasonName = ibsContactReasonName;
		this.description = description;
		this.creationDate = creationDate;
		this.contactStatusCode = contactStatusCode;
		this.contactStatusName = contactStatusName;
		this.characteristicValue = characteristicValue;
		this.category = category; 
		this.subCategory = subCategory; 
	}

	public String getIbsContactCode() {
		return ibsContactCode;
	}

	public void setIbsContactCode(String ibsContactCode) {
		this.ibsContactCode = ibsContactCode;
	}

	public String getIbsContactReasonCode() {
		return ibsContactReasonCode;
	}

	public void setIbsContactReasonCode(String ibsContactReasonCode) {
		this.ibsContactReasonCode = ibsContactReasonCode;
	}

	public String getIbsContactReasonName() {
		return ibsContactReasonName;
	}

	public void setIbsContactReasonName(String ibsContactReasonName) {
		this.ibsContactReasonName = ibsContactReasonName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getContactStatusCode() {
		return contactStatusCode;
	}

	public void setContactStatusCode(String contactStatusCode) {
		this.contactStatusCode = contactStatusCode;
	}

	public String getContactStatusName() {
		return contactStatusName;
	}

	public void setContactStatusName(String contactStatusName) {
		this.contactStatusName = contactStatusName;
	}

	public String getCharacteristicValue() {
		return characteristicValue;
	}

	public void setCharacteristicValue(String characteristicValue) {
		this.characteristicValue = characteristicValue;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	
	
	
}
