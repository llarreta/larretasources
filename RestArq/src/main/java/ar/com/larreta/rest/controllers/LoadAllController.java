package ar.com.larreta.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ar.com.larreta.annotations.PerformanceMonitor;
import ar.com.larreta.rest.business.Business;
import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.rest.messages.LoadBody;
import ar.com.larreta.rest.messages.Response;

public abstract class LoadAllController {
	public static final String LOAD = "/load";
	
	@Autowired
	protected ApplicationContext applicationContext;

	protected Business loadBusiness;
	
	public Business getLoadBusiness() {
		return loadBusiness;
	}

	public abstract void setLoadBusiness(Business loadBusiness);

	@PerformanceMonitor
	@RequestMapping(value = LOAD, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<LoadBody<JSONable>> loadPost() throws Exception{
		Response<LoadBody<JSONable>> response = applicationContext.getBean(Response.class);;
		LoadBody body = (LoadBody) loadBusiness.execute(null);
		response.setBody(body);
		return response;
	}
	
}
