package co.com.directv.sdii.assign.kpi;

import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorConfigDTO;
import co.com.directv.sdii.assign.kpi.dto.KPIConfigCriteria;
import co.com.directv.sdii.assign.kpi.dto.KPIConfiguration;
import co.com.directv.sdii.exceptions.BusinessException;

/**
 * Define las operaciones de cargue de la configuraci�n de los indicadores
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:47
 */
public interface KPIConfigLoader {

	/**
	 * Carga la configuracion de los indicadores dado el criterio de busqueda
	 * @param criteria criterios de busqueda para la configuracion de los indicadores
	 * @return
	 * @throws BusinessException
	 */
	public KPIConfiguration loadKPIConfiguration(KPIConfigCriteria criteria)throws BusinessException ;

	/**
	 * carga la configuraci�n de un indicador
	 * 
	 * @param kpiCode    c�digo del indicador que se desea cargar
	 * @param serviceSuperCategoryCode    c�digo de la super categor�a de servicio
	 * @param countryCode    c�digo del pa�s
	 */
	public DealerIndicatorConfigDTO loadKPIConfiguration(String kpiCode, String serviceSuperCategoryCode, String countryCode, String zoneTypeCode)throws BusinessException ;

}