package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ProfileId entity. @author MyEclipse Persistence Tools
 */

public class ProfileId implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -9004608723508424630L;
	private MenuItem menuItem;
	private Rol rol;

	// Constructors

	/** default constructor */
	public ProfileId() {
	}

	/** full constructor */
	public ProfileId(MenuItem menuItem, Rol rol) {
		this.menuItem = menuItem;
		this.rol = rol;
	}

	// Property accessors

	public MenuItem getMenuItem() {
		return this.menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ProfileId))
			return false;
		ProfileId castOther = (ProfileId) other;

		return ((this.getMenuItem() == castOther.getMenuItem()) || (this
				.getMenuItem() != null
				&& castOther.getMenuItem() != null && this.getMenuItem()
				.equals(castOther.getMenuItem())))
				&& ((this.getRol() == castOther.getRol()) || (this.getRol() != null
						&& castOther.getRol() != null && this.getRol().equals(
						castOther.getRol())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getMenuItem() == null ? 0 : this.getMenuItem().hashCode());
		result = 37 * result
				+ (getRol() == null ? 0 : this.getRol().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "ProfileId [menuItem=" + menuItem + ", rol=" + rol + "]";
	}
}