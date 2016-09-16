package co.com.directv.sdii.persistence.dao.core;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.DispatchWorkOrderLog;

/**
 * Interface Local para la gestiÃ³n DispatchWorkOrder
 * 
 * @author fsanabri
 * @version 5.1.2
 * 
 * @see
 */
@Local
public interface DispatchWorkOrderDAOLocal {

	/**
	 * MÃ©todo encargado de persistir un {@link DispatchWorkOrderLog}
	 * 
	 * @param dispatchWorkOrderLog
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public void createDispatchWorkOrderLog(DispatchWorkOrderLog dispatchWorkOrderLog, byte[] responseXML) throws DAOServiceException, DAOSQLException ;
	
}