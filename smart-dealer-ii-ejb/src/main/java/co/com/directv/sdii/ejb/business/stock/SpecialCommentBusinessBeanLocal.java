package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SpecialCommentResponse;
import co.com.directv.sdii.model.vo.SpecialCommentVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad SpecialComment.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SpecialCommentBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto SpecialCommentVO
	 * @param obj objeto que encapsula la información de un SpecialCommentVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSpecialComment(SpecialCommentVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un SpecialCommentVO
	 * @param obj objeto que encapsula la información de un SpecialCommentVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSpecialComment(SpecialCommentVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un SpecialCommentVO
	 * @param obj información del SpecialCommentVO a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteSpecialComment(SpecialCommentVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un SpecialCommentVO por su identificador
	 * @param id identificador del SpecialCommentVO a ser consultado
	 * @return objeto con la información del SpecialCommentVO dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public SpecialCommentVO getSpecialCommentByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los SpecialCommentVO almacenados en la persistencia
	 * @return Lista con los SpecialCommentVO existentes, una lista vacia en caso que no existan SpecialCommentVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
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
	 * Metodo: Permite obtener los special comments asociados a una remision para CU 36
	 * @param referenceId Long ID de la remision
	 * @return List<SpecialCommentVO> Lista de special comments reducida para envio de mensaje menos cargados  
	 * @throws BusinessException
	 */
	public List<SpecialCommentVO> getSpecialCommentsByReferenceId(Long referenceId) throws BusinessException;
	
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
