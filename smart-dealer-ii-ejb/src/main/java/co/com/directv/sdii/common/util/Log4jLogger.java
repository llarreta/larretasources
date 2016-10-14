package co.com.directv.sdii.common.util;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import co.com.directv.sdii.exceptions.Log4jLoggerException;


/**
 * 
 * Clase que implementa las operaciones basicas de lectura
 * de archivos de configuracion de log4j y de traza de mensajes. 
 * 
 * Fecha de Creación: Mar 15, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see org.apache.log4j.Logger
 */
public class Log4jLogger {
	
	// private PropertiesReader prConf = null;	
	private Logger logger = null;
	private StringBuffer str;
	
	public Log4jLogger(Class pClass) throws Log4jLoggerException{ 		
		try{	 			
			str = new StringBuffer(PropertiesReader.getInstance().getAppKey(Constantes.LABEL_RUTA_LOG4J));			
			logger = Logger.getLogger(pClass);			
			DOMConfigurator.configure(str.toString());	
			
		}catch (Exception ex){	
			logger.fatal("Error obteniendo la configuracion de Log4j");
			throw new Log4jLoggerException("Error obteniendo la configuracion de Log4j",ex);				
		}		
	}

	
	public void info(Object message) {
		logger.info(message);
	}
	
	public void debug(Object message) {
		logger.debug(message);
	}
	
	public void fatal(Object message) {		
		logger.fatal(message);
	}
	
	public void error(Object message) {
		logger.error(message);
	}
	
	public void warn(Object message) {
		logger.warn(message);
	}
	
	public void info(Object message,Throwable ex) {
		logger.info(message,ex);
	}
	
	public void debug(Object message,Throwable ex) {
		logger.debug(message,ex);
	}
	
	public void fatal(Object message,Throwable ex) {		
		logger.fatal(message,ex);
	}
	
	public void error(Object message,Throwable ex) {
		logger.error(message,ex);
	}
	
	public void warn(Object message,Throwable ex) {
		logger.warn(message,ex);
	}
	

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Logger pLogger) {
		logger = pLogger;
	}
}
