package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.dto.WorkOrderTrayDTO;

public class WorkOrderTrayResponse extends CollectionBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4218581568914380036L;
	
	private List<WorkOrderTrayDTO> workOrderList;

	public List<WorkOrderTrayDTO> getWorkOrderList() {
		return workOrderList;
	}

	public void setWorkOrderList(List<WorkOrderTrayDTO> workOrderList) {
		this.workOrderList = workOrderList;
	}

}
