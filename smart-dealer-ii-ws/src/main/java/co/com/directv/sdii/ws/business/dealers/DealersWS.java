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
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.facade.dealers.DealerStatusFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.DealerTypesFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.DealersMediaContactFacadeBeanLocal;
import co.com.directv.sdii.model.dto.DealersCodesDTO;
import co.com.directv.sdii.model.dto.InfoDealerCountryWarehouseDTO;
import co.com.directv.sdii.model.vo.DealerMediaContactVO;
import co.com.directv.sdii.model.vo.DealerStatusVO;
import co.com.directv.sdii.model.vo.DealerTypeVO;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 *
 * Dealers WebService
 * WebService encargado de gestionar todas las operaciones de dealers
 * @author jcasas <a href="mailto:jcasas@intergrupo.com">e-mail</a>
 * @version 1.0
 * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal
 * @see co.com.directv.sdii.facade.dealers.DealersMediaContactFacadeBeanLocal
 * @see co.com.directv.sdii.facade.dealers.DealerStatusFacadeBeanLocal
 * @see co.com.directv.sdii.facade.dealers.DealerTypesFacadeBeanLocal
 */
@MTOM
@WebService()
@Stateless()
public class DealersWS {

    @EJB
    private DealersFacadeBeanLocal dealerFacadeBean;
    @EJB
    private DealersMediaContactFacadeBeanLocal dealerMediaFacadeBean;
    @EJB
    private DealerStatusFacadeBeanLocal dealerStatusBean;
    @EJB
    private DealerTypesFacadeBeanLocal dealerTypeBean;

    
    /**
     * 
     * Metodo: Permite crear en el sistema un Dealer que aún no ha sido registrado.
     * @param pVo - DealerVO Dealer que va a ser creado en el sistema <br>
     * Debe contener: <br>
     * - id del dealerType <br>
     * - id del channelType <br>
     * - id del postalCode <br>
     * - id del dealerStatus <br>
     * - dealerName <br>
     * - legalRepresentative <br>
     * - nit <br>
     * - address <br>
     * - depotCode <br>
     * - dealerCode <br>
     * @throws BusinessException en caso de error al tratar de crear el Dealer <br> Códigos: <br>
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
    @WebMethod(operationName = "createDealer", action = "createDealer")
    public void createDealer(@WebParam(name = "pVo") DealerVO pVo) throws BusinessException {
        dealerFacadeBean.createDealer(pVo);
    }
    
   
    /**
     * 
     * Metodo: Permite crear en el sistema un conjunto de  Dealers provenientes desde IBS que aún no ha sido registrados.
     * @param dealersDTO - List<DealersCodesDTO>  Lista de DealersCodesDTO; cada uno debe contener  <br>
     * - dealerCode <br>
     * - depotCode <br>
     * @throws BusinessException en caso de error al tratar de crear los Dealers a partir de IBS <br> Códigos: <br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_object_already_exist</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * 
     * @author jcasas
     */
    @WebMethod(operationName = "createDealersFromIBS", action = "createDealersFromIBS")
    public void createDealersFromIBS(@WebParam(name = "dealersDTO")List<DealersCodesDTO> dealersDTO)throws BusinessException, BusinessDetailException{
        dealerFacadeBean.createDealersFromIBS(dealersDTO);
    }
    
    
    /**
     * 
     * Metodo: Permite consultar un Dealer dado su identificador.
     * @param id - Long  Identificador del Dealer que se va a consultar
     * @return DealerVO Dealer cuyo identificador coincide con el especificado; nulo en caso contrario
     * @throws BusinessException en caso de error al tratar de obtener la información
     * del dealer por su identificador <br> Códigos: <br>
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
    @WebMethod(operationName = "getDealerByID", action = "getDealerByID")
    public DealerVO getDealerByID(@WebParam(name = "id") Long id) throws BusinessException {
        return dealerFacadeBean.getDealerByID(id);
    }

   
    /**
     * 
     * Metodo: Permite consultar un conjunto de Dealer dado su nombre.
     * @param name - String  Nombre de los Dealer que se van a consultar
     * @return List<DealerVO> Lista de Dealer cuyo nombre coincide con el especificado; vacio en caso contrario
     * @throws BusinessException en caso de error al tratar de obtener la información
     * de los dealer por su nombre <br> Códigos: <br>
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
    @WebMethod(operationName = "getDealerByName", action = "getDealerByName")
    public List<DealerVO> getDealerByName(@WebParam(name = "name") String name) throws BusinessException {
        return dealerFacadeBean.getDealerByName(name);
    }
    /**
     * 
     * Metodo: Permite actualizar un Dealer registrado en el sistema.
     * @param pVo - DealerVO Dealer que va a ser actualizado en el sistema <br>
     * Debe contener: <br>
     * - id del dealer <br>
     * - id del dealerType <br>
     * - id del channelType <br>
     * - id del postalCode <br>
     * - id del dealerStatus <br>
     * - dealerName <br>
     * - legalRepresentative <br>
     * - nit <br>
     * - address <br>
     * - depotCode <br>
     * - dealerCode <br>
     * @return List<DealerVO> Lista de Dealer cuyo nombre coincide con el especificado; vacio en caso contrario
     * @throws BusinessException en caso de error al tratar de obtener la información
     * de los dealer por su nombre <br> Códigos: <br>
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
    @WebMethod(operationName = "updateDealer", action = "updateDealer")
    public void updateDealer(@WebParam(name = "pVo") DealerVO pVo) throws BusinessException {
        dealerFacadeBean.updateDealer(pVo);
    }
    /**
     * 
     * Metodo: Permite eliminar un Dealer registrado en el sistema.
     * @param pVo - DealerVO Dealer que va a ser eliminado del sistema <br>
     * Debe contener: <br>
     * - id del dealer
     * @return List<DealerVO> Lista de Dealer cuyo nombre coincide con el especificado; vacio en caso contrario
     * @throws BusinessException en caso de error al tratar de eliminar el dealer <br> Códigos: <br>
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
    @WebMethod(operationName = "deleteDealer", action = "deleteDealer")
    public void deleteDealer(@WebParam(name = "pVo") DealerVO pVo) throws BusinessException {
        dealerFacadeBean.deleteDealer(pVo);
    }
    /**
     * 
     * Metodo: Permite consultar todo el conjunto de Dealer registrado en el sistema.
     * @return List<DealerVO> Lista de Dealer registrados en el sistema; vacio en caso contrario
     * @throws BusinessException en caso de error al tratar de obtener los dealer <br> Códigos: <br>
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
    @WebMethod(operationName = "getAllDealers", action = "getAllDealers")
    public List<DealerVO> getAllDealers() throws BusinessException {
        return dealerFacadeBean.getAll();
    }
    /**
     * 
     * Metodo: Permite consultar todo el conjunto de Dealer registrado en el sistema que se encuentran 
     * asociados a un país.
     * @param countryId - Long Identificador del país
     * @return List<DealerVO> Lista de Dealer que se encuentran asociados con el pís especificado; vacio en caso contrario
     * @throws BusinessException en caso de error al tratar de obtener la información
     * de los dealer asociados al país especificado <br> Códigos: <br>
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
    @WebMethod(operationName = "getAllDealersByCountryId", action = "getAllDealersByCountryId")
    public List<DealerVO> getAllDealersByCountryId(@WebParam(name = "countryId")Long countryId,
    		@WebParam(name="isSeller")String isSeller,@WebParam(name="isInstaller")String isInstaller)throws BusinessException {
    	return dealerFacadeBean.getAllDealersByCountryId(countryId, isSeller, isInstaller);
    }
    
    /**
     * 
     * Metodo: Permite consultar todo el conjunto de Dealer registrado en el sistema que se encuentran 
     * asociados a un país.
     * @param countryId - Long Identificador del país
     * @return List<DealerVO> Lista de Dealer que se encuentran asociados con el pís especificado; vacio en caso contrario
     * @throws BusinessException en caso de error al tratar de obtener la información
     * de los dealer asociados al país especificado <br> Códigos: <br>
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
    @WebMethod(operationName = "getDealersByDealerTypeCodeAndCountryId", action = "getDealersByDealerTypeCodeAndCountryId")
    public List<DealerVO> getDealersByDealerTypeCodeAndCountryId(@WebParam(name = "dealerTypeCode")String dealerTypeCode, @WebParam(name = "countryId")Long countryId)throws BusinessException {
    	return dealerFacadeBean.getDealersByDealerTypeCodeAndCountryId(dealerTypeCode, countryId);
    }
    
    /**
     * 
     * Metodo: Permite consultar todo el conjunto de Dealer registrado en el sistema. Por cada uno retorna 
     * la información básica
     * @return List<DealerVO> Lista de Dealer que se encuentran registrados en el sistema; vacio en caso contrario. Cada registro contiene:<br>
     * - id <br>
     * - dealerName <br>
     * - legalRepresentative <br>
     * - nit <br>
     * - address <br>
     * @throws BusinessException en caso de error al tratar de obtener la información
     * de los dealer con información básica <br> Códigos: <br>
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
    @WebMethod(operationName = "getAllDealersOnlyBasicInfo", action = "getAllDealersOnlyBasicInfo")
    public List<DealerVO> getAllDealersOnlyBasicInfo() throws BusinessException {
        return dealerFacadeBean.getAllDealersOnlyBasicInfo();
    }
    
    /**
     * Metodo: Permite consultar el conjunto de sucursales de un dealer específico 
     * 
     * @return List<DealerVO> Lista de Dealer que se encuentran registrados en el sistema; vacio en caso contrario. Cada registro contiene:
     * @throws BusinessException en caso de error al tratar de obtener la información
     * de los dealer con información básica <br> Códigos: <br>
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
    @WebMethod(operationName = "getDealerBranchesByDealerId", action = "getDealerBranchesByDealerId")
    public List<DealerVO> getDealerBranchesByDealerId(@WebParam(name = "dealerId") Long dealerId) throws BusinessException {
        return dealerFacadeBean.getDealerBranchesByDealerId(dealerId);
    }
  
    /**
     * Metodo: Permite consultar el conjunto de sucursales instaladoras/vendedoras de un dealer específico 
     * 
     * @return List<DealerVO> Lista de Dealer que se encuentran registrados en el sistema; vacio en caso contrario. Cada registro contiene:
     * @throws BusinessException en caso de error al tratar de obtener la información
     * de los dealer con información básica <br> Códigos: <br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author carlopez
     */
    @WebMethod(operationName = "getDealerBranchesByDealerIdAndFilter", action = "getDealerBranchesByDealerIdAndFilter")
    public List<DealerVO> getDealerBranchesByDealerIdAndFilter(@WebParam(name = "dealerId") Long dealerId, @WebParam(name="isSeller") String isSeller, @WebParam(name="isInstaller") String isInstaller) throws BusinessException {
        return dealerFacadeBean.getDealerBranchesByDealerIdAndFilter(dealerId, isSeller, isInstaller);
    }
  
    
    /**
     * 
     * Metodo: Permite consultar un dealer que se encuentra asociado a un Depot
     * @param id - String Identificador del Depot
     * @return DealerVO Dealer cuyo DepotId coincide con el especificado; nulo en caso contrario
     * @throws BusinessException en caso de error al tratar de obtener la información
     * de dealer asociado al Depot especificado <br> Códigos: <br>
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
    @WebMethod(operationName = "getDealerByDepotID", action = "getDealerByDepotID")
    public DealerVO getDealerByDepotID(@WebParam(name = "id") String id) throws BusinessException {
        return dealerFacadeBean.getDealerByDepotID(id);
    }
    /**
     * 
     * Metodo: Permite consultar un dealer que se encuentra asociado a un Depot
     * @param depotCode - String Código del Depot
     * @param dealerCode - Long Código del Dealer
     * @return DealerVO Dealer cuyo DepotId coincide con el especificado; nulo en caso contrario
     * @throws BusinessException en caso de error al tratar de obtener la información
     * de dealer asociado al Depot especificado <br> Códigos: <br>
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
    @WebMethod(operationName = "getDealerByDepotCodeOrDealerCode", action = "getDealerByDepotCodeOrDealerCode")
    public DealerVO getDealerByDepotCodeOrDealerCode(@WebParam(name = "depotCode") String depotCode , @WebParam(name = "dealerCode") Long dealerCode) throws BusinessException {
        return dealerFacadeBean.getDealerByDepotCodeOrDealerCode(depotCode, dealerCode);
    }
    /**
     * 
     * Metodo: Permite crear un Medio de Contacto con Dealer (DealerMediaContact) que aun no se encuentra registrado
     * @param obj - DealerMediaContactVO Medio de contacto que se va a crear. Debe contener: <br>
     * - id mediaContactType <br>
     * - id dealer <br>
     * - mediaContactValue <br>
     * - id postalCode <br>
     * @throws BusinessException en caso de error al tratar de crear un medio de contacto en el dealer <br> Códigos: <br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_object_already_exist</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jcasas
     */
    @WebMethod(operationName = "createDealerMediaContact", action="createDealerMediaContact")
    public void createDealerMediaContact(@WebParam(name = "obj") DealerMediaContactVO obj) throws BusinessException {
        dealerMediaFacadeBean.createDealerMediaContact(obj);
    }
    /**
     * 
     * Metodo: Permite consultar un medio de contacto dado su identificador
     * @param id - Long Identificador del medio de contacto del dealer 
     * @return DealerMediaContactVO Medio de contacto cuyo identificador coincide con el especificado
     * @throws BusinessException en caso de error al tratar de consultar la información de un medio de contacto 
     * dado su identificador<br> Códigos: <br>
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
    @WebMethod(operationName = "getDealersMediaContactByID", action="getDealersMediaContactByID")
    public DealerMediaContactVO getDealersMediaContactByID(@WebParam(name = "id") Long id) throws BusinessException {
        return dealerMediaFacadeBean.getDealersMediaContactByID(id);
    }
    /**
     * 
     * Metodo: Permite actualizar un medio de contacto registrado en el sistema
     * @param obj - DealerMediaContactVO Medio de contacto que se desea actualizar. Debe contener: <br>
     * - id<br>
     * - id mediaContactType <br>
     * - id dealer <br>
     * - mediaContactValue <br>
     * - id postalCode <br>
     * @throws BusinessException en caso de error al tratar de actualizar la información de un medio de contacto 
     * <br> Códigos: <br>
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
    @WebMethod(operationName = "updateDealersMediaContact", action="updateDealersMediaContact")
    public void updateDealersMediaContact(@WebParam(name = "obj") DealerMediaContactVO obj) throws BusinessException {
        dealerMediaFacadeBean.updateDealersMediaContact(obj);
    }
    /**
     * 
     * Metodo: Permite eliminar un medio de contacto registrado en el sistema
     * @param obj - DealerMediaContactVO Medio de contacto que se desea eliminar. Debe contener: <br>
     * - id<br>
     * @throws BusinessException en caso de error al tratar de eliminar la información de un medio de contacto 
     * <br> Códigos: <br>
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
    @WebMethod(operationName = "deleteDealersMediaContact", action="deleteDealersMediaContact")
    public void deleteDealersMediaContact(@WebParam(name = "obj") DealerMediaContactVO obj) throws BusinessException {
        dealerMediaFacadeBean.deleteDealersMediaContact(obj);
    }
    /**
     * 
     * Metodo: Permite consultar el conjunto de medios de contacto registrado en el sistema
     * @return List<DealerMediaContactVO> Lista de todos los medios de contacto de dealer registrados
     * @throws BusinessException en caso de error al tratar de consutal la información de todos los medios de contacto 
     * <br> Códigos: <br>
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
    @WebMethod(operationName = "getAllDealersMediaContact" , action="getAllDealersMediaContact")
    public List<DealerMediaContactVO> getAllDealersMediaContact() throws BusinessException {
        return dealerMediaFacadeBean.getAllDealersMediaContact();
    }
    /**
     * 
     * Metodo: Permite consultar un estado de dealer dado su código
     * @param code - String Código de Estado de Dealer a consultar
     * @return DealerStatusVO Estado de dealer cuyo código corresponde al especificado. 
     * @throws BusinessException en caso de error al tratar de consultar la información de un estado de contacto
     * dado su código
     * <br> Códigos: <br>
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
    @WebMethod(operationName = "getDealerStatusByCode", action="getDealerStatusByCode")
    public DealerStatusVO getDealerStatusByCode(@WebParam(name = "code") String code) throws BusinessException {
        return dealerStatusBean.getDealerStatusByCode(code);
    }
    /**
     * 
     * Metodo: Permite consultar un estado de dealer dado su identificado
     * @param id - Long Identificador de Estado de Dealer a consultar
     * @return DealerStatusVO Estado de dealer cuyo identificador corresponde al especificado. 
     * @throws BusinessException en caso de error al tratar de consultar la información de un estado de contacto
     * dado su identificador
     * <br> Códigos: <br>
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
    @WebMethod(operationName = "getDealerStatusByID", action = "getDealerStatusByID")
    public DealerStatusVO getDealerStatusByID(@WebParam(name = "id") Long id) throws BusinessException {
        return dealerStatusBean.getDealerStatusByID(id);
    }
    
    /**
     * 
     * Metodo: Permite consultar los estados de dealer registrados en el sistema
     * @return List<DealerStatusVO> Lista de estados de dealer registrados en el sistema. 
     * @throws BusinessException en caso de error al tratar de consultar la información de todos los estados de contacto 
     * registrados en el sistema
     * <br> Códigos: <br>
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
    @WebMethod(operationName = "getAllDealerStatus", action = "getAllDealerStatus")
    public List<DealerStatusVO> getAllDealerStatus() throws BusinessException {
        return dealerStatusBean.getAllDealerStatus();
    }
    /**
     * 
     * Metodo: Permite consultar un tipo de dealer dado su código
     * @param code - String Código del tipo de dealer que se va a consultar
     * @return DealerStatusVO Tipo de dealer cuyo código corresponde al especificado. 
     * @throws BusinessException en caso de error al tratar de consultar la información de un tipo de 
     * dealer por su código
     * <br> Códigos: <br>
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
    @WebMethod(operationName = "getDealerTypesByCode", action = "getDealerTypesByCode")
    public DealerTypeVO getDealerTypesByCode(@WebParam(name = "code") String code) throws BusinessException {
        return dealerTypeBean.getDealerTypesByCode(code);
    }
    /**
     * 
     * Metodo: Permite consultar un tipo de dealer dado su identificador
     * @param id - Long Identificador del tipo de dealer que se va a consultar
     * @return DealerStatusVO Tipo de dealer cuyo identificador corresponde al especificado. 
     * @throws BusinessException en caso de error al tratar de consultar la información de un tipo de 
     * dealer por su identificador
     * <br> Códigos: <br>
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
    @WebMethod(operationName = "getDealerTypesByID", action = "getDealerTypesByID")
    public DealerTypeVO getDealerTypesByID(@WebParam(name = "id") Long id) throws BusinessException {
        return dealerTypeBean.getDealerTypesByID(id);
    }
    /**
     * 
     * Metodo: Permite consultar los tipos de dealer registrados en el sistema
     * @return List<DealerTypeVO> Tipos de dealer regostrados en el sistema  
     * @throws BusinessException en caso de error al tratar de consultar la información de los tipos de 
     * dealer registrados en el sistema
     * <br> Códigos: <br>
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
    @WebMethod(operationName = "getAllDealerTypes", action = "getAllDealerTypes")
    public List<DealerTypeVO> getAllDealerTypes() throws BusinessException {
        return dealerTypeBean.getAllDealerTypes();
    }

    /**
     * 
     * Metodo: Permite crear un tipo de dealer en el sistema
     * @param DealerTypeVO Tipo de dealer que se va a crear. Debe contener: <br>
     * - dealerTypeName<br>
     * - dealerTypeCode<br>
     * - isAlloc<br>
     * @throws BusinessException en caso de error al tratar de crear un tipo de 
     * dealer en el sistema
     * <br> Códigos: <br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_object_already_exist</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jcasas
     */
    @WebMethod(operationName = "createDealerType", action = "createDealerType")
    public void createDealerType(@WebParam(name = "dealerTypeVO") DealerTypeVO dealerTypeVO) throws BusinessException {
        dealerTypeBean.createDealerType(dealerTypeVO);
    }
    
