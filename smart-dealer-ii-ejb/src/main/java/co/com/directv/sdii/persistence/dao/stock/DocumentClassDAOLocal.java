package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.DocumentClass;

/**
 * Interface Local para la gestión del CRUD de la
 * Entidad DocumentClass
 * 
 * Fecha de Creación: 16/11/2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface DocumentClassDAOLocal {

	/**
	 * Metodo:  persiste la información de un DocumentClass
	 * @param obj objeto que encapsula la información de un DocumentClass
	 * @throws DAOServiceException en caso de error al ejecutar la creación de DocumentClass
	 * @throws DAOSQLException en caso de error al ejecutar la creación de DocumentClass
	 * @author cduarte
	 */
	public void createDocumentClass(DocumentClass obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un DocumentClass
	 * @param obj objeto que encapsula la información de un DocumentClass
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de DocumentClass
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de DocumentClass
	 * @author cduarte
	 */
	public void updateDocumentClass(DocumentClass obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DocumentClass
	 * @param obj información del DocumentClass a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de DocumentClass
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de DocumentClass
	 * @author cduarte
	 */
	public void deleteDocumentClass(DocumentClass obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un DocumentClass por su identificador
	 * @param id identificador del DocumentClass a ser consultado
	 * @return objeto con la información del DocumentClass dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de DocumentClass por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de DocumentClass por ID
	 * @author cduarte
	 */
	public DocumentClass getDocumentClassByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los DocumentClass almacenados en la persistencia
	 * @return Lista con los DocumentClass existentes, una lista vacia en caso que no existan DocumentClass en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los DocumentClass
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los DocumentClass
	 * @author cduarte
	 */
	public List<DocumentClass> getAllDocumentClass() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de un DocumentClass dado su código
	 * @param code código por el cual se consultará el tipo de elemento
	 * @return Tipo de elemento dado el código
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de DocumentClass por código
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de DocumentClass por código
	 * @author cduarte
	 */
	public DocumentClass getDocumentClassByCode(String code)throws DAOServiceException, DAOSQLException;
	
}