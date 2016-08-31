package co.com.directv.sdii.model.dto;

import java.util.Date;

import co.com.directv.sdii.model.vo.ServiceTypeVO;
import co.com.directv.sdii.model.vo.TechnologyVO;

/**
 * 
 * Clase que contiene la información a ser visualizada en el pdf 
 * 
 * Fecha de Creación: 11/07/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class PdfServiceDTO implements java.io.Serializable{

	private static final long serialVersionUID = 7686667265551964670L;
	
	private String woCode;
	private String shippingOrderCode;
	private TechnologyVO technology;
	private ServiceTypeVO serviceType;
	private String serviceName;
	private String serialNumber;
	private String ibsServiceKey;
	private String linkedSerialNumber;
	private Date finalizationDate;
	private String woAction;
	private String technicianName;
	private String technicianObservations;
	
	public String getWoCode() {
		return woCode;
	}
	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}
	public String getShippingOrderCode() {
		return shippingOrderCode;
	}
	public void setShippingOrderCode(String shippingOrderCode) {
		this.shippingOrderCode = shippingOrderCode;
	}
	public ServiceTypeVO getServiceType() {
		return serviceType;
	}
	public void setServiceType(ServiceTypeVO serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public TechnologyVO getTechnology() {
		return technology;
	}
	public void setTechnology(TechnologyVO technology) {
		this.technology = technology;
	}
	public Date getFinalizationDate() {
		return finalizationDate;
	}
	public void setFinalizationDate(Date finalizationDate) {
		this.finalizationDate = finalizationDate;
	}
	public String getWoAction() {
		return woAction;
	}
	public void setWoAction(String woAction) {
		this.woAction = woAction;
	}
	public String getTechnicianName() {
		return technicianName;
	}
	public void setTechnicianName(String technicianName) {
		this.technicianName = technicianName;
	}
	public String getTechnicianObservations() {
		return technicianObservations;
	}
	public void setTechnicianObservations(String technicianObservations) {
		this.technicianObservations = technicianObservations;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getIbsServiceKey() {
		return ibsServiceKey;
	}
	public void setIbsServiceKey(String ibsServiceKey) {
		this.ibsServiceKey = ibsServiceKey;
	}
	public String getLinkedSerialNumber() {
		return linkedSerialNumber;
	}
	public void setLinkedSerialNumber(String linkedSerialNumber) {
		this.linkedSerialNumber = linkedSerialNumber;
	}
}