    /**
     * 
     * Metodo: Permite actualizar un tipo de dealer existente en el sistema
     * @param DealerTypeVO Tipo de dealer que se va a actualizar. Debe contener: <br>
     * - id<br>
     * - dealerTypeName<br>
     * - dealerTypeCode<br>
     * - isAlloc<br>
     * @throws BusinessException en caso de error al tratar de actualizar un tipo de 
     * dealer en el sistema
     * <br> Códigos: <br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
     */
    @WebMethod(operationName = "updateDealerType", action = "updateDealerType")
    public void updateDealerType(@WebParam(name = "dealerTypeVO") DealerTypeVO dealerTypeVO) throws BusinessException {
        dealerTypeBean.updateDealerType(dealerTypeVO);
    }
    
    /**
     * 
     * Metodo: Permite eliminar un tipo de dealer existente en el sistema
     * @param DealerTypeVO Tipo de dealer que se va a eliminar. Debe contener: <br>
     * - id<br>
     * @throws BusinessException en caso de error al tratar de eliminar un tipo de 
     * dealer en el sistema
     * <br> Códigos: <br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
     */
    @WebMethod(operationName = "deleteDealerType", action = "deleteDealerType")
    public void deleteDealerType(@WebParam(name = "dealerTypeVO") DealerTypeVO dealerTypeVO) throws BusinessException {
        dealerTypeBean.deleteDealerType(dealerTypeVO);
    }

