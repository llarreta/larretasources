package co.com.directv.sdii.persistence.dao.kpi;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.KpiGoals;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad KpiGoals
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface KpiGoalsDAOLocal {

	/**
	 * Metodo:  persiste la información de un KpiGoals
	 * @param obj objeto que encapsula la información de un KpiGoals
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createKpiGoals(KpiGoals obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un KpiGoals
	 * @param obj objeto que encapsula la información de un KpiGoals
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateKpiGoals(KpiGoals obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un KpiGoals
	 * @param obj información del KpiGoals a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteKpiGoals(KpiGoals obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un KpiGoals por su identificador
	 * @param id identificador del KpiGoals a ser consultado
	 * @return objeto con la información del KpiGoals dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public KpiGoals getKpiGoalsByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los KpiGoals almacenados en la persistencia
	 * @return Lista con los KpiGoals existentes, una lista vacia en caso que no existan KpiGoals en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<KpiGoals> getAllKpiGoalss() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los KpiGoals almacenados en la persistencia
	 * @param country - Long identificador del pa�s
	 * @return Lista con los KpiGoals existentes, una lista vacia en caso que no existan KpiGoals en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author gfandino
	 */
	public List<KpiGoals> getAllKpiGoalssAndByCountry(Long country) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene una lista de los KPI Goals dado el identificador del indicador y el identificador del país
	 * @param indicatorId identificador del indicador 
	 * @param countryId identificador del país
	 * @return Lista con los KPI Goals dados los criterios de consulta
	 * @throws DAOServiceException en caso de error al realizar la consulta
	 * @throws DAOSQLException en caso de error al realizar la consulta
	 * @author jjimenezh
	 */
	public List<KpiGoals> getKpiGoalsByIndicatorIdAndCountryId(Long indicatorId, Long countryId) throws DAOServiceException, DAOSQLException;


}