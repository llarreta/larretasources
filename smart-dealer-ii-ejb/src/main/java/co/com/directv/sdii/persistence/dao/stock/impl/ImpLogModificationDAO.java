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
import co.com.directv.sdii.model.pojo.ImpLogModification;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ImpLogModificationDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ImpLogModification
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ImpLogModification
 * @see co.com.directv.sdii.model.hbm.ImpLogModification.hbm.xml
 */
@Stateless(name="ImpLogModificationDAOLocal",mappedName="ejb/ImpLogModificationDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImpLogModificationDAO extends BaseDao implements ImpLogModificationDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ImpLogModificationDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImpLogModificationsDAOLocal#createImpLogModification(co.com.directv.sdii.model.pojo.ImpLogModification)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createImpLogModification(ImpLogModification obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createImpLogModification/ImpLogModificationDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ImpLogModification ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createImpLogModification/ImpLogModificationDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImpLogModificationsDAOLocal#updateImpLogModification(co.com.directv.sdii.model.pojo.ImpLogModification)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateImpLogModification(ImpLogModification obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateImpLogModification/ImpLogModificationDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ImpLogModification ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateImpLogModification/ImpLogModificationDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImpLogModificationsDAOLocal#deleteImpLogModification(co.com.directv.sdii.model.pojo.ImpLogModification)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteImpLogModification(ImpLogModification obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteImpLogModification/ImpLogModificationDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ImpLogModification entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ImpLogModification ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImpLogModification/ImpLogModificationDAO ==");
        }
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteImpLogModificationByImportLogId(Long impLogID) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteImpLogModificationByImportLogId/ImpLogModificationDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from "+ImpLogModification.class.getName()+" entity where entity.importLog.id = :impLogID");
            query.setLong("impLogID", impLogID);
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ImpLogModification ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImpLogModificationByImportLogId/ImpLogModificationDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImpLogModificationsDAOLocal#getImpLogModificationsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ImpLogModification getImpLogModificationByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getImpLogModificationByID/ImpLogModificationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImpLogModification.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ImpLogModification) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImpLogModificationByID/ImpLogModificationDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImpLogModificationsDAOLocal#getImpLogModificationsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ImpLogModification> getImpLogModificationByImportLogID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getImpLogModificationByImportLogID/ImpLogModificationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImpLogModification.class.getName());
        	stringQuery.append(" entity where entity.importLog.id = :anId");
        	stringQuery.append(" order by entity.id desc ");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImpLogModificationByImportLogID/ImpLogModificationDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImpLogModificationsDAOLocal#getAllImpLogModifications()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ImpLogModification> getAllImpLogModifications() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllImpLogModifications/ImpLogModificationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImpLogModification.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllImpLogModifications/ImpLogModificationDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ImpLogModificationsDAOLocal#getImpLogModificationByImportLogIDAndStatusId(java.lang.Long, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public List<ImpLogModification> getImpLogModificationByImportLogIDAndStatusId(Long id, Long statusId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getImpLogModificationByImportLogIDAndStatusId/ImpLogModificationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ImpLogModification.class.getName());
        	stringQuery.append(" entity where entity.importLog.id = :anId");
        	stringQuery.append(" and entity.impLogModificationType.id = :anStatusId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);
            query.setLong("anStatusId", statusId);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error getImpLogModificationByImportLogIDAndStatusId/ImpLogModificationDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getImpLogModificationByImportLogIDAndStatusId/ImpLogModificationDAO ==");
        }
    }
}
