package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.RefIncType;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad RefIncType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface RefIncTypeDAOLocal {

	/**
	 * Metodo:  persiste la información de un RefIncType
	 * @param obj objeto que encapsula la información de un RefIncType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createRefIncType(RefIncType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un RefIncType
	 * @param obj objeto que encapsula la información de un RefIncType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateRefIncType(RefIncType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un RefIncType
	 * @param obj información del RefIncType a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteRefIncType(RefIncType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un RefIncType por su identificador
	 * @param id identificador del RefIncType a ser consultado
	 * @return objeto con la información del RefIncType dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public RefIncType getRefIncTypeByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los RefIncType almacenados en la persistencia
	 * @return Lista con los RefIncType existentes, una lista vacia en caso que no existan RefIncType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<RefIncType> getAllRefIncTypes() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de un RefIncType por su código
	 * @param refIncTypeCode código del RefIncType a ser consultado
	 * @return objeto con la información del RefIncType dado su codigo, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public RefIncType getRefIncTypeByCode(String refIncTypeCode) throws DAOServiceException, DAOSQLException;
	
	public RefIncType getRefIncTypeExceed() throws DAOServiceException, DAOSQLException, PropertiesException;
    public RefIncType getRefIncTypeIncomplete() throws DAOServiceException, DAOSQLException, PropertiesException;
    
}