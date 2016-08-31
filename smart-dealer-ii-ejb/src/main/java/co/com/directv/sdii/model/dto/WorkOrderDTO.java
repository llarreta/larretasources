/**
 * Creado 29/06/2010 8:58:11
 */
package co.com.directv.sdii.model.dto;

import java.util.List;

import co.com.directv.sdii.model.pojo.Building;
import co.com.directv.sdii.model.pojo.Ibs6Status;
import co.com.directv.sdii.model.pojo.ShippingOrder;
import co.com.directv.sdii.model.pojo.ShippingOrderDetail;
import co.com.directv.sdii.model.pojo.ShippingOrderElement;
import co.com.directv.sdii.model.pojo.WoAssignment;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderAgenda;
import co.com.directv.sdii.model.pojo.WorkorderReason;
import co.com.directv.sdii.model.pojo.WorkorderStatus;

/**
 * Objeto que encapsula la información relacionada de una work order que se
 * consulta de un sistema externo a sdii.
 * 
 * Contiene la información necesaria para la creación de una work order en SDII.
 * 
 * Fecha de Creación: 29/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WorkOrder
 * @see co.com.directv.sdii.model.pojo.ShippingOrder
 */
public class WorkOrderDTO {

	private WorkOrder workOrder;
	
	private ShippingOrder shippingOrder;
	
	private List<ShippingOrderElement> shippingOrderElements;
	
	private WorkOrderAgenda woAgenda;
	
	private WoAssignment woAssignment;
	
	private List<ShippingOrderDetail> shippingOrderDetails;
	
	private Long dealerDummyCodeLong;
	
	private Boolean createAssignment;
	
	private Boolean updateAssignment;
	
	private String dealerDummyCode;
	
	private Long dealerAssignmentCode;
	
	private Building building;
	
	private Long processSourceId;
	
	/*
	 * Variables para la creacion de historial cuando la WO baja con agenda comprometida y se informa a IBS que se agenda la WO
	 * */
	private boolean createHistoryWoStatusHistory;
	private WorkorderReason historyWoReason;
	private String historyDescription;
	private String ibsHistoryEventCode;
	
	private boolean error;
	private boolean warning;
	private String errorCode;
	private String errorMessage;
	
	private WorkorderStatus workorderStatusPrevius;
	
	private WorkorderStatus workorderStatusNew;
	
	private Ibs6Status ibs6Status;
	
	private boolean callEsbChangeStateSchedule;
	
	CustomerInfoAggregatedDTO customerInfoAggregatedDTO;
	
	/*
	 * Objeto que se envia a la cola de inventarios para afectar inventario que se mueve en IBs
	 * */
	private List<MovementInventoryDTO> movementInventoryDTO;
	
	private Long dealerSalesId;
	
	private Boolean markWorkOrderRequiredContract;
	private Boolean markWorkOrderPriorityService;
	
	private Boolean unMarkWorkOrderAttention;
	
	private Boolean markPriorityNexus;
	
	private transient String customerCodeType;
	
	private transient String customerCodeClass;
	
	private boolean updateWorkOrderIBSToHSP;
	
	private boolean workOrderStatusCancel;

	public String getCustomerCodeType() {
		return customerCodeType;
	}

	public void setCustomerCodeType(String customerCodeType) {
		this.customerCodeType = customerCodeType;
	}

	public String getCustomerCodeClass() {
		return customerCodeClass;
	}

	public void setCustomerCodeClass(String customerCodeClass) {
		this.customerCodeClass = customerCodeClass;
	}

	public Boolean getMarkPriorityNexus() {
		return markPriorityNexus;
	}

