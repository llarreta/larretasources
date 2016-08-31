package co.com.directv.sdii.persistence.dao.file;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.UploadFileFilterDTO;
import co.com.directv.sdii.model.pojo.UploadFile;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.UploadFileResponse;

/**
 * Interface Local para la gestion del CRUD de la entidad UploadFile 
 * 
 * Fecha de Creacion: Jun 29, 2011
 * @author rdelarosa <a href="mailto:rdelarosa@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface UploadFileDAOLocal {
    /**
     * Busca un archivo (instancia de la clase UploadFile) con identificador 'id'
     * @param id
     * @return El archivo buscado
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
	public UploadFile findById(Long id)  throws DAOServiceException, DAOSQLException;
	
	/**
	 * Permite buscar los archivos de tipo 'fileTypeCode' con estado 'fileStatusCode' 
	 * y con fecha de cargue entre 'initialUploadDate' y 'finalUploadDate'   
	 * @param fileTypeCode
	 * @param fileStatusCode
	 * @param initialUploadDate
	 * @param finalUploadDate
	 * @return Lista con los archivos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<UploadFile> findByTypeAndStatusAndUploadDate(String fileTypeCode, String fileStatusCode, Date initialUploadDate, Date finalUploadDate, String  loginNameUser, String countryCode)  
	   throws DAOServiceException, DAOSQLException;
	
	/**
	 * Permite buscar los archivos de tipo 'fileTypeCode' con estado 'fileStatusCode' 
	 * y con fecha de cargue entre 'initialUploadDate' y 'finalUploadDate'   
	 * @param filter - UploadFileFilterDTO datos para filtro  
	 * @return UploadFileResponse - Datos paginados de los archivos cargados 
	 * @throws DAOServiceException en caso de error consultarndo los archivos cargados por filtro
	 * @throws DAOSQLException en caso de error consultarndo los archivos cargados por filtro
	 */
	public UploadFileResponse findByTypeAndStatusAndUploadDate(UploadFileFilterDTO filter, RequestCollectionInfo request)throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * @param transientInstance
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public UploadFile save(UploadFile uploadFile)  
	   throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * @param uploadFile
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public void updateUploadFile(UploadFile uploadFile) throws DAOServiceException, DAOSQLException;
	
}
