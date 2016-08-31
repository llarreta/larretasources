package co.com.directv.sdii.assign.assignment.re;

import java.util.List;

import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * Define las operaciones de evaluación del resultado de una habilidad permitiendo
 * el encapsulamiento de dos comportamientos diferentes:
 * 1. Si el resultado de una habilidad es una lista vacia se deberá lanzar una
 * excepción
 * 2. Si el resultado de una habilidad es una lista vacia se deberá retornar una
 * lista igual a la lista de entrada de la habilidad anterior.
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public interface AssignmentSkillResultEvaluator {

	/**
	 * realiza la evaluación concreta del resultado de la ejecución de una habilidad
	 * @param currentSkillResult Conjunto de resultado de la evaluación de la habilidad
	 * @param previousSkillResult Conjunto inicial de compañías antes de realizar la
	 * evaluación de la habilidad
	 * @param skillEvalParameters
	 * @return
	 * @throws BusinessException
	 */
	public List<DealerVO> evaluateResult(List<DealerVO> currentSkillResult, List<DealerVO> previousSkillResult, SkillEvaluationDTO skillEvalParameters)throws BusinessException;

}