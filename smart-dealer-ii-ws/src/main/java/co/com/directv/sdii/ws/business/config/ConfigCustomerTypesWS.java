/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.directv.sdii.ws.business.config;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigTiposClienteFacadeLocal;
import co.com.directv.sdii.model.vo.CustomerCategoryVO;
import co.com.directv.sdii.model.vo.CustomerClassTypeVO;
import co.com.directv.sdii.model.vo.CustomerClassVO;
import co.com.directv.sdii.model.vo.CustomerTypeVO;

/**
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del módulo de Configuración de Códigos de Tipos de Clientes.
 *
 * Caso de Uso CFG - 06 - Gestionar Códigos de Tipos de Clientes
 *
 * @author Jimmy Vélez Muñoz
 * @author jjimenezh
 * 
 * @see co.com.directv.sdii.facade.config.ConfigTiposClienteFacadeLocal
 */
@MTOM
@WebService()
@Stateless()
public class ConfigCustomerTypesWS {
	/**
	 * Referencia a la fachada que ofrece el acceso a las operaciones de los tipos de cliente
	 */
    @EJB
    private ConfigTiposClienteFacadeLocal ejbRef;

    /** 
     * Metodo: Obtiene el tipo de cliente por el identificador
     * @param id identificador del tipo de cliente
     * @return objeto con la información del tipo de cliente, nulo en caso que no se encuentre el objeto por el identificador
     * especificado
     * @throws BusinessException en caso de error al ejecutar la operación,
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
    @WebMethod(operationName = "getCustomerCodeTypeByID", action="getCustomerCodeTypeByID")
    public CustomerClassTypeVO getCustomerCodeTypeByID(@WebParam(name = "id")
    Long id) throws BusinessException {
        return ejbRef.getCustomerClassTypeByID(id);
    }

    @WebMethod(operationName = "getCustomerTypeByID", action="getCustomerTypeByID")
    public CustomerTypeVO getCustomerTypeByID(@WebParam(name = "id")
    Long id) throws BusinessException {
        return ejbRef.getCustomerTypeByID(id);
    }
    
    @WebMethod(operationName = "getCustomerClassTypeByID", action="getCustomerClassTypeByID")
    public CustomerClassTypeVO getCustomerClassTypeByID(@WebParam(name = "id")
    Long id) throws BusinessException {
        return ejbRef.getCustomerClassTypeByID(id);
    }
    
    @WebMethod(operationName = "getCustomerClassByID", action="getCustomerClassByID")
    public CustomerClassVO getCustomerClassByID(@WebParam(name = "id")
    Long id) throws BusinessException {
        return ejbRef.getCustomerClassByID(id);
    }
    
    
    
    /**
     * Metodo: Obtiene el código de tipo cliente por el nombre
     * @param name nombre del código de tipo cliente
     * @return lista con objetos cuyo nombre coincide con el especificado, una lista vacia en
     * caso que no existan tipos de cliente por el nombre especificado
     * @throws BusinessException en caso de error al ejecutar la operación,
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
    @WebMethod(operationName = "getCustomerCodeTypeByNameType", action="getCustomerCodeTypeByNameType")
    public List<CustomerClassTypeVO> getCustomerCodeTypeByNameType(@WebParam(name = "name")
    String name, @WebParam(name = "countryId")Long countryId) throws BusinessException {
        return ejbRef.getCustomerClassTypeByNameType(name, countryId);
    }

    @WebMethod(operationName = "getCustomerCodeTypeByNameClass", action="getCustomerCodeTypeByNameClass")
    public List<CustomerClassTypeVO> getCustomerCodeTypeByNameClass(@WebParam(name = "name")
    String name, @WebParam(name = "countryId")Long countryId) throws BusinessException {
        return ejbRef.getCustomerClassTypeByNameClass(name, countryId);
    }
    
    /**
     * Metodo: obtiene un código de tipo cliente por el código
     * @param code código de tipo cliente
     * @return lista con los objetos cuyo código coincide con el especificado, una lista vacia en caso
     * que no existan tipos de cliente con el código especificado
     * @throws BusinessException en caso de error al ejecutar la operación,
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
    @WebMethod(operationName = "getCustomerClassTypeByCode", action="getCustomerClassTypeByCode")
    public CustomerClassTypeVO getCustomerClassTypeByCode(@WebParam(name = "codeType")
    String codeType, @WebParam(name = "codeClass") String codeClass, @WebParam(name = "countryId") Long countryId) throws BusinessException {
        return ejbRef.getCustomerClassTypeByCode(codeType, codeClass, countryId);
    }

    /**
     * Metodo: Obtiene un código de tipo de cliente por el 
     * @param customerClass clase de cliente por la cual se realizará el filtro de tipos de cliente
     * @return lista con la información de códigos de tipo cliente, una lista vacia en caso que no se
     * encuentren tipos de cliente por la clase de cliente especificada
     * @throws BusinessException en caso de error al ejecutar la operación,
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
    @WebMethod(operationName = "getCustomerClassTypeByCustomerClass", action="getCustomerClassTypeByCustomerClass")
    public List<CustomerClassTypeVO> getCustomerClassTypeByCustomerClass(@WebParam(name = "customerClass")
    CustomerClassVO customerClass) throws BusinessException {
        return ejbRef.getCustomerClassTypeByCustomerClass(customerClass);
    }

    /**
     * Metodo: obtiene todos los códigos de tipo de cliente
     * @return Lista con los códigos de tipos de cliente que existen en el sistema, una lista vacia en caso que no
     * exista ningún tipo de cliente
     * @throws BusinessException en caso de error al ejecutar la operación,
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
    @WebMethod(operationName = "getAllCustomerClassType", action="getAllCustomerClassType")
    public List<CustomerClassTypeVO> getAllCustomerClassType(@WebParam(name = "countryId") Long countryId) throws BusinessException {
        return ejbRef.getAll(countryId);
    }
    
    @WebMethod(operationName = "getAllCustomerType", action="getAllCustomerType")
    public List<CustomerTypeVO> getAllCustomerType(@WebParam(name = "countryId") Long countryId) throws BusinessException {
        return ejbRef.getAllCustomerTypes(countryId);
    }
    
    @WebMethod(operationName = "getAllCustomerCategory", action="getAllCustomerCategory")
    public List<CustomerCategoryVO> getAllCustomerCategory() throws BusinessException {
        return ejbRef.getAllCustomerCategory();
    }

    /**
     * Req-0096 - Requerimiento Consolidado Asignador
     * 
     * Metodo: Obtiene categorias de clientes
     * @return lista con las categorias de cliente definidas en la propiedad CUSTOMER_CATEGORY_DEALER_CONF
     * , una lista vacia en caso que no exista ninguna categoría de cliente
     * @throws BusinessException en caso de error al ejecutar la operación,
     * @author ialessan
     */

