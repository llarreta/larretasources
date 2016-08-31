package co.com.directv.sdii.facade.bb;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ServiceProviderVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ServiceProvider.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ServiceProviderFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ServiceProvider
	 * @param obj - ServiceProviderVO  objeto que encapsula la información de un ServiceProviderVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createServiceProvider(ServiceProviderVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ServiceProvider
	 * @param obj - ServiceProviderVO  objeto que encapsula la información de un ServiceProviderVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateServiceProvider(ServiceProviderVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ServiceProvider
	 * @param obj - ServiceProviderVO  información del ServiceProviderVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteServiceProvider(ServiceProviderVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ServiceProvider por su identificador
	 * @param id - Long identificador del ServiceProvider a ser consultado
	 * @return objeto con la información del ServiceProviderVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public ServiceProviderVO getServiceProviderByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ServiceProvider almacenados en la persistencia
	 * @return List<ServiceProviderVO> Lista con los ServiceProviderVO existentes, una lista vacia en caso que no existan ServiceProviderVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<ServiceProviderVO> getAllServiceProviders() throws BusinessException;

}
