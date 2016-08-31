package co.com.directv.sdii.ws.business.stock.test.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.CoreWOFacadeLocal;
import co.com.directv.sdii.model.dto.IBSMoveResourceBetweenCustAndDealerDTO;
import co.com.directv.sdii.model.vo.WorkOrderServiceElementVO;
import co.com.directv.sdii.ws.business.stock.test.IStockTestWS;

@MTOM
@WebService(serviceName="StockTestService",
		endpointInterface="co.com.directv.sdii.ws.business.stock.test.IStockTestWS",
		targetNamespace="http://stock.business.ws.sdii.directv.com.co/",
		portName="StockTestWSPort")
@Stateless()
public class StockTestWS implements IStockTestWS {

	@EJB
	private CoreWOFacadeLocal coreWOFacade;
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.test.IReferenceTestWS#notify2IbsRecoveryOfSerializedElements(java.lang.Long, java.lang.Long, java.lang.Long, java.util.List)
	 */
	@Override
	public List<IBSMoveResourceBetweenCustAndDealerDTO> notify2IbsRecoveryOfSerializedElements(
			Long dealerId, Long customerId, Long countryId,
			List<WorkOrderServiceElementVO> installationSerializedElements)
			throws BusinessException {
		return null;//coreWOFacade.notify2IbsRecoveryOfSerializedElements(dealerId, customerId, countryId, installationSerializedElements);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.test.IReferenceTestWS#notify2IbsSerializedElementsUsedInService(java.lang.Long, java.lang.Long, java.lang.Long, java.util.List)
	 */
	@Override
	public List<IBSMoveResourceBetweenCustAndDealerDTO> notify2IbsSerializedElementsUsedInService(
			Long dealerId, Long customerId, Long countryId,
			List<WorkOrderServiceElementVO> installationSerializedElements)
			throws BusinessException {
		return null;//coreWOFacade.notify2IbsSerializedElementsUsedInService(dealerId, customerId, countryId, installationSerializedElements);
	}

}
