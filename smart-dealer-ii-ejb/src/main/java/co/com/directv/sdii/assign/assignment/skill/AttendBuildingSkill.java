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
 * Habilidad atiende edificio:
 * Busca los Dealers que prestan servicios en edificios ó el 
 * Dealer que atiende un edificio especifico si este está configurado
 *
 *para evaluar esta habilidad:
 *0. Si el código de edificio viene nulo se devuelve una lista vacia. OK
 *1. Consultar con el código de edificio la tabla building, si el edificio existe continúa con el paso 2, si no existe continúa con el paso 4:
 *2. Consultar si alguno de los dealers recibidos como entrada tiene configurado ese edificio en la tabla dealers_buildings, 
 *	 si existe la configuración con alguno de los dealers de entrada de devolverán los que atiendan el edificio
 *3. Si existe la configuración de un dealer que atiende el edificio pero este no se encuentra en la lista 
 *   de entrada, la habilidad retorna una lista vacia
 *4. Si no existe la configuración de un dealer que atiende el edificio, se consulta dentro de los dealers de entrada los que tienen en
 *   la tabla dealer_details en la columna attend_buildings marcada con una S y se devuelven los que cumplen con esto, si ninguno cumple,
 *   se devuelve una lista vacia
 *5. Si se recibe como entrada una lista vacia se devuelven los dealers que atienden el edificio
 *   
 * 
 * Fecha de Creación: 09/06/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class AttendBuildingSkill extends AssignmentSkill {
	
	private static Log log = LogFactory.getLog(AttendBuildingSkill.class);

	public AttendBuildingSkill() {

	}

	public void finalize() throws Throwable {

	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.assign.assignment.AssignmentSkill#doSkillEvaluation(co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO)
	 */
	@Override
	public List<DealerVO> doSkillEvaluation(SkillEvaluationDTO parameters)
			throws AssignmentSkillException{
		
		List<DealerVO> listDealers = null;
		try {
			if(log.isDebugEnabled()){
				log.debug("== Inicia doSkillEvaluation/AttendBuildingSkill ==");
			}
			
			//ibsBuildingCode = parameters.getIbsBuildingCode();
			Long longBuildingId = null;
			if ( parameters.getIbsBuildingCode() != null){
				longBuildingId = Long.parseLong(parameters.getIbsBuildingCode());
			}
			
			//Req-0096 - Requerimiento Consolidado Asignador / Automatizacion, Relacion de Edificios para Asignacion de Work Orders
			List<DealerVO> skillDealers = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealersWhoAttendsABuilding(longBuildingId);
			
			listDealers = parameters.getDealerList() ; 
			if(  listDealers==null || listDealers.isEmpty()){
				return skillDealers;
			}
			return intersectDealerList(listDealers, skillDealers);			
		}catch (BusinessException e) {
			log.error("== Error al tratar de ejecutar la operacion doSkillEvaluation/AttendBuildingSkill", e);
			e.printStackTrace();
			throw super.manageException(e, AttendBuildingSkill.class.getName());
		}finally{
			if(log.isDebugEnabled()){
				log.debug("== Termina doSkillEvaluation/AttendBuildingSkill ==");
			}
		}		
	}

}
