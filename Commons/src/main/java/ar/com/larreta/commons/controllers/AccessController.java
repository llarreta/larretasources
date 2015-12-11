package ar.com.larreta.commons.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;
import ar.com.larreta.commons.services.impl.AccessServiceImpl;


public class AccessController extends StandardControllerImpl {

	@Autowired
	public void setService(AccessServiceImpl standardService) {
		super.setService(standardService);
	}
}
