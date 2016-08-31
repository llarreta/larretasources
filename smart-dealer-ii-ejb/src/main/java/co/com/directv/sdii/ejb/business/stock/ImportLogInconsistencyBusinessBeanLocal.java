package co.com.directv.sdii.ejb.business.stock;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.ImportLogInconsistencyResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ImportLogInconsistencyVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ImportLogInconsistency.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ImportLogInconsistencyBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ImportLogInconsistencyVO
	 * @param obj objeto que encapsula la información de un ImportLogInconsistencyVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la creación de ImportLogInconsistency
	 * @author gfandino
	 */
	public void createImportLogInconsistency(ImportLogInconsistencyVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ImportLogInconsistencyVO
	 * @param obj objeto que encapsula la información de un ImportLogInconsistencyVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la actualización de ImportLogInconsistency
	 * @author gfandino
	 */
	public void updateImportLogInconsistency(ImportLogInconsistencyVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ImportLogInconsistencyVO
	 * @param obj información del ImportLogInconsistencyVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la eliminación de ImportLogInconsistency
	 * @author gfandino
	 */
	public void deleteImportLogInconsistency(ImportLogInconsistencyVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ImportLogInconsistencyVO por su identificador
	 * @param id identificador del ImportLogInconsistencyVO a ser consultado
	 * @return objeto con la información del ImportLogInconsistencyVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ImportLogInconsistency por ID
	 * @author gfandino
	 */
	public ImportLogInconsistencyVO getImportLogInconsistencyByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ImportLogInconsistencyVO almacenados en la persistencia
	 * @return Lista con los ImportLogInconsistencyVO existentes, una lista vacia en caso que no existan ImportLogInconsistencyVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ImportLogInconsistency
	 * @author gfandino
	 */
	public List<ImportLogInconsistencyVO> getAllImportLogInconsistencys() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los ImportLogInconsistencyVO generados en la fecha especificada
	 * @param inic - Date fecha de consulta
	 * @return List<ImportLogInconsistencyVO> Lista de los ImportLogInconsistencyVO correspondientes a la fecha especificada. Vacio en otro caso
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de los ImportLogInconsistency por fecha
	 * @author gfandino
	 */
	public List<ImportLogInconsistencyVO> getImportLogInconsitencysByCreationDate(Date inic)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los ImportLogInconsistencyVO generados en el rango de fechas especificadas
	 * @param inic - Date fecha de  inicio de consulta
	 * @param end - Date fecha de  fin de consulta
	 * @return List<ImportLogInconsistencyVO> Lista de los ImportLogInconsistencyVO correspondientes a las fechas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de los ImportLogInconsistency por rango de fechas
	 * @author gfandino
	 */
	public List<ImportLogInconsistencyVO> getImportLogInconsitencysByCreationDate(Date inic,Date end)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los ImportLogInconsistencyVO generados en estado inconsistente
	 * @return List<ImportLogInconsistencyVO> Lista de los ImportLogInconsistencyVO correspondientes a estado inconsistente. Vacio en otro caso
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de los ImportLogInconsistency por estado inconsistente
	 * @author gfandino
	 */
	public List<ImportLogInconsistencyVO> getImportLogInconsitencysByInconsistenState()throws BusinessException;
	
	/**
	 * Metodo: Permite cerrar una ImportLogInconsistencyVO
	 * @param obj - ImportLogInconsistencyVO inconsistencia que va a ser cerrada
	 * @throws BusinessException en caso de error al tratar de ejecutar el registro de cierre de inconsistencia
	 * @author gfandino
	 */
	public void registerImportLogInconsitencysClosed(ImportLogInconsistencyVO obj)throws BusinessException;
	
	/**
	 * Metodo: Permite terminar una ImportLogInconsistencyVO
	 * @param obj - ImportLogInconsistencyVO inconsistencia que va a ser terminada
	 * @throws BusinessException en caso de error al tratar de ejecutar el registro de termación de inconsistencia
	 * @author gfandino
	 */
	public void registerImportLogInconsitencysFinished(ImportLogInconsistencyVO obj)throws BusinessException;

	/**
	 * 
	 * Metodo: Retorna la lista paginada de las inconsistencias, dado un registro de importación.
	 * @param importLogId
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author hcorredor
	 */
	public ImportLogInconsistencyResponse getImportLogInconsistenciesByImportLogId(
			Long importLogId, RequestCollectionInfo requestCollInfo)
			throws BusinessException;

}
