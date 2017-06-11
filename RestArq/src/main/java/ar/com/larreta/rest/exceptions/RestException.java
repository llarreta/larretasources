package ar.com.larreta.rest.exceptions;

public class RestException extends Exception {

	public RestException() {
		super();
	}

	public RestException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestException(String message) {
		super(message);
	}

}
