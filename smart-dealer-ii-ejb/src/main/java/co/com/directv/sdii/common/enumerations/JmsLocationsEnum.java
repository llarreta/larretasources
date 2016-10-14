package co.com.directv.sdii.common.enumerations;

import co.com.directv.sdii.common.util.PropertiesReader;
import co.com.directv.sdii.exceptions.PropertiesException;

/**
 * 
 * Clase de tipo Enum, encargada de realizar el mapping
 * de las propiedades necesarias para invocar los jms
 * 
 * Fecha de Creación: 18/02/2012
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see properties.JmsLocations.properties
 */
public enum JmsLocationsEnum {
	
	INVENTORY_JMS("inventory_jndi_queue","inventory_jndi_queue_connection_factory"),
	KPI_JMS("kpi_jndi_queue","kpi_jndi_queue_connection_factory"),
	TIME_MANAGER_JMS("time_manager_jndi_queue","time_manager_jndi_queue_connection_factory"),
	ALLOCATOR_JMS("allocator_jndi_queue","allocator_jndi_queue_connection_factory"),
	CORE_JMS("core_jndi_queue","core_jndi_queue_connection_factory"),
	MovCmdQueue_JMS("movCmd_jndi_queue","movCmd_jndi_queue_connection_factory"),
	DISPATCH_TECHNICIAN_JMS("dispatch_techninician_jndi_queue","dispatch_techninician_jndi_queue_connection_factory"),
	FILE_PROCESSOR_JMS("file_processor_jndi_queue","file_processor_jndi_queue_connection_factory"),
	;
	
	private String jmsQueueName;
	private String jmsQueueConnectionFactoryName;
	
	/**
	 * 
	 * Constructor: <Descripcion>
	 * @param jmsQueueName
	 * @param jmsQueueConnectionFactoryName
	 * @author
	 */
	JmsLocationsEnum(String jmsQueueName, String jmsQueueConnectionFactoryName){
		this.jmsQueueName = jmsQueueName;
		this.jmsQueueConnectionFactoryName = jmsQueueConnectionFactoryName;
	}
	
	/**
	 * Metodo: Obtiene el jms queue name
	 * @return cadena con el nombre del queue name
	 * @author
	 */
	public String getJmsQueueName() throws PropertiesException{

		String queueName = PropertiesReader.getInstance().getAppKey(this.jmsQueueName);
		return queueName != null ? queueName : this.jmsQueueName;

	}
	
	/**
	 * Metodo: Obtiene el jms queue factory name
	 * @return cadena con el nombre del queue factory name
	 * @author
	 */
	public String getJmsQueueConnectionFactoryName() throws PropertiesException{

		String queueFactoryName = PropertiesReader.getInstance().getAppKey(this.jmsQueueConnectionFactoryName);
		return queueFactoryName != null ? queueFactoryName : this.jmsQueueConnectionFactoryName;

	}

}
