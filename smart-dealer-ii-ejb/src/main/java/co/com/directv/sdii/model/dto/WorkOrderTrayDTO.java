package co.com.directv.sdii.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;
import co.com.directv.sdii.model.pojo.CustomerMediaContact;
import co.com.directv.sdii.model.pojo.MediaContactType;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.vo.ContactVO;
import co.com.directv.sdii.model.vo.CustomerMediaContactVO;
import co.com.directv.sdii.model.vo.DealerMediaContactVO;
import co.com.directv.sdii.model.vo.DocumentTypeVO;
import co.com.directv.sdii.model.vo.OptimusStatusVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.ShippingOrderVO;
import co.com.directv.sdii.model.vo.WoTypeVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceVO;
import co.com.directv.sdii.model.vo.WorkorderReasonVO;
import co.com.directv.sdii.model.vo.WorkorderStatusVO;

/**
 * 
 * Clase que contiene todos los datos necesarios para viasualizar la bandeja de
 * work orders de la compania instaladora 
 * 
 * Fecha de Creación: 4/05/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class WorkOrderTrayDTO implements  java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1949738179666791848L;
	
	private Long woId;
	private String workorderCode;
	private WoTypeVO workOrderTypeVO;
	private String productTechnology;
	private String codeProductTechnology;
	private Date creationDate;
	private WorkorderStatusVO workorderStatusVO;
	private Date agendationDate;
	private ServiceHourVO serviceHourVO;
	private String assignment;
	private CustomerDTO customerDTO;
	private List<WorkOrderServiceVO> servicesByWorkOrder;
	private String workOrderDescription;
	private WorkorderReasonVO workOrderReason;
	private PostalCodeVO postalCode;
	private String address;
	private String responsableName;
	private String responsableDocumentNumber;
	private int woDays;
	private String shippingOrderCode;
	private List<ShippingOrderVO> shippingOrderList;
	private List<ContactVO> contacts;
	private Date lastAttentionServiceDate;
	private Date attentionDate;
	private Date finalizationDate;
	private Long dealerCode;
	private Long dealerId;	
	private String dealerName;
	private String plate;
	private String responsableMobileNumber;
	private Long saleDealerCode;
	private String saleDealerName;
	private List<DealerMediaContactVO> saleDealerMediaContacts;
	private Date importDate;
	private Long crewAssigmentId;
	private String crewAssigmentCode;
	private Long crewId;
	private Long workOrderDealerCode;
	private String workOrderDealerName;
	private String woProcessSourceDescription;
	private boolean isWoProcessDescription;
	private boolean agendationExpired;
	
	private String stateCity;
	private String city;
	private String perimeter;
	
	private List<WorkOrderMarkDTO> workOrderMarkDTO;
	
	private List<IbsContactDTO> ibsContactDTO;

	private boolean denyActivateWO;
	

	private OptimusStatusVO optimusStatusVO;
	
	private String optimusDeclineCode;
	private String optimusDeclineDescription;
	
	public WorkOrderTrayDTO() {
		super();
	}

	public WorkOrderTrayDTO(WorkOrder wo, String responsibleName) throws BusinessException {
		this.woId = wo.getId();
		this.workorderCode = wo.getWoCode();
		this.workOrderTypeVO = new WoTypeVO();
		this.workOrderTypeVO.setId(wo.getWoTypeByWoTypeId().getId());
		this.workOrderTypeVO.setWoTypeCode(wo.getWoTypeByWoTypeId().getWoTypeCode());
		this.workOrderTypeVO.setWoTypeName(wo.getWoTypeByWoTypeId().getWoTypeName());
		this.creationDate = wo.getCreationDate();
		this.workorderStatusVO = new WorkorderStatusVO();
		this.workorderStatusVO.setId(wo.getWorkorderStatusByActualStatusId().getId());
		this.workorderStatusVO.setWoStateCode(wo.getWorkorderStatusByActualStatusId().getWoStateCode());
		this.workorderStatusVO.setWoStateName(wo.getWorkorderStatusByActualStatusId().getWoStateName());
		this.agendationDate = wo.getWoProgrammingDate();
		
		List<CustomerMediaContactVO> customerMediaContactVOs=new ArrayList<CustomerMediaContactVO>();
		
		for(CustomerMediaContact cmc : wo.getCustomer().getCustomerMediaContacts()){
			CustomerMediaContactVO cmcvo= new CustomerMediaContactVO();
			cmcvo.setCustomerId(wo.getCustomer().getId());
			cmcvo.setMediaContactType(new MediaContactType(cmc.getMediaContactType().getMediaName(), cmc.getMediaContactType().getMediaCode()));
			cmcvo.getMediaContactType().setId(cmc.getMediaContactType().getId());
			cmcvo.setCustomerId(wo.getCustomer().getId());
			cmcvo.setId(cmc.getId());
			cmcvo.setMediaContactValue(cmc.getMediaContactValue());
			customerMediaContactVOs.add(cmcvo);
		}
		
		this.customerDTO = new CustomerDTO(wo.getCustomer().getId(), wo.getCustomer().getCustomerCode(), wo.getCustomer().getFirstName(), 
				wo.getCustomer().getLastName(), wo.getCustomer().getDocumentNumber(), null, null, 
				new DocumentTypeVO(wo.getCustomer().getDocumentTypeId()), customerMediaContactVOs, null, null, null, null, false);
		
		this.servicesByWorkOrder = UtilsBusiness.convertList(wo.getWorkOrderServices(), WorkOrderServiceVO.class);
		this.workOrderDescription = wo.getWoDescription();
		this.postalCode = UtilsBusiness.copyObject(PostalCodeVO.class, wo.getPostalCode());
		this.address = wo.getWoAddress();
		this.attentionDate = wo.getWoRealizationDate();
		this.finalizationDate = wo.getFinalizationDate();
		this.dealerId = wo.getDealerId();
		this.saleDealerCode = wo.getSaleDealer().getDealerCode();
		this.saleDealerName = wo.getSaleDealer().getDealerName();
		this.importDate = wo.getImportDate();
		
		this.responsableName = responsibleName;
	}

	public WorkOrderTrayDTO(Long woId, String workorderCode,
			WoTypeVO workOrderTypeVO, String productTechnology,
			String codeProductTechnology, Date creationDate,
			WorkorderStatusVO workorderStatusVO, Date agendationDate,
			ServiceHourVO serviceHourVO, String assignment,
			CustomerDTO customerDTO,
			List<WorkOrderServiceVO> servicesByWorkOrder,
			String workOrderDescription, WorkorderReasonVO workOrderReason,
			PostalCodeVO postalCode, String address, String responsableName,
			String responsableDocumentNumber, int woDays,
			String shippingOrderCode, List<ShippingOrderVO> shippingOrderList,
			List<ContactVO> contacts, Date lastAttentionServiceDate,
			Date attentionDate, Date finalizationDate, Long dealerCode,
			Long dealerId, String dealerName, String plate,
			String responsableMobileNumber, Long saleDealerCode,
			String saleDealerName,
			List<DealerMediaContactVO> saleDealerMediaContacts,
			Date importDate, Long crewAssigmentId, String crewAssigmentCode,
			Long crewId, Long workOrderDealerCode, String workOrderDealerName,
			String woProcessSourceDescription, boolean isWoProcessDescription,
			boolean agendationExpired, String stateCity, String city,
			String perimeter) {
		super();
		this.woId = woId;
		this.workorderCode = workorderCode;
		this.workOrderTypeVO = workOrderTypeVO;
		this.productTechnology = productTechnology;
		this.codeProductTechnology = codeProductTechnology;
		this.creationDate = creationDate;
		this.workorderStatusVO = workorderStatusVO;
		this.agendationDate = agendationDate;
		this.serviceHourVO = serviceHourVO;
		this.assignment = assignment;
		this.customerDTO = customerDTO;
		this.servicesByWorkOrder = servicesByWorkOrder;
		this.workOrderDescription = workOrderDescription;
		this.workOrderReason = workOrderReason;
		this.postalCode = postalCode;
		this.address = address;
		this.responsableName = responsableName;
		this.responsableDocumentNumber = responsableDocumentNumber;
		this.woDays = woDays;
		this.shippingOrderCode = shippingOrderCode;
		this.shippingOrderList = shippingOrderList;
		this.contacts = contacts;
		this.lastAttentionServiceDate = lastAttentionServiceDate;
		this.attentionDate = attentionDate;
		this.finalizationDate = finalizationDate;
		this.dealerCode = dealerCode;
		this.dealerId = dealerId;
		this.dealerName = dealerName;
		this.plate = plate;
		this.responsableMobileNumber = responsableMobileNumber;
		this.saleDealerCode = saleDealerCode;
		this.saleDealerName = saleDealerName;
		this.saleDealerMediaContacts = saleDealerMediaContacts;
		this.importDate = importDate;
		this.crewAssigmentId = crewAssigmentId;
		this.crewAssigmentCode = crewAssigmentCode;
		this.crewId = crewId;
		this.workOrderDealerCode = workOrderDealerCode;
		this.workOrderDealerName = workOrderDealerName;
		this.woProcessSourceDescription = woProcessSourceDescription;
		this.isWoProcessDescription = isWoProcessDescription;
		this.agendationExpired = agendationExpired;
		this.stateCity = stateCity;
		this.city = city;
		this.perimeter = perimeter;
	}
	
	public String getStateCity() {
		return stateCity;
	}
	public void setStateCity(String stateCity) {
		this.stateCity = stateCity;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPerimeter() {
		return perimeter;
	}
	public void setPerimeter(String perimeter) {
		this.perimeter = perimeter;
	}

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
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
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
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
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
	public List<WorkOrderServiceVO> getServicesByWorkOrder() {
		return servicesByWorkOrder;
	}
	public void setServicesByWorkOrder(List<WorkOrderServiceVO> servicesByWorkOrder) {
		this.servicesByWorkOrder = servicesByWorkOrder;
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
	public List<ShippingOrderVO> getShippingOrderList() {
		return shippingOrderList;
	}
	public void setShippingOrderList(List<ShippingOrderVO> shippingOrderList) {
		this.shippingOrderList = shippingOrderList;
	}
	public List<ContactVO> getContacts() {
		return contacts;
	}
	public void setContacts(List<ContactVO> contacts) {
		this.contacts = contacts;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getLastAttentionServiceDate() {
		return lastAttentionServiceDate;
	}
	public void setLastAttentionServiceDate(Date lastAttentionServiceDate) {
		this.lastAttentionServiceDate = lastAttentionServiceDate;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getAttentionDate() {
		return attentionDate;
	}
	public void setAttentionDate(Date attentionDate) {
		this.attentionDate = attentionDate;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
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
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
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
	public Long getWorkOrderDealerCode() {
		return workOrderDealerCode;
	}
	public void setWorkOrderDealerCode(Long workOrderDealerCode) {
		this.workOrderDealerCode = workOrderDealerCode;
	}
	public String getWorkOrderDealerName() {
		return workOrderDealerName;
	}
	public void setWorkOrderDealerName(String workOrderDealerName) {
		this.workOrderDealerName = workOrderDealerName;
	}
	public String getWoProcessSourceDescription() {
		return woProcessSourceDescription;
	}
	public void setWoProcessSourceDescription(String woProcessSourceDescription) {
		this.woProcessSourceDescription = woProcessSourceDescription;
	}
	public boolean isWoProcessDescription() {
		return isWoProcessDescription;
	}
	public void setWoProcessDescription(boolean isWoProcessDescription) {
		this.isWoProcessDescription = isWoProcessDescription;
	}
	public boolean isAgendationExpired() {
		return agendationExpired;
	}
	public void setAgendationExpired(boolean agendationExpired) {
		this.agendationExpired = agendationExpired;
	}
	public String getCodeProductTechnology() {
		return codeProductTechnology;
	}
	public void setCodeProductTechnology(String codeProductTechnology) {
		this.codeProductTechnology = codeProductTechnology;
	}
	public List<WorkOrderMarkDTO> getWorkOrderMarkDTO() {
		return workOrderMarkDTO;
	}
	public void setWorkOrderMarkDTO(List<WorkOrderMarkDTO> workOrderMarkDTO) {
		this.workOrderMarkDTO = workOrderMarkDTO;
	}

	public List<IbsContactDTO> getIbsContactDTO() {
		return ibsContactDTO;
	}

	public void setIbsContactDTO(List<IbsContactDTO> ibsContactDTO) {
		this.ibsContactDTO = ibsContactDTO;
	}
	
	public boolean isDenyActivateWO() {
		return denyActivateWO;
	}

	public void setDenyActivateWO(boolean denyActivateWO) {
		this.denyActivateWO = denyActivateWO;
	}
	
	public OptimusStatusVO getOptimusStatusVO() {
		return optimusStatusVO;
	}

	public void setOptimusStatusVO(OptimusStatusVO optimusStatus) {
		this.optimusStatusVO = optimusStatus;
	}

	public String getOptimusDeclineCode() {
		return optimusDeclineCode;
	}

	public void setOptimusDeclineCode(String optimusDeclineCode) {
		this.optimusDeclineCode = optimusDeclineCode;
	}

	public String getOptimusDeclineDescription() {
		return optimusDeclineDescription;
	}

	public void setOptimusDeclineDescription(String optimusDeclineDescription) {
		this.optimusDeclineDescription = optimusDeclineDescription;
	}

}
