package co.com.directv.sdii.model.pojo;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

/**
 * KpiConfiguration entity. @author MyEclipse Persistence Tools
 */

public class KpiConfiguration implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2328511390444263437L;
	private Long id;
	private Kpi kpi;
	private ServiceSuperCategory serviceSuperCategory;
	private User user;
	private ZoneType zoneTypes;
	private Country country;
	private KpiCalculationType kpiCalculationType;
	private String status;
	private Double weighting;
	private String frecuencyExpression;
	private Long dayCount;
	private Double goal;
	private Date dateLastChange;
	/**
	 * Proxima fecha de ejecucion del programador de tareas
	 */
	private java.util.Date nextExecutionDate;

	// Constructors

	/** default constructor */
	public KpiConfiguration() {
	}

	public KpiConfiguration(Long id) {
		this.id = id;
	}

	public KpiConfiguration(Kpi kpi, ZoneType zoneTypes, String status,
			Country country, ServiceSuperCategory serviceSuperCategory, KpiCalculationType calculationType) {
		this.kpi = kpi;
		this.zoneTypes = zoneTypes;
		this.status = status; 
		this.country = country;
		this.serviceSuperCategory = serviceSuperCategory;
		this.kpiCalculationType = calculationType;
	}
	
	/** minimal constructor */
	public KpiConfiguration(Kpi kpi, ServiceSuperCategory serviceSuperCategory,
			User user, ZoneType zoneTypes, Country country,
			KpiCalculationType kpiCalculationType, String status,
			Long dayCount, Date dateLastChange) {
		this.kpi = kpi;
		this.serviceSuperCategory = serviceSuperCategory;
		this.user = user;
		this.zoneTypes = zoneTypes;
		this.country = country;
		this.kpiCalculationType = kpiCalculationType;
		this.status = status;
		this.dayCount = dayCount;
		this.dateLastChange = dateLastChange;
	}

	/** full constructor */
	public KpiConfiguration(Kpi kpi, ServiceSuperCategory serviceSuperCategory,
			User user, ZoneType zoneTypes, Country country,
			KpiCalculationType kpiCalculationType, String status,
			Double weighting, String frecuencyExpression, Long dayCount,
			Double goal, Date dateLastChange) {
		this.kpi = kpi;
		this.serviceSuperCategory = serviceSuperCategory;
		this.user = user;
		this.zoneTypes = zoneTypes;
		this.country = country;
		this.kpiCalculationType = kpiCalculationType;
		this.status = status;
		this.weighting = weighting;
		this.frecuencyExpression = frecuencyExpression;
		this.dayCount = dayCount;
		this.goal = goal;
		this.dateLastChange = dateLastChange;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Kpi getKpi() {
		return this.kpi;
	}

	public void setKpi(Kpi kpi) {
		this.kpi = kpi;
	}

	public ServiceSuperCategory getServiceSuperCategory() {
		return this.serviceSuperCategory;
	}

	public void setServiceSuperCategory(
			ServiceSuperCategory serviceSuperCategory) {
		this.serviceSuperCategory = serviceSuperCategory;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ZoneType getZoneTypes() {
		return this.zoneTypes;
	}

	public void setZoneTypes(ZoneType zoneTypes) {
		this.zoneTypes = zoneTypes;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public KpiCalculationType getKpiCalculationType() {
		return this.kpiCalculationType;
	}

	public void setKpiCalculationType(KpiCalculationType kpiCalculationType) {
		this.kpiCalculationType = kpiCalculationType;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getWeighting() {
		return this.weighting;
	}

	public void setWeighting(Double weighting) {
		this.weighting = weighting;
	}

	public String getFrecuencyExpression() {
		return this.frecuencyExpression;
	}

	public void setFrecuencyExpression(String frecuencyExpression) {
		this.frecuencyExpression = frecuencyExpression;
	}

	public Long getDayCount() {
		return this.dayCount;
	}

	public void setDayCount(Long dayCount) {
		this.dayCount = dayCount;
	}

	public Double getGoal() {
		return this.goal;
	}

	public void setGoal(Double goal) {
		this.goal = goal;
	}

	public Date getDateLastChange() {
		return this.dateLastChange;
	}

	public void setDateLastChange(Date dateLastChange) {
		this.dateLastChange = dateLastChange;
	}

	@Override
	public String toString() {
		return new StringBuffer("id=").append(id!=null?id:"null")
		.append("kpiId=").append(kpi!=null?kpi.getId():"null").toString();
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public java.util.Date getNextExecutionDate() {
		return nextExecutionDate;
	}

	public void setNextExecutionDate(java.util.Date nextExecutionDate) {
		this.nextExecutionDate = nextExecutionDate;
	}
	
}