package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ElementStatus;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ElementStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ElementStatusDAOLocal {

	/**
	 * Metodo:  persiste la información de un ElementStatus
	 * @param obj objeto que encapsula la información de un ElementStatus
	 * @throws DAOServiceException en caso de error al ejecutar la creación de ElementStatus
	 * @throws DAOSQLException en caso de error al ejecutar la creación de ElementStatus
	 * @author gfandino
	 */
	public void createElementStatus(ElementStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ElementStatus
	 * @param obj objeto que encapsula la información de un ElementStatus
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de ElementStatus
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de ElementStatus
	 * @author gfandino
	 */
	public void updateElementStatus(ElementStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ElementStatus
	 * @param obj información del ElementStatus a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de ElementStatus
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de ElementStatus
	 * @author gfandino
	 */
	public void deleteElementStatus(ElementStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ElementStatus por su identificador
	 * @param id identificador del ElementStatus a ser consultado
	 * @return objeto con la información del ElementStatus dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementStatus por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementStatus por ID
	 * @author gfandino
	 */
	public ElementStatus getElementStatusByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementStatus almacenados en la persistencia
	 * @return Lista con los ElementStatus existentes, una lista vacia en caso que no existan ElementStatus en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los ElementStatus
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los ElementStatus
	 * @author gfandino
	 */
	public List<ElementStatus> getAllElementStatuss() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los ElementStatus almacenados en la persistencia correspondientes al <br>
	 * código especificado.
	 * @param elementStatusCode - Código del ElementStatus
	 * @return ElementStatus ElementStatus existentes correspondiente al código especificado. Nulo en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementStatus por código
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementStatus por código
	 * @author gfandino
	 */
	public ElementStatus getElementStatusByCode(String elementStatusCode)throws DAOServiceException, DAOSQLException;


}