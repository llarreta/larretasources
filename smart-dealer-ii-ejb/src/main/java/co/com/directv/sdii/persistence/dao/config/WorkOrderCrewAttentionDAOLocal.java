package co.com.directv.sdii.persistence.dao.config;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WorkOrderCrewAttention;

@Local
public interface WorkOrderCrewAttentionDAOLocal {

	
    /**
     * Permite crear una ordern de trabajo atendida por una cuadrilla
     * @param obj - WorkOrder
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createWorkOrderCrewAttention(WorkOrderCrewAttention obj) throws DAOServiceException, DAOSQLException;
    
    /**
     * Busca un registro en la tabla WorkOrderCrewAttentions por WoCode 
     * @param woCode - String
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WorkOrderCrewAttention getWorkOrderCrewAttentionByWoCode(String woCode) throws DAOServiceException, DAOSQLException;


}
