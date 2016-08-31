package co.com.directv.sdii.model.dto;

import java.util.Date;
import java.util.List;

/**
 * Clase que contiene todos los filtros para visualizar la bandeja de work
 * orders de la compania instaladora
 * 
 * @author jnova
 * 
 */
public class WorkOrderFilterTrayDTO extends BaseRequest implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5219714521420031270L;

	/*
	 * Variable que indica si la consulta viene del acordeon de fecha de
	 * agendamiento
	 */
	private boolean fromExpirationGrouping;

	/*
	 * Id del pais del usuario que esta realizando la consulta
	 */
	private Long countryId;

	/*
	 * Filtro utilizado para la agrupacion por vencimiento
	 */
	private Date expirationGroupingInit;
	private Date expirationGroupingEnd;

	/*
	 * Codigo WO
	 */
	private String woCode;

	/*
	 * Ids de Tipos de WO
	 */
	private List<Long> woTypeIds;

	/*
	 * Ids de Tipos de Servicio
	 */
	private List<Long> serviceTypeIds;

	/*
	 * Ids de categorias de servicio
	 */
	private List<Long> serviceCategoryIds;

	/*
	 * Ids de servicios
	 */
	private List<Long> serviceIds;

	/*
	 * Ids de estados de la WO
	 */
	private List<Long> woStatusIds;

	/*
	 * Id del departamento del servicio
	 */
	private Long serviceDepartamentId;

	/*
	 * Id de la ciudad del servicio
	 */
	private Long serviceCityId;

	/*
	 * Id del codigo postal del servicio
	 */
	private Long servicePostalCodeId;

	/*
	 * Direccion del servicio
	 */
	private String serviceAddress;

	/*
	 * Fecha de creacion de la WO
	 */
	private Date woCreationDateInit;
	private Date woCreationDateEnd;

	/*
	 * Fecha de cancelación de la WO
	 */
	private Date woCancelationDateInit;
	private Date woCancelationDateEnd;

	/*
	 * Fecha Agendamiento WO
	 */
	private Date woAgendationDateInit;
	private Date woAgendationDateEnd;

	/*
	 * Fecha Atencion WO
	 */
	private Date woAttentionDateInit;
	private Date woAttentionDateEnd;

	/*
	 * Fecha de finalizacion de WO
	 */
	private Date woFinalizationDateInit;
	private Date woFinalizationDateEnd;

	/*
	 * Ids de las jornadas de la WO
	 */
	private List<Long> serviceHourIds;

	/*
	 * IdS de programas asignados a la WO
	 */
	private List<Long> programIds;

	/*
	 * Id del dealer vendedor de la WO
	 */
	private Long saleDealerId;

	/*
	 * IBS del cliente
	 */
	private String customerIbs;

	/*
	 * Telefono casa del cliente
	 */
	private String customerHomePhoneNum;

	/*
	 * Telefono oficina del cliente
	 */
	private String customerWorkPhoneNum;

	/*
	 * Numero de Fax del cliente
	 */
	private String customerFaxNum;

	/*
	 * Numero de celular del cliente
	 */
	private String customerMobileNum;

	/*
	 * Numero de documento del cliente
	 */
	private String customerDocumentNum;

	/*
	 * Nombre del cliente
	 */
	private String customerFirstName;

	/*
	 * Apellido del cliente
	 */
	private String customerLastName;

	/*
	 * Id de la compania instaladora
	 */
	private Long dealerId;

	/*
	 * Id de la sucursal
	 */
	private Long branchId;

	private List<Long> branchIds;

	/*
	 * Id de la cuadrilla asignada
	 */
	private Long crewId;

	private List<Long> dealersIds;

	/**
	 * Lista de ids de clases de customer
	 */
	private List<Long> customerClassIds;

	private Long customerCategoryId;

	private List<Long> customerTypesIds;

	/**
	 * Indica si el dealer tiene que ir contra la WorkOrder o contra el
	 * Assigment
	 */
	private boolean dealerToWO = false;

	/*
	 * Id de la mark de la workOrder
	 */
	private List<Long> workOrderMarkIds;

	/**
	 * Indica si se tiene que filtrar por "Agendadas" en 1era linea"
	 **/
	private boolean scheduledCSR;

	
	private List<String> processSourceCodes;
	

	public Long getCustomerCategoryId() {
		return customerCategoryId;
	}

	public void setCustomerCategoryId(Long customerCategoryId) {
		this.customerCategoryId = customerCategoryId;
	}

	public List<Long> getCustomerTypesIds() {
		return customerTypesIds;
	}

	public void setCustomerTypesIds(List<Long> customerTypesIds) {
		this.customerTypesIds = customerTypesIds;
	}

	public String getWoCode() {
		return woCode;
	}

	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}

	public List<Long> getWoTypeIds() {
		return woTypeIds;
	}

	public void setWoTypeIds(List<Long> woTypeIds) {
		this.woTypeIds = woTypeIds;
	}

	public List<Long> getServiceTypeIds() {
		return serviceTypeIds;
	}

	public void setServiceTypeIds(List<Long> serviceTypeIds) {
		this.serviceTypeIds = serviceTypeIds;
	}

	public List<Long> getServiceCategoryIds() {
		return serviceCategoryIds;
	}

	public void setServiceCategoryIds(List<Long> serviceCategoryIds) {
		this.serviceCategoryIds = serviceCategoryIds;
	}

	public List<Long> getServiceIds() {
		return serviceIds;
	}

	public void setServiceIds(List<Long> serviceIds) {
		this.serviceIds = serviceIds;
	}

	public List<Long> getWoStatusIds() {
		return woStatusIds;
	}

	public void setWoStatusIds(List<Long> woStatusIds) {
		this.woStatusIds = woStatusIds;
	}

	public Long getServiceDepartamentId() {
		return serviceDepartamentId;
	}

	public void setServiceDepartamentId(Long serviceDepartamentId) {
		this.serviceDepartamentId = serviceDepartamentId;
	}

	public Long getServiceCityId() {
		return serviceCityId;
	}

	public void setServiceCityId(Long serviceCityId) {
		this.serviceCityId = serviceCityId;
	}

	public Long getServicePostalCodeId() {
		return servicePostalCodeId;
	}

	public void setServicePostalCodeId(Long servicePostalCodeId) {
		this.servicePostalCodeId = servicePostalCodeId;
	}

	public String getServiceAddress() {
		return serviceAddress;
	}

	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

	public Long getSaleDealerId() {
		return saleDealerId;
	}

	public void setSaleDealerId(Long saleDealerId) {
		this.saleDealerId = saleDealerId;
	}

	public String getCustomerIbs() {
		return customerIbs;
	}

	public void setCustomerIbs(String customerIbs) {
		this.customerIbs = customerIbs;
	}

	public String getCustomerHomePhoneNum() {
		return customerHomePhoneNum;
	}

	public void setCustomerHomePhoneNum(String customerHomePhoneNum) {
		this.customerHomePhoneNum = customerHomePhoneNum;
	}

	public String getCustomerWorkPhoneNum() {
		return customerWorkPhoneNum;
	}

	public void setCustomerWorkPhoneNum(String customerWorkPhoneNum) {
		this.customerWorkPhoneNum = customerWorkPhoneNum;
	}

	public String getCustomerFaxNum() {
		return customerFaxNum;
	}

	public void setCustomerFaxNum(String customerFaxNum) {
		this.customerFaxNum = customerFaxNum;
	}

	public String getCustomerMobileNum() {
		return customerMobileNum;
	}

	public void setCustomerMobileNum(String customerMobileNum) {
		this.customerMobileNum = customerMobileNum;
	}

	public String getCustomerDocumentNum() {
		return customerDocumentNum;
	}

	public void setCustomerDocumentNum(String customerDocumentNum) {
		this.customerDocumentNum = customerDocumentNum;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Long getCrewId() {
		return crewId;
	}

	public void setCrewId(Long crewId) {
		this.crewId = crewId;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Date getWoCreationDateInit() {
		return woCreationDateInit;
	}

	public void setWoCreationDateInit(Date woCreationDateInit) {
		this.woCreationDateInit = woCreationDateInit;
	}

	public Date getWoCreationDateEnd() {
		return woCreationDateEnd;
	}

	public void setWoCreationDateEnd(Date woCreationDateEnd) {
		this.woCreationDateEnd = woCreationDateEnd;
	}

	public Date getWoAgendationDateInit() {
		return woAgendationDateInit;
	}

	public void setWoAgendationDateInit(Date woAgendationDateInit) {
		this.woAgendationDateInit = woAgendationDateInit;
	}

	public Date getWoAgendationDateEnd() {
		return woAgendationDateEnd;
	}

	public void setWoAgendationDateEnd(Date woAgendationDateEnd) {
		this.woAgendationDateEnd = woAgendationDateEnd;
	}

	public Date getWoAttentionDateInit() {
		return woAttentionDateInit;
	}

	public void setWoAttentionDateInit(Date woAttentionDateInit) {
		this.woAttentionDateInit = woAttentionDateInit;
	}

	public Date getWoAttentionDateEnd() {
		return woAttentionDateEnd;
	}

	public void setWoAttentionDateEnd(Date woAttentionDateEnd) {
		this.woAttentionDateEnd = woAttentionDateEnd;
	}

	public Date getWoFinalizationDateInit() {
		return woFinalizationDateInit;
	}

	public void setWoFinalizationDateInit(Date woFinalizationDateInit) {
		this.woFinalizationDateInit = woFinalizationDateInit;
	}

	public Date getWoFinalizationDateEnd() {
		return woFinalizationDateEnd;
	}

	public void setWoFinalizationDateEnd(Date woFinalizationDateEnd) {
		this.woFinalizationDateEnd = woFinalizationDateEnd;
	}

	public List<Long> getServiceHourIds() {
		return serviceHourIds;
	}

	public void setServiceHourIds(List<Long> serviceHourIds) {
		this.serviceHourIds = serviceHourIds;
	}

	public List<Long> getProgramIds() {
		return programIds;
	}

	public void setProgramIds(List<Long> programIds) {
		this.programIds = programIds;
	}

	public List<Long> getDealersIds() {
		return dealersIds;
	}

	public void setDealersIds(List<Long> dealersIds) {
		this.dealersIds = dealersIds;
	}

	public boolean isFromExpirationGrouping() {
		return fromExpirationGrouping;
	}

	public void setFromExpirationGrouping(boolean fromExpirationGrouping) {
		this.fromExpirationGrouping = fromExpirationGrouping;
	}

	public Date getExpirationGroupingInit() {
		return expirationGroupingInit;
	}

	public void setExpirationGroupingInit(Date expirationGroupingInit) {
		this.expirationGroupingInit = expirationGroupingInit;
	}

	public Date getExpirationGroupingEnd() {
		return expirationGroupingEnd;
	}

	public void setExpirationGroupingEnd(Date expirationGroupingEnd) {
		this.expirationGroupingEnd = expirationGroupingEnd;
	}

	public List<Long> getBranchIds() {
		return branchIds;
	}

	public void setBranchIds(List<Long> branchIds) {
		this.branchIds = branchIds;
	}

	public Date getWoCancelationDateInit() {
		return woCancelationDateInit;
	}

	public void setWoCancelationDateInit(Date woCancelationDateInit) {
		this.woCancelationDateInit = woCancelationDateInit;
	}

	public Date getWoCancelationDateEnd() {
		return woCancelationDateEnd;
	}

	public void setWoCancelationDateEnd(Date woCancelationDateEnd) {
		this.woCancelationDateEnd = woCancelationDateEnd;
	}

	public boolean isDealerToWO() {
		return dealerToWO;
	}

	public void setDealerToWO(boolean dealerToWO) {
		this.dealerToWO = dealerToWO;
	}

	public List<Long> getCustomerClassIds() {
		return customerClassIds;
	}

	public void setCustomerClassIds(List<Long> customerClassIds) {
		this.customerClassIds = customerClassIds;
	}

	public List<Long> getWorkOrderMarkIds() {
		return workOrderMarkIds;
	}

	public void setWorkOrderMarkIds(List<Long> workOrderMarkIds) {
		this.workOrderMarkIds = workOrderMarkIds;
	}

	public boolean isScheduledCSR() {
		return scheduledCSR;
	}

	public void setScheduledCSR(boolean scheduledcsr) {
		scheduledCSR = scheduledcsr;
	}

	public List<String> getProcessSourceCodes() {
		return processSourceCodes;
	}

	public void setProcessSourceCodes(List<String> processSourceCodes) {
		this.processSourceCodes = processSourceCodes;
	}

	
}
