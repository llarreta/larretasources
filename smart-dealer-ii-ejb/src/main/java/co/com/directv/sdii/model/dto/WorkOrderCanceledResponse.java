package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.collection.CollectionBase;

public class WorkOrderCanceledResponse extends CollectionBase implements Serializable {
	
	private List<WorkOrderCanceledDTO> listResponse;

	public List<WorkOrderCanceledDTO> getListResponse() {
		return listResponse;
	}

	public void setListResponse(List<WorkOrderCanceledDTO> listResponse) {
		this.listResponse = listResponse;
	}

	public WorkOrderCanceledResponse(List<WorkOrderCanceledDTO> listResponse) {
		super();
		this.listResponse = listResponse;
	}

	public WorkOrderCanceledResponse() {
		super();
	}
	
	
	
}
