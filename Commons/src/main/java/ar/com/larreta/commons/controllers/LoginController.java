/**
 * 
 */
package ar.com.larreta.commons.controllers;

import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.exceptions.ErrorLoginException;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.commons.exceptions.SignUpException;
import ar.com.larreta.commons.services.UserService;
import ar.com.larreta.commons.views.LoginDataView;

public interface LoginController extends StandardController {
	public void init(RequestContext context) throws ErrorLoginException;
	
	public void login(RequestContext context) throws ErrorLoginException;

	public void addLoginError(RequestContext context);

	public void executeInitializers();
	
	public void signup(RequestContext context) throws SignUpException;

	public void setExtraUserData(LoginDataView loginView, User user);

	public User getUser();

	public void confirm(RequestContext context);
	
	public void change(RequestContext context) throws NotServiceAssignedException;

	public UserService getUserService();

	public void setUserService(UserService userService);

}
