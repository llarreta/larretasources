package co.com.directv.sdii.ws.business.dealers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.EmployeeVehicleFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.MembershipTypesFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.VehicleStatusFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.VehicleTypesFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.VehiclesFacadeBeanLocal;
import co.com.directv.sdii.model.vo.EmployeeVehicleVO;
import co.com.directv.sdii.model.vo.MembershipTypeVO;
import co.com.directv.sdii.model.vo.VehicleStatusVO;
import co.com.directv.sdii.model.vo.VehicleTypeVO;
import co.com.directv.sdii.model.vo.VehicleVO;

/**
 * Expone los servicios para la configuraci�n de vehiculos
 * 
 * Fecha de Creación: 25/03/2010
 * @author jcasas <a href="jcasas@intergrupo.com">e-mail</a>
 * @version 1.0   
 */
@MTOM
@WebService()
@Stateless()
public class VehiclesWS {

    @EJB
    private VehiclesFacadeBeanLocal vehicleBean;
    @EJB
    private VehicleStatusFacadeBeanLocal vehicleStatusBean;
    @EJB
    private VehicleTypesFacadeBeanLocal vehicleTypesBean;

    @EJB
    private MembershipTypesFacadeBeanLocal membershipTypesBean;

    @EJB
    private EmployeeVehicleFacadeBeanLocal employeeVehicleBean;


