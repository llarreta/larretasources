package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.City;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad Cities
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CitiesDAOLocal {

	
	public City getCitiesByCode(String code) throws DAOServiceException, DAOSQLException;
	
	public City getCitiesByID(Long id) throws DAOServiceException, DAOSQLException;
	
	public List<City> getAllCities() throws DAOServiceException, DAOSQLException;

        public List<City> getCitiesByStateId(Long stateId)throws DAOServiceException, DAOSQLException;

}