package co.com.directv.sdii.ejb.business.core;

import java.util.List;

import javax.ejb.Local;


import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.MessageCoreAllocatorDTO;
import co.com.directv.sdii.model.vo.WorkOrderVO;

@Local
public interface WoInfoEsbServiceBusinessLocal {

	/**
	 * Crea un evento en la tabla de eventos de procesamiento en paralelo de core y asignador, para el proceso de asignador
	 * @param countryId id del pais al cual pertenece la work order que se dejara encolada para el procesamiento en paralelo de asignador
	 * @param woId id de la work order que se dejara para que el proceso de asignador 
	 * @param woCode codigo de la work order que se dejara para que el proceso de asignador
	 * @param customerCode codigo del cliente al que pertenece la work order
	 * @throws BusinessException
	 * @author Aharker
	 */
	public void createWoInfoEsbServiceForAllocator(Long countryId, Long woId, String woCode, String customerCode) throws BusinessException ;
	
	/**
	 * Prepara los mensajes que debe enviar a procesar en paralelo de core y asignador y los envia a sus respectivas colas de mensajes
	 * @param countryId id del pais del cual se llevara a cabo el proceso de core
	 * @throws BusinessException
	 * @author Aharker
	 */
	public void prepareAndSendMessageForAllocatorAndCore(Long countryId) throws BusinessException ;
	
	/**
	 * Genera un registro en la tabla de logs de procesamiento en paralelo de core y asignador
	 * @param woInfoEsbServiceId id de la tabla de eventos de core y asignador al cual se le va a generar log
	 * @param codeStatus codigo del estado en el que debe quedar el evento de core o de asignador
	 * @param reg mensaje de log que se generara
	 * @param codeRegisterLogType en caso de necesitar notificacion via correo electronico, tipo de notificacion que se necesita, si es de core o 
	 * 								asignador
	 * @param codeTypeProccess codigo del tipo de proceso al que se le genera log, si es core o asignador
	 * @param countryId pais al que pertenece la work order que se proceso
	 * @param tryNum numero de intentos que lleva de procesamiento el evento de core o asignador
	 * @throws BusinessException
	 * @author Aharker
	 */
	public void generateLogCoreAndAllocator(Long woInfoEsbServiceId, String codeStatus, String reg, String codeRegisterLogType, String codeTypeProccess, Long countryId, boolean tryNum) throws BusinessException;
	
	/**
	 * Crea un evento en la tabla de eventos de procesamiento en paralelo de core y asignador, para el proceso de asignador
	 * @param publishWOEvent objeto del mensaje descargado de ESB.
	 * @param countryId pais al que pertenece la Work order
	 * @throws BusinessException
	 * @author Aharker
	 */
	public void createWoInfoEsbServiceForCore(co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent publishWOEvent, Long countryId)throws BusinessException;
	
	/**
	 * Crea un objeto de la descarga de core a partir del xml guardado en base de datos
	 * @param message mensaje recojido de la cola de mensajes de core para el proceamiento en paralelo de core
	 * @return a partir del XML guardado en base de datos arma un objeto de la descarga de core
	 * @throws BusinessException
	 * @author Aharker
	 */
	public co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent createWoIbsFromXmlData(MessageCoreAllocatorDTO message) throws BusinessException;

	/**
	 * Metodo encargado del envio de los reportes de errores del procesamiento en paralelo de core y asignador
	 * @param countryId pais del cual se desean los reportes
	 * @throws BusinessException
	 * @author Aharker
	 */
	public void sendEmailReportCoreAllocator(Long countryId) throws BusinessException;
	
	/**
	 * Metodo encargado de eliminar un registro de eventos de la tabla de procesamiento en paralelo de core y asignador
	 * @param id id del registro de eventos de la tabla de procesamiento en paralelo que se desea eliminar
	 * @return si se logro eliminar o no
	 * @throws BusinessException
	 * @author Aharker
	 */
	public Boolean deleteWoInfoEsbService(Long id) throws BusinessException;
	
	/**
	 * Metodo enfocado a consultar que codigo de work orders de un conjunto especifico esta pendiente por el proceso en paralelo para un tipo de proceso dado 
	 * @param workOrders consjunto de work orders que se desea validar
	 * @param status estados de las work order que se necesitan validar
	 * @param process oproceso para el cual se validara
	 * @return
	 * @throws BusinessException
	 * @author Aharker
	 */
	public List<String> getPendingProccessForCore(List<WorkOrderVO> workOrders, List<String> status, String process) throws BusinessException;
	
	public void generateLogCoreAndAllocatorSuccesForAllocator(Long woInfoEsbServiceId, String codeStatus, String reg, Long countryId, boolean tryNum) throws BusinessException;
}
