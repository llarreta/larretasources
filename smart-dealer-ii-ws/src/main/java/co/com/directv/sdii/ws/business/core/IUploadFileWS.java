package co.com.directv.sdii.ws.business.core;

import java.util.List;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.FilterUploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.dto.UploadFileFilterDTO;
import co.com.directv.sdii.model.dto.UploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.dto.collection.FileDetailProcessCollectionDTO;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.FiltersFindUpdateFile;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.UploadFileResponse;
import co.com.directv.sdii.model.vo.FileStatusVO;
import co.com.directv.sdii.model.vo.FileTypeVO;
import co.com.directv.sdii.model.vo.UploadFileVO;

@WebService(name="UploadFileWS",targetNamespace="http://core.business.ws.sdii.directv.com.co/")
public interface IUploadFileWS {
	
    /**
     * Busca un archivo (instancia de la clase UploadFile) con identificador 'id'
     * @param id
     * @return El archivo buscado
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
	@WebMethod(operationName = "findUploadFileById", action = "findUploadFileById")	
	public UploadFileVO findUploadFileById(@WebParam( name = "uploadFileId") Long uploadFileId)  throws BusinessException;

	/**
	 * Permite buscar los archivos cargados segun un conjunto de filtros 
	 * @param filters: Los filtros son:
	 *		String fileTypeCode;
	 *		String fileStatusCode; 
	 *		Date initialUploadDate; 
	 *		Date finalUploadDate;
	 *		String  loginNameUser;
	 *		String countryCode; 
	 * @return Lista con los archivos resultado de la consulta
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "findUploadFileByTypeAndStatusAndUploadDate", action = "findUploadFileByTypeAndStatusAndUploadDate")
	public List<UploadFileVO> findUploadFileByTypeAndStatusAndUploadDate(@WebParam( name = "filters") FiltersFindUpdateFile filters) 
	throws BusinessException;
	
	@WebMethod(operationName = "findUploadedFilesByFilters", action = "findUploadedFilesByFilters")
	public UploadFileResponse findUploadedFilesByFilters(@WebParam( name = "filter") UploadFileFilterDTO filter, @WebParam( name = "requestCollectionInfo") RequestCollectionInfo requestCollectionInfo)
			throws BusinessException;
	/** 
	 * Permite almacenar un 'UploadFileVO' en la BD. Adicionalmente almacena los bytes que vienen en 'data'
	 * en el sistema de archivos.  
	 * @param UploadFileVO uploadFileVO Objeto que almacena la informacion del archivo.
	 * @param DataHandler data          Contiene los bytes del archivo que sera almacenado en el sistema de archivos.
	 * @throws BusinessException                        
	 * Precondiciones:
	 * uploadFileVO.getFileType().getCode()          No es una cadena nula ni vacia, es decir, tiene un valor valido
	 * uploadFileVO.getCountries().getCountryCode()  No es una cadena nula ni vacia, es decir, tiene un valor valido
	 * uploadFileVO.getName()                        No es una cadena nula ni vacia, es decir, tiene un valor valido
	 * uploadFileVO.getUsers().getLogin()            No es una cadena nula ni vacia, es decir, tiene un valor valido
	 * @author Ricardo De la Rosa
	 **/	
	@WebMethod(operationName = "createUploadFile", action = "createUploadFile")
	public Long createUploadFile(@WebParam( name = "uploadFileVO") UploadFileVO uploadFileVO,@WebParam( name = "dHandler") @XmlMimeType("application/octet-stream") DataHandler dHandler) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Descarga un archivo dado el identificador del mismo
	 * @param uploadFileId identificador del archivo
	 * @return información para descargar el archivo
	 * @throws BusinessException En caso de error al tratar de descargar el archivo
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "downloadUploadedFile", action = "downloadUploadedFile")
	@XmlMimeType("application/octet-stream")
	public DataHandler downloadUploadedFile(@WebParam( name = "uploadFileId") Long uploadFileId)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Descarga un archivo dado el identificador del mismo
	 * @param uploadFileId identificador del archivo
	 * @return informaciÃ³n para descargar el archivo
	 * @throws BusinessException En caso de error al tratar de descargar el archivo
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "downloadOutUploadedFile", action = "downloadOutUploadedFile")
	@XmlMimeType("application/octet-stream")
	public DataHandler downloadOutUploadedFile(@WebParam( name = "uploadFileId") Long uploadFileId)throws BusinessException;
	
	/**
	 * Metodo: Consulta los estados que puede tener un archivo
	 * @return lista con los estados que puede tener el archivo
	 * @throws BusinessException En caso de error al consultar los estados de archivo
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getAllFileStatus", action = "getAllFileStatus")
	public List<FileStatusVO> getAllFileStatus()throws BusinessException;
	
	/**
	 * Metodo: Consulta los tipos de archivo
	 * @return lista con todos los tipos de archivo
	 * @throws BusinessException En caso de error al consultar los tipos de archivo
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getAllFileTypes", action = "getAllFileTypes")
	public List<FileTypeVO> getAllFileTypes()throws BusinessException;
	
	/**
	 * Metodo: Consulta la informaciÃ³n de los registros de un archivo
	 * @param fileId identificador del archivo
	 * @param requestCollection informaciÃ³n de consulta de la paginaciÃ³n
	 * @return datos de paginaciÃ³n
	 * @throws BusinessException En caso de error al realizar la consulta
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "findFileDetailProcessByFileId", action = "findFileDetailProcessByFileId")
	public FileDetailProcessCollectionDTO findFileDetailProcessByFileId(@WebParam( name = "fileId") Long fileId,@WebParam( name = "requestCollection")  RequestCollectionInfoDTO requestCollection) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la informaciÃ³n de todos los UploadFile dependiendo de un filtro de UploadFileParam
	 * @param List<FilterUploadFileParamByFileTypeParamNameAndCodeDTO> lista de los parametros a filtrar
	 * @return List<UploadFileParamByFileTypeParamNameAndCodeDTO> lista de todos los UploadFile que cumplen con el filtro
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de UploadFileParam asociado al uploadfile y el nombre del parÃ¡metro
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de UploadFileParam asociado al uploadfile y el nombre del parÃ¡metro
	 * @author cduarte
	 */
	@WebMethod(operationName = "getUploadFileParamByFileTypeParamNameAndCode", action = "getUploadFileParamByFileTypeParamNameAndCode")
	public List<UploadFileParamByFileTypeParamNameAndCodeDTO> getUploadFileParamByFileTypeParamNameAndCode(@WebParam( name = "filters")List<FilterUploadFileParamByFileTypeParamNameAndCodeDTO> filters) throws BusinessException;
	
}
