package co.com.directv.sdii.persistence.dao.bb.impl;

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
import co.com.directv.sdii.model.pojo.ServiceProvider;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.bb.ServiceProviderDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ServiceProvider
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ServiceProvider
 * @see co.com.directv.sdii.model.hbm.ServiceProvider.hbm.xml
 */
@Stateless(name="ServiceProviderDAOLocal",mappedName="ejb/ServiceProviderDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceProviderDAO extends BaseDao implements ServiceProviderDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ServiceProviderDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.ServiceProviderDAOLocal#createServiceProvider(co.com.directv.sdii.model.pojo.ServiceProvider)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createServiceProvider(ServiceProvider obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createServiceProvider/ServiceProviderDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el ServiceProvider ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createServiceProvider/ServiceProviderDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.ServiceProviderDAOLocal#updateServiceProvider(co.com.directv.sdii.model.pojo.ServiceProvider)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateServiceProvider(ServiceProvider obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateServiceProvider/ServiceProviderDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el ServiceProvider ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateServiceProvider/ServiceProviderDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.ServiceProviderDAOLocal#deleteServiceProvider(co.com.directv.sdii.model.pojo.ServiceProvider)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteServiceProvider(ServiceProvider obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteServiceProvider/ServiceProviderDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ServiceProvider entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el ServiceProvider ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteServiceProvider/ServiceProviderDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.ServiceProviderDAOLocal#getServiceProvidersByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ServiceProvider getServiceProviderByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getServiceProviderByID/ServiceProviderDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceProvider.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ServiceProvider) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getServiceProviderByID/ServiceProviderDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getServiceProviderByID/ServiceProviderDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.bb.ServiceProviderDAOLocal#getAllServiceProviders()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ServiceProvider> getAllServiceProviders() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllServiceProviders/ServiceProviderDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceProvider.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllServiceProviders/ServiceProviderDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllServiceProviders/ServiceProviderDAO ==");
        }
    }

}
