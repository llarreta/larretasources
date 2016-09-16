package co.com.directv.sdii.model.pojo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import co.com.directv.sdii.audit.Auditable;

public class WorkOrderExport implements java.io.Serializable,Auditable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7987371206432737837L;
	private Long id;
	private Timestamp creationDate;
	private Set<WorkOrderExportData> workOrdersExportDatas = new HashSet<WorkOrderExportData>(0);
	private Long numWo;
	private Long idUsuario;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	public Set<WorkOrderExportData> getWorkOrdersExportDatas() {
		return workOrdersExportDatas;
	}
	public void setWorkOrdersExportDatas(Set<WorkOrderExportData> workOrderExportData) {
		this.workOrdersExportDatas = workOrderExportData;
	}
	public Long getNumWo() {
		return numWo;
	}
	public void setNumWo(Long numWo) {
		this.numWo = numWo;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
}