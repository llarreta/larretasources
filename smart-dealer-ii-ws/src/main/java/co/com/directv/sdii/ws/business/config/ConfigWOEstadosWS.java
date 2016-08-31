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
import co.com.directv.sdii.facade.config.ConfigWOEstadosFacadeLocal;
import co.com.directv.sdii.model.vo.Ibs6StatusVO;
import co.com.directv.sdii.model.vo.WorkorderStatusVO;

/**
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del módulo de Configuración de Estados de la WO.
 *
 * Caso de Uso CFG - 04 - Gestionar Códigos de Estado de las Work Orders
 *
 * @author Jimmy Vélez Muñoz
 * @author jjimenezh
 * 
 * @see co.com.directv.sdii.facade.config.ConfigWOEstadosFacadeLocal
 */
@MTOM
@WebService()
@Stateless()
public class ConfigWOEstadosWS {

    @EJB
    private ConfigWOEstadosFacadeLocal ejbRef;

    /**
     * Metodo: Obtiene los estados de las workorders por identificador
     * @param id identificador del estado de la workorder
     * @return estado de la workorder que coincide con el id especificado, nulo
     * en caso que no exista
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
    @WebMethod(operationName = "getWorkorderStatusByID", action = "getWorkorderStatusByID")
    public WorkorderStatusVO getWorkorderStatusByID(@WebParam(name = "id") Long id) throws BusinessException {
        try {
            return ejbRef.getWorkorderStatusByID(id);
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
     * Metodo: Obtiene el estado de la workorder por código
     * @param code código del estado de la work order
     * @return estado de la workorder que coincide con el código. nulo en caso que no exista
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
    @WebMethod(operationName = "getWorkorderStatusByCode", action = "getWorkorderStatusByCode")
    public WorkorderStatusVO getWorkorderStatusByCode(@WebParam(name = "code") String code) throws BusinessException {
        try {
            return ejbRef.getWorkorderStatusByCode(code);
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
     * Metodo: Obtiene una lista de los estados de la workorder dado el nombre
     * @param name nombre del estado de work order
     * @return lista de los estados de workorder cuyo nombre coincide con el especificado por el usuario
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
    @WebMethod(operationName = "getWorkorderStatusByName", action = "getWorkorderStatusByName")
    public List<WorkorderStatusVO> getWorkorderStatusByName(@WebParam(name = "name") String name) throws BusinessException {
        try {
            return ejbRef.getWorkorderStatusByName(name);
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
     * Metodo: Obtiene los estados de la work order por el identificador del estado en IBS6
     * @param status estado de la work order en IBS 6
     * @return lista con los estados de las work order que coinciden con el estado de ibs 6
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
    @WebMethod(operationName = "getWorkorderStatusByIBS6Status", action = "getWorkorderStatusByIBS6Status")
    public List<WorkorderStatusVO> getWorkorderStatusByIBS6Status(@WebParam(name = "status") Ibs6StatusVO status) throws BusinessException {
        try {
            return ejbRef.getWorkorderStatusByIBS6Status(status);
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
     * Metodo: Obtiene todos los estados de las workorders
     * @return lista con los estados de work orders registrados en el sistema
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
    @WebMethod(operationName = "getAllWorkOrderStatus", action = "getAllWorkOrderStatus")
    public List<WorkorderStatusVO> getAllWorkOrderStatus() throws BusinessException {
        try {
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
     * Metodo: Crea un estado de work order en el sistema
     * @param obj estado de workorder a ser creado en el sistema
     * @throws BusinessException En caso de error al ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia, o
     * que no se asigne valor a todas las propiedades obligatorias de work order status<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_object_already_exist</code> En caso que se trate de crear un estado de work order con el código de uno existente<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "createWorkorderStatus", action = "createWorkorderStatus")
    public void createWorkorderStatus(@WebParam(name = "obj") WorkorderStatusVO obj) throws BusinessException {
        try {
            ejbRef.createWorkorderStatus(obj);
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
     * Metodo: actualiza el estado de una workorder
     * @param obj workorder a ser actualizada
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
	 * <code>sdii_CODE_object_already_exist</code> En caso que se trate de asignar el código de estado de work order con el código de uno existente y diferente al que se trata de actualizar<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "updateWorkorderStatus", action = "updateWorkorderStatus")
    public void updateWorkorderStatus(@WebParam(name = "obj") WorkorderStatusVO obj) throws BusinessException {
        try {
            ejbRef.updateWorkorderStatus(obj);
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
     * Metodo: borra el estado de una workorder
     * @param obj workorder a ser borrada
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
    @WebMethod(operationName = "deleteWorkorderStatus", action = "deleteWorkorderStatus")
    public void deleteWorkorderStatus(@WebParam(name = "obj") WorkorderStatusVO obj) throws BusinessException {
        try {
            ejbRef.deleteWorkorderStatus(obj);
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
     * Metodo: Obtiene todos los estados de las workorders para modificar work orders.
     * @return lista con los estados de work orders registrados en el sistema
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
     * @author jnova
     */
    @WebMethod(operationName = "getAllWorkOrderStatusToModify", action = "getAllWorkOrderStatusToModify")
    public List<WorkorderStatusVO> getAllWorkOrderStatusToModify() throws BusinessException {
        try {
            return ejbRef.getAllWorkOrdersToAsign();
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
}
