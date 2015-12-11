package ar.com.larreta.commons.threads;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.utils.DateUtils;

public abstract class Thread extends java.lang.Thread implements AppObject{
	
	protected Long executeCount;
	protected Long interval;
	protected Date executeTime;
	
	private AppObject appObject = new AppObjectImpl(getClass());
	
	protected void setExecuteCount(Long executeCount) {
		this.executeCount = executeCount;
	}
	
	protected void setInterval(Long interval) {
		this.interval = interval;
	}

	protected void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}

	/**
	 * Crea un thread que se ejecuta cada cierta cantidad de tiempo a partir de un momento dado
	 * @param executeTime
	 * @param interval
	 * @param executeCount
	 */
	public Thread(Date executeTime, Long interval, Long executeCount){
		this.interval = interval;
		this.executeCount = executeCount;
		this.executeTime = executeTime;
		start();
	}
	
	/**
	 * Crea un thread para ejecutar cada un intervalo definido de tiempo una cierta cantidad de veces
	 * Si executeCount es nulo, entonces la cantidad de ejecuciones es infinita
	 * @param interval
	 */
	public Thread(Long interval, Long executeCount){
		this.interval = interval;
		this.executeCount = executeCount;
		this.executeTime = new Date();
		start();
	}

	/**
	 * Crea un thread para una unica ejecucion en el horario establecido
	 * @param executeTime
	 */
	public Thread(Date executeTime){
		this.executeTime = executeTime;
	}
	
	@Override
	public void run() {
		try {
			if (executeContinue()){
				super.run();
				execute();
				evaluateNewInstanceForInterval();
			}
		} catch (Exception e){
			getLog().error(AppException.getStackTrace(e));
		}
	}

	/**
	 * Determina si tiene que continuar con la ejecucion
	 * @return
	 */
	private Boolean executeContinue() {
		Date now = new Date();
		Boolean executeContinue = DateUtils.isMajorOrEqual(now, executeTime);
		if (!executeContinue){
			try {
				Long waitTime = executeTime.getTime() - now.getTime();
				sleep(waitTime);
			} catch (InterruptedException e) {
				getLog().error(AppException.getStackTrace(e));
			}
		}
		return Boolean.TRUE;
	}
	
	/**
	 * Logica a ejecutar cuando corra el thread
	 */
	protected abstract void execute();

	/**
	 * Evalua si es necesario relanzar el thread porque se coloco algun intervalo de ejecucion
	 */
	private void evaluateNewInstanceForInterval() {
		if ((interval!=null)&&(executeCount==null || executeCount > 1)){
			if (executeCount!=null){
				executeCount--;
			}
			restart(executeCount, interval, getExecuteTime());
		}
	}

	/**
	 * Retorna el nuevo horario de ejecucion a partir de la fecha de ejecucion actual
	 * @return
	 */
	protected Date getExecuteTime() {
		return DateUtils.add(executeTime, Calendar.MILLISECOND, interval.intValue());
	}
		
	/**
	 * Debe retornar una nueva instancia del thread
	 * @return
	 */
	protected abstract void restart(Long executeCount, Long interval, Date executeTime);

	public Logger getLog(){
		return appObject.getLog();
	}

	public void setLog(Logger log){
		appObject.setLog(log);
	}
	
	/**
	 * Arranca a contabilizar estadisticas
	 * @param mark
	 * @return
	 */
	public Long statisticsStart(String mark){
		return appObject.statisticsStart(mark);
	} 
	
	/**
	 * Finaliza de contabilizar estadisticas
	 * @param id
	 */
	public void statisticsStop(Long id){
		appObject.statisticsStop(id);
	}
	
}
