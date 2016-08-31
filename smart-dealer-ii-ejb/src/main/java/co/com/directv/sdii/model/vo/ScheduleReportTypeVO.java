package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.ScheduleReportType;

public class ScheduleReportTypeVO extends ScheduleReportType implements
		Serializable {

	public ScheduleReportTypeVO(ScheduleReportType srt) {
		super(srt);
	}
	
	public ScheduleReportTypeVO() {
		super();
	}
}
