package co.com.directv.sdii.model.pojo;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

public class OptimusStatusHistory implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id; 
	private WorkOrder workOrder;
	private OptimusStatus optimusStatus;
	private Date optimusDate;

	public OptimusStatusHistory(){
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}
	
	public OptimusStatus getOptimusStatus() {
		return optimusStatus;
	}

	public void setOptimusStatus(OptimusStatus optimusStatus) {
		this.optimusStatus = optimusStatus;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getOptimusDate() {
		return optimusDate;
	}

	public void setOptimusDate(Date optimusDate) {
		this.optimusDate = optimusDate;
	}
	
}
