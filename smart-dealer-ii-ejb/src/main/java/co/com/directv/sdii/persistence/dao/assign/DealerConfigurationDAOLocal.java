package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerConfiguration;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad DealerConfiguration
 * 
 * Fecha de Creación: M1 18, 2013
 * @author ialessan
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerConfigurationDAOLocal {

	/**
	 * Metodo: persiste la información de un DealerConfiguration
	 * @param  obj objeto que encapsula la información de un DealerConfiguration
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author ialessan
	 */
	public void createDealerConfiguration(DealerConfiguration obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: obtiene los DealerConfiguration existentes segun el CustomerClassType
	 * @param  customerClasstypeList 
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author ialessan
	 */	
	public List<DealerConfiguration> getDealerConfigurationByCustomerCategoryId(Long dealerId , Long customerCategoryId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: obtiene los DealerConfiguration existentes segun el id
	 * @param  dealerId 
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @author ialessan
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 */	
	public List<DealerConfiguration> getDealerConfigurationById(Long dealerId)throws DAOServiceException, DAOSQLException;
	
	
	//public void deleteDealerConfigurationByDealerId(Long dealerId)throws DAOServiceException, DAOSQLException;
	
	
	public DealerConfiguration getDealerConfigurationByDealerIdAreaIdCustomerCategoryId(Long dealerId,
			                                                                                  Long areaId,
			                                                                                  Long customerCategoryId
			)throws DAOServiceException, DAOSQLException;

	
	public void deleteDealerConfiguration(DealerConfiguration obj) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene una configuracion de dealer particular dada por los parametros.
	 * @param dealerCode: codigo del dealer de IBS.
	 * @param depotCode: codigo de depot.
	 * @param customerCategoryCode: codigo de categoria de cliente.
	 * @param businessAreaCode: codigo de area de negocio.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */	
	public DealerConfiguration getDealerConfigurationBy(Long dealerCode, String depotCode, String customerCategoryCode, 
			String businessAreaCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: <br/>
	 * Busca los Dealers que tienen:<br/>
	 * 	<ul><li> cobertura para el área de negocio de la WO y la categoria de cliente del cliente de la WO. </li>
	 * 	<li> sub categoria de servicio para el área de negocio de la WO. </li>
	 *  <li> tipo de cliente para la categoría de cliente del cliente de la WO. </li></ul>
	 * @param customerCategoryId id de la categoria de negocio
	 * @param businesAreaId id del área de negocio
	 * @param customerCassTypeId id de la relacion entre class y type del customer
	 * @param serviceCategoryId id de la categoria de servicio de la wo
	 * @param postalCodeId id del codigo postal del customer
	 * @param  countryCode código del país
	 * @return Lista de compañías 
	 * @throws BusinessException en caso de error en la consulta
	 * @author ssanabri
	 */
	public List<Dealer> getDealerFromDealerByCustomerType(Long customerCategoryId, Long businesAreaId, Long customerClassTypeId, 
    		Long serviceCategoryId, Long postalCodeId, Long countryCode) throws DAOServiceException, DAOSQLException;
}