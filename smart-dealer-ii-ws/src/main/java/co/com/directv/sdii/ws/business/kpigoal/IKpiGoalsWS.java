package co.com.directv.sdii.ws.business.kpigoal;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.assign.kpi.dto.RequestSearchKpiResultsDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.ResponseSearchKpiResultsResponse;
import co.com.directv.sdii.model.vo.KpiGoalsVO;
import co.com.directv.sdii.model.vo.KpiVO;

/**
 * Servicio web que expone las operaciones relacionadas con KpiGoals
 * 
 * Fecha de CreaciÃ³n: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 */
@WebService(name="KpiGoalsWS",targetNamespace="http://kpigoal.business.ws.sdii.directv.com.co/")
public interface IKpiGoalsWS {

	/**
	 * Metodo: persiste la informaciÃ³n de un KpiGoals
	 * @param obj objeto que encapsula la informaciÃ³n necesaria para construir el KpiGoals,
	 * no debe venir asignada la propiedad id, de lo contrario se generarÃ¡ un error
	 * @throws BusinessException en caso de error al ejecutar la operaciÃ³n
	 * a continuaciÃ³n los cÃ³digos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricciÃ³n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genÃ©rico relacionado con el componente de conexiÃ³n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramÃ¡tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parÃ¡metros necesarios para la ejecuciÃ³n de la operaciÃ³n<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="createKpiGoals", action="createKpiGoals")
	public void createKpiGoals(@WebParam(name="objKpiGoals")KpiGoalsVO objKpiGoals) throws BusinessException;
	
	/**
	 * Metodo: actualiza la informaciÃ³n de un KpiGoals
	 * @param obj objeto que encapsula la informaciÃ³n del KpiGoals a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generarÃ¡ un nuevo KpiGoals
	 * @throws BusinessException en caso de error al ejecutar la operaciÃ³n
	 * a continuaciÃ³n los cÃ³digos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricciÃ³n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genÃ©rico relacionado con el componente de conexiÃ³n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramÃ¡tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parÃ¡metros necesarios para la ejecuciÃ³n de la operaciÃ³n<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="updateKpiGoals", action="updateKpiGoals")
	public void updateKpiGoals(@WebParam(name="objKpiGoals")KpiGoalsVO objKpiGoals) throws BusinessException;
	
	/**
	 * Metodo: borra un KpiGoals de la persistencia
	 * @param obj objeto que encapsula la informaciÃ³n del KpiGoals, solo se requiere la propiedad id
	 * @throws BusinessException en caso de error al ejecutar la operaciÃ³n
	 * a continuaciÃ³n los cÃ³digos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricciÃ³n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genÃ©rico relacionado con el componente de conexiÃ³n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramÃ¡tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parÃ¡metros necesarios para la ejecuciÃ³n de la operaciÃ³n<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="deleteKpiGoals", action="deleteKpiGoals")
	public void deleteKpiGoals(@WebParam(name="objKpiGoals")KpiGoalsVO objKpiGoals) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un KpiGoals dado el identificador del mismo
	 * @param id identificador del KpiGoals a ser consultado
	 * @return KpiGoals con el id especificado, se lanza una exepciÃ³n en caso que no se
	 * encuentre el KpiGoals con el id, ver cÃ³digos de excepciÃ³n.
	 * @throws BusinessException en caso de error al ejecutar la operaciÃ³n
	 * a continuaciÃ³n los cÃ³digos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricciÃ³n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genÃ©rico relacionado con el componente de conexiÃ³n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramÃ¡tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parÃ¡metros necesarios para la ejecuciÃ³n de la operaciÃ³n<br>
	 * <code>sdii_CODE_entity_not_found</code> En caso que no encuentre el tipo de dealer por el id especificado<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getKpiGoalsByID", action="getKpiGoalsByID")
	public KpiGoalsVO getKpiGoalsByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los KpiGoals almacenados en la persistencia
	 * @return lista con los KpiGoals existentes, una lista vacia en caso que no exista ninguno.
	 * @throws BusinessException en caso de error al ejecutar la operaciÃ³n
	 * a continuaciÃ³n los cÃ³digos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricciÃ³n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genÃ©rico relacionado con el componente de conexiÃ³n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramÃ¡tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parÃ¡metros necesarios para la ejecuciÃ³n de la operaciÃ³n<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getAllKpiGoalss", action="getAllKpiGoalss")
	public List<KpiGoalsVO> getAllKpiGoalss() throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los KpiGoals almacenados en la persistencia por identificador de indicador
	 * @return lista con los KpiGoals existentes dado el identificador de indicador, una lista vacia en caso que no exista ninguno.
	 * @throws BusinessException en caso de error al ejecutar la operaciÃ³n
	 * a continuaciÃ³n los cÃ³digos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricciÃ³n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genÃ©rico relacionado con el componente de conexiÃ³n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramÃ¡tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parÃ¡metros necesarios para la ejecuciÃ³n de la operaciÃ³n<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getKpiGoalsByIndicatorIdAndCountryId", action="getKpiGoalsByIndicatorIdAndCountryId")
	public List<KpiGoalsVO> getKpiGoalsByIndicatorIdAndCountryId(@WebParam(name="indicatorId") Long indicatorId,@WebParam(name="countryId") Long countryId) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene todos los KpiGoals almacenados en la persistencia
	 * @param country - Long identificador del paï¿½s
	 * @return lista con los KpiGoals existentes, una lista vacia en caso que no exista ninguno.
	 * @throws BusinessException en caso de error al ejecutar la operaciÃ³n
	 * a continuaciÃ³n los cÃ³digos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricciÃ³n definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genÃ©rico relacionado con el componente de conexiÃ³n a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramÃ¡tica para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parÃ¡metros necesarios para la ejecuciÃ³n de la operaciÃ³n<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getAllKpiGoalssAndByCountry", action="getAllKpiGoalssAndByCountry")
	public List<KpiGoalsVO> getAllKpiGoalssAndByCountry(@WebParam(name="country")Long country) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la informaciÃ³n del promedio por mes de los Kpi
	 * @param RequestSearchKpiResultsDTO Objeto para transportar los filtros
	 * @param RequestCollectionInfo Objeto utilizado para la paginacion
	 * @return Lista con los Kpi existentes, una lista vacia en caso que no existan Kpi en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operaciÃ³n
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operaciÃ³n
	 * @author cduarte
	 */
	@WebMethod(operationName="getKpiResultDealersByDealerIdAndBetweenDate", action="getKpiResultDealersByDealerIdAndBetweenDate")
	public ResponseSearchKpiResultsResponse getKpiResultDealersByDealerIdAndBetweenDate(@WebParam(name="request")RequestSearchKpiResultsDTO request,@WebParam(name="requestCollInfo") RequestCollectionInfo requestCollInfo )throws BusinessException;
	
	/**
	 * Metodo: Obtiene la informaciÃ³n del promedio por mes de los Kpi
	 * @param RequestSearchKpiResultsDTO Objeto para transportar los filtros
	 * @param RequestCollectionInfo Objeto utilizado para la paginacion
	 * @return Lista con los Kpi existentes, una lista vacia en caso que no existan Kpi en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operaciÃ³n
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operaciÃ³n
	 * @author cduarte
	 */
	@WebMethod(operationName="getKpiResultByDealerIdAndBetweenDate", action="getKpiResultByDealerIdAndBetweenDate")
	public ResponseSearchKpiResultsResponse getKpiResultByDealerIdAndBetweenDate(@WebParam(name="request")RequestSearchKpiResultsDTO request)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la informaciÃ³n de todos los KpiVO almacenados en la persistencia
	 * @return Lista con los KpiVO existentes, una lista vacia en caso que no existan KpiVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operaciÃ³n
	 * @author cduarte
	 */
	@WebMethod(operationName="getAllKpis", action="getAllKpis")
	public List<KpiVO> getAllKpis() throws BusinessException;
	
}
