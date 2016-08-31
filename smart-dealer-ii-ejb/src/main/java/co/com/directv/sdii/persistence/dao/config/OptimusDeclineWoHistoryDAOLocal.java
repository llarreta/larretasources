package co.com.directv.sdii.persistence.dao.config;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.OptimusDeclineWoHistory;


@Local
public interface OptimusDeclineWoHistoryDAOLocal {

    public void createOptimusDeclineWoHistory(OptimusDeclineWoHistory obj) throws DAOSQLException, DAOServiceException;
	
	public void updateOptimusDeclineWoHistory(OptimusDeclineWoHistory obj) throws DAOSQLException, DAOServiceException;
	
}
