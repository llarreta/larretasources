package co.com.directv.sdii.ejb.business.bb;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.MobileDeviceVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad MobileDevice.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MobileDeviceBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto MobileDeviceVO
	 * @param obj objeto que encapsula la información de un MobileDeviceVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createMobileDevice(MobileDeviceVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un MobileDeviceVO
	 * @param obj objeto que encapsula la información de un MobileDeviceVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateMobileDevice(MobileDeviceVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un MobileDeviceVO
	 * @param obj información del MobileDeviceVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteMobileDevice(MobileDeviceVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un MobileDeviceVO por su identificador
	 * @param id identificador del MobileDeviceVO a ser consultado
	 * @return objeto con la información del MobileDeviceVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public MobileDeviceVO getMobileDeviceByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los MobileDeviceVO almacenados en la persistencia
	 * @return Lista con los MobileDeviceVO existentes, una lista vacia en caso que no existan MobileDeviceVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<MobileDeviceVO> getAllMobileDevices() throws BusinessException;

}
