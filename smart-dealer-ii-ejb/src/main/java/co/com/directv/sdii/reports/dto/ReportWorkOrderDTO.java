package co.com.directv.sdii.reports.dto;


/**
 * 
 * Clase encargada de mapear la información que se va a mostrar en el archivo xls de las bandejas 
 * 
 * Fecha de Creación: 13/07/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ReportWorkOrderDTO implements java.io.Serializable{

	private static final long serialVersionUID = -677180518084260345L;
	
	private String id;
	private String woCode;
	private String customerCode;
	private String customerDocument;
	private String customerName;
	private String customerTypeName;
	private String dealerName;
	private String depotCode;
	private String woStateName;
	private String woStateCode;
	private String ibs6StateName;
	private String reason;
	private String dateLastModification;
	private String model;
	private String creationDate;
	private String importDate;
	private String assignDealerDate;
	private String jornada;
	private String jornadaDate;
	private String woProgrammingDate;
	private String woRealizationDate;
	private String finalizationDate;
	private String woDescription;
	private String woAction;
	private String serviceTypeName;
	private String serviceTypeCode;
	private String serviceCode;
	private String serviceName;
	private String assignDealer;
	private String assignDealerCode;
	private String assignDealerCodeCode;
	private String postalCodeCode;
	private String stateName;
	private String cityName;
	private String responsableDoc;
	private String responsableName;
	private String helpers;
	
	private String customerPhoneHome;
	private String customerPhoneWork;
	private String customerCel;
	private String customerMail;
	private String customerFax;
	private String customerAddress;
	private String extraIndication;
	private String contDecos;
	private String previousVisits; 
	private String observationCompany; 
	private String retiredIRDSeries;
	private String retiredSCSeries;
	private String installedIRDSeries; 
	private String installedSCSeries;
	private String dispatcher;
	private String loginDispatcher;
	private String ibsTechnical;
	
	private String zoneTypeName;
	private String problemDescriptions;
	private String contactPersonAgenda;
	private String isRequiredContract;
	
	private String customerClass; 
	
	private String serialNumber;
	private String linkedSerialNumber;
	private String optimusStatus;
	private String optimusStatusDate;
	private String optimusDeclineReason;
	private String optimusDeclineDate;
	
	/**
	 * Constructor
	 */
	public ReportWorkOrderDTO() {
	}
	
	public ReportWorkOrderDTO(String id, String woCode, String customerCode,
			String customerDocument, String customerName,
			String customerTypeName, String dealerName, String depotCode,
			String woStateName, String woStateCode, String ibs6StateName,
			String reason, String dateLastModification, String model,
			String creationDate, String importDate, String assignDealerDate,
			String jornada, String jornadaDate, String woProgrammingDate,
			String woRealizationDate, String finalizationDate,
			String woDescription, String woAction, String serviceTypeName,
			String serviceTypeCode, String serviceCode, String serviceName,
			String assignDealer, String assignDealerCode,
			String assignDealerCodeCode, String postalCodeCode,
			String stateName, String cityName, String responsableDoc,
			String responsableName, String helpers, 
			String customerPhoneHome, String customerPhoneWork,
			String customerCel, String customerMail, String customerFax,
			String customerAddress, String extraIndication, String contDecos,
			String previousVisits, String observationCompany,
			String retiredIRDSeries, String retiredSCSeries,
			String installedIRDSeries, String installedSCSeries,
			String dispatcher, String loginDispatcher, String ibsTechnical,
			String zoneTypeName, String problemDescriptions,
			String contactPersonAgenda,String isRequiredContract,String customerClass,
		    String serialNumber, String linkedSerialNumber , String optimusStatus, String optimusStatusDate,
		    String optimusDeclineReason, String optimusDeclineDate) {
		super();
		this.id = id;
		this.woCode = woCode;
		this.customerCode = customerCode;
		this.customerDocument = customerDocument;
		this.customerName = customerName;
		this.customerTypeName = customerTypeName;
		this.dealerName = dealerName;
		this.depotCode = depotCode;
		this.woStateName = woStateName;
		this.woStateCode = woStateCode;
		this.ibs6StateName = ibs6StateName;
		this.reason = reason;
		this.dateLastModification = dateLastModification;
		this.model = model;
		this.creationDate = creationDate;
		this.importDate = importDate;
		this.assignDealerDate = assignDealerDate;
		this.jornada = jornada;
		this.jornadaDate = jornadaDate;
		this.woProgrammingDate = woProgrammingDate;
		this.woRealizationDate = woRealizationDate;
		this.finalizationDate = finalizationDate;
		this.woDescription = woDescription;
		this.woAction = woAction;
		this.serviceTypeName = serviceTypeName;
		this.serviceTypeCode = serviceTypeCode;
		this.serviceCode = serviceCode;
		this.serviceName = serviceName;
		this.assignDealer = assignDealer;
		this.assignDealerCode = assignDealerCode;
		this.assignDealerCodeCode = assignDealerCodeCode;
		this.postalCodeCode = postalCodeCode;
		this.stateName = stateName;
		this.cityName = cityName;
		this.responsableDoc = responsableDoc;
		this.responsableName = responsableName;
		this.helpers = helpers;
		this.customerPhoneHome = customerPhoneHome;
		this.customerPhoneWork = customerPhoneWork;
		this.customerCel = customerCel;
		this.customerMail = customerMail;
		this.customerFax = customerFax;
		this.customerAddress = customerAddress;
		this.extraIndication = extraIndication;
		this.contDecos = contDecos;
		this.previousVisits = previousVisits;
		this.observationCompany = observationCompany;
		this.retiredIRDSeries = retiredIRDSeries;
		this.retiredSCSeries = retiredSCSeries;
		this.installedIRDSeries = installedIRDSeries;
		this.installedSCSeries = installedSCSeries;
		this.dispatcher = dispatcher;
		this.loginDispatcher = loginDispatcher;
		this.ibsTechnical = ibsTechnical;
		this.zoneTypeName = zoneTypeName;
		this.problemDescriptions = problemDescriptions;
		this.contactPersonAgenda = contactPersonAgenda;
		this.isRequiredContract=isRequiredContract;
		this.customerClass=customerClass;
		this.serialNumber = serialNumber;
		this.linkedSerialNumber = linkedSerialNumber;
		this.optimusStatus = optimusStatus;
		this.optimusStatusDate = optimusStatusDate;
		this.optimusDeclineReason = optimusDeclineReason;
		this.optimusDeclineDate = optimusDeclineDate;
		
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWoCode() {
		return woCode;
	}

	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerDocument() {
		return customerDocument;
	}

	public void setCustomerDocument(String customerDocument) {
		this.customerDocument = customerDocument;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerTypeName() {
		return customerTypeName;
	}

	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDepotCode() {
		return depotCode;
	}

	public void setDepotCode(String depotCode) {
		this.depotCode = depotCode;
	}

	public String getWoStateName() {
		return woStateName;
	}

	public void setWoStateName(String woStateName) {
		this.woStateName = woStateName;
	}

	public String getWoStateCode() {
		return woStateCode;
	}

	public void setWoStateCode(String woStateCode) {
		this.woStateCode = woStateCode;
	}

	public String getIbs6StateName() {
		return ibs6StateName;
	}

	public void setIbs6StateName(String ibs6StateName) {
		this.ibs6StateName = ibs6StateName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDateLastModification() {
		return dateLastModification;
	}

	public void setDateLastModification(String dateLastModification) {
		this.dateLastModification = dateLastModification;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getImportDate() {
		return importDate;
	}

	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

	public String getAssignDealerDate() {
		return assignDealerDate;
	}

	public void setAssignDealerDate(String assignDealerDate) {
		this.assignDealerDate = assignDealerDate;
	}

	public String getJornada() {
		return jornada;
	}

	public void setJornada(String jornada) {
		this.jornada = jornada;
	}

	public String getJornadaDate() {
		return jornadaDate;
	}

	public void setJornadaDate(String jornadaDate) {
		this.jornadaDate = jornadaDate;
	}

	public String getWoProgrammingDate() {
		return woProgrammingDate;
	}

	public void setWoProgrammingDate(String woProgrammingDate) {
		this.woProgrammingDate = woProgrammingDate;
	}

	public String getWoRealizationDate() {
		return woRealizationDate;
	}

	public void setWoRealizationDate(String woRealizationDate) {
		this.woRealizationDate = woRealizationDate;
	}

	public String getFinalizationDate() {
		return finalizationDate;
	}

	public void setFinalizationDate(String finalizationDate) {
		this.finalizationDate = finalizationDate;
	}

	public String getWoDescription() {
		return woDescription;
	}

	public void setWoDescription(String woDescription) {
		this.woDescription = woDescription;
	}

	public String getWoAction() {
		return woAction;
	}

	public void setWoAction(String woAction) {
		this.woAction = woAction;
	}

	public String getServiceTypeName() {
		return serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public String getServiceTypeCode() {
		return serviceTypeCode;
	}

	public void setServiceTypeCode(String serviceTypeCode) {
		this.serviceTypeCode = serviceTypeCode;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getAssignDealer() {
		return assignDealer;
	}

	public void setAssignDealer(String assignDealer) {
		this.assignDealer = assignDealer;
	}

	public String getAssignDealerCode() {
		return assignDealerCode;
	}

	public void setAssignDealerCode(String assignDealerCode) {
		this.assignDealerCode = assignDealerCode;
	}

	public String getAssignDealerCodeCode() {
		return assignDealerCodeCode;
	}

	public void setAssignDealerCodeCode(String assignDealerCodeCode) {
		this.assignDealerCodeCode = assignDealerCodeCode;
	}

	public String getPostalCodeCode() {
		return postalCodeCode;
	}

	public void setPostalCodeCode(String postalCodeCode) {
		this.postalCodeCode = postalCodeCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getResponsableDoc() {
		return responsableDoc;
	}

	public void setResponsableDoc(String responsableDoc) {
		this.responsableDoc = responsableDoc;
	}

	public String getResponsableName() {
		return responsableName;
	}

	public void setResponsableName(String responsableName) {
		this.responsableName = responsableName;
	}

	public String getHelpers() {
		return helpers;
	}

	public void setHelpers(String helpers) {
		this.helpers = helpers;
	}

	public String getCustomerPhoneHome() {
		return customerPhoneHome;
	}

	public void setCustomerPhoneHome(String customerPhoneHome) {
		this.customerPhoneHome = customerPhoneHome;
	}

	public String getCustomerPhoneWork() {
		return customerPhoneWork;
	}

	public void setCustomerPhoneWork(String customerPhoneWork) {
		this.customerPhoneWork = customerPhoneWork;
	}

	public String getCustomerCel() {
		return customerCel;
	}

	public void setCustomerCel(String customerCel) {
		this.customerCel = customerCel;
	}

	public String getCustomerMail() {
		return customerMail;
	}

	public void setCustomerMail(String customerMail) {
		this.customerMail = customerMail;
	}

	public String getCustomerFax() {
		return customerFax;
	}

	public void setCustomerFax(String customerFax) {
		this.customerFax = customerFax;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getExtraIndication() {
		return extraIndication;
	}

	public void setExtraIndication(String extraIndication) {
		this.extraIndication = extraIndication;
	}

	public String getContDecos() {
		return contDecos;
	}

	public void setContDecos(String contDecos) {
		this.contDecos = contDecos;
	}

	public String getPreviousVisits() {
		return previousVisits;
	}

	public void setPreviousVisits(String previousVisits) {
		this.previousVisits = previousVisits;
	}

	public String getObservationCompany() {
		return observationCompany;
	}

	public void setObservationCompany(String observationCompany) {
		this.observationCompany = observationCompany;
	}

	public String getRetiredIRDSeries() {
		return retiredIRDSeries;
	}

	public void setRetiredIRDSeries(String retiredIRDSeries) {
		this.retiredIRDSeries = retiredIRDSeries;
	}

	public String getRetiredSCSeries() {
		return retiredSCSeries;
	}

	public void setRetiredSCSeries(String retiredSCSeries) {
		this.retiredSCSeries = retiredSCSeries;
	}

	public String getInstalledIRDSeries() {
		return installedIRDSeries;
	}

	public void setInstalledIRDSeries(String installedIRDSeries) {
		this.installedIRDSeries = installedIRDSeries;
	}

	public String getInstalledSCSeries() {
		return installedSCSeries;
	}

	public void setInstalledSCSeries(String installedSCSeries) {
		this.installedSCSeries = installedSCSeries;
	}

	public String getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}

	public String getLoginDispatcher() {
		return loginDispatcher;
	}

	public void setLoginDispatcher(String loginDispatcher) {
		this.loginDispatcher = loginDispatcher;
	}

	public String getIbsTechnical() {
		return ibsTechnical;
	}

	public void setIbsTechnical(String ibsTechnical) {
		this.ibsTechnical = ibsTechnical;
	}

	public String getZoneTypeName() {
		return zoneTypeName;
	}

	public void setZoneTypeName(String zoneTypeName) {
		this.zoneTypeName = zoneTypeName;
	}

	public String getProblemDescriptions() {
		return problemDescriptions;
	}

	public void setProblemDescriptions(String problemDescriptions) {
		this.problemDescriptions = problemDescriptions;
	}

	public String getContactPersonAgenda() {
		return contactPersonAgenda;
	}

	public void setContactPersonAgenda(String contactPersonAgenda) {
		this.contactPersonAgenda = contactPersonAgenda;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIsRequiredContract() {
		return isRequiredContract;
	}

	public void setIsRequiredContract(String isRequiredContract) {
		this.isRequiredContract = isRequiredContract;
	}

	public String getCustomerClass() {
		return customerClass;
	}

	public void setCustomerClass(String customerClass) {
		this.customerClass = customerClass;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getLinkedSerialNumber() {
		return linkedSerialNumber;
	}

	public void setLinkedSerialNumber(String linkedSerialNumber) {
		this.linkedSerialNumber = linkedSerialNumber;
	}

	public String getOptimusStatus() {
		return optimusStatus;
	}

	public void setOptimusStatus(String optimusStatus) {
		this.optimusStatus = optimusStatus;
	}

	public String getOptimusStatusDate() {
		return optimusStatusDate;
	}

	public void setOptimusStatusDate(String optimusStatusDate) {
		this.optimusStatusDate = optimusStatusDate;
	}

	public String getOptimusDeclineReason() {
		return optimusDeclineReason;
	}

	public void setOptimusDeclineReason(String optimusDeclineReason) {
		this.optimusDeclineReason = optimusDeclineReason;
	}

	public String getOptimusDeclineDate() {
		return optimusDeclineDate;
	}

	public void setOptimusDeclineDate(String optimusDeclineDate) {
		this.optimusDeclineDate = optimusDeclineDate;
	}

}
