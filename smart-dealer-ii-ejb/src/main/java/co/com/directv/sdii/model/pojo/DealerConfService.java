package co.com.directv.sdii.model.pojo;

import java.util.Date;


/**
 * Req-0096 - Requerimiento Consolidado AsignadorO
 * DealerConfService entity. @author MyEclipse Persistence Tools
 */

public class DealerConfService implements java.io.Serializable {



	private static final long serialVersionUID = -6509044023557182396L;
	private Long id;	
	private Long dealerConfigurationId;    
	private Long serviceCategoryId;
    private Date dateLastChange;
    private Long userIdLastChange;
    private String status;


    // Constructors

    /** default constructor */
    public DealerConfService() {
    }

    
    public Long getDealerConfigurationId() {
		return dealerConfigurationId;
	}


	public void setDealerConfigurationId(Long dealerConfigurationId) {
		this.dealerConfigurationId = dealerConfigurationId;
	}


	public Long getServiceCategoryId() {
		return serviceCategoryId;
	}


	public void setServiceCategoryId(Long serviceCategoryId) {
		this.serviceCategoryId = serviceCategoryId;
	}


	/** full constructor */
    public DealerConfService(	Long 				dealerConfigurationId, 
    							Long 				serviceCategoryId, 
    							Date 				dateLastChange, 
    							Long 				userIdLastChange, 
    							String 				status) 
    {
        this.dealerConfigurationId 	= dealerConfigurationId;
        this.serviceCategoryId 		= serviceCategoryId;
        this.dateLastChange 		= dateLastChange;
        this.userIdLastChange 		= userIdLastChange;
        this.status 				= status;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
   
}