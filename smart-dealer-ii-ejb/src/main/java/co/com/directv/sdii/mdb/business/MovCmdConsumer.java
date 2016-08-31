
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
import co.com.directv.sdii.ejb.business.stock.MovCmdBusinessBeanLocal;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;
/*
 * Req-0098 - Paralelismo de Inventarios
 */
public class MovCmdConsumer implements MessageListener {
	
	private final static Logger log = UtilsBusiness.getLog4J(MovCmdConsumer.class);
		
	@EJB(name="MovCmdBusinessBeanLocal", beanInterface=MovCmdBusinessBeanLocal.class)
	private MovCmdBusinessBeanLocal movCmdBusinessBean;
	
	private static final long serialVersionUID = 1L;
	
	public MovCmdConsumer() {
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void onMessage(Message msg) {
		//log.debug("== Inicia onMessage/MovCmdConsumer ==");
		if (log.isDebugEnabled()) log.debug("== Inicia onMessage/MovCmdConsumer ==" );
		ObjectMessage objMsg = (ObjectMessage) msg;
		MovCmdQueueVO request = null;
		
		try {
			Object o = objMsg.getObject();
			if (o != null) {
				if(o instanceof MovCmdQueueVO){ 
					request = (MovCmdQueueVO) o;
					//log.debug("== Inicia Llamado a inventarios/MovCmdConsumer ==");
					if (log.isDebugEnabled()) log.debug("== Inicia Llamado a inventarios/MovCmdConsumer == serial: "+request.getSerialized().getElementId());
					try {
						log.info("== Llamado sendStatusCmdToIBSParallel/MovCmdConsumer ==");
						log.info( request.toString() );
						movCmdBusinessBean.sendStatusCmdToIBSParallel(request); 
					} catch (Exception e) {
						log.error("== Error Llamado a inventarios/MovCmdConsumer ==", e);
					} finally{
						log.debug("== Finaliza Llamado a inventarios/MovCmdConsumer ==");
					}
				}
			}
		} catch (JMSException e) {
			log.error("== Error onMessage/MovCmdConsumer ==", e);
		} finally {
			log.debug("== Finaliza onMessage/MovCmdConsumer ==");
		}
	}
	
}
