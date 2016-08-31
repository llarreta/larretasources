package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.rules.BusinessRequired;

/**
 * Entidad que modela un elemento en una bodega
 * @author MyEclipse Persistence Tools
 */

public class WarehouseElement implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 9064816335234727639L;
	@BusinessRequired private Long id;
	@BusinessRequired(isComplexType=true, fieldNameRequired="warehouseId")
	private Warehouse warehouseId;
	private NotSerialized notSerialized;
	private Serialized serialized;
	@BusinessRequired(isComplexType=true, fieldNameRequired="movementType")
	private MovementType movementType;
	@BusinessRequired(isComplexType=true, fieldNameRequired="recordStatus")
	private RecordStatus recordStatus;
	private ElementType elementType;
	@BusinessRequired private Double actualQuantity;
	@BusinessRequired private Double initialQuantity;
	@BusinessRequired private Double movedQuantity;
	@BusinessRequired private Date movementDate;
	private WarehouseElement sourceRecord;
	private Long documentId;
	private DocumentClass documentClass;  
	private Serialized linkedSerId;
	
	// Constructors


	/** default constructor */
	public WarehouseElement() {
	}

	/** minimal constructor */
	public WarehouseElement(Warehouse warehouseId,
			MovementType movementType, RecordStatus recordStatus,
			Double movedQuantity, Double actualQuantity,
			Double initialQuantity, Date movementDate) {
		this.warehouseId = warehouseId;
		this.movementType = movementType;
		this.recordStatus = recordStatus;
		this.movedQuantity = movedQuantity;
		this.actualQuantity = actualQuantity;
		this.initialQuantity = initialQuantity;
		this.movementDate = movementDate;
	}

	/** full constructor */
	public WarehouseElement(Warehouse warehouseId, NotSerialized notSerialized,
			Serialized serialized, MovementType movementType,
			RecordStatus recordStatus, ElementType elementType,
			Double actualQuantity, Double initialQuantity,
			Double movedQuantity, Date movementDate, WarehouseElement sourceRecord,
			Serialized linkedSerId) {
		super();
		this.warehouseId = warehouseId;
		this.notSerialized = notSerialized;
		this.serialized = serialized;
		this.movementType = movementType;
		this.recordStatus = recordStatus;
		this.elementType = elementType;
		this.actualQuantity = actualQuantity;
		this.initialQuantity = initialQuantity;
		this.movedQuantity = movedQuantity;
		this.movementDate = movementDate;
		this.sourceRecord = sourceRecord;
		this.setLinkedSerId(linkedSerId);
	}

	// Property accessors

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Warehouse getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Warehouse warehouseId) {
		this.warehouseId = warehouseId;
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

	public MovementType getMovementType() {
		return movementType;
	}

	public void setMovementType(MovementType movementType) {
		this.movementType = movementType;
	}

	public RecordStatus getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(RecordStatus recordStatus) {
		this.recordStatus = recordStatus;
	}

	public ElementType getElementType() {
		return elementType;
	}

	public void setElementType(ElementType elementType) {
		this.elementType = elementType;
	}

	public Double getActualQuantity() {
		return actualQuantity;
	}

	public void setActualQuantity(Double actualQuantity) {
		this.actualQuantity = actualQuantity;
	}

	public Double getInitialQuantity() {
		return initialQuantity;
	}

	public void setInitialQuantity(Double initialQuantity) {
		this.initialQuantity = initialQuantity;
	}

	public Double getMovedQuantity() {
		return movedQuantity;
	}

	public void setMovedQuantity(Double movedQuantity) {
		this.movedQuantity = movedQuantity;
	}

	public Date getMovementDate() {
		return movementDate;
	}

	public void setMovementDate(Date movementDate) {
		this.movementDate = movementDate;
	}

	public WarehouseElement getSourceRecord() {
		return sourceRecord;
	}

	public void setSourceRecord(WarehouseElement sourceRecord) {
		this.sourceRecord = sourceRecord;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WarehouseElement [id=");
		builder.append(id);
		builder.append(", causeAdjustment=");
		builder.append(", elementType=");
		builder.append(elementType);
		builder.append(", initialQuantity=");
		builder.append(initialQuantity);
		builder.append(", actualQuantity=");
		builder.append(actualQuantity);
		builder.append(", movedQuantity=");
		builder.append(movedQuantity);
		builder.append(", movementDate=");
		builder.append(movementDate);
		builder.append(", movementType=");
		builder.append(movementType);
		builder.append(", notSerialized=");
		builder.append(notSerialized);
		builder.append(", recordStatus=");
		builder.append(recordStatus);
		builder.append(", warehouseId=");
		builder.append(warehouseId);
		builder.append(", sourceRecord=");
		builder.append(sourceRecord);		
		builder.append("]");
		return builder.toString();
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public DocumentClass getDocumentClass() {
		return documentClass;
	}

	public void setDocumentClass(DocumentClass documentClass) {
		this.documentClass = documentClass;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setLinkedSerId(Serialized linkedSerId) {
		this.linkedSerId = linkedSerId;
	}

	public Serialized getLinkedSerId() {
		return linkedSerId;
	}
	
	
}