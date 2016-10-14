package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WorkOrderExportData;


@Local
public interface WorkOrderExportDataDAOLocal {
	
	public void createWorkOrderExportData(List<WorkOrderExportData> obj) throws DAOServiceException, DAOSQLException;

}
