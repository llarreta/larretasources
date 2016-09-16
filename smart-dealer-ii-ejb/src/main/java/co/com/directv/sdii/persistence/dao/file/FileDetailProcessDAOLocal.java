package co.com.directv.sdii.persistence.dao.file;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.collection.FileDetailProcessCollectionDTO;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * Interface Local para la gestion del CRUD de la entidad FileDetailProcess 
 * 
 * Fecha de Creacion: Jun 29, 2011
 * @author rdelarosa <a href="mailto:rdelarosa@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface FileDetailProcessDAOLocal {
	/**
	 * Retorna los objetos de tipo 'FileDetailProcess' (estos objetos tiene los errores detectados sobre el archivo) 
	 * asociados al archivo con id 'fileId'
	 * @param fileId
	 * @return List<FileDetailProcess>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<FileDetailProcess> findByFileId(Long fileId)  throws DAOServiceException, DAOSQLException  ;
	
	/**
	 * Metodo: Consulta la información de los detalles de un archivo por el identificador del mismo
	 * @param fileId identificador del archivo a consultar sus detalles
	 * @param requestCollection información de paginación para la consulta
	 * @return objeto que encapsula los resultados con paginación
	 * @throws DAOServiceException en caso de error al ejecutar la consulta
	 * @throws DAOSQLException en caso de error al ejecutar la consulta
	 * @author jjimenezh
	 */
	public FileDetailProcessCollectionDTO findFileDetailByFileId(Long fileId, RequestCollectionInfo requestCollection)  throws DAOServiceException, DAOSQLException;
	
    /**
     * Almacena una instancia de la clase 'FileDetailProcess'
     * @param employee - Objeto con la información básica del empleado
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */	
	public void save(FileDetailProcess transientInstance) throws DAOServiceException, DAOSQLException;

	public void save(List<FileDetailProcess> fileDetailProcess) throws DAOServiceException, DAOSQLException;
}
