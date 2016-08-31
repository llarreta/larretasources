package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.KpiVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad Kpi.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface KpiFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto Kpi
	 * @param obj - KpiVO  objeto que encapsula la información de un KpiVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createKpi(KpiVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un Kpi
	 * @param obj - KpiVO  objeto que encapsula la información de un KpiVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateKpi(KpiVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un Kpi
	 * @param obj - KpiVO  información del KpiVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteKpi(KpiVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un Kpi por su identificador
	 * @param id - Long identificador del Kpi a ser consultado
	 * @return objeto con la información del KpiVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public KpiVO getKpiByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los Kpi almacenados en la persistencia
	 * @return List<KpiVO> Lista con los KpiVO existentes, una lista vacia en caso que no existan KpiVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<KpiVO> getAllKpis() throws BusinessException;

}