    /**
	 * 
	 * Metodo: Retorna Los Dealers principales por pais
	 * @param countryId Long - id del pais
	 * @param isSeller determina si un dealer es vendedor
	 * @param isInstaller determina si un dealer es instalador
	 * @return List<DealerVO>  - Listado de Dealers's principales
	  * @throws BusinessException en caso de error al tratar de eliminar un tipo de 
     * dealer en el sistema
     * <br> Códigos: <br>
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
    @WebMethod(operationName = "getPrincipalsDealersByAndCountryId", action = "getPrincipalsDealersByAndCountryId")
	public List<DealerVO> getPrincipalsDealersByAndCountryId(Long countryId, String isSeller, String isInstaller) throws BusinessException{
    	return dealerFacadeBean.getPrincipalsDealersByAndCountryId(countryId, isSeller, isInstaller);
	}
    
    /**
     * 
     * Metodo: Permite actualizar los datos de un Dealer a partir de IBS dado su identificador de SDII.
     * @param id - Long  Identificador del Dealer que se va a actualizar
     * @throws BusinessException en caso de error al tratar de obtener la información
     * del dealer por su identificador <br> Códigos: <br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jnova
     */
    @WebMethod(operationName = "updateDealerFromIbs", action = "updateDealerFromIbs")
    public void updateDealerFromIbs(@WebParam(name = "id") Long id) throws BusinessException {
        dealerFacadeBean.updateDealerFromIbs(id);
    }
    
