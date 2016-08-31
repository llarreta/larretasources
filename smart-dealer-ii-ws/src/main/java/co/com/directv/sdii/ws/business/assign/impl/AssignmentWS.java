/**
 * Creado 14/06/2011 09:45:53
 */
package co.com.directv.sdii.ws.business.assign.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.assign.assignment.AssignmentFacade;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.ws.business.assign.IAssignmentWS;
import co.com.directv.sdii.ws.model.dto.AssignRequestDTO;
import co.com.directv.sdii.ws.model.dto.AssignResponseDTO;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 14/06/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@MTOM
@WebService(serviceName="AssignmentWSService",
		endpointInterface="co.com.directv.sdii.ws.business.assign.IAssignmentWS",
		targetNamespace="http://assign.business.ws.sdii.directv.com.co/",
		portName="AssignmentWSPort")
@Stateless()
public class AssignmentWS implements IAssignmentWS {

	@EJB(name="AssignmentFacade", beanInterface=AssignmentFacade.class)
	private AssignmentFacade assignmentFacade;
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentWS#assignDealer(co.com.directv.sdii.ws.model.dto.AssignRequestDTO)
	 */
	@Override
	public AssignResponseDTO assignDealer(AssignRequestDTO request)
			throws BusinessException {
		return assignmentFacade.assignDealer(request);
	}

}
