package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ServiceSuperCategoryVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ServiceSuperCategory.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ServiceSuperCategoryFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ServiceSuperCategory
	 * @param obj - ServiceSuperCategoryVO  objeto que encapsula la información de un ServiceSuperCategoryVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createServiceSuperCategory(ServiceSuperCategoryVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ServiceSuperCategory
	 * @param obj - ServiceSuperCategoryVO  objeto que encapsula la información de un ServiceSuperCategoryVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateServiceSuperCategory(ServiceSuperCategoryVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ServiceSuperCategory
	 * @param obj - ServiceSuperCategoryVO  información del ServiceSuperCategoryVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteServiceSuperCategory(ServiceSuperCategoryVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ServiceSuperCategory por su identificador
	 * @param id - Long identificador del ServiceSuperCategory a ser consultado
	 * @return objeto con la información del ServiceSuperCategoryVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public ServiceSuperCategoryVO getServiceSuperCategoryByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ServiceSuperCategory almacenados en la persistencia
	 * @return List<ServiceSuperCategoryVO> Lista con los ServiceSuperCategoryVO existentes, una lista vacia en caso que no existan ServiceSuperCategoryVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<ServiceSuperCategoryVO> getAllServiceSuperCategorys() throws BusinessException;

}
