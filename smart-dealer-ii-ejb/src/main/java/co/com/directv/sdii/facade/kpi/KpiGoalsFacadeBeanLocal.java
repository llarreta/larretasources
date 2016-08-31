package co.com.directv.sdii.facade.kpi;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.KpiGoalsVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad KpiGoals.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface KpiGoalsFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto KpiGoals
	 * @param obj - KpiGoalsVO  objeto que encapsula la información de un KpiGoalsVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createKpiGoals(KpiGoalsVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un KpiGoals
	 * @param obj - KpiGoalsVO  objeto que encapsula la información de un KpiGoalsVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateKpiGoals(KpiGoalsVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un KpiGoals
	 * @param obj - KpiGoalsVO  información del KpiGoalsVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteKpiGoals(KpiGoalsVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un KpiGoals por su identificador
	 * @param id - Long identificador del KpiGoals a ser consultado
	 * @return objeto con la información del KpiGoalsVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public KpiGoalsVO getKpiGoalsByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los KpiGoals almacenados en la persistencia
	 * @return List<KpiGoalsVO> Lista con los KpiGoalsVO existentes, una lista vacia en caso que no existan KpiGoalsVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<KpiGoalsVO> getAllKpiGoalss() throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los KpiGoals almacenados en la persistencia
	 * @param country - Long identificador del pa�s
	 * @return List<KpiGoalsVO> Lista con los KpiGoalsVO existentes, una lista vacia en caso que no existan KpiGoalsVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
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
