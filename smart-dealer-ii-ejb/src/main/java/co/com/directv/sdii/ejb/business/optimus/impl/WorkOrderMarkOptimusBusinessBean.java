package co.com.directv.sdii.ejb.business.optimus.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.optimus.WorkOrderMarkOptimusBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.security.impl.SecurityBusinessBean;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderMark;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;

@Stateless(name="WorkOrderMarkOptimusBusinessBean", mappedName = "ejb/WorkOrderMarkOptimusBusinessBean")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WorkOrderMarkOptimusBusinessBean extends BusinessBase implements WorkOrderMarkOptimusBusinessBeanLocal{

	private final static Logger log = UtilsBusiness.getLog4J(SecurityBusinessBean.class);
	
	@EJB(name="WorkOrderMarkBusinessBeanLocal",beanInterface=WorkOrderMarkBusinessBeanLocal.class)
	private WorkOrderMarkBusinessBeanLocal workOrderMarkBusiness;
	
	@EJB(name="WorkOrderDAOLocal", beanInterface=WorkOrderDAOLocal.class)
	private WorkOrderDAOLocal workOrderDAO;

	//constructor por defecto
	public WorkOrderMarkOptimusBusinessBean(){
		
	}
	
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void deleteWorkOrderOptimusStatus(WorkOrder workOrder) throws BusinessException {
    	log.debug("== Inicia deleteWorkOrderOptimusStatus/WorkOrderMarkOptimusBusinessBean ==");
		try {
			//Si el optimus status no es null entonces hay que quitar el estado.
			if(workOrder.getOptimusStatus() != null){						
				// nulear OptimusStatus y OptimusStatusDate
				workOrder.setOptimusStatus(null);
				workOrder.setOptimusStatusDate(null);
				//workOrderDAO.updateWorkOrder(workOrder, "crear un evento para StatusMessage al momento de nulear OptimusStatus y OptimusStatusDate");
				workOrderDAO.updateWorkOrder(workOrder, CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_OPTIMUS.getCodeEntity());
			}
		} catch (Exception e) {
			log.error("== Error al tratar de ejecutar la operacion deleteWorkOrderOptimusStatus/WorkOrderMarkOptimusBusinessBean ==");
        	throw this.manageException(e);
		}
	}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void deleteWorkOrderOptimusStatusDecline(WorkOrder workOrder, Long userId) throws BusinessException {
    	log.debug("== Inicia deleteWorkOrderOptimusStatusDecline/WorkOrderMarkOptimusBusinessBean ==");
		try {
			//Si el optimus decline code no es null entonces hay que quitar la marca.
			if(workOrder.getOptimusDeclineCode() != null){
				WorkOrderMark workOrderMark = workOrderMarkBusiness.getWorkOrderMarkByCode(CodesBusinessEntityEnum.CODE_WORK_ORDER_MARK_REJECTED_BY_CREW.getCodeEntity());			
				workOrderMarkBusiness.markOrUnMarkWorkOrder(workOrder.getId(), workOrderMark.getId(), userId, false);
				
				workOrder.setOptimusDeclineCode(null);
				workOrder.setOptimusDeclineDescription(null);
				workOrderDAO.updateWorkOrder(workOrder, CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_OPTIMUS.getCodeEntity());
			}
		} catch (Exception e) {
			log.error("== Error al tratar de ejecutar la operacion deleteWorkOrderOptimusStatusDecline/WorkOrderMarkOptimusBusinessBean ==");
        	throw this.manageException(e);
		}
		
	}
    
}
