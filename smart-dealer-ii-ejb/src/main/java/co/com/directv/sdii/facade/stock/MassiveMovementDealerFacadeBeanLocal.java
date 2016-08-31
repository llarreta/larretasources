package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.MassiveMovementDealerVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad MassiveMovementDealer.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MassiveMovementDealerFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto MassiveMovementDealer
	 * @param obj - MassiveMovementDealerVO  objeto que encapsula la información de un MassiveMovementDealerVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createMassiveMovementDealer(MassiveMovementDealerVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un MassiveMovementDealer
	 * @param obj - MassiveMovementDealerVO  objeto que encapsula la información de un MassiveMovementDealerVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateMassiveMovementDealer(MassiveMovementDealerVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un MassiveMovementDealer
	 * @param obj - MassiveMovementDealerVO  información del MassiveMovementDealerVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteMassiveMovementDealer(MassiveMovementDealerVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un MassiveMovementDealer por su identificador
	 * @param id - Long identificador del MassiveMovementDealer a ser consultado
	 * @return objeto con la información del MassiveMovementDealerVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public MassiveMovementDealerVO getMassiveMovementDealerByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los MassiveMovementDealer almacenados en la persistencia
	 * @return List<MassiveMovementDealerVO> Lista con los MassiveMovementDealerVO existentes, una lista vacia en caso que no existan MassiveMovementDealerVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<MassiveMovementDealerVO> getAllMassiveMovementDealers() throws BusinessException;

	/**
	 * 
	 * Metodo: Consulta la lista en el reporte del movimiento masivo de elementos. CU INV 131
	 * @param id
	 * @return <tipo> <descripcion>
	 * @author hcorredor
	 */
	public List<MassiveMovementDealerVO> getMassiveMovementDealerByMovementID(Long id) throws BusinessException;

}
