package co.com.directv.sdii.model.dto;

import java.util.List;

public class DispatchWorkOrderResponseDTO implements  java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6975724240743320576L;
	private List<DispatchWorkOrderDTO> dispatchWorkOrderDTOs;
	
	
	public DispatchWorkOrderResponseDTO(List<DispatchWorkOrderDTO> dispatchWorkOrderDTOs) {
		super();
		this.dispatchWorkOrderDTOs = dispatchWorkOrderDTOs;
	}

	public List<DispatchWorkOrderDTO> getDispatchWorkOrderDTOs() {
		return dispatchWorkOrderDTOs;
	}
}
