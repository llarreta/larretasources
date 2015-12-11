package ar.com.larreta.commons.views;

import ar.com.larreta.commons.domain.Entity;

public class ChangeDataView extends DataView {
	
	private String lastPassword;
	private String reentered;
	private String newPassword;
	
	

	public String getLastPassword() {
		return lastPassword;
	}



	public void setLastPassword(String lastPassword) {
		this.lastPassword = lastPassword;
	}



	public String getReentered() {
		return reentered;
	}



	public void setReentered(String reentered) {
		this.reentered = reentered;
	}



	public String getNewPassword() {
		return newPassword;
	}



	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}



	@Override
	public Entity newSelected() {
		return null;
	}

}
