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
import co.com.directv.sdii.ejb.business.allocator.AllocatorBeanHelperLocal;
import co.com.directv.sdii.ejb.business.core.WoInfoEsbServiceBusinessLocal;
import co.com.directv.sdii.model.dto.MessageCoreAllocatorDTO;

/**
 * Message-Driven Bean implementation class for:
 * ParallelProcessingCoreAllocatorConsumer
 * 
 * @author Aharker
 * 
 * MDB dedicado al consumo de mensajes de la cola de asignador para el procesamiento en paralelo
 * 
 */

public class ParallelProcessingAllocatorConsumer implements MessageListener {

	@EJB(name="AllocatorBeanHelperLocal", beanInterface=AllocatorBeanHelperLocal.class)
	private AllocatorBeanHelperLocal allocatorBean;
	
	@EJB(name="WoInfoEsbServiceBusinessLocal",beanInterface=WoInfoEsbServiceBusinessLocal.class)
	private WoInfoEsbServiceBusinessLocal woInfoEsbServiceBusinessLocal;
	
	private final static Logger log = UtilsBusiness.getLog4J(ParallelProcessingAllocatorConsumer.class);
	
	/**
	 * Default constructor.
	 */
	public ParallelProcessingAllocatorConsumer() {
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
				MessageCoreAllocatorDTO objInternalMessage=(MessageCoreAllocatorDTO)objInternal;
				
				if(objInternalMessage.getProcessCode().equalsIgnoreCase(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_ALLOCATOR.getCodeEntity())){
					log.info("== operación onMessage/ParallelProcessingAllocatorConsumer == "+objInternalMessage.getWoCode());
					allocatorBean.assignDealer(objInternalMessage.getWoCode(), objInternalMessage.getCountryCode(), objInternalMessage.getId(), objInternalMessage.getCountryId());
				}
				
			}
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación onMessage/ParallelProcessingAllocatorConsumer =="+ex);
		}
	}

}
