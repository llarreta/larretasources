/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.directv.sdii.ws.business.dealers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigMatrizInventarioPorServicioFacadeLocal;
import co.com.directv.sdii.model.pojo.RequiredServiceElementId;
import co.com.directv.sdii.model.vo.ElementTypeVO;
import co.com.directv.sdii.model.vo.RequiredServiceElementVO;
import co.com.directv.sdii.model.vo.ServiceVO;


/**
 * Expone servicios web del módulo de Configuración de Matriz de Inventario Requerido por Servicio.
 * 
 * Fecha de Creación: Mar 25, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 *  
 *  @see co.com.directv.sdii.facade.config.ConfigMatrizInventarioPorServicioFacadeLocal
 */
@MTOM
@WebService()
@Stateless()
public class RequiredServiceElementsWS {
    @EJB
    private ConfigMatrizInventarioPorServicioFacadeLocal ejbRef;
    
    /**
     * Metodo: Este método permite consultar Elemento requerido por servicio dado su identificador
     * @param id - RequiredServiceElementId Identificador de Elemento requerido por servicio. Debe contener:<br>
     * - serviceId<br>
     * - elementTypeId
     * @return RequiredServiceElementVO Elemento requerido por servicio cuyo identificador es el especificado; nulo en 
     * otro caso
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de estado de programa por su identificador<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br> 
     * @author gfandino
     */
    @WebMethod(operationName = "getRequiredServiceElementByID", action="getRequiredServiceElementByID")
    public RequiredServiceElementVO getRequiredServiceElementByID(@WebParam(name = "id")
    RequiredServiceElementId id) throws BusinessException {
        return ejbRef.getRequiredServiceElementByID(id);
    }

    /**
     * Metodo: Este método retorna una lista de Elementos requeridos por servicio dado un servicio
     * @param service - ServiceVO Servicio por el cual se va a consultar. Debe contener: <br>
     * - id
     * @return List<RequiredServiceElementVO> Lista de elementos asociados al servicio especificado; vacio en otro caso
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de estado de programa por su identificador<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>   
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getRequiredServiceElementByService", action="getRequiredServiceElementByService")
    public List<RequiredServiceElementVO> getRequiredServiceElementByService(@WebParam(name = "service")
    ServiceVO service) throws BusinessException {
        return ejbRef.getRequiredServiceElementByService(service);
    }
    
    /**
     * Metodo: Este método retorna una lista de Elementos requeridos por servicio dado un servicio
     * @param service - ServiceVO Servicio por el cual se va a consultar. Debe contener: <br>
     * - id
     * @return List<RequiredServiceElementVO> Lista de elementos asociados al servicio especificado; vacio en otro caso
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de estado de programa por su identificador<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>   
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getRequiredServiceElementsByServiceIdOnlySelectedElements", action="getRequiredServiceElementsByServiceIdOnlySelectedElements")
    public List<RequiredServiceElementVO> getRequiredServiceElementsByServiceIdOnlySelectedElements(@WebParam(name = "serviceId") Long serviceId) throws BusinessException{
    	return ejbRef.getRequiredServiceElementsByServiceIdOnlySelectedElements(serviceId);
    }


    /**
     * Metodo: Este método permite consultar un conjunto de Elementos requeridos de servicio por el tipo de elemento
     * @param elementType - ElementTypeVO Tipo de elemento por el cual se va a consultar. Debe contener:<br> 
     * - id
     * @return List<RequiredServiceElementVO> Lista de elementos requeridos de servicio asociados e un tipo de elemento especificado;
     * nulo en otro caso
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de estado de programa por su identificador<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getRequiredServiceElementByElementType", action="getRequiredServiceElementByElementType")
    public List<RequiredServiceElementVO> getRequiredServiceElementByElementType(@WebParam(name = "elementType")
    ElementTypeVO elementType) throws BusinessException {
        return ejbRef.getRequiredServiceElementByElementType(elementType);
    }

    /**
     * Metodo: Este método permite consultar un elemento requerido de servicio dado un servicio y un tipo de elemento
     * @param service - ServiceVO Servicio por el cual se va a consultar. Debe contener <br>
     * - id
     * @param elementType - ElementTypeVO Tipo de elemento por el cual se va a consultar. Debe contener <br>
     * - id
     * @return RequiredServiceElementVO Elemento requerido de servicio asociado al servicio y tipo de elemento 
     * especificados; nulo en otro caso.
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de estado de programa por su identificador<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getRequiredServiceElementByServiceElementType", action="getRequiredServiceElementByServiceElementType")
    public RequiredServiceElementVO getRequiredServiceElementByServiceElementType(@WebParam(name = "service")
    ServiceVO service, @WebParam(name = "elementType")
    ElementTypeVO elementType) throws BusinessException {
        return ejbRef.getRequiredServiceElementByServiceElementType(service, elementType);
    }

    /**
     * Metodo: Este método permite consultar todos los elementos requeridos de servcio registrados.
     * @return List<RequiredServiceElementVO> Lista de elementos requeridos de servicio; vacio en caso de no existir
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de estado de programa por su identificador<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getAllRequiredServiceElements", action="getAllRequiredServiceElements")
    public List<RequiredServiceElementVO> getAllRequiredServiceElements() throws BusinessException {
        return ejbRef.getAll();
    }

    /**
     * Metodo: Este método permite crear un Elemento requerido de servicio.
     * @param obj - RequiredServiceElementVO Elemento requerido de servicio que se va a crear. Debe contener:<br>
     * - id service<br>
     * - id elementType<br>
     * - quantity<br>
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de estado de programa por su identificador<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author gfandino
     */
    @WebMethod(operationName = "createRequiredServiceElement", action="createRequiredServiceElement")
    public void createRequiredServiceElement(@WebParam(name = "obj")
    RequiredServiceElementVO obj) throws BusinessException {
        ejbRef.createRequiredServiceElement(obj);
    }

