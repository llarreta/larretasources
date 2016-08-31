package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.City;

/**
 * 
 * Cities Value Object
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Cities
 */
public class CityVO extends City implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6498170005921391950L;

     
    private Long estatesId;

    /**
     * @return the estatesId
     */
    public Long getEstatesId() {
        return estatesId;
    }

    /**
     * @param estatesId the estatesId to set
     */
    public void setEstatesId(Long estatesId) {
        this.estatesId = estatesId;
    }

}
