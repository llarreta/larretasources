package co.com.directv.sdii.ejb.business.stock;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.MassiveMovementBetweenWareHouseDTO;
import co.com.directv.sdii.model.dto.collection.ReferenceElementItemsDTO;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.RecordStatus;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.ReferenceStatus;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.collection.ElementMixedResponse;
import co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ReferenceElementItem.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ReferenceElementItemBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ReferenceElementItemVO
	 * @param obj objeto que encapsula la información de un ReferenceElementItemVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la creación de ReferenceElementItem
	 * @author gfandino
	 */
	public void createReferenceElementItem(ReferenceElementItemVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ReferenceElementItemVO
	 * @param obj objeto que encapsula la información de un ReferenceElementItemVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la actualización de ReferenceElementItem
	 * @author gfandino
	 */
	public void updateReferenceElementItem(ReferenceElementItemVO obj) throws BusinessException;
	
	/**
	 * Metodo: Crea una lista de ítems de remisión a partir de una lista de origen de archivo plano, NO persiste la información en la base de datos
	 * @param refElements lista de elementos con la información enviada desde archivo plano
	 * @param itemStatusCode Código del estado del item a ser creado
	 * @param registeringReception indica si se está registrando la recepción de los elementos o si 
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jjimenezh
	 */
	public List<ReferenceElementItemVO> fillreferenceElementItems(List<ReferenceElementItemVO> refElements, String itemStatusCode, boolean registeringReception) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ReferenceElementItemVO
	 * @param obj información del ReferenceElementItemVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la eliminación de ReferenceElementItem
	 * @author gfandino
	 */
	public void deleteReferenceElementItem(ReferenceElementItemVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ReferenceElementItemVO por su identificador
	 * @param id identificador del ReferenceElementItemVO a ser consultado
	 * @return objeto con la información del ReferenceElementItemVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ReferenceElementItem por ID
	 * @author gfandino
	 */
	public ReferenceElementItemVO getReferenceElementItemByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ReferenceElementItemVO almacenados en la persistencia
	 * @return Lista con los ReferenceElementItemVO existentes, una lista vacia en caso que no existan ReferenceElementItemVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ReferenceElementItem
	 * @author gfandino
	 */
	public List<ReferenceElementItemVO> getAllReferenceElementItems() throws BusinessException;
	
	/**
	 * Metodo: INV30 Obtiene la información de los ReferenceElementItemVO almacenados en la persistencia correspondientes a una remisión
	 * @param refID - Long Identificador de la remisión
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return ReferenceElementItemsResponse, List<ReferenceElementItemVO> correspondiente a la remisión especificada; vacio en otro caso
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ReferenceElementItem por remisión
	 * @author gfandino
	 */
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceID(Long refID, RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	/**
	 * Metodo: Cambia el estado de los items asociados a una remisión al estado especificado
	 * @param referenceId información de la remisión
	 * @param newStatusId estado de los items
	 * @throws BusinessException en caso de error al cambiar los estados de los items
	 * @author jjimenezh
	 */
	public void changeStatusOfReferenceElements(Long referenceId, Long newStatusId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la lista de elementos no serializados de una remision junto con sus confirmaciones parcial
	 * @param referenceId Long ID de la remision de la que se desean obtener los elementos
	 * @return List<ReferenceElementItemVO> lista de elementos no serializados junto con su lista de confirmaciones parciales
	 * @throws BusinessException En caso de error en la consulta de elementos no serializados
	 * @author jnova
	 */
	public List<ReferenceElementItemVO> getNotSerializedReferenceElementItemByReferenceIdForDetails(Long referenceId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la lista de elementos serializados de una remision junto con sus confirmaciones parcial
	 * @param referenceId Long ID de la remision de la que se desean obtener los elementos
	 * @return List<ReferenceElementItemVO> lista de elementos no serializados junto con su lista de confirmaciones parciales
	 * @throws BusinessException En caso de error en la consulta de elementos no serializados
	 * @author jnova
	 */
	public List<ReferenceElementItemVO> getSerializedReferenceElementItemByReferenceIdForDetails(Long referenceId) throws BusinessException;
	
	/**
	 * Metodo: Actualiza el estado de los elementos de una remision
	 * @param referenceId Long Id de la remision
	 * @param newStatus Long Id del estado al que se van a pasar los elementos 
	 * @throws BusinessException En caso de error al actualizar el estado de los elementos
	 */
	public void updateElementItemsByReferenceId(Long referenceId , Long newStatus) throws BusinessException;
	
	/**
	 * Metodo: Actualiza el estado de los elementos serializados de una remision
	 * @param referenceId Long Id de la remision
	 * @param newStatus Long Id del estado al que se van a pasar los elementos 
	 * @throws BusinessException En caso de error al actualizar el estado de los elementos
	 * @author jnova
	 */
	public void updateSerializeElementItemsByReferenceId(Long referenceId , Long newStatus) throws BusinessException;
	
	/**
	 * Metodo: Actualiza el estado de los elementos no serializados de una remision
	 * @param referenceId Long Id de la remision
	 * @param newStatus Long Id del estado al que se van a pasar los elementos 
	 * @throws BusinessException En caso de error al actualizar el estado de los elementos
	 * @author jnova
	 */
	public void updateNotSerializeElementItemsByReferenceId(Long referenceId , Long newStatus) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la lista de elementos no serializados de una remision
	 * @param referenceId Long ID de la remision de la que se desean obtener los elementos
	 * @return List<ReferenceElementItemVO> lista de elementos no serializados junto con su lista de confirmaciones parciales
	 * @throws BusinessException En caso de error en la consulta de elementos no serializados
	 * @author jnova
	 */
	public List<ReferenceElementItemVO> getNotSerializedReferenceElementItemByReferenceId(Long referenceId) throws BusinessException;

	/**
	 * Metodo: Obtiene la lista de elementos serializados de una remision
	 * @param referenceId Long ID de la remision de la que se desean obtener los elementos
	 * @return List<ReferenceElementItemVO> lista de elementos no serializados junto con su lista de confirmaciones parciales
	 * @throws BusinessException En caso de error en la consulta de elementos no serializados
	 * @author jnova
	 */
	public List<ReferenceElementItemVO> getSerializedReferenceElementItemByReferenceId(Long referenceId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene los elementos asociados a una remision 
	 * @param referenceId Long Id de la remision
	 * @param elementItemStatus String Estado de elemento de la remision
	 * @param elementType String Tipo del elemento: serializado o no serializado
	 * @return List<ReferenceElementItemVO> Lista de elementos de la remision que se encuentran en el estado enviado
	 * @throws BusinessException En caso de error consultando los elementos
	 * @author jnova
	 */
	public List<ReferenceElementItemVO> getReferenceElementItemByReferenceIdAndByItemStatusAndElementType(Long referenceId , String elementItemStatus , String elementType) throws BusinessException;
	
	/**
	 * Metodo: Permite generar la recepción de elementos serializados de una remisión
	 * @param refElements - Elementos de la remisión
	 * @throws BusinessException En caso de error generanlo la recepción de elementos para la remisión
	 * @author gfandino
	 */
	public void receptRefSerialElementItem(List<ReferenceElementItemVO> refElements) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene la información de los ReferenceElementItemVO 
	 * almacenados en la persistencia correspondientes a una remisión.
	 * @param refID Long, id de la referencia
	 * @param requestCollInfo RequestCollectionInfo, parametros para la paginacion
	 * @return ReferenceElementItemsDTO
	 * @throws BusinessException 
	 * @author jalopez
	 */
	public ReferenceElementItemsDTO getReferenceElementSerializedNotSerialized(Long refID, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
//	/**
//     * Método: INV30 permite adicionar un elemento a una remisión
//     * @param refElementVO - ReferenceElementItemVO item a ingresar
//     * @param send - String indica si se debe generar el proceso de envío
//     * @throws BusinessException en caso de error adicionando el elemento a la remisión
//     * @author gfandino
//     * @author jnova
//     */
//    public void addElementToReference (ReferenceElementItemVO refElementVO)throws BusinessException;
//        
   /**
	 * 
	 * Metodo: Elimina un elemento de una remision y lo devuelve a la bodega de origen de la remision desde la bodega de 
	 * transito origen
	 * @param refId Identificador de la remision
	 * @param serialCode Serial del elemento que se va a eliminar de la remision
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public void removeElementOfReference(ReferenceElementItemVO item, Long userId)throws BusinessException;

	/**
	 * Metodo: Obtiene la información de los ReferenceElementItemVO almacenados en la persistencia correspondientes a una remisión
	 * filtrados por serial o por tipo de elemento en caso que se busquen elementos no serializados
	 * @param refID - Long Identificador de la remisión
	 * @param serial - String serial del elemento
	 * @param elementTypeId - Long Identificador del tipo de elemento
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return ReferenceElementItemsResponse, List<ReferenceElementItemVO> correspondiente a la remisión especificada; vacio en otro caso
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ReferenceElementItem por remisión
	 * @author wjimenez
	 */
	public ElementMixedResponse getReferenceElementsByReferenceIdAndSerialOrElementType(
			Long refID, String serial, Long elementTypeId, boolean isSerialized, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene el codigo de entrada de tipo ed movimiento para elementos de una remision
	 * @param reference remision
	 * @return String Codigo de tipo de movimiento
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public String getEntryTypeCodeByReference(Reference reference, boolean isBetweenDealers) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene el codigo de entrada de tipo ed movimiento para elementos de una remision
	 * @param reference remision
	 * @return String Codigo de tipo de movimiento
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public String getExitTypeCodeByReference(Reference reference, boolean isBetweenDealers) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Realiza el movimiento masivo de elementos serializados cuando se confirman masivamente
	 * @param reference remision a la cual se le confirman los elementos
	 * @param whSource bodega origen de la cual se van a mover los elementos
	 * @param whTargetId bodega destino a la cual se van a mover los elementos
	 * @param validateElementsToConfirm indica si se debe validar que la remision tenga elementos no serializados para confirmar
	 * @param isSerialized Indica si se vana a confirmar los elementos serializados o los no serializados
	 * @param userId id del usuario que realiza la confirmacion
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public void massiveMovementOfReferenceElementItems(MassiveMovementBetweenWareHouseDTO dto, Long userId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Permite confirmar parcialmente elementos asociados a una remision
	 * @param elements Elementos a confirmar
	 * @param userId usuario que realiza la confirmacion parcial
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public void partialReceptionOfReferenceElementItem(List<ReferenceElementItemVO> elements,Long userId) throws BusinessException;
	
	/**
	 * Metodo: Busca los elementos de la remisión que fueron agregados como resultado del cierre
	 * de una inconsistencia de mas elementos físicos
	 * @param refInconsistencyId identificador de la inconsistencia cerrada
	 * @return listado de elementos de remisión que fueron agregados
	 * @throws BusinessException
	 * @author wjimenez
	 */
	public List<ReferenceElementItemVO> getReferenceElementsByRefInconsistencyId(Long refInconsistencyId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta los elementos asociados a una remision
	 * @param refID identificador de la remision
	 * @param requestCollInfo objeto de paginacion
	 * @param isSerialized true si son serializados, false si no lo son
	 * @return ReferenceElementItemsResponse
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceIDAndSerOrNotSer(Long refID, RequestCollectionInfo requestCollInfo,boolean isSerialized)throws BusinessException;
	
	/**
	 * 
	 * Método encargado de agregar elementos serializados a una remision a partir del serial
	 * El elemento debe existir en la bodega origen de la remision
	 * @param serialCode
	 * @param isSerialized
	 * @param referenceID
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public void addElementSerialized(String serialCode, 
			Long referenceID, boolean isPrepaid, Long userId) throws BusinessException;

	/**
	 * 
	 * Método encargado de agregar elementos serializados a una remision a partir del serial
	 * El elemento debe existir en la bodega origen de la remision
	 * @param serialCode
	 * @param isSerialized
	 * @param referenceID
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public void addElementSerialized(String serialCode, 
			Long referenceID, Long userId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Método encargado de agregar elementos no serializados a una remision a partir del serial
	 * El elemento debe existir en la bodega origen de la remision
	 * @param listElementNotSerializedToAdd
	 * @param isPrepaid <tipo> <descripcion>
	 * @author waguilera
	 */
	public void addElementNotSerialized(
			List<ReferenceElementItemVO> listElementNotSerializedToAdd,
			Long referenceID, Long userId) throws BusinessException;
	
	public void addElementNotSerializedMassive(
			List<ReferenceElementItemVO> listElementNotSerializedToAdd,
			Long referenceID, User user,ItemStatus[] itemStatusNotSerialized,ReferenceStatus[] refStatus
			,RecordStatus[] recordStatusU,RecordStatus[] recordStatusH) throws BusinessException;

	/**
	 * 
	 * Metodo: operación encargada de retornar los elmentos no serializados agrupados
	 * que se encuentren en la ubicación origen de la remisión
	 * @param requestCollInfo
	 * @param referenceID
	 * @param isPrepaid
	 * @param elementTypeId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public ReferenceElementItemsResponse getElementNotSerializedFromWarehouse(RequestCollectionInfo requestCollInfo, Long referenceID,
			boolean isPrepaid, Long elementTypeId, Long modelId) throws BusinessException;

	/**
	 * Metodo: Obtiene los elementos de una remisión dado el identificador de la remisión y el identificador del elemento
	 * @param referenceId identificador de la remisión
	 * @param elementId identificador del elemento
	 * @return Información del objeto de la remisión
	 * @throws BusinessException
	 * @author jjimenezh
	 */
	public ReferenceElementItemVO getReferenceElementItemByReferenceIdAndElementId(
			Long referenceId, Long elementId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Método encargado de agregar elementos no serializados a la remisión
	 * No realiza movimientos de la tabla WAREHOUSE_ELEMENTS
	 * @param listElementNotSerializedToAdd
	 * @param reference
	 * @param itemStatus
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<ReferenceElementItemVO> addElementNotSerializedToReference(
			List<ReferenceElementItemVO> listElementNotSerializedToAdd, Reference reference, ItemStatus itemStatus, Long elementTypeId, Long sourceWh) throws BusinessException;
	
	
	/**
	 * 
	 * Metodo: Consulta los elementos asociados a una remision
	 * @param refID identificador de la remision
	 * @param requestCollInfo objeto de paginacion
	 * @param isSerialized true si son serializados, false si no lo son
	 * @return ReferenceElementItemsResponse
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceIDSerAndNotSerPending(Long refID, RequestCollectionInfo requestCollInfo,String serialCode, boolean isSerialized)throws BusinessException;
	
	/**
	 * Método encargado de adicionar elementos serializados a la remisión.
	 * @param serialCode
	 * @param ref
	 * @param user
	 * @throws BusinessException
	 * @author waguilera
	 */
	public void addElementSerialized(String serialCode, String serialCodeLinked, 
			Long ref, User user) throws BusinessException;
 
	public void addElementSerializedMassive(String serialCode, String serialCodeLinked, 
			Long ref, User user, HashMap<String, Reference> mapaReferencias, ReferenceStatus[] refStatus, ItemStatus[] itemStatus) throws BusinessException;
}
