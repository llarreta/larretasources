package ar.com.larreta.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import ar.com.larreta.rest.messages.TargetedBody;

public abstract class HelpConfig {

	@Autowired
	protected ApplicationContext applicationContext;
	
	@Autowired
	protected TargetedBody targetedBody;
	
}
