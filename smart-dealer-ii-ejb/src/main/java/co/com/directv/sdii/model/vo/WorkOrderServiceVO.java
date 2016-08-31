package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.com.directv.sdii.model.pojo.WorkOrderService;

/**
 * 
 * WorkOrderService Value Object
 * 
 * Fecha de Creaci�n: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WorkOrderService
 */
public class WorkOrderServiceVO extends WorkOrderService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6868039867327861236L;

	private Boolean addService;
	
	private List<WorkOrderServiceElementVO> woServiceElements;
	private List<WorkOrderServiceElementVO> woServiceElementsRecovery;
		
	public Boolean getAddService() {
		return addService;
	}

	public void setAddService(Boolean addService) {
		this.addService = addService;
	}

	public List<WorkOrderServiceElementVO> getWoServiceElements() {
		if(woServiceElements == null){
			woServiceElements = new ArrayList<WorkOrderServiceElementVO>();
		}
		return woServiceElements;
	}

	public void setWoServiceElements(
			List<WorkOrderServiceElementVO> woServiceElements) {
		this.woServiceElements = woServiceElements;
	}

	public List<WorkOrderServiceElementVO> getWoServiceElementsRecovery() {
		return woServiceElementsRecovery;
	}

	public void setWoServiceElementsRecovery(
			List<WorkOrderServiceElementVO> woServiceElementsRecovery) {
		this.woServiceElementsRecovery = woServiceElementsRecovery;
	}	
}
