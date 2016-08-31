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
import co.com.directv.sdii.model.pojo.RecordStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.RecordStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad RecordStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.RecordStatus
 * @see co.com.directv.sdii.model.hbm.RecordStatus.hbm.xml
 */
@Stateless(name="RecordStatusDAOLocal",mappedName="ejb/RecordStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RecordStatusDAO extends BaseDao implements RecordStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(RecordStatusDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RecordStatussDAOLocal#createRecordStatus(co.com.directv.sdii.model.pojo.RecordStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createRecordStatus(RecordStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createRecordStatus/RecordStatusDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el RecordStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createRecordStatus/RecordStatusDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RecordStatussDAOLocal#updateRecordStatus(co.com.directv.sdii.model.pojo.RecordStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateRecordStatus(RecordStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateRecordStatus/RecordStatusDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el RecordStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateRecordStatus/RecordStatusDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RecordStatussDAOLocal#deleteRecordStatus(co.com.directv.sdii.model.pojo.RecordStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteRecordStatus(RecordStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteRecordStatus/RecordStatusDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from RecordStatus entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el RecordStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteRecordStatus/RecordStatusDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RecordStatussDAOLocal#getRecordStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public RecordStatus getRecordStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getRecordStatusByID/RecordStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RecordStatus.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (RecordStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getRecordStatusByID/RecordStatusDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RecordStatussDAOLocal#getAllRecordStatuss()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<RecordStatus> getAllRecordStatuss() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllRecordStatuss/RecordStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RecordStatus.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllRecordStatuss/RecordStatusDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.RecordStatusDAOLocal#getRecordStatusByCode(java.lang.String)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public RecordStatus getRecordStatusByCode(String codeEntity)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getRecordStatusByCode/RecordStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RecordStatus.class.getName());
        	stringQuery.append(" entity where entity.recordStatusCode = :aRSCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aRSCode", codeEntity);

            return (RecordStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getRecordStatusByCode/RecordStatusDAO ==");
        }
	}

}
