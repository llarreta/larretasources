package co.com.directv.sdii.ws.business.kpigoal.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.assign.kpi.dto.RequestSearchKpiResultsDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.KpiFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.KpiResultFacadeBeanLocal;
import co.com.directv.sdii.facade.kpi.KpiGoalsFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.ResponseSearchKpiResultsResponse;
import co.com.directv.sdii.model.vo.KpiGoalsVO;
import co.com.directv.sdii.model.vo.KpiVO;
import co.com.directv.sdii.ws.business.kpigoal.IKpiGoalsWS;

/**
 * Servicio web que expone las operaciones relacionadas con KpiGoals
 * 
 * Fecha de CreaciÃ³n: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.kpi.KpiGoalsFacadeBeanLocal
 */
@MTOM
@WebService(serviceName="KpiGoalsWSService",
		endpointInterface="co.com.directv.sdii.ws.business.kpigoal.IKpiGoalsWS",
		targetNamespace="http://kpigoal.business.ws.sdii.directv.com.co/",
		portName="KpiGoalsWSPort")
@Stateless()
public class KpiGoalsWS implements IKpiGoalsWS {

	@EJB
    private KpiGoalsFacadeBeanLocal ejbRef;
	
	@EJB
    private KpiResultFacadeBeanLocal beanKpiResult;
	
	@EJB
    private KpiFacadeBeanLocal beanKpi;
	
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
	public void createKpiGoals(KpiGoalsVO objKpiGoals) throws BusinessException{
		ejbRef.createKpiGoals(objKpiGoals);
	}
	
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
	public void updateKpiGoals(KpiGoalsVO objKpiGoals) throws BusinessException{
		ejbRef.updateKpiGoals(objKpiGoals);
	}
	
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
	public void deleteKpiGoals(KpiGoalsVO objKpiGoals) throws BusinessException{
		ejbRef.deleteKpiGoals(objKpiGoals);
	}
	
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
	public KpiGoalsVO getKpiGoalsByID(Long id) throws BusinessException{
		return ejbRef.getKpiGoalsByID(id);
	}
	
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
	public List<KpiGoalsVO> getAllKpiGoalss() throws BusinessException{
		return ejbRef.getAllKpiGoalss();
	}
	
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
	 * @author jjimenezh
	 */
	public List<KpiGoalsVO> getAllKpiGoalssAndByCountry(Long country) throws BusinessException{
		return ejbRef.getAllKpiGoalssAndByCountry(country);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.kpi.IKpiGoalsWS#getKpiGoalsByIndicatorId(java.lang.Long)
	 */
	@Override
	public List<KpiGoalsVO> getKpiGoalsByIndicatorIdAndCountryId(Long indicatorId, Long countryId)throws BusinessException {
		return ejbRef.getKpiGoalsByIndicatorIdAndCountryId(indicatorId, countryId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.kpi.IKpiGoalsWS#getKpiResultByDealerIdAndBetweenDate(co.com.directv.sdii.assign.kpi.dto.RequestSearchKpiResultsDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ResponseSearchKpiResultsResponse getKpiResultDealersByDealerIdAndBetweenDate(RequestSearchKpiResultsDTO request, RequestCollectionInfo requestCollInfo ) throws BusinessException {
		return beanKpiResult.getKpiResultDealersByDealerIdAndBetweenDate(request,requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.kpi.IKpiGoalsWS#getKpiResultByDealerIdAndBetweenDate(co.com.directv.sdii.assign.kpi.dto.RequestSearchKpiResultsDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ResponseSearchKpiResultsResponse getKpiResultByDealerIdAndBetweenDate(RequestSearchKpiResultsDTO request) throws BusinessException {
		return beanKpiResult.getKpiResultByDealerIdAndBetweenDate(request);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.kpi.IKpiGoalsWS#getAllKpis()
	 */
	public List<KpiVO> getAllKpis() throws BusinessException {
		return beanKpi.getAllKpis();
	}
	
}
