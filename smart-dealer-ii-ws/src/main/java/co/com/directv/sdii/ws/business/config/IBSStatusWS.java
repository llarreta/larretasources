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
import co.com.directv.sdii.facade.config.Ibs6StatusFacadeBeanLocal;
import co.com.directv.sdii.model.vo.Ibs6StatusVO;

/**
 * Servicio que expone todos los métodos referentes a la administración de los estados de IBS
 * 
 * Fecha de Creación: 25/03/2010
 * @author jcasas <a href="mailto:jcasas@intergrupo.com">e-mail</a>
 * @version 1.0    
 * 
 * @see co.com.directv.sdii.facade.config.Ibs6StatusFacadeBeanLocal
 */

@MTOM
@WebService()
@Stateless()
public class IBSStatusWS {

    @EJB
    private Ibs6StatusFacadeBeanLocal ibs6Bean;

    /**
     * Crea un estado IBS en el sistema
     * @param obj objeto que encapsula la información de un estado de IBS
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
    @WebMethod(operationName = "createIbs6Status", action = "createIbs6Status")
    public void createIbs6Status(@WebParam(name = "obj") Ibs6StatusVO obj) throws BusinessException {
        ibs6Bean.createIbs6Status(obj);
    }

    /**
     * Obtiene un estado IBS por id
     * @param id Id del estado IBS
     * @return Objeto con la informacion basica del estado de IBS, nulo en caso que no se encuentre
     * el estado de ibs con el id especificado
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
    @WebMethod(operationName = "getIbs6StatusByID", action = "getIbs6StatusByID")
    public Ibs6StatusVO getIbs6StatusByID(@WebParam(name = "id") Long id) throws BusinessException {
        return ibs6Bean.getIbs6StatusByID(id);
    }

    /**
     * Actualiza un estado IBS existente
     * @param obj Objeto con la informacion basica del estado de IBS
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
    @WebMethod(operationName = "updateIbs6Status", action = "updateIbs6Status")
    public void updateIbs6Status(@WebParam(name = "obj") Ibs6StatusVO obj) throws BusinessException {
        ibs6Bean.updateIbs6Status(obj);
    }

    /**
     * Crea un estado IBS en el sistema
     * @param obj Objeto con la informacion basica del estado de IBS
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
    @WebMethod(operationName = "deleteIbs6Status", action = "deleteIbs6Status")
    public void deleteIbs6Status(@WebParam(name = "obj") Ibs6StatusVO obj) throws BusinessException {
        ibs6Bean.deleteIbs6Status(obj);
    }

    /**
     * Obtiene todos los estados IBS del sistema
     * @return Listado con los estados IBS del sistema, una lista vacia en caso que no
     * existan estados de ibs en el sistema
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
    @WebMethod(operationName = "getAllIbs6Status", action = "getAllIbs6Status")
    public List<Ibs6StatusVO> getAllIbs6Status() throws BusinessException {
        return ibs6Bean.getAll();
    }
}