    /**
     * Metodo: Este método permite actualizar un elemento requerido de servicio
     * @param obj - RequiredServiceElementVO Elemento requerido de servicio que se quiere actualizar. Debe contener: <br>
     * - id: (serviceId, elementTypeId)<br>
     * - id service<br>
     * - id elementType<br>
     * - quantity<br>
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de estado de programa por su identificador<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author gfandino
     */
    @WebMethod(operationName = "updateRequiredServiceElement", action="updateRequiredServiceElement")
    public void updateRequiredServiceElement(@WebParam(name = "obj")
    RequiredServiceElementVO obj) throws BusinessException {
        ejbRef.updateRequiredServiceElement(obj);
    }

    /**
     * Metodo: Este método permite eliminar un elemento requerido de servico existente en el sistema
     * @param obj - RequiredServiceElementVO Elemento requerido de servicio que se desea eliminar. Debe contener:<br>
     * - id: (serviceId, elementTypeId)<br>
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de estado de programa por su identificador<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author gfandino
     */
    @WebMethod(operationName = "deleteRequiredServiceElement", action="deleteRequiredServiceElement")
    public void deleteRequiredServiceElement(@WebParam(name = "obj")
    RequiredServiceElementVO obj) throws BusinessException {
        ejbRef.deleteRequiredServiceElement(obj);
    }

    /**
     * Metodo: Este método permite consultar un Tipo de Elemento dado su identificador
     * @param id - Long Identificador del tipo de elemento
     * @return ElementTypeVO Tipo de elemento cuyo identificador corresponde al especificado, nulo en otro caso
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de estado de programa por su identificador<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getElementTypeByID", action="getElementTypeByID")
    public ElementTypeVO getElementTypeByID(@WebParam(name = "id")
    Long id) throws BusinessException {
        return ejbRef.getElementTypeByID(id);
    }

    /**
     * Metodo: Este método permite consultar un servicio por su identificador
     * @param id - Long Identificador del servicio
     * @return ServiceVO Servicio cuyo identificador es el especificado; nulo en otro caso
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de estado de programa por su identificador<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author gfandino
     */
    @WebMethod(operationName = "getServiceByID", action="getServiceByID")
    public ServiceVO getServiceByID(@WebParam(name = "id")
    Long id) throws BusinessException {
        return ejbRef.getServiceByID(id);
    }

}
