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
import co.com.directv.sdii.facade.dealers.PositionsFacadeBeanLocal;
import co.com.directv.sdii.model.vo.PositionVO;

/**
 * Expone los servicios para la configuraci�n de Cargos
 * 
 * Fecha de Creaci�n: 25/03/2010
 * @author jcasas <a href="jcasas@intergrupo.com">e-mail</a>
 * @version 1.0   
 */
@MTOM
@WebService()
@Stateless()
public class PositionsWS {

    @EJB
    private PositionsFacadeBeanLocal ejbRef;

    /**
     * Método: Permite crear un cargo en el sistema
     * @param obj - PositionVO Objeto con los datos basicos del cargo. Debe contener:<br>
     * - id de dealer<br>
     * - positionNamer<br>
     * - positionCode<br>
     * - description<br>
     * - id country<br>
     * @throws BusinessException en caso de error al ejecutar la operación de creación de una posición<br> Códigos:
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
    @WebMethod(operationName = "createPositions", action = "createPositions")
    public void createPositions(@WebParam(name = "obj") PositionVO obj) throws BusinessException {
        ejbRef.createPositions(obj);
    }

    /**
     * Método: Permite consultar un cargo con el identificador especificado
     * @param id - Long Id del cargo
     * @return Objeto con la informacion basica del cargo cuyo identificador corresponde al especificado; nulo en caso de no existir
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de una posición dado su identificador <br> Códigos:
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
    @WebMethod(operationName = "getPositionsByID", action = "getPositionsByID")
    public PositionVO getPositionsByID(@WebParam(name = "id") Long id) throws BusinessException {
        return ejbRef.getPositionsByID(id);
    }

    /**
     * Método: Permite consultar un cargo con el codigo especificado
     * @param code - PositionVO Codigo del cargo
     * @return PositionVO Objeto con la informacion basica del cargo cuyo código corresponde al asignado; nulo en otro caso.
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de una posición dado su código <br> Códigos:
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
    @WebMethod(operationName = "getPositionsByPositionCode", action = "getPositionsByPositionCode")
    public PositionVO getPositionsByPositionCode(@WebParam(name = "code") String code) throws BusinessException {
        return ejbRef.getPositionsByPositionCode(code);
    }
    
    /**
     * Método: Obtiene todas las posiciones que contienen el nombre especificado
     * @param positionName - StringNombre del cargo a consultar
     * @return List<PositionVO> Listado de los cargos que contienen el nombre especificado; vacio en otro caso
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de posiciones dado el nombre <br> Códigos:
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
    @WebMethod(operationName = "getPositionsByName",action="getPositionsByName")
    public List<PositionVO> getPositionsByName(@WebParam(name = "positionName")
            final String positionName) throws BusinessException {
        return ejbRef.getPositionsByName(positionName);
    }

    /**
     * Método: Permite actualizar un cargo existente en el sistema
     * @param obj - PositionVOObjeto con la informacion basica del cargo a actualizar. Debe contener:<br>
     * - id<br>
     * - id de dealer<br>
     * - positionNamer<br>
     * - positionCode<br>
     * - description<br>
     * - id country<br>
     * @throws BusinessException en caso de error al ejecutar la operación  de actualización de una posición<br> Códigos:
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
    @WebMethod(operationName = "updatePositions", action = "updatePositions")
    public void updatePositions(@WebParam(name = "obj") PositionVO obj) throws BusinessException {
        ejbRef.updatePositions(obj);
    }

    /**
     * Método: Permite eliminar un cargo existente en el sistema
     * @param obj - PositionVO Objeto con la informacion basica del cargo a eliminar. Debe contener:<br>
     * - id<br>
     * @throws BusinessException en caso de error al ejecutar la operación de eliminación de una posición<br> Códigos:
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
    @WebMethod(operationName = "deletePositions", action = "deletePositions")
    public void deletePositions(@WebParam(name = "obj") PositionVO obj) throws BusinessException {
        ejbRef.deletePositions(obj);
    }

    /**
     * Método: permite consultar todos los cargos existentes en el sistema
     * @return List<PositionVO> Listado con los cargos existentes en el sistema. Vacio en caso que no existan
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de todas las posiciones registradas
     * en el sistema<br> Códigos:
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
    @WebMethod(operationName = "getAllPositions", action = "getAllPositions")
    public List<PositionVO> getAllPositions() throws BusinessException {
    	return ejbRef.getAllPositions();
    }
    
    /**
     * Método: Obtiene un listado con los cargos pertenecientes a un pais especifico
     * @param countryId - Long Id del pais
     * @return List<PositionVO> Listado con los cargos de un pais especifico; vacio en caso contrario
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de las posiciones asociadas a un país<br> Códigos:
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
    @WebMethod(operationName = "getAllPositionsByCountryId", action = "getAllPositionsByCountryId")
    public List<PositionVO> getAllPositionsByCountryId(@WebParam(name = "countryId")Long countryId) throws BusinessException {
    	return ejbRef.getAllPositionsByCountryId(countryId);
    }

    /**
     * Método: Obtiene todos los cargos pertenecientes a un dealer
     * @param dealerId - Long Id de l dealer
     * @return List<PositionVO> Listado con los cargos pertenecientes a un dealer especifico; vacio en otro caso
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de ´posiciones asociadas al delaer <br> Códigos:
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
    @WebMethod(operationName = "getPositionsByDealerId",action="getPositionsByDealerId")
    public List<PositionVO> getPositionsByDealerId(@WebParam(name = "dealerId")
            final Long dealerId) throws BusinessException {
        return ejbRef.getPositionsByDealerId(dealerId);
    }

    /**
     * Método: Obtiene un cargo con el nombre y id del dealer especificado
     * @param positionName - String Nombre del cargo
     * @param id - Long Id del dealer 
     * @return PositionVO Objeto con la informacion basica del cargo cuyo nombre e identificador de dealer son los especificados; 
     * nulo en caso contrario
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de las posiciones por posición y dealer<br> Códigos:
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
    @WebMethod(operationName = "getPositionsByNameAndDealerId",action="getPositionsByNameAndDealerId")
    public PositionVO getPositionsByNameAndDealerId(@WebParam(name = "positionName")
            final String positionName, @WebParam(name = "id")
            final Long id) throws BusinessException {
        return ejbRef.getPositionsByNameAndDealerId(positionName, id);
    }

    /**
     * Método: Obtiene un cargo con el codigo y id del dealer especificado
     * @param code - String Codigo del cargo
     * @param id - Long Id del dealer
     * @return PositionVO Objeto con la informacion basica del cargo cuyo código e identificador del dealer
     * son los especificados; nulo en caso contrario
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de una posición por su código y dealer<br> Códigos:
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
    @WebMethod(operationName = "getPositionsByPositionCodeAndDealerId",action="getPositionsByPositionCodeAndDealerId" )
    public PositionVO getPositionsByPositionCodeAndDealerId(@WebParam(name = "code")
            final String code, @WebParam(name = "id") Long id) throws BusinessException {
        return ejbRef.getPositionsByPositionCodeAndDealerId(code, id);
    }
    
    /**
     * Método: Obtiene un listado de cargos con el codigo, nombre y dealer especificado
     * @param code - String Codigo del cargo
     * @param name - String Nombre del cargo
     * @param dealerId - Long Id del dealer
     * @return List<PositionVO> Listado de cargos cuyos códigos, nombres e identificador del dealer son los especificados;
     * vacio en caso contrario
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de posición por su código y dealer<br> Códigos:
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
    @WebMethod(operationName = "getPositionsByCodeAndNameAndDealerId",action="getPositionsByCodeAndNameAndDealerId")
    public List<PositionVO> getPositionsByCodeAndNameAndDealerId(@WebParam(name = "code")final String code,@WebParam(name = "name")final String  name, @WebParam(name = "dealerId")final Long dealerId) throws BusinessException {
        return ejbRef.getPositionsByCodeAndNameAndDealerId(code, name, dealerId);
    }
    
}
