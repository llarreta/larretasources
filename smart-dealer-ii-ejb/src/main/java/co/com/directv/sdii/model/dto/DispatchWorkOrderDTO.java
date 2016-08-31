package co.com.directv.sdii.model.dto;


public class DispatchWorkOrderDTO implements  java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1352375682105640906L;
	private Integer workOrderCode;
	private Integer ibsHistoryEventCode;
	
	public DispatchWorkOrderDTO(Integer workOrderCode, Integer ibsHistoryEventCode) {
		super();
		this.workOrderCode = workOrderCode;
		this.ibsHistoryEventCode = ibsHistoryEventCode;
	}

	public Integer getWorkOrderCode() {
		return workOrderCode;
	}

	public void setWorkOrderCode(Integer workOrderCode) {
		this.workOrderCode = workOrderCode;
	}

	public Integer getIbsHistoryEventCode() {
		return ibsHistoryEventCode;
	}

	public void setIbsHistoryEventCode(Integer ibsHistoryEventCode) {
		this.ibsHistoryEventCode = ibsHistoryEventCode;
	}
	
}
