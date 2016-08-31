package co.com.directv.sdii.assign.assignment;

import java.util.ArrayList;
import java.util.List;

import co.com.directv.sdii.assign.assignment.dto.AssignmentConfiguration;
import co.com.directv.sdii.assign.assignment.dto.AssignmentCriteria;
import co.com.directv.sdii.assign.assignment.dto.SkillExecConfiguration;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.SkillConfigurationVO;

/**
 * Implementación del cargador de configuración del asignador para base de datos
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public class DBAssignmentConfigLoader implements AssignmentConfigLoader {

	public DBAssignmentConfigLoader(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * 
	 * @param criteria    criterio de cargue de la configuraci�n
	 */
	public AssignmentConfiguration loadAssignmentConfiguration(AssignmentCriteria criteria){
		
		try {
			List<SkillConfigurationVO> skillConfigurations = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getSkillConfigurationsByCriteria(criteria.getCountryIso2Code(), criteria.getServiceCategoryCode(), criteria.getExecutionMode());
			
			List<SkillExecConfiguration> execConfigs = new ArrayList<SkillExecConfiguration>();
			SkillExecConfiguration execConfig;
			for (SkillConfigurationVO skillConfigurationVO : skillConfigurations) {
				execConfig = buildExcecConfig(skillConfigurationVO);
				execConfigs.add(execConfig);
			}
			AssignmentConfiguration result = new AssignmentConfiguration(criteria,execConfigs);
			return result;
		} catch (BusinessException e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Metodo: Convierte un objeto del modelo de datos de SDII en un objeto del dominio del
	 * componente de asignación
	 * @param skillConfigurationVO modelo del dominio de SDII
	 * @return objeto del modelo de dominio del asignador
	 * @author jjimenezh
	 */
	private SkillExecConfiguration buildExcecConfig(SkillConfigurationVO skillConfigurationVO) {
		SkillExecConfiguration result = new SkillExecConfiguration();
		result.setEvaluationModeCode(skillConfigurationVO.getSkillEvaluationType().getCode());
		result.setEvaluationOrder(skillConfigurationVO.getSkillOrder().intValue());
		result.setSkillCode(skillConfigurationVO.getSkill().getSkillCode());
		return result;
	}
}