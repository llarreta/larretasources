package co.com.directv.sdii.ejb.business.bb;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ServiceProviderVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ServiceProvider.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ServiceProviderBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ServiceProviderVO
	 * @param obj objeto que encapsula la información de un ServiceProviderVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createServiceProvider(ServiceProviderVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ServiceProviderVO
	 * @param obj objeto que encapsula la información de un ServiceProviderVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateServiceProvider(ServiceProviderVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ServiceProviderVO
	 * @param obj información del ServiceProviderVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteServiceProvider(ServiceProviderVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ServiceProviderVO por su identificador
	 * @param id identificador del ServiceProviderVO a ser consultado
	 * @return objeto con la información del ServiceProviderVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ServiceProviderVO getServiceProviderByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ServiceProviderVO almacenados en la persistencia
	 * @return Lista con los ServiceProviderVO existentes, una lista vacia en caso que no existan ServiceProviderVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ServiceProviderVO> getAllServiceProviders() throws BusinessException;

}
