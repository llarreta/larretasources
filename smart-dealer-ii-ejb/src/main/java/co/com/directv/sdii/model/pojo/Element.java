package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * Element entity. @author MyEclipse Persistence Tools
 */

public class Element implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6764352383387969503L;
	private Long id;
	private ElementStatus elementStatus;
	private Supplier supplier;
	private ElementType elementType;
	private Double warrantyPeriod;
	private String isSerialized;
	private String lote;
	private Country country;
	
	// Constructors

	/** default constructor */
	public Element() {
	}

	/** minimal constructor */
	public Element(ElementStatus elementStatus,
			ElementType elementType, String isSerialized,
			Country country) {
		this.elementStatus = elementStatus;
		this.elementType = elementType;
		this.isSerialized = isSerialized;
		this.country = country;
	}

	/** full constructor */
	public Element(ElementStatus elementStatus, 
			Supplier supplier, 
			ElementType elementType, 
			Double warrantyPeriod, String isSerialized, String lote,
			Country country) {
		this.elementStatus = elementStatus;
		this.supplier = supplier;
		this.elementType = elementType;		
		this.warrantyPeriod = warrantyPeriod;
		this.isSerialized = isSerialized;
		this.lote = lote;
		this.country = country;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public ElementStatus getElementStatus() {
		return this.elementStatus;
	}

	public void setElementStatus(ElementStatus elementStatus) {
		this.elementStatus = elementStatus;
	}

	public Supplier getSupplier() {
		return this.supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public ElementType getElementType() {
		return this.elementType;
	}

	public void setElementType(ElementType elementType) {
		this.elementType = elementType;
	}
	
	public Double getWarrantyPeriod() {
		return this.warrantyPeriod;
	}

	public void setWarrantyPeriod(Double warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}

	public String getIsSerialized() {
		return this.isSerialized;
	}

	public void setIsSerialized(String isSerialized) {
		this.isSerialized = isSerialized;
	}

	public String getLote() {
		return this.lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Element [id=" + id + "]";
	}
	
}