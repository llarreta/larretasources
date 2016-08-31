package co.com.directv.sdii.model.pojo;
import java.util.Date;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * DealerConfCustomerType entity. @author MyEclipse Persistence Tools
 */

public class DealerConfCustomerType implements java.io.Serializable {

	private static final long serialVersionUID = -49005629317847270L;
	private Long id;
    
	private Long dealerConfigurationId;    
	private Long customerClassTypeId;
	private Date dateLastChange;
    private Long userIdLastChange;
    private String status;

    public DealerConfCustomerType() {
    }
    public DealerConfCustomerType(	Long 	dealerConfigurationId,    								
    								Long 	customerClassTypeId,
    								Date 	dateLastChange, 
    								Long 	userIdLastChange, 
    								String 	status) 
    {
        this.dealerConfigurationId = dealerConfigurationId;
        this.customerClassTypeId = customerClassTypeId;
        this.dateLastChange = dateLastChange;
        this.userIdLastChange = userIdLastChange;
        this.status = status;
    }


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

	public Long getDealerConfigurationId() {
		return dealerConfigurationId;
	}
	public void setDealerConfigurationId(Long dealerConfigurationId) {
		this.dealerConfigurationId = dealerConfigurationId;
	}
	public Long getCustomerClassTypeId() {
		return customerClassTypeId;
	}
	public void setCustomerClassTypeId(Long customerClassTypeId) {
		this.customerClassTypeId = customerClassTypeId;
	}
   
	
}