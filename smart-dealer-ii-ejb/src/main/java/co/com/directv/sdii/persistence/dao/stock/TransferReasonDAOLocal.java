package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;
import javax.ejb.Local;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.TransferReason;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.TransferReasonResponse;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad TransferReason
 * 
 * Fecha de Creación: Oct 31, 2011
 * @author mrugeles <a href="mailto:mrugeles@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface TransferReasonDAOLocal {
	/**
	 * Metodo:  persiste la información de un TransferReason
	 * @param obj objeto que encapsula la información de un TransferReason
	 * @throws DAOServiceException en caso de error al ejecutar la creación de TransferReason
	 * @throws DAOSQLException en caso de error al ejecutar la creación de TransferReason
	 * @author mrugeles
	 */
	public void createTransferReason(TransferReason obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un TransferReason
	 * @param obj objeto que encapsula la información de un TransferReason
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de TransferReason
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de TransferReason
	 * @author gfandino
	 */
	public void updateTransferReason(TransferReason obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un TransferReason
	 * @param obj información del TransferReason a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de TransferReason
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de TransferReason
	 * @author mrugeles
	 */
	public void deleteTransferReason(TransferReason obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un TransferReason por su identificador
	 * @param id identificador del TransferReason a ser consultado
	 * @return objeto con la información del TransferReason dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de TransferReason por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de TransferReason por ID
	 * @author mrugeles
	 */
	public TransferReason getTransferReasonByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los TransferReason almacenados en la persistencia
	 * @return Lista con los TransferReason existentes, una lista vacia en caso que no existan TransferReason en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los TransferReason
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los TransferReason
	 * @author mrugeles
	 */
	public List<TransferReason> getAllTransferReasons() throws DAOServiceException, DAOSQLException;

	
	/**
	 * Metodo: Obtiene la informaciÃ³n de un TransferReason dado su estado
	 * @param status - String estado del elemento
	 * @return List<TransferReason> correspondiente al estado; vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de TransferReason por estado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de TransferReason por estado
	 * @author gfandino
	 */
	public List<TransferReason> getTransferReasonsByIsActive(String status)throws DAOServiceException, DAOSQLException;
		
	/**
	 * Método: Permite consultar todos los TransferReason en estado dado un ElementModel 
	 * @param elementModelId - Long Código del ElementModel
	 * @param TransferReasonStatus - String Estado
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @return TransferReasonResponse con los resultados de la consulta
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de TransferReason en estado por ElementModel
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de TransferReason en estado por ElementModel
	 * @author gfandino
	 */
	public TransferReasonResponse getTransferReasonByFilter(String TransferReasonName,RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Permite consultar los TransferReason con base en los parametros enviados que son no requeridos
	 * @param codeAdjustmentType
	 * @param isActive
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author waguilera
	 */
	public List<TransferReason> getTransferReasonByAdjustmentType(String codeAdjustmentType, String isActive)throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta las razones de transferencia por codigo de tipo de movimiento de entrada
	 * @param movementTypeInCode Codigo de tipo de movimiento
	 * @param isActive
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<TransferReason> getTransferReasonByMovementTypeInCode(String movementTypeInCode, String isActive)throws DAOServiceException, DAOSQLException;

}
