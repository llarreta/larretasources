package co.com.directv.sdii.common.locator;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.enumerations.JmsLocationsEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.JMSLocatorException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import co.com.directv.sdii.jms.common.util.DistributedQueueMessage;

/**
 * Servicio para la busqueda y creacion de MDB
 *  
 * Fecha de Creación: 14/02/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class JMSLocator {

	private static JMSLocator locator;
	private final static Logger log = UtilsBusiness.getLog4J(JMSLocator.class);
	
	/**
	 * 
	 * Metodo: retorna una instacia de MessageDrivenBeanLocator, patron singleton.
	 * @return ServiceLocator
	 * @throws ServiceLocatorException <tipo> <descripcion>
	 * @author cduarte
	 */
	public static JMSLocator getInstance() throws JMSLocatorException{
		try{
			if( locator == null ){
				locator = new JMSLocator();
			}
			return locator;
		}catch (Exception e) {
			throw new JMSLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_NOT_INSTANCE.getCode(),ErrorBusinessMessages.SERVICE_LOCATOR_NOT_INSTANCE.getMessage(),e);
		}
	}	
	
	/**
	 * Metodo: Permite obtener la referencia al web service de inventarios expuesto por IBS
	 * @return PtInventory - Referencia al WS para invocar las operaciones de inventarios en IBS
	 * @throws ServiceLocatorException 
	 */
	public DistributedQueueMessage queueMovementInventory() throws JMSLocatorException {
		
		log.info("=== Inicia getInventoryService/ServiceLocator ===");
		DistributedQueueMessage mdb = null;
		try{
			String jndiQueue = JmsLocationsEnum.INVENTORY_JMS.getJmsQueueName();
	    	String jndiQueueConnectionFactory = JmsLocationsEnum.INVENTORY_JMS.getJmsQueueConnectionFactoryName();
	    	  
	    	mdb = new DistributedQueueMessage();
	    	mdb.selectQueue(jndiQueue, jndiQueueConnectionFactory);
		}catch (Exception e) {
			throw new JMSLocatorException(e.getMessage(),e);
		}finally{
			log.info("=== Termina getInventoryService/ServiceLocator ===");
		}
		return mdb;
    }
	
	/**
	 * Metodo: Permite obtener la referencia al web service de kpis
	 * @return PtInventory - Referencia al WS para invocar las operaciones de inventarios en IBS
	 * @throws ServiceLocatorException 
	 */
	public DistributedQueueMessage queueCalculateKpi() throws JMSLocatorException {
		
		log.info("=== Inicia getInventoryService/ServiceLocator ===");
		DistributedQueueMessage mdb = null;
		try{
			String jndiQueue = JmsLocationsEnum.KPI_JMS.getJmsQueueName();
	    	String jndiQueueConnectionFactory = JmsLocationsEnum.KPI_JMS.getJmsQueueConnectionFactoryName();
	    	  
	    	mdb = new DistributedQueueMessage();
	    	mdb.selectQueue(jndiQueue, jndiQueueConnectionFactory);
		}catch (Exception e) {
			throw new JMSLocatorException(e.getMessage(),e);
		}finally{
			log.info("=== Termina getInventoryService/ServiceLocator ===");
		}
		return mdb;
    }
	
	/**
	 * Metodo: Permite obtener la referencia de la cola de mensajeria para el procesamiento en paralelo de asignador
	 * @return DistributedQueueMessage - Referencia de la cola de mensajeria de Asignador
	 * @throws JMSLocatorException
 	 * @author Aharker
	 */
	public DistributedQueueMessage getQueueAllocatorParallel() throws JMSLocatorException {
		
		log.info("=== Inicia getQueueAllocatorParallel/ServiceLocator ===");
		DistributedQueueMessage queue = null;
		try{
			String jndiQueue = JmsLocationsEnum.ALLOCATOR_JMS.getJmsQueueName();
	    	String jndiQueueConnectionFactory = JmsLocationsEnum.ALLOCATOR_JMS.getJmsQueueConnectionFactoryName();
	    	  
	    	queue = new DistributedQueueMessage();
	    	queue.selectQueue(jndiQueue, jndiQueueConnectionFactory);
		}catch (Exception e) {
			throw new JMSLocatorException(e.getMessage(),e);
		}finally{
			log.info("=== Termina getQueueAllocatorParallel/ServiceLocator ===");
		}
		return queue;
    }

	/**
	 * Metodo: Permite obtener la referencia de la cola de mensajeria para el procesamiento en paralelo de core
	 * @return DistributedQueueMessage - Referencia de la cola de mensajeria de core
	 * @throws JMSLocatorException
 	 * @author Aharker
	 */
	public DistributedQueueMessage getQueueCoreParallel() throws JMSLocatorException {
		
		log.info("=== Inicia getQueueCoreParallel/ServiceLocator ===");
		DistributedQueueMessage queue = null;
		try{
			String jndiQueue = JmsLocationsEnum.CORE_JMS.getJmsQueueName();
	    	String jndiQueueConnectionFactory = JmsLocationsEnum.CORE_JMS.getJmsQueueConnectionFactoryName();
	    	  
	    	queue = new DistributedQueueMessage();
	    	queue.selectQueue(jndiQueue, jndiQueueConnectionFactory);
		}catch (Exception e) {
			throw new JMSLocatorException(e.getMessage(),e);
		}finally{
			log.info("=== Termina getQueueCoreParallel/ServiceLocator ===");
		}
		return queue;
    }

	/**
	 * Req-0098 - Paralelismo de Inventarios
	 * Metodo: Permite obtener la referencia de la cola de mensajeria para el procesamiento en paralelo de movimientos de inventario
	 * @return DistributedQueueMessage - Referencia de la cola de mensajeria de movimientos de inventario
	 * @throws JMSLocatorException
 	 * @author ialessan
	 */
	public DistributedQueueMessage getQueueMovCmdQueue() throws JMSLocatorException {
		
		log.info("=== Inicia getQueueCoreParallel/ServiceLocator ===");
		DistributedQueueMessage queue = null;
		try{
				String jndiQueue = JmsLocationsEnum.MovCmdQueue_JMS.getJmsQueueName();
				String jndiQueueConnectionFactory = JmsLocationsEnum.MovCmdQueue_JMS.getJmsQueueConnectionFactoryName();
	    	  
				queue = new DistributedQueueMessage();
				queue.selectQueue(jndiQueue, jndiQueueConnectionFactory);
		}catch (Exception e) {
			throw new JMSLocatorException(e.getMessage(),e);
		}finally{
			log.info("=== Termina getQueueMovCmdQueue/ServiceLocator ===");
		}
		return queue;
    }	
	
	/**
	 * Metodo: Permite obtener la referencia de la cola de mensajeria para el procesamiento de dispatch de techninician.
	 * @return DistributedQueueMessage - Referencia de la cola de mensajeria de DispatchTechnician
	 * @throws JMSLocatorException
 	 * @author fsanabri
	 */
	public DistributedQueueMessage getQueueDispatchTechnician() throws JMSLocatorException {
		
		log.info("=== Inicia getQueueDispatchTechnician/ServiceLocator ===");
		DistributedQueueMessage queue = null;
		try{
			String jndiQueue = JmsLocationsEnum.DISPATCH_TECHNICIAN_JMS.getJmsQueueName();
	    	String jndiQueueConnectionFactory = JmsLocationsEnum.DISPATCH_TECHNICIAN_JMS.getJmsQueueConnectionFactoryName();
	    	  
	    	queue = new DistributedQueueMessage();
	    	queue.selectQueue(jndiQueue, jndiQueueConnectionFactory);
		}catch (Exception e) {
			throw new JMSLocatorException(e.getMessage(),e);
		}finally{
			log.info("=== Termina getQueueDispatchTechnician/ServiceLocator ===");
		}
		return queue;
    }
	
	
	public DistributedQueueMessage getQueueFileProcessor() throws JMSLocatorException {
		
		log.info("=== Inicia getQueueFileProcessor/ServiceLocator ===");
		DistributedQueueMessage queue = null;
		try{
			String jndiQueue = JmsLocationsEnum.FILE_PROCESSOR_JMS.getJmsQueueName();
	    	String jndiQueueConnectionFactory = JmsLocationsEnum.FILE_PROCESSOR_JMS.getJmsQueueConnectionFactoryName();
	    	  
	    	queue = new DistributedQueueMessage();
	    	queue.selectQueue(jndiQueue, jndiQueueConnectionFactory);
		}catch (Exception e) {
			throw new JMSLocatorException(e.getMessage(),e);
		}finally{
			log.info("=== Termina getQueueFileProcessor/ServiceLocator ===");
		}
		return queue;
    }
	
}
