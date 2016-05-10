package ar.com.larreta.commons.utils.iterators;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.AppObjectImpl;

public class IterableProperties extends Properties implements Iterator{
	
	private AppObject appObject = new AppObjectImpl(getClass());
	private PropertyAction action;
	
	public  IterableProperties(URL url, PropertyAction action){
		this.action = action;
		try {
			load(url.openStream());
		} catch (IOException e) {
			appObject.getLog().error("Ocurrio un error cargando propiedades", e);
		}
	}

	@Override
	public void start() {
		Enumeration keys = keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = getProperty(key);
			action.process(key, value);
		}
		
	}
	
	
	
	
}
