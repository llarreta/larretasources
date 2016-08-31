package co.com.directv.sdii.persistence.dao.config;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.OptimusStatusEvent;


@Local
public interface OptimusStatusEventDAOLocal {

	public void createOptimusStatusEvent(OptimusStatusEvent O , byte[] resquestXML) throws DAOSQLException, DAOServiceException;
	
	public void updateOptimusStatusEvent(OptimusStatusEvent obj) throws DAOSQLException, DAOServiceException;
	
}
