package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Customer;
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
public interface CustomerDAOLocal {
	
	/**
     * Crea un Customer en el sistema
     * @param obj - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createCustomer(Customer obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un customer con el id especificado
     * @param id - Long
     * @return - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public Customer getCustomerByID(Long id) throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene un customer con el customerCode especificado
     * @param code - String
     * @return - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public Customer getCustomerByCode(String customerCode) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un customer especificado
     * @param obj - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateCustomer(Customer obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un customer del sistema
     * @param obj - Customer
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteCustomer(Customer obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los customers del sistema
     * @return - List<Customer>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<Customer> getAll() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: <Descripcion>
	 * @param workOrderId
	 * @return <tipo> <descripcion>
	 * @author
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	public Customer getCustomerByWoId(Long workOrderId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: <Descripcion>
	 * @param workOrderCode
	 * @return <tipo> <descripcion>
	 * @author
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	public Customer getCustomerByWoCode(String workOrderCode) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: <Descripcion>
	 * @param code
	 * @param countryId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public Customer getCustomerByCodeAndCountryId(String code, Long countryId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: <Descripcion>
	 * @param documentTypeId
	 * @param documentNumber
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public Customer getCustomerByDocTypeIdAndDocNumber(Long documentTypeId, String documentNumber, Long countryId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Permite consultar los customer por documentTypeId, documentNumber, ibsCode y countryId
	 * @param documentTypeId
	 * @param documentNumber
	 * @param ibsCode
	 * @param countryId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	public List<CustomerVO> getCustomerByDocTypeIdDocNumberAndIbsCode(Long documentTypeId,
															  		String documentNumber, 
															  		String ibsCode,
															  		Long countryId) throws DAOServiceException,DAOSQLException;
	
}
