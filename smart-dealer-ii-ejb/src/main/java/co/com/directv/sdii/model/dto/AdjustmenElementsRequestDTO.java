package co.com.directv.sdii.model.dto;

import java.util.List;

/**
 * DTO para empaquetar la informacion para los elementos del ajuste.
 * 
 * Fecha de Creación: 22/03/2012
 * @author csierra <a href="mailto:csierra@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class AdjustmenElementsRequestDTO {

	private Long adjustmentId;
	private Long countryId;
	private Long userId;
	private String serialCode;
	private boolean isSerialized;
	private List<Long> adjustmentIdList;
	
	public void setAdjustmentId(Long adjustmentId) {
		this.adjustmentId = adjustmentId;
	}
	
	public Long getAdjustmentId() {
		return adjustmentId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	
	public Long getCountryId() {
		return countryId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}
	
	public String getSerialCode() {
		return serialCode;
	}

	public void setAdjustmentIdList(List<Long> adjustmentIdList) {
		this.adjustmentIdList = adjustmentIdList;
	}

	public List<Long> getAdjustmentIdList() {
		return adjustmentIdList;
	}

	public void setSerialized(boolean isSerialized) {
		this.isSerialized = isSerialized;
	}

	public boolean isSerialized() {
		return isSerialized;
	}

}
