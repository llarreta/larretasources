package co.com.directv.sdii.model.pojo;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;
import co.com.directv.sdii.rules.BusinessRequired;


/**
 * ImportLog entity. @author MyEclipse Persistence Tools
 */

public class ImportLog implements java.io.Serializable,Auditable {

	private static final long serialVersionUID = 6514205129642112160L;
	
	
	private Long id;
	@BusinessRequired (isComplexType=true, fieldNameRequired="id") 
	private Supplier supplier;
	@BusinessRequired (isComplexType=true, fieldNameRequired="id") 
	private Dealer dealer;
	@BusinessRequired (isComplexType=true, fieldNameRequired="id") 
	private ImportLogStatus importLogStatus;
	@BusinessRequired private String purchaseOrder;
	@BusinessRequired private Date deliveryDate;
	@BusinessRequired private Date shippingDate;
	@BusinessRequired private Date creationDate;
	@BusinessRequired (isComplexType=true, fieldNameRequired="id") 
	private User user;
	private String importDoc;
	private String legalDoc;
	private Country country;

	// Constructors

	/** default constructor */
	public ImportLog() {
	}

	/** minimal constructor */
	public ImportLog(Supplier supplier, ImportLogStatus importLogStatus,
			String purchaseOrder, Date deliveryDate, Date shippingDate,
			Date creationDate, User user, String legalTransportDoc,Country country) {
		this.supplier = supplier;
		this.importLogStatus = importLogStatus;
		this.purchaseOrder = purchaseOrder;
		this.deliveryDate = deliveryDate;
		this.shippingDate = shippingDate;
		this.creationDate = creationDate;
		this.user = user;
		this.importDoc = legalTransportDoc;
		this.country = country;
	}

	// Property accessors

	/**
	 * Usuario que crea la importacion
	 */
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Proveedor
	 * @return Supplier
	 */
	public Supplier getSupplier() {
		return this.supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	/**
	 * Estado del Registro de Importación
	 * @return ImportLogStatus
	 */
	public ImportLogStatus getImportLogStatus() {
		return this.importLogStatus;
	}

	public void setImportLogStatus(ImportLogStatus importLogStatus) {
		this.importLogStatus = importLogStatus;
	}

	/**
	 * Orden de Compra
	 * @return String
	 */
	public String getPurchaseOrder() {
		return this.purchaseOrder;
	}

	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	/**
	 * Fecha estimada de entrega
	 * @return Date
	 */
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	/**
	 * Fecha de Embarque
	 * @return Date
	 */
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getShippingDate() {
		return this.shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	/**
	 * Fecha de creacion
	 * @return Date
	 */
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Usuario de Creación
	 * @return Long
	 */
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * No de documento legal de transporte,
	 * se remplaza por Documento de importacion
	 * @return String
	 */
	public String getImportDoc() {
		return this.importDoc;
	}

	public void setImportDoc(String importDoc) {
		this.importDoc = importDoc;
	}
	
	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public Dealer getDealer() {
		return dealer;
	}

	@Override
	public String toString() {
		return "ImportLog [id=" + id + "]";
	}

	public void setLegalDoc(String legalDoc) {
		this.legalDoc = legalDoc;
	}

	public String getLegalDoc() {
		return legalDoc;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
}