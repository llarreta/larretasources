package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 28/06/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class CustomerCharacteristicDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6287143018842312729L;
	
	protected String customerKey;
    protected String characteristicSpecValue;
    protected String characteristicSpecDescription;
    protected String characteristicValue;
    protected String description;
    protected String id;
    protected Integer characteristicCountValue;
    
	public CustomerCharacteristicDTO() {
		super();
	}
	
	public CustomerCharacteristicDTO(String customerKey,
			String characteristicSpecValue,
			String characteristicSpecDescription, String characteristicValue,
			String description, String id, Integer characteristicCountValue) {
		super();
		this.customerKey = customerKey;
		this.characteristicSpecValue = characteristicSpecValue;
		this.characteristicSpecDescription = characteristicSpecDescription;
		this.characteristicValue = characteristicValue;
		this.description = description;
		this.id = id;
		this.characteristicCountValue = characteristicCountValue;
	}
	
	public String getCustomerKey() {
		return customerKey;
	}
	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}
	public String getCharacteristicSpecValue() {
		return characteristicSpecValue;
	}
	public void setCharacteristicSpecValue(String characteristicSpecValue) {
		this.characteristicSpecValue = characteristicSpecValue;
	}
	public String getCharacteristicSpecDescription() {
		return characteristicSpecDescription;
	}
	public void setCharacteristicSpecDescription(
			String characteristicSpecDescription) {
		this.characteristicSpecDescription = characteristicSpecDescription;
	}
	public String getCharacteristicValue() {
		return characteristicValue;
	}
	public void setCharacteristicValue(String characteristicValue) {
		this.characteristicValue = characteristicValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getCharacteristicCountValue() {
		return characteristicCountValue;
	}
	public void setCharacteristicCountValue(Integer characteristicCountValue) {
		this.characteristicCountValue = characteristicCountValue;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
