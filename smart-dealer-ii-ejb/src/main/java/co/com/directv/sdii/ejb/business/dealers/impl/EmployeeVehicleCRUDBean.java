package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.EmployeeVehicleCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.EmployeeVehicleVO;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad EmployeeVehicle
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.EmployeeVehicleDAOLocal;
 * @see co.com.directv.sdii.ejb.business.dealers.EmployeeVehicleCRUDBeanLocal;
 */
@Stateless(name="EmployeeVehicleCRUDBeanLocal",mappedName="ejb/EmployeeVehicleCRUDBeanLocal")
public class EmployeeVehicleCRUDBean extends BusinessBase implements	EmployeeVehicleCRUDBeanLocal {

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param obj
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void createEmployeeVehicle(EmployeeVehicleVO obj)
			throws BusinessException {
		
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param obj
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void deleteEmployeeVehicle(EmployeeVehicleVO obj)
			throws BusinessException {
		
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<EmployeeVehicleVO> getAllEmployeeVehicle()
			throws BusinessException {
		return null;
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param id
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public EmployeeVehicleVO getEmployeeVehicleByID(Long id)
			throws BusinessException {
		return null;
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param obj
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void updateEmployeeVehicle(EmployeeVehicleVO obj)
			throws BusinessException {
		
	}

	
}
