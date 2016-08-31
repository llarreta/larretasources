package co.com.directv.sdii.facade.bb;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.EmployeeDevicesVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad EmployeeDevices.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeeDevicesFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto EmployeeDevices
	 * @param obj - EmployeeDevicesVO  objeto que encapsula la información de un EmployeeDevicesVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createEmployeeDevices(EmployeeDevicesVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un EmployeeDevices
	 * @param obj - EmployeeDevicesVO  objeto que encapsula la información de un EmployeeDevicesVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateEmployeeDevices(EmployeeDevicesVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un EmployeeDevices
	 * @param obj - EmployeeDevicesVO  información del EmployeeDevicesVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteEmployeeDevices(EmployeeDevicesVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un EmployeeDevices por su identificador
	 * @param id - Long identificador del EmployeeDevices a ser consultado
	 * @return objeto con la información del EmployeeDevicesVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public EmployeeDevicesVO getEmployeeDevicesByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los EmployeeDevices almacenados en la persistencia
	 * @return List<EmployeeDevicesVO> Lista con los EmployeeDevicesVO existentes, una lista vacia en caso que no existan EmployeeDevicesVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<EmployeeDevicesVO> getAllEmployeeDevicess() throws BusinessException;

}
