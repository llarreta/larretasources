package co.com.directv.sdii.persistence.dao.config;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WorkOrderExport;


@Local
public interface WorkOrderExportDAOLocal {
	
	public void createWorkOrderExport(WorkOrderExport obj) throws DAOServiceException, DAOSQLException;

}
