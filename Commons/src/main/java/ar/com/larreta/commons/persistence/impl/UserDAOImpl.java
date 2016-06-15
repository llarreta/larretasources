package ar.com.larreta.commons.persistence.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.domain.Role;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.persistence.UserDAO;
import ar.com.larreta.commons.persistence.dao.args.LoadArguments;
import ar.com.larreta.commons.persistence.dao.impl.StandardDAOImpl;
import ar.com.larreta.commons.persistence.exceptions.UnreportedEntityException;

@Repository
public class UserDAOImpl extends StandardDAOImpl implements UserDAO {

	private Criteria getUserCriteria() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(User.class);
		return criteria;
	}
	
	public User getUserByNick(String nick){
		Criteria criteria = getUserCriteria();
		criteria.add(Restrictions.eq("nick", nick));
		
		return getFirst(criteria);
	}

	public User getUserByToken(String token){
		Criteria criteria = getUserCriteria();
		criteria.add(Restrictions.eq("token", token));
		
		return getFirst(criteria);
	}
	
	public User getUserByEmail(String email){
		Criteria criteria = getUserCriteria();
		criteria.add(Restrictions.eq("email", email));
		User first = getFirst(criteria);
		return first;
	}

	private User getFirst(Criteria criteria) {
		List<User> users = criteria.list(); 
		if ((users==null) || (users.isEmpty())){
			return null;
		}
		return users.get(0);
	}
	
	public Collection<Profile> getProfiles(User user){
		return getPropertyCollection(user, "profiles");
	}

	public Collection<Role> getRoles(Profile profile){
		return getPropertyCollection(profile, "roles");
	}

	/**
	 * Trae todos los roles para un determinado usuario
	 * @param user
	 * @return
	 * @throws UnreportedEntityException 
	 */
	public Collection<Role> getRoles(User user) throws UnreportedEntityException{
		LoadArguments args = new LoadArguments(Role.class);
		args.addInnerJoin("profiles").addInnerJoin("profiles.users");
		args.addWhereEqual("profiles.users.id", user.getId());
		return load(args); 
	}

}
