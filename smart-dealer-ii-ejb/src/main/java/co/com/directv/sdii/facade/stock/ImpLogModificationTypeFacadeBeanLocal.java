package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ImpLogModificationTypeVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ImpLogModificationType.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ImpLogModificationTypeFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ImpLogModificationType
	 * @param obj - ImpLogModificationTypeVO  objeto que encapsula la información de un ImpLogModificationTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createImpLogModificationType(ImpLogModificationTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ImpLogModificationType
	 * @param obj - ImpLogModificationTypeVO  objeto que encapsula la información de un ImpLogModificationTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateImpLogModificationType(ImpLogModificationTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ImpLogModificationType
	 * @param obj - ImpLogModificationTypeVO  información del ImpLogModificationTypeVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteImpLogModificationType(ImpLogModificationTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ImpLogModificationType por su identificador
	 * @param id - Long identificador del ImpLogModificationType a ser consultado
	 * @return objeto con la información del ImpLogModificationTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public ImpLogModificationTypeVO getImpLogModificationTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ImpLogModificationType almacenados en la persistencia
	 * @return List<ImpLogModificationTypeVO> Lista con los ImpLogModificationTypeVO existentes, una lista vacia en caso que no existan ImpLogModificationTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<ImpLogModificationTypeVO> getAllImpLogModificationTypes() throws BusinessException;

	
	/**
	 * Metodo: Obtiene un tipo de modificación de import log por el código
	 * @param code código del tipo de modificación a ser consultado
	 * @return tipo de modificación de import log, nulo en caso que no se encuentre
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public ImpLogModificationTypeVO getImpLogModificationTypeByCode(String code)throws BusinessException;

	/**
	 * Metodo: Obtiene un tipo de modificación de import log por el código
	 * @param code código del tipo de modificación a ser consultado
	 * @return tipo de modificación de import log, nulo en caso que no se encuentre
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<ImpLogModificationTypeVO> getActiveImpLogModificationTypes()throws BusinessException;

}
