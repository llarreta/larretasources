package co.com.directv.sdii.assign.assignment.skill;

import java.util.ArrayList;
import java.util.Date;
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
 * Habilidad de otros servicios a cliente:  Eval�a si un cliente tiene otra Wo en
 * estado (asignada, reasignada,  agendada, reagendada o pendiente) para enviar la
 * misma compa��a instaladora a atender la nueva Wo. Se deben cumplir las
 * siguientes condiciones:
 * <ol>
 * 	<li>La segunda work order debe ser del mismo cliente que la primera</li>
 * 	<li>La direcci�n de atenci�n de las work orders debe ser la misma.</li>
 * 	<li>La fecha de atenci�n de las Work orders debe coincidir.</li>
 * </ol>
 * Condiciones especiales:
 * <ol>
 * 	<li>Si la primera work order (la que ya tiene asignaci�n a compa��a) tiene
 * fecha, la segunda no y la fecha es por lo menos del dia siguiente, se asignar
 * a la misma compania.</li>
 * 	<li>Si la primera no tiene fecha y la segunda si, la habilidad no seleccionar
 * ninguna compania.</li>
 * </ol>
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:47
 */
public class OtherServices2CustomerSkill extends AssignmentSkill {
	private static Log log = LogFactory.getLog(OtherServices2CustomerSkill.class);
	
	public OtherServices2CustomerSkill(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * realiza la evaluaci�n de la habilidad dados los par�metros
	 * 
	 * @param parameters    parametros para realizar la evaluacion de la habilidad
	 */
	public List<DealerVO> doSkillEvaluation(SkillEvaluationDTO parameters)throws AssignmentSkillException{
		String countryIso2Code = "" ;
		String ibsCustomerCode = "" ;
		String postalCode = "" ;
		String workOrderAddress = "" ;
		String workOrderAddressCode = "" ;
		Date   scheduleDate = null ; 
		DealerVO dealerVO = null ;
		List<DealerVO> intersectionList = null ;
		
		if(log.isDebugEnabled()){
			log.debug("== Inicia doSkillEvaluation/OtherServices2CustomerSkill ==");
		}
		
		ibsCustomerCode = parameters.getIbsCustomerCode();
		countryIso2Code = parameters.getCountryIso2Code();
		postalCode = parameters.getPostalCode();
		workOrderAddress = parameters.getAddress();
		workOrderAddressCode = parameters.getAddressCode();
		scheduleDate = parameters.getScheduleDate();
		List<DealerVO> skillDealers= (List<DealerVO>) new ArrayList<DealerVO>();
		/* Vamos a buscar un work order que tenga como cliente, un cliente con 'ibsCustomerCode' */
		try { 
			dealerVO = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().otherServices2CustmerSkill(countryIso2Code, ibsCustomerCode, postalCode, scheduleDate, workOrderAddressCode, workOrderAddress) ;
			
			if ( dealerVO != null ) {
			   skillDealers.add(dealerVO);
			}

			intersectionList = intersectDealerList(parameters.getDealerList(), skillDealers); 
			return intersectionList ;
		} catch (BusinessException e) {
			log.error("== Error al tratar de ejecutar la operacion doSkillEvaluation/OtherServices2CustomerSkill", e);
			e.printStackTrace();
			throw super.manageException(e, OtherServices2CustomerSkill.class.getName());
		}finally{
			if(log.isDebugEnabled()){
				log.debug("== Termina doSkillEvaluation/OtherServices2CustomerSkill ==");
			}
		}
		
		//return null ;
	}
}