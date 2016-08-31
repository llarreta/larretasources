package co.com.directv.sdii.ejb.business.assign;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.ContractWorkOrderRequestDTO;
import co.com.directv.sdii.model.dto.WorkOrderDTO;
import co.com.directv.sdii.model.dto.WorkOrderMarkDTO;
import co.com.directv.sdii.model.pojo.WorkOrderService;
import co.com.directv.sdii.model.vo.WorkOrderMarkVO;
import co.com.directv.sdii.model.vo.WorkOrderWorkOrderMarkVO;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

@Local
public interface WorkOrderMarkBusinessBeanLocal {

    /**
     * Metodo: Permite crear una orden de servicio
     * @param workOrderMark
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public void createWorkOrderMark(WorkOrderMarkVO workOrderMarkVO) throws BusinessException;

    /**
     * Metodo: permite consutlar una orden de servicio por su ID
     * @param id
     * @return
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public WorkOrderMarkVO getWorkOrderMarkByID(Long id) throws BusinessException;
    
    /**
     * Metodo: permite consultar un codigo de WorkOrderMark de servicio por su ID
     * @param id
     * @return
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public String getCodeWorkOrderMarkByID(Long id) throws BusinessException;
   
    /**
     * Metodo: permite consultar un WorkOrderMark por su code
     * @param code
     * @return
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public WorkOrderMarkVO getWorkOrderMarkByCode(String code) throws BusinessException;
    
    /**
     * Metodo: Permite actualizar una orden de servicio
     * @param obj
     * @return
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public Object updateWorkOrderMark(WorkOrderMarkVO obj) throws BusinessException;

    /**
     * Metodo: Permite eliminar una orden de servicio
     * @param obj
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public void deleteWorkOrderMark(WorkOrderMarkVO obj) throws BusinessException;

    /**
     * Metodo: Permite consutlar todas las ordenes de servicio
     * @return
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public List<WorkOrderMarkVO> getAllWorkOrderMark() throws BusinessException;
    
    
    /**
     * Metodo: Permite obtener las workorderworkOrdermark activas y que se deben desmarcar en la atencion por un workorder id
     * @param woId
     * @return
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public List<WorkOrderWorkOrderMarkVO> getWorkOrderWorkOrderMarkIsActiveAndIsUnMarkAttention(Long woId) throws BusinessException;
    
    /**
     * Metodo: Permite obtener las workorderworkOrdermark que se deben desmarcar en la atencion por un workorder id
     * @param woId
     * @return
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public List<WorkOrderWorkOrderMarkVO> getWorkOrderWorkOrderMarkIsUnMarkAttention(Long woId) throws BusinessException;
    
    /**
     * Metodo: Permite obtener las workordermark activas por un workorder id
     * @param workOrderId
     * @return
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public List<WorkOrderMarkDTO> getWorkOrderMarkDTOIsActiveByWoId(Long workOrderId) throws BusinessException;
    
    
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
     * Metodo: Permite desmarcar las workOrder que tienen el campo IS_UNMARK_ATTENTION en S 
     * @param workOrderId
     * @param workOrderMarkId
     * @param userId
     * @return
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
	public void unMarkWorkOrderAttention(Long workOrderId,Long userId) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar si la workorder esta marcada por un tipo de marca
	 * @param workOrderCodes
	 * @param workOrderMarkId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<WorkOrderWorkOrderMarkVO> getWorkOrderWorkOrderMarkIsActiveByWoCodesAndCodeWorkOrderMark(List<String> workOrderCodes, 
            Long workOrderMarkId) throws BusinessException;
	
    /**
     * Metodo: Permite marcar o desmarcar una workOrder
     * @param woId
     * @param workOrderMarkId
     * @param userId
     * @param markOrUnMark
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public void markOrUnMarkWorkOrder(Long woId,Long workOrderMarkId,Long userId,boolean markOrUnMark) throws BusinessException;
	
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
	
	/**
	 * Metodo: Permite obtener el serviceIbsCode de los servicios de apertura de la workOrder
	 * @param workOrderServices
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public String getServiceIbsCode(Set<WorkOrderService> workOrderServices) throws BusinessException;
	
	/**
	 * Metodo: Permite obtener si se debe marcar la workOrder por requiere contrato
	 * @param workOrderDto
	 * @throws PropertiesException
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	public void populateRequiredContract(WorkOrderDTO workOrderDto);

	/**
	 * Metodo: Permite obtener si se debe marcar la workOrder por servicio de prioridad
	 * @param workOrderDto
	 * @throws PropertiesException
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void populateWorkOrderMarkPriorityService(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder,WorkOrderDTO workOrderDto) throws BusinessException;

	/**
	 * Metodo: Permite obtener si se debe marcar la workOrder por la clase del cliente
	 * @param pIbsWorkOrder
	 * @param workOrderDto
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void populateWorkOrderMarkPriorityNexus(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto) throws BusinessException;
	
	/**
	 * Metodo: permite consultar las workOrderMark que se tienen que desmarcar en la atencion
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<WorkOrderMarkVO> getWorkOrderMarkByIsUnMarkAttention() throws BusinessException;
	
}
