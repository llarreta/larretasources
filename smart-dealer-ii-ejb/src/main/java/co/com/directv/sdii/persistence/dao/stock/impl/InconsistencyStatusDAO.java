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
import co.com.directv.sdii.model.pojo.InconsistencyStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.InconsistencyStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad InconsistencyStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.InconsistencyStatus
 * @see co.com.directv.sdii.model.hbm.InconsistencyStatus.hbm.xml
 */
@Stateless(name="InconsistencyStatusDAOLocal",mappedName="ejb/InconsistencyStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class InconsistencyStatusDAO extends BaseDao implements InconsistencyStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(InconsistencyStatusDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.InconsistencyStatussDAOLocal#createInconsistencyStatus(co.com.directv.sdii.model.pojo.InconsistencyStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createInconsistencyStatus(InconsistencyStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createInconsistencyStatus/InconsistencyStatusDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el InconsistencyStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createInconsistencyStatus/InconsistencyStatusDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.InconsistencyStatussDAOLocal#updateInconsistencyStatus(co.com.directv.sdii.model.pojo.InconsistencyStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateInconsistencyStatus(InconsistencyStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateInconsistencyStatus/InconsistencyStatusDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el InconsistencyStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateInconsistencyStatus/InconsistencyStatusDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.InconsistencyStatussDAOLocal#deleteInconsistencyStatus(co.com.directv.sdii.model.pojo.InconsistencyStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteInconsistencyStatus(InconsistencyStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteInconsistencyStatus/InconsistencyStatusDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from InconsistencyStatus entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el InconsistencyStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteInconsistencyStatus/InconsistencyStatusDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.InconsistencyStatussDAOLocal#getInconsistencyStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public InconsistencyStatus getInconsistencyStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getInconsistencyStatusByID/InconsistencyStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(InconsistencyStatus.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (InconsistencyStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getInconsistencyStatusByID/InconsistencyStatusDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.InconsistencyStatussDAOLocal#getAllInconsistencyStatuss()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<InconsistencyStatus> getAllInconsistencyStatuss() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllInconsistencyStatuss/InconsistencyStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(InconsistencyStatus.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllInconsistencyStatuss/InconsistencyStatusDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.InconsistencyStatusDAOLocal#getInconsistencyStatusByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public InconsistencyStatus getInconsistencyStatusByCode(String code)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getInconsistencyStatusByCode/InconsistencyStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(InconsistencyStatus.class.getName());
        	stringQuery.append(" entity where entity.incStatusCode = :aCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCode", code);

            return (InconsistencyStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getInconsistencyStatusByCode/InconsistencyStatusDAO ==");
        }
	}

}
