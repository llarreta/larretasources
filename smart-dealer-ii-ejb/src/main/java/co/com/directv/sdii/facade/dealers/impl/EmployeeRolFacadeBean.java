package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.EmployeeRolCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.EmployeeRolFacadeBeanLocal;
import co.com.directv.sdii.model.vo.EmployeeRolVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad EmployeeRol 
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.EmployeeRolCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.EmployeeRolFacadeBeanLocal
 */
@Stateless(name="EmployeeRolFacadeBeanLocal",mappedName="ejb/EmployeeRolFacadeBeanLocal")
public class EmployeeRolFacadeBean implements EmployeeRolFacadeBeanLocal {
	
	@EJB(name="EmployeeRolCRUDBeanLocal",beanInterface=EmployeeRolCRUDBeanLocal.class)
	private EmployeeRolCRUDBeanLocal business;

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EmployeeRolFacadeBeanLocal#getAllEmployeeRol()
	 */
	public List<EmployeeRolVO> getAllEmployeeRol() throws BusinessException {
		return business.getAllEmployeeRol();
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EmployeeRolFacadeBeanLocal#getEmployeeRolByCode(java.lang.String)
	 */
	public EmployeeRolVO getEmployeeRolByCode(String code) throws BusinessException {
		return business.getEmployeeRolByCode(code);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EmployeeRolFacadeBeanLocal#getEmployeeRolByID(java.lang.Long)
	 */
	public EmployeeRolVO getEmployeeRolByID(Long id) throws BusinessException {
		return business.getEmployeeRolByID(id);
	}
		

}