    /**
     * Metodo: Crea un vehiculo en el sistema
     * @param obj - VehicleVO Objeto con la informacion basica del vehiculo a crear
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "createVehicles", action = "createVehicles")
    public void createVehicles(@WebParam(name = "obj") VehicleVO obj) throws BusinessException {
        vehicleBean.createVehicles(obj);
    }

    /**
     * Método: Obtiene un vehiculo con el id especificado
     * @param id - Long Id del vehiculo
     * @return obj Objeto con la informacion basica del vehiculo
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "getVehiclesByID", action = "getVehiclesByID")
    public VehicleVO getVehiclesByID(@WebParam(name = "id") Long id) throws BusinessException {
        return vehicleBean.getVehiclesByID(id);
    }

    /**
     * Método: Actualiza un vehiculo en el sistema
     * @param obj - VehicleVO Objeto con la informacion basica del vehiculo a actualizar
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "updateVehicles", action = "updateVehicles")
    public void updateVehicles(@WebParam(name = "obj") VehicleVO obj) throws BusinessException {
        vehicleBean.updateVehicles(obj);
    }

    /**
     * Método: Elimina un vehiculo en el sistema
     * @param obj - VehicleVO Objeto con la informacion basica del vehiculo a eliminar
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "deleteVehicles", action = "deleteVehicles")
    public void deleteVehicles(@WebParam(name = "obj") VehicleVO obj) throws BusinessException {
        vehicleBean.deleteVehicles(obj);
    }

    /**
     * Método: Obtiene todos los vehiculos del sistema
     * @return  List<VehicleVO> Listado con los vehiculos del sistema
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "getAllVehicles", action = "getAllVehicles")
    public List<VehicleVO> getAllVehicles() throws BusinessException {
        return vehicleBean.getAllVehicles();
    }
    
    /**
     * Método: Obtiene todos los vehiculos del sistema con la informacion basica
     * @return List<VehicleVO>  Listado de los vehiculos del sistema con la informacion basica
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "getAllVehiclesOnlyBasicInfo", action = "getAllVehiclesOnlyBasicInfo")
    public List<VehicleVO> getAllVehiclesOnlyBasicInfo() throws BusinessException {
        return vehicleBean.getAllVehiclesOnlyBasicInfo();
    }

    /**
     * Método: Obtiene todas las placas de los vehiculos registradas en el sistema
     * @return List<String> Listado de las placas de los vehiculos registrados en el sistema
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "getAllVehiclePlates", action = "getAllVehiclePlates")
    public List<String> getAllVehiclePlates() throws BusinessException {
        return vehicleBean.getAllVehiclePlates();
        
    }
    
    /**
     * Método: Obtiene un estado del vehiculo por el codigo especificado
     * @param code - String Codigo del vehiculo
     * @return VehicleStatusVO Objeto que contiene la informacion basica del vehiculo
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "getVehicleStatusByCode", action = "getVehicleStatusByCode")
    public VehicleStatusVO getVehicleStatusByCode(@WebParam(name = "code") String code) throws BusinessException {
        return vehicleStatusBean.getVehicleStatusByCode(code);
    }

    /**
     * Método: Obtiene un estado del vehiculo por el id especificado
     * @param id - Long Id del vehiculo
     * @return VehicleStatusVO Objeto con la informacion basica del vehiculo
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "getVehicleStatusByID", action = "getVehicleStatusByID")
    public VehicleStatusVO getVehicleStatusByID(@WebParam(name = "id") Long id) throws BusinessException {
        return vehicleStatusBean.getVehicleStatusByID(id);
    }

    /**
     * Método: Obtiene todos los estados de los vehiculos
     * @return List<VehicleStatusVO> Listado de los estados de los vehiculos
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "getAllVehicleStatus", action = "getAllVehicleStatus")
    public List<VehicleStatusVO> getAllVehicleStatus() throws BusinessException {
        return vehicleStatusBean.getAllVehicleStatus();
    }

    /**
     * Método: Obtiene un tipo de vehiculo con el codigo especificado 
     * @param code - String Codigo del tipo de vehiculo
     * @return VehicleTypeVO Objeto con la informacion basica del tipo de vehiculo
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "getVehicleTypesByCode", action = "getVehicleTypesByCode")
    public VehicleTypeVO getVehicleTypesByCode(@WebParam(name = "code") String code) throws BusinessException {
        return vehicleTypesBean.getVehicleTypesByCode(code);
    }

    /**
     * Método: Obtiene el tipo de vehiculo con el id especificado
     * @param id - Long Id del tipo de vehiculo
     * @return VehicleTypeVO Objeto con la informacion basica del tipo de vehiculo
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "getVehicleTypesByID", action = "getVehicleTypesByID")
    public VehicleTypeVO getVehicleTypesByID(@WebParam(name = "id") Long id) throws BusinessException {
        return vehicleTypesBean.getVehicleTypesByID(id);
    }

    /**
     * Método: Obtiene un vehiculo con la placa especificada
     * @param plate - String Placa del vehiculo
     * @return VehicleVO Objeto con la informacion basica del tipo de vehiculo
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "getVehicleByPlate", action = "getVehicleByPlate")
    public VehicleVO getVehicleByPlate(@WebParam(name = "plateNumber") String plate) throws BusinessException {
        return vehicleBean.getVehicleByPlate(plate);
    }

    /**
     * Método: Obtiene todos los tipos de vehiculos
     * @return List<VehicleTypeVO> Listado con los tipos de vehiculos
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "getAllVehicleTypes", action = "getAllVehicleTypes")
    public List<VehicleTypeVO> getAllVehicleTypes() throws BusinessException {
        return vehicleTypesBean.getAllVehicleTypes();
    }

    /**
     * Método: Obtiene un listado de vehiculos pertenecientes a un codigo de dealer y codigo depot
     * @param dealerCode - long Codigo del dealer
     * @param depotCode - StringCodigo depot
     * @return List<VehicleVO> Listado de vehiculos pertenecientes a un dealer
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "getVehiclesByDealerCodOrDealerDepotCode", action = "getVehiclesByDealerCodOrDealerDepotCode")
    public List<VehicleVO> getVehiclesByDealerCodeOrDepotCode(@WebParam(name = "dealerCode") long dealerCode, @WebParam(name = "depotCope") String depotCode) throws BusinessException {
        return vehicleBean.getVehiclesByDealerCodeOrDepotCode(dealerCode, depotCode);
    }
    
    /**
     * Método: Obtiene un listado de vehiculos pertenecientes a un dealer
     * @param dealerId - long Id del dealer
     * @return List<VehicleVO> Listado de vehiculos pertenecientes a un dealer
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "getVehiclesByDealerId", action = "getVehiclesByDealerId")
    public List<VehicleVO> getVehiclesByDealerId(@WebParam(name = "dealerId") long dealerId) throws BusinessException {
        return vehicleBean.getVehiclesByDealerId(dealerId);
    }
    
    /**
	 * 
	 * Metodo: Consulta los vehiculos activos por id de dealer
	 * @param dealerId
	 * @return Lista de vehiculos activos asociados a un dealer
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
    @WebMethod(operationName = "getActiveVehiclesByDealerId", action = "getActiveVehiclesByDealerId")
	public List<VehicleVO> getActiveVehiclesByDealerId(@WebParam(name = "dealerId") long dealerId)throws BusinessException{
		return vehicleBean.getActiveVehiclesByDealerId(dealerId);
	}

    /**
     * Método: Obtiene todos los tipos de membresia
     * @return List<MembershipTypeVO> Listado con los tipos de membresia
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "getAllMembershipTypes", action = "getAllMembershipTypes")
    public List<MembershipTypeVO> getAllMembershipTypes() throws BusinessException {
        return membershipTypesBean.getAllMembershipTypes();
    }

    /**
     * Método: Crea  una relacion entre un empleado y un vehiculo
     * @param employeeVehicle - EmployeeVehicleVO Objeto con la informacion basica de la relacion entre un empleado y un vehiculo
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "createEmployeeVehicle", action = "createEmployeeVehicle")
    public void createEmployeeVehicle(@WebParam(name = "employeeVehicle")EmployeeVehicleVO employeeVehicle) throws BusinessException {
        employeeVehicleBean.createEmployeeVehicle(employeeVehicle);
    }
    
    /**
	 * 
	 * Metodo: Consulta los vehiculos por dealer y por placa
	 * @param dealerId
	 * @param plate
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
    @WebMethod(operationName = "getVehiclesByDealerIdAndPlate", action = "getVehiclesByDealerIdAndPlate")
    public List<VehicleVO> getVehiclesByDealerIdAndPlate(@WebParam(name = "dealerId") Long dealerId,@WebParam(name = "plate") String plate) throws BusinessException {
        return vehicleBean.getVehiclesByDealerIdAndPlate(dealerId, plate);
    }
    
    /**
	 * 
	 * Metodo: Consulta los vehiculos por dealer y por placa
	 * @param dealerId
	 * @param plate
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
    @WebMethod(operationName = "getVehiclesByDealerIdAndStatusCodeOrPlate", action = "getVehiclesByDealerIdAndStatusCodeOrPlate")
	public List<VehicleVO> getVehiclesByDealerIdAndStatusCodeOrPlate(Long dealerId,String plate) throws BusinessException {
		return vehicleBean.getVehiclesByDealerIdAndStatusCodeOrPlate(dealerId, plate);
	}
    
}
