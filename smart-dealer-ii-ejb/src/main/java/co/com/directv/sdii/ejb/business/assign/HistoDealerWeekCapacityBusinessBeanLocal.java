package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.HistoDealerWeekCapacityVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad HistoDealerWeekCapacity.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface HistoDealerWeekCapacityBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto HistoDealerWeekCapacityVO
	 * @param obj objeto que encapsula la información de un HistoDealerWeekCapacityVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createHistoDealerWeekCapacity(HistoDealerWeekCapacityVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un HistoDealerWeekCapacityVO
	 * @param obj objeto que encapsula la información de un HistoDealerWeekCapacityVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateHistoDealerWeekCapacity(HistoDealerWeekCapacityVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un HistoDealerWeekCapacityVO
	 * @param obj información del HistoDealerWeekCapacityVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteHistoDealerWeekCapacity(HistoDealerWeekCapacityVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un HistoDealerWeekCapacityVO por su identificador
	 * @param id identificador del HistoDealerWeekCapacityVO a ser consultado
	 * @return objeto con la información del HistoDealerWeekCapacityVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public HistoDealerWeekCapacityVO getHistoDealerWeekCapacityByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los HistoDealerWeekCapacityVO almacenados en la persistencia
	 * @return Lista con los HistoDealerWeekCapacityVO existentes, una lista vacia en caso que no existan HistoDealerWeekCapacityVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<HistoDealerWeekCapacityVO> getAllHistoDealerWeekCapacitys() throws BusinessException;

}
