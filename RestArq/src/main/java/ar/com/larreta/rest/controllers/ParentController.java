package ar.com.larreta.rest.controllers;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ar.com.larreta.annotations.PerformanceMonitor;
import ar.com.larreta.persistence.aspects.Retry;
import ar.com.larreta.prototypes.JSONable;
import ar.com.larreta.rest.exceptions.BadInputException;
import ar.com.larreta.rest.exceptions.NotImplementedException;
import ar.com.larreta.rest.exceptions.NotPermitedExeption;
import ar.com.larreta.rest.exceptions.RestException;
import ar.com.larreta.rest.messages.DeleteRequest;
import ar.com.larreta.rest.messages.DeleteResponse;
import ar.com.larreta.rest.messages.LoadRequest;
import ar.com.larreta.rest.messages.LoadResponse;
import ar.com.larreta.rest.messages.Request;
import ar.com.larreta.rest.messages.Response;
import ar.com.larreta.rest.messages.UpdateRequest;
import ar.com.larreta.rest.messages.UpdateResponse;

public abstract class ParentController<T extends JSONable> {

	@Autowired
	protected ServletContext context;

	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response sourcePost(@RequestBody Request request) throws RestException{
		return executePost(request);
	}
	
	@RequestMapping(value = "/*", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response rootPost(@RequestBody Request request) throws RestException{
		return executePost(request);
	}
	
	@Retry(count=3)
	@PerformanceMonitor
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public UpdateResponse createPost(@Valid @RequestBody UpdateRequest<T> request, Errors errors) throws RestException{
		if (errors.hasErrors()){
			throw new BadInputException(); 
		}
		return executeCreate(request);
	}
	
	@PerformanceMonitor
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public UpdateResponse updatePost(@Valid @RequestBody UpdateRequest<T> request, Errors errors) throws RestException{
		return executeUpdate(request);
	}

	@PerformanceMonitor
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeleteResponse deletePost(@Valid @RequestBody DeleteRequest request, Errors errors) throws RestException{
		return executeDelete(request);
	}

	@PerformanceMonitor
	@RequestMapping(value = "/load", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public LoadResponse<T> loadPost(@Valid @RequestBody LoadRequest<T> request, Errors errors) throws RestException{
		return executeLoad(request);
	}

	public UpdateResponse executeCreate(UpdateRequest<T> request) throws RestException{
		throw new NotImplementedException();
	}
	
	public UpdateResponse executeUpdate(UpdateRequest<T> request) throws RestException{
		throw new NotImplementedException();
	}
	
	public DeleteResponse executeDelete(DeleteRequest request) throws RestException{
		throw new NotImplementedException();
	}
	
	public LoadResponse<T> executeLoad(LoadRequest<T> request) throws RestException{
		throw new NotImplementedException();
	}
	
	public Response executePost(Request request) throws RestException{
		throw new NotImplementedException();
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response sourceGet() throws RestException{
		return executeGet();
	}
	
	@RequestMapping(value = "/*", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response rootGet() throws RestException{
		return executeGet();
	}
	
	public Response executeGet() throws RestException{
		throw new NotPermitedExeption();
	}
	
	@RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response sourcePut() throws RestException{
		return executePut();
	}
	
	@RequestMapping(value = "/*", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response rootPut() throws RestException{
		return executePut();
	}
	
	public Response executePut() throws RestException{
		throw new NotPermitedExeption();
	}
	
	@RequestMapping(value = "", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response sourceDelete() throws RestException{
		return executeRequestMethodDelete();
	}
	
	@RequestMapping(value = "/*", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response rootDelete() throws RestException{
		return executeRequestMethodDelete();
	}
	
	public Response executeRequestMethodDelete() throws RestException{
		throw new NotPermitedExeption();
	}
	
}
