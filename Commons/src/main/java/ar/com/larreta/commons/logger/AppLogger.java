package ar.com.larreta.commons.logger;

import java.io.Serializable;
import java.util.Collection;

import org.apache.log4j.Logger;

/**
 * Abstraccion para administrar varios loggers
 *
 */
public class AppLogger implements Serializable {
	
	private static final String HQL = "hqlLogger";
	private static final String MAIL = "mailLogger";
	private static final String BD_INITIALIZER = "bdInitializeLogger";
	private static final String ERROR = "errorLogger";
	private static final String COPY = "copyLogger";
	private static final String SAVE_THREAD = "saveThreadLogger";
	private static final String SCREEN = "screenLogger";
	
	
	private Logger commons;
	private Logger hqlLogger = Logger.getLogger(HQL);;
	private Logger mailLogger = Logger.getLogger(MAIL);
	private Logger bdInitializerLogger = Logger.getLogger(BD_INITIALIZER);
	private Logger errorLogger = Logger.getLogger(ERROR);
	private Logger copyLogger = Logger.getLogger(COPY);
	private Logger saveThreadLogger = Logger.getLogger(SAVE_THREAD);
	private Logger screenLogger = Logger.getLogger(SCREEN);

	public AppLogger(Class type) {
		commons = Logger.getLogger(type);
	}
	
	public void debug(Object message) {
		commons.debug(message);
	}

	public void error(Object message) {
		commons.error(message);
		errorLogger.error(message);
	}

	public void error(Object message, Throwable t) {
		commons.error(message, t);
		errorLogger.error(message, t);
	}

	public void info(Object message) {
		commons.info(message);
	}
	
	public void hql(String hql, Collection values) {
		String message = hql + "(" + values + ")";
		debug(message);
		hqlLogger.info(message);
	}
	
	public void bdInitializer(String sql) {
		debug(sql);
		bdInitializerLogger.info(sql);
	}

	public void mail(Object message) {
		debug(message);
		mailLogger.info(message);
	}
	
	public void copy(Object message) {
		debug(message);
		copyLogger.info(message);
	}
	
	public void save(Object message) {
		debug(message);
		saveThreadLogger.info(message);
	}

	public void saveError(Object message, Throwable t) {
		error(message, t);
		saveThreadLogger.error(message, t);
	}

	public void screen(Object message) {
		debug(message);
		screenLogger.info(message);
	}

	public void screenError(Object message, Throwable t) {
		error(message, t);
		screenLogger.error(message, t);
	}
}
