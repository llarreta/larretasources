package ar.com.larreta.rest.exceptions.handlers;

import java.util.Iterator;
import java.util.Locale;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.rest.exceptions.NotPermitedExeption;
import ar.com.larreta.rest.exceptions.RestException;
import ar.com.larreta.rest.messages.Response;
import ar.com.larreta.rest.messages.status.BAD;
import ar.com.larreta.rest.messages.status.NOK;
import ar.com.larreta.rest.messages.status.NOP;
import ar.com.larreta.rest.messages.status.NOT;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private static @Log Logger LOG;

	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return processException(ex);
	}

	@ExceptionHandler(value = { ConstraintViolationException.class})
	public ResponseEntity<Object> handleBadInputException(Throwable ex, WebRequest request) {
		return processException(ex);
	}

	private ResponseEntity<Object> processException(Throwable ex) {
		LOG.error("handleRestException", ex);

		Response response = context.getBean(Response.class);
		response.setState(context.getBean(BAD.class));
		
		if (ex instanceof ConstraintViolationException) {
			ConstraintViolationException violationException = (ConstraintViolationException) ex;
			Iterator<ConstraintViolation<?>> it = violationException.getConstraintViolations().iterator();
			Long index = new Long(0);
			while (it.hasNext()) {
				index++;
				ConstraintViolation constraintViolation = (ConstraintViolation) it.next();
				response.getState().addDetail(index, messageSource.getMessage(constraintViolation.getMessage(), null, constraintViolation.getMessage(), Locale.ROOT));
			}
		}
		
		return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = { RestException.class })
	public ResponseEntity<Object> handleRestException(RestException ex, WebRequest request) {
		LOG.error("handleRestException", ex);

		Response response = context.getBean(Response.class);
		response.setState(context.getBean(NOK.class));
		return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = { NotPermitedExeption.class })
	public ResponseEntity<Object> handleNotPermitedException(NotPermitedExeption ex, WebRequest request) {
		LOG.error("handleRestException", ex);

		Response response = context.getBean(Response.class);
		response.setState(context.getBean(NOP.class));
		return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		LOG.error("handleNoHandlerFoundException", ex);
		
		Response response = context.getBean(Response.class);
		response.setState(context.getBean(NOT.class));
		return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { Throwable.class } )
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

		LOG.error("handleConflict", ex);

		Response response = context.getBean(Response.class);
		response.setState(context.getBean(NOK.class));
		response.getState().setDescription("Unexpected exception");
		return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
	}

}
