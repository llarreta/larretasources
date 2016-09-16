package co.com.directv.sdii.mdb.business;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.stock.MovementQueueBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.MovementInventoryListDTO;

public class MovementInventoryConsumer implements MessageListener {
	
	private final static Logger log = UtilsBusiness.getLog4J(MovementInventoryConsumer.class);
	
	@EJB
	private MovementQueueBusinessBeanLocal movementQueueBusinessBean;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void onMessage(Message msg) {
		log.debug("== Inicia onMessage/MovementInventoryConsumer ==");
		ObjectMessage objMsg = (ObjectMessage) msg;
		MovementInventoryListDTO request = null;
		
		try {
			Object o = objMsg.getObject();
			if (o != null) {
				if(o instanceof MovementInventoryListDTO){
					request = (MovementInventoryListDTO) o;
					log.debug("== Inicia Llamado a inventarios/MovementInventoryConsumer ==");
					try {
						log.info("== Llamado createQueueMovementInventoryByWorkOrder/MovementInventoryConsumer ==");
						log.info( request.toString() );
						movementQueueBusinessBean.createQueueMovementInventoryByWorkOrder(request);
					} catch (BusinessException e) {
						log.error("== Error Llamado a inventarios/MovementInventoryConsumer ==", e);
					} finally{
						log.debug("== Finaliza Llamado a inventarios/MovementInventoryConsumer ==");
					}
				}
			}
		} catch (JMSException e) {
			log.error("== Error onMessage/MovementInventoryConsumer ==", e);
		} finally {
			log.debug("== Finaliza onMessage/MovementInventoryConsumer ==");
		}
	}
	
}