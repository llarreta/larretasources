package co.com.directv.sdii.model.pojo;

public class ScheduleReportParameter implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7049552672601939433L;
	
	private Long id;
	
	private String code;
	
	private	String value;

	private ScheduleReport scheduleReport;
	
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ScheduleReport getScheduleReport() {
		return scheduleReport;
	}

	public void setScheduleReport(ScheduleReport scheduleReport) {
		this.scheduleReport = scheduleReport;
	}

}
