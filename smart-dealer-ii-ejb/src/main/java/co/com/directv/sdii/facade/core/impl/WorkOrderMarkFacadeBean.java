package co.com.directv.sdii.facade.core.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.WorkOrderMarkFacadeLocal;
import co.com.directv.sdii.model.dto.ContractWorkOrderRequestDTO;
import co.com.directv.sdii.model.vo.WorkOrderMarkVO;
import co.com.directv.sdii.model.vo.WorkOrderWorkOrderMarkVO;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

/**
 *  Clase que implementa las operaciones para workOrderMark
 * 
 * Fecha de Creación: 20/09/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="WorkOrderMarkFacadeLocal")
@Local({WorkOrderMarkFacadeLocal.class})
public class WorkOrderMarkFacadeBean implements WorkOrderMarkFacadeLocal {

	@EJB(name="WorkOrderMarkBusinessBeanLocal",beanInterface=WorkOrderMarkBusinessBeanLocal.class)
	private WorkOrderMarkBusinessBeanLocal workOrderMarkBusinessBean;
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.WorkOrderMarkFacadeLocal#getAllWorkOrderMark()
	 */
	public List<WorkOrderMarkVO> getAllWorkOrderMark() throws BusinessException{
		return workOrderMarkBusinessBean.getAllWorkOrderMark();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.WorkOrderMarkFacadeLocal#markWorkOrder(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	public WorkOrderWorkOrderMarkVO markWorkOrder(Long workOrderId, 
            Long workOrderMarkId,
            Long userId) throws BusinessException {
        		return workOrderMarkBusinessBean.markWorkOrder(workOrderId, 
        				workOrderMarkId,
        	            userId);
        	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.WorkOrderMarkFacadeLocal#unMarkWorkOrder(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	public WorkOrderWorkOrderMarkVO unMarkWorkOrder(Long workOrderId, 
         Long workOrderMarkId,
         Long userId) throws BusinessException{
     		return workOrderMarkBusinessBean.unMarkWorkOrder(workOrderId, 
     				workOrderMarkId,
										     		         userId);
    }
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.WorkOrderMarkFacadeLocal#requiredContractWorkOrder(co.com.directv.sdii.model.dto.ContractWorkOrderRequestDTO)
	 */
	public boolean requiredContractWorkOrder(ContractWorkOrderRequestDTO request) throws BusinessException{
 		return workOrderMarkBusinessBean.requiredContractWorkOrder(request);
 	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.core.WorkOrderMarkFacadeLocal#downLoadContractWorkOrder(co.com.directv.sdii.model.dto.ContractWorkOrderRequestDTO)
     */
    public FileResponseDTO downLoadContractWorkOrder(ContractWorkOrderRequestDTO request) throws BusinessException{
 		return workOrderMarkBusinessBean.downLoadContractWorkOrder(request);
 	}
    
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.WorkOrderMarkFacadeLocal#requiredContractWorkOrder(java.lang.Long)
	 */
	public boolean requiredContractWorkOrder(Long woId) throws BusinessException{
 		return workOrderMarkBusinessBean.requiredContractWorkOrder(woId);
 	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.WorkOrderMarkFacadeLocal#downLoadContractWorkOrder(java.lang.Long)
	 */
	public FileResponseDTO downLoadContractWorkOrder(Long woId) throws BusinessException{
 		return workOrderMarkBusinessBean.downLoadContractWorkOrder(woId);
 	}
		
}
