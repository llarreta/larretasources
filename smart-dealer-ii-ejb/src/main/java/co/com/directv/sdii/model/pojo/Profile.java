package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * Profile entity. @author MyEclipse Persistence Tools
 */

public class Profile implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4595620268541341421L;
	private ProfileId id;

	// Constructors

	/** default constructor */
	public Profile() {
	}

	/** full constructor */
	public Profile(ProfileId id) {
		this.id = id;
	}

	// Property accessors

	public ProfileId getId() {
		return this.id;
	}

	public void setId(ProfileId id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Profile [id=" + id + "]";
	}

}