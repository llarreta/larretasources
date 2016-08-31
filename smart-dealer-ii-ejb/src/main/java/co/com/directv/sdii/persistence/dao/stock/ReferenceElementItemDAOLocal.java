package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.ReferenceElementItem;
import co.com.directv.sdii.model.pojo.collection.ElementMixedResponse;
import co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;
import co.com.directv.sdii.model.vo.ReferenceVO;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ReferenceElementItem
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ReferenceElementItemDAOLocal {

	/**
	 * Metodo:  persiste la información de un ReferenceElementItem
	 * @param obj objeto que encapsula la información de un ReferenceElementItem
	 * @throws DAOServiceException en caso de error al ejecutar la creación de ReferenceElementItem
	 * @throws DAOSQLException en caso de error al ejecutar la creación de ReferenceElementItem
	 * @author gfandino
	 */
	public void createReferenceElementItem(ReferenceElementItem obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ReferenceElementItem
	 * @param obj objeto que encapsula la información de un ReferenceElementItem
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de ReferenceElementItem
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de ReferenceElementItem
	 * @author gfandino
	 */
	public void updateReferenceElementItem(ReferenceElementItem obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ReferenceElementItem de manera masiva para una remision
	 * @param referenceVO objeto que encapsula la información de la remision a actualizar
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de ReferenceElementItem
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de ReferenceElementItem
	 * @author JFP
	 */
    public void updateMasiveReferenceElementItem(ReferenceVO referenceVO, Long newCodeItemStatus) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ReferenceElementItem
	 * @param obj información del ReferenceElementItem a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de ReferenceElementItem
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de ReferenceElementItem
	 * @author gfandino
	 */
	public void deleteReferenceElementItem(ReferenceElementItem obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ReferenceElementItem por su identificador
	 * @param id identificador del ReferenceElementItem a ser consultado
	 * @return objeto con la información del ReferenceElementItem dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ReferenceElementItem por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ReferenceElementItem por ID
	 * @author gfandino
	 */
	public ReferenceElementItem getReferenceElementItemByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ReferenceElementItem almacenados en la persistencia
	 * @return Lista con los ReferenceElementItem existentes, una lista vacia en caso que no existan ReferenceElementItem en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los ReferenceElementItem
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los ReferenceElementItem
	 * @author gfandino
	 */
	public List<ReferenceElementItem> getAllReferenceElementItems() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los ReferenceElementItem almacenados en la persistencia asociados a una remisión
	 * @param refID - Long Identificador de la remisión
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return ReferenceElementItemsResponse, List<ReferenceElementItem> Asociados a la remisión especificada; vacio en caso contrario
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @author gfandino
	 */
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceID(Long refID, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;

	
	/**
	 * Metodo: Actualiza el estado de todos los objetos ReferenceElementItem asociados a un Reference
	 * @param ReferenceElementItem - ReferenceElementItem el objeto Reference al que pertencen los objetos ReferenceElementItem
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @author garciniegas
	 */
	public void updateElementItemStatusToReferenceShipment( ReferenceElementItem reference )throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Consulta los elementos NO serializados de una remision
	 * @param referenceId Long ID de la remision de la cual se quieren obtener los elementos No serializados
	 * @return List<ReferenceElementItem> Lista de elementos no serializados de la remision
	 * @throws DAOServiceException En caso de error al ejecutar la consulta de los elementos
	 * @throws DAOSQLException En caso de error al ejecutar la consulta de los elementos
	 * @author jnova
	 */
	public List<ReferenceElementItem> getNotSerializedReferenceElementItemByReferenceId(Long referenceId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Consulta los elementos serializados de una remision
	 * @param referenceId Long ID de la remision de la cual se quieren obtener los elementos serializados
	 * @return List<ReferenceElementItem> Lista de elementos no serializados de la remision
	 * @throws DAOServiceException En caso de error al ejecutar la consulta de los elementos
	 * @throws DAOSQLException En caso de error al ejecutar la consulta de los elementos
	 * @author jnova
	 */
	public List<ReferenceElementItem> getSerializedReferenceElementItemByReferenceId(Long referenceId)throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Consulta los elementos serializados de una remision
	 * @param referenceId Long ID de la remision de la cual se quieren obtener los elementos serializados
	 * @return List<ReferenceElementItem> Lista de elementos no serializados de la remision
	 * @throws DAOServiceException En caso de error al ejecutar la consulta de los elementos
	 * @throws DAOSQLException En caso de error al ejecutar la consulta de los elementos
	 * @author JPose
	 */
	public List<ReferenceElementItem> getSerializedReferenceElementItemByReferenceIdSQL(Long referenceId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Actualiza las cantidades de los objetos ReferenceElementItem asociados a un Reference
	 * Caso de uso INV 37
	 * @param reference - Reference el objeto Reference al que pertencen los objetos ReferenceElementItem
	 * @param items - los objetos ReferenceElementItem que seran actualizados
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @author garciniegas
	 */
	public void modifyReferenceElementItemCuantityByReferenceAndItemList( List<ReferenceElementItem>items,Reference reference,Boolean finished )throws DAOServiceException, DAOSQLException;

	
	/**
	 * Metodo: adiciona objetos ReferenceElementItem asociados a un Reference
	 * Caso de uso INV 37
	 * @param reference - Reference el objeto Reference al que pertencen los objetos ReferenceElementItem
	 * @param items - los objetos ReferenceElementItem que seran adicionados
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @author garciniegas
	 */
	public void addReferenceElementItemToReference( List<ReferenceElementItem>items,Reference reference )throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene los items de una remisión dado el identificador del elemento
	 * @param elementId identificador del elemento
	 * @param referenceId 
	 * @return Elemento de remisión, nulo en caso que no se encuentre el elemento de remisión con el id del elemento especificado
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException  en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public ReferenceElementItem getReferenceElementItemByElementIdAndReferenceId(Long elementId, Long referenceId)throws DAOServiceException, DAOSQLException; 
	
	/**
	 * Metodo: Obtiene los elementos de una remision de acuerdo a un estado pasado por parametro
	 * @param referenceId Long ID de la remision
	 * @param elementItemStatus String Estado de los elementos
	 * @return List<ReferenceElementItem> Lista de elementos de una remision que se encuentran en un estado especifico
	 * @throws DAOServiceException En caso de error al ejecutar la consulta
	 * @throws DAOSQLException En caso de error al ejecutar la consulta
	 */
	public List<ReferenceElementItem> getReferenceElementItemByReferenceIdAndByItemStatusAndElementType(Long referenceId , String elementItemStatus,String elementType) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene los elementos de una remisión dado el identificador de la remisión y el identificador del elemento
	 * @param referenceId identificador de la remisión
	 * @param elementId identificador del elemento
	 * @return Información del objeto de la remisión
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jjimenezh
	 */
	public ReferenceElementItem getReferenceElementItemByReferenceIdAndElementId(Long referenceId, Long elementId) throws DAOServiceException, DAOSQLException; 
	
	
	/**
	 * Metodo: Obtiene los elementos de una remisión dado el identificador de la bodega destino de la remisión y el identificador del elemento
	 * @param referenceId identificador de la remisión
	 * @param elementId identificador del elemento
	 * @return Información del objeto de la remisión
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jjimenezh
	 */
	public List<ReferenceElementItem> getReferenceElementItemByRefTargetWhIdAndElementId(Long targetWhId, Long elementId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la cantidad de de un elemento en una remision
	 * @param referenceId
	 * @param elementId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jnova
	 */
	public Double getReferenceElementItemQuantityByReferenceAndElement(Long referenceId, Long elementId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene los elementos No serializados de una remision de acuerdo que no estan en el estado pasado por parametro
	 * @param referenceId Long ID de la remision
	 * @param elementItemStatus String Estado de los elementos
	 * @return List<ReferenceElementItem> Lista de elementos de una remision que se encuentran en un estado especifico
	 * @throws DAOServiceException En caso de error al ejecutar la consulta
	 * @throws DAOSQLException En caso de error al ejecutar la consulta
	 * @author jnova
	 */
	public List<ReferenceElementItem> getNotSerializeReferenceElementItemByReferenceIdAndByNotItemStatus(Long referenceId , String elementItemStatus) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene los elementos serializados de una remision de acuerdo que no estan en el estado pasado por parametro
	 * @param referenceId Long ID de la remision
	 * @param elementItemStatus String Estado de los elementos
	 * @return List<ReferenceElementItem> Lista de elementos de una remision que se encuentran en un estado especifico
	 * @throws DAOServiceException En caso de error al ejecutar la consulta
	 * @throws DAOSQLException En caso de error al ejecutar la consulta
	 * @author jnova
	 */
	public List<ReferenceElementItem> getSerializeReferenceElementItemByReferenceIdAndByNotItemStatus(Long referenceId , String elementItemStatus) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene los elementos de una remision de acuerdo que no estan en el estado pasado por parametro
	 * @param referenceId Long ID de la remision
	 * @param elementItemStatus String Estado de los elementos
	 * @return List<ReferenceElementItem> Lista de elementos de una remision que se encuentran en un estado especifico
	 * @throws DAOServiceException En caso de error al ejecutar la consulta
	 * @throws DAOSQLException En caso de error al ejecutar la consulta
	 * @author jnova
	 */
	public List<ReferenceElementItem> getReferenceElementItemByReferenceIdAndByNotItemStatus(Long referenceId , String elementItemStatus) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: <Descripcion>
	 * @param refElementItemId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public String getItemStatusCodeByRefElementItemId(Long refElementItemId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de los ReferenceElementItemVO almacenados en la persistencia correspondientes a una remisión
	 * filtrados por serial o por tipo de elemento para elementos serializados
	 * @param refID - Long Identificador de la remisión
	 * @param serial - String serial del elemento
	 * @param elementTypeId - Long Identificador del tipo de elemento
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return ElementMixedResponse
	 * @throws DAOServiceException En caso de error al ejecutar la consulta
	 * @throws DAOSQLException En caso de error al ejecutar la consulta
	 * @author waguilera
	 */
	public ElementMixedResponse getReferenceElementsSerializedByReferenceIdAndSerialOrElementType(
			Long refID, String serial, Long elementTypeId, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de los ReferenceElementItemVO almacenados en la persistencia correspondientes a una remisión
	 * por tipo de elemento para elementos no serializados
	 * @param refID - Long Identificador de la remisión
	 * @param serial - String serial del elemento
	 * @param elementTypeId - Long Identificador del tipo de elemento
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return ElementMixedResponse
	 * @throws DAOServiceException En caso de error al ejecutar la consulta
	 * @throws DAOSQLException En caso de error al ejecutar la consulta
	 * @author waguilera
	 */
	public ElementMixedResponse getReferenceElementsNotSerializedByReferenceIdElementType(
			Long refID, Long elementTypeId, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Busca los elementos de la remisión que fueron agregados como resultado del cierre
	 * de una inconsistencia de mas elementos físicos
	 * @param refInconsistencyId identificador de la inconsistencia cerrada
	 * @return listado de elementos de remisión que fueron agregados
	 * @throws DAOServiceException En caso de error al ejecutar la consulta
	 * @throws DAOSQLException En caso de error al ejecutar la consulta
	 * @author wjimenez
	 */
	public List<ReferenceElementItemVO> getReferenceElementsByRefInconsistencyId(Long refInconsistencyId)
			throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Consulta los elementos de una remision paginados
	 * @param referenceId Long ID de la remision de la cual se quieren obtener los elementos serializados
	 * @param RequestCollectionInfo objeto de paginacion
	 * @param isSerialized indica si se consultan los elementos serializados o los no serializados de la remision
	 * @return List<ReferenceElementItem> Lista de elementos serializados o no serializados de la remision
	 * @throws DAOServiceException En caso de error al ejecutar la consulta de los elementos
	 * @throws DAOSQLException En caso de error al ejecutar la consulta de los elementos
	 * @author jnova
	 */
	public ReferenceElementItemsResponse getReferenceElementItemByReferenceId(Long referenceId, RequestCollectionInfo requestCollInfo,boolean isSerialized)throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Consulta los elementos de una remision paginados
	 * @param referenceId Long ID de la remision de la cual se quieren obtener los elementos serializados
	 * @param RequestCollectionInfo objeto de paginacion
	 * @param isSerialized indica si se consultan los elementos serializados o los no serializados de la remision
	 * @return List<ReferenceElementItem> Lista de elementos serializados o no serializados de la remision
	 * @throws DAOServiceException En caso de error al ejecutar la consulta de los elementos
	 * @throws DAOSQLException En caso de error al ejecutar la consulta de los elementos
	 * @author waguilera
	 */
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceIDSerAndNotSerPending(Long referenceId, RequestCollectionInfo requestCollInfo,String serialCode, boolean isSerialized)throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * 
	 * Metodo: Es el metodo encardado de consultar los elementos no serializados de una bodega que esten 
	 * en estado último
	 * @param wareHouseId
	 * @param requestCollInfo
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public ReferenceElementItemsResponse getNotSerizalizedElementByWarehouseIdAndLastStatus(Long wareHouseId, Long elementTypeId, String isPrepaid, RequestCollectionInfo requestCollInfo, Long modelId)throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Obtiene los elementos asociados a una remisión, adicionalmente se puede filtrar por serial
	 * @param refID
	 * @param serial
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<ReferenceElementItem> getReferenceElementsByReferenceIdAndSerial(Long refID, String serial) throws DAOServiceException, DAOSQLException;

	public boolean areAllReferenceElementsInState(Long refId, String status) throws DAOServiceException, DAOSQLException;

	public boolean areSomeReferenceElementsInState(Long refId, String status) throws DAOServiceException, DAOSQLException;

	public ReferenceElementItem getReferenceElementItemByElementTypeIdAndReferenceId(
			Long elementTypeId, Long referenceId) throws DAOServiceException,
			DAOSQLException;
	
}