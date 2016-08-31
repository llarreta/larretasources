package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.assign.schedule.dto.DealerWorkLoad;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.ScheduleException;
import co.com.directv.sdii.model.vo.DealerCapacityVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad DealerCapacity.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerCapacityBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto DealerCapacityVO
	 * @param obj objeto que encapsula la información de un DealerCapacityVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerCapacity(DealerCapacityVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un DealerCapacityVO
	 * @param obj objeto que encapsula la información de un DealerCapacityVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerCapacity(DealerCapacityVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DealerCapacityVO
	 * @param obj información del DealerCapacityVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDealerCapacity(DealerCapacityVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un DealerCapacityVO por su identificador
	 * @param id identificador del DealerCapacityVO a ser consultado
	 * @return objeto con la información del DealerCapacityVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DealerCapacityVO getDealerCapacityByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerCapacityVO almacenados en la persistencia
	 * @return Lista con los DealerCapacityVO existentes, una lista vacia en caso que no existan DealerCapacityVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DealerCapacityVO> getAllDealerCapacitys() throws BusinessException;
	
	/**
	 * 
	 * Metodo: Persiste la capacidad de un dealer por pais, super categoria y service_hour 
	 * @param dealerCapacity Objeto que contiene la informacion para persistir
	 * @throws ScheduleException <tipo> <descripcion>
	 * @author jnova
	 */
	public void saveDealerCapacity(DealerWorkLoad dealerCapacity) throws ScheduleException;

}
