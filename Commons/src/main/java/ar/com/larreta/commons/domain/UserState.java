package ar.com.larreta.commons.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "userState")
public class UserState extends ParametricEntity {
	
	public static final UserState ACTIVE = new UserState(new Long(1), "Active");
	public static final UserState PENDING = new UserState(new Long(2), "Pending");
	public static final UserState INACTIVE = new UserState(new Long(1), "Inactive");
	
	public UserState() {
		super();
	}

	public UserState(Long id, String description) {
		super(id, description);
	}

	
	

}
