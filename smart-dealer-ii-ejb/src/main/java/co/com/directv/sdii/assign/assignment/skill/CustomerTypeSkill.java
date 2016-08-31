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
 * Habilidad de tipo de cliente: Busca dentro de la lista de entrada los Dealers
 * que prestan servicios a un tipo de cliente especifico, si la lista de entrada
 * es vac�a, consulta todas las compa��as instaladoras en el pa�s que prestan
 * servicios al tipo de cliente especificado
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public class CustomerTypeSkill extends AssignmentSkill {

	private static Log log = LogFactory.getLog(CustomerTypeSkill.class);
	
	public CustomerTypeSkill(){

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
			log.debug("== Inicia doSkillEvaluation/CustomerTypeSkill ==");
		}
		try {
			List<DealerVO> skillDealers = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealersWhoAttendCustomerTypeWoutCoverage(parameters.getCountryIso2Code(), parameters.getIbsCustomerTypeId());
			if(parameters.getDealerList() == null || parameters.getDealerList().isEmpty()){
				return skillDealers;
			}
			return intersectDealerList(parameters.getDealerList(), skillDealers);
		} catch (BusinessException e) {
			log.error("== Error al tratar de ejecutar la operacion doSkillEvaluation/CustomerTypeSkill", e);
			e.printStackTrace();
			throw super.manageException(e, CustomerTypeSkill.class.getName());
		}finally{
			if(log.isDebugEnabled()){
				log.debug("== Termina doSkillEvaluation/CustomerTypeSkill ==");
			}
		}
	}
}