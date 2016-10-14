package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.EmployeePaginationResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.EmployeeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad Employee
 * 
 * Fecha de Creación: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeeCRUDBeanLocal {

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createEmployee(EmployeeVO obj) throws BusinessException;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EmployeeVO getEmployeeByID(Long id) throws BusinessException;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateEmployee(EmployeeVO obj) throws BusinessException;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteEmployee(EmployeeVO obj) throws BusinessException;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EmployeeVO> getAllEmployee() throws BusinessException;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EmployeeVO> getEmployeesByDealerId(Long id) throws BusinessException;
    
    /**
     * Método: Permite consultar los empleados asocidos al dealer en estado activo
     * @param id Long identificador del dealer
     * @return List<EmployeeVO> lista de los empleados activos asociados al dealer
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EmployeeVO> getEmployeesActiveByDealerId(Long id)throws BusinessException;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EmployeeVO getEmployeeByDocTypeAndDocNum(Long idDocType, String documentNumber) throws BusinessException;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EmployeeVO getEmployeeByDocTypeAndDocNum(Long idDocType, String documentNumber,Long dealerId) throws BusinessException;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void retireEmployee(EmployeeVO employeeVO, String description) throws BusinessException;
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<EmployeeVO> getEmployeeByDocumentTypeId(Long typeId)throws BusinessException;

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<EmployeeVO> getEmployeeByDocumentNumber(String documentNumber)throws BusinessException;

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<EmployeeVO> getEmployeeByFirstName(String firstName)throws BusinessException;

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<EmployeeVO> getEmployeeByLastName(String lastName)throws BusinessException;

	public List<EmployeeVO> getEmployeeByDocTypeIdAndDocNumberAndFirstNAndLastN(
			Long typeId, String documentNumber, String firstName, String lastName)throws BusinessException;
	
	public List<EmployeeVO> getEmployeesDocumentsByDealer(Long dealerID) throws BusinessException;	
	/**
	 * Realiza la b�squeda de colaboradores por cualquiera de los criterios opcionales que se definen por par�metro
	 * @param typeId
	 * @param documentNumber
	 * @param firstName
	 * @param lastName
	 * @param depotCode
	 * @param dealerCode
	 * @return List<Employee>
	 * @throws BusinessException
	 */
	public EmployeePaginationResponse getEmployeeByChriteria(
			Long typeId, String documentNumber, String firstName,
			String lastName, String depotCode, Long dealerCode,Long dealerId,Long countryId, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la informacion de un empleado a partir de su identificador
     * @param employeeId - Long identificador del empleado
     * @return EmployeeVO Informacion del empleado de acuerdo al identificador enviado 
	 * @throws BusinessException  en caso de error al ejecutar la operación de consulta del empleado
	 */
	public EmployeeVO getEmployeeDetailsByEmployeeId(Long employeeId) throws BusinessException;

	/**
	 * Metodo: Retorna la cantidad de técnicos activos que se encuentran en la compañía instaladora especificada
	 * @param dealerId identificador de la compañía
	 * @return cantidad de técnicos activos inscritos en la compañía
	 * @throws BusinessException En caso de error
	 * @author jjimenezh
	 */
	public Long getActiveTechniciansQtyByDealerId(Long dealerId)throws BusinessException;

	
}
