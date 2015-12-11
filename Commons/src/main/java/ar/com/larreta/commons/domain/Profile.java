package ar.com.larreta.commons.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "profile")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Profile SET deleted=CURRENT_TIMESTAMP WHERE id=? and description!='Admin'")
public class Profile extends ParametricEntity {
	
	private Set<Role> roles;
	private Set<User> users;

	@ManyToMany (fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinTable(name = "hasProfiles", joinColumns = { @JoinColumn(name = "idProfile") }, 
		inverseJoinColumns = { @JoinColumn(name = "idUser") })
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@ManyToMany (fetch=FetchType.LAZY, targetEntity=Role.class)
	@JoinTable(name = "containsRoles", joinColumns = { @JoinColumn(name = "idProfile") }, 
		inverseJoinColumns = { @JoinColumn(name = "idRole") })
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	@Override
	@Transient
	public Date getDeleted(){
		return deleted;
	}
	

}
