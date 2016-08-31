/**
 * Creado Mar 3, 2010 4:40:45 PM
 */
package co.com.directv.sdii.model.vo;

import co.com.directv.sdii.model.pojo.EmployeeMediaContact;

/**
 * EmployeeMediaContact Value Object
 * 
 * Fecha de Creaci�n: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.EmployeeMediaContact  
 */
public class EmployeeMediaContactVO extends EmployeeMediaContact {

    /**
     *
     */
    private static final long serialVersionUID = -8005156744807925403L;

    
    private Long mediaContactTypeId;
    private Long dealerId;

    /**
     * @return the mediaContactTypeId
     */
    public Long getMediaContactTypeId() {
        return mediaContactTypeId;
    }

    /**
     * @param mediaContactTypeId the mediaContactTypeId to set
     */
    public void setMediaContactTypeId(Long mediaContactTypeId) {
        this.mediaContactTypeId = mediaContactTypeId;
    }

    /**
     * @return the dealerId
     */
    public Long getDealerId() {
        return dealerId;
    }

    /**
     * @param dealerId the dealerId to set
     */
    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

	@Override
	public String toString() {
		return "EmployeeMediaContactVO [dealerId=" + dealerId
				+ ", mediaContactTypeId=" + mediaContactTypeId
				+ ", getDealerId()=" + getDealerId()
				+ ", getMediaContactTypeId()=" + getMediaContactTypeId()
				+ ", getId()=" + getId() + ", getMediaContactType()="
				+ getMediaContactType() + ", getMediaContactValue()="
				+ getMediaContactValue() + "]";
	}

	
    
    

    
}
