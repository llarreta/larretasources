package co.com.directv.sdii.ejb.business.broker.toa;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.BaseDTO;
import co.com.directv.sdii.model.dto.SwopsDTORequest;
import co.com.directv.sdii.model.dto.UpgradeAndDowngradeDTORequest;
import co.com.directv.sdii.model.dto.WOAttentionElementsRequestDTO;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceElementVO;

import com.directvla.schema.businessdomain.common.resourceprovisioning.v1_0.FunctionOrProcessProvider;
import com.directvla.schema.businessdomain.common.resourceprovisioning.v1_0.MovementCategoryType;
import com.directvla.schema.businessdomain.common.resourceprovisioning.v1_0.PhysicalResource;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.AddIRDChanges;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.AddIRDChangesRequest;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.GetSwopsRequest;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.GetSwopsType;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.GetUpgradeDowngradeRequest;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.GetUpgradeDowngradeType;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.UpdateResourceStatus;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.UpdateResourceStatusRequest;
import com.directvla.schema.util.commondatatypes.resourceprovisioning.v1_0.RequestMetadataType;

/**
 * 
 * Clase encargada de obtner objetos de negocio
 * basados en objetos de de Contratos de Web Services
 * 
 * Fecha de Creación: 30/06/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public final class ResourceProvisioningServiceTOA {

	private ResourceProvisioningServiceTOA(){}
	
	private final static Logger log = UtilsBusiness.getLog4J(ResourceProvisioningServiceTOA.class);
	
	/**
	 * 
	 * Metodo: Pobla la informacion necesaria para
	 * consumir el servicio
	 * @param woAttentionDTO WOAttentionElementsRequestDTO
	 * @return AddIRDChangesRequest
	 * @throws BusinessException
	 * @author jalopez
	 * @throws PropertiesException 
	 */
	public static AddIRDChangesRequest populateAddIRDChangesRequest(WOAttentionElementsRequestDTO woAttentionDTO, SystemParameter reasonNew, SystemParameter reasonOld) throws BusinessException, PropertiesException {
		AddIRDChangesRequest request = new AddIRDChangesRequest();
		AddIRDChanges addIrdChange = new AddIRDChanges();
		
		validateAddIRDChangesRequest(woAttentionDTO);
		
		
		
		addIrdChange.setWorkOrderID( Integer.parseInt( woAttentionDTO.getWorkorderVo().getWoCode() ) ); //Codigo de la WorkOrder
		addIrdChange.setStockhandlerId( woAttentionDTO.getDealerCode().intValue()  ); //Codigo del dealer
		addIrdChange.setReasonIdNew(Integer.parseInt(reasonNew.getValue() ) );
		addIrdChange.setReasonIdOld(Integer.parseInt(reasonOld.getValue()) );
		addIrdChange.getNewSerials().addAll( getSerials(woAttentionDTO.getInstallationSerializedElements()) ); //Seriales Instalados
		addIrdChange.getPreviousSerials().addAll( getSerials(woAttentionDTO.getRecoverySerializedElements()) ); //Seriales Recuperados
		
		request.setAddIRDChanges( addIrdChange );
		request.setRequestMetadata( getMetadataType(woAttentionDTO) );
		
		logAddIRDChangesRequest(request);
		return request;
	}
	
	/**
	 * 
	 * Metodo: Construye el encabezado para la operacion de getSwops
	 * @param swopsDTO DTO que contiene informacion necesaria para crear el encabezado
	 * @return <tipo> <descripcion>
	 * @author
	 */
				   
	private static com.directvla.schema.util.commondatatypes.resourceprovisioning.v1_0.RequestMetadataType getMetadataType(BaseDTO swopsDTO){
		com.directvla.schema.util.commondatatypes.resourceprovisioning.v1_0.RequestMetadataType requestMetadataType = new com.directvla.schema.util.commondatatypes.resourceprovisioning.v1_0.RequestMetadataType();		
		requestMetadataType.setRequestID( swopsDTO.getCountryCode() );
		requestMetadataType.setSourceID( swopsDTO.getCountryCode() );
		requestMetadataType.setValidate(false);
		try {
			requestMetadataType.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
		} catch (PropertiesException e) {
			log.debug("== Termina la operaciÃ³n getMetadataType/ResourceProvisioningServiceTOA ==");
			e.printStackTrace();
		}
		return requestMetadataType;
	}
	
	/**
	 * 
	 * Metodo: Construye el objeto para invocar la operacion GetSwops
	 * @param swopsDTO Objeto con informacion necesaria para crear el request
	 * @return GetSwopsRequest Request para operacion sobre IBS
	 * @throws BusinessException
	 * @author jnova 
	 */
	public static GetSwopsRequest populateGetSwopsRequest( SwopsDTORequest swopsDTO ) throws BusinessException{
		GetSwopsRequest request = new GetSwopsRequest();
		validateGetSwopsRequest( swopsDTO );
		request.setRequestMetadata( getMetadataType( swopsDTO ) );
		GetSwopsType swopsType = new GetSwopsType();
		swopsType.setCreateDatetime( UtilsBusiness.dateToGregorianCalendar( swopsDTO.getCreateDateTime() ) );
		swopsType.setCustomerKey( swopsDTO.getCustomerCode() );
		request.setGetSwops( swopsType );
		logGetSwops(swopsDTO);
		return request;
	}
	
	/**
	 * 
	 * Metodo: Construye el objeto para invocar la operacion GetUpgradeDowngrade
	 * @param upgradeAndDowngradeDTORequest Objeto con informacion necesaria para crear el request
	 * @return GetUpgradeDowngradeRequest Request para operacion sobre IBS
	 * @throws BusinessException
	 * @author Aharker 
	 */
	public static GetUpgradeDowngradeRequest populateGetUpgradeAndDowngradeRequest( UpgradeAndDowngradeDTORequest upgradeAndDowngradeDTORequest ) throws BusinessException{
		GetUpgradeDowngradeRequest request = new GetUpgradeDowngradeRequest();
		validateGetUpgradeDowngradeRequest( upgradeAndDowngradeDTORequest );
		request.setRequestMetadata( getMetadataType( upgradeAndDowngradeDTORequest ) );
		GetUpgradeDowngradeType upgradeDowngradeType = new GetUpgradeDowngradeType();
		upgradeDowngradeType.setCreateDatetime( UtilsBusiness.dateToGregorianCalendar( upgradeAndDowngradeDTORequest.getCreateDateTime() ) );
		upgradeDowngradeType.setCustomerKey( upgradeAndDowngradeDTORequest.getCustomerCode() );
		request.setGetUpgradeDowngrade(upgradeDowngradeType);
		logService(upgradeAndDowngradeDTORequest, "GetUpgradeDowngrade");
		return request;
	}
	
	/**
	 * 
	 * Metodo: Validacion de valores obligatorios para crear el request de GetSwopd
	 * @param swopsDTO objeto a validar
	 * @throws BusinessException En caso que el objeto no contenga un valor obligatorio
	 * @author jnova
	 */
	public static void validateGetSwopsRequest(SwopsDTORequest swopsDTO) throws BusinessException{
		Object[] params = null;
		params = new Object[2];	
	
		params[1] = "validateGetSwopsRequest";		
		params[0] = "swopsDTO";
		UtilsBusiness.validateRequestResponseWebService(params,swopsDTO, ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "countryCode";
		UtilsBusiness.validateRequestResponseWebService(params,swopsDTO.getCountryCode(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "customerCode";
		UtilsBusiness.validateRequestResponseWebService(params,swopsDTO.getCustomerCode(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "createDateTime";
		UtilsBusiness.validateRequestResponseWebService(params,swopsDTO.getCreateDateTime(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
	}
	
	/**
	 * 
	 * Metodo: Validacion de valores obligatorios para crear el request de GetUpgradeDowngrade
	 * @param upgradeAndDowngradeDTO objeto a validar
	 * @throws BusinessException En caso que el objeto no contenga un valor obligatorio
	 * @author aharker
	 */
	public static void validateGetUpgradeDowngradeRequest(UpgradeAndDowngradeDTORequest upgradeAndDowngradeDTO) throws BusinessException{
		Object[] params = null;
		params = new Object[2];	
	
		params[1] = "validateGetUpgradeDowngradeRequest";		
		params[0] = "upgradeAndDowngradeDTO";
		UtilsBusiness.validateRequestResponseWebService(params,upgradeAndDowngradeDTO, ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "requestID";
		UtilsBusiness.validateRequestResponseWebService(params,upgradeAndDowngradeDTO.getCountryCode(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "customerKey";
		UtilsBusiness.validateRequestResponseWebService(params,upgradeAndDowngradeDTO.getCustomerCode(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "createDatetime";
		UtilsBusiness.validateRequestResponseWebService(params,upgradeAndDowngradeDTO.getCreateDateTime(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
	}
	
	/**
	 * 
	 * Metodo: Retorna una lista con los seriales
	 * @param request
	 * @return List<String>
	 * @author jalopez
	 */
	public static List<String> getSerials(List<WorkOrderServiceElementVO> request) {
		List<String> serials = new ArrayList<String>();
		for (WorkOrderServiceElementVO workOrderServiceElementVO : request) {			
			serials.add(workOrderServiceElementVO.getElementSerial());
			//jnova se comenta ya que solo se envia uno por invocacion y desde el business se controla que se mande
			//serials.add(workOrderServiceElementVO.getLinkedWOServiceElement().getElementSerial());			
		}
		return serials;
	}
	
	/**
	 * 
	 * Metodo: Retorna el metadata del servicio.
	 * @param WOAttentionsRequestDTO woAttentionDTO
	 * @return RequestMetadataType
	 * @author jalopez
	 */
	private static com.directvla.schema.util.commondatatypes.resourceprovisioning.v1_0.RequestMetadataType getMetadataType(WOAttentionElementsRequestDTO woAttentionDTO){
		com.directvla.schema.util.commondatatypes.resourceprovisioning.v1_0.RequestMetadataType requestMetadataType = new com.directvla.schema.util.commondatatypes.resourceprovisioning.v1_0.RequestMetadataType();		
		requestMetadataType.setRequestID( woAttentionDTO.getCountryCode() );
		requestMetadataType.setSourceID( woAttentionDTO.getCountryCode() );
		requestMetadataType.setValidate(false);
		try {
			requestMetadataType.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
		} catch (PropertiesException e) {
			log.debug("== Termina la operaciÃ³n getMetadataType/ResourceProvisioningServiceTOA ==");
			e.printStackTrace();
		}
		return requestMetadataType;
	}
	
	/**
	 * 
	 * Metodo: valida los parametros requeridos
	 * para el consumo del servicio.
	 * @param woAttentionDTO WOAttentionElementsRequestDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public static void validateAddIRDChangesRequest(WOAttentionElementsRequestDTO woAttentionDTO) throws BusinessException{
		Object[] params = null;
		params = new Object[2];	
	
		params[1] = "validateAddIRDChangesRequest";		
		params[0] = "woAttentionDTO";
		UtilsBusiness.validateRequestResponseWebService(params,woAttentionDTO, ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "WorkorderVo";
		UtilsBusiness.validateRequestResponseWebService(params,woAttentionDTO.getWorkorderVo(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "WoCode";
		UtilsBusiness.validateRequestResponseWebService(params,woAttentionDTO.getWorkorderVo().getWoCode(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "DealerCode";
		UtilsBusiness.validateRequestResponseWebService(params,woAttentionDTO.getDealerCode(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));		
		params[0] = "SwapFallaTecnicaNew";
		UtilsBusiness.validateRequestResponseWebService(params,woAttentionDTO.getSwapFallaTecnicaNew(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "SwapFallaTecnicaOld";
		UtilsBusiness.validateRequestResponseWebService(params,woAttentionDTO.getSwapFallaTecnicaOld(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
	}
	
	
	/**
	 * 
	 * Metodo: Genera el log de los valores enviado a la DTO de un servicio de forma generica
	 * @param genericDTO Objeto que contiene informacion enviada al servicio
	 * @author aharker
	 */
	public static void logService(Object genericDTO, String nameService){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		Field[] fields = null;
		try{
			fields = genericDTO.getClass().getFields();
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(fields != null){
			if(fields.length>0){
				for(int i=0; i<fields.length; ++i){
					try{
						buffer.append(fields[i].getName()+" : ");
				    	String nameMethod = "get";
				    	String nameField = fields[i].getName();
				    	char firstLetter=fields[i].getName().charAt(0);
				    	if(firstLetter>='a' && firstLetter<='z'){
				    		int firstLetterNew = firstLetter - 32;
				    	    char newFirstLetter = (char)firstLetterNew;
				    	        nameField = newFirstLetter + fields[i].getName().substring(1);
				    	}
				    	nameMethod += nameField;
				    	Object info = genericDTO.getClass().getMethod(nameMethod, null).invoke(genericDTO, null);
				    	buffer.append(info);						
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		}
		params[0] = nameService;
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
	 * Metodo: Genera el log de los valores enviado al servicio GetSwops
	 * @param swopsDTO Objeto que contiene informacion enviada a servicio GetSwops
	 * @author jnova
	 */
	public static void logGetSwops(SwopsDTORequest swopsDTO){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		buffer.append("requestID : ");
		buffer.append(swopsDTO.getCountryCode());
		buffer.append(" sourceID ");
		buffer.append(swopsDTO.getCountryCode());
		buffer.append(" CreateDatetime ");
		buffer.append( UtilsBusiness.dateToString(swopsDTO.getCreateDateTime(), UtilsBusiness.DATE_FORMAT_DDMMYYYYHHMMSS) );
		buffer.append(" CustomerKey ");
		buffer.append( swopsDTO.getCustomerCode() );
		
		//Construcccion del mensaje
		params[0] = "GetSwops";
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
	 * Metodo: Escribe en el log
	 * la informacion enviada al servicio.
	 * @param request
	 * @author jalopez
	 */
	public static void logAddIRDChangesRequest(AddIRDChangesRequest request){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();		
		StringBuilder serialsInstall = new StringBuilder();
		StringBuilder serialsRecovery = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		for (String serial : request.getAddIRDChanges().getNewSerials()) {
			serialsInstall.append(serial);
			serialsInstall.append(", ");
		}
		for (String serial : request.getAddIRDChanges().getPreviousSerials()) {
			serialsRecovery.append(serial);
			serialsRecovery.append(", ");
		}
		
		//Parametros enviados
		buffer.append(" WorkOrderID :" + request.getAddIRDChanges().getWorkOrderID() );
		buffer.append(" StockhandlerId :" + request.getAddIRDChanges().getStockhandlerId() );	
		buffer.append(" ReasonIdNew :" + request.getAddIRDChanges().getReasonIdNew() );
		buffer.append(" ReasonIdOld :" + request.getAddIRDChanges().getReasonIdOld() );
		buffer.append(" NewSerials :[" + serialsInstall.toString() + "]");
		buffer.append(" PreviousSerials :[" + serialsRecovery.toString() + "]");
		buffer.append(logMetadata(request.getRequestMetadata()));
		
		//Construcccion del mensaje
		params[0] = "AddIRDChanges";
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
	private static String logMetadata(com.directvla.schema.util.commondatatypes.resourceprovisioning.v1_0.RequestMetadataType requestMetadataType){
		StringBuilder buffer = new StringBuilder();
		buffer.append(" Metadata [");
		buffer.append(" SystemID: "+requestMetadataType.getSystemID());
		buffer.append(" RequestID: "+requestMetadataType.getRequestID());
		buffer.append(" SourceID: "+requestMetadataType.getSourceID());
		buffer.append(" ]");
		return buffer.toString();
	}

	/**
	 * Este metodo se encarga de generar la metadata para los request de las operaciones del servicio resourceprovisioning
	 * @param countryCode codigo del pais que se desea enviar en la metadata del request
	 * @return La metadata generica para los request del servico resourceprovisioning con el codigo del pais indicado
	 * @author Aharker
	 */
	private static RequestMetadataType getMetadataType(String countryCode){
		RequestMetadataType requestMetadataType = new RequestMetadataType();		
		requestMetadataType.setRequestID(countryCode);
		requestMetadataType.setSourceID(countryCode);
		try {
			requestMetadataType.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
		} catch (PropertiesException e) {
			log.debug("== Termina la operaciÃ³n getMetadataType/ResourceProvisioningServiceTOA ==");
			e.printStackTrace();
		}
		return requestMetadataType;
	}
	
	/**
	 * 
	 * @param wh bodega de la cual se quiere extraer el codigo del dueño de la 
	 * @return
	 * @throws BusinessException
	 */
	private static Long getWhOwnerCode(Warehouse wh) throws BusinessException {
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
	
	public static UpdateResourceStatusRequest buildUpdateResourceStatus(MovCmdQueueVO movCmdQueueVO) throws BusinessException {
		UpdateResourceStatusRequest requestReturn = new UpdateResourceStatusRequest();
		requestReturn.setRequestMetadata(getMetadataType(movCmdQueueVO.getCountry().getCountryCode()));
		requestReturn.setUpdateResourceStatus(new UpdateResourceStatus());
		requestReturn.getUpdateResourceStatus().setStockHandler(new FunctionOrProcessProvider());
		
		if( movCmdQueueVO.getTargetWarehouse() != null) {
			Long sourceWhId = movCmdQueueVO.getTargetWarehouse().getId();
			Long dealerOwnerSourceCode = 0L;
			if(sourceWhId != null && sourceWhId.longValue() > 0){
				dealerOwnerSourceCode = getWhOwnerCode(movCmdQueueVO.getTargetWarehouse());
				requestReturn.getUpdateResourceStatus().getStockHandler().setPartyRoleId(dealerOwnerSourceCode.toString());
			}
		}
		
		requestReturn.getUpdateResourceStatus().setMovementCategory(new MovementCategoryType());
		
		if( movCmdQueueVO!=null && movCmdQueueVO.getMovCmdConfig()!=null && movCmdQueueVO.getMovCmdConfig().getUpdateStatusReaons() != null 
				&& movCmdQueueVO.getMovCmdConfig().getUpdateStatusReaons().getReasonId()!=null){
			requestReturn.getUpdateResourceStatus().getMovementCategory().setMovement(movCmdQueueVO.getMovCmdConfig().getUpdateStatusReaons()
					.getReasonId().toString());
		}
		
		requestReturn.getUpdateResourceStatus().setPhysicalResource(new PhysicalResource());
		
		requestReturn.getUpdateResourceStatus().getPhysicalResource().setSerialNumber(movCmdQueueVO!=null ? movCmdQueueVO.getSerialized() != null ? 
				movCmdQueueVO.getSerialized().getSerialCode() : null : null);
		
		if(movCmdQueueVO!=null && movCmdQueueVO.getSerialized()!=null && movCmdQueueVO.getSerialized().getSerialized()!=null){
			requestReturn.getUpdateResourceStatus().setPhysicalResourceAssociated(new PhysicalResource());
			requestReturn.getUpdateResourceStatus().getPhysicalResourceAssociated().setSerialNumber(movCmdQueueVO.getSerialized().getSerialized().getSerialCode());
		}
		
		return requestReturn;
		
	}

}
