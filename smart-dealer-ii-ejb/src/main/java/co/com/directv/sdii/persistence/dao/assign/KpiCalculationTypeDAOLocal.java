package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.KpiCalculationType;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad KpiCalculationType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface KpiCalculationTypeDAOLocal {

	/**
	 * Metodo:  persiste la información de un KpiCalculationType
	 * @param obj objeto que encapsula la información de un KpiCalculationType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createKpiCalculationType(KpiCalculationType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un KpiCalculationType
	 * @param obj objeto que encapsula la información de un KpiCalculationType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateKpiCalculationType(KpiCalculationType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un KpiCalculationType
	 * @param obj información del KpiCalculationType a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteKpiCalculationType(KpiCalculationType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un KpiCalculationType por su identificador
	 * @param id identificador del KpiCalculationType a ser consultado
	 * @return objeto con la información del KpiCalculationType dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public KpiCalculationType getKpiCalculationTypeByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los KpiCalculationType almacenados en la persistencia
	 * @return Lista con los KpiCalculationType existentes, una lista vacia en caso que no existan KpiCalculationType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<KpiCalculationType> getAllKpiCalculationTypes() throws DAOServiceException, DAOSQLException;


}