package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Eps;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad Eps
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EpsDAOLocal {

	
	public Eps getEpsByCode(String code) throws DAOServiceException, DAOSQLException;
	
	public Eps getEpsByID(Long id) throws DAOServiceException, DAOSQLException;
	
	public List<Eps> getAllEps() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Obtiene una lista de las eps ragionalizadas.
	 * @param countryId identificador del país por el que se hará el filtrado
	 * @return Lista de las eps regionalizadas
	 * @throws BusinessException en caso de error al tratar de consultar la lista
	 * @author jjimenezh agregado por control de cambios 2010-04-23
	 */
	public List<Eps> getAllEpsByCountryId(Long countryId) throws DAOServiceException, DAOSQLException;

}