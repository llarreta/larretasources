package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.State;

/**
 * 
 * States Value Object
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.States
 */
public class StateVO extends State implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1748947231362695987L;

    
    private Long countriesId;

    /**
     * @return the countriesId
     */
    public Long getCountriesId() {
        return countriesId;
    }

    /**
     * @param countriesId the countriesId to set
     */
    public void setCountriesId(Long countriesId) {
        this.countriesId = countriesId;
    }
    
}
