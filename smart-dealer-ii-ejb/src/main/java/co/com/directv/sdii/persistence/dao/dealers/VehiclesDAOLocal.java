package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Vehicle;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad Vehicle
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface VehiclesDAOLocal {

    public void createVehicle(Vehicle obj)
            throws DAOServiceException, DAOSQLException;

    public Vehicle getVehicleByID(Long id)
            throws DAOServiceException, DAOSQLException;

    public void updateVehicle(Vehicle obj)
            throws DAOServiceException, DAOSQLException;

    public void deleteVehicle(Vehicle obj)
            throws DAOServiceException, DAOSQLException;

    public List<Vehicle> getAllVehicle()
            throws DAOServiceException, DAOSQLException;

    public Vehicle getVehicleByPlate(String plate) throws DAOServiceException, DAOSQLException;

    public List<String> getAllVehiclePlates() throws DAOServiceException, DAOSQLException ;

    public List<Vehicle> getVehiclesByDealerCodeOrDepotCode(long dealerCode, String depotCode) throws DAOServiceException, DAOSQLException;

	public List<Vehicle> getAllVehiclesOnlyBasicInfo()throws DAOServiceException, DAOSQLException;

	public List<Vehicle> getVehiclesByDealerId(long dealerId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta los vehiculos asociados a un dealer y en un estado especifico
	 * @param dealerId id del dealer
	 * @param statusCode codigo del estado del vehiculo por el cual se desea filtrar
	 * @return Lista de vehiculos asociados al dealer y en el estado enviado por parametro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Vehicle> getVehiclesByDealerIdAndStatusCode(long dealerId,String statusCode)throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta los vehiculos por placa y estado
	 * @param plate
	 * @param statusCode
	 * @return List<Vehicle>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Vehicle> getVehicleByPlateAndStatus(String plate,String statusCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: consulta los vehiculos por placa y por dealer
	 * @param dealerId
	 * @param plate
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Vehicle> getVehiclesByDealerIdAndPlate(Long dealerId,String plate) throws DAOServiceException, DAOSQLException;
	
	
}
