package co.com.directv.sdii.model.pojo;

import java.sql.Timestamp;

import co.com.directv.sdii.audit.Auditable;

public class PorccessLinkClientSerialLog implements java.io.Serializable,Auditable {

	/**
	 * Atributo encargado de tener el id de base de datos del log de bvinculacion de seriales de IBS
	 * @author Aharker
	 */
	private Long id;
	
	/**
	 * Atributo encargado de referenciar la work order de la cual se intento realizar la vinculacion
	 * @author Aharker
	 */
	private WorkOrder workOrder;
	
	/**
	 * Atributo que almacena el serial principal que IBS señalo para la vinculacion
	 * @author Aharker
	 */
	private String principalSerial;

	/**
	 * Atributo que almacena el serial vinculado que IBS señalo para la vinculacion
	 * @author Aharker
	 */
	private String linkedSerial;
	
	/**
	 * Registro de la accion de vinculacion
	 * @author Aharker
	 */
	private String logMessage;
	
	/**
	 * Fecha y hora en la que se realiza el registro
	 * @author Aharker
	 */
	private Timestamp dateLog;
	
	public Timestamp getDateLog() {
		return dateLog;
	}
	public void setDateLog(Timestamp dateLog) {
		this.dateLog = dateLog;
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
	public String getPrincipalSerial() {
		return principalSerial;
	}
	public void setPrincipalSerial(String principalSerial) {
		this.principalSerial = principalSerial;
	}
	public String getLinkedSerial() {
		return linkedSerial;
	}
	public void setLinkedSerial(String linkedSerial) {
		this.linkedSerial = linkedSerial;
	}
	public String getLogMessage() {
		return logMessage;
	}
	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

	public PorccessLinkClientSerialLog(Long id, WorkOrder workOrder,
			String principalSerial, String linkedSerial, String logMessage,
			Timestamp dateLog) {
		super();
		this.id = id;
		this.workOrder = workOrder;
		this.principalSerial = principalSerial;
		this.linkedSerial = linkedSerial;
		this.logMessage = logMessage;
		this.dateLog = dateLog;
	}
	public PorccessLinkClientSerialLog() {
		super();
	}
	public PorccessLinkClientSerialLog(PorccessLinkClientSerialLog copy) {
		super();
		this.id = copy.id;
		this.workOrder = copy.workOrder;
		this.principalSerial = copy.principalSerial;
		this.linkedSerial = copy.linkedSerial;
		this.logMessage = copy.logMessage;
		this.dateLog = copy.dateLog;
	}
	
	
	
}
