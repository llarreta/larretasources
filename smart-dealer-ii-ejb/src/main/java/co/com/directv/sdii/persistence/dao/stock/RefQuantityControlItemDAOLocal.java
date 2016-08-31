package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.RefQuantityControlItem;
import co.com.directv.sdii.model.pojo.collection.RefQuantityControlItemsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad RefConfirmation
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface RefQuantityControlItemDAOLocal {

	/**
	 * Metodo:  persiste la información de un RefQuantityControlItem
	 * @param obj objeto que encapsula la información de un RefQuantityControlItem
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createRefQuantityControlItem(RefQuantityControlItem obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un RefQuantityControlItem
	 * @param obj objeto que encapsula la información de un RefQuantityControlItem
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateRefQuantityControlItem(RefQuantityControlItem obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un RefQuantityControlItem
	 * @param obj información del RefQuantityControlItem a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteRefQuantityControlItem(RefQuantityControlItem obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un RefQuantityControlItem por su identificador
	 * @param id identificador del RefQuantityControlItem a ser consultado
	 * @return objeto con la información del RefQuantityControlItem dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public RefQuantityControlItem getRefQuantityControlItemByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los RefQuantityControlItem almacenados en la persistencia
	 * @return Lista con los RefQuantityControlItem existentes, una lista vacia en caso que no existan RefQuantityControlItem en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<RefQuantityControlItem> getAllRefQuantityControlItems() throws DAOServiceException, DAOSQLException;

	/**
	 * Método: Obtiene la información del contrl de cantidades para uan remisión
	 * @param refId - Long identificador de la remisión
	 * @param requestCollInfo - RequestCollectionInfo datos de la paginación
	 * @return RefQuantityControlItemsResponse datos paginados
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta por remisión
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta por remisión
	 * @author gfandino
	 */
	public RefQuantityControlItemsResponse getRefQtyCtrlItemsByReference(Long refId,RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException; 
	
	
	/**
	 * Método: UC30 (sección 21 prototipo) Se consultan las remisiones por la bodega de origen
	 * @param idSourceWh - Long identificador de la bodega de origen
	 * @param requestCollInfo - RequestCollectionInfo datos de la paginación
	 * @return RefQuantityControlItemsResponse con los datos de las remisiones precargadas paginadas
	 * @throws DAOServiceException en caso de error en la consulta de remisiones precargadas
	 * @throws DAOSQLException en caso de error en la consulta de remisiones precargadas
	 * @author gfandino
	 */
	public RefQuantityControlItemsResponse getReferencesPreloadByCreationProcess(Long idSourceWh,
			RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException;
	
	/**
	 * Método: UC30 consulta el control de cantidad para un tipo de elemento en una remisión
	 * @param elementType - Long Identificador del tipo de elemento
	 * @param reference - Long Identificador de la remisión
	 * @return RefQuantityControlItem
	 * @throws DAOServiceException en caso de error consultando el contrl de cantidad por tipo de elemento y remisión
	 * @throws DAOSQLException en caso de error consultando el contrl de cantidad por tipo de elemento y remisión
	 * @author gfandino
	 */
	public RefQuantityControlItem getRefQuantityControlItemByElmtTypeAndRef(Long elementType, Long reference)throws DAOServiceException,DAOSQLException;

	/**
	 * Metodo: valida la existencia de diferencias entre las cantidades solicitadas
	 * y cantidades existentes en una remisión
	 * @param referenceId identificador de la referencia
	 * @return verdadero si no se encuentran diferencias en las cantidades 
	 * @throws DAOServiceException en caso de error al ejecutar la consulta
	 * @throws DAOSQLException en caso de error al ejecutar la consulta
	 * @author wjimenez
	 */
	public boolean validateExistenceOfReferenceQuantityDifferences(
			Long referenceId)throws DAOServiceException,DAOSQLException;
	
}