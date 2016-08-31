package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.SkillEvaluationTypeVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad SkillEvaluationType.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SkillEvaluationTypeFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto SkillEvaluationType
	 * @param obj - SkillEvaluationTypeVO  objeto que encapsula la información de un SkillEvaluationTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSkillEvaluationType(SkillEvaluationTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un SkillEvaluationType
	 * @param obj - SkillEvaluationTypeVO  objeto que encapsula la información de un SkillEvaluationTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSkillEvaluationType(SkillEvaluationTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un SkillEvaluationType
	 * @param obj - SkillEvaluationTypeVO  información del SkillEvaluationTypeVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteSkillEvaluationType(SkillEvaluationTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un SkillEvaluationType por su identificador
	 * @param id - Long identificador del SkillEvaluationType a ser consultado
	 * @return objeto con la información del SkillEvaluationTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public SkillEvaluationTypeVO getSkillEvaluationTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los SkillEvaluationType almacenados en la persistencia
	 * @return List<SkillEvaluationTypeVO> Lista con los SkillEvaluationTypeVO existentes, una lista vacia en caso que no existan SkillEvaluationTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<SkillEvaluationTypeVO> getAllSkillEvaluationTypes() throws BusinessException;

}
