package co.com.directv.sdii.persistence.dao.security.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Profile;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.security.ProfileDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;



/**
 * Implementación de ProfileDAOLocal
 * 
 * Fecha de Creación: 28/05/2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="ProfileDAOLocal",mappedName="ejb/ProfileDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProfileDAO extends BaseDao implements ProfileDAOLocal{
	
	 private final static Logger log = UtilsBusiness.getLog4J(ProfileDAO.class);


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.ProfileDAOLocal#createProfile(co.com.directv.sdii.model.pojo.Profile)
	 */
	public void createProfile(Profile obj) throws DAOServiceException,
			DAOSQLException {
		 log.debug("== Inicio createProfile/ProfileDAO ==");
	        Session session = ConnectionFactory.getSession();
	        try {
	            if (session == null) {
	                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
	            }
	            session.save(obj);
	            this.doFlush(session);
	        } catch (Throwable ex) {
	            log.debug("== Error creando el Profile ==");
	            throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina createProfile/ProfileDAO ==");
	        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.ProfileDAOLocal#getProfileByIDMenuItem(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Profile> getProfileByIDMenuItem(Long idMenuItem)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getProfileByIDMenuItem/ProfileDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from " );
            queryBuffer.append( Profile.class.getName());
            queryBuffer.append(" pr where pr.id.menuItem.id= :prID ");
            queryBuffer.append("order by pr.id.menuItem.itemCode asc");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("prID", idMenuItem);
            return query.list();
        }catch (Throwable ex) {
            log.debug("== Error consultando Profiles por IDMenuItem ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getProfileByIDMenuItem/ProfileDAO ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.ProfileDAOLocal#getProfileByIDMenuItemAndIDRol(java.lang.Long, java.lang.Long)
	 */
	public Profile getProfileByIDMenuItemAndIDRol(Long idRol, Long idMenuItem)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getProfileByIDMenuItemAndIDRol/ProfileDAO ==");
        Session session = super.getSession();
        Profile obj = null;

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from " );
            queryBuffer.append( Profile.class.getName());
            queryBuffer.append(" pr where pr.id.rol.id= :prID and ");
            queryBuffer.append(" pr.id.menuItem.id= :prMenuID");
            queryBuffer.append(" order by pr.id.rol.rolCode asc");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("prID", idRol);
            query.setLong("prMenuID", idMenuItem);
            obj = (Profile) query.uniqueResult();
            return obj;
        }catch (Throwable ex) {
            log.debug("== Error consultando Profiles por Rol y MenuItem==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getProfileByIDMenuItemAndIDRol/ProfileDAO ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.ProfileDAOLocal#getProfileByIDRol(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Profile> getProfileByIDRol(Long idRol)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getProfileByIDRol/ProfileDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from " );
            queryBuffer.append( Profile.class.getName());
            queryBuffer.append(" pr where pr.id.rol.id= :prID ");
            queryBuffer.append("order by pr.id.menuItem.menuOrder asc");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("prID", idRol);
            return query.list();
        }catch (Throwable ex) {
            log.debug("== Error consultando Profiles por Rol ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getProfileByIDRol/ProfileDAO ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.ProfileDAOLocal#deleteProfile(co.com.directv.sdii.model.pojo.Profile)
	 */
	public void deleteProfile(Profile obj) throws DAOServiceException,
			DAOSQLException {
		 log.debug("== Inicia deleteProfile/ProfileDAO ==");
	        Session session = super.getSession();

	        try {
	            if (session == null) {
	                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
	            }
	            session.delete(obj);
	            this.doFlush(session);
	        }catch (Throwable ex) {
	            log.debug("== Error eliminando el Profilie ==");
	            throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina deleteProfile/ProfileDAO ==");
	        }
	}
	
	

}
