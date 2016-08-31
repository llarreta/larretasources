package co.com.directv.sdii.persistence.dao.core.impl;

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
import co.com.directv.sdii.model.pojo.IbsContactReason;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.core.IbsContactReasonDAOLocal;

/**
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad IbsContactReasonDAO 
 * 
 * Fecha de Creación: 29/11/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="IbsContactReasonDAOLocal",mappedName="ejb/IbsContactReasonDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class IbsContactReasonDAO extends BaseDao implements IbsContactReasonDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(IbsContactReasonDAO.class);
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.IbsContactReasonDAOLocal#createIbsContactReason(co.com.directv.sdii.model.pojo.IbsContactReason)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createIbsContactReason(IbsContactReason obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createIbsContactReason/IbsContactReasonDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el IbsContactReason ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createIbsContactReason/IbsContactReasonDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.IbsContactReasonDAOLocal#updateIbsContactReason(co.com.directv.sdii.model.pojo.IbsContactReason)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateIbsContactReason(IbsContactReason obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateIbsContactReason/IbsContactReasonDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el IbsContactReason ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateIbsContactReason/IbsContactReasonDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.IbsContactReasonDAOLocal#deleteIbsContactReason(co.com.directv.sdii.model.pojo.IbsContactReason)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteIbsContactReason(IbsContactReason obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteIbsContactReason/IbsContactReasonDAO ==");
        Session session = super.getSession();
        try {
            
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append(" delete ");
        	stringQuery.append("   from " + IbsContactReason.class.getName() + " entity ");
        	stringQuery.append("  where entity.id = :anEntityId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el IbsContactReason ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteIbsContactReason/IbsContactReasonDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.IbsContactReasonDAOLocal#getIbsContactReasonByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public IbsContactReason getIbsContactReasonByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getIbsContactReasonByID/IbsContactReasonDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("  from " + IbsContactReason.class.getName() + " entity ");
        	stringQuery.append(" where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (IbsContactReason) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getIbsContactReasonByID/IbsContactReasonDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.IbsContactReasonDAOLocal#getIbsContactReasonByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public IbsContactReason getIbsContactReasonByCode(String code,Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getIbsContactReasonByCode/IbsContactReasonDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("  from " + IbsContactReason.class.getName() + " entity ");
        	stringQuery.append(" where entity.code = :aCode ");
        	stringQuery.append("   and entity.country.id = :aCountryId ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCode", code);
            query.setLong("aCountryId", countryId);

            return (IbsContactReason) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getIbsContactReasonByCode/IbsContactReasonDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.IbsContactReasonDAOLocal#getAllIbsContactReasons(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<IbsContactReason> getAllIbsContactReasons(Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllIbsContactReasons/IbsContactReasonDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("  from " + IbsContactReason.class.getName() + " entity ");
        	stringQuery.append(" where  entity.country.id= :aCountryId");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aCountryId", countryId);
            
            return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllIbsContactReasons/IbsContactReasonDAO ==");
        }
    }

}
