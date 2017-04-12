package ar.com.larreta.rest.messages;

import ar.com.larreta.rest.messages.status.BAD;
import ar.com.larreta.rest.messages.status.NOK;
import ar.com.larreta.rest.messages.status.NOP;
import ar.com.larreta.rest.messages.status.NOT;
import ar.com.larreta.rest.messages.status.OK;
import ar.com.larreta.rest.messages.status.State;

public class Response<T extends JSONable> extends Message {
	
	/**
	 * Estado de la ejecucion realizada
	 */
	private State state = new OK();
	
	/**
	 * Nuevo token de seguridad proporcionado al cliente para posteriores llamadas
	 */
	private String token = "Not Implemented";
	
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

	public void setOK(){
		state = new OK();
	}

	public void setNOP(){
		state = new NOP();
	}
	
	public void setNOK(){
		state = new NOK();
	}
	
	public void setNOT(){
		state = new NOT();
	}
	
	public void setBAD(){
		state = new BAD();
	}

	public State getState() {
		return state;
	}
	

}
