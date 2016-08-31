package co.com.directv.sdii.ejb.business.stock;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.FilterReferencesToPrintDTO;
import co.com.directv.sdii.model.dto.InfoToPrintReferencesDTO;
import co.com.directv.sdii.model.dto.ReferenceShipmentDTO;
import co.com.directv.sdii.model.dto.ReferencesFilterDTO;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.RefConfirmation;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInReferencePaginationResponse;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInSelectReferenceToPrintPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.ReferenceResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SerializedElementInReferencePaginationResponse;
import co.com.directv.sdii.model.vo.DeliveryVO;
import co.com.directv.sdii.model.vo.RefConfirmationVO;
import co.com.directv.sdii.model.vo.RefInconsistencyVO;
import co.com.directv.sdii.model.vo.RefRecieveDataVO;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;
import co.com.directv.sdii.model.vo.ReferenceModificationVO;
import co.com.directv.sdii.model.vo.ReferenceStatusVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.model.vo.ReportedElementVO;
import co.com.directv.sdii.model.vo.SpecialCommentVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.model.vo.WarehouseVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad Reference.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ReferenceBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ReferenceVO
	 * @param obj objeto que encapsula la información de un ReferenceVO
	 * @throws BusinessException en caso de error al ejecutar la creación de Reference
	 * @author gfandino
	 */
	public void createReference(ReferenceVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ReferenceVO
	 * @param obj objeto que encapsula la información de un ReferenceVO
	 * @throws BusinessException en caso de error al ejecutar la actualización de Reference
	 * @author gfandino
	 */
	public void updateReference(ReferenceVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ReferenceVO
	 * @param obj información del ReferenceVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la eliminación de Reference
	 * @author gfandino
	 */
	public void deleteReference(ReferenceVO obj) throws BusinessException;
	
	/**
	 * Permite al eliminación de una remisión de manera lógica
	 * @param referenceID
	 * @param userDelete
	 * @throws BusinessException
	 */
	public Long deleteReferenceLogic(Long referenceID, Long userDelete) throws BusinessException;
	
	/**
	 * Metodo: INV 129 marca como eliminado un ReferenceVO
	 * @param obj información del ReferenceVO a ser marcado como eliminado
	 * @throws BusinessException en caso de error al ejecutar la eliminación de Reference
	 * @author gfandino
	 */
	public void logicDeleteReference(ReferenceVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ReferenceVO por su identificador
	 * @param id identificador del ReferenceVO a ser consultado
	 * @return objeto con la información del ReferenceVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de Reference por ID
	 * @author gfandino
	 */
	public ReferenceVO getReferenceByID(Long id) throws BusinessException;

	/**
	 * Metodo: Obtiene la información de una lista de ReferenceVO por su rnNumber filtrando aquellas cuyo estado sea eliminada
	 * @param id identificador de el o los ReferenceVO a ser consultados
	 * @return objeto con la información de la lista de ReferenceVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de Reference por rnNumber
	 * @author fsanabria
	 */
	public List<ReferenceVO> getReferenceByRNNumber(String rnNumber, String refStatus) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ReferenceVO almacenados en la persistencia
	 * @return Lista con los ReferenceVO existentes, una lista vacia en caso que no existan ReferenceVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la consulta de todos los Reference
	 * @author gfandino
	 */
	public List<ReferenceVO> getAllReferences() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ReferenceVO almacenados en la persistencia
	 * @param country - Long identificador del país
	 * @return Lista con los ReferenceVO existentes, una lista vacia en caso que no existan ReferenceVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la consulta de todos los Reference
	 * @author gfandino
	 */
	public List<ReferenceVO> getAllReferencesAndByCountry(Long country) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar las remisiones asociadas a un estado dado el código o identificado de este
	 * @param refStatus - ReferenceStatusVO Debe contener el ID o el código del estado
	 * @param country - Long identificador del país
	 * @return  List<ReferenceVO> correspondiente al estado de remisión. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su estado
	 * @author gfadnino
	 */
	public List<ReferenceVO> getReferencesByReferenceStatusAndByCountry (ReferenceStatusVO refStatus,Long country)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones asociadas a un estado y unas bodegas de entrada y salida
	 * @param refStatus - ReferenceStatusVO Debe contener el ID o el código del estado
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente al estado de remisión con bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su estado y bodegas
	 * @author gfadnino
	 */
	public List<ReferenceVO> getReferencesByReferenceStatusAndWhAndByCountry (ReferenceStatusVO refStatus,WarehouseVO whSource,WarehouseVO whTarget,Long country)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones a bodegas de entrada y salida
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a las bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por sus bodegas
	 * @author gfadnino
	 */
	public List<ReferenceVO> getReferencesBySourceAndTargetWareHouseAndByCountry (WarehouseVO whSource,WarehouseVO whTarget,Long country)throws BusinessException;
	
	/**
	 * Metodo: Obtiene los Reference almacenados en la persistencia dado un conjunto de filtros
	 * @param referenceId identificador de la remisión
	 * @param sourceWhId identificador de la bodega origen
	 * @param targetWhId identificador de la bodega destino
	 * @return lista con las remisiones que coincidan con los filtros especificados, una lista vacia en caso que no se encuentren
	 * remisiones con los criterios de búsqueda especificados.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<ReferenceVO> getReferencesByIdOrSourceWhOrTargetWh(Long referenceId, Long sourceWhId, Long targetWhId, Date referenceCreationDate, String recorderUserName)throws BusinessException;

	/**
	 * Metodo: Permite consultar las remisiones a bodegas de entrada
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a las bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por sus bodegas
	 * @author gfadnino
	 */
	public List<ReferenceVO> getReferencesBySourceWareHouseAndByCountry (WarehouseVO whSource,Long country)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones a bodegas de salida
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a las bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por sus bodegas
	 * @author gfadnino
	 */
	public List<ReferenceVO> getReferencesByTargetWareHouseAndByCountry (WarehouseVO whTarget,Long country)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones a bodegas de entrada y su estado es inconsistente
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a las bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por sus bodegas
	 * @author gfadnino
	 */
	public List<ReferenceVO> getReferencesBySourceWareHouseAndRefStatusInconsistentAndByCountry (WarehouseVO whSource,Long country)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones segun el filtro ingresado
	 * @param referencesFilterToPrintDTO - ReferencesFilterToPrintDTO Contiene los filtros de 
	 * idReferences: El numero de Remision
	 * whSource: La bodega de origen
	 * whTarget: La bodega Destino
	 * referenceStatusId: El estado de la Remision
	 * @return  NotSerializedElementInSelectReferenceToPrintPaginationResponse correspondiente a las remisiones segun el filtro. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por sus bodegas
	 * @author cduarte
	 */
	public NotSerializedElementInSelectReferenceToPrintPaginationResponse getAllReferencesByIdSourceTargetWareHouseStatus(
			FilterReferencesToPrintDTO referencesFilterToPrintDTO, RequestCollectionInfo requestCollectionInfo )
			throws BusinessException;
	
	/**
	 * Metodo: Obtiene la informacion de la Reference para imprimir
	 * @param referenceId id de la reference a imprimir
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author cduarte
	 */
	public InfoToPrintReferencesDTO getInfoToPrintReferencesById(Long referenceId)throws BusinessException;
	
	/**
	 * Metodo: Casos de Uso:  INV_32 Registrar el envío de una remisión entre diferentes compañías<br> 
	 * Registra el envio de una remision creada
	 * @param referencesShipment ReferenceShipmentDTO - El objeto DTO que encapsula la data del envio
	 * de una remision dada. 
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author garciniegas 
	 */
	public void registerReferenceShipment( ReferenceShipmentDTO referencesShipment, UserVO user )throws BusinessException;	
	/**
	 * Metodo: Actualiza la información de una remisión y los elementos asociados a ella,
	 * Caso de uso INV 31 Consulta o modificación de una remisión
	 * @param reference Información completa de la remisión a ser actualizada, se validará
	 * que tenga la información completa para su actualización
	 * @param referenceElements lista con los elementos de asociados a la remisión, se verificará que estén completos los datos
	 * ya que este caso de uso permite agregar elementos no registrados con anterioridad, en caso que el id del elemento venga
	 * especificado, se asumirá como una actualización, de lo contrario, se creará un nuevo registro de elemento.
	 * @param isFinished Determina si la remisión fué finalizada o nó, de acuerdo con el caso de uso
	 * @param userId identificador del usuario que realizó la remisión
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void updateReferenceAndElements(ReferenceVO reference, List<ReferenceElementItemVO> referenceElements, boolean isFinished, Long userId)throws BusinessException;
	
	/**
	 * Metodo: Regresa un <b>posible</b> conjunto de remisiones definidas por un fitro basado
	 * en el nm de remision y el codigo de pais
	 * @param referenceId Long - El identificador de la remision
	 * @param countryCode Long - El codigo del pais
	 * @throws BusinessException en caso de error al tratar de listar las remisiones	 
	 * @author garciniegas 
	 */
    public List<ReferenceVO>getReferenceByIdAndCountryCode( Long referenceId,Long countryCode )throws BusinessException;
	
	/**
	 * Metodo: Valida que las cantidades a poner en una remision esten disponibles en la bodega que se va a retirar
	 * @param referencesElementItems elementos que se van a retirar
	 * @param sourceWareHouseId ID bodega de donde se van a retirar los elementos
	 * @throws BusinessException Error en la validacion
	 * @author jnova
	 */
	public void validateElementsInWHQuantities(List<ReferenceElementItemVO> referencesElementItems , Long sourceWareHouseId) throws BusinessException;

	/**
	 * Metodo: Registra la entrega de una remisión y sus elementos<br>
	 * Caso de uso INV 35 Registrar  la entrega  y  movimiento de los elementos de  una remisión entre  Bodegas de una misma compañía<br>
	 * Las reglas de negocio son las siguientes:<br>
	 * El sistema actualiza la remisión en tabla Remisiones (salida 1):<br><br>
	 *	•	Estado (recepcionada)<br>
	 *	•	Sección Datos de envío<br>
	 *	•	Fecha de envío de los elementos – Fecha de entrega<br>
	 * 	•	Nombre del conductor – Nombre del colaborador que recibió los elementos<br>
	 *	•	Fecha esperada de llegada – Fecha de entrega<br>
	 *	•	Fecha de llegada – Fecha de entrega<br>
	 *	•	Estado de todos los elementos serializados y no serializados – recepcionado<br>
	 *	•	Cantidad recibida elementos no serializados – cantidad esperada<br>
	 *	•	Comentarios especiales sobre la remisión<br><br>
	 *	El Sistema usa el caso de uso INV-12 (salida 2) para mover los elementos NO serializados de la bodega origen a la bodega destino<br>
	 *	El Sistema usa el caso de uso INV-14 (salida 3) para mover los elementos serializados de la bodega origen a la bodega destino<br>
	 *	El Sistema usa el caso de uso INV-15 (salida 4) para informar al IBS movimiento de elementos NO serializados entre la bodega origen a la bodega destino<br>
	 *	El Sistema usa el caso de uso INV-16 (salida 5) para informar al IBS movimiento de elementos serializados desde la bodega origen a la bodega destino<br>
	 *	El Sistema usa el caso de uso INV-08 (salida 6) para informar a los responsables de las bodegas origen y destino sobre el envío de la remisión<br>
	 * @param referenceId identificador de la remisión
	 * @param deliveryDate fecha de entrega de la remisión
	 * @param targetEmployeeId empleado que recibe los elementos en la bodega destino
	 * @param comments Comentarios sobre la entrega de la remisión
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author jjimenez
	 */
	public void registerReferenceDeliveryAndElementMovement(Long referenceId, Date deliveryDate, Long targetEmployeeId, String comments, Double sentUnits, Long userId) throws BusinessException;
	
	/**
	 * Metodo: Consulta las remisiones por id de la remision y destino de la remision
	 * Caso de uso INV 34
	 * @param referenceId identificador de la remisión
	 * @param warehouseId Identificador de  la bodega de destino
	 * @return List<ReferenceVO> Lista que cumple con el filtro enviado por el usuario
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author jnova
	 */
	public List<ReferenceVO> getReferenceByIdAndWarhouseTarget(Long referenceId, Long warehouseId )throws BusinessException;
	
	
	/**
	 * Metodo: Consulta las remisiones filtradas por la bodega de origen,bodega destino, 
	 * id de la remision y con estado inconsistente 
	 * Caso de uso INV 37
	 * @param whSource identificador de la bodega de origen
	 * @param whTarget Identificador de la bodega de destino
	 * @param reference Identificador de remision
	 * @param country Identificador del pais
	 * @return List<ReferenceVO> Lista que cumple con el filtro enviado por el usuario
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	public List<ReferenceVO> getReferenceByComplexFilterToFindInconsistenceReferences( 
			WarehouseVO whSource,WarehouseVO whTarget,ReferenceVO reference,Long country )throws BusinessException;
	
	
	/**
	 * Metodo: Consulta los elementos serializados asociados a una remision 
	 * Caso de uso INV 37
	 * @param reference Identificador de remision
	 * @param requestCollectionInfo
	 * @return SerializedElementInReferencePaginationResponse Lista que cumple con el filtro enviado por el usuario
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	public SerializedElementInReferencePaginationResponse getSerializedElementInReference( ReferenceVO reference, RequestCollectionInfo requestCollectionInfo )throws BusinessException;
	
	/**
	 * Metodo: Consulta los elementos no serializados asociados a una remision 
	 * Caso de uso INV 37
	 * @param reference Identificador de remision
	 * @param requestCollectionInfo
	 * @return NotSerializedElementInReferencePaginationResponse Lista que cumple con el filtro enviado por el usuario
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	public NotSerializedElementInReferencePaginationResponse getNotSerializedElementInReference( ReferenceVO reference, RequestCollectionInfo requestCollectionInfo )throws BusinessException;
	
	
	/**
	 * Metodo: Consulta objetos ReferenceModificationVO asociados a un objeto Reference
	 * Caso de uso INV 37
	 * @param refID Identificador de remision
	 * @return List<ReferenceModificationVO> Lista que cumple con el filtro enviado por el usuario
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	public List<ReferenceModificationVO> getReferenceModificationsByReferenceID(ReferenceVO refID)throws BusinessException;
	
	
	/**
	 * Metodo: Consulta las detalles de una remision
	 * Caso de uso INV 34
	 * @param referenceId identificador de la remisión
	 * @return ReferenceVO Referencia con todos los detalles necesarios para realizar actualizaciones
	 * @throws BusinessException En caso de error al ejecutar la operación	 
	 * @author jnova
	 */
	public ReferenceVO getReferenceDetailsById(Long referenceId)throws BusinessException;
	
	/**
	 * Metodo: Consulta objetos RefInconsistencyVO asociados a un Reference
	 * Caso de uso INV 37
	 * @param refID Identificador de remision
	 * @return List<RefInconsistency> Lista que cumple con el filtro enviado por el usuario
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	public List<RefInconsistencyVO>getReferenceInconsistencyByReferenceID( Long id )throws BusinessException;
	
	/**
	 * Metodo: Guarda las entregas de una remision dada
	 * Caso de uso INV 34 - Opcion 2
	 * Centraliza la llamado a los business de delivery y de special comments ya que se estan afectando las remisiones
	 * @param referenceId identificador de la remisión
	 * @param deliveriesList List<DeliveryVO> Lista de entregas ingresadas por el usuario
	 * @param specialCommentsList Lista de comentarios especial ingresados por el usuario
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author jnova
	 */
	public void saveReferenceDeliveries(Long referenceId ,List<DeliveryVO> deliveriesList ,List<SpecialCommentVO> specialCommentsList)throws BusinessException;
	
		
	
	/**
	 * Metodo: Actualiza el estado de las inconsistencias asociados a un Reference
	 * Caso de uso INV 37
	 * @param inconsistenciesListIds Una lista con los identificadores de los objetos RefInconsistency a cambiar a estado cerrado
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	public void closeReferenceInconsistencyStatus( List<Long>inconsistenciesListIds )throws BusinessException;
	
	/**
	 * Metodo: Actualiza las cantidades de los objetos ReferenceElementItem asociados a un Reference
	 * @param reference - Reference el objeto Reference al que pertencen los objetos ReferenceElementItem
	 * @param items - los objetos ReferenceElementItem que seran actualizados
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @author garciniegas
	 */
	public void modifyReferenceElementItemCuantityByReferenceAndItemList( List<ReferenceElementItemVO>items,ReferenceVO reference ,Boolean finished,UserVO user)throws BusinessException;
	
	/**
	 * Metodo: adiciona objetos ReferenceElementItem asociados a un Reference
	 * Caso de uso INV 37
	 * @param reference - Reference el objeto Reference al que pertencen los objetos ReferenceElementItem
	 * @param items - los objetos ReferenceElementItem que seran adicionados
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @author garciniegas
	 */
	public void addReferenceElementItemToReference( List<ReferenceElementItemVO>items,ReferenceVO reference )throws BusinessException;
	

	/**
	 * Metodo: elimina objetos ReferenceElementItem asociados a un Reference
	 * Caso de uso INV 37
	 * @param itemsId - los id de los objetos ReferenceElementItem que seran eliminados
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de los ReferenceElementItem por remisión.
	 * @author garciniegas
	 */
	public void deleteReferenceElementItemInReference( List<Long>itemsId )throws BusinessException;
	
	/**
	 * Metodo: Confirma el recibido de todos los elementos de una remision
	 * Caso de uso INV 34 - Opcion 3
	 * LLama a CU 12
	 * LLama a CU 14
	 * LLama a CU 16
	 * LLama a CU 08
	 * @param referenceId identificador de la remisión
	 * @param user UserVO Usuario que realiza la confirmacion
	 * @throws BusinessException En caso de error al modificar el estado de la remision o sus elementos y/o al llamado
	 * de los casos de uso de los cuales es dependiente
	 * @author jnova
	 */
	public void confirmReferenceElementsReception(Long referenceId,UserVO user)throws BusinessException;
	
	/**
	 * Metodo: Confirma el recibido de los elementos NO serializados de una remision poniendo el estado de la remision en recepcionado
	 * si todos sus elementos quedan confirmados o en cofirmado parcial en caso contrario y los elementos en recepcionados
	 * Caso de uso INV 34 - Opcion 4
	 * LLama a CU 12
	 * LLama a CU 08
	 * @param referenceId identificador de la remisión
	 * @param user UserVO Usuario que realiza la confirmacion
	 * @throws BusinessException En caso de error al modificar el estado de la remision o sus elementos y/o al llamado
	 * de los casos de uso de los cuales es dependiente
	 * @author jnova
	 */
	public void confirmNotSerializeReferenceElementsReception(Long referenceId,UserVO user)throws BusinessException;
	
	/**
	  * Metodo: Confirma el recibido de los elementos serializados de una remision poniendo el estado de la remision en recepcionado
	 * si todos sus elementos quedan confirmados o en cofirmado parcial en caso contrario y los elementos en recepcionados
	 * Caso de uso INV 34 - Opcion 5
	 * LLama a CU 14
	 * LLama a CU 16
	 * LLama a CU 08
	 * @param referenceId Long identificador de la remisión
	 * @param user UserVO Usuario que realiza la confirmacion
	 * @throws BusinessException En caso de error al modificar el estado de la remision o sus elementos y/o al llamado
	 * de los casos de uso de los cuales es dependiente
	 * @author jnova
	 */
	public void confirmSerializeReferenceElementsReception(Long referenceId,UserVO user)throws BusinessException;
	
	/**
	 * Metodo: Permite crear una modificacion de una referencia 
	 * @param referencePojo referencia que se modifico
	 * @param creationUser usuario que realizo la modificaion
	 * @param refModTypeCode codigo de la modificacion realizada
	 * @throws BusinessException En caso de error al crear la modificacion de una referencia
	 */
	public void buildReferenceModification(Reference referencePojo, User creationUser, String refModTypeCode) throws BusinessException;
	
	/**
	 * Metodo: Permite enviar un mail asincronamente
	 * @param user UserVO usuario que realiza la operaion
	 * @param referencePojo Reference Remision a la que se va a hacer referencia
	 * @param emailType Tipo de novedad
	 * @param mailObservation Observaciones que van en el correo
	 * @throws BusinessException
	 * @author jnova
	 */
	public void sendEmail(UserVO user, Reference referencePojo, String emailType,String mailObservation) throws BusinessException;
	
	
	
	/**
	 * Metodo: Permite consultar las remisiones asociadas a un número de remisión y bodegas
	 * CU Inv-35
	 * @param ref - Long Identificador de la referencia; nulo si no hace parte de la consulta
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta 
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a la remisión con bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su identificador y bodegas
	 * @author gfadnino
	 */
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountry (Long ref,WarehouseVO whSource,WarehouseVO whTarget,Long country)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones asociadas a un número de remisión y bodegas
	 * CU Inv-33
	 * @param ref - Long Identificador de la referencia; nulo si no hace parte de la consulta
	 * @param whSource - WarehouseVO Debe contener el ID de la bodega; nulo si no hace parte de la consulta 
	 * @param whTarget - WarehouseVO Debe contener el ID de la bodega; nulo si no hace parte de la consulta
	 * @para refStatus - ReferenceStatusVO Debe contener el ID del estadoo; nulo si no hace parte de la consulta
	 * @return  List<ReferenceVO> correspondiente a la remisión con bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su identificador y bodegas
	 * @author gfadnino
	 */
	public ReferenceResponse getReferencesByReferenceAndWhAndRefStatusRefStatus(Long ref,
			WarehouseVO whSource, WarehouseVO whTarget, ReferenceStatusVO refStatus, Long userId, RequestCollectionInfo requestCollectionInfo )
			throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones por fecha de confirmacion de remision: remisiones que tienen la fecha
	 * esperada de entrega menor a la actual y el estado esta en "Enviada" o "recepcionada parcial"
	 * CU Inv-49
	 * @param dealer Long ID que solicita las remisiones por fecha de confirmacion
	 * @param country - Long identificador del país
	 * @return  List<ReferenceVO> Lista que contiene las remisiones que tienen como fecha esperada de entrega menor
	 * a la actual y el estado en "enviado" o "recepcionado parcial"
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference 
	 * @author jnova
	 */
	public ReferenceResponse getReferencesByConfirmationDateAndByCountry(Long dealer,Long country, RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	/**
	 * Metodo: Regresa un <b>posible</b> conjunto de remisiones definidas por un fitro basado
	 * en el nm de remision y la bodega destino
	 * @param referenceId Long - El identificador de la remision
	 * @param warehouseId Long - El codigo de de la bodega destino
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference 
	 * @author garciniegas 
	 */
    public List<ReferenceVO>getReferenceByIdAndWarehouseTarget( Long referenceId,Long warehouseId )throws BusinessException;
    
    /**
	 * Metodo: Obtiene las confirmaciones parciales asociadas a una remision
	 * @param referenceId Long ID de la remision
	 * @return List<RefConfirmation> Lista de confirmaciones parciales
	 * @throws DAOServiceException En caso de error en la consulta de las confirmaciones
	 * @throws DAOSQLException En caso de error en la consulta de las confirmaciones
	 * @author garciniegas
	 */
    public List<RefConfirmationVO> getConfirmationsByReferenceId( Long referenceId ) throws BusinessException;
    
    /**
	 * Metodo: Obtiene las confirmaciones parciales asociadas a una remision y a un elemento en especifico
	 * @param referenceId Long ID de la remision
	 * @param elementId Long ID del elemento
	 * @return List<RefConfirmation> Lista de confirmaciones parciales
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference 
	 * @author garciniegas
	 */
	public List<RefConfirmationVO> getConfirmationsByReferenceIdAndElementId( Long referenceId,Long elementId ) throws BusinessException;
	
	
	/**
	 * Metodo: Regresa un <b>posible</b> conjunto de remisiones definidas por un fitro basado
	 * en el nm de remision y la bodega de origen
	 * @param referenceId Long - El identificador de la remision
	 * @param warehouseId Long - El codigo de de la bodega destino
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference 
	 * @author garciniegas 
	 */
    public List<ReferenceVO>getReferenceByIdAndWarehouseSourceAndWareHouseTarget( Long referenceId,Long whSourceId,Long whTargetId )throws BusinessException;
	
    /**
	 * Metodo: Regresa un <b>posible</b> conjunto de remisiones definidas por un fitro basado
	 * en el nm de remision y la bodega de origen
	 * @param referenceId Long - El identificador de la remision
	 * @param warehouseId Long - El codigo de de la bodega destino
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference 
	 * @author garciniegas 
	 */
    public List<ReferenceVO>getReferenceByIdAndWarehouseSource( Long referenceId,Long warehouseId )throws BusinessException;
    
    
    /**
	 * Caso de uso INV 50 Cargar elementos serializados a una Remisi�n desde archivo plano
	 * 
	 * Metodo: persiste la informacion de serialized
	 * @param referenceID Long id de la remision a la que se le van a adicionar elementos 
	 * @param List<Serialized> listSerialized Lista de los serialized que van a asociarse a una remision
	 * @param sourceWareHouseId Long Id de la bodega de origen
	 * @param targetWareHouseId Long Id de la bodega de destino
	 * @param userId UserVO usuario que crea la remision
	 * @return Long Id de la remision creada
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jnova
	 */
    public Long createReferenceByFile(List<ReferenceElementItemVO> refElements, Long userId ) throws BusinessException;
	
    /**
	 * Caso de uso INV 20 Regresa las remisiones a las que esta asociado un elemento
	 * 
	 * Metodo: persiste la informacion de serialized
	 * @param elementId el id del elemento
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author garciniegas
	 */
    public List<ReferenceVO> getReferencesByElementId(Long elementId) throws BusinessException;
    
    /**
	 * Metodo: Permite consultar las remisiones asociadas a un número de remisión y bodegas en estado creado
	 * CU Inv-35
	 * @param ref - Long Identificador de la referencia; nulo si no hace parte de la consulta
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta 
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a la remisión con bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su identificador y bodegas
	 * @author jnova
	 */
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountryAndCreatedStatus (Long ref,WarehouseVO whSource,WarehouseVO whTarget,Long country)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones asociadas a un número de remisión y bodegas en estado creado y en creacion
	 * CU Inv-31
	 * @param ref - Long Identificador de la referencia; nulo si no hace parte de la consulta
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta 
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a la remisión con bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su identificador y bodegas
	 * @author jnova
	 */
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountryAndCreatedStatusAndCreationstatus (Long ref,WarehouseVO whSource,WarehouseVO whTarget,Long country)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones asociadas a un número de remisión y bodegas en estado enviado y confirmado parcial
	 * CU Inv-34
	 * @param ref - Long Identificador de la referencia; nulo si no hace parte de la consulta
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta 
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a la remisión con bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su identificador y bodegas
	 * @author jnova
	 */
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountryAndSendedOrPartialConfirmed (Long ref,WarehouseVO whSource,WarehouseVO whTarget,Long country)throws BusinessException;
	
	/**
	 * Metodo: Obtiene una lista de las remisiones dados los criterios de filtro y además cuyas bodegas 
	 * origen y destino correspondan al mismo dealer
	 * @param ref identificador de la remisión
	 * @param whSource información de la bodega origen
	 * @param whTarget información de la bodega destino
	 * @param country identificador del país
	 * @return Lista con las remisiones que coinciden con los filtros seleccionados cuyas bodegas origen y destino 
	 * @throws BusinessException En caso de error
	 * @author jjimenezh
	 */
	public List<ReferenceVO> getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndCreatedStatusSameWhDealerOwner(Long ref, WarehouseVO whSource, WarehouseVO whTarget,Long country)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las remisiones asociadas a un número de remisión y bodegas
	 * CU Inv-35
	 * @param ref - Long Identificador de la referencia; nulo si no hace parte de la consulta
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta 
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a la remisión con bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su identificador y bodegas
	 * @author gfadnino
	 */
	public List<ReferenceVO> getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndDifferentDealerWhs(Long ref,WarehouseVO whSource,WarehouseVO whTarget,Long country)throws BusinessException;
	
	
	/**
	 * Metodo: Regresa un <b>posible</b> conjunto de remisiones definidas por un fitro basado
	 * en el nm de remision y la bodega de origen Solo traerá el listado de remisiones si y solo si
	 * la bodega origen y la bodega destino de las remisiones son de diferentes compañías
	 * @param referenceId Long - El identificador de la remision
	 * @param warehouseId Long - El codigo de de la bodega destino
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference 
	 * @author jjimenezh 
	 */
    public List<ReferenceVO> getReferencesByRefIdOrWhSourceIdAndDifferentDealerWhs( Long referenceId, Long sourceWarehouseId )throws BusinessException;
    
    
    /**
     * Método: Consulta las remisiones en estado creado o en creación  por un filtro establecido
     * @param referenceDTO - ReferencesFilterDTO filtro de remisiones
     * @param requestCollInfo - RequestCollectionInfo Paginación
     * @return ReferenceResponse - Datos de Remisión paginados
     * @throws BusinessException en caso de error al ejecutar la consulta de las Reference en estado creado o en creación  por un filtro establecido
     * @author gfandino
     */
    public ReferenceResponse getCreatedReferencesByFilter (ReferencesFilterDTO referenceDTO,RequestCollectionInfo requestCollInfo)throws BusinessException;
    
    
    /**
     * Método: Genera los datos básicos de la remisión
     * INV CU-30
     * @param reference - ReferenceVO datos de la remisión
     * @return ReferenceVO - retorna los datos de la remisión con el ID asignado
     * @throws BusinessException en caso de error al generar los datos de la remisión
     * @author gfandino
     */
    public ReferenceVO generateReference(ReferenceVO reference)throws BusinessException;
    
   /**
    * 
    * Metodo: Consulta las remisiones en precarga a partir de un identificador de bodega de origen
    * @param sourceWhId Identificador de la bodega de origen
    * @param RequestCollectionInfo RequestCollectionInfo Paginación
    * @return List<ReferenceVO> Lista de remisiones en precarga que tiene como bodega de origen el parametro
    * @throws BusinessException <tipo> <descripcion>
    * @author jnova
    */
   public ReferenceResponse getPreloadedReferences(Long sourceWhId,RequestCollectionInfo requestCollInfo)throws BusinessException;
    
   /**
    * 
    * Metodo: Permite validar si una remision es de la misma compania o de diferente compania para realizar el envio de una 
    * remision
    * @param referenceId identificador de la remision
    * @return true si la remision es entre la misma compania, false si no lo es
    * @throws BusinessException <tipo> <descripcion>
    * @author jnova
    */
   public boolean isReferenceFromSameDealer(Long referenceId)throws BusinessException;
   
   /**
    * 
    * Metodo: Realiza la accion de modificar el estado de la remisión en la base de datos e invocar al procedimiento sendReference
    * @param reference remision con datos necesarios para el envio de la remision
    * @param isBetweenDifDealers indica si es una remision entre diferentes companias
    * @param validateQuantityControl indica si se deben validar los controles de cantidad
    * @throws BusinessException <tipo> <descripcion>
    * @author jnova
    */
   public void sendReference(ReferenceVO referenceVO,boolean isBetweenDifDealers,boolean validateQuantityControl, Long userId) throws BusinessException;
  
   /**
    * 
    * Metodo: Realiza la accion de enviar la remision en el CU-30 y CU-32
    * @param reference remision con datos necesarios para el envio de la remision
    * @param isBetweenDifDealers indica si es una remision entre diferentes companias
    * @param validateQuantityControl indica si se deben validar los controles de cantidad
    * @throws BusinessException <tipo> <descripcion>
    * @author jnova
    */
   public void sendReferenceTransact(ReferenceVO referenceVO,boolean isBetweenDifDealers,boolean validateQuantityControl, Long userId) throws BusinessException; 

   /**
    * 
    * Metodo: Accion de enviar una remision entre diferentes companias implementando el CU-32 para ser usado por el CU-30
    * @param reference remision a ser enviada
    * @throws BusinessException <tipo> <descripcion>
    * @author jnova
    */
   public void sendReferenceBetweenDifDealers(ReferenceVO referenceVO, User user) throws BusinessException;

   /**
	 * Metodo: consulta las remisiones de acuerdo al identificador de la remisión padre o generadora
	 * @param parentReferenceId identificador de la remisión padre o generadora
	 * @return List<Reference> listado de objetos Reference que contiene solo los identificadores
	 * @throws BusinessException En caso de error en la consulta de referencia por filtro
	 * @author wjimenez
	 */
	public List<ReferenceVO> getReferencesByParentReferenceId(Long parentReferenceId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta las remisiones por el filtro enviado por parametro
	 * @param referenceDTO Filtro para la consulta de remisiones
	 * @param requestCollInfo objeto para manejo de paginacion
	 * @return ReferenceResponse Objeto que contiene la respuesta a la consulta y objeto de paginacion
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public ReferenceResponse getReferencesByFilter( ReferencesFilterDTO referenceDTO,RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Valida si una remision no se le han ingresado los datos de ingreso 
	 * @param referenceId Long identificador de la remision a ser validada
	 * @return boolean true si ya tiene los datos de ingreso, false si no los tiene
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public boolean validateReferenceReceiveData(Long referenceId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Crea los datos de ingreso de una remision
	 * @param refRecieveDataVO datos de ingreso de la remision
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public void createRefRecieveData(RefRecieveDataVO refRecieveDataVO, Long userId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene los datos de ingreso de una remision
	 * @param referenceId id de la remision
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public RefRecieveDataVO getRefRecieveDataByReferenceId(Long referenceId) throws BusinessException;
	
	/**
	 * Metodo: Almacena una confirmacion para el CU 34
	 * @param confirmationPojo
	 * @param userId
	 * @param pendQuantity
	 * @throws BusinessException
	 * @author jnova
	 */
	public void saveConfirmation(RefConfirmation confirmationPojo , Long userId , Double pendQuantity) throws BusinessException;

	/**
	 * Metodo: <Descripcion>
	 * @param quantity
	 * @param refPojo
	 * @param elementType
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void addNotSerializedElementsToReference(Double quantity,
			Reference refPojo, ElementType elementType, String isAdded, ItemStatus itemStatus, ReportedElementVO reportedElement) throws BusinessException;

	

	/**
	 * Metodo: cierra una inconsistencia generando una nueva remisión de devolución y realizando los
	 * correspondientes movimientos entre bodegas
	 * @param refInconsistencyVO objeto con id, answer, answerUserId, reference.id, reference.warehouses, reference.warehouses.dealer, reference.country.id
	 * @param reportedElementVOs
	 * @return en caso que la inconsistencia sea por menos elementos físicos, retorna el identificador de la remisión
	 * asociada. De lo contrario retorna cero.
	 * @author wjimenez
	 * @throws BusinessException 
	 */
	public Long closeRefInconsistency(RefInconsistencyVO refInconsistencyVO, Long userId) throws BusinessException;

	/**
	 * Metodo: de acuerdo a las bodegas de la remisión, determina si la remisión requiere un proceso estricto
	 * de confirmación de la remisión y registro de inconsistencias
	 * @param sourceWhId identificador de la bodega de origen
	 * @param targetWhId identificador de la bodega destino
	 * @return verdadero si se requiere realizar el proceso
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public boolean isReferenceRequiresValidationProcess(Long sourceWhId, Long targetWhId) throws BusinessException;

	/**
	 * Metodo: realiza un movimiento entre bodegas originado por el cierre de una inconsistencia
	 * en una remisón
	 * @param whIdSource
	 * @param whIdTarget
	 * @param reportedElementVOs listado de los elementos reportados que hacen parte de
	 * la inconsistencia a cerrar
	 * @author wjimenez
	 */
	public void moveElementsBetweenWarehouses(Long whIdSource, Long whIdTarget,
			List<ReportedElementVO> reportedElementVOs, ReferenceVO reference,
			ItemStatus itemStatus, boolean isFisrtMovement, User user, boolean reportIBS) throws BusinessException;
   
}
