package co.com.directv.sdii.ws.business.assign.test.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.assign.assignment.AssignmentFacade;
import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.assign.assignment.skill.exception.AssignmentSkillException;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.ws.business.assign.test.ISkillTestWS;

/**
 * Servicio web que expone las operaciones relacionadas con Building
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.BuildingFacadeBeanLocal
 */
@MTOM
@WebService(serviceName="SkillTestWSService",
		endpointInterface="co.com.directv.sdii.ws.business.assign.test.ISkillTestWS",
		targetNamespace="http://assign.test.business.ws.sdii.directv.com.co/",
		portName="SkillTestWSPort")
@Stateless()
public class SkillTestWS implements ISkillTestWS {

	@EJB(name="AssignmentFacade", beanInterface=AssignmentFacade.class)
	private AssignmentFacade assignmentFacade;
	
	@Override
	public List<DealerVO> testDoSkillEvaluation(SkillEvaluationDTO parameters, String skillCode)
			throws AssignmentSkillException {
		return assignmentFacade.doSkillEvaluation(parameters, skillCode);
	}
	
	
	
}
