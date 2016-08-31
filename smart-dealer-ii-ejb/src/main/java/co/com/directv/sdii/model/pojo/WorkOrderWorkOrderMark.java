package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * WorkOrder entity.
 * 
 * Fecha de Creación: 19/09/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class WorkOrderWorkOrderMark implements java.io.Serializable {


	private static final long serialVersionUID = -4187864903532259553L;
	
	private Long id;
	private WorkOrder workOrder;
	private WorkOrderMark workOrderMark;
	private User userMark;
	private Date userMarkDate;
	private User userUnMark;
	private Date userUnMarkDate;
	private String isActive;
	private String observation;
	
	public WorkOrderWorkOrderMark() {
		super();
	}
	
	public WorkOrderWorkOrderMark(Long id, 
			                      WorkOrder workOrder,
								  WorkOrderMark workOrderMark, 
								  User userMark, 
								  Date userMarkDate,
								  User userUnMark, 
								  Date userUnMarkDate, 
								  String isActive,
								  String observation) {
		super();
		this.id = id;
		this.workOrder = workOrder;
		this.workOrderMark = workOrderMark;
		this.userMark = userMark;
		this.userMarkDate = userMarkDate;
		this.userUnMark = userUnMark;
		this.userUnMarkDate = userUnMarkDate;
		this.isActive = isActive;
		this.observation = observation;
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
	public WorkOrderMark getWorkOrderMark() {
		return workOrderMark;
	}
	public void setWorkOrderMark(WorkOrderMark workOrderMark) {
		this.workOrderMark = workOrderMark;
	}
	public User getUserMark() {
		return userMark;
	}
	public void setUserMark(User userMark) {
		this.userMark = userMark;
	}
	public User getUserUnMark() {
		return userUnMark;
	}
	public void setUserUnMark(User userUnMark) {
		this.userUnMark = userUnMark;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getUserMarkDate() {
		return userMarkDate;
	}

	public void setUserMarkDate(Date userMarkDate) {
		this.userMarkDate = userMarkDate;
	}

	public Date getUserUnMarkDate() {
		return userUnMarkDate;
	}

	public void setUserUnMarkDate(Date userUnMarkDate) {
		this.userUnMarkDate = userUnMarkDate;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}	
	
	
}