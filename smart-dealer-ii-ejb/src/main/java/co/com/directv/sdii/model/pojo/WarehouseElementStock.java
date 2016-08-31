package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * WarehouseElementStock entity. @author MyEclipse Persistence Tools
 */

public class WarehouseElementStock implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -677356496119929790L;
	private Long id;
	private ElementType elementType;
	private Warehouse warehouse;
	private Double minQuantity;
	private Double maxQuantity;
	private Double reorderQuantity;
	private String comments;

	// Constructors

	/** default constructor */
	public WarehouseElementStock() {
	}

	/** minimal constructor */
	public WarehouseElementStock(ElementType elementType, Warehouse warehouse,
			Double minQuantity, Double maxQuantity, Double reorderQuantity) {
		this.elementType = elementType;
		this.warehouse = warehouse;
		this.minQuantity = minQuantity;
		this.maxQuantity = maxQuantity;
		this.reorderQuantity = reorderQuantity;
	}

	/** full constructor */
	public WarehouseElementStock(ElementType elementType, Warehouse warehouse,
			Double minQuantity, Double maxQuantity, Double reorderQuantity,
			String comments) {
		this.elementType = elementType;
		this.warehouse = warehouse;
		this.minQuantity = minQuantity;
		this.maxQuantity = maxQuantity;
		this.reorderQuantity = reorderQuantity;
		this.comments = comments;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Warehouse getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Double getMinQuantity() {
		return this.minQuantity;
	}

	public void setMinQuantity(Double minQuantity) {
		this.minQuantity = minQuantity;
	}

	public Double getMaxQuantity() {
		return this.maxQuantity;
	}

	public void setMaxQuantity(Double maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public Double getReorderQuantity() {
		return this.reorderQuantity;
	}

	public void setReorderQuantity(Double reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public ElementType getElementType() {
		return elementType;
	}

	public void setElementType(ElementType elementType) {
		this.elementType = elementType;
	}

	@Override
	public String toString() {
		return "WarehouseElementStock [elementType=" + elementType + ", id="
				+ id + ", maxQuantity=" + maxQuantity + ", minQuantity="
				+ minQuantity + ", reorderQuantity=" + reorderQuantity
				+ ", warehouse=" + warehouse + "]";
	}
}