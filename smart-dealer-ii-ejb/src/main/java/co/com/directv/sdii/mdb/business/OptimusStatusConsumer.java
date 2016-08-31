package co.com.directv.sdii.mdb.business;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.dto.esb.notifyWorkOrdersOptimus.WorkOrderStatusChange;
import co.com.directv.sdii.ejb.business.config.OptimusStatusEventBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.wo.RequestProcessor;
import co.com.directv.sdii.ejb.business.wo.RequestProcessorDeclineWorkOrder;
import co.com.directv.sdii.ejb.business.wo.RequestProcessorStatusMessage;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.OptimusStatusEvent;
import co.com.ig.common.error.utils.XmlUnmarshaller;

public class OptimusStatusConsumer implements MessageListener {

	@EJB(name = "OptimusStatusEventBusinessBeanLocal", beanInterface=OptimusStatusEventBusinessBeanLocal.class)
	private OptimusStatusEventBusinessBeanLocal optimusStatusEventBusinessBean;

	@EJB(name = "RequestProcessorDeclineWorkOrder", beanInterface = RequestProcessorDeclineWorkOrder.class)
	private RequestProcessorDeclineWorkOrder requestProcessorDeclineWorkOrder;

	@EJB(name = "RequestProcessorStatusMessage", beanInterface = RequestProcessorStatusMessage.class)
	private RequestProcessorStatusMessage requestProcessorStatusMessage;
	
	private final static Logger log = UtilsBusiness.getLog4J(OptimusStatusConsumer.class);

	@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
	public void onMessage(Message message) {
		TextMessage msg = (TextMessage) message;
		WorkOrderStatusChange request = null;
		RequestProcessor processor = null;

		log.debug("== Inicia llamado a onMessage/OptimusStatusConsumer == ");

		try {

			request = (WorkOrderStatusChange) XmlUnmarshaller.unmarshaller(WorkOrderStatusChange.class, msg.getText());
			byte[] byteMessageArr = msg.getText().getBytes("UTF-8");
			OptimusStatusEvent optimusStatusEvent = this.createOptimusStatusEvent(request, byteMessageArr, msg.getStringProperty(CodesBusinessEntityEnum.OPTIMUS_PROPERTY_CONSUMER_NAME.getCodeEntity()));			
			processor = this.verifyCodeAndDelegate(msg, optimusStatusEvent);
			processor.start(request, optimusStatusEvent);

		} catch (Throwable e) {
			log.error("== Error en onMessage/OptimusStatusConsumer ==", e);			
		}  finally {
			log.debug("== Finaliza llamado a onMessage/OptimusStatusConsumer ==");
		}
	}
 
	private RequestProcessor verifyCodeAndDelegate(Message message, OptimusStatusEvent optimusStatusEvent) throws BusinessException, PropertiesException {
		RequestProcessor result = null;
		try {
			String propertyKey = CodesBusinessEntityEnum.OPTIMUS_PROPERTY_CONSUMER_NAME.getCodeEntity();
			String messageSelector = message.getStringProperty(propertyKey);
			
			if (log.isDebugEnabled()) {
				log.debug("PorpertyKey: " + propertyKey);
				log.debug("MessageSelector: " + messageSelector);
				log.debug(((TextMessage) message).getText());
			}
			
			if (CodesBusinessEntityEnum.CODE_MESSAGE_DECLINE.getCodeEntity().equals(messageSelector)) {
				result = requestProcessorDeclineWorkOrder;
			}else if (CodesBusinessEntityEnum.CODE_MESSAGE_STATUS_WO.getCodeEntity().equals(messageSelector)) {
				result = requestProcessorStatusMessage;
			}else{
				optimusStatusEvent.setLogDescription("No se logra interpretar la propiedad con clave: "+propertyKey+" y valor: "+ messageSelector);
				optimusStatusEvent.setStatus(CodesBusinessEntityEnum.OPTIMUS_STATUS_EVENT_ERROR.getCodeEntity());
				optimusStatusEventBusinessBean.updateOptimusStatusEvent(optimusStatusEvent);
			}
				
		} catch (JMSException jme) {
			throw new BusinessException("La propiedad \"type\" no contiene un valor válido");
		}
		
		return result;
	}

	
	private OptimusStatusEvent createOptimusStatusEvent(WorkOrderStatusChange workOrderStatusChange, byte[] byteMessageArr, String type) throws BusinessException, PropertiesException{
		//Cuando entra el msg se persiste como error hasta que termine de procesar y haga el update a OK.
		OptimusStatusEvent event = new OptimusStatusEvent();
		event.setType(type);
		event.setWoCode(workOrderStatusChange.getCustWorkOrd().getID());
		event.setCreationDate(new Date());
        event.setStatus(CodesBusinessEntityEnum.OPTIMUS_STATUS_EVENT_ERROR.getCodeEntity());
        event.setLogDescription("Inicialización de Evento.");
        optimusStatusEventBusinessBean.createOptimusStatusEvent(event, byteMessageArr);
        return event;
	}

}
