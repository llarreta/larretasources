package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;

/**
 * KpiGoals entity. @author MyEclipse Persistence Tools
 */

public class KpiGoals implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2423500663659659946L;
	private Long id;
	
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private Indicators indicators;
	
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private Country countries;
	
	@BusinessRequired
	private Double goalValue;
	
	@BusinessRequired
	private Date initDate;
	
	private Date endDate;
	
	private String goalDescription;

	// Constructors

	/** default constructor */
	public KpiGoals() {
	}

	/** minimal constructor */
	public KpiGoals(Indicators indicators, Country countries,
			Double goalValue, Date initDate) {
		this.indicators = indicators;
		this.countries = countries;
		this.goalValue = goalValue;
		this.initDate = initDate;
	}

	/** full constructor */
	public KpiGoals(Indicators indicators, Country countries,
			Double goalValue, Date initDate, Date endDate,
			String goalDescription) {
		this.indicators = indicators;
		this.countries = countries;
		this.goalValue = goalValue;
		this.initDate = initDate;
		this.endDate = endDate;
		this.goalDescription = goalDescription;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Indicators getIndicators() {
		return this.indicators;
	}

	public void setIndicators(Indicators indicators) {
		this.indicators = indicators;
	}

	public Country getCountries() {
		return this.countries;
	}

	public void setCountries(Country countries) {
		this.countries = countries;
	}

	public Double getGoalValue() {
		return this.goalValue;
	}

	public void setGoalValue(Double goalValue) {
		this.goalValue = goalValue;
	}

	public Date getInitDate() {
		return this.initDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getGoalDescription() {
		return this.goalDescription;
	}

	public void setGoalDescription(String goalDescription) {
		this.goalDescription = goalDescription;
	}

	@Override
	public String toString() {
		return "KpiGoals [id=" + id + "]";
	}

}