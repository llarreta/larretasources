package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.VehicleType;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad VehicleTypes
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface VehicleTypesDAOLocal {

	
	public VehicleType getVehicleTypesByCode(String code) throws DAOServiceException, DAOSQLException;
	
	public VehicleType getVehicleTypesByID(Long id) throws DAOServiceException, DAOSQLException;
	
	public List<VehicleType> getAllVehicleTypes() throws DAOServiceException, DAOSQLException;

}