package ar.com.larreta.tools;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements Serializable {

	private static SpringUtils INSTANCE;
	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private JSONParser parser;

	//FIXME: Revisar necesidad de este codigo de esta forma
	public SpringUtils(){
		synchronized (SpringUtils.class) {
			if (INSTANCE==null){
				INSTANCE = this;
			}	
		}
	}
	
	public ApplicationContext getAppContext() {
		return appContext;
	}

	public static SpringUtils getInstance(){
		return INSTANCE;
	}
	
	public static Object getBean(String key){
		return INSTANCE.getAppContext().getBean(key);
	}
	
	public static Object getBean(Class type){
		return INSTANCE.getAppContext().getBean(type);
	}
	
	public JSONParser getParser() {
		return parser;
	}
	
	public static JSONParser getJSONParser(){
		return INSTANCE.getParser();
	}
	
	
}
