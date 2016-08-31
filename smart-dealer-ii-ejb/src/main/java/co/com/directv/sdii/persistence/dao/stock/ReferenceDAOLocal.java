package co.com.directv.sdii.persistence.dao.stock;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.FilterReferencesToPrintDTO;
import co.com.directv.sdii.model.dto.InfoToPrintReferencesDTO;
import co.com.directv.sdii.model.dto.ReferencesFilterDTO;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.ReferenceStatus;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInReferencePaginationResponse;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInSelectReferenceToPrintPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.ReferenceResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SerializedElementInReferencePaginationResponse;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.model.vo.WarehouseVO;

/**
 * 
 * Interface Local para la gestión del CRUD de la Entidad Reference
 * 
 * Fecha de Creación: Mar 8, 2010
 * 
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ReferenceDAOLocal {

	/**
	 * Metodo: persiste la información de un Reference
	 * 
	 * @param obj
	 *            objeto que encapsula la información de un Reference
	 * @throws DAOServiceException
	 *             en caso de error al ejecutar la creación de Reference
	 * @throws DAOSQLException
	 *             en caso de error al ejecutar la creación de Reference
	 * @author gfandino
	 */
	public void createReference(Reference obj) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: actualiza la información de un Reference
	 * 
	 * @param obj
	 *            objeto que encapsula la información de un Reference
	 * @throws DAOServiceException
	 *             en caso de error al ejecutar la actualización de Reference
	 * @throws DAOSQLException
	 *             en caso de error al ejecutar la actualización de Reference
	 * @author gfandino
	 */
	public void updateReference(Reference obj) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Borra de la persistencia la información de un Reference
	 * 
	 * @param obj
	 *            información del Reference a ser borrado
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la eliminación de
	 *             Reference
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la eliminación de
	 *             Reference
	 * @author gfandino
	 */
	public void deleteReference(Reference obj) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Obtiene la información de un Reference por su identificador
	 * 
	 * @param id
	 *            identificador del Reference a ser consultado
	 * @return objeto con la información del Reference dado su identificador,
	 *         nulo en caso que no se encuentre
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por ID
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por ID
	 * @author gfandino
	 */
	public Reference getReferenceByID(Long id) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Obtiene la información de una lista de Reference por su rnNumber filtrando aquellas cuyo estado sea eliminada
	 * 
	 * @param rnNumber
	 *            rnNumber del Reference a ser consultado
	 * @param refStatus
	 * 				status de la referencia por el cual se filtrará (eliminada)	
	 * @return objeto con la información de los Reference dado su rnNumber y no contemplando las eliminadas,
	 *         nulo en caso que no se encuentre
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por rnNumber
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por rnNumber
	 * @author fsanabria
	 */
	public List<Reference> getReferenceByRNNumber(String rnNumber, String refStatus) throws DAOServiceException,
			DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los Reference almacenados en la
	 * persistencia
	 * 
	 * @return Lista con los Reference existentes, una lista vacia en caso que
	 *         no existan Reference en el sistema
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de todos
	 *             los Reference
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de todos
	 *             los Reference
	 * @author gfandino
	 */
	public List<Reference> getAllReferences() throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Obtiene la información de todos los Reference almacenados en la
	 * persistencia
	 * 
	 * @param country
	 *            - Long identificador del país
	 * @return Lista con los Reference existentes, una lista vacia en caso que
	 *         no existan Reference en el sistema
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de todos
	 *             los Reference
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de todos
	 *             los Reference
	 * @author gfandino
	 */
	public List<Reference> getAllReferencesAndByCountry(Long country)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Reference almacenados en la
	 * persistencia dado su estado
	 * 
	 * @param idStatus
	 *            - Long Identificador del estado de las remisiones
	 * @param country
	 *            - Long identificador del país
	 * @return List<Reference> correspondiente al estado especificado
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por estado
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por estado
	 * @author gfandino
	 */
	public List<Reference> getReferencesByReferenceStatusAndByCountry(
			Long idStatus, Long country) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Reference almacenados en la
	 * persistencia dado su estado y bodegas de entrada y salida
	 * 
	 * @param idStatus
	 *            - Long Identificador del estado de las remisiones
	 * @param idWhSource
	 *            - Long Identificador de la bodega de entrada
	 * @param idWhTarget
	 *            - Long Identificador de la bodega de salida
	 * @return List<Reference> correspondiente al estado, bodegas de entrada y
	 *         salida especificados
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por estado y bodegas de entrada y salida
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por estado y bodegas de entrada y salida
	 * @author gfandino
	 */
	public List<Reference> getReferencesByReferenceStatusAndWh(Long idStatus,
			Long idWhSource, Long idWhTarget) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Reference almacenados en la
	 * persistencia dado sus bodegas de entrada y salida
	 * 
	 * @param idWhSource
	 *            - Long Identificador de la bodega de entrada
	 * @param idWhTarget
	 *            - Long Identificador de la bodega de salida
	 * @return List<Reference> correspondiente al bodegas de entrada y salida
	 *         especificados
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de entrada y salida
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de entrada y salida
	 * @author gfandino
	 */
	public List<Reference> getReferencesBySourceAndTargetWareHouse(
			Long idWhSource, Long idWhTarget) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Reference almacenados en la
	 * persistencia dado sus bodegas de entrada
	 * 
	 * @param idWhSource
	 *            - Long Identificador de la bodega de entrada
	 * @return List<Reference> correspondiente al bodegas de entrada y salida
	 *         especificados
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de entrada
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de entrada
	 * @author gfandino
	 */
	public List<Reference> getReferencesBySourceWareHouse(Long idWhSource)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Reference salida en la persistencia
	 * dado sus bodegas de entrada en estado inconsistente
	 * 
	 * @param idWhTarget
	 *            - Long Identificador de la bodega de entrada
	 * @return List<Reference> correspondiente al bodegas de entrada y salida
	 *         especificados
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de salida
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de salida
	 * @author gfandino
	 */
	public List<Reference> getReferencesByTargetWareHouse(Long idWhTarget)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Reference almacenados en la
	 * persistencia dado su estado y bodegas de entrada
	 * 
	 * @param idStatus
	 *            - Long Identificador del estado de las remisiones
	 * @param idWhSource
	 *            - Long Identificador de la bodega de entrada
	 * @return List<Reference> correspondiente al estado, bodegas de entrada y
	 *         salida especificados
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por estado y bodegas de entrada
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por estado y bodegas de entrada
	 * @author gfandino
	 */
	public List<Reference> getReferencesByReferenceStatusAndSourceWh(
			Long idStatus, Long idWhSource) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Reference almacenados en la
	 * persistencia dado su estado y bodegas de salida
	 * 
	 * @param idStatus
	 *            - Long Identificador del estado de las remisiones
	 * @param idWhTarget
	 *            - Long Identificador de la bodega de salida
	 * @return List<Reference> correspondiente al estado, bodegas de entrada y
	 *         salida especificados
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por estado y bodegas de salida
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por estado y bodegas de salida
	 * @author gfandino
	 */
	public List<Reference> getReferencesByReferenceStatusAndTargerWh(
			Long idStatus, Long idWhTarget) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Obtiene la información de las remisiones dado el identificador de
	 * la bodega origen, la bodega destino, la fecha de creación de la remisión
	 * y el nombre de usuario de la persona que realizó la remisión
	 * 
	 * @param sourceWhId
	 *            identificador de la bodega origen
	 * @param targetWhId
	 *            identificador de la bodega destino
	 * @param referenceCreationDate
	 *            fecha de creación de la remisión
	 * @param recorderUserName
	 *            nombre de usuario que creó la remisión
	 * @return Lista con las remisiones conicidentes con los criterios de
	 *         búsqueda
	 * @throws DAOServiceException
	 *             en caso de error al ejecutar la operación
	 * @throws DAOSQLException
	 *             en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<Reference> getReferencesBySourceAndTargetWareHouseAndCreatDateAndRecUserName(
			Long sourceWhId, Long targetWhId, Date referenceCreationDate,
			String recorderUserName) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Permite consultar las remisiones segun el filtro ingresado
	 * 
	 * @param referencesFilterToPrintDTO
	 *            - ReferencesFilterToPrintDTO Contiene los filtros de
	 *            idReferences: El numero de Remision whSource: La bodega de
	 *            origen whTarget: La bodega Destino referenceStatusId: El
	 *            estado de la Remision
	 * @return NotSerializedElementInSelectReferenceToPrintPaginationResponse correspondiente a las remisiones
	 *         segun el filtro. Vacio en otro caso
	 * @throws DAOServiceException
	 *             en caso de error al ejecutar la operación
	 * @throws DAOSQLException
	 *             en caso de error al ejecutar la operación
	 * @author cduarte
	 */
	public NotSerializedElementInSelectReferenceToPrintPaginationResponse getAllReferencesByIdSourceTargetWareHouseStatus(
			FilterReferencesToPrintDTO referencesFilterToPrintDTO, RequestCollectionInfo requestCollectionInfo)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la informacion de la Reference para imprimir
	 * 
	 * @param referenceId
	 *            id de la reference a imprimir
	 * @throws BusinessException
	 *             en caso de error al ejecutar la operación
	 * @author cduarte
	 */
	public InfoToPrintReferencesDTO getInfoToPrintReferencesById(
			Long referenceId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Registra el envio de una remision creada
	 * 
	 * @param reference
	 *            Reference - La remision que sera impactada
	 * @param ReferenceStatus
	 *            statusSended Estado enviado de remision
	 * @throws DAOServiceException
	 *             en caso de error al ejecutar la consulta de elementos en una
	 *             bodega
	 * @throws DAOSQLException
	 *             en caso de error al ejecutar la consulta de elementos en una
	 *             bodega
	 * @author garciniegas
	 */
	public void registerReferenceShipment(Reference reference,
			ReferenceStatus statusSended) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Regresa un <b>posible</b> conjunto de remisiones definidas por un
	 * fitro basado en el nm de remision y el codigo de pais
	 * 
	 * @param referenceId
	 *            Long - El identificador de la remision
	 * @param countryCode
	 *            Long - El codigo del pais
	 * @throws DAOServiceException
	 *             en caso de error al ejecutar la consulta remisiones
	 * @throws DAOSQLException
	 *             en caso de error al ejecutar la consulta de remisiones
	 * @author garciniegas
	 */
	public List<Reference> getReferenceByIdAndCountryCode(Long referenceId,
			Long countryCode) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Reference salida en la persistencia
	 * dado sus bodegas de entrada en estado inconsistente
	 * 
	 * @param referenceId
	 *            - Long Identificador de la referencia
	 * @param warehouseId
	 *            - Long Identificador de la bodega de entrada
	 * @return List<Reference> correspondiente al bodegas de entrada y salida
	 *         especificados
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de salida y ID de referencia
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de salida y ID de referencia
	 * @author jnova
	 */
	public List<Reference> getReferenceByIdAndWarhouseTarget(Long referenceId,
			Long warehouseId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Consulta las remisiones filtradas por la bodega de origen,bodega
	 * destino, id de la remision y con estado inconsistente Caso de uso INV 37
	 * 
	 * @param whSource
	 *            identificador de la bodega de origen
	 * @param whTarget
	 *            Identificador de la bodega de destino
	 * @param reference
	 *            Identificador de remision
	 * @param country
	 *            Identificador del pais
	 * @return List<ReferenceVO> Lista que cumple con el filtro enviado por el
	 *         usuario
	 * @throws BusinessException
	 *             En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	public List<Reference> getReferenceByComplexFilterToFindInconsistenceReferences(
			WarehouseVO whSource, WarehouseVO whTarget, ReferenceVO reference,
			Long country) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Consulta los elementos serializados asociados a una remision Caso
	 * de uso INV 37
	 * 
	 * @param reference
	 *            Identificador de remision
	 * @param requestCollectionInfo
	 * @return SerializedElementInReferencePaginationResponse Lista que cumple
	 *         con el filtro enviado por el usuario
	 * @throws BusinessException
	 *             En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	public SerializedElementInReferencePaginationResponse getSerializedElementInReference(
			ReferenceVO reference, RequestCollectionInfo requestCollectionInfo)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Consulta los elementos no serializados asociados a una remision
	 * Caso de uso INV 37
	 * 
	 * @param reference
	 *            Identificador de remision
	 * @param requestCollectionInfo
	 * @return NotSerializedElementInReferencePaginationResponse Lista que
	 *         cumple con el filtro enviado por el usuario
	 * @throws BusinessException
	 *             En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	public NotSerializedElementInReferencePaginationResponse getNotSerializedElementInReference(
			ReferenceVO reference, RequestCollectionInfo requestCollectionInfo)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Consulta los elementos no serializados asociados a una remision
	 * Caso de uso INV 37
	 * 
	 * @param reference
	 *            Identificador de remision
	 * @param requestCollectionInfo
	 * @return NotSerializedElementInReferencePaginationResponse Lista que
	 *         cumple con el filtro enviado por el usuario con arreglo de
	 *         objetos
	 * @throws BusinessException
	 *             En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	public NotSerializedElementInReferencePaginationResponse getNotSerializedElementInReferenceObjectsReturn(
			ReferenceVO reference, RequestCollectionInfo requestCollectionInfo)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Permite consultar referencias asociadas a referencia y bodega
	 * especificada
	 * 
	 * @param ref
	 *            - Long Identificador de la referencia a consultar
	 * @param whSource
	 *            - Long Identificador de la bodega origen
	 * @return List<Reference> correspondiente a la referencia y bodega
	 *         especificadas; vacio en otro caso
	 * @throws DAOServiceException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega origen
	 * @throws DAOSQLException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega origen
	 * @author gfandino
	 */
	public List<Reference> getReferencesByReferenceAndSourceWh(Long ref,
			Long whSource) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Permite consultar referencias asociadas a referencia y bodega
	 * especificada
	 * 
	 * @param ref
	 *            - Long Identificador de la referencia a consultar
	 * @param whTarger
	 *            - Long Identificador de la bodega destino
	 * @return List<Reference> correspondiente a la referencia y bodega
	 *         especificadas; vacio en otro caso
	 * @throws DAOServiceException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega destino
	 * @throws DAOSQLException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega destino
	 * @author gfandino
	 */
	public List<Reference> getReferencesByReferenceAndTargerWh(Long ref,
			Long whTarger) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Permite consultar referencias asociadas a referencia y bodegas
	 * especificadas
	 * 
	 * @param ref
	 *            - Long Identificador de la referencia a consultar
	 * @param whSource
	 *            - Long Identificador de la bodega origen
	 * @param whTarger
	 *            - Long Identificador de la bodega destino
	 * @return List<Reference> correspondiente a la referencia y bodega
	 *         especificadas; vacio en otro caso
	 * @throws DAOServiceException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega origen y destino
	 * @throws DAOSQLException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega origen y destino
	 * @author gfandino
	 */
	public List<Reference> getReferencesByReferenceAndWh(Long ref,
			Long whSource, Long whTarger) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Permite consultar referencias asociadas a referencia y bodegas
	 * especificadas por estado de remisión
	 * 
	 * @param ref
	 *            - Long Identificador de la referencia a consultar
	 * @param whSource
	 *            - Long Identificador de la bodega origen
	 * @param whTarger
	 *            - Long Identificador de la bodega destino
	 * @param refStatus
	 *            - Long Identificador del estado de remisión
	 * @return List<Reference> correspondiente a la referencia y bodega
	 *         especificadas por estado de remisión; vacio en otro caso
	 * @throws DAOServiceException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega origen y destino por estado de remisión
	 * @throws DAOSQLException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega origen y destino por estado de remisión
	 * @author gfandino
	 */
	public ReferenceResponse getReferencesByReferenceAndWhRefStatus(Long ref,
			Long whSource, Long whTarger, Long refStatus, User user, RequestCollectionInfo requestCollectionInfo )
			throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Consulta la remision asociada al identificador de Element enviado
	 * Caso de uso INV 20
	 * 
	 * @param elementId
	 *            Identificador del Element
	 * @return Reference el objeto Reference asociado al elementId
	 * @throws BusinessException
	 *             En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	public List<Reference> getReferencesByElementId(Long elementId)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Permite consultar las remisiones por fecha de confirmacion de
	 * remision: remisiones que tienen la fecha esperada de entrega menor a la
	 * actual y el estado esta en "Enviada" o "recepcionada parcial" CU Inv-49
	 * 
	 * @param userId
	 *            Long Id del usuario que acaba de loguearse en el sistema
	 * @param country
	 *            - Long identificador del país
	 * @return List<Reference> Lista que contiene las remisiones que tienen como
	 *         fecha esperada de entrega menor a la actual y el estado en
	 *         "enviado" o "recepcionado parcial"
	 * @throws DAOServiceException
	 *             En caso de error en la consulta de referencia
	 * @throws DAOSQLException
	 *             En caso de error en la consulta de referencia
	 * @author jnova
	 */
	public ReferenceResponse getReferencesByConfirmationDateAndByCountry(
			Long userId, Long country, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException,
			DAOSQLException;
	
	/**
	 * Metodo: Regresa un <b>posible</b> conjunto de remisiones definidas por un
	 * fitro basado en el nm de remision y la bodega destino
	 * 
	 * @param referenceId
	 *            Long - El identificador de la remision
	 * @param warehouseId
	 *            Long - El codigo de de la bodega destino
	 * @throws DAOServiceException
	 *             en caso de error al ejecutar la consulta remisiones
	 * @throws DAOSQLException
	 *             en caso de error al ejecutar la consulta de remisiones
	 * @author garciniegas
	 */
	public List<Reference> getReferenceByIdAndWarehouseTarget(Long referenceId,
			Long warehouseId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Regresa un <b>posible</b> conjunto de remisiones definidas por un
	 * fitro basado en el nm de remision y la bodega de origen
	 * 
	 * @param referenceId
	 *            Long - El identificador de la remision
	 * @param warehouseId
	 *            Long - El codigo de de la bodega destino
	 * @throws DAOServiceException
	 *             en caso de error al ejecutar la consulta remisiones
	 * @throws DAOSQLException
	 *             en caso de error al ejecutar la consulta de remisiones
	 * @author garciniegas
	 */
	public List<Reference> getReferenceByIdAndWarehouseSourceAndWareHouseTarget(
			Long referenceId, Long whSourceId, Long whTargetId,
			String referenceStatusCode) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Regresa un <b>posible</b> conjunto de remisiones definidas por un
	 * fitro basado en el nm de remision y la bodega de origen
	 * 
	 * @param referenceId
	 *            Long - El identificador de la remision
	 * @param warehouseId
	 *            Long - El codigo de de la bodega destino
	 * @throws DAOServiceException
	 *             en caso de error al ejecutar la consulta remisiones
	 * @throws DAOSQLException
	 *             en caso de error al ejecutar la consulta de remisiones
	 * @author garciniegas
	 */
	public List<Reference> getReferenceByIdAndWarehouseSource(Long referenceId,
			Long warehouseId, String referenceStatusCode)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Reference salida en la persistencia
	 * dado sus bodegas de entrada en estado creado
	 * 
	 * @param idWhTarget
	 *            - Long Identificador de la bodega de entrada
	 * @return List<Reference> correspondiente al bodegas de entrada y salida
	 *         especificados
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de salida
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de salida
	 * @author jnova
	 */
	public List<Reference> getReferencesByTargetWareHouseAndCreatedStatus(
			Long idWhTarget) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Reference almacenados en la
	 * persistencia dado sus bodegas de entrada en estado creado
	 * 
	 * @param idWhSource
	 *            - Long Identificador de la bodega de entrada
	 * @return List<Reference> correspondiente al bodegas de entrada y salida
	 *         especificados
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de entrada
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de entrada
	 * @author jnova
	 */
	public List<Reference> getReferencesBySourceWareHouseAndCreatedStatus(
			Long idWhSource) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Reference almacenados en la
	 * persistencia dado sus bodegas de entrada y salida en estado creado
	 * 
	 * @param idWhSource
	 *            - Long Identificador de la bodega de entrada
	 * @param idWhTarget
	 *            - Long Identificador de la bodega de salida
	 * @return List<Reference> correspondiente al bodegas de entrada y salida
	 *         especificados
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de entrada y salida
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de entrada y salida
	 * @author jnova
	 */
	public List<Reference> getReferencesBySourceAndTargetWareHouseAndCreatedStatus(
			Long idWhSource, Long idWhTarget) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Obtiene la información de un Reference por su identificador en
	 * estado creado
	 * 
	 * @param id
	 *            identificador del Reference a ser consultado
	 * @return objeto con la información del Reference dado su identificador,
	 *         nulo en caso que no se encuentre
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por ID
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por ID
	 * @author jnova
	 */
	public Reference getReferenceByIDAndCreatedStatus(Long id)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Permite consultar referencias asociadas a referencia y bodega
	 * especificada en estado creado
	 * 
	 * @param ref
	 *            - Long Identificador de la referencia a consultar
	 * @param whSource
	 *            - Long Identificador de la bodega origen
	 * @return List<Reference> correspondiente a la referencia y bodega
	 *         especificadas; vacio en otro caso
	 * @throws DAOServiceException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega origen
	 * @throws DAOSQLException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega origen
	 * @author jnova
	 */
	public List<Reference> getReferencesByReferenceAndSourceWhAndCreatedStatus(
			Long ref, Long whSource) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Permite consultar referencias asociadas a referencia y bodega
	 * especificada en estado creado
	 * 
	 * @param ref
	 *            - Long Identificador de la referencia a consultar
	 * @param whTarger
	 *            - Long Identificador de la bodega destino
	 * @return List<Reference> correspondiente a la referencia y bodega
	 *         especificadas; vacio en otro caso
	 * @throws DAOServiceException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega destino
	 * @throws DAOSQLException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega destino
	 * @author jnova
	 */
	public List<Reference> getReferencesByReferenceAndTargerWhAndCreatedStatus(
			Long ref, Long whTarger) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Permite consultar referencias asociadas a referencia y bodegas
	 * especificadas en estado creado
	 * 
	 * @param ref
	 *            - Long Identificador de la referencia a consultar
	 * @param whSource
	 *            - Long Identificador de la bodega origen
	 * @param whTarger
	 *            - Long Identificador de la bodega destino
	 * @return List<Reference> correspondiente a la referencia y bodega
	 *         especificadas; vacio en otro caso
	 * @throws DAOServiceException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega origen y destino
	 * @throws DAOSQLException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega origen y destino
	 * @author jnova
	 */
	public List<Reference> getReferencesByReferenceAndWhAndCreatedStatus(
			Long ref, Long whSource, Long whTarger) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Reference salida en la persistencia
	 * dado sus bodegas de entrada en estado creado y en creacion
	 * 
	 * @param idWhTarget
	 *            - Long Identificador de la bodega de entrada
	 * @return List<Reference> correspondiente al bodegas de entrada y salida
	 *         especificados
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de salida
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de salida
	 * @author jnova
	 */
	public List<Reference> getReferencesByTargetWareHouseAndCreatedStatusAndCreationStatus(
			Long idWhTarget) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Reference almacenados en la
	 * persistencia dado sus bodegas de entrada en estado creado y en creacion
	 * 
	 * @param idWhSource
	 *            - Long Identificador de la bodega de entrada
	 * @return List<Reference> correspondiente al bodegas de entrada y salida
	 *         especificados
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de entrada
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de entrada
	 * @author jnova
	 */
	public List<Reference> getReferencesBySourceWareHouseAndCreatedStatusAndCreationStatus(
			Long idWhSource) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Reference almacenados en la
	 * persistencia dado sus bodegas de entrada y salida en estado creado y en
	 * creacion
	 * 
	 * @param idWhSource
	 *            - Long Identificador de la bodega de entrada
	 * @param idWhTarget
	 *            - Long Identificador de la bodega de salida
	 * @return List<Reference> correspondiente al bodegas de entrada y salida
	 *         especificados
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de entrada y salida
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de entrada y salida
	 * @author jnova
	 */
	public List<Reference> getReferencesBySourceAndTargetWareHouseAndCreatedStatusAndCreationStatus(
			Long idWhSource, Long idWhTarget) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Obtiene la información de un Reference por su identificador en
	 * estado creado y en creacion
	 * 
	 * @param id
	 *            identificador del Reference a ser consultado
	 * @return objeto con la información del Reference dado su identificador,
	 *         nulo en caso que no se encuentre
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por ID
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por ID
	 * @author jnova
	 */
	public Reference getReferenceByIDAndCreatedStatusAndCreatedStatus(Long id)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Permite consultar referencias asociadas a referencia y bodega
	 * especificada en estado creado y en creacion
	 * 
	 * @param ref
	 *            - Long Identificador de la referencia a consultar
	 * @param whSource
	 *            - Long Identificador de la bodega origen
	 * @return List<Reference> correspondiente a la referencia y bodega
	 *         especificadas; vacio en otro caso
	 * @throws DAOServiceException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega origen
	 * @throws DAOSQLException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega origen
	 * @author jnova
	 */
	public List<Reference> getReferencesByReferenceAndSourceWhAndCreatedStatusAndCreatedStatus(
			Long ref, Long whSource) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Permite consultar referencias asociadas a referencia y bodega
	 * especificada en estado creado y en creacion
	 * 
	 * @param ref
	 *            - Long Identificador de la referencia a consultar
	 * @param whTarger
	 *            - Long Identificador de la bodega destino
	 * @return List<Reference> correspondiente a la referencia y bodega
	 *         especificadas; vacio en otro caso
	 * @throws DAOServiceException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega destino
	 * @throws DAOSQLException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega destino
	 * @author jnova
	 */
	public List<Reference> getReferencesByReferenceAndTargerWhAndCreatedStatusAndCreatedStatus(
			Long ref, Long whTarger) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Permite consultar referencias asociadas a referencia y bodegas
	 * especificadas en estado creado y en creacion
	 * 
	 * @param ref
	 *            - Long Identificador de la referencia a consultar
	 * @param whSource
	 *            - Long Identificador de la bodega origen
	 * @param whTarger
	 *            - Long Identificador de la bodega destino
	 * @return List<Reference> correspondiente a la referencia y bodega
	 *         especificadas; vacio en otro caso
	 * @throws DAOServiceException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega origen y destino
	 * @throws DAOSQLException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega origen y destino
	 * @author jnova
	 */
	public List<Reference> getReferencesByReferenceAndWhAndCreatedStatusAndCreatedStatus(
			Long ref, Long whSource, Long whTarger) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Reference salida en la persistencia
	 * dado sus bodegas de entrada en estado enviado o confirmado parcial
	 * 
	 * @param idWhTarget
	 *            - Long Identificador de la bodega de entrada
	 * @return List<Reference> correspondiente al bodegas de entrada y salida
	 *         especificados
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de salida
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de salida
	 * @author jnova
	 */
	public List<Reference> getReferencesByTargetWareHouseAndSendedOrPartialConfirmed(
			Long idWhTarget) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Reference almacenados en la
	 * persistencia dado sus bodegas de entrada en estado enviado o confirmado
	 * parcial
	 * 
	 * @param idWhSource
	 *            - Long Identificador de la bodega de entrada
	 * @return List<Reference> correspondiente al bodegas de entrada y salida
	 *         especificados
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de entrada
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de entrada
	 * @author jnova
	 */
	public List<Reference> getReferencesBySourceWareHouseAndSendedOrPartialConfirmed(
			Long idWhSource) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Reference almacenados en la
	 * persistencia dado sus bodegas de entrada y salida en estado enviado o
	 * confirmado parcial
	 * 
	 * @param idWhSource
	 *            - Long Identificador de la bodega de entrada
	 * @param idWhTarget
	 *            - Long Identificador de la bodega de salida
	 * @return List<Reference> correspondiente al bodegas de entrada y salida
	 *         especificados
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de entrada y salida
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por bodegas de entrada y salida
	 * @author jnova
	 */
	public List<Reference> getReferencesBySourceAndTargetWareHouseAndSendedOrPartialConfirmed(
			Long idWhSource, Long idWhTarget) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Obtiene la información de un Reference por su identificador en
	 * estado enviado o confirmado parcial
	 * 
	 * @param id
	 *            identificador del Reference a ser consultado
	 * @return objeto con la información del Reference dado su identificador,
	 *         nulo en caso que no se encuentre
	 * @throws DAOServiceException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por ID
	 * @throws DAOSQLException
	 *             en caso de error al tratar de ejecutar la consulta de
	 *             Reference por ID
	 * @author jnova
	 */
	public Reference getReferenceByIDAndSendedOrPartialConfirmed(Long id)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Permite consultar referencias asociadas a referencia y bodega
	 * especificada en estado enviado o confirmado parcial
	 * 
	 * @param ref
	 *            - Long Identificador de la referencia a consultar
	 * @param whSource
	 *            - Long Identificador de la bodega origen
	 * @return List<Reference> correspondiente a la referencia y bodega
	 *         especificadas; vacio en otro caso
	 * @throws DAOServiceException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega origen
	 * @throws DAOSQLException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega origen
	 * @author jnova
	 */
	public List<Reference> getReferencesByReferenceAndSourceWhAndSendedOrPartialConfirmed(
			Long ref, Long whSource) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Permite consultar referencias asociadas a referencia y bodega
	 * especificada en estado enviado o confirmado parcial
	 * 
	 * @param ref
	 *            - Long Identificador de la referencia a consultar
	 * @param whTarger
	 *            - Long Identificador de la bodega destino
	 * @return List<Reference> correspondiente a la referencia y bodega
	 *         especificadas; vacio en otro caso
	 * @throws DAOServiceException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega destino
	 * @throws DAOSQLException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega destino
	 * @author jnova
	 */
	public List<Reference> getReferencesByReferenceAndTargerWhAndSendedOrPartialConfirmed(
			Long ref, Long whTarger) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Permite consultar referencias asociadas a referencia y bodegas
	 * especificadas en estado enviado o confirmado parcial
	 * 
	 * @param ref
	 *            - Long Identificador de la referencia a consultar
	 * @param whSource
	 *            - Long Identificador de la bodega origen
	 * @param whTarger
	 *            - Long Identificador de la bodega destino
	 * @return List<Reference> correspondiente a la referencia y bodega
	 *         especificadas; vacio en otro caso
	 * @throws DAOServiceException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega origen y destino
	 * @throws DAOSQLException
	 *             En caso de error en la consulta de referencia por
	 *             identificado y bodega origen y destino
	 * @author jnova
	 */
	public List<Reference> getReferencesByReferenceAndWhAndSendedOrPartialConfirmed(
			Long ref, Long whSource, Long whTarger) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Método: Permite consultar las remisiones en estado creado o en creación
	 * 
	 * @param referenceDTO
	 *            - ReferencesFilterDTO datos para filtrar la remisión
	 * @param requestCollInfo
	 *            - RequestCollectionInfo datos para la paginación
	 * @return ReferenceResponse Datos paginados de remisiones
	 * @throws DAOServiceException
	 *             En caso de error en la consulta de referencia por filtro
	 * @throws DAOSQLException
	 *             En caso de error en la consulta de referencia por filtro
	 * @author gfandino
	 */
	public ReferenceResponse getCreatedReferencesByFilter(
			ReferencesFilterDTO referenceDTO,
			RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException;

	/**
	 * 
	 * Metodo: consulta las remisiones por bodega de origen y estado de precarga
	 * y estado de remision
	 * 
	 * @param sourceWhId
	 *            bodega de origen de la remision
	 * @param preloadStatus
	 *            estado de precarga de la remision
	 * @param refStatusCode
	 *            codigo de estado de la remision
	 * @param requestCollInfo
	 *            RequestCollectionInfo datos para la paginación
	 * @return ReferenceResponse Datos paginados de remisiones
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 *             <tipo> <descripcion>
	 * @author jnova
	 */
	public ReferenceResponse getReferencesBySourceWhIdAndPreloadStatus(
			Long sourceWhId, String preloadStatus, String refStatusCode,
			RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: consulta las remisiones de acuerdo al identificador de la
	 * remisión padre o generadora
	 * 
	 * @param parentReferenceId
	 *            identificador de la remisión padre o generadora
	 * @return List<Reference> listado de objetos Reference que contiene solo
	 *         los identificadores
	 * @throws DAOServiceException
	 *             En caso de error en la consulta de referencia por filtro
	 * @throws DAOSQLException
	 *             En caso de error en la consulta de referencia por filtro
	 * @author wjimenez
	 */
	public List<Reference> getReferencesByParentReferenceId(
			Long parentReferenceId) throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Consulta las remisiones por el filtro enviado por parametro
	 * 
	 * @param referenceDTO
	 *            Filtro para la consulta de remisiones
	 * @param requestCollInfo
	 *            objeto para manejo de paginacion
	 * @return ReferenceResponse Objeto que contiene la respuesta a la consulta
	 *         y objeto de paginacion
	 * @throws DAOServiceException
	 *             En caso de error en la consulta de referencia por filtro
	 * @throws DAOSQLException
	 *             En caso de error en la consulta de referencia por filtro
	 * @author jnova
	 */
	public ReferenceResponse getReferencesByFilter(
			ReferencesFilterDTO referenceDTO,
			RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Cuenta cuantos archivos se encuentran en el estado <code>fileStatusCode</code>
	 * Para la remisión <code>referenceId</code>. 
	 * El param name tienen que ser referenceId siempre.
	 * 
	 * @param fileStatusCode
	 * 			Codigo del estado del archivo.
	 * @param uploadFileParam
	 * 			Param name de la tabla Upload file param.
	 * @param referenceId
	 * 			Identificador unico de la remisión.
	 * @return
	 * @throws DAOServiceException
	 *             En caso de error en la consulta de referencia por filtro
	 * @throws DAOSQLException
	 *             En caso de error en la consulta de referencia por filtro
	 */
	public Long getCountUploadFilesByFileStatusAndReference( String fileStatusCodePending, 
			String fileStatusCodeProcessing,
			String uploadFileParam, 
			String referenceId) throws DAOServiceException, 
			DAOSQLException;
	
}