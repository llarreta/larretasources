package co.com.directv.sdii.assign.kpi;

import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorConfigDTO;
import co.com.directv.sdii.assign.kpi.dto.KPIConfigCriteria;
import co.com.directv.sdii.assign.kpi.dto.KPIConfiguration;
import co.com.directv.sdii.exceptions.BusinessException;

/**
 * impemenataci�n de la fachada de KPI
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:47
 */
public class KPIFacadeImpl implements KPIFacade {

	private KPICoordinator kpiCoordinator;

	public KPIFacadeImpl(){
		kpiCoordinator = KPICoordinator.getInstance();
	}

	public void finalize() throws Throwable {

	}
	/**
	 * Obtiene el valor de los indicadores para un dealer
	 * 
	 * @param countryId    identificador del pa�s
	 * @param serviceSuperCatgoryId    identificador de la categor�a de servicio
	 * @param dealerId    identificador de la compa��a instaladora
	 * @throws BusinessException 
	 */
	public Double getDealerKPI(Long countryId, Long serviceSuperCatgoryId, Long dealerId, Long zoneTypeId) throws BusinessException{
		Double response = kpiCoordinator.getDealerKPI(countryId, serviceSuperCatgoryId, dealerId, zoneTypeId);
		return response;
	}

	/**
	 * Obtiene el valor de un indicador espec�fico para un dealer espec�fico
	 * 
	 * @param countryId    identificador del pa�s
	 * @param serviceCategoryId    identificador de la categor�a del servicio
	 * @param dealerId    identificador de la compa��a instaladora a la que se le
	 * calcular� el indicador
	 * @param indicatorCode    c�digo del indicador a ser consultado
	 * @throws BusinessException 
	 */
	public Double getDealerKPIByKPICode(Long countryId, Long serviceSuperCatgoryId, Long dealerId, String indicatorCode, Long zoneTypeId) throws BusinessException{
		Double response = kpiCoordinator.getDealerKPIByKPICode(countryId, serviceSuperCatgoryId, zoneTypeId, dealerId, indicatorCode);
		return response;
	}

	/**
	 * carga la configuraci�n de un indicador dados los criterios de b�squeda
	 * 
	 * @param kpiCode    c�digo del indicador a ser consultado
	 * @param serviceSuperCategoryCode    c�digo de la super categor�a de servicio
	 * @param countryCode    c�digo del pa�s
	 * @throws BusinessException 
	 */
	public DealerIndicatorConfigDTO loadKPIConfiguration(String kpiCode, String serviceSuperCategoryCode, String countryCode, String zoneTypeCode) throws BusinessException{
		DealerIndicatorConfigDTO result = kpiCoordinator.loadKPIConfiguration(kpiCode, serviceSuperCategoryCode, countryCode, zoneTypeCode);
		return result;
	}

	/**
	 * consulta la informaci�n de la configuraci�n de todos los indicadores dado el
	 * pa�s y la super categor�a de servicio
	 * 
	 * @param kpiConfigCriteria    crtierio de b�squeda para el indicador
	 * @throws BusinessException 
	 */
	public KPIConfiguration loadKPIConfiguration(KPIConfigCriteria kpiConfigCriteria) throws BusinessException{
		KPIConfiguration result = kpiCoordinator.loadKPIConfiguration(kpiConfigCriteria);
		return result;
	}
}