package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Building;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad Building
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface BuildingDAOLocal {

	/**
	 * Metodo:  persiste la información de un Building
	 * @param obj objeto que encapsula la información de un Building
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createBuilding(Building obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un Building
	 * @param obj objeto que encapsula la información de un Building
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateBuilding(Building obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un Building
	 * @param obj información del Building a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteBuilding(Building obj) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de un Building por su ibsBuildingCode
	 * @param ibsBuildingCode ibsBuildingCode del Building a ser consultado
	 * @return objeto con la información del Building dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author rdelarosa
	 */
	public Building getBuildingByIBSBuildingCode(Long ibsBuildingCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un Building por su identificador
	 * @param id identificador del Building a ser consultado
	 * @return objeto con la información del Building dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public Building getBuildingByID(Long id) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de todos los Building almacenados en la persistencia
	 * @return Lista con los Building existentes, una lista vacia en caso que no existan Building en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<Building> getAllBuildings() throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene la informacion de un building por su codigo y codigo de pais
	 * @param ibsBuildingCode codigo de edificio
	 * @param countryCode codigo de pais
	 * @return Building edificio que cumple con el filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public Building getBuildingByIbsBuldingCodeAndCountryCode(Long ibsBuildingCode,String countryCode) throws DAOServiceException, DAOSQLException;

}