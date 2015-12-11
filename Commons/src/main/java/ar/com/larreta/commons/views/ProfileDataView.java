package ar.com.larreta.commons.views;

import org.primefaces.model.DualListModel;

import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.domain.Role;

public class ProfileDataView extends DataView {
	
	private DualListModel<Role> roles;

	public DualListModel<Role> getRoles() {
		return roles;
	}

	public void setRoles(DualListModel<Role> roles) {
		this.roles = roles;
	}
	
	@Override
	public Profile getSelected() {
		return (Profile) super.getSelected();
	}

	public void setSelected(Profile selected) {
		super.setSelected(selected);
	}

}
