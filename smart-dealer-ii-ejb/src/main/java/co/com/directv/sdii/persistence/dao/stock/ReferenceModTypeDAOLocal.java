package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ReferenceModType;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ReferenceModType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ReferenceModTypeDAOLocal {

	/**
	 * Metodo:  persiste la información de un ReferenceModType
	 * @param obj objeto que encapsula la información de un ReferenceModType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createReferenceModType(ReferenceModType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ReferenceModType
	 * @param obj objeto que encapsula la información de un ReferenceModType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateReferenceModType(ReferenceModType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ReferenceModType
	 * @param obj información del ReferenceModType a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteReferenceModType(ReferenceModType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ReferenceModType por su identificador
	 * @param id identificador del ReferenceModType a ser consultado
	 * @return objeto con la información del ReferenceModType dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ReferenceModType getReferenceModTypeByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ReferenceModType almacenados en la persistencia
	 * @return Lista con los ReferenceModType existentes, una lista vacia en caso que no existan ReferenceModType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ReferenceModType> getAllReferenceModTypes() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene un tipo de modificación de remisión
	 * @param refModTypeCode código de tipo de modificación de remisión
	 * @return Tipo de modificación de remisión dado el código
	 * @author jjimenezh
	 */
	public ReferenceModType getReferenceModTypeByCode(String refModTypeCode)throws DAOServiceException, DAOSQLException;


}