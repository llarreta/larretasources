/**
 * Creado 18/09/2013 
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.DealerConfiguration;



/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * Objeto que encapsula la información de un DealerConfiguration.
 * 
 * Fecha de Creación: 18/09/2013
 * @author ialessan
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealerConfiguration    
 */
/**
 * @author ialessan
 *
 */
public class DealerConfigurationVO extends DealerConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4856247910674742538L;
	
	private List<DealerCoverageVO> dealerCoverage;
	private List<DealerServiceSubCategoryVO> dealerServiceSubCategories;
	private List<DealerCustomerTypesWoutPcVO> dealerCustomerTypesWoutPc;
	private Long customerCategoryId;
	private Long businessAreaId;
	
	public List<DealerCoverageVO> getDealerCoverage() {
		return dealerCoverage;
	}
	public void setDealerCoverage(List<DealerCoverageVO> dealerCoverage) {
		this.dealerCoverage = dealerCoverage;
	}
	public List<DealerServiceSubCategoryVO> getDealerServiceSubCategories() {
		return dealerServiceSubCategories;
	}
	public void setDealerServiceSubCategories(
			List<DealerServiceSubCategoryVO> dealerServiceSubCategories) {
		this.dealerServiceSubCategories = dealerServiceSubCategories;
	}
	public List<DealerCustomerTypesWoutPcVO> getDealerCustomerTypesWoutPc() {
		return dealerCustomerTypesWoutPc;
	}
	public void setDealerCustomerTypesWoutPc(
			List<DealerCustomerTypesWoutPcVO> dealerCustomerTypesWoutPc) {
		this.dealerCustomerTypesWoutPc = dealerCustomerTypesWoutPc;
	}

	public Long getBusinessAreaId() {
		return businessAreaId;
	}
	public void setBusinessAreaId(Long businessAreaId) {
		this.businessAreaId = businessAreaId;
	}
	public Long getCustomerCategoryId() {
		return customerCategoryId;
	}
	public void setCustomerCategoryId(Long customerCategoryId) {
		this.customerCategoryId = customerCategoryId;
	}
	
}
