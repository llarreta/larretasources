package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerConfigurationVO;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad DealerConfiguration.
 * 
 * Fecha de Creación: Mi 18, 2013
 * @author ialessan
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerConfigurationBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto DealerConfigurationVO
	 * @param obj objeto que encapsula la información de un DealerConfigurationVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerConfiguration(DealerConfigurationVO obj) throws BusinessException;
	
	/**
	 * Metodo: Obtiene una configuracion de dealer particular dada por los parametros.
	 * @param dealerCode: codigo del dealer de IBS.
	 * @param depotCode: codigo de depot.
	 * @param customerCategoryCode: codigo de categoria de cliente.
	 * @param businessAreaCode: codigo de area de negocio.
	 * @param countryId: id de pais.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */	
	public DealerConfigurationVO getDealerConfigurationBy(Long dealerCode, String depotCode, 
			String customerCategoryCode, String businessAreaCode) throws BusinessException;

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
	public List<DealerVO> getDealerFromDealerByCustomerType(Long customerCategoryId, Long businesAreaId, Long customerClassTypeId, 
    		Long serviceCategoryId, Long postalCodeId, Long countryCode) throws BusinessException;
	
	
	/**
	 * release_4.2.1.5_Spira_28780 - Configuracion Masiva de Cobertura  de  La  compañía  por  Clase de Cliente
	 * ialessan
	 * marzo 2014
	 * 
	 * @param dealerId
	 * @param customerCategoryId
	 * @param businessAreaId
	 * @return
	 * @throws BusinessException
	 */
	public DealerConfigurationVO getDealerConfigurationByDealerIdAreaIdCustomerCategoryId (Long dealerId, Long customerCategoryId, Long businessAreaId)throws BusinessException;
	

}
