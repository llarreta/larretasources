package co.com.directv.sdii.ws.business.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.RefConfirmationFacadeBeanLocal;
import co.com.directv.sdii.model.vo.RefConfirmationVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.ws.business.stock.IReferenceConfirmationWS;

@MTOM
@WebService(serviceName="ReferenceConfirmationService",
		endpointInterface="co.com.directv.sdii.ws.business.stock.IReferenceConfirmationWS",
		targetNamespace="http://stock.business.ws.sdii.directv.com.co/",
		portName="ReferenceConfirmationPort")
@Stateless()
public class ReferenceConfirmationWS implements IReferenceConfirmationWS {

	@EJB
	private RefConfirmationFacadeBeanLocal ejbRefConfirmation;
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceConfirmation#saveAllNotSerializedReferenceElementItemConfirmation(co.com.directv.sdii.model.vo.RefConfirmationVO,co.com.directv.sdii.model.vo.UserVO,java.lang.Long)
	 */
	@Override
	public void saveAllNotSerializedReferenceElementItemConfirmation(List<RefConfirmationVO> confirmations , UserVO user,Long referenceId) throws BusinessException {
		ejbRefConfirmation.saveAllNotSerializedReferenceElementItemConfirmation(confirmations,user,referenceId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceConfirmation#saveSerializedReferenceElementItemConfirmation(java.util.List<co.com.directv.sdii.model.vo.RefConfirmationVO>,co.com.directv.sdii.model.vo.UserVO,java.lang.Long)
	 */
	@Override
	public void saveSerializedReferenceElementItemConfirmation(List<RefConfirmationVO> confirmation , UserVO user,Long referenceId) throws BusinessException {
		ejbRefConfirmation.saveSerializedReferenceElementItemConfirmation(confirmation,user,referenceId);
	}
}
