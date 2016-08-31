package co.com.directv.sdii.model.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * ScheduleReport entity. @author MyEclipse Persistence Tools
 */
public class ScheduleReport implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7548562515140758822L;
	private Long id;
	private String code;
	private Date beginDate;
	private Date endDate;
	private User user;
	private Long period;
	private ScheduleReportType reportType;
	
	private ScheduleReportPeriodType periodType;
	private Date creationDate;
	private Date executionDate;
	private Country country;
	private ScheduleReportStatus status;
	private String includeLastTime;
	private Set<ScheduleReportParameter> scheduleReportParamSet;

	public ScheduleReport(ScheduleReport sr){
		id=sr.id;
		code=sr.code;
		beginDate=sr.beginDate;
		endDate=sr.endDate;
		user=sr.user;
		period=sr.period;
		reportType=sr.reportType;
		periodType=sr.periodType;
		creationDate=sr.creationDate;
		executionDate=sr.executionDate;
		country=sr.country;
		status=sr.status;
		includeLastTime = sr.includeLastTime;
	}
	
	public ScheduleReportStatus getStatus() {
		return status;
	}
	public void setStatus(ScheduleReportStatus status) {
		this.status = status;
	}
	public ScheduleReport(Long id, String code, Date beginDate, Date endDate,
						  User user, Long period,
						  ScheduleReportType reportType,
						  ScheduleReportPeriodType periodType, Date creationDate,
						  Date executionDate, Country country, ScheduleReportStatus status,String includeLastTime) {
		super();
		this.id = id;
		this.code = code;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.user = user;
		this.period = period;
		this.reportType = reportType;
		this.periodType = periodType;
		this.creationDate = creationDate;
		this.executionDate = executionDate;
		this.country = country;
		this.status = status;
		this.includeLastTime = includeLastTime;
	}
	public ScheduleReport(Long id, String code, Date beginDate, Date endDate,
			User user, Long period,
			ScheduleReportType reportType,
			ScheduleReportPeriodType periodType, Date creationDate,
			Date executionDate, Country country,String includeLastTime) {
		super();
		this.id = id;
		this.code = code;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.user = user;
		this.period = period;
		this.reportType = reportType;
		this.periodType = periodType;
		this.creationDate = creationDate;
		this.executionDate = executionDate;
		this.country = country;
		this.includeLastTime = includeLastTime;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Long getPeriod() {
		return period;
	}
	public void setPeriod(Long period) {
		this.period = period;
	}
	public ScheduleReportType getReportType() {
		return reportType;
	}
	public void setReportType(ScheduleReportType reportType) {
		this.reportType = reportType;
	}
	public ScheduleReportPeriodType getPeriodType() {
		return periodType;
	}
	public void setPeriodType(ScheduleReportPeriodType periodType) {
		this.periodType = periodType;
	}
	public ScheduleReport(Long id, String code, Date beginDate, Date endDate,
			User user, Long period, ScheduleReportType reportType
			, ScheduleReportPeriodType periodType,String includeLastTime) {
		super();
		this.id = id;
		this.code = code;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.user = user;
		this.period = period;
		this.reportType = reportType;
		this.periodType = periodType;
		this.includeLastTime = includeLastTime;
	}
	public ScheduleReport() {
		super();
	}

	public String getIncludeLastTime() {
		return includeLastTime;
	}

	public void setIncludeLastTime(String includeLastTime) {
		this.includeLastTime = includeLastTime;
	}

	public Set<ScheduleReportParameter> getScheduleReportParamSet() {
		return scheduleReportParamSet;
	}

	public void setScheduleReportParamSet(
			Set<ScheduleReportParameter> scheduleReportParamSet) {
		this.scheduleReportParamSet = scheduleReportParamSet;
	}
	
}
