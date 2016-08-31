package co.com.directv.sdii.persistence.dao.bb;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.DeviceModel;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad DeviceModel
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DeviceModelDAOLocal {

	/**
	 * Metodo:  persiste la información de un DeviceModel
	 * @param obj objeto que encapsula la información de un DeviceModel
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDeviceModel(DeviceModel obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un DeviceModel
	 * @param obj objeto que encapsula la información de un DeviceModel
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDeviceModel(DeviceModel obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DeviceModel
	 * @param obj información del DeviceModel a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDeviceModel(DeviceModel obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un DeviceModel por su identificador
	 * @param id identificador del DeviceModel a ser consultado
	 * @return objeto con la información del DeviceModel dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DeviceModel getDeviceModelByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los DeviceModel almacenados en la persistencia
	 * @return Lista con los DeviceModel existentes, una lista vacia en caso que no existan DeviceModel en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DeviceModel> getAllDeviceModels() throws DAOServiceException, DAOSQLException;


}