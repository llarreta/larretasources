package co.com.directv.sdii.ejb.business.broker.toa;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.WorkOrderDTO;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkorderReason;

import com.directvla.schema.businessdomain.serviceconfigurationandactivation.v1_0.AddActivation;
import com.directvla.schema.businessdomain.serviceconfigurationandactivation.v1_0.AddActivationRequest;
import com.directvla.schema.crm.common.v1_1.RequestMetadataType;

import com.directvla.schema.crm.customer.v1.AddServiceToWorkOrder;
import com.directvla.schema.crm.customer.v1.AddServiceToWorkOrderRequest;
import com.directvla.schema.crm.customer.v1.CancelWorkOrder;
import com.directvla.schema.crm.customer.v1.CancelWorkOrderRequest;
import com.directvla.schema.crm.customer.v1.RemoveWorkOrderService;
import com.directvla.schema.crm.customer.v1.RemoveWorkOrderServiceRequest;
import com.directvla.schema.crm.customer.v1.UpdateWorkOrder;
import com.directvla.schema.crm.customer.v1.UpdateWorkOrderRequest;

/**
 * 
 * Clase encargada de obtner objetos de negocio
 * basados en objetos de de Contratos de Web Services
 * 
 * 
 * Fecha de Creación: 20/06/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public final class CoreWorkOrderServiceTOA {
	
	private CoreWorkOrderServiceTOA(){}
	
	private final static Logger log = UtilsBusiness.getLog4J(CoreWorkOrderServiceTOA.class);
	
	/**
	 * Metodo: Construye el requerimiento para la invocación de la operación
	 * de actualización de estado de una work order
	 * @param workOrderSdii orden de trabajo de SDII con los datos de código
	 * y estado actual de work order
	 * @param workorderReason Razón para el cambio de estado de la work order
	 * @return Obqueto que encapsula el requerimiento para la invocación de la operación
	 * del servicio web
	 * @throws BusinessException En caso de error al tratar de construir el objeto del request
	 * @author jjimenezh
	 */
	public static UpdateWorkOrderRequest buildUpdateWorkOrderRequest(WorkOrderDTO workOrderSdii, WorkorderReason workorderReason) throws BusinessException {
		UpdateWorkOrderRequest request = new UpdateWorkOrderRequest();
		RequestMetadataType requestMetadataType = getMetadataType(workOrderSdii.getWorkOrder().getCountry().getCountryCode());
		request.setRequestMetadata(requestMetadataType);
		
		UpdateWorkOrder updateWorkOrder = new UpdateWorkOrder();
		
		//solo se envia la fecha en caso de ser un agendamiento.
		if(workOrderSdii.getWoAgenda() != null)
			if( workOrderSdii.getWoAgenda().getAgendationDate() != null ){		
				updateWorkOrder.setDate(UtilsBusiness.dateToGregorianCalendar(workOrderSdii.getWoAgenda().getAgendationDate()));
			}
		if(workOrderSdii.getDealerAssignmentCode() != null && workOrderSdii.getDealerAssignmentCode().longValue() > 0 ){
			updateWorkOrder.setServiceProviderId(workOrderSdii.getDealerAssignmentCode().intValue());
		}
		
		validateResult("No se asignó el código de la razón de la WO", workorderReason.getWorkorderReasonCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		updateWorkOrder.setReasonCode(workorderReason.getWorkorderReasonCode());
		
		validateResult("No se asignó el estado acutal a la work order para reportarlo a IBS: workOrderSdii.getWorkorderStatusByActualStatusId()", workOrderSdii.getWorkOrder().getWorkorderStatusByActualStatusId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		if(workOrderSdii.getWorkOrder().getWorkorderStatusByActualStatusId().getIbs6Status() != null){
			validateResult("No se asignó el estado acutal a la work order para reportarlo a IBS: workOrderSdii.getWorkorderStatusByActualStatusId().getIbs6Status().getIbs6StateCode()", workOrderSdii.getWorkOrder().getWorkorderStatusByActualStatusId().getIbs6Status().getIbs6StateCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			String statusCode = workOrderSdii.getWorkOrder().getWorkorderStatusByActualStatusId().getIbs6Status().getIbs6StateCode();
			updateWorkOrder.setStatusCode(statusCode);
		}
		/*validateResult("No se asignó el estado acutal a la work order para reportarlo a IBS: workOrderSdii.getWorkorderStatusByActualStatusId().getIbs6Status().getIbs6StateCode()", workOrderSdii.getWorkOrder().getWorkorderStatusByActualStatusId().getIbs6Status().getIbs6StateCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		String statusCode = workOrderSdii.getWorkOrder().getWorkorderStatusByActualStatusId().getIbs6Status().getIbs6StateCode();
		updateWorkOrder.setStatusCode(statusCode);*/
		
		updateWorkOrder.setWorkOrderKey(workOrderSdii.getWorkOrder().getWoCode());
		updateWorkOrder.setReturnHistoryId(true);
		request.setUpdateWorkOrder(updateWorkOrder);
		
		//Registra los parametros enviados en el log
		logUpdateWorkOrderRequest( updateWorkOrder );
		
		return request;
	}
	
	/**
	 * Metodo: Construye el requerimiento para la invocación de la operación
	 * de actualización de estado de una work order
	 * @param workOrderSdii orden de trabajo de SDII con los datos de código
	 * y estado actual de work order
	 * @param workorderReason Razón para el cambio de estado de la work order
	 * @return Obqueto que encapsula el requerimiento para la invocación de la operación
	 * del servicio web
	 * @throws BusinessException En caso de error al tratar de construir el objeto del request
	 * @author jjimenezh
	 */
	public static UpdateWorkOrderRequest buildUpdateWorkOrderRequest(String countryCode,
			 														 String woCode,
			                                                         Date agendationDate,
			                                                         Long dealerCodeIbs,
			                                                         String workorderReasonCode,
			                                                         String ibs6StateCode) throws BusinessException {
		UpdateWorkOrderRequest request = new UpdateWorkOrderRequest();
		RequestMetadataType requestMetadataType = getMetadataType(countryCode);
		request.setRequestMetadata(requestMetadataType);
		
		UpdateWorkOrder updateWorkOrder = new UpdateWorkOrder();
		
		//solo se envia la fecha en caso de ser un agendamiento.
		updateWorkOrder.setDate(UtilsBusiness.dateToGregorianCalendar(agendationDate));

		if(dealerCodeIbs != null && dealerCodeIbs.longValue() > 0 ){
			updateWorkOrder.setServiceProviderId(dealerCodeIbs.intValue());
		}
		
		updateWorkOrder.setReasonCode(workorderReasonCode);
		
		if(ibs6StateCode != null){
			validateResult("No se asignó el estado acutal a la work order para reportarlo a IBS: workOrderSdii.getWorkorderStatusByActualStatusId().getIbs6Status().getIbs6StateCode()", ibs6StateCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			updateWorkOrder.setStatusCode(ibs6StateCode);
		}
		/*validateResult("No se asignó el estado acutal a la work order para reportarlo a IBS: workOrderSdii.getWorkorderStatusByActualStatusId().getIbs6Status().getIbs6StateCode()", workOrderSdii.getWorkOrder().getWorkorderStatusByActualStatusId().getIbs6Status().getIbs6StateCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		String statusCode = workOrderSdii.getWorkOrder().getWorkorderStatusByActualStatusId().getIbs6Status().getIbs6StateCode();
		updateWorkOrder.setStatusCode(statusCode);*/
		
		updateWorkOrder.setWorkOrderKey(woCode);
		updateWorkOrder.setReturnHistoryId(true);
		request.setUpdateWorkOrder(updateWorkOrder);
		
		//Registra los parametros enviados en el log
		logUpdateWorkOrderRequest( updateWorkOrder );
		
		return request;
	}
	
	/**
	 * Metodo: Construye el requerimiento para la invocación de la operación
	 * de actualización de estado de una work order
	 * @param workOrderSdii orden de trabajo de SDII con los datos de código
	 * y estado actual de work order
	 * @param workorderReason Razón para el cambio de estado de la work order
	 * @return Obqueto que encapsula el requerimiento para la invocación de la operación
	 * del servicio web
	 * @throws BusinessException En caso de error al tratar de construir el objeto del request
	 * @author jjimenezh
	 */
	public static UpdateWorkOrderRequest buildNotifyWorkOrderAssignmentRequest(WorkOrder workOrderSdii, WorkorderReason workorderReason, Long dealerCode) throws BusinessException {
		UpdateWorkOrderRequest request = new UpdateWorkOrderRequest();
		RequestMetadataType requestMetadataType = getMetadataType(workOrderSdii.getCountry().getCountryCode());
		request.setRequestMetadata(requestMetadataType);
		
		UpdateWorkOrder updateWorkOrder = new UpdateWorkOrder();
		
		//jalopez solo se envia cuando se realiza agendamiento
		//updateWorkOrder.setDate(new XMLGregorianCalendarImpl(new GregorianCalendar()));
		
		validateResult("No se asignó el código de la razón de la WO", workorderReason.getWorkorderReasonCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		updateWorkOrder.setReasonCode(workorderReason.getWorkorderReasonCode());
		
		validateResult("No se asignó el estado acutal a la work order para reportarlo a IBS: workOrderSdii.getWorkorderStatusByActualStatusId()", workOrderSdii.getWorkorderStatusByActualStatusId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		if(workOrderSdii.getWorkorderStatusByActualStatusId().getIbs6Status() != null){
			validateResult("No se asignó el estado acutal a la work order para reportarlo a IBS: workOrderSdii.getWorkorderStatusByActualStatusId().getIbs6Status().getIbs6StateCode()", workOrderSdii.getWorkorderStatusByActualStatusId().getIbs6Status().getIbs6StateCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			String statusCode = workOrderSdii.getWorkorderStatusByActualStatusId().getIbs6Status().getIbs6StateCode();
			updateWorkOrder.setStatusCode(statusCode);
		}
		
		updateWorkOrder.setWorkOrderKey(workOrderSdii.getWoCode());
		
		if(dealerCode != null && dealerCode.longValue() > 0){
			updateWorkOrder.setServiceProviderId(dealerCode.intValue());
		}
		updateWorkOrder.setReturnHistoryId(true);
		request.setUpdateWorkOrder(updateWorkOrder);
		//Registra los parametros enviados en el log
		logNotifyWorkOrderAssignment( updateWorkOrder );
		
		return request;
	}
	
	/**
	 * Metodo: Construye el request para la invocación de un la operación
	 * para agregar un servicio a la workOrder
	 * @param serial Número de serial que se notificará a IBS
	 * @param serviceCode código del servicio
	 * @param workOrderCode código de la work order
	 * @return Objeto que encapsula la petición que se realiza a IBS
	 * @author jjimenezh
	 */
	public static AddServiceToWorkOrderRequest buildAddServiceToWorkOrderRequest(String serial, String serviceCode, String workOrderCode, String countryCode) {
		AddServiceToWorkOrderRequest request = new AddServiceToWorkOrderRequest();
		RequestMetadataType requestMetadata = getMetadataType(countryCode);
		request.setRequestMetadata(requestMetadata);
		
		AddServiceToWorkOrder data = new AddServiceToWorkOrder();
		
		if(serial != null && serial.trim().length() > 0){
			data.setSerial(serial);
		}else{
			data.setSerial("");
		}
		
		data.setServiceCode(serviceCode);
		data.setWorkOrderKey(workOrderCode);
		
		//Registra los parametros enviados en el log
		logAddServiceToWorkOrder(data);
		
		request.setAddServiceToWorkOrder(data);
		
		return request;
	}
	
	/**
	 * Metodo: Construye el request para la invocación de un la operación
	 * para borrar un servicio a la workOrder
	 * @param serviceCode código del servicio
	 * @param workOrderCode código de la work order
	 * @return Objeto que encapsula la petición que se realiza a IBS
	 * @author jjimenezh
	 */
	public static RemoveWorkOrderServiceRequest buildRemoveServiceFromWorkOrderRequest(String serviceCode, String workOrderCode, String countryCode) {
		RemoveWorkOrderServiceRequest request = new RemoveWorkOrderServiceRequest();
		
		RequestMetadataType requestMetadata = getMetadataType(countryCode);
		request.setRequestMetadata(requestMetadata);
		
		RemoveWorkOrderService data = new RemoveWorkOrderService();
		
		data.setServiceCode(serviceCode);
		data.setWorkOrderKey(workOrderCode);
		
		request.setRemoveWorkOrderService(data);
		
		//Registra los parametros enviados en el log
		logRemoveServiceFromWorkOrder(data);
		
		return request;
	}
	
	/**
	 * Metodo: Construye el objeto que encapsula la petición sobre la
	 * operación AddActivation
	 * @param workOrderCode código de la work order
	 * @param serials Lista de seriales que se reportarán como activos
	 * para esa work order
	 * @return Objeto que encapsula los parámetros necesarios para realizar
	 * la petición de registrar la activación de equipos mediante la atención
	 * de una work order
	 * @author jjimenezh
	 */
	public static AddActivationRequest buildAddActivationRequest(String workOrderCode, List<String> serials, String countryCode) {
		AddActivationRequest request = new AddActivationRequest();
		
		com.directvla.schema.util.commondatatypes.serviceconfigurationandactivation.v1_0.RequestMetadataType requestMetadata = getMetadataTypeServiceConfigurationAndActivation(countryCode);
		request.setRequestMetadata(requestMetadata);
		
		AddActivation data = new AddActivation();
		data.setWorkOrderKey(workOrderCode);
		data.getSerials().addAll(serials);
		request.setAddActivation(data);
		
		//Registra los parametros enviados en el log
		logAddActivationRequest(workOrderCode, serials);
		
		return request;
	}
	
	/**
	 * Metodo: Construye el request para la operación de cancelación de wo
	 * @param woCode código de la Work Order
	 * @param comment comentario de la cancelación
	 * @param reasonCode código de la razón de cancelación
	 * @param countryCode código del país
	 * @return Información del requerimiento para realizar la cancelación
	 * @author jjimenezh
	 */
	public static CancelWorkOrderRequest buildCancelWorkOrderRequest(String woCode, String comment, String reasonCode, String countryCode) {
		CancelWorkOrderRequest request = new CancelWorkOrderRequest();
		RequestMetadataType requestMetadata = getMetadataType(countryCode);

		CancelWorkOrder data = new CancelWorkOrder();
		data.setComment(comment);
		data.setWorkOrderKey(Integer.parseInt(woCode));
		data.setReasonCode(Integer.parseInt(reasonCode));
		data.setReturnHistoryId(true);
		//Registra los parametros enviados en el log
		logCancelWorkOrderRequest(woCode, comment, reasonCode);
		
		request.setRequestMetadata(requestMetadata);
		request.setCancelWorkOrder(data);

		return request;
	}
	
	
	/**
	 * 
	 * Metodo: Registra en el log los parametros enviados
	 * @param UpdateWorkOrder updateWorkOrder
	 * @author jalopez
	 */
	private static void logUpdateWorkOrderRequest(UpdateWorkOrder updateWorkOrder){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		//Parametros enviados
		buffer.append(" WorkOrderKey :" + updateWorkOrder.getWorkOrderKey());
		buffer.append(" StatusCode: " + updateWorkOrder.getStatusCode());
		buffer.append(" ReasonCode: " + updateWorkOrder.getReasonCode());
		buffer.append(" fecha:  " + updateWorkOrder.getDate() );				
		buffer.append(" serviceProviderId: " + updateWorkOrder.getServiceProviderId());
		
		//Construcccion del mensaje
		params[0] = "UpdateWorkOrder";
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
	 * Metodo: Registra en el log los parametros enviados
	 * @param UpdateWorkOrder updateWorkOrder
	 * @author jalopez
	 */
	private static void logNotifyWorkOrderAssignment(UpdateWorkOrder updateWorkOrder){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		//Parametros enviados
		buffer.append(" WorkOrderKey :" + updateWorkOrder.getWorkOrderKey());
		buffer.append(" StatusCode: " + updateWorkOrder.getStatusCode());
		buffer.append(" ReasonCode: " + updateWorkOrder.getReasonCode());
		buffer.append(" fecha:  " + updateWorkOrder.getDate() );				
		buffer.append(" serviceProviderId: " + updateWorkOrder.getServiceProviderId());
		
		//Construcccion del mensaje
		params[0] = "UpdateWorkOrder";
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
	 * Metodo: Registra en el log los parametros enviados
	 * @param AddServiceToWorkOrder data
	 * @author jalopez
	 */
	private static void logAddServiceToWorkOrder(AddServiceToWorkOrder data){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		//Parametros enviados
		buffer.append(" WorkOrderKey :" + data.getWorkOrderKey());
		buffer.append(" ServiceCode: " + data.getServiceCode());
		buffer.append(" Serial: " + data.getSerial());
		
		//Construcccion del mensaje
		params[0] = "AddServiceToWorkOrder";
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
	 * Metodo: Registra en el log los parametros enviados
	 * @param RemoveWorkOrderService data
	 * @author jalopez
	 */
	private static void logRemoveServiceFromWorkOrder(RemoveWorkOrderService data){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		//Parametros enviados
		buffer.append(" WorkOrderKey :" + data.getWorkOrderKey());
		buffer.append(" ServiceCode: " + data.getServiceCode());
		
		//Construcccion del mensaje
		params[0] = "RemoveWorkOrderService";
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
	 * Metodo: Registra en el log los parametros enviados
	 * @param String workOrderCode
	 * @param List<String> serials
	 * @author jalopez
	 */
	private static void logAddActivationRequest(String workOrderCode, List<String> serials){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		//Parametros enviados
		buffer.append(" WorkOrderKey :" + workOrderCode);
		buffer.append(" serials: " + serials);
		
		//Construcccion del mensaje
		params[0] = "AddActivation";
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
	 * Metodo: Registra en el log los parametros enviados
	 * @param String woCode
	 * @param String comment
	 * @param String reasonCode
	 * @author jalopez
	 */
	private static void logCancelWorkOrderRequest(String woCode, String comment, String reasonCode){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		//Parametros enviados
		buffer.append(" WorkOrderKey :" + woCode);
		buffer.append(" reasonCode: " + reasonCode);
		buffer.append(" comment: " + comment);
		
		//Construcccion del mensaje
		params[0] = "CancelWorkOrder";
		params[1] = buffer.toString();
		message.append("== ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getCode() );
		message.append(" : ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getMessage(params) );
		message.append(" ==");
		
		log.info( message.toString() );
	}
	/**
	 * Metodo: Obtiene la cabecera que se usará para la invocación de cualquiera de las operaciones
	 * expuestas por el servicio web de core en ibs
	 * @param countryCode código del país
	 * @return Cabecera que será usada en la invocación de operaciones del rervicio web de core
	 * @author jjimenezh
	 */
	private static RequestMetadataType getMetadataType(String countryCode){
		Random r = new Random();
		RequestMetadataType requestMetadataType = new RequestMetadataType();
		int requestId =  r.nextInt(1000000);
		requestMetadataType.setRequestID(requestId+"");
		requestMetadataType.setSourceID(countryCode);
		requestMetadataType.setAppID(false);
		requestMetadataType.setValidate(false);
		requestMetadataType.setAppID(false);
		try {
			requestMetadataType.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
		} catch (Throwable ex) {
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operaciÃ³n sendESBDispatchWorkOrderMessage/DistributedQueueMessageBrokerImpll ==");
		} finally {
			log.debug("== Termina la operaciÃ³n getMetadataType/CoreWorkOrderServiceTOA ==");
		}
		return requestMetadataType;
	}
	
	private static com.directvla.schema.util.commondatatypes.serviceconfigurationandactivation.v1_0.RequestMetadataType getMetadataTypeServiceConfigurationAndActivation(String countryCode){
		Random r = new Random();
		com.directvla.schema.util.commondatatypes.serviceconfigurationandactivation.v1_0.RequestMetadataType requestMetadataType = new com.directvla.schema.util.commondatatypes.serviceconfigurationandactivation.v1_0.RequestMetadataType();
		int requestId =  r.nextInt(1000000);
		requestMetadataType.setRequestID(requestId+"");
		requestMetadataType.setSourceID(countryCode);
		requestMetadataType.setValidate(false);
		try {
			requestMetadataType.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
		} catch (Throwable ex) {
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getMetadataType/CoreWorkOrderServiceTOA ==");
		} finally {
			log.debug("== Termina la operaciÃ³n sendESBDispatchWorkOrderMessage/DistributedQueueMessageBrokerImpll ==");
		}
		return requestMetadataType;
	}
	
	/**
	 * Metodo: Valida un objeto determinando si es nulo, en caso que sea nulo lanza
	 * una excepción y escribe en el log el atributo que fué validado
	 * @param parameterName Nombre del atributo a ser validado o mensaje para
	 * ser escrito en el log en caso que el objeto sea nulo
	 * @param value2Validate objeto a ser validado
	 * @param errorCode código de error a ser lanzado
	 * @param errorMessage mensaje de error a ser lanzado
	 * @throws BusinessException En caso que el objeto a validar sea nulo
	 * @author jjimenezh
	 */
	private static void validateResult(String parameterName, Object value2Validate, String errorCode, String errorMessage) throws BusinessException{
		try {
			UtilsBusiness.assertNotNull(value2Validate, errorCode, errorMessage + " nombre del parámetro: " + parameterName);
		} catch (BusinessException e) {
			log.debug("== Error de validación de parámetros de respuesta de un WS de ibs: el parámetro: \""+parameterName+"\" llegó nulo ==");
			e.printStackTrace();
			throw e;
		}
	}
}
