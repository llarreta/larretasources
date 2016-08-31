package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.SkillModeTypeVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad SkillModeType.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SkillModeTypeFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto SkillModeType
	 * @param obj - SkillModeTypeVO  objeto que encapsula la información de un SkillModeTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSkillModeType(SkillModeTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un SkillModeType
	 * @param obj - SkillModeTypeVO  objeto que encapsula la información de un SkillModeTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSkillModeType(SkillModeTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un SkillModeType
	 * @param obj - SkillModeTypeVO  información del SkillModeTypeVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteSkillModeType(SkillModeTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un SkillModeType por su identificador
	 * @param id - Long identificador del SkillModeType a ser consultado
	 * @return objeto con la información del SkillModeTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public SkillModeTypeVO getSkillModeTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los SkillModeType almacenados en la persistencia
	 * @return List<SkillModeTypeVO> Lista con los SkillModeTypeVO existentes, una lista vacia en caso que no existan SkillModeTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<SkillModeTypeVO> getAllSkillModeTypes() throws BusinessException;

}
