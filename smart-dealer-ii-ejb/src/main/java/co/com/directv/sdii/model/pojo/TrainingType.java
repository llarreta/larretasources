package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * TrainingType entity. @author MyEclipse Persistence Tools
 */

public class TrainingType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5154768987633573651L;
	private Long id;
	private String trainingTypeName;
	private String trainingTypeCode;
	private Country country;

	// Constructors

	/** default constructor */
	public TrainingType() {
	}

	/** minimal constructor */
	public TrainingType(String trainingTypeName, String trainingTypeCode) {
		this.trainingTypeName = trainingTypeName;
		this.trainingTypeCode = trainingTypeCode;
	}
	
	/** full constructor */
	public TrainingType(String trainingTypeName, String trainingTypeCode,Country country) {
		this.trainingTypeName = trainingTypeName;
		this.trainingTypeCode = trainingTypeCode;
		this.country = country;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTrainingTypeName() {
		return this.trainingTypeName;
	}

	public void setTrainingTypeName(String trainingTypeName) {
		this.trainingTypeName = trainingTypeName;
	}

	public String getTrainingTypeCode() {
		return this.trainingTypeCode;
	}

	public void setTrainingTypeCode(String trainingTypeCode) {
		this.trainingTypeCode = trainingTypeCode;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "TrainingType [country=" + country + ", id=" + id
				+ ", trainingTypeCode=" + trainingTypeCode + "]";
	}

}