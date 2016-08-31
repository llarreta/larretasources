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
import co.com.directv.sdii.facade.config.ConfigHabilidadesFacadeLocal;
import co.com.directv.sdii.model.vo.SkillTypeVO;
import co.com.directv.sdii.model.vo.SkillVO;

/**
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del módulo de Configuración de Habilidades Determinantes y Eliminantes.
 *
 * En la consulta de SKILLS se recupera la relación con SKILL_TYPES.
 *
 * Caso de Uso CFG - 08 - Gestionar Habilidades Determinantes y Eliminantes
 *
 * @author Jimmy Vélez Muñoz
 * @author jjimenezh
 * 
 * @see co.com.directv.sdii.facade.config.ConfigHabilidadesFacadeLocal
 */
@MTOM
@WebService()
@Stateless()
public class ConfigSkillsWS {
    @EJB
    private ConfigHabilidadesFacadeLocal ejbRef;

    /**
     * Metodo: Obtiene una habilidad dado el identificador
     * @param id identificador de la entidad a consultar
     * @return habilidad cuyo identificador coincide con el especificado, nulo en caso que no exista
     * @throws BusinessException en caso de errror al ejecutar la operación,
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
    @WebMethod(operationName = "getSkillsByID", action="getSkillsByID")
    public SkillVO getSkillsByID(@WebParam(name = "id")
    Long id) throws BusinessException {
        return ejbRef.getSkillsByID(id);
    }

    /**
     * Metodo: Obtiene una habilidad dado el código
     * @param code código de la habilidad a ser consultada
     * @return habilidad cuyo código coincide con el especificado por el usuario, nulo en caso
     * que no se encuentre una habilidad con el código especificado
     * @throws BusinessException en caso de errror al ejecutar la operación,
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
    @WebMethod(operationName = "getSkillByCode", action="getSkillByCode")
    public SkillVO getSkillByCode(@WebParam(name = "code")
    String code) throws BusinessException {
        return ejbRef.getSkillByCode(code);
    }

    /**
     * Metodo: Consulta un conjunto de habilidades cuyos nombres coinciden con el especificado
     * @param name nombre que se usará para el filtro de habilidades
     * @return lista con las habilidades coincidentes, una lista vacia en caso que no existan coincidencias
     * @throws BusinessException en caso de errror al ejecutar la operación,
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
    @WebMethod(operationName = "getSkillsByName", action="getSkillsByName")
    public List<SkillVO> getSkillsByName(@WebParam(name = "name")
    String name) throws BusinessException {
        return ejbRef.getSkillsByName(name);
    }

    /**
     * Metodo: Obtiene una lista de habilidades dado el tipo de habilidad
     * @param typeId identificador del tipo de habilidad por el que se realizará el filtro
     * @return lista con las habilidades coincidentes con el tipo especificado, una lista vacia en caso que no se
     * encuentren habilidades con el tipo especificado
     * @throws BusinessException en caso de errror al ejecutar la operación,
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
    @WebMethod(operationName = "getSkillsByType", action="getSkillsByType")
    public List<SkillVO> getSkillsByType(@WebParam(name = "typeId")
    Long typeId) throws BusinessException {
        return ejbRef.getSkillsByType(typeId);
    }


    /**
     * Metodo: obtiene todas las habilidades registradas en el sistemas
     * @return lista con todas las habilidades del sistema, una lista vacia en caso que no existan habilidades en el sistema
     * @throws BusinessException en caso de errror al ejecutar la operación,
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
    @WebMethod(operationName = "getAllSkills", action="getAllSkills")
    public List<SkillVO> getAllSkills() throws BusinessException {
    	return ejbRef.getAll();
    }
    
    /**
     * Metodo: Obtiene las habilidades por país
     * @param countryId identificador del país por el que se filtrarán las habilidades
     * @return Lista con las habilidades del país especificado, una lista vacia en caso que no
     * se encuentren habilidades del país definido
     * @throws BusinessException en caso de errror al ejecutar la operación,
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
    @WebMethod(operationName = "getAllSkillsByCountryId", action="getAllSkillsByCountryId")
    public List<SkillVO> getAllSkillsByCountryId(@WebParam(name = "countryId")Long countryId) throws BusinessException {
    	return ejbRef.getAllSkillsByCountryId(countryId);
    }
    
    /**
     * Metodo: obtiene todas las habilidades dado el tipo de habilidad y el identificador del país
     * @param skillTypeId identificador del tipo de habilidad
     * @param countryId identificador del país por el que se filtrará
     * @return lista con las habilidades coincidentes con los criterios de búsqueda, una lista vacia en caso
     * que no se encuentren habilidades
     * @throws BusinessException en caso de errror al ejecutar la operación,
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
    @WebMethod(operationName = "getAllSkillsBySkillTypeAndCountryId", action="getAllSkillsBySkillTypeAndCountryId")
    public List<SkillVO> getAllSkillsBySkillTypeAndCountryId(@WebParam(name = "skillTypeId")Long skillTypeId, @WebParam(name = "countryId")Long countryId) throws BusinessException{
    	return ejbRef.getAllSkillsBySkillTypeAndCountryId(skillTypeId, countryId);
    }

    /**
     * Metodo: Genera una habilidad en el sistema
     * @param obj habilidad a ser creada en el sistema
     * @throws BusinessException en caso de errror al ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia, o que no se hayan asignado todas las propiedades obligatorias<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_error_already_exists</code> En caso que se encuentre una habilidad con el mismo código de la que se desea crear<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "createSkill", action="createSkill")
    public void createSkill(@WebParam(name = "obj")
    SkillVO obj) throws BusinessException {
        ejbRef.createSkill(obj);
    }

    /**
     * Metodo: actualiza una habilidad en el sistema
     * @param obj habilidad a ser actualizada, se requieres que se especifique el valor de la
     * propiedad id
     * @throws BusinessException en caso de errror al ejecutar la operación,
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
    @WebMethod(operationName = "updateSkill", action="updateSkill")
    public void updateSkill(@WebParam(name = "obj")
    SkillVO obj) throws BusinessException {
        ejbRef.updateSkill(obj);
    }

    /**
     * Metodo: Borra una habilidad del sistema
     * @param obj habilidad a ser eliminada se requiere que la propiedad id
     * esté especificada
     * @throws BusinessException en caso de errror al ejecutar la operación,
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
    @WebMethod(operationName = "deleteSkill", action="deleteSkill")
    public void deleteSkill(@WebParam(name = "obj")
    SkillVO obj) throws BusinessException {
        ejbRef.deleteSkill(obj);
    }
    
    /**
     * Metodo: Obtiene todos los tipos de habilidad 
     * @return lista con todos los tipos de habilidad registrados en el sistema, una lista vacia en caso
     * que no existan tipos de habilidad en el sistema
     * @throws BusinessException en caso de errror al ejecutar la operación,
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
    @WebMethod(operationName = "getAllSkillTypes", action="getAllSkillTypes")
    public List<SkillTypeVO> getAllSkillTypes()throws BusinessException{
    	return ejbRef.getAllSkillTypes();
    }
    
    /**
     * Metodo: obtiene todos los tipos de habilidad dado el país
     * @param countryId identificador del país por el que se realizará el filtro
     * @return lista con los tipos de habilidad del país consultado, una lista vacia en caso que no
     * existan tipos de habilidad para el país consultado
     * @throws BusinessException en caso de errror al ejecutar la operación,
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
    @WebMethod(operationName = "getAllSkillTypesByCountryId", action="getAllSkillTypesByCountryId")
    public List<SkillTypeVO> getAllSkillTypesByCountryId(@WebParam(name = "countryId")Long countryId)throws BusinessException{
    	return ejbRef.getAllSkillTypesByCountryId(countryId);
    }

}
