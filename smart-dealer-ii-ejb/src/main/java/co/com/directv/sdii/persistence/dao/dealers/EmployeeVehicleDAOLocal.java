package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.EmployeeVehicle;
import co.com.directv.sdii.model.pojo.EmployeeVehicleId;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad EmployeeVehicle
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeeVehicleDAOLocal {

	
	public void createEmployeeVehicle(EmployeeVehicle obj)
			throws DAOServiceException, DAOSQLException;

	
	public EmployeeVehicle getEmployeeVehicleByID(EmployeeVehicleId id)
			throws DAOServiceException, DAOSQLException;

	
	public void updateEmployeeVehicle(EmployeeVehicle obj)
			throws DAOServiceException, DAOSQLException;

	
	public void deleteEmployeeVehicle(EmployeeVehicle obj)
			throws DAOServiceException, DAOSQLException;

	
	public List<EmployeeVehicle> getAllEmployeeVehicle()
			throws DAOServiceException, DAOSQLException;

        public List<EmployeeVehicle> getEmployeeVehicleByVehicleId(long vehicleId)
                    throws DAOServiceException, DAOSQLException;

    public void deleteAllEmployeeVehicleByVehicleId(Long id) throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Obtiene el ultimo vehiculo asociado a un empleado 
     * @param employeeId Identificador del empleado
     * @return EmployeeVehicle vehiculo asociado al empleado
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jnova
     */
    public EmployeeVehicle getEmployeeVehicleByEmployeeId(long employeeId) throws DAOServiceException, DAOSQLException;


	/**
	 * Metodo: Borra la información de todas las relaciones que pudiera tener un empleado con los vehículos
	 * @param employeeId identificador del empleado
	 * @throws DAOServiceException en caso de error al realizar el borrado
	 * @throws DAOSQLException en caso de error al realizar el borrado
	 * @author jjimenezh
	 */
	public void deleteAllEmployeeVehicleByEmployeeId(Long employeeId)throws DAOServiceException, DAOSQLException;

}