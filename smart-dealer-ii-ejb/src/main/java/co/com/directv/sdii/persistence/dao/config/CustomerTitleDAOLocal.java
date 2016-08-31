package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.CustomerTitle;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad CustomerTitle
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CustomerTitleDAOLocal {
	
	/**
     * Crea un CustomerTitle en el sistema
     * @param obj - CustomerTitle
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createCustomerTitle(CustomerTitle obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un customertitle con el id especificado
     * @param id - Long
     * @return - CustomerTitle
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public CustomerTitle getCustomerTitleByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un customertitle especificado
     * @param obj - CustomerTitle
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateCustomerTitle(CustomerTitle obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un customertitle del sistema
     * @param obj - CustomerTitle
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteCustomerTitle(CustomerTitle obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los customertitles del sistema
     * @return - List<CustomerTitle>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<CustomerTitle> getAll() throws DAOServiceException, DAOSQLException;
    
    /**
	 * Metodo: <Descripcion>
	 * @param titleCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author
	 */
	public CustomerTitle getCustomerTitleByCode(String titleCode)throws DAOServiceException, DAOSQLException;


}
