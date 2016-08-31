package co.com.directv.sdii.common.locator;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.enumerations.WsdlLocationsEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;

import com.directvla.inventoryinterface.IInventory;
import com.directvla.inventoryinterface.Inventory;

/**
 * 
 *  Servicio para la busqueda y creacion de servicios por medio
 *  de interfaces complejas expuestas en aplicaciones externas.
 *  Locator para el Consumo del servicio de Inventarios para la
 *  para la actualizacion de Field Services.
 *  
 * 
 * Fecha de Creación: 1/06/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ExternalInventoryServiceLocator {
	
	private static ExternalInventoryServiceLocator locator;
	private final static Logger log = UtilsBusiness.getLog4J(ExternalInventoryServiceLocator.class);
	
	/**
	 * 
	 * Metodo: retorna una instacia de ServiceLocator, patron singleton.
	 * @return ServiceLocator
	 * @throws ServiceLocatorException
	 * @author jalopez
	 */
	public static ExternalInventoryServiceLocator getInstance() throws ServiceLocatorException{
		try{
			if( locator == null ){
				locator = new ExternalInventoryServiceLocator();
			}
			return locator;
		}catch (Exception e) {
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_NOT_INSTANCE.getCode(),ErrorBusinessMessages.SERVICE_LOCATOR_NOT_INSTANCE.getMessage(),e);
		}
	}
	
	/**
	 * 
	 * Metodo: Permite obtener la referencia al web service 
	 * de inventory expuesto por Field Services
	 * @return PtCustomer- Referencia al WS para invocar las 
	 * operaciones de inventory de Field Services
	 * @throws ServiceLocatorException 
	 * @author jalopez
	 */
	public IInventory getInventoryInterfaceService() throws ServiceLocatorException {
		log.info("=== Inicia getInventoryInterfaceService/ExternalInventoryServiceLocator ===");
		IInventory inventoryInterface = null;
		try{
			
	    	URL wsdlLocation = new URL(WsdlLocationsEnum.INVENTORY_INTERFACE_WSDL_LOCATION.getWsdlLocation());
			QName serviceName = new QName(WsdlLocationsEnum.INVENTORY_INTERFACE_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.INVENTORY_INTERFACE_WSDL_LOCATION.getQnameService());
			QName serviceNameEndPoint = new QName(WsdlLocationsEnum.INVENTORY_INTERFACE_WSDL_LOCATION.getQnameNameSpace(), WsdlLocationsEnum.INVENTORY_INTERFACE_WSDL_LOCATION.getQnameEndPointService());
	    	Inventory service = new Inventory(wsdlLocation, serviceName);
	    	inventoryInterface = service.getPort(serviceNameEndPoint, IInventory.class);
			traceInvocation(wsdlLocation.getAuthority(), wsdlLocation.getPath());
		}catch (MalformedURLException e) { 
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getCode(),ErrorBusinessMessages.SERVICE_LOCATOR_URL_MALFORMED.getMessage(),e);
		}catch (PropertiesException e) {
			throw new ServiceLocatorException(e.getMessageCode(),e.getMessage(),e);
		}catch (Exception e) {
			throw new ServiceLocatorException(e.getMessage(),e);
		}finally{
			log.info("=== Termina getInventoryInterfaceService/ExternalInventoryServiceLocator ===");
		}
		return inventoryInterface;
	}
	
	/**
	 * Metodo: Escribe en el log la traza de la invocacion
	 * del servicio de Field Services.
	 * @param authority String
	 * @param path String
	 * @author jalopez
	 */
	private void traceInvocation(String authority, String path){
		StringBuffer logBuffer = new StringBuffer();
		logBuffer.append("== Consumo Servicio Field Services: ");
		logBuffer.append(authority);
		logBuffer.append(path);
		logBuffer.append(" ==");
		log.debug(logBuffer.toString());
	}
}
