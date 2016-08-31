package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ZoneType;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad ZoneTypes
 * 
 * Fecha de Creaci�n: Mar 15, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ZoneTypesDAOLocal {

	
	public ZoneType getZoneTypesByCode(String code) throws DAOServiceException, DAOSQLException;
	
	public ZoneType getZoneTypesByID(Long id) throws DAOServiceException, DAOSQLException;
	
	public List<ZoneType> getAllZoneTypes() throws DAOServiceException, DAOSQLException;

}