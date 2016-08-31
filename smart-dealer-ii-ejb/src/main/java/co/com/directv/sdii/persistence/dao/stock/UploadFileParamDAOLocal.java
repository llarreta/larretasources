package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.FilterUploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.dto.UploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.pojo.UploadFileParam;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad UploadFileParam
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface UploadFileParamDAOLocal {

	/**
	 * Metodo:  persiste la información de un UploadFileParam
	 * @param obj objeto que encapsula la información de un UploadFileParam
	 * @throws DAOServiceException en caso de error al ejecutar la creación de UploadFileParam
	 * @throws DAOSQLException en caso de error al ejecutar la creación de UploadFileParam
	 * @author gfandino
	 */
	public void createUploadFileParam(UploadFileParam obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un UploadFileParam
	 * @param obj objeto que encapsula la información de un UploadFileParam
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de UploadFileParam
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de UploadFileParam
	 * @author gfandino
	 */
	public void updateUploadFileParam(UploadFileParam obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un UploadFileParam
	 * @param obj información del UploadFileParam a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de UploadFileParam
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de UploadFileParam
	 * @author gfandino
	 */
	public void deleteUploadFileParam(UploadFileParam obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un UploadFileParam por su identificador
	 * @param id identificador del UploadFileParam a ser consultado
	 * @return objeto con la información del UploadFileParam dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de UploadFileParam por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de UploadFileParam por ID
	 * @author gfandino
	 */
	public UploadFileParam getUploadFileParamByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los UploadFileParam almacenados en la persistencia
	 * @return Lista con los UploadFileParam existentes, una lista vacia en caso que no existan UploadFileParam en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los  UploadFileParam
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los  UploadFileParam
	 * @author gfandino
	 */
	public List<UploadFileParam> getAllUploadFileParams() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los UploadFileParam almacenados en la persistencia asociados al uploadfile
	 * @return Lista con los UploadFileParam existentes, una lista vacia en caso que no existan UploadFileParam en el sistema asociados al uploadfile
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los  UploadFileParam asociados al uploadfile
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los  UploadFileParam asociados al uploadfile
	 * @author gfandino
	 */
	public List<UploadFileParam> getUploadFileParamsByUploadFile(Long idUploadFile) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos un UploadFileParam almacenado en la persistencia asociados al uploadfile y con nombre
	 * @return UploadFileParam existente, asociado al uploadfile y el nombre del parámetro
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de UploadFileParam asociado al uploadfile y el nombre del parámetro
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de UploadFileParam asociado al uploadfile y el nombre del parámetro
	 * @author gfandino
	 */
	public UploadFileParam getUploadFileParamByUploadFileAndName(Long idUploadFile,String nameParam) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de todos los UploadFile dependiendo de un filtro de UploadFileParam
	 * @param List<FilterUploadFileParamByFileTypeParamNameAndCodeDTO> lista de los parametros a filtrar
	 * @return List<UploadFileParamByFileTypeParamNameAndCodeDTO> lista de todos los UploadFile que cumplen con el filtro
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de UploadFileParam asociado al uploadfile y el nombre del parámetro
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de UploadFileParam asociado al uploadfile y el nombre del parámetro
	 * @author cduarte
	 */
	public List<UploadFileParamByFileTypeParamNameAndCodeDTO> getUploadFileParamByFileTypeParamNameAndCode(List<FilterUploadFileParamByFileTypeParamNameAndCodeDTO> filters) throws DAOServiceException,
			DAOSQLException;



}