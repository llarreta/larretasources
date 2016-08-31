package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * Indicators entity. @author MyEclipse Persistence Tools
 */

public class Indicators implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3295368339524719232L;
	private Long id;
	private String kpiCode;
	private String kpiName;
	private String kpiDescription;

	// Constructors

	/** default constructor */
	public Indicators() {
	}

	/** minimal constructor */
	public Indicators(String kpiCode, String kpiName) {
		this.kpiCode = kpiCode;
		this.kpiName = kpiName;
	}

	/** full constructor */
	public Indicators(String kpiCode, String kpiName, String kpiDescription) {
		this.kpiCode = kpiCode;
		this.kpiName = kpiName;
		this.kpiDescription = kpiDescription;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKpiCode() {
		return this.kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public String getKpiName() {
		return this.kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public String getKpiDescription() {
		return this.kpiDescription;
	}

	public void setKpiDescription(String kpiDescription) {
		this.kpiDescription = kpiDescription;
	}

	@Override
	public String toString() {
		return "Indicators [id=" + id + ", kpiCode=" + kpiCode + "]";
	}
	
	
}