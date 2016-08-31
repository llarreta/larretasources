package co.com.directv.sdii.model.dto;

public class GeneralParametersDTO {

	private Long statusScheduled;
	private Long statusReScheduled;
	private Long statusReAssign;
	private Long statusAssign;
	private Long statusRealized;
	
	public Long getStatusScheduled() {
		return statusScheduled;
	}
	public void setStatusScheduled(Long statusScheduled) {
		this.statusScheduled = statusScheduled;
	}
	public Long getStatusReScheduled() {
		return statusReScheduled;
	}
	public void setStatusReScheduled(Long statusReScheduled) {
		this.statusReScheduled = statusReScheduled;
	}
	public Long getStatusReAssign() {
		return statusReAssign;
	}
	public void setStatusReAssign(Long statusReAssign) {
		this.statusReAssign = statusReAssign;
	}
	public Long getStatusAssign() {
		return statusAssign;
	}
	public void setStatusAssign(Long statusAssign) {
		this.statusAssign = statusAssign;
	}
	public Long getStatusRealized() {
		return statusRealized;
	}
	public void setStatusRealized(Long statusRealized) {
		this.statusRealized = statusRealized;
	}

}
