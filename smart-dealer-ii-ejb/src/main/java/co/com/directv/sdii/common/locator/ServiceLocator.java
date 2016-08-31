package co.com.directv.sdii.common.locator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.enumerations.WsdlLocationsEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;

import com.directvla.contract.businessdomain.crmsupportandreadiness.v1_0.CRMSupportAndReadinessPt;
import com.directvla.contract.businessdomain.crmsupportandreadiness.v1_0.CRMSupportAndReadinessPt_Service;
import com.directvla.contract.businessdomain.customerinterfacemanagement.v1_0.CustomerInterfaceManagementPt;
import com.directvla.contract.businessdomain.customerinterfacemanagement.v1_0.CustomerInterfaceManagementPt_Service;
import com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.HSPSupportAndReadiness;
import com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.HSPSupportAndReadinessPt;
import com.directvla.contract.businessdomain.manageworkforce.v1_0.ManageWorkforceEp;
import com.directvla.contract.businessdomain.manageworkforce.v1_0.ManageWorkforcePt;
import com.directvla.contract.businessdomain.orderhandling.v1_0.OrderHandlingPt;
import com.directvla.contract.businessdomain.orderhandling.v1_0.OrderHandlingService;
import com.directvla.contract.businessdomain.resourceprovisioning.v1_0.ResourceProvicioningService;
import com.directvla.contract.businessdomain.resourceprovisioning.v1_0.ResourceProvisioningPt;
import com.directvla.contract.businessdomain.serviceconfigurationandactivation.v1_0.ServiceConfigurationAndActivationPt;
import com.directvla.contract.businessdomain.serviceconfigurationandactivation.v1_0.ServiceConfigurationAndActivationPt_Service;
import com.directvla.contract.businessdomain.serviceproblemmanagement.v1_0.ServiceProblemManagementPt;
import com.directvla.contract.businessdomain.serviceproblemmanagement.v1_0.ServiceProblemManagementPt_Service;
import com.directvla.contract.businessdomain.sprmsupportandreadiness.v1_0.SPRMSupportAndReadinessPt;
import com.directvla.contract.businessdomain.sprmsupportandreadiness.v1_0.SPRMSupportAndReadinessPt_Service;
import com.directvla.contract.crm.customer.v1.CORESERVICE;
import com.directvla.contract.crm.customer.v1.PtCore;
import com.directvla.contract.crm.customer.v1_0.CustomerPortType;
import com.directvla.contract.crm.customer.v1_0.CustomerPortType_Service;
import com.directvla.contract.crm.inventory.v1_0.InventoryService;
import com.directvla.contract.crm.inventory.v1_0.PtInventory;
import com.directvla.contract.crm.vista360.v1.PtVista360;
import com.directvla.contract.crm.vista360.v1.PtVista360_Service;
import com.oracle.xmlns.attentions_jws.bpmattentions.attentionprocess.AttentionProcess;
import com.oracle.xmlns.attentions_jws.bpmattentions.attentionprocess.AttentionprocessClientEp;
import com.oracle.xmlns.mer_sdii_jws.bpmallocator.allocatorprocess.AllocatorProcess;
import com.oracle.xmlns.mer_sdii_jws.bpmallocator.allocatorprocess.AllocatorprocessClientEp;

import ec.com.directv.apps.ShippingOrder;
import ec.com.directv.apps.ShippingOrderSoap;

