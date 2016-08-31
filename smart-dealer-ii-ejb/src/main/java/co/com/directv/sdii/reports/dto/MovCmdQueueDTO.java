package co.com.directv.sdii.reports.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;
import co.com.directv.sdii.model.pojo.MovCmdLog;
import co.com.directv.sdii.model.pojo.Warehouse;

/**
 * MovCmdQueue entity. @author MyEclipse Persistence Tools
 */

public class MovCmdQueueDTO implements java.io.Serializable {
	
	private Long id;

	private String status;
	
	private String statusCode;
	
	private Date creationDate;
	
	private Date executeDate;
	
	private String serial;
	
	private String serialLinked;
	
	private String warehouseNameSource;
	
	private String warehouseNameTarget;
	
	private String typeNumberDocument;
	
	private String comments;
	
	private Warehouse warehouseSource;
	
	private Warehouse warehouseTarget;
	
	private String isManagment;
	
	private boolean showButtonComments;
	
	private boolean showObservations;
	
	private String lastObservation;
	
	private List<MovCmdLog> listResultProcess;
	
	public MovCmdQueueDTO(){
		
	}
	
	

	public MovCmdQueueDTO(Long id, String status, Date creationDate, Date executeDate,
			String serial, String serialLinked, Warehouse warehouseSource,
			Warehouse warehouseTarget, String typeNumberDocument,
			String comments, String statusCode, String lastObservation, String isManagment) {
		super();
		this.id = id;
		this.status = status;
		this.creationDate = creationDate;
		this.executeDate = executeDate;
		this.serial = serial;
		this.serialLinked = serialLinked;
		this.warehouseSource = warehouseSource;
		this.warehouseTarget = warehouseTarget;
		this.typeNumberDocument = typeNumberDocument;
		this.comments = comments;
		this.statusCode = statusCode; 
		this.lastObservation = lastObservation;
		this.isManagment = isManagment;
	}



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getSerialLinked() {
		return serialLinked;
	}

	public void setSerialLinked(String serialLinked) {
		this.serialLinked = serialLinked;
	}

	public String getWarehouseNameSource() {
		return warehouseNameSource;
	}

	public void setWarehouseNameSource(String warehouseNameSource) {
		this.warehouseNameSource = warehouseNameSource;
	}

	public String getWarehouseNameTarget() {
		return warehouseNameTarget;
	}

	public void setWarehouseNameTarget(String warehouseNameTarget) {
		this.warehouseNameTarget = warehouseNameTarget;
	}

	public String getTypeNumberDocument() {
		return typeNumberDocument;
	}

	public void setTypeNumberDocument(String typeNumberDocument) {
		this.typeNumberDocument = typeNumberDocument;
	}

	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean isShowButtonComments() {
		return showButtonComments;
	}

	public void setShowButtonComments(boolean showButtonComments) {
		this.showButtonComments = showButtonComments;
	}

	public Warehouse getWarehouseSource() {
		return warehouseSource;
	}

	public void setWarehouseSource(Warehouse warehouseSource) {
		this.warehouseSource = warehouseSource;
	}

	public Warehouse getWarehouseTarget() {
		return warehouseTarget;
	}

	public void setWarehouseTarget(Warehouse warehouseTarget) {
		this.warehouseTarget = warehouseTarget;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<MovCmdLog> getListResultProcess() {
		return listResultProcess;
	}

	public void setListResultProcess(List<MovCmdLog> listResultProcess) {
		this.listResultProcess = listResultProcess;
	}

	public boolean isShowObservations() {
		return showObservations;
	}

	public void setShowObservations(boolean showObservations) {
		this.showObservations = showObservations;
	}

	public String getLastObservation() {
		return lastObservation;
	}
	
	public void setLastObservation(String lastObservation) {
		this.lastObservation = lastObservation;
	}

	public String getIsManagment() {
		return isManagment;
	}

	public void setIsManagment(String isManagment) {
		this.isManagment = isManagment;
	}
	
}