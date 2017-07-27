package ar.com.larreta.stepper.controllers;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ar.com.larreta.annotations.PerformanceMonitor;
import ar.com.larreta.stepper.Step;
import ar.com.larreta.stepper.StepElement;
import ar.com.larreta.stepper.messages.Body;
import ar.com.larreta.stepper.messages.JSONable;
import ar.com.larreta.stepper.messages.LoadBody;
import ar.com.larreta.stepper.messages.Request;
import ar.com.larreta.stepper.messages.Response;
import ar.com.larreta.stepper.messages.TargetedBody;

public abstract class ParentController<UpdateBodyRequest extends Body, LoadBodyRequest extends Body> extends StepElement {

	public static final String LOAD = "/load";

	public static final String DELETE = "/delete";

	public static final String UPDATE = "/update";

	public static final String CREATE = "/create";

	@Autowired
	protected ServletContext servletContext;
	
	protected Step createBusiness;
	protected Step updateBusiness;
	protected Step deleteBusiness;
	protected Step loadBusiness;
	
	public abstract void setCreateBusiness(Step createBusiness);
	public abstract void setUpdateBusiness(Step updateBusiness);
	public abstract void setDeleteBusiness(Step deleteBusiness);
	public abstract void setLoadBusiness(Step loadBusiness);
	
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
	
	private Response<TargetedBody> executeBusiness(Request<UpdateBodyRequest> request, Step business) throws Exception{
		Response<TargetedBody> response = applicationContext.getBean(Response.class);
		Long target = (Long) business.execute(request.getBody(), null, null);
		TargetedBody responseBody = new TargetedBody();
		responseBody.setTarget(target);
		response.setBody(responseBody);
		return response;
	}

	@PerformanceMonitor
	@RequestMapping(value = DELETE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<TargetedBody> deletePost(@Valid @RequestBody Request<TargetedBody> request, Errors errors) throws Exception{
		Response<TargetedBody> response = applicationContext.getBean(Response.class);
		TargetedBody responseBody = new TargetedBody();
		responseBody.setTarget(request.getBody().getTarget());
		response.setBody(responseBody);
		
		deleteBusiness.execute(request.getBody().getTarget(), null, null);
		
		return response;
	}

	@PerformanceMonitor
	@RequestMapping(value = LOAD, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<LoadBody<JSONable>> loadPost(@Valid @RequestBody Request<LoadBodyRequest> request, Errors errors) throws Exception{
		Response<LoadBody<JSONable>> response = applicationContext.getBean(Response.class);;
		LoadBody body = (LoadBody) loadBusiness.execute(request.getBody(), null, null);
		response.setBody(body);
		return response;
	}
}
