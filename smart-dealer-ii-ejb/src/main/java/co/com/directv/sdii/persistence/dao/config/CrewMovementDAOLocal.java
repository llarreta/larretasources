package co.com.directv.sdii.persistence.dao.config;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.CrewMovement;

@Local
public interface CrewMovementDAOLocal {

	
    /**
     * Permite crear un movimento de cuadrilla
     * @param obj - WorkOrder
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createCrewMovement(CrewMovement obj) throws DAOServiceException, DAOSQLException;

}