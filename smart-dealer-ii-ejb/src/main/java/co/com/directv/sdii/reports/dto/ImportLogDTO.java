package co.com.directv.sdii.reports.dto;

import java.util.Date;

public class ImportLogDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8597300714062642459L;
	
	private Long id;
	private Date creationDate;
	private Date deliveryDate;
	private Date shippingDate;
	private String importLogStatusName;
	private String creationUserName;
	private String purchaseOrder;
	private String importDoc;
	private String descripcionEstado;
	private String serialized;
	private String notSerialized; 
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Date getShippingDate() {
		return shippingDate;
	}
	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}
	public String getImportLogStatusName() {
		return importLogStatusName;
	}
	public void setImportLogStatusName(String importLogStatusName) {
		this.importLogStatusName = importLogStatusName;
	}
	public String getCreationUserName() {
		return creationUserName;
	}
	public void setCreationUserName(String creationUserName) {
		this.creationUserName = creationUserName;
	}
	public String getPurchaseOrder() {
		return purchaseOrder;
	}
	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
	public String getImportDoc() {
		return importDoc;
	}
	public void setImportDoc(String importDoc) {
		this.importDoc = importDoc;
	}
	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}
	public String getDescripcionEstado() {
		return descripcionEstado;
	}
	public void setSerialized(String serialized) {
		this.serialized = serialized;
	}
	public String getSerialized() {
		return serialized;
	}
	public void setNotSerialized(String notSerialized) {
		this.notSerialized = notSerialized;
	}
	public String getNotSerialized() {
		return notSerialized;
	}
	
}
