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
import co.com.directv.sdii.model.pojo.DealerWeekCapacity;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.DealerWeekCapacityDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad DealerWeekCapacity
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealerWeekCapacity
 * @see co.com.directv.sdii.model.hbm.DealerWeekCapacity.hbm.xml
 */
@Stateless(name="DealerWeekCapacityDAOLocal",mappedName="ejb/DealerWeekCapacityDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerWeekCapacityDAO extends BaseDao implements DealerWeekCapacityDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DealerWeekCapacityDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerWeekCapacityDAOLocal#createDealerWeekCapacity(co.com.directv.sdii.model.pojo.DealerWeekCapacity)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createDealerWeekCapacity(DealerWeekCapacity obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createDealerWeekCapacity/DealerWeekCapacityDAO ==");
        saveAuditEnvers(obj);
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el DealerWeekCapacity ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerWeekCapacity/DealerWeekCapacityDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerWeekCapacityDAOLocal#updateDealerWeekCapacity(co.com.directv.sdii.model.pojo.DealerWeekCapacity)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateDealerWeekCapacity(DealerWeekCapacity obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateDealerWeekCapacity/DealerWeekCapacityDAO ==");
        saveAuditEnvers(obj);
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el DealerWeekCapacity ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerWeekCapacity/DealerWeekCapacityDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerWeekCapacityDAOLocal#deleteDealerWeekCapacity(co.com.directv.sdii.model.pojo.DealerWeekCapacity)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteDealerWeekCapacity(DealerWeekCapacity obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteDealerWeekCapacity/DealerWeekCapacityDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from DealerWeekCapacity entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el DealerWeekCapacity ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerWeekCapacity/DealerWeekCapacityDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerWeekCapacityDAOLocal#getDealerWeekCapacitysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DealerWeekCapacity getDealerWeekCapacityByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDealerWeekCapacityByID/DealerWeekCapacityDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerWeekCapacity.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (DealerWeekCapacity) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getDealerWeekCapacityByID/DealerWeekCapacityDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerWeekCapacityByID/DealerWeekCapacityDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerWeekCapacityDAOLocal#getAllDealerWeekCapacitys()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DealerWeekCapacity> getAllDealerWeekCapacitys() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllDealerWeekCapacitys/DealerWeekCapacityDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerWeekCapacity.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllDealerWeekCapacitys/DealerWeekCapacityDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllDealerWeekCapacitys/DealerWeekCapacityDAO ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerWeekCapacityDAOLocal#getWeekCapacityByDealerIdAndServiceHourAndServiceSuperCat(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DealerWeekCapacity getWeekCapacityByDealerIdAndServiceHourAndServiceSuperCat(
			Long countryId, Long dealerId, Long serviceHourId,
			Long serviceSuperCategoryId) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getDealerWeekCapacityByID/DealerWeekCapacityDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerWeekCapacity.class.getName());
        	stringQuery.append(" entity where entity.country.id = :aCountryId and entity.dealer.id = :aDealerId and entity.serviceHour.id = :aShId and entity.serviceSuperCategory.id = :aServSCId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aCountryId", countryId);
            query.setLong("aDealerId", dealerId);
            query.setLong("aShId", serviceHourId);
            query.setLong("aServSCId", serviceSuperCategoryId);

            return (DealerWeekCapacity) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getDealerWeekCapacityByID/DealerWeekCapacityDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerWeekCapacityByID/DealerWeekCapacityDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.DealerWeekCapacityDAOLocal#getDealerWeekCapacityByDealerId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerWeekCapacity> getDealerWeekCapacityByDealerId(Long dealerId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllDealerWeekCapacitys/DealerWeekCapacityDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerWeekCapacity.class.getName());
        	stringQuery.append(" entity where entity.dealer.id = :aDealerId");
        	stringQuery.append(" and entity.serviceHour.serviceHourStatus.serviceHoursStatusCode= :aServiceHoursStatusCode "); 
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aDealerId", dealerId);
            query.setString("aServiceHoursStatusCode", CodesBusinessEntityEnum.SERVICE_HOUR_STATUS_ACTIVE.getCodeEntity());
            
            return query.list();
        } catch (Throwable ex){
			log.error("== Error getAllDealerWeekCapacitys/DealerWeekCapacityDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllDealerWeekCapacitys/DealerWeekCapacityDAO ==");
        }
	}
	
	private void saveAuditEnvers(DealerWeekCapacity obj){
    	if(obj!=null){
    		if(obj.getDealer()!=null){
    			obj.setDealerId(obj.getDealer().getId());
    		}
    		if(obj.getUser()!=null){
    			obj.setUserId(obj.getUser().getId());
    		}
    		if(obj.getServiceSuperCategory()!=null){
    			obj.setServiceSuperCategoryId(obj.getServiceSuperCategory().getId());
    		}
    		if(obj.getCountry()!=null){
    			obj.setCountryId(obj.getCountry().getId());
    		}
    		if(obj.getServiceHour()!=null){
    			obj.setServiceHourId(obj.getServiceHour().getId());
    		}
    	}
    }

}
