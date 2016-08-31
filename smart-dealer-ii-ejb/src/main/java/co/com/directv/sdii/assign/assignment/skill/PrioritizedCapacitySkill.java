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
 * Habilidad capacidad por prioridad: si la evaluación recibe como entrada más de
 * una compañía, buscará la configuración de prioridad por microzona para
 * establecer el orden de asignación de cada compañía, una vez establecida la
 * prioridad, se procederá a calcular el backlog en días de la compañía con mayor
 * prioridad, si este valor es inferior al back log permitido en días para la
 * super categoría del primer servicio recibido como parámetro y el tipo de zona especificado, 
 * se elegirá esa compañía,de lo contrario se realizará el cálculo para la próxima compañía en orden de
 * prioridad, una vez todas las compañías hayan alcanzado el límite de back log
 * permitido en días, se devolverá únicamente la primer compañía en el orden de
 * prioridad.  Si se identifica que la capacidad para atención alcanza o supera la
 * capacidad del Dealer con mayor prioridad se generará un e-mail de alerta al los usuarios
 * con el rol backoffice de DIRECTV en el país seleccionado
 * @author jjimenezh
 * @author jnova
 * @version 1.0,  &nbsp; 26-may-2011 15:11:47
 */
public class PrioritizedCapacitySkill extends AssignmentSkill {
	
	private static Log log = LogFactory.getLog(PrioritizedCapacitySkill.class);

	public PrioritizedCapacitySkill(){

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
			log.debug("== Inicia doSkillEvaluation/PrioritizedCapacitySkill ==");
		}
		try {
			List<DealerVO> skillDealers = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealersForPrioritizedCapacitySkill(parameters);
			if(parameters.getDealerList() == null || parameters.getDealerList().isEmpty()){
				return skillDealers;
			}
			return intersectDealerList(parameters.getDealerList(), skillDealers);			
		} catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operacion doSkillEvaluation/PrioritizedCapacitySkill", e);
			e.printStackTrace();
			throw super.manageException(e, PrioritizedCapacitySkill.class.getName());
		}finally{
			if(log.isDebugEnabled()){
				log.debug("== Termina doSkillEvaluation/PrioritizedCapacitySkill ==");
			}
		}
	}
}