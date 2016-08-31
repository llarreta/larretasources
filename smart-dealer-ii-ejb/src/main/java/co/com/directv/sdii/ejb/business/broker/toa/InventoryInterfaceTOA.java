package co.com.directv.sdii.ejb.business.broker.toa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.ElementDTO;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateDetailResponse;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateResponse;
import co.com.directv.sdii.model.dto.InventoryDTO;
import co.com.directv.sdii.model.dto.RequiredServiceElementDTO;
import co.com.directv.sdii.model.dto.WOAttentionElementsRequestDTO;
import co.com.directv.sdii.model.pojo.Element;
import co.com.directv.sdii.model.pojo.ElementModel;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.RequiredServiceElement;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.vo.RequiredServiceElementVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceElementVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceVO;

import com.directvla.inventoryinterface.CtgetNotSerializedResourceRequest;
import com.directvla.inventoryinterface.CtgetResourcesByServiceTypeRequest;
import com.directvla.inventoryinterface.CtgetResourcesByServiceTypeResponse;
import com.directvla.inventoryinterface.CtgetSerializedResourceRequest;
import com.directvla.inventoryinterface.CtregisterNotSerializedResourceRequest;
import com.directvla.inventoryinterface.CtregisterSerializedResourceRequest;
import com.directvla.inventoryinterface.GetNotSerializedResourceRequest;
import com.directvla.inventoryinterface.GetNotSerializedResourceResponse;
import com.directvla.inventoryinterface.GetResourcesByServiceTypeRequest;
import com.directvla.inventoryinterface.GetResourcesByServiceTypeResponse;
import com.directvla.inventoryinterface.GetSerializedResourceRequest;
import com.directvla.inventoryinterface.GetSerializedResourceResponse;
import com.directvla.inventoryinterface.RegisterNotSerializedResourceRequest;
import com.directvla.inventoryinterface.RegisterNotSerializedResourceResponse;
import com.directvla.inventoryinterface.RegisterSerializedResourcesRequest;
import com.directvla.inventoryinterface.RegisterSerializedResourcesResponse;
import com.directvla.schema.businessdomain.common.inventoryinterface.v1_0.BusinessInteractionType;
import com.directvla.schema.businessdomain.common.inventoryinterface.v1_0.Category;
import com.directvla.schema.businessdomain.common.inventoryinterface.v1_0.FunctionOrProcessProvider;
import com.directvla.schema.businessdomain.common.inventoryinterface.v1_0.Quantity;
import com.directvla.schema.businessdomain.common.inventoryinterface.v1_0.ServiceProvider;
import com.directvla.schema.businessdomain.service.inventoryinterface.v1_0.Service;
import com.directvla.schema.inventorydomain.common.inventoryinterface.v1_0.ArrayOfPhysicalResourceItem;
import com.directvla.schema.inventorydomain.common.inventoryinterface.v1_0.PhysicalResourceItem;
import com.directvla.schema.inventorydomain.common.inventoryinterface.v1_0.ServiceRegisterResource;
import com.directvla.schema.inventorydomain.common.inventoryinterface.v1_0.WorkOrderPhysicalResources;
import com.directvla.schema.util.commondatatypes.inventoryinterface.v1_0.RequestMetadataType;

