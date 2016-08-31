package co.com.directv.sdii.assign.assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.directv.sdii.assign.assignment.dto.AssignmentConfiguration;
import co.com.directv.sdii.assign.assignment.dto.AssignmentCriteria;
import co.com.directv.sdii.assign.assignment.dto.AssignmentResultDTO;
import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.assign.assignment.dto.SkillExecConfiguration;
import co.com.directv.sdii.assign.assignment.re.AssignmentSkillResultEvaluator;
import co.com.directv.sdii.assign.assignment.re.NDSCSkillResultEvaluator;
import co.com.directv.sdii.assign.assignment.re.SNDSCSkillResultEvaluator;
import co.com.directv.sdii.assign.assignment.skill.exception.AssignmentSkillException;
import co.com.directv.sdii.assign.assignment.skill.factory.AssignmentSkillFactory;
import co.com.directv.sdii.assign.assignment.skill.factory.AssignmentSkillFactoryImpl;
import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ServiceTypeVO;

/**
 * Realiza la orquestación en la invocación de las habilidades de acuerdo con las
 * configuraciones de cada país y la categoría del primer servicio recibido para
 * la asignación
 * se deberá ejecutar de la siguiente manera:
 * 1. invoca la operación loadAssignmentConfiguration
 * 2. Dada la configuración establece el orden de evaluación y ejecuta cada una de
 * las habilidades, con el resultado de cada una, lo pasa por el
 * SkillResultEvaluator que le corresponda, en caso que en la ejecución se capture
 * una excepción de NDSC, se seleccionará el dealer sin cobertura y se retornará
 * como resultado.
 * 3. con cada ejecución de cada habilidad y su posterior evaluación, se tomará el
 * resultado y se realizará una intersección de la lista de la evaluación de la
 * habilidad anterior y la habilidad actual. en caso que se esté ejecutando en
 * modalidad de agenda se deberá pasar por todas las habilidades si se encuentra
 * que una de ellas arroja una lista vacia, se retornará una lista vacia de
 * compañías instaladoras.
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public final class AssignmentSkillCoordinator extends BusinessBase {

	private static Log log = LogFactory.getLog(AssignmentSkillCoordinator.class);
	
	private AssignmentConfigLoader assignConfigLoader = new DBAssignmentConfigLoader();
	private Map<String, AssignmentSkillResultEvaluator> resultEvaluators;
	private AssignmentSkillFactory skillFactory = new AssignmentSkillFactoryImpl();
	private static AssignmentSkillCoordinator mySelf;
	
	
	private AssignmentSkillCoordinator(){
		resultEvaluators = new HashMap<String, AssignmentSkillResultEvaluator>();
		try {
			resultEvaluators.put(CodesBusinessEntityEnum.NDSC_RESULT_SKILL_EVAL.getCodeEntity(), new NDSCSkillResultEvaluator());
			resultEvaluators.put(CodesBusinessEntityEnum.SNDSC_RESULT_SKILL_EVAL.getCodeEntity(), new SNDSCSkillResultEvaluator());
		} catch (PropertiesException e) {
			e.printStackTrace();
			log.error("No se pudo cargar la información de códigos de tipo de evaluación de habilidades", e);
			throw new IllegalStateException(e);
		}
	}
	
	public static AssignmentSkillCoordinator getInstance(){
		if(mySelf == null){
			mySelf = new AssignmentSkillCoordinator();
		}
		return mySelf;
	}

	public void finalize() throws Throwable {

	}
	/**
	 * Realiza la evaluación de las habilidades dado el conjunto de parámetros de
	 * entrada, consultado la configuración específica para el país, la categoría de
	 * servicio y el tipo de evaluación (asignación o agendamiento) en el caso de
	 * ejecutarse como asignación devolverá únicamente una compañía, en caso de
	 * agendamiento devolverá la lista de todas las compañías que cumplen todas las
	 * habilidades configuradas.
	 * 
	 * @param evaluationData    Información para realizar la evaluación de las
	 * habilidades
	 * @throws BusinessException
	 */
	public AssignmentResultDTO doSkillEvaluations(SkillEvaluationDTO evaluationData) throws BusinessException{
		if(log.isDebugEnabled()){
			log.debug("-- inicia la ejecución de las evaluaciones de habilidades --");
		}
		try {
			StringBuilder traceStr = new StringBuilder();
//          Comentariado porque no se ha aprobado control de cambios

			UtilsBusiness.assertNotNull(evaluationData, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " Se intentó realizar la evaluación de habilidades con datos nulos");
			String serviceCode = evaluationData.getServices().get(0);
			ServiceTypeVO serviceType = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getServiceTypeByServiceCode(serviceCode);
			UtilsBusiness.assertNotNull(serviceType, ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getMessage()  + "No se ha encontrado el tipo de servicio por el código de servicio especificado: " + serviceCode);
			AssignmentCriteria criteria = new AssignmentCriteria(evaluationData.getCountryIso2Code(), evaluationData.getExecutionMode(), serviceType.getServiceTypeCode());
			
			//Cargando la configuración de las habilidades:
			AssignmentConfiguration assignConfig = loadAssignmentConfiguration(criteria);
			UtilsBusiness.assertNotNull(assignConfig, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se ha encontrado la información de configuración de asignación por el siguiente criterio: " + criteria.toString());
			String codeCapacityDateServiceHourSkill = CodesBusinessEntityEnum.CODE_CAPACITY_DATE_SERVICE_HOUR_SKILL.getCodeEntity();
			//
			List<SkillExecConfiguration> skillConfigs = assignConfig.getSkillConfigurations();
			UtilsBusiness.assertNotNull(skillConfigs, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se encontró información de la confguración de las habilidades y el orden en el que deben ser evaluadas dados los siguientes criterios: " + criteria.toString());
			List<DealerVO> oldList = new ArrayList<DealerVO>(), newList = new ArrayList<DealerVO>();
			for (SkillExecConfiguration skillExecConfiguration : skillConfigs) {
 				if(skillExecConfiguration.getSkillCode().equals(codeCapacityDateServiceHourSkill) && (evaluationData.getScheduleDate() == null)){
 					traceStr.append(traceSkillResult(null, skillExecConfiguration));
 				}else{
	 				oldList.clear();
					oldList.addAll(newList);
					evaluationData.setDealerList(oldList);
					newList = doSkillEvaluation(skillExecConfiguration, evaluationData);
					
					traceStr.append(traceSkillResult(newList, skillExecConfiguration));
					newList = doResultSkillEvaluation(skillExecConfiguration, newList, oldList, evaluationData);
					
					//Si la evaluación de los resultados de la habilidad retornan una sola compañía y además esta es la marcada como sin cobertura se retornará este resultado
					if(newList.size() == 1 && newList.get(0).getDealerCode().longValue() == getDealerCodeWoutCoverage(evaluationData.getCountryId())){
						return buildAssignmentResult(newList, traceStr);
					}
					//Si la evaluación se está ejecutando en modalidad de asignación y se encuentra una sola compañía, se retorna esa compañía
					if(newList.size() == 1 && evaluationData.getExecutionMode().equalsIgnoreCase(CodesBusinessEntityEnum.SKILL_MODE_TYPE_ASSIGN.getCodeEntity())){
						return buildAssignmentResult(newList, traceStr);
					}
 				}
			}
			
			return buildAssignmentResult(newList, traceStr);
			
		} catch (Throwable e) {
			log.error("Error al tratar de realizar el proceso de asignación", e);
			e.printStackTrace();
			throw super.manageException(e);
		}finally{
			if(log.isDebugEnabled()){
				log.debug("-- termina la ejecución de las evaluaciones de habilidades --");
			}
		}
	}

	private AssignmentResultDTO buildAssignmentResult(List<DealerVO> newList,
			StringBuilder traceStr) {
		AssignmentResultDTO result = new AssignmentResultDTO();
		result.setSelectedDealers(newList);
		result.setTraceAssignmentInformation(traceStr.toString());
		return result;
	}

	/**
	 * Metodo: <Descripcion>
	 * @param newList
	 * @param skillExecConfiguration <tipo> <descripcion>
	 * @author
	 * @throws PropertiesException 
	 */
	private String traceSkillResult(List<DealerVO> newList, SkillExecConfiguration skillExecConfiguration) {
		StringBuilder str = new StringBuilder(ApplicationTextEnum.RESULT.getApplicationTextValue());
		str.append(skillExecConfiguration.getSkillCode());
		str.append(" ");
		str.append(ApplicationTextEnum.ORDER_CONFIG.getApplicationTextValue());
		str.append(skillExecConfiguration.getEvaluationOrder());
		str.append(" ");
		str.append(ApplicationTextEnum.DEALERS_FOUND.getApplicationTextValue());
		if(newList==null)
			str.append(0);
		else
			str.append(newList.size());
		str.append("\n\n");
		str.append(ApplicationTextEnum.DEALERS_ARE.getApplicationTextValue() + "\n");
		str.append("\n");
		if(newList!=null)
			for (DealerVO dealerVO : newList) {
				str.append(dealerVO.getDealerCode());
				str.append(" - ");
				str.append(dealerVO.getDealerName());
				str.append("\n");
			}
		
		str.append("\n\n");
		if(log.isDebugEnabled()){
			log.debug( str );
			log.debug("Fin traza de resultado habilidad");
		}
		return str.toString();
	}

	/**
	 * Metodo: Obtiene el código del dealer sin cobertura configurado en el país
	 * @param countryId identificador del país
	 * @return código de la compañía marcada como sin cobertura en el país seleccionado
	 * @author jjimenezh
	 * @throws BusinessException en caso de error al tratar de obtener el código de dealer sin cobertura
	 */
	private long getDealerCodeWoutCoverage(Long countryId) throws BusinessException {
		return AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealerCodeWoutCoverage(countryId);
	}

	/**
	 * Metodo: Realiza la evaluación del resultado de la habilidad
	 * @param skillExecConfiguration configuración de la ejecución de la habilidad
	 * @param newList lista con las compañías seleccionadas por la evaluación de la habilidad
	 * @param oldList Lista con las compañías seleccionadas por la evaluación de la anterior habilidad
	 * @param skillEvalParameters
	 * @return Lista con las compañías que resultan de la evaluación del resultado así:
	 * 1. si se ha configurado con NDSC y la newList no tiene compañías, se devolverá una sola compañía con el código de la
	 * compañía sin cobertura
	 * 2. Se se ha configurado SNDSC y la newList no tiene compañías se devolverá la misma oldList
	 * @throws BusinessException  en caso de error
	 * @author jjimenezh
	 */
	private List<DealerVO> doResultSkillEvaluation(SkillExecConfiguration skillExecConfiguration, List<DealerVO> newList, List<DealerVO> oldList, SkillEvaluationDTO skillEvalParameters) throws BusinessException {
		String resultEvalCode = skillExecConfiguration.getEvaluationModeCode();
		AssignmentSkillResultEvaluator resultEval = resultEvaluators.get(resultEvalCode);
		
		UtilsBusiness.assertNotNull(resultEval, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se encontró el evaluador del resultado por el código: " + resultEvalCode);
		List<DealerVO> result = resultEval.evaluateResult(newList, oldList, skillEvalParameters);
		return result;
	}

	/**
	 * Metodo: Se encarga de desacoplar la contrucción e invocación de la evauación de una 
	 * habilidad
	 * @param skillExecConfiguration
	 * @param evaluationData
	 * @return <tipo> <descripcion>
	 * @author
	 * @throws AssignmentSkillException 
	 */
	private List<DealerVO> doSkillEvaluation(SkillExecConfiguration skillExecConfiguration,
			SkillEvaluationDTO evaluationData) throws AssignmentSkillException {
		AssignmentSkill skill = skillFactory.buildAssignmentSkill(skillExecConfiguration.getSkillCode());
		List<DealerVO> result = skill.doSkillEvaluation(evaluationData);
		return result;
	}

	/**
	 * carga la configuración
	 * 
	 * @param criteria criterio de búsqueda de la configuración de la asignación
	 */
	private AssignmentConfiguration loadAssignmentConfiguration(AssignmentCriteria criteria){
		return assignConfigLoader.loadAssignmentConfiguration(criteria);
	}
}