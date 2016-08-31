package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ServiceTypeWarrantyVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ServiceTypeWarranty.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ServiceTypeWarrantyFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ServiceTypeWarranty
	 * @param obj - ServiceTypeWarrantyVO  objeto que encapsula la información de un ServiceTypeWarrantyVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createServiceTypeWarranty(ServiceTypeWarrantyVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ServiceTypeWarranty
	 * @param obj - ServiceTypeWarrantyVO  objeto que encapsula la información de un ServiceTypeWarrantyVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateServiceTypeWarranty(ServiceTypeWarrantyVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ServiceTypeWarranty
	 * @param obj - ServiceTypeWarrantyVO  información del ServiceTypeWarrantyVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteServiceTypeWarranty(ServiceTypeWarrantyVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ServiceTypeWarranty por su identificador
	 * @param id - Long identificador del ServiceTypeWarranty a ser consultado
	 * @return objeto con la información del ServiceTypeWarrantyVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public ServiceTypeWarrantyVO getServiceTypeWarrantyByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ServiceTypeWarranty almacenados en la persistencia
	 * @return List<ServiceTypeWarrantyVO> Lista con los ServiceTypeWarrantyVO existentes, una lista vacia en caso que no existan ServiceTypeWarrantyVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<ServiceTypeWarrantyVO> getAllServiceTypeWarrantys() throws BusinessException;
	
	/**
	 * Metodo: obtiene la configuración de garantías para todos los tipos de servicio existentes
	 * en un país específico
	 * @param countryId identificador del país
	 * @return List<ServiceTypeWarranty> listado de garantías. Si un tipo de servicio no tiene garantía configurada,
	 * se incluye un ítem en la lista con el país y tipo de servicio asignados. 
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public List<ServiceTypeWarrantyVO> getServiceTypeWarrantiesConfigurationByCountryId(Long countryId) throws BusinessException;

	/**
	 * Metodo: actualiza o crea las parametrizaciones de garantías por tipo de servicio
	 * @param serviceTypeWarranties listado con las garantías que se desean crear o actualizar
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public void updateServiceTypeWarrantiesConfiguration(List<ServiceTypeWarrantyVO> serviceTypeWarranties) throws BusinessException;

}
