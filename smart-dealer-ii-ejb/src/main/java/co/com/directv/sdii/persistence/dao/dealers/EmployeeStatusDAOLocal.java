package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.EmployeeStatus;


/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad EmployeeStatus
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeeStatusDAOLocal {

    public List<EmployeeStatus> getAllEmployeeStatus() throws DAOServiceException, DAOSQLException;

    public EmployeeStatus getEmployeeStatusById(Long statusId)throws DAOServiceException, DAOSQLException;

    public EmployeeStatus getEmployeeStatusByCode(String code) throws DAOServiceException, DAOSQLException;

}