package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.HistoDealerWeekCapacityVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad HistoDealerWeekCapacity.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface HistoDealerWeekCapacityFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto HistoDealerWeekCapacity
	 * @param obj - HistoDealerWeekCapacityVO  objeto que encapsula la información de un HistoDealerWeekCapacityVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createHistoDealerWeekCapacity(HistoDealerWeekCapacityVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un HistoDealerWeekCapacity
	 * @param obj - HistoDealerWeekCapacityVO  objeto que encapsula la información de un HistoDealerWeekCapacityVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateHistoDealerWeekCapacity(HistoDealerWeekCapacityVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un HistoDealerWeekCapacity
	 * @param obj - HistoDealerWeekCapacityVO  información del HistoDealerWeekCapacityVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteHistoDealerWeekCapacity(HistoDealerWeekCapacityVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un HistoDealerWeekCapacity por su identificador
	 * @param id - Long identificador del HistoDealerWeekCapacity a ser consultado
	 * @return objeto con la información del HistoDealerWeekCapacityVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public HistoDealerWeekCapacityVO getHistoDealerWeekCapacityByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los HistoDealerWeekCapacity almacenados en la persistencia
	 * @return List<HistoDealerWeekCapacityVO> Lista con los HistoDealerWeekCapacityVO existentes, una lista vacia en caso que no existan HistoDealerWeekCapacityVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<HistoDealerWeekCapacityVO> getAllHistoDealerWeekCapacitys() throws BusinessException;

}
