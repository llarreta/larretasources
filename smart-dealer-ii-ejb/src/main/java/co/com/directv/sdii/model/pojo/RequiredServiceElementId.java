package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;



/**
 * RequiredServiceElementId entity. @author MyEclipse Persistence Tools
 */

public class RequiredServiceElementId  implements java.io.Serializable,Auditable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 3321205736467699833L;
	private Long serviceId;
     private Long elementTypeId;


    // Constructors

    /** default constructor */
    public RequiredServiceElementId() {
    }

    
    /** full constructor */
    public RequiredServiceElementId(Long serviceId, Long elementTypeId) {
        this.serviceId = serviceId;
        this.elementTypeId = elementTypeId;
    }

   
    // Property accessors

    public Long getServiceId() {
        return this.serviceId;
    }
    
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    



   public Long getElementTypeId() {
		return elementTypeId;
	}


	public void setElementTypeId(Long elementTypeId) {
		this.elementTypeId = elementTypeId;
	}


public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RequiredServiceElementId) ) return false;
		 RequiredServiceElementId castOther = ( RequiredServiceElementId ) other; 
         
		 return ( (this.getServiceId()==castOther.getServiceId()) || ( this.getServiceId()!=null && castOther.getServiceId()!=null && this.getServiceId().equals(castOther.getServiceId()) ) )
 && ( (this.getElementTypeId()==castOther.getElementTypeId()) || ( this.getElementTypeId()!=null && castOther.getElementTypeId()!=null && this.getElementTypeId().equals(castOther.getElementTypeId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getServiceId() == null ? 0 : this.getServiceId().hashCode() );
         result = 37 * result + ( getElementTypeId() == null ? 0 : this.getElementTypeId().hashCode() );
         return result;
   }


	@Override
	public String toString() {
		return "RequiredServiceElementId [elementClassId=" + elementTypeId
				+ ", serviceId=" + serviceId + "]";
	}   
}