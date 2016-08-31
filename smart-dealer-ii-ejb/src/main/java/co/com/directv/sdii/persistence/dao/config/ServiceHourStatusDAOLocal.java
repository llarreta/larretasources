package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ServiceHourStatus;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad ServiceHourStatus
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ServiceHourStatusDAOLocal {
	
	 /**
     * Crea un ServiceHourStatus en el sistema
     * @param obj - ServiceHourStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createServiceHourStatus(ServiceHourStatus obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un servicehourstatus con el id especificado
     * @param id - Long
     * @return - ServiceHourStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public ServiceHourStatus getServiceHourStatusByID(Long id) throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene un servicehourstatus con el id especificado
     * @param code - Code
     * @return - ServiceHourStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public ServiceHourStatus getServiceHourStatusByCode(String code) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un servicehourstatus especificado
     * @param obj - ServiceHourStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateServiceHourStatus(ServiceHourStatus obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un servicehourstatus del sistema
     * @param obj - ServiceHourStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteServiceHourStatus(ServiceHourStatus obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los servicehourstatuss del sistema
     * @return - List<ServiceHourStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<ServiceHourStatus> getAll() throws DAOServiceException, DAOSQLException;

}
