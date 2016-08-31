package co.com.directv.sdii.model.pojo;

import java.io.Serializable;

public class ScheduleReportType implements Serializable {
	
	private Long id;
	private String code;
	private String reportClass;
	private String prefixFile;
	private String description;
	private Long pageSize;
	
	public ScheduleReportType(ScheduleReportType srt) {
		super();
		this.id = srt.id;
		this.code = srt.code;
		this.reportClass = srt.reportClass;
		this.prefixFile = srt.prefixFile;
		this.description = srt.description;
		this.pageSize=srt.pageSize;
	}
	
	
	
	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Long getPageSize() {
		return pageSize;
	}



	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}



	public ScheduleReportType(Long id, String code, String reportClass,
			String prefixFile) {
		super();
		this.id = id;
		this.code = code;
		this.reportClass = reportClass;
		this.prefixFile = prefixFile;
	}
	public ScheduleReportType() {
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
	public String getReportClass() {
		return reportClass;
	}
	public void setReportClass(String reportClass) {
		this.reportClass = reportClass;
	}
	public String getPrefixFile() {
		return prefixFile;
	}
	public void setPrefixFile(String prefixFile) {
		this.prefixFile = prefixFile;
	}

}
