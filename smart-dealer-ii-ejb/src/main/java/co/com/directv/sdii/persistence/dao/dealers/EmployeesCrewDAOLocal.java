package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.EmployeeCrew;
/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad EmployeesCrew
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeesCrewDAOLocal {
	
	public void createEmployeesCrew(EmployeeCrew obj) throws DAOServiceException, DAOSQLException;
	
	public List<EmployeeCrew> getEmployeesCrewByCrewID(Long id)	throws DAOServiceException, DAOSQLException;
	
	public void updateEmployeesCrew(EmployeeCrew obj) throws DAOServiceException, DAOSQLException;
	
	public void deleteEmployeesCrew(EmployeeCrew obj) throws DAOServiceException, DAOSQLException;
	
	public void deleteEmployeesCrewByCrewId(Long crewId) throws DAOServiceException, DAOSQLException;
	
	public List<EmployeeCrew> getAllEmployeesCrew()	throws DAOServiceException, DAOSQLException;
	
	public List<EmployeeCrew> getEmployeesCrewByDealerID(Long dealerId,String isResponsible) throws DAOServiceException, DAOSQLException;
	
	public List<EmployeeCrew> getEmployeesCrewByDealerIDAndCrewType(Long dealerId,String isResponsible,Long crewType) throws DAOServiceException, DAOSQLException;
	
	public List<EmployeeCrew> getEmployeesCrewByDealerIdAndCrewTypeAndResponsible(Long dealerId,String isResponsible , Long crewType, String responsibleName) throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Consulta el empleado responsable de una cuadrilla
	 * @param crewId identificador de la cuadilla de la cual se desea consultar el responsable
	 * @return Employee responsable de la cuadrilla
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public Employee getEmployeeResponsibleByCrewId(Long crewId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta los empleados asociados a una cuadrilla que NO son responsables
	 * @param crewId identiifcador de la cuadrilla de la cual se desean consultar los empleados NO responsables
	 * @return List<Employee> Lista de empleados NO responsables de la cuadrilla
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Employee> getEmployeeNotResponsibleByCrewId(Long crewId)throws DAOServiceException, DAOSQLException;
	
	public Employee getEmployeeWoutResponsibleByCrewId(Long crewId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta todos los empleados responsables de las cuadrillas por pais y por dealer. SI el dealer viene nulo, se filtra solo
	 * por pais
	 * @param countryId
	 * @param dealerId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<EmployeeCrew> getAllResponsableEmployeeCrewByCountryAndDealerId(Long countryId,Long dealerId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Borra las relaciones de un empleado con las cuadrillas
	 * @param employeeId identificador del empleado
	 * @throws DAOServiceException en caso de error al tratar de borrar las relaciones
	 * @throws DAOSQLException en caso de error al tratar de borrar las relaciones
	 * @author jjimenezh
	 */
	public void deleteEmployeeCrewsByEmployeeId(Long employeeId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene una lista de los registros en los que un empleado está relacionado a cuadrillas
	 * @param employeeId identificador del empleado
	 * @return Lista de las cuadrillas en las que el empleado está relacionado
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 * @author jjimenezh
	 */
	public List<EmployeeCrew> getEmployeesCrewByEmpId(Long id)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: obtiene la asociación de un colaborador a cuadrillas en estado determinado
	 * @param employeeId identificador del empleado
	 * @param codeStatus codigo del estado de la cuadrilla
	 * @return Lista de las cuadrillas en estado determinado en las que el empleado está relacionado
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 * @author gfandino
	 */
	public List<EmployeeCrew> getEmployeesCrewByEmpIdAndCrewStatus(Long employeeId, String codeStatus)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la cantidad de técnicos activos dado el identificador de la compañía
	 * instaladora y que la cuadrilla en la que esten este activa
	 * @param dealerId identificador de la compañía
	 * @return cantidad de técnicos activos que posee la compañía instaladora
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 * @author jnova
	 */
	public Long getActiveTechniciansQtyByDealerIdAndActiveCrew(Long dealerId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta todos los empleados responsables de las cuadrillas por pais y por dealer. SI el dealer viene nulo, se filtra solo
	 * por pais
	 * @param countryId
	 * @param dealerId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<EmployeeCrew> getAllResponsableEmployeeCrewByCountryAndDealerIds(Long countryId,List<Long> dealerIds) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta todos los empleados de las cuadrillas por id dela cuadrilla.
	 * @param crewId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Employee> getAllEmployeeCrewByCrewId(Long crewId)throws DAOServiceException, DAOSQLException;
 

}