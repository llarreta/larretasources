package co.com.directv.sdii.persistence.dao.bb;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.MobileDevice;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad MobileDevice
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MobileDeviceDAOLocal {

	/**
	 * Metodo:  persiste la información de un MobileDevice
	 * @param obj objeto que encapsula la información de un MobileDevice
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createMobileDevice(MobileDevice obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un MobileDevice
	 * @param obj objeto que encapsula la información de un MobileDevice
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateMobileDevice(MobileDevice obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un MobileDevice
	 * @param obj información del MobileDevice a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteMobileDevice(MobileDevice obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un MobileDevice por su identificador
	 * @param id identificador del MobileDevice a ser consultado
	 * @return objeto con la información del MobileDevice dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public MobileDevice getMobileDeviceByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los MobileDevice almacenados en la persistencia
	 * @return Lista con los MobileDevice existentes, una lista vacia en caso que no existan MobileDevice en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<MobileDevice> getAllMobileDevices() throws DAOServiceException, DAOSQLException;


}