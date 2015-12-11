package ar.com.larreta.commons.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role extends ParametricEntity {
	
	private Set<Profile> profiles;

	@ManyToMany (fetch=FetchType.LAZY, targetEntity=Profile.class)
	@JoinTable(name = "containsRoles", joinColumns = { @JoinColumn(name = "idRole") }, 
		inverseJoinColumns = { @JoinColumn(name = "idProfile") })
	public Set<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<Profile> profiles) {
		this.profiles = profiles;
	}

}
