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
import org.hibernate.criterion.Example;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.MovCmdStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.MovCmdStatusDAOLocal;

/**
 * A data access object (DAO) providing persistence and search support for
 * MovCmdStatus entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see co.com.directv.sdii.persistence.MovCmdStatus
 * @author MyEclipse Persistence Tools
 */

@Stateless(name="MovCmdStatusDAOLocal",mappedName="ejb/MovCmdStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MovCmdStatusDAO extends BaseDao implements MovCmdStatusDAOLocal {
	private static final Logger log = UtilsBusiness.getLog4J(MovCmdStatusDAO.class);
	// property constants
	public static final String CMD_STATUS_CODE = "cmdStatusCode";
	public static final String CMD_STATUS_NAME = "cmdStatusName";

	//caché de objetos
	private MovCmdStatus movCmdStatusNoConfig;
	private MovCmdStatus movCmdStatusPending;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MovCmdStatus getMovCmdStatusNoConfig() throws DAOServiceException, DAOSQLException, PropertiesException {
		if(movCmdStatusNoConfig == null) {
			movCmdStatusNoConfig = findByCmdStatusCode(CodesBusinessEntityEnum.MOV_CMD_STATUS_NO_CONFIG.getCodeEntity()).get(0);
		}
		return movCmdStatusNoConfig;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MovCmdStatus getMovCmdStatusPending() throws DAOServiceException, DAOSQLException, PropertiesException {
		if(movCmdStatusPending == null) {
			movCmdStatusPending = findByCmdStatusCode(CodesBusinessEntityEnum.MOV_CMD_STATUS_PENDING.getCodeEntity()).get(0);
		}
		return movCmdStatusPending;
	}
	
    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdStatusDAOLocal#save(co.com.directv.sdii.model.pojo.MovCmdStatus)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void save(MovCmdStatus transientInstance) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio save/MovCmdStatusDAO ==");
		try {
			getSession().save(transientInstance);
		} catch (Throwable re) {
			log.error("== Error save/MovCmdStatusDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina save/MovCmdStatusDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdStatusDAOLocal#delete(co.com.directv.sdii.model.pojo.MovCmdStatus)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(MovCmdStatus persistentInstance) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio delete/MovCmdStatusDAO ==");
		try {
			getSession().delete(persistentInstance);
		} catch (Throwable re) {
			log.error("== Error delete/MovCmdStatusDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina delete/MovCmdStatusDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdStatusDAOLocal#findById(java.math.BigDecimal)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MovCmdStatus findById(java.math.BigDecimal id) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio findById/MovCmdStatusDAO ==");
		try {
			MovCmdStatus instance = (MovCmdStatus) getSession().get(
					"co.com.directv.sdii.model.pojo.MovCmdStatus", id);
			return instance;
		} catch (Throwable re) {
			log.error("== Error findById/MovCmdStatusDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findById/MovCmdStatusDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdStatusDAOLocal#findByExample(co.com.directv.sdii.model.pojo.MovCmdStatus)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdStatus> findByExample(MovCmdStatus instance) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio findByExample/MovCmdStatusDAO ==");
		try {
			List<MovCmdStatus> results = getSession().createCriteria(
					"co.com.directv.sdii.model.pojo.MovCmdStatus").add(
					Example.create(instance)).list();
			return results;
		} catch (Throwable re) {
			log.error("== Error findByExample/MovCmdStatusDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findByExample/MovCmdStatusDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdStatusDAOLocal#findByProperty(java.lang.String, java.lang.Object)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdStatus> findByProperty(String propertyName, Object value) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio findByProperty/MovCmdStatusDAO ==");
		try {
			String queryString = "from MovCmdStatus as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (Throwable re) {
			log.error("== Error findByProperty/MovCmdStatusDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findByProperty/MovCmdStatusDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdStatusDAOLocal#findByCmdStatusCode(java.lang.Object)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdStatus> findByCmdStatusCode(Object cmdStatusCode) throws DAOServiceException, DAOSQLException  {
		return findByProperty(CMD_STATUS_CODE, cmdStatusCode);
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdStatusDAOLocal#findByCmdStatusName(java.lang.Object)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdStatus> findByCmdStatusName(Object cmdStatusName) throws DAOServiceException, DAOSQLException  {
		return findByProperty(CMD_STATUS_NAME, cmdStatusName);
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdStatusDAOLocal#findAll()
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdStatus> findAll() throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio findAll/MovCmdStatusDAO ==");
		try {
			String queryString = "from MovCmdStatus";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (Throwable re) {
			log.error("== Error findAll/MovCmdStatusDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findAll/MovCmdStatusDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdStatusDAOLocal#merge(co.com.directv.sdii.model.pojo.MovCmdStatus)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MovCmdStatus merge(MovCmdStatus detachedInstance) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio merge/MovCmdStatusDAO ==");
		try {
			MovCmdStatus result = (MovCmdStatus) getSession().merge(
					detachedInstance);
			return result;
		} catch (Throwable re) {
			log.error("== Error merge/MovCmdStatusDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina merge/MovCmdStatusDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdStatusDAOLocal#attachDirty(co.com.directv.sdii.model.pojo.MovCmdStatus)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void attachDirty(MovCmdStatus instance) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio attachDirty/MovCmdStatusDAO ==");
		try {
			getSession().saveOrUpdate(instance);
		} catch (Throwable re) {
			log.error("== Error attachDirty/MovCmdStatusDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina attachDirty/MovCmdStatusDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdStatusDAOLocal#attachClean(co.com.directv.sdii.model.pojo.MovCmdStatus)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void attachClean(MovCmdStatus instance) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio attachClean/MovCmdStatusDAO ==");
		try {
			getSession().lock(instance, LockMode.NONE);
		} catch (Throwable re) {
			log.error("== Error attachClean/MovCmdStatusDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina attachClean/MovCmdStatusDAO ==");
		}
	}

	@Override
	public List<MovCmdStatus>  getMovCmdStatusHspToIbs() throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getMovCmdStatusHspToIbs/MovCmdStatusDAO ==");
		try {
			String queryString = "from "+MovCmdStatus.class.getName() +" entity";
			queryString = queryString + " where entity.cmdStatusCode in ('01','02','03','04','05','06') ";
			queryString = queryString + " order by entity.cmdStatusCode asc";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (Throwable re) {
			log.error("== Error getMovCmdStatusHspToIbs/MovCmdStatusDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina getMovCmdStatusHspToIbs/MovCmdStatusDAO ==");
		}
	}

	@Override
	public List<MovCmdStatus> getMovCmdStatusIbsToHsp() throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getMovCmdStatusIbsToHsp/MovCmdStatusDAO ==");
		try {
			String queryString = "from "+MovCmdStatus.class.getName() +" entity ";
			queryString = queryString + " where entity.cmdStatusCode in ('01','02','03','06') ";
			queryString = queryString + " order by entity.cmdStatusCode asc";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (Throwable re) {
			log.error("== Error getMovCmdStatusIbsToHsp/MovCmdStatusDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina getMovCmdStatusIbsToHsp/MovCmdStatusDAO ==");
		}
	}

	@Override
	public MovCmdStatus getMovCmdStatusByCode(String code)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getMovCmdStatusByCode/MovCmdStatusDAO ==");
		try {
			String queryString = "from "+MovCmdStatus.class.getName() +" entity ";
			queryString = queryString + " where entity.cmdStatusCode = :cmdStatusCode ";
			queryString = queryString + " order by entity.cmdStatusCode asc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setString("cmdStatusCode", code);
			return (MovCmdStatus) queryObject.uniqueResult();
		} catch (Throwable re) {
			log.error("== Error getMovCmdStatusByCode/MovCmdStatusDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina getMovCmdStatusByCode/MovCmdStatusDAO ==");
		}
	}
	
	
}