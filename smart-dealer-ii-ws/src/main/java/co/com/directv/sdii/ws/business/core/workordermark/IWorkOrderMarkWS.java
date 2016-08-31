package co.com.directv.sdii.ws.business.core.workordermark;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.WorkOrderMarkVO;
import co.com.directv.sdii.model.vo.WorkOrderWorkOrderMarkVO;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

/**
 * 
 * 
 * Fecha de Creación: 25/06/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@WebService(name="WorkOrderMarkWS",targetNamespace="http://workordermark.core.business.ws.sdii.directv.com.co/")
public interface IWorkOrderMarkWS {
	
	/**
	 * Metodo: Permite consutlar todas las marcas para las workOrders
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName = "getAllWorkOrderMark", action = "getAllWorkOrderMark")
	public List<WorkOrderMarkVO> getAllWorkOrderMark() throws BusinessException;
	
    /**
	 * Metodo: Permite marcar un workorder
	 * @param workOrderId
	 * @param codeWorkOrderMark
	 * @param userId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName = "markWorkOrder", action = "markWorkOrder")
	public WorkOrderWorkOrderMarkVO markWorkOrder(@WebParam(name = "workOrderId") Long workOrderId, 
												  @WebParam(name = "workOrderMarkId") Long workOrderMarkId,
												  @WebParam(name = "userId") Long userId) throws BusinessException;
	
	/**
	 * Metodo: Permite desmarcar un workorder
	 * @param workOrderId
	 * @param codeWorkOrderMark
	 * @param userId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName = "unMarkWorkOrder", action = "unMarkWorkOrder")
	public WorkOrderWorkOrderMarkVO unMarkWorkOrder(@WebParam(name = "workOrderId") Long workOrderId, 
													@WebParam(name = "workOrderMarkId") Long workOrderMarkId,
													@WebParam(name = "userId") Long userId) throws BusinessException;
	
//	/**
//	 * Metodo: Permite determinar si una workOrder requiere contrato.
//	 * @param countryCode .
//	 * @param woCode .
//	 * @param customerCode .
//	 * @return
//	 * @throws BusinessException <tipo> <descripcion>
//	 * @author
//	 */
//	@WebMethod(operationName = "requiredContractWorkOrderContractWorkOrderRequestDTO", action = "requiredContractWorkOrderContractWorkOrderRequestDTO")
//	public boolean requiredContractWorkOrder(@WebParam(name = "request") ContractWorkOrderRequestDTO request) throws BusinessException;
//    
//    /**
//     * Metodo: Permite determinar si una workOrder requiere contrato.
//     * @param countryCode
//     * @param woCode
//     * @param customerCode
//     * @return
//     * @throws BusinessException <tipo> <descripcion>
//     * @author
//     */
//	@WebMethod(operationName = "downLoadContractWorkOrderContractWorkOrderRequestDTO", action = "downLoadContractWorkOrderContractWorkOrderRequestDTO")
//    public FileResponseDTO downLoadContractWorkOrder(@WebParam(name = "request") ContractWorkOrderRequestDTO request) throws BusinessException;
    
	/**
	 * Metodo: Permite determinar si una workOrder requiere contrato.
	 * @param woId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName = "requiredContractWorkOrder", action = "requiredContractWorkOrder")
	public boolean requiredContractWorkOrder(@WebParam(name = "woId") Long woId) throws BusinessException;
	
	/**
	 * Metodo: Permite determinar si una workOrder requiere contrato.
	 * @param woId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName = "downLoadContractWorkOrder", action = "downLoadContractWorkOrder")
	public FileResponseDTO downLoadContractWorkOrder(@WebParam(name = "woId") Long woId) throws BusinessException;
	
		
}
