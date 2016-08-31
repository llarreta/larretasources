package co.com.directv.sdii.facade.core;

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

@Local
public interface UploadFileFacadeBeanLocal {
	
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
	 * Metodo: Consulta la información de archivos paginada
	 * @param filter filtros de consulta para buscar los archivos
	 * @param request información de paginación
	 * @return respuesta paginada
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jjimenezh
	 */
	public UploadFileResponse findByTypeAndStatusAndUploadDate(
			UploadFileFilterDTO filter, RequestCollectionInfo request)
			throws BusinessException;
	
	/** 
	 * Permite almacenar un 'UploadFileVO' en la BD. Adicionalmente almacena los bytes que vienen en 'data'
	 * en el sistema de archivos.  
	 * @param UploadFileVO uploadFileVO Objeto que almacena la informacion del archivo.
	 * @param DataHandler data          Contiene los bytes del archivo que sera almacenado en el sistema de archivos.
	 * Precondiciones:
	 * uploadFileVO.getFileType().getCode() tiene un valor valido
	 * uploadFileVO.getCountries().getCountryCode()  tiene un valor valido
	 * @author rdelarosa
	 **/
	public Long createUploadFile(UploadFileVO uploadFileVO, DataHandler dHandler) 
	   throws BusinessException;
	
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
	 * @author jjimenezh
	 */
	public DataHandler downloadOutUploadedFile(Long uploadFileId)throws BusinessException;

	/**
	 * Metodo: Consulta la información de los estados de un archivo
	 * @return lista con los estados de un archivo
	 * @throws BusinessException En caso de error al consultar los estados de archivo
	 * @author jjimenezh
	 */
	public List<FileStatusVO> getAllFileStatus()throws BusinessException;

	/**
	 * Metodo: Consulta la información de los tipos de archivo
	 * @return información de los tipos de archivo
	 * @throws BusinessException En caso de error al consultar los tipos de archivo
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
