package ar.com.larreta.commons.utils.iterators;

import java.net.URL;
import java.util.Enumeration;

import ar.com.larreta.commons.AppObjectImpl;

public class IterateResources extends AppObjectImpl implements Iterator{
	
	private Enumeration<URL> definitionFiles;
	private URLAction urlAction;
	private String name;
	
	public IterateResources(String name, URLAction urlAction){
		this.urlAction = urlAction;
		this.name = name;
	}
	
	
	@Override
	public void start() {
		try {
			definitionFiles = getClass().getClassLoader().getResources(name);
			while (definitionFiles.hasMoreElements()) {
				URL url = (URL) definitionFiles.nextElement();
				urlAction.process(url);
			}
		} catch (Exception e){
			getLog().error("Ocurrio un error iterando URLs", e);
		}
	}
	
	
	
	
}
