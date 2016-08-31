package co.com.directv.sdii.persistence.dao.security.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ReferenceModification;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;

/**
 * 
 * Implementación en Hibernate para la administración de la persistencia de Usuarios
 * 
 * Fecha de Creación: 28/05/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.security.UserDAOLocal
 * @see co.com.directv.sdii.persistence.dao.BaseDao
 * @see co.com.directv.sdii.model.pojo.User
 */
@Stateless(name="UserDAOLocal",mappedName="ejb/UserDAOLocal")
public class UserDAO extends BaseDao implements UserDAOLocal{

	private final static Logger log = UtilsBusiness.getLog4J(UserDAO.class);
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.UserDAOLocal#getAllUsers()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getAllUsers/UserDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(User.class.getName());
        	queryStr.append(" usr order by usr.name, usr.idNumber");
            Query query = session.createQuery(queryStr.toString());
            query.setCacheable(true);
            
            List<User> result = query.list();
            return result;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando todos los Usuarios ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAllUsers/UserDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.UserDAOLocal#getUserByDocId(java.lang.String)
	 */
	@Override
	public User getUserByDocId(String documentId) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getUserByDocId/UserDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(User.class.getName());
        	queryStr.append(" usr where usr.idNumber = :anUsrDocId");
            Query query = session.createQuery(queryStr.toString());
            query.setString("anUsrDocId", documentId);
            query.setCacheable(true);
            
            User result = (User)query.uniqueResult();
            return result;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el usuario por número de documento ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getUserByDocId/UserDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.UserDAOLocal#getUserById(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public User getUserById(Long userId) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getUserById/UserDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(User.class.getName());
        	queryStr.append(" usr where usr.id = :anUsrId");
            Query query = session.createQuery(queryStr.toString());
            query.setLong("anUsrId", userId);
            query.setCacheable(true);
            
