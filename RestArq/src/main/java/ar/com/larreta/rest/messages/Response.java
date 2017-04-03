package ar.com.larreta.rest.messages;

import ar.com.larreta.rest.messages.status.BAD;
import ar.com.larreta.rest.messages.status.NOK;
import ar.com.larreta.rest.messages.status.NOP;
import ar.com.larreta.rest.messages.status.NOT;
import ar.com.larreta.rest.messages.status.OK;
import ar.com.larreta.rest.messages.status.State;

public class Response extends Message {
	
	private State state = new OK();
	
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
