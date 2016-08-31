package co.com.directv.sdii.assign.assignment.skill;
import java.util.ArrayList;
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
 * Habilidad atención último servicio con periodo de garantía: Asigna el mismo
 * Dealer del servicio anterior mientras el periodo de garantía para la categoría
 * de servicio no haya sido superado y el servicio a prestar este incluido en la
 * lista de posibles servicios de garantía, de lo contrario no asigna ningún
 * Dealer.
 * La tabla en donde se guardan por categoría de servicio los servicios que constituyen garantía es:
 * services_are_warranties, que está relacionada con services_types_warranties.
 * <br><br>
 * El orden para realizar la evaluación de la habilidad es:<br><br>
 * 1. Consultar La última Work order que se atendió al cliente, es decir que esté en estado 
 * 		realizada o finalizada (Si no existe, se devuelve una lista vacía de dealers)<br>
 * 2. Consultar la categoría de servicio del primer servicio asociado a esa Work Order (la consultada en el paso 1)
 * 		en la base de datos la categoría del servicio se almacena en la tabla service_type<br>
 * 3. Consultar si algunos de los servicios que se prestarán al cliente en esta oportunidad están configurados como que
 * 		constituyen garantía de la categoría consultada en el paso 2, esto se almacena en la tabla
 * 		services_are_warranties, si ninguno constituye garantía la habilidad devuelve una lista vacia de dealers<br>
 * 4. Consultar el periodo de garantía de la categoría de servicio de esa WO (la consultada en el paso 1)
 * 		en la tabla services_type_warranties<br>
 * 5. Si la última WO consultada en el paso 1 tiene como fecha de atención o finalización una fecha superior
 * 		a la fecha actual menos los días de periodo de garantía consultados en el paso 4, quiere decir
 * 		que esa compañía debe atender la WO actual<br>
 * 6. Si la compañía que atendió la WO del paso 1, está dentro de la lista de compañías que se recibieron, se
 * 		devuelve una lista con solo esa compañía, si la compañía no está dentro de la lista que se recibe
 * 		se devuelve una lista vacia.<br>
 * 7. En caso que la habilidad reciba una lista vacía de compañías devolverá una lista
 * 		con la compañía que se encontró en el paso 1.
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public class AttendedLastServiceWithWarrantySkill extends AssignmentSkill {
	
	private static Log log = LogFactory.getLog(AttendedLastServiceWithoutWarrantySkill.class);

	public AttendedLastServiceWithWarrantySkill(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * realiza la evaluacion de la habilidad dados los parametros
	 * 
	 * @param parameters    parametros para realizar la evaluacion de la habilidad
	 * @throws AssignmentSkillException
	 */
	public List<DealerVO> doSkillEvaluation(SkillEvaluationDTO parameters) throws AssignmentSkillException{
		if(log.isDebugEnabled()){
			log.debug("== Inicia doSkillEvaluation/AttendedLastServiceWithWarrantySkill ==");
		}
		try {
			List<DealerVO> skillDealers = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealerFromWoWithWarranty(parameters.getIbsCustomerCode(), parameters.getCountryId(), parameters.getServices());
			
			if(skillDealers == null || skillDealers.isEmpty()){
				return new ArrayList<DealerVO>();
			}
			
			if(parameters.getDealerList() == null || parameters.getDealerList().isEmpty()){
				return skillDealers;
			}
			
			return intersectDealerList(parameters.getDealerList(), skillDealers);
		} catch (BusinessException e) {
			log.error("== Error al tratar de ejecutar la operacion doSkillEvaluation/AttendedLastServiceWithWarrantySkill", e);
			e.printStackTrace();
			throw super.manageException(e, AttendedLastServiceWithWarrantySkill.class.getName());
		}finally{
			if(log.isDebugEnabled()){
				log.debug("== Termina doSkillEvaluation/AttendedLastServiceWithWarrantySkill ==");
			}
		}	
	}
}