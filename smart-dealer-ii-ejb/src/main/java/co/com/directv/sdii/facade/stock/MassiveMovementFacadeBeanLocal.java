package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.MassiveMovementVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad MassiveMovement.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MassiveMovementFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto MassiveMovement
	 * @param obj - MassiveMovementVO  objeto que encapsula la información de un MassiveMovementVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createMassiveMovement(MassiveMovementVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un MassiveMovement
	 * @param obj - MassiveMovementVO  objeto que encapsula la información de un MassiveMovementVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateMassiveMovement(MassiveMovementVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un MassiveMovement
	 * @param obj - MassiveMovementVO  información del MassiveMovementVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteMassiveMovement(MassiveMovementVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un MassiveMovement por su identificador
	 * @param id - Long identificador del MassiveMovement a ser consultado
	 * @return objeto con la información del MassiveMovementVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public MassiveMovementVO getMassiveMovementByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los MassiveMovement almacenados en la persistencia
	 * @return List<MassiveMovementVO> Lista con los MassiveMovementVO existentes, una lista vacia en caso que no existan MassiveMovementVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<MassiveMovementVO> getAllMassiveMovements() throws BusinessException;

}
