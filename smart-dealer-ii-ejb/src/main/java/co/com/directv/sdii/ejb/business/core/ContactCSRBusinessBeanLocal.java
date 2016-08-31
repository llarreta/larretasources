package co.com.directv.sdii.ejb.business.core;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.WorkOrderCSRDTO;
import co.com.directv.sdii.model.pojo.WoStatusHistory;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkorderReason;
import co.com.directv.sdii.model.pojo.WorkorderStatus;

/**
 * Gestion de contact CSR
 * 
 * Fecha de Creación: 12/04/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface ContactCSRBusinessBeanLocal {

	/**
	 * Metodo: Crea un Contact de Gestion de la WorkOrder
	 * @param woStatusHistory
	 * @param woReason
	 * @param countryCode
	 * @param woStatus
	 * @param request
	 * @param workOrderPojo
	 * @throws BusinessException
	 * @author cduarte
	 */
	public void createWoContact(WoStatusHistory woStatusHistory, 
            WorkorderReason woReason, 
            String countryCode,
            WorkorderStatus woStatus,
            WorkOrderCSRDTO request, WorkOrder workOrderPojo) throws BusinessException;
	
}
