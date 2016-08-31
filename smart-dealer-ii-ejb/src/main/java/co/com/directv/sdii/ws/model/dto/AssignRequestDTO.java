/**
 * Creado 14/06/2011 09:27:45
 */
package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Encapsula la información de request para el WS de asignación
 * 
 * Fecha de Creación: 14/06/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class AssignRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 847915657543339251L;

	/**
	 * código del país en formato ISO 2
	 */
	private String countryIso2Code;
	
	/**
	 * Determina si la evaluación de las habilidades debe realizarse en modalidad de
	 * asignación (AS) o agendamiento (AG). 
	 */
	private String executionMode;
	
	/**
	 * Código ibs del edificio, en caso que la instalación se realice en un edificio
	 */
	private String ibsBuildingCode;
	
	/**
	 * código de ibs del cliente
	 */
	private String ibsCustomerCode;
	
	/**
	 * name de ibs del cliente
	 */
	private String ibsCustomerName;
	
	/**
	 * Código de ibs del tipo de cliente, debe coincidir con los códigos homologados
	 * en SDII
	 */
	private String ibsCustomerTypeCode;
	
	private String ibsCustomerClassCode;
	
	private String ibsCustomerClassName;
	
	/**
	 * Código de ibs del tipo de cliente, debe coincidir con los códigos homologados
	 * en SDII
	 */
	private String ibsCustomerTypeName;
	
	/**
	 * Código ibs del dealer que realizó la venta de la work order en caso que esta
	 * sea de instalación
	 */
	private String ibsSaleDealerCode;
	
	/**
	 * Código ibs del dealer que realizó la venta de la work order en caso que esta
	 * sea de instalación
	 */
	private String ibsSaleDealerName;
	
	/**
	 * Código postal, debe existir en SmartDealer, se mantiene la nomenclatura de
	 * código postal debido a que en cada país este se configurará al nivel que sea
	 * necesario
	 */
	private String postalCode;
	
	/**
	 * Código postal, debe existir en SmartDealer, se mantiene la nomenclatura de
	 * código postal debido a que en cada país este se configurará al nivel que sea
	 * necesario
	 */
	private String postalName;
	
	/**
	 * Monto a recuperar, aplica para las Work Orders de cobranza
	 */
	private Double recoveryAmout;
	
	/**
	 * fecha de agendamiento acordada con el cliente para la prestación de los
	 * servicios
	 */
	private java.util.Date scheduleDate;
	
	/**
	 * fecha de agendamiento acordada con el cliente para la prestación de los
	 * servicios
	 */
	private java.util.Date scheduleDateIBS;
	
	/**
	 * código de la jornada en SDII
	 */
	private String sdiiServiceHourCode;
	
	/**
	 * Lista de códigos de los servicios que se prestarán al cliente, estos códigos
	 * deben coincidir entre los dos sistemas: IBS y SDII.
	 */
	private List<String> services;
	
	/**
	 * Lista de id de las shippingOrder en la work order
	 */
	private List<Long> shippingOrders;

	/**
	 * código de la dirección del cliente
	 */
	private String ibsCustomerAddressCode;
	
	/**
	 * Dirección del cliente
	 */
	private String ibsCustomerAddress;
	
	/**
	 * id del usuario AssignRequest
	 */
	private Long userId;

	/**
	 * codigo de la workorder
	 */
	private String woCode;
	
	/**
	 * identificador del dealer en caso que la agenda se deba consultar para un dealer específico
	 */
	private Long dealerId;
	
	/**
	 * Código ibs del edificio, en caso que la instalación se realice en un edificio
	 */
	private Long dealerIdIBS;
	
	/**
	 * Estado de la workorder
	 */
	private Long actualStatusId;
	
	/**
	 * Estado de la workorder
	 */
	private String actualStatusCode;
	
	/**
	 * Estado de la workorder
	 */
	private String actualStatusName;
	
	private String serviceSuperCatName;
	
	private boolean isCSR;
	private boolean isExternalSystem;
	
	private String userNameSystemExternal;
	private String ipSystemExternal;
	private String urlSystemExternal;
	
	private Long customerCategoryId;
	private Long businessAreaId;
	private Long serviceCategoryId;
	
	//Req-0096 - Requerimiento Consolidado Asignador / Automatizacion, Relacion de Edificios para Asignacion de Work Orders
	private Long customerBuilding;
	
	public String getCountryIso2Code() {
		return countryIso2Code;
	}

	public void setCountryIso2Code(String countryIso2Code) {
		this.countryIso2Code = countryIso2Code;
	}

	public String getIbsCustomerClassCode() {
		return ibsCustomerClassCode;
	}

	public void setIbsCustomerClassCode(String ibsCustomerClassCode) {
		this.ibsCustomerClassCode = ibsCustomerClassCode;
	}

	public String getExecutionMode() {
		return executionMode;
	}

	public void setExecutionMode(String executionMode) {
		this.executionMode = executionMode;
	}

	public String getIbsCustomerClassName() {
		return ibsCustomerClassName;
	}

	public void setIbsCustomerClassName(String ibsCustomerClassName) {
		this.ibsCustomerClassName = ibsCustomerClassName;
	}

	public String getIbsBuildingCode() {
		return ibsBuildingCode;
	}

	public void setIbsBuildingCode(String ibsBuildingCode) {
		this.ibsBuildingCode = ibsBuildingCode;
	}

	public String getIbsCustomerCode() {
		return ibsCustomerCode;
	}

	public void setIbsCustomerCode(String ibsCustomerCode) {
		this.ibsCustomerCode = ibsCustomerCode;
	}

	public String getIbsCustomerTypeCode() {
		return ibsCustomerTypeCode;
	}

	public void setIbsCustomerTypeCode(String ibsCustomerTypeCode) {
		this.ibsCustomerTypeCode = ibsCustomerTypeCode;
	}

	public String getIbsSaleDealerCode() {
		return ibsSaleDealerCode;
	}

	public void setIbsSaleDealerCode(String ibsSaleDealerCode) {
		this.ibsSaleDealerCode = ibsSaleDealerCode;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Double getRecoveryAmout() {
		return recoveryAmout;
	}

	public void setRecoveryAmout(Double recoveryAmout) {
		this.recoveryAmout = recoveryAmout;
	}

	public java.util.Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(java.util.Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public List<String> getServices() {
		return services;
	}

	public void setServices(List<String> services) {
		this.services = services;
	}

	public List<Long> getShippingOrders() {
		return shippingOrders;
	}

	public void setShippingOrders(List<Long> shippingOrders) {
		this.shippingOrders = shippingOrders;
	}

	public String getSdiiServiceHourCode() {
		return sdiiServiceHourCode;
	}

	public void setSdiiServiceHourCode(String sdiiServiceHourCode) {
		this.sdiiServiceHourCode = sdiiServiceHourCode;
	}

	public String getIbsCustomerAddressCode() {
		return ibsCustomerAddressCode;
	}

	public void setIbsCustomerAddressCode(String ibsCustomerAddressCode) {
		this.ibsCustomerAddressCode = ibsCustomerAddressCode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setWoCode(String woCode){
	    this.woCode = woCode;	
	}
	
	public String getWoCode(){
		return woCode;
	}
	
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public Long getDealerIdIBS() {
		return dealerIdIBS;
	}

	public void setDealerIdIBS(Long dealerIdIBS) {
		this.dealerIdIBS = dealerIdIBS;
	}

	public java.util.Date getScheduleDateIBS() {
		return scheduleDateIBS;
	}

	public void setScheduleDateIBS(java.util.Date scheduleDateIBS) {
		this.scheduleDateIBS = scheduleDateIBS;
	}

	public Long getActualStatusId() {
		return actualStatusId;
	}

	public void setActualStatusId(Long actualStatusId) {
		this.actualStatusId = actualStatusId;
	}

	public String getActualStatusCode() {
		return actualStatusCode;
	}

	public void setActualStatusCode(String actualStatusCode) {
		this.actualStatusCode = actualStatusCode;
	}

	public String getServiceSuperCatName() {
		return serviceSuperCatName;
	}

	public void setServiceSuperCatName(String serviceSuperCatName) {
		this.serviceSuperCatName = serviceSuperCatName;
	}

	public boolean isCSR() {
		return isCSR;
	}

	public void setCSR(boolean isCSR) {
		this.isCSR = isCSR;
	}

	public String getIbsCustomerName() {
		return ibsCustomerName;
	}

	public void setIbsCustomerName(String ibsCustomerName) {
		this.ibsCustomerName = ibsCustomerName;
	}

	public String getIbsCustomerTypeName() {
		return ibsCustomerTypeName;
	}

	public void setIbsCustomerTypeName(String ibsCustomerTypeName) {
		this.ibsCustomerTypeName = ibsCustomerTypeName;
	}

	public String getIbsSaleDealerName() {
		return ibsSaleDealerName;
	}

	public void setIbsSaleDealerName(String ibsSaleDealerName) {
		this.ibsSaleDealerName = ibsSaleDealerName;
	}

	public String getPostalName() {
		return postalName;
	}

	public void setPostalName(String postalName) {
		this.postalName = postalName;
	}

	public String getActualStatusName() {
		return actualStatusName;
	}

	public void setActualStatusName(String actualStatusName) {
		this.actualStatusName = actualStatusName;
	}

	public String getIbsCustomerAddress() {
		return ibsCustomerAddress;
	}

	public void setIbsCustomerAddress(String ibsCustomerAddress) {
		this.ibsCustomerAddress = ibsCustomerAddress;
	}

	public boolean isExternalSystem() {
		return isExternalSystem;
	}

	public void setExternalSystem(boolean isExternalSystem) {
		this.isExternalSystem = isExternalSystem;
	}

	public String getUserNameSystemExternal() {
		return userNameSystemExternal;
	}

	public void setUserNameSystemExternal(String userNameSystemExternal) {
		this.userNameSystemExternal = userNameSystemExternal;
	}

	public String getIpSystemExternal() {
		return ipSystemExternal;
	}

	public void setIpSystemExternal(String ipSystemExternal) {
		this.ipSystemExternal = ipSystemExternal;
	}

	public String getUrlSystemExternal() {
		return urlSystemExternal;
	}

	public void setUrlSystemExternal(String urlSystemExternal) {
		this.urlSystemExternal = urlSystemExternal;
	}

	public Long getCustomerBuilding() {
		return customerBuilding;
	}

	public void setCustomerBuilding(Long customerBuilding) {
		this.customerBuilding = customerBuilding;
	}

	public Long getCustomerCategoryId() {
		return customerCategoryId;
	}

	public void setCustomerCategoryId(Long customerCategoryId) {
		this.customerCategoryId = customerCategoryId;
	}

	public Long getBusinessAreaId() {
		return businessAreaId;
	}

	public void setBusinessAreaId(Long businessAreaId) {
		this.businessAreaId = businessAreaId;
	}

	public Long getServiceCategoryId() {
		return serviceCategoryId;
	}

	public void setServiceCategoryId(Long serviceCategoryId) {
		this.serviceCategoryId = serviceCategoryId;
	}

}
