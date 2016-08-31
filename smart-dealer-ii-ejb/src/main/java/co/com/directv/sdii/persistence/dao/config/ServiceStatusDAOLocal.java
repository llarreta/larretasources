package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ServiceStatus;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad ServiceStatus
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ServiceStatusDAOLocal {
	
	/**
     * Crea una ServiceStatus en el sistema
     * @param obj - ServiceStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createServiceStatus(ServiceStatus obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un servicestatus con el id especificado
     * @param id - Long
     * @return - ServiceStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public ServiceStatus getServiceStatusByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un servicestatus especificado
     * @param obj - ServiceStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateServiceStatus(ServiceStatus obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un servicestatus del sistema
     * @param obj - ServiceStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteServiceStatus(ServiceStatus obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los servicestatuss del sistema
     * @return - List<ServiceStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<ServiceStatus> getAll() throws DAOServiceException, DAOSQLException;


    /**
     * Metodo: Obtiene un estado de servicio por el código
     * @param serviceStatusCode código de estado de servicio
     * @return objeto con el código
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public ServiceStatus getServiceStatusByCode(String serviceStatusCode)throws DAOServiceException, DAOSQLException;

}
