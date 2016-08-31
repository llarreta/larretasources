package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.VehicleStatus;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad VehicleStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface VehicleStatusDAOLocal {

	
	public VehicleStatus getVehicleStatusByCode(String code) throws DAOServiceException, DAOSQLException;
	
	public VehicleStatus getVehicleStatusByID(Long id) throws DAOServiceException, DAOSQLException;
	
	public List<VehicleStatus> getAllVehicleStatus() throws DAOServiceException, DAOSQLException;

}