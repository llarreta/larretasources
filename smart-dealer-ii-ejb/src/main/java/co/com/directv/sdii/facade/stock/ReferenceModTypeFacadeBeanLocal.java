package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ReferenceModTypeVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ReferenceModType.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ReferenceModTypeFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ReferenceModType
	 * @param obj - ReferenceModTypeVO  objeto que encapsula la información de un ReferenceModTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createReferenceModType(ReferenceModTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ReferenceModType
	 * @param obj - ReferenceModTypeVO  objeto que encapsula la información de un ReferenceModTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateReferenceModType(ReferenceModTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ReferenceModType
	 * @param obj - ReferenceModTypeVO  información del ReferenceModTypeVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteReferenceModType(ReferenceModTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ReferenceModType por su identificador
	 * @param id - Long identificador del ReferenceModType a ser consultado
	 * @return objeto con la información del ReferenceModTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public ReferenceModTypeVO getReferenceModTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ReferenceModType almacenados en la persistencia
	 * @return List<ReferenceModTypeVO> Lista con los ReferenceModTypeVO existentes, una lista vacia en caso que no existan ReferenceModTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<ReferenceModTypeVO> getAllReferenceModTypes() throws BusinessException;

}
