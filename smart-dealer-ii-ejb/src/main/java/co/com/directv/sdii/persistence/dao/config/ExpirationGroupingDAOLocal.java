package co.com.directv.sdii.persistence.dao.config;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ExpirationGrouping;

/**
 * 
 * Clase encargada de realizar operacion CRUD sobre la tabla expiration_grouping 
 * 
 * Fecha de Creación: 11/05/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ExpirationGroupingDAOLocal {

	/**
	 * 
	 * Metodo: Consulta todas las fechas por agrupacion por vencimiento
	 * @return List<ExpirationGrouping>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<ExpirationGrouping> getAllExpirationGrouping()  throws DAOServiceException, DAOSQLException;
}
