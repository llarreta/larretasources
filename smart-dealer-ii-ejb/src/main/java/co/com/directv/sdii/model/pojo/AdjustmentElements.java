package co.com.directv.sdii.model.pojo;

import java.util.Date;



/**
 * AdjustmentElements entity. @author MyEclipse Persistence Tools
 */

public class AdjustmentElements  implements java.io.Serializable {


	// Fields    

	/**
	 * 
	 */
	private static final long serialVersionUID = 7689842488744151748L;
	private Long id;
	private Adjustment adjustment;
	private User authorizedUser;
	private Date authorizationDate;
	private Warehouse warehouseSource;
	private Warehouse warehouseDestination;
	private Double initialQuantity;
	private AdjustmentElementsStatus adjustmentElementsStatus;
	private NotSerialized notSerialized;
	private Serialized serialized;


	// Constructors

	/** default constructor */
	public AdjustmentElements() {
	}


	/**
	 * 
	 * @param id
	 * @param adjustment
	 * @param element
	 * @param elementId
	 */
	public AdjustmentElements(Long id, Adjustment adjustment, Element element, Long elementId) {
		this.id = id;
		this.adjustment = adjustment;
	}


	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Adjustment getAdjustment() {
		return this.adjustment;
	}

	public void setAdjustment(Adjustment adjustment) {
		this.adjustment = adjustment;
	}


	public Warehouse getWarehouseSource() {
		return warehouseSource;
	}


	public void setWarehouseSource(Warehouse warehouseSource) {
		this.warehouseSource = warehouseSource;
	}


	public Warehouse getWarehouseDestination() {
		return warehouseDestination;
	}


	public void setWarehouseDestination(Warehouse warehouseDestination) {
		this.warehouseDestination = warehouseDestination;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getInitialQuantity() {
		return initialQuantity;
	}


	public void setInitialQuantity(Double initialQuantity) {
		this.initialQuantity = initialQuantity;
	}


	public AdjustmentElementsStatus getAdjustmentElementsStatus() {
		return adjustmentElementsStatus;
	}


	public void setAdjustmentElementsStatus(
			AdjustmentElementsStatus adjustmentElementsStatus) {
		this.adjustmentElementsStatus = adjustmentElementsStatus;
	}

	public NotSerialized getNotSerialized() {
		return notSerialized;
	}

	public void setNotSerialized(NotSerialized notSerialized) {
		this.notSerialized = notSerialized;
	}

	public Serialized getSerialized() {
		return serialized;
	}

	public void setSerialized(Serialized serialized) {
		this.serialized = serialized;
	}
		
	public User getAuthorizedUser() {
		return authorizedUser;
	}

	public void setAuthorizedUser(User authorizedUser) {
		this.authorizedUser = authorizedUser;
	}
	
	public Date getAuthorizationDate() {
		return this.authorizationDate;
	}

	public void setAuthorizationDate(Date authorizationDate) {
		this.authorizationDate = authorizationDate;
	}
	
}