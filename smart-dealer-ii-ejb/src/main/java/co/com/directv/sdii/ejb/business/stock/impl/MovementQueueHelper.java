package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.Date;

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
import co.com.directv.sdii.ejb.business.stock.MovementQueueHelperLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.MovCmdLog;
import co.com.directv.sdii.model.pojo.MovCmdQueue;
import co.com.directv.sdii.model.pojo.MovCmdStatus;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovCmdLogDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovCmdQueueDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovCmdStatusDAOLocal;

/**
 * EJB que sirve de helper para el manejo de la transaccionalidad para las 
 * operaciones sobre la entidad MovCmdQueue 
 * 
 * Session Bean implementation class MovementQueueHelper
 * 
 * @author waguilera - 13/02/2012
 */

@Stateless(name = "MovementQueueHelperLocal", mappedName = "ejb/MovementQueueBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MovementQueueHelper extends BusinessBase implements MovementQueueHelperLocal {
	
	@EJB(name="MovCmdQueueDAOLocal", beanInterface=MovCmdQueueDAOLocal.class)
	private MovCmdQueueDAOLocal daoMovCmdQueue;

	@EJB(name="MovCmdLogDAOLocal", beanInterface=MovCmdLogDAOLocal.class)
	private MovCmdLogDAOLocal daoMovCmdLog;

	@EJB(name="MovCmdStatusDAOLocal", beanInterface=MovCmdStatusDAOLocal.class)
	private MovCmdStatusDAOLocal daoMovCmdStatus;

	
	private final static Logger log =  UtilsBusiness.getLog4J(MovementQueueHelper.class);

	/**
	 * Default constructor. 
	 */
	public MovementQueueHelper() {
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createMovCmdQueueRegisterByWorkOrderSuccess(MovCmdQueue movCmdQueue, User user)
			throws BusinessException {
		log.debug("== Inicia getMovementQueueHspToIbsByFilter/MovementQueueBusinessBean ==");
		try {
			MovCmdStatus movCmdStatus = daoMovCmdStatus.getMovCmdStatusByCode(CodesBusinessEntityEnum.MOV_CMD_STATUS_PROCESSED.getCodeEntity());
			movCmdQueue.setMovCmdStatus(movCmdStatus);
			movCmdQueue.setExecuteDate(UtilsBusiness.getCurrentDateByTimeZoneKey(user.getSdiiTimeZone().getTimeZoneKey(), new Date()));
			daoMovCmdQueue.save(movCmdQueue);
			
			MovCmdLog movCmdLog = new MovCmdLog();
			movCmdLog.setMovCmdQueue(movCmdQueue);
			movCmdLog.setCreationDate(UtilsBusiness.getCurrentDateByTimeZoneKey(user.getSdiiTimeZone().getTimeZoneKey(), new Date()));
			movCmdLog.setDescription("Proceso terminado con éxito");
			daoMovCmdLog.save(movCmdLog);
		} catch (Throwable be){
			log.error("== Error getMovementQueueHspToIbsByFilter/MovementQueueBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina getMovementQueueHspToIbsByFilter/MovementQueueBusinessBean ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createMovCmdQueueRegisterByWorkOrderError(MovCmdQueue movCmdQueue, String message, User user)
			throws BusinessException {
		log.debug("== Inicia createMovCmdQueueRegisterByWorkOrderError/MovementQueueBusinessBean ==");
		try {
			MovCmdStatus movCmdStatus = daoMovCmdStatus.getMovCmdStatusByCode(CodesBusinessEntityEnum.MOV_CMD_STATUS_ERROR.getCodeEntity());
			movCmdQueue.setMovCmdStatus(movCmdStatus);
			movCmdQueue.setExecuteDate(UtilsBusiness.getCurrentDateByTimeZoneKey(user.getSdiiTimeZone().getTimeZoneKey(), new Date()));
			daoMovCmdQueue.save(movCmdQueue);
			
			MovCmdLog movCmdLog = new MovCmdLog();
			movCmdLog.setMovCmdQueue(movCmdQueue);
			movCmdLog.setCreationDate(UtilsBusiness.getCurrentDateByTimeZoneKey(user.getSdiiTimeZone().getTimeZoneKey(), new Date()));
			movCmdLog.setDescription(message);
			daoMovCmdLog.save(movCmdLog);
		} catch (Throwable be){
			log.error("== Error createMovCmdQueueRegisterByWorkOrderError/MovementQueueBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina createMovCmdQueueRegisterByWorkOrderError/MovementQueueBusinessBean ==");
		}
	}

}
