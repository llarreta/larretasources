package co.com.directv.sdii.ejb.business.broker.toa;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;

import com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.FunctionOrProcessProvider;
import com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.MovementCategoryType;
import com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.PhysicalResource;
import com.directvla.schema.businessdomain.sprmsupportandreadiness.v1_0.MoveResourceToStockHandler;
import com.directvla.schema.businessdomain.sprmsupportandreadiness.v1_0.MoveResourceToStockHandlerRequest;
import com.directvla.schema.businessdomain.sprmsupportandreadiness.v1_0.ReceiveReturnedPhysicalResource;
import com.directvla.schema.businessdomain.sprmsupportandreadiness.v1_0.ReceiveReturnedPhysicalResourceRequest;
import com.directvla.schema.util.commondatatypes.sprmsupportandreadiness.v1_0.RequestMetadataType;

/**
 * 
 * Clase encargada de obtner objetos de negocio
 * basados en objetos de de Contratos de Web Services
 * 
 * 
 * Fecha de CreaciÃ³n: 20/06/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public final class IBSSPRMSupportAndReadinessTOA {
	
	private IBSSPRMSupportAndReadinessTOA(){}
	
	private final static Logger log = UtilsBusiness.getLog4J(CoreWorkOrderServiceTOA.class);
	
	/**
	 * 
	 * Metodo: Genera log de la operacion MoveResourceToStockHandler
	 * @param moveSerElementRequest Request que se envia a ibs
	 * @author aharker
	 */
	public static void generateMoveResourceToStockHandlerLog(MoveResourceToStockHandlerRequest moveSerElementRequest){
		log.info("== Inicia la operaciÃ³n generateMoveResourceToStockHandlerLog/IBSSPRMSupportAndReadinessBrokerImpl ==");
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
		if( moveSerElementRequest.getMoveResourceToStockHandler() != null ){
			sb.append("MoveResource: ");
			if( moveSerElementRequest.getMoveResourceToStockHandler().getStockHandlerFrom() != null ){
				sb.append("StockHandlerFrom.PartyRoleId: "+moveSerElementRequest.getMoveResourceToStockHandler().getStockHandlerFrom().getPartyRoleId()+" ");
			}
			if( moveSerElementRequest.getMoveResourceToStockHandler().getStockHandlerTo() != null ){
				sb.append("StockHandlerTo.PartyRoleId: "+moveSerElementRequest.getMoveResourceToStockHandler().getStockHandlerTo().getPartyRoleId()+" ");
			}
			if( moveSerElementRequest.getMoveResourceToStockHandler().getPhysicalResource() != null ){
				sb.append("PhysicalResource: "+moveSerElementRequest.getMoveResourceToStockHandler().getPhysicalResource().getSerialNumber()+" ");
			}
			if( moveSerElementRequest.getMoveResourceToStockHandler().getPhysicalResourceAssociated() != null ){
				sb.append("PhysicalResourceAssociated: "+moveSerElementRequest.getMoveResourceToStockHandler().getPhysicalResourceAssociated().getSerialNumber()+" ");
			}
		}
		log.info(sb.toString());
		log.info("== Termina la operaciÃ³n generateMoveResourceToStockHandlerLog/IBSSPRMSupportAndReadinessBrokerImpl ==");
	}

	/**
	 * 
	 * Metodo: Genera log de la operacion MoveResourceToStockHandler
	 * @param moveSerElementRequest Request que se envia a ibs
	 * @author aharker
	 */
	public static void generatereceiveReturnedPhysicalResourceLog(ReceiveReturnedPhysicalResourceRequest receiveReturnedPhysicalResourceRequest, boolean serialLink){
		log.info("== Inicia la operaciÃ³n generatereceiveReturnedPhysicalResourceLog/IBSSPRMSupportAndReadinessBrokerImpl ==");
		StringBuilder sb = new StringBuilder();
		String tempSeralLink = "";
		sb.append("MoveResourceToStockHandlerRequest: ");
		if( receiveReturnedPhysicalResourceRequest.getRequestMetadata() != null ){
			sb.append("RequestMetadata: ");
			if( receiveReturnedPhysicalResourceRequest.getRequestMetadata().getRequestID() != null ){
				sb.append("requestId: "+receiveReturnedPhysicalResourceRequest.getRequestMetadata().getRequestID()+" ");
			}
			if( receiveReturnedPhysicalResourceRequest.getRequestMetadata().getSourceID() != null ){
				sb.append("sourceId: "+receiveReturnedPhysicalResourceRequest.getRequestMetadata().getSourceID()+" ");
			}
		}
		if( receiveReturnedPhysicalResourceRequest.getReceiveReturnedPhysicalResource() != null ){
			sb.append("ReceiveReturnedPhysicalResource: ");
			if(serialLink){
				tempSeralLink = "Vinculado";
			}
			if( receiveReturnedPhysicalResourceRequest.getReceiveReturnedPhysicalResource().getPhysicalResourceSerialNumber() != null ){
				sb.append(tempSeralLink + " PhysicalResourceSerialNumber: "+receiveReturnedPhysicalResourceRequest.getReceiveReturnedPhysicalResource().getPhysicalResourceSerialNumber()+" ");
			}
			if( receiveReturnedPhysicalResourceRequest.getReceiveReturnedPhysicalResource().getReasonId()>0 ){
				sb.append("ReasonId: "+receiveReturnedPhysicalResourceRequest.getReceiveReturnedPhysicalResource().getReasonId()+" ");
			}
			if( receiveReturnedPhysicalResourceRequest.getReceiveReturnedPhysicalResource().getStockHandlerId()>0 ){
				sb.append("StockHandlerId: "+receiveReturnedPhysicalResourceRequest.getReceiveReturnedPhysicalResource().getStockHandlerId()+" ");
			}
		}
		log.info(sb.toString());
		log.info("== Termina la operacion generatereceiveReturnedPhysicalResourceLog/IBSSPRMSupportAndReadinessBrokerImpl ==");
	}
	

	/**
	 * 
	 * @param countryCode es el parametro de pais que va en la cabecera del mensaje soap
	 * @return El retorno es la metadata del request que se le hara al servicio
	 * @author aharker
	 */
	private static RequestMetadataType createMessageHeaders(String countryCode){
		log.info("== Inicia la operaciÃ³n createMessageHeaders/IBSSPRMSupportAndReadinessBrokerImpl ==");
		RequestMetadataType resquestMetaData = new RequestMetadataType();
		resquestMetaData.setRequestID(countryCode);
		resquestMetaData.setSourceID(countryCode);
		try {
			resquestMetaData.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
		} catch (PropertiesException e) {
			log.info("== Error en la operacion createMessageHeaders/IBSSPRMSupportAndReadinessBrokerImpl ==");
			e.printStackTrace();
		}
		log.info("== Termina la operaciÃ³n createMessageHeaders/IBSSPRMSupportAndReadinessBrokerImpl ==");
		return resquestMetaData;
	}
	
	/**
	 * 
	 * @param record El movimiento que se debe realizar en IBS
	 * @return Retorna todo el contenido de la peticion al servicio de movimeinto de elementos serializados
	 * @throws BusinessException
	 * @throws PropertiesException
	 * @author aharker
	 */
	private static MoveResourceToStockHandler createMoveResourceToStockHandler(MovCmdQueueVO record) throws BusinessException, PropertiesException{
		log.info("== Inicia la operaciÃ³n createMoveResourceToStockHandler/IBSSPRMSupportAndReadinessBrokerImpl ==");
		MoveResourceToStockHandler moveResourceToStockHandler = new MoveResourceToStockHandler();
		//Creo y asigno la bodega de destino
		if( record.getTargetWarehouse() != null ){
			Long targetWhId = record.getTargetWarehouse().getId();
			Long dealerOwnerTargetCode = 0L;
			if(targetWhId != null && targetWhId.longValue() > 0){
				FunctionOrProcessProvider targetWarehouse = new FunctionOrProcessProvider();
				dealerOwnerTargetCode = getWhDealerOwnerCode(record.getTargetWarehouse());
				targetWarehouse.setPartyRoleId( "" + dealerOwnerTargetCode);
				moveResourceToStockHandler.setStockHandlerTo(targetWarehouse);
			}
		}else{
			throw new BusinessException(ErrorBusinessMessages.STOCK_IN511.getCode(), ErrorBusinessMessages.STOCK_IN511.getMessage());
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
		log.info("== Termina la operaciÃ³n createMoveResourceToStockHandler/IBSSPRMSupportAndReadinessBrokerImpl ==");
		return moveResourceToStockHandler;
	}
	
	/**
	 * Metodo: Permite crear el una instancia de la clase ReceiveReturnedPhysicalResourceRequest con la informacion de MovCmdQueueVO
	 * @param record
	 * @param linkSerial
	 * @return
	 * @throws BusinessException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author cduarte
	 */
	public static ReceiveReturnedPhysicalResourceRequest createReceiveReturnedPhysicalResourceRequest(MovCmdQueueVO record,boolean linkSerial) throws BusinessException, PropertiesException {
		ReceiveReturnedPhysicalResourceRequest request = new ReceiveReturnedPhysicalResourceRequest();
		RequestMetadataType resquestMetaData = createMessageHeaders(record.getMovCmdConfig().getCountry().getCountryCode());
		
		ReceiveReturnedPhysicalResource receiveReturnedPhysicalResource = createReceiveReturnedPhysicalResource(record,linkSerial);
		
		//Asigno los valores al request
		request.setRequestMetadata(resquestMetaData);
		request.setReceiveReturnedPhysicalResource(receiveReturnedPhysicalResource);
		
		return request;
	}
	
	/**
	 * Metodo: Permite crear un objeto de ReceiveReturnedPhysicalResource
	 * @param record
	 * @param linkSerial
	 * @return
	 * @throws BusinessException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author cduarte
	 */
	private static ReceiveReturnedPhysicalResource createReceiveReturnedPhysicalResource(MovCmdQueueVO record,boolean linkSerial) throws BusinessException, PropertiesException{
		ReceiveReturnedPhysicalResource receiveReturnedPhysicalResource = new ReceiveReturnedPhysicalResource();

		//Se asigna el dealer
		if( record.getTargetWarehouse() != null ){
			Long targetWhId = record.getTargetWarehouse().getId();
			Long dealerOwnerTargetCode = 0L;
			if(targetWhId != null && targetWhId.longValue() > 0){
				dealerOwnerTargetCode = getWhDealerOwnerCode(record.getTargetWarehouse());
				receiveReturnedPhysicalResource.setStockHandlerId(dealerOwnerTargetCode.intValue());
			}
		}else{
			throw new BusinessException(ErrorBusinessMessages.STOCK_IN511.getCode(), ErrorBusinessMessages.STOCK_IN511.getMessage());
		}
		
		//se asigna el elemento
		if( record.getSerialized() != null && record.getSerialized().getSerialCode()!= null && !record.getSerialized().getSerialCode().equals("")){
			if(!linkSerial){
				receiveReturnedPhysicalResource.setPhysicalResourceSerialNumber(record.getSerialized().getSerialCode());
			}else{ 
				if( record.getSerialized().getSerialized().getSerialCode()!= null && !record.getSerialized().getSerialized().getSerialCode().equals("")){
					receiveReturnedPhysicalResource.setPhysicalResourceSerialNumber(record.getSerialized().getSerialized().getSerialCode());
				}else{
					return null;
				}
			}
		}else{
			return null;
		}
		
		//Se asigna la reason
		receiveReturnedPhysicalResource.setReasonId(record.getMovCmdConfig().getIbsElementStatusRecovery().getReasonId().intValue());
		
		return receiveReturnedPhysicalResource;
		
	}
	
	/**
	 * 
	 * Metodo: Permite armar el objeto para invocar el WS en IBS para notificar movimientos entre bodegas de elementos
	 * serializados
	 * @param record
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author aharker
	 * @throws PropertiesException 
	 * @throws BusinessException 
	 */
	public static MoveResourceToStockHandlerRequest createMoveResourceToStockHandlerRequest(MovCmdQueueVO record) throws BusinessException, PropertiesException {
		MoveResourceToStockHandlerRequest request = new MoveResourceToStockHandlerRequest();
		RequestMetadataType resquestMetaData = createMessageHeaders(record.getMovCmdConfig().getCountry().getCountryCode());
		
		MoveResourceToStockHandler moveResourceToStockHandler = createMoveResourceToStockHandler(record);
		
		//Asigno los valores al request
		request.setRequestMetadata(resquestMetaData);
		request.setMoveResourceToStockHandler(moveResourceToStockHandler);
		
		return request;
	}
	
	/***
	 * Metodo enfocado a extraer el codigo del dealer o del customer dueÃ±o de una bodega especifica
	 * @param wh bodega de la cual se desea saber el codigo del dealer o del cliente segun corresponda
	 * @return codigo del dueÃ±o de la bodega 
	 * @throws BusinessException Cualquier excepcion generada por el metodo de negocio encapsulada en una excepcion de negocio de HSP+
	 * @author Aharker
	 */
	private static Long getWhDealerOwnerCode(Warehouse wh) throws BusinessException {
		log.info("== getWhDealerOwnerCode/IBSSPRMSupportAndReadinessBrokerImpl ==");
		
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
}
