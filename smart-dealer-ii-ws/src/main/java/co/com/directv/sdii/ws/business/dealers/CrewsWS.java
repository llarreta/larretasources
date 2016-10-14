package co.com.directv.sdii.ws.business.dealers;

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
import co.com.directv.sdii.facade.dealers.CrewStatusFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.CrewTypesFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.CrewsFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.EmployeesCrewFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.CrewOff;
import co.com.directv.sdii.model.vo.CrewOffVO;
import co.com.directv.sdii.model.vo.CrewStatusVO;
import co.com.directv.sdii.model.vo.CrewTypeVO;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.EmployeeCrewVO;
import co.com.directv.sdii.model.vo.UserVO;


/**
 * Servicio que provee las operaciones para la administración de cuadrillas en Smart Dealer II,
 * una cuadrilla es un grupo de personas que atienden ordenes de trabajo en el domicilio del
 * cliente, pueden contar con un vehículo y están asociadas a una compañía instaladora.
 * <br><br>
 * Fecha de Creación: 24/03/2010<br>
 * Control de cambios:<br>
 * 24/06/2010 jjimenezh Se actualiza la documentación del servicio web<br>
 * @author jcasas <a href="mailto:jcasas@intergrupo.com">e-mail</a>
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.dealers.CrewsFacadeBeanLocal
 * @see co.com.directv.sdii.facade.dealers.EmployeesCrewFacadeBeanLocal
 * @see co.com.directv.sdii.facade.dealers.CrewStatusFacadeBeanLocal
 * @see co.com.directv.sdii.facade.dealers.CrewTypesFacadeBeanLocal
 * 
 */
@MTOM
@WebService()
@Stateless()
public class CrewsWS {

