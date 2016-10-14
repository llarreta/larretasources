package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.EmployeeRetirement;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad EmployeeRetirement
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeeRetirementDAOLocal {

    public void createEmployeeRetirement(EmployeeRetirement obj)
            throws DAOServiceException, DAOSQLException;

    public EmployeeRetirement getEmployeeRetirementByID(Long id)
            throws DAOServiceException, DAOSQLException;

    public void updateEmployeeRetirement(EmployeeRetirement obj)
            throws DAOServiceException, DAOSQLException;

    public void deleteEmployeeRetirement(EmployeeRetirement obj)
            throws DAOServiceException, DAOSQLException;

    public List<EmployeeRetirement> getAllEmployeeRetirement()
            throws DAOServiceException, DAOSQLException;
    
    /**
     * Método: Consulta las causas de retiro dado un id de empleado
     * @param idEmployee - Long Identificador del empleado
     * @return List<EmployeeRetirement> lista de las causas de retiro del empleado
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<EmployeeRetirement> getEmployeeRetirementByEmployee(Long idEmployee)
    throws DAOServiceException, DAOSQLException;

	public void deleteEmployeeRetirementByEmployeeId(Long employeeId)throws DAOServiceException, DAOSQLException;
}
