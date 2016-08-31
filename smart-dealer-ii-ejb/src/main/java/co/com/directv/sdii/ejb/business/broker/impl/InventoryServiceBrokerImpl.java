/**
 * Creado 11/08/2010 11:40:16
 */
package co.com.directv.sdii.ejb.business.broker.impl;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.ServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.broker.IServiceBroker;
import co.com.directv.sdii.ejb.business.broker.InventoryServiceBrokerLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;

import com.directvla.contract.crm.inventory.v1_0.MoveResourceToStockHandler;
import com.directvla.contract.crm.inventory.v1_0.MoveResourceToStockHandlerException;
import com.directvla.contract.crm.inventory.v1_0.MoveResourceToStockHandlerException_Exception;
import com.directvla.contract.crm.inventory.v1_0.MoveResourceToStockHandlerRequest;
import com.directvla.contract.crm.inventory.v1_0.MoveResourceToStockHandlerResponse;
import com.directvla.contract.crm.inventory.v1_0.PtInventory;
import com.directvla.contract.crm.inventory.v1_0.UpdateResourceStatus;
import com.directvla.contract.crm.inventory.v1_0.UpdateResourceStatusException;
import com.directvla.contract.crm.inventory.v1_0.UpdateResourceStatusException_Exception;
import com.directvla.contract.crm.inventory.v1_0.UpdateResourceStatusRequest;
import com.directvla.schema.businessdomain.common.inventory.v1_0.FunctionOrProcessProvider;
import com.directvla.schema.businessdomain.common.inventory.v1_0.MovementCategoryType;
import com.directvla.schema.businessdomain.common.inventory.v1_0.PhysicalResource;
import com.directvla.schema.util.commondatatypes.inventory.v1_0.RequestMetadataType;


/**
 * Implementación de Broker de servicios asociados con el módulo de inventarios
 * 
 * Fecha de Creación: 11/08/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.broker.InventoryServiceBrokerLocal
 */
@Stateless(name="InventoryServiceBrokerLocal",mappedName="ejb/InventoryServiceBrokerLocal")
public class InventoryServiceBrokerImpl extends BusinessBase implements	InventoryServiceBrokerLocal, IServiceBroker {

	private final static Logger log = UtilsBusiness.getLog4J(InventoryServiceBrokerImpl.class);
	
	/*
	 * 	(non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.InventoryServiceBrokerLocal#moveResourceToStockHandler(co.com.directv.sdii.model.vo.MovCmdQueueVO)
	 */
	@Override
	public boolean moveResourceToStockHandler(MovCmdQueueVO record) throws BusinessException {
		log.debug("== Inicia la operación moveResourceToStockHandler/InventoryServiceBrokerImpl ==");
		boolean success = true;
		try {
			//log.debug("Se invocará la operación moveResourceToStockHandler con los siguientes parámetros: " + serElMovDto);
			ServiceLocator servicelocator = ServiceLocator.getInstance();
			PtInventory service = servicelocator.getInventoryService();
			MoveResourceToStockHandlerRequest moveSerElementRequest = this.createMoveResourceToStockHandlerRequest(record);
			
			//Si el request viene nulo, no se notifica el movimiento
			if(moveSerElementRequest == null){
				return success;
			}
			generateMoveResourceToStockHandlerLog(moveSerElementRequest);
			MoveResourceToStockHandlerResponse moveSerElementResponse = service.moveResourceToStockHandler(moveSerElementRequest);
			com.directvla.schema.util.commondatatypes.inventory.v1_0.ResponseMetadataType responseMetaData = moveSerElementResponse.getResponseMetadata();
			if(responseMetaData.getBoxUsage() == null || responseMetaData.getRequestID() == null || responseMetaData.getTimeStamp() == null){
				success = false;
			}			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación moveResourceToStockHandler/InventoryServiceBrokerImpl ==");
			throw this.manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación moveResourceToStockHandler/InventoryServiceBrokerImpl ==");
		}
		return success;
		
	}
	
