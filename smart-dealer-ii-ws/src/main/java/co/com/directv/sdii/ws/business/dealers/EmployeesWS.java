package co.com.directv.sdii.ws.business.dealers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.ContractTypesFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.DocumentTypesFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.EducationLevelFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.EmployeeFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.EmployeeRetirementFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.EmployeeRolFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.EmployeeStatusFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.MaritalStatusFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.MediaContactTypesFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.TrainingFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.TrainingTypesFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.TrainingType;
import co.com.directv.sdii.model.pojo.collection.EmployeePaginationResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ContractTypeVO;
import co.com.directv.sdii.model.vo.DocumentTypeVO;
import co.com.directv.sdii.model.vo.EducationLevelVO;
import co.com.directv.sdii.model.vo.EmployeeRetirementVO;
import co.com.directv.sdii.model.vo.EmployeeRolVO;
import co.com.directv.sdii.model.vo.EmployeeStatusVO;
import co.com.directv.sdii.model.vo.EmployeeVO;
import co.com.directv.sdii.model.vo.MaritalStatusVO;
import co.com.directv.sdii.model.vo.MediaContactTypeVO;
import co.com.directv.sdii.model.vo.TrainingTypeVO;
import co.com.directv.sdii.model.vo.TrainingVO;

/**
 * 
 * Clase que define las operaciones para empleados que serán expuestas
 * por el sistema en forma de WebServices
 * 
 * Fecha de Creación: 14/05/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.dealers.EmployeeFacadeBeanLocal
 * @see co.com.directv.sdii.facade.dealers.EmployeeRetirementFacadeBeanLocal
 * @see co.com.directv.sdii.facade.dealers.DocumentTypesFacadeBeanLocal
 * @see co.com.directv.sdii.facade.dealers.ContractTypesFacadeBeanLocal
 * @see co.com.directv.sdii.facade.dealers.EmployeeStatusFacadeBeanLocal
 * @see co.com.directv.sdii.facade.dealers.EmployeeRolFacadeBeanLocal
 * @see co.com.directv.sdii.facade.dealers.MediaContactTypesFacadeBeanLocal
 * @see co.com.directv.sdii.facade.dealers.TrainingFacadeBeanLocal
 * @see co.com.directv.sdii.facade.dealers.TrainingTypesFacadeBeanLocal
 */
@MTOM
@WebService()
@Stateless()
public class EmployeesWS {

	/**
	 * Ofrece el acceso a las operaciones relacionadas con las entidades
	 * de empleados
	 */
    @EJB
    private EmployeeFacadeBeanLocal ejbEmployeeBean;
    
    /**
     * Ofrece las operaciones relacionadas con el retiro de empleados
     * de un dealer
     */
    @EJB
    private EmployeeRetirementFacadeBeanLocal ejbEmployeeRetirementBean;
    
    /**
     * Expone las operaciones relacionadas con los tipos de documento
     */
    @EJB
    private DocumentTypesFacadeBeanLocal documentTypesBean;
    
    /**
     * Ofrece las operaciones relacionadas con los tipos de contrato que se asignan a
     * los empleados de un dealer
     */
    @EJB
    private ContractTypesFacadeBeanLocal contractTypesFacadeBean;
    
    /**
     * Ofrece las operaciones relacionadas con los posibles estados 
     * que puede tener un empleado de un dealer
     */
    @EJB
    private EmployeeStatusFacadeBeanLocal employeeStatusBean;
    
    /**
     * Ofrece las operaciones que permiten administrar la información
     * de los roles que tendrán los empleados dependiendo del dealer
     */
    @EJB
    private EmployeeRolFacadeBeanLocal employeeRolBean;
    
    /**
     * Ofrece el acceso a los medios de contacto, que serán usados para 
     * el registro de información de los empleados
     */
    @EJB
    private MediaContactTypesFacadeBeanLocal mediaContactTypesBean;
    
    /**
     * ofrece acceso a los tipos de entrenamineto que el dealer ofrecerá a los
     * empleados
     */
    @EJB
    private TrainingFacadeBeanLocal trainingFacadeBean;
    
    /**
     * Ofrece el acceso a los tipos de entrenamiento
     */
    @EJB
    private TrainingTypesFacadeBeanLocal trainingTypesFacadeBean;
    
