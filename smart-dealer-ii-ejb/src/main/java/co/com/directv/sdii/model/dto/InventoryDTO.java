package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.EmployeeCrewVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;

/**
 * 
 * Data Transfer Object para transportar objetos entre el modulo
 * de CORE y el modulo de Inventarios.
 * 
 * Fecha de Creación: 10/06/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class InventoryDTO extends BaseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5281149691490940693L;

	private ElementDTO elementDTO;
	private DealerVO dealer;
	private EmployeeCrewVO employeeCrew;
	private CustomerVO customer;
	private TrayServiceDTO serviceDTO;
	private WOAttentionsRequestDTO woAttentionDTO;
	private WOAttentionElementsRequestDTO attentionElements;
	private List<WOAttentionElementsRequestDTO> attentionElementsList;
	private WorkOrderVO workOrderVO;
	private String documentResponsibleCrew;
	private Long assignCrewId;
	private Service woService;
	private Boolean isUpgradeDowngrade;
	private Boolean isInstallation;
	private Boolean isSwap;
	private String invokeInternalInventory;
	private boolean invokeInventoryService;
	
	private Long workOrderServiceId;

	public Long getWorkOrderServiceId() {
		return workOrderServiceId;
	}
	public void setWorkOrderServiceId(Long workOrderServiceId) {
		this.workOrderServiceId = workOrderServiceId;
	}
	/**
	 * Se parametriza cual es el codigo del proceso para recuperaciones 
	 */
	private String processCodeIbsRecovery;
	
	public ElementDTO getElementDTO() {
		return elementDTO;
	}
	public void setElementDTO(ElementDTO elementDTO) {
		this.elementDTO = elementDTO;
	}
	public DealerVO getDealer() {
		return dealer;
	}
	public void setDealer(DealerVO dealer) {
		this.dealer = dealer;
	}
	public EmployeeCrewVO getEmployeeCrew() {
		return employeeCrew;
	}
	public void setEmployeeCrew(EmployeeCrewVO employeeCrew) {
		this.employeeCrew = employeeCrew;
	}
	public CustomerVO getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerVO customer) {
		this.customer = customer;
	}
	public TrayServiceDTO getServiceDTO() {
		return serviceDTO;
	}
	public void setServiceDTO(TrayServiceDTO serviceDTO) {
		this.serviceDTO = serviceDTO;
	}
	public WOAttentionsRequestDTO getWoAttentionDTO() {
		return woAttentionDTO;
	}
	public void setWoAttentionDTO(WOAttentionsRequestDTO woAttentionDTO) {
		this.woAttentionDTO = woAttentionDTO;
	}
	public WorkOrderVO getWorkOrderVO() {
		return workOrderVO;
	}
	public void setWorkOrderVO(WorkOrderVO workOrderVO) {
		this.workOrderVO = workOrderVO;
	}
	public String getDocumentResponsibleCrew() {
		return documentResponsibleCrew;
	}
	public void setDocumentResponsibleCrew(String documentResponsibleCrew) {
		this.documentResponsibleCrew = documentResponsibleCrew;
	}
	public WOAttentionElementsRequestDTO getAttentionElements() {
		return attentionElements;
	}
	public void setAttentionElements(WOAttentionElementsRequestDTO attentionElements) {
		this.attentionElements = attentionElements;
	}
	public List<WOAttentionElementsRequestDTO> getAttentionElementsList() {
		return attentionElementsList;
	}
	public void setAttentionElementsList(
			List<WOAttentionElementsRequestDTO> attentionElementsList) {
		this.attentionElementsList = attentionElementsList;
	}
	public Long getAssignCrewId() {
		return assignCrewId;
	}
	public void setAssignCrewId(Long assignCrewId) {
		this.assignCrewId = assignCrewId;
	}
	public Service getWoService() {
		return woService;
	}
	public void setWoService(Service woService) {
		this.woService = woService;
	}
	public Boolean isUpgradeDowngrade() {
		return isUpgradeDowngrade;
	}
	public void setUpgradeDowngrade(Boolean isUpgradeDowngrade) {
		this.isUpgradeDowngrade = isUpgradeDowngrade;
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
	public String getInvokeInternalInventory() {
		return invokeInternalInventory;
	}
	public void setInvokeInternalInventory(String invokeInternalInventory) {
		this.invokeInternalInventory = invokeInternalInventory;
	}
	public boolean isInvokeInventoryService() {
		return invokeInventoryService;
	}
	public void setInvokeInventoryService(boolean invokeInventoryService) {
		this.invokeInventoryService = invokeInventoryService;
	}
	public String getProcessCodeIbsRecovery() {
		return processCodeIbsRecovery;
	}
	public void setProcessCodeIbsRecovery(String processCodeIbsRecovery) {
		this.processCodeIbsRecovery = processCodeIbsRecovery;
	}
	
}
