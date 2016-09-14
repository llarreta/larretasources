package ar.com.larreta.commons.views;

import java.io.Serializable;

import ar.com.larreta.commons.domain.Entity;

@Deprecated
public class LoginDataView extends DataView implements Serializable {

	public static final String LOGIN_DATA_VIEW = "loginDataView";
	
	private String nick;
	private String password;
	private String reenteredPassword;
	private String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getReenteredPassword() {
		return reenteredPassword;
	}
	public void setReenteredPassword(String reenteredPassword) {
		this.reenteredPassword = reenteredPassword;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public Entity newSelected() {
		return null;
	}
	
}
