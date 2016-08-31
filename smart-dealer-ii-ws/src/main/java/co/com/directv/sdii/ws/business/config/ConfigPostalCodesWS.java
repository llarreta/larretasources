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
import co.com.directv.sdii.facade.dealers.PostalCodesFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.ZoneTypesFacadeBeanLocal;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ZoneTypeVO;

/**
 * Servicio web que expone las operaciones necesarias para la administración
 * de los códigos postales.
 * 
 * Fecha de Creación: Mar 15, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.dealers.PostalCodesFacadeBeanLocal
 * @see co.com.directv.sdii.facade.dealers.ZoneTypesFacadeBeanLocal
 * @see co.com.directv.sdii.facade.config.ConfigHabilidadesPorDealerFacadeLocal
 */
@MTOM
@WebService()
@Stateless()
public class ConfigPostalCodesWS {
	
	/**
	 * Ofrece las operaciones para los códigos postales
	 */
    @EJB
    private PostalCodesFacadeBeanLocal ejbRef;
    
    /**
     * Ofrece las operaciones para los tipos de zona
     */
    @EJB
    private ZoneTypesFacadeBeanLocal zoneTypesFacadeBean;
    
    /**
     * Metodo: Obtiene el código postal pos código
     * @param code código del código postal
     * @return código postal, o nulo en caso que no se encuentre
     * @throws BusinessException En caso de error al ejecutar la operación,
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
    @WebMethod(operationName = "getPostalCodesByCode" , action="getPostalCodesByCode")
    public PostalCodeVO getPostalCodesByCode(@WebParam(name = "code")String code) throws BusinessException {
        return ejbRef.getPostalCodesByCode(code);
    }
    
    /**
     * Metodo: Obtiene el código postal pos código
     * @param code código del código postal
     * @return código postal, o nulo en caso que no se encuentre
     * @throws BusinessException En caso de error al ejecutar la operación,
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
    @WebMethod(operationName = "getPostalCodeByCodeAndCountryCode" , action="getPostalCodeByCodeAndCountryCode")
    public PostalCodeVO getPostalCodeByCodeAndCountryCode(@WebParam(name = "code")String code, @WebParam(name = "countryCode")String countryCode) throws BusinessException {
        return ejbRef.getPostalCodeByCodeAndCountryCode(code, countryCode);
    }

    /**
     * Metodo: Obtiene el código postal por identificador
     * @param id identificador del código postal
     * @return código postal
     * @throws BusinessException En caso de error al ejecutar la operación,
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
    @WebMethod(operationName = "getPostalCodesByID" , action="getPostalCodesByID")
    public PostalCodeVO getPostalCodesByID(@WebParam(name = "id")Long id) throws BusinessException {
        return ejbRef.getPostalCodesByID(id);
    }

    /**
     * Metodo: obtiene todos los códigos postales
     * @return lista con todos los códigos postales
     * @throws BusinessException En caso de error al ejecutar la operación,
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
    @WebMethod(operationName = "getAllPostalCodes" , action="getAllPostalCodes")
    public List<PostalCodeVO> getAllPostalCodes() throws BusinessException {
        return ejbRef.getAllPostalCodes();
    }
    
    /**
     * Metodo: Obtiene todos los códigos postales por país
     * @param countryId identificador del país
     * @return lista con todos los códigos postales de ese país
     * @throws BusinessException En caso de error al ejecutar la operación,
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
    @WebMethod(operationName = "getAllPostalCodesByCountryId" , action="getAllPostalCodesByCountryId")
    public List<PostalCodeVO> getAllPostalCodesByCountryId(@WebParam(name = "cityId")Long countryId) throws BusinessException {
        return ejbRef.getAllPostalCodesByCountryId(countryId);
    }

    /**
     * Metodo: Obtiene los códigos postales por ciudad
     * @param cityId identificador de ciudad
     * @return
     * @throws BusinessException En caso de error al ejecutar la operación,
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
    @WebMethod(operationName = "getPostalCodesByCityId" , action="getPostalCodesByCityId")
    public List<PostalCodeVO> getPostalCodesByCityId(@WebParam(name = "cityId")Long cityId) throws BusinessException {
        return ejbRef.getPostalCodesByCityId(cityId);
    }

    /**
     * Metodo: obtiene los códigos postales por nombre
     * @param name nombre de los códigos postales
     * @return lista con los códigos postales
     * @throws BusinessException En caso de error al ejecutar la operación,
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
    @WebMethod(operationName = "getPostalCodesByName" , action="getPostalCodesByName")
    public List<PostalCodeVO> getPostalCodesByName(@WebParam(name = "name")String name) throws BusinessException {
        return ejbRef.getPostalCodesByName(name);
    }

    /**
     * Metodo: Crea un codigo postal
     * @param obj código postal a crear
     * @throws BusinessException En caso de error al ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia, o que no vengan los atributos obligatorios para la creación del objeto<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_object_already_exist</code> En caso que exista un código postal con el mismo código del que se trata de crear<br>
	 * 
     * @author jjimenezh
     */
    @WebMethod(operationName = "createPostalCode" , action="createPostalCode")
    public void createPostalCode(@WebParam(name = "postalCode")PostalCodeVO obj) throws BusinessException {
        ejbRef.createPostalCode(obj);
    }

    /**
     * Metodo: Actualiza un código postal
     * @param obj código postal a actualizar
     * @throws BusinessException En caso de error al ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia, o que no vengan los atributos obligatorios 
     * para la actualización del objeto<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "updatePostalCode" , action="updatePostalCode")
    public void updatePostalCode(@WebParam(name = "obj")PostalCodeVO obj) throws BusinessException {
        ejbRef.updatePostalCode(obj);
    }

    /**
     * Metodo: Borra un código postal
     * @param obj código postal a actualizar
     * @throws BusinessException En caso de error al ejecutar la operación,
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
    @WebMethod(operationName = "deletePostalCode" , action="deletePostalCode")
    public void deletePostalCode(@WebParam(name = "obj")PostalCodeVO obj) throws BusinessException {
        ejbRef.deletePostalCode(obj);
    }

    /**
     * Metodo: obtiene todos los tipos de zona
     * @return lista con los tipos de zona
     * @throws BusinessException En caso de error al ejecutar la operación,
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
    @WebMethod(operationName = "getAllZoneTypes" , action="getAllZoneTypes")
    public List<ZoneTypeVO> getAllZoneTypes() throws BusinessException{
        return zoneTypesFacadeBean.getAllZoneTypes();
    }    
}
