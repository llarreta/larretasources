package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerCapacityVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad DealerCapacity.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerCapacityFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto DealerCapacity
	 * @param obj - DealerCapacityVO  objeto que encapsula la información de un DealerCapacityVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerCapacity(DealerCapacityVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un DealerCapacity
	 * @param obj - DealerCapacityVO  objeto que encapsula la información de un DealerCapacityVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerCapacity(DealerCapacityVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un DealerCapacity
	 * @param obj - DealerCapacityVO  información del DealerCapacityVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteDealerCapacity(DealerCapacityVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un DealerCapacity por su identificador
	 * @param id - Long identificador del DealerCapacity a ser consultado
	 * @return objeto con la información del DealerCapacityVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public DealerCapacityVO getDealerCapacityByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los DealerCapacity almacenados en la persistencia
	 * @return List<DealerCapacityVO> Lista con los DealerCapacityVO existentes, una lista vacia en caso que no existan DealerCapacityVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<DealerCapacityVO> getAllDealerCapacitys() throws BusinessException;

}
