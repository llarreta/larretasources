/**
 * Creado 17/06/2011 09:55:40
 */
package co.com.directv.sdii.assign.schedule.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.exceptions.BusinessException;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 17/06/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class ThreadManager {

	private static Log log = LogFactory.getLog(ThreadManager.class);
	
	/**
	 * Metodo: Ejecuta un grupo de hilos 
	 * @param runnables Conjunto de hilos a ser ejecutados
	 * @author
	 * @throws BusinessException  en caso de error con la ejecución de algún hilo
	 */
	public void executeThreadsAndWait(List<Runnable> runnables) throws BusinessException{
		if(log.isDebugEnabled()){
			log.debug("Se inicia la ejecución de un grupo de " + runnables.size() + " hilos y se esperará a que todos terminen");
		}
		
		List<Thread> ts = new ArrayList<Thread>();
		for (Runnable runnable : runnables) {
			Thread t = new Thread(runnable);
			ts.add(t);
		}

		for (Thread thread : ts) {
			thread.start();
		}

		for (Thread thread : ts) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error("Al tratar de ejecutar uno de los hilos del grupo: " + e.getMessage(),e);
				throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode(), "Error al ejecutar un hilo consulte el log para mas información" + e.getMessage());
			}
		}
		
		if(log.isDebugEnabled()){
			log.debug("Se Finaliza la ejecución de un grupo de " + runnables.size() + " hilos");
		}
	}

}
