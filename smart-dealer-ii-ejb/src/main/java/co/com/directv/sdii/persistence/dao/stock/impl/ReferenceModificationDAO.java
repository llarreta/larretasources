package co.com.directv.sdii.persistence.dao.stock.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ReferenceModification;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ReferenceModificationDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ReferenceModification
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ReferenceModification
 * @see co.com.directv.sdii.model.hbm.ReferenceModification.hbm.xml
 */
@Stateless(name="ReferenceModificationDAOLocal",mappedName="ejb/ReferenceModificationDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReferenceModificationDAO extends BaseDao implements ReferenceModificationDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ReferenceModificationDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceModificationsDAOLocal#createReferenceModification(co.com.directv.sdii.model.pojo.ReferenceModification)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createReferenceModification(ReferenceModification obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createReferenceModification/ReferenceModificationDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ReferenceModification ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createReferenceModification/ReferenceModificationDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceModificationsDAOLocal#updateReferenceModification(co.com.directv.sdii.model.pojo.ReferenceModification)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateReferenceModification(ReferenceModification obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateReferenceModification/ReferenceModificationDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ReferenceModification ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateReferenceModification/ReferenceModificationDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceModificationsDAOLocal#deleteReferenceModification(co.com.directv.sdii.model.pojo.ReferenceModification)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteReferenceModification(ReferenceModification obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteReferenceModification/ReferenceModificationDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ReferenceModification entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ReferenceModification ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteReferenceModification/ReferenceModificationDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceModificationsDAOLocal#getReferenceModificationsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ReferenceModification getReferenceModificationByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getReferenceModificationByID/ReferenceModificationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceModification.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ReferenceModification) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceModificationByID/ReferenceModificationDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceModificationsDAOLocal#getAllReferenceModifications()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ReferenceModification> getAllReferenceModifications() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllReferenceModifications/ReferenceModificationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceModification.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllReferenceModifications/ReferenceModificationDAO ==");
        }
    }



    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceModificationsDAOLocal#getAllReferenceModifications()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ReferenceModification> getReferenceModificationsByReferenceID(
			Long refID) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getReferenceModificationsByReferenceID/ReferenceModificationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceModification.class.getName());
        	stringQuery.append(" entity where entity.reference.id = :aRefId");
        	stringQuery.append(" order by entity.id desc ");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aRefId", refID);
            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Inicio getReferenceModificationsByReferenceID/ReferenceModificationDAO ==");
        }
	}
	
	/* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceModificationsDAOLocal#getReferenceModificationsAndUsersModificationsByReferenceID(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Object[]> getReferenceModificationsAndUsersModificationsByReferenceID(Long refID)throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getReferenceModificationsAndUsersModificationsByReferenceID/ReferenceModificationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceModification.class.getName());
        	stringQuery.append(" entity, ");
        	stringQuery.append(User.class.getName());
        	stringQuery.append(" user where ");
        	stringQuery.append("entity.reference.id=:refID ");
        	stringQuery.append("and entity.userId=user.id ");
        	Query query =session.createQuery(stringQuery.toString()); 
        	query.setLong("refID", refID);
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error getReferenceModificationsAndUsersModificationsByReferenceID/ReferenceModificationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceModificationsAndUsersModificationsByReferenceID/ReferenceModificationDAO ==");
        }
	}

}
