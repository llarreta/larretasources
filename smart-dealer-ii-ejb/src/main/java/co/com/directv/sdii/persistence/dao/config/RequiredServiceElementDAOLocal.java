package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.RequiredServiceElement;
import co.com.directv.sdii.model.pojo.RequiredServiceElementId;
import co.com.directv.sdii.model.pojo.Service;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad RequiredServiceElement
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface RequiredServiceElementDAOLocal {
	
    /**
     * Crea REQUIRED_SERVICE_ELEMENT
     * @param obj - RequiredServiceElement
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public void createRequiredServiceElement(RequiredServiceElement obj) throws DAOServiceException, DAOSQLException;

    /**
     * Consulta REQUIRED_SERVICE_ELEMENT por REQUIRED_SERVICE_ELEMENT_ID
     * @param id - RequiredServiceElementId
     * @return RequiredServiceElement
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public RequiredServiceElement getRequiredServiceElementByID(RequiredServiceElementId id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza REQUIRED_SERVICE_ELEMENT
     * @param obj -RequiredServiceElement
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public void updateRequiredServiceElement(RequiredServiceElement obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina REQUIRED_SERVICE_ELEMENT
     * @param obj - RequiredServiceElement
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public void deleteRequiredServiceElement(RequiredServiceElement obj) throws DAOServiceException, DAOSQLException;

    /**
     * Consulta todos los REQUIRED_SERVICE_ELEMENT
     * @return - List<RequiredServiceElement>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public List<RequiredServiceElement> getAll() throws DAOServiceException, DAOSQLException;

    /**
     * Consulta los REQUIRED_SERVICE_ELEMENT por ELEMENT_TYPE
     * @param idElementType - Long
     * @return List<RequiredServiceElement>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public List<RequiredServiceElement> getRequiredServiceElementByElementType(Long idElementType) throws DAOServiceException, DAOSQLException;

    /**
     * Consulta REQUIRED_SERVICE_ELEMENT por ELEMENT_TYPE y SERVICE
     * @param idElementType - Long
     * @param idService - Long
     * @return RequiredServiceElement
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public RequiredServiceElement getRequiredServiceElementByServiceElementType(Long idElementType, Long idService) throws DAOServiceException, DAOSQLException;

    /**
     * Consulta los REQUIRED_SERVICE_ELEMENT asociado a SERVICE
     * @param idService - Long
     * @return List<RequiredServiceElement>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public List<RequiredServiceElement> getRequiredServiceElementByService(Long idService) throws DAOServiceException, DAOSQLException;

    
	/**
	 * Metodo: Obtiene todoos los elementos relacionados con una lista de servicios
	 * @param services Lista de servicios
	 * @throws DAOServiceException En caso de error al ejecutar la operación
	 * @throws DAOSQLException En caso de error al ejecutar la operación
	 * @author jjimenezh 2010-05-20
	 */
	public List<RequiredServiceElement> getRequiredServiceElementByServices(List<Service> services)throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Retorna los elementos serializados
	 * requeridos por el servicio
	 * @param idService
	 * @return List<RequiredServiceElement>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<RequiredServiceElement> getRequiredServiceElementsSerialized(Long idService) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Retorna los elementos no serializados
	 * requeridos para el servicio
	 * @param idService
	 * @return List<RequiredServiceElement>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<RequiredServiceElement> getRequiredServiceElementsNotSerialized(Long idService) throws DAOServiceException, DAOSQLException;
}
