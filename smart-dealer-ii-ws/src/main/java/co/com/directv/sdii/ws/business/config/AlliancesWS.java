/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.directv.sdii.ws.business.config;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal;
import co.com.directv.sdii.model.vo.AllianceCompanyVO;
import co.com.directv.sdii.model.vo.AllianceFileVO;
import co.com.directv.sdii.model.vo.AllianceVO;
import co.com.directv.sdii.model.vo.ComercialProductVO;


/**
 * Expone los servicios para la configuraci�n de Alianzas
 * 
 * Fecha de Creación: 25/03/2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal   
 */
@MTOM
@WebService()
@Stateless()
public class AlliancesWS {
    @EJB
    private ConfigAlianzasFacadeLocal ejbRef;
    // Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

    /**
     * Metodo: Este método retorna una instancia de ALLIANCES por ID.
     * @param id - Long identificador de la alianza que se desea consultar
     * @return AllianceVO objeto alianza que encapsula la información de las alianzas,
     * nulo en caso que no se encuentre una alianza con el código especficicado
     * @throws BusinessException en caso de error al tratar de consultar la alianza por el identificador especificado  
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getAllianceByID", action="getAllianceByID")
    public AllianceVO getAllianceByID(@WebParam(name = "id")
    Long id) throws BusinessException {
        return ejbRef.getAllianceByID(id);
    }

    /**
     * Metodo: Este método retorna una instancia de ALLIANCES por ALLIANCE_CODE.
     * @param code - String código de la alianza que desea ser consultada
     * @return AllianceVO objeto alianza cuyo código coincide con el especificado, nulo en caso
     * que no se encuentre una alianza con el código consultado
     * @throws BusinessException  en caso de error al consultar las alianzas con el código
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getAllianceByCode", action="getAllianceByCode")
    public AllianceVO getAllianceByCode(@WebParam(name = "code")
    String code) throws BusinessException {
        return ejbRef.getAllianceByCode(code);
    }

    /**
     * Metodo: Este método retorna una lista de ALLIANCES por ALLIANCE_NAME.
     * @param name - String nombre de la alianza a ser consultada
     * @return List<AllianceVO> lista con las alianzas cuyo nombre coincide con el especificado
     * @throws BusinessException  En caso de error al consultar las alianzas por nombre
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getAllianceByName", action="getAllianceByName")
    public List<AllianceVO> getAllianceByName(@WebParam(name = "name")
    String name) throws BusinessException {
        return ejbRef.getAllianceByName(name);
    }

    /**
     * Metodo: Este método retorna una lista de Todas las ALLIANCES.
     * @return List<AllianceVO> lista con las alianzas existentes en el sistema, una lista vacia en caso
     * que no existan alianzas en el sistema
     * @throws BusinessException en caso de error al consultar las alianzas  
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getAllAlliances", action="getAllAlliances")
    public List<AllianceVO> getAllAlliances() throws BusinessException {
    	return ejbRef.getAll();
    }
    
    /**
     * Metodo: Este m�todo retorna Lista de alianzas regionalizadas
     * @param countryId - Long identificador del país por el cual se consultarán las alanzas
     * @return List<AllianceVO> lista con las alianzas consultadas de ese país, una lista vacia en caso que no se encuentren 
     * alianzas registradas en ese país.
     * @throws BusinessException en caso de error al consultar las alianzas por país  
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getAllAlliancesByCountryId", action="getAllAlliancesByCountryId")
    public List<AllianceVO> getAllAlliancesByCountryId(@WebParam(name = "countryId")Long countryId) throws BusinessException {
        return ejbRef.getAllAllianceByCountryId(countryId);
    }

    /**
     * Metodo: Este método retorna una lista de las ALLIANCES que se encuentran en un rango de fechas
     * determinado
     * @param init - Date fecha de inicio del rango a consultar
     * @param end - Date  fecha de fin del rango
     * @return List<AllianceVO> lista de alianzas cuya vigencia se encuentra dentro del rango especificado, una lista vacia en
     * caso que no se encuentren alianzas con la vigencia dentro del rango.
     * @throws BusinessException en caso de error al consultar las alianzas en un  rango de fechas específico   
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_date_or_hour_range_invalid</code> En caso que se haya especificado un rango de fechas inválido<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getAllianceByDate", action="getAllianceByDate")
    public List<AllianceVO> getAllianceByDate(@WebParam(name = "init")
    Date init, @WebParam(name = "end")
    Date end) throws BusinessException {
        return ejbRef.getAllianceByDate(init, end);
    }

    /**
     * Metodo: Este método crea un ALLIANCE.
     * @param obj - AllianceVO objeto que contiene la información encapsulada de la alianza a ser creada.
     * @throws BusinessException en caso de error al crear la alianza,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia o que no haya superado la validación de campos obligatorios de la alianza<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_code_name_already_exist</code> En caso que se encuentre una alianza con el mismo código<br>
	 * <code>sdii_CODE_date_or_hour_range_invalid</code> En caso que haya especificado una fecha de alianza inválida<br>
     * @author gfandino
     */
    @WebMethod(operationName = "createAlliance", action="createAlliance")
    public void createAlliance(@WebParam(name = "obj")
    AllianceVO obj) throws BusinessException {
        ejbRef.createAlliance(obj);
    }
    
