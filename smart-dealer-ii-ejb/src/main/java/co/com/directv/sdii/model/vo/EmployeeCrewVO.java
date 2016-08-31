package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.EmployeeCrew;

/**
 * 
 * Employees Crew Value Object   
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.EmployeesCrew
 */
public class EmployeeCrewVO extends EmployeeCrew implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7934963983356583057L;

    private Long amountWoAssigment;
    
    
    private Long employeeRolId;

    /**
     * @return the employeeRolId
     */
    public Long getEmployeeRolId() {
        return employeeRolId;
    }

    /**
     * @param employeeRolId the employeeRolId to set
     */
    public void setEmployeeRolId(Long employeeRolId) {
        this.employeeRolId = employeeRolId;
    }

	public Long getAmountWoAssigment() {
		return amountWoAssigment;
	}

	public void setAmountWoAssigment(Long amountWoAssigment) {
		this.amountWoAssigment = amountWoAssigment;
	}
    
}
