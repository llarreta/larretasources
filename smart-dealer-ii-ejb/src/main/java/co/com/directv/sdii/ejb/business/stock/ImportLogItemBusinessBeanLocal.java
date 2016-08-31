package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.ConfirmSerializedElementItemFromImportLogDTO;
import co.com.directv.sdii.model.dto.ImportLogElementsFilterDTO;
import co.com.directv.sdii.model.dto.MassiveMovementBetweenWareHouseDTO;
import co.com.directv.sdii.model.dto.ModifyImportLogItemDTO;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.ElementStatus;
import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.pojo.ImportLogItem;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.collection.ImportLogItemResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.ImportLogItemVO;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.reports.dto.SerializedElementImportLogDTO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ImportLogItem.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ImportLogItemBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ImportLogItemVO
	 * @param obj objeto que encapsula la información de un ImportLogItemVO
	 * @throws BusinessException en caso de error al ejecutar la creación de ImportLogItem
	 * @author gfandino
	 */
	public void createImportLogItem(ImportLogItemVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ImportLogItemVO
	 * @param obj objeto que encapsula la información de un ImportLogItemVO
	 * @throws BusinessException en caso de error al ejecutar la actualización de ImportLogItem
	 * @author gfandino
	 */
	public void updateImportLogItem(ImportLogItemVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Borra de la persistencia la información de un ImportLogItemVO
	 * @param obj información del ImportLogItemVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la eliminación de ImportLogItem
	 * @author gfandino
	 */
	public void deleteImportLogItem(ImportLogItemVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite eliminar de un registro de importacion el importLogItem 
	 * @param warehouse
	 * @param importLog
	 * @param importLogItem
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void deleteElementItemImportLog(ImportLog importLog, List<ImportLogItemVO> deleteItems, User user) throws BusinessException;
	
	
	/**
	 * Operacion encargada de la creacion de nuevos items en el registro de importacion 
	 * @param importLogItems
	 * @param importLogId
	 * @param itemStatus
	 * @param isFinished
	 * @param user
	 * @throws BusinessException
	 * @author waguilera
	 */
	public void createNewImportLogElementItems(List<ImportLogItemVO> importLogItems,ImportLogVO importLogId, ItemStatus itemStatus,boolean isFinished, User user) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un ImportLogItemVO por su identificador
	 * @param id identificador del ImportLogItemVO a ser consultado
	 * @return objeto con la información del ImportLogItemVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de ImportLogItemVO por ID
	 * @author gfandino
	 */
	public ImportLogItemVO getImportLogItemByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ImportLogItemVO almacenados en la persistencia
	 * @return Lista con los ImportLogItemVO existentes, una lista vacia en caso que no existan ImportLogItemVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la consulta de todos los ImportLogItemVO 
	 * @author gfandino
	 */
	public List<ImportLogItemVO> getAllImportLogItems() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los ImportLogItemVO de un ImportLog especificado
	 * @param idImportLog - Long identificador del ImportLog que se va a consultar
	 * @param RequestCollectionInfo requestCollInfo, parametros para paginacion.
	 * @return ImportLogItemResponse, List<ImportLogItemVO> correspondiente al importlog; vacio en caso contrario
	 * @throws BusinessException en caso de error al ejecutar la consulta de ImportLogItemVO por importLog
	 * @author gfandino
	 */
	public ImportLogItemResponse getImportLogItemsByImportLog(Long idImportLog, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: CU INV - 03 Confirmar los elementos No serializados de un registro
	 * de importación<br>
	 * Obtiene una lista de los ítems de un registro de importación
	 * dado el identificador del registro de importación y la distición de si
	 * es serializado o nó
	 * @param importLogId identificador del importLog
	 * @param isSerialized define si se consultan los elementos serializados o nó
	 * @return Lista con los items del registro de importación
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	/*public ImportLogItemResponse getImportLogItemsByImportLogIdAndIsSerialized(
			Long importLogId, boolean isSerialized,RequestCollectionInfo requestCollInfo)throws BusinessException;*/
	
	/**
	 * Metodo:CU INV - 03 Confirmar los elementos No serializados de un registro
	 * de importación<br>
	 * Operación para confirmar los elementos de un registro de importación<br>
	 * Las reglas del negocio son las siguientes:<br><br>
	 * 
	 *  OPCION 1 - Confirmar el recibido de todos los elementos <br>
	 * (para cuando las cantidades recibidas de todos los elementos no serializados coinciden con
	 *  las cantidades esperadas)<br>
	 *  En este caso se debe enviar desde la vista en el campo confirmedQuantity el mismo número 
	 *  de elementos que tiene el campo amountExpected<br><br>
	 * 
	 *  OPCION 2 - Confirmar el recibido total de un elemento <br>
	 * (para cuando las cantidades recibidas de un elemento coinciden con la cantidad esperada)<br>
	 * En este caso dentro de la lista solo vienen los elementos a confirmar y el campo
	 * confirmedQuantity tiene el mismo valor que el campo amountExpected<br><br>
	 * 
	 *  OPCION 3 - Confirmar el recibido parcial de un elemento <br>
	 * (para cuando el usuario ingresa una cantidad de elementos recibidos, pero que todavia no completan la cantidad esperada)<br>
	 * En este caso se envía una cantidad diferente en el campo confirmedQuantity al campo amountExpected<br><br>
	 * 
	 * <b>Por cada Elemento de la lista se evaluara:</b><br><br>
	 * 
	 * 1. Si el elemento tiene en el campo <code>confirmedQuantity</code> el mismo valor que el campo
	 * <code>amountExpected</code>:<br>
	 *  Si no había confirmación parcial del elemento, adiciona una confirmación igual a la cantidad esperada  Confirmado Parcial en la tabla IMP_LOG_CONFIRMATIONS<br>
	 *  Si ya había confirmación parcial del elemento, adiciona una nueva confirmación parcial para ese elemento de tal forma que la suma de las confirmaciones parciales sea igual a la cantidad esperada de ese elemento<br>
	 *  Modifica el estado del los elementos no serializados del registro de Importación a "recepcionado" es decir cambia el valor de la columna ITEM_STATUS_ID en la tabla IMPORT_LOG_ITEMS<br><br>
	 * 
	 * 2. Si el elemento tiene en el campo <code>confirmedQuantity</code> el una cantidad diferente al campo <code>amountExpected</code>:<br>
	 *  Se realiza la siguiente validación:<br>
	 *    -La cantidad ingresada en la cantidad confirmada no puede superar la cantidad esperada.<br>
	 *    -La suma de las confirmaciones parciales no puede superar la cantidad esperada<br>
	 *  Se adiciona una confirmación parcial con la cantidad ingresada por el usuario<br> 
	 *  Si la suma de las confirmaciones parciales del elemento  es igual a la cantidad esperada de ese elemento modifica el estado del elemento a recepcionado<br>
	 *  Si la suma de las confirmaciones parciales del elemento no  es igual a la cantidad esperada de ese elemento modifica el estado del elemento a Confirmado Parcial<br><br>
	 *
	 * <b> Una vez terminada la iteración sobre la lista de ítems, se ejecutan las siguientes tareas:</b><br><br>
	 *  Si todos los elementos del Registro de Importación ya han sido confirmados, modifica el estado del regisro de Importación a Recepcionado y el Sistema usa el caso de uso INV-08 para informar al Analista de Logística que el Registro de Importación ha sido recepcionado<br>
	 *  Si faltan elementos del registro de Importación por confirmar modifica el estado del registro de Importación a Confirmado parcial<br>
	 *  Invocar el caso de uso INV-06 para mover todos los elementos no serializados, que no habían sido confirmados anteriormente, a la bodega de Control de Calidad del Operador logístico<br>
	 *  Invocar el caso de uso INV-07 para informar al IBS el movimiento de los elementos no serializados que no habían sido confirmados anteriormente, a bodega
	 * @param impLogItems2Confirm lista con los ítems a confirmar, cada ítem debe especificar la cantidad
	 * confirmada del elemento relacionado
	 * @param userId identificador del usuario que realiza la acción
	 * @param logisticOperatorDealerId identificador del operador logistico
	 * @throws BusinessException En caso de error al realizar la operación
	 * @author jjimenezh
	 */
	public void confirmNotSerializedImportLogItems(List<ImportLogItemVO> impLogItems2Confirm, Long userId, Long logisticOperatorDealerId, boolean isWHQ) throws BusinessException;
	
	/**
	 * Metodo: UC_INV_04 Permite confirmar un elemento serializado de un registro de importación
	 * @param importLog
	 * @param item
	 * @param qaControl
	 * @param user
	 * @throws BusinessException
	 * @author Jimmy Vélez Muñoz
	 */
	public void  confirmSerializedElementItemFromImportLog(ConfirmSerializedElementItemFromImportLogDTO dto)throws BusinessException;
	
	
	
	/**
	 * Metodo: CU INV - 03 Confirmar los elementos No serializados de un registro
	 * de importación<br>
	 * Permite reportar la inconsistencia en un registro de importación<br>
	 * Se deben realizar los siguientes pasos:<br><br>
	 *  Modificar el estado del registro de Importación a Inconsistente<br>
	 *  Actualizar los campos de la inconsistencia, y se genera un registro en la tabla import_log_inconsistencies<br>
	 *  Invocar el caso de uso INV-08 para informar al Analista de Logística informando novedad de inconsistencia en el Registro de Importación<br> 
	 * @param inconsistentLogItems Lista con los ítems que se reportan como inconsistentes
	 * @param comment comentario que se persistira en el registro de inconsistencia
	 * @param userId identificador del usuario que realiza el reporte de la inconsistencia
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void reportInconsistentItems(List<ImportLogItemVO> inconsistentLogItems, String comment, Long incTypeId, Long userId)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los ImportLogItem con sus confirmaciones
	 * CU INV - 04
	 * @param idImportLog - Long  identificadot del ImportLog
	 * @return List<ImportLogItemVO> correspondiente al importlog especificado; vacio en otro caso
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de los ImportLogItem por ImportLog
	 * @author jalopez
	 */
	public ImportLogItemResponse getImportLogItemsByImportLogWithConfirmations(Long idImportLog,RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Confirma los elementos serializados de un registro de importacion
	 * CU INV - 04
	 * @param List<ImportLogItemVO>  Elementos de un registro de importacion
	 * @param Long pImpLogId, id del registro de importacion
	 * @param List<ImportLogItemVO> pImpLogItems, elementos individuales del registro de importacion
	 * @param logisticOpId - Long identificador del pa�s
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de los ImportLogItem por ImportLog
	 * @author jalopez
	 */
	public void confirmSerializedElementsImportLog(Long pImpLogId, List<ImportLogItemVO> pImpLogItems,Long logisticOpId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Crear la inconsistencia del registro de importacion
	 * CU INV - 04
	 * @param pImpLogId Long, id del registro de importacion
	 * @param impLogItems List<ImportLogItemVO>, items del registro de importacion
	 * @param comment String, Comentario de la inconsistencia
	 * @param pUserId Long, usuario que creo el registro de inconsistencia
	 * @param Long incType, id del tipo de inconsistencia
	 * @param logisticOpId - Long identificador del pa�s
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void reportInconsistentSerializedImpLogElements(Long pImpLogId, List<ImportLogItemVO> impLogItems, String comment,Long pUserId, Long incType,Long logisticOpId) throws BusinessException;

	/**
	 * Metodo: <Descripcion>
	 * @param logisticOperatorDealerId
	 * @param whTypeCode
	 * @return
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 */
	public Warehouse getLogisticOperatorWarehouse( Long logisticOperatorDealerId, String whTypeCode) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException;
	
	
	/**
	 * Metodo: Confirma los elementos serializados y no serializados de un registro de importación
	 * @param impLogItems2Confirm lista con los elementos serializados y no serializados
	 * @param userId identificador del usuario autenticado en el sistema
	 * @param logisticOperatorDealerId identificador del operador logístico al que se le enviarán los elementos confirmados
	 * @throws BusinessException En caso de error al realizar la confirmación
	 * @author jjimenezh
	 */
	public void confirmSerializedAndNotSerializedImportLogItems(List<ImportLogItemVO> impLogItems2Confirm, Long userId,	Long logisticOperatorDealerId, Dealer dealer)throws BusinessException;
	
	/**
	 * Metodo: Actualiza los items de un registro de importacion a partir del id del registro de importacion
	 * @param importLogId Long id del registro de importacion
	 * @param newImportLogItemStatusCode String Codigo del nuevo estado de los items del registro 
	 * @throws BusinessException En caso de error en la actualizacion
	 * @author jnova
	 */
	public void updateImportLogItemByImportLogId(Long importLogId , String newImportLogItemStatusCode) throws BusinessException;
	
	/**
	 * Metodo: Crea Objeto MassiveMovementBetweenWareHouseDTO, para ser enviado a una bodega
	 * @param elements List<ElementVO> Elementos que van a ser almacenados o transmitidos a una bodega
	 * @param wareHouseSource WarehouseVO Bodega Fuente
	 * @param wareHouseTarget WarehouseVO Bodega Destino
	 * @return MassiveMovementBetweenWareHouseDTO @see MassiveMovementBetweenWareHouseDTO    
	 * @author hcorredor
	 */
	public MassiveMovementBetweenWareHouseDTO buildMassiveMovementDTO(List<ElementVO> elements, WarehouseVO wareHouseSource, WarehouseVO wareHouseTarget) throws PropertiesException;
	
	/**
	 * Metodo: Metodo utilitario que retorna la lista de elementos de una lista de ImportLogItemVO
	 * @param impLogItems2Confirm List<ImportLogItemVO> Elementos de tipo ImportLogItemVO
	 * @param isSerializable boolean, indicando si debe retornar elementos serializados o no.
     * @return List<ElementVO>, lista de elementos a mover en la bodega.
	 * @author hcorredor
	 */
	public List<ElementVO> getListElementVO(List<ImportLogItemVO> impLogItems2Confirm);
	
	/**
	 * 
	 * @param pImpLogId
	 * @param pImpLogItems
	 * @throws BusinessException
	 */
	public void confirmSerializedElementsImportLog(Long pImpLogId, List<ImportLogItemVO> pImpLogItems, Dealer dealer) throws BusinessException;

	/**
	 * 
	 * @param modifyImportLogItemCriteria
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 */
	public ImportLogItemResponse getImportLogItemsByCriteria(ModifyImportLogItemDTO modifyImportLogItemCriteria, RequestCollectionInfo requestCollInfo) throws BusinessException;

	
    /**
     * 
     * @param importLogItemVO
     * @throws BusinessException
     */
	void updateQuantityItemImportLogNotSerializedElements(List <ImportLogItemVO> importLogItemVO) throws BusinessException;

	/**
	 * 
	 * @param impLogItems2Confirm
	 * @param userId
	 * @param logisticOperatorDealerId
	 * @param company
	 * @param Cod_WHDestiny
	 * @throws BusinessException
	 */
	void confirmNotSerializedImportLogItemsCompany(
			List<ImportLogItemVO> impLogItems2Confirm, Long userId,
			Long logisticOperatorDealerId, long company, String Cod_WHDestiny)
			throws BusinessException;

	/**
	 * 
	 * @param impLogItems2Confirm
	 * @param userId
	 * @param logisticOperatorDealerId
	 * @param logisticOperator
	 * @param codWHDestiny
	 * @throws BusinessException
	 */
	void confirmNotSerializedImportLogItemsLogisticOperator(
			List<ImportLogItemVO> impLogItems2Confirm, Long userId,
			Long logisticOperatorDealerId, long logisticOperator,
			String codWHDestiny) throws BusinessException;

	
	/**
	 * 
	 * @param filterImportLogElements
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	public List<SerializedElementImportLogDTO> getImportLogItemsByImportLogIdAndIsSerializedForExcel(
			ImportLogElementsFilterDTO filterImportLogElements) throws BusinessException;
	
	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param importLogId
	 * @param isSerialized
	 * @param requestCollInfo
	 * @return <tipo> <descripcion>
	 * @author
	 * @throws BusinessException 
	 */
	public ImportLogItemResponse getImportLogItemsByImportLogIdAndIsSerialized(
			ImportLogElementsFilterDTO filterImportLogElements,
			RequestCollectionInfo requestCollInfo) throws BusinessException;

	/**
	 * 
	 * Metodo: Método encargado de calcular la ubicacions de transito
	 * @param logisticOperatorDealerId
	 * @param whTypeCode
	 * @return
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 */
	public Warehouse getWarehouse(
			Long logisticOperatorDealerId, String whTypeCode) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException;

	
	/**
	 * 
	 * Metodo: Realiza los movimientos de los elementos de un registro de importacion a la bodega enviada por parametro
	 * @param importLogId identificador del registro de importacion
	 * @param targetWhId identificador de la bodega de destino a la cual se envian los elementos
	 * @param movTypeCodeE codigo de tipo movimiento de entrada
	 * @param movTypeCodeS codigo de tipo de movimiento de salida
	 * @param processCode
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public void finishImportLog(User user, Long importLogId, Warehouse targetWhId, String movTypeCodeE, String movTypeCodeS, String processCode) throws BusinessException;
	

	/**
	 * Operacion encargada de actualizar los items de un registro de importación
	 * cuando se modifica
	 * @param importLogItemListUpdate
	 * @param elementStatus
	 * @param itemStatus
	 * @param importLog
	 * @throws BusinessException
	 * @author waguilera
	 */
	public void updateElementItemImportLog(List<ImportLogItemVO> importLogItemListUpdate, ElementStatus elementStatus, ItemStatus itemStatus, ImportLog importLog) throws BusinessException;
	
	
	public void createElementSerializedForNewImportLog(ImportLogItemVO importLogItem, 
			ElementStatus elementStatus, ImportLog importLog, ItemStatus itemStatus, 
			boolean isSendStatus, boolean isNewItems, MovementElementDTO dtoGenerics, User user,
			Warehouse warehouseTarget)  throws BusinessException;
	
	
	public void createElementNotSerializedForNewImport(ImportLogItemVO importLogItem, 
			ElementStatus elementStatus, ImportLog importLog, ItemStatus itemStatus, 
			boolean isSendStatus, boolean isNewItems, MovementElementDTO dtoGenerics, User user,
			Warehouse warehouseTarget)  throws BusinessException;
	
	public void setElementSerializedInWareHouseTransit(User user, ImportLogItem importLogItem,
			ImportLog importLog, Warehouse warehouseTarget, ItemStatus itemStatus, MovementElementDTO dtoGenerics) throws BusinessException;
	
	
	public void setElementNotSerializedInWareHouseTransit(User user, ImportLogItem importLogItem,
			ImportLog importLog, Warehouse warehouseTarget, ItemStatus itemStatus, MovementElementDTO dtoGenerics) throws BusinessException;
	
	/**
	 * Operacion encargada de la acutualización del estado de un item de un registro
	 * de importación en una nueva transaccion
	 * @param obj
	 * @throws BusinessException
	 * @author waguilera
	 */
	public void updateImportLogItemStatus(ImportLogItem importLogItem) throws BusinessException;
}
