package co.com.directv.sdii.mdb.business;

import java.util.Date;
import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.WorkManagerConfigEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.config.SchedulerTaskBussinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.SchedulerTask;

import commonj.work.WorkManager;

/**
 * Message-Driven Bean implementation class for: ColaDeJobs
 *
 */
public class TimerConsumer implements MessageListener {

    @EJB(name="SchedulerTaskBussinessRemote",beanInterface=SchedulerTaskBussinessLocal.class)
    private SchedulerTaskBussinessLocal schedulerTaskBussinessLocal;
	
    private final static Logger log = UtilsBusiness.getLog4J(TimerConsumer.class);
    
	/**
     * @see MessageListener#onMessage(Message)
     */
	@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
    public void onMessage(Message message) {
    	log.info("== Inicia onMessage/TimerConsumer ==");
    	try {
    		if(message instanceof ObjectMessage){
    			printLogMessage((ObjectMessage) message); // ****************************  comienza aca  
	    		doWorkManager(schedulerTaskBussinessLocal.getSchedulerTaskByStateAndId(CodesBusinessEntityEnum.CODE_SCHEDULER_TASK_STATUS_ACTIVE.getCodeEntity(), null));
    		}
		} catch (NamingException e) {
			log.error("== Error onMessage/TimerConsumer ==", e);
		}catch (BusinessException e) {
			log.error("== Error onMessage/TimerConsumer ==", e);
		} catch (JMSException e) {
			log.error("== Error onMessage/TimerConsumer ==", e);
		} catch (PropertiesException e) {
			log.error("== Error onMessage/TimerConsumer ==", e);
		}finally {
			log.info("== Finaliza onMessage/TimerConsumer ==");
		}
    }
	
	private void printLogMessage(ObjectMessage textMessage) throws JMSException{
        Object[] messageObject = (Object[])textMessage.getObject();//posicion 0 la fecha, posicion 1 la ip
        Date date = (Date) messageObject[0];
        String ip = (String) messageObject[1];
		log.info("Mensaje Timer fecha " + date + " desde la ip" + ip);
	}
	
	private void doWorkManager(List<SchedulerTask> schedulerTask) throws NamingException, PropertiesException, BusinessException{
		Context ctx= new InitialContext();
		String jndiAllocator = WorkManagerConfigEnum.JNDI_ALLOCATOR.getCodeEntity();
		WorkManager workManager = (WorkManager)ctx.lookup(jndiAllocator);
		schedulerTaskBussinessLocal.doWorkManager(workManager, schedulerTask);
	}

}
