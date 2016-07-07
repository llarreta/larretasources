package ar.com.larreta.commons.threads;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.springframework.stereotype.Component;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.initializer.LockAppInitializer;
import ar.com.larreta.commons.services.StandardService;
import ar.com.larreta.commons.utils.DateUtils;

/**
 * Thread de persistencia asincronica
 * permite encolar entidades y las va guardando secuencialmente 
 *
 */

@Component
public class SaveThread extends Thread {
	
	private static final Long DELTA_INTERVAL = DateUtils.ONE_SECOND;
	private static final Long MAX_INTERVAL = DateUtils.ONE_MINUTE;
	
	private static SaveThread INSTANCE;
	
	private StandardService service;

	private static Collection<Entity> entities = new ArrayList<Entity>();

	public SaveThread(){
		super(DELTA_INTERVAL, null);
		INSTANCE = this;
		getLog().info("Se creo SaveThread (" + this + ")");
	}
	
	private SaveThread(StandardService service, Date executeTime, Long interval, Long executeCount){
		super(executeTime, interval, executeCount);
		this.service = service;
		INSTANCE = this;
	}

	
	public StandardService getService() {
		if ((service==null) && (AppManager.getInstance()!=null)){
			service = (StandardService) AppManager.getInstance().getStandardService();
		}
		return service;
	}

	
	public static void addEntity(Entity entity){
		synchronized (SaveThread.class){
			if (entity!=null){
				entities.add(entity);
				INSTANCE.shortenInterval();
			}
		}
	}
	

	public void shortenInterval() {
		if (interval>DateUtils.ONE_SECOND){
			interval-=DELTA_INTERVAL;
		}
	}
	
	public void enlargeInterval() {
		if (interval<MAX_INTERVAL){
			interval+=DELTA_INTERVAL;
		}
	}
	
	@Override
	protected void execute() {
		Collection entitiesPersisted = new ArrayList();
		synchronized (SaveThread.class) {
			Entity entity = null;
			Long key = null;
			try {
				if ((!entities.isEmpty()) && (getService()!=null) && (AppManager.getInstance().getAppConfig().getLockApp()!=null)){
					Iterator<Entity> it = entities.iterator();
					while (it.hasNext()) {
						if (getService()!=null){
							entity = it.next();
							entitiesPersisted.add(entity);
							getService().saveOrUpdate(entities);
						}
						// acotamos el intervalo o frecuencia de ejecucion de este thread
						shortenInterval();
					}
				} else {
					// estiramos el intervalo o frecuencia de ejecucion de este thread
					enlargeInterval();
				}
				//Grabamos la info de lockeo
				LockAppInitializer.writeLockApp(this);
			} catch (Exception e){
				getLog().error("Ocurrio un error al intentar almacenar (ID:" + entity.getId() + ") :" + entity.getClass().getName(), e);
				// Se borra para q no siga ocurriendo el error
				entities.remove(entity);
			} finally {
				entities.removeAll(entitiesPersisted);
			}
		}
	}

	@Override
	protected void restart(Long executeCount, Long interval, Date executeTime){
		INSTANCE = new SaveThread(getService(), getExecuteTime(), interval, null);
	}

}
