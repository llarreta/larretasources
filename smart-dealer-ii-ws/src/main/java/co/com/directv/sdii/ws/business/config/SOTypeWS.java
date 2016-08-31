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
import co.com.directv.sdii.facade.config.ConfigSOTiposFacadeLocal;
import co.com.directv.sdii.model.vo.ShippingOrderTypeVO;


/**
 * Expone servicios web del módulo de Configuración de Tipos de Shipping Orders.
 * 
 * Fecha de Creación: Mar 25, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.config.ConfigSOTiposFacadeLocal
 */
@MTOM
@WebService()
@Stateless()
public class SOTypeWS {
    @EJB
    private ConfigSOTiposFacadeLocal ejbRef;
    
    /**
     * Metodo: Este método retorna una instancia de SHIPPING_ORDER_TYPES por ID.
     * @param id - Long identificador de de la shipping order que se está consultando
     * @return ShippingOrderTypeVO tipo de shipping order dado el identificador, nulo en caso que no se
     * encuentre un tipo de so por el id especificado
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
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
    @WebMethod(operationName = "getShippingOrderTypeByID", action="getShippingOrderTypeByID")
    public ShippingOrderTypeVO getShippingOrderTypeByID(@WebParam(name = "id")
    Long id) throws BusinessException {
        return ejbRef.getShippingOrderTypeByID(id);
    }

    /**
     * Metodo: Este método retorna una instancia de SHIPPING_ORDER_TYPES por SO_TYPE_CODE.
     * @param code - String código de shipping order a consultar
     * @return ShippingOrderTypeVO tipo de shipping order cuyo código coincide con el especificado,
     * nulo en caso que no se encuentre ninguno con ese código
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
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
    @WebMethod(operationName = "getShippingOrderTypeByCode", action="getShippingOrderTypeByCode")
    public ShippingOrderTypeVO getShippingOrderTypeByCode(@WebParam(name = "code")
    String code) throws BusinessException {
        return ejbRef.getShippingOrderTypeByCode(code);
    }

    /**
     * Metodo: Este método retorna una lista de SHIPPING_ORDER_TYPES por SO_TYPE_NAME.
     * @param name - String nombre por el cual se realizará el filtro
     * @return List<ShippingOrderTypeVO> lista con los tipos de shipping order 
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
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
    @WebMethod(operationName = "getShippingOrderTypeByName", action="getShippingOrderTypeByName")
    public List<ShippingOrderTypeVO> getShippingOrderTypeByName(@WebParam(name = "name")
    String name) throws BusinessException {
        return ejbRef.getShippingOrderTypeByName(name);
    }

    /**
     * Metodo: Este método retorna una lista de Todas los SHIPPING_ORDER_TYPES.
     * @return List<ShippingOrderTypeVO>  lista con los tipos de shipping order registrados en el sistema,
     * una lista vacia en caso que no exista ninguna shipping order
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
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
    @WebMethod(operationName = "getAllShippingOrderType", action="getAllShippingOrderType")
    public List<ShippingOrderTypeVO> getAllShippingOrderType() throws BusinessException {
        return ejbRef.getAll();
    }

    /**
     * Metodo: Este método crea un SHIPPING_ORDER_TYPES.
     * @param obj - ShippingOrderTypeVO objeto que encapsula la información de la so a ser creada
     * no debe venir especificada la propiedad id, en caso que venga, se generará un error
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
     * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_code_name_already_exist</code> En caso se encuentre un estado de shipping order con el mismo código<br>
     * @author gfandino
     */
    @WebMethod(operationName = "createShippingOrderType", action="createShippingOrderType")
    public void createShippingOrderType(@WebParam(name = "obj")
    ShippingOrderTypeVO obj) throws BusinessException {
        ejbRef.createShippingOrderType(obj);
    }

    /**
     * Metodo: Este método actualiza una SHIPPING_ORDER_TYPES.
     * @param obj - ShippingOrderTypeVO objeto con la información del tipo de so a ser actualizado,
     * se requiere la propiedad id, en caso que esta no venga, se tratará de crear un nuevo tipo de SO.
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
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
    @WebMethod(operationName = "updateShippingOrderType", action="updateShippingOrderType")
    public void updateShippingOrderType(@WebParam(name = "obj")
    ShippingOrderTypeVO obj) throws BusinessException {
        ejbRef.updateShippingOrderType(obj);
    }

    /**
     * Metodo: Este método elimina un SHIPPING_ORDER_TYPES.
     * @param obj - ShippingOrderTypeVO
     * @throws BusinessException en caso de error al tratar de ejecutar la operación,
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
    @WebMethod(operationName = "deleteShippingOrderType", action="deleteShippingOrderType")
    public void deleteShippingOrderType(@WebParam(name = "obj")
    ShippingOrderTypeVO obj) throws BusinessException {
        ejbRef.deleteShippingOrderType(obj);
    }

}
