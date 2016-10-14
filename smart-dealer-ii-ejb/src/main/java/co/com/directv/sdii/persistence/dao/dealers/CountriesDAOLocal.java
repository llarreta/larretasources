package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Country;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad Countries
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CountriesDAOLocal {

	
	public Country getCountriesByCode(String code) throws DAOServiceException, DAOSQLException;
	
	public Country getCountriesByID(Long id) throws DAOServiceException, DAOSQLException;
	
	public List<Country> getAllCountries() throws DAOServiceException, DAOSQLException;
	
	public Country getCountriesByIso3Code(String code) throws DAOServiceException, DAOSQLException;

}