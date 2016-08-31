package co.com.directv.sdii.model.pojo;

import java.io.Serializable;

public class ScheduleReportStatus implements Serializable {
	
	private static final long serialVersionUID = -667337158788889409L;
	private Long id;
	private String code;
	private String description;
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
	public ScheduleReportStatus(Long id, String code, String description) {
		super();
		this.id = id;
		this.code = code;
		this.description = description;
	}
	public ScheduleReportStatus() {
		super();
	}

}
