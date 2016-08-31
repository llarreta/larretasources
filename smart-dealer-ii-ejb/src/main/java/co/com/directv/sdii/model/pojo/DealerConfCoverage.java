package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * DealerConfCoverages entity. @author MyEclipse Persistence Tools
 */

public class DealerConfCoverage implements java.io.Serializable {


	 private static final long serialVersionUID = -4852568919557567031L;
	 private Long id;
	 private Long dealerConfigurationId;
	 //private DealerConfiguration dealerConfiguration; 
     
     private CoverageType coverageType;
 	 private Dealer dealer;
 	 private Country country;
 	 private PostalCode postalCode;
 	 private Long dealerPriority;
 	
     private Date dateLastChange;
     private Long userIdLastChange;
     private String status;

 	//Campos para realizar la auditoria
 	private Long dealerId;
 	private Long userId;
 	private Long coverageTypeId;
 	private Long countryId;
 	private Long postalCodeIds;
 	
    // Constructors

    /** default constructor */
    public DealerConfCoverage() {
    }

    
    /** full constructor */
    public DealerConfCoverage(	Long dealerConfigurationId, 
    							Long coverageTypeId, 
    							Date dateLastChange, 
    							Long userIdLastChange, 
    							String status) {
        this.dealerConfigurationId = dealerConfigurationId;
        this.coverageTypeId = coverageTypeId;
        this.dateLastChange = dateLastChange;
        this.userIdLastChange = userIdLastChange;
        this.status = status;
    }

   
    // Property accessors


    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
		return id;
	}

    public Date getDateLastChange() {
        return this.dateLastChange;
    }
    
    public void setDateLastChange(Date dateLastChange) {
        this.dateLastChange = dateLastChange;
    }

    public Long getUserIdLastChange() {
        return this.userIdLastChange;
    }
    
    public void setUserIdLastChange(Long userIdLastChange) {
        this.userIdLastChange = userIdLastChange;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }


	public CoverageType getCoverageType() {
		return coverageType;
	}


	public void setCoverageType(CoverageType coverageType) {
		this.coverageType = coverageType;
	}


	public Dealer getDealer() {
		return dealer;
	}


	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}


	public Country getCountry() {
		return country;
	}


	public void setCountry(Country country) {
		this.country = country;
	}


	public PostalCode getPostalCode() {
		return postalCode;
	}


	public void setPostalCode(PostalCode postalCode) {
		this.postalCode = postalCode;
	}


	public Long getDealerPriority() {
		return dealerPriority;
	}


	public void setDealerPriority(Long dealerPriority) {
		this.dealerPriority = dealerPriority;
	}


	public Long getDealerId() {
		return dealerId;
	}


	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Long getCoverageTypeId() {
		return coverageTypeId;
	}


	public void setCoverageTypeId(Long coverageTypeId) {
		this.coverageTypeId = coverageTypeId;
	}


	public Long getCountryId() {
		return countryId;
	}


	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}


	public Long getPostalCodeIds() {
		return postalCodeIds;
	}


	public void setPostalCodeIds(Long postalCodeIds) {
		this.postalCodeIds = postalCodeIds;
	}


	public Long getDealerConfigurationId() {
		return dealerConfigurationId;
	}


	public void setDealerConfigurationId(Long dealerConfigurationId) {
		this.dealerConfigurationId = dealerConfigurationId;
	}

/*
	public DealerConfiguration getDealerConfiguration() {
		return dealerConfiguration;
	}


	public void setDealerConfiguration(DealerConfiguration dealerConfiguration) {
		this.dealerConfiguration = dealerConfiguration;
	}
   */
 

}