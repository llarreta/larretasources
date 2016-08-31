package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.PostalCode;

/**
 * 
 * PostalCodes Value Object
 * 
 * Fecha de Creaci�n: Mar 15, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.PostalCodes
 */
public class PostalCodeVO extends PostalCode implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5130945513883458857L;
    
    
    private Long zoneTypeId;
    private Long cityId;

    /**
     * @return the zoneTypeId
     */
    public Long getZoneTypeId() {
        return zoneTypeId;
    }

    /**
     * @param zoneTypeId the zoneTypeId to set
     */
    public void setZoneTypeId(Long zoneTypeId) {
        this.zoneTypeId = zoneTypeId;
    }

    /**
     * @return the cityId
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    
}
