package ar.com.larreta.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;

import ar.com.larreta.rest.messages.Body;
import ar.com.larreta.rest.messages.Message;
import ar.com.larreta.rest.messages.NotRequestNeeded;
import ar.com.larreta.rest.messages.NotRequestNeededBody;
import ar.com.larreta.rest.messages.Request;
import ar.com.larreta.rest.messages.TargetedBody;

public abstract class HelpConfig <UpdateBodyRequest extends Body, LoadBodyRequest extends Body> {

	@Autowired
	protected ApplicationContext applicationContext;
	
	public Message getCreateHelp(){
		Class<?>[] generics = ResolvableType.forClass(HelpConfig.class, getClass()).resolveGenerics();
		Request<Body> request = (Request<Body>) applicationContext.getBean(Request.COMPONENT_NAME);
		request.setBody((Body) applicationContext.getBean(generics[0]));
		return request;
	}

	public Message getUpdateHelp(){
		return getCreateHelp();
	}
	
	public Message getTargetedRequest(){
		Request<Body> request = (Request<Body>) applicationContext.getBean(Request.COMPONENT_NAME);
		request.setBody(applicationContext.getBean(TargetedBody.class));
		return request;
	}
	
	public Message getLoadHelp(){
		Class<?>[] generics = ResolvableType.forClass(HelpConfig.class, getClass()).resolveGenerics();
		Request<Body> request = (Request<Body>) applicationContext.getBean(Request.COMPONENT_NAME);
		request.setBody((Body) applicationContext.getBean(generics[1]));
		return request;
	}
	
	public Message getNotRequestNeeded(){
		return (Message) applicationContext.getBean(NotRequestNeeded.COMPONENT_NAME);
	}
	
}
