package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.RecordStatusVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad RecordStatus.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface RecordStatusBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto RecordStatusVO
	 * @param obj objeto que encapsula la información de un RecordStatusVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createRecordStatus(RecordStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un RecordStatusVO
	 * @param obj objeto que encapsula la información de un RecordStatusVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateRecordStatus(RecordStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un RecordStatusVO
	 * @param obj información del RecordStatusVO a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteRecordStatus(RecordStatusVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un RecordStatusVO por su identificador
	 * @param id identificador del RecordStatusVO a ser consultado
	 * @return objeto con la información del RecordStatusVO dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public RecordStatusVO getRecordStatusByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los RecordStatusVO almacenados en la persistencia
	 * @return Lista con los RecordStatusVO existentes, una lista vacia en caso que no existan RecordStatusVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<RecordStatusVO> getAllRecordStatuss() throws BusinessException;

}
