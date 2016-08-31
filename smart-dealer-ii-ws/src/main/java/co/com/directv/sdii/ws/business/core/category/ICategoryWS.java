package co.com.directv.sdii.ws.business.core.category;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.CategoryVO;

/**
 * Servicio web que expone las operaciones relacionadas con Contact
 * 
 * Fecha de Creación: Oct 7, 2014
 * @author ssanabri
 * @version 1.0
 * 
 */
@WebService(name="ICategoryWS",targetNamespace="http://directvla.com.contract/ws/sdii/Category")
public interface ICategoryWS {

	/**
	 * Metodo: Obtiene la información de todos los categorias padres del sistema.
	 * @return Lista con los Category existentes, una lista vacia en caso que no existan Category en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación	
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_entity_not_found</code> En caso que no encuentre el tipo de dealer por el id especificado<br>
	 * @author ssanabri
	 */
	@WebMethod(operationName="getParentCategories", action="getParentCategories")
	public List<CategoryVO> getParentCategories() throws BusinessException;

	/**
	 * Metodo: Busca todas las categorias hijas para un padre.
	 * @param parentId el id de la categoria padre. Es obligatorio colocarlo, caso contrario se devolvera una BusinessException.
	 * @return Lista con los categories para un padre. una lista vacia en caso de no encontrar hijos para ese padre.
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación	
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_entity_not_found</code> En caso que no encuentre el tipo de dealer por el id especificado<br>
	 * @author ssanabri
	 */
	@WebMethod(operationName="getChildrenCategories", action="getChildrenCategories")
	public List<CategoryVO> getChildrenCategories(@WebParam(name="parentId") Long parentId) throws BusinessException;
	
}
