package ar.com.larreta.rest.messages;

import java.io.Serializable;

import org.apache.log4j.Logger;

import ar.com.larreta.prototypes.JSONable;

public abstract class Message extends JSONable implements Serializable {
	
	private final static Logger LOGGER = Logger.getLogger(Message.class);

}
