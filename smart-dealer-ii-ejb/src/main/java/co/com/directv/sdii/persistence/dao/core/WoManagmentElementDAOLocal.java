package co.com.directv.sdii.persistence.dao.core;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WoManagmentElement;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad WoManagmentElement
 * 
 * Fecha de Creación: 24/05/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WoManagmentElementDAOLocal {

	/**
	 * 
	 * Metodo: Crea WoManagmentElement
	 * @param obj WoManagmentElement
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jalopez
	 */
	public void createWoManagmentElement(WoManagmentElement obj) throws DAOServiceException, DAOSQLException;
}
