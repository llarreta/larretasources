package co.com.directv.sdii.model.dto;

import java.io.Serializable;

public class MonthlyActivityResponseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7547987753935509573L;

	private String id;
	private String woCode;
	private String customerCode;
	private String customerName;
	private String customerTypeName;
	private String dealerNameS;
	private String depotCodeS;
	private String woStateName;
	private String ibs6StateName;
	private String reason;
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
	private String postalCodeCode;
	private String stateName;
	private String cityName;
	private String responsableDoc;
	private String responsableName;
	private String reasonDesc;
	private String dealerNameWo;
	private String deptocodeWo;
	private String crewId;
	private String customerPhone;
	private String customerAddress;
	private String contDecos;
	private String previousVisits;
	private String ibsTechnical;
	private String observationCompany;
	private String retiredIRDSeries;
	private String retiredSCSeries;
	private String installedIRDSeries;
	private String installedSCSeries;
	private String dispatcher;
	private String loginDispatcher;
	private String dateFirstScheduling;
	private String firstResponsibleCrewAssign;
	private String codeDepotDealerFirstAssign;
	private String nameDealerFirstAssign;
	private String helpers;

	public MonthlyActivityResponseDTO(String id, String woCode,
			String customerCode, String customerName, String customerTypeName,
			String dealerNameS, String depotCodeS, String woStateName,
			String ibs6StateName, String reason, String model,
			String creationDate, String importDate, String assignDealerDate,
			String jornada, String jornadaDate, String woProgrammingDate,
			String woRealizationDate, String finalizationDate,
			String woDescription, String woAction, String serviceTypeName,
			String serviceTypeCode, String serviceCode, String serviceName,
			String assignDealer, String assignDealerCode,
			String postalCodeCode, String stateName, String cityName,
			String responsableDoc, String responsableName, String reasonDesc,
			String dealerNameWo, String deptocodeWo, String crewId,
			String customerPhone, String customerAddress, String contDecos,
			String previousVisits, String ibsTechnical,String observationCompany,
			String retiredIRDSeries, String retiredSCSeries,
			String installedIRDSeries, String installedSCSeries,
			String dispatcher,String loginDispatcher, String dateFirstScheduling,
			String firstResponsibleCrewAssign,
			String codeDepotDealerFirstAssign, String nameDealerFirstAssign,
			String helpers) {
		super();
		this.id = id;
		this.woCode = woCode;
		this.customerCode = customerCode;
		this.customerName = customerName;
		this.customerTypeName = customerTypeName;
		this.dealerNameS = dealerNameS;
		this.depotCodeS = depotCodeS;
		this.woStateName = woStateName;
		this.ibs6StateName = ibs6StateName;
		this.reason = reason;
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
		this.postalCodeCode = postalCodeCode;
		this.stateName = stateName;
		this.cityName = cityName;
		this.responsableDoc = responsableDoc;
		this.responsableName = responsableName;
		this.reasonDesc = reasonDesc;
		this.dealerNameWo = dealerNameWo;
		this.deptocodeWo = deptocodeWo;
		this.crewId = crewId;
		this.customerPhone = customerPhone;
		this.customerAddress = customerAddress;
		this.contDecos = contDecos;
		this.previousVisits = previousVisits;
		this.ibsTechnical = ibsTechnical;
		this.observationCompany = observationCompany;
		this.retiredIRDSeries = retiredIRDSeries;
		this.retiredSCSeries = retiredSCSeries;
		this.installedIRDSeries = installedIRDSeries;
		this.installedSCSeries = installedSCSeries;
		this.dispatcher = dispatcher;
		this.loginDispatcher = loginDispatcher; 
		this.dateFirstScheduling = dateFirstScheduling;
		this.firstResponsibleCrewAssign = firstResponsibleCrewAssign;
		this.codeDepotDealerFirstAssign = codeDepotDealerFirstAssign;
		this.nameDealerFirstAssign = nameDealerFirstAssign;
		this.helpers = helpers;
	}

	public String getHelpers() {
		return helpers;
	}

	public void setHelpers(String helpers) {
		this.helpers = helpers;
	}

	public MonthlyActivityResponseDTO() {
		super();
	}
	
	public MonthlyActivityResponseDTO(String id, String woCode,
			String customerCode, String customerName, String customerTypeName,
			String dealerNameS, String depotCodeS, String woStateName,
			String ibs6StateName, String reason, String model,
			String creationDate, String importDate, String assignDealerDate,
			String jornada, String jornadaDate, String woProgrammingDate,
			String woRealizationDate, String finalizationDate,
			String woDescription, String woAction, String serviceTypeName,
			String serviceTypeCode, String serviceCode, String serviceName,
			String assignDealer, String assignDealerCode,
			String postalCodeCode, String stateName, String cityName,
			String responsableDoc, String responsableName, String reasonDesc,
			String dealerNameWo, String deptocodeWo, String crewId,
			String customerPhone, String customerAddress, String contDecos,
			String previousVisits, String ibsTechnical, String observationCompany,
			String retiredIRDSeries, String retiredSCSeries,
			String installedIRDSeries, String installedSCSeries,
			String dispatcher,String loginDispatcher, String dateFirstScheduling,
			String firstResponsibleCrewAssign,
			String codeDepotDealerFirstAssign, String nameDealerFirstAssign) {
		super();
		this.id = id;
		this.woCode = woCode;
		this.customerCode = customerCode;
		this.customerName = customerName;
		this.customerTypeName = customerTypeName;
		this.dealerNameS = dealerNameS;
		this.depotCodeS = depotCodeS;
		this.woStateName = woStateName;
		this.ibs6StateName = ibs6StateName;
		this.reason = reason;
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
		this.postalCodeCode = postalCodeCode;
		this.stateName = stateName;
		this.cityName = cityName;
		this.responsableDoc = responsableDoc;
		this.responsableName = responsableName;
		this.reasonDesc = reasonDesc;
		this.dealerNameWo = dealerNameWo;
		this.deptocodeWo = deptocodeWo;
		this.crewId = crewId;
		this.customerPhone = customerPhone;
		this.customerAddress = customerAddress;
		this.contDecos = contDecos;
		this.previousVisits = previousVisits;
		this.ibsTechnical = ibsTechnical;
		this.observationCompany = observationCompany;
		this.retiredIRDSeries = retiredIRDSeries;
		this.retiredSCSeries = retiredSCSeries;
		this.installedIRDSeries = installedIRDSeries;
		this.installedSCSeries = installedSCSeries;
		this.dispatcher = dispatcher;
		this.loginDispatcher = loginDispatcher;
		this.dateFirstScheduling = dateFirstScheduling;
		this.firstResponsibleCrewAssign = firstResponsibleCrewAssign;
		this.codeDepotDealerFirstAssign = codeDepotDealerFirstAssign;
		this.nameDealerFirstAssign = nameDealerFirstAssign;
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
	public String getDealerNameS() {
		return dealerNameS;
	}
	public void setDealerNameS(String dealerNameS) {
		this.dealerNameS = dealerNameS;
	}
	public String getDepotCodeS() {
		return depotCodeS;
	}
	public void setDepotCodeS(String depotCodeS) {
		this.depotCodeS = depotCodeS;
	}
	public String getWoStateName() {
		return woStateName;
	}
	public void setWoStateName(String woStateName) {
		this.woStateName = woStateName;
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
	public String getReasonDesc() {
		return reasonDesc;
	}
	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}
	public String getDealerNameWo() {
		return dealerNameWo;
	}
	public void setDealerNameWo(String dealerNameWo) {
		this.dealerNameWo = dealerNameWo;
	}
	public String getDeptocodeWo() {
		return deptocodeWo;
	}
	public void setDeptocodeWo(String deptocodeWo) {
		this.deptocodeWo = deptocodeWo;
	}
	public String getCrewId() {
		return crewId;
	}
	public void setCrewId(String crewId) {
		this.crewId = crewId;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
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
	public String getDateFirstScheduling() {
		return dateFirstScheduling;
	}
	public void setDateFirstScheduling(String dateFirstScheduling) {
		this.dateFirstScheduling = dateFirstScheduling;
	}
	public String getFirstResponsibleCrewAssign() {
		return firstResponsibleCrewAssign;
	}
	public void setFirstResponsibleCrewAssign(String firstResponsibleCrewAssign) {
		this.firstResponsibleCrewAssign = firstResponsibleCrewAssign;
	}
	public String getCodeDepotDealerFirstAssign() {
		return codeDepotDealerFirstAssign;
	}
	public void setCodeDepotDealerFirstAssign(String codeDepotDealerFirstAssign) {
		this.codeDepotDealerFirstAssign = codeDepotDealerFirstAssign;
	}
	public String getNameDealerFirstAssign() {
		return nameDealerFirstAssign;
	}
	public void setNameDealerFirstAssign(String nameDealerFirstAssign) {
		this.nameDealerFirstAssign = nameDealerFirstAssign;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getIbsTechnical() {
		return ibsTechnical;
	}
	public void setIbsTechnical(String ibsTechnical) {
		this.ibsTechnical = ibsTechnical;
	}
	
}