    /**
     * Metodo: Persiste la información de un archivo que soporta la creación de una alianza
     * @param obj objeto que encapsula la información de un archivo de alianza.
     * @throws BusinessException en caso de error al tratar de persistir la información de un archivo alianza  
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author gfandino
     */
    @WebMethod(operationName = "createAllianceFile", action="createAllianceFile")
    public void createAllianceFile(@WebParam(name = "obj")
    AllianceFileVO obj) throws BusinessException {
        ejbRef.createAllianceFile(obj);
    }
    
    /**
     * Metodo: Este método obtiene un ALLIANCEFILE dado el identificador de la alianza.
     * @param obj - AllianceVO objeto que encapsula la información de la alianza cuyo archivo será consultado
     * @return AllianceFileVO objeto que encapsula la información de un archivo de alianza, 
     * nulo en caso que no se encuentre un archivo relacionado con la alianza especificada
     * @throws BusinessException  en caso de error al tratar de consultar la información del archivo de alianza,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getAllianceFileByAllianceID", action="getAllianceFileByAllianceID")
    public AllianceFileVO getAllianceFileByAllianceID(@WebParam(name = "obj")
    AllianceVO obj) throws BusinessException {
        return ejbRef.getAllianceFileByAllianceID(obj);
    }

    /**
     * Metodo: Este método actualiza una ALLIANCE.
     * @param obj - AllianceVO objeto que encapsula la información de la alianza a ser actualizada, es necesario que el
     * identificador venga especificado en el campo id.
     * @throws BusinessException en caso de error al tratar de actualizar la información de la alianza  
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia, que el objeto recibido como parámetro no supere la validación de campos obligatorios<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author gfandino
     */
    @WebMethod(operationName = "updateAlliance", action="updateAlliance")
    public void updateAlliance(@WebParam(name = "obj")
    AllianceVO obj) throws BusinessException {
        ejbRef.updateAlliance(obj);
    }

    /**
     * Metodo: Este método elimina un ALLIANCE.
     * @param obj - AllianceVO información de la alianza que se desea borrar, es necesario que se especifique el campo id,
     * los demás datos no son necesarios
     * @throws BusinessException en caso de error al tratar de borrar la información de una alianza,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author gfandino
     */
    @WebMethod(operationName = "deleteAlliance", action="deleteAlliance")
    public void deleteAlliance(@WebParam(name = "obj")
    AllianceVO obj) throws BusinessException {
        ejbRef.deleteAlliance(obj);
    }

    /**
     * Metodo: Este método retorna una lista de todas las empresas que participan en alianzas
     * @return List<AllianceCompanyVO> lista con la información de las empresas que participan en alianzas, una lista
     * vacia en caso que no existan empresas que participan en alianzas
     * @throws BusinessException en caso de error al tratar de consultar la información de las empresas que 
     * participan en alianzas.  
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author gfandino
     */
    @WebMethod(operationName = "populateEmpresas", action="populateEmpresas")
    public List<AllianceCompanyVO> populateEmpresas() throws BusinessException {
        //return ejbRef.getAllAllianceCompanyByCountryId(countryId);
    	return ejbRef.populateEmpresas();
    }
    
