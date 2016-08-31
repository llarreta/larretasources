/**
 * Creado 11/02/2016
 */
package co.com.directv.sdii.ejb.business.core.tray.impl;

import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.apache.log4j.Logger;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.WorkOrderBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.core.CoreWOAttentionsBusinessLocal;
import co.com.directv.sdii.ejb.business.core.tray.ReportHelperBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.AttentionStatus;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.vo.WoAttentionStatusVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;

/**
 * Implementa metodos helper para el proceso de atenciones de una WorkOrder. 
 * 
 * @author Jorge Ariel Silva
 * @version 1.0
 * 
 */
@Stateless(name="ReportHelperBusinessBeanLocal",mappedName="ejb/ReportHelperBusinessBeanLocal") //ejb/TrayManagmentBusinessHelperLocal
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReportHelperBusinessBean extends BusinessBase implements ReportHelperBusinessBeanLocal {

	private final static Logger log = UtilsBusiness.getLog4J(ReportHelperBusinessBean.class);
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
	
	@EJB(name="CoreWOAttentionsBusinessLocal", beanInterface=CoreWOAttentionsBusinessLocal.class)
	private CoreWOAttentionsBusinessLocal coreWOAttentionsBusiness;
	
	@EJB(name="WorkOrderBusinessBeanLocal",beanInterface=WorkOrderBusinessBeanLocal.class)	
	private WorkOrderBusinessBeanLocal workOrderBusiness;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
	/**
	 * Metodo: Persiste el error generado en el 
	 * proceso de Atención y/o Finalización
	 * @param workOrderId
	 * @param woAttentionStatusCode
	 * @param messageCode
	 * @param message
	 * @throws BusinessException
	 * @author jalopez
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void reportWoAttentionStatus(Long workOrderId, String woAttentionStatusCode, String messageCode, String message, Long ... userId) throws BusinessException {
		
		log.debug("== Inicia reportWoAttentionStatus/TrayManagmentBusinessHelper ==");
		
		try{
			User userModification=null;
			if(userId!=null && userId.length>0 && userId[0]!=null){
				userModification=userDao.getUserByIdWithNewTransaction(userId[0]);
			}
			AttentionStatus attStatus = coreWOAttentionsBusiness.getAttentionStatus(woAttentionStatusCode);	
			WorkOrder wo=this.workOrderBusiness.getWorkOrderByID(workOrderId);
			if(wo==null || wo.getCountry()==null || wo.getCountry().getId()==null){
				throw new BusinessException(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
			}
			WoAttentionStatusVO statusVo = new WoAttentionStatusVO();
			statusVo.setAttentionStatus(attStatus);
			statusVo.setWoId(workOrderId);
			statusVo.setMessage(message);
			statusVo.setMessageCode(messageCode);
			Date dateNow=UtilsBusiness.getDateLastChangeOfUser(wo.getCountry().getId(), userDao, systemParameterDAO);
			statusVo.setAttentionDate(dateNow);
			statusVo.setRecordStatus(CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_INACTIVE.getCodeEntity());
			coreWOAttentionsBusiness.reportWoAttentionStatus(statusVo);
			if(userModification!=null){
				coreWOAttentionsBusiness.sendTrayManagementErrorMail(workOrderId, messageCode, statusVo, message, userModification);
			}
			else{
				coreWOAttentionsBusiness.sendTrayManagementErrorMail(workOrderId, messageCode, statusVo, message);
			}
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion reportWoAttentionStatus/TrayManagmentBusinessHelper");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina reportWoAttentionStatus/TrayManagmentBusinessHelper ==");
		}
	}
}
