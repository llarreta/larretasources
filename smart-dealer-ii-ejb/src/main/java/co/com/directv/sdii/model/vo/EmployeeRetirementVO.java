package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.EmployeeRetirement;

/**
 * 
 * EmployeeRetirement Value Object
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.EmployeeRetirement
 */
public class EmployeeRetirementVO extends EmployeeRetirement implements
		Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6540373420493933276L;

    
    private Long employeeId;

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
