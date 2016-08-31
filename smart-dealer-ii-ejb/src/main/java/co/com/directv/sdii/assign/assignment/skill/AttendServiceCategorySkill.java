package co.com.directv.sdii.assign.assignment.skill;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.assign.assignment.AssignmentSkill;
import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.assign.assignment.skill.exception.AssignmentSkillException;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * consulta la lista de compagnias instaladoras que atienden la categor�a de
 * servicio especificada. una compagna atiende una categor�a de servicio si
 * atiente por lo menos una sub categoria de esa categoria
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public class AttendServiceCategorySkill extends AssignmentSkill {

	private static Log log = LogFactory.getLog(AttendServiceCategorySkill.class);
	
	public AttendServiceCategorySkill(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * Metodo: realiza la evaluacion de la habilidad dados los parametros
	 * 
	 * @param parameters    parametros para realizar la evaluacion de la habilidad
	 * @throws AssignmentSkillException 
	 */
	public List<DealerVO> doSkillEvaluation(SkillEvaluationDTO parameters) throws AssignmentSkillException{
		
		if(log.isDebugEnabled()){
			log.debug("== Inicia doSkillEvaluation/AttendServiceCategorySkill ==");
		}
		try {
			List<DealerVO> skillDealers = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealersWhoAttendServiceCategory(parameters.getCountryIso2Code(), parameters.getServices().get(0));
			if(parameters.getDealerList() == null || parameters.getDealerList().isEmpty()){
				return skillDealers;
			}
			return intersectDealerList(parameters.getDealerList(), skillDealers);
		} catch (BusinessException e) {
			log.error("== Error al tratar de ejecutar la operacion doSkillEvaluation/AttendServiceCategorySkill", e);
			e.printStackTrace();
			throw super.manageException(e, AttendServiceCategorySkill.class.getName());
		}finally{
			if(log.isDebugEnabled()){
				log.debug("== Termina doSkillEvaluation/AttendServiceCategorySkill ==");
			}
		}
	}
}