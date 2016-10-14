package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.Position;

/**
 * 
 * Positions Value Object 
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Positions
 */
public class PositionVO extends Position implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2158886491718479280L;

    
    private Long dealerId;

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