/**
 * 
 *  Servicio para la busqueda y creacion de servicios por medio
 *  de interfaces complejas expuestas en aplicaciones externas.
 * 
 * Fecha de Creación: 13/10/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ServiceLocator {

	private static ServiceLocator locator;
	private final static Logger log = UtilsBusiness.getLog4J(ServiceLocator.class);
	/*timeout*/
	private static final String CONNECT_TIMEOUT = "com.sun.xml.ws.connect.timeout";
	private static final String REQUEST_TIMEOUT = "com.sun.xml.ws.request.timeout";
	/**
	 * 
	 * Metodo: retorna una instacia de ServiceLocator, patron singleton.
	 * @return ServiceLocator
	 * @throws ServiceLocatorException <tipo> <descripcion>
	 * @author jalopez
	 */
	public static ServiceLocator getInstance() throws ServiceLocatorException{
		try{
			if( locator == null ){
				locator = new ServiceLocator();
			}
			return locator;
		}catch (Exception e) {
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_NOT_INSTANCE.getCode(),ErrorBusinessMessages.SERVICE_LOCATOR_NOT_INSTANCE.getMessage(),e);
		}
	}

	/**
	 * (1)
	 * Metodo: Permite obtener la referencia al web service de inventarios expuesto por IBS
	 * @return PtInventory - Referencia al WS para invocar las operaciones de inventarios en IBS
	 * @throws ServiceLocatorException 
	 */
	public PtInventory getInventoryService() throws ServiceLocatorException {
		
		log.info("=== Inicia getInventoryService/ServiceLocator ===");
		try{
			/*SR469307 - HSP+ Cambio Service locator*/
			URL url = null;
			URL baseUrl;				  
            baseUrl = com.directvla.contract.crm.inventory.v1_0.InventoryService.class.getResource(".");
            url = new URL(baseUrl, "http://localhost/wsdl/Inventory-Services.wsdl");
            URL wsdlLocation = url;			
			QName serviceName = new QName(WsdlLocationsEnum.INVENTORY_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.INVENTORY_WSDL_LOCATION.getQnameService());
	    	InventoryService service = new InventoryService(wsdlLocation, serviceName);
	    	PtInventory inventory = service.getPtInventoryPort();
			BindingProvider bindingProvider = (BindingProvider) inventory;
	    	String serviceURL = WsdlLocationsEnum.INVENTORY_WSDL_LOCATION.getEndPointService();
			bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);
			/*compression*/
			Map<String, List<String>> httpHeaders = new HashMap<String, List<String>>();
	    	httpHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
			bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);
			/*timeout*/
			bindingProvider.getRequestContext().put(CONNECT_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.CONNECT_TIMEOUT.getCodeEntity()));
			bindingProvider.getRequestContext().put(REQUEST_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.REQUEST_TIMEOUT.getCodeEntity()));
			traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			return inventory;
		}catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(),ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(),e);
		}catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(),e.getMessage(),e);
		}catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(),e);
		}finally{
			log.info("=== Termina getInventoryService/ServiceLocator ===");
		}
    }

	/**
	 * (2)
	 * Metodo: Permite obtener la referencia al web service de inventarios expuesto por IBS (SPRMSupportAndReadiness)
	 * @return PtInventory - Referencia al WS para invocar las operaciones de inventarios en IBS (SPRMSupportAndReadiness)
	 * @throws ServiceLocatorException 
	 */
	public SPRMSupportAndReadinessPt getSPRMSupportAndReadinessInventoryService() throws ServiceLocatorException {
		
		log.info("=== Inicia getInventoryService/ServiceLocator ===");
		try{
			/*SR469307 - HSP+ Cambio Service locator*/
			URL url = null;
			URL baseUrl;
            baseUrl = com.directvla.contract.businessdomain.sprmsupportandreadiness.v1_0.SPRMSupportAndReadinessPt_Service.class.getResource(".");
            url = new URL(baseUrl, "http://localhost/wsdl/SPRMSupportAndReadiness.wsdl");
            URL wsdlLocation = url;			
			QName serviceName = new QName(WsdlLocationsEnum.SPRMSUPPOT_AND_READINESS_INVENTORY_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.SPRMSUPPOT_AND_READINESS_INVENTORY_WSDL_LOCATION.getQnameService());
			SPRMSupportAndReadinessPt_Service service = new SPRMSupportAndReadinessPt_Service(wsdlLocation, serviceName);			
			SPRMSupportAndReadinessPt inventory = service.getSPRMSupportAndReadinessPtPort();						
			BindingProvider bindingProvider = (BindingProvider) inventory;
			String serviceURL = WsdlLocationsEnum.SPRMSUPPOT_AND_READINESS_INVENTORY_WSDL_LOCATION.getEndPointService();
		    bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);
		    /*compression*/
			Map<String, List<String>> httpHeaders = new HashMap<String, List<String>>();
	    	httpHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
			bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);
			/*timeout*/
			bindingProvider.getRequestContext().put(CONNECT_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.CONNECT_TIMEOUT.getCodeEntity()));
			bindingProvider.getRequestContext().put(REQUEST_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.REQUEST_TIMEOUT.getCodeEntity()));
		    traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			return inventory;
		}catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(),ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(),e);
		}catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(),e.getMessage(),e);
		}catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(),e);
		}finally{
			log.info("=== Termina getInventoryService/ServiceLocator ===");
		}
    }
	
	/**
	 * (3)
	 * Metodo: Permite obtener la referencia al web service de inventarios expuesto por IBS (SPRMSupportAndReadiness)
	 * @return PtInventory - Referencia al WS para invocar las operaciones de inventarios en IBS (SPRMSupportAndReadiness)
	 * @throws ServiceLocatorException 
	 */
	public CRMSupportAndReadinessPt getCRMSupportAndReadinessService() throws ServiceLocatorException {
		
		log.info("=== Inicia getCRMSupportAndReadinessService/ServiceLocator ===");
		try{
	    	/*SR469307 - HSP+ Cambio Service locator*/
			URL url = null;
			URL baseUrl;
            baseUrl = com.directvla.contract.businessdomain.crmsupportandreadiness.v1_0.CRMSupportAndReadinessPt_Service.class.getResource(".");
            url = new URL(baseUrl, "http://localhost/wsdl/CRMSupportAndReadiness.wsdl");
            URL wsdlLocation = url;
			QName serviceName = new QName(WsdlLocationsEnum.CRMSUPPOT_AND_READINESS_INVENTORY_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.CRMSUPPOT_AND_READINESS_INVENTORY_WSDL_LOCATION.getQnameService());
			CRMSupportAndReadinessPt_Service service = new CRMSupportAndReadinessPt_Service(wsdlLocation, serviceName);			
			CRMSupportAndReadinessPt inventory = service.getCRMSupportAndReadinessPtPort();						
			BindingProvider bindingProvider = (BindingProvider) inventory;
			String serviceURL = WsdlLocationsEnum.CRMSUPPOT_AND_READINESS_INVENTORY_WSDL_LOCATION.getEndPointService();
		    bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);
		    /*compression*/
			Map<String, List<String>> httpHeaders = new HashMap<String, List<String>>();
	    	httpHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
			bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);
			/*timeout*/
			bindingProvider.getRequestContext().put(CONNECT_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.CONNECT_TIMEOUT.getCodeEntity()));
			bindingProvider.getRequestContext().put(REQUEST_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.REQUEST_TIMEOUT.getCodeEntity()));
		    traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			return inventory;
		}catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(),ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(),e);
		}catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(),e.getMessage(),e);
		}catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(),e);
		}finally{
			log.info("=== Termina getInventoryService/ServiceLocator ===");
		}
    }
	
	/**
	 * 
	 * Metodo: Permite obtener la referencia al web service de Dealers expuesto por IBS
	 * @return PtCustomer- Referencia al WS para invocar las operaciones de Dealers en IBS
	 * @throws ServiceLocatorException 
	 * @author jalopez
	 */
