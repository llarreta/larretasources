package co.com.directv.sdii.model.dto;

import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;

/**
 * 
 * Clase encargada de encapsular la informacion necesaria para asignar una Workorder 
 * 
 * Fecha de Creación: 12/10/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class AssignWorkOrderDTO extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3672865520470359473L;
	
	private DealerVO dealer;
	private WorkOrderVO workorder;
	
	/*
	 * variable que permite identificar si la asignacion viene del asignador
	 * */
	private boolean isAssignFromAllocator;
	
	
	public DealerVO getDealer() {
		return dealer;
	}
	public void setDealer(DealerVO dealer) {
		this.dealer = dealer;
	}
	public WorkOrderVO getWorkorder() {
		return workorder;
	}
	public void setWorkorder(WorkOrderVO workorder) {
		this.workorder = workorder;
	}
	public boolean isAssignFromAllocator() {
		return isAssignFromAllocator;
	}
	public void setAssignFromAllocator(boolean isAssignFromAllocator) {
		this.isAssignFromAllocator = isAssignFromAllocator;
	}

}
