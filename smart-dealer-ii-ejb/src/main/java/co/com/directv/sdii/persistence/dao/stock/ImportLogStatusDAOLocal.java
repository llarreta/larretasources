package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ImportLogStatus;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ImportLogStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ImportLogStatusDAOLocal {

	/**
	 * Metodo:  persiste la información de un ImportLogStatus
	 * @param obj objeto que encapsula la información de un ImportLogStatus
	 * @throws DAOServiceException en caso de error al ejecutar la creación de ImportLogStatus
	 * @throws DAOSQLException en caso de error al ejecutar la creación de ImportLogStatus
	 * @author gfandino
	 */
	public void createImportLogStatus(ImportLogStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ImportLogStatus
	 * @param obj objeto que encapsula la información de un ImportLogStatus
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de ImportLogStatus
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de ImportLogStatus
	 * @author gfandino
	 */
	public void updateImportLogStatus(ImportLogStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ImportLogStatus
	 * @param obj información del ImportLogStatus a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de ImportLogStatus
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de ImportLogStatus
	 * @author gfandino
	 */ 
	public void deleteImportLogStatus(ImportLogStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ImportLogStatus por su identificador
	 * @param id identificador del ImportLogStatus a ser consultado
	 * @return objeto con la información del ImportLogStatus dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ImportLogStatus por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ImportLogStatus por ID
	 * @author gfandino
	 */
	public ImportLogStatus getImportLogStatusByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ImportLogStatus almacenados en la persistencia
	 * @return Lista con los ImportLogStatus existentes, una lista vacia en caso que no existan ImportLogStatus en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los ImportLogStatus
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los ImportLogStatus
	 * @author gfandino
	 */
	public List<ImportLogStatus> getAllImportLogStatuss() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de un ImportLogStatus por su código
	 * @param code - String Código del ImportLogStatus a ser consultado
	 * @return ImportLogStatus objeto con la información del ImportLogStatus dado su código, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ImportLogStatus por código
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ImportLogStatus por código
	 * @author gfandino
	 */
	public ImportLogStatus getImportLogStatusByCode(String code)throws DAOServiceException, DAOSQLException;


}