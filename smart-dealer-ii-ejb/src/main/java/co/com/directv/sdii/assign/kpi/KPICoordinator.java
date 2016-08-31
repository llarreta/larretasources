package co.com.directv.sdii.assign.kpi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorConfigDTO;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorResultDTO;
import co.com.directv.sdii.assign.kpi.dto.KPIConfigCriteria;
import co.com.directv.sdii.assign.kpi.dto.KPIConfiguration;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.vo.KpiConfigurationVO;
import co.com.directv.sdii.model.vo.KpiTypeVO;
import co.com.directv.sdii.model.vo.SystemParameterVO;

/**
 * Se encarga de coordinar el cálculo de los indicadores que le aplican a una
 * compañía instaladora dependiendo de la categoría de servicio que se quiera
 * evaluar y el país de la compañía instaladora
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:47
 */
public final class KPICoordinator {

	private static Log log = LogFactory.getLog(KPICoordinator.class);
	
	private static KPICoordinator mySelf;
	
	private KPIConfigLoader kpiConfigLoader;
	private Map<String, DealerKPIValueFinder> kpiValueFinders;
	private Map<Long, KpiTypeVO> kpiTypes;

	private KPICoordinator(){
		
		try {
			
			kpiConfigLoader = KPIConfigLoaderImpl.getInstance();
			kpiValueFinders = new HashMap<String, DealerKPIValueFinder>();
			
			
			kpiValueFinders.put(CodesBusinessEntityEnum.KPI_CALCULATION_TYPE_ONLINE.getCodeEntity(), new OnLineDealerKPIValueFinder());
			kpiValueFinders.put(CodesBusinessEntityEnum.KPI_CALCULATION_TYPE_FRECUENCY.getCodeEntity(), new FrecuencyDealerKPIValueFinder());
		
			kpiTypes = new HashMap<Long, KpiTypeVO>();
			
			List<KpiTypeVO> kpiTypeList = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getActiveKpiTypes();
			
			for (KpiTypeVO kpiTypeVO : kpiTypeList) {
				kpiTypes.put(kpiTypeVO.getId(), kpiTypeVO);
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
			log.error(e);
		}
	}
	
	public static KPICoordinator getInstance(){
		if(mySelf == null){
			mySelf = new KPICoordinator();
		}
		return mySelf;
	}

	public void finalize() throws Throwable {

	}
	/**
	 * Obtiene el valor de los indicadores para un dealer
	 * 
	 * @param countryId    identificador del país
	 * @param serviceCatgoryId    identificador de la categoría de servicio
	 * @param dealerId    identificador de la compañía instaladora
	 * @throws BusinessException 
	 */
	public Double getDealerKPI(Long countryId, Long serviceSuperCatgoryId, Long dealerId, Long zoneTypeId) throws BusinessException{
		
		Double totalResult;
		try {
			Double kpiResultUpperLimit = getNumericSystemParam(CodesBusinessEntityEnum.SYSTEM_PARAM_KPI_RESULT_UPPER_LIMIT.getCodeEntity(), countryId);
			Double kpiResultLowerLimit = getNumericSystemParam(CodesBusinessEntityEnum.SYSTEM_PARAM_KPI_RESULT_LOWER_LIMIT.getCodeEntity(), countryId);
			
			KPIConfigCriteria kpiConfigCriteria = new KPIConfigCriteria(countryId, serviceSuperCatgoryId, zoneTypeId);
			KPIConfiguration kpiConfig = kpiConfigLoader.loadKPIConfiguration(kpiConfigCriteria);
			List<DealerIndicatorConfigDTO> kpiConfigs = kpiConfig.getIndicatorsConfig();
			
			totalResult = 0D;
			Double kpiPartialResult = 0D;
			DealerIndicatorDTO resultCriteria = new DealerIndicatorDTO(countryId, serviceSuperCatgoryId, 0, zoneTypeId, null, null, dealerId);
			
			for (DealerIndicatorConfigDTO dealerIndicatorConfigDTO : kpiConfigs) {
				log.debug("gdfks-KPI "+dealerIndicatorConfigDTO.getIndicatorId());
				kpiPartialResult = calculateKPIPartialResult(dealerIndicatorConfigDTO, resultCriteria, kpiResultUpperLimit, kpiResultLowerLimit);
				log.debug("gdfks-Puntaje="+kpiPartialResult);
				totalResult += kpiPartialResult;
			}
		} catch (Throwable e) {
			log.error(e);
			if(e instanceof BusinessException){
				throw (BusinessException)e;
			}
			throw new BusinessException(ErrorBusinessMessages.KPI_CALCULATION_ERROR.getCode(),  ErrorBusinessMessages.KPI_CALCULATION_ERROR.getMessage() + e.getMessage());
		}
		
		return totalResult;
	}

	/**Calcula el resultado parcial de un KPI dada la configuración de ponderación
	 * y el tipo de indicador (+ o -)
	 * @param dealerIndicatorConfigDTO configurador del indicador 
	 * @param resultCriteria criterio de búsqueda del indicador
	 * @return valor ponderado del indicador que será sumado con los demás
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jjimenezh
	 * @throws PropertiesException 
	 */
	private Double calculateKPIPartialResult(DealerIndicatorConfigDTO dealerIndicatorConfigDTO,
			DealerIndicatorDTO resultCriteria, Double kpiResultUpperLimit, Double kpiResultLowerLimit) throws BusinessException, PropertiesException {

		DealerKPIValueFinder vf = kpiValueFinders.get(dealerIndicatorConfigDTO.getCalculateTypeCode());
		UtilsBusiness.assertNotNull(vf, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se encontró el KPIvalueFinder por el código especificado: " + dealerIndicatorConfigDTO.getCalculateTypeCode());
		resultCriteria.setDayCount(dealerIndicatorConfigDTO.getDayCount());
		resultCriteria.setIndicatorId(dealerIndicatorConfigDTO.getIndicatorId());
		resultCriteria.setIndicatorTypeId(dealerIndicatorConfigDTO.getIndicatorTypeId());
		DealerIndicatorResultDTO dealerKpiRes = vf.findDealerKPIValue(resultCriteria);
		
		Double kpiResult = dealerKpiRes.getIndicatorValue();
		Double goal = dealerIndicatorConfigDTO.getGoal();
		Double weight = dealerIndicatorConfigDTO.getWeighting();
		Double finalResult = 0D;
		
		KpiTypeVO kpiType = kpiTypes.get(dealerIndicatorConfigDTO.getIndicatorTypeId());
		UtilsBusiness.assertNotNull(kpiType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se encontró el tipo de KPI por el id especificado: " + dealerIndicatorConfigDTO.getIndicatorTypeId());
		
		if(CodesBusinessEntityEnum.KPI_TYPE_POSITIVE.getCodeEntity().equalsIgnoreCase(kpiType.getCode())){
			finalResult = ((1 - ((goal - kpiResult) / goal)) * 100);
			log.debug("gdfks-Cumplimiento real Vs Meta %="+"((1 - (("+goal+" - "+kpiResult+") / "+goal+")) * 100))="+finalResult);
			finalResult = calculateWithLimits(kpiResultUpperLimit, kpiResultLowerLimit, weight, finalResult);
		}else if(CodesBusinessEntityEnum.KPI_TYPE_NEGATIVE.getCodeEntity().equalsIgnoreCase(kpiType.getCode())){
			finalResult = ((1 + ((goal - kpiResult) / goal)) * 100);
			log.debug("gdfks-Cumplimiento real Vs Meta %="+"((1 - (("+goal+" - "+kpiResult+") / "+goal+")) * 100))="+finalResult);
			finalResult = calculateWithLimits(kpiResultUpperLimit, kpiResultLowerLimit, weight, finalResult);
		}else{
			throw new BusinessException(ErrorBusinessMessages.INVALID_PARAMETER.getCode(), ErrorBusinessMessages.INVALID_PARAMETER.getMessage(), Arrays.asList(new String[]{"Tipos de KPI", "identificador del tipo de KPI", dealerIndicatorConfigDTO.getIndicatorTypeId().toString()}));
		}
		
		return finalResult;
	}

	/**
	 * Metodo: Calcula el valor de un KPi teniendo en cuenta los límites superior e inferior de dichos
	 * KPI
	 * @param kpiResultUpperLimit Límite superior para el valor del KPI
	 * @param kpiResultLowerLimit límite inferior para el valor del KPI
	 * @param weight peso del KPI
	 * @param finalResult resultado final del KPI
	 * @return resultado del KPI teniendo en cuenta los límites y ponderado
	 * @author jjimenezh
	 */
	private Double calculateWithLimits(Double kpiResultUpperLimit,
			Double kpiResultLowerLimit, Double weight, Double finalResult) {
		Double result = 0D, result1 = 0D;
		if(finalResult < kpiResultLowerLimit){
			result1 = new Double(kpiResultLowerLimit.doubleValue());
		}else if(finalResult > kpiResultUpperLimit){
			result1 = new Double(kpiResultUpperLimit.doubleValue());
		}else{
			result1 = finalResult;
		}
		if(weight > 1){
			weight = weight / 100;
		}
		result = result1 * weight;
		log.debug("gdfks-Puntaje="+result1 +"*"+ weight+"="+finalResult);
		return result;
	}

	/**
	 * Metodo: Obtiene el valor numérico de un parámetro del sistema
	 * @param sysParamCode código del parámetro del sistema
	 * @param countryId identificador del país
	 * @return valor numperido del parámetro del sistema
	 * @throws BusinessException En caso que no se encuentre el parámetro del sistema con el código
	 * especificado
	 * @author jjimenezh
	 */
	private Double getNumericSystemParam(String sysParamCode, Long countryId) throws BusinessException {
		SystemParameterVO sysParam = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getSystemParameterByCodeAndCountryId(sysParamCode, countryId);
		Double value = new Double(sysParam.getValue());
		return value;
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
	public Double getDealerKPIByKPICode(Long countryId, Long serviceSuperCategoryId, Long zoneTypeId, Long dealerId, String indicatorCode) throws BusinessException{
		try{
			Double kpiResultUpperLimit = getNumericSystemParam(CodesBusinessEntityEnum.SYSTEM_PARAM_KPI_RESULT_UPPER_LIMIT.getCodeEntity(), countryId);
			Double kpiResultLowerLimit = getNumericSystemParam(CodesBusinessEntityEnum.SYSTEM_PARAM_KPI_RESULT_LOWER_LIMIT.getCodeEntity(), countryId);
			
			
			KpiConfigurationVO kpiConfig = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode(countryId, serviceSuperCategoryId, zoneTypeId, indicatorCode);
			UtilsBusiness.assertNotNull(kpiConfig, ErrorBusinessMessages.INVALID_PARAMETER.getCode(), ErrorBusinessMessages.INVALID_PARAMETER.getMessage(), Arrays.asList(new String[]{"No se encontró la configuración para el kpi"," countryId, serviceSuperCategoryId, zoneTypeId, indicatorCode", ""+ countryId + serviceSuperCategoryId + zoneTypeId + indicatorCode}));
			DealerIndicatorConfigDTO config = kpiConfig.toDealerIndicatorConfigDTO();
			DealerIndicatorDTO resultCriteria = new DealerIndicatorDTO(countryId, serviceSuperCategoryId, 0, zoneTypeId, null, null, dealerId);
			Double kpiResult = calculateKPIPartialResult(config, resultCriteria, kpiResultUpperLimit, kpiResultLowerLimit);
			return kpiResult;
		} catch (Throwable e) {
			e.printStackTrace();
			log.error(e);
			if(e instanceof BusinessException){
				throw (BusinessException)e;
			}
			throw new BusinessException(ErrorBusinessMessages.KPI_CALCULATION_ERROR.getCode(),  ErrorBusinessMessages.KPI_CALCULATION_ERROR.getMessage() + e.getMessage());
		}
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
		return kpiConfigLoader.loadKPIConfiguration(kpiCode, serviceSuperCategoryCode, countryCode, zoneTypeCode);
	}

	/**
	 * consulta la informaci�n de la configuraci�n de todos los indicadores dado el
	 * pa�s y la super categor�a de servicio
	 * 
	 * @param kpiConfigCriteria    crtierio de b�squeda para el indicador
	 * @throws BusinessException 
	 */
	public KPIConfiguration loadKPIConfiguration(KPIConfigCriteria kpiConfigCriteria) throws BusinessException{
		return kpiConfigLoader.loadKPIConfiguration(kpiConfigCriteria);
	}
	
}