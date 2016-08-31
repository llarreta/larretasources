package co.com.directv.sdii.model.pojo;

import java.io.Serializable;
import java.util.Date;

public class ScheduleReportLog implements Serializable {
	
	private Long id;
	private ScheduleReport scheduleReport;
	private Date dateLog;
	private ScheduleReportStatus scheduleReportStatus;
	private String log;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ScheduleReport getScheduleReport() {
		return scheduleReport;
	}
	public void setScheduleReport(ScheduleReport scheduleReport) {
		this.scheduleReport = scheduleReport;
	}
	public Date getDateLog() {
		return dateLog;
	}
	public void setDateLog(Date dateLog) {
		this.dateLog = dateLog;
	}
	public ScheduleReportStatus getScheduleReportStatus() {
		return scheduleReportStatus;
	}
	public void setScheduleReportStatus(ScheduleReportStatus scheduleReportStatus) {
		this.scheduleReportStatus = scheduleReportStatus;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public ScheduleReportLog(Long id, ScheduleReport scheduleReport,
			Date dateLog, ScheduleReportStatus scheduleReportStatus, String log) {
		super();
		this.id = id;
		this.scheduleReport = scheduleReport;
		this.dateLog = dateLog;
		this.scheduleReportStatus = scheduleReportStatus;
		this.log = log;
	}
	public ScheduleReportLog() {
		super();
	}

	
	
}
