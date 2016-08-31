package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerCustomerTypesWoutPcVO;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad DealerCustomerTypesWoutPc.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerCustomerTypesWoutPcBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto DealerCustomerTypesWoutPcVO
	 * @param obj objeto que encapsula la información de un DealerCustomerTypesWoutPcVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerCustomerTypesWoutPc(DealerCustomerTypesWoutPcVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un DealerCustomerTypesWoutPcVO
	 * @param obj objeto que encapsula la información de un DealerCustomerTypesWoutPcVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerCustomerTypesWoutPc(DealerCustomerTypesWoutPcVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DealerCustomerTypesWoutPcVO
	 * @param obj información del DealerCustomerTypesWoutPcVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDealerCustomerTypesWoutPc(DealerCustomerTypesWoutPcVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un DealerCustomerTypesWoutPcVO por su identificador
	 * @param id identificador del DealerCustomerTypesWoutPcVO a ser consultado
	 * @return objeto con la información del DealerCustomerTypesWoutPcVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DealerCustomerTypesWoutPcVO getDealerCustomerTypesWoutPcByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerCustomerTypesWoutPcVO almacenados en la persistencia
	 * @return Lista con los DealerCustomerTypesWoutPcVO existentes, una lista vacia en caso que no existan DealerCustomerTypesWoutPcVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DealerCustomerTypesWoutPcVO> getAllDealerCustomerTypesWoutPcs() throws BusinessException;

	/**
	 * Metodo: A partir de los filtros, obtiene las compañías que atienden el tipo de cliente
	 * @param countryCode código del país
	 * @param customerTypeCode código del tipo de cliente
	 * @return Lista de compañías que atienden el tipo de cliente
	 * @author waltuzarra
	 */
	public List<DealerVO> getDealersWhoAttendCustomerTypeWoutCoverage(String countryCode, Long customerTypeClassId)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerCustomerTypesWoutPc existentes
	 * para un dealer específico
	 * @param dealerId identificador del dealer por el que se realizará la búsqueda
	 * @param countryId 
	 * @return List<DealerCustomerTypesWoutPc> Lista con los DealerCustomerTypesWoutPc correspondientes,
	 * una lista vacia en caso que no se encuentren registros 
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public List<DealerCustomerTypesWoutPcVO> getAllDealerCustomerTypesWoutPcConfigurationByDealerIdAndCountryId(Long dealerId, Long countryId) throws BusinessException;

	
	/**
	 * 
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * 
	 * Nueva Pantalla de Configuracion de Dealers por Tipo de Cliente Carga solapa del acordeon  “Tipos de cliente”
	 * 
	 * Metodo: Obtiene la información de CustomerTypeConfiguration 
	 * para un CustomerCategory específico
	 * @param countryId 
	 * @param dealerId
	 * @param customerCategoryId identificador de la categoria de cliente por la que se realizara la busqueda 
	 * @return List<DealerCustomerTypesWoutPc> Lista con los DealerCustomerTypesWoutPc correspondientes,
	 * una lista vacia en caso que no se encuentren registros 
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author ialessan
	 */	

	public List<DealerCustomerTypesWoutPcVO>getAllDealerCustomerTypesByCustomerCategoryId(	Long dealerId, 
																							Long countryId, 
																							Long customerCategoryId,
																							Long businessAreaId) 
    throws BusinessException;
	
}
