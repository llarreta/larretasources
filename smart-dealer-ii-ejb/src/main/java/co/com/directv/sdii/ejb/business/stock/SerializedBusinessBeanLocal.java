package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ElementDTO;
import co.com.directv.sdii.model.vo.SerializedVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad Serialized.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SerializedBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto SerializedVO
	 * @param obj objeto que encapsula la información de un SerializedVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la creación de Serialized
	 * @author gfandino
	 */
	public void createSerialized(SerializedVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un SerializedVO
	 * @param obj objeto que encapsula la información de un SerializedVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la actualización de Serialized
	 * @author gfandino
	 */
	public void updateSerialized(SerializedVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un SerializedVO
	 * @param obj información del SerializedVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la eliminación de Serialized
	 * @author gfandino
	 */
	public void deleteSerialized(SerializedVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un SerializedVO por su identificador
	 * @param id identificador del SerializedVO a ser consultado
	 * @return objeto con la información del SerializedVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de Serialized por ID
	 * @author gfandino
	 */
	public SerializedVO getSerializedByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los SerializedVO almacenados en la persistencia
	 * @return Lista con los SerializedVO existentes, una lista vacia en caso que no existan SerializedVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la onsulta de todos los Serialized
	 * @author gfandino
	 */
	public List<SerializedVO> getAllSerializeds() throws BusinessException;
	
	
	
	/**
	 * Metodo: Obtiene la información de un SerializedVO por su serial
	 * @param serial - String serial del SerializedVO a ser consultado
	 * @return SerializedVO objeto con la información del SerializedVO dado su serial, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de Serialized por serial
	 * @author gfandino
	 */
	public SerializedVO getSerializedBySerial(String serial,Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un SerializedVO por su serial
	 * @param serial - String serial del SerializedVO a ser consultado
	 * @return SerializedVO objeto con la información del SerializedVO dado su serial, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de Serialized por serial
	 * @author cduarte
	 */
	public SerializedVO getSerializedBySerial(String serial,String countryCode) throws BusinessException;
	
	/**
	 * Metodo: Consulta resializados por el identificador del elemento
	 * @param id Identificador del elemento
	 * @return List<SerializedVO> Lista de serializados encontrados
	 * @throws BusinessException 
	 * @author jforero 11/08/2010
	 */
	public List<SerializedVO> getSerializedByElementId(Long id) throws BusinessException;
	
	
	
	/**
	 * Metodo: Persiste uns lista de serialized validando la existencia de sus Element
	 * @param listSerialized Lista de serialized a guardar
	 * @throws BusinessException
	 * @author jforero 19/08/2010
	 */
	public void saveSerializedList(List<SerializedVO> listSerialized,Long countryId)throws BusinessException;
	
	/**
	 * Metodo: Consulta los elementos vinculados de un elemento serializado
	 * @param serialized - Long identificador del serial
	 * @return List<SerializedVO> lista de elementos vinculados; vacio en otro caso
	 * @throws BusinessException en caso de error al consultar los elementos vinculados
	 * @author gfandino
	 */
	public List<SerializedVO> getLinkedSerializedBySerializedId(Long serialized)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna la informacion de un elemnto
	 * filtrado por el serial.
	 * @param serial
	 * @return ElementDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public ElementDTO getElementBySerial(String serial,Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un SerializedVO por su serial
	 * @param serial - String serial del SerializedVO a ser consultado
	 * @return SerializedVO objeto con la información del SerializedVO dado su serial, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de Serialized por serial
	 * @author jnova
	 */
	public SerializedVO getSerializedBySerialCode(String serial,Long countryId) throws BusinessException;

}