	/**
	 * 
	 * Metodo: Genera log de la operacion MoveResourceToStockHandler
	 * @param moveSerElementRequest Request que se envia a ibs
	 * @author jnova
	 */
	private void generateMoveResourceToStockHandlerLog(MoveResourceToStockHandlerRequest moveSerElementRequest){
		StringBuilder sb = new StringBuilder();
		sb.append("MoveResourceToStockHandlerRequest: ");
		if( moveSerElementRequest.getRequestMetadata() != null ){
			sb.append("RequestMetadata: ");
			if( moveSerElementRequest.getRequestMetadata().getRequestID() != null ){
				sb.append("requestId: "+moveSerElementRequest.getRequestMetadata().getRequestID()+" ");
			}
			if( moveSerElementRequest.getRequestMetadata().getSourceID() != null ){
				sb.append("sourceId: "+moveSerElementRequest.getRequestMetadata().getSourceID()+" ");
			}
		}
		if( moveSerElementRequest.getMoveResource() != null ){
			sb.append("MoveResource: ");
			if( moveSerElementRequest.getMoveResource().getStockHandlerFrom() != null ){
				sb.append("StockHandlerFrom.PartyRoleId: "+moveSerElementRequest.getMoveResource().getStockHandlerFrom().getPartyRoleId()+" ");
			}
			if( moveSerElementRequest.getMoveResource().getStockHandlerTo() != null ){
				sb.append("StockHandlerTo.PartyRoleId: "+moveSerElementRequest.getMoveResource().getStockHandlerTo().getPartyRoleId()+" ");
			}
			if( moveSerElementRequest.getMoveResource().getPhysicalResource() != null ){
				sb.append("PhysicalResource: "+moveSerElementRequest.getMoveResource().getPhysicalResource().getSerialNumber()+" ");
			}
			if( moveSerElementRequest.getMoveResource().getPhysicalResourceAssociated() != null ){
				sb.append("PhysicalResourceAssociated: "+moveSerElementRequest.getMoveResource().getPhysicalResourceAssociated().getSerialNumber()+" ");
			}
		}
		log.info(sb.toString());
	}

	
	
