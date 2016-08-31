/**
 * Creado 20/10/2010 10:31:04
 */
package co.com.directv.sdii.ejb.business.broker.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.JMSLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.dto.esb.dispatchtechnician.DispatchWorkOrder;
import co.com.directv.sdii.dto.esb.dispatchtechnician.DispatchWorkOrderRequest;
import co.com.directv.sdii.dto.esb.dispatchtechnician.PhysicalDevice;
import co.com.directv.sdii.dto.esb.dispatchtechnician.PhysicalDeviceCollection;
import co.com.directv.sdii.dto.esb.dispatchtechnician.RequestMetadataType;
import co.com.directv.sdii.dto.esb.dispatchtechnician.SenderType;
import co.com.directv.sdii.dto.esb.dispatchtechnician.ServiceProvider;
import co.com.directv.sdii.dto.esb.dispatchtechnician.TargetType;
import co.com.directv.sdii.dto.esb.dispatchtechnician.TelecomTechnician;
import co.com.directv.sdii.dto.esb.dispatchtechnician.WithdrawWorkOrderFromTechnician;
import co.com.directv.sdii.dto.esb.dispatchtechnician.WithdrawWorkOrderFromTechnicianRequest;
import co.com.directv.sdii.dto.esb.dispatchtechnician.WorkOrderCollection;
import co.com.directv.sdii.ejb.business.IBSWSBase;
import co.com.directv.sdii.ejb.business.broker.DistributedQueueMessageBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.DistributedQueueMessageBrokerRemote;
import co.com.directv.sdii.ejb.business.broker.IServiceBroker;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.jms.common.util.DistributedQueueMessage;
import co.com.directv.sdii.model.dto.KpiCalculateDTO;
import co.com.directv.sdii.model.dto.MovementInventoryListDTO;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WorkOrder;

