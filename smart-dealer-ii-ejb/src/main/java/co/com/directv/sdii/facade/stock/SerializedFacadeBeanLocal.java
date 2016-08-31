package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SerializedResponse;
import co.com.directv.sdii.model.vo.SerializedVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad Serialized.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SerializedFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto Serialized
	 * @param obj - SerializedVO  objeto que encapsula la información de un SerializedVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSerialized(SerializedVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un Serialized
	 * @param obj - SerializedVO  objeto que encapsula la información de un SerializedVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSerialized(SerializedVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un Serialized
	 * @param obj - SerializedVO  información del SerializedVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteSerialized(SerializedVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un Serialized por su identificador
	 * @param id - Long identificador del Serialized a ser consultado
	 * @return objeto con la información del SerializedVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public SerializedVO getSerializedByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los Serialized almacenados en la persistencia
	 * @return List<SerializedVO> Lista con los SerializedVO existentes, una lista vacia en caso que no existan SerializedVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<SerializedVO> getAllSerializeds() throws BusinessException;
	
	
	/**
	 * Metodo: Consulta un serializado por Numero de Serie
	 * @param serial Numero de Serie
	 * @return SerializedVO Datos del serializado obtenido
	 * @throws BusinessException 
	 * @author jforero 06/08/2010
	 */
	public SerializedVO getSerializedBySerial(String serial,Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Consulta resializados por el identificador del elemento
	 * @param id Identificador del elemento
	 * @return List<SerializedVO> Lista de serializados encontrados
	 * @throws BusinessException 
	 * @author jalopez
	 */
	public List<SerializedVO> getSerializedByElementId(Long id) throws BusinessException;

	
	/**
	 * Metodo: Consulta los elementos vinculados de un elemento serializado
	 * @param serialized - Long identificador del serial
	 * @return List<SerializedVO> lista de elementos vinculados; vacio en otro caso
	 * @throws BusinessException en caso de error al consultar los elementos vinculados
	 * @author gfandino
	 */
	public List<SerializedVO> getLinkedSerializedBySerializedId(Long serialized)throws BusinessException;
	
}