/**
 * 
 * Clase encargada de obtner objetos de negocio
 * basados en objetos de de Contratos de Web Services
 * 
 * Fecha de Creación: 7/06/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public final class InventoryInterfaceTOA {
	
	private InventoryInterfaceTOA(){}
	
	private final static Logger log = UtilsBusiness.getLog4J(InventoryInterfaceTOA.class);
	
	/**
	 * 
	 * Metodo: Retorna la peticion
	 * del servicio en un objeto de tipo
	 * contrato.
	 * @param inventoryDTO InventoryDTO
	 * @return GetSerializedResourceRequest
	 * @author jalopez
	 */
	public static GetSerializedResourceRequest populateSerializedResourceInventory(InventoryDTO inventoryDTO){
		GetSerializedResourceRequest request = new GetSerializedResourceRequest();
		CtgetSerializedResourceRequest serializedResource = new CtgetSerializedResourceRequest();
		
		serializedResource.setSerialNumber( inventoryDTO.getElementDTO().getSerializedVO().getSerialCode() );//Serial del Elemento
		
		request.setGetSerializedResourceRequest(serializedResource);
		request.setRequestMetadata( InventoryInterfaceTOA.getRequestMetadata(inventoryDTO) );
		//Log
		logGetSerializedResourceRequest(inventoryDTO);
		return request;
	}
	
	/**
	 * 
	 * Metodo: Retorna la respuesta del
	 * servicio en un objeto de tipo 
	 * negocio
	 * @param response GetSerializedResourceResponse
	 * @return ElementDTO
	 * @throws PropertiesException
	 * @throws BusinessException
	 * @author jalopez
	 */
	public static ElementDTO populateSerializedResourceElement(GetSerializedResourceResponse response) throws PropertiesException, BusinessException{
		ElementDTO elementDTO = new ElementDTO();
		Serialized linkedSerialized = null;	
		InventoryInterfaceTOA.validateSerializedResource(response);
		PhysicalResourceItem physicalResource = response.getGetSerializedResourceResponse().getPhysicalResourceItem();	
		InventoryInterfaceTOA.validatePhysicalResourceItem(physicalResource);
		Serialized serialized = InventoryInterfaceTOA.populateSerializedElement( physicalResource );
			
		if(physicalResource.getLinkedResource() != null){
			for (PhysicalResourceItem resource : physicalResource.getLinkedResource()) {				
				InventoryInterfaceTOA.validatePhysicalLinkedResourceItem(resource);
				linkedSerialized = InventoryInterfaceTOA.populateSerializedElement( resource );
			}						
		}
		
		elementDTO.setSerializedVO( UtilsBusiness.copyObject(SerializedVO.class, serialized) );
		elementDTO.getSerializedVO().setSerialized( linkedSerialized );
		return elementDTO;
	}
	
	/**
	 * 
	 * Metodo: Pobla la informacion de respuesta
	 * del objeto serializado
	 * @param physicalResource PhysicalResourceItem
	 * @return SerializedVO
	 * @throws PropertiesException
	 * @author jalopez
	 */
	public static SerializedVO populateSerializedElement (PhysicalResourceItem physicalResource) throws PropertiesException{
		SerializedVO serialized = new SerializedVO();	
		Element element = new Element();
		ElementType elementType = new ElementType();		
		ElementModel elementModel = new ElementModel();
		
		//Se valida que venga el modelo para el caso del elemento vinculado (Smartcard)
		//las SmartCard no tienen modelo sobre el sistema de FieldService
		String modelName = "";		
		if(physicalResource.getDeviceModel() != null){
			modelName = physicalResource.getDeviceModel().getName();
		}
		
		elementModel.setModelName(modelName);
		elementType.setId(new Long(physicalResource.getDeviceType().getId()));
		elementType.setTypeElementCode(physicalResource.getDeviceType().getName());	
		elementType.setIsSerialized(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
		elementType.setElementModel(elementModel);
		element.setElementType(elementType);		
		serialized.setSerialCode(physicalResource.getSerialNumber().trim());
		serialized.setElement(element);
		serialized.setDeviceTypeId( physicalResource.getDeviceType().getId() );
		
		return serialized;
	}
	
	/**
	 * 
	 * Metodo: Retorna la peticion
	 * del servicio en un objeto de tipo
	 * contrato.
	 * @param inventoryDTO InventoryDTO
	 * @return GetNotSerializedResourceRequest
	 * @author jalopez
	 */
	public static GetNotSerializedResourceRequest populateNotSerializedResourceInventory(InventoryDTO inventoryDTO){
		GetNotSerializedResourceRequest request = new GetNotSerializedResourceRequest();
		CtgetNotSerializedResourceRequest notSerializedResource = new CtgetNotSerializedResourceRequest();
		FunctionOrProcessProvider processProvider = new FunctionOrProcessProvider();
		Quantity quantity = new Quantity();
		Category category = new Category();
		
		category.setId(inventoryDTO.getElementDTO().getElementTypeCode());//Codigo del Elemento
		quantity.setAmount(new BigDecimal( inventoryDTO.getElementDTO().getQuantity() ));//Cantidad del elemento
		processProvider.setCommisionType( inventoryDTO.getDealer().getDepotCode() );//IBS Dealer
		notSerializedResource.setAdminCrew( inventoryDTO.getEmployeeCrew().getEmployee().getDocumentNumber() );//Cedula del Tecnico
		notSerializedResource.setCustomerCode( inventoryDTO.getCustomer().getCustomerCode() );//IBS del Cliente
		notSerializedResource.setDeviceType(category);
		notSerializedResource.setQuanity(quantity);
		notSerializedResource.setStockHandler( processProvider );
		
		request.setGetNotSerializedResourceRequest(notSerializedResource);
		request.setRequestMetadata( InventoryInterfaceTOA.getRequestMetadata(inventoryDTO) );
		//Log
		logGetNotSerializedResource(inventoryDTO);
		return request;
	}
	
	/**
	 * 
	 * Metodo: Retorna la respuesta del
	 * servicio en un objeto de tipo 
	 * negocio
	 * @param response GetNotSerializedResourceResponse
	 * @return ElementDTO
	 * @author jalopez
	 */
	public static ElementDTO populateNotSerializedResourceElement(GetNotSerializedResourceResponse response){
		ElementDTO elementDTO = new ElementDTO();
		
		return elementDTO;
	}
	
	/**
	 * 
	 * Metodo: Retorna la peticion
	 * del servicio en un objeto de tipo
	 * contrato.
	 * @param inventoryDTO InventoryDTO
	 * @return GetResourcesByServiceTypeRequest
	 * @author jalopez
	 */ 
	public static GetResourcesByServiceTypeRequest populateResourcetsByServiceTypeInventory(InventoryDTO inventoryDTO){
		GetResourcesByServiceTypeRequest request = new GetResourcesByServiceTypeRequest();
		CtgetResourcesByServiceTypeRequest resourceByService = new CtgetResourcesByServiceTypeRequest();
		Service service = new Service();
		
		service.setCode(inventoryDTO.getServiceDTO().getServiceCode());
		resourceByService.setService(service);		
		
		request.setGetResourcesByServiceTypeRequest(resourceByService);
		request.setRequestMetadata( InventoryInterfaceTOA.getRequestMetadata(inventoryDTO) );
		
		//Log
		logResourcesByServiceType(inventoryDTO);
		return request;
	}
	
	/**
	 * 
	 * Metodo: Retorna la respuesta del
	 * servicio en un objeto de tipo 
	 * negocio
	 * @param response   GetResourcesByServiceTypeResponse
	 * @return RequiredServiceElementDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public static RequiredServiceElementDTO populateResourcetsByServiceTypeElement(GetResourcesByServiceTypeResponse response) throws BusinessException{
		List<RequiredServiceElement> requiredList = new ArrayList<RequiredServiceElement>();
		RequiredServiceElementDTO requiredElementDTO = new RequiredServiceElementDTO();
		RequiredServiceElementVO requiredElementVO = new RequiredServiceElementVO();
		RequiredServiceElement requiredElement = null;
		ElementType elementType = null;
		
		CtgetResourcesByServiceTypeResponse resourceService =  response.getGetResourcesByServiceTypeResponse();
		
		if(resourceService.getPhysicalResourceList() != null){
			if(!resourceService.getPhysicalResourceList().getPhysicalResource().isEmpty()){
				for (PhysicalResourceItem item : resourceService.getPhysicalResourceList().getPhysicalResource()) {
					requiredElement = new RequiredServiceElement();
					elementType = new ElementType();
					InventoryInterfaceTOA.validateResourcetsByServiceType(item);
					elementType.setId( new Long(item.getDeviceType().getId()) );
					elementType.setId( new Long(item.getProductID()) );
					elementType.setTypeElementCode( item.getOtherIdentifier() );					
					elementType.setTypeElementName( item.getCommonName() );
					elementType.setTypeElementDescription( item.getDescription() );
					requiredElement.setElementType( elementType );
					requiredList.add( requiredElement );
				}
			}	
		}
				
		requiredElementVO.setNotSerializedElements(requiredList);
		requiredElementDTO.setRequiredServiceElement(requiredElementVO);
		return requiredElementDTO;
	}
	
	/**
	 * 
	 * Metodo: Retorna la peticion
	 * del servicio en un objeto de tipo
	 * contrato.
	 * @param inventoryDTO InventoryDTO
	 * @return RegisterSerializedResourcesRequest
	 * @author jalopez
	 */
	public static RegisterSerializedResourcesRequest populateRegisterSerializedResourcesInventory(InventoryDTO inventoryDTO) {
		RegisterSerializedResourcesRequest request = new RegisterSerializedResourcesRequest();
		CtregisterSerializedResourceRequest serializedRequest = new CtregisterSerializedResourceRequest();
		
		WorkOrderPhysicalResources woPhysical = new WorkOrderPhysicalResources();
		ServiceProvider serviceProvider = new ServiceProvider();
		ServiceRegisterResource registerResource = null;
		ArrayOfPhysicalResourceItem arrayOfPhysicalResourceItem = null;
		PhysicalResourceItem physicalResource = null;
		BusinessInteractionType businessType = new BusinessInteractionType();		
		
		serviceProvider.setID( inventoryDTO.getDealer().getDealerCode().toString() ); //Dealer
		if(inventoryDTO.getAttentionElementsList() != null && !inventoryDTO.getAttentionElementsList().isEmpty())
		{
			woPhysical.setID( inventoryDTO.getAttentionElementsList().get(0).getWorkorderVo().getWoCode() ); //Codigo de la WorkOrder
			woPhysical.setServiceProvider(serviceProvider);
			//Enviar tipo de workorder
			businessType.setId( inventoryDTO.getAttentionElementsList().get(0).getWorkorderVo().getWoTypeByWoTypeId().getWoTypeCode() );
			woPhysical.setType(businessType);
			
			for(WOAttentionElementsRequestDTO attentionElements : inventoryDTO.getAttentionElementsList())
			{			
				registerResource = new ServiceRegisterResource();
				arrayOfPhysicalResourceItem = new ArrayOfPhysicalResourceItem();
				
				//Elementos Instalados
				if ( attentionElements.getInstallationSerializedElements() != null && !attentionElements.getInstallationSerializedElements().isEmpty() ) {
					for (WorkOrderServiceElementVO workOrderServiceElementVO : attentionElements.getInstallationSerializedElements()) {
						//datos del decodificador
						physicalResource = new PhysicalResourceItem();		
						physicalResource.setSerialNumber( workOrderServiceElementVO.getElementSerial() ); //Serial del elemento
						physicalResource.setProductID( String.valueOf( workOrderServiceElementVO.getElementTypeId() ) ); //Codigo del Elemento	
						arrayOfPhysicalResourceItem.getPhysicalResource().add( physicalResource );
						
						//datos de la smartcard
						physicalResource = new PhysicalResourceItem();		
						physicalResource.setSerialNumber( workOrderServiceElementVO.getLinkedWOServiceElement().getElementSerial() ); //Serial del elemento
						physicalResource.setProductID( String.valueOf( workOrderServiceElementVO.getLinkedWOServiceElement().getElementTypeId() ) ); //Codigo del Elemento	
						arrayOfPhysicalResourceItem.getPhysicalResource().add( physicalResource );
						
					}
				}
				//Elementos Recuperados
				if ( attentionElements.getRecoverySerializedElements()!= null && !attentionElements.getRecoverySerializedElements().isEmpty() ) {
					for (WorkOrderServiceElementVO workOrderServiceElementVO : attentionElements.getRecoverySerializedElements()) {
						//datos del decodificador
						physicalResource = new PhysicalResourceItem();		
						physicalResource.setSerialNumber( workOrderServiceElementVO.getElementSerial() ); //Serial del elemento
						physicalResource.setProductID( String.valueOf( workOrderServiceElementVO.getElementTypeId() ) ); //Codigo del Elemento		
						arrayOfPhysicalResourceItem.getPhysicalResource().add( physicalResource );
						
						//datos de la smartcard
						physicalResource = new PhysicalResourceItem();		
						physicalResource.setSerialNumber( workOrderServiceElementVO.getLinkedWOServiceElement().getElementSerial() ); //Serial del elemento
						physicalResource.setProductID( String.valueOf( workOrderServiceElementVO.getLinkedWOServiceElement().getElementTypeId() ) ); //Codigo del Elemento		
						arrayOfPhysicalResourceItem.getPhysicalResource().add( physicalResource );
					}			
				}
				
				registerResource.setResource( arrayOfPhysicalResourceItem );	
				//enviar el IBServiceKey de WorkOrderService
				registerResource.setServiceKey( attentionElements.getWoService().getIbsServiceKey() );			
							
				woPhysical.getServices().add( registerResource );
			}
		}
		serializedRequest.setWorkOrder( woPhysical );
		request.setRegisterSerializedResourceRequest( serializedRequest );
		request.setRequestMetadata( InventoryInterfaceTOA.getRequestMetadata( inventoryDTO ) );
		//log
		logRegisterSerializedResourcesRequest(inventoryDTO, request);
		return request;
	}
	
	/**
	 * 
	 * Metodo: Retorna la respuesta del
	 * servicio en un objeto de tipo 
	 * negocio
	 * @param response RegisterSerializedResourcesResponse
	 * @return EnvelopeEncapsulateResponse
	 * @throws BusinessException
	 * @author jalopez
	 */
	public static EnvelopeEncapsulateResponse populateRegisterSerializedResourcesElement (RegisterSerializedResourcesResponse response) throws BusinessException{
		EnvelopeEncapsulateResponse encapsulateResponse = new EnvelopeEncapsulateResponse();
		List<EnvelopeEncapsulateDetailResponse> exceptions = new ArrayList<EnvelopeEncapsulateDetailResponse>();
		EnvelopeEncapsulateDetailResponse envelope = new EnvelopeEncapsulateDetailResponse();
		
		InventoryInterfaceTOA.validateRegisterSerializedResourcesResponse(response);
		envelope.setExceptionCode( response.getRegisterSerializedResourceResponse().getRegisterStatus().getCode() );
		envelope.setExceptionMessage( response.getRegisterSerializedResourceResponse().getRegisterStatus().getName() );
		
		exceptions.add(envelope);
		encapsulateResponse.setExceptions(exceptions);
		return encapsulateResponse;
	}
	
	/**
	 * 
	 * Metodo: Retorna la peticion
	 * del servicio en un objeto de tipo
	 * contrato
	 * @param inventoryDTO InventoryDTO
	 * @return RegisterNotSerializedResourceRequest
	 * @author jalopez
	 * @throws BusinessException 
	 */
	public static RegisterNotSerializedResourceRequest populateRegisterNotSerializedResourcesInventory(InventoryDTO inventoryDTO) throws BusinessException{
		
		//valida los datos requeridos
		validateRegisterNotSerializedResourceRequest(inventoryDTO);
		
		RegisterNotSerializedResourceRequest request = new RegisterNotSerializedResourceRequest();
		CtregisterNotSerializedResourceRequest serializedRequest = new CtregisterNotSerializedResourceRequest();
		
		WorkOrderPhysicalResources woPhysical = new WorkOrderPhysicalResources();
		ServiceProvider serviceProvider = new ServiceProvider();
		ServiceRegisterResource registerResource = null;
		ArrayOfPhysicalResourceItem arrayOfPhysicalResourceItem = null;
		PhysicalResourceItem physicalResource = null;
		FunctionOrProcessProvider dealer = new FunctionOrProcessProvider();
		Quantity quantity = null;
		Category category = null;
		
		dealer.setPartyRoleId( String.valueOf( inventoryDTO.getDealer().getDealerCode() ) );//Dealer
		serviceProvider.setID( String.valueOf( inventoryDTO.getDocumentResponsibleCrew() ) );//Cedula del Tecnico de la Cuadrilla
		woPhysical.setID( inventoryDTO.getWoAttentionDTO().getWorkorderVo().getWoCode() );//Codigo de la WorkOrder
		woPhysical.setReferencesId( inventoryDTO.getCustomer().getCustomerCode() );//Customer Code
		woPhysical.setServiceProvider( serviceProvider );
		woPhysical.setStockhandler( dealer );		
		
		arrayOfPhysicalResourceItem = new ArrayOfPhysicalResourceItem();
		registerResource = new ServiceRegisterResource();
		for (WorkOrderServiceVO workOrderServiceVO : inventoryDTO.getWoAttentionDTO().getWorkorderServices()) {
			//valida los datos requeridos de los elementos
			validateRegisterNotSerializedResourceRequest(workOrderServiceVO);
			
			//Elementos Instalados			
			for (WorkOrderServiceElementVO workOrderServiceElementVO : workOrderServiceVO.getWoServiceElements()) {
				//valida los datos requeridos de los elementos
				validateRegisterNotSerializedResourceRequest( workOrderServiceElementVO );
				category = new Category();
				quantity = new Quantity();				
				physicalResource = new PhysicalResourceItem();
				
				physicalResource.setProductID( String.valueOf( workOrderServiceElementVO.getElementTypeId() ) );//Codigo del Elemento
				quantity.setAmount( BigDecimal.valueOf( workOrderServiceElementVO.getQuantity() ) );//Cantidad utilizada en la atencion del servicio
				category.setId( String.valueOf( workOrderServiceElementVO.getElementTypeId() ) );////Codigo del Elemento
				
				physicalResource.setDeviceType( category );						
				physicalResource.setQuantity( quantity );
				arrayOfPhysicalResourceItem.getPhysicalResource().add( physicalResource );
			}	
			
		}
		registerResource.setResource( arrayOfPhysicalResourceItem );			
		woPhysical.getServices().add( registerResource );
		
		serializedRequest.setWorkOrder( woPhysical );
		request.setRegisterNotSerializedResourceRequest( serializedRequest );
		request.setRequestMetadata( InventoryInterfaceTOA.getRequestMetadata( inventoryDTO ) );
		//Log
		logRegisterNotSerializedResourceRequest(inventoryDTO, request, registerResource);
		return request;
	}
	
	/**
	 * 
	 * Metodo: Retorna la respuesta del
	 * servicio en un objeto de tipo 
	 * negocio
	 * @param response RegisterNotSerializedResourceResponse
	 * @return EnvelopeEncapsulateResponse
	 * @throws BusinessException
	 * @author jalopez
	 */
	public static EnvelopeEncapsulateResponse populateRegisterNotSerializedResourcesElement (RegisterNotSerializedResourceResponse response) throws BusinessException{
		EnvelopeEncapsulateResponse encapsulateResponse = new EnvelopeEncapsulateResponse();
		List<EnvelopeEncapsulateDetailResponse> exceptions = new ArrayList<EnvelopeEncapsulateDetailResponse>();
		EnvelopeEncapsulateDetailResponse envelope = new EnvelopeEncapsulateDetailResponse();
		
		InventoryInterfaceTOA.validateRegisterNotSerializedResourcesResponse(response);
		envelope.setExceptionCode( response.getRegisterNotSerializedResourceResponse().getRegisterStatus().getCode() );
		envelope.setExceptionMessage( response.getRegisterNotSerializedResourceResponse().getRegisterStatus().getName() );
		
		exceptions.add(envelope);
		encapsulateResponse.setExceptions(exceptions);
		return encapsulateResponse;
	}
	
	/**
	 * 
	 * Metodo: <Descripcion>
	 * @return <tipo> <descripcion>
	 * @author jalopez
	 */
	public static RegisterSerializedResourcesRequest populateRegisterNotSerializedResourcesInventory(){
		RegisterSerializedResourcesRequest request = new RegisterSerializedResourcesRequest();
		
		return request;
	}
	
	/**
	 * 
	 * Metodo: Retorna el objeto de tipo contrato
	 * con la informacion transversal a la aplicacion.
	 * @param inventoryDTO InventoryDTO
	 * @return RequestMetadataType
	 * @author jalopez
	 */
	public static RequestMetadataType getRequestMetadata(InventoryDTO inventoryDTO) {
		RequestMetadataType metadata = new RequestMetadataType();
		metadata.setRequestID( inventoryDTO.getCountryCode() );
		metadata.setSourceID( inventoryDTO.getCountryCode() );
		try {
			metadata.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
		} catch (PropertiesException e) {
			log.debug("== Error al tratar de ejecutar la operaciÃ³n IBSSPRMSupportAndReadinessTOA/RequestMetadataType ==");
			e.printStackTrace();
		}
		return metadata;
	}
	
	/**
	 * 
	 * Metodo:Valida el objeto de respuesta del 
	 * servicio.
	 * @param response
	 * @throws BusinessException
	 * @author jalopez
	 */
	private static void validateRegisterNotSerializedResourcesResponse(RegisterNotSerializedResourceResponse response) throws BusinessException{
		Object[] params = null;
		params = new Object[2];	
	
		params[1] = "Obtener la Respuesta de RegisterSerializedResources";		
		params[0] = "RegisterStatus";
		UtilsBusiness.validateRequestResponseWebService(params,response.getRegisterNotSerializedResourceResponse(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "RegisterStatus";
		UtilsBusiness.validateRequestResponseWebService(params,response.getRegisterNotSerializedResourceResponse().getRegisterStatus(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "RegisterStatus";
		UtilsBusiness.validateRequestResponseWebService(params,response.getRegisterNotSerializedResourceResponse().getRegisterStatus().getCode(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "RegisterStatus";
		UtilsBusiness.validateRequestResponseWebService(params,response.getRegisterNotSerializedResourceResponse().getRegisterStatus().getName(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
	}
	
	/**
	 * 
	 * Metodo:Valida el objeto de respuesta del 
	 * servicio.
	 * @param response
	 * @throws BusinessException
	 * @author jalopez
	 */
	private static void validateRegisterSerializedResourcesResponse(RegisterSerializedResourcesResponse response) throws BusinessException{
		Object[] params = null;
		params = new Object[2];	
	
		params[1] = "Obtener la Respuesta de RegisterSerializedResources";		
		params[0] = "RegisterStatus";
		UtilsBusiness.validateRequestResponseWebService(params,response.getRegisterSerializedResourceResponse(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "RegisterStatus";
		UtilsBusiness.validateRequestResponseWebService(params,response.getRegisterSerializedResourceResponse().getRegisterStatus(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "RegisterStatus";
		UtilsBusiness.validateRequestResponseWebService(params,response.getRegisterSerializedResourceResponse().getRegisterStatus().getCode(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
		params[0] = "RegisterStatus";
		UtilsBusiness.validateRequestResponseWebService(params,response.getRegisterSerializedResourceResponse().getRegisterStatus().getName(), ErrorBusinessMessages.BUSINESS_BL187.getCode(), ErrorBusinessMessages.BUSINESS_BL187.getMessage(params));
	}
	
	/**
	 * 
	 * Metodo: Validaciones de los datos requeridos
	 * para obtener la informacion de un elemento
	 * serializado
	 * @param physicalResource
	 * @throws BusinessException
	 * @author jalopez
	 */
	private static void validateSerializedResource(GetSerializedResourceResponse response) throws BusinessException{
		Object[] params = null;
		params = new Object[2];	
	
		params[1] = "Obtener Elemento Serializado Field Services";		
		params[0] = "GetSerializedResource";
		UtilsBusiness.validateRequestResponseWebService(params,response.getGetSerializedResourceResponse(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "PhysicalResourceItem";
		UtilsBusiness.validateRequestResponseWebService(params,response.getGetSerializedResourceResponse().getPhysicalResourceItem(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
	}
	
	/**
	 * 
	 * Metodo: Validaciones de los datos requeridos
	 * para obtener la informacion de un elemento
	 * serializado
	 * @param physicalResource
	 * @throws BusinessException
	 * @author jalopez
	 */
	private static void validatePhysicalResourceItem(PhysicalResourceItem physicalResource) throws BusinessException{
		Object[] params = null;
		params = new Object[2];	
	
		params[1] = "Obtener Elemento Serializado Field Services";		
		params[0] = "DeviceModel";
		UtilsBusiness.validateRequestResponseWebService(params, physicalResource.getDeviceModel(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "DeviceModel";
		UtilsBusiness.validateRequestResponseWebService(params, physicalResource.getDeviceModel().getName(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "DeviceType";
		UtilsBusiness.validateRequestResponseWebService(params, physicalResource.getDeviceType(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "DeviceType";
		UtilsBusiness.validateRequestResponseWebService(params, physicalResource.getDeviceType().getId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "DeviceType";
		UtilsBusiness.validateRequestResponseWebService(params, physicalResource.getDeviceType().getName(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "SerialNumber";
		UtilsBusiness.validateRequestResponseWebService(params, physicalResource.getSerialNumber(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));		
		params[0] = "DeviceType";
		UtilsBusiness.validateRequestResponseWebService(params, physicalResource.getDeviceType(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "DeviceType";
		UtilsBusiness.validateRequestResponseWebService(params, physicalResource.getDeviceType().getId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
	}
	
	/**
	 * 
	 * Metodo: Validaciones de los datos requeridos
	 * para obtener la informacion de un elemento
	 * serializado vinculado
	 * @param physicalResource
	 * @throws BusinessException
	 * @author jalopez
	 */
	private static void validatePhysicalLinkedResourceItem(PhysicalResourceItem physicalResource) throws BusinessException{
		Object[] params = null;
		params = new Object[2];	
	
		params[1] = "Obtener Elemento Serializado Field Services";		
		params[0] = "DeviceType";
		UtilsBusiness.validateRequestResponseWebService(params, physicalResource.getDeviceType(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "DeviceType";
		UtilsBusiness.validateRequestResponseWebService(params, physicalResource.getDeviceType().getId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "DeviceType";
		UtilsBusiness.validateRequestResponseWebService(params, physicalResource.getDeviceType().getName(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "SerialNumber";
		UtilsBusiness.validateRequestResponseWebService(params, physicalResource.getSerialNumber(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));		
	}
	
	/**
	 * 
	 * Metodo: Validacion de los datos requeridos 
	 * para obtner la informacion de los elementos
	 * por servicio.
	 * @param physicalResource
	 * @throws BusinessException
	 * @author jalopez
	 */
	private static void validateResourcetsByServiceType(PhysicalResourceItem item) throws BusinessException{
		Object[] params = null;
		params = new Object[2];	
	
		params[1] = "Obtener Elementos Requeridos por Servicio Field Services";	
		params[0] = "ProductID";
		UtilsBusiness.validateRequestResponseWebService(params, item.getProductID(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));		
		params[0] = "ProductID";
		UtilsBusiness.validateRequestResponseWebService(params, item.getProductID(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "OtherIdentifier";
		UtilsBusiness.validateRequestResponseWebService(params, item.getOtherIdentifier(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "CommonName";
		UtilsBusiness.validateRequestResponseWebService(params, item.getCommonName(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "Description";
		UtilsBusiness.validateRequestResponseWebService(params, item.getDescription(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));			
	}
	
	/**
	 * 
	 * Metodo: valida los parametros requeridos
	 * para el consumo de la operacion RegisterNotSerialized
	 * @param InventoryDTO inventoryDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	private static void  validateRegisterNotSerializedResourceRequest(InventoryDTO inventoryDTO)throws BusinessException{
		Object[] params = null;
		params = new Object[2];	
	
		params[1] = "validateRegisterNotSerializedResourceRequest";		
		params[0] = "inventoryDTO";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO, ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "Dealer";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO.getDealer(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "DepotCode";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO.getDealer().getDealerCode(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "WoAttentionDTO";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO.getWoAttentionDTO(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "WorkorderVo";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO.getWoAttentionDTO().getWorkorderVo(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "WoCode";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO.getWoAttentionDTO().getWorkorderVo().getWoCode(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "WorkorderServices";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO.getWoAttentionDTO().getWorkorderServices(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "DocumentResponsibleCrew";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO.getDocumentResponsibleCrew(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "Customer";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO.getCustomer(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "CustomerCode";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO.getCustomer().getCustomerCode(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
	}
	
	/**
	 * 
	 * Metodo: valida los parametros requeridos
	 * para el consumo de la operacion RegisterNotSerialized
	 * @param WorkOrderServiceVO workOrderServiceVO
	 * @throws BusinessException
	 * @author jalopez
	 */
	private static void  validateRegisterNotSerializedResourceRequest(WorkOrderServiceVO workOrderServiceVO)throws BusinessException{
		Object[] params = null;
		params = new Object[2];	
	
		params[1] = "validateRegisterNotSerializedResourceRequest";		
		params[0] = "workOrderServiceVO";
		UtilsBusiness.validateRequestResponseWebService(params,workOrderServiceVO, ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "WoServiceElements";
		UtilsBusiness.validateRequestResponseWebService(params,workOrderServiceVO.getWoServiceElements(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));	
	}
	
	/**
	 * 
	 * Metodo: valida los parametros requeridos
	 * para el consumo de la operacion RegisterNotSerialized
	 * @param WorkOrderServiceElementVO workOrderServiceElementVO
	 * @throws BusinessException
	 * @author jalopez
	 */
	private static void  validateRegisterNotSerializedResourceRequest(WorkOrderServiceElementVO workOrderServiceElementVO)throws BusinessException{
		Object[] params = null;
		params = new Object[2];	
	
		params[1] = "validateRegisterNotSerializedResourceRequest";		
		params[0] = "workOrderServiceElementVO";
		UtilsBusiness.validateRequestResponseWebService(params,workOrderServiceElementVO, ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "ElementTypeId";
		UtilsBusiness.validateRequestResponseWebService(params,workOrderServiceElementVO.getElementTypeId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));	
		params[0] = "Quantity";
		UtilsBusiness.validateRequestResponseWebService(params,workOrderServiceElementVO.getQuantity(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
	}
	
	
	/**
	 * 
	 * Metodo: Registra en el log los parametros enviados
	 * @param InventoryDTO inventoryDTO
	 * @author jalopez
	 */
	private static void logGetSerializedResourceRequest(InventoryDTO inventoryDTO){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		//Parametros enviados
		buffer.append(" SerialNumber: " + inventoryDTO.getElementDTO().getSerializedVO().getSerialCode());
		buffer.append(logMetadata(inventoryDTO));
		
		//Construcccion del mensaje
		params[0] = "GetSerializedResource";
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
	 * Metodo:  Registra en el log los parametros enviados
	 * @param inventoryDTO InventoryDTO
	 * @author jalopez
	 */
	private static void logGetNotSerializedResource(InventoryDTO inventoryDTO){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		//Parametros enviados
		buffer.append(" category Id: " + inventoryDTO.getElementDTO().getElementTypeCode());
		buffer.append(" Quantity Amount: " + inventoryDTO.getElementDTO().getQuantity());
		buffer.append(" FunctionOrProcessProvider ComissionType: " + inventoryDTO.getDealer().getDepotCode());
		buffer.append(" AdminCrew: " + inventoryDTO.getEmployeeCrew().getEmployee().getDocumentNumber());
		buffer.append(" CustomerCode: " + inventoryDTO.getCustomer().getCustomerCode());
		buffer.append(logMetadata(inventoryDTO));
		
		//Construcccion del mensaje
		params[0] = "GetNotSerializedResource";
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
	 * @param inventoryDTO InventoryDTO
	 * @author jalopez
	 */
	private static void logResourcesByServiceType(InventoryDTO inventoryDTO){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		//Parametros enviados
		buffer.append(" Service Code: " + inventoryDTO.getServiceDTO().getServiceCode());
		buffer.append(logMetadata(inventoryDTO));
		
		//Construcccion del mensaje
		params[0] = "GetResourcesByServiceType";
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
	 * @param inventoryDTO InventoryDTO
	 * @param ServiceRegisterResource registerResource
	 * @param RegisterSerializedResourcesRequest request
	 * @author jalopez
	 */
	private static void logRegisterSerializedResourcesRequest(InventoryDTO inventoryDTO, RegisterSerializedResourcesRequest request){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		StringBuilder resources = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		//Parametros enviados
		buffer.append(" ServiceProvider Id: " + request.getRegisterSerializedResourceRequest().getWorkOrder().getServiceProvider().getID() );
		buffer.append(" WorkOrderPhysicalResources Id: " + request.getRegisterSerializedResourceRequest().getWorkOrder().getID() );
		buffer.append(" WorkOrder Type: " + request.getRegisterSerializedResourceRequest().getWorkOrder().getType().getId() );
		buffer.append(" Services List: ");
		for(ServiceRegisterResource registerResource : request.getRegisterSerializedResourceRequest().getWorkOrder().getServices())
		{
			resources = new StringBuilder();
			buffer.append(" - Services ServiceKey: " + registerResource.getServiceKey() );			
			for (PhysicalResourceItem resource : registerResource.getResource().getPhysicalResource() ) {
				resources.append(resource.getSerialNumber());
				resources.append(",");
			}
			if(resources.length() > 0){
				resources.deleteCharAt(resources.length() - 1);
			}
			buffer.append(" - ServiceRegisterResource Resource: " + resources.toString() );
		}
		buffer.append(logMetadata(inventoryDTO));
		
		//Construcccion del mensaje
		params[0] = "RegisterSerializedResources";
		params[1] = buffer.toString();
		message.append("== ");
		message.append(ErrorBusinessMessages.BUSINESS_BL188.getCode());
		message.append(" : ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getMessage(params) );
		message.append(" ==");
		log.info( message.toString() );
	}
	
	/**
	 * 
	 * Metodo: Registra en el log los parametros enviados
	 * @param InventoryDTO inventoryDTO
	 * @param RegisterNotSerializedResourceRequest request
	 * @param ServiceRegisterResource registerResource
	 * @author jalopez
	 */
	private static void logRegisterNotSerializedResourceRequest(InventoryDTO inventoryDTO, RegisterNotSerializedResourceRequest request, ServiceRegisterResource registerResource){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		StringBuilder resources = new StringBuilder();
		StringBuilder deviceTypes = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		//Parametros enviados
		buffer.append(" ServiceProvider Id: " + request.getRegisterNotSerializedResourceRequest().getWorkOrder().getServiceProvider().getID() );
		buffer.append(" Stockhandler PartyRoleId: " + request.getRegisterNotSerializedResourceRequest().getWorkOrder().getStockhandler().getPartyRoleId() );
		//buffer.append(" Stockhandler BusinessRoles: " + request.getRegisterNotSerializedResourceRequest().getWorkOrder().getStockhandler().getBusinessRoles().get(0).getPartyRoleId() );
		buffer.append(" WorkOrder ID: " + request.getRegisterNotSerializedResourceRequest().getWorkOrder().getID() );
		buffer.append(" WorkOrder ReferencesId: " + request.getRegisterNotSerializedResourceRequest().getWorkOrder().getReferencesId() );
		
		if( registerResource.getResource() != null)
			for (PhysicalResourceItem resource : registerResource.getResource().getPhysicalResource() ) {
				resources.append(resource.getProductID());
				resources.append(",");
				deviceTypes.append( resource.getDeviceType().getId() );
				deviceTypes.append( "," );
			}
		buffer.append(" Resource ProductID: " + resources.toString() );
		buffer.append(" Resource DeviceType: " + deviceTypes.toString() );
		buffer.append(logMetadata(inventoryDTO));
		
		//Construcccion del mensaje
		params[0] = "RegisterNotSerializedResource";
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
	private static String logMetadata(InventoryDTO inventoryDTO){
		StringBuilder buffer = new StringBuilder();
		RequestMetadataType metadata = getRequestMetadata(inventoryDTO);
		buffer.append(" Metadata [");
		buffer.append(" RequestID: "+metadata.getRequestID());
		buffer.append(" SourceID: "+metadata.getSourceID());
		buffer.append(" ]");
		return buffer.toString();
	}
}
