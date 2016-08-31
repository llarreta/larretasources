package co.com.directv.sdii.assign.assignment.dto;

import java.util.Date;
import java.util.List;

import co.com.directv.sdii.model.vo.DealerVO;

/**
 * Encapsula la información que consumirán las habilidades para su evaluación,
 * deberá contener la suma de los parámetros que requieren las habilidades para su
 * evaluación, en caso que se genere una nueva habilidad que requiera un nuevo
 * parámetro del cliente o servicios no contemplado en este DTO, será necesario
 * modificarlo y agregar dicho parámetro
 * @version 1.0,  &nbsp; 26-may-2011 15:11:48
 */
public class SkillEvaluationDTO implements java.io.Serializable{

	private static final long serialVersionUID = 2768368892803348327L;

	/**
	 * código del país en formato ISO 2
	 */
	private String countryIso2Code;
	
	private Long countryId;
	/**
	 * Determina si la evaluación de las habilidades debe realizarse en modalidad de
	 * asignación (AS) o agendamiento (AG). 
	 */
	private String executionMode;
	
	private Long executionModeId;
	/**
	 * C�digo ibs del edificio, en caso que la instalaci�n se realice en un edificio
	 */
	private String ibsBuildingCode;
	/**
	 * c�digo de ibs del cliente
	 */
	private String ibsCustomerCode;
	/**
	 * C�digo de ibs del tipo de cliente, debe coincidir con los c�digos homologados
	 * en SDII
	 */
	
	private Long ibsCustomerTypeId;

	/**
	 * C�digo ibs del dealer que realiz� la venta de la work order en caso que esta
	 * sea de instalaci�n
	 */
	private String ibsSaleDealerCode;
	/**
	 * C�digo postal, debe existir en SmartDealer, se mantiene la nomenclatura de
	 * c�digo postal debido a que en cada pa�s este se configurar� al nivel que sea
	 * necesario
	 */
	private String postalCode;
	
	private Long postalCodeId;
	/**
	 * Monto a recuperar, aplica para las Work Orders de cobranza
	 */
	private Double recoveryAmout;
	/**
	 * fecha de agendamiento acordada con el cliente para la prestaci�n de los
	 * servicios
	 */
	private java.util.Date scheduleDate;
	/**
	 * identificador de la jornada en SDII
	 */
	private Long serviceHourId;
	
	private String serviceHourCode;
	
	/**
	 * identificador de la super categoría de servicio del primero de los servicios de la lista
	 */
	private Long serviceSupercategoyId;
	
	/**
	 * Lista de c�digos de los servicios que se prestar�n al cliente, estos c�digos
	 * deben coincidir entre los dos sistemas: IBS y SDII.
	 */
	private List<String> services;
	
	/**
	 * Lista de id de las shippingOrder en la work order
	 */
	private List<Long> shippingOrders;
	
	/**
	 * Lista de compañías instaladoras que se reciben de la evaluación de la habilidad anterior
	 * para en caso que la lista venga vacía se asumirá que es porque se está realizando la evaluación de la primera
	 * habilidad 
	 */
	private List<DealerVO> dealerList;
	
	
	/**
	 * código de la dirección del cliente
	 */
	private String addressCode;
	
	/**
	 * Dirección del cliente
	 */
	private String address; 
	
	/**
	 * Usuario responsable del cambio
	 */
	private Long userId;
	
	/**
	 * Usuario responsable del cambio
	 */
	private Long workOrderCSRId;
	
	/**
	 * Usuario responsable del cambio
	 */
	private boolean isCSR;
	
	private Long customerCategoryId;
	
	private Long businessAreaId;
	
	private Long serviceCategoryId;
	
	public SkillEvaluationDTO(){

	}


	public SkillEvaluationDTO(String countryIso2Code, String executionMode,
			String ibsBuildingCode, String ibsCustomerCode,
			String ibsSaleDealerCode,
			String postalCode, Double recoveryAmout, Date scheduleDate,
			Long serviceHourId, Long serviceSupercategoyId,
			List<String> services,
			Long userId) {
		super();
		this.countryIso2Code = countryIso2Code;
		this.executionMode = executionMode;
		this.ibsBuildingCode = ibsBuildingCode;
		this.ibsCustomerCode = ibsCustomerCode;
		this.ibsSaleDealerCode = ibsSaleDealerCode;
		this.postalCode = postalCode;
		this.recoveryAmout = recoveryAmout;
		this.scheduleDate = scheduleDate;
		this.serviceHourId = serviceHourId;
		this.serviceSupercategoyId = serviceSupercategoyId;
		this.services = services;
		this.userId = userId;
	}




	public String getCountryIso2Code() {
		return countryIso2Code;
	}



	public void setCountryIso2Code(String countryIso2Code) {
		this.countryIso2Code = countryIso2Code;
	}



	public String getExecutionMode() {
		return executionMode;
	}



	public void setExecutionMode(String executionMode) {
		this.executionMode = executionMode;
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



	public Long getServiceHourId() {
		return serviceHourId;
	}



	public void setServiceHourId(Long serviceHourId) {
		this.serviceHourId = serviceHourId;
	}

	public List<Long> getShippingOrders() {
		return shippingOrders;
	}

	public void setShippingOrders(List<Long> shippingOrders) {
		this.shippingOrders = shippingOrders;
	}

	public List<String> getServices() {
		return services;
	}



	public void setServices(List<String> services) {
		this.services = services;
	}
	


	public Long getServiceSupercategoyId() {
		return serviceSupercategoyId;
	}

	public void setServiceSupercategoyId(Long serviceSupercategoyId) {
		this.serviceSupercategoyId = serviceSupercategoyId;
	}
	
	public Long getCountryId() {
		return countryId;
	}


	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}


	public Long getExecutionModeId() {
		return executionModeId;
	}


	public void setExecutionModeId(Long executionModeId) {
		this.executionModeId = executionModeId;
	}


	public Long getIbsCustomerTypeId() {
		return ibsCustomerTypeId;
	}


	public void setIbsCustomerTypeId(Long ibsCustomerTypeId) {
		this.ibsCustomerTypeId = ibsCustomerTypeId;
	}


	public Long getPostalCodeId() {
		return postalCodeId;
	}


	public void setPostalCodeId(Long postalCodeId) {
		this.postalCodeId = postalCodeId;
	}


	public String getServiceHourCode() {
		return serviceHourCode;
	}


	public void setServiceHourCode(String serviceHourCode) {
		this.serviceHourCode = serviceHourCode;
	}
	

	public List<DealerVO> getDealerList() {
		return dealerList;
	}


	public void setDealerList(List<DealerVO> dealerList) {
		this.dealerList = dealerList;
	}
	

	public String getAddressCode() {
		return addressCode;
	}


	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}
	
	public void finalize() throws Throwable {

	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getWorkOrderCSRId() {
		return workOrderCSRId;
	}

	public void setWorkOrderCSRId(Long workOrderCSRId) {
		this.workOrderCSRId = workOrderCSRId;
	}

	public boolean isCSR() {
		return isCSR;
	}

	public void setCSR(boolean isCSR) {
		this.isCSR = isCSR;
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