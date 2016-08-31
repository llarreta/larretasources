package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;


/**
 * WorkorderReason entity. @author MyEclipse Persistence Tools
 */

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 26/11/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class WorkorderReason implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1923134790095228978L;

	private Long id;

    @BusinessRequired(isComplexType=true, fieldNameRequired="id")
    private ResponsibleArea responsibleArea;

    @BusinessRequired(isComplexType=true, fieldNameRequired="id")
    private WorkorderReasonCategory workorderReasonCategory;

    @BusinessRequired
    private String workorderReasonName;

    @BusinessRequired
    private String workorderReasonCode;

    @BusinessRequired
    private String isSolveByCi;
    
    private WorkorderStatus workorderStatus;

	// Constructors

	/** default constructor */
	public WorkorderReason() {
	}

	/** minimal constructor */
	public WorkorderReason(ResponsibleArea responsibleArea,
			WorkorderReasonCategory workorderReasonCategory,
			String workorderReasonName, String workorderReasonCode,
			String isSolveByCi) {
		this.responsibleArea = responsibleArea;
		this.workorderReasonCategory = workorderReasonCategory;
		this.workorderReasonName = workorderReasonName;
		this.workorderReasonCode = workorderReasonCode;
		this.isSolveByCi = isSolveByCi;
	}
	
	/** full constructor */
	public WorkorderReason(ResponsibleArea responsibleArea,
			WorkorderReasonCategory workorderReasonCategory,
			String workorderReasonName, String workorderReasonCode,
			String isSolveByCi,WorkorderStatus workorderStatus) {
		this.responsibleArea = responsibleArea;
		this.workorderReasonCategory = workorderReasonCategory;
		this.workorderReasonName = workorderReasonName;
		this.workorderReasonCode = workorderReasonCode;
		this.isSolveByCi = isSolveByCi;
		this.workorderStatus = workorderStatus;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ResponsibleArea getResponsibleArea() {
		return this.responsibleArea;
	}

	public void setResponsibleArea(ResponsibleArea responsibleArea) {
		this.responsibleArea = responsibleArea;
	}

	public WorkorderReasonCategory getWorkorderReasonCategory() {
		return this.workorderReasonCategory;
	}

	public void setWorkorderReasonCategory(
			WorkorderReasonCategory workorderReasonCategory) {
		this.workorderReasonCategory = workorderReasonCategory;
	}

	public String getWorkorderReasonName() {
		return this.workorderReasonName;
	}

	public void setWorkorderReasonName(String workorderReasonName) {
		this.workorderReasonName = workorderReasonName;
	}

	public String getWorkorderReasonCode() {
		return this.workorderReasonCode;
	}

	public void setWorkorderReasonCode(String workorderReasonCode) {
		this.workorderReasonCode = workorderReasonCode;
	}

	public String getIsSolveByCi() {
		return this.isSolveByCi;
	}

	public void setIsSolveByCi(String isSolveByCi) {
		this.isSolveByCi = isSolveByCi;
	}

	public WorkorderStatus getWorkorderStatus() {
		return workorderStatus;
	}

	public void setWorkorderStatus(WorkorderStatus workorderStatus) {
		this.workorderStatus = workorderStatus;
	}

	@Override
	public String toString() {
		return "WorkorderReason [id=" + id + ", workorderReasonCode="
				+ workorderReasonCode + "]";
	}
}