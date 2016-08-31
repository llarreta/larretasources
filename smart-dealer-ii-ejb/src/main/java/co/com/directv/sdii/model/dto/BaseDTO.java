package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * 
 * Data Transfer Object Transversal a los objetos de negocio 
 * que viajan entre las capas de Business, Brokers, Helper y
 * Transfer Object Assembler (TOA)
 * 
 * 
 * Fecha de Creación: 23/06/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1883975056172075435L;
	
	private Long userId;
	private Long countryId;
	private String customerCode;
	private String countryCode;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}	
}
