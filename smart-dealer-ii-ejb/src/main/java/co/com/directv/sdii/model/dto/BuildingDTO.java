package co.com.directv.sdii.model.dto;

import co.com.directv.sdii.model.vo.BuildingVO;

public class BuildingDTO extends BaseDTO {

	private static final long serialVersionUID = -4104648658113425299L;

	private BuildingVO building;	
	private String action;
	
	public BuildingVO getBuilding() {
		return building;
	}
	public void setBuilding(BuildingVO building) {
		this.building = building;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
}
