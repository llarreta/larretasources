package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.DealerWeekCapacityVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad DealerWeekCapacity.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerWeekCapacityBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto DealerWeekCapacityVO
	 * @param obj objeto que encapsula la información de un DealerWeekCapacityVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerWeekCapacity(DealerWeekCapacityVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un DealerWeekCapacityVO
	 * @param obj objeto que encapsula la información de un DealerWeekCapacityVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerWeekCapacity(DealerWeekCapacityVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DealerWeekCapacityVO
	 * @param obj información del DealerWeekCapacityVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDealerWeekCapacity(DealerWeekCapacityVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un DealerWeekCapacityVO por su identificador
	 * @param id identificador del DealerWeekCapacityVO a ser consultado
	 * @return objeto con la información del DealerWeekCapacityVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DealerWeekCapacityVO getDealerWeekCapacityByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerWeekCapacityVO almacenados en la persistencia
	 * @return Lista con los DealerWeekCapacityVO existentes, una lista vacia en caso que no existan DealerWeekCapacityVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DealerWeekCapacityVO> getAllDealerWeekCapacitys() throws BusinessException;

	/**
	 * Metodo: Obtiene la configuración de capacidad de una semana por los filtros
	 * especificados
	 * @param countryId identificador del país
	 * @param dealerId identificador de la compañía instaladora
	 * @param serviceHourId identificador de la jornada
	 * @param serviceSuperCategoryId identificador de la super categoría
	 * @return Información de la capacidad configurada para la semana tipo por jornada y super categoría de servicio
	 * @author jjimenezh
	 */
	public DealerWeekCapacityVO getWeekCapacityByDealerIdAndServiceHourAndServiceSuperCat(
			Long countryId, Long dealerId, Long serviceHourId,
			Long serviceSuperCategoryId)throws BusinessException;

	/**
	 * Metodo: Obtiene la configuración de capacidad de una semana por los filtros
	 * @param dealerId identificador del dealer
	 * @return List<DealerWeekCapacityVO>
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public List<DealerWeekCapacityVO> getDealerWeekCapacityByDealerIdAndCountryId(Long dealerId, Long countryId) throws BusinessException;

}
