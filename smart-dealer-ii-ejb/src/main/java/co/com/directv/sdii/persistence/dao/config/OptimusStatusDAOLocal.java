package co.com.directv.sdii.persistence.dao.config;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.OptimusStatus;


@Local
public interface OptimusStatusDAOLocal {

	public OptimusStatus getOptimusStatusById(String id) throws DAOSQLException, DAOServiceException;
	//public OptimusStatus getOptimusStatusById(Long id) throws DAOSQLException, DAOServiceException;
	public OptimusStatus getOptimusStatusByCode(String code) throws DAOSQLException, DAOServiceException;
	
}
