/**
 * 
 */
package ar.com.larreta.commons.domain;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE User SET deleted=CURRENT_TIMESTAMP WHERE id=? and nick!='root'")
@XmlRootElement
public class User extends ar.com.larreta.commons.domain.Entity{

	private String nick;
	private String password;
	private String email;
	private UserState userState;
	private String token;
	private Set<Profile> profiles;
	
	@ManyToMany (fetch=FetchType.LAZY, targetEntity=Profile.class)
	@JoinTable(name = "hasProfiles", joinColumns = { @JoinColumn(name = "idUser") }, 
		inverseJoinColumns = { @JoinColumn(name = "idProfile") })
	public Set<Profile> getProfiles() {
		return profiles;
	}
	public void setProfiles(Set<Profile> profiles) {
		this.profiles = profiles;
	}
	@Basic
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=UserState.class)
	@JoinColumn (name="idUserState")
	public UserState getUserState() {
		return userState;
	}
	public void setUserState(UserState userState) {
		this.userState = userState;
	}
	@Basic
	public String getNick() {
		return StringUtils.lowerCase(nick);
	}
	public void setNick(String nick) {
		this.nick = StringUtils.lowerCase(nick);
	}

	@Basic
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Basic
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

	
	
}
