package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * 
 * Data Transfer Object que encapsula la informacion 
 * de un elemento serializado o no serializado.
 * 
 * Fecha de Creación: 1/06/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class InfoDealerCountryWarehouseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4853519002183700792L;

	private Long userId;
	private Long countryId;
	private boolean codeLogisticsOperator;
	private String warehouseTypeCode;
    
	public InfoDealerCountryWarehouseDTO() {
		super();
	}
	
	public InfoDealerCountryWarehouseDTO(Long userId, Long countryId,
			boolean codeLogisticsOperator, String warehouseTypeCode) {
		super();
		this.userId = userId;
		this.countryId = countryId;
		this.codeLogisticsOperator = codeLogisticsOperator;
		this.warehouseTypeCode = warehouseTypeCode;
	}

	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public boolean isCodeLogisticsOperator() {
		return codeLogisticsOperator;
	}
	
	public void setCodeLogisticsOperator(boolean codeLogisticsOperator) {
		this.codeLogisticsOperator = codeLogisticsOperator;
	}
	
	public String getWarehouseTypeCode() {
		return warehouseTypeCode;
	}
	
	public void setWarehouseTypeCode(String warehouseTypeCode) {
		this.warehouseTypeCode = warehouseTypeCode;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	
	
		
}
