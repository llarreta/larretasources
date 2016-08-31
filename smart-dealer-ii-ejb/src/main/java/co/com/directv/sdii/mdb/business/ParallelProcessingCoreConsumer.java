package co.com.directv.sdii.mdb.business;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.core.CoreWOImporterLocal;
import co.com.directv.sdii.ejb.business.core.WoInfoEsbServiceBusinessLocal;
import co.com.directv.sdii.model.dto.MessageCoreAllocatorDTO;
import co.com.directv.sdii.model.dto.WorkOrderDTO;

/**
 * Message-Driven Bean implementation class for:
 * ParallelProcessingCoreConsumer
 * 
 * @author Aharker
 * 
 * MDB dedicado al consumo de mensajes de la cola de core para el procesamiento en paralelo
 * 
 */

public class ParallelProcessingCoreConsumer implements MessageListener {

	@EJB(name="CoreWOImporterLocal",beanInterface=CoreWOImporterLocal.class)
	private CoreWOImporterLocal coreWoImporterBean;
	
	@EJB(name="WoInfoEsbServiceBusinessLocal",beanInterface=WoInfoEsbServiceBusinessLocal.class)
	private WoInfoEsbServiceBusinessLocal woInfoEsbServiceBusinessLocal;
	
	private final static Logger log = UtilsBusiness.getLog4J(ParallelProcessingAllocatorConsumer.class);
	
	/**
	 * Default constructor.
	 */
	public ParallelProcessingCoreConsumer() {
	}

	/**
	 * @param message mensaje a procesar por asignador, se presupone que es de tipo ObjectMessage y solo se procesa si el obgjeto que trae es de tipo 
	 * MessageCoreAllocatorDTO
	 * 
	 * @author Aharker
	 * 
	 */
	@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
	public void onMessage(Message message) {
		try {
			ObjectMessage objMsg = (ObjectMessage) message;
			Object objInternal;

			objInternal = objMsg.getObject();

			if (objInternal instanceof MessageCoreAllocatorDTO) {
				MessageCoreAllocatorDTO objInternalMessage = (MessageCoreAllocatorDTO) objInternal;
				try {
					if (objInternalMessage.getProcessCode().equalsIgnoreCase(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_CORE.getCodeEntity())) {
						co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent publishWOEvent = woInfoEsbServiceBusinessLocal.createWoIbsFromXmlData(objInternalMessage);
						
						WorkOrderDTO workOrderDto = new WorkOrderDTO();
						coreWoImporterBean.addWorkOrder(publishWOEvent, workOrderDto, objInternalMessage.getCountryCode(), objInternalMessage.getId(), objInternalMessage.getCountryId());
					}
				} catch (Throwable ex) {
					log.error("== Error al tratar de ejecutar la operación onMessage/ParallelProcessingCoreConsumer ==" + ex);
				}
			}
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación onMessage/ParallelProcessingCoreConsumer =="+ex);
		}
	}

}
