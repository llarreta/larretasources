package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ServiceAreWarrantyVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ServiceAreWarranty.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ServiceAreWarrantyBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ServiceAreWarrantyVO
	 * @param obj objeto que encapsula la información de un ServiceAreWarrantyVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createServiceAreWarranty(ServiceAreWarrantyVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ServiceAreWarrantyVO
	 * @param obj objeto que encapsula la información de un ServiceAreWarrantyVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateServiceAreWarranty(ServiceAreWarrantyVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ServiceAreWarrantyVO
	 * @param obj información del ServiceAreWarrantyVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteServiceAreWarranty(ServiceAreWarrantyVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ServiceAreWarrantyVO por su identificador
	 * @param id identificador del ServiceAreWarrantyVO a ser consultado
	 * @return objeto con la información del ServiceAreWarrantyVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ServiceAreWarrantyVO getServiceAreWarrantyByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ServiceAreWarrantyVO almacenados en la persistencia
	 * @return Lista con los ServiceAreWarrantyVO existentes, una lista vacia en caso que no existan ServiceAreWarrantyVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ServiceAreWarrantyVO> getAllServiceAreWarrantys() throws BusinessException;

	/**
	 * Metodo: obtiene una lista que solamente contiene el identificador del Service
	 * y el de ServiceTypeWarranty, buscando en las entidades ServiceAreWarranty por el
	 * identificador del serviceTypeWarranty
	 * @param serviceTypeWarrantyId identificador por el que se realiza la búsqueda
	 * @return Listado de ServiceAreWarranty que coincide con el criterio de búsqueda, pero que
	 * no contiene la estructura de información completa
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public List<ServiceAreWarrantyVO> getServiceAreWarrantiesByServiceTypeWarrantyId(Long serviceTypeWarrantyId) throws BusinessException;

	/**
	 * Metodo: actualiza la configuración globlal de ServiceAreWarranty para un ServiceTypeWarranty específico.
	 * La configuración que se pasa como parámetro debe ser completa, ya que no se conservan configuraciones antigüas.
	 * @param serviceTypeWarrantyId identificador del ServiceTypeWarranty
	 * @param serviceAreWarranties listado de toda la configuración a guardar para el serviceTypeWarranty especificado 
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public void updateServiceAreWarrantiesConfiguration(Long serviceTypeWarrantyId,
			List<ServiceAreWarrantyVO> serviceAreWarranties) throws BusinessException;
	
}
