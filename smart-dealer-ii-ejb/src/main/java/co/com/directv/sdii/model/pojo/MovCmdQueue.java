package co.com.directv.sdii.model.pojo;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

/**
 * MovCmdQueue entity. @author MyEclipse Persistence Tools
 */

public class MovCmdQueue implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8957210258389793572L;
	private Long id;
	private MovCmdConfig movCmdConfig;
	private Serialized serialized;
	private MovCmdStatus movCmdStatus;
	private Date creationDate;
	private Date executeDate;
	private Warehouse sourceWarehouse;
	private Warehouse targetWarehouse;
	private String typeComunication;
	private Serialized serializedLinked;
	private DocumentClass documentClass;
	private Adjustment adjustment;
	private Reference reference;
	private ImportLog importLog;
	private WorkOrder workOrder;
	private String isManagment;
	private User managmentUserId;
	private String commentManagment;
	private Customer customer;
	private Country country;
	private String typeWorkOrderForMovInv;
	private Long historyEventId;
	// Constructors

	/** default constructor */
	public MovCmdQueue() {
	}

	/** minimal constructor */
	public MovCmdQueue(Date creationDate,
			Date executeDate) {
		this.creationDate = creationDate;
		this.executeDate = executeDate;
	}

	/** full constructor */
	public MovCmdQueue(MovCmdConfig movCmdConfig, 
			Serialized serialized, MovCmdStatus movCmdStatus,
			Date creationDate, Date executeDate) {
		this.movCmdConfig = movCmdConfig;
		this.serialized = serialized;
		this.movCmdStatus = movCmdStatus;
		this.creationDate = creationDate;
		this.executeDate = executeDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MovCmdConfig getMovCmdConfig() {
		return this.movCmdConfig;
	}

	public void setMovCmdConfig(MovCmdConfig movCmdConfig) {
		this.movCmdConfig = movCmdConfig;
	}

	
	public Serialized getSerialized() {
		return this.serialized;
	}

	public void setSerialized(Serialized serialized) {
		this.serialized = serialized;
	}

	public MovCmdStatus getMovCmdStatus() {
		return this.movCmdStatus;
	}

	public void setMovCmdStatus(MovCmdStatus movCmdStatus) {
		this.movCmdStatus = movCmdStatus;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getCreationDate() {
		return this.creationDate;
	}
	
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getExecuteDate() {
		return this.executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public Warehouse getSourceWarehouse() {
		return sourceWarehouse;
	}

	public void setSourceWarehouse(Warehouse sourceWarehouse) {
		this.sourceWarehouse = sourceWarehouse;
	}

	public Warehouse getTargetWarehouse() {
		return targetWarehouse;
	}

	public void setTargetWarehouse(Warehouse targetWarehouse) {
		this.targetWarehouse = targetWarehouse;
	}

	public String getTypeComunication() {
		return typeComunication;
	}

	public void setTypeComunication(String typeComunication) {
		this.typeComunication = typeComunication;
	}

	public Serialized getSerializedLinked() {
		return serializedLinked;
	}

	public void setSerializedLinked(Serialized serializedLinked) {
		this.serializedLinked = serializedLinked;
	}

	public DocumentClass getDocumentClass() {
		return documentClass;
	}

	public void setDocumentClass(DocumentClass documentClass) {
		this.documentClass = documentClass;
	}


	public Adjustment getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(Adjustment adjustment) {
		this.adjustment = adjustment;
	}

	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public ImportLog getImportLog() {
		return importLog;
	}

	public void setImportLog(ImportLog importLog) {
		this.importLog = importLog;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public String getIsManagment() {
		return isManagment;
	}

	public void setIsManagment(String isManagment) {
		this.isManagment = isManagment;
	}

	public User getManagmentUserId() {
		return managmentUserId;
	}

	public void setManagmentUserId(User managmentUserId) {
		this.managmentUserId = managmentUserId;
	}

	public String getCommentManagment() {
		return commentManagment;
	}

	public void setCommentManagment(String commentManagment) {
		this.commentManagment = commentManagment;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getTypeWorkOrderForMovInv() {
		return typeWorkOrderForMovInv;
	}

	public void setTypeWorkOrderForMovInv(String typeWorkOrderForMovInv) {
		this.typeWorkOrderForMovInv = typeWorkOrderForMovInv;
	}

	public Long getHistoryEventId() {
		return historyEventId;
	}

	public void setHistoryEventId(Long historyEventId) {
		this.historyEventId = historyEventId;
	}
	
	
}