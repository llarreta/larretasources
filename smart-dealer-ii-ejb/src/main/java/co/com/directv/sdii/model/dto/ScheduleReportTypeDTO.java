package co.com.directv.sdii.model.dto;

import java.io.Serializable;

public class ScheduleReportTypeDTO implements Serializable {

	private Long id;
	private String nameType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNameType() {
		return nameType;
	}
	public void setNameType(String nameType) {
		this.nameType = nameType;
	}
	public ScheduleReportTypeDTO(Long id, String nameType) {
		super();
		this.id = id;
		this.nameType = nameType;
	}
	public ScheduleReportTypeDTO() {
		super();
	}
	
	
	
}
