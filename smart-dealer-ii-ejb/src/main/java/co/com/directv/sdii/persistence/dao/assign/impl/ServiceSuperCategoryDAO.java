package co.com.directv.sdii.persistence.dao.assign.impl;

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
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.ServiceSuperCategory;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.ServiceSuperCategoryDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ServiceSuperCategory
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ServiceSuperCategory
 * @see co.com.directv.sdii.model.hbm.ServiceSuperCategory.hbm.xml
 */
@Stateless(name="ServiceSuperCategoryDAOLocal",mappedName="ejb/ServiceSuperCategoryDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceSuperCategoryDAO extends BaseDao implements ServiceSuperCategoryDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ServiceSuperCategoryDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.ServiceSuperCategoryDAOLocal#createServiceSuperCategory(co.com.directv.sdii.model.pojo.ServiceSuperCategory)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createServiceSuperCategory(ServiceSuperCategory obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createServiceSuperCategory/ServiceSuperCategoryDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el ServiceSuperCategory ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createServiceSuperCategory/ServiceSuperCategoryDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.ServiceSuperCategoryDAOLocal#updateServiceSuperCategory(co.com.directv.sdii.model.pojo.ServiceSuperCategory)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateServiceSuperCategory(ServiceSuperCategory obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateServiceSuperCategory/ServiceSuperCategoryDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el ServiceSuperCategory ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateServiceSuperCategory/ServiceSuperCategoryDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.ServiceSuperCategoryDAOLocal#deleteServiceSuperCategory(co.com.directv.sdii.model.pojo.ServiceSuperCategory)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteServiceSuperCategory(ServiceSuperCategory obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteServiceSuperCategory/ServiceSuperCategoryDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ServiceSuperCategory entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el ServiceSuperCategory ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteServiceSuperCategory/ServiceSuperCategoryDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.ServiceSuperCategoryDAOLocal#getServiceSuperCategorysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ServiceSuperCategory getServiceSuperCategoryByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getServiceSuperCategoryByID/ServiceSuperCategoryDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceSuperCategory.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ServiceSuperCategory) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getServiceSuperCategoryByID/ServiceSuperCategoryDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getServiceSuperCategoryByID/ServiceSuperCategoryDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.ServiceSuperCategoryDAOLocal#getAllServiceSuperCategorys()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ServiceSuperCategory> getAllServiceSuperCategorys() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllServiceSuperCategorys/ServiceSuperCategoryDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceSuperCategory.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllServiceSuperCategorys/ServiceSuperCategoryDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllServiceSuperCategorys/ServiceSuperCategoryDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.ServiceSuperCategoryDAOLocal#getServiceSuperCategoryByServiceCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ServiceSuperCategory getServiceSuperCategoryByServiceCode(
			String serviceCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getServiceSuperCategoryByID/ServiceSuperCategoryDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select ser.serviceCategory.serviceType.superCategory from ");
        	stringQuery.append(Service.class.getName());
        	stringQuery.append(" ser where ser.serviceCode = :aServiceCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aServiceCode", serviceCode);

            return (ServiceSuperCategory) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getServiceSuperCategoryByID/ServiceSuperCategoryDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getServiceSuperCategoryByID/ServiceSuperCategoryDAO ==");
        }
	}

}
