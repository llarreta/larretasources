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

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ServiceAreWarranty;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.ServiceAreWarrantyDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ServiceAreWarranty
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ServiceAreWarranty
 * @see co.com.directv.sdii.model.hbm.ServiceAreWarranty.hbm.xml
 */
@Stateless(name="ServiceAreWarrantyDAOLocal",mappedName="ejb/ServiceAreWarrantyDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceAreWarrantyDAO extends BaseDao implements ServiceAreWarrantyDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ServiceAreWarrantyDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.ServiceAreWarrantyDAOLocal#createServiceAreWarranty(co.com.directv.sdii.model.pojo.ServiceAreWarranty)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createServiceAreWarranty(ServiceAreWarranty obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createServiceAreWarranty/ServiceAreWarrantyDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el ServiceAreWarranty ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createServiceAreWarranty/ServiceAreWarrantyDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.ServiceAreWarrantyDAOLocal#updateServiceAreWarranty(co.com.directv.sdii.model.pojo.ServiceAreWarranty)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateServiceAreWarranty(ServiceAreWarranty obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateServiceAreWarranty/ServiceAreWarrantyDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el ServiceAreWarranty ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateServiceAreWarranty/ServiceAreWarrantyDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.ServiceAreWarrantyDAOLocal#deleteServiceAreWarranty(co.com.directv.sdii.model.pojo.ServiceAreWarranty)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteServiceAreWarranty(ServiceAreWarranty obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteServiceAreWarranty/ServiceAreWarrantyDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ServiceAreWarranty entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el ServiceAreWarranty ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteServiceAreWarranty/ServiceAreWarrantyDAO ==");
        }
    }
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.ServiceAreWarrantyDAOLocal#deleteByServiceTypeWarrantyId(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteByServiceTypeWarrantyId(Long serviceTypeWarrantyId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio deleteByServiceWarrantyTypeId/ServiceAreWarrantyDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ServiceAreWarranty entity where entity.serviceTypeWarranty.id = :serviceTypeWarrantyId");
            query.setLong("serviceTypeWarrantyId", serviceTypeWarrantyId);
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error ejecutando deleteByServiceWarrantyTypeId/ServiceAreWarrantyDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteByServiceWarrantyTypeId/ServiceAreWarrantyDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.ServiceAreWarrantyDAOLocal#getServiceAreWarrantysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ServiceAreWarranty getServiceAreWarrantyByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getServiceAreWarrantyByID/ServiceAreWarrantyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceAreWarranty.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ServiceAreWarranty) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getServiceAreWarrantyByID/ServiceAreWarrantyDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.ServiceAreWarrantyDAOLocal#getAllServiceAreWarrantys()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ServiceAreWarranty> getAllServiceAreWarrantys() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllServiceAreWarrantys/ServiceAreWarrantyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceAreWarranty.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllServiceAreWarrantys/ServiceAreWarrantyDAO ==");
        }
    }


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.ServiceAreWarrantyDAOLocal#getServiceAreWarrantyByServiceWarranty(java.lang.Long, java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ServiceAreWarranty> getServiceAreWarrantyByServiceWarranty(Long serviceWarrantyId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getServiceAreWarrantyByServiceWarranty/ServiceAreWarrantyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceAreWarranty.class.getName());
        	stringQuery.append(" saw ");
        	stringQuery.append("where ");
        	stringQuery.append("saw.serviceTypeWarranty.id = :sWarrantyId ");
        	
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("sWarrantyId", serviceWarrantyId);
			List<ServiceAreWarranty> result = query.list();
            
            return result;
        } catch (Throwable ex){
        	log.error("== Error en getServiceAreWarrantyByServiceWarranty ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getServiceAreWarrantyByServiceWarranty/ServiceAreWarrantyDAO ==");
        }		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.ServiceAreWarrantyDAOLocal#getServiceAreWarrantiesByServiceTypeWarrantyId(java.lang.Long)
	 */
	@Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ServiceAreWarranty> getServiceAreWarrantiesByServiceTypeWarrantyId(Long serviceTypeWarrantyId) throws DAOSQLException, DAOServiceException {
    	log.debug("== Inicia getServiceAreWarrantiesByServiceTypeWarrantyId/ServiceAreWarrantyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer("select new ServiceAreWarranty(sw.serviceTypeWarranty.id, sw.service.id, sw.user.id) from ")
        	.append(ServiceAreWarranty.class.getName())
        	.append(" sw where sw.serviceTypeWarranty.id = :serviceTypeWarrantyId")
        	.append(" and sw.service.serviceStatus.serviceStatusCode = '")
        	.append(CodesBusinessEntityEnum.CODIGO_SERVICE_STATUS_ACTIVE.getCodeEntity()).append("'");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("serviceTypeWarrantyId", serviceTypeWarrantyId);
        	
        	List<ServiceAreWarranty> serviceTypeWarranties = query.list();
        	
        	return serviceTypeWarranties; 
            
        } catch (Throwable ex){
			log.error("== Error in getServiceAreWarrantiesByServiceTypeWarrantyId/ServiceAreWarrantyDAO==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getServiceAreWarrantiesByServiceTypeWarrantyId/ServiceAreWarrantyDAO ==");
        }
    }
	
}
