package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;



/**
 * CustomerMediaContact entity. @author MyEclipse Persistence Tools
 */

public class CustomerMediaContact  implements java.io.Serializable,Auditable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 4738892420552266900L;
	private Long id;
    private Long customerId;
    private String mediaContactValue;
    private MediaContactType mediaContactType;


    // Constructors

    /** default constructor */
    public CustomerMediaContact() {
    }

    
    /** full constructor */
    public CustomerMediaContact(Long customerId, String mediaContactValue, MediaContactType mediaContactType) {
        this.customerId = customerId;
        this.mediaContactValue = mediaContactValue;
        this.mediaContactType = mediaContactType;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return this.customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getMediaContactValue() {
        return this.mediaContactValue;
    }
    
    public void setMediaContactValue(String mediaContactValue) {
        this.mediaContactValue = mediaContactValue;
    }


	public MediaContactType getMediaContactType() {
		return mediaContactType;
	}

	public void setMediaContactType(MediaContactType mediaContactType) {
		this.mediaContactType = mediaContactType;
	}


	@Override
	public String toString() {
		return "CustomerMediaContact [id=" + id + "]";
	}

}