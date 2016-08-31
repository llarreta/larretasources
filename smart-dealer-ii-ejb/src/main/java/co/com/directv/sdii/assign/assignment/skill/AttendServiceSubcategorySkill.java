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
 * Habilidad atiende sub categor�a de servicio, en SDII la sub categor�a de
 * servicio es la categor�a o serviceCategory: dentro de la lista de entrada,
 * busca los Dealers que prestan una sub categor�a de servicio, del primer
 * servicio recibido como par�metro
 * @author jjimenezh
 * @author rdelarosa
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public class AttendServiceSubcategorySkill extends AssignmentSkill {
	private static Log log = LogFactory.getLog(AttendServiceSubcategorySkill.class);
	
	public AttendServiceSubcategorySkill(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * Realiza la evaluacion de la habilidad dados los parametros
	 * 
	 * @param parameters    parametros para realizar la evaluacion de la habilidad
	 * @return
	 * @throws AssignmentSkillException
	 */
	public List<DealerVO> doSkillEvaluation(SkillEvaluationDTO parameters)throws AssignmentSkillException{
		if(log.isDebugEnabled()){
			log.debug("== Inicia doSkillEvaluation/AttendServiceSubcategorySkill ==");
		}
		try {
			List<DealerVO> skillDealers = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealersWhoAttendServiceSubCategory(parameters.getCountryIso2Code(), parameters.getServices().get(0)) ;
			if(parameters.getDealerList() == null || parameters.getDealerList().isEmpty()){
				return skillDealers;
			}
			return intersectDealerList(parameters.getDealerList(), skillDealers);
		} catch (BusinessException e) {
			log.error("== Error al tratar de ejecutar la operacion doSkillEvaluation/AttendServiceSubcategorySkill", e);
			e.printStackTrace();
			throw super.manageException(e, AttendServiceSubcategorySkill.class.getName());
		}finally{
			if(log.isDebugEnabled()){
				log.debug("== Termina doSkillEvaluation/AttendServiceSubcategorySkill ==");
			}
		}
	}
}