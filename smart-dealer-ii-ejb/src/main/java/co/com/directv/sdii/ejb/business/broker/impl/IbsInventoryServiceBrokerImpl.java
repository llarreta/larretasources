/**
 * Creado 11/08/2010 11:40:16
 */
package co.com.directv.sdii.ejb.business.broker.impl;

import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.ServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.broker.IServiceBroker;
import co.com.directv.sdii.ejb.business.broker.IbsInventoryServiceBrokerLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.IBSMoveResourceBetweenCustAndDealerDTO;
import co.com.directv.sdii.model.dto.IBSSerElemMovementDTO;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;

import com.directvla.contract.crm.inventory.v1_0.MoveResourceToCustomer;
import com.directvla.contract.crm.inventory.v1_0.MoveResourceToCustomerRequest;
import com.directvla.contract.crm.inventory.v1_0.MoveResourceToCustomerResponse;
import com.directvla.contract.crm.inventory.v1_0.MoveResourceToStockHandler;
import com.directvla.contract.crm.inventory.v1_0.MoveResourceToStockHandlerException;
import com.directvla.contract.crm.inventory.v1_0.MoveResourceToStockHandlerException_Exception;
import com.directvla.contract.crm.inventory.v1_0.MoveResourceToStockHandlerRequest;
import com.directvla.contract.crm.inventory.v1_0.MoveResourceToStockHandlerResponse;
import com.directvla.contract.crm.inventory.v1_0.PtInventory;
import com.directvla.contract.crm.inventory.v1_0.UpdateResourceStatus;
import com.directvla.contract.crm.inventory.v1_0.UpdateResourceStatusRequest;
import com.directvla.schema.businessdomain.common.inventory.v1_0.FunctionOrProcessProvider;
import com.directvla.schema.businessdomain.common.inventory.v1_0.MovementCategoryType;
import com.directvla.schema.businessdomain.common.inventory.v1_0.PhysicalResource;
import com.directvla.schema.businessdomain.customer.inventory.v1_0.Customer;
import com.directvla.schema.util.commondatatypes.inventory.v1_0.RequestMetadataType;


/**
 * Implementación de Broker de servicios asociados con el módulo de inventarios
 * 
 * Fecha de Creación: 11/08/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.broker.IbsInventoryServiceBrokerLocal
 */
@Stateless(name="IbsInventoryServiceBrokerLocal",mappedName="ejb/IbsInventoryServiceBrokerLocal")
public class IbsInventoryServiceBrokerImpl extends BusinessBase implements	IbsInventoryServiceBrokerLocal, IServiceBroker {

private final static Logger log = UtilsBusiness.getLog4J(IbsInventoryServiceBrokerImpl.class);
	
