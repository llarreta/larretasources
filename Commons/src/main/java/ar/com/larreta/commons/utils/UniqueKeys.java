package ar.com.larreta.commons.utils;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.services.StandardService;

public class UniqueKeys {
	
	private static UniqueKeys instance;
	
	private StandardService service = (StandardService) AppManager.getInstance().getStandardService();
	private Map<Class, Key> keys = new ConcurrentHashMap<Class, Key>();
	
	public static UniqueKeys getInstance(){
		if (instance==null){
			instance = new UniqueKeys();
		}
		return instance;
	}
	
	private UniqueKeys(){
		
	}
	
	public synchronized Long next(Class type) {
		Key key = keys.get(type);
		if (key==null){
			Long max = service.getMaxId(type);
			key = new Key();
			key.setType(type);
			key.setId(max);
		}
		
		key.setId(key.getId()+1);
		key.setLastExecute(new Date());
		
		keys.put(type, key);
		
		return key.getId();
	}

}
