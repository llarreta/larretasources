package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.SpecialComment;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SpecialCommentResponse;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad SpecialComment
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SpecialCommentDAOLocal {

	/**
	 * Metodo:  persiste la información de un SpecialComment
	 * @param obj objeto que encapsula la información de un SpecialComment
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSpecialComment(SpecialComment obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un SpecialComment
	 * @param obj objeto que encapsula la información de un SpecialComment
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSpecialComment(SpecialComment obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un SpecialComment
	 * @param obj información del SpecialComment a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteSpecialComment(SpecialComment obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un SpecialComment por su identificador
	 * @param id identificador del SpecialComment a ser consultado
	 * @return objeto con la información del SpecialComment dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public SpecialComment getSpecialCommentByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los SpecialComment almacenados en la persistencia
	 * @return Lista con los SpecialComment existentes, una lista vacia en caso que no existan SpecialComment en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<SpecialComment> getAllSpecialComments() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la informacion de los special comments asociados a una remision
	 * @param referenceId Long ID de la referencia
	 * @return List<SpecialComment> lista de comentarios asociados a la remision
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta
	 * @author jnova
	 */
	public List<SpecialComment> getSpecialCommentsByReferenceId(Long referenceId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la informacion de los special comments asociados a una remision
	 * @param referenceId Long ID de la referencia
	 * @param requestCollInfo - RequestCollectionInfo 
	 * @return SpecialCommentResponse lista de comentarios asociados a la remision
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta
	 * @author gfandino
	 */
	public SpecialCommentResponse getSpecialCommentsByReferenceId(Long referenceId,RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException;
}