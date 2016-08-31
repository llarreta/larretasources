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

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigParametrosFacadeLocal;
import co.com.directv.sdii.facade.config.ParameterTypeFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ParameterTypeVO;
import co.com.directv.sdii.model.vo.SystemParameterVO;

/**
 * Servicio que expone todos los metodos referentes a la administracion de los parametros del sistema
 * 
 * Fecha de Creación: 25/03/2010
 * @author jcasas <a href="mailto:jcasas@intergrupo.com">e-mail</a>
 * @version 1.0    
 */
@MTOM()
@WebService()
@Stateless()
public class ConfigSystemParamsWS {

    @EJB
    private ConfigParametrosFacadeLocal ejbRef;
    @EJB
    private ParameterTypeFacadeBeanLocal paramType;
    

    /**
     * Obtiene un parametro del sistema con el id especificado
     * @param id Id del parametro
     * @return Objeto con la información básica del parametro de sistema solicitado
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
     */
    @WebMethod(operationName = "configTimers", action="configTimers")
    public void configTimers() throws BusinessException {
		ejbRef.runTimeManager();
    }

    
    /**
     * Obtiene un parametro del sistema con el id especificado
     * @param id Id del parametro
     * @return Objeto con la información básica del parametro de sistema solicitado
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
     */
    @WebMethod(operationName = "getSystemParameterByID", action="getSystemParameterByID")
    public SystemParameterVO getSystemParameterByID(@WebParam(name = "id") Long id) throws BusinessException {
        try {
            return ejbRef.getSystemParameterByID(id);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Throwable ex) {
        	if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
        }
    }

    /**
     * Obtiene un parametro del sistema con el id especificado
     * @param id Id del parametro
     * @return Objeto con la información básica del parametro de sistema solicitado
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
     */
    @WebMethod(operationName = "getSystemParameterByCodeAndCountryId", action="getSystemParameterByCodeAndCountryId")
    public SystemParameterVO getSystemParameterByCodeAndCountryId(@WebParam(name = "code") String code,@WebParam(name = "countryId") Long countryId) throws BusinessException {
        try {
            return ejbRef.getSystemParameterByCodeAndCountryId(code, countryId);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Throwable ex) {
        	if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
        }
    }

    /**
     * Obtiene todos los parametros del sistema
     * @return Listado con todos los parametros del sistema
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
     */
    @WebMethod(operationName = "getAllSystemParams", action="getAllSystemParams")
    public List<SystemParameterVO> getAllSystemParams() throws BusinessException {
        try {
        	//return ejbRef.getAllSystemParametersByCountryId(countryId);
            return ejbRef.getAll();
        } catch (BusinessException ex) {
            throw ex;
        } catch (Throwable ex) {
        	if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
        }
    }
    
    /**
     * Obtiene todos los parametros del sistema de un pais especifico
     * @param countryId Id del pais
     * @return Listado de los parametros de sistema del pais especificado
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
     */
    @WebMethod(operationName = "getAllSystemParamsByCountryId", action="getAllSystemParamsByCountryId")
    public List<SystemParameterVO> getAllSystemParamsByCountryId(@WebParam(name = "countryId")Long countryId) throws BusinessException {
        try {
        	return ejbRef.getAllSystemParametersByCountryId(countryId);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Throwable ex) {
        	if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
        }
    }

    /**
     * Crea un parametro de sistema
     * @param obj Objeto con la informacion basica del parametro de sistema a crear
     * @throws BusinessException en caso de error al ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia, que exista otro parámetro del sistema con el mismo código o 
     * que no estén asignadas todas las propiedades obligatorias<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     */
    @WebMethod(operationName = "createSystemParameter", action="createSystemParameter")
    public void createSystemParameter(@WebParam(name = "obj") SystemParameterVO obj) throws BusinessException {
        try {
            ejbRef.createSystemParameter(obj);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Throwable ex) {
        	if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
        }
    }

    /**
     * Actualiza un parametro de sistema
     * @param obj Objeto con la informacion basica del parametro de sistema a actualizar
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
     */
    @WebMethod(operationName = "updateSystemParameter", action="updateSystemParameter")
    public void updateSystemParameter(@WebParam(name = "obj") SystemParameterVO obj) throws BusinessException {
        try {
            ejbRef.updateSystemParameter(obj);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Throwable ex) {
        	if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
        }
    }

    /**
     * Elimina un parametro de sistema
     * @param obj Objeto con la informacion basica del parametro de sistema a eliminar
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
     */
    @WebMethod(operationName = "deleteSystemParameter", action="deleteSystemParameter")
    public void deleteSystemParameter(@WebParam(name = "obj") SystemParameterVO obj) throws BusinessException {
        try {
            ejbRef.deleteSystemParameter(obj);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Throwable ex) {
        	if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
        }
    }
    
    /**
     * Obtiene todos los tipos de parametros del sistema
     * @return Listado con los tipos de parametros del sistema
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
     */
    @WebMethod(operationName = "getAllParametersTypes", action="getAllParametersTypes")
    public List<ParameterTypeVO> getAllParametersTypes() throws BusinessException {
        try {
            return this.paramType.getAllParametersTypes();
        } catch (BusinessException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new BusinessException(ex.getMessage());
        }
    }
    
    /**
     * Metodo: <Descripcion>
     * @param name
     * @param countryId
     * @return
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    @WebMethod(operationName = "getSystemParameterByNameAndCountryId", action="getSystemParameterByNameAndCountryId")
    public List<SystemParameterVO> getSystemParameterByNameAndCountryId(@WebParam(name = "name")String name,@WebParam(name = "countryId") Long countryId) throws BusinessException {
    	return ejbRef.getSystemParameterByNameAndCountryId(name, countryId);
    }
}
