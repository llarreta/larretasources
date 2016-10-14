package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.CrewOff;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad CrewOff
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CrewsOffDAOLocal {

	
	public void createCrewOff(CrewOff obj) throws DAOServiceException, DAOSQLException;

	
	public CrewOff getCrewOffByID(Long id) throws DAOServiceException, DAOSQLException;

	
	public void updateCrewOff(CrewOff obj) throws DAOServiceException, DAOSQLException;

	
	public void deleteCrewOff(CrewOff obj) throws DAOServiceException, DAOSQLException;

	
	public List<CrewOff> getAllCrewOff() throws DAOServiceException, DAOSQLException;
	
	 public List<CrewOff> getCrewOffByCreId(CrewOff crewOff) throws DAOServiceException, DAOSQLException;

}