package co.com.directv.sdii.ejb.business.core.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.dto.esb.dispatchtechnician.DispatchWorkOrderResponse;
import co.com.directv.sdii.dto.esb.dispatchtechnician.ResponseMetadataType;
import co.com.directv.sdii.dto.esb.dispatchtechnician.SharedApplicationItem;
import co.com.directv.sdii.dto.esb.dispatchtechnician.WithdrawWorkOrderFromTechnicianResponse;
import co.com.directv.sdii.dto.esb.dispatchtechnician.WorkOrderResultItem;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.core.DispatchWorkOrderBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.DispatchWorkOrderLog;
import co.com.directv.sdii.persistence.dao.core.DispatchWorkOrderDAOLocal;

/**
 * Session Bean implementation class DispatchWorkOrderBusinessBean
 * 
 * @author fsanabri
 */
@Stateless(name="DispatchWorkOrderBusinessBean",mappedName="ejb/DispatchWorkOrderBusinessBean")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DispatchWorkOrderBusinessBean extends BusinessBase implements DispatchWorkOrderBusinessLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DispatchWorkOrderBusinessBean.class);
	
	@EJB(name="DispatchWorkOrderDAOLocal", beanInterface=DispatchWorkOrderDAOLocal.class)
	private DispatchWorkOrderDAOLocal dispatchWorkOrderDAOLocal;

    /**
     * Default constructor. 
     */
    public DispatchWorkOrderBusinessBean() {
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void processDispatchWorkOrderResponse(DispatchWorkOrderResponse response, byte[] responseXml) throws BusinessException {
		log.info("== Inicia processDispatchWorkOrderResponse/DispatchWorkOrderBusinessBean ==");
		try{
			List<WorkOrderResultItem> woList = response.getDispatchWorkOrderResult().getWorkOrderResultCollection().getWorkOrderResultItem();
			ResponseMetadataType metadata = response.getResponseMetadata();
			copyAndPersistDispatchWorkOrderLogs(true, metadata, woList, responseXml);

		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n processDispatchWorkOrderResponse/DispatchWorkOrderBusinessBean ==");
        	throw this.manageException(ex);
        }finally {
	        log.info("== Termina processDispatchWorkOrderResponse/DispatchWorkOrderBusinessBean ==");
	    }
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void processWithdrawWoFromTechResponse(WithdrawWorkOrderFromTechnicianResponse response, byte[] responseXml) throws BusinessException {
		log.info("== Inicia processWithdrawWoFromTechResponse/DispatchWorkOrderBusinessBean ==");
		try{
			List<WorkOrderResultItem> woList = response.getWithdrawWorkOrderFromTechnicianResult().getWorkOrderResultCollection().getWorkOrderResultItem();
			ResponseMetadataType metadata = response.getResponseMetadata();
			copyAndPersistDispatchWorkOrderLogs(false, metadata, woList, responseXml);

		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n processWithdrawWoFromTechResponse/DispatchWorkOrderBusinessBean ==");
        	throw this.manageException(ex);
        }finally {
	        log.info("== Termina processWithdrawWoFromTechResponse/DispatchWorkOrderBusinessBean ==");
	    }
	}
	
	private void copyAndPersistDispatchWorkOrderLogs(Boolean isDispatch, ResponseMetadataType metadata, List<WorkOrderResultItem> woList, byte[] responseXml) throws DAOServiceException, DAOSQLException, PropertiesException {

		for(WorkOrderResultItem woRI : woList){					
			DispatchWorkOrderLog dispatchWorkOrderLog = new DispatchWorkOrderLog();
			dispatchWorkOrderLog.setTarget(metadata.getRequestMetadata().getTarget().getName());
			dispatchWorkOrderLog.setEmployeeCode(woRI.getTechnician().getID());
			dispatchWorkOrderLog.setWoCode(woRI.getWorkOrder().getID());
			dispatchWorkOrderLog.setCreationDate(new Timestamp(new java.util.Date().getTime()));
			String isDispatchStr = isDispatch ? CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity() : CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity();
			
			dispatchWorkOrderLog.setIsDispatch(isDispatchStr);
			List<SharedApplicationItem> sharedAppItems = woRI.getSharedApplicationCollection().getSharedApplicationItem();
			for(SharedApplicationItem shared : sharedAppItems){
				if(CodesBusinessEntityEnum.SYSTEM_NAME_IBS.getCodeEntity().equals(shared.getApplicationCode())){
					dispatchWorkOrderLog.setIbsStatusCode(shared.getStatus().getCode()+"");
					dispatchWorkOrderLog.setIbsMessage(shared.getStatus().getMessage());
				}else if (CodesBusinessEntityEnum.SYSTEM_NAME_OPTIMUS.getCodeEntity().equals(shared.getApplicationCode())){
					dispatchWorkOrderLog.setOptimusStatusCode(shared.getStatus().getCode()+"");
					dispatchWorkOrderLog.setOptimusMessage(shared.getStatus().getMessage());
				}
			}
		
			dispatchWorkOrderDAOLocal.createDispatchWorkOrderLog(dispatchWorkOrderLog, responseXml);
		}
	}

}
