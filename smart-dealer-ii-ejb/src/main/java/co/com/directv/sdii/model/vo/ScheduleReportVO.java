package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.Date;

import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.ScheduleReport;
import co.com.directv.sdii.model.pojo.ScheduleReportPeriodType;
import co.com.directv.sdii.model.pojo.ScheduleReportStatus;
import co.com.directv.sdii.model.pojo.ScheduleReportType;
import co.com.directv.sdii.model.pojo.User;

public class ScheduleReportVO extends ScheduleReport implements Serializable {

	public ScheduleReportVO(ScheduleReport sr){
		super(sr);
	}
	
	public ScheduleReportVO(){
		super();
	}
	
}
