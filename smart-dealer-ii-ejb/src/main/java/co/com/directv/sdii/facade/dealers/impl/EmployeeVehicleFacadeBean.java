package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.EmployeeVehicleCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.EmployeeVehicleFacadeBeanLocal;
import co.com.directv.sdii.model.vo.EmployeeVehicleVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad EmployeeVehicle 
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.EmployeeVehicleCRUDBeanLocal;
 * @see co.com.directv.sdii.facade.dealers.EmployeeVehicleFacadeBeanLocal;
 */
@Stateless(name="EmployeeVehicleFacadeBeanLocal",mappedName="ejb/EmployeeVehicleFacadeBeanLocal")
public class EmployeeVehicleFacadeBean implements EmployeeVehicleFacadeBeanLocal {

	@SuppressWarnings("unused")
	@EJB(name="EmployeeVehicleCRUDBeanLocal",beanInterface=EmployeeVehicleCRUDBeanLocal.class)
	private EmployeeVehicleCRUDBeanLocal business;
	
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
