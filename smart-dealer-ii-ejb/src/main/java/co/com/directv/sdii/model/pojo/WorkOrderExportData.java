package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


public class WorkOrderExportData implements java.io.Serializable,Auditable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2101617337790286576L;
		
	private WorkOrderExportDataId id;
	private WorkOrderExport workOrdersExport;

	public WorkOrderExportData() {
	}

	public WorkOrderExportData(WorkOrderExportDataId id, WorkOrderExport workOrdersExport) {
		this.id = id;
		this.workOrdersExport = workOrdersExport;
	}

	public WorkOrderExportDataId getId() {
		return this.id;
	}

	public void setId(WorkOrderExportDataId id) {
		this.id = id;
	}

	public WorkOrderExport getWorkOrdersExport() {
		return this.workOrdersExport;
	}

	public void setWorkOrdersExport(WorkOrderExport workOrdersExport) {
		this.workOrdersExport = workOrdersExport;
	}
}