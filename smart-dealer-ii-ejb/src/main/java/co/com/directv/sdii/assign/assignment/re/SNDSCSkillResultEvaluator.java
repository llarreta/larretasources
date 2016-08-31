package co.com.directv.sdii.assign.assignment.re;

import java.util.ArrayList;
import java.util.List;

import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * Implementación del evaluador de resultado de habilidades que no lanza error en
 * caso que el resultado sea vacio sino que devuelve la lista de entrada de antes
 * de evaluar la habilidad
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:48
 */
public class SNDSCSkillResultEvaluator implements AssignmentSkillResultEvaluator {

	public SNDSCSkillResultEvaluator(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * realiza la evaluación concreta del resultado de la ejecución de una habilidad
	 * 
	 * @param currentSkillResult Conjunto de resultado de la evaluación de la habilidad
	 * @param previousSkillResult Conjunto inicial de compañías antes de realizar la
	 * evaluación de la habilidad
	 */
	public List<DealerVO> evaluateResult(List<DealerVO> currentSkillResult, List<DealerVO> previousSkillResult, SkillEvaluationDTO skillEvalParameters){
		if(currentSkillResult == null || currentSkillResult.isEmpty()){
			List<DealerVO> result = new ArrayList<DealerVO>();
			result.addAll(previousSkillResult);
			return result;
		}else{
			return currentSkillResult;
		}
	}
}