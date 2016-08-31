package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.NotSerializedVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad NotSerialized.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface NotSerializedBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto NotSerializedVO
	 * @param obj objeto que encapsula la información de un NotSerializedVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createNotSerialized(NotSerializedVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un NotSerializedVO
	 * @param obj objeto que encapsula la información de un NotSerializedVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateNotSerialized(NotSerializedVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un NotSerializedVO
	 * @param obj información del NotSerializedVO a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteNotSerialized(NotSerializedVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un NotSerializedVO por su identificador
	 * @param id identificador del NotSerializedVO a ser consultado
	 * @return objeto con la información del NotSerializedVO dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public NotSerializedVO getNotSerializedByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los NotSerializedVO almacenados en la persistencia
	 * @return Lista con los NotSerializedVO existentes, una lista vacia en caso que no existan NotSerializedVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<NotSerializedVO> getAllNotSerializeds() throws BusinessException;
	
	/**
	 * Metodo: Obtiene una lista de los elementos no serializados que tiene un registro
	 * de importación, se agrega para el caso de uso INV - 03 confirmar los elementos
	 * no serializados de un registro de importación
	 * @param importLogId identificador del registro de importación
	 * @return Lista con los elementos no serializados encontrados en el
	 * registro de importación, una lista vacia en caso que el registro
	 * de importación no tenga items
	 * @throws BusinessException en caso de error al consultar los elementos
	 * no serializados
	 * @author jjimenezh
	 */
	public List<NotSerializedVO> getNotSerializedByImportLogId(Long importLogId)throws BusinessException;
	
	


}
