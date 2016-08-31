package co.com.directv.sdii.facade.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ContractWorkOrderRequestDTO;
import co.com.directv.sdii.model.vo.WorkOrderMarkVO;
import co.com.directv.sdii.model.vo.WorkOrderWorkOrderMarkVO;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

/**
 * Encapsula la fachada para marcar la workOrder
 * 
 * Fecha de Creación: 1/10/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface WorkOrderMarkFacadeLocal {
	
	/**
     * Metodo: Permite consutlar todas las marcas para las workOrders
     * @return
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
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
	public WorkOrderWorkOrderMarkVO markWorkOrder(Long workOrderId, 
            Long workOrderMarkId,
            Long userId) throws BusinessException;
	
	/**
	 * Metodo: Permite desmarcar un workorder
	 * @param workOrderId
	 * @param codeWorkOrderMark
	 * @param userId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public WorkOrderWorkOrderMarkVO unMarkWorkOrder(Long workOrderId, 
         Long workOrderMarkId,
         Long userId) throws BusinessException;
	
	/**
	 * Metodo: Permite determinar si una workOrder requiere contrato.
	 * @param countryCode .
	 * @param woCode .
	 * @param customerCode .
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public boolean requiredContractWorkOrder(ContractWorkOrderRequestDTO request) throws BusinessException;
    
    /**
     * Metodo: Permite determinar si una workOrder requiere contrato.
     * @param countryCode
     * @param woCode
     * @param customerCode
     * @return
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public FileResponseDTO downLoadContractWorkOrder(ContractWorkOrderRequestDTO request) throws BusinessException;
    
	/**
	 * Metodo: Permite determinar si una workOrder requiere contrato.
	 * @param woId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public boolean requiredContractWorkOrder(Long woId) throws BusinessException;
	
	/**
	 * Metodo: Permite determinar si una workOrder requiere contrato.
	 * @param woId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public FileResponseDTO downLoadContractWorkOrder(Long woId) throws BusinessException;
	
}
