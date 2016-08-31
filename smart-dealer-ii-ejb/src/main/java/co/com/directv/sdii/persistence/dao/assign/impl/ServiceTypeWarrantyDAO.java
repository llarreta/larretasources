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
import co.com.directv.sdii.model.pojo.ServiceType;
import co.com.directv.sdii.model.pojo.ServiceTypeWarranty;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.ServiceTypeWarrantyDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ServiceTypeWarranty
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ServiceTypeWarranty
 * @see co.com.directv.sdii.model.hbm.ServiceTypeWarranty.hbm.xml
 */
@Stateless(name="ServiceTypeWarrantyDAOLocal",mappedName="ejb/ServiceTypeWarrantyDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceTypeWarrantyDAO extends BaseDao implements ServiceTypeWarrantyDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ServiceTypeWarrantyDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.ServiceTypeWarrantyDAOLocal#createServiceTypeWarranty(co.com.directv.sdii.model.pojo.ServiceTypeWarranty)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createServiceTypeWarranty(ServiceTypeWarranty obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createServiceTypeWarranty/ServiceTypeWarrantyDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el ServiceTypeWarranty ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createServiceTypeWarranty/ServiceTypeWarrantyDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.ServiceTypeWarrantyDAOLocal#updateServiceTypeWarranty(co.com.directv.sdii.model.pojo.ServiceTypeWarranty)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateServiceTypeWarranty(ServiceTypeWarranty obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateServiceTypeWarranty/ServiceTypeWarrantyDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el ServiceTypeWarranty ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateServiceTypeWarranty/ServiceTypeWarrantyDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.ServiceTypeWarrantyDAOLocal#deleteServiceTypeWarranty(co.com.directv.sdii.model.pojo.ServiceTypeWarranty)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteServiceTypeWarranty(ServiceTypeWarranty obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteServiceTypeWarranty/ServiceTypeWarrantyDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ServiceTypeWarranty entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el ServiceTypeWarranty ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteServiceTypeWarranty/ServiceTypeWarrantyDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.ServiceTypeWarrantyDAOLocal#getServiceTypeWarrantysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ServiceTypeWarranty getServiceTypeWarrantyByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getServiceTypeWarrantyByID/ServiceTypeWarrantyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceTypeWarranty.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ServiceTypeWarranty) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getServiceTypeWarrantyByID/ServiceTypeWarrantyDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getServiceTypeWarrantyByID/ServiceTypeWarrantyDAO ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.ServiceTypeWarrantyDAOLocal#getAllServiceTypeWarrantys()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ServiceTypeWarranty> getAllServiceTypeWarrantys() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllServiceTypeWarrantys/ServiceTypeWarrantyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceTypeWarranty.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllServiceTypeWarrantys/ServiceTypeWarrantyDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllServiceTypeWarrantys/ServiceTypeWarrantyDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.ServiceTypeWarrantyDAOLocal#getAllServiceTypeWarrantys()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ServiceTypeWarranty> getServiceTypeWarrantiesByServiceType(Long serviceTypeId, Long countryCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getServiceTypeWarrantiesByServiceType/ServiceTypeWarrantyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ServiceTypeWarranty.class.getName());
        	stringQuery.append(" sw ");
        	stringQuery.append("where ");
        	stringQuery.append("sw.serviceType.id = :sTypeId and ");
        	stringQuery.append("sw.country.id = :countryCode");
        	
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("sTypeId", serviceTypeId);
        	query.setLong("countryCode", countryCode);
        	
        	List<ServiceTypeWarranty> serviceTypeWarranties = query.list();
        	
        	return serviceTypeWarranties; 
            
        } catch (Throwable ex){
			log.error("== Error in getServiceTypeWarrantiesByServiceType/ServiceTypeWarrantyDAO==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getServiceTypeWarrantiesByServiceType/ServiceTypeWarrantyDAO ==");
        }
    }
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.ServiceTypeWarrantyDAOLocal#getServiceTypeWarrantiesConfigurationByCountryId(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ServiceTypeWarranty> getServiceTypeWarrantiesConfigurationByCountryId(Long countryId) throws DAOSQLException, DAOServiceException {
    	log.debug("== Inicia getServiceTypeWarrantiesConfiguration/ServiceTypeWarrantyDAO ==");
        Session session = super.getSession();

        try {
        	String mainData = new StringBuffer("from ")
	    		.append(ServiceTypeWarranty.class.getName())
	    		.append(" entity where entity.country.id = :countryId").toString();
			
	    	String complementData = new StringBuffer(" select new ").append(ServiceTypeWarranty.class.getName())
	    		.append("(cct, ").append(countryId).append("L) from ")
	    		.append(ServiceType.class.getName()).append(" cct where cct.id not in ")
	    		.append("(select distinct entity1.serviceType.id from ").append(ServiceTypeWarranty.class.getName())
	    		.append(" entity1 where entity1.country.id = :countryId)").toString();
	    	
	    	Query query = session.createQuery(mainData);
	    	query.setLong("countryId", countryId);
	    	List<ServiceTypeWarranty> mainResult = query.list();
	    	
	    	query = session.createQuery(complementData);
	    	query.setLong("countryId", countryId);
	    	List<ServiceTypeWarranty> complementResult = query.list();
	    	
	    	mainResult.addAll(complementResult);
	    	
	    	return mainResult;
            
        } catch (Throwable ex){
			log.error("== Error in getServiceTypeWarrantiesConfiguration/ServiceTypeWarrantyDAO==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getServiceTypeWarrantiesConfiguration/ServiceTypeWarrantyDAO ==");
        }
    }

}
