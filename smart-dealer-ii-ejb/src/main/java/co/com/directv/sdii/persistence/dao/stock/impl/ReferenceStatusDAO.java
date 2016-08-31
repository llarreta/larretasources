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
import co.com.directv.sdii.model.pojo.ReferenceStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ReferenceStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ReferenceStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ReferenceStatus
 * @see co.com.directv.sdii.model.hbm.ReferenceStatus.hbm.xml
 */
@Stateless(name="ReferenceStatusDAOLocal",mappedName="ejb/ReferenceStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReferenceStatusDAO extends BaseDao implements ReferenceStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ReferenceStatusDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceStatussDAOLocal#createReferenceStatus(co.com.directv.sdii.model.pojo.ReferenceStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createReferenceStatus(ReferenceStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createReferenceStatus/ReferenceStatusDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ReferenceStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createReferenceStatus/ReferenceStatusDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceStatussDAOLocal#updateReferenceStatus(co.com.directv.sdii.model.pojo.ReferenceStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateReferenceStatus(ReferenceStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateReferenceStatus/ReferenceStatusDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ReferenceStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateReferenceStatus/ReferenceStatusDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceStatussDAOLocal#deleteReferenceStatus(co.com.directv.sdii.model.pojo.ReferenceStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteReferenceStatus(ReferenceStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteReferenceStatus/ReferenceStatusDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ReferenceStatus entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ReferenceStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteReferenceStatus/ReferenceStatusDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceStatussDAOLocal#getReferenceStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ReferenceStatus getReferenceStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getReferenceStatusByID/ReferenceStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceStatus.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ReferenceStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceStatusByID/ReferenceStatusDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceStatussDAOLocal#getAllReferenceStatuss()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ReferenceStatus> getAllReferenceStatuss() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllReferenceStatuss/ReferenceStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceStatus.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllReferenceStatuss/ReferenceStatusDAO ==");
        }
    }



    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReferenceStatusDAOLocal#getReferenceByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ReferenceStatus getReferenceByCode(String refStatusCode)
			throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicio getReferenceByCode/ReferenceStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReferenceStatus.class.getName());
        	stringQuery.append(" entity where entity.refStatusCode = :aCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCode", refStatusCode);

            return (ReferenceStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReferenceByCode/ReferenceStatusDAO ==");
        }
	}

}
