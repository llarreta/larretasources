package co.com.directv.sdii.mdb.business;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.dto.esb.dispatchtechnician.DispatchWorkOrderResponse;
import co.com.directv.sdii.dto.esb.dispatchtechnician.WithdrawWorkOrderFromTechnicianResponse;
import co.com.directv.sdii.ejb.business.core.DispatchWorkOrderBusinessLocal;

/**
 * MDB dedicado al consumo de mensajes de la cola de eventos de ESB.
 * Esta cola estÃ¡ actualmente estÃ¡ en un ForeignServer (ConfiguraciÃ³n encontrada en el WLS).
 * 
 * @author ssanabri
 *
 */
public class DispatchWorkOrderConsumer implements MessageListener {

	@EJB(name="DispatchWorkOrderBusinessLocal", beanInterface=DispatchWorkOrderBusinessLocal.class)
	private DispatchWorkOrderBusinessLocal dispatchWorkOrderBusinessBean;
	
	private final static Logger log = UtilsBusiness.getLog4J(DispatchWorkOrderConsumer.class);
	
	
	/**
	 * Default constructor.
	 */
	public DispatchWorkOrderConsumer(){
	}
	
	/** 
	 * @param message mensaje que contiene toda la informaciÃ³n del Evento. 
	 * Se presupone que es de tipo ObjectMesage y solo se procesa si el objecto que 
	 * trae esde tipo .
	 */
	@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
	public void onMessage(Message message) {
		try{
			String propertyKey = CodesBusinessEntityEnum.DISPATCH_PROPERTY_CONSUMER_NAME.getCodeEntity();
			String messageSelector = message.getStringProperty(propertyKey);
			
			if(log.isDebugEnabled()){
				log.debug("PorpertyKey: " + propertyKey);
				log.debug("MessageSelector: " + messageSelector);
				log.debug(((TextMessage)message).getText());
			}
			
			byte[] byteMessageArr = ((TextMessage)message).getText().getBytes("UTF-8");
			InputStream inStream = null;
			inStream = new ByteArrayInputStream(byteMessageArr);
			
			
			if(CodesBusinessEntityEnum.DISPATCH_PROPERTY_DISPATCH_WO.getCodeEntity().equals(messageSelector)){
				JAXBContext jc = JAXBContext.newInstance(co.com.directv.sdii.dto.esb.dispatchtechnician.DispatchWorkOrderResponse.class);
		        Unmarshaller u = jc.createUnmarshaller();
		        DispatchWorkOrderResponse dispatchWorkOrderResponse = (DispatchWorkOrderResponse)u.unmarshal(inStream);
				
				dispatchWorkOrderBusinessBean.processDispatchWorkOrderResponse(dispatchWorkOrderResponse, byteMessageArr);
			}else if (CodesBusinessEntityEnum.DISPATCH_PROPERTY_WITHDRAW_WO_FROM_TECHNICIAN.getCodeEntity().equals(messageSelector)){
				JAXBContext jc = JAXBContext.newInstance(co.com.directv.sdii.dto.esb.dispatchtechnician.WithdrawWorkOrderFromTechnicianResponse.class);
		        Unmarshaller u = jc.createUnmarshaller();
		        WithdrawWorkOrderFromTechnicianResponse withDrawWorkOrderResponse = (WithdrawWorkOrderFromTechnicianResponse)u.unmarshal(inStream);
				
				dispatchWorkOrderBusinessBean.processWithdrawWoFromTechResponse(withDrawWorkOrderResponse, byteMessageArr);
			}else{
				log.error("== No se logró interpretar el tipo de mensaje por medio de la property. ==");
			}
				
		} catch (Throwable te) {
			log.error("== Error al tratar de ejecutar la operaciÃ³n onMessage/DispatchWorkOrderConsumer ==", te);
		}
	}
	
}
