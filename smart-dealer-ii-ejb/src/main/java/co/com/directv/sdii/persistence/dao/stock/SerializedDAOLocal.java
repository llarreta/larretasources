package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SerializedResponse;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad Serialized
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SerializedDAOLocal {

	/**
	 * Metodo:  persiste la información de un Serialized
	 * @param obj objeto que encapsula la información de un Serialized
	 * @throws DAOServiceException en caso de error al ejecutar la creación de Serialized
	 * @throws DAOSQLException en caso de error al ejecutar la creación de Serialized
	 * @author gfandino
	 */
	public void createSerialized(Serialized obj) throws DAOServiceException, DAOSQLException, BusinessException;
	
	/**
	 * Metodo: actualiza la información de un Serialized
	 * @param obj objeto que encapsula la información de un Serialized
	 * @throws DAOServiceException en caso de error al ejecutar la consulta de Serialized
	 * @throws DAOSQLException en caso de error al ejecutar la consulta de Serialized
	 * @author gfandino
	 */
	public void updateSerialized(Serialized obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un Serialized
	 * @param obj información del Serialized a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de Serialized
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de Serialized
	 * @author gfandino
	 */
	public void deleteSerialized(Serialized obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un Serialized por su identificador
	 * @param id identificador del Serialized a ser consultado
	 * @return objeto con la información del Serialized dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Serialized por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Serialized por ID 
	 * @author gfandino
	 */
	public Serialized getSerializedByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los Serialized almacenados en la persistencia
	 * @return Lista con los Serialized existentes, una lista vacia en caso que no existan Serialized en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los Serialized 
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los Serialized
	 * @author gfandino
	 */
	public List<Serialized> getAllSerializeds() throws DAOServiceException, DAOSQLException;

	

	/**
	 * Metodo: Obtiene la información de un Serialized por su serial
	 * @param serial - String serial del Serialized a ser consultado
	 * @return Serialized objeto con la información del Serialized dado su serial, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Serialized por serial
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Serialized por serial 
	 * @author gfandino
	 */
	public Serialized getSerializedBySerial(String serial,Long countryId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un Serialized por su serial
	 * @param serial - String serial del Serialized a ser consultado
	 * @return Serialized objeto con la información del Serialized dado su serial, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Serialized por serial
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Serialized por serial 
	 * @author cduarte
	 */
	public Serialized getSerializedBySerial(String serial,String countryCode)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Consulta si existe un Serialized por su serial
	 * @param serial - String serial del Serialized a ser consultado
	 * @return Serialized objeto con la información del Serialized dado su serial, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Serialized por serial
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Serialized por serial 
	 * @author cduarte
	 */
	public boolean isSerializedBySerial(String serial) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Consulta una lista de Serialized por su serial
	 * @param serials - String serial del Serialized a ser consultado
	 * @return Serialized objeto con la información del Serialized dado su serial, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Serialized por serial
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Serialized por serial 
	 * @author cduarte
	 */
	public List<Serialized> getSerializedByListSerial(StringBuffer stringSerials) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Retorna una lista de serialized por medio del element id 
	 * @param elementId - id del element relacionado con el Serialized a ser consultado
	 * @return List<Serialized>  objetos con la información del Serialized
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Serialized por serial
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Serialized por serial 
	 * @author jalopez
	 */
	public List<Serialized> getSerializedByElementId(Long elementId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Retorna una lista de serialized por medio del element id y que no han sido confirmados
	 * @param elementId - id del element relacionado con el Serialized a ser consultado
	 * @return List<Serialized>  objetos con la información del Serialized
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Serialized por serial
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Serialized por serial 
	 * @author jalopez
	 */
	public List<Serialized> getSerializedByElementIdUnconfirmed(Long elementId) throws DAOServiceException, DAOSQLException;
	
	
	 /**
	 * Metodo: Obtiene los registros de todos los objetos Serialized asociados a un WareHouse.
	 * Cuando el objeto tiene un elemento vinculado, no se retorna un Serialized adicional con el objeto vinculado,
	 * sino que este se accede a través del objeto principal.
	 * @return Lista con los registros de todos los objetos Serialized propios de un WareHouse
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la tarea
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la tarea
	 * @author garciniegas
	 */
	public List<Serialized> getSerializedsByWareHouseId( Warehouse warehouseId ) throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Regresa una instancia de Serialized consultada por el IRD
	 * @return una instancia de Serialized consultada por el IRD
	 * @param  el IRD involucrado en la busqueda 
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la tarea
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la tarea
	 * @author garciniegas
	 */
	public Serialized getSerializedByIRD( String ird ) throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Consulta el elemento serializado asociado al serial y al tipo de elemento
	 * @param serialCode - String serial a consultar
	 * @param elementType - Identificador del tipo del elemento
	 * @return Serialized correspondiente al tipo de elemento
	 * @throws DAOServiceException en caso de error al consultar Serialized por serial y tipo de elemento
	 * @throws DAOSQLException en caso de error al consultar Serialized por serial y tipo de elemento
	 * @author gfandino
	 */
	public Serialized getSerializedBySerialAndElementType (String serialCode,Long elementType,Long countryId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Consulta el elemento serializado asociado al serial y al tipo de elemento
	 * @param serialCode - String serial a consultar
	 * @param elementsTypes - Identificador de los tipos de elementos
	 * @param countryId - Id del pias 
	 * @return Serialized correspondiente al tipo de elemento
	 * @throws DAOServiceException en caso de error al consultar Serialized por serial y tipo de elemento
	 * @throws DAOSQLException en caso de error al consultar Serialized por serial y tipo de elemento
	 * @author clopez
	 */
	public Serialized getSerializedBySerialAndElementsTypes (String serialCode, String elementsTypes, Long countryId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Consulta el elemento vinculado serializado asociado al serial y al tipo de elemento
	 * @param serialCode - String serial a consultar
	 * @param elementType - Identificador del tipo del elemento
	 * @return Serialized correspondiente al tipo de elemento
	 * @throws DAOServiceException en caso de error al consultar Serialized por serial y tipo de elemento
	 * @throws DAOSQLException en caso de error al consultar Serialized por serial y tipo de elemento
	 * @author gfandino
	 */
	public List<Serialized> getSerialLinkedBySerialAndElementType (String serialCode,Long elementType,Long countryId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta elementos serializados que cumplan con filtros
	 * de elementId, isSerialized y serialCode
	 * @param elementId Long - 
	 * @param serialCode String -
	 * @return List<Serialized> - Lista de objetos serializados que cumplan con el filtro
	 * @throws DAOServiceException 
	 * @throws DAOSQLException 
	 * @author jalopez
	 */
	public List<Serialized> getSerializedByElementIdBySerialCode(Long elementId,String serialCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo de consulta de elementos serializados que obtiene el objeto serializado y la fecha de entrada ala bodega
	 * @param warehouseId Bodega en donde se van a buscar los elementos serializados 
	 * @return List<Object[] lista de elementos serialziados disponibles en una bodega junto con la fecha de ingreso a la bodega
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jnova
	 */
	public List<Object[]> getSerializedsAndWhEntryDateByWareHouseId( Warehouse warehouseId ) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Permite consultar los elementos vinculados de un serializado
	 * @param serialized - Long identificador del serializado
	 * @return  List<SerializedVO> lista de elementos serializados vinculados al serializado especificado;vacio en otro caso
	 * @throws DAOServiceException en caso de error en la consutla de elementos vinculados serialzados
	 * @throws DAOSQLException en caso de error en la consutla de elementos vinculados serialzados
	 * @author
	 */
	public List<Serialized> getLinkedSerializedBySerializedId(Long serialized)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene los elementos serializados a partir del id de un registro de importacion
	 * @param idImportLog
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<Serialized> getSerializedElementsByImportLogId(Long idImportLog)throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Control Cambios Creacion Contacts
	 * Retorna un elemento serializado diltrando por
	 * el Element.
	 * @param elementId Long - id del elementto
	 * @return Serialized
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jalopez
	 */
	public Serialized getSerializedElementByElementId(Long elementId) throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Obtiene la información de un Serialized por su serial
	 * @param serial - String serial del Serialized a ser consultado
	 * @param typeCode id del tipo del elemento
	 * @return Serialized objeto con la información del Serialized dado su serial, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Serialized por serial
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Serialized por serial 
	 * @author jnova
	 */
	public Serialized getSerializedBySerialAndTypeId(String serial,Long typeId,Long countryId)throws DAOServiceException, DAOSQLException;
//	
	/**
	 * Metodo: Retorna un listado de elmentos serializados filtrados
	 * por el serial del elmento vinculado.
	 * @param linkedSerialCode String
	 * @return List<Serialized>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jalopez
	 */
	public List<Serialized> getLinkedSerializedBySerialCode(String linkedSerialCode,Long countryId)throws DAOServiceException, DAOSQLException;
	
	
	public Map<String, Object[]> getSerialExists(List<String> serialCodes,Long countrId) throws DAOServiceException, DAOSQLException ;
	
	public List<String> getLinkedSerialExists(List<String> serialized,Long countrId) throws DAOServiceException, DAOSQLException ;

}