package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.AddressType;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.CustomerAddresses;
import co.com.directv.sdii.model.vo.CustomerVO;

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
public interface AddressTypeDAOLocal {
	
	/**
     * Crea un Customer en el sistema
     * @param obj - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createAddressType(AddressType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un customer con el id especificado
     * @param id - Long
     * @return - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public AddressType getAddressTypeByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un customer especificado
     * @param obj - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateAddressType(AddressType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un customer del sistema
     * @param obj - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteAddressType(AddressType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los customers del sistema
     * @return - List<Customer>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<AddressType> getAll() throws DAOServiceException, DAOSQLException;
    
	public AddressType getAddressTypeByCode(String code) throws DAOServiceException,DAOSQLException ;

}
