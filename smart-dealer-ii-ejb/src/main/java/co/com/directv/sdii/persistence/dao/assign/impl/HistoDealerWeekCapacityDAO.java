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
import co.com.directv.sdii.model.pojo.HistoDealerWeekCapacity;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.HistoDealerWeekCapacityDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad HistoDealerWeekCapacity
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.HistoDealerWeekCapacity
 * @see co.com.directv.sdii.model.hbm.HistoDealerWeekCapacity.hbm.xml
 */
@Stateless(name="HistoDealerWeekCapacityDAOLocal",mappedName="ejb/HistoDealerWeekCapacityDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HistoDealerWeekCapacityDAO extends BaseDao implements HistoDealerWeekCapacityDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(HistoDealerWeekCapacityDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerWeekCapacityDAOLocal#createHistoDealerWeekCapacity(co.com.directv.sdii.model.pojo.HistoDealerWeekCapacity)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createHistoDealerWeekCapacity(HistoDealerWeekCapacity obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createHistoDealerWeekCapacity/HistoDealerWeekCapacityDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el HistoDealerWeekCapacity ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createHistoDealerWeekCapacity/HistoDealerWeekCapacityDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerWeekCapacityDAOLocal#updateHistoDealerWeekCapacity(co.com.directv.sdii.model.pojo.HistoDealerWeekCapacity)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateHistoDealerWeekCapacity(HistoDealerWeekCapacity obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateHistoDealerWeekCapacity/HistoDealerWeekCapacityDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el HistoDealerWeekCapacity ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateHistoDealerWeekCapacity/HistoDealerWeekCapacityDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerWeekCapacityDAOLocal#deleteHistoDealerWeekCapacity(co.com.directv.sdii.model.pojo.HistoDealerWeekCapacity)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteHistoDealerWeekCapacity(HistoDealerWeekCapacity obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteHistoDealerWeekCapacity/HistoDealerWeekCapacityDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from HistoDealerWeekCapacity entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el HistoDealerWeekCapacity ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteHistoDealerWeekCapacity/HistoDealerWeekCapacityDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerWeekCapacityDAOLocal#getHistoDealerWeekCapacitysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public HistoDealerWeekCapacity getHistoDealerWeekCapacityByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getHistoDealerWeekCapacityByID/HistoDealerWeekCapacityDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(HistoDealerWeekCapacity.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (HistoDealerWeekCapacity) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getHistoDealerWeekCapacityByID/HistoDealerWeekCapacityDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getHistoDealerWeekCapacityByID/HistoDealerWeekCapacityDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerWeekCapacityDAOLocal#getAllHistoDealerWeekCapacitys()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<HistoDealerWeekCapacity> getAllHistoDealerWeekCapacitys() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllHistoDealerWeekCapacitys/HistoDealerWeekCapacityDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(HistoDealerWeekCapacity.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllHistoDealerWeekCapacitys/HistoDealerWeekCapacityDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllHistoDealerWeekCapacitys/HistoDealerWeekCapacityDAO ==");
        }
    }

}
