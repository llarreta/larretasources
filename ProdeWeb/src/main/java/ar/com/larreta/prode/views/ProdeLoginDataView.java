package ar.com.larreta.prode.views;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.primefaces.model.UploadedFile;

import ar.com.larreta.commons.views.LoginDataView;
import ar.com.larreta.prode.domain.Player;

public class ProdeLoginDataView extends LoginDataView {

	public static final String PRODE_LOGIN_DATA_VIEW = "prodeLoginDataView";
	
	@NotNull(message="{validations.notnull}")
	@NotEmpty(message="{validations.notnull}")
	private String name;
	@NotNull(message="{validations.notnull}")
	@NotEmpty(message="{validations.notnull}")
	private String surname;
	
	private Player friend;
	
	public Player getFriend() {
		return friend;
	}
	public void setFriend(Player friend) {
		this.friend = friend;
	}
	private UploadedFile file;
	
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}

}
