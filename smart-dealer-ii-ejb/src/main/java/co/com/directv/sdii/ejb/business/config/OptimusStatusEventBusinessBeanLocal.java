package co.com.directv.sdii.ejb.business.config;
import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.OptimusStatusEvent;

@Local
public interface OptimusStatusEventBusinessBeanLocal {
		
	public void createOptimusStatusEvent(OptimusStatusEvent optimusStatusEvent, byte[] resquestXML) throws BusinessException;
	
	public void updateOptimusStatusEvent(OptimusStatusEvent optimusStatusEvent) throws BusinessException;
	
}
