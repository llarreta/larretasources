package co.com.directv.sdii.model.pojo;

import java.util.Date;

// default package


/**
 * ReportedElements entity. @author MyEclipse Persistence Tools
 */

public class ReportedElement implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Long id;
	private RefInconsistency refInconsistency;
	private ReferenceElementItem referenceElementItem;
	private User user;
	private ElementType elementType;
	private Double qty;
	private String serialCode;
	private Date reporteDate;

	// Constructors

	/** default constructor */
	public ReportedElement() {
	}

	/** minimal constructor */
	public ReportedElement(Long id,
			RefInconsistency refInconsistency, User user,
			Date reporteDate) {
		this.id = id;
		this.refInconsistency = refInconsistency;
		this.user = user;
		this.reporteDate = reporteDate;
	}

	/** full constructor */
	public ReportedElement(Long id,
			RefInconsistency refInconsistency,
			ReferenceElementItem referenceElementItem, User user,
			ElementType elementType, Double qty, String serialCode,
			Date reporteDate) {
		this.id = id;
		this.refInconsistency = refInconsistency;
		this.referenceElementItem = referenceElementItem;
		this.user = user;
		this.elementType = elementType;
		this.qty = qty;
		this.serialCode = serialCode;
		this.reporteDate = reporteDate;
	}

	// Property accessors

	


	

	public Double getQty() {
		return this.qty;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RefInconsistency getRefInconsistency() {
		return refInconsistency;
	}

	public void setRefInconsistency(RefInconsistency refInconsistency) {
		this.refInconsistency = refInconsistency;
	}

	public ReferenceElementItem getReferenceElementItem() {
		return referenceElementItem;
	}

	public void setReferenceElementItem(ReferenceElementItem referenceElementItem) {
		this.referenceElementItem = referenceElementItem;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ElementType getElementType() {
		return elementType;
	}

	public void setElementType(ElementType elementType) {
		this.elementType = elementType;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public String getSerialCode() {
		return this.serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	public Date getReporteDate() {
		return this.reporteDate;
	}

	public void setReporteDate(Date reporteDate) {
		this.reporteDate = reporteDate;
	}

}