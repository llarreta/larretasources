package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.SkillModeTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad SkillModeType.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SkillModeTypeBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto SkillModeTypeVO
	 * @param obj objeto que encapsula la información de un SkillModeTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSkillModeType(SkillModeTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un SkillModeTypeVO
	 * @param obj objeto que encapsula la información de un SkillModeTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSkillModeType(SkillModeTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un SkillModeTypeVO
	 * @param obj información del SkillModeTypeVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteSkillModeType(SkillModeTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un SkillModeTypeVO por su identificador
	 * @param id identificador del SkillModeTypeVO a ser consultado
	 * @return objeto con la información del SkillModeTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public SkillModeTypeVO getSkillModeTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los SkillModeTypeVO almacenados en la persistencia
	 * @return Lista con los SkillModeTypeVO existentes, una lista vacia en caso que no existan SkillModeTypeVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<SkillModeTypeVO> getAllSkillModeTypes() throws BusinessException;

	/**
	 * Metodo: Obtiene el modo de ejecución dado su código
	 * @param skillModeTypeCode código del modo de ejecución
	 * @return modo de ejecución
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public SkillModeTypeVO getSkillModeTypeByCode(String skillModeTypeCode)throws BusinessException;

}
