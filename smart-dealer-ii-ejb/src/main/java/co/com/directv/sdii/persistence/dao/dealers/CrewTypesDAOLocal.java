package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.CrewType;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad CrewTypes
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CrewTypesDAOLocal {

	
	public CrewType getCrewTypesByCode(String code) throws DAOServiceException, DAOSQLException;
	
	public CrewType getCrewTypesByID(Long id) throws DAOServiceException, DAOSQLException;
	
	public List<CrewType> getAllCrewTypes() throws DAOServiceException, DAOSQLException;
}