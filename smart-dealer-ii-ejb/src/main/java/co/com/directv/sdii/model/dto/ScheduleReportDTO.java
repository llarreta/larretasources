package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * //CC053 - HSP Reportes - CRUD Programacion.
 * @author martlago
 *
 */
public class ScheduleReportDTO implements Serializable {

	private Long id;
	private String reportTypeName;
	private String statusName;
	private Date beginDate;
	private Date endDate;
	private String login;
	private String log;
	
	public ScheduleReportDTO(){
	}
	
	public ScheduleReportDTO(Long id, String reportTypeName, String statusName,
			Date beginDate, Date endDate, String login, String log) {
		super();
		this.id = id;
		this.reportTypeName = reportTypeName;
		this.statusName = statusName;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.login = login;
		this.log = log;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReportTypeName() {
		return reportTypeName;
	}
	public void setReportTypeName(String reportTypeName) {
		this.reportTypeName = reportTypeName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
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
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	
	
	
	
}
