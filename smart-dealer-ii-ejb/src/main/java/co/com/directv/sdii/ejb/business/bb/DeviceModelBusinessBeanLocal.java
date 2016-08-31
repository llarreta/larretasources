package co.com.directv.sdii.ejb.business.bb;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DeviceModelVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad DeviceModel.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DeviceModelBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto DeviceModelVO
	 * @param obj objeto que encapsula la información de un DeviceModelVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDeviceModel(DeviceModelVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un DeviceModelVO
	 * @param obj objeto que encapsula la información de un DeviceModelVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDeviceModel(DeviceModelVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DeviceModelVO
	 * @param obj información del DeviceModelVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDeviceModel(DeviceModelVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un DeviceModelVO por su identificador
	 * @param id identificador del DeviceModelVO a ser consultado
	 * @return objeto con la información del DeviceModelVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DeviceModelVO getDeviceModelByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los DeviceModelVO almacenados en la persistencia
	 * @return Lista con los DeviceModelVO existentes, una lista vacia en caso que no existan DeviceModelVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DeviceModelVO> getAllDeviceModels() throws BusinessException;

}
