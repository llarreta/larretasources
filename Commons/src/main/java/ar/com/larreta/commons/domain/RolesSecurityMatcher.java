package ar.com.larreta.commons.domain;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Entity
@Table(name = "securityMatcher")
@DiscriminatorValue(RolesSecurityMatcher.ROLES)
public class RolesSecurityMatcher extends SecurityMatcher {

	public static final String ROLES = "roles";
	
	private Set<Role> roles;

	@ManyToMany (fetch=FetchType.EAGER, targetEntity=Role.class)
	@JoinTable(name = "securityMatcherRoles", joinColumns = { @JoinColumn(name = "idSecurityMatcher") }, 
		inverseJoinColumns = { @JoinColumn(name = "idRole") })
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	@Transient
	@Override
	public void process(HttpSecurity http) throws Exception {
		if (roles!=null){
			String[] authorities = new String[roles.size()];
			Iterator<Role> it = roles.iterator();
			Integer index = 0;
			while (it.hasNext()) {
				Role role = (Role) it.next();
				authorities[index] = role.getDescription();
				index++;
			}
			http.authorizeRequests().antMatchers(getPattern()).hasAnyAuthority(authorities);
		}
		
	}

	@Transient
	@Override
	public String getSecurityMatcherType() {
		return ROLES;
	}
	
}
