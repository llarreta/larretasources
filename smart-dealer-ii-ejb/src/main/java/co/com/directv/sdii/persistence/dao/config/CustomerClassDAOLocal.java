package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.CustomerClass;
import co.com.directv.sdii.model.vo.CustomerClassVO;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad CustomerClass
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CustomerClassDAOLocal {
	
	/**
     * Crea un CustomerClass en el sistema
     * @param obj - CustomerClass
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createCustomerClass(CustomerClass obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un customerclass con el id especificado
     * @param id - Long
     * @return - CustomerClass
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public CustomerClass getCustomerClassByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un customerclass especificado
     * @param obj - CustomerClass
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateCustomerClass(CustomerClass obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un customerclass del sistema
     * @param obj - CustomerClass
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteCustomerClass(CustomerClass obj) throws DAOServiceException, DAOSQLException;
    
    
    public List<CustomerClass> getAll(Long countryId) throws DAOServiceException, DAOSQLException;
    
        
    /**
     * Metodo: permite consultar los CustomerClass filtrando por customerTypeCode
     * @param customerTypeCode
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */

	public CustomerClass getCustomerClassByCode(String code, Long countryId) throws DAOServiceException, DAOSQLException;

	List<CustomerClassVO> getCustomerClassByCategoryId(Long categoryId, Long countryId)
			throws DAOServiceException, DAOSQLException;

}
