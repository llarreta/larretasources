package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.PostalCode;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad PostalCodes
 * 
 * Fecha de Creaci�n: Mar 15, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface PostalCodesDAOLocal {

	
	public PostalCode getPostalCodesByCode(String code) throws DAOServiceException, DAOSQLException;
	
	public PostalCode getPostalCodesByID(Long id) throws DAOServiceException, DAOSQLException;
	
	public List<PostalCode> getAllPostalCodes() throws DAOServiceException, DAOSQLException;

    public List<PostalCode> getPostalCodesByCityId(Long cityId)throws DAOServiceException, DAOSQLException;

    public List<PostalCode> getPostalCodesByName(String name) throws DAOServiceException, DAOSQLException;

    public void createPostalCode(PostalCode obj) throws DAOServiceException, DAOSQLException;

    public void updatePostalCode(PostalCode obj) throws DAOServiceException, DAOSQLException;

    public void deletePostalCode(PostalCode obj) throws DAOServiceException, DAOSQLException;

	public List<PostalCode> getAllPostalCodesByCountryId(Long countryId)throws DAOServiceException, DAOSQLException;
	
	public PostalCode getPostalCodesByCodeByCountryCode(String postalCode,String countryCode) throws DAOServiceException, DAOSQLException;

	public PostalCode getPostalCodesByCodeByCountryId(String postalCode, Long countryId)throws DAOServiceException, DAOSQLException;

}