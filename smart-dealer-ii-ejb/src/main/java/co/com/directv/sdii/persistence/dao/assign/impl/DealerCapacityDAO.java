package co.com.directv.sdii.persistence.dao.assign.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.DealerCapacity;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.DealerCapacityDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad DealerCapacity
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealerCapacity
 * @see co.com.directv.sdii.model.hbm.DealerCapacity.hbm.xml
 */
@Stateless(name="DealerCapacityDAOLocal",mappedName="ejb/DealerCapacityDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerCapacityDAO extends BaseDao implements DealerCapacityDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DealerCapacityDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerCapacityDAOLocal#createDealerCapacity(co.com.directv.sdii.model.pojo.DealerCapacity)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createDealerCapacity(DealerCapacity obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createDealerCapacity/DealerCapacityDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el DealerCapacity ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerCapacity/DealerCapacityDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerCapacityDAOLocal#updateDealerCapacity(co.com.directv.sdii.model.pojo.DealerCapacity)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateDealerCapacity(DealerCapacity obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateDealerCapacity/DealerCapacityDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el DealerCapacity ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerCapacity/DealerCapacityDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerCapacityDAOLocal#deleteDealerCapacity(co.com.directv.sdii.model.pojo.DealerCapacity)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteDealerCapacity(DealerCapacity obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteDealerCapacity/DealerCapacityDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from DealerCapacity entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el DealerCapacity ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerCapacity/DealerCapacityDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerCapacityDAOLocal#getDealerCapacitysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DealerCapacity getDealerCapacityByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDealerCapacityByID/DealerCapacityDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerCapacity.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (DealerCapacity) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getDealerCapacityByID/DealerCapacityDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerCapacityByID/DealerCapacityDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerCapacityDAOLocal#getAllDealerCapacitys()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DealerCapacity> getAllDealerCapacitys() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllDealerCapacitys/DealerCapacityDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerCapacity.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllDealerCapacitys/DealerCapacityDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllDealerCapacitys/DealerCapacityDAO ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerCapacityDAOLocal#getDealerCapacityByCriteria(co.com.directv.sdii.assign.schedule.DealerWorkCapacityCriteria)
     */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public DealerCapacity getDealerCapacityByCriteria(DealerWorkCapacityCriteria criteria) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicio getDealerCapacityByCriteria/DealerCapacityDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerCapacity.class.getName());
        	stringQuery.append(" entity where ");
        	stringQuery.append(" entity.dealer.id = :dealerId ");
        	stringQuery.append(" and entity.country.id = :countryId ");
        	stringQuery.append(" and entity.serviceHour.id = :serviceHourId ");
        	stringQuery.append(" and entity.serviceSuperCategory.id = :serviceSuperCategoryId ");
        	stringQuery.append(" and entity.capacityDate between :capacityDateInit and :capacityDateEnd ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerId", criteria.getDealerId());
            query.setLong("countryId", criteria.getCountryId());
            query.setLong("serviceHourId", criteria.getServiceHourId());
            query.setLong("serviceSuperCategoryId", criteria.getSuperCategoryServiceId());
            query.setTimestamp("capacityDateInit", new Timestamp( UtilsBusiness.dateTo12am( criteria.getCapacityDate() ).getTime() ));
            query.setTimestamp("capacityDateEnd", new Timestamp( UtilsBusiness.dateTo12pm( criteria.getCapacityDate() ).getTime() ));
            
            List<DealerCapacity> result = query.list();
            if(result.isEmpty()){
            	return null;
            }
            DealerCapacity obj = result.get(0);
            return obj;
        } catch (Throwable ex){
			log.error("== Error getDealerCapacityByCriteria/DealerCapacityDAO  ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerCapacityByCriteria/DealerCapacityDAO ==");
        }
	}

}