    @WebMethod(operationName = "getCustomerCategoryForDealerConf", action="getCustomerCategoryForDealerConf")
    public List<CustomerCategoryVO> getCustomerCategoryForDealerConf(@WebParam(name = "countryId") Long countryId) throws BusinessException {
    	return ejbRef.getCustomerCategoryForDealerConf(countryId);
    }        
    
    /**
     * Metodo: Obtiene todas las clases de clientes
     * @return lista con todas las clases de cliente, una lista vacia en caso que no exista ninguna clase de cliente
     * @throws BusinessException en caso de error al ejecutar la operación,
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
    @WebMethod(operationName = "getAllCustomerClass", action="getAllCustomerClass")
    public List<CustomerClassVO> getAllCustomerClass(@WebParam(name = "countryId") Long countryId) throws BusinessException {
        return ejbRef.getAllCustomerClass(countryId);
    }
    

    @WebMethod(operationName = "getAllCustomerClassByCategoryId", action="getAllCustomerClassByCategoryId")
    public List<CustomerClassVO> getAllCustomerClassByCategoryId(@WebParam(name = "countryId") Long countryId, @WebParam(name = "categoryId") Long categoryId) throws BusinessException {
        return ejbRef.getAllCustomerClassByCategoryId(countryId,categoryId);
    }
    
    /**
     * Metodo: Crea un código de tipo cliente
     * @param obj código de tipo cliente a persistir, la propiedad id no debe venir especificada en el objeto,
     * de lo contrario se arrojará una excepción.
     * @throws BusinessException en caso de error al ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_error_already_exists</code> En caso que ya exista un tipo de cliente con el código especificado<br>
	 * 
     * @author jjimenezh
     */
    @WebMethod(operationName = "createCustomerClassType", action="createCustomerClassType")
    public void createCustomerClassType(@WebParam(name = "obj")
    CustomerClassTypeVO obj) throws BusinessException {
        ejbRef.createCustomerClassType(obj);
    }

    @WebMethod(operationName = "createCustomerType", action="createCustomerType")
    public void createCustomerType(@WebParam(name = "obj")
    CustomerTypeVO obj) throws BusinessException {
        ejbRef.createCustomerType(obj);
    }
    
    /**
     * Metodo: actualiza un código de tipo cliente
     * @param obj código de tipo cliente a ser actualizado, la propiedad id debe venir especificada en el objeto,
     * de lo contrario se creará un nuevo tipo de cliente.
     * @throws BusinessException en caso de error al ejecutar la operación,
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
    @WebMethod(operationName = "updateCustomerClassType", action="updateCustomerClassType")
    public void updateCustomerClassType(@WebParam(name = "obj")
    CustomerClassTypeVO obj) throws BusinessException {
        ejbRef.updateCustomerClassType(obj);
    }

    /**
     * Metodo: borra la información de un código de tipo cliente
     * @param obj código de tipo cliente a ser borrado, debe venir la propiedad id especificada, de lo contrario no se
     * borrará ningún tipo de cliente.
     * @throws BusinessException en caso de error al ejecutar la operación,
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
    @WebMethod(operationName = "deleteCustomerClassType", action="deleteCustomerClassType")
    public void deleteCustomerClassType(@WebParam(name = "obj")
    CustomerClassTypeVO obj) throws BusinessException {
        ejbRef.deleteCustomerClassType(obj);
    }


    @WebMethod(operationName = "updateCustomerType", action="updateCustomerType")
    public void updateCustomerType(@WebParam(name = "obj")
    CustomerTypeVO obj) throws BusinessException {
        ejbRef.updateCustomerType(obj);
    }
    
    @WebMethod(operationName = "getCustomerTypeByCode", action="getCustomerTypeByCode")
    public CustomerTypeVO getCustomerTypeByCode(@WebParam(name = "code")
    String code, @WebParam(name = "countryId")Long countryId) throws BusinessException {
    	return ejbRef.getCustomerTypeByCode(code, countryId);
    }
    
}
