package ar.com.larreta.commons.views;

import org.primefaces.model.DualListModel;

import ar.com.larreta.commons.controllers.UsersPaginator;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.exceptions.PaginatorNotFoundException;

@Deprecated
public class UserDataView extends DataView {
	
	private DualListModel<Profile> profiles;
	
	public UsersPaginator getPaginator() throws PaginatorNotFoundException {
		return (UsersPaginator) super.getPaginator();
	}

	@Override
	public User getSelected() {
		return (User) super.getSelected();
	}

	public void setSelected(User selected) {
		super.setSelected(selected);
	}
	
	public DualListModel<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(DualListModel<Profile> profiles) {
		this.profiles = profiles;
	}

	@Override
	public Entity newSelected() {
		return new User();
	}



}
