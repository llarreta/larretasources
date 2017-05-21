package ar.com.larreta.rest.controllers;

import java.io.Serializable;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ar.com.larreta.annotations.PerformanceMonitor;
import ar.com.larreta.rest.business.Business;
import ar.com.larreta.rest.messages.Body;
import ar.com.larreta.rest.messages.JSONableCollectionBody;
import ar.com.larreta.rest.messages.Request;
import ar.com.larreta.rest.messages.Response;
import ar.com.larreta.rest.messages.TargetedBody;

public abstract class ParentController<UpdateBodyRequest extends Body, LoadBodyRequest extends Body> {

	public static final String LOAD = "/load";

	public static final String DELETE = "/delete";

	public static final String UPDATE = "/update";

	public static final String CREATE = "/create";

	@Autowired
	protected ServletContext servletContext;
	
	@Autowired
	private ApplicationContext applicationContext;

	protected Business createBusiness;
	protected Business updateBusiness;
	protected Business deleteBusiness;
	protected Business loadBusiness;
	
	public abstract void setCreateBusiness(Business createBusiness);
	public abstract void setUpdateBusiness(Business updateBusiness);
	public abstract void setDeleteBusiness(Business deleteBusiness);
	public abstract void setLoadBusiness(Business loadBusiness);
	

	
	@PerformanceMonitor
	@RequestMapping(value = CREATE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<TargetedBody> createPost(@Valid @RequestBody Request<UpdateBodyRequest> request, Errors errors) throws Exception{
		return executeBusiness(request, createBusiness);	
	}

	@PerformanceMonitor
	@RequestMapping(value = UPDATE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<TargetedBody> updatePost(@Valid @RequestBody Request<UpdateBodyRequest> request, Errors errors) throws Exception{
		return executeBusiness(request, updateBusiness);	
	}
	
	private Response<TargetedBody> executeBusiness(Request<UpdateBodyRequest> request, Business business) throws Exception{
		Response<TargetedBody> response = applicationContext.getBean(Response.class);
		Long target = (Long) business.execute(request.getBody());
		TargetedBody responseBody = new TargetedBody();
		responseBody.setTarget(target);
		response.setBody(responseBody);
		return response;
	}

	@PerformanceMonitor
	@RequestMapping(value = DELETE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<TargetedBody> deletePost(@Valid @RequestBody Request<TargetedBody> request, Errors errors) throws Exception{
		Response<TargetedBody> response = applicationContext.getBean(Response.class);
		
		deleteBusiness.execute(request.getBody().getTarget());
		
		TargetedBody responseBody = new TargetedBody();
		responseBody.setTarget(request.getBody().getTarget());
		response.setBody(responseBody);
		return response;

	}

	@PerformanceMonitor
	@RequestMapping(value = LOAD, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<JSONableCollectionBody<Serializable>> loadPost(@Valid @RequestBody Request<LoadBodyRequest> request, Errors errors) throws Exception{
		Response<JSONableCollectionBody<Serializable>> response = applicationContext.getBean(Response.class);;
		JSONableCollectionBody<Serializable> loadResponse = (JSONableCollectionBody<Serializable>) loadBusiness.execute(request.getBody());
		response.setBody(loadResponse);
		return response;
	}
}
