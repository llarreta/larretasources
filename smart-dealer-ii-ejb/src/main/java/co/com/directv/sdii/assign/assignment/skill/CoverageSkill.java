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
 * Habilidad de cobertura: Busca los Dealers que prestan cualquier servicio en la
 * micro zona de b�squeda, se tendr� la opci�n de marcar que la cobertura de la
 * compa��a instaladora en la micro zona seleccionada es ocasional o permanente;
 * cuando el componente se ejecute en modalidad de agenda, se tendr�n en cuenta
 * las coberturas de las compa��as marcadas como permanentes y cuando se ejecute
 * en modo asignaci�n, se tendr�n en cuenta las dos. La modalidad ocasional
 * permite que una compa��a preste servicios solo ciertos d�as de la semana o
 * ciertos d�as en el mes
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public class CoverageSkill extends AssignmentSkill {
	
	private static Log log = LogFactory.getLog(CoverageSkill.class);

	public CoverageSkill(){

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
			log.debug("== Inicia doSkillEvaluation/CoverageSkill ==");
		}
		try {
			List<DealerVO> skillDealers = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealersInMicrozoneWithExMode(parameters.getExecutionMode(), parameters.getPostalCode(), parameters.getCountryIso2Code(),null);
			if(parameters.getDealerList() == null || parameters.getDealerList().isEmpty()){
				return skillDealers;
			}
			return intersectDealerList(parameters.getDealerList(), skillDealers);
		} catch (BusinessException e) {
			log.error("== Error al tratar de ejecutar la operacion doSkillEvaluation/CoverageSkill", e);
			e.printStackTrace();
			throw super.manageException(e, CoverageSkill.class.getName());
		}finally{
			if(log.isDebugEnabled()){
				log.debug("== Termina doSkillEvaluation/CoverageSkill ==");
			}
		}
	}
}