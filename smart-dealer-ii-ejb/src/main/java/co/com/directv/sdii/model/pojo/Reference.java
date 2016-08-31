package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;


/**
 * Reference entity. @author MyEclipse Persistence Tools
 */

public class Reference implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5031559358947124775L;
	private Long id;
	private Warehouse warehouseBySourceWh;
	private Warehouse warehouseByTargetWh;
	private TransportCompany transportCompany;
	private ReferenceStatus referenceStatus;
	private Warehouse targetTransitWh;
	private Warehouse sourceTransitWh;
	private Date creationReferenceDate;
	private User createUserId;
	private Date shippingDate;
	private String transportGuide;
	private String driverName;
	private String vehiclePlate;
	private Double sendUnits;
	private Double volume;
	private Date deliveryDate;
	private User shippingUserId;
	private Country countryCodeId;
	private String isPrepaidRef;
	private String isPreloadRef;
	private String rnNumber;
	private String isQuantityControl;
	
	/** al cerrar una inconsistencia "menos elementos físicos", se genera una remisión asociada, y la relación se mantiene a través de este campo */
	private Long parentReferenceId;

	// Constructors

	/** default constructor */
	public Reference() {
	}

	public Reference(Long id) {
		this.id = id;
	}
	
	/** minimal constructor */
	public Reference(Warehouse warehouseBySourceWh,
			Warehouse warehouseByTargetWh, ReferenceStatus referenceStatus,
			Warehouse warehouseByTransitWh, Date creationReferenceDate,
			User createUserId, User shippingUserId,String isPrepaidRef) {
		this.warehouseBySourceWh = warehouseBySourceWh;
		this.warehouseByTargetWh = warehouseByTargetWh;
		this.referenceStatus = referenceStatus;
		this.targetTransitWh = warehouseByTransitWh;
		this.creationReferenceDate = creationReferenceDate;
		this.createUserId = createUserId;
		this.shippingUserId = shippingUserId;
		this.isPrepaidRef = isPrepaidRef;
	}

	/** full constructor */
	public Reference(Warehouse warehouseBySourceWh,
			Warehouse warehouseByTargetWh, TransportCompany transportCompany,
			ReferenceStatus referenceStatus, Warehouse warehouseByTransitWh,
			Date creationReferenceDate, User createUserId,
			Date shippingDate, String transportGuide, String driverName,
			String vehiclePlate, Double sendUnits, Double volume,
			Date deliveryDate, User shippingUserId,String isPrepaidRef) {
		this.warehouseBySourceWh = warehouseBySourceWh;
		this.warehouseByTargetWh = warehouseByTargetWh;
		this.transportCompany = transportCompany;
		this.referenceStatus = referenceStatus;
		this.targetTransitWh = warehouseByTransitWh;
		this.creationReferenceDate = creationReferenceDate;
		this.createUserId = createUserId;
		this.shippingDate = shippingDate;
		this.transportGuide = transportGuide;
		this.driverName = driverName;
		this.vehiclePlate = vehiclePlate;
		this.sendUnits = sendUnits;
		this.volume = volume;
		this.deliveryDate = deliveryDate;
		this.shippingUserId = shippingUserId;
		this.isPrepaidRef = isPrepaidRef;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Warehouse getWarehouseBySourceWh() {
		return this.warehouseBySourceWh;
	}

	public void setWarehouseBySourceWh(Warehouse warehouseBySourceWh) {
		this.warehouseBySourceWh = warehouseBySourceWh;
	}

	public Warehouse getWarehouseByTargetWh() {
		return this.warehouseByTargetWh;
	}

	public void setWarehouseByTargetWh(Warehouse warehouseByTargetWh) {
		this.warehouseByTargetWh = warehouseByTargetWh;
	}

	public TransportCompany getTransportCompany() {
		return this.transportCompany;
	}

	public void setTransportCompany(TransportCompany transportCompany) {
		this.transportCompany = transportCompany;
	}

	public ReferenceStatus getReferenceStatus() {
		return this.referenceStatus;
	}

	public void setReferenceStatus(ReferenceStatus referenceStatus) {
		this.referenceStatus = referenceStatus;
	}

	public Warehouse getTargetTransitWh() {
		return this.targetTransitWh;
	}

	public void setTargetTransitWh(Warehouse warehouseByTransitWh) {
		this.targetTransitWh = warehouseByTransitWh;
	}

	public Warehouse getSourceTransitWh() {
		return sourceTransitWh;
	}

	public void setSourceTransitWh(Warehouse sourceTransitWh) {
		this.sourceTransitWh = sourceTransitWh;
	}

	public Date getCreationReferenceDate() {
		return this.creationReferenceDate;
	}

	public void setCreationReferenceDate(Date creationReferenceDate) {
		this.creationReferenceDate = creationReferenceDate;
	}

	public User getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(User createUserId) {
		this.createUserId = createUserId;
	}

	public Date getShippingDate() {
		return this.shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getTransportGuide() {
		return this.transportGuide;
	}

	public void setTransportGuide(String transportGuide) {
		this.transportGuide = transportGuide;
	}

	public String getDriverName() {
		return this.driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getVehiclePlate() {
		return this.vehiclePlate;
	}

	public void setVehiclePlate(String vehiclePlate) {
		this.vehiclePlate = vehiclePlate;
	}

	public Double getSendUnits() {
		return this.sendUnits;
	}

	public void setSendUnits(Double sendUnits) {
		this.sendUnits = sendUnits;
	}

	public Double getVolume() {
		return this.volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Date getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public User getShippingUserId() {
		return this.shippingUserId;
	}

	public void setShippingUserId(User shippingUserId) {
		this.shippingUserId = shippingUserId;
	}

	public Country getCountryCodeId() {
		return countryCodeId;
	}

	public void setCountryCodeId(Country countryCodeId) {
		this.countryCodeId = countryCodeId;
	}
	

	public String getIsPrepaidRef() {
		return isPrepaidRef;
	}

	public void setIsPrepaidRef(String isPrepaidRef) {
		this.isPrepaidRef = isPrepaidRef;
	}
	
	

	public String getIsPreloadRef() {
		return isPreloadRef;
	}

	public void setIsPreloadRef(String isPreloadRef) {
		this.isPreloadRef = isPreloadRef;
	}

	@Override
	public String toString() {
		return "Reference [id=" + id + "]";
	}

	public void setRnNumber(String rnNumber) {
		this.rnNumber = rnNumber;
	}

	public String getRnNumber() {
		return rnNumber;
	}

	public Long getParentReferenceId() {
		return parentReferenceId;
	}

	public void setParentReferenceId(Long parentReferenceId) {
		this.parentReferenceId = parentReferenceId;
	}

	public String getIsQuantityControl() {
		return isQuantityControl;
	}

	public void setIsQuantityControl(String isQuantityControl) {
		this.isQuantityControl = isQuantityControl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}