package co.com.directv.sdii.ejb.business.kpi;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.IndicatorsVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad Indicators.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface IndicatorsBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto IndicatorsVO
	 * @param obj objeto que encapsula la información de un IndicatorsVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createIndicators(IndicatorsVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un IndicatorsVO
	 * @param obj objeto que encapsula la información de un IndicatorsVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateIndicators(IndicatorsVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un IndicatorsVO
	 * @param obj información del IndicatorsVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteIndicators(IndicatorsVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un IndicatorsVO por su identificador
	 * @param id identificador del IndicatorsVO a ser consultado
	 * @return objeto con la información del IndicatorsVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public IndicatorsVO getIndicatorsByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los IndicatorsVO almacenados en la persistencia
	 * @return Lista con los IndicatorsVO existentes, una lista vacia en caso que no existan IndicatorsVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<IndicatorsVO> getAllIndicatorss() throws BusinessException;

}
