package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.IbsMediaContactType;
import co.com.directv.sdii.model.pojo.MediaContactType;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad MediaContactTypes
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MediaContactTypesDAOLocal {
	
	public MediaContactType getMediaContactTypesByID(Long id) throws DAOServiceException, DAOSQLException;
	
	public List<MediaContactType> getAllMediaContactTypes() throws DAOServiceException, DAOSQLException;
	
	public MediaContactType getMediaContactTypesByCode(String mediaCode) throws DAOServiceException, DAOSQLException;
	
	public IbsMediaContactType getMediaContactTypeByIbsCode(String mediaCode, String ibsCode) throws DAOServiceException, DAOSQLException;
	
	public List<IbsMediaContactType> getIbsMediaContactTypeByMediaContactTypeId(Long mediaContactTypeId) throws DAOServiceException, DAOSQLException;


}