package co.com.directv.sdii.persistence.dao.file;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.FileStatus;

/**
 * Interface Local para la gestion del CRUD de la entidad FileStatus 
 * 
 * Fecha de Creacion: Jun 29, 2011
 * @author rdelarosa <a href="mailto:rdelarosa@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface FileStatusDAOLocal {
	
	/* 
	 * Almacena en la BD la entidad fileStatus de clase FileStatus
	 */
	public void save(FileStatus fileStatus)  throws DAOServiceException, DAOSQLException;
	
	/* 
	 * Recupera de la BD una entidad fileStatus de clase FileStatusm a partir del 'statusCode' 
	 */
	public FileStatus findByCode(String statusCode) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: <Descripcion>
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<FileStatus> getAllFileStatus()throws DAOServiceException, DAOSQLException;
}
