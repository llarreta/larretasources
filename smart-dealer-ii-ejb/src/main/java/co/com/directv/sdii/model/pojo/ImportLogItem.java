package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;




/**
 * ImportLogItem entity. @author MyEclipse Persistence Tools
 */

public class ImportLogItem implements java.io.Serializable,Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6055481336736549544L;
	private Long id;
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private ItemStatus itemStatus;
	private ItemStatus oldItemStatus;
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private ImportLog importLog;
	private MeasureUnit measureUnit;
	private Element element;
	@BusinessRequired
	private Double amountExpected;

	// Constructors

	/** default constructor */
	public ImportLogItem() {
	}

	/** minimal constructor */
	public ImportLogItem(ItemStatus itemStatus, ImportLog importLog,
			Element element, Double amountExpected) {
		this.itemStatus = itemStatus;
		this.importLog = importLog;
		this.element = element;
		this.amountExpected = amountExpected;
		this.oldItemStatus = null;
	}	
		
	/** full constructor */
	public ImportLogItem(ItemStatus itemStatus, ImportLog importLog,
			MeasureUnit measureUnit, Element element,
			double amountExpected) {
		this.itemStatus = itemStatus;
		this.importLog = importLog;
		this.measureUnit = measureUnit;
		this.element = element;
		this.amountExpected = amountExpected;
		this.oldItemStatus = null;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ItemStatus getItemStatus() {
		return this.itemStatus;
	}

	public void setItemStatus(ItemStatus itemStatus) {
		this.itemStatus = itemStatus;
	}

	public ImportLog getImportLog() {
		return this.importLog;
	}

	public void setImportLog(ImportLog importLog) {
		this.importLog = importLog;
	}

	public MeasureUnit getMeasureUnit() {
		return this.measureUnit;
	}

	public void setMeasureUnit(MeasureUnit measureUnit) {
		this.measureUnit = measureUnit;
	}

	public Element getElement() {
		return this.element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public Double getAmountExpected() {
		return this.amountExpected;
	}

	public void setAmountExpected(Double amountExpected) {
		this.amountExpected = amountExpected;
	}
	
	public ItemStatus getOldItemStatus() {
		return oldItemStatus;
	}
	
	public void setOldItemStatus(ItemStatus oldItemStatus) {
		this.oldItemStatus = oldItemStatus;
	}

	@Override
	public String toString() {
		return "ImportLogItem [id=" + id + "]";
	}
	
}