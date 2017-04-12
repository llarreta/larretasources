package ar.com.larreta.rest.messages;

import javax.validation.Valid;

import ar.com.larreta.validators.annotations.NotNull;

public class Request<T extends JSONable> extends Message {
	
	/**
	 * Idioma en el que se retornaran los mensajes 
	 */
	private String language;
	
	/**
	 * Token para realizar validaciones de seguridad 
	 */
	private String token;
	
	/**
	 * Cuerpo del mensaje que se esta enviando
	 */
	@NotNull(message="body is required")
	@Valid
	private T body;

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public String getToken() {
		return token;
	}
 
	public void setToken(String token) {
		this.token = token;
	}
}
