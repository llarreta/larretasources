package co.com.directv.sdii.ws.business.dealers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.ProgramFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.ProgramStatusFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ProgramStatusVO;
import co.com.directv.sdii.model.vo.ProgramVO;

/**
 * Web Service que expone las operaciones
 * asociadas a Programas
 * 
 * Fecha de Creacion: 14/05/2010
 * @author jcasas <a href="mailto:jcasas@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@MTOM
@WebService
@Stateless
public class ProgramsWS {
	
	/**
	 * Referencia a la fachada que ofrece las operaciones de programas
	 */
	@EJB
	private ProgramFacadeBeanLocal programFacadeBean;
	
	@EJB
	private ProgramStatusFacadeBeanLocal statusFacadeBean;
	
	/**
	 * Método: Obtiene todos los programas registrados en el sistema
	 * @return List<ProgramVO> lista con todos los programas registrados en el sistema; vacio en caso de no existir
	 * @throws BusinessException en caso de error al ejecutar la operación de consutla de todos los programas registradosr<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jcasas
	 */
	@WebMethod(action="getAllPrograms", operationName="getAllPrograms")
	public List<ProgramVO> getAllPrograms() throws BusinessException{
		return programFacadeBean.getAll();
	}
	
	/**
	 * Método: Obtiene todos los programas de un dealer especifico
	 * @param dealerId - Long identificador del dealer al que se le consultarán 
	 * los programas
	 * @param getAll - boolean Indica si tiene que traer todos los programas o solo los activos: true si trae todos
	 * false si solo los activos
	 * @return List<ProgramVO> Lista con la información de los programas asociados al dealer especificado; vacio en
	 * otro caso
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta lista de programas por Dealer<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jcasas
	 */
	@WebMethod(action="getAllProgramsByDealerId", operationName="getAllProgramsByDealerId")
	public List<ProgramVO> getProgramsByDealerId(@WebParam(name="dealerId")Long dealerId,@WebParam(name="getAll")boolean getAll) throws BusinessException{
		return programFacadeBean.getProgramsByDealerId(dealerId,getAll);
	}
	
	/**
	 * Método: Obtiene una lista de los programas dado el nombre y el identificador del dealer
	 * @param dealerId - Long identificador del dealer
	 * @param name - String nombre de los programas
	 * @return List<ProgramVO> Lista con los programas cuyo nombre es el especificado y está asociados al dealer 
	 * especificado; vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de programas por su nombre y dealer<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jcasas
	 */
	@WebMethod(action="getProgramsByNameAndDealerId", operationName="getProgramsByNameAndDealerId")
	public List<ProgramVO> getProgramsByNameAndDealerId(@WebParam(name="dealerId") Long dealerId, @WebParam(name="name") String name) throws BusinessException{
		return programFacadeBean.getProgramsByNameAndDealerId(dealerId, name);
	}
	
	/**
	 * Método: Crea un programa en el sistema
	 * @param obj - ProgramVO Objeto con la informacion basica del programa. Debe contener: <br>
	 * - programStatus <br>
	 * - programName <br>
	 * - programCode <br>
	 * - id de Dealer <br>
	 * - programDescription <br>
	 * @throws BusinessException en caso de error al ejecutar la operación creación de programa<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jcasas
	 */
	@WebMethod(action="createProgram", operationName="createProgram")
	public void createProgram(@WebParam(name="obj") ProgramVO obj) throws BusinessException {
		programFacadeBean.createProgram(obj);
	}

	/**
	 * Método: Obtiene un programa con el id especificado
	 * @param id - Long Id del programa
	 * @return ProgramVO Programa con el id especificado; nulo en otro caso
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de programa por su identificador<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jcasas
	 */
	@WebMethod(action="getProgramByID", operationName="getProgramByID")
	public ProgramVO getProgramByID(@WebParam(name="id") Long id) throws BusinessException {
		return programFacadeBean.getProgramByID(id);
	}

	/**
	 * Método: Permite actualizat un programa existente
	 * @param obj - ProgramVO Programa que se desea actualizar. Debe contener: <br>
	 * - id<br>
	 * - programStatus <br>
	 * - programName <br>
	 * - programCode <br>
	 * - id de Dealer <br>
	 * - programDescription <br>
	 * @throws BusinessException en caso de error al ejecutar la operación de actualización de programa<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jcasas
	 */
	@WebMethod(action="updateProgram", operationName="updateProgram")
	public void updateProgram(@WebParam(name="obj") ProgramVO obj) throws BusinessException {
		programFacadeBean.updateProgram(obj);
	}

	/**
	 * Método: Permite eliminar un programa
	 * @param obj - ProgramVO Programa que se desea eliminar. Debe contener: <br>
	 * - id<br>
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de programas por estado<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jcasas
	 */
	@WebMethod(action="deleteProgram", operationName="deleteProgram")
	public void deleteProgram(@WebParam(name="obj") ProgramVO obj) throws BusinessException {
		programFacadeBean.deleteProgram(obj);
	}

	/**
	 * Método: Obtiene todos los programas con el estado especificado
	 * @param statusId - Long Status del programa
	 * @return List<ProgramVO> Listado con los programas con el estado especificado; vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de posición por su código y dealer<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jcasas
	 */
	@WebMethod(action="getProgramsByStatusId", operationName="getProgramsByStatusId")
	public List<ProgramVO> getProgramsByStatusId(@WebParam(name="statusId") Long statusId) throws BusinessException {
		return programFacadeBean.getProgramsByStatusId(statusId);
	}

	/**
	 * Método: Obtiene un programa con el codigo especificado
	 * @param programVOCode - String Codigo de programa
	 * @return ProgramVO Programa cuyo código es el especificado; nulo en otro caso
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de programa por su código<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jcasas
	 */
	@WebMethod(action="getProgramsByCode", operationName="getProgramsByCode")
	public ProgramVO getProgramsByCode( @WebParam(name="programVOCode") String programVOCode) throws BusinessException {
		return programFacadeBean.getProgramsByCode(programVOCode);
	}

	/**
	 * Método: Obtiene los programas que contienen el nombre especificado
	 * @param name - String Nombre del programa
	 * @return List<ProgramVO> Listado de los programas con el nombre especificado: vacio en caso contrario
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de programas por nombre<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jcasas
	 */
	@WebMethod(action="getProgramsByName", operationName="getProgramsByName")
	public List<ProgramVO> getProgramsByName(@WebParam(name="name") String name)throws BusinessException {
		return programFacadeBean.getProgramsByName(name);
	}
	
	/**
	 * Método: Obtiene todos los estados de programa existentes en el sistema
	 * @return List<ProgramStatusVO> Listado con los estados de programa existentes en el sistema; vacio en caso de no existir
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta todos los estados de programa registrados<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jcasas
	 */
	@WebMethod(action="getAllProgramStatus", operationName="getAllProgramStatus")
	public List<ProgramStatusVO> getAllProgramStatus() throws BusinessException{
		return statusFacadeBean.getAll();
	}
	
	/**
	 * Método: Obtiene un estado de un programa con el id especificado
	 * @param statusId - Long Id del estado del programa
	 * @return ProgramStatusVO Estado de programa con el id especificado; nulo en caso contrario
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de estado de programa por su identificador<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jcasas
	 */
	@WebMethod(action="getProgramStatusByStatusId", operationName="getProgramStatusByStatusId")
	public ProgramStatusVO getProgramStatusByStatusId( @WebParam(name="statusId") Long statusId) throws BusinessException{
		return statusFacadeBean.getProgramStatusByID(statusId);
	}
}