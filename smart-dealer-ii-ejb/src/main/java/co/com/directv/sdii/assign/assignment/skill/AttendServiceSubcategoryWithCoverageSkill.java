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
 * Habilidad cobertura por sub categor�a de servicio: Dado un conjunto de
 * companias instaladora de entrada, selecciona las compa��as que presten por lo
 * menos una sub categor�a de servicio de los servicios recibidos como par�metro
 * en el �rea geogr�fica especificada. La configuraci�n de esta habilidad se
 * realizar� por cada compa��a instaladora a nivel de sub categor�a de servicio.
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public class AttendServiceSubcategoryWithCoverageSkill extends AssignmentSkill {
	private static Log log = LogFactory.getLog(AttendServiceSubcategoryWithCoverageSkill.class);
	public AttendServiceSubcategoryWithCoverageSkill(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * realiza la evaluacion de la habilidad dados los par�metros
	 * 
	 * @param parameters    parametros para realizar la evaluacion de la habilidad
	 */
	public List<DealerVO> doSkillEvaluation(SkillEvaluationDTO parameters)throws AssignmentSkillException{
		if(log.isDebugEnabled()){
			log.debug("== Inicia doSkillEvaluation/AttendServiceSubcategoryWithCoverageSkill ==");
		}
		try {
			List<DealerVO> skillDealers = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealersWhoAttendServiceSubCategoryWithCoverage(parameters.getCountryIso2Code(), parameters.getServices().get(0) , parameters.getPostalCode() );
			if(parameters.getDealerList() == null || parameters.getDealerList().isEmpty()){
				return skillDealers;
			}
			return intersectDealerList(parameters.getDealerList(), skillDealers);
		} catch (BusinessException e) {
			log.error("== Error al tratar de ejecutar la operacion doSkillEvaluation/AttendServiceSubcategoryWithCoverageSkill", e);
			e.printStackTrace();
			throw super.manageException(e, AttendServiceSubcategoryWithCoverageSkill.class.getName());
		}finally{
			if(log.isDebugEnabled()){
				log.debug("== Termina doSkillEvaluation/AttendServiceSubcategoryWithCoverageSkill ==");
			}
		}
	}
}