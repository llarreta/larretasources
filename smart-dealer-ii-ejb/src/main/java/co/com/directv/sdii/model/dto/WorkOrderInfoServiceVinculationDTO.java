package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;

public class WorkOrderInfoServiceVinculationDTO implements Serializable{

	private Long crewId;
	private List<WorkOrderVO> workOrderList;
	public Long getCrewId() {
		return crewId;
	}
	public void setCrewId(Long crew) {
		this.crewId = crew;
	}
	public List<WorkOrderVO> getWorkOrderList() {
		return workOrderList;
	}
	public void setWorkOrderList(List<WorkOrderVO> workOrderList) {
		this.workOrderList = workOrderList;
	}
	public WorkOrderInfoServiceVinculationDTO(Long crew,
			List<WorkOrderVO> workOrderList) {
		super();
		this.crewId = crew;
		this.workOrderList = workOrderList;
	}
	public WorkOrderInfoServiceVinculationDTO() {
		super();
	}
	
	
}
