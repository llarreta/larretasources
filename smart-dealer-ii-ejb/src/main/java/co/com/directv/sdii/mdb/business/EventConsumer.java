package co.com.directv.sdii.mdb.business;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.ejb.EJB;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.core.CoreWOImporterLocal;

/**
 * MDB dedicado al consumo de mensajes de la cola de eventos de ESB.
 * Esta cola está actualmente está en un ForeignServer (Configuración encontrada en el WLS).
 * 
 * @author ssanabri
 *
 */
public class EventConsumer implements MessageListener {

	@EJB(name="CoreWOImporterLocal", beanInterface=CoreWOImporterLocal.class)
	private CoreWOImporterLocal coreWoImporterBean;
	
	private final static Logger log = UtilsBusiness.getLog4J(EventConsumer.class);
	
	
	/**
	 * Default constructor.
	 */
	public EventConsumer(){
	}
	
	/** 
	 * @param message mensaje que contiene toda la información del Evento. 
	 * Se presupone que es de tipo ObjectMesage y solo se procesa si el objecto que 
	 * trae esde tipo .
	 */
	public void onMessage(Message message) {
		try{
			if(log.isDebugEnabled()){
				log.debug(((TextMessage)message).getText());
			}
			
			InputStream inStream = null;
			inStream = new ByteArrayInputStream(((TextMessage)message).getText().getBytes("UTF-8"));
	        JAXBContext jc = JAXBContext.newInstance(co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent.class);
	        Unmarshaller u = jc.createUnmarshaller();
	        co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent publishWOEvent = (co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent)u.unmarshal(inStream);
				
			String iso3Code = (String)message.getObjectProperty("iso3Code");
			coreWoImporterBean.addEvent(publishWOEvent, iso3Code);
				
		} catch (Throwable te) {
			log.error("== Error al tratar de ejecutar la operación onMessage/EventConsumer ==" + te);
		}
	}
	
}
