package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.AdjustmentRequestDTO;
import co.com.directv.sdii.model.pojo.Adjustment;
import co.com.directv.sdii.model.pojo.collection.AdjustmentElementsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad Adjustment
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface AdjustmentDAOLocal {

	/**
	 * Metodo:  persiste la información de un Adjustment
	 * @param obj objeto que encapsula la información de un Adjustment
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public Long createAdjustment(Adjustment obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un Adjustment
	 * @param obj objeto que encapsula la información de un Adjustment
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateAdjustment(Adjustment obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un Adjustment
	 * @param obj información del Adjustment a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteAdjustment(Adjustment obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un Adjustment por su identificador
	 * @param id identificador del Adjustment a ser consultado
	 * @return objeto con la información del Adjustment dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public Adjustment getAdjustmentByID(Long id) throws DAOServiceException, DAOSQLException;
	
	public Adjustment getAdjustmentByIDMassive(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los Adjustment almacenados en la persistencia
	 * @return Lista con los Adjustment existentes, una lista vacia en caso que no existan Adjustment en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<Adjustment> getAllAdjustments() throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta los Adjustment por causal de movimiento
	 * @param transferReasonId
	 * @return Lista de Adjusment asociados a la causal pasada por parametro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Adjustment> getAllAdjustmentsByTransferReasonId(Long transferReasonId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Consulta los Adjustment por causal de movimiento
	 * @param params parametros de busqueda de la pantalla de ajustes
	 * @param requestCollInfo parametros de petiticion para paginacion y presentacion
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	public AdjustmentElementsResponse searchAdjustmentsBySearchParameters(AdjustmentRequestDTO params, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException, BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta los Adjustment por cuadrilla y estados que no sean los recibidos por parametro
	 * @param transferReasonId
	 * @return Lista de Adjusment asociados a la causal pasada por parametro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jgonzmol
	 */
	public List<Adjustment> getAdjustmentsByCrewIdAndDistinctAdjustmentStatus(List<String> statusCode,
			Long crewId) throws DAOServiceException, DAOSQLException;

}