    /**
     * 
     * Metodo: Obtiene la informacion de un dealer por su codigo depot, su codigo de dealer y su pais
    * @param dealerCode Codigo de dealer
    * @param depotCode Codigo depot del dealer 
    * @param countryId Identificador del pais del dealer
    * @return DealerVO Informacion del dealer que se esta consultando
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jnova
     */
    @WebMethod(operationName = "getDealerByDepotCodeAndDealerCodeAndCountryId", action = "getDealerByDepotCodeAndDealerCodeAndCountryId")
    public DealerVO getDealerByDepotCodeAndDealerCodeAndCountryId(@WebParam(name = "dealerCode") Long dealerCode,@WebParam(name = "depotCode") String depotCode,@WebParam(name = "countryId") Long countryId) throws BusinessException {
        return dealerFacadeBean.getDealerByDepotCodeAndDealerCodeAndCountryId(dealerCode,depotCode,countryId);
    }
    
    /**
	 * Metodo: Obtiene todas las compañías instaladoras por identificador de país que están activas y que su tipo de dealer permite asignación
	 * @param countryId identificador del país
 	 * @param isSeller determina si un dealer es vendedor
	 * @param isInstaller determina si un dealer es instalador
	 * @return lista de compañías en el país
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getAllDealersActiveByCountryIdAndIsAlloc", action="getAllDealersActiveByCountryIdAndIsAlloc", exclude = false)
	public List<DealerVO> getAllDealersActiveByCountryIdAndIsAlloc(@WebParam(name="countryId")Long countryId,
			@WebParam(name="isSeller")String isSeller,@WebParam(name="isInstaller")String isInstaller) throws BusinessException {
		return dealerFacadeBean.getAllActiveByCountryIdAndIsAlloc(countryId, isSeller, isInstaller);
	}
	
	
	
	/**
     * 
     * Metodo: Permite consultar los dealer principales que se encuentra registrados en el sistema  
     * asociados a un país.
     * @param countryId - Long Identificador del país
	 * @param isSeller determina si un dealer es vendedor
	 * @param isInstaller determina si un dealer es instalador
     * @return List<DealerVO> Lista de Dealer que se encuentran asociados con el pís especificado; vacio en caso contrario
     * @throws BusinessException en caso de error al tratar de obtener la información
     * de los dealer asociados al país especificado <br> Códigos: <br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author waguilera
     */
    @WebMethod(operationName = "getMajorDealersByCountryId", action = "getMajorDealersByCountryId")
    public List<DealerVO> getMajorDealersByCountryId(@WebParam(name = "countryId")Long countryId,
    		@WebParam(name="isSeller")String isSeller,@WebParam(name="isInstaller")String isInstaller)throws BusinessException {
    	return dealerFacadeBean.getMajorDealersByCountryId(countryId, isSeller, isInstaller);
    }
	
