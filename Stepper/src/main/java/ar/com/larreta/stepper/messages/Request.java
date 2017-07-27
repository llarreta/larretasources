package ar.com.larreta.stepper.messages;

import javax.validation.Valid;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.stepper.validators.annotations.NotNull;
import ar.com.larreta.tools.Const;

@Component(Request.COMPONENT_NAME) @Scope(Const.PROTOTYPE)
public class Request<T extends JSONable> extends Message {
	
	public static final String COMPONENT_NAME = "request";
	
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
	@NotNull(message="body.required")
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
