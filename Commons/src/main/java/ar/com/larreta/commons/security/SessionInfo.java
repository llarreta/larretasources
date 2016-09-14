package ar.com.larreta.commons.security;

import java.util.HashMap;
import java.util.Map;

import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.controllers.Paginator;

public class SessionInfo extends AppObjectImpl {

	private Map<Class, Paginator> paginators = new HashMap<>();
	
	public Paginator getPaginator(Class type){
		Paginator paginator = null;
		if (type != null) {
			paginator = paginators.get(type);
			if (paginator==null){
				paginator = new Paginator();
				paginators.put(type, paginator);
			}
		}
		return paginator;
	}
	
}
