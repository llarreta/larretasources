package co.com.directv.sdii.persistence.dao.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WoPdfElementTypeNotSerialized;

/**
 * Interface Local para la gestion del CRUD de la
 * Entidad <code>WoPdfElementTypeNotSerialized</code>
 * 
 * Fecha de Creacion: Oct 17, 2012
 * @author ssanabri <a href="mailto:ssanabri@everis.com">e-mail</a>
 * @version 1.0
 */
@Local
public interface WoPdfElementTypeNotSerializedDAOLocal {

	/**
	 * Metodo: Busca todos los <code>{@link WoPdfElementTypeNotSerialized}</code> 
	 * dado un <code>countryId</code> 
	 * @param countryId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<WoPdfElementTypeNotSerialized> getWoPdfElementTypeNotSerializedsByCountryId(Long countryId) throws DAOServiceException, DAOSQLException;
	
}
