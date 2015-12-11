package ar.com.larreta.commons;

import java.io.Serializable;

import org.apache.log4j.Logger;

public interface AppObject extends Serializable {
	public Logger getLog();

	public void setLog(Logger log);
	
	/**
	 * Arranca a contabilizar estadisticas
	 * @param mark
	 * @return
	 */
	public Long statisticsStart(String mark); 
	
	/**
	 * Finaliza de contabilizar estadisticas
	 * @param id
	 */
	public void statisticsStop(Long id);
}
