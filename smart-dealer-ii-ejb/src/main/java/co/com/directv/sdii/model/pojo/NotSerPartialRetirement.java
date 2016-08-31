package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * NotSerPartialRetirement entity. @author MyEclipse Persistence Tools
 */

public class NotSerPartialRetirement implements java.io.Serializable,Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3636741091954212441L;
	// Fields

	private NotSerPartialRetirementId id;
	//private WarehouseElement warehouseElement;
	private Warehouse warehouseTarget;
	private Warehouse warehouseSource;

	// Constructors

	/** default constructor */
	public NotSerPartialRetirement() {
	}

	/** minimal constructor */
	public NotSerPartialRetirement(NotSerPartialRetirementId id,
			WarehouseElement warehouseElement) {
		this.id = id;
		//this.warehouseElement = warehouseElement;
	}
	/** full constructor */
	public NotSerPartialRetirement(NotSerPartialRetirementId id,
			WarehouseElement warehouseElement, Warehouse warehouseTarget, Warehouse warehouseSource) {
		this.id = id;
		//this.warehouseElement = warehouseElement;
		this.warehouseTarget = warehouseTarget;
		this.warehouseSource = warehouseSource;
	}

	// Property accessors

	public NotSerPartialRetirementId getId() {
		return this.id;
	}

	public void setId(NotSerPartialRetirementId id) {
		this.id = id;
	}

	//public WarehouseElement getWarehouseElement() {
	//	return this.warehouseElement;
	//}

	//public void setWarehouseElement(WarehouseElement warehouseElement) {
	//	this.warehouseElement = warehouseElement;
	//}
	
	public Warehouse getWarehouseTarget() {
		return this.warehouseTarget;
	}

	public void setWarehouseTarget(Warehouse warehouse) {
		this.warehouseTarget = warehouse;
	}

	public Warehouse getWarehouseSource() {
		return this.warehouseSource;
	}

	public void setWarehouseSource(Warehouse warehouse) {
		this.warehouseSource = warehouse;
	}

	@Override
	public String toString() {
		return "NotSerPartialRetirement [id=" + id + "]";
	}
}