    /**
     * Metodo: Obtiene una lista de allianzas con compañias regionalizada por país
     * @param countryId - Long identificador del país
     * @return List<AllianceCompanyVO> lista con la información de las alianzas con compañías en un país, 
     * una lista vacia en caso que no se encuentren información de alianzas en el país consultado
     * @throws BusinessException en caso de error al obtener la información de alianzas del país,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getAllAllianceCompanyByCountryId", action="getAllAllianceCompanyByCountryId")
    public List<AllianceCompanyVO> getAllAllianceCompanyByCountryId(@WebParam(name = "countryId")Long countryId) throws BusinessException {
        return ejbRef.getAllAllianceCompanyByCountryId(countryId);
    }

    /**
     * Metodo: Consulta los productos comerciales que se ofrecen en las diferentes alianzas 
     * @return List<ComercialProductVO> lista con los productos comerciales registrados en el sistema, una lista vacia en caso que no
     * existan productos comerciales.
     * @throws BusinessException en caso de error al tratar de obtener los productos comerciales, 
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author gfandino
     */
    @WebMethod(operationName = "populateProductosComerciales", action="populateProductosComerciales")
    public List<ComercialProductVO> populateProductosComerciales() throws BusinessException {
    	return ejbRef.populateProductosComerciales();
    }
    
    /**
     * Metodo: Obtiene una lista de los productos comerciales dado el identificador del país
     * @param countryId - Long identificador del país por el cual se consultarán los productos comerciales
     * @return List<ComercialProductVO> lista con los productos comerciales del país, una lista vacia en caso que
     * no se encuentren productos comerciales en el país especificado
     * @throws BusinessException en caso de error al consultar los productos comerciales del país,  
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getAllComercialProductsByCountryId", action="getAllComercialProductsByCountryId")
    public List<ComercialProductVO> getAllComercialProductsByCountryId(@WebParam(name = "countryId")Long countryId) throws BusinessException {
        return ejbRef.getAllComercialProductsByCountryId(countryId);
    }

    /**
     * Metodo: Este método retorna una instancia de AllianceCompany por ID.
     * @param id - Long identificador del registro que relaciona alianzas con compañías
     * @return AllianceCompanyVO objeto que encapsula la información de la alianza y la compañía relacionada
     * @throws BusinessException en caso de error al consultar la información de la alianza y la compañía,  
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getAllianceCompanyByID", action="getAllianceCompanyByID")
    public AllianceCompanyVO getAllianceCompanyByID(@WebParam(name = "id")
    Long id) throws BusinessException {
        return ejbRef.getAllianceCompanyByID(id);
    }

    /**
     * Metodo: Este método retorna una instancia de AllianceCompany por Codigo.
     * @param code - String código de la relación entre la alianza y la compañía
     * @return AllianceCompanyVO objeto que encapsula la información de la relación entre la alianza y la compañía, nulo
     * en caso que no se encuentre la relación con el código especificado
     * @throws BusinessException en caso de error al consultar la información de la relación  
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getAllianceCompanyByCode", action="getAllianceCompanyByCode")
    public AllianceCompanyVO getAllianceCompanyByCode(@WebParam(name = "code")
    String code) throws BusinessException {
        return ejbRef.getAllianceCompanyByCode(code);
    }

    /**
     * Metodo: Este método retorna una instancia de ComercialProduct por ID.
     * @param id - Long identificador del producto comercial que se desea consultar
     * @return ComercialProductVO información encapsulada del producto comercial, nulo en caso
     * que no se encuentre el producto comercial con el identificador especificado
     * @throws BusinessException en caso de error al consultar la información del producto comercial por identificador, 
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getComercialProductByID", action="getComercialProductByID")
    public ComercialProductVO getComercialProductByID(@WebParam(name = "id")
    Long id) throws BusinessException {
        return ejbRef.getComercialProductByID(id);
    }

    /**
     * Metodo: Este método retorna una instancia de ComercialProduct por Codigo.
     * @param code - String código del producto comercial a ser consultado
     * @return ComercialProductVO información encapsulada del producto comercial, nulo en caso que no se
     * encuentre un producto comercial con el código especificado
     * @throws BusinessException en caso de error al consultar la información del producto comercial
     * con el código especificado,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getComercialProductByCode", action="getComercialProductByCode")
    public ComercialProductVO getComercialProductByCode(@WebParam(name = "code")
    String code) throws BusinessException {
        return ejbRef.getComercialProductByCode(code);
    }

}
