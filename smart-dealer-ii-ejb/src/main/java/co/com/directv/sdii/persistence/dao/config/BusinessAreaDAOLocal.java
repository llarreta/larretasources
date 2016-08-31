package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.BusinessArea;
import co.com.directv.sdii.model.vo.BusinessAreaVO;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad BusinessArea
 * 
 * Fecha de Creacion: Jue 12, 2013
 * @author ialessan
 * @version 1.0
 * 
 * @see
 */
@Local
public interface BusinessAreaDAOLocal {
	
	/**
     * Obtiene todas las BusinessArea en el sistema
     * @param obj - BusinessArea
     * @throws DAOServiceException
     * @throws DAOSQLException
     */	
	public List<BusinessArea> getAllBusinessAreas(Long countryId) throws DAOServiceException, DAOSQLException ;

	
	/**
     * Obtiene un BusinessArea segun el código
     * @param obj - BusinessArea
     * @throws DAOServiceException
     * @throws DAOSQLException
     * 
     * release_4.2.1.5_Spira_28780 - Configuracion Masiva de Cobertura  de  La  compañía  por  Clase de Cliente
     */	
	public BusinessAreaVO getBusinessAreaByCode(String businessAreaCode) throws DAOServiceException, DAOSQLException ;
	
	/**
     * Obtiene el business area dado el service code.
     * @param serviceCode - String
     * @throws DAOServiceException
     * @throws DAOSQLException
     */	
	public BusinessArea getBusinessAreaByServiceCode(String serviceCode) throws DAOServiceException, DAOSQLException;
	
	/**
     * Crea un BusinessArea en el sistema
     * @param obj - BusinessArea
     * @throws DAOServiceException
     * @throws DAOSQLException
     */	
    //public void save(BusinessArea businessArea) throws DAOServiceException,DAOSQLException ;
    
    
    /**
     * Elimina un BusinessArea del sistema
     * @param obj - BusinessArea
     * @throws DAOServiceException
     * @throws DAOSQLException
     */    
    //public void delete(BusinessArea businessArea) throws DAOServiceException,DAOSQLException;
    
    
    /**
     * Obtiene un BusinessArea con el id especificado
     * @param id - Long
     * @return - BusinessArea
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    //public BusinessArea findById(Long id) throws DAOServiceException, DAOSQLException;
    

    /**
     * Obtiene una lista de objetos BusinessArea con el Example especificado
     * @param businessArea - Long
     * @return - BusinessArea
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    //public List<BusinessArea> findByExample(BusinessArea businessArea) throws DAOServiceException, DAOSQLException;
       

    /**
     * Obtiene una lista de objetos BusinessArea con la Property especificada
     * @param propertyName - String
     * @param value - Object
     * @return - BusinessArea
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    //public List<BusinessArea> findByProperty(String propertyName, Object value) throws DAOServiceException, DAOSQLException;
    
    
	
 

	
	//public List<BusinessArea> findByBusinessAreaCode(Object businessAreaCode	) throws DAOServiceException, DAOSQLException ;
	//public List<BusinessArea> findByUserIdLastChange(Object userIdLastChange	) throws DAOServiceException, DAOSQLException ;
	//public List<BusinessArea> findByStatus(Object status) throws DAOServiceException, DAOSQLException ;
	//public List<BusinessArea> findAll() throws DAOServiceException, DAOSQLException ;
    //public BusinessArea merge(BusinessArea detachedInstance) throws DAOServiceException, DAOSQLException ;
}
