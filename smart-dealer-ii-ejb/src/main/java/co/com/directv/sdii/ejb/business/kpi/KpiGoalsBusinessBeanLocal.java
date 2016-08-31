package co.com.directv.sdii.ejb.business.kpi;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.KpiGoalsVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad KpiGoals.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface KpiGoalsBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto KpiGoalsVO
	 * @param obj objeto que encapsula la información de un KpiGoalsVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createKpiGoals(KpiGoalsVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un KpiGoalsVO
	 * @param obj objeto que encapsula la información de un KpiGoalsVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateKpiGoals(KpiGoalsVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un KpiGoalsVO
	 * @param obj información del KpiGoalsVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteKpiGoals(KpiGoalsVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un KpiGoalsVO por su identificador
	 * @param id identificador del KpiGoalsVO a ser consultado
	 * @return objeto con la información del KpiGoalsVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public KpiGoalsVO getKpiGoalsByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los KpiGoalsVO almacenados en la persistencia
	 * @return Lista con los KpiGoalsVO existentes, una lista vacia en caso que no existan KpiGoalsVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<KpiGoalsVO> getAllKpiGoalss() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los KpiGoalsVO almacenados en la persistencia
	 * @param country - Long identificador del pa�s
	 * @return Lista con los KpiGoalsVO existentes, una lista vacia en caso que no existan KpiGoalsVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author gfandino
	 */
	public List<KpiGoalsVO> getAllKpiGoalssAndByCountry(Long country) throws BusinessException;

	
	/**
	 * Metodo: Obtiene los KPI goals por identificador de indicador
	 * @param indicatorId identificador de indicador
	 * @return lista con los KPI goals cuyo identificador de indicador coincida con el especificado
	 * @throws BusinessException En caso de error al realizar la consulta por el identifiador de indicador
	 * @author jjimenezh
	 */
	public List<KpiGoalsVO> getKpiGoalsByIndicatorIdAndCountryId(Long indicatorId, Long countryId)throws BusinessException;

	
}
