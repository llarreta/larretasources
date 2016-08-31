package co.com.directv.sdii.persistence.dao.core.impl;

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
import co.com.directv.sdii.model.pojo.WoLoad;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.core.WoLoadDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad WoLoad
 * 
 * Fecha de Creación:Feb 10, 2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WoLoad
 * @see co.com.directv.sdii.model.hbm.WoLoad.hbm.xml
 */
@Stateless(name="WoLoadDAOLocal",mappedName="ejb/WoLoadDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WoLoadDAO extends BaseDao implements WoLoadDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WoLoadDAO.class);    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.WoLoadDAOLocal#createWoLoad(co.com.directv.sdii.model.pojo.WoLoad)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWoLoad(WoLoad obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio createWoLoad/WoLoadDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el WoLoad ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWoLoad/WoLoadDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see persistence.dao.core.WoLoadDAOLocal#updateWoLoad(co.com.directv.sdii.model.pojo.WoLoad)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWoLoad(WoLoad obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateWoLoad/WoLoadDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el WoLoad ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWoLoad/WoLoadDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see persistence.dao.core.WoLoadDAOLocal#deleteWoLoad(co.com.directv.sdii.model.pojo.WoLoad)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWoLoad(WoLoad obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteWoLoad/WoLoadDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from WoLoad entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el WoLoad ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteWoLoad/WoLoadDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see persistence.dao.core.WoLoadDAOLocal#getWoLoadsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public WoLoad getWoLoadByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWoLoadByID/WoLoadDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WoLoad.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (WoLoad) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWoLoadByID/WoLoadDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see persistence.dao.core.WoLoadDAOLocal#getWoLoadsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public WoLoad getLastWoLoad(Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getLastWoLoad/WoLoadDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WoLoad.class.getName());
        	stringQuery.append(" entity where entity.initDate is not null ");        	
        	stringQuery.append(" and entity.country.id = :pCountryId");
        	stringQuery.append(" order by entity.initDate desc");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setMaxResults(1);   	
        	query.setLong("pCountryId", countryId);

            return (WoLoad) query.uniqueResult();
        } catch (Throwable ex){
			log.error("== Error en la operacion getLastWoLoad/WoLoadDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getLastWoLoad/WoLoadDAO ==");
        }
    }	
}
