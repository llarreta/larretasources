package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SpecialCommentResponse;
import co.com.directv.sdii.model.vo.SpecialCommentVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad SpecialComment.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SpecialCommentFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto SpecialComment
	 * @param obj - SpecialCommentVO  objeto que encapsula la información de un SpecialCommentVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSpecialComment(SpecialCommentVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un SpecialComment
	 * @param obj - SpecialCommentVO  objeto que encapsula la información de un SpecialCommentVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSpecialComment(SpecialCommentVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un SpecialComment
	 * @param obj - SpecialCommentVO  información del SpecialCommentVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteSpecialComment(SpecialCommentVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un SpecialComment por su identificador
	 * @param id - Long identificador del SpecialComment a ser consultado
	 * @return objeto con la información del SpecialCommentVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public SpecialCommentVO getSpecialCommentByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los SpecialComment almacenados en la persistencia
	 * @return List<SpecialCommentVO> Lista con los SpecialCommentVO existentes, una lista vacia en caso que no existan SpecialCommentVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<SpecialCommentVO> getAllSpecialComments() throws BusinessException;
	
	/**
	 * Metodo: Permite almacenar una coleccion de special comments asociados a una remision
	 * @param referenceId Long ID de la remision
	 * @param specialCommentsList List<SpecialCommentVO> lista de special comments
	 * @throws BusinessException En caso de error al tratar de almacenar la informacion
	 */
	public void createSpecialComments(Long referenceId , List<SpecialCommentVO> specialCommentsList) throws BusinessException;
	
	/**
	 * Metodo: Permite obtener los special comments asociados a una remision para CU 30
	 * @param referenceId Long ID de la remision
	 * @param requestCollInfo RequestCollectionInfo datos para la paginación
	 * @return SpecialCommentResponse Lista de special comments paginada para una remisión  
	 * @throws BusinessException en caso de error en la consulta de los comentarios especiales por remisión
	 * @author gfandino
	 */
	public SpecialCommentResponse getSpecialCommentsByReferenceId(Long referenceId, RequestCollectionInfo requestCollInfo) throws BusinessException;

}
