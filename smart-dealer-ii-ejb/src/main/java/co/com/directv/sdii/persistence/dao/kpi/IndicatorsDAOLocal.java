package co.com.directv.sdii.persistence.dao.kpi;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Indicators;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad Indicators
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface IndicatorsDAOLocal {

	/**
	 * Metodo:  persiste la información de un Indicators
	 * @param obj objeto que encapsula la información de un Indicators
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createIndicators(Indicators obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un Indicators
	 * @param obj objeto que encapsula la información de un Indicators
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateIndicators(Indicators obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un Indicators
	 * @param obj información del Indicators a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteIndicators(Indicators obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un Indicators por su identificador
	 * @param id identificador del Indicators a ser consultado
	 * @return objeto con la información del Indicators dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public Indicators getIndicatorsByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los Indicators almacenados en la persistencia
	 * @return Lista con los Indicators existentes, una lista vacia en caso que no existan Indicators en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<Indicators> getAllIndicatorss() throws DAOServiceException, DAOSQLException;


}