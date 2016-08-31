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
import co.com.directv.sdii.model.pojo.AdjustmentModification;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentModificationDAOLocal;

/**
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad AdjustmentModificationDAO 
 * 
 * Fecha de Creación: 28/11/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="AdjustmentModificationDAOLocal",mappedName="ejb/AdjustmentModificationDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AdjustmentModificationDAO extends BaseDao implements AdjustmentModificationDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(AdjustmentModificationDAO.class);
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentModificationDAOLocal#createAdjustmentModification(co.com.directv.sdii.model.pojo.AdjustmentModification)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createAdjustmentModification(AdjustmentModification obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createAdjustmentModification/AdjustmentModificationDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el AdjustmentModification ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentModification/AdjustmentModificationDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentModificationDAOLocal#updateAdjustmentModification(co.com.directv.sdii.model.pojo.AdjustmentModification)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateAdjustmentModification(AdjustmentModification obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateAdjustmentModification/AdjustmentModificationDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el AdjustmentModification ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateAdjustmentModification/AdjustmentModificationDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentModificationDAOLocal#deleteAdjustmentModification(co.com.directv.sdii.model.pojo.AdjustmentModification)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteAdjustmentModification(AdjustmentModification obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteAdjustmentModification/AdjustmentModificationDAO ==");
        Session session = super.getSession();
        try {
            
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append(" delete ");
        	stringQuery.append(" from " + AdjustmentModification.class.getName() + " entity ");
        	stringQuery.append(" where entity.id = :anEntityId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el AdjustmentModification ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteAdjustmentModification/AdjustmentModificationDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentModificationDAOLocal#getAdjustmentModificationByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AdjustmentModification getAdjustmentModificationByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getAdjustmentModificationByID/AdjustmentModificationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentModification.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (AdjustmentModification) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustmentModificationByID/AdjustmentModificationDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentModificationDAOLocal#getAllAdjustmentModifications()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<AdjustmentModification> getAllAdjustmentModifications() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllAdjustmentModifications/AdjustmentModificationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentModification.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllAdjustmentModifications/AdjustmentModificationDAO ==");
        }
    }



    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentModificationDAOLocal#getAdjustmentModificationsByAdjustmentID(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<AdjustmentModification> getAdjustmentModificationsByAdjustmentID(
			Long adjID) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getAdjustmentModificationsByAdjustmentID/AdjustmentModificationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from " + AdjustmentModification.class.getName());
        	stringQuery.append(" entity where entity.adjustment.id = :aAdjId");
        	stringQuery.append(" order by entity.id desc ");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aAdjId", adjID);
            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Inicio getAdjustmentModificationsByAdjustmentID/AdjustmentModificationDAO ==");
        }
	}
	
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentModificationDAOLocal#getAdjustmentModificationsAndUsersModificationsByAdjustmentID(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Object[]> getAdjustmentModificationsAndUsersModificationsByAdjustmentID(Long adjID)throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getAdjustmentModificationsAndUsersModificationsByAdjustmentID/AdjustmentModificationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AdjustmentModification.class.getName());
        	stringQuery.append(" entity, ");
        	stringQuery.append(User.class.getName());
        	stringQuery.append(" user where ");
        	stringQuery.append("entity.adjustment.id=:adjID ");
        	stringQuery.append("and entity.userId=user.id ");
        	Query query =session.createQuery(stringQuery.toString()); 
        	query.setLong("adjID", adjID);
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error getAdjustmentModificationsAndUsersModificationsByAdjustmentID/AdjustmentModificationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAdjustmentModificationsAndUsersModificationsByAdjustmentID/AdjustmentModificationDAO ==");
        }
	}

}
