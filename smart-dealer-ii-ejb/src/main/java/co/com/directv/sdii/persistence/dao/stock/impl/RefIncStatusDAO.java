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
import co.com.directv.sdii.model.pojo.RefIncStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.RefIncStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad RefIncStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.RefIncStatus
 * @see co.com.directv.sdii.model.hbm.RefIncStatus.hbm.xml
 */
@Stateless(name="RefIncStatusDAOLocal",mappedName="ejb/RefIncStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RefIncStatusDAO extends BaseDao implements RefIncStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(RefIncStatusDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefIncStatussDAOLocal#createRefIncStatus(co.com.directv.sdii.model.pojo.RefIncStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createRefIncStatus(RefIncStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createRefIncStatus/RefIncStatusDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el RefIncStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createRefIncStatus/RefIncStatusDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefIncStatussDAOLocal#updateRefIncStatus(co.com.directv.sdii.model.pojo.RefIncStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateRefIncStatus(RefIncStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateRefIncStatus/RefIncStatusDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el RefIncStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateRefIncStatus/RefIncStatusDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefIncStatussDAOLocal#deleteRefIncStatus(co.com.directv.sdii.model.pojo.RefIncStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteRefIncStatus(RefIncStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteRefIncStatus/RefIncStatusDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from RefIncStatus entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el RefIncStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteRefIncStatus/RefIncStatusDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefIncStatussDAOLocal#getRefIncStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public RefIncStatus getRefIncStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getRefIncStatusByID/RefIncStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefIncStatus.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (RefIncStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getRefIncStatusByID/RefIncStatusDAO ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefIncStatusDAOLocal#getRefIncStatusByCode(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public RefIncStatus getRefIncStatusByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getRefIncStatusByCode/RefIncStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefIncStatus.class.getName());
        	stringQuery.append(" entity where entity.refIncStatusCode = :code");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("code", code);

            return (RefIncStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getRefIncStatusByCode/RefIncStatusDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.RefIncStatussDAOLocal#getAllRefIncStatuss()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<RefIncStatus> getAllRefIncStatuss() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllRefIncStatuss/RefIncStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefIncStatus.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllRefIncStatuss/RefIncStatusDAO ==");
        }
    }

}
