package co.com.directv.sdii.assign.assignment.re;

import java.util.ArrayList;
import java.util.List;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * Implementación del evaluador del resultado de una habilidad que devuelve una lista con el dealer sin cobertura
 * en caso que la lista de la habilidad haya retornado vacio.
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:47
 */
public class NDSCSkillResultEvaluator implements AssignmentSkillResultEvaluator {

	public NDSCSkillResultEvaluator(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * realiza la evaluación concreta del resultado de la ejecución de una habilidad
	 * 
	 * @param currentSkillResult Conjunto de resultado de la evaluación de la habilidad
	 * @param previousSkillResult Conjunto inicial de compañías antes de realizar la
	 * evaluación de la habilidad
	 * @throws BusinessException 
	 */
	public List<DealerVO> evaluateResult(List<DealerVO> currentSkillResult, List<DealerVO> previousSkillResult, SkillEvaluationDTO skillEvalParameters) throws BusinessException{
		if(currentSkillResult == null || currentSkillResult.isEmpty()){
			List<DealerVO> result = new ArrayList<DealerVO>();
			DealerVO dealer = new DealerVO();
			Long dealerWoutCoverageCode = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealerCodeWoutCoverage(skillEvalParameters.getCountryId());
			dealer.setDealerCode(dealerWoutCoverageCode);
			result.add(dealer);
			return result;
		}else{
			return currentSkillResult;
		}
	}
}