/**
 * 
 */
package ar.com.larreta.commons.controllers.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.controllers.LoginController;
import ar.com.larreta.commons.controllers.SessionInitializer;
import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.domain.UserState;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.exceptions.ErrorLoginException;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.commons.exceptions.SignUpException;
import ar.com.larreta.commons.reports.HTML;
import ar.com.larreta.commons.reports.Report;
import ar.com.larreta.commons.services.UserService;
import ar.com.larreta.commons.utils.MailSender;
import ar.com.larreta.commons.utils.SessionUtils;
import ar.com.larreta.commons.views.ChangeDataView;
import ar.com.larreta.commons.views.LoginDataView;

public class LoginControllerImpl extends StandardControllerImpl implements LoginController{
	
	private static final String LOGIN = "login";

	private static final String SIGNIN = "signinForm";

	private static final String APP_CONFIRM_TOKEN = "/app/confirm?token=";
	
	private static final String REENTERED_PASSWORD = "reenteredPassword";

	private static final String PASSWORD = "password";

	private static final String EMAIL = "email";

	private static final String NICK = "nick";

	private static final String SIGNUP = "signup";
	
	@Autowired
	private UserService userService;
	
	public LoginControllerImpl() {
		super();
	}
	
	public void init(RequestContext context) throws ErrorLoginException{
		AppManager.getInstance().setAppURL(getAppURL(context));
	}
	
	public void login(RequestContext context) throws ErrorLoginException{
		Long execStat = statisticsStart(LOGIN); 
		
		userService.setUserClass(getUser().getClass());
		
		try {
			LoginDataView loginView = (LoginDataView) getDataView();
            Authentication request = new UsernamePasswordAuthenticationToken(loginView.getNick(), loginView.getPassword());
            Authentication result = AppManager.getInstance().getAuthenticationManager().authenticate(request);
 
            userService.saveAccessEvent(result.getName(), LOGIN);
            
            HttpServletRequest httpServletRequest = getHttpServletRequest(context);
            httpServletRequest.getSession().setMaxInactiveInterval(appConfigData.getSessionTimeout());

            executeInitializers();
		
		} catch (AuthenticationException e) {
			getLog().error(AppException.getStackTrace(e));
			throw new ErrorLoginException();
        } finally {
        	statisticsStop(execStat);
        }
	}
	

	//FIXME: Este metodo tiene que seguir estando?
	public void addLoginError(RequestContext context) {
		addMessage(SIGNIN, NICK, "Error!", "Nick o Password incorrecto.", FacesMessage.SEVERITY_ERROR);
		addMessage(SIGNIN, PASSWORD, "Error!", "Nick o Password incorrecto.", FacesMessage.SEVERITY_ERROR);
		context.getFlowScope().put("styleElement", "ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all ui-state-error");
	}

	public void executeInitializers() {
		Collection<SessionInitializer> initializers = AppManager.getInstance().getSessionsInitializers();
		Iterator<SessionInitializer> it = initializers.iterator();
		while (it.hasNext()) {
			SessionInitializer sessionInitializer = (SessionInitializer) it.next();
			sessionInitializer.execute();
		}
		
	}
	
	public void signup(RequestContext context) throws SignUpException{
		userService.setUserClass(getUser().getClass());
		validate((LoginDataView) getDataView());
		User user = saveData((LoginDataView) getDataView());
		sendRegistrationEmail(context, user);
		getDataView().redirect("home", "7");
	}

	protected User saveData(LoginDataView loginView) {
		User user = getUser();
		user.setNick(loginView.getNick());
		user.setPassword(AppManager.getInstance().getBase64().encrypt(loginView.getPassword()));
		user.setEmail(loginView.getEmail());
		user.setUserState(UserState.PENDING);
		user.setToken(AppManager.getInstance().getBase64().getToken());
		
		setExtraUserData(loginView, user);
		
		userService.save(user);

		return user;
	}
	
	public void setExtraUserData(LoginDataView loginView, User user){
		getLog().debug("setExtraUserData:" + loginView.toString() + user.toString());
	}

	public User getUser() {
		return new User();
	}

