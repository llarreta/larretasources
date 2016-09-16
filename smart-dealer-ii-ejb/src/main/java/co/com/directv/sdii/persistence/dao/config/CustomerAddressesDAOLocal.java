package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.CustomerAddresses;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad Customer
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CustomerAddressesDAOLocal {
	
	/**
     * Crea un Customer en el sistema
     * @param obj - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createCustomerAddresses(CustomerAddresses obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un customer con el id especificado
     * @param id - Long
     * @return - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public CustomerAddresses getCustomerAddressesByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un customer especificado
     * @param obj - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateCustomerAddresses(CustomerAddresses obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina una direccion de cliente del sistema
     * @param obj - CustomerAddresses
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author aharker
     */
    public void deleteCustomerAddresses(CustomerAddresses obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todas las direcciones de clientes del sistema
     * @return - List<Customer>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author aharker
     */
    public List<CustomerAddresses> getAll() throws DAOServiceException, DAOSQLException;

    /**
     * 
     * @param customerId
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
	public void deleteCustomerAddressesByCustomerId(Long customerId)throws DAOServiceException, DAOSQLException;
    
}
