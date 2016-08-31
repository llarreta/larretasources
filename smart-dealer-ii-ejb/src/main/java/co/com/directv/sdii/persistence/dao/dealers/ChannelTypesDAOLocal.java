package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ChannelType;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad ChannelTypes
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ChannelTypesDAOLocal {

	
	public ChannelType getChannelTypesByCode(String code) throws DAOServiceException, DAOSQLException;
	
	public ChannelType getChannelTypesByID(Long id) throws DAOServiceException, DAOSQLException;
	
	public List<ChannelType> getAllChannelTypes() throws DAOServiceException, DAOSQLException;

}