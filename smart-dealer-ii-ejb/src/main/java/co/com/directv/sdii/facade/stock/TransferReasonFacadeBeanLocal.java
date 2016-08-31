package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.TransferReasonResponse;
import co.com.directv.sdii.model.vo.AdjustmentTypeVO;
import co.com.directv.sdii.model.vo.TransferReasonVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ElementType.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author mrugeles <a href="mailto:mrugeles@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface TransferReasonFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto TransferReason
	 * @param obj - TransferReasonVO  objeto que encapsula la información de un TransferReasonVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author mrugeles
	 */
	public void createTransferReason(TransferReasonVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un TransferReason
	 * @param obj - TransferReasonVO  objeto que encapsula la información de un TransferReasonVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author mrugeles
	 */
	public void updateTransferReason(TransferReasonVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un TransferReason
	 * @param obj - TransferReasonVO  información del TransferReasonVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author mrugeles
	 */
	public void deleteTransferReason(TransferReasonVO obj) throws BusinessException;

	/**
	 * Metodo: Obtiene la información de todos los TransferReason almacenados en la persistencia
	 * @return Lista con los TransferReason existentes, una lista vacia en caso que no existan TransferReason en el sistema
	 * @author mrugeles
	 * @throws BusinessException 
	 */
	public List<TransferReasonVO> getAllTransferReasons() throws BusinessException;

	/**
	 * Método: Permite consultar todos los TransferReason en estado dado un ElementModel 
	 * @param elementModelId - Long Código del ElementModel
	 * @param TransferReasonStatus - String Estado
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @return TransferReasonResponse con los resultados de la consulta
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de TransferReason en estado por ElementModel
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de TransferReason en estado por ElementModel
	 * @author mrugeles
	 * @throws BusinessException 
	 */
	public TransferReasonResponse getTransferReasonByFilter(String TransferReasonName,RequestCollectionInfo requestCollInfo)throws BusinessException;

	/**
	 * Metodo: Obtiene la información de un TransferReason por su identificador
	 * @param id identificador del TransferReason a ser consultado
	 * @return objeto con la información del TransferReason dado su identificador, nulo en caso que no se encuentre
	 * @author mrugeles
	 * @throws BusinessException 
	 */
	public TransferReasonVO getTransferReasonByID(Long id) throws BusinessException;

	/**
	 * Metodo: Obtiene la informaciÃ³n de un TransferReason dado su estado
	 * @param status - String estado del elemento
	 * @return List<TransferReason> correspondiente al estado; vacio en otro caso
	 * @author mrugeles
	 * @throws BusinessException 
	 */
	public List<TransferReasonVO> getTransferReasonsByIsActive(String status)throws BusinessException;
	
	
	/**
	 * 
	 * Metodo: Método encargado de obtener los causales de ajuste a partir de el tipo de ajuste
	 * que se reciba como parametro.
	 * Si son causal de entrada o de calidad retorna los MovementType de entrada o de salida de 
	 * acuerdo a al tipo, si es un causal de translado consulta la tabla de TransferReason
	 * Un TransferReason esta compuesto por dos MovementType.
	 * @param adjustment
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public List<TransferReasonVO> getAdjustmentReasonbyAdjustmentType(AdjustmentTypeVO adjustmentType)throws BusinessException;
	
}
