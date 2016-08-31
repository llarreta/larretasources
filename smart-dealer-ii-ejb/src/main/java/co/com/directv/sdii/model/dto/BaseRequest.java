package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * 
 * Encapsula las propiedades Transversales 
 * a toda la aplicaion para consumo de WebServices.
 * 
 * Fecha de Creación: 24/05/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class BaseRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 321406742011317518L;

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
