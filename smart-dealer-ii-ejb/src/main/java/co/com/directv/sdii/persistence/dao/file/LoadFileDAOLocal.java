package co.com.directv.sdii.persistence.dao.file;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.LoadFile;
import co.com.directv.sdii.model.pojo.UploadFile;

/**
 * Interface Local para la gestion del CRUD de la entidad LoadFile 
 * 
 * Fecha de Creación: 24/01/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface LoadFileDAOLocal {
    
	/**
	 * Metodo: Metodo encargado de crear un LoadFile
	 * @param loadFile objeto a persistir
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	public void createLoadFile(LoadFile loadFile)  throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Metodo encargado de crear un LoadFile
	 * @param loadFile objeto a persistir
	 * @param byte[] arrayByte arreglo de butes con archivo que se esta cargando
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	public void createLoadFile(LoadFile loadFile,byte[] arrayByte)  throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Metodo encargado de update un LoadFile
	 * @param loadFile
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public void updateLoadFile(LoadFile loadFile) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite buscar un LoadFile con un id
	 * @param id del loadFile
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public LoadFile getLoadFileById(Long id)  throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite buscar un LoadFile filtrando por un id de un UploadFile y un codigo del tipo del archivo cargado
	 * @param idUploadFile
	 * @param codeLoadFileType
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public LoadFile getLoadFileByUploadFileAndCodeLoadFileType(Long idUploadFile,String codeLoadFileType)  throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite buscar un LoadFile filtrando por un UploadFile y un codigo del tipo del archivo cargado
	 * @param uploadFile
	 * @param codeLoadFileType
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public LoadFile getLoadFileByUploadFileAndCodeLoadFileType(UploadFile uploadFile,String codeLoadFileType)  throws DAOServiceException, DAOSQLException;
	
}
