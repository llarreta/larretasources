package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.AdjustmenElementsRequestDTO;
import co.com.directv.sdii.model.dto.collection.AdjustmentElementCollDTO;
import co.com.directv.sdii.model.pojo.AdjustmentElements;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad AdjustmentElements
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface AdjustmentElementsDAOLocal {

	/**
	 * Metodo:  persiste la información de un AdjustmentElements
	 * @param obj objeto que encapsula la información de un AdjustmentElements
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createAdjustmentElements(AdjustmentElements obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un AdjustmentElements
	 * @param obj objeto que encapsula la información de un AdjustmentElements
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateAdjustmentElements(AdjustmentElements obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un AdjustmentElements
	 * @param obj información del AdjustmentElements a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteAdjustmentElements(AdjustmentElements obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un AdjustmentElements por su identificador
	 * @param id identificador del AdjustmentElements a ser consultado
	 * @return objeto con la información del AdjustmentElements dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public AdjustmentElements getAdjustmentElementsByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los AdjustmentElements almacenados en la persistencia
	 * @return Lista con los AdjustmentElements existentes, una lista vacia en caso que no existan AdjustmentElements en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<AdjustmentElements> getAllAdjustmentElementss() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de los AdjustmentElements almacenados en la persistencia del con respesto al id
	 * del adjustment Maestro
	 * @param idAdjustment id del ajuste para el cual se consultan elementos
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<AdjustmentElements> getAdjustmentElementsByAdjusmentId(Long idAdjustment) throws DAOServiceException, DAOSQLException;

	/**
	 * consulta los elementos de un ajuste que requieren autorizacion
	 * @param request
	 * @param requestCollInfo
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public AdjustmentElementCollDTO getAdjustmentElementsForAuthorization(
			   AdjustmenElementsRequestDTO request, RequestCollectionInfo requestCollInfo)
	throws DAOServiceException, DAOSQLException;
	
	
	public List<AdjustmentElements> getAdjustmentElementsForAuthorization(Long adjustmentId)
	throws DAOServiceException, DAOSQLException ;

	/**
	 * Metodo encargado de contar los elementos de un ajuste por el estado indicado
	 * @param adjustmentId
	 * @param statusCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public Long countAdjustmentElementsByStatus(Long adjustmentId, String statusCode)
	throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite contar todos los estados de AdjusmentElements
	 * @param adjustmentId id del ajuste del cual se requiere hacer el conteo
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public Object[] countAdjustmentElementsByAllStatus(Long adjustmentId) throws DAOServiceException, DAOSQLException;
	
}