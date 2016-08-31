package co.com.directv.sdii.jms.common.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DistributedQueueMessage {

	private QueueConnection cnn;
	private QueueSender sender;
	private QueueSession session;
	private InitialContext ctx;
	private Queue queue;
	
	private String jndiQueue;
	private String jndiQueueConnectionFactory;
	
	private boolean transacted;
	private int acknowledgeMode = QueueSession.CLIENT_ACKNOWLEDGE;
	private int deliveryMode = DeliveryMode.NON_PERSISTENT;
	
	public DistributedQueueMessage() {
		super();
	}
	
	public void selectQueue(String jndiQueue,String jndiQueueConnectionFactory) {
		this.jndiQueue=jndiQueue;
		this.jndiQueueConnectionFactory=jndiQueueConnectionFactory;
	}
	
	public void sendMessage(Serializable request) throws NamingException, JMSException {
		open();
		send(request);
		close();
	}

	public void sendTextMessage(String text, Map<String,String> properties) throws NamingException, JMSException {
		open();
		sendText(text, properties);
		close();
	}
	
	public void sendMessage(Serializable request,long timeToLive) throws NamingException, JMSException {
		open();
		send(request,timeToLive);
		close();
	}

	private void open() throws NamingException, JMSException {
		ctx = new InitialContext();
		queue = (Queue) ctx.lookup(jndiQueue);
		QueueConnectionFactory factory;
		factory = (QueueConnectionFactory) ctx
				.lookup(jndiQueueConnectionFactory);
		cnn = factory.createQueueConnection();
		session = cnn.createQueueSession(transacted, acknowledgeMode);
	}
	
//	private void initialContext() throws NamingException{
//
//		Properties props = new Properties( );
//		props.put( Context.PROVIDER_URL,url);
//		props.put( Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory" );
//		props.put(Context.SECURITY_PRINCIPAL, user);  
//		props.put(Context.SECURITY_CREDENTIALS, password);
//		ctx = new InitialContext(props);
//	}

	private void send(Serializable request) throws JMSException {		
		Message msg = session.createObjectMessage(request);
		sender = session.createSender(queue);
		sender.setDeliveryMode(deliveryMode);
		sender.send(msg);		
	}

	private void sendText(String text, Map<String,String> properties) throws JMSException {		
		Message msg = session.createTextMessage(text);
		
		Iterator<Entry<String,String>> it = properties.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, String> property = it.next();
			msg.setStringProperty(property.getKey(), property.getValue());
		}
		
		sender = session.createSender(queue);
		sender.setDeliveryMode(deliveryMode);
		sender.send(msg);		
	}
	
	private void send(Serializable request,long timeToLive) throws JMSException {
		Message msg = session.createObjectMessage(request);
		sender = session.createSender(queue);
		sender.setDeliveryMode(deliveryMode);
		sender.setTimeToLive(timeToLive);
		sender.send(msg);
	}

	private void close() throws JMSException {
		sender.close();
		session.close();
		cnn.close();
	}
	
}