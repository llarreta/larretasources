package ar.com.larreta.rest.controllers;

import java.io.Serializable;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ar.com.larreta.annotations.PerformanceMonitor;
import ar.com.larreta.rest.business.Business;
import ar.com.larreta.rest.exceptions.NotImplementedException;
import ar.com.larreta.rest.exceptions.NotPermitedExeption;
import ar.com.larreta.rest.exceptions.RestException;
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

	public static final String ALL_URLS = "/*";
	
	@Autowired
	protected ServletContext context;

	protected Business createBusiness;
	protected Business updateBusiness;
	protected Business deleteBusiness;
	protected Business loadBusiness;
	
	public abstract void setCreateBusiness(Business createBusiness);
	public abstract void setUpdateBusiness(Business updateBusiness);
	public abstract void setDeleteBusiness(Business deleteBusiness);
	public abstract void setLoadBusiness(Business loadBusiness);
	
	@RequestMapping(value = StringUtils.EMPTY, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response sourcePost(@RequestBody Request request) throws RestException{
		return executePost(request);
	}
	
	@RequestMapping(value = ALL_URLS, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response rootPost(@RequestBody Request request) throws RestException{
		return executePost(request);
	}
	
	@PerformanceMonitor
	@RequestMapping(value = CREATE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<TargetedBody> createPost(@Valid @RequestBody Request<UpdateBodyRequest> request, Errors errors) throws RestException{
		return executeBusiness(request, createBusiness);	
	}

	@PerformanceMonitor
	@RequestMapping(value = UPDATE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<TargetedBody> updatePost(@Valid @RequestBody Request<UpdateBodyRequest> request, Errors errors) throws RestException{
		return executeBusiness(request, updateBusiness);	
	}
	
	private Response<TargetedBody> executeBusiness(Request<UpdateBodyRequest> request, Business business) {
		Response<TargetedBody> response = new Response<TargetedBody>();
		Long target = (Long) business.execute(request.getBody());
		TargetedBody responseBody = new TargetedBody();
		responseBody.setTarget(target);
		response.setBody(responseBody);
		return response;
	}

	@PerformanceMonitor
	@RequestMapping(value = DELETE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<TargetedBody> deletePost(@Valid @RequestBody Request<TargetedBody> request, Errors errors) throws RestException{
		Response<TargetedBody> response = new Response<TargetedBody>();
		
		deleteBusiness.execute(request.getBody().getTarget());
		
		TargetedBody responseBody = new TargetedBody();
		responseBody.setTarget(request.getBody().getTarget());
		response.setBody(responseBody);
		return response;

	}

	@PerformanceMonitor
	@RequestMapping(value = LOAD, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<JSONableCollectionBody<Serializable>> loadPost(@Valid @RequestBody Request<LoadBodyRequest> request, Errors errors) throws RestException{
		Response<JSONableCollectionBody<Serializable>> response = new Response<JSONableCollectionBody<Serializable>>();
		JSONableCollectionBody<Serializable> loadResponse = (JSONableCollectionBody<Serializable>) loadBusiness.execute(request.getBody());
		response.setBody(loadResponse);
		return response;
	}

	public Response executePost(Request request) throws RestException{
		throw new NotImplementedException();
	}
	
	@RequestMapping(value = StringUtils.EMPTY, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response sourceGet() throws RestException{
		return executeGet();
	}
	
	@RequestMapping(value = ALL_URLS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response rootGet() throws RestException{
		return executeGet();
	}
	
	public Response executeGet() throws RestException{
		throw new NotPermitedExeption();
	}
	
	@RequestMapping(value = StringUtils.EMPTY, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response sourcePut() throws RestException{
		return executePut();
	}
	
	@RequestMapping(value = ALL_URLS, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response rootPut() throws RestException{
		return executePut();
	}
	
	public Response executePut() throws RestException{
		throw new NotPermitedExeption();
	}
	
	@RequestMapping(value = StringUtils.EMPTY, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response sourceDelete() throws RestException{
		return executeRequestMethodDelete();
	}
	
	@RequestMapping(value = ALL_URLS, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response rootDelete() throws RestException{
		return executeRequestMethodDelete();
	}
	
	public Response executeRequestMethodDelete() throws RestException{
		throw new NotPermitedExeption();
	}
	
}
