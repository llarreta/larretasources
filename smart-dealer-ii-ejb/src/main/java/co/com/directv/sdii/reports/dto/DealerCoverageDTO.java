/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.reports.dto;

import java.io.Serializable;

import co.com.directv.sdii.model.dto.BaseRequest;
import co.com.directv.sdii.model.pojo.DealerCoverage;

/**
 * Objeto que encapsula la información de un DealerCoverage
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealerCoverage    
 */
public class DealerCoverageDTO extends BaseRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6592179487725205951L;
	/**
	 * 
	 */
	
	
	private String stateName;
	private String cityName;
	private String postalCodeName;
	private String dealerCode;
	private String depotCode;
	private String coverageTypeName;
	
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getPostalCodeName() {
		return postalCodeName;
	}
	public void setPostalCodeName(String postalCodeName) {
		this.postalCodeName = postalCodeName;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getDepotCode() {
		return depotCode;
	}
	public void setDepotCode(String depotCode) {
		this.depotCode = depotCode;
	}
	public String getCoverageTypeName() {
		return coverageTypeName;
	}
	public void setCoverageTypeName(String coverageTypeName) {
		this.coverageTypeName = coverageTypeName;
	}

}
