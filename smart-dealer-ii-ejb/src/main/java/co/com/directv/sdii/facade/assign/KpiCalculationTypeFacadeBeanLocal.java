package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.KpiCalculationTypeVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad KpiCalculationType.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface KpiCalculationTypeFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto KpiCalculationType
	 * @param obj - KpiCalculationTypeVO  objeto que encapsula la información de un KpiCalculationTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createKpiCalculationType(KpiCalculationTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un KpiCalculationType
	 * @param obj - KpiCalculationTypeVO  objeto que encapsula la información de un KpiCalculationTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateKpiCalculationType(KpiCalculationTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un KpiCalculationType
	 * @param obj - KpiCalculationTypeVO  información del KpiCalculationTypeVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteKpiCalculationType(KpiCalculationTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un KpiCalculationType por su identificador
	 * @param id - Long identificador del KpiCalculationType a ser consultado
	 * @return objeto con la información del KpiCalculationTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public KpiCalculationTypeVO getKpiCalculationTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los KpiCalculationType almacenados en la persistencia
	 * @return List<KpiCalculationTypeVO> Lista con los KpiCalculationTypeVO existentes, una lista vacia en caso que no existan KpiCalculationTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<KpiCalculationTypeVO> getAllKpiCalculationTypes() throws BusinessException;

}
