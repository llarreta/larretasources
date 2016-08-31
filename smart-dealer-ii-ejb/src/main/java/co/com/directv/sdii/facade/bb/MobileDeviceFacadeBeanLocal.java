package co.com.directv.sdii.facade.bb;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.MobileDeviceVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad MobileDevice.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MobileDeviceFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto MobileDevice
	 * @param obj - MobileDeviceVO  objeto que encapsula la información de un MobileDeviceVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createMobileDevice(MobileDeviceVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un MobileDevice
	 * @param obj - MobileDeviceVO  objeto que encapsula la información de un MobileDeviceVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateMobileDevice(MobileDeviceVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un MobileDevice
	 * @param obj - MobileDeviceVO  información del MobileDeviceVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteMobileDevice(MobileDeviceVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un MobileDevice por su identificador
	 * @param id - Long identificador del MobileDevice a ser consultado
	 * @return objeto con la información del MobileDeviceVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public MobileDeviceVO getMobileDeviceByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los MobileDevice almacenados en la persistencia
	 * @return List<MobileDeviceVO> Lista con los MobileDeviceVO existentes, una lista vacia en caso que no existan MobileDeviceVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<MobileDeviceVO> getAllMobileDevices() throws BusinessException;

}
