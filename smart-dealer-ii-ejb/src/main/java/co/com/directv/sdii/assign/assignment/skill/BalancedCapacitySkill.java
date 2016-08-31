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
 * Si la evaluación recibe como entrada más de una compañía:<br>
 * calculará el back log en días para cada una de las compañías suponiendo que se le asignara el trabajo
 * que se está evaluando, la que tenga menos back log en días será la seleccionada.
 * En caso que la evaluación no reciba ninguna compañía como entrada, se obtendrá
 * como base el conjunto de compañías que tengan cobertura en la ubicación
 * geográfica donde se atenderán los servicios al cliente y se realizará el
 * cálculo con este conjunto de compañías para determinar cual tiene menos
 * <b><i>backlog</i></b> en días.
 * 
 * Habilidad de capacidad por balanceo: Cantidad de servicios restantes que puede
 * recibir un Dealer/ HSP para alcanzar el volumen de trabajos permitidos según su
 * capacidad de resolución diaria y la cantidad de días indicado por el backlog
 * permitido en días, se define por la ecuación (capacidadTotalDia *
 * BackLogPermitidoDia) - BackLog
 * <b>
 * </b><b>Backlog: </b>Cantidad total de Work Orders asignadas a una compañía
 * instaladora y en estado asignado, reasignado, agendado, reagendado o pendiente;
 * a la fecha por Categoróa de servicio.
 * <b>
 * </b><b>Backlog Permitido en días: </b>Cantidad de días de trabajo que puede
 * tener acumulado un Dealer/ HSP por Categoría de servicio y tipo de zona (urbana
 * o rural).  (Backlog/CapacidadTotalDia). El valor objetivo a alcanzar debe ser
 * parametrizable en la aplicación por el usuario, se configurará mediante la
 * pantalla de KPI en la sección del KPI de backlog por días.
 * @author jjimenezh
 * @author jnova
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public class BalancedCapacitySkill extends AssignmentSkill {
	
	private static Log log = LogFactory.getLog(BalancedCapacitySkill.class);

	public BalancedCapacitySkill(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * realiza la evaluación de la habilidad dados los parámetros
	 * 
	 * @param parameters parámetros para realizar la evaluación de la habilidad
	 * @return
	 * @throws AssignmentSkillException
	 * @author jnova
	 */
	public List<DealerVO> doSkillEvaluation(SkillEvaluationDTO parameters)throws AssignmentSkillException{
		if(log.isDebugEnabled()){
			log.debug("== Inicia doSkillEvaluation/BalancedCapacitySkill ==");
		}
		try {
			//Si la habilidad recibe como entrada una lista de compañías vacía o nula, deberá consultar las compañías con cobertura en el código postal
			if(parameters.getDealerList() == null || parameters.getDealerList().isEmpty()){
				List<DealerVO> coverageDealers = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealersInMicrozoneWithExMode(parameters.getExecutionMode(), parameters.getPostalCode(), parameters.getCountryIso2Code(),null);
				if(coverageDealers.isEmpty()){
					return new ArrayList<DealerVO>();
				}
				parameters.setDealerList(coverageDealers);
			}
			
			List<DealerVO> skillDealers = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealersForBalancedCapacitySkill(parameters);
			if(parameters.getDealerList() == null || parameters.getDealerList().isEmpty()){
				return skillDealers;
			}
			return intersectDealerList(parameters.getDealerList(), skillDealers);
		} catch (BusinessException e) {
			log.error("== Error al tratar de ejecutar la operacion doSkillEvaluation/BalancedCapacitySkill", e);
			e.printStackTrace();
			throw super.manageException(e, BalancedCapacitySkill.class.getName());
		}finally{
			if(log.isDebugEnabled()){
				log.debug("== Termina doSkillEvaluation/BalancedCapacitySkill ==");
			}
		}
	}
}