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
import co.com.directv.sdii.ejb.business.stock.FileProcessorQueueBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.UploadFileVO;

public class FileProcessorConsumer implements MessageListener {
	
	@EJB
	private FileProcessorQueueBusinessLocal fileProcessorQueueBusinessBean;
	
	private final static Logger log = UtilsBusiness.getLog4J(co.com.directv.sdii.mdb.business.FileProcessorConsumer.class);
	
	public FileProcessorConsumer(){
		
	}
	
	@Override
	@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
	public void onMessage(Message msg) {
		
		log.debug("== Inicia onMessage/FileProcessorConsumer ==");
		ObjectMessage objMsg = (ObjectMessage) msg;
		UploadFileVO request = null;
		
		try {
			Object o = objMsg.getObject();
			if (o != null) {
				if(o instanceof UploadFileVO){
					request = (UploadFileVO) o;
					log.debug("== Inicia llamado a procesamiento de archivos/FileProcessorConsumer ==");
					try {
						fileProcessorQueueBusinessBean.processFile(request);
					} catch (BusinessException e) {
						log.error("== Error procesamiento de archivos /FileProcessorConsumer ==", e);
					} finally{
						log.debug("== Finaliza Llamado procesamiento de archivos/FileProcessorConsumer ==");
					}
				}
			}
		} catch (JMSException e) {
			log.error("== Error onMessage/FileProcessorConsumer ==", e);
		} finally {
			log.debug("== Finaliza onMessage/FileProcessorConsumer ==");
		}
		
	}
	
}
