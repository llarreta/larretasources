package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerServiceSubCategoryWithPcVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ServiceTypeVO;
import co.com.directv.sdii.reports.dto.DealerServiceSubCategoryWithPcDTO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad DealerServiceSubCategoryWithPc.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerServiceSubCategoryWithPcBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto DealerServiceSubCategoryWithPcVO
	 * @param obj objeto que encapsula la información de un DealerServiceSubCategoryWithPcVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerServiceSubCategoryWithPc(DealerServiceSubCategoryWithPcVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un DealerServiceSubCategoryWithPcVO
	 * @param obj objeto que encapsula la información de un DealerServiceSubCategoryWithPcVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerServiceSubCategoryWithPc(DealerServiceSubCategoryWithPcVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DealerServiceSubCategoryWithPcVO
	 * @param obj información del DealerServiceSubCategoryWithPcVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDealerServiceSubCategoryWithPc(DealerServiceSubCategoryWithPcVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un DealerServiceSubCategoryWithPcVO por su identificador
	 * @param id identificador del DealerServiceSubCategoryWithPcVO a ser consultado
	 * @return objeto con la información del DealerServiceSubCategoryWithPcVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DealerServiceSubCategoryWithPcVO getDealerServiceSubCategoryWithPcByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerServiceSubCategoryWithPcVO almacenados en la persistencia
	 * @return Lista con los DealerServiceSubCategoryWithPcVO existentes, una lista vacia en caso que no existan DealerServiceSubCategoryWithPcVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DealerServiceSubCategoryWithPcVO> getAllDealerServiceSubCategoryWithPcs() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los Dealer que atienden la sucategoria del servicio 'serviceCode' en la zona 
	 * postal cuyo codigo postal es 'postalCode' del pais con codigo 'countryCode'
	 * @return Lista con los DealerVO que cumplan el proposito de este metodo 
	 * @throws BusinessException en caso de error al tratar de ejecutar la operacion
	 * @author
	 */
    public List<DealerVO> getDealersWhoAttendServiceSubCategoryWithCoverage(String countryCode, String serviceCode, String postalCode) throws BusinessException;
    
    
    /**
     * Método: Permite consultar la información de DealerServiceSubCategoryWithPcDTO por país
     * @param idCountry - Long identificador del país
     * @return List<DealerServiceSubCategoryWithPcDTO>
     * @throws BusinessException en caso de error en la consulta
     * @author gfandino
     */
    public List<DealerServiceSubCategoryWithPcDTO> getDealerServSubCatPcByCountryAndActive(Long idCountry)throws BusinessException;

    /**
     * Metodo: Obtiene el arbol completo de configuraciones de DealerServiceSubCategoryWithPc para un DealerCoverage
     * específico, empezando por la raiz que es de tipo ServiceTypeVO
     * @param dealerCoverageId
     * @return List<ServiceTypeVO>
     * @throws BusinessException en caso de error al tratar de ejecutar la operacion
     * @author wjimenez
     */
	public List<ServiceTypeVO> getDealerServiceSubCategoriesWithPcTree(Long dealerCoverageId) throws BusinessException;

	/**
	 * Metodo: persiste los cambios a un grupo de configuraciones de DealerServiceSubCategoriesWithPc
	 * La actualización hace parte de una sola transacción, es decir, o se actualizan todos los elementos
	 * o no se actualiza ninguno
	 * @param dealerServiceSubCategoriesWithPc
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public void updateDealerServiceSubCategoriesWithPcConfiguration(
			List<DealerServiceSubCategoryWithPcVO> dealerServiceSubCategoriesWithPc) throws BusinessException;

	/**
	 * Metodo: obtiene el DealerServiceSubCategoryWithPcVO correspondiente a una cobertura y
	 * categoría de servicio específica
	 * @param dealerCoverageId
	 * @param serviceCategoryId
	 * @return
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public DealerServiceSubCategoryWithPcVO getByDealerCoverageIdAndServiceCategotyId(
			Long dealerCoverageId, Long serviceCategoryId) throws BusinessException;

}