    /**
     * Ofrece el acceso a los estados civiles
     */
    @EJB
    private MaritalStatusFacadeBeanLocal maritalStatusFacadeBean;
    
    /**
     * Ofrece el acceso a los niveles de educacion
     */
    @EJB
    private EducationLevelFacadeBeanLocal educationLevelFacadeBean;
    

    /**
     * Metodo: Permite la creación de un nuevo empleado
     * @param eVo - EmployeeVO objeto que contiene la información del empleado que se creará
     * @throws BusinessException En caso de error al tratar de crear el empleado <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_object_already_exist</code> En caso de un error al tratar de crear un objeto ya existente<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "createEmployee", action = "createEmployee")
    public void createEmployee(@WebParam(name = "pVo") EmployeeVO eVo) throws BusinessException {
        ejbEmployeeBean.createEmployee(eVo);
    }

    /**
     * Metodo: Obtiene la información de un empleado dado su identificador
     * @param id - Long identificador del empleado cuya información se desea consultar
     * @return EmployeeVO Objeto con la información del empleado, nulo en caso que no exista el empleado
     * con el identificador especificado.
     * @throws BusinessException En caso de errror al tratar de consultar la información del empleado <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getEmployeeByID", action = "getEmployeeByID")
    public EmployeeVO getEmployeeByID(@WebParam(name = "id") Long id) throws BusinessException {
        return ejbEmployeeBean.getEmployeeByID(id);
    }

    /**
     * 
     * Metodo: Borra la información de un empleado
     * @param eVo - EmployeeVO objeto con la información del empleado que será borrado, solo se requiere que 
     * la propiedad id tenga el número de identificación del registro que desea ser eliminado
     * @throws BusinessException En caso de error al tratar de borrar la información del empleado <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "deleteEmploye", action = "deleteEmploye")
    public void deleteEmploye(@WebParam(name = "pVo") EmployeeVO eVo) throws BusinessException {
        ejbEmployeeBean.deleteEmployee(eVo);
    }

    /**
     * Metodo: Actualiza la información de un empleado
     * @param eVo - EmployeeVO información del empleado a ser actualizado, es posible modificar cualquier propiedad
     * excepto el identificador ya que este determina cual empleado debe ser actualizado
     * @throws BusinessException En caso de error al tratar de actualizar la información del empleado <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "updateEmployee", action = "updateEmployee")
    public void updateEmployee(@WebParam(name = "pVo") EmployeeVO eVo) throws BusinessException {
        ejbEmployeeBean.updateEmployee(eVo);
    }

    /**
     * Metodo: Operación para obtener la información de todos los empleados registrados en el sistema
     * @return List<EmployeeVO> Lista con la información de todos los empleados del sistema, una lista vacía en caso que no
     * exista información de empleados
     * @throws BusinessException En caso de error al tratar de obtener la información de los empleados <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getAllEmployee", action = "getAllEmployee")
    public List<EmployeeVO> getAllEmployee() throws BusinessException {
        return ejbEmployeeBean.getAllEmployee();
    }
    
    /**
     * Metodo: Operación para obtener la información de los empleados, dado el identificador del tipo
     * de documento
     * @param typeId - Long identificador del tipo de documento por el que se filtrarán los empleados
     * @return List<EmployeeVO> Lista con los empleados cuyo tipo de documento de identificación, coincide con el tipo
     * de documento especificado; vacio en otro caso
     * @throws BusinessException En caso de error al tratar de obtener la información de los 
     * empleados por el tipo de documento <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getEmployeeByDocumentTypeId", action = "getEmployeeByDocumentTypeId")
    public List<EmployeeVO> getEmployeeByDocumentTypeId(@WebParam(name = "typeId")Long typeId) throws BusinessException {
        return ejbEmployeeBean.getEmployeeByDocumentTypeId(typeId);
    }
    
    /**
     * Metodo: Obtiene la lista de empleados cuyo número de documento coincide con el
     * especificado por el usuario
     * @param documentNumber - String número de documento de identificación
     * @return List<EmployeeVO> Lista con la información de los empleados. ; vacio si no existen
     * @throws BusinessException En caso de error al tratar de obtener de los empleados por el número
     * de identificación <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getEmployeeByDocumentNumber", action = "getEmployeeByDocumentNumber")
    public List<EmployeeVO> getEmployeeByDocumentNumber(@WebParam(name = "documentNumber")String documentNumber) throws BusinessException {
        return ejbEmployeeBean.getEmployeeByDocumentNumber(documentNumber);
    }
    
    /**
     * Metodo: Obtiene una lista de empleados cuyos nombres coinciden con el especificado
     * @param firstName - String nombre por el que se realizará el filtro para obtener los empleados
     * @return List<EmployeeVO> Lista de empleados cuyo primero nombre coincide con el especificado; ; vacio si no existen
     * @throws BusinessException En caso de error al tratar de obtener la información de los
     * empleados <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getEmployeeByFirstName", action = "getEmployeeByFirstName")
    public List<EmployeeVO> getEmployeeByFirstName(@WebParam(name = "firstName")String firstName) throws BusinessException {
        return ejbEmployeeBean.getEmployeeByFirstName(firstName);
    }
    
    /**
     * Metodo: Permite obtener una lista de los empleados cuyos apellidos coinciden con los especificados
     * @param lastName - String cadena que filtrará el apellido de los empleados a ser consultados
     * @return List<EmployeeVO> Lista con la información de los empleados cuyo apellido concuerda con el especificado. vacio si no existen
     * @throws BusinessException en caso de error al tratar de obtenerla información de los empleados con
     * ese apellido <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getEmployeeByLastName", action = "getEmployeeByLastName")
    public List<EmployeeVO> getEmployeeByLastName(@WebParam(name = "lastName")String lastName) throws BusinessException {
        return ejbEmployeeBean.getEmployeeByLastName(lastName);
    }
    
    /**
     * 
     * Metodo: Consulta la información de los empleados cuyo tipo de identificación,
     * número de identificación, primer nombre y apellidos concuerdan con los especificados
     * @param typeId - Long tipo de identificación de los empleados
     * @param String - documentNumber Número de identificación por el cual se realizará la consulta
     * @param String - firstName nombre del empleado por el que se realizará la consulta
     * @param String - lastName apellido del empleado
     * @return List<EmployeeVO> Lista con los empleados; vacio si no existen
     * @throws BusinessException En caso de error al ejecutar la operación <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getEmployeeByDocTypeIdAndDocNumberAndFirstNAndLastN", action = "getEmployeeByDocTypeIdAndDocNumberAndFirstNAndLastN")
    public List<EmployeeVO> getEmployeeByDocTypeIdAndDocNumberAndFirstNAndLastN(@WebParam(name = "typeId")Long typeId,@WebParam(name = "documentNumber")String documentNumber,@WebParam(name = "firstName")String firstName,@WebParam(name = "lastName")String lastName) throws BusinessException {
        return ejbEmployeeBean.getEmployeeByDocTypeIdAndDocNumberAndFirstNAndLastN(typeId, documentNumber, firstName, lastName);
    }
    
    /**
     * 
     * Metodo: Consulta la información de los empleados cuyo tipo de identificación,
     * número de identificación, primer nombre, apellidos, depotCode y dealerId concuerdan con los especificados (pueden enviarse null)
     * @param typeId - Long tipo de identificación de los empleados
     * @param String - documentNumber Número de identificación por el cual se realizará la consulta
     * @param String - firstName nombre del empleado por el que se realizará la consulta
     * @param String - lastName apellido del empleado
     * @return List<EmployeeVO> Lista con los empleados; vacio si no existen
     * @throws BusinessException En caso de error al ejecutar la operación <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
     */
    @WebMethod(operationName = "getEmployeeByChriteria", action = "getEmployeeByChriteria")
    public EmployeePaginationResponse getEmployeeByChriteria(@WebParam(name = "typeId")Long typeId,@WebParam(name = "documentNumber")String documentNumber,
    		@WebParam(name = "firstName")String firstName,@WebParam(name = "lastName")String lastName, 
    		@WebParam(name = "depotCode")String depotCode, @WebParam(name = "dealerCode")Long dealerCode, @WebParam(name = "dealerId")Long dealerId,
    		@WebParam(name = "countryId")Long countryId,@WebParam(name = "requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException {
        return ejbEmployeeBean.getEmployeeByChriteria(typeId, documentNumber, firstName, lastName, depotCode, dealerCode,dealerId,countryId,requestCollInfo);
    }

    /**
     * Metodo: Genera el retiro de un empleado
     * @param obj - EmployeeRetirementVO información del retiro del empleado
     * @param statusId - long identificador del estado del empleado
     * @throws BusinessException en caso de error al tratar de ejecutar la operación <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "createEmployeeRetirement", action = "createEmployeeRetirement")
    public void createEmployeeRetirement(@WebParam(name = "obj") EmployeeRetirementVO obj, @WebParam(name = "eSVO") long statusId) throws BusinessException {
        ejbEmployeeRetirementBean.createEmployeeRetirement(obj, statusId);
    }

    /**
     * 
     * Metodo: Obtiene la información del retiro de un empleado por identificador
     * @param id - Long identificador del registro de retiro del empleado
     * @return EmployeeRetirementVO Información del retiro del empleado; nulo en caso de no existir
     * @throws BusinessException En caso de error al ejecutar la operación <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getEmployeeRetirementByID", action = "getEmployeeRetirementByID")
    public EmployeeRetirementVO getEmployeeRetirementByID(@WebParam(name = "id") Long id) throws BusinessException {
        return ejbEmployeeRetirementBean.getEmployeeRetirementByID(id);
    }

    /**
     * Metodo: Actualiza la información del retiro de un empleado
     * @param obj - EmployeeRetirementVO objeto de información de retiro del empleado a ser actualizado
     * @throws BusinessException en caso de error  <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "updateEmployeeRetirement", action = "updateEmployeeRetirement")
    public void updateEmployeeRetirement(@WebParam(name = "obj") EmployeeRetirementVO obj) throws BusinessException {
        ejbEmployeeRetirementBean.updateEmployeeRetirement(obj);
    }

    /**
     * Metodo: Borra la información del retiro de un empleado
     * @param obj - EmployeeRetirementVO información de retiro del empleado a ser borrada
     * @throws BusinessException en caso de error al borrar la información de retiro <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "deleteEmployeeRetirement", action = "deleteEmployeeRetirement")
    public void deleteEmployeeRetirement(@WebParam(name = "obj") EmployeeRetirementVO obj) throws BusinessException {
        ejbEmployeeRetirementBean.deleteEmployeeRetirement(obj);
    }

    /**
     * Metodo: Obtiene la información de todos los retiros de empleados en el sistema
     * @return List<EmployeeRetirementVO> lista con la información de todos los retiros de los empleados; vacio si no existen
     * @throws BusinessException en caso de error al consultar todos los retiros <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getAllEmployeeRetirement", action = "getAllEmployeeRetirement")
    public List<EmployeeRetirementVO> getAllEmployeeRetirement() throws BusinessException {
        return ejbEmployeeRetirementBean.getAllEmployeeRetirement();
    }

    /**
     * Metodo: Obtiene la información de empleados por identificador de dealer
     * @param id - Long identificador del dealer por el que se realizará el filtro
     * @return List<EmployeeVO> Lista con los empleados asociados a ese dealer; vacio si no existen
     * @throws BusinessException en caso de error al consultar la información de los
     * empleados <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getEmployeesByDealerId", action = "getEmployeesByDealerId")
    public List<EmployeeVO> getEmployeesByDealerId(@WebParam(name = "id")
            final Long id) throws BusinessException {
        return ejbEmployeeBean.getEmployeesByDealerId(id);
    }

    /**
     * Metodo: Obtiene la información de empleados activos por identificador de dealer
     * @param id - Long identificador del dealer por el que se realizará el filtro
     * @return List<EmployeeVO> Lista con los empleados activos asociados a ese dealer; vacio si no existen
     * @throws BusinessException en caso de error al consultar la información de los
     * empleados <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getEmployeesActiveByDealerId", action = "getEmployeesActiveByDealerId")
    public List<EmployeeVO> getEmployeesActiveByDealerId(@WebParam(name = "id")
            final Long id) throws BusinessException {
        return ejbEmployeeBean.getEmployeesActiveByDealerId(id);
    }
    
    /**
     * 
     * Metodo: Obtiene la información de todos los tipos de documento por país
     * @param countryId - Long identificador del país
     * @return List<DocumentTypeVO> Lista con los tipos de documentos de un país; vacio si no existen
     * @throws BusinessException en caso de error al tratar de consultar la información
     * de los tipos de documento <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getAllDocumentTypesByCountryId", action = "getAllDocumentTypesByCountryId")
    public List<DocumentTypeVO> getAllDocumentTypesByCountryId(@WebParam(name = "countryId")Long countryId) throws BusinessException {
        return documentTypesBean.getAllDocumentTypesByCountryId(countryId);
    }

    /**
     * Metodo: Obtiene todos los tipos de contrato del sistema
     * @return List<ContractTypeVO> Lista con los tipos de contrato; vacio si no existen
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de todos los tipos de contrato<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getAllContractTypes", action = "getAllContractTypes")
    public List<ContractTypeVO> getAllContractTypes() throws BusinessException {
    	return contractTypesFacadeBean.getAllContractTypes();
    }
    
    /**
     * 
     * Metodo: Obtiene los tipos de contrato por cada país
     * @param countryId - Long identificador del país
     * @return List<ContractTypeVO> Lista con los tipos de contrato; vacio si no existen; vacio si no existen
     * @throws BusinessException en caso de error al ejecutar la operación de consulta todos los tipos de contrato asociados
     * a un país <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getAllContractTypesByCountryId", action = "getAllContractTypesByCountryId")
    public List<ContractTypeVO> getAllContractTypesByCountryId(@WebParam(name = "countryId")Long countryId) throws BusinessException {
        return contractTypesFacadeBean.getAllContractTypesByCountryId(countryId);
    }

    /**
     * Metodo: Obtiene la información de un empleado
     * @param idDocType - Long identificador del tipo de documento
     * @param documentNumber - String número de documento
     * @return EmployeeVO objeto con la información del empleado cuyo tipo de documento y documento son los especificados; nulo en caso de no existir
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de empleado
     * por los parámetros especificados<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getEmployeeByDocTypeAndDocNum", action = "getEmployeeByDocTypeAndDocNum")
    public EmployeeVO getEmployeeByDocTypeAndDocNum(@WebParam(name = "idDocType")
            final Long idDocType, @WebParam(name = "documentNumber")
            final String documentNumber) throws BusinessException {
        return this.ejbEmployeeBean.getEmployeeByDocTypeAndDocNum(idDocType, documentNumber);
    }
    
    /**
     * 
     * Metodo: Obtiene la información de un empleado
     * @param idDocType - Long identificador del tipo de documento
     * @param documentNumber - String número de documento
     * @param dealerId - Long identificador del dealer
     * @return EmployeeVO información del empleado cuyo tipo de documento, codumento y dealer son los 
     * especificados; nulo en caso de no existir
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de empleado por los parámetros especificados <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getEmployeeByDocTypeAndDocNumByDealer", action = "getEmployeeByDocTypeAndDocNumByDealer")
    public EmployeeVO getEmployeeByDocTypeAndDocNumByDealer(
    		@WebParam(name = "idDocType")final Long idDocType, 
    		@WebParam(name = "documentNumber")final String documentNumber,
    		@WebParam(name = "dealerId")final Long dealerId) throws BusinessException {
        return this.ejbEmployeeBean.getEmployeeByDocTypeAndDocNum(idDocType, documentNumber, dealerId);
    }

    /**
     * 
     * Metodo: Obtiene la lista de los posibles estados para los empleados
     * @return List<EmployeeStatusVO> lista de los posibles estados para los empleados; vacio si no existen
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de los Estados de empleado<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getAllEmployeeStatus", action = "getAllEmployeeStatus")
    public List<EmployeeStatusVO> getAllEmployeeStatus() throws BusinessException {
        return employeeStatusBean.getAllEmployeeStatus();
    }

    /**
     * 
     * Metodo: Obtiene la información del rol de un empleado
     * @param code - String código del rol del empleado
     * @return EmployeeRolVO información del rol de un empleado; nulo en caso de no existir
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de un rol de empleado por su código<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getEmployeeRolByCode", action="getEmployeeRolByCode")
    public EmployeeRolVO getEmployeeRolByCode(@WebParam(name = "code")
    String code) throws BusinessException {
        return employeeRolBean.getEmployeeRolByCode(code);
    }

    /**
     * 
     * Metodo: Obtiene la información de un rol por identificador
     * @param id - Long identificador del rol a consultar
     * @return EmployeeRolVO información del rol consultado, nulo en caso que no se encuentre
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de un rol de empleado por su identificador<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getEmployeeRolByID", action="getEmployeeRolByID")
    public EmployeeRolVO getEmployeeRolByID(@WebParam(name = "id")
    Long id) throws BusinessException {
        return employeeRolBean.getEmployeeRolByID(id);
    }

    /**
     * 
     * Metodo: Obtiene todos los roles de empleados registrados en el sistema
     * @return List<EmployeeRolVO> lista con la información de los roles; vacio si no existen
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de todos los roles de empleados<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getAllEmployeeRol", action="getAllEmployeeRol")
    public List<EmployeeRolVO> getAllEmployeeRol() throws BusinessException {
        return employeeRolBean.getAllEmployeeRol();
    }
    
    /**
     * 
     * Metodo: realiza el retiro de un empleado
     * @param employeeVO - EmployeeVO información del empleado a ser retirado
     * @param description - String descripción de la causa del retiro
     * @throws BusinessException en caso de error al ejecutar la operación de retiro del empleado<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "retireEmployee", action="retireEmployee")
    public void retireEmployee(@WebParam(name="employeeVO") EmployeeVO employeeVO, @WebParam(name="description") String description) throws BusinessException {
        this.ejbEmployeeBean.retireEmployee(employeeVO, description);
    }
    
    /**
     * 
     * Metodo: Obtiene los tipos de contacto por identificador
     * @param id - Long identificador de los tipos de contacto
     * @return MediaContactTypeVO información del tipo de contacto cuyo id coicide con el especificado; nulo en caso de no existir
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de tipo de medio de contacto dado su identificador <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getMediaContactTypesByID", action="getMediaContactTypesByID")
    public MediaContactTypeVO getMediaContactTypesByID(@WebParam(name="id")Long id) throws BusinessException{
		
			return this.mediaContactTypesBean.getMediaContactTypesByID(id);
		
    }
    
    /**
     * 
     * Metodo: Obtiene todos los medios de contacto registrados en el sistema
     * @return List<MediaContactTypeVO> Lista con los medios de contacto registrados en el sistema; vacio si no existen
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de los Tipos de Medio de Contacto <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getAllMediaContactTypes", action="getAllMediaContactTypes")
	public List<MediaContactTypeVO> getAllMediaContactTypes() throws BusinessException{
    	
			return this.mediaContactTypesBean.getAllMediaContactTypes();
		
	}
    
    /**
     * 
     * Metodo: Obtiene la información de todos los tipos de entrenamiento
     * @return List<TrainingVO> Lista con los entrenamientos para los empleados; vacio si no existen
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de todos los TrainingTypes<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getAllTrainings", action="getAllTrainings") 
    public List<TrainingVO> getAllTrainings() throws BusinessException{
    		return this.trainingFacadeBean.getAllTraining();
    }
    
    /**
     * 
     * Metodo: Obtiene la información de capacitaciones para un empleado
     * @param idEmployee - Longidentificador del empleado al que se le consultará 
     * la información de capacitación
     * @return List<TrainingVO> lista con los entrenamientos que aplican a ese empleado; vacio si no existen
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de los TrainingTypes del identificador del empleado <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getTrainingsByEmployeeId", action="getTrainingsByEmployeeId") 
    public List<TrainingVO> getTrainingsByEmployeeId(@WebParam(name="idEmployee")Long idEmployee) throws BusinessException{

    		return this.trainingFacadeBean.getTrainingsByEmployeeId(idEmployee);
    }
    
    /**
     * 
     * Metodo: Obtiene una lista con todos los tipos de entrenamiento del sistema
     * @return List<TrainingTypeVO> lista con los tipos de entrenamiento que existen en el sistema; vacio si no existen
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de todos los TrainingTypes del sistema <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getAllTrainingTypes", action="getAllTrainingTypes")
	public List<TrainingTypeVO> getAllTrainingTypes() throws BusinessException {
		return trainingTypesFacadeBean.getAllTrainingTypes();
	}

    /**
     * 
     * Metodo: Obtiene una lista de tipos de entrenamiento por código
     * @param code - String código de los tipos de entrenamiento
     * @return TrainingType tipo de entrenamiento cuyo código coincide con el especificado; nulo en caso de no existir
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de TrainingTypes por su código<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getTrainingTypesByCode", action="getTrainingTypesByCode")
	public TrainingType getTrainingTypesByCode(@WebParam(name="code")String code) throws BusinessException {
		return trainingTypesFacadeBean.getTrainingTypesByCode(code);
	}

    /**
     * 
     * Metodo: Obtiene un tipo de entrenamiento por tipo de identificador
     * @param id - Long identificador del tipo de entrenamiento
     * @return TrainingType tipo de entrenamiento; nulo en caso de no existir
     * @throws BusinessException en caso de error al ejecutar la operación de consulta TrainingTypes por su identificador<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getTrainingTypesByID", action="getTrainingTypesByID")
	public TrainingType getTrainingTypesByID(@WebParam(name="id")Long id) throws BusinessException {
		return trainingTypesFacadeBean.getTrainingTypesByID(id);
	}

    /**
     * 
     * Metodo: Obtiene una lista de los tipos de entrenamiento por el país
     * @param countryId - Long identificador del país
     * @return List<TrainingTypeVO> lista con los tipos de entrenamiento del país especificado; vacio si no existen
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de todos los TrainingTypes 
     * por identificador de país<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getAllTrainingTypesByCountryId", action="getAllTrainingTypesByCountryId")
	public List<TrainingTypeVO> getAllTrainingTypesByCountryId(@WebParam(name="countryId")Long countryId)
			throws BusinessException {
		return trainingTypesFacadeBean.getAllTrainingTypesByCountryId(countryId);
	}
    
    /**
     * 
     * Metodo: Obtiene la información de los documentos de los empleados de un dealer específico
     * @param dealerID - Long identificador del dealer
     * @return List<EmployeeVO> lista con los documentos de los empleados; vacio si no existen
     * @throws BusinessException  en caso de error al ejecutar la operación de consulta de los documentos de los empleados
     * dado el dealer <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getEmployeesDocumentsByDealer", action="getEmployeesDocumentsByDealer")
	public List<EmployeeVO> getEmployeesDocumentsByDealer(@WebParam(name="dealerID")Long dealerID)
			throws BusinessException {
		return this.ejbEmployeeBean.getEmployeesDocumentsByDealer(dealerID);
	}
    
    /**
     * 
     * Metodo: Obtiene la informacion de un empleado a partir de su identificador
     * @param employeeId - Long identificador del empleado
     * @return EmployeeVO Informacion del empleado de acuerdo al identificador enviado 
     * @throws BusinessException  en caso de error al ejecutar la operación de consulta del empleado <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jnova
     */
    public EmployeeVO getEmployeeDetailsByEmployeeId(@WebParam(name="employeeId")Long employeeId) throws BusinessException{
    	return this.ejbEmployeeBean.getEmployeeDetailsByEmployeeId(employeeId);
    }
    
    /**
	 * Metodo: Obtiene todos los estados civiles
	 * @return Lista de estados civiles
	 * @throws BusinessException En caso de error en la consulta	 
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jnova
     */
    @WebMethod(operationName = "getAllMaritalStatuss", action="getAllMaritalStatuss")
    public List<MaritalStatusVO> getAllMaritalStatuss() throws BusinessException{
    	return this.maritalStatusFacadeBean.getAllMaritalStatuss();
    }
    
    /**
	 * Metodo: Obtiene todos los niveles de educacion
	 * @return Lista con los niveles de educacion
	 * @throws BusinessException En caso de error en la consulta
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jnova
     */
    @WebMethod(operationName = "getAllEducationLevel", action="getAllEducationLevel")
    public List<EducationLevelVO> getAllEducationLevel() throws BusinessException{
    	return this.educationLevelFacadeBean.getAllEducationLevel();
    }

    /**
	 * Metodo: Obtiene todos los empleados disponibles para ser asociados a una cuadrilla
	 * @return Lista con los empleados activos disponibles (que no tengan cuadrilla asignada, o que la cuadrilla a la que esta asignado se encuentre inactiva)
	 * @throws BusinessException En caso de error en la consulta
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jgonzmol
     */
    @WebMethod(operationName = "getEmployeesAviableByDealerId", action = "getEmployeesAviableByDealerId")
    public List<EmployeeVO> getEmployeesAviableByDealerId(@WebParam(name = "id")
            final Long id) throws BusinessException {
        return ejbEmployeeBean.getEmployeesAviableByDealerId(id);
    }

}
