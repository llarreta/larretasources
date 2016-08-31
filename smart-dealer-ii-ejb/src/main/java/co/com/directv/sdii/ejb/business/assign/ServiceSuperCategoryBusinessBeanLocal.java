package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ServiceSuperCategoryVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ServiceSuperCategory.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ServiceSuperCategoryBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ServiceSuperCategoryVO
	 * @param obj objeto que encapsula la información de un ServiceSuperCategoryVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createServiceSuperCategory(ServiceSuperCategoryVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ServiceSuperCategoryVO
	 * @param obj objeto que encapsula la información de un ServiceSuperCategoryVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateServiceSuperCategory(ServiceSuperCategoryVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ServiceSuperCategoryVO
	 * @param obj información del ServiceSuperCategoryVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteServiceSuperCategory(ServiceSuperCategoryVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ServiceSuperCategoryVO por su identificador
	 * @param id identificador del ServiceSuperCategoryVO a ser consultado
	 * @return objeto con la información del ServiceSuperCategoryVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ServiceSuperCategoryVO getServiceSuperCategoryByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ServiceSuperCategoryVO almacenados en la persistencia
	 * @return Lista con los ServiceSuperCategoryVO existentes, una lista vacia en caso que no existan ServiceSuperCategoryVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ServiceSuperCategoryVO> getAllServiceSuperCategorys() throws BusinessException;

	/**
	 * Metodo: Obtiene la super categoría de servicio por el código de servicio especificado
	 * @param serviceCode código del servicio al cual se le buscará la super categoría de servicio
	 * @return super categoría de servicio, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public ServiceSuperCategoryVO getServiceSuperCategoryByServiceCode(String serviceCode)throws BusinessException;

}
