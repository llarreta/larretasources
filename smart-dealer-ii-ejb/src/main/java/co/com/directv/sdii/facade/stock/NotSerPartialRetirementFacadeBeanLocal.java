package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.NotSerPartialRetirementVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad NotSerPartialRetirement.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface NotSerPartialRetirementFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto NotSerPartialRetirement
	 * @param obj - NotSerPartialRetirementVO  objeto que encapsula la información de un NotSerPartialRetirementVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createNotSerPartialRetirement(NotSerPartialRetirementVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un NotSerPartialRetirement
	 * @param obj - NotSerPartialRetirementVO  objeto que encapsula la información de un NotSerPartialRetirementVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateNotSerPartialRetirement(NotSerPartialRetirementVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un NotSerPartialRetirement
	 * @param obj - NotSerPartialRetirementVO  información del NotSerPartialRetirementVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteNotSerPartialRetirement(NotSerPartialRetirementVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un NotSerPartialRetirement por su identificador
	 * @param id - Long identificador del NotSerPartialRetirement a ser consultado
	 * @return objeto con la información del NotSerPartialRetirementVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public NotSerPartialRetirementVO getNotSerPartialRetirementByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los NotSerPartialRetirement almacenados en la persistencia
	 * @return List<NotSerPartialRetirementVO> Lista con los NotSerPartialRetirementVO existentes, una lista vacia en caso que no existan NotSerPartialRetirementVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<NotSerPartialRetirementVO> getAllNotSerPartialRetirements() throws BusinessException;

}
