package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.Training;

/**
 * 
 * Training Value Object 
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Training
 */
public class TrainingVO extends Training implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3578038346309736851L;

    
    private Long trainingTypeId;
    private Long employeeId;

    /**
     * @return the trainingTypeId
     */
    public Long getTrainingTypeId() {
        return trainingTypeId;
    }

    /**
     * @param trainingTypeId the trainingTypeId to set
     */
    public void setTrainingTypeId(Long trainingTypeId) {
        this.trainingTypeId = trainingTypeId;
    }

    /**
     * @return the employeeId
     */
    public Long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    

}
