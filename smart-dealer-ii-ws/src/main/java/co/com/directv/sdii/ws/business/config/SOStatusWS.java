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
import co.com.directv.sdii.facade.config.ConfigSOEstadosFacadeLocal;
import co.com.directv.sdii.model.vo.ShippingOrderStatusVO;

/**
 * Expone servicios web del módulo de Configuración de Estados de SO.
 *
 * Caso de Uso CFG - 20 - Gestionar Códigos de Estado de Shipping Order
 * Fecha de Creación: Mar 25, 2010
 *
 * @author Jimmy Vélez Muñoz
 */
@MTOM
@WebService()
@Stateless()
public class SOStatusWS {
    @EJB
    private ConfigSOEstadosFacadeLocal ejbRef;

    /**
     * Metodo: Este método retorna una instancia de SHIPPING_ORDER_STATUS por ID.
     * @param id - Long
     * @return ShippingOrderStatusVO
     * @throws BusinessException  
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
    @WebMethod(operationName = "getShippingOrderStatusByID", action="getShippingOrderStatusByID")
    public ShippingOrderStatusVO getShippingOrderStatusByID(@WebParam(name = "id")
    Long id) throws BusinessException {
        return ejbRef.getShippingOrderStatusByID(id);
    }

    /**
     * Metodo: Este método retorna una instancia de SHIPPING_ORDER_STATUS por SO_STATUS_CODE.
     * @param code - String
     * @return ShippingOrderStatusVO
     * @throws BusinessException  
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
    @WebMethod(operationName = "getShippingOrderStatusByCode", action="getShippingOrderStatusByCode")
    public ShippingOrderStatusVO getShippingOrderStatusByCode(@WebParam(name = "code")
    String code) throws BusinessException {
        return ejbRef.getShippingOrderStatusByCode(code);
    }

    /**
     * Metodo: Este método retorna una lista de SHIPPING_ORDER_STATUS por SO_STATUS_NAME.
     * @param name - String
     * @return List<ShippingOrderStatusVO>
     * @throws BusinessException  
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
    @WebMethod(operationName = "getShippingOrderStatusByName", action="getShippingOrderStatusByName")
    public List<ShippingOrderStatusVO> getShippingOrderStatusByName(@WebParam(name = "name")
    String name) throws BusinessException {
        return ejbRef.getShippingOrderStatusByName(name);
    }

    /**
     * Metodo: Este método retorna una lista de Todas los SHIPPING_ORDER_STATUS.
     * @return List<ShippingOrderStatusVO>
     * @throws BusinessException  
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
    @WebMethod(operationName = "getAllShippingOrderStatus", action="getAllShippingOrderStatus")
    public List<ShippingOrderStatusVO> getAllShippingOrderStatus() throws BusinessException {
        return ejbRef.getAll();
    }

    /**
     * Metodo: Este método crea un SHIPPING_ORDER_STATUS.
     * @param obj - ShippingOrderStatusVO
     * @throws BusinessException  
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_code_name_already_exist</code> En caso que exista otra shipping order con el mismo código de la que se desea crear<br> 
     * @author gfandino
     */
    @WebMethod(operationName = "createShippingOrderStatus", action="createShippingOrderStatus")
    public void createShippingOrderStatus(@WebParam(name = "obj")
    ShippingOrderStatusVO obj) throws BusinessException {
        ejbRef.createShippingOrderStatus(obj);
    }

    /**
     * Metodo: Este método actualiza una SHIPPING_ORDER_STATUS.
     * @param obj - ShippingOrderStatusVO
     * @throws BusinessException  
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
    @WebMethod(operationName = "updateShippingOrderStatus", action="updateShippingOrderStatus")
    public void updateShippingOrderStatus(@WebParam(name = "obj")
    ShippingOrderStatusVO obj) throws BusinessException {
        ejbRef.updateShippingOrderStatus(obj);
    }

    /**
     * Metodo: Este método elimina un SHIPPING_ORDER_STATUS.
     * @param obj - ShippingOrderStatusVO
     * @throws BusinessException  
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
    @WebMethod(operationName = "deleteShippingOrderStatus", action="deleteShippingOrderStatus")
    public void deleteShippingOrderStatus(@WebParam(name = "obj")
    ShippingOrderStatusVO obj) throws BusinessException {
        ejbRef.deleteShippingOrderStatus(obj);
    }

}
