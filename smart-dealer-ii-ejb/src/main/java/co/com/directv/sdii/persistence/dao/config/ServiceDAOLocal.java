package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.ServiceStatus;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad Service
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ServiceDAOLocal {
	
	 /**
     * Crea un Service en el sistema
     * @param obj - Service
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createService(Service obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un service con el id especificado
     * @param id - Long
     * @return - Service
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public Service getServiceByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un service especificado
     * @param obj - Service
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateService(Service obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un service del sistema
     * @param obj - Service
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteService(Service obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los services del sistema
     * @return - List<Service>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<Service> getAll() throws DAOServiceException, DAOSQLException;
    
    /**
     * @param serviceCodeOpening
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<Service> getAllByServiceCodeOpening(boolean serviceCodeOpening) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un listado de services con el estado especificado
     * @param id - Long
     * @return - Service
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<Service> getServiceByState(ServiceStatus status) throws DAOServiceException, DAOSQLException;
    
    
    /**
     * Método: Obtiene los servicios asociados a la categoría
     * @param categoriesId - Lista de categorías
     * @param status - estado 
     * @return List<Service> asociados a la categoría en el estado indicado
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public List<Service> getActiveServicesByServiceCategories(List<Long> categoriesId,ServiceStatus status)throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un service con el codigo especificado
     * @param id - Long
     * @return - Service
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public Service getServiceByCode(String code) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un listado de services con el estado especificado
     * @param id - Long
     * @return - Service
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<Service> getServiceByName(String name) throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Obtiene los servicios a partir de un tipo de WO
     * @param woTypeId Long identificador del tipo de WO
     * @return List<Service> Lista de servicios que por tipo estan asociados al tipo enviado por parametro
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jnova
     */
    public List<Service> getServicesByWoTypeId(Long woTypeId) throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: A partir de una lista de codigos de servicios calcula cuantos de estos se encusntran en la supercategoria
	 * enviada por parametro
	 * @param servicesCodes lista de codigo de servicios
	 * @param superCatCode codigo de super categoria
	 * @return Cantidad de servicios que se encuentran en la super categoria
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
    public Long calculateWoServicesInSuperCategory(List<String> servicesCodes, String superCatCode) throws DAOServiceException, DAOSQLException;

    /**
     * Metodo: Obtiene un service con el codigo especificado
     * @param id - Long
     * @return - Service
     * @author jalopez
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public Service getServiceBySimpleCode(String code) throws DAOServiceException, DAOSQLException;
}