/**
 * Implementa la comunicación con el Queue  
 * 
 * Fecha de Creación: 14/02/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="DistributedQueueMessageBrokerLocal")
@Local({DistributedQueueMessageBrokerLocal.class})
@Remote({DistributedQueueMessageBrokerRemote.class})
public class DistributedQueueMessageBrokerImpl extends IBSWSBase implements
			 DistributedQueueMessageBrokerLocal,DistributedQueueMessageBrokerRemote, IServiceBroker {

	private final static Logger log = UtilsBusiness.getLog4J(DistributedQueueMessageBrokerImpl.class);

	@Override
	public void setQueueCalculateKpi(KpiCalculateDTO request) throws BusinessException {
		
	  log.debug("== Inicia la operación setQueueMovementInventory/DistributedQueueMessageBrokerImpl ==");
	  
      try { 
    	  
    	  DistributedQueueMessage distributedQueueMessage =JMSLocator.getInstance().queueCalculateKpi();
    	  distributedQueueMessage.sendMessage(request);
    	  
      } catch (Throwable ex) { 
    	  	ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operación setQueueMovementInventory/DistributedQueueMessageBrokerImpll ==");
			throw manageServiceException(ex);
      } finally{
			log.debug("== Termina la operación setQueueMovementInventory/DistributedQueueMessageBrokerImpll ==");
	  }
	}
	
	@Override
	public BusinessException manageServiceException(Throwable e) {
		BusinessException be = null;
		String errorCode = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getCode();
		String errorMessage = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getMessage();
		
		if(e instanceof BusinessException){
			be = (BusinessException)e;
		}else{
			be = super.manageException(e);
		}
		return be;
	}

	/**
	 * Metodo encargado de enviar un mensaje a la cola de movimientos de inventario
	 * @param request datos necesarios del movimiento de inventarios de la work order.
	 * @throws BusinessException
	 */
	@Override
	public void setQueueMovementInventory(MovementInventoryListDTO request)
			throws BusinessException {
		
		  log.debug("== Inicia la operación setQueueMovementInventory/DistributedQueueMessageBrokerImpl ==");
		  
	      try { 
	    	  
	    	  DistributedQueueMessage distributedQueueMessage =JMSLocator.getInstance().queueMovementInventory();
	    	  distributedQueueMessage.sendMessage(request);
	    	  
	      } catch (Throwable ex) { 
	    	  	ex.printStackTrace();
				log.debug("== Error al tratar de ejecutar la operación setQueueMovementInventory/DistributedQueueMessageBrokerImpll ==");
				throw manageServiceException(ex);
	      } finally{
				log.debug("== Termina la operación setQueueMovementInventory/DistributedQueueMessageBrokerImpll ==");
		  }
	}

	
	public void sendESBDispatchWorkOrderMessages(String systemToNotify, User user, Employee employee, List<WorkOrder> woList) throws BusinessException{
		log.debug("== Inicia la operación sendESBDispatchWorkOrderMessage/DistributedQueueMessageBrokerImpl ==");
		 try {
			 String customerCode = woList.get(0).getCustomer().getCustomerCode();
			 
			 logOperation("ESBDispatchWorkOrderMessage", systemToNotify, user, employee, woList, customerCode);
			 
			 DispatchWorkOrder dispatchWorkOrder = new DispatchWorkOrder();
			 dispatchWorkOrder.setTechnician(this.buildTelecomTechnicianESBDispatchWo(employee));
			 dispatchWorkOrder.setWorkOrderCollection(this.buildWorkOrderCollectionESBDispatchWo(woList, customerCode));
			 DispatchWorkOrderRequest request = new DispatchWorkOrderRequest();
			 request.setRequestMetadata(this.buildMetadataESBDispatchWo(systemToNotify, user, user.getCountry()));
			 request.setDispatchWorkOrder(dispatchWorkOrder);
			 
			 DistributedQueueMessage distributedQueueMessage = JMSLocator.getInstance().getQueueDispatchTechnician();
			 JAXBContext jc = JAXBContext.newInstance(co.com.directv.sdii.dto.esb.dispatchtechnician.DispatchWorkOrderRequest.class);
			 Marshaller m = jc.createMarshaller();
			 java.io.OutputStream os = new java.io.ByteArrayOutputStream();
			 m.marshal( request, new java.io.PrintWriter(os) );
			 
			 Map<String,String> properties = new HashMap<String, String>();
			 properties.put(CodesBusinessEntityEnum.DISPATCH_PROPERTY_NAME.getCodeEntity(), CodesBusinessEntityEnum.DISPATCH_PROPERTY_DISPATCH_WO.getCodeEntity());
			 
			 distributedQueueMessage.sendTextMessage(os.toString(), properties);

	      } catch (Throwable ex) { 
	    	  	ex.printStackTrace();
				log.debug("== Error al tratar de ejecutar la operación sendESBDispatchWorkOrderMessage/DistributedQueueMessageBrokerImpll ==");
				throw manageServiceException(ex);
	      } finally{
				log.debug("== Termina la operación sendESBDispatchWorkOrderMessage/DistributedQueueMessageBrokerImpll ==");
		  }
	}
	
	public void sendESBWithdrawWorkOrderMessages(String systemToNotify, User user, Employee employee, List<WorkOrder> woList) throws BusinessException{
		log.debug("== Inicia la operación sendESBWithdrawWorkOrderMessage/DistributedQueueMessageBrokerImpl ==");
		 try {
			String customerCode = woList.get(0).getCustomer().getCustomerCode();
			 
			logOperation("ESBWithdrawWorkOrderMessage", systemToNotify, user, employee, woList, customerCode);
			 
			 WithdrawWorkOrderFromTechnician withdrawWo = new WithdrawWorkOrderFromTechnician();
			 withdrawWo.setTechnician(this.buildTelecomTechnicianESBDispatchWo(employee));
			 withdrawWo.setWorkOrderCollection(this.buildWorkOrderCollectionESBDispatchWo(woList, customerCode));
			 WithdrawWorkOrderFromTechnicianRequest request = new WithdrawWorkOrderFromTechnicianRequest();
			 request.setRequestMetadata(this.buildMetadataESBDispatchWo(systemToNotify, user, user.getCountry()));
			 request.setWithdrawWorkOrderFromTechnician(withdrawWo);				
			 
			 DistributedQueueMessage distributedQueueMessage = JMSLocator.getInstance().getQueueDispatchTechnician();
			 JAXBContext jc = JAXBContext.newInstance(co.com.directv.sdii.dto.esb.dispatchtechnician.WithdrawWorkOrderFromTechnicianRequest.class);
			 Marshaller m = jc.createMarshaller();
			 java.io.OutputStream os = new java.io.ByteArrayOutputStream();
			 m.marshal( request, new java.io.PrintWriter(os) );
			 
			 Map<String,String> properties = new HashMap<String, String>();
			 properties.put(CodesBusinessEntityEnum.DISPATCH_PROPERTY_NAME.getCodeEntity(), CodesBusinessEntityEnum.DISPATCH_PROPERTY_WITHDRAW_WO_FROM_TECHNICIAN.getCodeEntity());
			 
			 distributedQueueMessage.sendTextMessage(os.toString(), properties);
			
		 } catch (Throwable ex) { 
	    	  	ex.printStackTrace();
				log.debug("== Error al tratar de ejecutar la operación sendESBWithdrawWorkOrderMessage/DistributedQueueMessageBrokerImpll ==");
				throw manageServiceException(ex);
	      } finally{
				log.debug("== Termina la operación sendESBWithdrawWorkOrderMessage/DistributedQueueMessageBrokerImpll ==");
		  }
	}

	private RequestMetadataType buildMetadataESBDispatchWo(String systemToNotify, User user, Country country) throws PropertiesException{
		RequestMetadataType metadata = new RequestMetadataType();
		metadata.setUserID(user.getLogin());
		metadata.setSourceID(country.getCountryCode());
		metadata.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity()); 
		Random r = new Random();
		metadata.setRequestID(r.nextInt(1000000)+"");
		TargetType tt = new TargetType();
		tt.setName(systemToNotify);
		metadata.setTarget(tt);
		
		SenderType st = new SenderType();
		st.setCountry(user.getCountry().getCountryCode());
		metadata.setSender(st);
		return metadata;
	}
	
	private TelecomTechnician buildTelecomTechnicianESBDispatchWo(Employee employee){
		TelecomTechnician technician = new TelecomTechnician();
		technician.setID(employee.getDocumentNumber());
		technician.setDealer(new ServiceProvider());
		PhysicalDeviceCollection physicalDeviceCollection = new PhysicalDeviceCollection();
		PhysicalDevice phyisicalDevice = new PhysicalDevice();

		String pin = (employee.getPin() != null) ? employee.getPin() : "";
		phyisicalDevice.setBrand(pin);
		phyisicalDevice.setPin(pin);
		phyisicalDevice.setImei(pin);
		phyisicalDevice.setAni(pin);
		phyisicalDevice.setFirmware(pin);
		
		physicalDeviceCollection.getPhysicalDevice().add(phyisicalDevice);
		technician.setOwnsResource(physicalDeviceCollection);
		return technician;
	}
	
	private WorkOrderCollection buildWorkOrderCollectionESBDispatchWo(List<WorkOrder> woList, String customerKey){
		WorkOrderCollection woCollection = new WorkOrderCollection();
		for(WorkOrder wo : woList){
			co.com.directv.sdii.dto.esb.dispatchtechnician.WorkOrder esbWo = new co.com.directv.sdii.dto.esb.dispatchtechnician.WorkOrder();
			esbWo.setID(wo.getWoCode());
			esbWo.setCustomerKey(customerKey);
			woCollection.getWorkOrder().add(esbWo);					
		}
		return woCollection;
	}
	
	private void logOperation(String operation, String systemToNotify, User user, Employee employee, List<WorkOrder> woList, String customerKey) {
		HashMap<String, String> logRequestValues = new HashMap<String, String>();
		logRequestValues.put("SystemToNotify", systemToNotify);
		logRequestValues.put("country", user.getCountry().getCountryCode());
		logRequestValues.put("employee document", employee.getDocumentNumber());
		logRequestValues.put("pin", employee.getPin());
		logRequestValues.put("customer key", customerKey);
		int i = 0;
		for(WorkOrder wo : woList){
			logRequestValues.put("woCode"+(++i), wo.getWoCode() );				
		}
		logOperationInvoke(operation, logRequestValues);
	}
	
	public void logOperationInvoke(String operation, HashMap<String, String> requestValueMap){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = new Object[2];	
		
		buffer.append(logMetadata(requestValueMap));
		
		//Construcccion del mensaje
		params[0] = operation;
		params[1] = buffer.toString();
		message.append("== ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getCode() );
		message.append(" : ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getMessage(params) );
		message.append(" ==");
		log.info( message.toString() );
		
	}
	
	private String logMetadata(HashMap<String, String> requestValueMap){
		StringBuilder buffer = new StringBuilder();
		buffer.append(" Metadata [");
		java.util.Iterator<Entry<String, String>> iterator = requestValueMap.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String,String> each = iterator.next();
			buffer.append(" " + each.getKey() + ":" + each.getValue());
		}
		buffer.append(" ]");
		return buffer.toString();
	}
	
}
