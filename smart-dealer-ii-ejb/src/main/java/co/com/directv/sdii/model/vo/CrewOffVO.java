package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.CrewOff;

/**
 * 
 * CrewOff Value Object   
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.CrewOff
 */
public class CrewOffVO extends CrewOff implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7934963983356583057L;

   
    private Long crewId;

    /**
     * @return the crewId
     */
    public Long getCrewId() {
        return crewId;
    }

    /**
     * @param crewId the crewId to set
     */
    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }

        

}
