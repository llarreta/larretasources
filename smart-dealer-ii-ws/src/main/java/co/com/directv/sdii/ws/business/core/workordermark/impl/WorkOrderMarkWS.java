package co.com.directv.sdii.ws.business.core.workordermark.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.WorkOrderMarkFacadeLocal;
import co.com.directv.sdii.model.vo.WorkOrderMarkVO;
import co.com.directv.sdii.model.vo.WorkOrderWorkOrderMarkVO;
import co.com.directv.sdii.reports.dto.FileResponseDTO;
import co.com.directv.sdii.ws.business.core.workordermark.IWorkOrderMarkWS;

@MTOM(threshold=3072)
@WebService(serviceName="WorkOrderMarkService",
		endpointInterface="co.com.directv.sdii.ws.business.core.workordermark.IWorkOrderMarkWS",
		targetNamespace="http://workordermark.core.business.ws.sdii.directv.com.co/",
		portName="WorkOrderMarkPort")	
@Stateless()
public class WorkOrderMarkWS implements IWorkOrderMarkWS{
	
	@EJB
	private WorkOrderMarkFacadeLocal workOrderMarkFacade;
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.workordermark.IWorkOrderMarkWS#getAllWorkOrderMark()
	 */
	public List<WorkOrderMarkVO> getAllWorkOrderMark() throws BusinessException{
		
		return workOrderMarkFacade.getAllWorkOrderMark();
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.workordermark.IWorkOrderMarkWS#markWorkOrder(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	public WorkOrderWorkOrderMarkVO markWorkOrder(Long workOrderId, 
												  Long workOrderMarkId,
												  Long userId) throws BusinessException{
		
		return workOrderMarkFacade.markWorkOrder(workOrderId, workOrderMarkId,userId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.workordermark.IWorkOrderMarkWS#unMarkWorkOrder(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	public WorkOrderWorkOrderMarkVO unMarkWorkOrder(Long workOrderId, 
													Long workOrderMarkId,
													Long userId) throws BusinessException{
		return workOrderMarkFacade.unMarkWorkOrder(workOrderId,workOrderMarkId,userId);
	}
	
//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.ws.business.core.workordermark.IWorkOrderMarkWS#requiredContractWorkOrder(co.com.directv.sdii.model.dto.ContractWorkOrderRequestDTO)
//	 */
//	public boolean requiredContractWorkOrder(ContractWorkOrderRequestDTO request) throws BusinessException{
// 		return workOrderMarkFacade.requiredContractWorkOrder(request);
// 	}
//    
//    /* (non-Javadoc)
//     * @see co.com.directv.sdii.ws.business.core.workordermark.IWorkOrderMarkWS#downLoadContractWorkOrder(co.com.directv.sdii.model.dto.ContractWorkOrderRequestDTO)
//     */
//    public FileResponseDTO downLoadContractWorkOrder(ContractWorkOrderRequestDTO request) throws BusinessException{
// 		return workOrderMarkFacade.downLoadContractWorkOrder(request);
// 	}
    
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.workordermark.IWorkOrderMarkWS#requiredContractWorkOrder(java.lang.Long)
	 */
	public boolean requiredContractWorkOrder(Long woId) throws BusinessException{
 		return workOrderMarkFacade.requiredContractWorkOrder(woId);
 	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.workordermark.IWorkOrderMarkWS#downLoadContractWorkOrder(java.lang.Long)
	 */
	public FileResponseDTO downLoadContractWorkOrder(Long woId) throws BusinessException{
 		return workOrderMarkFacade.downLoadContractWorkOrder(woId);
 	}
	
}
