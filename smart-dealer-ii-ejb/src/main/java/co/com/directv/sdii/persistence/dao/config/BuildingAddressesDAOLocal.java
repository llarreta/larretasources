package co.com.directv.sdii.persistence.dao.config;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.BuildingAddresses;


/**
 * Interface Local para la gestion del CRUD de la
 * Entidad Building
 * 
 * Fecha de Creacion: Nov 14, 2013
 * @author ssanabri <a href="mailto:facundo.sanabria@everis.com">e-mail</a>
 * @version 4.2.0
 * 
 * @see
 */
@Local
public interface BuildingAddressesDAOLocal {

	/**
     * Crea un Building en el sistema
     * @param obj - Building
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
	public void createBuildingAddresses(BuildingAddresses obj) throws DAOServiceException, DAOSQLException;
	
    /**
     * Actualiza un buildingAddresses especificado
     * @param obj - BuildingAddresses
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
	public void updateBuildingAddresses(BuildingAddresses obj) throws DAOServiceException, DAOSQLException;
	
    /**
     * Elimina todas las BuildingAddresses de un building.
     * @param buildingId - identificacion del building
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
	public void deleteBuildingAddressesByBuildingId(Long buildingId) throws DAOServiceException, DAOSQLException;
	
}
