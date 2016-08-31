package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ServiceAreWarrantyVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ServiceAreWarranty.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ServiceAreWarrantyFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ServiceAreWarranty
	 * @param obj - ServiceAreWarrantyVO  objeto que encapsula la información de un ServiceAreWarrantyVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createServiceAreWarranty(ServiceAreWarrantyVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ServiceAreWarranty
	 * @param obj - ServiceAreWarrantyVO  objeto que encapsula la información de un ServiceAreWarrantyVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateServiceAreWarranty(ServiceAreWarrantyVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ServiceAreWarranty
	 * @param obj - ServiceAreWarrantyVO  información del ServiceAreWarrantyVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteServiceAreWarranty(ServiceAreWarrantyVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ServiceAreWarranty por su identificador
	 * @param id - Long identificador del ServiceAreWarranty a ser consultado
	 * @return objeto con la información del ServiceAreWarrantyVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public ServiceAreWarrantyVO getServiceAreWarrantyByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ServiceAreWarranty almacenados en la persistencia
	 * @return List<ServiceAreWarrantyVO> Lista con los ServiceAreWarrantyVO existentes, una lista vacia en caso que no existan ServiceAreWarrantyVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
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