	@EJB(name = "WarehouseDAOLocal", beanInterface = WarehouseDAOLocal.class)
	private WarehouseDAOLocal warehouseDao;

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.InventoryServiceBrokerLocal#moveResourceFromDealerToCustomer(co.com.directv.sdii.model.dto.IBSMoveResourceBetweenCustAndDealerDTO)
	 */
	@Override
	public void moveResourceFromDealerToCustomer(IBSMoveResourceBetweenCustAndDealerDTO moveResToCustDto) throws BusinessException{
		log.debug("== Inicia la operación moveResourceToStockHandler/InventoryServiceBrokerImpl ==");
		try {
			ServiceLocator servicelocator = ServiceLocator.getInstance();
			PtInventory service = servicelocator.getInventoryService();
			MoveResourceToCustomerRequest moveResToCust = createMoveResourceToCustomerRequest(moveResToCustDto);
			MoveResourceToCustomerResponse response = service.moveResourceToCustomer(moveResToCust);		
			log.debug("RequestID = " + response.getResponseMetadata().getRequestID());
		} catch (Throwable ex) {
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operación moveResourceToStockHandler/InventoryServiceBrokerImpl ==");
			throw super.manageException(ex);
		}finally{
			log.debug("== Termina la operación moveResourceToStockHandler/InventoryServiceBrokerImpl ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.InventoryServiceBrokerLocal#moveResourceFromCustomerToDealer(co.com.directv.sdii.model.dto.IBSMoveResourceBetweenCustAndDealerDTO)
	 */
	@Override
	public void moveResourceFromCustomerToDealer(IBSMoveResourceBetweenCustAndDealerDTO moveResToCustDto) throws BusinessException{
		log.debug("== Inicia la operación moveResourceToStockHandler/InventoryServiceBrokerImpl ==");
		try {
			ServiceLocator servicelocator = ServiceLocator.getInstance();
			PtInventory service = servicelocator.getInventoryService();
			MoveResourceToStockHandlerRequest moveResToDealer = createMoveResourceFromCustomerToDealerRequest(moveResToCustDto);
			MoveResourceToStockHandlerResponse response = service.moveResourceToStockHandler(moveResToDealer);		
			log.debug("RequestID = " + response.getResponseMetadata().getRequestID());
		
		} catch (Throwable ex) {
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operación moveResourceToStockHandler/InventoryServiceBrokerImpl ==");
			throw super.manageException(ex);
		}finally{
			log.debug("== Termina la operación moveResourceToStockHandler/InventoryServiceBrokerImpl ==");
		}
	}
	
	/**
	 * Metodo: Construye una petición
	 * @param moveResToDealerDto
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jjimenezh
	 */
	private MoveResourceToStockHandlerRequest createMoveResourceFromCustomerToDealerRequest(
			IBSMoveResourceBetweenCustAndDealerDTO moveResToDealerDto) throws BusinessException{
		log.info("== Inicia createMoveResourceToStockHandlerRequest/InventoryServiceBrokerImpl ==");
		MoveResourceToStockHandlerRequest request = new MoveResourceToStockHandlerRequest();
		try{
			RequestMetadataType resquestMetaData = new RequestMetadataType();
			Random r = new Random();
			int requestId =  r.nextInt(1000000);
			resquestMetaData.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
			resquestMetaData.setRequestID(requestId+"");
			resquestMetaData.setSourceID(moveResToDealerDto.getCountryCode());
			
			MoveResourceToStockHandler moveResourceToStockHandler = new MoveResourceToStockHandler();
			if(moveResToDealerDto.getCustomerCode() != null && moveResToDealerDto.getCustomerCode().trim().length() > 0){
				Customer cust = new Customer();
				cust.setID(moveResToDealerDto.getCustomerCode());
				moveResourceToStockHandler.setCustomerFrom(cust);
			}
			//Creo y asigno la bodega de destino
			String dealerCode = moveResToDealerDto.getDealerCode();
			if(dealerCode != null && dealerCode.trim().length() > 0){
				FunctionOrProcessProvider targetWarehouse = new FunctionOrProcessProvider();
				targetWarehouse.setPartyRoleId(dealerCode);
				moveResourceToStockHandler.setStockHandlerTo(targetWarehouse);
			}
			
			//se asigna el elemento
			PhysicalResource physicalResource = new PhysicalResource();
			physicalResource.setSerialNumber( moveResToDealerDto.getElementSerialCode() != null ? moveResToDealerDto.getElementSerialCode() : "" );
			moveResourceToStockHandler.setPhysicalResource( physicalResource );
			
			PhysicalResource physicalResourceAssociated = new PhysicalResource();
			physicalResourceAssociated.setSerialNumber( moveResToDealerDto.getLinkedElementSerial() != null ? moveResToDealerDto.getLinkedElementSerial() : "" );
			
			//Se asigna el tipo de movimiento
			MovementCategoryType movementType = new MovementCategoryType();
			movementType.setMovement(moveResToDealerDto.getMouvementTypeID());
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

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.InventoryServiceBrokerLocal#reportNotSerializedElementMovement(co.com.directv.sdii.model.dto.IBSSerElemMovementDTO)
	 */	
	@Override
	public boolean moveResourceToStockHandler(IBSSerElemMovementDTO serElMovDto) throws BusinessException {
		log.debug("== Inicia la operación moveResourceToStockHandler/InventoryServiceBrokerImpl ==");
		boolean success = true;
		try {
			log.debug("Se invocará la operación moveResourceToStockHandler con los siguientes parámetros: " + serElMovDto);
			ServiceLocator servicelocator = ServiceLocator.getInstance();
			PtInventory service = servicelocator.getInventoryService();
			MoveResourceToStockHandlerRequest moveSerElementRequest = createMoveResourceToStockHandlerRequest(serElMovDto);
			
			//Si el request viene nulo, no se notifica el movimiento
			if(moveSerElementRequest == null){
				return success;
			}
			
			MoveResourceToStockHandlerResponse moveSerElementResponse = service.moveResourceToStockHandler(moveSerElementRequest);
			com.directvla.schema.util.commondatatypes.inventory.v1_0.ResponseMetadataType responseMetaData = moveSerElementResponse.getResponseMetadata();
			if(responseMetaData.getBoxUsage() == null || responseMetaData.getRequestID() == null || responseMetaData.getTimeStamp() == null){
				success = false;
			}			
		} catch (Throwable ex) {
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operación moveResourceToStockHandler/InventoryServiceBrokerImpl ==");
			throw this.manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación moveResourceToStockHandler/InventoryServiceBrokerImpl ==");
		}
		return success;
		
	}

	
	/**
	 * Metodo: Permite armar el objeto para invocar el WS en IBS para notificar movimientos entre bodegas de elementos
	 * serializados
	 * @param serElemMouvementDTO
	 * @return MoveResourceToStockHandlerRequest - Respuest para invocar WS de movimiento de elementos entre bodegas
	 * @throws BusinessException Lanza excpecion en el caso que ocurra un error creadno el request
	 * @author jnova
	 */
	private MoveResourceToStockHandlerRequest createMoveResourceToStockHandlerRequest(IBSSerElemMovementDTO serElemMouvementDTO) throws BusinessException{
		log.info("== Inicia createMoveResourceToStockHandlerRequest/InventoryServiceBrokerImpl ==");
		MoveResourceToStockHandlerRequest request = new MoveResourceToStockHandlerRequest();
		try{
			RequestMetadataType resquestMetaData = new RequestMetadataType();
			Random r = new Random();
			int requestId =  r.nextInt(1000000);
			resquestMetaData.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
			resquestMetaData.setRequestID(requestId+"");
			resquestMetaData.setSourceID(serElemMouvementDTO.getCountryCode());
			
			MoveResourceToStockHandler moveResourceToStockHandler = new MoveResourceToStockHandler();
			//Creo y asigno la bodega de origen
			FunctionOrProcessProvider sourceWarehouse = new FunctionOrProcessProvider();
			
			Long sourceWhId = serElemMouvementDTO.getSourceWarehouseID();
			Long dealerOwnerSourceCode = 0L;
			if(sourceWhId != null && sourceWhId.longValue() > 0){
				dealerOwnerSourceCode = getWhDealerOwnerCode(sourceWhId);
				sourceWarehouse.setPartyRoleId("" + dealerOwnerSourceCode);
				moveResourceToStockHandler.setStockHandlerFrom(sourceWarehouse);
			}
			//Creo y asigno la bodega de destino
			Long targetWhId = serElemMouvementDTO.getTargetWarehouseID();
			Long dealerOwnerTargetCode = 0L;
			if(targetWhId != null && targetWhId.longValue() > 0){
				FunctionOrProcessProvider targetWarehouse = new FunctionOrProcessProvider();
				dealerOwnerTargetCode = getWhDealerOwnerCode(targetWhId);
				targetWarehouse.setPartyRoleId( "" + dealerOwnerTargetCode);
				moveResourceToStockHandler.setStockHandlerTo(targetWarehouse);
			}
			
			//Si el dealer dueño de la bodega origen es igual al dealer dueño de la bodega destino, no se notifica el movimiento:
			if(dealerOwnerTargetCode.longValue() == dealerOwnerTargetCode.longValue()){
				if(log.isDebugEnabled()){
					log.debug("No se notificará el movimiento del elemento con serial: " + serElemMouvementDTO.getElementSerialCode() + " debido a que se ha movido entre bodegas del mismo dealer, el código del dealer es: " + dealerOwnerTargetCode);
				}
				return null;
			}
			
			//se asigna el elemento
			PhysicalResource physicalResource = new PhysicalResource();
			physicalResource.setSerialNumber( serElemMouvementDTO.getElementSerialCode() != null ? serElemMouvementDTO.getElementSerialCode() : "" );
			moveResourceToStockHandler.setPhysicalResource( physicalResource );
			
			PhysicalResource physicalResourceAssociated = new PhysicalResource();
			physicalResourceAssociated.setSerialNumber( serElemMouvementDTO.getLinkedElementSerial() != null ? serElemMouvementDTO.getLinkedElementSerial() : "" );
			
			//Se asigna el tipo de movimiento
			MovementCategoryType movementType = new MovementCategoryType();
			movementType.setMovement(serElemMouvementDTO.getMouvementTypeID());
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
	
	/**
	 * Metodo: Permite armar el objeto para invocar el WS en IBS para notificar movimientos entre bodegas de elementos
	 * serializados
	 * @param serElemMouvementDTO
	 * @return MoveResourceToStockHandlerRequest - Respuest para invocar WS de movimiento de elementos entre bodegas
	 * @throws BusinessException Lanza excpecion en el caso que ocurra un error creadno el request
	 * @author jnova
	 */
	private MoveResourceToCustomerRequest createMoveResourceToCustomerRequest(IBSMoveResourceBetweenCustAndDealerDTO serElemMouvementDTO) throws BusinessException{
		log.info("== Inicia createMoveResourceToStockHandlerRequest/InventoryServiceBrokerImpl ==");
		MoveResourceToCustomerRequest request = new MoveResourceToCustomerRequest();
		try{
			RequestMetadataType resquestMetaData = new RequestMetadataType();
			Random r = new Random();
			int requestId =  r.nextInt(1000000);
			resquestMetaData.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
			resquestMetaData.setRequestID(requestId+"");
			resquestMetaData.setSourceID(serElemMouvementDTO.getCountryCode());
			
			MoveResourceToCustomer moveResourceToCustomer = new MoveResourceToCustomer();
			//Creo y asigno la bodega de origen
			FunctionOrProcessProvider sourceWarehouse = new FunctionOrProcessProvider();
			
			String dealerCode = serElemMouvementDTO.getDealerCode();
			if(dealerCode != null && dealerCode.trim().length() > 0){
				sourceWarehouse.setPartyRoleId("" + dealerCode);
				moveResourceToCustomer.setStockHandlerFrom(sourceWarehouse);
			}
			//Creo y asigno la bodega de destino
			String customerCode = serElemMouvementDTO.getCustomerCode();
			if(customerCode != null && customerCode.trim().length() > 0){
				Customer customer = new Customer();
				customer.setID(customerCode);
				moveResourceToCustomer.setCustomerTo(customer);
			}
			//se asigna el elemento
			PhysicalResource physicalResource = new PhysicalResource();
			physicalResource.setSerialNumber( serElemMouvementDTO.getElementSerialCode() != null ? serElemMouvementDTO.getElementSerialCode() : "" );
			moveResourceToCustomer.setPhysicalResource( physicalResource );
			
			PhysicalResource physicalResourceAssociated = new PhysicalResource();
			physicalResourceAssociated.setSerialNumber( serElemMouvementDTO.getLinkedElementSerial() != null ? serElemMouvementDTO.getLinkedElementSerial() : "" );
			
			//Se asigna el tipo de movimiento
			MovementCategoryType movementType = new MovementCategoryType();
			movementType.setMovement(serElemMouvementDTO.getMouvementTypeID());
			moveResourceToCustomer.setMovementCategory( movementType );
			
			//Asigno los valores al request
			request.setRequestMetadata(resquestMetaData);
			request.setMoveResourceToCustomer(moveResourceToCustomer);
			
			return request;
		}catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createMoveResourceToStockHandlerRequest/InventoryServiceBrokerImpl ==");
        	throw this.manageException(ex);
        } finally {
            log.info("== Termina createMoveResourceToStockHandlerRequest/InventoryServiceBrokerImpl ==");
        }	
	}

	private Long getWhDealerOwnerCode(Long warehouseId) throws DAOServiceException, DAOSQLException, BusinessException {
		Warehouse wh = warehouseDao.getWarehouseByID(warehouseId);
		UtilsBusiness.assertNotNull(wh, ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage() + " No se encontró la bodega con el identificador: " + warehouseId);
		
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
	 * @see co.com.directv.sdii.ejb.business.broker.InventoryServiceBrokerLocal#updateResourceStatus(co.com.directv.sdii.model.dto.IBSSerElemMovementDTO)
	 */
	@Override
	public boolean updateResourceStatus(IBSSerElemMovementDTO serElMovDto) throws BusinessException {
		log.info("== Inicia updateResourceStatus/InventoryServiceBrokerImpl ==");
		try{
			boolean success = true;
			ServiceLocator servicelocator = ServiceLocator.getInstance();
			PtInventory service = servicelocator.getInventoryService();
			UpdateResourceStatusRequest request = new UpdateResourceStatusRequest();
			
			RequestMetadataType resquestMetaData = new RequestMetadataType();
			int requestId = new Double(Math.random() * 1000000).intValue();
			resquestMetaData.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
			resquestMetaData.setRequestID(requestId+"");
			resquestMetaData.setSourceID(serElMovDto.getCountryCode());
			
			request.setRequestMetadata( resquestMetaData );
			request.setUpdateResourceStatus( createUpdateResourceStatus(serElMovDto) );
			service.updateResourceStatus( request );
			return success;
		}catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operaci�n updateResourceStatus/InventoryServiceBrokerImpl ==");
        	throw this.manageException(ex);
        } finally {
            log.info("== Termina updateResourceStatus/InventoryServiceBrokerImpl ==");
        }
	}
	
	/**
	 * Metodo: Crea el objeto para invocacion de servicio de updateResourceStatus
	 * @param serElMovDto Objeto de negocio que encapsula la informacion para actualizar el estado de elemento en IBS
	 * @return Objeto que encapsula informacion para 
	 */
	private UpdateResourceStatus createUpdateResourceStatus(IBSSerElemMovementDTO serElMovDto){
		UpdateResourceStatus request = new UpdateResourceStatus();
		//jnova poner la informacion que informe DTV del servicio
		
		FunctionOrProcessProvider functionOrProcessProvider = new FunctionOrProcessProvider();
		functionOrProcessProvider.setPartyRoleId( serElMovDto.getSourceWarehouseID() != null ? serElMovDto.getSourceWarehouseID().toString() : "" );
		
		PhysicalResource physicalResource = new PhysicalResource();
		physicalResource.setSerialNumber( serElMovDto.getElementSerialCode() != null ? serElMovDto.getElementSerialCode() : "" );
		
		PhysicalResource physicalResourceAssociated = new PhysicalResource();
		physicalResourceAssociated.setSerialNumber( serElMovDto.getLinkedElementSerial() != null ? serElMovDto.getLinkedElementSerial() : "" );
		//pendiente saber que tipo de movimiento
		
		request.setStockHandler( functionOrProcessProvider );
		request.setPhysicalResource( physicalResource );
		request.setPhysicalResourceAssociated( physicalResourceAssociated );
		return request;
	}

	@Override
	public BusinessException manageServiceException(Throwable e) {
		BusinessException be = null;
		String errorCode = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getCode();
		String errorMessage = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getMessage();
		if(e instanceof MoveResourceToStockHandlerException_Exception){
			errorMessage = getMoveResourceToStockHandlerExceptionMessage(((MoveResourceToStockHandlerException_Exception)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}
		return be;
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
