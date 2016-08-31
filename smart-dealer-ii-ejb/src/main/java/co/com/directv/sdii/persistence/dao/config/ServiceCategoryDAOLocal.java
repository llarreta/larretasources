package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ServiceCategory;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad ServiceCategory
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ServiceCategoryDAOLocal {
	
	/**
     * Crea una ServiceCategory en el sistema
     * @param obj - ServiceCategory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createServiceCategory(ServiceCategory obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un servicecategory con el id especificado
     * @param id - Long
     * @return - ServiceCategory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public ServiceCategory getServiceCategoryByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un servicecategory especificado
     * @param obj - ServiceCategory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateServiceCategory(ServiceCategory obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un servicecategory del sistema
     * @param obj - ServiceCategory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteServiceCategory(ServiceCategory obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los servicecategorys del sistema
     * @return - List<ServiceCategory>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<ServiceCategory> getAll() throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene todos los servicecategorys del sistema pertenecientes a una categoria especifica
     * @return - List<ServiceCategory>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<ServiceCategory> getServiceCategoryByTypeId(Long typeId) throws DAOServiceException, DAOSQLException ;
    
    /**
     * Obtiene todos los ServiceCategory 
     * @param typesId - Long
     * @return List<ServiceCategory> - Lista de ServiceCategoty asociadas a typesId
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public List<ServiceCategory> getServiceCategoryByTypesId(List<Long> typesId)throws DAOServiceException, DAOSQLException ;
   
    /**
     * Obtiene un servicecategory con el código especificado
     * @param serviceCategoryCode
     * @return - ServiceCategory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
	public ServiceCategory getServiceCategoryByCode(String serviceCategoryCode)
			throws DAOServiceException, DAOSQLException;

	 /**
	 * Req-0096 - Requerimiento Consolidado Asignador
     * Obtiene ServiceCategory cuyos ServiceTypes pertenecen a determinada Area de Negocio 
     * @param businessArea - Long
     * @return List<ServiceCategory> - Lista de ServiceCategory 
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author ialessan
     */
    public List<ServiceCategory> getServiceCategoryByServiceTypeBusinessArea(Long businessAreaId)throws DAOServiceException, DAOSQLException ;	
    
	/**
	* Obtiene ServiceCategory dado un service code 
    * @param serviceCode - String
    * @return ServiceCategory
    * @throws DAOServiceException
    * @throws DAOSQLException
    */
    public ServiceCategory getServiceCategoryByServiceCode(String serviceCode) throws DAOServiceException, DAOSQLException;
}
