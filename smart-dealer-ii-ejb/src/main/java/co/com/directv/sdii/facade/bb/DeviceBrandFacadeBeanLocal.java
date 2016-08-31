package co.com.directv.sdii.facade.bb;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DeviceBrandVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad DeviceBrand.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DeviceBrandFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto DeviceBrand
	 * @param obj - DeviceBrandVO  objeto que encapsula la información de un DeviceBrandVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDeviceBrand(DeviceBrandVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un DeviceBrand
	 * @param obj - DeviceBrandVO  objeto que encapsula la información de un DeviceBrandVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDeviceBrand(DeviceBrandVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un DeviceBrand
	 * @param obj - DeviceBrandVO  información del DeviceBrandVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteDeviceBrand(DeviceBrandVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un DeviceBrand por su identificador
	 * @param id - Long identificador del DeviceBrand a ser consultado
	 * @return objeto con la información del DeviceBrandVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public DeviceBrandVO getDeviceBrandByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los DeviceBrand almacenados en la persistencia
	 * @return List<DeviceBrandVO> Lista con los DeviceBrandVO existentes, una lista vacia en caso que no existan DeviceBrandVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<DeviceBrandVO> getAllDeviceBrands() throws BusinessException;

}
