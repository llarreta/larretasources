package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.collection.EmployeePaginationResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad Employee 
 * 
 * Fecha de Creación: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeeDAOLocal {

    public void createEmployee(Employee obj) throws DAOServiceException,
            DAOSQLException;

    public Employee getEmployeeByID(Long id) throws DAOServiceException,
            DAOSQLException;

    public void updateEmployee(Employee obj) throws DAOServiceException,
            DAOSQLException;

    public void deleteEmployee(Employee obj) throws DAOServiceException,
            DAOSQLException;

    public List<Employee> getAllEmployee() throws DAOServiceException,
            DAOSQLException;

    public List<Employee> getEmployeesByDealerId(Long id) throws DAOServiceException, DAOSQLException;
    
    /**
     * Método: consulta los Empleador asociados a un Dealer y que se encuentran en determinado estado
     * @param idDealer - Long identificador del dealer
     * @param codeStatus - String codigo del estado
     * @return List<Employee> lista de empleados asocaidos al dealer en determinado estado
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<Employee> getEmployeesByDealerIdAndStatus(Long idDealer,String codeStatus) throws DAOServiceException, DAOSQLException;

    public Employee getEmployeeByDocNumAndTypeDealerId(Long idDocType, String documentNumber, Long dealerId) throws DAOServiceException, DAOSQLException ;

    public Employee getEmployeeByDocTypeAndDocNum(Long idDocType, String documentNumber,Long dealerId) throws DAOServiceException, DAOSQLException;
    
    public Employee getEmployeeByDocTypeAndDocNum(Long idDocType, String documentNumber) throws DAOServiceException, DAOSQLException;

	public List<Employee> getEmployeeByDocumentNumber(String documentNumber) throws DAOServiceException, DAOSQLException;
	
	public Employee getEmployeeByDocumentNumberAndStatus(String documentNumber,String employeeStatus) throws DAOServiceException, DAOSQLException;

	public List<Employee> getEmployeeByDocumentNumberAnDocTypeAndStatus(String documentNumber,Long idType, String employeeStatus) throws DAOServiceException, DAOSQLException;	
	
	public List<Employee> getEmployeeByDocumentTypeId(Long typeId)throws DAOServiceException, DAOSQLException;

	public List<Employee> getEmployeeByFirstName(String firstName)throws DAOServiceException, DAOSQLException;

	public List<Employee> getEmployeeByLastName(String lastName)throws DAOServiceException, DAOSQLException;

	public List<Employee> getEmployeeByDocTypeIdAndDocNumberAndFirstNAndLastN(
			Long typeId, String documentNumber, String firstName,
			String lastName)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Realiza la búsqueda de colaboradores por cualquiera de los criterios opcionales que se definen por parámetro
	 * @param typeId
	 * @param documentNumber
	 * @param firstName
	 * @param lastName
	 * @param depotCode
	 * @param dealerCode
	 * @return List<Employee>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public EmployeePaginationResponse getEmployeeByChriteria(
			Long typeId, String documentNumber, String firstName,
			String lastName, String depotCode, Long dealerCode,Long dealerId,Long countryId, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException;
	
	public List<Employee> getEmployeesDocumentsByDealer(Long dealerID) throws DAOServiceException, DAOSQLException ;

	/**
	 * Metodo: Obtiene una lista de los empleados dado un identificador de cargo
	 * @param positionId identificador del cargo por el que se realizará el filtro
	 * @return Lista con los empleados que tienen el cargo especificador
	 * @throws DAOServiceException En caso de error al tratar de obtener los empleados por identificador de cargo
	 * @throws DAOSQLException En caso de error al tratar de obtener los empleados por identificador de cargo
	 * @author
	 */
	public List<Employee> getEmployeesByPositionId(Long positionId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la cantidad de técnicos activos dado el identificador de la compañía
	 * instaladora
	 * @param dealerId identificador de la compañía
	 * @return cantidad de técnicos activos que posee la compañía instaladora
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 * @author jjimenezh
	 */
	public Long getActiveTechniciansQtyByDealerId(Long dealerId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene un empleado por su identificador ibsTechnical
	 * @param employeeId
	 * @param ibsTechnical
	 * @param countryId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public Employee getEmployeeByIbsTechnical(Long employeeId,Long ibsTechnical,Long countryId) throws DAOServiceException, DAOSQLException;
	
	
    public List<Employee> getEmployeesAviableByDealerId(Long idDealer,String codeStatus,String crewStatus) throws DAOServiceException, DAOSQLException;

}
