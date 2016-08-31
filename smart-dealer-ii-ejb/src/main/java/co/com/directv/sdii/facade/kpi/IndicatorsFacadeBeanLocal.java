package co.com.directv.sdii.facade.kpi;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.IndicatorsVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad Indicators.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface IndicatorsFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto Indicators
	 * @param obj - IndicatorsVO  objeto que encapsula la información de un IndicatorsVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createIndicators(IndicatorsVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un Indicators
	 * @param obj - IndicatorsVO  objeto que encapsula la información de un IndicatorsVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateIndicators(IndicatorsVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un Indicators
	 * @param obj - IndicatorsVO  información del IndicatorsVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteIndicators(IndicatorsVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un Indicators por su identificador
	 * @param id - Long identificador del Indicators a ser consultado
	 * @return objeto con la información del IndicatorsVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public IndicatorsVO getIndicatorsByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los Indicators almacenados en la persistencia
	 * @return List<IndicatorsVO> Lista con los IndicatorsVO existentes, una lista vacia en caso que no existan IndicatorsVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<IndicatorsVO> getAllIndicatorss() throws BusinessException;

}
