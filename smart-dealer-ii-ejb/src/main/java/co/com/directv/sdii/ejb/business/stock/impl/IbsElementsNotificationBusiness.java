package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.locator.JMSLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.broker.IBSSPRMSupportAndReadinessBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.InventoryServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.ResourceProvisioningBrokerLocal;
import co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.IbsElementsNotificationBusinessLocal;
import co.com.directv.sdii.ejb.business.stock.IbsElementsNotificationBusinessRemote;
import co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.jms.common.util.DistributedQueueMessage;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;
import co.com.directv.sdii.persistence.dao.stock.MovCmdQueueDAOLocal;

/**
 * 
 * Implementa la logica para realizar el llamado a IBS para actualizar los elementos cuando
 * se realizan movimientos en HSP a partir de la cola que se genera luego de cada movimiento  
 * 
 * Fecha de Creación: Oct 21, 2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="IbsElementsNotificationBusiness")
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED) 
@Local({IbsElementsNotificationBusinessLocal.class})
@Remote({IbsElementsNotificationBusinessRemote.class})
public class IbsElementsNotificationBusiness implements IbsElementsNotificationBusinessLocal,IbsElementsNotificationBusinessRemote {
	
	private final static Logger log = UtilsBusiness.getLog4J(IbsElementsNotificationBusiness.class);
	
	private final static String MOVEMENT_MESSAGE = ApplicationTextEnum.MOVEMENT.getApplicationTextValue()+": ";
	
	private final static String UPDATE_MESSAGE = ApplicationTextEnum.STATUS_UPDATE.getApplicationTextValue()+": ";
	
	@EJB(name = "ResourceProvisioningBroker", beanInterface = ResourceProvisioningBrokerLocal.class)
	private ResourceProvisioningBrokerLocal resourceProvisioningBroker;
		
	private final static String RECOVERY_RESOURCE =ApplicationTextEnum.RECOVER_SERIALIZED_ITEM.getApplicationTextValue()+": ";
	
	private final static boolean RECOVERY_RESOURCE_SERIALIZED = false;
	
	@EJB(name="MovementConfigBusinessBeanLocal", beanInterface=MovementConfigBusinessBeanLocal.class)
	private MovementConfigBusinessBeanLocal businessMovementConfig;
	
	@EJB(name="InventoryServiceBrokerLocal", beanInterface=InventoryServiceBrokerLocal.class)
	private InventoryServiceBrokerLocal inventoryServiceBroker;
	
	@EJB(name="IBSSPRMSupportAndReadinessBrokerLocal", beanInterface=IBSSPRMSupportAndReadinessBrokerLocal.class)
	private IBSSPRMSupportAndReadinessBrokerLocal ibsSprmSupportAndReadinessBroker;
	
	@EJB(name="SecurityBusinessBeanLocal", beanInterface=SecurityBusinessBeanLocal.class)
	private SecurityBusinessBeanLocal businessSecurity;

	
	@EJB(name="MovCmdQueueDAOLocal", beanInterface=MovCmdQueueDAOLocal.class)
	private MovCmdQueueDAOLocal daoMovCmdQueue;	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void sendStatusCmdToIBS(Long countryId) throws BusinessException {
		
		log.debug("Inicia sendStatusCmdToIBS/IbsElementsNotificationBusiness");
		
		List<MovCmdQueueVO> pendientes = null;

		try {
			pendientes = businessMovementConfig.getPendingMovCmdQueueRecordsByPage(countryId);

			//counsultar usuario para calcular la fecha del proceso
			User user = UtilsBusiness.copyObject(User.class, this.businessSecurity.getIBSAdminUserByCountryId(countryId));
			
			// Si hay registros pendientes por informar
			if (pendientes != null && !pendientes.isEmpty()){
				for (MovCmdQueueVO record : pendientes){
					if (record.getMovCmdConfig() != null && record.getMovCmdConfig().getId().longValue() > 0){
						try{
							if(record.getMovCmdConfig().getStatus().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())){
								boolean isMoveResourceIbsStatus = record.getMovCmdConfig().getMoveResourceReason() != null 	&& record.getMovCmdConfig().getMoveResourceReason().getId() > 0;
								boolean isUpdateIbsStatus = record.getMovCmdConfig().getUpdateStatusReaons() != null  && record.getMovCmdConfig().getUpdateStatusReaons().getId() > 0;
								boolean isRecovery = record.getMovCmdConfig().getIbsElementStatusRecovery() != null  && record.getMovCmdConfig().getIbsElementStatusRecovery().getId() > 0;													
													
								if(isMoveResourceIbsStatus || isUpdateIbsStatus || isRecovery){
									this.callServiceInventory(record, user);
								} else {
									log.warn("El registro que se desea enviar a IBS no tiene un estado IBS para ser reportado ==> RecordId = " + record.getId());
									businessMovementConfig.createMovCmdLogRecordNoIbsStatus(record.getId(), user);
									businessMovementConfig.changeMovCmdQueueRecordStatusToNoIbsElementStatus(record, user);
								}
							}else{
								log.warn("El registro que se desea enviar a IBS tiene una configuración inactiva ==> RecordId = " + record.getId());
								businessMovementConfig.createMovCmdLogRecordConfigInactive(record.getId(), user);
								businessMovementConfig.changeMovCmdQueueRecordStatusToNoConfig(record, user);
							}
						} catch (Throwable e) {
							log.warn("Error al procesar el registro ==> RecordId = " + record.getId());
							businessMovementConfig.createMovCmdLogRecord(record.getId(), e.getMessage(), user);
							businessMovementConfig.changeMovCmdQueueRecordStatusToError(record, user);
						}						
					} else {
						if(record.getMovCmdConfig() == null || record.getMovCmdConfig().getId().longValue() <= 0){
							log.warn("El registro que se desea enviar a IBS no tiene una configuración asociada ==> RecordId = " + record.getId());
							businessMovementConfig.createMovCmdLogRecordNoConfig(record.getId(), user);
							businessMovementConfig.changeMovCmdQueueRecordStatusToNoConfig(record, user);
						}
					}
				}
			}
		} catch (BusinessException be){
			log.error("Error Inicia sendStatusCmdToIBS/IbsElementsNotificationBusiness",be);
			throw new BusinessException(be.getMessageCode(), be.getMessage(), be);
		} finally{
			log.debug("Finaliza sendStatusCmdToIBS/IbsElementsNotificationBusiness");
		}
		
	}
	
	/**
	 * Req-0098 - Paralelismo de Inventarios
	 * Método encargado de obtener los movimientos de inventarios de la tabla MOVEMENT_COMAND_QUEUES a informar a IBS
	 * y enviar a la cola interna MovCmdQueue.
	 * @param countryId
	 * @throws BusinessException
	 * @ialessan
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void sendToMovCmdQueue(Long countryId) throws BusinessException {
		
		log.debug("Inicia sendStatusCmdToIBS/IbsElementsNotificationBusiness");
		
		List<MovCmdQueueVO> pendientes = null;

		//counsultar usuario para calcular la fecha del proceso
		User user = UtilsBusiness.copyObject(User.class, this.businessSecurity.getIBSAdminUserByCountryId(countryId));
		
		try {
			    pendientes = businessMovementConfig.getPendingMovCmdQueueRecordsByPageParallel(countryId);

			    //se cambia de estado los registros en la tabla MOVEMENT_COMAND_QUEUES a MOV_CMD_STATUS_PROCESSING para que no los tome otro proceso.
			    for(MovCmdQueueVO movCmdQueueVO:pendientes){
			    	businessMovementConfig.changeMovCmdQueueRecordStatusToProcessing(movCmdQueueVO, user);	
			    }
												
				int movCmdCount = 0;
				// Si hay registros pendientes por informar
				if (pendientes != null && !pendientes.isEmpty()){
					for (MovCmdQueueVO record : pendientes){
						if (record.getMovCmdConfig() != null && record.getMovCmdConfig().getId().longValue() > 0){
							try{
								if(record.getMovCmdConfig().getStatus().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())){
									boolean isMoveResourceIbsStatus = record.getMovCmdConfig().getMoveResourceReason() != null 	&& record.getMovCmdConfig().getMoveResourceReason().getId() > 0;
									boolean isUpdateIbsStatus = record.getMovCmdConfig().getUpdateStatusReaons() != null && record.getMovCmdConfig().getUpdateStatusReaons().getId() > 0;
									boolean isRecovery = record.getMovCmdConfig().getIbsElementStatusRecovery() != null && record.getMovCmdConfig().getIbsElementStatusRecovery().getId() > 0;													
														
									if(isMoveResourceIbsStatus || isUpdateIbsStatus || isRecovery){
										
										 DistributedQueueMessage distributedQueueMessage = JMSLocator.getInstance().getQueueMovCmdQueue();
										 distributedQueueMessage.sendMessage(record);
										 movCmdCount++;		
										 if (log.isDebugEnabled()) log.debug("Se envió a la cola MovCmdQueue el mensaje del registro "+record.getId()+" de la tabla MOVEMENT_COMAND_QUEUES");										 
										
									} else {
										log.warn("El registro que se desea enviar a IBS no tiene un estado IBS para ser reportado ==> RecordId = " + record.getId());
										businessMovementConfig.createMovCmdLogRecordNoIbsStatus(record.getId(), user);
										businessMovementConfig.changeMovCmdQueueRecordStatusToNoIbsElementStatus(record, user);									
									}
								}else{
									log.warn("El registro que se desea enviar a IBS tiene una configuración inactiva ==> RecordId = " + record.getId());
									businessMovementConfig.createMovCmdLogRecordConfigInactive(record.getId(), user);
									businessMovementConfig.changeMovCmdQueueRecordStatusToNoConfig(record, user);
								}
							} catch (Throwable e) {
								log.warn("Error al procesar el registro ==> RecordId = " + record.getId());
								String message =  (e.getMessage()==null) ? "sendToMovCmdQueue Error en el procesamiento del registro id:"+ record.getId() : e.getMessage();
								businessMovementConfig.createMovCmdLogRecord(record.getId(), message, user);
								businessMovementConfig.changeMovCmdQueueRecordStatusToError(record, user);
							}						
						} else {
							if(record.getMovCmdConfig() == null || record.getMovCmdConfig().getId().longValue() <= 0){
								log.warn("El registro que se desea enviar a IBS no tiene una configuración asociada ==> RecordId = " + record.getId());
								businessMovementConfig.createMovCmdLogRecordNoConfig(record.getId(), user);
								businessMovementConfig.changeMovCmdQueueRecordStatusToNoConfig(record, user);
							}
						}
					}
				}

		log.info("Se enviaron a la cola MovCmdQueue "+movCmdCount+" mensajes de movimiento de inventario ");

	} catch(Throwable be){
    	log.error("== Error al tratar de ejecutar la operación sendToMovCmdQueue/IbsElementsNotificationBusiness ==", be);    	
		throw new BusinessException(be.getMessage(), be);		
	}finally{
			log.debug("Finaliza sendStatusCmdToMovCmdQueue/IbsElementsNotificationBusiness");
		}
		
	}	
	
	
	/**
	 * Método encargado de evaluar los servicios de ibs que se deben invocar de acuerdo
	 * a la configuración dentro de hsp para la notificacion de lo elementos a IBS
	 * @param record
	 * @throws BusinessException
	 * @waguilera
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private void callServiceInventory(MovCmdQueueVO record, User user) throws BusinessException{
		log.debug("Inicia callServiceInventory/IbsElementsNotificationBusiness");
		try{
			
			Boolean isMovement = record.getMovCmdConfig().getMoveResourceReason()       != null && record.getMovCmdConfig().getMoveResourceReason().getReasonId() != null;
			Boolean isStatus   = record.getMovCmdConfig().getUpdateStatusReaons()       != null && record.getMovCmdConfig().getUpdateStatusReaons().getReasonId() != null;
			Boolean isRecovery = record.getMovCmdConfig().getIbsElementStatusRecovery() != null && record.getMovCmdConfig().getIbsElementStatusRecovery().getReasonId() != null;
			
			businessMovementConfig.changeMovCmdQueueRecordStatusToProcessing(record, user);
			boolean finishProcessCorrect = true;
			// Se informa el cambio de estado a IBS
			if (isStatus){
				finishProcessCorrect = this.callUpdateStatus(record, user);
			}
		
			// Se informa el movimiento a IBS
			if (isMovement){
				if(finishProcessCorrect){
					finishProcessCorrect = this.callMoveToStockHandler(record, user);
				}else{
					this.callMoveToStockHandler(record, user);
				}
			}
			
			// Se informa el movimiento de recuperaciones
			if (isRecovery){
				if(finishProcessCorrect){
					finishProcessCorrect = this.callRecoveryResource(record, user);
				}else{
					this.callRecoveryResource(record, user);
				}
			}
			
			if(finishProcessCorrect){
				businessMovementConfig.changeMovCmdQueueRecordStatusToProcessed(record, user);
			}else{
				businessMovementConfig.changeMovCmdQueueRecordStatusToError(record, user);
			}
			
		} catch (BusinessException be){
			log.error("Error Inicia callServiceInventory/IbsElementsNotificationBusiness",be);
			throw new BusinessException(be.getMessageCode(), be.getMessage(), be);
		} finally{
			log.debug("Finaliza callServiceInventory/IbsElementsNotificationBusiness");
		}
		

	}
	
	/**
	 * Método encargado de evaluar los servicios de ibs que se deben invocar de acuerdo
	 * a la configuración dentro de hsp para la notificacion de lo elementos a IBS
	 * @param record
	 * @throws BusinessException
	 * @waguilera
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void callServiceInventoryParallel(MovCmdQueueVO record, User user) throws BusinessException{
		log.debug("Inicia callServiceInventoryParallel/IbsElementsNotificationBusiness");
		try{
			
			Boolean isMovement = record.getMovCmdConfig().getMoveResourceReason()       != null && record.getMovCmdConfig().getMoveResourceReason().getReasonId() != null;
			Boolean isStatus   = record.getMovCmdConfig().getUpdateStatusReaons()       != null && record.getMovCmdConfig().getUpdateStatusReaons().getReasonId() != null;
			Boolean isRecovery = record.getMovCmdConfig().getIbsElementStatusRecovery() != null && record.getMovCmdConfig().getIbsElementStatusRecovery().getReasonId() != null;
			
			//ya se cambió el estado antes de encolar
			//businessMovementConfig.changeMovCmdQueueRecordStatusToProcessing(record, user);
			
			boolean finishProcessCorrect = true;
			// Se informa el cambio de estado a IBS
			if (isStatus){
				finishProcessCorrect = this.callUpdateStatus(record, user);
			}
		
			// Se informa el movimiento a IBS
			if (isMovement){
				if(finishProcessCorrect){
					finishProcessCorrect = this.callMoveToStockHandler(record, user);
				}else{
					this.callMoveToStockHandler(record, user);
				}
			}
			
			// Se informa el movimiento de recuperaciones
			if (isRecovery){
				if(finishProcessCorrect){
					finishProcessCorrect = this.callRecoveryResource(record, user);
				}else{
					this.callRecoveryResource(record, user);
				}
			}
			
			if(finishProcessCorrect){
				if (log.isDebugEnabled()) log.debug("callServiceInventoryParallel/IbsElementsNotificationBusiness cambiara estado de serial: "+record.getSerialized().getElementId()+" a procesado.");
				businessMovementConfig.changeMovCmdQueueRecordStatusToProcessed(record, user);
			}else{
				if (log.isDebugEnabled()) log.debug("callServiceInventoryParallel/IbsElementsNotificationBusiness cambiara estado de serial: "+record.getSerialized().getElementId()+" a error.");
				businessMovementConfig.changeMovCmdQueueRecordStatusToError(record, user);
			}
			
		} catch (BusinessException be){
			log.error("Error callServiceInventoryParallel/IbsElementsNotificationBusiness",be);
			if (log.isDebugEnabled()) log.debug("Error callServiceInventoryParallel/IbsElementsNotificationBusiness  serial: "+record.getSerialized().getElementId(),be);
			throw new BusinessException(be.getMessageCode(), be.getMessage(), be);
		} finally{
			if (log.isDebugEnabled()) log.debug("Finaliza callServiceInventoryParallel/IbsElementsNotificationBusiness - serial: "+record.getSerialized().getElementId());
		}
		

	}	
	
	
	
	
	/**
	 * Método encargado de invocar el servicio de ibs encargado de 
	 * realizar la actualización de estado al elemento
	 * o elementos enviados en el servicio
	 * @param record
	 * @throws BusinessException
	 * @autor waguilera
	 */
	private boolean callUpdateStatus(MovCmdQueueVO record, User user) throws BusinessException{
		log.debug("Inicia callUpdateStatus/IbsElementsNotificationBusiness");
		try{
			resourceProvisioningBroker.updateResourceStatus(record);
			//inventoryServiceBroker.updateResourceStatus(record);
			businessMovementConfig.createMovCmdLogRecordSuccess(record.getId(), UPDATE_MESSAGE, user);
			return true;
		} catch (BusinessException be){
			businessMovementConfig.createMovCmdLogRecord(record.getId(), UPDATE_MESSAGE+ ": " +be.getMessage(), user);
			return false;
		} finally{
			log.debug("Finaliza callUpdateStatus/IbsElementsNotificationBusiness");
		}
	}
	
	/**
	 * Método encargado de invocar el servicio de ibs encargado de 
	 * realizar el movimiento de cambio de stock handler al elemento
	 * o elementos enviados en el servicio
	 * @param record
	 * @throws BusinessException
	 * @waguilera
	 */
	private boolean callMoveToStockHandler(MovCmdQueueVO record, User user) throws BusinessException{
		log.debug("Inicia callMoveToStockHandler/IbsElementsNotificationBusiness");
		try{
			ibsSprmSupportAndReadinessBroker.moveResourceToStockHandler(record);
			businessMovementConfig.createMovCmdLogRecordSuccess(record.getId(), MOVEMENT_MESSAGE, user);
			return true;
		} catch (BusinessException be){
			businessMovementConfig.createMovCmdLogRecord(record.getId(), MOVEMENT_MESSAGE + ": " + be.getMessage(), user);
			return false;
		} finally{
			log.debug("Finaliza callMoveToStockHandler/IbsElementsNotificationBusiness");
		}
	}
	
	/**
	 * Metodo: Permite reportar a ibs los elementos al atender una wo
	 * @param record 
	 * @param user 
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	private boolean callRecoveryResource(MovCmdQueueVO record, User user) throws BusinessException{
		log.debug("Inicia callRecoveryResource/IbsElementsNotificationBusiness");
		try{
			ibsSprmSupportAndReadinessBroker.receiveReturnedPhysicalResource(record,RECOVERY_RESOURCE_SERIALIZED);
			businessMovementConfig.createMovCmdLogRecordSuccess(record.getId(), RECOVERY_RESOURCE, user);
			return true;
		} catch (BusinessException be){
			businessMovementConfig.createMovCmdLogRecord(record.getId(), RECOVERY_RESOURCE + ": " + be.getMessage(), user);
			return false;
		} finally{
			log.debug("Finaliza callRecoveryResource/IbsElementsNotificationBusiness");
		}
	}

}
