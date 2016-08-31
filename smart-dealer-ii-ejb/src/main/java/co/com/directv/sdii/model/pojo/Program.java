package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;

/**
 * Program entity. @author MyEclipse Persistence Tools
 */

public class Program implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5809386471384437939L;
	private Long id;
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private ProgramStatus programStatus;
	@BusinessRequired
	private String programName;
	private String programCode;
	@BusinessRequired
	private Long dealerId;
	@BusinessRequired
	private String programDescription;

	// Constructors

	/** default constructor */
	public Program() {
	}

	/** minimal constructor */
	public Program(ProgramStatus programStatus, String programName,
			String programCode, Long dealerId) {
		this.programStatus = programStatus;
		this.programName = programName;
		this.programCode = programCode;
		this.dealerId = dealerId;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProgramStatus getProgramStatus() {
		return this.programStatus;
	}

	public void setProgramStatus(ProgramStatus programStatus) {
		this.programStatus = programStatus;
	}

	public String getProgramName() {
		return this.programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getProgramCode() {
		return this.programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public Long getDealerId() {
		return this.dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public String getProgramDescription() {
		return this.programDescription;
	}

	public void setProgramDescription(String programDescription) {
		this.programDescription = programDescription;
	}

	@Override
	public String toString() {
		return "Program [id=" + id + ", programCode=" + programCode + "]";
	}
}