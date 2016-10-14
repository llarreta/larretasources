package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.EmployeeRetirementCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.EmployeeRetirementFacadeBeanLocal;
import co.com.directv.sdii.model.vo.EmployeeRetirementVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad EmployeeRetirement 
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.EmployeeRetirementCRUDBeanLocal;
 * @see co.com.directv.sdii.facade.dealers.EmployeeRetirementFacadeBeanLocal;
 */
@Stateless(name="EmployeeRetirementFacadeBeanLocal",mappedName="ejb/EmployeeRetirementFacadeBeanLocal")
public class EmployeeRetirementFacadeBean implements EmployeeRetirementFacadeBeanLocal {

	@EJB(name="EmployeeRetirementCRUDBeanLocal",beanInterface=EmployeeRetirementCRUDBeanLocal.class)
	private EmployeeRetirementCRUDBeanLocal business;
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EmployeeRetirementFacadeBeanLocal#createEmployeeRetirement(co.com.directv.sdii.model.vo.EmployeeRetirementVO, long)
	 */
	public void createEmployeeRetirement(EmployeeRetirementVO erVO, long employeeStatusId)	throws BusinessException {
		business.createEmployeeRetirement(erVO, employeeStatusId);
		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EmployeeRetirementFacadeBeanLocal#deleteEmployeeRetirement(co.com.directv.sdii.model.vo.EmployeeRetirementVO)
	 */
	public void deleteEmployeeRetirement(EmployeeRetirementVO erVO) throws BusinessException {
		business.deleteEmployeeRetirement(erVO);
		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EmployeeRetirementFacadeBeanLocal#getAllEmployeeRetirement()
	 */
	public List<EmployeeRetirementVO> getAllEmployeeRetirement() throws BusinessException {
		return business.getAllEmployeeRetirement();
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EmployeeRetirementFacadeBeanLocal#getEmployeeRetirementByID(java.lang.Long)
	 */
	public EmployeeRetirementVO getEmployeeRetirementByID(Long id) throws BusinessException {
		return business.getEmployeeRetirementByID(id);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EmployeeRetirementFacadeBeanLocal#updateEmployeeRetirement(co.com.directv.sdii.model.vo.EmployeeRetirementVO)
	 */
	public void updateEmployeeRetirement(EmployeeRetirementVO erVO) throws BusinessException {
		business.updateEmployeeRetirement(erVO);
		
	}

}
