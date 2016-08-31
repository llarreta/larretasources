package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.EventReasonIbs;

@Local
public interface EventReasonIbsDAOLocal {

	/**
	 * 
	 * @param transientInstance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public abstract void save(EventReasonIbs transientInstance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param persistentInstance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public abstract void delete(EventReasonIbs persistentInstance)
			throws DAOServiceException, DAOSQLException;

	/**
	 * @param id
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public abstract EventReasonIbs findById(Long id)
			throws DAOServiceException, DAOSQLException;

	

	/**
	 * Operacion encargada de actualizar un registro de la tabla MovCmdQueue 
	 * @param obj
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author waguilera
	 */
	public void updateEventReasonIbs(EventReasonIbs obj) throws DAOServiceException, DAOSQLException;

	/**
	 * Operacion encargada de obtener una configuracion con respecto al reason y al evento 
	 * @param obj
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author waguilera
	 */
	public List<EventReasonIbs> getConfigEventReasonIbsForEvent(Long eventId) throws DAOServiceException, DAOSQLException;
	
	
}