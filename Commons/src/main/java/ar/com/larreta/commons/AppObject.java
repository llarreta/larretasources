package ar.com.larreta.commons;

import java.io.Serializable;

import ar.com.larreta.commons.logger.AppLogger;

public interface AppObject extends Serializable {
	public AppLogger getLog();

	public void setLog(AppLogger log);
	
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
