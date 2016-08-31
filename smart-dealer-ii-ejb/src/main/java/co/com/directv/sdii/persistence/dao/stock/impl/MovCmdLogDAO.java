package co.com.directv.sdii.persistence.dao.stock.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.MovCmdLog;
import co.com.directv.sdii.model.pojo.MovCmdQueue;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.MovCmdLogDAOLocal;

/**
 * A data access object (DAO) providing persistence and search support for
 * MovCmdLog entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see co.com.directv.sdii.persistence.MovCmdLog
 * @author MyEclipse Persistence Tools
 */

@Stateless(name="MovCmdLogDAOLocal",mappedName="ejb/MovCmdLogDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MovCmdLogDAO extends BaseDao implements MovCmdLogDAOLocal {
	private static final Logger log = UtilsBusiness.getLog4J(MovCmdLogDAO.class);
	// property constants
	public static final String DESCRIPTION = "description";
	public static final String CREATION_DATE = "creationDate";

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdLogDAOLocal#save(co.com.directv.sdii.model.pojo.MovCmdLog)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void save(MovCmdLog transientInstance) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio save/MovCmdLogDAO ==");
		try {
			getSession().save(transientInstance);
		} catch (Throwable re) {
			log.error("== Error save/MovCmdLogDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina save/MovCmdLogDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdLogDAOLocal#delete(co.com.directv.sdii.model.pojo.MovCmdLog)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(MovCmdLog persistentInstance) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio delete/MovCmdLogDAO ==");
		try {
			getSession().delete(persistentInstance);
		} catch (Throwable re) {
			log.error("== Error delete/MovCmdLogDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina delete/MovCmdLogDAO ==");
		}
	}
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteMovCmdLogBySerializedElementID(Long serializedElementID) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio deleteMovCmdLogBySerializedElementID/MovCmdLogDAO ==");
        Session session = getSession();
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append(" delete ");
			stringQuery.append(" from "+MovCmdLog.class.getName()+" entity ");
			stringQuery.append(" where exists(select 1 ");
			stringQuery.append(" 				from  "+MovCmdQueue.class.getName()+" mcq ");
			stringQuery.append(" 			   where mcq.serialized.elementId = :serializedElementID "); 
			stringQuery.append(" 			         and mcq.id = entity.movCmdQueue.id ) ");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("serializedElementID", serializedElementID);
			query.executeUpdate();
			super.doFlush(session);
		} catch (Throwable re) {
			log.error("== Error deleteMovCmdLogBySerializedElementID/MovCmdLogDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina deleteMovCmdLogBySerializedElementID/MovCmdLogDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdLogDAOLocal#findById(java.math.BigDecimal)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MovCmdLog findById(java.math.BigDecimal id) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio findById/MovCmdLogDAO ==");
		try {
			MovCmdLog instance = (MovCmdLog) getSession().get("co.com.directv.sdii.model.pojo.MovCmdLog", id);
			return instance;
		} catch (Throwable re) {
			log.error("== Error findById/MovCmdLogDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findById/MovCmdLogDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdLogDAOLocal#findByExample(co.com.directv.sdii.model.pojo.MovCmdLog)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdLog> findByExample(MovCmdLog instance) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio findByExample/MovCmdLogDAO ==");
		try {
			List<MovCmdLog> results = getSession().createCriteria(
					"co.com.directv.sdii.model.pojo.MovCmdLog").add(
					Example.create(instance)).list();
			return results;
		} catch (Throwable re) {
			log.error("== Error findByExample/MovCmdLogDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findByExample/MovCmdLogDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdLogDAOLocal#findByProperty(java.lang.String, java.lang.Object)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdLog> findByProperty(String propertyName, Object value) throws DAOServiceException, DAOSQLException  {
    	log.debug("== Inicio findByProperty/MovCmdLogDAO ==");
		try {
			String queryString = "from MovCmdLog as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (Throwable re) {
			log.error("== Error findByProperty/MovCmdLogDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findByProperty/MovCmdLogDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdLogDAOLocal#findByDescription(java.lang.Object)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdLog> findByDescription(Object description) throws DAOServiceException, DAOSQLException  {
		return findByProperty(DESCRIPTION, description);
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdLogDAOLocal#findByCreationDate(java.lang.Object)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdLog> findByCreationDate(Object creationDate) throws DAOServiceException, DAOSQLException  {
		return findByProperty(CREATION_DATE, creationDate);
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdLogDAOLocal#findAll()
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdLog> findAll() throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio findAll/MovCmdLogDAO ==");
		try {
			String queryString = "from MovCmdLog";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (Throwable re) {
			log.error("== Error findAll/MovCmdLogDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findAll/MovCmdLogDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdLogDAOLocal#merge(co.com.directv.sdii.model.pojo.MovCmdLog)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MovCmdLog merge(MovCmdLog detachedInstance) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio merge/MovCmdLogDAO ==");
		try {
			MovCmdLog result = (MovCmdLog) getSession().merge(detachedInstance);
			return result;
		} catch (Throwable re) {
			log.error("== Error merge/MovCmdLogDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina merge/MovCmdLogDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdLogDAOLocal#attachDirty(co.com.directv.sdii.model.pojo.MovCmdLog)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void attachDirty(MovCmdLog instance) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio attachDirty/MovCmdLogDAO ==");
		try {
			getSession().saveOrUpdate(instance);
		} catch (Throwable re) {
			log.error("== Error attachDirty/MovCmdLogDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina attachDirty/MovCmdLogDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdLogDAOLocal#attachClean(co.com.directv.sdii.model.pojo.MovCmdLog)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void attachClean(MovCmdLog instance) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio attachClean/MovCmdLogDAO ==");
		try {
			getSession().lock(instance, LockMode.NONE);
		} catch (Throwable re) {
			log.error("== Error attachClean/MovCmdLogDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina attachClean/MovCmdLogDAO ==");
		}
	}

	@Override
	public List<MovCmdLog> getLogByMovementId(Long movCmdQueueId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getLogByMovementId/MovCmdLogDAO ==");
		try {
			
			StringBuffer queryString = new StringBuffer();
			queryString.append("select new "+ MovCmdLog.class.getName()+" ( ");
			queryString.append("model.id, model.description, model.creationDate)");
			queryString.append("from MovCmdLog as model ");
			queryString.append("where model.movCmdQueue.id = :movCmdQueueId ");
			queryString.append("order by model.id desc");
			Query queryObject = getSession().createQuery(queryString.toString());
			queryObject.setLong("movCmdQueueId", movCmdQueueId);
			return queryObject.list();
		} catch (Throwable re) {
			log.error("== Error getLogByMovementId/MovCmdLogDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina getLogByMovementId/MovCmdLogDAO ==");
		}
		
	}
}