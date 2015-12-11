package ar.com.larreta.prode.views;

import org.primefaces.model.DualListModel;

import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.views.DataView;

public class PlayerDataView extends DataView {

	private DualListModel<Profile> profiles;
	
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
