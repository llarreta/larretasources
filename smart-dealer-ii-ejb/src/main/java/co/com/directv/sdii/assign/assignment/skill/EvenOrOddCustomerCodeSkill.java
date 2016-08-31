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
 * Habilidad c�digo de cliente par o impar: De acuerdo al IBS del cliente asigna
 * un Dealer especifico (Se pre-configuran previamente dos �nicos Dealers), si el
 * IBS es par asigna un Dealer pre-configurado y si es impar asigna el otro.
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public class EvenOrOddCustomerCodeSkill extends AssignmentSkill {
	
	private static Log log = LogFactory.getLog(EvenOrOddCustomerCodeSkill.class);

	public EvenOrOddCustomerCodeSkill(){

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
			log.debug("== Inicia doSkillEvaluation/EvenOrOddCustomerCodeSkill ==");
		}
		try {
			List<DealerVO> skillDealers = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealersForEvenOrOddCustomerCodeSkill(parameters);
			if(parameters.getDealerList() == null || parameters.getDealerList().isEmpty()){
				return skillDealers;
			}
			return intersectDealerList(parameters.getDealerList(), skillDealers);			
		} catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operacion doSkillEvaluation/EvenOrOddCustomerCodeSkill", e);
			e.printStackTrace();
			throw super.manageException(e, EvenOrOddCustomerCodeSkill.class.getName());
		}finally{
			if(log.isDebugEnabled()){
				log.debug("== Termina doSkillEvaluation/EvenOrOddCustomerCodeSkill ==");
			}
		}
	}
}