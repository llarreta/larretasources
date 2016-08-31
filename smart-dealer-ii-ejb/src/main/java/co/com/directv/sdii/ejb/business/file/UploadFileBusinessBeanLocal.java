package co.com.directv.sdii.ejb.business.file;

import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.FilterUploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.dto.UploadFileFilterDTO;
import co.com.directv.sdii.model.dto.UploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.UploadFileResponse;
import co.com.directv.sdii.model.vo.FileStatusVO;
import co.com.directv.sdii.model.vo.FileTypeVO;
import co.com.directv.sdii.model.vo.UploadFileVO;


/**
 * Interfaz que define los metodos de negocio de la entidad UpdateFile.
 *
 * @author Ricardo De la Rosa Rosero
 */
@Local
public interface UploadFileBusinessBeanLocal {

    /**
     * Busca un archivo (instancia de la clase UploadFile) con identificador 'id'
     * @param id
     * @return El archivo buscado
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
	public UploadFileVO findById(Long id)  throws BusinessException;
	
	/**
	 * Permite buscar los archivos de tipo 'fileTypeCode' con estado 'fileStatusCode' 
	 * y con fecha de cargue entre 'initialUploadDate' y 'finalUploadDate'   
	 * @param fileTypeCode
	 * @param fileStatusCode
	 * @param initialUploadDate
	 * @param finalUploadDate
	 * @return Lista con los archivos
	 * @throws BusinessException
	 */
	public List<UploadFileVO> findByTypeAndStatusAndUploadDate(String fileTypeCode, String fileStatusCode, Date initialUploadDate, Date finalUploadDate, String  loginNameUser, String countryCode)  
	   throws BusinessException;
	
	/**
	 * Método: Permite buscar los archivos de tipo 'fileTypeCode' con estado 'fileStatusCode' 
	 * y con fecha de cargue entre 'initialUploadDate' y 'finalUploadDate' paginados
	 * @param filter - UploadFileFilterDTO Opciones de filtro
	 * @param request - RequestCollectionInfo - Datos de paginación
	 * @return UploadFileResponse - Con los datos de archivos con filtro especificado
	 * @throws BusinessException en caso de error en la consulta
	 * @author gfandino
	 */
	public UploadFileResponse findByTypeAndStatusAndUploadDate (UploadFileFilterDTO filter,RequestCollectionInfo request)  
	   throws BusinessException;
	
	/**
	 * Permite almacenar un 'UploadFileVO' en la BD. Adicionalmente almacena los bytes que vienen en 'data'
	 * en el sistema de archivos.  
	 * @param UploadFileVO uploadFileVO Objeto que almacena la informacion del archivo.
	 * @param DataHandler data          Contiene los bytes del archivo que sera almacenado en el sistema de archivos.
	 * Precondiciones:
	 * uploadFileVO.getFileType().getCode() tiene un valor valido
	 * uploadFileVO.getCountries().getCountryCode()  tiene un valor valido
	 * uploadFileVO.getName() No es una cadena nula ni vacia
	 * @author rdelarosa
	 **/
	public Long createUploadFile(UploadFileVO uploadFileVO, DataHandler data) throws BusinessException;

	/**
	 * Metodo: descarga el archivo por el identificador especificado
	 * @param uploadFileId identificador del archivo
	 * @return Información del archivo
	 * @throws BusinessException En caso de error al tratar de obtener el archivo
	 * @author jjimenezh
	 */
	public DataHandler downloadUploadedFile(Long uploadFileId)throws BusinessException;

	/**
	 * Metodo: descarga el archivo por el identificador especificado
	 * @param uploadFileId identificador del archivo
	 * @return Información del archivo
	 * @throws BusinessException En caso de error al tratar de obtener el archivo
	 * @author cduarte
	 */
	public DataHandler downloadOutUploadedFile(Long uploadFileId)throws BusinessException;

	/**
	 * Metodo: Actualiza un archivo cargado
	 * @param uploadFile Archivo a ser actualizado
	 * @author jjimenezh
	 */
	public void updateUploadFile(UploadFileVO uploadFile)throws BusinessException;

	/**
	 * Metodo: Actualiza un archivo cargado
	 * @param uploadFile Archivo a ser actualizado
	 * @author jjimenezh
	 */
	public void updateUploadFile(UploadFileVO uploadFile, String newFileStatusCode)throws BusinessException;
	
	/**
	 * Metodo: Consulta todos los estados de arhivo
	 * @return Lista con los estados de archivo
	 * @throws BusinessException En caso de error al consultar los estados de archivo
	 * @author jjimenezh
	 */
	public List<FileStatusVO> getAllFileStatus()throws BusinessException;

	/**
	 * Metodo: Consulta todos los tipos de archivo
	 * @return lista con los tipos de archivo
	 * @throws BusinessException En caso de error al consultar el tipo de archivo
	 * @author jjimenezh
	 */
	public List<FileTypeVO> getAllFileTypes()throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los UploadFile dependiendo de un filtro de UploadFileParam
	 * @param List<FilterUploadFileParamByFileTypeParamNameAndCodeDTO> lista de los parametros a filtrar
	 * @return List<UploadFileParamByFileTypeParamNameAndCodeDTO> lista de todos los UploadFile que cumplen con el filtro
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de UploadFileParam asociado al uploadfile y el nombre del parámetro
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de UploadFileParam asociado al uploadfile y el nombre del parámetro
	 * @author cduarte
	 */
	public List<UploadFileParamByFileTypeParamNameAndCodeDTO> getUploadFileParamByFileTypeParamNameAndCode(List<FilterUploadFileParamByFileTypeParamNameAndCodeDTO> filters) throws BusinessException;
}
