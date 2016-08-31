package co.com.directv.sdii.facade.bb;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DeviceModelVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad DeviceModel.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DeviceModelFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto DeviceModel
	 * @param obj - DeviceModelVO  objeto que encapsula la información de un DeviceModelVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDeviceModel(DeviceModelVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un DeviceModel
	 * @param obj - DeviceModelVO  objeto que encapsula la información de un DeviceModelVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDeviceModel(DeviceModelVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un DeviceModel
	 * @param obj - DeviceModelVO  información del DeviceModelVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteDeviceModel(DeviceModelVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un DeviceModel por su identificador
	 * @param id - Long identificador del DeviceModel a ser consultado
	 * @return objeto con la información del DeviceModelVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public DeviceModelVO getDeviceModelByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los DeviceModel almacenados en la persistencia
	 * @return List<DeviceModelVO> Lista con los DeviceModelVO existentes, una lista vacia en caso que no existan DeviceModelVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<DeviceModelVO> getAllDeviceModels() throws BusinessException;

}
