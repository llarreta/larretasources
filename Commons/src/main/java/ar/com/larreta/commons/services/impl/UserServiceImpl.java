/**
 * 
 */
package ar.com.larreta.commons.services.impl;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.domain.Role;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.domain.audit.UserAccessHistory;
import ar.com.larreta.commons.exceptions.ErrorLoginException;
import ar.com.larreta.commons.exceptions.NotImplementedException;
import ar.com.larreta.commons.persistence.UserDAO;
import ar.com.larreta.commons.persistence.dao.impl.LoadArguments;
import ar.com.larreta.commons.services.UserService;

@Service(UserServiceImpl.USER_SERVICE)
@Transactional
public class UserServiceImpl extends StandardServiceImpl implements UserService {
	
	public static final String USER_SERVICE = "userService";
	public static final String EMAIL = "email";
	public static final String NICK = "nick";

	@Autowired
	private UserDAO userDAO;
	
	private Class userClass;
	
	public Class getUserClass() {
		return userClass;
	}

	public void setUserClass(Class userClass) {
		this.userClass = userClass;
	}
	
	/**
	 * Valida la disponibilidad de un nick
	 * @param nick
	 * @return
	 * @throws ErrorLoginException
	 */
	public Boolean isNickAvailable(String nick){
		return getUserByNick(nick)!=null;
	}

	/**
	 * Retorna un usuario a partir de su nick y sus perfiles
	 * @param nick
	 * @return
	 */
	@Transactional(readOnly=true)
	public User getUserByNickWithProfiles(String nick) {
		LoadArguments args = new LoadArguments(User.class);
		args.addProjectedCollection("profiles").addProjectedCollection("profiles.roles");
		args.addWhereEqual("nick", nick);
		Collection users = userDAO.load(args);
		if ((users==null) || (users.isEmpty())){
			return null;
		}
		return (User) users.iterator().next();
	}

	/**
	 * Retorna un usuario a partir de su nick
	 * @param nick
	 * @return
	 */
	@Transactional(readOnly=true)
	public User getUserByNick(String nick) {
		LoadArguments args = new LoadArguments(User.class);
		args.addWhereEqual("nick", nick);
		Collection users = userDAO.load(args);
		if ((users==null) || (users.isEmpty())){
			return null;
		}
		return (User) users.iterator().next();
	}

	/**
	 * Retorna un usuario a partir de su token
	 * @param token
	 * @return
	 */
	public User getUserByToken(String token) {
		return userDAO.getUserByToken(token);
	}
	
	/**
	 * Valida la disponibilidad de un email
	 * @param email
	 * @return
	 * @throws ErrorLoginException
	 */
	public Boolean isEmailAvailable(String email){
		User user = userDAO.getUserByEmail(email);
		return user!=null;
	}
	
	public void saveAccessEvent(String userName, String detail){
		UserAccessHistory userAccessHistory = new UserAccessHistory();
		userAccessHistory.setUser(getUserByNick(userName));
		userAccessHistory.setDetail(detail);
		userAccessHistory.setUserAccessDate(new Date());
		
		userDAO.save(userAccessHistory);

		getLog().debug("XML=>" + userAccessHistory.toString());
	}
	
	public Collection<Role> getRoles(Profile profile){
		return userDAO.getRoles(profile);
	}
	
	@Transactional(readOnly=true)
	public Collection<Role> getRoles(User user){
		return userDAO.getRoles(user);
	}
	
	public Collection<Profile> getProfiles(User user){
		return userDAO.getProfiles(user);
	}
}
