package co.com.directv.sdii.assign.assignment.skill;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.assign.assignment.AssignmentSkill;
import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.assign.assignment.skill.exception.AssignmentSkillException;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * Habilidad de KPI: dados los indicadores configurados la habilidad filtrar� los
 * dealers que se reciben como entrada dejando �nicamente los dealers que
 * sobrepasan las metas definidas.
 * Cada pa�s seleccionar� los KPI que aplican para cada categor�a de servicio y
 * asignar� la ponderaci�n que le dar� peso sobre esa categor�a de servicio.
 * Con la configuraci�n de los KPI que aplican para cada categor�a de servicio se
 * configurar�n las metas que aplican para cada tipo de zona (rural o urbana).
 * La forma de utilizaci�n en la asignaci�n ser� utilizando la meta que deber�n
 * superar los candidatos y/o como ranking para asignar al que tenga mayor
 * calificaci�n.
 * Cada pa�s definir� la frecuencia de generaci�n y el n�mero de d�as a tener en
 * cuenta para el KPI y cada KPI tendr� un valor por cada compa��a instaladora por
 * categor�a de servicio por cada tipo de zona en la que la compa��a tiene
 * cobertura
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:47
 */
public class KPISkill extends AssignmentSkill {
	
	private static Log log = LogFactory.getLog(KPISkill.class);

	public KPISkill(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * realiza la evaluaci�n de la habilidad dados los par�metros
	 * 
	 * @param parameters    par�metros para realizar la evaluaci�n de la habilidad
	 */
	public List<DealerVO> doSkillEvaluation(SkillEvaluationDTO parameters)throws AssignmentSkillException{
		if(log.isDebugEnabled()){
			log.debug("== Inicia doSkillEvaluation/KPISkill ==");
		}
		try {
			List<DealerVO> skillDealers = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealersForKpiSkill(parameters);
			if(parameters.getDealerList() == null || parameters.getDealerList().isEmpty()){
				return skillDealers;
			}
			List<DealerVO> result = intersectDealerList(parameters.getDealerList(), skillDealers);
			return result;
		} catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operacion doSkillEvaluation/KPISkill", e);
			e.printStackTrace();
			throw super.manageException(e, KPISkill.class.getName());
		}finally{
			if(log.isDebugEnabled()){
				log.debug("== Termina doSkillEvaluation/KPISkill ==");
			}
		}
	}
}