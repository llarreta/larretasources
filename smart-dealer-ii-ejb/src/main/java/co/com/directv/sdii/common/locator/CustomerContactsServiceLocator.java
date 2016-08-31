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

import com.directvla.contract.crm.customer.v1.contacts.Customer;
import com.directvla.contract.crm.customer.v1.contacts.PtCustomer;

/**
 * 
 *  Servicio para la busqueda y creacion de servicios por medio
 *  de interfaces complejas expuestas en aplicaciones externas.
 *  Locator para el Consumo del servicio de Clientes para la
 *  Creacion de Contacts.
 *  
 * 
 * Fecha de Creación: 20/11/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class CustomerContactsServiceLocator {

	private static CustomerContactsServiceLocator locator;
	private final static Logger log = UtilsBusiness.getLog4J(CustomerContactsServiceLocator.class);
	/*timeout*/
	private static final String CONNECT_TIMEOUT = "com.sun.xml.ws.connect.timeout";
	private static final String REQUEST_TIMEOUT = "com.sun.xml.internal.ws.request.timeout";

	/**
	 * 
	 * Metodo: retorna una instacia de ServiceLocator, patron singleton.
	 * @return ServiceLocator
	 * @throws ServiceLocatorException
	 * @author jalopez
	 */
	public static CustomerContactsServiceLocator getInstance() throws ServiceLocatorException{
		try{
			if( locator == null ){
				locator = new CustomerContactsServiceLocator();
			}
			return locator;
		}catch (Exception e) {
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_NOT_INSTANCE.getCode(),ErrorBusinessMessages.SERVICE_LOCATOR_NOT_INSTANCE.getMessage(),e);
		}
	}
	
	/**
	 * 
	 * Metodo: Permite obtener la referencia al web service de contacts expuesto por IBS
	 * @return PtCustomer- Referencia al WS para invocar las operaciones de contacts en IBS
	 * @throws ServiceLocatorException 
	 * @author jalopez
	 */
	public PtCustomer getCustomerContactsService() throws ServiceLocatorException {
		log.info("=== Inicia getCustomerContactsService/ServiceLocator ===");
		PtCustomer customer = null;
		try{
	    	//URL wsdlLocation = new URL(WsdlLocationsEnum.CUSTOMER_CONTACTS_WSDL_LOCATION.getWsdlLocation());
			//QName serviceName = new QName(WsdlLocationsEnum.CUSTOMER_CONTACTS_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.CUSTOMER_CONTACTS_WSDL_LOCATION.getQnameService());
			//QName serviceNameEndPoint = new QName(WsdlLocationsEnum.CUSTOMER_CONTACTS_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.CUSTOMER_CONTACTS_WSDL_LOCATION.getQnameEndPointService());
	    	//Customer service = new Customer(wsdlLocation, serviceName);
	    	//customer = service.getPort(serviceNameEndPoint, PtCustomer.class);
			//traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());

			/*SR469307 - HSP+ Cambio Service locator*/
			URL url = null; 
			URL baseUrl = com.directvla.contract.crm.customer.v1.contacts.Customer.class.getResource(".");
			url = new URL(baseUrl, "http://localhost/wsdl/Customer.wsdl");
			URL wsdlLocation = url;
			QName serviceName = new QName(WsdlLocationsEnum.CUSTOMER_CONTACTS_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.CUSTOMER_CONTACTS_WSDL_LOCATION.getQnameService());
			Customer service = new Customer(wsdlLocation,serviceName);  
			customer = service.getPtCustomerPt();
			BindingProvider bindingProvider = (BindingProvider) customer;
			String contactURL = WsdlLocationsEnum.CUSTOMER_CONTACTS_WSDL_LOCATION.getEndPointService();
			bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, contactURL);
			/*compression*/
			Map<String, List<String>> httpHeaders = new HashMap<String, List<String>>();
	    	httpHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
			bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);
			/*timeout*/
			bindingProvider.getRequestContext().put(CONNECT_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.CONNECT_TIMEOUT.getCodeEntity()));
			bindingProvider.getRequestContext().put(REQUEST_TIMEOUT, Integer.valueOf(CodesBusinessEntityEnum.REQUEST_TIMEOUT.getCodeEntity()));
			traceInvocation(wsdlLocation.getAuthority(),wsdlLocation.getPath());
			
		}catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(),ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(),e);
		}catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(),e.getMessage(),e);
		}catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(),e);
		}finally{
			log.info("=== Termina getCustomerContactsService/ServiceLocator ===");
		}
		return customer;
	}
	
	/**
	 * Metodo: Escribe en el log la traza de la invocacion
	 * del servicio de IBS.
	 * @param authority String
	 * @param path String
	 * @author jalopez
	 */
	private void traceInvocation(String authority, String path){
		StringBuffer logBuffer = new StringBuffer();
		logBuffer.append("== Consumo Servicio IBS: ");
		logBuffer.append(authority);
		logBuffer.append(path);
		logBuffer.append(" ==");
		log.debug(logBuffer.toString());
	}
}
