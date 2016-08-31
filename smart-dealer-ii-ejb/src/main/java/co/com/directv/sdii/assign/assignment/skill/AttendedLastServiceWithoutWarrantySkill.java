package co.com.directv.sdii.assign.assignment.skill;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.directv.sdii.assign.assignment.AssignmentSkill;
import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.assign.assignment.skill.exception.AssignmentSkillException;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * Habilidad atendió último servicio sin periodo de garantía: Asigna el mismo
 * Dealer que atendió el último servicio al cliente 
 * (Se busca la última work order en estado realizada o finalizada y 
 * se obtiene el dealer que prestó el servicio al cliente) 
 * siempre y cuando este venga dentro de la
 * lista de entrada, de lo contrario no asigna ningún Dealer, es decir que si
 * el dealer que prestó el último servicio no viene dentro de la lista o 
 * el cliente no tenía work orders en estado atendida o finalizada previamente, 
 * se devuelve una lista vacia
 * En caso que la habilidad reciba una lista vacía de compañías devolverá una lista
 * con la compañía que realizó la última atención del servicio
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public class AttendedLastServiceWithoutWarrantySkill extends AssignmentSkill {
	
	private static Log log = LogFactory.getLog(AttendedLastServiceWithoutWarrantySkill.class);

	public AttendedLastServiceWithoutWarrantySkill(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * realiza la evaluacion de la habilidad dados los parametros
	 * 
	 * @param parameters parametros para realizar la evaluacion de la habilidad
	 * @throws AssignmentSkillException
	 */
	public List<DealerVO> doSkillEvaluation(SkillEvaluationDTO parameters) throws AssignmentSkillException{
		if(log.isDebugEnabled()){
			log.debug("== Inicia doSkillEvaluation/AttendedLastServiceWithoutWarrantySkill ==");
		}
		try {
			List<DealerVO> skillDealers = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealerFromLastWoFromCustomer(parameters.getIbsCustomerCode(), parameters.getCountryId());
			if(parameters.getDealerList() == null || parameters.getDealerList().isEmpty()){
				return skillDealers;
			}
			return intersectDealerList(parameters.getDealerList(), skillDealers);
		} catch (BusinessException e) {
			log.error("== Error al tratar de ejecutar la operacion doSkillEvaluation/AttendedLastServiceWithoutWarrantySkill", e);
			e.printStackTrace();
			throw super.manageException(e, AttendedLastServiceWithoutWarrantySkill.class.getName());
		}finally{
			if(log.isDebugEnabled()){
				log.debug("== Termina doSkillEvaluation/AttendedLastServiceWithoutWarrantySkill ==");
			}
		}		
	}
}