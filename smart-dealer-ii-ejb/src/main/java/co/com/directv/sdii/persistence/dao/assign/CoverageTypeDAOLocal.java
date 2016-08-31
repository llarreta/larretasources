package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.CoverageType;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad CoverageType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CoverageTypeDAOLocal {

	/**
	 * Metodo:  persiste la información de un CoverageType
	 * @param obj objeto que encapsula la información de un CoverageType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createCoverageType(CoverageType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un CoverageType
	 * @param obj objeto que encapsula la información de un CoverageType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateCoverageType(CoverageType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un CoverageType
	 * @param obj información del CoverageType a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteCoverageType(CoverageType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un CoverageType por su identificador
	 * @param id identificador del CoverageType a ser consultado
	 * @return objeto con la información del CoverageType dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public CoverageType getCoverageTypeByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un CoverageType por su código
	 * @param code código del CoverageType a ser consultado
	 * @return objeto con la información del CoverageType dado su codigo, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public CoverageType getCoverageTypeByCode(String code) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los CoverageType almacenados en la persistencia
	 * @return Lista con los CoverageType existentes, una lista vacia en caso que no existan CoverageType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<CoverageType> getAllCoverageTypes() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene directamente el objeto que representa el tipo de covertura permanente
	 * @return CoverageType
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 * @author
	 */
	public CoverageType getCoverageTypePermanent() throws DAOServiceException,
			DAOSQLException, PropertiesException;

	/**
	 * Metodo: Obtiene directamente el objeto que representa el tipo de covertura permanente
	 * @return CoverageType
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 * @author
	 */
	public CoverageType getCoverageTypeOccasional() throws DAOServiceException,
			DAOSQLException, PropertiesException;


}