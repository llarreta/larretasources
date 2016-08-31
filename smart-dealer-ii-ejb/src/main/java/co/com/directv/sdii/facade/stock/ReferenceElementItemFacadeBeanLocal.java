package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.collection.ReferenceElementItemsDTO;
import co.com.directv.sdii.model.pojo.collection.ElementMixedResponse;
import co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ReferenceElementItem.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ReferenceElementItemFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ReferenceElementItem
	 * @param obj - ReferenceElementItemVO  objeto que encapsula la información de un ReferenceElementItemVO
	 * @throws BusinessException en caso de error al ejecutar la creación de ReferenceElementItem
	 * @author gfandino
	 */
	public void createReferenceElementItem(ReferenceElementItemVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ReferenceElementItem
	 * @param obj - ReferenceElementItemVO  objeto que encapsula la información de un ReferenceElementItemVO
	 * @throws BusinessException en caso de error al ejecutar la actualización de ReferenceElementItem
	 * @author gfandino
	 */
	public void updateReferenceElementItem(ReferenceElementItemVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ReferenceElementItem
	 * @param obj - ReferenceElementItemVO  información del ReferenceElementItemVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la eliminación de ReferenceElementItem
	 * @author gfandino
	 */
	public void deleteReferenceElementItem(ReferenceElementItemVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ReferenceElementItem por su identificador
	 * @param id - Long identificador del ReferenceElementItem a ser consultado
	 * @return objeto con la información del ReferenceElementItemVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de ReferenceElementItem por ID
	 * @author gfandino
	 */
	public ReferenceElementItemVO getReferenceElementItemByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ReferenceElementItem almacenados en la persistencia
	 * @return List<ReferenceElementItemVO> Lista con los ReferenceElementItemVO existentes, una lista vacia en caso que no existan ReferenceElementItemVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la consulta de todos los ReferenceElementItem
	 * @author gfandino
	 */
	public List<ReferenceElementItemVO> getAllReferenceElementItems() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los ReferenceElementItemVO almacenados en la persistencia correspondientes a una remisión
	 * @param refID - Long Identificador de la remisión
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return ReferenceElementItemsResponse, List<ReferenceElementItemVO> correspondiente a la remisión especificada; vacio en otro caso
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ReferenceElementItem por remisión
	 * @author gfandino
	 */
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceID(Long refID, RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la lista de elementos no serializados de una remision
	 * @param referenceId Long ID de la remision de la que se desean obtener los elementos
	 * @return List<ReferenceElementItemVO> lista de elementos no serializados junto con su lista de confirmaciones parciales
	 * @throws BusinessException En caso de error en la consulta de elementos no serializados
	 * @author jnova
	 */
	public List<ReferenceElementItemVO> getNotSerializedReferenceElementItemByReferenceId(Long referenceId) throws BusinessException;
	
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
	
//	 /**
//     * Método: INV30 permite adicionar un elemento a una remisión
//     * @param refElementVO - ReferenceElementItemVO item a ingresar
//     * @throws BusinessException en caso de error adicionando el elemento a la remisión
//     * @author gfandino
//     */
//    public void addElementToReference (ReferenceElementItemVO refElementVO)throws BusinessException;
    
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
	 * @return ElementMixedResponse
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ReferenceElementItem por remisión
	 * @author wjimenez
	 */
	public ElementMixedResponse getReferenceElementsByReferenceIdAndSerialOrElementType(
			Long refID, String serial, Long elementTypeId, boolean isSerialized, RequestCollectionInfo requestCollInfo)throws BusinessException;
	
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
	public List<ReferenceElementItemVO> getAddedElements(Long refInconsistencyId) throws BusinessException;
	
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
	 * Metodo: Método encargado de agregar elementos no serializados a una remision a partir del serial
	 * El elemento debe existir en la bodega origen de la remision
	 * @param listElementNotSerializedToAdd
	 * @param referenceID
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public void addElementNotSerialized(
			List<ReferenceElementItemVO> listElementNotSerializedToAdd, Long referenceID, Long userId)throws BusinessException;

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

	
}
