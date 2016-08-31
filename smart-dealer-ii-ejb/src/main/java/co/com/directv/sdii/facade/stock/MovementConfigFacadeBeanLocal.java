package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ElementMovementDTO;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;

/**
 * Interfaz Local que define los métodos que permiten informar el estado de los elementos
 * y los movimientos que se realizan entre bodegas de SmartDealerII a IBS.
 * 
 * @author Jimmy Vélez Muñoz
 *
 */

@Local
public interface MovementConfigFacadeBeanLocal {

	/**
	 * Método: UC_INV_132 Permite crear un registro de comando para ser enviado a IBS cuando se mueve un elemento entre bodegas.
	 * 
	 * 
	 * @param MovCmdQueueVO vo
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	public void createMovementCommandQueue(ElementMovementDTO dto) throws BusinessException;
    
	/**
	 * Método: Obtiene los registros de los comandos a enviar a IBS con estado PENDIENTE.
	 * 
	 * @return List<MovCmdQueueVO>
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	public List<MovCmdQueueVO> getPendingMovCmdQueueRecords() throws BusinessException;

	/**
	 * Método: Obtiene los registros de los comandos a enviar a IBS con estado ERROR.
	 * 
	 * @return List<MovCmdQueueVO>
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	public List<MovCmdQueueVO> getErrorMovCmdQueueRecords() throws BusinessException;

	/**
	 * Método: Obtiene los registros de los comandos a enviar a IBS con estado PROCESADO.
	 * 
	 * @return List<MovCmdQueueVO>
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	public List<MovCmdQueueVO> getCompletedMovCmdQueueRecords() throws BusinessException;

	/**
	 * Método: Obtiene todos los registros de los comandos a enviar a IBS sin importar el estado.
	 * 
	 * @return List<MovCmdQueueVO>
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	public List<MovCmdQueueVO> getAllMovCmdQueueRecords() throws BusinessException;

	/**
	 * Método: Permite invocar los servicios de informe de cambio de estado de elemento y
	 * movimientos de elementos en IBS.
	 * 
	 * @param record
	 * @param countryConfig
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
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
	public void createMovCmdLogRecord(Long queueRecordId, String message, User user) throws BusinessException;
	
	/**
	 * Método: Permite actualizar un registro de la Cola de Comandos que se envían a IBS a estado ERROR.
	 * 
	 * @param record
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	public void changeMovCmdQueueRecordStatusToError(MovCmdQueueVO record, User user) throws BusinessException;

	/**
	 * Método: Permite actualizar un registro de la Cola de Comandos que se envían a IBS a estado SIN CONFIGURACION.
	 * 
	 * @param record
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	public void changeMovCmdQueueRecordStatusToNoConfig(MovCmdQueueVO record, User user) throws BusinessException;

	/**
	 * Método: Permite actualizar un registro de la Cola de Comandos que se envían a IBS a estado PROCESADO.
	 * 
	 * @param record
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	public void changeMovCmdQueueRecordStatusToProcessing(MovCmdQueueVO record, User user) throws BusinessException;

}