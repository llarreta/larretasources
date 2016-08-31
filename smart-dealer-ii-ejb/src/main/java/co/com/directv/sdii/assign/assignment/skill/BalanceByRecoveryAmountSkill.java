package co.com.directv.sdii.assign.assignment.skill;
import java.util.List;

import co.com.directv.sdii.assign.assignment.AssignmentSkill;
import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.assign.assignment.skill.exception.AssignmentSkillException;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * Habilidad de balanceo por monto a recuperar: Valida que el valor a recuperar en
 * todas las WO asignadas se distribuya equitativamente entre todos los Dealers
 * que prestan los servicios de cobranza, para realizar esta evaluación:
 * 
 * 1. Dada la lista de entrada de compañías, por cada compañía calcula el monto
 * que tiene asignado y pendiente de recuperar: una consulta sumando el wo_description de las 
 * work orders de la categoría de cobranza, ya que esas wo's tienen el monto a recuperar en
 * ese campo.
 * 2. Dado el monto de cada compañía, asigna selecciona la compañía o compañías que tienen
 * asignado el menor monto a recuperar.
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public class BalanceByRecoveryAmountSkill extends AssignmentSkill {

	public BalanceByRecoveryAmountSkill(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * realiza la evaluaci�n de la habilidad dados los par�metros
	 * 
	 * @param parameters    par�metros para realizar la evaluaci�n de la habilidad
	 * @return
	 * @throws AssignmentSkillException
	 */
	public List<DealerVO> doSkillEvaluation(SkillEvaluationDTO parameters)throws AssignmentSkillException{
		return null;
	}
}