package co.com.directv.sdii.persistence.dao.file;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.LoadFileType;

/**
 * Interface Local para la gestion del CRUD de la entidad LoadFileType
 * 
 * Fecha de Creación: 24/01/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface LoadFileTypeDAOLocal {
    
	/**
	 * Metodo: Metodo encargado de crear un LoadFileType
	 * @param loadFileType
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public void createLoadFileType(LoadFileType loadFileType)  throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Metodo encargado de update un LoadFileType
	 * @param loadFileType
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public void updateLoadFileType(LoadFileType loadFileType) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Permite buscar un LoadFileType con un id
	 * @param id
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public LoadFileType getLoadFileTypeById(Long id)  throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite buscar un LoadFileType con un codigo
	 * @param code
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public LoadFileType getLoadFileTypeByCode(String code)  throws DAOServiceException, DAOSQLException;
}
