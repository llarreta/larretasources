package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.EmployeeVehicleVO;

/**
 * 
 *  Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de EmployeeVehicle 
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeeVehicleFacadeBeanLocal {

	public void createEmployeeVehicle(EmployeeVehicleVO obj) throws BusinessException;


	public EmployeeVehicleVO getEmployeeVehicleByID(Long id) throws BusinessException;
	
	
	public void updateEmployeeVehicle(EmployeeVehicleVO obj) throws BusinessException;
	
	
	public void deleteEmployeeVehicle(EmployeeVehicleVO obj) throws BusinessException;
	
	
	public List<EmployeeVehicleVO> getAllEmployeeVehicle()  throws BusinessException;
}