	public void confirm(RequestContext context) {
		String token = getHttpServletRequest(context).getParameter("token");
		User user = userService.getUserByToken(token);
		if(user!=null){
			user.setUserState(UserState.ACTIVE);
			user.setToken(null);
			
			Profile profile = new Profile();
			profile.setId(appConfigData.getNewUserDefaultProfile());
			profile = (Profile) userService.getEntity(profile);
			
			Set<Profile> profiles = new HashSet<Profile>();
			profiles.add(profile);
			
			user.setProfiles(profiles);
			
			userService.update(user);
			sendConfirmEmail(context, user);
			
			getDataView().redirect("home", "7");
		}
	}
	
	protected void sendRegistrationEmail(RequestContext context, User user) {
		try {
			Report report = new HTML(appConfigData);
			addMailParams(user, report);
			report.addParameter("user", user.getNick());
			report.addParameter("link", getAppURL(context) + APP_CONFIRM_TOKEN + user.getToken());
			ByteArrayOutputStream outputStream = report.getOutputStream(appConfigData.getMailTemplateRegistration());
			
			FileOutputStream fileOutputStream = new FileOutputStream(new File("c://tmp/" + user.getNick() + ".html"));
			fileOutputStream.write(outputStream.toByteArray());
			fileOutputStream.close();
			
			Collection<String> emails = new ArrayList<String>();
			emails.add(user.getEmail());
			
			AppManager.getInstance().getMailSender().send(new String(outputStream.toByteArray()), 
												MailSender.HTML_CONTENT_TYPE, 
												"Registracion de nuevos usuarios", 
												appConfigData.decryptedMailUser(), 
												emails);
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));
		}
	}

	protected void sendConfirmEmail(RequestContext context, User user) {
		try {
			Report report = new HTML(appConfigData);
			addMailParams(user, report);
			report.addParameter("user", user.getNick());
			report.addParameter("link", getAppURL(context) );
			ByteArrayOutputStream outputStream = report.getOutputStream(appConfigData.getMailTemplateConfirm());
			
			FileOutputStream fileOutputStream = new FileOutputStream(new File("c://tmp/" + user.getNick() + "_confirm.html"));
			fileOutputStream.write(outputStream.toByteArray());
			fileOutputStream.close();
			
			Collection<String> emails = new ArrayList<String>();
			emails.add(user.getEmail());
			
			AppManager.getInstance().getMailSender().send(new String(outputStream.toByteArray()), 
												MailSender.HTML_CONTENT_TYPE, 
												"Prueba de confirmacion de usuarios", 
												appConfigData.decryptedMailUser(), 
												emails);
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));
		}
	}
	
	protected void addMailParams(User user, Report report) {
		getLog().debug("addMailParams:" + user.toString() + report.toString());
	}
	

	protected void validate(LoginDataView loginView) throws SignUpException {
		Boolean valid = Boolean.TRUE;
		if ((loginView.getNick()!=null)&&(userService.isNickAvailable(loginView.getNick()))){
			addMessage(SIGNUP, NICK, "Error!", AppManager.getInstance().getResourceBundle().getString("app.validation.nick.unavailable"), FacesMessage.SEVERITY_ERROR);
			valid = Boolean.FALSE;
		}	

		if ((loginView.getEmail()!=null)&&(userService.isEmailAvailable(loginView.getEmail()))){
			addMessage(SIGNUP, EMAIL, "Error!", AppManager.getInstance().getResourceBundle().getString("app.validation.email.unavailable"), FacesMessage.SEVERITY_ERROR);
			valid = Boolean.FALSE;
		}

		if ((loginView.getPassword()!=null)&&!loginView.getPassword().equals(loginView.getReenteredPassword())){
			addMessage(SIGNUP, PASSWORD, "Error!", AppManager.getInstance().getResourceBundle().getString("app.validation.password.differents"), FacesMessage.SEVERITY_ERROR);
			addMessage(SIGNUP, REENTERED_PASSWORD, "Error!", AppManager.getInstance().getResourceBundle().getString("app.validation.password.differents"), FacesMessage.SEVERITY_ERROR);
			valid = Boolean.FALSE;
		}
		if (!valid){
			throw new SignUpException();
		}
	}
	
	
	public void change(RequestContext context) throws NotServiceAssignedException {
		getLog().debug("change:" + context.toString());
		//FIXME: Faltan validaciones
		User user = SessionUtils.getActualUser();
		user.setPassword(AppManager.getInstance().getBase64().encrypt(((ChangeDataView)getDataView()).getNewPassword()));
		getService().update(user);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
