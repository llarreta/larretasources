package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.CustomerAgreementType;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad CustomerAgreementType
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author waguilera
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CustomerAgreementTypeDAOLocal {
	
	/**
     * Crea un CustomerAgreement en el sistema
     * @param obj - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createCustomerAgreementType(CustomerAgreementType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un CustomerAgreement con el id especificado
     * @param id - Long
     * @return - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public Customer getCustomerAgreementTypeByID(Long id) throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene un CustomerAgreement con el CustomerAgreementCode especificado
     * @param code - String
     * @return - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public Customer getCustomerAgreementTypeByCode(String customerCode, Long countryId) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un CustomerAgreement especificado
     * @param obj - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateCustomerAgreementType(CustomerAgreementType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un CustomerAgreement del sistema
     * @param obj - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteCustomerAgreementType(CustomerAgreementType obj) throws DAOServiceException, DAOSQLException;
    
   
   /**
    * Consulta los CustomerAgreement del sistema por códigos
    * @param customerAgreementTypesCodes
    * @return
    * @throws DAOServiceException
    * @throws DAOSQLException
    * @author waguilera
    */
    public List<CustomerAgreementType> getCustomerAgreementTypes(String customerAgreementTypesCodes) throws DAOServiceException, DAOSQLException;

    
	
}
