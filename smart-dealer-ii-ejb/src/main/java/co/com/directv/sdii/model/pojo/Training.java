package co.com.directv.sdii.model.pojo;

// default package

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;

/**
 * Training entity. @author MyEclipse Persistence Tools
 */

public class Training implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7933820430607202225L;
	
	@BusinessRequired
	private Long id;
	
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private Employee employee;
	
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private TrainingType trainingType;
	
	@BusinessRequired
	private String description;
	
	@BusinessRequired
	private Date dateTraining;

	// Constructors

	/** default constructor */
	public Training() {
	}

	/** minimal constructor */
	public Training(Employee employee, TrainingType trainingType,
			Date dateTraining) {
		this.employee = employee;
		this.trainingType = trainingType;
		this.dateTraining = dateTraining;
	}

	/** full constructor */
	public Training(Employee employee, TrainingType trainingType,
			String description, Date dateTraining) {
		this.employee = employee;
		this.trainingType = trainingType;
		this.description = description;
		this.dateTraining = dateTraining;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public TrainingType getTrainingType() {
		return this.trainingType;
	}

	public void setTrainingType(TrainingType trainingType) {
		this.trainingType = trainingType;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateTraining() {
		return this.dateTraining;
	}

	public void setDateTraining(Date dateTraining) {
		this.dateTraining = dateTraining;
	}

	@Override
	public String toString() {
		return "Training [employee=" + employee + ", id=" + id
				+ ", trainingType=" + trainingType + "]";
	}
	
}