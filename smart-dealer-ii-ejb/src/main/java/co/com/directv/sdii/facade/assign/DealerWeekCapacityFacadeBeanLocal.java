package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.DealerWeekCapacityVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad DealerWeekCapacity.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerWeekCapacityFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto DealerWeekCapacity
	 * @param obj - DealerWeekCapacityVO  objeto que encapsula la información de un DealerWeekCapacityVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerWeekCapacity(DealerWeekCapacityVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un DealerWeekCapacity
	 * @param obj - DealerWeekCapacityVO  objeto que encapsula la información de un DealerWeekCapacityVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerWeekCapacity(DealerWeekCapacityVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un DealerWeekCapacity
	 * @param obj - DealerWeekCapacityVO  información del DealerWeekCapacityVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteDealerWeekCapacity(DealerWeekCapacityVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un DealerWeekCapacity por su identificador
	 * @param id - Long identificador del DealerWeekCapacity a ser consultado
	 * @return objeto con la información del DealerWeekCapacityVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public DealerWeekCapacityVO getDealerWeekCapacityByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los DealerWeekCapacity almacenados en la persistencia
	 * @return List<DealerWeekCapacityVO> Lista con los DealerWeekCapacityVO existentes, una lista vacia en caso que no existan DealerWeekCapacityVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<DealerWeekCapacityVO> getAllDealerWeekCapacitys() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la configuración de capacidad de una semana por los filtros
	 * @param dealerId identificador del dealer
	 * @return List<DealerWeekCapacity>
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public List<DealerWeekCapacityVO> getDealerWeekCapacityByDealerIdAndCountryId(Long dealerId, Long countryId) throws BusinessException;

}
