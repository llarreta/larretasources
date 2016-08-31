package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * 
 * Objeto que mapea la informacion de la respuesta del servicio de customer que consulta los elementos
 * del cliente 
 * 
 * Fecha de Creación: 15/07/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class CustomerResourcesDTO implements Serializable {

	private static final long serialVersionUID = -6734508805411644577L;
	
	private String description;
	private String objectID;
	private String serialNumber;
	private String statusId;
	private String statusName;
	private String deviceModelId;
	private String deviceModelName;
	private Integer technicalProdcutId;
	private String productId;
	private CustomerResourcesDTO linkedResource;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String desciption) {
		this.description = desciption;
	}
	public String getObjectID() {
		return objectID;
	}
	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getDeviceModelId() {
		return deviceModelId;
	}
	public void setDeviceModelId(String deviceModelId) {
		this.deviceModelId = deviceModelId;
	}
	public String getDeviceModelName() {
		return deviceModelName;
	}
	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}
	public Integer getTechnicalProdcutId() {
		return technicalProdcutId;
	}
	public void setTechnicalProdcutId(Integer technicalProdcutId) {
		this.technicalProdcutId = technicalProdcutId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public CustomerResourcesDTO getLinkedResource() {
		return linkedResource;
	}
	public void setLinkedResource(CustomerResourcesDTO linkedResource) {
		this.linkedResource = linkedResource;
	}
}
