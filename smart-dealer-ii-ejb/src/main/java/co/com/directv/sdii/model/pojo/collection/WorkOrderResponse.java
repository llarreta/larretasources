package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

public class WorkOrderResponse extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2640504559907560384L;

	/*
	 * Arreglo de id's de workorders de tipo Long
	 * */
	private List<Object[]> workOrdersIds;

	public List<Object[]> getWorkOrdersIds() {
		return workOrdersIds;
	}

	public void setWorkOrdersIds(List<Object[]> workOrdersIds) {
		this.workOrdersIds = workOrdersIds;
	}
	
}
