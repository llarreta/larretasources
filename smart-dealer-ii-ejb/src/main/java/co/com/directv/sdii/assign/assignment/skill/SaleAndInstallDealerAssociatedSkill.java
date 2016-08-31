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
 * Habilidad vende instala con dealer asociado Dealer o sucursal: asignar al
 * dealer o sucursal que pueda prestar el servicio aunque no lo haya vendido dicha
 * sucursal.
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:48
 */
public class SaleAndInstallDealerAssociatedSkill extends AssignmentSkill {
	
	private static Log log = LogFactory.getLog(SaleAndInstallDealerAssociatedSkill.class);

	public SaleAndInstallDealerAssociatedSkill(){

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
			log.debug("== Inicia doSkillEvaluation/SaleAndInstallDealerAssociatedSkill ==");
		}
		try {
			List<DealerVO> skillDealers = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealersForSaleAndInstallDealerAssociatedSkill(parameters);
			if(parameters.getDealerList() == null || parameters.getDealerList().isEmpty()){
				return skillDealers;
			}
			return intersectDealerList(parameters.getDealerList(), skillDealers);			
		} catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operacion doSkillEvaluation/SaleAndInstallDealerAssociatedSkill", e);
			e.printStackTrace();
			throw super.manageException(e, SaleAndInstallDealerAssociatedSkill.class.getName());
		}finally{
			if(log.isDebugEnabled()){
				log.debug("== Termina doSkillEvaluation/SaleAndInstallDealerAssociatedSkill ==");
			}
		}
	}
}