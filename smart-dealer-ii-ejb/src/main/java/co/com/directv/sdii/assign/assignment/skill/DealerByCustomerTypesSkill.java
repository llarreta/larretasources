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
 * Habilidad configuración de dealers por tipo de cliente:<br/>
 * Busca los Dealers que tienen:<br/>
 * 	<ul><li> cobertura para el área de negocio de la WO y la categoria de cliente del cliente de la WO. </li>
 * 	<li> sub categoria de servicio para el área de negocio de la WO. </li>
 *  <li> tipo de cliente para la categoría de cliente del cliente de la WO. </li></ul>
 *  Solo se devolveran los dealers que cumplan los 3 requisitos anteriores.
 * 
 * Fecha de Creación: 30/01/2014
 * @author ssanabri <a href="mailto:faucndo.sanabria@everis.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class DealerByCustomerTypesSkill extends AssignmentSkill {
	
	private static Log log = LogFactory.getLog(DealerByCustomerTypesSkill.class);

	public DealerByCustomerTypesSkill() {
	}

	public void finalize() throws Throwable {
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.assign.assignment.AssignmentSkill#doSkillEvaluation(co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO)
	 */
	@Override
	public List<DealerVO> doSkillEvaluation(SkillEvaluationDTO parameters) throws AssignmentSkillException{
		try {
			if(log.isDebugEnabled()){
				log.debug("== Inicia doSkillEvaluation/DealerByCustomerTypesSkill ==");
			}
			
			List<DealerVO> skillDealers = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealerFromDealerByCustomerType(
					parameters.getCustomerCategoryId(), parameters.getBusinessAreaId(), parameters.getIbsCustomerTypeId(),
					parameters.getServiceCategoryId(), parameters.getPostalCodeId(), parameters.getCountryId());
			
			if(parameters.getDealerList() == null || parameters.getDealerList().isEmpty()){
				return skillDealers;
			}
			
			return intersectDealerList(parameters.getDealerList(), skillDealers);			
		}catch (BusinessException e) {
			log.error("== Error al tratar de ejecutar la operacion doSkillEvaluation/DealerByCustomerTypesSkill", e);
			e.printStackTrace();
			throw super.manageException(e, DealerByCustomerTypesSkill.class.getName());
		}finally{
			if(log.isDebugEnabled()){
				log.debug("== Termina doSkillEvaluation/DealerByCustomerTypesSkill ==");
			}
		}		
	}

}
