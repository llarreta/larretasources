package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerServiceSubCategoryWithPcVO;
import co.com.directv.sdii.model.vo.ServiceTypeVO;
import co.com.directv.sdii.reports.dto.DealerServiceSubCategoryWithPcDTO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad DealerServiceSubCategoryWithPc.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerServiceSubCategoryWithPcFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto DealerServiceSubCategoryWithPc
	 * @param obj - DealerServiceSubCategoryWithPcVO  objeto que encapsula la información de un DealerServiceSubCategoryWithPcVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerServiceSubCategoryWithPc(DealerServiceSubCategoryWithPcVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un DealerServiceSubCategoryWithPc
	 * @param obj - DealerServiceSubCategoryWithPcVO  objeto que encapsula la información de un DealerServiceSubCategoryWithPcVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerServiceSubCategoryWithPc(DealerServiceSubCategoryWithPcVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un DealerServiceSubCategoryWithPc
	 * @param obj - DealerServiceSubCategoryWithPcVO  información del DealerServiceSubCategoryWithPcVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteDealerServiceSubCategoryWithPc(DealerServiceSubCategoryWithPcVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un DealerServiceSubCategoryWithPc por su identificador
	 * @param id - Long identificador del DealerServiceSubCategoryWithPc a ser consultado
	 * @return objeto con la información del DealerServiceSubCategoryWithPcVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public DealerServiceSubCategoryWithPcVO getDealerServiceSubCategoryWithPcByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los DealerServiceSubCategoryWithPc almacenados en la persistencia
	 * @return List<DealerServiceSubCategoryWithPcVO> Lista con los DealerServiceSubCategoryWithPcVO existentes, una lista vacia en caso que no existan DealerServiceSubCategoryWithPcVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<DealerServiceSubCategoryWithPcVO> getAllDealerServiceSubCategoryWithPcs() throws BusinessException;
	
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
	public void updateDealerServiceSubCategoriesWithPcConfiguration(List<DealerServiceSubCategoryWithPcVO> dealerServiceSubCategoriesWithPc) throws BusinessException;
	
}