            User result = (User)query.uniqueResult();
            return result;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el usuario por id de registro ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getUserById/UserDAO ==");
        }
	}

	/**
	 * Metodo: Obtiene la información de un usuario dado el identificador del registro en la persistencia
	 * @param userId identificador del usuario
	 * @return Objeto con la información del usuario, nulo en caso que no exista
	 * @throws DAOServiceException en caso de error al tratar de obtener la información del usuario por identificador del registro
	 * @throws DAOSQLException en caso de error al tratar de obtener la información del usuario por identificador del registro
	 * @author aharker
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public User getUserByIdWithNewTransaction(Long userId)throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getUserById/UserDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(User.class.getName());
        	queryStr.append(" usr where usr.id = :anUsrId");
            Query query = session.createQuery(queryStr.toString());
            query.setLong("anUsrId", userId);
            query.setCacheable(true);
            
            User result = (User)query.uniqueResult();
            return result;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el usuario por id de registro ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getUserById/UserDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.UserDAOLocal#getUserBySample(co.com.directv.sdii.model.pojo.User)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserBySample(User userSample)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getUserBySample/UserDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(User.class.getName());
        	queryStr.append(" usr ");
        	boolean byId = false, byName = false, byPosition = false, byRol = false, byDealer = false, byEmail = false, byIdNumber = false, byLogin = false, byCountry = false;
        	if(userSample != null){
        		queryStr.append("where ");
        		if(userSample.getId() != null && userSample.getId().longValue() > 0){
        			byId = true;
        			queryStr.append("usr.id = :anUsrId and ");
        		}
        		if(userSample.getName() != null && userSample.getName().trim().length() > 0){
        			byName = true;
        			queryStr.append("upper(usr.name) = :anUsrName and ");
        		}
        		if(userSample.getPosition() != null && userSample.getPosition().getId() != null && userSample.getPosition().getId().longValue() > 0){
        			byPosition = true;
        			queryStr.append("usr.position.id = :anUsrPosition and ");
        		}
        		if(userSample.getRol() != null && userSample.getRol().getId() != null && userSample.getRol().getId().longValue() > 0){
        			byRol = true;
        			queryStr.append("usr.rol.id = :anUsrRol and ");
        		}
        		if(userSample.getDealer() != null && userSample.getDealer().getId() != null && userSample.getDealer().getId().longValue() > 0){
        			byDealer = true;
        			queryStr.append("usr.dealer.id = :anUsrDealer and ");
        		}
        		if(userSample.getEmail() != null && userSample.getEmail().trim().length() > 0){
        			byEmail = true;
        			queryStr.append("usr.email = :anUsrEmail and ");
        		}
        		if(userSample.getIdNumber() != null && userSample.getIdNumber().trim().length() > 0){
        			byIdNumber = true;
        			queryStr.append("usr.idNumber = :anUsrIdNumber and ");
        		}
        		if(userSample.getLogin() != null && userSample.getLogin().trim().length() > 0){
        			byLogin = true;
        			queryStr.append("upper(usr.login) = :anUsrLogin and ");
        		}
        		if(userSample.getCountry() != null && userSample.getCountry().getId() != null && userSample.getCountry().getId().longValue() > 0){
        			byCountry = true;
        			queryStr.append("usr.country.id = :anUsrCountry and ");
        		}
        		
        		String queryStrTemp = StringUtils.removeEnd(queryStr.toString(), "and ");
        		queryStr = new StringBuffer(queryStrTemp);
        	}
        	
        	queryStr.append(" order by usr.name, usr.idNumber");
            Query query = session.createQuery(queryStr.toString());
            
            if(byId){
            	query.setLong("anUsrId", userSample.getId());
            }
            if(byName){
            	query.setString("anUsrName", userSample.getName().toUpperCase());
            }
            if(byPosition){
            	query.setLong("anUsrPosition", userSample.getPosition().getId());
            }
            if(byRol){
            	query.setLong("anUsrRol", userSample.getRol().getId());
            }
            if(byDealer){
            	query.setLong("anUsrDealer", userSample.getDealer().getId());
            }
            if(byEmail){
            	query.setString("anUsrEmail", userSample.getEmail());
            }
            if(byIdNumber){
            	query.setString("anUsrIdNumber", userSample.getIdNumber());
            }
            if(byLogin){
            	query.setString("anUsrLogin", userSample.getLogin().toUpperCase());
            }
            if(byCountry){
            	query.setLong("anUsrCountry", userSample.getCountry().getId());
            }
            query.setCacheable(true);
            List<User> result = query.list();
            return result;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando todos los Usuarios por ejemplo ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getUserBySample/UserDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.UserDAOLocal#saveUser(co.com.directv.sdii.model.pojo.User)
	 */
	@Override
	public void createUser(User user) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio saveUser/UserDAO ==");
        Session session = super.getSession();
        try {
        	session.save(user);
        	super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error guardando el usuario ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina saveUser/UserDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.UserDAOLocal#updateUser(co.com.directv.sdii.model.pojo.User)
	 */
	@Override
	public void updateUser(User user) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio updateUser/UserDAO ==");
        Session session = super.getSession();
        try {
        	user = (User)session.merge(user);
        	super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el usuario ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateUser/UserDAO ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.UserDAOLocal#deleteUser(co.com.directv.sdii.model.pojo.User)
	 */
	@Override
	public void deleteUser(User user) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio updateUser/UserDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("delete from ");
        	queryStr.append(User.class.getName());
        	queryStr.append(" usr where usr.id = :anUsrId");
        	Query query = session.createQuery(queryStr.toString());
        	query.setLong("anUsrId", user.getId());
        	query.executeUpdate();
        	super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el usuario ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateUser/UserDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.UserDAOLocal#getUserReferenceModificationsByReferenceIDAndModificationType(java.lang.Long, java.lang.String)
	 */
	public User getUserReferenceModificationsByReferenceIDAndModificationType(Long refID,String modificationType)throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getUserReferenceModificationsByReferenceIDAndModificationType/UserDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("select user from ");
        	queryStr.append(User.class.getName());
        	queryStr.append(" user, ");
        	queryStr.append(ReferenceModification.class.getName());
        	queryStr.append(" ref ");
        	queryStr.append("where ");
        	queryStr.append("ref.reference.id= :refID ");
        	queryStr.append("and ref.referenceModType.refModTypeCode= :modificationType ");
        	queryStr.append("and ref.userId=user.id ");
        	
        	Query query = session.createQuery(queryStr.toString());
        	
        	query.setLong("refID", refID);
        	query.setString("modificationType", modificationType);
        	query.setCacheable(true);
        	
        	return (User) query.uniqueResult();
        	
        } catch (Throwable ex) {
            log.debug("== Error getUserReferenceModificationsByReferenceIDAndModificationType/UserDAO ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getUserReferenceModificationsByReferenceIDAndModificationType/UserDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.UserDAOLocal#getUsersByDealerIdAndRoleTypeCode(java.lang.Long, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<User> getUsersByDealerIdAndRoleTypeCode(Long dealerId, String roleTypeCode)throws DAOServiceException, DAOSQLException{
		
		log.debug("== Inicio getUsersByDealerIdAndRoleTypeCode/UserDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(User.class.getName());
        	queryStr.append(" usr where usr.dealer.id= :aDealerId and ");
        	queryStr.append("usr.rol.roleType.roleTypeCode= :aRoleTypeCode");
        	
        	Query query = session.createQuery(queryStr.toString());
        	
        	query.setLong("aDealerId", dealerId);
        	query.setString("aRoleTypeCode", roleTypeCode);
        	query.setCacheable(true);
        	
        	return query.list();
        } catch (Throwable ex) {
            log.debug("== Error getUsersByDealerIdAndRoleTypeCode/UserDAO ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getUsersByDealerIdAndRoleTypeCode/UserDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.UserDAOLocal#getUserByLoginName(java.lang.String)
	 */
	public User getUserByLoginName(String loginName,Long countryId) throws DAOServiceException, DAOSQLException
	{
		log.debug("== Inicio getUserById/UserDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(User.class.getName() + " usr ");
        	queryStr.append(" where usr.login = :aLoginName");
        	queryStr.append(" and usr.country.id = :aCountryId");
            Query query = session.createQuery(queryStr.toString());
            query.setString("aLoginName", loginName);
            query.setLong("aCountryId", countryId);
            query.setCacheable(true);
            
            User result = (User)query.uniqueResult();
            return result;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el usuario por id de registro ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getUserById/UserDAO ==");
        }		
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.UserDAOLocal#getUserByRoleTypeCodeAndCountryId(java.lang.String, java.lang.Long)
	 */
	@Override
	public List<User> getUsersByRoleTypeCodeAndCountryId(String roleTypeCode, Long countryId) throws DAOServiceException, DAOSQLException
	{
		log.debug("== Inicio getUserByRoleTypeCodeAndCountryId/UserDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(User.class.getName());
        	queryStr.append(" usr where usr.rol.roleType.roleTypeCode = :roleTypeCode");
        	queryStr.append(" and usr.country.id = :countryId");
            
        	Query query = session.createQuery(queryStr.toString());
            
            query.setString("roleTypeCode", roleTypeCode);
            query.setLong("countryId", countryId);
            query.setCacheable(true);
            
            return query.list();
            
        } catch (Throwable ex) {
            log.debug("== Error en getUserByRoleTypeCodeAndCountryId/UserDAO ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getUserByRoleTypeCodeAndCountryId/UserDAO ==");
        }		
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.UserDAOLocal#getUsersByRoleTypeCode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<User> getUsersByRoleTypeCode(String roleTypeCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getUsersByRoleTypeCode/UserDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer(" from ");
        	queryStr.append(User.class.getName());
        	queryStr.append(" usr where usr.rol.roleType.roleTypeCode= :aRoleTypeCode");
        	
        	Query query = session.createQuery(queryStr.toString());
        	
        	query.setString("aRoleTypeCode", roleTypeCode);
        	query.setCacheable(true);
        	
        	return query.list();
        } catch (Throwable ex) {
            log.debug("== Error getUsersByRoleTypeCode/UserDAO ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getUsersByRoleTypeCode/UserDAO ==");
        }
	}

}
