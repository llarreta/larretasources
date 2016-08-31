package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.CustomerMediaContact;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad CustomerMediaContact
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CustomerMediaContactDAOLocal {
	
	/**
     * Crea una cCustomerMediaContact en el sistema
     * @param obj - CustomerMediaContact
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createCustomerMediaContact(CustomerMediaContact obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un customermediacontact con el id especificado
     * @param id - Long
     * @return - CustomerMediaContact
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public CustomerMediaContact getCustomerMediaContactByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un customermediacontact especificado
     * @param obj - CustomerMediaContact
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateCustomerMediaContact(CustomerMediaContact obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un customermediacontact del sistema
     * @param obj - CustomerMediaContact
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteCustomerMediaContact(CustomerMediaContact obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los customermediacontacts del sistema
     * @return - List<CustomerMediaContact>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<CustomerMediaContact> getAll() throws DAOServiceException, DAOSQLException;

    /**
     * Metodo: Borra los medios de contacto de un cliente dado el identificador del cliente
     * @param customerId identificador del cliente que se va a borrar
     * @throws DAOServiceException en caso de error al tratar de borrar los medios de contacto
     * @throws DAOSQLException En caso de error al tratar de borrar
     * @author jjimenezh
     */
	public void deleteCustomerMediaContactByCustomerId(Long customerId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene los media contacts asociados a un customer
	 * @param customerId Identificador del customer del cual se desea obtener los media contacts
	 * @return List<CustomerMediaContact> Lista de media contacts
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<CustomerMediaContact> getCustomerMediaContactByCustomerId(Long customerId) throws DAOServiceException, DAOSQLException; 
	
	/**
	 * 
	 * Metodo: Obtiene los media contacts asociados a un customer
	 * @param customerCode Codigo del customer del cual se desea obtener los media contacts
	 * @return List<CustomerMediaContact> Lista de media contacts
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<CustomerMediaContact> getCustomerMediaContactByCustomerCode(String customerCode) throws DAOServiceException, DAOSQLException;

}
