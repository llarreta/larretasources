package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.DealerCoverageResponse;
import co.com.directv.sdii.model.pojo.collection.PostalCodeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.DealerConfCoverageVO;
import co.com.directv.sdii.reports.dto.DealerConfCoverageDTO;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad DealerConfCoverage.
 * 
 * Fecha de Creacion: Vier 4, 2013
 * @author ialessan
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerConfCoverageBusinessBeanLocal {
	
	/**
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * 
	 * Metodo: Obtiene todos los DealerConfCoverage del sistema correspondientes a un dealer especifico
	 * @param dealerId identificador del Dealer
	 * @param customerCategoryId identificador de la Categoria de Cliente
	 * @param businessAreaId identificador del Area de Negocio
	 * @return DealerCoverageResponse listado de DealerCoverage activos
	 * @throws BusinessException en caso de un error en la ejecucion de la operacion
	 * @author wjimenez
     *
	 * 
     * Spira 28731 – Quitar filtros de Departamento y Ciudad al “Cargar Configuración” – solapa de cobertura
     * modificación
     * ialessan
     * marzo 2014
	 * 
	 */
	public DealerCoverageResponse getDealerConfCoverageByDealerIdCustomerCategoryIdAreaId(	Long dealerId, 
																							RequestCollectionInfo requestCollectionInfo, 
																							Long customerCategoryId, 
																							Long businessAreaId
     ) throws BusinessException;
	
	/**
	 * Metodo: Trae los códigos postales de una ciudad en los que un dealer no tiene configuracion de covertura 
	 * @param dealerId identificador del dealer
	 * @param cityId identificador de la ciudad
	 * @param requestCollectionInfo informaciÃ³n de paginaciÃ³n
	 * @return PostalCodeResponse respuesta paginada
	 * @throws BusinessException en caso de un error en la ejecuciÃ³n de la operaciÃ³n
	 * @author ssanabri
	 */
	public PostalCodeResponse getPostalCodesWithoutConfCoverageByDealerIdAndCityId(Long dealerId, Long cityId,
			RequestCollectionInfo requestCollectionInfo, Long customerCategoryId, Long businessAreaId) throws BusinessException;

	/**
	 * Busca los DealerConfCoverage que correspondan a los parametros.
	 * @param dealerConfigurationId: id de configuracion de dealer
	 * @param dealerId: id de dealer
	 * @param postalCodeId: id de codigo postal.
	 * @return
	 * @throws BusinessException
	 */
	public DealerConfCoverageVO getDealerConfCoverageBy(Long dealerConfigurationId, Long dealerId, Long postalCodeId) 
			throws BusinessException;

	/**
	 * Metodo: actualiza la informaciÃ³n de un DealerConfCoverageVO
	 * @param obj objeto que encapsula la informaciÃ³n de un DealerConfCoverageVO
	 * @throws BusinessException en caso de error al ejecutar la operaciÃ³n
	 * @author
	 */
	public void updateDealerConfCoverage(DealerConfCoverageVO dealerConfCoverageVO) throws BusinessException;
		
	/**
	 * Método: Consulta los DealerConfCoverage que se encuentran en estado activo para el país determinado
	 * @return List<DealerConfCoverageDTO> con los DealerCoverage activos
	 * @throws BusinessException en caso de error en la consulta
	 * @author ssanabri
	 */
	public List<DealerConfCoverageDTO> getAllDealerConfCoverageByCountryAndActiveStatus(Long countryID) throws BusinessException;
	
}
