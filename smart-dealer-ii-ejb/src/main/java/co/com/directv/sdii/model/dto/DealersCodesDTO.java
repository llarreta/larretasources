package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * 
 * DealersCodes Data Transfer Object
 * 
 * Fecha de Creación: Mar 4, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class DealersCodesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6102780584809331502L;
	
	private Long dealerCode;
	private String depotCode;
	private String dealerName;
	private Long countryId;
	private boolean isBranch;
	private Long parentDealerID;
	
	public boolean isBranch() {
		return isBranch;
	}
	public void setBranch(boolean isBranch) {
		this.isBranch = isBranch;
	}
	public Long getParentDealerID() {
		return parentDealerID;
	}
	public void setParentDealerID(Long parentDealerID) {
		this.parentDealerID = parentDealerID;
	}
	public Long getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(Long dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getDepotCode() {
		return depotCode;
	}
	public void setDepotCode(String depotCode) {
		this.depotCode = depotCode;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	

}
