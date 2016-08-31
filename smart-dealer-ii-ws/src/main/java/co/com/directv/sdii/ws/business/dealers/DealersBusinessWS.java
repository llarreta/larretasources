package co.com.directv.sdii.ws.business.dealers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessDetailException;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.DealersFacadeBusinessBeanLocal;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * 
 * Interfaz que define las operaciones de los web services de
 * DealersBusiness.
 * 
 * Fecha de Creación: 14/05/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.dealers.DealersFacadeBusinessBeanLocal
 */
@MTOM
@WebService()
@Stateless()
public class DealersBusinessWS {
	
	/**
	 * Referencia a la fachada que provee el acceso a las operaciones que
	 * el servicio expondrá
	 */
    @EJB
    private DealersFacadeBusinessBeanLocal ejbRef;
    
    /**
     * Metodo: Obtiene los códigos de los dealers persistidos y administrados
     * por IBS
     * @return List<DealerVO> Lista con objetos DealerVO que contienen la información de los
     * códigos de dealers persistidos en IBS
     * @throws BusinessException En caso de error al tratar de obtener la lista de dealers de
     * IBS <br> Códigos: <br>
     * <code>sdii_CODE_entity_not_found</code> En caso que la entidad no se pueda identificar<br>
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
    @WebMethod(operationName = "getDealerCodes",action="getDealerCodes")
    public List<DealerVO> getDealerCodes(@WebParam(name = "countryId") Long countryId) throws BusinessException {
        return ejbRef.getDealerCodes(countryId);
    }

    /**
     * Metodo: Obtiene la información de un dealer, desde IBS, consultando dado el código
     * de dealer y el código depot.
     * @param dealerCode - String código de dealer que coincide con el código almacenado
     * en IBS
     * @param depotCode - Long código depot que coincide con el código almacenado en IBS
     * @return DealerVO Objeto del dominio de SDII con la información del dealer obtenida desde IBS
     * @throws BusinessException En caso de error al tratar de obtener la información desde IBS <br> Códigos: <br>
     * <code>sdii_CODE_entity_not_found</code> En caso que la entidad no se pueda identificar<br>
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
    @WebMethod(operationName = "getDealer",action="getDealer")
    public DealerVO getDealer(@WebParam(name = "dealerCode")Long dealerCode, @WebParam(name = "pdepotCodeVo")String depotCode, @WebParam(name = "countryId") Long countryId) throws BusinessException,BusinessDetailException {
        return ejbRef.getDealer(dealerCode, depotCode, countryId);
    }

}
