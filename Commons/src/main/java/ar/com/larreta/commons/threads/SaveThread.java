package ar.com.larreta.commons.threads;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
	private static final String STANDARD_SERVICE = "standardService";
	
	private static SaveThread INSTANCE;
	
	private StandardService service;

	private static Map<Long, Entity> entities = new ConcurrentHashMap<Long, Entity>();

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
			service = (StandardService) AppManager.getInstance().getBean(STANDARD_SERVICE);
		}
		return service;
	}

	
	public static void addEntity(Entity entity){
		if (entity!=null){
			entities.put(entity.getId(), entity);
			INSTANCE.shortenInterval();
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
	//FIXME: Actualizar el lockApp
	protected void execute() {
		synchronized (SaveThread.class) {
			Entity entity = null;
			Long key = null;
			try {
				if (!entities.isEmpty()){
					
					Set<Long> keys = entities.keySet();
					if (keys!=null){
						Iterator<Long> itKeys = keys.iterator();
						while (itKeys.hasNext()) {
							key = (Long) itKeys.next();
							entity = entities.get(key);
							if (getService()!=null){
								getService().save(entity);
								entities.remove(key);
							}
							shortenInterval();
						}
					}
				} else {
					//Grabamos la info de lockeo
					LockAppInitializer.writeLockApp(this);
					// estiramos el intervalo o frecuencia de ejecucion de este thread
					enlargeInterval();
				}
			} catch (Exception e){
				getLog().error(AppException.getStackTrace(e));
				entities.remove(entity.getId());
			}
		}
	}

	@Override
	protected void restart(Long executeCount, Long interval, Date executeTime){
		INSTANCE = new SaveThread(getService(), getExecuteTime(), interval, null);
	}

}
