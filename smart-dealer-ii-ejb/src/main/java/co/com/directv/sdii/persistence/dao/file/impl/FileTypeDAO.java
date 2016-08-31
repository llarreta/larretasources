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
import co.com.directv.sdii.model.pojo.FileType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.file.FileTypeDAOLocal;

/**
 * A data access object (DAO) providing persistence and search support for
 * FileType entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see co.com.directv.sdii.model.pojo.FileType
 * @author MyEclipse Persistence Tools
 */

@Stateless(name="FileTypeDAOLocal",mappedName="ejb/FileTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FileTypeDAO extends BaseDao implements FileTypeDAOLocal {
	
	private static final Log log = LogFactory.getLog(FileTypeDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String CODE = "code";
	public static final String LOCATION = "location";
	
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.file.FileTypeDAOLocal#save(co.com.directv.sdii.model.pojo.FileType)
     */
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void save(FileType transientInstance) throws DAOServiceException, DAOSQLException  {

        Session session = null ;

        try {
            log.debug("== Inicio save/FileTypeDAO ==");
            session = getSession();
            
            session.save(transientInstance);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error save/FileTypeDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina save/FileTypeDAO ==");
        }		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.file.FileTypeDAOLocal#findById(java.math.BigDecimal)
	 */
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public FileType findById(Long id) throws DAOServiceException, DAOSQLException {
        
        Session session = null ;

		try {
			log.debug("== Inicio findById/FileTypeDAO ==");
			session = getSession();
			
			FileType instance = (FileType) session.get("co.com.directv.sdii.persistence.dao.file.impl.FileType",id);
			return instance;
			
		} catch (Throwable ex) {
            log.debug("== Error findById/FileTypeDAO ==");
			throw  this.manageException(ex);
		} finally {
	            log.debug("== Termina findById/FileTypeDAO ==");
	    }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.file.FileTypeDAOLocal#findByCode(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FileType findByCode(String strTypeCode) throws DAOServiceException, DAOSQLException {
        Session session = null ;

        try {
        	log.debug("== Inicio findByCode/FileTypeDAO ==");
        	session = getSession();
        	StringBuffer stringQuery = new StringBuffer("from ")
        		.append(FileType.class.getName())
        		.append(" where code = :strTypeCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("strTypeCode", strTypeCode);

            return (FileType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error findByCode/FileTypeDAO==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina findByCode/FileTypeDAO ==");
        }		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.file.FileTypeDAOLocal#getAllFileTypes()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<FileType> getAllFileTypes() throws DAOServiceException,
			DAOSQLException {
		Session session = null ;

        try {
        	log.debug("== Inicio getAllFileTypes/FileTypeDAO ==");
        	session = getSession();
        	StringBuffer stringQuery = new StringBuffer("from ")
        		.append(FileType.class.getName());
        	Query query = session.createQuery(stringQuery.toString());

            return query.list();

        } catch (Throwable ex){
			log.error("== Error getAllFileTypes/FileTypeDAO==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllFileTypes/FileTypeDAO ==");
        }
	}	
	
	/**
	public void delete(FileType persistentInstance) {
		log.debug("deleting FileType instance");
		try {
			//getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed");
			throw re;
		}
	}
	
	
	public List findByExample(FileType instance) {
		log.debug("finding FileType instance by example");
		try {
			List results = null ;
		    
			// 	getSession().createCriteria(
			//		"co.com.directv.sdii.persistence.dao.file.impl.FileType")
			//		.add(Example.create(instance)).list();
			
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed");
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding FileType instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from FileType as model where model."
					+ propertyName + "= ?";
			Query queryObject = null ; 
				// getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed");
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List findByLocation(Object location) {
		return findByProperty(LOCATION, location);
	}

	public List findAll() {
		log.debug("finding all FileType instances");
		try {
			String queryString = "from FileType";
			Query queryObject = null ; 
				// getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed");
			throw re;
		}
	}

	public FileType merge(FileType detachedInstance) {
		log.debug("merging FileType instance");
		try {
			FileType result = null ;
			// (FileType) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed");
			throw re;
		}
	}

	public void attachDirty(FileType instance) {
		log.debug("attaching dirty FileType instance");
		try {
			// getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed");
			throw re;
		}
	}

	public void attachClean(FileType instance) {
		log.debug("attaching clean FileType instance");
		try {
			// getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed");
			throw re;
		}
	}
	*/
}