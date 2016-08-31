package co.com.directv.sdii.ws.business.dealers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.ContactTypeFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ContactTypeVO;

/**
 * <Descripcion> 
 * Servicio que provee las operaciones relacionadas con tipos de contacto (ContactType).
 * Fecha de Creación: 14/05/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * @see co.com.directv.sdii.facade.dealers
 */
@MTOM
@WebService()
@Stateless()
public class ContactTypeWS {

	/**
	 * referencia a la fachada de tipos de contacto que proveerá el acceso a las operaciones
	 */
    @EJB
    private ContactTypeFacadeBeanLocal ejbRef;

    /**
     * 
     * Metodo: Obtiene un tipo de contacto dado el código que debe ser único
     * @param code - String Cadena de caracteres que contiene el código único del tipo de contacto
     * @return ContactTypeVO Tipo de contacto cuyo código coincide con el especificado, nulo en caso que no se
     * encuentre el tipo de contacto con dicho código
     * @throws BusinessException en caso de error al tratar de obtener la información
     * del tipo de contacto por el código <br> Códigos: <br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getContactTypeByCode", action = "getContactTypeByCode")
    public ContactTypeVO getContactTypeByCode(@WebParam(name = "code") String code) throws BusinessException {
        return ejbRef.getContactTypeByCode(code);
    }

    /**
     * 
     * Metodo: Método que obtiene el tipo de contacto por identificador
     * @param id - Long identificador del tipo de contacto que se desea consultar
     * @return ContactTypeVO Tipo de contacto cuyo identificador coincide con el id especificado, nulo en caso que no
     * se encuentre el tipo de contacto
     * @throws BusinessException En caso de error al tratar de consultar el tipo de contacto <br> Códigos: <br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getContactTypeByID", action = "getContactTypeByID")
    public ContactTypeVO getContactTypeByID(@WebParam(name = "id") Long id) throws BusinessException {
        return ejbRef.getContactTypeByID(id);
    }

    /**
     * 
     * Metodo: Obtiene la lista con todos los tipos de contacto registrados en el sistema
     * @return List<ContactTypeVO> Lista con los tipos de contacto, en caso que no exista ninguno se retorna una
     * lista vacia
     * @throws BusinessException En caso de error al tratar de consultar los tipos de contacto <br> Códigos: <br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
    @WebMethod(operationName = "getAllContactType", action = "getAllContactType")
    public List<ContactTypeVO> getAllContactType() throws BusinessException {
        return ejbRef.getAllContactType();
    }
}
