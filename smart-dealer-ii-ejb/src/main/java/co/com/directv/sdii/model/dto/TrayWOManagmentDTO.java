package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceElementVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.model.vo.WorkorderReasonVO;

public class TrayWOManagmentDTO extends BaseRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4278558426450876296L;
	
	private Long workOrderId;
	private String workOrderCode;
	private String attentionComments;
	private String finalizationComments;
	private Boolean workOrderFinished;
	private Long dealerId;
	private Long dealerCode;
	private String depotCode;
	private Long crewId;
	private String documentResponsibleCrew;
	private Long customerId;
	private String customerCode;
	private WorkOrderServiceElementVO elementVO;
	private String technicalCrewDocument;
	private List<TrayWorkOrderServiceDTO> trayWOServiceDTO;
	private String workOrderTypeCode;
	private String workOrderTypeId;
	private String customerAddress;
	private TrayServiceDTO serviceDTO;
	private Boolean pricipalWorkOrder;
	private String responsibleCrewName;
	//propiedades para Agendamiento, Reagendamiento, Pendiente (Dificultad y Rechazo)
	private WorkOrderVO workorder;//Agendamiento, Reagendamiento, Pendiente (Dificultad y Rechazo)
	private WorkorderReasonVO workorderReason;//Agendamiento, Reagendamiento, Pendiente (Dificultad y Rechazo)
	private String commentManagment;//Agendamiento, Reagendamiento, Pendiente (Dificultad y Rechazo)
	private ServiceHourVO serviceHour;//Agendamiento, Reagendamiento
	private Date agendationDate;//Agendamiento, Reagendamiento
	private String contactPerson;//Agendamiento, Reagendamiento
	
	private Boolean isInstallation;
	private Boolean isSwap;
	
	
	public Long getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}
	
	public Boolean getWorkOrderFinished() {
		return workOrderFinished;
	}
	public void setWorkOrderFinished(Boolean workOrderFinished) {
		this.workOrderFinished = workOrderFinished;
	}
	public Long getDealerId() {
		return dealerId;
	}
	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}
	public List<TrayWorkOrderServiceDTO> getTrayWOServiceDTO() {
		return trayWOServiceDTO;
	}
	public void setTrayWOServiceDTO(List<TrayWorkOrderServiceDTO> trayWOServiceDTO) {
		this.trayWOServiceDTO = trayWOServiceDTO;
	}
	public String getAttentionComments() {
		return attentionComments;
	}
	public void setAttentionComments(String attentionComments) {
		this.attentionComments = attentionComments;
	}
	public String getFinalizationComments() {
		return finalizationComments;
	}
	public void setFinalizationComments(String finalizationComments) {
		this.finalizationComments = finalizationComments;
	}
	public Long getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(Long dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Long getCrewId() {
		return crewId;
	}
	public void setCrewId(Long crewId) {
		this.crewId = crewId;
	}	
	public String getWorkOrderCode() {
		return workOrderCode;
	}
	public void setWorkOrderCode(String workOrderCode) {
		this.workOrderCode = workOrderCode;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public WorkOrderServiceElementVO getElementVO() {
		return elementVO;
	}
	public void setElementVO(WorkOrderServiceElementVO elementVO) {
		this.elementVO = elementVO;
	}
	public String getTechnicalCrewDocument() {
		return technicalCrewDocument;
	}
	public void setTechnicalCrewDocument(String technicalCrewDocument) {
		this.technicalCrewDocument = technicalCrewDocument;
	}
	public String getWorkOrderTypeCode() {
		return workOrderTypeCode;
	}
	public void setWorkOrderTypeCode(String workOrderTypeCode) {
		this.workOrderTypeCode = workOrderTypeCode;
	}
	public String getWorkOrderTypeId() {
		return workOrderTypeId;
	}
	public void setWorkOrderTypeId(String workOrderTypeId) {
		this.workOrderTypeId = workOrderTypeId;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}	
	public TrayServiceDTO getServiceDTO() {
		return serviceDTO;
	}
	public void setServiceDTO(TrayServiceDTO serviceDTO) {
		this.serviceDTO = serviceDTO;
	}
	public Boolean getPricipalWorkOrder() {
		return pricipalWorkOrder;
	}
	public void setPricipalWorkOrder(Boolean pricipalWorkOrder) {
		this.pricipalWorkOrder = pricipalWorkOrder;
	}
	public String getDepotCode() {
		return depotCode;
	}
	public void setDepotCode(String depotCode) {
		this.depotCode = depotCode;
	}
	public WorkOrderVO getWorkorder() {
		return workorder;
	}
	public void setWorkorder(WorkOrderVO workorder) {
		this.workorder = workorder;
	}
	public WorkorderReasonVO getWorkorderReason() {
		return workorderReason;
	}
	public void setWorkorderReason(WorkorderReasonVO workorderReason) {
		this.workorderReason = workorderReason;
	}
	public String getCommentManagment() {
		return commentManagment;
	}
	public void setCommentManagment(String commentManagment) {
		this.commentManagment = commentManagment;
	}
	public ServiceHourVO getServiceHour() {
		return serviceHour;
	}
	public void setServiceHour(ServiceHourVO serviceHour) {
		this.serviceHour = serviceHour;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getAgendationDate() {
		return agendationDate;
	}
	
	public void setAgendationDate(Date agendationDate) {
		this.agendationDate = agendationDate;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getDocumentResponsibleCrew() {
		return documentResponsibleCrew;
	}
	public void setDocumentResponsibleCrew(String documentResponsibleCrew) {
		this.documentResponsibleCrew = documentResponsibleCrew;
	}
	public String getResponsibleCrewName() {
		return responsibleCrewName;
	}
	public void setResponsibleCrewName(String responsibleCrewName) {
		this.responsibleCrewName = responsibleCrewName;
	}
	public Boolean getIsInstallation() {
		return isInstallation;
	}
	public void setIsInstallation(Boolean isInstallation) {
		this.isInstallation = isInstallation;
	}
	public Boolean getIsSwap() {
		return isSwap;
	}
	public void setIsSwap(Boolean isSwap) {
		this.isSwap = isSwap;
	}		
}
