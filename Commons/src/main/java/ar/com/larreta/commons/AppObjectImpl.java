package ar.com.larreta.commons;

import javax.persistence.Transient;

import ar.com.larreta.commons.logger.AppLogger;
import ar.com.larreta.commons.statistics.StatisticsExecutorManager;

/**
 * Clase padre para todas las clases de nuestra aplicacion 
 */
public class AppObjectImpl implements AppObject {
	
	/**
	 * Logger que permite escribir trazas en nuestra aplicacion
	 */
	private transient AppLogger log;

	/**
	 * Manager de estadisticas
	 */
	//FIXME: Mejorar!!! ver la opcion de hacerlo con AOP
	private StatisticsExecutorManager statisticsManager = StatisticsExecutorManager.getInstance();
	
	/**
	 * Tipo utilizado cuando esta dentro de un wrapper
	 */
	private Class type;
	
	public AppObjectImpl(){
		type = getClass();
	}
	public AppObjectImpl(Class type){
		this.type = type;
	}

	@Transient
	public AppLogger getLog() {
		if (log==null){
			log = new AppLogger(type);
		}
		return log;
	}

	
	/**
	 * Arranca a contabilizar estadisticas
	 * @param mark
	 * @return
	 */
	public Long statisticsStart(String mark){
		return statisticsManager.start(mark); 
	}
	
	/**
	 * Finaliza de contabilizar estadisticas
	 * @param id
	 */
	public void statisticsStop(Long id){
		statisticsManager.stop(id);
	}
	@Override
	public void setLog(AppLogger log) {
		this.log = log;
	}
}
