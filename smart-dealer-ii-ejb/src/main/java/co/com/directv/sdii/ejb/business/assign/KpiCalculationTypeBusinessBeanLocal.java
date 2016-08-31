package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.KpiCalculationTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad KpiCalculationType.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface KpiCalculationTypeBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto KpiCalculationTypeVO
	 * @param obj objeto que encapsula la información de un KpiCalculationTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createKpiCalculationType(KpiCalculationTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un KpiCalculationTypeVO
	 * @param obj objeto que encapsula la información de un KpiCalculationTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateKpiCalculationType(KpiCalculationTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un KpiCalculationTypeVO
	 * @param obj información del KpiCalculationTypeVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteKpiCalculationType(KpiCalculationTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un KpiCalculationTypeVO por su identificador
	 * @param id identificador del KpiCalculationTypeVO a ser consultado
	 * @return objeto con la información del KpiCalculationTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public KpiCalculationTypeVO getKpiCalculationTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los KpiCalculationTypeVO almacenados en la persistencia
	 * @return Lista con los KpiCalculationTypeVO existentes, una lista vacia en caso que no existan KpiCalculationTypeVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<KpiCalculationTypeVO> getAllKpiCalculationTypes() throws BusinessException;

}