	public void setMarkPriorityNexus(Boolean markPriorityNexus) {
		this.markPriorityNexus = markPriorityNexus;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public ShippingOrder getShippingOrder() {
		return shippingOrder;
	}

	public void setShippingOrder(ShippingOrder shippingOrder) {
		this.shippingOrder = shippingOrder;
	}

	public List<ShippingOrderElement> getShippingOrderElements() {
		return shippingOrderElements;
	}

	public void setShippingOrderElements(
			List<ShippingOrderElement> shippingOrderElements) {
		this.shippingOrderElements = shippingOrderElements;
	}

	public WorkOrderAgenda getWoAgenda() {
		return woAgenda;
	}

	public void setWoAgenda(WorkOrderAgenda woAgenda) {
		this.woAgenda = woAgenda;
	}

	public WoAssignment getWoAssignment() {
		return woAssignment;
	}

	public void setWoAssignment(WoAssignment woAssignment) {
		this.woAssignment = woAssignment;
	}
	
	public List<ShippingOrderDetail> getShippingOrderDetails() {
		return shippingOrderDetails;
	}

	public void setShippingOrderDetails(
			List<ShippingOrderDetail> shippingOrderDetails) {
		this.shippingOrderDetails = shippingOrderDetails;
	}

	public Long getDealerDummyCodeLong() {
		return dealerDummyCodeLong;
	}

	public void setDealerDummyCodeLong(Long dealerDummyCodeLong) {
		this.dealerDummyCodeLong = dealerDummyCodeLong;
	}

	public Boolean getCreateAssignment() {
		return createAssignment;
	}

	public void setCreateAssignment(Boolean createAssignment) {
		this.createAssignment = createAssignment;
	}

	public String getDealerDummyCode() {
		return dealerDummyCode;
	}

	public void setDealerDummyCode(String dealerDummyCode) {
		this.dealerDummyCode = dealerDummyCode;
	}

	public Long getDealerAssignmentCode() {
		return dealerAssignmentCode;
	}

	public void setDealerAssignmentCode(Long dealerAssignmentCode) {
		this.dealerAssignmentCode = dealerAssignmentCode;
	}

	public Boolean getUpdateAssignment() {
		return updateAssignment;
	}

	public void setUpdateAssignment(Boolean updateAssignment) {
		this.updateAssignment = updateAssignment;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public Long getProcessSourceId() {
		return processSourceId;
	}

	public void setProcessSourceId(Long processSourceId) {
		this.processSourceId = processSourceId;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isWarning() {
		return warning;
	}

	public void setWarning(boolean warning) {
		this.warning = warning;
	}

	public List<MovementInventoryDTO> getMovementInventoryDTO() {
		return movementInventoryDTO;
	}

	public void setMovementInventoryDTO(
			List<MovementInventoryDTO> movementInventoryDTO) {
		this.movementInventoryDTO = movementInventoryDTO;
	}
	public boolean isCreateHistoryWoStatusHistory() {
		return createHistoryWoStatusHistory;
	}

	public void setCreateHistoryWoStatusHistory(boolean createHistoryWoStatusHistory) {
		this.createHistoryWoStatusHistory = createHistoryWoStatusHistory;
	}

	public String getIbsHistoryEventCode() {
		return ibsHistoryEventCode;
	}

	public void setIbsHistoryEventCode(String ibsHistoryEventCode) {
		this.ibsHistoryEventCode = ibsHistoryEventCode;
	}

	public WorkorderReason getHistoryWoReason() {
		return historyWoReason;
	}

	public void setHistoryWoReason(WorkorderReason historyWoReason) {
		this.historyWoReason = historyWoReason;
	}

	public String getHistoryDescription() {
		return historyDescription;
	}

	public void setHistoryDescription(String historyDescription) {
		this.historyDescription = historyDescription;
	}

	public WorkorderStatus getWorkorderStatusPrevius() {
		return workorderStatusPrevius;
	}

	public void setWorkorderStatusPrevius(WorkorderStatus workorderStatusPrevius) {
		this.workorderStatusPrevius = workorderStatusPrevius;
	}

	public WorkorderStatus getWorkorderStatusNew() {
		return workorderStatusNew;
	}

	public void setWorkorderStatusNew(WorkorderStatus workorderStatusNew) {
		this.workorderStatusNew = workorderStatusNew;
	}

	public Ibs6Status getIbs6Status() {
		return ibs6Status;
	}

	public void setIbs6Status(Ibs6Status ibs6Status) {
		this.ibs6Status = ibs6Status;
	}

	public boolean isCallEsbChangeStateSchedule() {
		return callEsbChangeStateSchedule;
	}

	public void setCallEsbChangeStateSchedule(boolean callEsbChangeStateSchedule) {
		this.callEsbChangeStateSchedule = callEsbChangeStateSchedule;
	}	
	
	public CustomerInfoAggregatedDTO getCustomerInfoAggregatedDTO() {
		return customerInfoAggregatedDTO;
	}

	public void setCustomerInfoAggregatedDTO(
			CustomerInfoAggregatedDTO customerInfoAggregatedDTO) {
		this.customerInfoAggregatedDTO = customerInfoAggregatedDTO;
	}
	
	
	public Long getDealerSalesId() {
		return dealerSalesId;
	}

	public void setDealerSalesId(Long dealerSalesId) {
		this.dealerSalesId = dealerSalesId;
	}
	
	public Boolean getMarkWorkOrderRequiredContract() {
		return markWorkOrderRequiredContract;
	}

	public void setMarkWorkOrderRequiredContract(
			Boolean markWorkOrderRequiredContract) {
		this.markWorkOrderRequiredContract = markWorkOrderRequiredContract;
	}

	public Boolean getMarkWorkOrderPriorityService() {
		return markWorkOrderPriorityService;
	}

	public void setMarkWorkOrderPriorityService(Boolean markWorkOrderPriorityService) {
		this.markWorkOrderPriorityService = markWorkOrderPriorityService;
	}

	public Boolean getUnMarkWorkOrderAttention() {
		return unMarkWorkOrderAttention;
	}

	public void setUnMarkWorkOrderAttention(Boolean unMarkWorkOrderAttention) {
		this.unMarkWorkOrderAttention = unMarkWorkOrderAttention;
	}

	public boolean isUpdateWorkOrderIBSToHSP() {
		return updateWorkOrderIBSToHSP;
	}

	public void setUpdateWorkOrderIBSToHSP(boolean updateWorkOrderIBSToHSP) {
		this.updateWorkOrderIBSToHSP = updateWorkOrderIBSToHSP;
	}

	public boolean isWorkOrderStatusCancel() {
		return workOrderStatusCancel;
	}

	public void setWorkOrderStatusCancel(boolean workOrderStatusCancel) {
		this.workOrderStatusCancel = workOrderStatusCancel;
	}
	
	
	
}
