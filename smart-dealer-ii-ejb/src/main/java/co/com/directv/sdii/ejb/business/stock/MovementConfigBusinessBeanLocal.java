package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ElementMovementDTO;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;


/**
 * Interfaz que define los métodos para realizar la configuración de los
 * informes que se realizan a IBS cuando se mueven elementos entre las
 * bodegas.
 * 
 * Session Bean implementation class MovementConfigBusinessBean
 * 
 * @author Jimmy Vélez muñoz - 16/06/2011
 */

@Local
public interface MovementConfigBusinessBeanLocal {

	/**
	 * Método: Permite crear un registro de comando para ser enviado a IBS cuando se mueve un elemento entre bodegas.
	 * 
	 * 
	 * @param ElementMovementDTO dto
	 * @throws BusinessException
	 * 
	 * @author waguilera
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createMovementCommandQueue(MovementElementDTO dto) throws BusinessException;
	
	/**
	 * Método: UC_INV_132 Permite crear un registro de comando para ser enviado a IBS cuando se mueve un elemento entre bodegas.
	 * 
	 * 
	 * @param ElementMovementDTO dto
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 * @author jnova EliminarPorCambioProceso
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createMovementCommandQueue(ElementMovementDTO dto) throws BusinessException;
    
	/**
	 * Método: Obtiene los registros de los comandos a enviar a IBS con estado PENDIENTE.
	 * 
	 * @return List<MovCmdQueueVO>
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdQueueVO> getPendingMovCmdQueueRecords() throws BusinessException;
	

	/**
	 * Método: Obtiene los registros de los comandos a enviar a IBS con estado PENDIENTE
	 * paginados teniendo en cuenta parametro de sistema.
	 * 
	 * @return List<MovCmdQueueVO>
	 * @throws BusinessException
	 * 
	 * @author jnova
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdQueueVO> getPendingMovCmdQueueRecordsByPage(Long countryId) throws BusinessException;

	/**
	 * Req-0098 - Paralelismo de Inventarios
	 * Método: Obtiene los registros de los comandos a enviar a IBS con estado PENDIENTE
	 * paginados teniendo en cuenta parametro de sistema
	 * y teniendo en cuenta las marcas de estado para el paralelismo
	 * 
	 * @return List<MovCmdQueueVO>
	 * @throws BusinessException
	 * 
	 * @author ialessan
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdQueueVO> getPendingMovCmdQueueRecordsByPageParallel(Long countryId) throws BusinessException;
		
	/**
	 * Método: Obtiene los registros de los comandos a enviar a IBS con estado ERROR.
	 * 
	 * @return List<MovCmdQueueVO>
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdQueueVO> getErrorMovCmdQueueRecords() throws BusinessException;

	/**
	 * Método: Obtiene los registros de los comandos a enviar a IBS con estado PROCESADO.
	 * 
	 * @return List<MovCmdQueueVO>
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdQueueVO> getCompletedMovCmdQueueRecords() throws BusinessException;

	/**
	 * Método: Obtiene todos los registros de los comandos a enviar a IBS sin importar el estado.
	 * 
	 * @return List<MovCmdQueueVO>
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdQueueVO> getAllMovCmdQueueRecords() throws BusinessException;

	/**
	 * Método: Obtiene los registros de los comandos que no fueron enviados a IBS por falta de
	 * configuración.
	 * 
	 * @return List<MovCmdQueueVO>
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdQueueVO> getNoConfigMovCmdQueueRecords() throws BusinessException;

	/**
	 * UC_INV_10
	 * Método: Permite invocar los servicios de informe de cambio de estado de elemento y
	 * movimientos de elementos en IBS.
	 * 
	 * @param record
	 * @param countryConfig
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void callStockService(MovCmdQueueVO record) throws BusinessException;

	/**
	 * Método: Permite crear el registro en el archivo de Log de movimientos a IBS.
	 * 
	 * @param queueRecordId
	 * @param message
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 * 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createMovCmdLogRecord(Long queueRecordId, String message, User user) throws BusinessException;
	
	/**
	 * Método: Permite crear el registro en el archivo de Log de movimientos a IBS cuando hay error de configuracion.
	 * 
	 * @param queueRecordId
	 * @throws BusinessException
	 * 
	 * @author jnova
	 * 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createMovCmdLogRecordNoConfig(Long queueRecordId, User user) throws BusinessException;
	
	/**
	 * Método: Permite crear el registro en el archivo de Log de movimie User userntos a IBS cuando hay configuracion
	 * inactiva.
	 * 
	 * @param queueRecordId
	 * @throws BusinessException
	 * 
	 * @author jnova
	 * 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createMovCmdLogRecordConfigInactive(Long queueRecordId, User user) throws BusinessException;
	
	/**
	 * Método: Permite crear el registro en el archivo de Log de movimientos a IBS.
	 * 
	 * @param queueRecordId
	 * @throws BusinessException
	 * 
	 * @author jnova
	 * 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createMovCmdLogRecordSuccess(Long queueRecordId, String typeProcess, User user) throws BusinessException;
	
	/**
	 * Método: Permite crear el registro en el archivo de Log de movimientos a IBS cuando hay error de 
	 * no existencia de estado IBS.
	 * 
	 * @param queueRecordId
	 * @param message
	 * @throws BusinessException
	 * 
	 * @author jnova
	 * 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createMovCmdLogRecordNoIbsStatus(Long queueRecordId, User user) throws BusinessException;
	
	/**
	 * Método: Permite actualizar un registro de la Cola de Comandos que se envían a IBS a estado ERROR.
	 * 
	 * @param record
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void changeMovCmdQueueRecordStatusToError(MovCmdQueueVO record, User user) throws BusinessException;

	/**
	 * Método: Permite actualizar un registro de la Cola de Comandos que se envían a IBS a estado SIN CONFIGURACION.
	 * 
	 * @param record
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void changeMovCmdQueueRecordStatusToNoConfig(MovCmdQueueVO record, User user) throws BusinessException;
	
	/**
	 * Método: Permite actualizar un registro de la Cola de Comandos que se envían a IBS a estado SIN ESTADO IBS.
	 * 
	 * @param record
	 * @throws BusinessException
	 * 
	 * @author jnova
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void changeMovCmdQueueRecordStatusToNoIbsElementStatus(MovCmdQueueVO record, User user) throws BusinessException;

	/**
	 * Método: Permite actualizar un registro de la Cola de Comandos que se envían a IBS a estado EN PROCESADO.
	 * 
	 * @param record
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void changeMovCmdQueueRecordStatusToProcessing(MovCmdQueueVO record, User user) throws BusinessException;
	
	/**
	 * Método: Permite actualizar un registro de la Cola de Comandos que se envían a IBS a estado PROCESADO.
	 * 
	 * @param record
	 * @throws BusinessException
	 * 
	 * @author jnova
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void changeMovCmdQueueRecordStatusToProcessed(MovCmdQueueVO record, User user) throws BusinessException;


}