	/**
     * Metodo: Obtiene  las sucursales del dealer dado identificadores de dealers,
	 * Se retornan unicamente los dealers en estado Normal
     * 
     * @return List<DealerVO> Lista de Dealer que se encuentran registrados en el sistema; vacio en caso contrario. Cada registro contiene:
     * @throws BusinessException en caso de error al tratar de obtener la información
     * de los dealer con información básica <br> Códigos: <br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jnova
     */
    @WebMethod(operationName = "getDealerBranchesByDealerIds", action = "getDealerBranchesByDealerIds")
    public List<DealerVO> getDealerBranchesByDealerIds(@WebParam(name = "dealerId") List<Long> dealerIds) throws BusinessException {
        return dealerFacadeBean.getDealerBranchesByDealerId(dealerIds);
    }
	
    /**
	 * Metodo: Obtiene una lista de las compañías principales y dentro de cada una una lista
	 * con sus sucursales con la información básica de id, código, depot, nombre, dirección y representante legal
	 * @param countryId identificador del país
	 * @return lista con las compañías principales y dentro de cada una una lista
	 * con sus sucursales con la información básica de id, código, depot, nombre, dirección y representante legal
	 * @throws BusinessException En caso de error al realizar la operación
	 * @author jjimenezh
	 */
    @WebMethod(operationName = "getActiveMajorDealersAndBranchesByCountryId", action = "getActiveMajorDealersAndBranchesByCountryId")
	public List<DealerVO> getActiveMajorDealersAndBranchesByCountryId(@WebParam(name = "countryId") Long countryId)throws BusinessException{
    	return dealerFacadeBean.getActiveMajorDealersAndBranchesByCountryId(countryId);
    }
    
