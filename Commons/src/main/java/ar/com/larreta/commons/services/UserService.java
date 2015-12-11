package ar.com.larreta.commons.services;

import java.util.Collection;

import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.domain.Role;
import ar.com.larreta.commons.domain.User;

public interface UserService extends StandardService {
	
	public Class getUserClass();
	public void setUserClass(Class userClass);
	public Boolean isNickAvailable(String nick);
	public User getUserByNick(String nick);
	public User getUserByNickWithProfiles(String nick);
	public User getUserByToken(String token);
	public Boolean isEmailAvailable(String email);
	public void saveAccessEvent(String userName, String detail);
	public Collection<Role> getRoles(Profile profile);
	public Collection<Role> getRoles(User user);
	public Collection<Profile> getProfiles(User user);
}
