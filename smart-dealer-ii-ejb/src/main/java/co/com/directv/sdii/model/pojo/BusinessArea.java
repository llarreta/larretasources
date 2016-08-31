package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * Req-0096 - Requerimiento Consolidado AsignadorO
 * BusinessArea entity. @author MyEclipse Persistence Tools
 */

public class BusinessArea implements java.io.Serializable {

	private static final long serialVersionUID = 7152128203564950069L;
	private Long id;
    private String businessAreaCode;
    private String businessAreaName;
    private Date dateLastChange;    
    private Long userIdLastChange;
    private String status;


    // Constructors

    /** default constructor */
    public BusinessArea() {
    }

    
	public BusinessArea(BusinessArea copy) {
		super();
		this.id = copy.id;
		this.businessAreaCode = copy.businessAreaCode;
		this.businessAreaName = copy.businessAreaName;
		this.dateLastChange = copy.dateLastChange;
		this.userIdLastChange = copy.userIdLastChange;
		this.status = copy.status;
		
		
	}
    
    /** full constructor */
    //public BusinessArea(String businessAreaCode, Timestamp dateLastChange, Long userIdLastChange, String status) {
    public BusinessArea(String businessAreaCode, Date dateLastChange, Long userIdLastChange, String status) {
        this.businessAreaCode = businessAreaCode;
        this.dateLastChange = dateLastChange;
        this.userIdLastChange = userIdLastChange;
        this.status = status;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessAreaCode() {
        return this.businessAreaCode;
    }
    
    public void setBusinessAreaCode(String businessAreaCode) {
        this.businessAreaCode = businessAreaCode;
    }

    public String getBusinessAreaName() {
		return businessAreaName;
	}


	public void setBusinessAreaName(String businessAreaName) {
		this.businessAreaName = businessAreaName;
	}

    /*
	public Timestamp getDateLastChange() {
        return this.dateLastChange;
    }
    
    public void setDateLastChange(Timestamp dateLastChange) {
        this.dateLastChange = dateLastChange;
    }
    */
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