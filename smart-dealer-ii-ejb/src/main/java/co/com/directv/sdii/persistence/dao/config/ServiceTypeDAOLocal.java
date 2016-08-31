package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ServiceType;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad ServiceType
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ServiceTypeDAOLocal {
	
	/**
     * Crea una ServiceType en el sistema
     * @param obj - ServiceType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createServiceType(ServiceType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un servicetype con el id especificado
     * @param id - Long
     * @return - ServiceType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public ServiceType getServiceTypeByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un servicetype especificado
     * @param obj - ServiceType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateServiceType(ServiceType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un servicetype del sistema
     * @param obj - ServiceType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteServiceType(ServiceType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los servicetypes del sistema
     * @return - List<ServiceType>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<ServiceType> getAll() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene el tipo de servicio dado el código de servicio
	 * @param serviceCode código del servicio
	 * @return información del tipo de servicio
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 * @author jjimenezh
	 */
	public ServiceType getServiceTypeByServiceCode(String serviceCode)throws DAOServiceException, DAOSQLException;

	
	/**
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * Metodo: Obtiene el tipo de servicio dado el Area de Negocio
	 * @param businessAreaId
	 * @return información del tipos de servicio
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 * @author jjimenezh
	 */
	public  List<ServiceType> getServiceTypeByBusinessArea(Long businessAreaId)throws DAOServiceException, DAOSQLException;
	
}
