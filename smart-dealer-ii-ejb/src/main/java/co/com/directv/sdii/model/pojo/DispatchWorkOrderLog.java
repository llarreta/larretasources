package co.com.directv.sdii.model.pojo;

import java.sql.Blob;
import java.util.Date;

/**
 * DispatchWorkOrderLog entity. @author MyEclipse Persistence Tools
 */

public class DispatchWorkOrderLog implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -270152796826549315L;
	private Long id;
	private String target;
	private String woCode;
	private String employeeCode;
	private String ibsStatusCode;
	private String ibsMessage;
	private String optimusStatusCode;
	private String optimusMessage;
	private String isDispatch;
	private Date creationDate;
	private Blob responseXml;

	// Constructors

	/** default constructor */
	public DispatchWorkOrderLog() {
	}

	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getWoCode() {
		return woCode;
	}

	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getIbsStatusCode() {
		return ibsStatusCode;
	}

	public void setIbsStatusCode(String ibsStatusCode) {
		this.ibsStatusCode = ibsStatusCode;
	}

	public String getIbsMessage() {
		return ibsMessage;
	}

	public void setIbsMessage(String ibsMessage) {
		this.ibsMessage = ibsMessage;
	}

	public String getOptimusStatusCode() {
		return optimusStatusCode;
	}

	public void setOptimusStatusCode(String optimusStatusCode) {
		this.optimusStatusCode = optimusStatusCode;
	}

	public String getOptimusMessage() {
		return optimusMessage;
	}

	public void setOptimusMessage(String optimusMessage) {
		this.optimusMessage = optimusMessage;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Blob getResponseXml() {
		return responseXml;
	}

	public void setResponseXml(Blob responseXml) {
		this.responseXml = responseXml;
	}

	public String getIsDispatch() {
		return isDispatch;
	}

	public void setIsDispatch(String isDispatch) {
		this.isDispatch = isDispatch;
	}
	
}