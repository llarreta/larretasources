package co.com.directv.sdii.persistence.dao.config;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.OptimusStatusHistory;

@Local
public interface OptimusStatusHistoryDAOLocal {

	public void createOptimusStatusHistory(OptimusStatusHistory obj) throws DAOSQLException, DAOServiceException;
	
	public void updateOptimusStatusHistory(OptimusStatusHistory obj) throws DAOSQLException, DAOServiceException;
}
