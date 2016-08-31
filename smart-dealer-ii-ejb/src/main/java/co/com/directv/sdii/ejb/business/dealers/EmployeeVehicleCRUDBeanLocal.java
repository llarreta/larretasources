package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.EmployeeVehicleVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad EmployeeVehicle
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeeVehicleCRUDBeanLocal {

	public void createEmployeeVehicle(EmployeeVehicleVO obj) throws BusinessException;


	public EmployeeVehicleVO getEmployeeVehicleByID(Long id) throws BusinessException;
	
	
	public void updateEmployeeVehicle(EmployeeVehicleVO obj) throws BusinessException;
	
	
	public void deleteEmployeeVehicle(EmployeeVehicleVO obj) throws BusinessException;
	
	
	public List<EmployeeVehicleVO> getAllEmployeeVehicle()  throws BusinessException;
}
