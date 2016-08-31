package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.NotSerializedVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad NotSerialized.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface NotSerializedFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto NotSerialized
	 * @param obj - NotSerializedVO  objeto que encapsula la información de un NotSerializedVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createNotSerialized(NotSerializedVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un NotSerialized
	 * @param obj - NotSerializedVO  objeto que encapsula la información de un NotSerializedVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateNotSerialized(NotSerializedVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un NotSerialized
	 * @param obj - NotSerializedVO  información del NotSerializedVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteNotSerialized(NotSerializedVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un NotSerialized por su identificador
	 * @param id - Long identificador del NotSerialized a ser consultado
	 * @return objeto con la información del NotSerializedVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public NotSerializedVO getNotSerializedByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los NotSerialized almacenados en la persistencia
	 * @return List<NotSerializedVO> Lista con los NotSerializedVO existentes, una lista vacia en caso que no existan NotSerializedVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
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
