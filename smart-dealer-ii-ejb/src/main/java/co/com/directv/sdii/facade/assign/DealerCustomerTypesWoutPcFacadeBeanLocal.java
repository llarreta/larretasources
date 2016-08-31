package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerCustomerTypesWoutPcVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad DealerCustomerTypesWoutPc.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerCustomerTypesWoutPcFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto DealerCustomerTypesWoutPc
	 * @param obj - DealerCustomerTypesWoutPcVO  objeto que encapsula la información de un DealerCustomerTypesWoutPcVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerCustomerTypesWoutPc(DealerCustomerTypesWoutPcVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un DealerCustomerTypesWoutPc
	 * @param obj - DealerCustomerTypesWoutPcVO  objeto que encapsula la información de un DealerCustomerTypesWoutPcVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerCustomerTypesWoutPc(DealerCustomerTypesWoutPcVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un DealerCustomerTypesWoutPc
	 * @param obj - DealerCustomerTypesWoutPcVO  información del DealerCustomerTypesWoutPcVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteDealerCustomerTypesWoutPc(DealerCustomerTypesWoutPcVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un DealerCustomerTypesWoutPc por su identificador
	 * @param id - Long identificador del DealerCustomerTypesWoutPc a ser consultado
	 * @return objeto con la información del DealerCustomerTypesWoutPcVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public DealerCustomerTypesWoutPcVO getDealerCustomerTypesWoutPcByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los DealerCustomerTypesWoutPc almacenados en la persistencia
	 * @return List<DealerCustomerTypesWoutPcVO> Lista con los DealerCustomerTypesWoutPcVO existentes, una lista vacia en caso que no existan DealerCustomerTypesWoutPcVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<DealerCustomerTypesWoutPcVO> getAllDealerCustomerTypesWoutPcs() throws BusinessException;

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
	 * @param customerCategoryId identificador de categoria de client por la que se realizará la busqueda
	 * @param countryId 
	 * @return List<DealerCustomerTypesWoutPc> Lista con los DealerCustomerTypesWoutPc correspondientes,
	 * una lista vacia en caso que no se encuentren registros 
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author ialessan
	 */
	public List<DealerCustomerTypesWoutPcVO> getAllDealerCustomerTypesByCustomerCategoryId(	Long dealerId, 
																							Long countryId, 
																							Long customerCategoryId,
																							Long businessAreaId) 
	throws BusinessException;

}
