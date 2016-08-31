package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerServiceSubCategoryVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ServiceTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad DealerServiceSubCategory.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerServiceSubCategoryBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto DealerServiceSubCategoryVO
	 * @param obj objeto que encapsula la información de un DealerServiceSubCategoryVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerServiceSubCategory(DealerServiceSubCategoryVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un DealerServiceSubCategoryVO
	 * @param obj objeto que encapsula la información de un DealerServiceSubCategoryVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerServiceSubCategory(DealerServiceSubCategoryVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DealerServiceSubCategoryVO
	 * @param obj información del DealerServiceSubCategoryVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDealerServiceSubCategory(DealerServiceSubCategoryVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un DealerServiceSubCategoryVO por su identificador
	 * @param id identificador del DealerServiceSubCategoryVO a ser consultado
	 * @return objeto con la información del DealerServiceSubCategoryVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DealerServiceSubCategoryVO getDealerServiceSubCategoryByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerServiceSubCategoryVO almacenados en la persistencia
	 * @return Lista con los DealerServiceSubCategoryVO existentes, una lista vacia en caso que no existan DealerServiceSubCategoryVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DealerServiceSubCategoryVO> getAllDealerServiceSubCategorys() throws BusinessException;

	/**
	 * Metodo: Obtiene las compañías que atienden una categoría de servicio de un servicio asociado
	 * @param countryCode código del país
	 * @param serviceCode código del servicio
	 * @return Lista de compañías que atienden la categoría del servicio especificado
	 * @throws BusinessException en caso de error en la consulta
	 * @author
	 */
	public List<DealerVO> getDealersWhoAttendServiceCategory(String countryCode, String serviceCode)throws BusinessException;
	
	/**
	 * Metodo: Obtiene las compañías que atienden una subcategoría de servicio de un servicio asociado
	 * @param countryCode código del país
	 * @param serviceCode código del servicio
	 * @return Lista de compañías que atienden la categoría del servicio especificado
	 * @throws BusinessException en caso de error en la consulta
	 * @author
	 */
	public List<DealerVO> getDealersWhoAttendServiceSubCategory(String countryCode, String serviceCode) throws BusinessException;

	/**
	 * Metodo: Obtiene la configuración completa de todos los DealerServiceSubCategory existentes
	 * para un dealer específico. Esto significa que si un DealerServiceSubCategory no se ha configurado para
	 * una categoría específica, también se retornan estos registros para que se les agregue la configuración
	 * y se puedam persistir.
	 * @param dealerId identificador del dealer por el que se realizará la búsqueda
	 * @param countryId
	 * @return List<DealerServiceSubCategory> Lista con los DealerServiceSubCategory correspondientes. 
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public List<DealerServiceSubCategoryVO> getAllConfigurationActiveByDealerIdAndCountryId(Long dealerId, Long countryId) throws BusinessException;

	/**
     * Metodo: Obtiene el arbol completo de configuraciones de DealerServiceSubCategory para un Dealer
     * en un país específico, empezando por la raiz que es de tipo ServiceTypeVO
     * @param dealerId
     * @param countryId
     * @return List<ServiceTypeVO> estructura lista de ServiceTypeVO incluyendo
     * el listado internamente de DealerServiceSubCategoryVO
     * @throws BusinessException en caso de error al tratar de ejecutar la operacion
     * @author wjimenez
     */
	public List<ServiceTypeVO> getDealerServiceSubCategoriesTree(Long dealerId, Long countryId) throws BusinessException;
	
	/**
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * 
     * Metodo: Obtiene las configuraciones de ServiceTypesConfiguration para un Dealer y Arean de Negocio
     * @param dealerId
     * @param countryId
     * @return List<ServiceTypeVO> estructura lista de ServiceTypeVO 
     * @throws BusinessException en caso de error al tratar de ejecutar la operacion
     * @author ialessan
     */
	public List<ServiceTypeVO> getDealerServiceSubCategoriesTreeByBusinessAreaId(	Long dealerId, 
																					Long countryId, 																					
																					Long businessAreaId,
																					Long customerCategoryId) throws BusinessException;	
}
