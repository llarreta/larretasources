package ar.com.larreta.rest.exceptions.handlers;

import java.util.Iterator;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.rest.exceptions.NotPermitedExeption;
import ar.com.larreta.rest.exceptions.RestException;
import ar.com.larreta.rest.messages.Response;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private static @Log Logger LOG;

	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ResponseEntity<Object> handleBadInputException(Throwable ex, WebRequest request) {
		LOG.error("handleRestException", ex);

		Response response = new Response();
		response.setBAD();
		
		if (ex instanceof ConstraintViolationException) {
			ConstraintViolationException violationException = (ConstraintViolationException) ex;
			Iterator<ConstraintViolation<?>> it = violationException.getConstraintViolations().iterator();
			Long index = new Long(0);
			while (it.hasNext()) {
				index++;
				ConstraintViolation constraintViolation = (ConstraintViolation) it.next();
				response.getState().addDetail(index, constraintViolation.getMessage());
			}
		}
		
		return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = { RestException.class })
	public ResponseEntity<Object> handleRestException(RestException ex, WebRequest request) {
		LOG.error("handleRestException", ex);

		Response response = new Response();
		response.setNOK();
		return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = { NotPermitedExeption.class })
	public ResponseEntity<Object> handleNotPermitedException(NotPermitedExeption ex, WebRequest request) {
		LOG.error("handleRestException", ex);

		Response response = new Response();
		response.setNOP();
		return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		LOG.error("handleNoHandlerFoundException", ex);
		
		Response response = new Response();
		response.setNOT();
		return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { Throwable.class } )
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

		LOG.error("handleConflict", ex);

		Response response = new Response();
		response.setNOK();
		response.getState().setDescription("Unexpected exception");
		return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
	}

}
