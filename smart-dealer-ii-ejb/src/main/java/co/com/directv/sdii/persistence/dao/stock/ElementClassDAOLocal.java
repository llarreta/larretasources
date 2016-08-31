package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ElementClass;
import co.com.directv.sdii.model.pojo.collection.ElementClassResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ElementClass
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ElementClassDAOLocal {

	/**
	 * Metodo:  persiste la información de un ElementClass
	 * @param obj objeto que encapsula la información de un ElementClass
	 * @throws DAOServiceException en caso de error al ejecutar la creación de ElementClass
	 * @throws DAOSQLException en caso de error al ejecutar la creación de ElementClass
	 * @author gfandino
	 */
	public void createElementClass(ElementClass obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ElementClass
	 * @param obj objeto que encapsula la información de un ElementClass
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de ElementClass
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de ElementClass
	 * @author gfandino
	 */
	public void updateElementClass(ElementClass obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ElementClass
	 * @param obj información del ElementClass a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de ElementClass
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de ElementClass
	 * @author gfandino
	 */
	public void deleteElementClass(ElementClass obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ElementClass por su identificador
	 * @param id identificador del ElementClass a ser consultado
	 * @return objeto con la información del ElementClass dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementClass por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementClass por ID
	 * @author gfandino
	 */
	public ElementClass getElementClassByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementClass almacenados en la persistencia
	 * @return Lista con los ElementClass existentes, una lista vacia en caso que no existan ElementClass en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los  ElementClass
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los  ElementClass
	 * @author gfandino
	 */
	public List<ElementClass> getAllElementClasss() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los ElementClass almacenados en la persistencia correspondientes al código
	 * @param elementClassCode - String Código del ElementClass
	 * @return ElementClass Correspondiente al código especificado. Nulo en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementClass por código
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementClass por código
	 * @author gfandino
	 */
	public ElementClass getElementClassByCode(String elementClassCode)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de los ElementClass almacenados en la persistencia correspondientes al nombtr
	 * @param elementClassName - String Nombre del ElementClass
	 * @return ElementClass Correspondiente al nombre especificado. Nulo en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementClass por nombre
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementClass por nombre
	 * @author gfandino
	 */
	public ElementClass getElementClassByName(String elementClassName)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de los ElementClass almacenados en la persistencia correspondientes al estado especificado
	 * @param codeEntity - String estado el cual se va a consultar
	 * @return List<ElementClass> Correspondiente al estado especificado. Vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementClass por estado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementClass por estado
	 * @author gfandino
	 */
	public List<ElementClass> getElementClassByStatus(String codeEntity)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de los ElementClass almacenados en la persistencia correspondientes al estado especificado
	 * @param codeEntity - String estado el cual se va a consultar
	 * @return ElementClassResponse con la información de la consulta
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementClass por estado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementClass por estado
	 * @author gfandino
	 */
	public ElementClassResponse getElementClassByStatus(String codeEntity,RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * Método: Obtiene la información de las clases de elemento que se encuentran en cualquier estado
	 * @param code - Código de la clase de elemento
	 * @param requestCollInfo - RequestCollectionInfo con información de la paginación
	 * @return ElementClassResponse con las clases de elemento paginadas
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementClass 
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementClass 
	 */
	public ElementClassResponse getElementClassByAllStatusPage(
			String code, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * Método: Obtiene la información de las clases de elemento de un elemento serializado.
	 * @param serialNumber - Numero serial de un elemento.
	 * @return ElementClass - devuelve la clase de elemento del elemento serializado.
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementClass 
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementClass 
	 */
	public ElementClass getElementClassByElementSerialNumber(String serialNumber) throws DAOSQLException, DAOServiceException; 

}