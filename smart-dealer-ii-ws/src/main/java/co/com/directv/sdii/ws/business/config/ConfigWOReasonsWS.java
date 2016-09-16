package co.com.directv.sdii.ws.business.config;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigWOReasonsFacadeLocal;
import co.com.directv.sdii.model.vo.ResponsibleAreaVO;
import co.com.directv.sdii.model.vo.WorkorderReasonCategoryVO;
import co.com.directv.sdii.model.vo.WorkorderReasonTypeVO;
import co.com.directv.sdii.model.vo.WorkorderReasonVO;

/**
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del módulo de Configuración de Reasons de WO.
 *
 * Caso de Uso CFG - 05 - Gestionar Reasons en las Work Orders
 *
 * @author Jimmy Vélez Muñoz
 * @author jjimenezh
 */
@MTOM
@WebService()
@Stateless()
public class ConfigWOReasonsWS {
    
	@EJB
    private ConfigWOReasonsFacadeLocal ejbRef;
    
	/**
     * Metodo: Obtiene una razón de workorder dado su identificador
     * @param id identificador de la razón de la workorder
     * @return razón de workorder dado el identificador, nulo en caso que no exista
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "getWorkorderReasonByID", action="getWorkorderReasonByID")
    public WorkorderReasonVO getWorkorderReasonByID(@WebParam(name = "id")
    Long id) throws BusinessException {
        return ejbRef.getWorkorderReasonByID(id);
    }

    /**
     * Metodo: Obtiene la razón de workorder por código
     * @param code código de la razón de workorder
     * @return razón de work order dado el código, nulo en caso que no exista
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "getWorkorderReasonByCode", action="getWorkorderReasonByCode")
    public WorkorderReasonVO getWorkorderReasonByCode(@WebParam(name = "code")
    String code) throws BusinessException {
        return ejbRef.getWorkorderReasonByCode(code);
    }

    /**
     * Metodo: Obtiene una lista de razones de workorder dado el nombre
     * @param name nombre que será usado para filtrar las razones de workorder
     * @return lista con las razones de workorder cuyo nombre coincide con el especificado
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "getWorkorderReasonByName", action="getWorkorderReasonByName")
    public List<WorkorderReasonVO> getWorkorderReasonByName(@WebParam(name = "name")
    String name) throws BusinessException {
        return ejbRef.getWorkorderReasonByName(name);
    }

    /**
     * Metodo: Obtiene una lista de razones de workorder dada la categoría
     * @param category categoría de razones de workorder
     * @return lista con las razones de workorder que tienen asignada la categoría especificada
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "getWorkorderReasonByCategory", action="getWorkorderReasonByCategory")
    public List<WorkorderReasonVO> getWorkorderReasonByCategory(@WebParam(name = "category")
    WorkorderReasonCategoryVO category) throws BusinessException {
        return ejbRef.getWorkorderReasonByCategory(category);
    }

    /**
     * Metodo: obtiene las razones de workorder por el tipo de categoría especificado
     * @param category categoría para realizar el filtro
     * @param type tipo de categoría por el que se realizará el filtro
     * @return  lista de razones de orden de trabajo
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "getWorkorderReasonByCategoryType", action="getWorkorderReasonByCategoryType")
    public List<WorkorderReasonVO> getWorkorderReasonByCategoryType(@WebParam(name = "category")
    WorkorderReasonCategoryVO category, @WebParam(name = "type")
    WorkorderReasonTypeVO type) throws BusinessException {
        return ejbRef.getWorkorderReasonByCategoryType(category, type);
    }

    /**
     * Metodo: Obtiene todas las razones de workorder registradas en el sistema
     * @return lista de razones de work order que existen en el sistema
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "getAllWOReasons", action="getAllWOReasons")
    public List<WorkorderReasonVO> getAllWOReasons() throws BusinessException {
        return ejbRef.getAll();
    }

    /**
     * Metodo: crea una razón de work order en el sistema
     * @param obj razón de work order a ser creada en el sistema
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_object_already_exist</code> En caso que se intente crear una razón de work order con el código de una existente<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "createWorkorderReason", action="createWorkorderReason")
    public void createWorkorderReason(@WebParam(name = "obj")
    WorkorderReasonVO obj) throws BusinessException {
        ejbRef.createWorkorderReason(obj);
    }

    /**
     * Metodo: actualiza la información de una razón de workorder
     * @param obj razón de work order a ser actualizada en el sistema
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "updateWorkorderReason", action="updateWorkorderReason")
    public void updateWorkorderReason(@WebParam(name = "obj")
    WorkorderReasonVO obj) throws BusinessException {
        ejbRef.updateWorkorderReason(obj);
    }

    /**
     * Metodo: borra la razón de una work order
     * @param obj razón de workorder a ser borrada
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "deleteWorkorderReason", action="deleteWorkorderReason")
    public void deleteWorkorderReason(@WebParam(name = "obj")
    WorkorderReasonVO obj) throws BusinessException {
        ejbRef.deleteWorkorderReason(obj);
    }

    /**
     * Metodo: obtiene todos los tipos de razones de workorder
     * @return lista con todos los tipos de razones de workorder registrados en el sistema
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "getAllWOReasonTypes", action="getAllWOReasonTypes")
    public List<WorkorderReasonTypeVO> getAllWOReasonTypes() throws BusinessException{
        List<WorkorderReasonTypeVO> result = ejbRef.getAllWOReasonTypes();
        return result;
    }

    /**
     * Metodo: Obtiene todas las áreas responsables
     * @return lista con todas las áreas responsables registradas en el sistema, si no
     * existen se retorna una lista vacia
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "getAllResponsibleAreas", action="getAllResponsibleAreas")
    public List<ResponsibleAreaVO> getAllResponsibleAreas() throws BusinessException{
        List<ResponsibleAreaVO> result = ejbRef.getAllResponsibleAreas();
        return result;
    }

    /**
     * Metodo: obtiene todas las categoría de razones de work order que existen en el sistema
     * dado el tipo de razón
     * @param woReasonTypeId identificador del tipo de razón 
     * @return lista con las categorias de razón de work order
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "getAllWOReasonCategoriesByReasonTypeId", action="getAllWOReasonCategoriesByReasonTypeId")
    public List<WorkorderReasonCategoryVO> getWOReasonCategoriesByReasonTypeId(@WebParam(name = "woReasonTypeId") Long woReasonTypeId) throws BusinessException{
        List<WorkorderReasonCategoryVO> result = ejbRef.getWOReasonCategoriesByReasonTypeId(woReasonTypeId);
        return result;
    }

    /**
     * Metodo: obtiene todas las de razones de work order que existen en el sistema
     * dado el codigo de la categoría
     * @param categoryCode codigo de la categoria de razon 
     * @return lista con las razones de work order
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author jgonzmol
     */
    @WebMethod(operationName = "getWorkorderReasonByCategoryCode", action="getWorkorderReasonByCategoryCode")
    public List<WorkorderReasonVO> getWorkorderReasonByCategoryCode(@WebParam(name = "categoryCode") String categoryCode) throws BusinessException{
        List<WorkorderReasonVO> result = ejbRef.getWorkorderReasonByCategoryCode(categoryCode);
        return result;
    }

}
