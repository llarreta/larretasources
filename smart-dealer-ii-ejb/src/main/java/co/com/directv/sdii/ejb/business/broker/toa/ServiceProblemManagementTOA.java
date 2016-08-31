package co.com.directv.sdii.ejb.business.broker.toa;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.WOAttentionElementsRequestDTO;

import com.directvla.schema.businessdomain.serviceproblemmanagement.v1_0.ResourceChangeDamagedRequest;
import com.directvla.schema.util.commondatatypes.serviceproblemmanagement.v1_0.RequestMetadataType;


/**
 * Clase encargada de obtner objetos de negocio
 * basados en objetos de de Contratos de Web Services
 * 
 * Fecha de Creación: 8/09/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public final class ServiceProblemManagementTOA {
	
	private ServiceProblemManagementTOA(){}
	
	private final static Logger log = UtilsBusiness.getLog4J(ServiceProblemManagementTOA.class);
	
	
	/**
	 * Metodo: Pobla el objeto ResourceChangeDamagedRequest
	 * para el consumo de la operacion ResourceChangeDamaged
	 * @param attentionElements
	 * @return ResourceChangeDamagedRequest
	 * @author jalopez
	 */
	public static ResourceChangeDamagedRequest populateResourceChangeDamaged(WOAttentionElementsRequestDTO attentionElements) {
		ResourceChangeDamagedRequest request = new ResourceChangeDamagedRequest();
		
		request.setRequestMetadata( getMetadataType(attentionElements) );
		logResourceChangeDamaged( request );
		return request;
	}
	
	/**
	 * 
	 * Metodo: Retorna el metadata del servicio.
	 * @param WOAttentionsRequestDTO woAttentionDTO
	 * @return RequestMetadataType
	 * @author jalopez
	 */
	private static RequestMetadataType getMetadataType(WOAttentionElementsRequestDTO woAttentionDTO){
		RequestMetadataType requestMetadataType = new RequestMetadataType();		
		requestMetadataType.setRequestID( woAttentionDTO.getCountryCode() );
		requestMetadataType.setSourceID( woAttentionDTO.getCountryCode() );
		requestMetadataType.setValidate(false);
		try {
			requestMetadataType.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
		} catch (PropertiesException e) {
			log.debug("== Termina la operaciÃ³n getMetadataType/ServiceProblemManagementTOA ==");
			e.printStackTrace();
		}
		return requestMetadataType;
	}
	
	/**
	 * 
	 * Metodo: Escribe en el log
	 * la informacion enviada al servicio.
	 * @param request
	 * @author jalopez
	 */
	public static void logResourceChangeDamaged(ResourceChangeDamagedRequest request){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();		
				
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
				
		//Parametros enviados
		buffer.append(" CustomerKey :" + request.getResourceChangeDamaged().getCustomerKey() );
		buffer.append(" OldDecoderResource SerialNumber :" + request.getResourceChangeDamaged().getOldDecoderResource().getSerialNumber() );	
		buffer.append(" NewDecoderResource SerialNumber :" + request.getResourceChangeDamaged().getNewDecoderResource().getSerialNumber() );
		buffer.append(" OldSmartcardResource SerialNumber :" + request.getResourceChangeDamaged().getOldSmartcardResource().getSerialNumber() );
		buffer.append(" NewSmartcardResource SerialNumber :" + request.getResourceChangeDamaged().getNewSmartcardResource().getSerialNumber() );
		buffer.append(" StockhandlerId :" + request.getResourceChangeDamaged().getStockhandlerId()); //dealer
		buffer.append(" ReasonIdNewChange :" + request.getResourceChangeDamaged().getReasonIdNewChange() );
		buffer.append(" ReasonIdOldChange :" + request.getResourceChangeDamaged().getReasonIdOldChange() );
		buffer.append(" ReasonIdNewChangeSmartcard :" + request.getResourceChangeDamaged().getReasonIdNewChangeSmartcard() );
		buffer.append(" ReasonIdOldChangeSmartcard :" + request.getResourceChangeDamaged().getReasonIdOldChangeSmartcard() );
		buffer.append(" WorkOrder ID :" + request.getResourceChangeDamaged().getWorkOrder().getID() );
		buffer.append(" WorkOrder ActionTaken :" + request.getResourceChangeDamaged().getWorkOrder().getActionTaken() ); //descripcion de la atencion
		buffer.append(" WorkOrder Quantity :" + request.getResourceChangeDamaged().getWorkOrder().getWorkOrderItemList().getWorkOrderItem().get(0).getQuantity() ); //cantidad de servicios de la WO
		buffer.append(" WorkOrder BusinessInteractionItemReferences :" + request.getResourceChangeDamaged().getWorkOrder().getWorkOrderItemList().getWorkOrderItem().get(0).getBusinessInteractionItemReferences() ); 
		buffer.append(" WorkOrder BusinessInteractionItemInvolvesService :" + request.getResourceChangeDamaged().getWorkOrder().getWorkOrderItemList().getWorkOrderItem().get(0).getBusinessInteractionItemInvolvesService().getId() ); 
		buffer.append(" WorkOrder ReasonIdWorkOrderEdit :" + request.getResourceChangeDamaged().getReasonIdWorkOrderEdit() ); 
		buffer.append(" WorkOrder ReasonIdWorkOrderComplete :" + request.getResourceChangeDamaged().getReasonIdWorkOrderComplete() ); 
		
		buffer.append(logMetadata(request.getRequestMetadata()));
		
		//Construcccion del mensaje
		params[0] = "ResourceChangeDamaged";
		params[1] = buffer.toString();
		message.append("== ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getCode() );
		message.append(" : ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getMessage(params) );
		message.append(" ==");
		log.info( message.toString() );
	}
	
	/**
	 * 
	 * Metodo: Retorna el log de la informacion
	 * enviada de la Metadata
	 * @param inventoryDTO
	 * @return String
	 * @author jalopez
	 */
	private static String logMetadata(RequestMetadataType requestMetadataType){
		StringBuilder buffer = new StringBuilder();
		buffer.append(" Metadata [");
		buffer.append(" SystemId: "+requestMetadataType.getSystemID());
		buffer.append(" RequestID: "+requestMetadataType.getRequestID());
		buffer.append(" SourceID: "+requestMetadataType.getSourceID());
		buffer.append(" ]");
		return buffer.toString();
	}
}
