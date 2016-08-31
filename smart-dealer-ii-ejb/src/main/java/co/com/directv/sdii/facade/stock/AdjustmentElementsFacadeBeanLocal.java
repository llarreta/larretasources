package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.AdjustmentElementsVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad AdjustmentElements.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface AdjustmentElementsFacadeBeanLocal {

	
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un AdjustmentElements
	 * @param obj - AdjustmentElementsVO  objeto que encapsula la información de un AdjustmentElementsVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateAdjustmentElements(AdjustmentElementsVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un AdjustmentElements
	 * @param obj - AdjustmentElementsVO  información del AdjustmentElementsVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteAdjustmentElements(AdjustmentElementsVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un AdjustmentElements por su identificador
	 * @param id - Long identificador del AdjustmentElements a ser consultado
	 * @return objeto con la información del AdjustmentElementsVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public AdjustmentElementsVO getAdjustmentElementsByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los AdjustmentElements almacenados en la persistencia
	 * @return List<AdjustmentElementsVO> Lista con los AdjustmentElementsVO existentes, una lista vacia en caso que no existan AdjustmentElementsVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<AdjustmentElementsVO> getAllAdjustmentElementss() throws BusinessException;

}
