package co.com.directv.sdii.model.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * DealerConfiguration entity. @author MyEclipse Persistence Tools
 */

public class DealerConfiguration  implements java.io.Serializable {


	 private static final long serialVersionUID = 1823391688547947366L;
	 private Long id;
     private Long dealerId;
     //private Dealer dealer;
     private Long customerCategoryId;
     //private CustomerCategory customerCategory;
     private Long businessAreaId;
     //private BusinessArea businessArea;
     private Date dateLastChange;
     private Long userIdLastChange;
     private String status;

     private Set<DealerConfCustomerType> dealerConfCustomerTypeSet = new HashSet<DealerConfCustomerType>(0);
     private Set<DealerConfService>      dealerConfServiceSet      = new HashSet<DealerConfService>(0);
     private Set<DealerConfCoverage>     dealerConfCoverageSet     = new HashSet<DealerConfCoverage>(0);

    // Constructors


	/** default constructor */
    public DealerConfiguration() {
    }

    
    /** full constructor */
    public DealerConfiguration(	Long dealerId, 
    							Long customerCategoryId, 
    							Long businessAreaId, 
    							Date dateLastChange, 
    							Long userIdLastChange, 
    							String status,
    							Set<DealerConfCustomerType> dealerConfCustomerTypeSet) 
    {
        this.dealerId = dealerId;
        this.customerCategoryId = customerCategoryId;
        this.businessAreaId = businessAreaId;
        this.dateLastChange = dateLastChange;
        this.userIdLastChange = userIdLastChange;
        this.status = status;
        this.dealerConfCustomerTypeSet = dealerConfCustomerTypeSet;
    }

    // Property accessors

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	/*public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}*/

	public Long getCustomerCategoryId() {
		return customerCategoryId;
	}

	public void setCustomerCategoryId(Long customerCategoryId) {
		this.customerCategoryId = customerCategoryId;
	}

	/*public CustomerCategory getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(CustomerCategory customerCategory) {
		this.customerCategory = customerCategory;
	}*/

	public Long getBusinessAreaId() {
		return businessAreaId;
	}

	public void setBusinessAreaId(Long businessAreaId) {
		this.businessAreaId = businessAreaId;
	}

	/*public BusinessArea getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(BusinessArea businessArea) {
		this.businessArea = businessArea;
	}*/

	public Date getDateLastChange() {
		return dateLastChange;
	}

	public void setDateLastChange(Date dateLastChange) {
		this.dateLastChange = dateLastChange;
	}

	public Long getUserIdLastChange() {
		return userIdLastChange;
	}

	public void setUserIdLastChange(Long userIdLastChange) {
		this.userIdLastChange = userIdLastChange;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<DealerConfCustomerType> getDealerConfCustomerTypeSet() {
		return dealerConfCustomerTypeSet;
	}

	public void setDealerConfCustomerTypeSet(
			Set<DealerConfCustomerType> dealerConfCustomerTypeSet) {
		this.dealerConfCustomerTypeSet = dealerConfCustomerTypeSet;
	}

	public Set<DealerConfService> getDealerConfServiceSet() {
		return dealerConfServiceSet;
	}

	public void setDealerConfServiceSet(Set<DealerConfService> dealerConfServiceSet) {
		this.dealerConfServiceSet = dealerConfServiceSet;
	}

	public Set<DealerConfCoverage> getDealerConfCoverageSet() {
		return dealerConfCoverageSet;
	}

	public void setDealerConfCoverageSet(
			Set<DealerConfCoverage> dealerConfCoverageSet) {
		this.dealerConfCoverageSet = dealerConfCoverageSet;
	}

}