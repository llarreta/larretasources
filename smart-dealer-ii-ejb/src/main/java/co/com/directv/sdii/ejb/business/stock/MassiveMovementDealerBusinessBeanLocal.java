package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.MassiveMovementDealerVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad MassiveMovementDealer.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MassiveMovementDealerBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto MassiveMovementDealerVO
	 * @param obj objeto que encapsula la información de un MassiveMovementDealerVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createMassiveMovementDealer(MassiveMovementDealerVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un MassiveMovementDealerVO
	 * @param obj objeto que encapsula la información de un MassiveMovementDealerVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateMassiveMovementDealer(MassiveMovementDealerVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un MassiveMovementDealerVO
	 * @param obj información del MassiveMovementDealerVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteMassiveMovementDealer(MassiveMovementDealerVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un MassiveMovementDealerVO por su identificador
	 * @param id identificador del MassiveMovementDealerVO a ser consultado
	 * @return objeto con la información del MassiveMovementDealerVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public MassiveMovementDealerVO getMassiveMovementDealerByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los MassiveMovementDealerVO almacenados en la persistencia
	 * @return Lista con los MassiveMovementDealerVO existentes, una lista vacia en caso que no existan MassiveMovementDealerVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<MassiveMovementDealerVO> getAllMassiveMovementDealers() throws BusinessException;

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param id
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<MassiveMovementDealerVO> getMassiveMovementDealerByMovementID(
			Long id) throws BusinessException;

}
