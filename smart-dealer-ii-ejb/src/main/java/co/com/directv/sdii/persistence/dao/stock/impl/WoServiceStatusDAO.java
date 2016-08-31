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
import co.com.directv.sdii.model.pojo.WoServiceStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.WoServiceStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad WoServiceStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WoServiceStatus
 * @see co.com.directv.sdii.model.hbm.WoServiceStatus.hbm.xml
 */
@Stateless(name="WoServiceStatusDAOLocal",mappedName="ejb/WoServiceStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WoServiceStatusDAO extends BaseDao implements WoServiceStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WoServiceStatusDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WoServiceStatusDAOLocal#createWoServiceStatus(co.com.directv.sdii.model.pojo.WoServiceStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWoServiceStatus(WoServiceStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWoServiceStatus/WoServiceStatusDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el WoServiceStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWoServiceStatus/WoServiceStatusDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WoServiceStatusDAOLocal#updateWoServiceStatus(co.com.directv.sdii.model.pojo.WoServiceStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWoServiceStatus(WoServiceStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateWoServiceStatus/WoServiceStatusDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el WoServiceStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWoServiceStatus/WoServiceStatusDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WoServiceStatusDAOLocal#deleteWoServiceStatus(co.com.directv.sdii.model.pojo.WoServiceStatus)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWoServiceStatus(WoServiceStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteWoServiceStatus/WoServiceStatusDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from WoServiceStatus entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el WoServiceStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteWoServiceStatus/WoServiceStatusDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WoServiceStatusDAOLocal#getWoServiceStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public WoServiceStatus getWoServiceStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWoServiceStatusByID/WoServiceStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WoServiceStatus.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (WoServiceStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWoServiceStatusByID/WoServiceStatusDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.WoServiceStatusDAOLocal#getAllWoServiceStatuss()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<WoServiceStatus> getAllWoServiceStatuss() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllWoServiceStatuss/WoServiceStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WoServiceStatus.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllWoServiceStatuss/WoServiceStatusDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WoServiceStatusDAOLocal#getWoServiceStatusByCode(java.lang.String)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WoServiceStatus getWoServiceStatusByCode(String codeEntity)
			throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicio getWoServiceStatusByID/WoServiceStatusDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WoServiceStatus.class.getName());
        	stringQuery.append(" entity where entity.statusCode = :aWoServStatusCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aWoServStatusCode", codeEntity);

            return (WoServiceStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWoServiceStatusByID/WoServiceStatusDAO ==");
        }
	}

}
