package co.com.directv.sdii.ejb.business.broker.impl;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.ServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.broker.IServiceBroker;
import co.com.directv.sdii.ejb.business.broker.IShippingOrderServiceBroker;
import co.com.directv.sdii.ejb.business.broker.toa.ShippingOrderServiceTOA;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import ec.com.directv.apps.ShippingOrderSoap;

/**
 * 
 * Broker que implementa las operaciones de negocio para la
 * comunicacion con el servicio de Shipping Order publicado en 
 * Ecuador 
 * 
 * Fecha de Creación: 19/07/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ShippingOrderServiceBrokerImpl extends BusinessBase implements IShippingOrderServiceBroker, IServiceBroker {
	
	private static ShippingOrderServiceBrokerImpl broker;
	private final static Logger log = UtilsBusiness.getLog4J(ShippingOrderServiceBrokerImpl.class);
	
	/**
	 * 
	 * Metodo: retorna una instacia de ShippingOrderServiceBrokerImpl, patron singleton.
	 * @return ShippingOrderServiceBrokerImpl
	 * @throws ServiceLocatorException
	 * @author jnova
	 */
	public static ShippingOrderServiceBrokerImpl getInstance() throws ServiceLocatorException{
		try{
			if( broker == null ){
				broker = new ShippingOrderServiceBrokerImpl();
			}
			return broker;
		}catch (Exception e) {
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_NOT_INSTANCE.getCode(),ErrorBusinessMessages.SERVICE_LOCATOR_NOT_INSTANCE.getMessage(),e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.IShippingOrderServiceBroker#shipOrder(int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String shipOrder(int pSubscriber, int pWorkOrder, String pSerialNumerSC, String pSerialNumerIRD, String pComment, String pInstaller) throws BusinessException{
		log.debug("== Inicia la operación shipOrder/ShippingOrderServiceBrokerImpl ==");
		try {
			ServiceLocator servicelocator = ServiceLocator.getInstance();
			ShippingOrderSoap service = servicelocator.getShippingOrderService();
			String response = service.shipOrder(pSubscriber, pWorkOrder, pSerialNumerSC, pSerialNumerIRD, pComment, pInstaller);
			logShipOrder(pSubscriber, pWorkOrder, pSerialNumerSC, pSerialNumerIRD, pComment, pInstaller,response);
			ShippingOrderServiceTOA.getShipOrderResponse(response);
			return response;
		} catch (Throwable ex) {
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operación shipOrder/ShippingOrderServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación shipOrder/ShippingOrderServiceBrokerImpl ==");
		}
	}

	@Override
	public BusinessException manageServiceException(Throwable e) {		
		BusinessException be = null;
		String errorCode = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getCode();
		//String errorMessage = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getMessage();
		if( e instanceof SOAPFaultException ){
			Node node = ((SOAPFaultException) e).getFault().getDetail().getFirstChild().getFirstChild().getFirstChild().getNextSibling().getFirstChild();
			be = new BusinessException(errorCode, node.getNodeValue());
		}else if( e instanceof BusinessException ){		
			be = new BusinessException(((BusinessException) e).getMessageCode(), e.getMessage(), ((BusinessException) e).getParameters());
		}else{
			be = new BusinessException(errorCode, e.getMessage());
		}
		return be;
	}
	
	/**
	 * 
	 * Metodo: Realiza el log de la operacion ShipOrder
	 * @param pSubscriber
	 * @param pWorkOrder
	 * @param pSerialNumerSC
	 * @param pSerialNumerIRD
	 * @param pComment
	 * @param pInstaller <tipo> <descripcion>
	 * @author jnova
	 */
	private static void logShipOrder(int pSubscriber, int pWorkOrder, String pSerialNumerSC, String pSerialNumerIRD, String pComment, String pInstaller, String response){
		StringBuilder buffer = new StringBuilder();
		buffer.append("ShipOder");
		buffer.append(" ==");
		buffer.append(" pSubscriber: " + ( pSubscriber == 0 ? "null" : pSubscriber ) );
		buffer.append(" pWorkOrder: " + ( pWorkOrder == 0 ? "null" : pWorkOrder ));
		buffer.append(" pSerialNumerSC: " + pSerialNumerSC);
		buffer.append(" pSerialNumerIRD: " + pSerialNumerIRD);
		buffer.append(" pComment: " + pComment);
		buffer.append(" pInstaller: " + pInstaller);
		buffer.append(" ShipOrderResult: " + response);
		buffer.append(" ==");
		log.info(buffer.toString());
	}

}
