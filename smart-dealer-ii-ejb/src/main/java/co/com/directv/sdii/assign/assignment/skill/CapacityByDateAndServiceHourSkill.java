package co.com.directv.sdii.assign.assignment.skill;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.assign.assignment.AssignmentSkill;
import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.assign.assignment.skill.exception.AssignmentSkillException;
import co.com.directv.sdii.assign.schedule.DealerWorkCapacityLoader;
import co.com.directv.sdii.assign.schedule.DealerWorkCapacityLoaderImpl;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacity;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria;
import co.com.directv.sdii.assign.schedule.dto.ScheduleColorsConfig;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.facade.allocator.AllocatorFacadeRemote;
import co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * Habilidad de capacidad por jornada y fecha: Esta habilidad consultar� las Work
 * Orders que tiene asignada una compa��a en una fecha y jornada espec�fica, luego
 * consultar� la tabla de ponderaci�n de la capacidad diaria de atenci�n por
 * semana tipo, por jornada y super categor�a de servicio para establecer si ha
 * copado o no su capacidad de atenci�n, solo se mostrar� capacidad para micro
 * zonas en las que la compa��a haya configurado que tiene cobertura de forma
 * permanente (ver prototipo prototipos_configurador_vX.0.vsd pesta�a config-
 * dealers). Dado el conjunto de compa��as recibidas como entrada, seleccionar�
 * solo las compa��as que tengan capacidad de atenci�n en esa fecha y jornada.
 * Adem�s de consultar las compa��as que tienen capacidad en los d�as
 * seleccionados, se deber� consultar la informaci�n de giras en la micro zona
 * cuyas fechas de programaci�n coincidan con las especificadas, para unir las
 * compa��as que prestan estas giras con las compa��as que tienen capacidad
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public class CapacityByDateAndServiceHourSkill extends AssignmentSkill {
	
	private static Log log = LogFactory.getLog(CapacityByDateAndServiceHourSkill.class);

	public CapacityByDateAndServiceHourSkill(){

	}

	public void finalize() throws Throwable {

	}
	
	/**
	 * realiza la evaluaci�n de la habilidad dados los par�metros
	 * 
	 * @param parameters    par�metros para realizar la evaluaci�n de la habilidad
	 * @throws AssignmentSkillException
	 * @author jnova
	 */
	public List<DealerVO> doSkillEvaluation(SkillEvaluationDTO parameters)throws AssignmentSkillException{
		if(log.isDebugEnabled()){
			log.debug("== Inicia doSkillEvaluation/CapacityByDateAndServiceHourSkill ==");
		}
		try {
			List<DealerVO> skillDealers = new ArrayList<DealerVO>();

			double maxCapacity = 0d;
			int scale = 0;
			BigDecimal big = null;
			AllocatorFacadeRemote allocatorFacadeRemote = AssignmentFacadeLocator.getInstance().getAllocatorFacade();
			CoreAssignmentFacadeRemote coreAssignmentFacadeRemote = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade();
			
			if( parameters != null ){
				if( validateParameters(parameters) ){
					
					DealerWorkCapacityLoader dealerWCapacityLr = new DealerWorkCapacityLoaderImpl();
					
					for( DealerVO dealer : parameters.getDealerList() ){
						
						//Se consulta si el dealer es permanente
						boolean checkDealerPerm = coreAssignmentFacadeRemote.checkDealersInMicrozoneWithTypePerm(parameters.getPostalCode(), 
															   													  parameters.getCountryIso2Code(),
															   													  dealer.getId());
						//el dealer debe ser permanente
						if(!checkDealerPerm)
							continue;
											
						DealerWorkCapacityCriteria dealerWCCriteria = buildDealerWorkCapacityCriteria(parameters,dealer);
						
						DealerWorkCapacity dealerWC = dealerWCapacityLr.loadCapacity(dealerWCCriteria);
						int usedCapacity = coreAssignmentFacadeRemote.getDealerUsedCapacity(dealerWCCriteria);
						ScheduleColorsConfig colorsConfig = allocatorFacadeRemote.loadScheduleColorsConfig(parameters.getServiceSupercategoyId(),
								                                                                           parameters.getCountryId());
						big = new BigDecimal(dealerWC.getMaxCapacity());
						if(parameters.getExecutionMode().equalsIgnoreCase(CodesBusinessEntityEnum.EXECUTION_TYPE_ASSIGNED.getCodeEntity())){

							maxCapacity = (new BigDecimal(dealerWC.getMaxCapacity()).multiply(new BigDecimal(colorsConfig.getScheduleLimit()))).doubleValue();
							big = UtilsBusiness.removeDecimal(maxCapacity,scale,RoundingMode.HALF_UP);

						}
						
						if( dealerWC != null && big.intValue() > usedCapacity )
							skillDealers.add( dealer );
					}
				}
				
				if(parameters.getDealerList() == null || parameters.getDealerList().isEmpty()){
					return skillDealers;
				}
				return intersectDealerList(parameters.getDealerList(), skillDealers);	
			}		
			return null;
		} catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operacion doSkillEvaluation/CapacityByDateAndServiceHourSkill", e);
			e.printStackTrace();
			throw super.manageException(e, CapacityByDateAndServiceHourSkill.class.getName());
		}finally{
			if(log.isDebugEnabled()){
				log.debug("== Termina doSkillEvaluation/CapacityByDateAndServiceHourSkill ==");
			}
		}
	}
	
	/**
	 * 
	 * Metodo: Crea el objeto para realizar los calculos necesarios para la habilidad
	 * @param parameters
	 * @param dealer
	 * @return
	 * @throws AssignmentSkillException <tipo> <descripcion>
	 * @author jnova
	 */
	private DealerWorkCapacityCriteria buildDealerWorkCapacityCriteria(SkillEvaluationDTO parameters,DealerVO dealer) throws AssignmentSkillException{
		log.debug("== Inicia buildDealerWorkCapacityCriteria/CapacityByDateAndServiceHourSkill ==");
		try{
			DealerWorkCapacityCriteria dealerWCCriteria = new DealerWorkCapacityCriteria();
			dealerWCCriteria.setDealerId( dealer.getId() );
			dealerWCCriteria.setCapacityDate( parameters.getScheduleDate() );
			dealerWCCriteria.setCountryId( parameters.getCountryId() );
			dealerWCCriteria.setServiceHourId( parameters.getServiceHourId() );
			dealerWCCriteria.setSuperCategoryServiceId( parameters.getServiceSupercategoyId() );
			return dealerWCCriteria;
		}catch (Throwable e) {
			log.error("== Error buildDealerWorkCapacityCriteria/CapacityByDateAndServiceHourSkill ==",e);
			throw super.manageException(e, CapacityByDateAndServiceHourSkill.class.getName());
		} finally{
			log.debug("== Finaliza buildDealerWorkCapacityCriteria/CapacityByDateAndServiceHourSkill ==");
		}
		
	}
	
	/**
	 * 
	 * Metodo: Valida los parametro para revisar la habilidad
	 * @param parameters
	 * @return boolean true si cumple, false si no
	 * @author jnova
	 */
	private boolean validateParameters(SkillEvaluationDTO parameters){
			
		return ( parameters != null 
					&& parameters.getServiceHourId() != null && parameters.getServiceHourId().longValue() > 0
					&& parameters.getScheduleDate() != null 
					&& parameters.getCountryId() != null && parameters.getCountryId().longValue() > 0
					&& parameters.getDealerList() != null && !parameters.getDealerList().isEmpty() 
					&& parameters.getServiceSupercategoyId() != null && parameters.getServiceSupercategoyId().longValue() > 0);
	}
}