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
import co.com.directv.sdii.facade.dealers.ArpFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.EpsFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.PensionFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ArpVO;
import co.com.directv.sdii.model.vo.EpsVO;
import co.com.directv.sdii.model.vo.PensionVO;

/**
 * 
 * <Descripcion> 
 * Servicio que provee las operaciones relacionadas con seguridad social en el sistema
 * tales como ARP, EPS y Pensiones
 * Fecha de Creación: 14/05/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.dealers.ArpFacadeBeanLocal
 * @see co.com.directv.sdii.facade.dealers.EpsFacadeBeanLocal
 * @see co.com.directv.sdii.facade.dealers.PensionFacadeBeanLocal
 */
@MTOM
@WebService()
@Stateless()
public class SocialSecurityWS {

	/**
	 * Fachada que provee acceso a las operaciones de ARP
	 */
    @EJB
    private ArpFacadeBeanLocal arpFacade;

    /**
	 * Fachada que provee acceso a las operaciones de EPS
	 */
    @EJB
    private EpsFacadeBeanLocal epsFacade;

    /**
	 * Fachada que provee acceso a las operaciones de Pensiones
	 */
    @EJB
    private PensionFacadeBeanLocal pensionFacade;

    /**
     * 
     * Metodo: Obtiene una arp que concuerda con el código especificado
     * @param code - String código de arp
     * @return ArpVO que coincide con el código, nulo en caso que no exista
     * @throws BusinessException En caso de error al tratar de obtener la arp por código o en caso
     * que exista mas de una ARP con el mismo código<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "getArpByCode", action="getArpByCode")
    public ArpVO getArpByCode(@WebParam(name = "code") String code) throws BusinessException {
        return arpFacade.getArpByCode(code);
    }

    /**
     * 
     * Metodo: Obtiene un objeto de ARP que concuerda con el identificador especifado
     * @param id - Longidentificador de la arp
     * @return ArpVO que concuerda con el identificador especificado, null en caso que no exista
     * @throws BusinessException en caso de error al tratar de obtener la arp<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "getArpByID", action="getArpByID")
    public ArpVO getArpByID(@WebParam(name = "id") Long id) throws BusinessException {
        return arpFacade.getArpByID(id);
    }

    /**
     * 
     * Metodo: Obtiene todos los registros de arp que existen en el sistema
     * @return List<ArpVO> Lista con las arp registradas en el sistema, lista vacia en caso que no exista
     * ninguna arp
     * @throws BusinessException En caso de error al tratar de obtener las arp<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "getAllArp", action="getAllArp")
    public List<ArpVO> getAllArp() throws BusinessException {
    	return arpFacade.getAllArp();
    }
    
    /**
     * Se obtiene la lista de objetos dado el identificador del país
     * @param countryId - Long identificador del país
     * @return List<ArpVO> Lista de objetos filtrados por el identificador del país
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "getAllArpByCountryId", action="getAllArpByCountryId")
    public List<ArpVO> getAllArpByCountryId(@WebParam(name = "countryId")Long countryId) throws BusinessException {
        return arpFacade.getAllArpByCountryId(countryId);
    }

    /**
     * 
     * Metodo: Obtiene una EPS que concuerda con el código especificado
     * @param code - String código de eps a ser consultado
     * @return EpsVO Objeto EPS cuyo código coincide con el especificado por el usuario
     * @throws BusinessException En caso de error al tratar de obtener la información de la EPS<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "getEpsByCode", action = "getEpsByCode")
    public EpsVO getEpsByCode(@WebParam(name = "code") String code) throws BusinessException {
        return epsFacade.getEpsByCode(code);
    }

    /**
     * 
     * Metodo: Obtiene la información de una eps dado su identificador
     * @param id - Long identificador de la eps
     * @return EpsVO Eps cuyo id coincide con el especificado, nulo en caso que no se
     * encuentre una eps con el mismo id
     * @throws BusinessException en caso de error al tratar de obtener la eps<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "getEpsByID", action = "getEpsByID")
    public EpsVO getEpsByID(@WebParam(name = "id") Long id) throws BusinessException {
        return epsFacade.getEpsByID(id);
    }

    /**
     * 
     * Metodo: Obtiene la información de todas las EPS persistidas en el sistema
     * @return List<EpsVO> Lista con las eps que existen en el sistema
     * @throws BusinessException En caso de error al tratar de obtener la lista de las EPS<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "getAllEps", action = "getAllEps")
    public List<EpsVO> getAllEps() throws BusinessException {
    	return epsFacade.getAllEps();
    }
    
    /**
     * Se obtiene la lista de objetos dado el identificador del país
     * @param countryId - Long identificador del país
     * @return List<EpsVO> Lista de objetos filtrados por el identificador del país
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jjimenezh
     */
    @WebMethod(operationName = "getAllEpsByCountryId", action = "getAllEpsByCountryId")
    public List<EpsVO> getAllEpsByCountryId(@WebParam(name = "countryId")Long countryId) throws BusinessException {
    	return epsFacade.getAllEpsByCountryId(countryId);
    }

/**
     *
     * Metodo: Obtiene la información de todos los fondos de pensiones que existen en el sistema
     * @return List<PensionVO> lista con todos los fondos de pension que existen en el sistema
     * @throws BusinessException En caso de error al tratar de obtener la lista de las
     * eps registradas en el sistema<br> Códigos:
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
    @WebMethod(operationName = "getAllPension", action = "getAllPension")
    public List<PensionVO> getAllPension() throws BusinessException {
    	return pensionFacade.getAllPension();
    }
    
    /**
     * Se obtiene la lista de objetos dado el identificador del país
     * @param countryId - Long identificador del país
     * @return List<PensionVO> Lista de objetos filtrados por el identificador del país
     * @throws BusinessException en caso de error al tratar de obtener la lista de objetos<br> Códigos:
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
    @WebMethod(operationName = "getAllPensionByCountryId", action = "getAllPensionByCountryId")
    public List<PensionVO> getAllPensionByCountryId(@WebParam(name = "countryId")Long countryId) throws BusinessException {
    	return pensionFacade.getAllPensionByCountryId(countryId);
    }

    /**
     *
     * Metodo: Obtiene la información de un fondo de pensiones cuyo código coincide con
     * el especificado
     * @param code  - String código por el que se realizará el filtro
     * @return PensionVO Información del fondo de pensiones que concuerda con el código especificado
     * @throws BusinessException En caso de error al tratar de obtener la información del fondo de pensiones<br> Códigos:
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
    @WebMethod(operationName = "getPensionByCode", action = "getPensionByCode")
    public PensionVO getPensionByCode(@WebParam(name = "code")String code) throws BusinessException {
            return pensionFacade.getPensionByCode(code);
    }

    /**
     *
     * Metodo: Obtiene la información de un fondo de pensones dado el dientificador
     * @param id  - Longidentificador del fondo de pensiones por el cual se realizará el filtro
     * @return PensionVO fondo de pensiones cuyo identificador concuerda con el especificado
     * @throws BusinessException En caso de error al tratar de obtener la información del fondo de pensiones<br> Códigos:
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
    @WebMethod(operationName = "getPensionByID", action = "getPensionByID")
    public PensionVO getPensionByID(@WebParam(name = "id")Long id) throws BusinessException {
            return pensionFacade.getPensionByID(id);
    }

}
