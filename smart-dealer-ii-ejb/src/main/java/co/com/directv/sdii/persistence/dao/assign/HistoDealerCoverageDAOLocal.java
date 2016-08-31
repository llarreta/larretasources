package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.HistoDealerCoverage;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad HistoDealerCoverage
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface HistoDealerCoverageDAOLocal {

	/**
	 * Metodo:  persiste la información de un HistoDealerCoverage
	 * @param obj objeto que encapsula la información de un HistoDealerCoverage
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createHistoDealerCoverage(HistoDealerCoverage obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un HistoDealerCoverage
	 * @param obj objeto que encapsula la información de un HistoDealerCoverage
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateHistoDealerCoverage(HistoDealerCoverage obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un HistoDealerCoverage
	 * @param obj información del HistoDealerCoverage a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteHistoDealerCoverage(HistoDealerCoverage obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un HistoDealerCoverage por su identificador
	 * @param id identificador del HistoDealerCoverage a ser consultado
	 * @return objeto con la información del HistoDealerCoverage dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public HistoDealerCoverage getHistoDealerCoverageByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los HistoDealerCoverage almacenados en la persistencia
	 * @return Lista con los HistoDealerCoverage existentes, una lista vacia en caso que no existan HistoDealerCoverage en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<HistoDealerCoverage> getAllHistoDealerCoverages() throws DAOServiceException, DAOSQLException;


}