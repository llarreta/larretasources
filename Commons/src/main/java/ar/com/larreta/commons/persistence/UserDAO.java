package ar.com.larreta.commons.persistence;

import java.util.Collection;

import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.domain.Role;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.persistence.dao.StandardDAO;

public interface UserDAO extends StandardDAO {

	public User getUserByNick(String nick);

	public User getUserByToken(String token);
	
	public User getUserByEmail(String email);
	
	public Collection<Profile> getProfiles(User user);

	public Collection<Role> getRoles(Profile profile);

	/**
	 * Trae todos los roles para un determinado usuario
	 * @param user
	 * @return
	 */
	public Collection<Role> getRoles(User user); 

}
