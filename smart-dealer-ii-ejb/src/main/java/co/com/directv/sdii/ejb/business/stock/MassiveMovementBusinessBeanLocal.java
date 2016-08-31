package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.MassiveMovementVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad MassiveMovement.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MassiveMovementBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto MassiveMovementVO
	 * @param obj objeto que encapsula la información de un MassiveMovementVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createMassiveMovement(MassiveMovementVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un MassiveMovementVO
	 * @param obj objeto que encapsula la información de un MassiveMovementVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateMassiveMovement(MassiveMovementVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un MassiveMovementVO
	 * @param obj información del MassiveMovementVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteMassiveMovement(MassiveMovementVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un MassiveMovementVO por su identificador
	 * @param id identificador del MassiveMovementVO a ser consultado
	 * @return objeto con la información del MassiveMovementVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public MassiveMovementVO getMassiveMovementByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los MassiveMovementVO almacenados en la persistencia
	 * @return Lista con los MassiveMovementVO existentes, una lista vacia en caso que no existan MassiveMovementVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<MassiveMovementVO> getAllMassiveMovements() throws BusinessException;

}
