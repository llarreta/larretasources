package co.com.directv.sdii.model.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import co.com.directv.sdii.model.vo.ContactVO;
import co.com.directv.sdii.model.vo.DealerMediaContactVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.WoPdfElementTypeNotSerializedVO;
import co.com.directv.sdii.model.vo.WoTypeVO;
import co.com.directv.sdii.model.vo.WorkorderReasonVO;
import co.com.directv.sdii.model.vo.WorkorderStatusVO;
import co.com.directv.sdii.reports.dto.WorkorderDescriptionDTO;

/**
 * 
 * Clase que contiene todos los datos necesarios para viasualizar el PDF de WO
 * 
 * Fecha de Creación: 5/05/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class WorkOrderTrayForPdfDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1949738179666791848L;
	
	private Long woId;
	private String workorderCode;
	private WoTypeVO workOrderTypeVO;
	private String productTechnology;
	private Date creationDate;
	private WorkorderStatusVO workorderStatusVO;
	private Date agendationDate;
	private ServiceHourVO serviceHourVO;
	private String assignment;
	private CustomerDTO customerDTO;
	private String workOrderDescription;
	
	private List<WorkorderDescriptionDTO> workOrderDescriptions;
	
	private WorkorderReasonVO workOrderReason;
	private PostalCodeVO postalCode;
	private String address;
	private String responsableName;
	private String responsableDocumentNumber;
	private String responsableHelperName;
	private String responsableHelperDocumentNumber;
	private int woDays;
	private String shippingOrderCode;
	private List<ContactVO> contacts;
	private Date lastAttentionServiceDate;
	private Date attentionDate;
	private Date finalizationDate;
	private Long dealerCode;
	private Long dealerId;	
	private String dealerName;
	private String dealerType;
	private Long branchCode;
	private Long branchId;	
	private String branchName;
	private String branchType;
	private String branchDepotCode;
	private String plate;
	private String responsableMobileNumber;
	private Long saleDealerCode;
	private String saleDealerName;
	private List<DealerMediaContactVO> saleDealerMediaContacts;
	private Date importDate;
	private Long crewAssigmentId;
	private String crewAssigmentCode;
	private Long crewId;
	private Date assignmentDate;
	private List<PdfServiceDTO> serviceDTO;
	private String agendaComments;
	private List<PdfServiceDTO> lastServices;
	private PdfServiceDTO firstService;
	private PdfServiceDTO secondService;
	private String technicianObservations;
	private List<CustomerResourcesDTO> customerResourcesLeft;
	private List<CustomerResourcesDTO> customerResourcesRight;
	private Long last90WosFinishedQuantity;
	private List<WoPdfElementTypeNotSerializedVO> woPdfElementTypeNotSerializedVOs;
	private String optimusStatus;
	private Date optimusDate;
	
	//informacion para el encabezado
	private String dummyDealerName;
	private String dummyDealerAddress;
	private String dummyDealerCity;
	private String dummyTelephone;
	private String dealerDepotCode;
	
	private Map<String, String> contractInformation;
	
	public Long getWoId() {
		return woId;
	}
	public void setWoId(Long woId) {
		this.woId = woId;
	}
	public String getWorkorderCode() {
		return workorderCode;
	}
	public void setWorkorderCode(String workorderCode) {
		this.workorderCode = workorderCode;
	}
	public WoTypeVO getWorkOrderTypeVO() {
		return workOrderTypeVO;
	}
	public void setWorkOrderTypeVO(WoTypeVO workOrderTypeVO) {
		this.workOrderTypeVO = workOrderTypeVO;
	}
	public String getProductTechnology() {
		return productTechnology;
	}
	public void setProductTechnology(String productTechnology) {
		this.productTechnology = productTechnology;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public WorkorderStatusVO getWorkorderStatusVO() {
		return workorderStatusVO;
	}
	public void setWorkorderStatusVO(WorkorderStatusVO workorderStatusVO) {
		this.workorderStatusVO = workorderStatusVO;
	}
	public Date getAgendationDate() {
		return agendationDate;
	}
	public void setAgendationDate(Date agendationDate) {
		this.agendationDate = agendationDate;
	}
	public ServiceHourVO getServiceHourVO() {
		return serviceHourVO;
	}
	public void setServiceHourVO(ServiceHourVO serviceHourVO) {
		this.serviceHourVO = serviceHourVO;
	}
	public String getAssignment() {
		return assignment;
	}
	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}
	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}
	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}
	public String getWorkOrderDescription() {
		return workOrderDescription;
	}
	public void setWorkOrderDescription(String workOrderDescription) {
		this.workOrderDescription = workOrderDescription;
	}
	public WorkorderReasonVO getWorkOrderReason() {
		return workOrderReason;
	}
	public void setWorkOrderReason(WorkorderReasonVO workOrderReason) {
		this.workOrderReason = workOrderReason;
	}
	public PostalCodeVO getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(PostalCodeVO postalCode) {
		this.postalCode = postalCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getResponsableName() {
		return responsableName;
	}
	public void setResponsableName(String responsableName) {
		this.responsableName = responsableName;
	}
	public int getWoDays() {
		return woDays;
	}
	public void setWoDays(int woDays) {
		this.woDays = woDays;
	}
	public String getShippingOrderCode() {
		return shippingOrderCode;
	}
	public void setShippingOrderCode(String shippingOrderCode) {
		this.shippingOrderCode = shippingOrderCode;
	}
	public List<ContactVO> getContacts() {
		return contacts;
	}
	public void setContacts(List<ContactVO> contacts) {
		this.contacts = contacts;
	}
	public Date getLastAttentionServiceDate() {
		return lastAttentionServiceDate;
	}
	public void setLastAttentionServiceDate(Date lastAttentionServiceDate) {
		this.lastAttentionServiceDate = lastAttentionServiceDate;
	}
	public Date getAttentionDate() {
		return attentionDate;
	}
	public void setAttentionDate(Date attentionDate) {
		this.attentionDate = attentionDate;
	}
	public Date getFinalizationDate() {
		return finalizationDate;
	}
	public void setFinalizationDate(Date finalizationDate) {
		this.finalizationDate = finalizationDate;
	}
	public Long getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(Long dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public String getResponsableMobileNumber() {
		return responsableMobileNumber;
	}
	public void setResponsableMobileNumber(String responsableMobileNumber) {
		this.responsableMobileNumber = responsableMobileNumber;
	}
	public Long getSaleDealerCode() {
		return saleDealerCode;
	}
	public void setSaleDealerCode(Long saleDealerCode) {
		this.saleDealerCode = saleDealerCode;
	}
	public String getSaleDealerName() {
		return saleDealerName;
	}
	public void setSaleDealerName(String saleDealerName) {
		this.saleDealerName = saleDealerName;
	}
	public List<DealerMediaContactVO> getSaleDealerMediaContacts() {
		return saleDealerMediaContacts;
	}
	public void setSaleDealerMediaContacts(
			List<DealerMediaContactVO> saleDealerMediaContacts) {
		this.saleDealerMediaContacts = saleDealerMediaContacts;
	}
	public Date getImportDate() {
		return importDate;
	}
	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}
	public Long getDealerId() {
		return dealerId;
	}
	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}
	public Long getCrewAssigmentId() {
		return crewAssigmentId;
	}
	public void setCrewAssigmentId(Long crewAssigmentId) {
		this.crewAssigmentId = crewAssigmentId;
	}
	public String getCrewAssigmentCode() {
		return crewAssigmentCode;
	}
	public void setCrewAssigmentCode(String crewAssigmentCode) {
		this.crewAssigmentCode = crewAssigmentCode;
	}
	public Long getCrewId() {
		return crewId;
	}
	public void setCrewId(Long crewId) {
		this.crewId = crewId;
	}
	public String getResponsableDocumentNumber() {
		return responsableDocumentNumber;
	}
	public void setResponsableDocumentNumber(String responsableDocumentNumber) {
		this.responsableDocumentNumber = responsableDocumentNumber;
	}
	public String getResponsableHelperName() {
		return responsableHelperName;
	}
	public void setResponsableHelperName(String responsableHelperName) {
		this.responsableHelperName = responsableHelperName;
	}
	public String getResponsableHelperDocumentNumber() {
		return responsableHelperDocumentNumber;
	}
	public void setResponsableHelperDocumentNumber(String responsableHelperDocumentNumber) {
		this.responsableHelperDocumentNumber = responsableHelperDocumentNumber;
	}
	public Date getAssignmentDate() {
		return assignmentDate;
	}
	public void setAssignmentDate(Date assignmentDate) {
		this.assignmentDate = assignmentDate;
	}
	public List<PdfServiceDTO> getServiceDTO() {
		return serviceDTO;
	}
	public void setServiceDTO(List<PdfServiceDTO> serviceDTO) {
		this.serviceDTO = serviceDTO;
	}
	public String getAgendaComments() {
		return agendaComments;
	}
	public void setAgendaComments(String agendaComments) {
		this.agendaComments = agendaComments;
	}
	public List<PdfServiceDTO> getLastServices() {
		return lastServices;
	}
	public void setLastServices(List<PdfServiceDTO> lastServices) {
		this.lastServices = lastServices;
	}
	public String getTechnicianObservations() {
		return technicianObservations;
	}
	public void setTechnicianObservations(String technicianObservations) {
		this.technicianObservations = technicianObservations;
	}
	public PdfServiceDTO getFirstService() {
		return firstService;
	}
	public void setFirstService(PdfServiceDTO firstService) {
		this.firstService = firstService;
	}
	public PdfServiceDTO getSecondService() {
		return secondService;
	}
	public void setSecondService(PdfServiceDTO secondService) {
		this.secondService = secondService;
	}
	public List<CustomerResourcesDTO> getCustomerResourcesLeft() {
		return customerResourcesLeft;
	}
	public void setCustomerResourcesLeft(List<CustomerResourcesDTO> customerResourcesLeft) {
		this.customerResourcesLeft = customerResourcesLeft;
	}
	public List<CustomerResourcesDTO> getCustomerResourcesRight() {
		return customerResourcesRight;
	}
	public void setCustomerResourcesRight(
			List<CustomerResourcesDTO> customerResourcesRight) {
		this.customerResourcesRight = customerResourcesRight;
	}
	public Long getLast90WosFinishedQuantity() {
		return last90WosFinishedQuantity;
	}
	public void setLast90WosFinishedQuantity(Long last90WosFinishedQuantity) {
		this.last90WosFinishedQuantity = last90WosFinishedQuantity;
	}
	public String getDummyDealerName() {
		return dummyDealerName;
	}
	public void setDummyDealerName(String dummyDealerName) {
		this.dummyDealerName = dummyDealerName;
	}
	public String getDummyDealerAddress() {
		return dummyDealerAddress;
	}
	public void setDummyDealerAddress(String dummyDealerAddress) {
		this.dummyDealerAddress = dummyDealerAddress;
	}
	public String getDummyDealerCity() {
		return dummyDealerCity;
	}
	public void setDummyDealerCity(String dummyDealerCity) {
		this.dummyDealerCity = dummyDealerCity;
	}
	public String getDummyTelephone() {
		return dummyTelephone;
	}
	public void setDummyTelephone(String dummyTelephone) {
		this.dummyTelephone = dummyTelephone;
	}
	public String getDealerDepotCode() {
		return dealerDepotCode;
	}
	public void setDealerDepotCode(String dealerDepotCode) {
		this.dealerDepotCode = dealerDepotCode;
	}
	public String getDealerType() {
		return dealerType;
	}
	public void setDealerType(String dealerType) {
		this.dealerType = dealerType;
	}
	public Map<String, String> getContractInformation() {
		return contractInformation;
	}
	public void setContractInformation(Map<String, String> contractInformation) {
		this.contractInformation = contractInformation;
	}
	public Long getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(Long branchCode) {
		this.branchCode = branchCode;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchType() {
		return branchType;
	}
	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}
	public String getBranchDepotCode() {
		return branchDepotCode;
	}
	public void setBranchDepotCode(String branchDepotCode) {
		this.branchDepotCode = branchDepotCode;
	}
	public List<WorkorderDescriptionDTO> getWorkOrderDescriptions() {
		return workOrderDescriptions;
	}
	public void setWorkOrderDescriptions(
			List<WorkorderDescriptionDTO> workOrderDescriptions) {
		this.workOrderDescriptions = workOrderDescriptions;
	}
	public List<WoPdfElementTypeNotSerializedVO> getWoPdfElementTypeNotSerializedVOs() {
		return woPdfElementTypeNotSerializedVOs;
	}
	public void setWoPdfElementTypeNotSerializedVOs(
			List<WoPdfElementTypeNotSerializedVO> woPdfElementTypeNotSerializedVOs) {
		this.woPdfElementTypeNotSerializedVOs = woPdfElementTypeNotSerializedVOs;
	}
	public String getOptimusStatus() {
		return optimusStatus;
	}
	public void setOptimusStatus(String optimusStatus) {
		this.optimusStatus = optimusStatus;
	}
	public Date getOptimusDate() {
		return optimusDate;
	}
	public void setOptimusDate(Date optimusDate) {
		this.optimusDate = optimusDate;
	}
	
}
