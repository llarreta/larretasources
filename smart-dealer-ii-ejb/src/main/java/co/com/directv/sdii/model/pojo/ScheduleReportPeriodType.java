package co.com.directv.sdii.model.pojo;

import java.io.Serializable;

public class ScheduleReportPeriodType implements Serializable {

	private Long id;
	private String code;
	private String description;
	
	public ScheduleReportPeriodType(ScheduleReportPeriodType srpt) {
		super();
		this.id = srpt.id;
		this.code = srpt.code;
		this.description = srpt.description;
	}
	
	public ScheduleReportPeriodType(Long id, String code, String description,
			String includeLastTime) {
		super();
		this.id = id;
		this.code = code;
		this.description = description;
	}
    public ScheduleReportPeriodType(Long id, String code, String description) {
		super();
		this.id = id;
		this.code = code;
		this.description = description;
	}
	public ScheduleReportPeriodType() {
		super();
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	
}