	/**
	 * 
	 * Metodo: Permite armar el objeto para invocar el WS en IBS para notificar movimientos entre bodegas de elementos
	 * serializados
	 * @param record
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private MoveResourceToStockHandlerRequest createMoveResourceToStockHandlerRequest(MovCmdQueueVO record) throws BusinessException{
		log.info("== Inicia createMoveResourceToStockHandlerRequest/InventoryServiceBrokerImpl ==");
		MoveResourceToStockHandlerRequest request = new MoveResourceToStockHandlerRequest();
		try{
			RequestMetadataType resquestMetaData = new RequestMetadataType();
			resquestMetaData.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
			resquestMetaData.setRequestID(record.getMovCmdConfig().getCountry().getCountryCode());
			resquestMetaData.setSourceID(record.getMovCmdConfig().getCountry().getCountryCode());
			
			MoveResourceToStockHandler moveResourceToStockHandler = new MoveResourceToStockHandler();
			//Creo y asigno la bodega de origen
			if( record.getSourceWarehouse() != null) {
				Long sourceWhId = record.getSourceWarehouse().getId();
				Long dealerOwnerSourceCode = 0L;
				if(sourceWhId != null && sourceWhId.longValue() > 0){
					FunctionOrProcessProvider sourceWarehouse = new FunctionOrProcessProvider();
					dealerOwnerSourceCode = getWhDealerOwnerCode(record.getSourceWarehouse());
					sourceWarehouse.setPartyRoleId("" + dealerOwnerSourceCode);
					moveResourceToStockHandler.setStockHandlerFrom(sourceWarehouse);
				}
			}
			
			//Creo y asigno la bodega de destino
			if( record.getTargetWarehouse() != null){
				Long targetWhId = record.getTargetWarehouse().getId();
				Long dealerOwnerTargetCode = 0L;
				if(targetWhId != null && targetWhId.longValue() > 0){
					FunctionOrProcessProvider targetWarehouse = new FunctionOrProcessProvider();
					dealerOwnerTargetCode = getWhDealerOwnerCode(record.getTargetWarehouse());
					targetWarehouse.setPartyRoleId( "" + dealerOwnerTargetCode);
					moveResourceToStockHandler.setStockHandlerTo(targetWarehouse);
				}
			}
			
			//se asigna el elemento
			if( record.getSerialized() != null ){				
				PhysicalResource physicalResource = new PhysicalResource();
				physicalResource.setSerialNumber( record.getSerialized().getSerialCode() != null ? record.getSerialized().getSerialCode() : "" );
				moveResourceToStockHandler.setPhysicalResource( physicalResource );
				
				if( record.getSerialized().getSerialized()!= null && record.getSerialized().getSerialized().getSerialCode() != null ){
					PhysicalResource physicalResourceAssociated = new PhysicalResource();
					physicalResourceAssociated.setSerialNumber( record.getSerialized().getSerialized().getSerialCode() );
					moveResourceToStockHandler.setPhysicalResourceAssociated(physicalResourceAssociated);
				}
			}
			
			
			//Se asigna el tipo de movimiento
			MovementCategoryType movementType = new MovementCategoryType();
			movementType.setMovement(record.getMovCmdConfig().getMoveResourceReason().getReasonId().toString());
			moveResourceToStockHandler.setMovementCategory( movementType );
			
			//Asigno los valores al request
			request.setRequestMetadata(resquestMetaData);
			request.setMoveResource(moveResourceToStockHandler);
			
			return request;
		}catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createMoveResourceToStockHandlerRequest/InventoryServiceBrokerImpl ==");
        	throw this.manageException(ex);
        } finally {
            log.info("== Termina createMoveResourceToStockHandlerRequest/InventoryServiceBrokerImpl ==");
        }	
	}
	
	

	
	private Long getWhDealerOwnerCode(Warehouse wh) throws BusinessException {
		UtilsBusiness.assertNotNull(wh, ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage() );
		
		if(wh.getDealerId() != null){
			return wh.getDealerId().getDealerCode();
		}
		
		if(wh.getCrewId() != null){
			return wh.getCrewId().getDealer().getDealerCode();
		}
		
		if(wh.getCustomerId() != null){
			return Long.parseLong(wh.getCustomerId().getCustomerCode());
		}
		
		return null;
	}

	
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.InventoryServiceBrokerLocal#updateResourceStatus(co.com.directv.sdii.model.vo.MovCmdQueueVO)
	 */
	@Override
	public boolean updateResourceStatus(MovCmdQueueVO record) throws BusinessException {
		log.info("== Inicia updateResourceStatus/InventoryServiceBrokerImpl ==");
		try{
			boolean success = true;
			ServiceLocator servicelocator = ServiceLocator.getInstance();
			PtInventory service = servicelocator.getInventoryService();
			UpdateResourceStatusRequest request = new UpdateResourceStatusRequest();
			
			RequestMetadataType resquestMetaData = new RequestMetadataType();
			resquestMetaData.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
			resquestMetaData.setRequestID(record.getMovCmdConfig().getCountry().getCountryCode());
			resquestMetaData.setSourceID(record.getMovCmdConfig().getCountry().getCountryCode());
			
			request.setRequestMetadata( resquestMetaData );
			request.setUpdateResourceStatus( this.createUpdateResourceStatus(record) );
			generateUpdateResourceStatusRequestLog(request);
			service.updateResourceStatus( request );
			return success;
		}catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operaci�n updateResourceStatus/InventoryServiceBrokerImpl ==");
        	throw this.manageServiceException(ex);
        } finally {
            log.info("== Termina updateResourceStatus/InventoryServiceBrokerImpl ==");
        }
	}
	
	/**
	 * 
	 * Metodo: Genera el log del request de la operacion UpdateResourceStatus
	 * @param request UpdateResourceStatusRequest
	 * @author jnova
	 */
	private void generateUpdateResourceStatusRequestLog(UpdateResourceStatusRequest request){
		StringBuilder sb = new StringBuilder();
		sb.append("UpdateResourceStatusRequest: ");
		if( request.getRequestMetadata() != null ){
			sb.append("RequestMetadata: ");
			if( request.getRequestMetadata().getRequestID() != null ){
				sb.append("requestId: "+request.getRequestMetadata().getRequestID()+" ");
			}
			if( request.getRequestMetadata().getSourceID() != null ){
				sb.append("sourceId: "+request.getRequestMetadata().getSourceID()+" ");
			}
		}
		if( request.getUpdateResourceStatus() != null ){
			sb.append("UpdateResourceStatus: ");
			if( request.getUpdateResourceStatus().getStockHandler() != null ){
				sb.append("StockHandler: "+request.getUpdateResourceStatus().getStockHandler().getPartyRoleId()+" ");
			}
			if( request.getUpdateResourceStatus().getPhysicalResource() != null ){
				sb.append("PhysicalResource: "+request.getUpdateResourceStatus().getPhysicalResource().getSerialNumber()+" ");
			}
			if( request.getUpdateResourceStatus().getPhysicalResourceAssociated() != null ){
				sb.append("PhysicalResourceAssociated: "+request.getUpdateResourceStatus().getPhysicalResourceAssociated().getSerialNumber()+" ");
			}
			if( request.getUpdateResourceStatus().getMovementCategory() != null ){
				sb.append("MovementCategory: "+request.getUpdateResourceStatus().getMovementCategory().getMovement()+" ");
			}
		}
		
		log.info(sb.toString());
	}
	
	
	
	private UpdateResourceStatus createUpdateResourceStatus(MovCmdQueueVO record) throws BusinessException, PropertiesException{
		UpdateResourceStatus request = new UpdateResourceStatus();
		
		if( record.getTargetWarehouse() != null) {
			Long sourceWhId = record.getTargetWarehouse().getId();
			Long dealerOwnerSourceCode = 0L;
			if(sourceWhId != null && sourceWhId.longValue() > 0){
				FunctionOrProcessProvider sourceWarehouse = new FunctionOrProcessProvider();
				dealerOwnerSourceCode = getWhDealerOwnerCode(record.getTargetWarehouse());
				sourceWarehouse.setPartyRoleId("" + dealerOwnerSourceCode);
				request.setStockHandler(sourceWarehouse);
			}
		}
		
		PhysicalResource physicalResource = new PhysicalResource();
		physicalResource.setSerialNumber( record.getSerialized() != null ? record.getSerialized().getSerialCode() : null );
		
		PhysicalResource physicalResourceAssociated = null;
		if( record.getSerialized() != null && record.getSerialized().getSerialized() != null ){
			physicalResourceAssociated = new PhysicalResource();
			physicalResourceAssociated.setSerialNumber( record.getSerialized().getSerialized().getSerialCode() );
		}
		
		request.setPhysicalResource( physicalResource );
		request.setPhysicalResourceAssociated( physicalResourceAssociated );
		
		if( record.getMovCmdConfig().getUpdateStatusReaons() != null ){
			MovementCategoryType movementCategoryType = new MovementCategoryType();
			movementCategoryType.setMovement( record.getMovCmdConfig().getUpdateStatusReaons().getReasonId().toString() );
			request.setMovementCategory(movementCategoryType);
		}
		
		return request;
	}

	@Override
	public BusinessException manageServiceException(Throwable e) {
		BusinessException be = null;
		String errorCode = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getCode();
		String errorMessage = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getMessage();
		if(e instanceof MoveResourceToStockHandlerException_Exception){
			errorMessage = this.getMoveResourceToStockHandlerExceptionMessage(((MoveResourceToStockHandlerException_Exception)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		} else if( e instanceof  UpdateResourceStatusException_Exception){
			errorMessage = this.getUpdateResourceStatusExceptionExceptionMessage(((UpdateResourceStatusException_Exception)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		} else{
			be = this.manageException(e);
		}
		return be;
	}
	
	private String getUpdateResourceStatusExceptionExceptionMessage(UpdateResourceStatusException faultInfo , String defaultMessage){
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException() != null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException() != null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException() != null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException() != null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException() != null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}else if(faultInfo.getServiceUnavailabbleException() != null){
			return faultInfo.getServiceUnavailabbleException().getMessage();
		}
		return defaultMessage;
	}
	
	private String getMoveResourceToStockHandlerExceptionMessage(MoveResourceToStockHandlerException faultInfo, String defaultMessage){
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException() != null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException() != null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException() != null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException() != null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException() != null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}else if(faultInfo.getServiceUnavailabbleException() != null){
			return faultInfo.getServiceUnavailabbleException().getMessage();
		}
		return defaultMessage;
	}
	
	
}
