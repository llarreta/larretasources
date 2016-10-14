package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;
import javax.jws.WebParam;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.EmployeePaginationResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.EmployeeVO;

/**
 * 
 * Iterface para la gestion del CRUD la Entidad 
 * Employee 
 * 
 * Fecha de Creación: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeeFacadeBeanLocal {

	/**
     * Metodo: Permite la creación de un nuevo empleado
     * @param eVo objeto que contiene la información del empleado que se creará
     * @throws BusinessException En caso de error al tratar de crear el empleado
     * @author jalopez
     */
    public void createEmployee(EmployeeVO obj) throws BusinessException;

    /**
     * Metodo: Obtiene la información de un empleado dado su identificador
     * @param id identificador del empleado cuya información se desea consultar
     * @return Objeto con la información del empleado, nulo en caso que no exista el empleado
     * con el identificador especificado.
     * @throws BusinessException En caso de errror al tratar de consultar la información del empleado
     * @author jalopez
     */
    public EmployeeVO getEmployeeByID(Long id) throws BusinessException;

    /**
     * Metodo: Actualiza la información de un empleado
     * @param eVo información del empleado a ser actualizado, es posible modificar cualquier propiedad
     * excepto el identificador ya que este determina cual empleado debe ser actualizado
     * @throws BusinessException En caso de error al tratar de borrar la información del empleado
     * @author jalopez
     */
    public void updateEmployee(EmployeeVO obj) throws BusinessException;

    /**
     * 
     * Metodo: Borra la información de un empleado
     * @param eVo objeto con la información del empleado que será borrado, solo se requiere que 
     * la propiedad id tenga el número de identificación del registro que desea ser eliminado
     * @throws BusinessException En caso de error al tratar de borrar la información del empleado
     * @author jalopez
     */
    public void deleteEmployee(EmployeeVO obj) throws BusinessException;

    /**
     * Metodo: Operación para obtener la información de todos los empleados registrados en el sistema
     * @return Lista con la información de todos los empleados del sistema, una lista vacía en caso que no
     * exista información de empleados
     * @throws BusinessException En caso de error al tratar de obtener la información de los empleados
     * @author jalopez
     */
    public List<EmployeeVO> getAllEmployee() throws BusinessException;

    /**
     * Metodo: Obtiene la información de empleados por identificador de dealer
     * @param id identificador del dealer por el que se realizará el filtro
     * @return Lista con los empleados asociados a ese dealer
     * @throws BusinessException en caso de error al consultar la información de los
     * empleados
     * @author jalopez
     */
    public List<EmployeeVO> getEmployeesByDealerId(Long id) throws BusinessException;
    
    /**
     * Metodo: Obtiene la información de empleados activos por identificador de dealer
     * @param id identificador del dealer por el que se realizará el filtro
     * @return Lista con los empleados activos asociados a ese dealer
     * @throws BusinessException en caso de error al consultar la información de los
     * empleados activos 
     * @author gfandino
     */
    public List<EmployeeVO> getEmployeesActiveByDealerId(Long id)throws BusinessException;

    /**
     * Metodo: Obtiene la información de un empleado
     * @param idDocType identificador del tipo de documento
     * @param documentNumber número de documento
     * @return objeto con la información del empleado
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jalopez
     */
    public EmployeeVO getEmployeeByDocTypeAndDocNum(Long idDocType, String documentNumber) throws BusinessException;
    
    /**
     * 
     * Metodo: Obtiene la información de un empleado
     * @param idDocType identificador del tipo de documento
     * @param documentNumber número de documento
     * @param dealerId identificador del dealer
     * @return información del empleado
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jalopez
     */
    public EmployeeVO getEmployeeByDocTypeAndDocNum(Long idDocType, String documentNumber,Long dealerId) throws BusinessException;
    
	/**
	 * Realiza la búsqueda de colaboradores por cualquiera de los criterios opcionales que se definen por parámetro
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param typeId
	 * @param documentNumber
	 * @param firstName
	 * @param lastName
	 * @param depotCode
	 * @param dealerCode
	 * @return List<Employee>
	 * @throws BusinessException
	 */
	public EmployeePaginationResponse getEmployeeByChriteria(Long typeId, String documentNumber, String firstName,String lastName, String depotCode, Long dealerCode,Long dealerId,Long countryId, RequestCollectionInfo requestCollInfo) throws BusinessException;
    
    /**
     * 
     * Metodo: realiza el retiro de un empleado
     * @param employeeVO información del empleado a ser retirado
     * @param description descripción de la causa del retiro
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jalopez
     */
    public void retireEmployee(EmployeeVO employeeVO, String description) throws BusinessException;

    /**
     * Metodo: Operación para obtener la información de los empleados, dado el identificador del tipo
     * de documento
     * @param typeId identificador del tipo de documento por el que se filtrarán los empleados
     * @return Lista con los empleados cuyo tipo de documento de identificación, coincide con el tipo
     * de documento especificado
     * @throws BusinessException En caso de error al tratar de obtener la información de los 
     * empleados por el tipo de documento
     * @author jalopez
     */
	public List<EmployeeVO> getEmployeeByDocumentTypeId(Long typeId)throws BusinessException;

	/**
     * Metodo: Obtiene la lista de empleados cuyo número de documento coincide con el
     * especificado por el usuario
     * @param documentNumber número de documento de identificación
     * @return Lista con la información de los empleados
     * @throws BusinessException En caso de error al tratar de obtener de los empleados por el número
     * de identificación
     * @author jalopez
     */
	public List<EmployeeVO> getEmployeeByDocumentNumber(String documentNumber)throws BusinessException;

	/**
     * Metodo: Obtiene una lista de empleados cuyos nombres coinciden con el especificado
     * @param firstName nombre por el que se realizará el filtro para obtener los empleados
     * @return Lista de empleados cuyo primero nombre coincide con el especificado
     * @throws BusinessException En caso de error al tratar de obtener la información de los
     * empleados
     * @author jalopez
     */
	public List<EmployeeVO> getEmployeeByFirstName(String firstName)throws BusinessException;

	/**
     * Metodo: Permite obtener una lista de los empleados cuyos apellidos coinciden con los especificados
     * @param lastName cadena que filtrará el apellido de los empleados a ser consultados
     * @return Lista con la información de los empleados cuyo apellido concuerda con el especificado
     * @throws BusinessException en caso de error al tratar de obtenerla información de los empleados con
     * ese apellido
     * @author jalopez
     */
	public List<EmployeeVO> getEmployeeByLastName(String lastName)throws BusinessException;

	/**
     * 
     * Metodo: Consulta la información de los empleados cuyo tipo de identificación,
     * número de identificación, primer nombre y apellidos concuerdan con los especificados
     * @param typeId tipo de identificación de los empleados
     * @param documentNumber Número de identificación por el cual se realizará la consulta
     * @param firstName nombre del empleado por el que se realizará la consulta
     * @param lastName apellido del empleado
     * @return Lista con los empleados
     * @throws BusinessException En caso de error al ejecutar la operación
     * @author jalopez
     */
	public List<EmployeeVO> getEmployeeByDocTypeIdAndDocNumberAndFirstNAndLastN(
			Long typeId, String documentNumber, String firstName,
			String lastName)throws BusinessException;
	
	/**
     * 
     * Metodo: Obtiene la información de los documentos de los empleados de un dealer específico
     * @param dealerID identificador del dealer
     * @return lista con los documentos de los empleados
     * @throws BusinessException  en caso de error al ejecutar la operación
     * @author jalopez
     */
	public List<EmployeeVO> getEmployeesDocumentsByDealer(Long dealerID) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la informacion de un empleado a partir de su identificador
     * @param employeeId - Long identificador del empleado
     * @return EmployeeVO Informacion del empleado de acuerdo al identificador enviado 
	 * @throws BusinessException  en caso de error al ejecutar la operación de consulta del empleado
	 */
	public EmployeeVO getEmployeeDetailsByEmployeeId(@WebParam(name="employeeId")Long employeeId) throws BusinessException;

	

}
