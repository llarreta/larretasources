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
import co.com.directv.sdii.model.pojo.NotSerPartialRetirementId;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementIdDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad NotSerPartialRetirementId
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.NotSerPartialRetirementId
 * @see co.com.directv.sdii.model.hbm.NotSerPartialRetirementId.hbm.xml
 */
@Stateless(name="NotSerPartialRetirementIdDAOLocal",mappedName="ejb/NotSerPartialRetirementIdDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NotSerPartialRetirementIdDAO extends BaseDao implements NotSerPartialRetirementIdDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(NotSerPartialRetirementIdDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementIdsDAOLocal#createNotSerPartialRetirementId(co.com.directv.sdii.model.pojo.NotSerPartialRetirementId)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createNotSerPartialRetirementId(NotSerPartialRetirementId obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createNotSerPartialRetirementId/NotSerPartialRetirementIdDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el NotSerPartialRetirementId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createNotSerPartialRetirementId/NotSerPartialRetirementIdDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementIdsDAOLocal#updateNotSerPartialRetirementId(co.com.directv.sdii.model.pojo.NotSerPartialRetirementId)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateNotSerPartialRetirementId(NotSerPartialRetirementId obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateNotSerPartialRetirementId/NotSerPartialRetirementIdDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el NotSerPartialRetirementId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateNotSerPartialRetirementId/NotSerPartialRetirementIdDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementIdsDAOLocal#deleteNotSerPartialRetirementId(co.com.directv.sdii.model.pojo.NotSerPartialRetirementId)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteNotSerPartialRetirementId(NotSerPartialRetirementId obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteNotSerPartialRetirementId/NotSerPartialRetirementIdDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from NotSerPartialRetirementId entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el NotSerPartialRetirementId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteNotSerPartialRetirementId/NotSerPartialRetirementIdDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementIdsDAOLocal#getNotSerPartialRetirementIdsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public NotSerPartialRetirementId getNotSerPartialRetirementIdByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getNotSerPartialRetirementIdByID/NotSerPartialRetirementIdDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(NotSerPartialRetirementId.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (NotSerPartialRetirementId) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getNotSerPartialRetirementIdByID/NotSerPartialRetirementIdDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementIdsDAOLocal#getAllNotSerPartialRetirementIds()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<NotSerPartialRetirementId> getAllNotSerPartialRetirementIds() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllNotSerPartialRetirementIds/NotSerPartialRetirementIdDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(NotSerPartialRetirementId.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllNotSerPartialRetirementIds/NotSerPartialRetirementIdDAO ==");
        }
    }

}
