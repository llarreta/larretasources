package ar.com.larreta.screens.impl.saver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;

import ar.com.larreta.commons.AppObjectImpl;

@Controller(ScreenConstantIds.CONSTANT_IDS)
public class ScreenConstantIds extends AppObjectImpl{
	
	public static final String CONSTANT_IDS = "constantIds";

	public Map<String, Long> ids = new HashMap<String, Long>();
	
	private Long index=new Long(0);

	public Long getIdentifier(String key){
		synchronized (ScreenConstantIds.class) {
			Long id = ids.get(key);
			if (id==null){
				id = index++;
			}
			ids.put(key, id);
			return id;
		}
	}
	
	
}
