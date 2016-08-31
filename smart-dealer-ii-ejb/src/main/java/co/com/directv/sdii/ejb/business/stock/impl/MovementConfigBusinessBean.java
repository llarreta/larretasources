package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.Date;
import java.util.List;

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
import co.com.directv.sdii.ejb.business.broker.InventoryServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.ResourceProvisioningBrokerLocal;
import co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ElementMovementDTO;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.pojo.MovCmdLog;
import co.com.directv.sdii.model.pojo.MovCmdQueue;
import co.com.directv.sdii.model.pojo.MovCmdStatus;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovCmdLogDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovCmdQueueDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovCmdStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.impl.MovCmdStatusDAO;

/**
 * EJB que implementa los métodos para realizar la configuración de los
 * informes que se realizan a IBS cuando se mueven elementos entre las
 * bodegas.
 * 
 * Session Bean implementation class MovementConfigBusinessBean
 * 
 * @author Jimmy Vélez Muñoz - 16/06/2011
 */

@Stateless(name = "MovementConfigBusinessBeanLocal", mappedName = "ejb/MovementConfigBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MovementConfigBusinessBean extends BusinessBase implements MovementConfigBusinessBeanLocal {

	@EJB(name = "ResourceProvisioningBroker", beanInterface = ResourceProvisioningBrokerLocal.class)
	private ResourceProvisioningBrokerLocal resourceProvisioningBroker;
	
	@EJB(name="MovCmdQueueDAOLocal", beanInterface=MovCmdQueueDAOLocal.class)
	private MovCmdQueueDAOLocal daoMovCmdQueue;

	@EJB(name="MovCmdLogDAOLocal", beanInterface=MovCmdLogDAOLocal.class)
	private MovCmdLogDAOLocal daoMovCmdLog;

	@EJB(name="MovCmdStatusDAOLocal", beanInterface=MovCmdStatusDAOLocal.class)
	private MovCmdStatusDAOLocal daoMovCmdStatus;
	
	@EJB(name="InventoryServiceBrokerLocal", beanInterface=InventoryServiceBrokerLocal.class)
	private InventoryServiceBrokerLocal inventoryServiceBroker;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
	private final static Logger log = UtilsBusiness.getLog4J(MovementConfigBusinessBean.class);

	/**
	 * Default constructor. 
	 */
	public MovementConfigBusinessBean() {
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal#createMovementCommandQueue(java.lang.Long, java.lang.Long, co.com.directv.sdii.model.vo.MovCmdConfigVO, co.com.directv.sdii.model.vo.CustomerVO, co.com.directv.sdii.model.vo.DealerVO, co.com.directv.sdii.model.vo.NotSerializedVO, co.com.directv.sdii.model.vo.SerializedVO, co.com.directv.sdii.model.vo.MovCmdStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createMovementCommandQueue(ElementMovementDTO dto) throws BusinessException {
		log.debug("== Inicia createMovementCommandQueue/MovementConfigBusinessBean ==");
		MovCmdQueue queueRecord = new MovCmdQueue();
		try {
			queueRecord = UtilsBusiness.copyObject(MovCmdQueue.class, dto.getMovCmdQueueVO());
			queueRecord.setCreationDate(new Date());
			daoMovCmdQueue.save(queueRecord);

		} catch (Throwable be){
			log.error("== Error createMovementCommandQueue/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina createMovementCommandQueue/MovementConfigBusinessBean ==");
		}    	
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal#getPendingMovCmdQueueRecords()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<MovCmdQueueVO> getPendingMovCmdQueueRecords() throws BusinessException {
		log.debug("== Inicia getPendingMovCmdQueueRecords/MovementConfigBusinessBean ==");
		List<MovCmdQueueVO> resultsVO = null;
		List<MovCmdQueue> resultsPojo = null;
		String movStatusCode = null;
		try {
			movStatusCode = CodesBusinessEntityEnum.MOV_CMD_STATUS_PENDING.getCodeEntity();
			resultsPojo = daoMovCmdQueue.findByStatus(movStatusCode);
			if (resultsPojo != null)
				resultsVO = UtilsBusiness.convertList(resultsPojo, MovCmdQueueVO.class);
			
			return resultsVO;
		} catch (Throwable be) {
			log.error("== Error getPendingMovCmdQueueRecords/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina getPendingMovCmdQueueRecords/MovementConfigBusinessBean ==");
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal#getPendingMovCmdQueueRecordsByPage()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<MovCmdQueueVO> getPendingMovCmdQueueRecordsByPage(Long countryId) throws BusinessException {
		log.debug("== Inicia getPendingMovCmdQueueRecordsByPage/MovementConfigBusinessBean ==");
		List<MovCmdQueueVO> resultsVO = null;
		List<MovCmdQueue> resultsPojo = null;
		String movStatusCode = null;
		try {
			movStatusCode = CodesBusinessEntityEnum.MOV_CMD_STATUS_PENDING.getCodeEntity();
			Long pageSize = this.getIbsNotificationPageSize(countryId);
			resultsPojo = daoMovCmdQueue.findByStatusAndPage(movStatusCode,pageSize,countryId);
			if (resultsPojo != null)
				resultsVO = UtilsBusiness.convertList(resultsPojo, MovCmdQueueVO.class);
			
			return resultsVO;
		} catch (Throwable be) {
			log.error("== Error getPendingMovCmdQueueRecordsByPage/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina getPendingMovCmdQueueRecordsByPage/MovementConfigBusinessBean ==");
		}
	}
	
	/*
	 * Req-0098 - Paralelismo de Inventarios
	 * 
	 * 27/08/2013 Modificación por HSP + IST Colombia IN22854
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<MovCmdQueueVO> getPendingMovCmdQueueRecordsByPageParallel(Long countryId) throws BusinessException {
		log.debug("== Inicia getPendingMovCmdQueueRecordsByPageParallel/MovementConfigBusinessBean ==");
		List<MovCmdQueueVO> resultsVO = null;
		List<MovCmdQueue> resultsPojo = null;
		String movStatusCode = null;
		try {
			movStatusCode = CodesBusinessEntityEnum.MOV_CMD_STATUS_PENDING.getCodeEntity();
			Long pageSize = this.getIbsNotificationPageSize(countryId);
			if (pageSize !=null ){
				resultsPojo = daoMovCmdQueue.findByStatusAndPageParallel(movStatusCode,pageSize,countryId);
				  //resultsPojo = daoMovCmdQueue.findByStatusAndPageParallel2(movStatusCode,pageSize,countryId);
					if (resultsPojo != null)
						resultsVO = UtilsBusiness.convertList(resultsPojo, MovCmdQueueVO.class);
					
					return resultsVO;	
			}else{
				throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAM_IBS_ELEMENTS_NOTIFICATION_NOT_EXIST.getCode(), ErrorBusinessMessages
						.SYSTEM_PARAM_IBS_ELEMENTS_NOTIFICATION_NOT_EXIST.getMessage());
			}
		    
		} catch (Throwable be) {
			log.error("== Error getPendingMovCmdQueueRecordsByPageParallel/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina getPendingMovCmdQueueRecordsByPageParallel/MovementConfigBusinessBean ==");
		}
	}	
	
	/**
	 * 
	 * Metodo: Consulta el parametro de sistema que determina cuantos elementos se consultan para notificar a IBS
	 * @return Long 
	 * @author jnova
	 * 
	 * 27/08/2013 ialessan Modificación por HSP + IST Colombia IN22854
	 */
	private Long getIbsNotificationPageSize(Long countryId) {
	  //Long result = 100L;
		Long result = null;
		String parameterCode = null;
		try {
			parameterCode = CodesBusinessEntityEnum.SYSTEM_PARAM_IBS_ELEMENTS_NOTIFICATION.getCodeEntity();
			SystemParameter pageSizeParam = systemParameterDAO.getSysParamByCodeAndCountryId(parameterCode,countryId);
			if(pageSizeParam!=null){
				result = Long.parseLong(pageSizeParam.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Consultando parametro para paginacion de elementos a notificar a IBS, el código del parámetro es: " + parameterCode, e);
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal#getErrorMovCmdQueueRecords()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdQueueVO> getErrorMovCmdQueueRecords() throws BusinessException {
		log.debug("== Inicia getErrorMovCmdQueueRecords/MovementConfigBusinessBean ==");
		List<MovCmdQueueVO> resultsVO = null;
		List<MovCmdQueue> resultsPojo = null;
		String movStatusCode = null;
		try {
			movStatusCode = CodesBusinessEntityEnum.MOV_CMD_STATUS_ERROR.getCodeEntity();
			resultsPojo = daoMovCmdQueue.findByStatus(movStatusCode);
			if (resultsPojo != null)
				resultsVO = UtilsBusiness.convertList(resultsPojo, MovCmdQueueVO.class);
			
			return resultsVO;
		} catch (Throwable be) {
			log.error("== Error getErrorMovCmdQueueRecords/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina getErrorMovCmdQueueRecords/MovementConfigBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal#getCompletedMovCmdQueueRecords()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdQueueVO> getCompletedMovCmdQueueRecords() throws BusinessException {
		log.debug("== Inicia getCompletedMovCmdQueueRecords/MovementConfigBusinessBean ==");
		List<MovCmdQueueVO> resultsVO = null;
		List<MovCmdQueue> resultsPojo = null;
		String movStatusCode = null;
		try {
			movStatusCode = CodesBusinessEntityEnum.MOV_CMD_STATUS_PROCESSING.getCodeEntity();
			resultsPojo = daoMovCmdQueue.findByStatus(movStatusCode);
			if (resultsPojo != null)
				resultsVO = UtilsBusiness.convertList(resultsPojo, MovCmdQueueVO.class);
			
			return resultsVO;
		} catch (Throwable be) {
			log.error("== Error getCompletedMovCmdQueueRecords/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina getCompletedMovCmdQueueRecords/MovementConfigBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal#getNoConfigMovCmdQueueRecords()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdQueueVO> getNoConfigMovCmdQueueRecords() throws BusinessException {
		log.debug("== Inicia getNoConfigMovCmdQueueRecords/MovementConfigBusinessBean ==");
		List<MovCmdQueueVO> resultsVO = null;
		List<MovCmdQueue> resultsPojo = null;
		String movStatusCode = null;
		try {
			movStatusCode = CodesBusinessEntityEnum.MOV_CMD_STATUS_NO_CONFIG.getCodeEntity();
			resultsPojo = daoMovCmdQueue.findByStatus(movStatusCode);
			if (resultsPojo != null)
				resultsVO = UtilsBusiness.convertList(resultsPojo, MovCmdQueueVO.class);
			
			return resultsVO;
		} catch (Throwable be) {
			log.error("== Error getNoConfigMovCmdQueueRecords/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina getNoConfigMovCmdQueueRecords/MovementConfigBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal#getAllMovCmdQueueRecords()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdQueueVO> getAllMovCmdQueueRecords() throws BusinessException {
		log.debug("== Inicia getAllMovCmdQueueRecords/MovementConfigBusinessBean ==");
		List<MovCmdQueueVO> resultsVO = null;
		List<MovCmdQueue> resultsPojo = null;
		try {
			resultsPojo = daoMovCmdQueue.findAll();
			if (resultsPojo != null)
				resultsVO = UtilsBusiness.convertList(resultsPojo, MovCmdQueueVO.class);
			
			return resultsVO;
		} catch (Throwable be) {
			log.error("== Error getAllMovCmdQueueRecords/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina getAllMovCmdQueueRecords/MovementConfigBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal#callStockService(co.com.directv.sdii.model.vo.MovCmdQueueVO, co.com.directv.sdii.model.vo.MovStatusConfigVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void callStockService(MovCmdQueueVO record) throws BusinessException {
		log.debug("== Inicia callStockService/MovementConfigBusinessBean ==");
		UtilsBusiness.assertNotNull(record, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			Boolean isMovement = record.getMovCmdConfig().getMoveResourceReason() != null && record.getMovCmdConfig().getMoveResourceReason().getReasonId() != null;
			Boolean isStatus = record.getMovCmdConfig().getUpdateStatusReaons() != null && record.getMovCmdConfig().getUpdateStatusReaons().getReasonId() != null;
		
			// Se informa el cambio de estado a IBS
			if (isStatus){
				resourceProvisioningBroker.updateResourceStatus(record);
				//inventoryServiceBroker.updateResourceStatus(record);
			}
			// Se informa el movimiento a IBS
			if (isMovement){
				inventoryServiceBroker.moveResourceToStockHandler(record);
			}
		}catch (Throwable be){
			log.error("== Error callStockService/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina callStockService/MovementConfigBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal#createMovCmdLogRecord(java.lang.Long, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createMovCmdLogRecord(Long queueRecordId, String message, User user) throws BusinessException {
		log.debug("== Inicia createMovCmdLogRecord/MovementConfigBusinessBean ==");
		MovCmdLog logRecord = new MovCmdLog();
		MovCmdQueue movCmdQueue = null;
		
		try {
			movCmdQueue = daoMovCmdQueue.findById(queueRecordId);
			if(movCmdQueue == null){
				throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			}
			logRecord.setCreationDate(UtilsBusiness.getCurrentDateByTimeZoneKey(user.getSdiiTimeZone().getTimeZoneKey(), new Date()));
			logRecord.setDescription(message);
			logRecord.setMovCmdQueue(movCmdQueue);
			daoMovCmdLog.save(logRecord);
		} catch (Throwable be){
			log.error("== Error createMovCmdLogRecord/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina createMovCmdLogRecord/MovementConfigBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal#createMovCmdLogRecordNoConfig(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createMovCmdLogRecordNoConfig(Long queueRecordId, User user) throws BusinessException {
		log.debug("== Inicia createMovCmdLogRecordNoConfig/MovementConfigBusinessBean ==");
		MovCmdLog logRecord = new MovCmdLog();
		MovCmdQueue movCmdQueue = null;
		
		try {
			movCmdQueue = daoMovCmdQueue.findById(queueRecordId);
			if(movCmdQueue == null){
				throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			}
			logRecord.setCreationDate(UtilsBusiness.getCurrentDateByTimeZoneKey(user.getSdiiTimeZone().getTimeZoneKey(), new Date()));
			logRecord.setDescription(ErrorBusinessMessages.MOV_CMD_CONFIG_NOT_EXIST.getMessage());
			logRecord.setMovCmdQueue(movCmdQueue);
			daoMovCmdLog.save(logRecord);
		} catch (Throwable be){
			log.error("== Error createMovCmdLogRecordNoConfig/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina createMovCmdLogRecordNoConfig/MovementConfigBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal#createMovCmdLogRecordConfigInactive(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createMovCmdLogRecordConfigInactive(Long queueRecordId, User user) throws BusinessException {
		log.debug("== Inicia createMovCmdLogRecordConfigInactive/MovementConfigBusinessBean ==");
		MovCmdLog logRecord = new MovCmdLog();
		MovCmdQueue movCmdQueue = null;
		
		try {
			movCmdQueue = daoMovCmdQueue.findById(queueRecordId);
			if (movCmdQueue == null){
				throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			}
			logRecord.setCreationDate(UtilsBusiness.getCurrentDateByTimeZoneKey(user.getSdiiTimeZone().getTimeZoneKey(), new Date()));
			logRecord.setDescription(ErrorBusinessMessages.MOV_CMD_CONFIG_INACTIVE.getMessage());
			logRecord.setMovCmdQueue(movCmdQueue);
			daoMovCmdLog.save(logRecord);
		} catch (Throwable be){
			log.error("== Error createMovCmdLogRecordConfigInactive/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina createMovCmdLogRecordConfigInactive/MovementConfigBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal#createMovCmdLogRecordSuccess(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createMovCmdLogRecordSuccess(Long queueRecordId, String typeProcess, User user) throws BusinessException {
		log.debug("== Inicia createMovCmdLogRecordSuccess/MovementConfigBusinessBean ==");
		MovCmdLog logRecord = new MovCmdLog();
		MovCmdQueue movCmdQueue = null;
		
		try {
			movCmdQueue = daoMovCmdQueue.findById(queueRecordId);
			if (movCmdQueue == null)
				throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			logRecord.setCreationDate(UtilsBusiness.getCurrentDateByTimeZoneKey(user.getSdiiTimeZone().getTimeZoneKey(), new Date()));
			logRecord.setDescription(typeProcess+": "+ErrorBusinessMessages.MOV_CMD_SUCCESS.getMessage());
			logRecord.setMovCmdQueue(movCmdQueue);
			daoMovCmdLog.save(logRecord);
		} catch (Throwable be){
			log.error("== Error createMovCmdLogRecordSuccess/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina createMovCmdLogRecordSuccess/MovementConfigBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal#createMovCmdLogRecordNoIbsStatus(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createMovCmdLogRecordNoIbsStatus(Long queueRecordId, User user) throws BusinessException {
		log.debug("== Inicia createMovCmdLogRecordNoIbsStatus/MovementConfigBusinessBean ==");
		MovCmdLog logRecord = new MovCmdLog();
		MovCmdQueue movCmdQueue = null;
		
		try {
			movCmdQueue = daoMovCmdQueue.findById(queueRecordId);
			if (movCmdQueue == null){
				throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			}
			logRecord.setCreationDate(UtilsBusiness.getCurrentDateByTimeZoneKey(user.getSdiiTimeZone().getTimeZoneKey(), new Date()));
			logRecord.setDescription(ErrorBusinessMessages.MOV_CMD_IBS_STATUS_NOT_EXIST.getMessage());
			logRecord.setMovCmdQueue(movCmdQueue);
			daoMovCmdLog.save(logRecord);
		} catch (Throwable be){
			log.error("== Error createMovCmdLogRecordNoIbsStatus/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina createMovCmdLogRecordNoIbsStatus/MovementConfigBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal#changeMovCmdQueueRecordStatusToError(co.com.directv.sdii.model.vo.MovCmdQueueVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void changeMovCmdQueueRecordStatusToError(MovCmdQueueVO record, User user) throws BusinessException {
		List<MovCmdStatus> movCmdStatusList = null;
		log.debug("== Inicia changeMovCmdQueueRecordStatusToError/MovementConfigBusinessBean ==");
		try {
			movCmdStatusList = daoMovCmdStatus.findByProperty(MovCmdStatusDAO.CMD_STATUS_CODE, CodesBusinessEntityEnum.MOV_CMD_STATUS_ERROR.getCodeEntity());
			UtilsBusiness.assertNotEmpty(movCmdStatusList, ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			MovCmdQueue queueRecord = this.daoMovCmdQueue.findById(record.getId());
			queueRecord.setMovCmdStatus(movCmdStatusList.get(0));
			daoMovCmdQueue.merge(queueRecord);
		} catch (Throwable be){
			log.error("== Error changeMovCmdQueueRecordStatusToError/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina changeMovCmdQueueRecordStatusToError/MovementConfigBusinessBean ==");
		}
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal#changeMovCmdQueueRecordStatusToNoConfig(co.com.directv.sdii.model.vo.MovCmdQueueVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void changeMovCmdQueueRecordStatusToNoConfig(MovCmdQueueVO record, User user) throws BusinessException {
		List<MovCmdStatus> movCmdStatusList = null;
		log.debug("== Inicia changeMovCmdQueueRecordStatusToNoConfig/MovementConfigBusinessBean ==");
		try {
			movCmdStatusList = daoMovCmdStatus.findByProperty(MovCmdStatusDAO.CMD_STATUS_CODE, CodesBusinessEntityEnum.MOV_CMD_STATUS_NO_CONFIG.getCodeEntity());
			UtilsBusiness.assertNotEmpty(movCmdStatusList, ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			MovCmdQueue queueRecord = this.daoMovCmdQueue.findById(record.getId());
			queueRecord.setMovCmdStatus(movCmdStatusList.get(0));
			queueRecord.setExecuteDate(UtilsBusiness.getCurrentDateByTimeZoneKey(user.getSdiiTimeZone().getTimeZoneKey(), new Date()));
			daoMovCmdQueue.merge(queueRecord);
		} catch (Throwable be){
			log.error("== Error changeMovCmdQueueRecordStatusToNoConfig/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina changeMovCmdQueueRecordStatusToNoConfig/MovementConfigBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal#changeMovCmdQueueRecordStatusToNoIbsElementStatus(co.com.directv.sdii.model.vo.MovCmdQueueVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void changeMovCmdQueueRecordStatusToNoIbsElementStatus(MovCmdQueueVO record, User user) throws BusinessException {
		List<MovCmdStatus> movCmdStatusList = null;
		log.debug("== Inicia changeMovCmdQueueRecordStatusToNoConfig/MovementConfigBusinessBean ==");
		try {
			movCmdStatusList = daoMovCmdStatus.findByProperty(MovCmdStatusDAO.CMD_STATUS_CODE, CodesBusinessEntityEnum.MOV_CMD_STATUS_NO_IBS_STATUS.getCodeEntity());
			UtilsBusiness.assertNotEmpty(movCmdStatusList, ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			MovCmdQueue queueRecord = this.daoMovCmdQueue.findById(record.getId());
			queueRecord.setMovCmdStatus(movCmdStatusList.get(0));
			queueRecord.setExecuteDate(UtilsBusiness.getCurrentDateByTimeZoneKey(user.getSdiiTimeZone().getTimeZoneKey(), new Date()));
			daoMovCmdQueue.merge(queueRecord);
		} catch (Throwable be){
			log.error("== Error changeMovCmdQueueRecordStatusToNoConfig/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina changeMovCmdQueueRecordStatusToNoConfig/MovementConfigBusinessBean ==");
		}
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal#changeMovCmdQueueRecordStatusToProcessing(co.com.directv.sdii.model.vo.MovCmdQueueVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void changeMovCmdQueueRecordStatusToProcessing(MovCmdQueueVO record, User user)
			throws BusinessException {
		List<MovCmdStatus> movCmdStatusList = null;
		log.debug("== Inicia changeMovCmdQueueRecordStatusToProcessing/MovementConfigBusinessBean ==");
		try {
			movCmdStatusList = daoMovCmdStatus.findByProperty(MovCmdStatusDAO.CMD_STATUS_CODE, CodesBusinessEntityEnum.MOV_CMD_STATUS_PROCESSING.getCodeEntity());
			UtilsBusiness.assertNotEmpty(movCmdStatusList, ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			MovCmdQueue queueRecord = this.daoMovCmdQueue.findById(record.getId());
			queueRecord.setMovCmdStatus(movCmdStatusList.get(0));
			queueRecord.setExecuteDate(UtilsBusiness.getCurrentDateByTimeZoneKey(user.getSdiiTimeZone().getTimeZoneKey(), new Date()));
			daoMovCmdQueue.merge(queueRecord);
		} catch (Throwable be){
			log.error("== Error changeMovCmdQueueRecordStatusToProcessing/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina changeMovCmdQueueRecordStatusToProcessing/MovementConfigBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal#changeMovCmdQueueRecordStatusToProcessed(co.com.directv.sdii.model.vo.MovCmdQueueVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void changeMovCmdQueueRecordStatusToProcessed(MovCmdQueueVO record, User user)
			throws BusinessException {
		List<MovCmdStatus> movCmdStatusList = null;
		log.debug("== Inicia changeMovCmdQueueRecordStatusToProcessed/MovementConfigBusinessBean ==");
		try {
			movCmdStatusList = daoMovCmdStatus.findByProperty(MovCmdStatusDAO.CMD_STATUS_CODE, CodesBusinessEntityEnum.MOV_CMD_STATUS_PROCESSED.getCodeEntity());
			UtilsBusiness.assertNotEmpty(movCmdStatusList, ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			MovCmdQueue queueRecord = this.daoMovCmdQueue.findById(record.getId());
			queueRecord.setMovCmdStatus(movCmdStatusList.get(0));
			daoMovCmdQueue.merge(queueRecord);
		} catch (Throwable be){
			log.error("== Error changeMovCmdQueueRecordStatusToProcessed/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina changeMovCmdQueueRecordStatusToProcessed/MovementConfigBusinessBean ==");
		}
	}


	@Override
	public void createMovementCommandQueue(MovementElementDTO dto)
		throws BusinessException {
		log.debug("== Inicia createMovementCommandQueue/MovementConfigBusinessBean ==");
		MovCmdQueue queueRecord = new MovCmdQueue();
		try {
			queueRecord = UtilsBusiness.copyObject(MovCmdQueue.class, dto.getMovCmdQueueVO());
			queueRecord.setTypeComunication(CodesBusinessEntityEnum.TYPE_COMUNICATION_HSP_TO_IBS.getCodeEntity());
			queueRecord.setIsManagment(CodesBusinessEntityEnum.COMUNICATION_HSP_IBS_IS_NOT_COMMENT_ERROR.getCodeEntity());
			queueRecord.setCreationDate(UtilsBusiness.getCurrentDateByTimeZoneKey(dto.getUser().getSdiiTimeZone().getTimeZoneKey(), new Date()));
			queueRecord.setCountry(dto.getUser().getCountry());
			queueRecord.setSerializedLinked(queueRecord.getSerialized().getSerialized());
			daoMovCmdQueue.save(queueRecord);
		} catch (Throwable be){
			log.error("== Error createMovementCommandQueue/MovementConfigBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina createMovementCommandQueue/MovementConfigBusinessBean ==");
		}    	
	}
}
