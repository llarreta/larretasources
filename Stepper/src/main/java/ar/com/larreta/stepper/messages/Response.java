package ar.com.larreta.stepper.messages;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.stepper.messages.status.State;
import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
@XmlRootElement
public class Response<T extends JSONable> extends Message {
	
	public static final String NOT_IMPLEMENTED = "not.implemented";

	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Estado de la ejecucion realizada
	 */
	private State state;
	
	/**
	 * Nuevo token de seguridad proporcionado al cliente para posteriores llamadas
	 */
	private String token = "Not Implemented";
	
	@PostConstruct
	public void initialize(){
		token = messageSource.getMessage(NOT_IMPLEMENTED, null, NOT_IMPLEMENTED, Locale.ROOT);
	} 
	
	/**
	 * Cuerpo del mensaje que se esta retornando
	 */
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

	public State getState() {
		return state;
	}
	
	@Autowired @Qualifier("OK")
	public void setState(State state) {
		this.state = state;
	}

}