    @EJB
    private CrewsFacadeBeanLocal ejbRef;
    @EJB
    private EmployeesCrewFacadeBeanLocal ecFacadeBean;
    @EJB
    private CrewStatusFacadeBeanLocal csFacadeBean;
    @EJB
    private CrewTypesFacadeBeanLocal ctFacadeBean;

    
    /**
     * Metodo: Esta operación permite crear una cuadrilla que podrá atender órdenes de trabajo
     * @param obj objeto que encapsula los atributos de una cuadrilla, no es necesario que se especifique
     * la propiedad id debido a que el sistema la generará automáticamente, todas las demás propiedades son
     * requeridas.
     * @throws BusinessException En caso de error al tratar de persistir la cuadrilla, se lanzarán los siguientes códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_params_null_or_missed</code> En caso de que el objeto tenga parámetros nulos que son obligatorios<br>
     * <code>sdii_CODE_not_update_crew</code> En caso de que el objeto tenga parámetros nulos que son obligatorios<br>
     * <code>sdii_CODE_crew_cannot_create_inactive</code> En caso que se intente crear una cuadrilla inactiva<br>
     * <code>sdii_CODE_not_assignment_employee_crew</code> En caso de que el objeto tenga parámetros nulos que son obligatorios<br>
     * <code>sdii_CODE_crew_not_responsible_specified</code> En caso que no se haya especificado el responsable de la cuadrilla<br>
     * <code>sdii_CODE_crew_multiple_responsible_specified</code> En caso que se hayan especificado múltiples responsables de la cuadrilla<br>
     * <code>sdii_CODE_crew_no_tecnician_specified</code> En caso que se hayan especificado múltiples responsables de la cuadrilla<br>
     * <code>sdii_CODE_crew_employee_responsible_in_other_crew</code> En caso que se encuentre otra cuadrilla en el mismo rango de fechas con el mismo responsable<br>
     * <code>sdii_CODE_date_or_hour_range_invalid</code> En caso que se especifique un rango de fechas inválido de inicio y fin de la cuadrilla<br>
     * @author jcasas
     */
    @WebMethod(operationName = "createCrews", action = "createCrews")
    public void createCrews(@WebParam(name = "obj") CrewVO obj) throws BusinessException {
        try {
            ejbRef.createCrews(obj);
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
     * Metodo: Consulta los datos completos de una cuadrilla dado el identificador de la misma en la persistencia
     * @param id identificador de la cuadrilla a ser consultada
     * @return Objeto que encapsula la información de una cuadrilla, nulo en caso que no se encuentre la cuadrilla con el id especificado
     * @throws BusinessException En caso de error al consultar la información de la cuadrilla, a continuación se especifican los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_params_null_or_missed</code> En caso de que el objeto tenga parámetros nulos que son obligatorios<br>
     * @author jcasas
     */
    @WebMethod(operationName = "getCrewsByID", action = "getCrewsByID")
    public CrewVO getCrewsByID(@WebParam(name = "id") Long id) throws BusinessException {
        try {
            return ejbRef.getCrewsByID(id);
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
     * Metodo: Actualiza la información de una cuadrilla
     * @param obj objeto que encapsula informacion dela cuadrilla
     * @param description en caso que se inactive, contiene la informacion del motivo de inactivacion
     * @throws BusinessException En caso de error al tratar de actualizar la cuadrilla, a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_params_null_or_missed</code> En caso de que el objeto tenga parámetros nulos que son obligatorios<br>
     * <code>sdii_CODE_not_update_crew</code> En caso de que el objeto tenga parámetros nulos que son obligatorios<br>
     * <code>sdii_CODE_crew_cannot_create_inactive</code> En caso que se intente crear una cuadrilla inactiva<br>
     * <code>sdii_CODE_not_assignment_employee_crew</code> En caso de que el objeto tenga parámetros nulos que son obligatorios<br>
     * <code>sdii_CODE_crew_not_responsible_specified</code> En caso que no se haya especificado el responsable de la cuadrilla<br>
     * <code>sdii_CODE_crew_multiple_responsible_specified</code> En caso que se hayan especificado múltiples responsables de la cuadrilla<br>
     * <code>sdii_CODE_crew_no_tecnician_specified</code> En caso que se hayan especificado múltiples responsables de la cuadrilla<br>
     * <code>sdii_CODE_crew_employee_responsible_in_other_crew</code> En caso que se encuentre otra cuadrilla en el mismo rango de fechas con el mismo responsable<br>
     * <code>sdii_CODE_date_or_hour_range_invalid</code> En caso que se especifique un rango de fechas inválido de inicio y fin de la cuadrilla<br>
     * @author jcasas
     * @author jnova
     */
    @WebMethod(operationName = "updateCrews", action = "updateCrews")
    public void updateCrews(@WebParam(name = "obj") CrewVO obj,@WebParam(name = "description") String description, @WebParam(name="user") UserVO user) throws BusinessException {
        try {
            ejbRef.updateCrews(obj,description, user);
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
     * Metodo: Borra de la persistencia la información de una cuadrila dado el identificador de la misma
     * @param obj objeto que encapsula el identificador de la cuadrilla a ser eliminada, debe venir la propiedad id
     * @throws BusinessException En caso de error al tratar de borrar la cuadrilla, a continuación los códigos de error que puede lanzar:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_params_null_or_missed</code> En caso de que el objeto tenga parámetros nulos que son obligatorios<br>
     * @author jcasas
     */
    @WebMethod(action = "deleteCrews", operationName = "deleteCrews")
    public void deleteCrews(@WebParam(name = "obj") CrewVO obj) throws BusinessException {
        try {
            ejbRef.deleteCrews(obj);
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
     * Metodo: Obtiene la información de todas las cuadrillas almacenadas en la persistencia
     * del sistema
     * @return una lista con la información de todas las cuadrillas, una lista vacia en caso que no existan cuadrillas
     * @throws BusinessException En caso de error la tratar de consultar la información de las cuadrillas, los códigos de error son los siguientes:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jcasas
     */
    @WebMethod(action = "getAllCrews", operationName = "getAllCrews")
    public List<CrewVO> getAllCrews() throws BusinessException {
        try {
            return ejbRef.getAllCrews();
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
     * Metodo: Obtiene un listado de cuadrillas que estan asociadas a un vehiculo especificado
     * @param vehicleId Id del vehiculo
     * @return Listado de cuadrillas que estan asociadas a un vehiculo especificado, una lista vacia en caso
     * que no se encuentren cuadrillas relacionadas con el vehículo 
     * @throws BusinessException en caso de error al consultar la información de las cuadrillas, a continuación los códigos de error:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jcasas
     */
    @WebMethod(action = "getCrewsByVehicleId", operationName = "getCrewsByVehicleId")
    public List<CrewVO> getCrewsByVehicleId(@WebParam(name = "vehicleId") long vehicleId) throws BusinessException {
        try {
            return ejbRef.getCrewsByVehicleId(vehicleId);
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
     * Metodo: Obtiene solamente la informacion basica, no se tiene en cuenta la informacion relacionada a la cuadrilla
     * @return Listado de cuadrillas con la información basica de una cuadrilla sin datos relacionados, una lista vacia en caso que no existan cuadrillas
     * @throws BusinessException en caso de error al consultar la información básica de las cuadrillas, a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jjimenezh
     */
    @WebMethod(action = "getAllCrewsOnlyBasicInfo", operationName = "getAllCrewsOnlyBasicInfo")
    public List<CrewVO> getAllCrewsOnlyBasicInfo() throws BusinessException {
        try {
            return ejbRef.getAllCrewsOnlyBasicInfo();
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
     * Metodo: Obtiene un listado de cuadrillas con los vehiculos asociados con la placa especificada 
     * y el empleado especificado
     * @param plate Placa del vehiculo
     * @param document Numero de Documento del empleado
     * @return Listado de cuadrillas con la informacion basica de una cuadrilla, una lista vacía 
     * en caso que no se encuentren cuadrillas asociadas al vehículo de la placa y al empleado 
     * con el número de identificación especificados
     * @throws BusinessException en caso de error al consultar la información de las cuadrillas, 
     * a continuación los códigos de error<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jcasas
     */
    @WebMethod(action = "getCrewsByPlateOrDocument", operationName = "getCrewsByPlateOrDocument")
    public List<CrewVO> getCrewsByPlateOrDocument(@WebParam(name = "plate") String plate, @WebParam(name = "document") String document) throws BusinessException {
        try {
            return ejbRef.getCrewsByPlateOrDocument(plate, document);
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
     * Metodo: Obtiene un listado de cuadrillas con los vehiculos asociados con la placa especificada, el empleado especificado y el dealer asociado
     * @param plate Placa del vehiculo
     * @param document Numero de Documento del empleado
     * @param dealerId identificador del dealer asociado a la cuadrilla
     * @return Listado de cuadrillas con la informacion basica de una cuadrilla. una lista vacia en caso
     * que no se encuentren cuadrillas asociadas a la placa, el empleado o el dealer especificados 
     * @throws BusinessException en caso de error al consultar la información de las cuadrillas,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jcasas
     */
    @WebMethod(action = "getCrewsByPlateOrDocumentOrDealer", operationName = "getCrewsByPlateOrDocumentOrDealer")
    public List<CrewVO> getCrewsByPlateOrDocumentOrDealer(@WebParam(name = "plate") String plate, @WebParam(name = "document") String document, @WebParam(name = "dealerId") Long dealerId) throws BusinessException {
        try {
            return ejbRef.getCrewsByPlateOrDocumentOrDealer(plate, document, dealerId);
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
     * Metodo: Obtiene un listado de cuadrillas pertenecientes a un dealer especifico, dentro de un rango de fechas dado 
     * @param dealerId Id del dealer
     * @param startDate Fecha de inicio
     * @param endDate Fecha de terminacion
     * @return Listado de cuadrillas con la informacion basica de una cuadrilla, una lista vacia en caso que no existan cuadrillas
     * asociadas al dealer que cubran el rango de fecha especificado
     * @throws BusinessException en caso de error al consultar las cuadrillas,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jcasas 
     */
    @WebMethod(action = "getCrewsByDealerIdStartDateAndEndDate", operationName = "getCrewsByDealerIdStartDateAndEndDate")
    public List<CrewVO> getCrewsByDealerIdStartDateAndEndDate(@WebParam(name = "dealerId") Long dealerId, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate) throws BusinessException {
        try {
            return ejbRef.getCrewsByDealerIdStartDateAndEndDate(dealerId,startDate,endDate);
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
     * Metodo: Desactiva una cuadrilla especificada 
     * @param crewToDeactivate Cuadrilla a desactivar
     * @param desc Motivo por el cual la cuadrilla ser� desactivada
     * @throws BusinessException
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jcasas
     */
    @WebMethod(operationName = "deactivateCrew", action = "deactivateCrew")
    public void deactivateCrew(@WebParam(name = "crewToDeactivate")
            final CrewVO crewToDeactivate, @WebParam(name = "desc") String desc) throws BusinessException {
        try {
            ejbRef.deactivateCrew(crewToDeactivate, desc);
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
     * Metodo: Crea una asociacion entre un empleado y una cuadrilla
     * @param obj Informacion basica de la relación entre el empleado y la cuadrilla
     * @throws BusinessException en caso de error al tratar de generar la relación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_params_null_or_missed</code> En caso de que el objeto tenga parámetros nulos que son obligatorios<br>
     * @author jcasas
     */
    @WebMethod(operationName = "createEmployeesCrew", action = "createEmployeesCrew")
    public void createEmployeesCrew(@WebParam(name = "obj") EmployeeCrewVO obj) throws BusinessException {
        try {
            ecFacadeBean.createEmployeesCrew(obj);
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
     * Metodo: Actualiza una asociacion entre un empleado y una cuadrilla
     * @param obj Informacion basica del empleado y la cuadrilla
     * @throws BusinessException en caso de error al tratar de actualizar la información,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_params_null_or_missed</code> En caso de que el objeto tenga parámetros nulos que son obligatorios<br>
     * @author jcasas
     */
    @WebMethod(operationName = "updateEmployeesCrew", action = "updateEmployeesCrew")
    public void updateEmployeesCrew(@WebParam(name = "obj") EmployeeCrewVO obj) throws BusinessException {
        try {
            ecFacadeBean.updateEmployeesCrew(obj);
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
     * Metodo: Elimina una asociacion entre un empleado y una cuadrilla
     * @param obj Informacion basica del empleado y la cuadrilla
     * @throws BusinessException en caso de error al borrar la información de la relación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_params_null_or_missed</code> En caso de que el objeto tenga parámetros nulos que son obligatorios<br>
     * @author jcasas
     */
    @WebMethod(operationName = "deleteEmployeesCrew", action = "deleteEmployeesCrew")
    public void deleteEmployeesCrew(@WebParam(name = "obj") EmployeeCrewVO obj) throws BusinessException {
        try {
            ecFacadeBean.deleteEmployeesCrew(obj);
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
     * Metodo: Obtiene un listado de relaciones entre empleados y cuadrillas
     * @return Listado de relaciones entre empleados y cuadrillas, una lista vacia en caso que no existan empleados
     * relacionados con cuadrillas
     * @throws BusinessException en caso de error al consultar las relaciones,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jcasas
     */
    @WebMethod(operationName = "getAllEmployeesCrew", action = "getAllEmployeesCrew")
    public List<EmployeeCrewVO> getAllEmployeesCrew() throws BusinessException {
        try {
            return ecFacadeBean.getAllEmployeesCrew();
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
     * Metodo: Obtiene un listado de relaciones entre empleados y cuadrillas de una cuadrilla especifica
     * @param id Id de la cuadrilla a consultar
     * @return Listado de relaciones entre empleados y cuadrillas pertenecientes a una cuadrilla especifica, una lista vacia en 
     * en caso que no existan empleados relacionados con la cuadrilla especificada
     * @throws BusinessException en caso de error al consultar las relaciones entre cuadrillas y empleados,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jcasas
     */
    @WebMethod(operationName = "getEmployeesCrewByCrewID", action = "getEmployeesCrewByCrewID")
    public List<EmployeeCrewVO> getEmployeesCrewByCrewID(@WebParam(name = "id") Long id) throws BusinessException {
        try {
            return ecFacadeBean.getEmployeesCrewByCrewID(id);
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
     * Metodo: Obtiene un estado de las cuadrillas con el codigo especificado
     * @param code Codigo de estado de la cuadrilla
     * @return Estado de cuadrilla cuyo código coincide con el especificado,
     * nulo en caso que no exista el estado con el código especificado
     * @throws BusinessException en caso de error al consultar el estado de la cuadrilla,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jcasas
     */
    @WebMethod(operationName = "getCrewStatusByCode", action = "getCrewStatusByCode")
    public CrewStatusVO getCrewStatusByCode(@WebParam(name = "code") String code) throws BusinessException {
        try {
            return csFacadeBean.getCrewStatusByCode(code);
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
     * Metodo: Obtiene un estado de las cuadrillas con el id especificado
     * @param id Id de estado de la cuadrilla
     * @return Tipo de estado de cuadrilla cuyo id coincide con el especificado,
     * nulo en caso que no exista el tipo de estado con el id especificado
     * @throws BusinessException en caso de error al consultar el estado de la cuadrilla,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jcasas
     */
    @WebMethod(operationName = "getCrewStatusByID", action = "getCrewStatusByID")
    public CrewStatusVO getCrewStatusByID(@WebParam(name = "id") Long id) throws BusinessException {
        try {
            return csFacadeBean.getCrewStatusByID(id);
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
     * Metodo: Obtiene todos los posibles estados de una cuadrilla
     * @return Listado con los posibles estados de una cuadrilla, una lista vacia en caso que no existan
     * estados de cuadrilla en el sistema
     * lista vacia en caso que no se encuentren estados en la persistencia
     * @throws BusinessException en caso de error al consultar los estados de la cuadrilla,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jcasas
     * 
     */
    @WebMethod(operationName = "getAllCrewStatus", action = "getAllCrewStatus")
    public List<CrewStatusVO> getAllCrewStatus() throws BusinessException {
        try {
            return csFacadeBean.getAllCrewStatus();
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
     * Metodo: Obtiene un tipo de cuadrilla con el codigo especificado
     * @param code Codigo del tipo de cuadrilla
     * @return Tipo de cuadrilla con el codigo especificado, nulo en caso que no exista el tipo con el código especificado
     * @throws BusinessException en caso de error al consultar el tipo de cuadrilla por código,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jcasas
     */
    @WebMethod(operationName = "getCrewTypesByCode", action = "getCrewTypesByCode")
    public CrewTypeVO getCrewTypesByCode(@WebParam(name = "code") String code) throws BusinessException {
        try {
            return ctFacadeBean.getCrewTypesByCode(code);
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
     * Metodo: Obtiene un tipo de cuadrilla con el id especificado
     * @param id Id del tipo de cuadrilla
     * @return Tipo de cuadrilla con el id especificado, nulo en caso que no se encuentre
     * el tipo de cuadrilla con ese id.
     * @throws BusinessException en caso de error al consultar el tipo de cuadrilla
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jcasas
     */
    @WebMethod(operationName = "getCrewTypesByID", action = "getCrewTypesByID")
    public CrewTypeVO getCrewTypesByID(@WebParam(name = "id") Long id) throws BusinessException {
        try {
            return ctFacadeBean.getCrewTypesByID(id);
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
     * Metodo: Obtiene todos los tipos de cuadrilla existentes
     * @return Listado con los tipos de cuadrilla existentes, una lista vacia en caso que no se
     * exista ningún tipo de cuadrilla
     * @throws BusinessException en caso de error al consultar todos los tipos de cuadrilla,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jcasas
     */
    @WebMethod(operationName = "getAllCrewTypes", action = "getAllCrewTypes")
    public List<CrewTypeVO> getAllCrewTypes() throws BusinessException {
        try {
            return ctFacadeBean.getAllCrewTypes();
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
     * Metodo: Obtiene las cuadrillas que estan asociadas a un dealer especifico
     * @param dealerId Id del dealer
     * @return Listado de cuadrillas pertenecientes a un dealer, una lista vacia en caso que el dealer no tenga
     * registrada ninguna cuadrilla
     * @throws BusinessException en caso de error al tratar de consultar las cuadrillas de la compañía
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jcasas
     */
    @WebMethod(operationName = "getCrewsByDealerId", action = "getCrewsByDealerId")
    public List<CrewVO> getCrewsByDealerId(@WebParam(name = "dealerId")Long dealerId) throws BusinessException {
    	 try {
             return ejbRef.getCrewsByDealerId(dealerId);
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
     * Metodo: Obtiene las cuadrillas que estan asociadas a un dealer especifico
     * @param dealerId Id del dealer
     * @return Listado de cuadrillas pertenecientes a un dealer, una lista vacia en caso que el dealer no tenga
     * registrada ninguna cuadrilla
     * @throws BusinessException en caso de error al tratar de consultar las cuadrillas de la compañía
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jcasas
     */
    @WebMethod(operationName = "getActiveCrewsByDealerId", action = "getActiveCrewsByDealerId")
    public List<CrewVO> getActiveCrewsByDealerId(@WebParam(name = "dealerId")Long dealerId) throws BusinessException {
    	 try {
             return ejbRef.getActiveCrewsByDealerId(dealerId);
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
     * Metodo: Obtiene las cuadrillas que estan asociadas a un dealer especifico y un responsable en particular
     * @param responsibleName Nombre del responsable
     * @param idDealer Id del dealer
     * @return Listado con las cuadrillas que estan asociadas a un dealer especifico y un responsable en particular, una
     * lista vacia en caso que no se encuentren cuadrillas asociadas al dealer y con ese responsable
     * @throws BusinessException en caso de error al consultar las cuadrillas por dealer y responsable
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jcasas
     */
    @WebMethod(operationName = "getCrewsByResponsibleNameAndDealerId", action = "getCrewsByResponsibleNameAndDealerId")
    public List<CrewVO> getCrewsByResponsibleNameAndDealerId(@WebParam(name = "responsibleName")String responsibleName, @WebParam(name = "idDealer")Long idDealer) throws BusinessException {
    	 try {
             return ejbRef.getCrewsByResponsibleNameAndDealerId(responsibleName, idDealer);
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
     * Metodo: Obtiene todas las relaciones entre empleados y cuadrillas que estan asociadas a un dealer en particular
     * @param dealerId Id del dealer
     * @return Listado con las relaciones entre empleados y cuadrillas que estan asociadas a un dealer en particular, una lista
     * vacía en caso que no se encuentren relaciones entre empleados y cuadrillas de la compañía especificada.
     * @throws BusinessException en caso de error al consultar la lista de relaciones entre empleados y cuadrillas por compañía
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jcasas
     */
    @WebMethod(operationName = "getEmployeesCrewByDealerID", action = "getEmployeesCrewByDealerID")
    public List<EmployeeCrewVO> getEmployeesCrewByDealerID(@WebParam(name = "dealerId")Long dealerId) throws BusinessException{
    	try {
            return ecFacadeBean.getEmployeesCrewByDealerID(dealerId);
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
     * Metodo: Obtiene las cuadrillas que estan asociadas a un dealer especifico y con un tipo especifico que se
     * encuentran activas
     * @param dealerId Id del dealer
     * @param crewType Tipo de la cuadrilla
     * @return Listado de cuadrillas pertenecientes a un dealer, una lista vacia en caso que el dealer no tenga
     * registrada ninguna cuadrilla
     * @throws BusinessException en caso de error al tratar de consultar las cuadrillas de la compañía
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jnova
     */
    @WebMethod(operationName = "getCrewsByDealerIdAndType", action = "getCrewsByDealerIdAndType")
    public List<CrewVO> getCrewsByDealerIdAndType(@WebParam(name = "dealerId")Long dealerId , @WebParam(name = "crewType")Long crewType) throws BusinessException {
    	 try {
             return ejbRef.getCrewsByDealerIdAndType(dealerId, crewType);
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
     * Metodo: Obtiene todas las relaciones entre empleados y cuadrillas que estan asociadas a un dealer en particular y de
     * un tipo de cuadrilla
     * @param dealerId Id del dealer
     * @param crewType Id del tipo de cuadrilla: estatica o dinamica
     * @return Listado con las relaciones entre empleados y cuadrillas que estan asociadas a un dealer en particular, una lista
     * vacía en caso que no se encuentren relaciones entre empleados y cuadrillas de la compañía especificada.
     * @throws BusinessException en caso de error al consultar la lista de relaciones entre empleados y cuadrillas por compañía
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jnova
     */
    @WebMethod(operationName = "getEmployeesCrewByDealerIDAndCrewType", action = "getEmployeesCrewByDealerIDAndCrewType")
    public List<EmployeeCrewVO> getEmployeesCrewByDealerIDAndCrewType(@WebParam(name = "dealerId")Long dealerId , @WebParam(name = "crewType")Long crewType) throws BusinessException{
    	try {
            return ecFacadeBean.getEmployeesCrewByDealerIDAndCrewType(dealerId, crewType);
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
     * Method: Retorna un listado de Crews por el
     * id del Dealer con la carga de la cuadrilla
     * @param  Long dealerId
     * @param  Long crewId
     * @return - CrewVO Cuadrilla con la informacion de la carga
     * @throws BusinessException
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author Joan Lopez
     */
    @WebMethod(operationName = "getCrewAmountByDealerId", action = "getCrewAmountByDealerId")
	public CrewVO getCrewAmountByDealerId(@WebParam(name = "crewId")Long crewId,@WebParam(name = "dealerId")Long dealerId) throws BusinessException{
    	try {
            return ejbRef.getCrewAmountByDealerId(crewId, dealerId);
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
     * 
     * Metodo: Retorna un listado de Empleados por cuadrilla
     * filtrando por nombre del empleado, dealer, tipo de cuadrilla
     * y estado de la cuadrilla
     * @param Long dealerId
     * @param String isResponsible
     * @param Long crewType
     * @param Long responsibleName
     * @return List<EmployeeCrewVO> Listado con la informacion de cuadrillas de un dealer con los datos del responsable y la carga de la cuadrilla.
     * @throws BusinessException
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricci�n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error gen�rico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gram�tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author Joan Lopez
     */
    @WebMethod(operationName = "getEmployeesCrewByDealerIdAndCrewTypeAndResponsible", action = "getEmployeesCrewByDealerIdAndCrewTypeAndResponsible")
	public List<EmployeeCrewVO> getEmployeesCrewByDealerIdAndCrewTypeAndResponsible ( @WebParam(name = "dealerId")Long dealerId, @WebParam(name = "crewType")Long crewType, @WebParam(name = "responsibleName")String responsibleName) throws BusinessException {
    	try {
            return ecFacadeBean.getEmployeesCrewByDealerIdAndCrewTypeAndResponsible(dealerId, crewType, responsibleName);
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
	 * 
	 * Metodo: Consulta las cuadrillas de un dealer, por tipo de cuadrilla, id de dealer y nombre del responsable de la cuadrilla
	 * @param dealerId
	 * @param crewType
	 * @param responsableName
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
    @WebMethod(operationName = "getCrewsByDealerIdAndTypeAndResponsableName", action = "getCrewsByDealerIdAndTypeAndResponsableName")
    public List<CrewVO> getCrewsByDealerIdAndTypeAndResponsableName(@WebParam(name = "dealerId")Long dealerId ,@WebParam(name = "crewType") Long crewType,@WebParam(name = "responsableName")String responsableName) throws BusinessException {
    	 try {
             return ejbRef.getCrewsByDealerIdAndTypeAndResponsableName(dealerId, crewType,responsableName);
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
	 * 
	 * Metodo: Retorna un listado de cuadrillas desactivadas
	 * @param  crewOff CrewOff
	 * @return List<CrewOffVO>
	 * @throws BusinessException 
	 * @author jalopez
	 */
    @WebMethod(operationName = "getCrewOffByCrew", action = "getCrewOffByCrew")
	public List<CrewOffVO> getCrewOffByCrew(CrewOff crewOff) throws BusinessException{
    	 try {
             return ejbRef.getCrewOffByCrew(crewOff);
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
