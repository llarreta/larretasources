package co.com.directv.sdii.persistence.dao.allocator.impl;

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
import co.com.directv.sdii.model.pojo.AllocatorEvent;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.allocator.AllocatorEventDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad AllocatorEvent
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.AllocatorEvent
 * @see co.com.directv.sdii.model.hbm.AllocatorEvent.hbm.xml
 */
@Stateless(name="AllocatorEventDAOLocal",mappedName="ejb/AllocatorEventDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AllocatorEventDAO extends BaseDao implements AllocatorEventDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(AllocatorEventDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.allocator.AllocatorEventDAOLocal#createAllocatorEvent(co.com.directv.sdii.model.pojo.AllocatorEvent)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createAllocatorEvent(AllocatorEvent obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createAllocatorEvent/AllocatorEventDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el AllocatorEvent ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAllocatorEvent/AllocatorEventDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.allocator.AllocatorEventDAOLocal#updateAllocatorEvent(co.com.directv.sdii.model.pojo.AllocatorEvent)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateAllocatorEvent(AllocatorEvent obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateAllocatorEvent/AllocatorEventDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el AllocatorEvent ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateAllocatorEvent/AllocatorEventDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.allocator.AllocatorEventDAOLocal#deleteAllocatorEvent(co.com.directv.sdii.model.pojo.AllocatorEvent)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteAllocatorEvent(AllocatorEvent obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteAllocatorEvent/AllocatorEventDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from AllocatorEvent entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el AllocatorEvent ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteAllocatorEvent/AllocatorEventDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.allocator.AllocatorEventDAOLocal#getAllocatorEventsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public AllocatorEvent getAllocatorEventByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getAllocatorEventByID/AllocatorEventDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AllocatorEvent.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (AllocatorEvent) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getAllocatorEventByID/AllocatorEventDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllocatorEventByID/AllocatorEventDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.allocator.AllocatorEventDAOLocal#getAllAllocatorEvents()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AllocatorEvent> getAllAllocatorEvents() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllAllocatorEvents/AllocatorEventDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AllocatorEvent.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllAllocatorEvents/AllocatorEventDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllAllocatorEvents/AllocatorEventDAO ==");
        }
    }

}