/*	public PtCustomer getDealerService() throws ServiceLocatorException {
		log.info("=== Inicia getDealerService/ServiceLocator ===");
		PtCustomer customer = null;
		try{
			
	    	URL wsdlLocation = new URL(WsdlLocationsEnum.DEALERS_WSDL_LOCATION.getWsdlLocation());
			QName serviceName = new QName(WsdlLocationsEnum.DEALERS_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.DEALERS_WSDL_LOCATION.getQnameService());
			QName serviceNameEndPoint = new QName(WsdlLocationsEnum.DEALERS_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.DEALERS_WSDL_LOCATION.getQnameEndPointService());
	    	CRMService service = new CRMService(wsdlLocation, serviceName);
			customer = service.getPort(serviceNameEndPoint, PtCustomer.class);
			traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
		}catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(),ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(),e);
		}catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(),e.getMessage(),e);
		}catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(),e);
		}finally{
			log.info("=== Termina getDealerService/ServiceLocator ===");
		}
		return customer;
	}*/
	
	/**
	 * (4)
	 * Metodo: Obtiene una referencia al objeto que es capaz de invocar operaciones
	 * sobre el servicio web de core expuesto por IBS	
	 * @return Objeto que es capaz de invocar operaciones del servicio web expuesto
	 * por IBS
	 * @throws MalformedURLException en caso de error
	 * @throws PropertiesException en caso de error
	 * @author jjimenezh
	 */
	public PtCore getCoreService() throws ServiceLocatorException {
		log.info("=== Inicia getCoreService/ServiceLocator ===");
		try {
			//service = coreService.getPort(serviceNameEndPoint, PtCore.class);

			/*SR469307 - HSP+ Cambio Service locator*/
			URL url = null;
			URL baseUrl;
            baseUrl = com.directvla.contract.crm.customer.v1.CORESERVICE.class.getResource(".");
            url = new URL(baseUrl, "http://localhost/wsdl/Core-Services.wsdl");
            URL wsdlLocation = url;			
			QName serviceName = new QName(WsdlLocationsEnum.CORE_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.CORE_WSDL_LOCATION.getQnameService());
			CORESERVICE service = new CORESERVICE(wsdlLocation, serviceName);			
			PtCore core = service.getCoreServiceHttpSoap11();
			BindingProvider bindingProvider = (BindingProvider) core;
			String serviceURL = WsdlLocationsEnum.CORE_WSDL_LOCATION.getEndPointService();
		    bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);
		    /*compression*/
			Map<String, List<String>> httpHeaders = new HashMap<String, List<String>>();
	    	httpHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
			bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);
			/*timeout*/
			bindingProvider.getRequestContext().put(CONNECT_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.CONNECT_TIMEOUT.getCodeEntity()));
			bindingProvider.getRequestContext().put(REQUEST_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.REQUEST_TIMEOUT.getCodeEntity()));
		    traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			return core;
		}catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(),ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(),e);
		}catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(),e.getMessage(),e);
		}catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(),e);
		}finally{
			log.info("=== Termina getCoreService/ServiceLocator ===");
		}
	}
	
	/** 
	 * (5)
	*/
	public ServiceConfigurationAndActivationPt getServiceConfigurationAndActivationService() throws ServiceLocatorException {
		log.info("=== Inicia getCoreService/ServiceLocator ===");
		try {
			/*SR469307 - HSP+ Cambio Service locator*/
			URL url = null;
			URL baseUrl;
            baseUrl =   com.directvla.contract.businessdomain.serviceconfigurationandactivation.v1_0.ServiceConfigurationAndActivationPt.class.getResource(".");
            url = new URL(baseUrl, "http://localhost/wsdl/ServiceConfigurationAndActivation.wsdl");
            URL wsdlLocation = url;			
			QName serviceName = new QName(WsdlLocationsEnum.SERVICE_CONFIGURATION_AND_ACTIVATION.getQnameNameSpace(), WsdlLocationsEnum.SERVICE_CONFIGURATION_AND_ACTIVATION.getQnameService());
			ServiceConfigurationAndActivationPt_Service service = new ServiceConfigurationAndActivationPt_Service(wsdlLocation, serviceName);			
			ServiceConfigurationAndActivationPt servicePort = service.getServiceConfigurationAndActivationPtPort();						
			BindingProvider bindingProvider = (BindingProvider) servicePort;
			String serviceURL = WsdlLocationsEnum.SERVICE_CONFIGURATION_AND_ACTIVATION.getEndPointService();
		    bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);
		    /*compression*/
			Map<String, List<String>> httpHeaders = new HashMap<String, List<String>>();
	    	httpHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
			bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);
			/*timeout*/
			bindingProvider.getRequestContext().put(CONNECT_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.CONNECT_TIMEOUT.getCodeEntity()));
			bindingProvider.getRequestContext().put(REQUEST_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.REQUEST_TIMEOUT.getCodeEntity()));
		    traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			return servicePort;
		}catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(),ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(),e);
		}catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(),e.getMessage(),e);
		}catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(),e);
		}finally{
			log.info("=== Termina getCoreService/ServiceLocator ===");
		}
	}
	
	/**
	 * Metodo: Obtiene el puerto para la invocación del serivicio web de proceso de negocio
	 * @return Puerto para la invocación del servicio web
	 * @throws ServiceLocatorException en caso de error relacionado con la obtención de los valores de
	 * configuración del servicio web
	 * @author jjimenezh
	 */
	public AttentionProcess getBPMAttentionService() throws ServiceLocatorException {
		log.info("=== Inicia getBPMAttentionService/ServiceLocator ===");
		try {
			URL wsdlLocation = new URL(WsdlLocationsEnum.ATTENTION_BPM_WSDL_LOCATION.getWsdlLocation());
			QName serviceName = new QName(WsdlLocationsEnum.ATTENTION_BPM_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.ATTENTION_BPM_WSDL_LOCATION.getQnameService());
			QName serviceNameEndPoint = new QName(WsdlLocationsEnum.ATTENTION_BPM_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.ATTENTION_BPM_WSDL_LOCATION.getQnameEndPointService());
			AttentionprocessClientEp bpmService = new AttentionprocessClientEp(wsdlLocation, serviceName);
			AttentionProcess service = bpmService.getPort(serviceNameEndPoint, AttentionProcess.class);
			traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			return service;
		} catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(), ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(), e);
		} catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(), e.getMessage(), e);
		} catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(), e);
		} finally {
			log.info("=== Termina getBPMAttentionService/ServiceLocator ===");
		}
	}
	
	/**
	 * Metodo: Obtiene el puerto para la invocación del serivicio web de proceso de negocio
	 * @return Puerto para la invocación del servicio web
	 * configuración del servicio web
	 * @author jjimenezh
	 */
	public AllocatorProcess getBPMAllocatorService() throws ServiceLocatorException {
		log.info("=== Inicia getBPMAllocatorService/ServiceLocator ===");
		try {
			URL wsdlLocation = new URL(WsdlLocationsEnum.ALLOCATOR_BPM_WSDL_LOCATION.getWsdlLocation());
			QName serviceName = new QName(WsdlLocationsEnum.ALLOCATOR_BPM_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.ALLOCATOR_BPM_WSDL_LOCATION.getQnameService());
			QName serviceNameEndPoint = new QName(WsdlLocationsEnum.ALLOCATOR_BPM_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.ALLOCATOR_BPM_WSDL_LOCATION.getQnameEndPointService());
			AllocatorprocessClientEp bpmService = new AllocatorprocessClientEp(wsdlLocation, serviceName);
			AllocatorProcess service = bpmService.getPort(serviceNameEndPoint, AllocatorProcess.class);
			traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			return service;
		} catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(), ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(), e);
		} catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(), e.getMessage(), e);
		} catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(), e);
		} finally {
			log.info("=== Termina getBPMAllocatorService/ServiceLocator ===");
		}
	}
	
	/**
	 * (8)
	 * Metodo: Permite obtener el puerto de invocacion del servicio de customer
	 * @return CustomerPortType Puerto de invocacion para el WS de IBS de customer
	 * @throws ServiceLocatorException
	 * @author jjimenezh
	 */
	public CustomerPortType getCustomerService()  throws ServiceLocatorException {
		log.info("=== Inicia getCustomerService/ServiceLocator ===");
		try {
			/*SR469307 - HSP+ Cambio Service locator*/
			URL url = null;
			URL baseUrl;
            baseUrl = com.directvla.contract.crm.customer.v1_0.CustomerPortType_Service.class.getResource(".");
            url = new URL(baseUrl, "http://localhost/wsdl/CustomerServices.wsdl");
            URL wsdlLocation = url;			
			QName serviceName = new QName(WsdlLocationsEnum.CUSTOMER_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.CUSTOMER_WSDL_LOCATION.getQnameService());
			CustomerPortType_Service service = new CustomerPortType_Service(wsdlLocation, serviceName);			
			CustomerPortType customer = service.getCustomerPortTypePort();						
			BindingProvider bindingProvider = (BindingProvider) customer;
			String serviceURL = WsdlLocationsEnum.CUSTOMER_WSDL_LOCATION.getEndPointService();
		    bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);
		    /*compression*/
			Map<String, List<String>> httpHeaders = new HashMap<String, List<String>>();
	    	httpHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
			bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);
			/*timeout*/
			bindingProvider.getRequestContext().put(CONNECT_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.CONNECT_TIMEOUT.getCodeEntity()));
			bindingProvider.getRequestContext().put(REQUEST_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.REQUEST_TIMEOUT.getCodeEntity()));
		    traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			return customer;
		} catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(), ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(), e);
		} catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(), e.getMessage(), e);
		} catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(), e);
		} finally {
			log.info("=== Termina getCustomerService/ServiceLocator ===");
		}
	}	
	
	/**
	 * (9)
	 * Metodo: Permite obtener el puerto de invocacion del servicio de customerInterfaceManagment
	 * @return
	 * @throws ServiceLocatorException <tipo> <descripcion>
	 * @author
	 */
	public CustomerInterfaceManagementPt getCustomerInterfaceManagment()  throws ServiceLocatorException {
		log.info("=== Inicia getCustomerInterfaceManagment/ServiceLocator ===");
		try {
			/*SR469307 - HSP+ Cambio Service locator*/
			URL url = null;
			URL baseUrl;
            baseUrl = com.directvla.contract.businessdomain.customerinterfacemanagement.v1_0.CustomerInterfaceManagementPt_Service.class.getResource(".");
            url = new URL(baseUrl, "http://localhost/wsdl/CustomerInterfaceManagement.wsdl");
            URL wsdlLocation = url;			
			QName serviceName = new QName(WsdlLocationsEnum.CUSTOMER_INTERFACE_MANAGMENT_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.CUSTOMER_INTERFACE_MANAGMENT_WSDL_LOCATION.getQnameService());
			CustomerInterfaceManagementPt_Service service = new CustomerInterfaceManagementPt_Service(wsdlLocation, serviceName);			
			CustomerInterfaceManagementPt customer = service.getCustomerInterfaceManagementPtPort();						
			BindingProvider bindingProvider = (BindingProvider) customer;
			String serviceURL = WsdlLocationsEnum.CUSTOMER_INTERFACE_MANAGMENT_WSDL_LOCATION.getEndPointService();
		    bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);
		    /*compression*/
			Map<String, List<String>> httpHeaders = new HashMap<String, List<String>>();
	    	httpHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
			bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);
			/*timeout*/
			bindingProvider.getRequestContext().put(CONNECT_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.CONNECT_TIMEOUT.getCodeEntity()));
			bindingProvider.getRequestContext().put(REQUEST_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.REQUEST_TIMEOUT.getCodeEntity()));
		    traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			return customer;
		} catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(), ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(), e);
		} catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(), e.getMessage(), e);
		} catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(), e);
		} finally {
			log.info("=== Termina getCustomerInterfaceManagment/ServiceLocator ===");
		}
	}	
	
	/**
	 * (10)
	 * Metodo: Permite obtener el puerto de invocacion del servicio de customer
	 * @return CustomerPortType Puerto de invocacion para el WS de IBS de customer
	 * @throws ServiceLocatorException
	 * @author jjimenezh
	 */
	public PtVista360 getVista360Service()  throws ServiceLocatorException {
		log.info("=== Inicia getVista360Service/ServiceLocator ===");
		try {
			/*SR469307 - HSP+ Cambio Service locator*/
			URL url = null;
			URL baseUrl;
            baseUrl = com.directvla.contract.crm.vista360.v1.PtVista360_Service.class.getResource(".");
            url = new URL(baseUrl, "http://localhost/wsdl/Vista360Service.wsdl");
            URL wsdlLocation = url;			
			QName serviceName = new QName(WsdlLocationsEnum.CUSTOMER_VISTA360_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.CUSTOMER_VISTA360_WSDL_LOCATION.getQnameService());
			PtVista360_Service service = new PtVista360_Service(wsdlLocation, serviceName);			
			PtVista360 ptVista360 = service.getPtVista360Port() ;						
			BindingProvider bindingProvider = (BindingProvider) ptVista360;
			String serviceURL = WsdlLocationsEnum.CUSTOMER_VISTA360_WSDL_LOCATION.getEndPointService();
		    bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);
		    /*compression*/
			Map<String, List<String>> httpHeaders = new HashMap<String, List<String>>();
	    	httpHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
			bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);
			/*timeout*/
			bindingProvider.getRequestContext().put(CONNECT_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.CONNECT_TIMEOUT.getCodeEntity()));
			bindingProvider.getRequestContext().put(REQUEST_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.REQUEST_TIMEOUT.getCodeEntity()));
		    traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			return ptVista360;	
		} catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(), ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(), e);
		} catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(), e.getMessage(), e);
		} catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(), e);
		} finally {
			log.info("=== Termina getVista360Service/ServiceLocator ===");
		}
	}

	
	/**
	 * (11)
	 * Metodo: Permite obtener el puerto de invocacion del servicio de customer
	 * @return CustomerPortType Puerto de invocacion para el WS de IBS de customer
	 * @throws ServiceLocatorException
	 * @author jjimenezh
	 */
	public HSPSupportAndReadinessPt getMarkWorkOrderService()  throws ServiceLocatorException {
		log.info("=== Inicia getMarkWorkOrderService/ServiceLocator ===");
		try {
/*			URL wsdlLocation = new URL(WsdlLocationsEnum.MARK_WORK_ORDER_WSDL_LOCATION.getWsdlLocation());
			QName serviceName = new QName(WsdlLocationsEnum.MARK_WORK_ORDER_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.MARK_WORK_ORDER_WSDL_LOCATION.getQnameService());
			QName serviceNameEndPoint = new QName(WsdlLocationsEnum.MARK_WORK_ORDER_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.MARK_WORK_ORDER_WSDL_LOCATION.getQnameEndPointService());
			
			HSPSupportAndReadiness service = new HSPSupportAndReadiness(wsdlLocation, serviceName);
			HSPSupportAndReadinessPt hspSupportAndReadinessPt = service.getPort(serviceNameEndPoint, HSPSupportAndReadinessPt.class);

			traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			return hspSupportAndReadinessPt;
*/			
			/*SR469307 - HSP+ Cambio Service locator*/
			URL url = null;
			URL baseUrl;
            baseUrl =  com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.HSPSupportAndReadiness.class.getResource(".");
            url = new URL(baseUrl, "http://localhost/wsdl/MediatorHSPSupportAndReadiness_ep.wsdl");
            URL wsdlLocation = url;			
			QName serviceName = new QName(WsdlLocationsEnum.MARK_WORK_ORDER_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.MARK_WORK_ORDER_WSDL_LOCATION.getQnameService());
			HSPSupportAndReadiness service = new HSPSupportAndReadiness(wsdlLocation, serviceName);			
			HSPSupportAndReadinessPt support = service.getHSPSupportAndReadinessPtSOAP();			
			BindingProvider bindingProvider = (BindingProvider) support;
			String serviceURL = WsdlLocationsEnum.MARK_WORK_ORDER_WSDL_LOCATION.getEndPointService();
		    bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);
		    /*compression*/
			Map<String, List<String>> httpHeaders = new HashMap<String, List<String>>();
	    	httpHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
			bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);
			/*timeout*/
			bindingProvider.getRequestContext().put(CONNECT_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.CONNECT_TIMEOUT.getCodeEntity()));
			bindingProvider.getRequestContext().put(REQUEST_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.REQUEST_TIMEOUT.getCodeEntity()));
		    traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			return support;
			
		} catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(), ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(), e);
		} catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(), e.getMessage(), e);
		} catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(), e);
		} finally {
			log.info("=== Termina getMarkWorkOrderService/ServiceLocator ===");
		}
	}	
	/**
	 * (12)
	 * Metodo: Obtiene el WS de manageWork Force
	 * @return ManageWorkforcePt
	 * @throws ServiceLocatorException <tipo> <descripcion>
	 * @author jalopez
	 */
	public ManageWorkforcePt getManageWorkForceService() throws ServiceLocatorException {
		log.info("=== Inicia getManageWorkForceService/ServiceLocator ===");
		///ManageWorkforcePt service = null;
		try {
			
			/*
			URL wsdlLocation = new URL(WsdlLocationsEnum.MANAGE_WORK_FORCE_WSDL_LOCATION.getWsdlLocation());
			QName serviceName = new QName(WsdlLocationsEnum.MANAGE_WORK_FORCE_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.MANAGE_WORK_FORCE_WSDL_LOCATION.getQnameService());
			QName serviceNameEndPoint = new QName(WsdlLocationsEnum.MANAGE_WORK_FORCE_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.MANAGE_WORK_FORCE_WSDL_LOCATION.getQnameEndPointService());
			ManageWorkforceEp manageWorkForceService = new ManageWorkforceEp(wsdlLocation, serviceName);
			service = manageWorkForceService.getPort(serviceNameEndPoint, ManageWorkforcePt.class);
			traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			return service;
			*/
			/*SR469307 - HSP+ Cambio Service locator*/
			URL url = null;
			URL baseUrl;
            baseUrl = com.directvla.contract.businessdomain.manageworkforce.v1_0.ManageWorkforceEp.class.getResource(".");
            url = new URL(baseUrl, "http://localhost/wsdl/ManageWorkforce_ep.wsdl");
            URL wsdlLocation = url;			
			QName serviceName = new QName(WsdlLocationsEnum.MANAGE_WORK_FORCE_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.MANAGE_WORK_FORCE_WSDL_LOCATION.getQnameService());
			ManageWorkforceEp service = new ManageWorkforceEp(wsdlLocation, serviceName);			
			ManageWorkforcePt managePort = service.getManageWorkforcePtPt() ;						
			BindingProvider bindingProvider = (BindingProvider) managePort;
			String serviceURL = WsdlLocationsEnum.MANAGE_WORK_FORCE_WSDL_LOCATION.getEndPointService();
		    bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);
		    /*compression*/
			Map<String, List<String>> httpHeaders = new HashMap<String, List<String>>();
	    	httpHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
			bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);
			/*timeout*/
			bindingProvider.getRequestContext().put(CONNECT_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.CONNECT_TIMEOUT.getCodeEntity()));
			bindingProvider.getRequestContext().put(REQUEST_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.REQUEST_TIMEOUT.getCodeEntity()));
		    traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			return managePort;
		} catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(), ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(), e);
		} catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(), e.getMessage(), e);
		} catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(), e);
		} finally {
			log.info("=== Termina getManageWorkForceService/ServiceLocator ===");
		}
	}
	
	/**
	 * (13)
	 * Metodo: Obtiene el WS de ResourceProvisioningService
	 * @return ResourceProvisioningPt
	 * @throws ServiceLocatorException
	 * @author jalopez
	 */
	public ResourceProvisioningPt getResourceProvisioningService() throws ServiceLocatorException {
		log.info("=== Inicia getResourceProvisioningService/ServiceLocator ===");
		//ResourceProvisioningPt service = null;
		try {
/*			
			URL wsdlLocation = new URL(WsdlLocationsEnum.RESOURCE_PROVISIONING_WSDL_LOCATION.getWsdlLocation());
			QName serviceName = new QName(WsdlLocationsEnum.RESOURCE_PROVISIONING_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.RESOURCE_PROVISIONING_WSDL_LOCATION.getQnameService());
			QName serviceNameEndPoint = new QName(WsdlLocationsEnum.RESOURCE_PROVISIONING_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.RESOURCE_PROVISIONING_WSDL_LOCATION.getQnameEndPointService());
			ResourceProvicioningService resourceProvisioningService = new ResourceProvicioningService(wsdlLocation, serviceName);
			service = resourceProvisioningService.getPort(serviceNameEndPoint, ResourceProvisioningPt.class);
			traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			
			return service;
*/			
			/*SR469307 - HSP+ Cambio Service locator*/
			URL url = null;
			URL baseUrl;
            baseUrl = com.directvla.contract.businessdomain.resourceprovisioning.v1_0.ResourceProvicioningService.class.getResource(".");
            url = new URL(baseUrl, "http://localhost/wsdl/ResourceProvisioningService.wsdl");
            URL wsdlLocation = url;			
			QName serviceName = new QName(WsdlLocationsEnum.RESOURCE_PROVISIONING_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.RESOURCE_PROVISIONING_WSDL_LOCATION.getQnameService());
			ResourceProvicioningService service = new ResourceProvicioningService(wsdlLocation, serviceName);			
			ResourceProvisioningPt customer = service.getResourceProvisioningPtPt();						
			BindingProvider bindingProvider = (BindingProvider) customer;
			String serviceURL = WsdlLocationsEnum.RESOURCE_PROVISIONING_WSDL_LOCATION.getEndPointService();
		    bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);
		    /*compression*/
			Map<String, List<String>> httpHeaders = new HashMap<String, List<String>>();
	    	httpHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
			bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);
			/*timeout*/
			bindingProvider.getRequestContext().put(CONNECT_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.CONNECT_TIMEOUT.getCodeEntity()));
			bindingProvider.getRequestContext().put(REQUEST_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.REQUEST_TIMEOUT.getCodeEntity()));
		    traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			return customer;
		} catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(), ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(), e);
		} catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(), e.getMessage(), e);
		} catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(), e);
		} finally {
			log.info("=== Termina getResourceProvisioningService/ServiceLocator ===");
		}
	}
	
	/**
	 * (14)
	 * Metodo: Obtiene el WS de ShippingOrder
	 * @return ShippingOrderSoap
	 * @throws ServiceLocatorException
	 * @author jnova
	 */
	public ShippingOrderSoap getShippingOrderService() throws ServiceLocatorException {
		log.info("=== Inicia getResourceProvisioningService/ServiceLocator ===");
		ShippingOrderSoap service = null;
		try {
			URL wsdlLocation = new URL(WsdlLocationsEnum.SHIPPING_ORDER_WSDL_LOCATION.getWsdlLocation());
			QName serviceName = new QName(WsdlLocationsEnum.SHIPPING_ORDER_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.SHIPPING_ORDER_WSDL_LOCATION.getQnameService());
			QName serviceNameEndPoint = new QName(WsdlLocationsEnum.SHIPPING_ORDER_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.SHIPPING_ORDER_WSDL_LOCATION.getQnameEndPointService());
			javax.xml.ws.Service shippingOrder = new ShippingOrder(wsdlLocation, serviceName);
			service = shippingOrder.getPort(serviceNameEndPoint, ShippingOrderSoap.class);
			traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			
			return service;
		} catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(), ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(), e);
		} catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(), e.getMessage(), e);
		} catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(), e);
		} finally {
			log.info("=== Termina getResourceProvisioningService/ServiceLocator ===");
		}
	}
	
	/**
	 * (15)
	 * Metodo: Obtiene el WS de ServiceProblemManagementService
	 * @return ShippingOrderSoap
	 * @throws ServiceLocatorException
	 * @author jalopez
	 */
	public ServiceProblemManagementPt getServiceProblemManagementService() throws ServiceLocatorException {
		log.info("=== Inicia getServiceProblemManagementService/ServiceLocator ===");
		//ServiceProblemManagementPt port = null;
		try {
/*			URL wsdlLocation = new URL(WsdlLocationsEnum.SERVICE_PROBLEM_MANAGEMENT_WSDL_LOCATION.getWsdlLocation());
			QName serviceName = new QName(WsdlLocationsEnum.SERVICE_PROBLEM_MANAGEMENT_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.SERVICE_PROBLEM_MANAGEMENT_WSDL_LOCATION.getQnameService());
			QName serviceNameEndPoint = new QName(WsdlLocationsEnum.SERVICE_PROBLEM_MANAGEMENT_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.SERVICE_PROBLEM_MANAGEMENT_WSDL_LOCATION.getQnameEndPointService());
			ServiceProblemManagementPt_Service service = new ServiceProblemManagementPt_Service(wsdlLocation, serviceName);
			port = service.getPort(serviceNameEndPoint, ServiceProblemManagementPt.class);
			traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			
			return port;
*/			
			/*SR469307 - HSP+ Cambio Service locator*/
			URL url = null;
			URL baseUrl;
            baseUrl = com.directvla.contract.businessdomain.serviceproblemmanagement.v1_0.ServiceProblemManagementPt_Service.class.getResource(".");
            url = new URL(baseUrl, "http://localhost/wsdl/ServiceProblemManagement.wsdl");
            URL wsdlLocation = url;			
			QName serviceName = new QName(WsdlLocationsEnum.SERVICE_PROBLEM_MANAGEMENT_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.SERVICE_PROBLEM_MANAGEMENT_WSDL_LOCATION.getQnameService());
			ServiceProblemManagementPt_Service service = new ServiceProblemManagementPt_Service(wsdlLocation, serviceName);			
			ServiceProblemManagementPt problemManPort = service.getServiceProblemManagementPtPort();						
			BindingProvider bindingProvider = (BindingProvider) problemManPort;
			String serviceURL = WsdlLocationsEnum.SERVICE_PROBLEM_MANAGEMENT_WSDL_LOCATION.getEndPointService();
		    bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);
		    /*compression*/
			Map<String, List<String>> httpHeaders = new HashMap<String, List<String>>();
	    	httpHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
			bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);
			/*timeout*/
			bindingProvider.getRequestContext().put(CONNECT_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.CONNECT_TIMEOUT.getCodeEntity()));
			bindingProvider.getRequestContext().put(REQUEST_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.REQUEST_TIMEOUT.getCodeEntity()));
		    traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			return problemManPort;
		} catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(), ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(), e);
		} catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(), e.getMessage(), e);
		} catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(), e);
		} finally {
			log.info("=== Termina getServiceProblemManagementService/ServiceLocator ===");
		}
	}
	
	/**
	 * (16)
	 * Metodo: Log de invocacion del servicio
	 * @param authority
	 * @param path
	 * @author jjimenezh
	 */
	private void traceInvocation(String authority, String path) {
		StringBuffer logBuffer = new StringBuffer();
		logBuffer.append("== Consumo Servicio WEB ESB: ");
		logBuffer.append(authority);
		logBuffer.append(path);
		logBuffer.append(" ==");
		log.info(logBuffer.toString());
	}

	/**
	 * (17)
	 */
	public OrderHandlingPt getOrderHandlingService() throws ServiceLocatorException {
		
		log.info("=== Inicia getOrderHandlingService/ServiceLocator ===");
		//OrderHandlingPt orderHandlingPt = null;
		try{
/*	    	URL wsdlLocation = new URL(WsdlLocationsEnum.SERVICE_ORDER_HANLING_SERVICE.getWsdlLocation());
			QName serviceName = new QName(WsdlLocationsEnum.SERVICE_ORDER_HANLING_SERVICE.getQnameNameSpace(), WsdlLocationsEnum.SERVICE_ORDER_HANLING_SERVICE.getQnameService());
			QName serviceNameEndPoint = new QName(WsdlLocationsEnum.SERVICE_ORDER_HANLING_SERVICE.getQnameNameSpace(), WsdlLocationsEnum.SERVICE_ORDER_HANLING_SERVICE.getQnameEndPointService());
			OrderHandlingService service = new OrderHandlingService(wsdlLocation, serviceName);
			orderHandlingPt = service.getPort(serviceNameEndPoint, OrderHandlingPt.class);
	    	traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
	    	return orderHandlingPt;
*/	    	
			/*SR469307 - HSP+ Cambio Service locator*/
			URL url = null;
			URL baseUrl;
            baseUrl = com.directvla.contract.businessdomain.orderhandling.v1_0.OrderHandlingService.class.getResource(".");
            url = new URL(baseUrl, "http://localhost/wsdl/OrderHandlingService.wsdl");
            URL wsdlLocation = url;			
			QName serviceName = new QName(WsdlLocationsEnum.SERVICE_ORDER_HANLING_SERVICE.getQnameNameSpace(), WsdlLocationsEnum.SERVICE_ORDER_HANLING_SERVICE.getQnameService());
			OrderHandlingService service = new OrderHandlingService(wsdlLocation, serviceName);			
			OrderHandlingPt customer = service.getOrderHandlingPtPt();						
			BindingProvider bindingProvider = (BindingProvider) customer;
			String serviceURL = WsdlLocationsEnum.SERVICE_ORDER_HANLING_SERVICE.getEndPointService();
		    bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);
		    /*compression*/
			Map<String, List<String>> httpHeaders = new HashMap<String, List<String>>();
	    	httpHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
			bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);
			/*timeout*/
			bindingProvider.getRequestContext().put(CONNECT_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.CONNECT_TIMEOUT.getCodeEntity()));
			bindingProvider.getRequestContext().put(REQUEST_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.REQUEST_TIMEOUT.getCodeEntity()));
		    traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
			return customer;
		}catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(),ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(),e);
		}catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(),e.getMessage(),e);
		}catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(),e);
		}finally{
			log.info("=== Termina getOrderHandlingService/ServiceLocator ===");
		}
		
	}
}
