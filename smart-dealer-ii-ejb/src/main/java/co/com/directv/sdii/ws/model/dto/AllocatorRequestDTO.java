/**
 * Creado 30/08/2010 21:11:36
 */
package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;
import java.util.Date;

import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 30/08/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class AllocatorRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5475228706110520167L;

	private DealerListDTO dealerList;
	
	private WorkOrderVO workOrder;
	
	private Date agendaDate;
	
	private ServiceHourVO serviceHour;

	public WorkOrderVO getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrderVO workOrder) {
		this.workOrder = workOrder;
	}

	public Date getAgendaDate() {
		return agendaDate;
	}

	public void setAgendaDate(Date agendaDate) {
		this.agendaDate = agendaDate;
	}

	public ServiceHourVO getServiceHour() {
		return serviceHour;
	}

	public void setServiceHour(ServiceHourVO serviceHour) {
		this.serviceHour = serviceHour;
	}

	public DealerListDTO getDealerList() {
		return dealerList;
	}

	public void setDealerList(DealerListDTO dealerList) {
		this.dealerList = dealerList;
	}
}
