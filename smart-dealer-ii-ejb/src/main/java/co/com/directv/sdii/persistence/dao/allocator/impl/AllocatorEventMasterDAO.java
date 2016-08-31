package co.com.directv.sdii.persistence.dao.allocator.impl;

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
import co.com.directv.sdii.model.pojo.AllocatorEventMaster;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.allocator.AllocatorEventMasterDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad AllocatorEventMaster
 * 
 * Fecha de Creación:Feb 10, 2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.AllocatorEventMaster
 * @see co.com.directv.sdii.model.hbm.AllocatorEventMaster.hbm.xml
 */
@Stateless(name="AllocatorEventMasterDAOLocal",mappedName="ejb/AllocatorEventMasterDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AllocatorEventMasterDAO extends BaseDao implements AllocatorEventMasterDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(AllocatorEventMasterDAO.class);    
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createAllocatorEventMaster(AllocatorEventMaster obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio createAllocatorEventMaster/AllocatorEventMasterDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
        	log.error("== Error createAllocatorEventMaster/AllocatorEventMasterDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAllocatorEventMaster/AllocatorEventMasterDAO ==");
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateAllocatorEventMaster(AllocatorEventMaster obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateAllocatorEventMaster/AllocatorEventMasterDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error updateAllocatorEventMaster/AllocatorEventMasterDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateAllocatorEventMaster/AllocatorEventMasterDAO ==");
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteAllocatorEventMaster(AllocatorEventMaster obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteAllocatorEventMaster/AllocatorEventMasterDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from AllocatorEventMaster entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error deleteAllocatorEventMaster/AllocatorEventMasterDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteAllocatorEventMaster/AllocatorEventMasterDAO ==");
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public AllocatorEventMaster getAllocatorEventMasterByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getAllocatorEventMasterByID/AllocatorEventMasterDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AllocatorEventMaster.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (AllocatorEventMaster) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getAllocatorEventMasterByID/AllocatorEventMasterDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllocatorEventMasterByID/AllocatorEventMasterDAO ==");
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public AllocatorEventMaster getLastAllocatorEventMaster(Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getLastAllocatorEventMaster/AllocatorEventMasterDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append(" from " + AllocatorEventMaster.class.getName() + " entity "); 
        	stringQuery.append(" where entity.endDate is null ");
        	stringQuery.append(" and entity.country.id = :aCountryId ");
        	stringQuery.append(" order by entity.endDate desc");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("aCountryId", countryId);
        	query.setMaxResults(1);   	

            return (AllocatorEventMaster) query.uniqueResult();
        } catch (Throwable ex){
			log.error("== Error getLastAllocatorEventMaster/AllocatorEventMasterDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getLastAllocatorEventMaster/AllocatorEventMasterDAO ==");
        }
    }	
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public Long getMaxAllocatorEventMaster() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getMaxAllocatorEventMaster/AllocatorEventMasterDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append(" SELECT NVL(MAX(entity.id),0)+1 ");
        	stringQuery.append(" FROM " + AllocatorEventMaster.class.getName()+ " entity ");
        	stringQuery.append(" WHERE entity.endDate is not null ");
        	Query query = session.createQuery(stringQuery.toString());

            return (Long) query.uniqueResult();
        } catch (Throwable ex){
			log.error("== Error getMaxAllocatorEventMaster/AllocatorEventMasterDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getMaxAllocatorEventMaster/AllocatorEventMasterDAO ==");
        }
    }	
    
}
