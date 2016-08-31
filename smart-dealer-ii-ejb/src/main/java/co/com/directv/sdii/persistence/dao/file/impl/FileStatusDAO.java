package co.com.directv.sdii.persistence.dao.file.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.FileStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.file.FileStatusDAOLocal;

/**
 * A data access object (DAO) providing persistence and search support for
 * FileStatus entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see co.com.directv.sdii.model.pojo.FileStatus
 * @author MyEclipse Persistence Tools
 */

@Stateless(name="FileStatusDAOLocal",mappedName="ejb/FileStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FileStatusDAO extends BaseDao implements FileStatusDAOLocal {
	private static final Log log = LogFactory.getLog(FileStatusDAO.class);
	// property constants
	public static final String CODE = "code";
	public static final String NAME = "name";

	public void save(FileStatus transientInstance)  throws DAOServiceException, DAOSQLException  {
		
        Session session = null ;

        try {
            log.debug("== Inicio save/FileStatusDAO ==");
            session = getSession();
            
            session.save(transientInstance);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error save/FileStatusDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina save/FileStatusDAO ==");
        }				
	}
	
	public FileStatus findByCode(String statusCode) throws DAOServiceException, DAOSQLException  {
        
        Session session = null ;

        try {
        	log.debug("== Inicio FileStatus/FileStatusDAO ==");
        	session = super.getSession();
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append( FileStatus.class.getName() );
        	stringQuery.append(" fstatus where fstatus.code = :aCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCode", statusCode );

            return (FileStatus) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error /FileStatusDAO==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getProcessStatusByID/FileStatusDAO ==");
        }		
	}	
    /*
		log.debug("getting FileStatus instance with id: " + id);
		try {
			FileStatus instance = (FileStatus) getSession().get(
					"co.com.directv.sdii.persistence.dao.file.impl.FileStatus",
					id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed");
			throw re;
		}

     * 
	public void delete(FileStatus persistentInstance) {
		log.debug("deleting FileStatus instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed");
			throw re;
		}
	}

	public FileStatus findById(java.math.BigDecimal id) {
		log.debug("getting FileStatus instance with id: " + id);
		try {
			FileStatus instance = (FileStatus) getSession().get(
					"co.com.directv.sdii.persistence.dao.file.impl.FileStatus",
					id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed");
			throw re;
		}
	}

	public List findByExample(FileStatus instance) {
		log.debug("finding FileStatus instance by example");
		try {
			List results = getSession().createCriteria(
					"co.com.directv.sdii.persistence.dao.file.impl.FileStatus")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed");
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding FileStatus instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from FileStatus as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed");
			throw re;
		}
	}

	public List findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findAll() {
		log.debug("finding all FileStatus instances");
		try {
			String queryString = "from FileStatus";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed");
			throw re;
		}
	}

	public FileStatus merge(FileStatus detachedInstance) {
		log.debug("merging FileStatus instance");
		try {
			FileStatus result = (FileStatus) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed");
			throw re;
		}
	}

	public void attachDirty(FileStatus instance) {
		log.debug("attaching dirty FileStatus instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed");
			throw re;
		}
	}

	public void attachClean(FileStatus instance) {
		log.debug("attaching clean FileStatus instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed");
			throw re;
		}
	}
	*/

	@Override
	@SuppressWarnings("unchecked")
	public List<FileStatus> getAllFileStatus() throws DAOServiceException,
			DAOSQLException {
		Session session = null ;

        try {
        	log.debug("== Inicio getAllFileStatus/FileStatusDAO ==");
        	session = super.getSession();
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append( FileStatus.class.getName() );
        	Query query = session.createQuery(stringQuery.toString());

            return query.list();

        } catch (Throwable ex){
			log.error("== Error /FileStatusDAO==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllFileStatus/FileStatusDAO ==");
        }
	}
}