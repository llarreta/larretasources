package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.SkillEvaluationTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad SkillEvaluationType.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SkillEvaluationTypeBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto SkillEvaluationTypeVO
	 * @param obj objeto que encapsula la información de un SkillEvaluationTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSkillEvaluationType(SkillEvaluationTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un SkillEvaluationTypeVO
	 * @param obj objeto que encapsula la información de un SkillEvaluationTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSkillEvaluationType(SkillEvaluationTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un SkillEvaluationTypeVO
	 * @param obj información del SkillEvaluationTypeVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteSkillEvaluationType(SkillEvaluationTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un SkillEvaluationTypeVO por su identificador
	 * @param id identificador del SkillEvaluationTypeVO a ser consultado
	 * @return objeto con la información del SkillEvaluationTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public SkillEvaluationTypeVO getSkillEvaluationTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los SkillEvaluationTypeVO almacenados en la persistencia
	 * @return Lista con los SkillEvaluationTypeVO existentes, una lista vacia en caso que no existan SkillEvaluationTypeVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<SkillEvaluationTypeVO> getAllSkillEvaluationTypes() throws BusinessException;

}
