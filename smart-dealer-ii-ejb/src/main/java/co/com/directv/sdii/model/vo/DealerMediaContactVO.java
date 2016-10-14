package co.com.directv.sdii.model.vo;

import co.com.directv.sdii.model.pojo.DealerMediaContact;

/**
 * 
 * DealersMediaContact Value Object 
 * 
 * Fecha de Creación: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealersMediaContact
 */
public class DealerMediaContactVO extends DealerMediaContact {

    /**
     *
     */
    private static final long serialVersionUID = 5697654639978632808L;

    
    private Long mediaContactTypeId;
    private Long dealerId;
    private String mediaContactTypeName;

    public String getMediaContactTypeName() {
        return mediaContactTypeName;
    }

    public void setMediaContactTypeName(String mediaContactTypeName) {
        this.mediaContactTypeName = mediaContactTypeName;
    }

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

    
}
