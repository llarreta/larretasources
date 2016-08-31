package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.KpiVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad Kpi.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface KpiBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto KpiVO
	 * @param obj objeto que encapsula la información de un KpiVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createKpi(KpiVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un KpiVO
	 * @param obj objeto que encapsula la información de un KpiVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateKpi(KpiVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un KpiVO
	 * @param obj información del KpiVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteKpi(KpiVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un KpiVO por su identificador
	 * @param id identificador del KpiVO a ser consultado
	 * @return objeto con la información del KpiVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public KpiVO getKpiByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los KpiVO almacenados en la persistencia
	 * @return Lista con los KpiVO existentes, una lista vacia en caso que no existan KpiVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<KpiVO> getAllKpis() throws BusinessException;

}
