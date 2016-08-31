package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.DealerCoverageResponse;
import co.com.directv.sdii.model.pojo.collection.PostalCodeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.reports.dto.DealerConfCoverageDTO;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad DealerConfCoverage.
 * 
 * Fecha de Creación: oct 04, 2013
 * @author ialessan
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerConfCoverageFacadeBeanLocal {
	/**
	 * Metodo: Obtiene todos los DealerConfCoverage del sistema correspondientes a un dealer categoria de cliente y area de negocio específicos
	 * @param dealerId identificador del Dealer
	 * @param customerCategoryId identificador de la Categoría de Cliente
	 * @param businessAreaId identificador del Area de Negocio
	 * @return DealerCoverageResponse listado de DealerCoverage activos
	 * @throws BusinessException en caso de un error en la ejecución de la operación
	 * @author ialessan
	 */
	public DealerCoverageResponse getDealerConfCoverageByDealerIdCustomerCategoryIdAreaId(	Long dealerId,
																							Long cityId,
																							RequestCollectionInfo requestCollectionInfo,
																							Long  customerCategoryId ,
																							Long  businessAreaId
    ) throws BusinessException;
	
	/**
	 * Metodo: Trae los códigos postales de una ciudad en los que un dealer no tiene configuracion de covertura 
	 * @param dealerId identificador del dealer
	 * @param cityId identificador de la ciudad
	 * @param requestCollectionInfo información de paginación
	 * @return PostalCodeResponse respuesta paginada
	 * @throws BusinessException en caso de un error en la ejecución de la operación
	 * @author ssanabri
	 */
	public PostalCodeResponse getPostalCodesWithoutConfCoverageByDealerIdAndCityId(Long dealerId, Long cityId,
			RequestCollectionInfo requestCollectionInfo, Long customerCategoryId, Long businessAreaId) throws BusinessException;
	
	/**
	 * Método: Consulta los DealerConfCoverage que se encuentran en estado activo para el país determinado
	 * @return List<DealerConfCoverageDTO> con los DealerConfCoverage activos
	 * @throws BusinessException en caso de error en la consulta
	 * @author ssanabri
	 */
	public List<DealerConfCoverageDTO> getAllDealerConfCoverageByCountryAndActiveStatus(Long countryId) throws BusinessException;
}