    /**
	 * Metodo: Obtiene una lista de las compañías principales y dentro de cada una una lista
	 * con sus sucursales con la información básica de id, código, depot, nombre, dirección y representante legal
	 * @param userId identificador del usuario para la seguridad
	 * @param isSeller identifica si un dealer es vendedor
	 * @param isInstaller identifica si un dealer es instalador
	 * @return lista con las compañías principales y dentro de cada una una lista
	 * con sus sucursales con la información básica de id, código, depot, nombre, dirección y representante legal
	 * @throws BusinessException En caso de error al realizar la operación
	 * @author cduarte
	 */
    @WebMethod(operationName = "getActiveMajorDealersAndBranchesByUserId", action = "getActiveMajorDealersAndBranchesByUserId")
	public List<DealerVO> getActiveMajorDealersAndBranchesByUserId(@WebParam(name = "userId") Long userId,
			@WebParam(name="isSeller")String isSeller,@WebParam(name="isInstaller")String isInstaller)throws BusinessException{
    	return dealerFacadeBean.getActiveMajorDealersAndBranchesByUserId(userId, isSeller, isInstaller);
    }
    
    /**
     * 
     * Metodo: Retorna las compañias segun un tipo de bodega.
     * @param warehouseType
     * @return <tipo> <descripcion>
     * @author
     * @throws BusinessException 
     */
    @WebMethod(operationName = "getDealersByWarehouseTypeCode", action = "getDealersByWarehouseTypeCode")
    public List<DealerVO> getDealersByWarehouseTypeCode(@WebParam(name = "warehouseType") String warehouseType, @WebParam(name = "countryId") Long countryId) throws BusinessException{
    	return dealerFacadeBean.getDealersByWarehouseTypeCode(warehouseType, countryId);
    }
    
    /**
	 * Metodo: Metodo que permite retornar todos los dealer dependiendo del usuario y el tipo de dealer
	 * @param userId id usuario de la interfaz
	 * @param codeLogisticsOperator bandera para indicar si consulta los delaer con codigo operador logistico
	 * @return
     * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
    @WebMethod(operationName = "getAllActiveDealerByUserIdAndCodeLogisticsOperatorAndWarehouseTypeCode", action = "getAllActiveDealerByUserIdAndCodeLogisticsOperatorAndWarehouseTypeCode")
    public List<DealerVO> getAllActiveDealerByUserIdAndCodeLogisticsOperatorAndWarehouseTypeCode(@WebParam(name = "infoDealerCountryWarehouseDTO") InfoDealerCountryWarehouseDTO infoDealerCountryWarehouseDTO) throws BusinessException{
    	
    	return dealerFacadeBean.getAllActiveDealerByUserIdAndCodeLogisticsOperatorAndWarehouseTypeCode(infoDealerCountryWarehouseDTO);
    	
    }
    
}
