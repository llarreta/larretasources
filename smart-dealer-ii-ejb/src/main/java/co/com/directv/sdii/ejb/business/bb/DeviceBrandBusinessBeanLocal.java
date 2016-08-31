package co.com.directv.sdii.ejb.business.bb;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DeviceBrandVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad DeviceBrand.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DeviceBrandBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto DeviceBrandVO
	 * @param obj objeto que encapsula la información de un DeviceBrandVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDeviceBrand(DeviceBrandVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un DeviceBrandVO
	 * @param obj objeto que encapsula la información de un DeviceBrandVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDeviceBrand(DeviceBrandVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DeviceBrandVO
	 * @param obj información del DeviceBrandVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDeviceBrand(DeviceBrandVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un DeviceBrandVO por su identificador
	 * @param id identificador del DeviceBrandVO a ser consultado
	 * @return objeto con la información del DeviceBrandVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DeviceBrandVO getDeviceBrandByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los DeviceBrandVO almacenados en la persistencia
	 * @return Lista con los DeviceBrandVO existentes, una lista vacia en caso que no existan DeviceBrandVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DeviceBrandVO> getAllDeviceBrands() throws BusinessException;

}
