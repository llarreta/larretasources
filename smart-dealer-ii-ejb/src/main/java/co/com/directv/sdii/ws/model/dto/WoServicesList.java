/**
 * 
 */
package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.vo.WorkOrderServiceVO;

/**
 * @author P-
 *
 */
public class WoServicesList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6718834657317747910L;
	
	
	private List<WorkOrderServiceVO> workorderServices;

	public List<WorkOrderServiceVO> getWorkorderServices() {
		return workorderServices;
	}

	public void setWorkorderServices(List<WorkOrderServiceVO> workorderServices) {
		this.workorderServices = workorderServices;
	}
	
	
	
}
