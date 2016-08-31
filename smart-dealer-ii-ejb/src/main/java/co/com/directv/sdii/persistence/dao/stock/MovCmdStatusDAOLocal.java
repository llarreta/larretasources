package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.MovCmdQueue;
import co.com.directv.sdii.model.pojo.MovCmdStatus;

public interface MovCmdStatusDAOLocal {

	/**
	 * @param transientInstance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract void save(MovCmdStatus transientInstance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param persistentInstance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract void delete(MovCmdStatus persistentInstance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param id
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract MovCmdStatus findById(java.math.BigDecimal id)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param instance
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdStatus> findByExample(MovCmdStatus instance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param propertyName
	 * @param value
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdStatus> findByProperty(String propertyName,
			Object value) throws DAOServiceException, DAOSQLException;

	/**
	 * @param cmdStatusCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdStatus> findByCmdStatusCode(Object cmdStatusCode)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param cmdStatusName
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdStatus> findByCmdStatusName(Object cmdStatusName)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract List<MovCmdStatus> findAll() throws DAOServiceException,
			DAOSQLException;

	/**
	 * @param detachedInstance
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract MovCmdStatus merge(MovCmdStatus detachedInstance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param instance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract void attachDirty(MovCmdStatus instance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param instance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public abstract void attachClean(MovCmdStatus instance)
			throws DAOServiceException, DAOSQLException;

	public MovCmdStatus getMovCmdStatusNoConfig() throws DAOServiceException,
			DAOSQLException, PropertiesException;

	public MovCmdStatus getMovCmdStatusPending() throws DAOServiceException,
			DAOSQLException, PropertiesException;
	
	/**
	 * Metodo encargado de consultar los estados para los procesos
	 * de reporte de hsp hacia IBS.  
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author waguilera
	 */
	public List<MovCmdStatus> getMovCmdStatusHspToIbs() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo encargado de consultar los estados para los procesos
	 * de reporte de Ibs hacia Hsp.  
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author waguilera
	 */
	public List<MovCmdStatus> getMovCmdStatusIbsToHsp() throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * @param id
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public MovCmdStatus getMovCmdStatusByCode(String code)
			throws DAOServiceException, DAOSQLException;
	
	

}