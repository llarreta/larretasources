package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

public class SchedulerTaskDetailsDTO  implements Serializable{

	private java.util.Date initDate;
	private java.util.Date endDate;
	private String detail;

	
	public SchedulerTaskDetailsDTO() {
		super();
	}


	public java.util.Date getInitDate() {
		return initDate;
	}


	public void setInitDate(java.util.Date initDate) {
		this.initDate = initDate;
	}


	public java.util.Date getEndDate() {
		return endDate;
	}


	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}


	public String getDetail() {
		return detail;
	}


	public void setDetail(String detail) {
		this.detail = detail;
	}	
}
