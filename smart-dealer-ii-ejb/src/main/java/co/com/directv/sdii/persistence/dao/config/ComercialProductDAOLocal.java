package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ComercialProduct;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad ComercialProduct
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ComercialProductDAOLocal {
	
	 /**
     * Crea una compañia en el sistema
     * @param obj - ComercialProduct
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createComercialProduct(ComercialProduct obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un comercialproduct con el id especificado
     * @param id - Long
     * @return - ComercialProduct
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public ComercialProduct getComercialProductByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un comercialproduct con el código especificado
     * @param id - String
     * @return - ComercialProduct
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public ComercialProduct getComercialProductByCode(String code) throws DAOServiceException, DAOSQLException;
    
    /**
     * Actualiza un comercialproduct especificado
     * @param obj - ComercialProduct
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateComercialProduct(ComercialProduct obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un comercialproduct del sistema
     * @param obj - ComercialProduct
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteComercialProduct(ComercialProduct obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los comercialproducts del sistema
     * @return - List<ComercialProduct>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<ComercialProduct> getAll() throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene una lista de los productos comerciales dado el identificador del país
     * @param countryId identificador del país por el que se realizará el filtro
     * @return lista de productos comerciales dado el identificador de país
     * @throws DAOServiceException en caso de error al tratar de consultar por el identificador del país
     * @throws DAOSQLException en caso de error al tratar de consultar por el identificador del país
     */
    public List<ComercialProduct> getAllComercialProductsByCountryId(Long countryId)throws DAOServiceException, DAOSQLException;


}
