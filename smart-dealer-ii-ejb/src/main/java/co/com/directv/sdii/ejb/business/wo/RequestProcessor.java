package co.com.directv.sdii.ejb.business.wo;

import javax.ejb.Local;

import co.com.directv.sdii.dto.esb.notifyWorkOrdersOptimus.WorkOrderStatusChange;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.OptimusStatusEvent;

@Local
public interface RequestProcessor {
	public void start(WorkOrderStatusChange workOrderStatusChange, OptimusStatusEvent optimusStatusEvent) throws PropertiesException , BusinessException;
}
