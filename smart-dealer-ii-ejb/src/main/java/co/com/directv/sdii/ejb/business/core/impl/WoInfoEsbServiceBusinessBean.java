package co.com.directv.sdii.ejb.business.core.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.EmailTypesEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.JMSLocator;
import co.com.directv.sdii.common.util.ExcelGenerator;
import co.com.directv.sdii.common.util.ExcelGeneratorLocal;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.common.EmailTypeBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.core.WoInfoEsbServiceBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.jms.common.util.DistributedQueueMessage;
import co.com.directv.sdii.model.dto.MessageCoreAllocatorDTO;
import co.com.directv.sdii.model.dto.SendEmailDTO;
import co.com.directv.sdii.model.dto.WoInfoEsbServiceDTO;
import co.com.directv.sdii.model.dto.WoInfoEsbServiceReportDTO;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.WoInfoEsbParentDate;
import co.com.directv.sdii.model.pojo.WoInfoEsbService;
import co.com.directv.sdii.model.pojo.WoInfoEsbState;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoInfoEsbServiceDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;

/**
 * Session Bean implementation class WoInfoEsbServiceBusinessBean
 * 
 * @author Aharker
 */
@Stateless(name="WoInfoEsbServiceBusinessBean",mappedName="ejb/WoInfoEsbServiceBusinessBean")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WoInfoEsbServiceBusinessBean extends BusinessBase implements WoInfoEsbServiceBusinessLocal {

	@EJB(name="WoInfoEsbServiceDAOLocal", beanInterface=WoInfoEsbServiceDAOLocal.class)
	private WoInfoEsbServiceDAOLocal woInfoEsbServiceDAOLocal;

	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;

    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
    
	@EJB
	private ExcelGeneratorLocal excelGenerator;
	
	@EJB(name="EmailTypeBusinessBeanLocal", beanInterface=EmailTypeBusinessBeanLocal.class)
    private EmailTypeBusinessBeanLocal businessEmailType;
    
    private final static Logger log = UtilsBusiness.getLog4J(WoInfoEsbServiceBusinessBean.class);
    
    /**
     * Default constructor. 
     */
    public WoInfoEsbServiceBusinessBean() {
    }

	/**
	 * Crea un evento en la tabla de eventos de procesamiento en paralelo de core y asignador, para el proceso de asignador
	 * @param countryId id del pais al cual pertenece la work order que se dejara encolada para el procesamiento en paralelo de asignador
	 * @param woId id de la work order que se dejara para que el proceso de asignador 
	 * @param woCode codigo de la work order que se dejara para que el proceso de asignador
	 * @param customerCode codigo del cliente al que pertenece la work order
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createWoInfoEsbServiceForAllocator(Long countryId, Long woId, String woCode, String customerCode)
			throws BusinessException {
		log.debug("== Inicia createWoInfoEsbServiceForAllocator/StateMachineWOBusiness ==");
		try{
			Date dateNow=UtilsBusiness.getDateLastChangeOfUser(countryId, userDao, systemParameterDAO);
			WoInfoEsbService wies = new WoInfoEsbService();
			WoInfoEsbParentDate woInfoEsbParentDate = woInfoEsbServiceDAOLocal.getDateForTheCountry(dateNow, countryId);
			if(woInfoEsbParentDate!=null){
				wies.setWoInfoEsbParentDate(woInfoEsbParentDate);
			}
			else{
				woInfoEsbParentDate = new WoInfoEsbParentDate(null,new Country(countryId),new Timestamp(dateNow.getTime()));
				
				try{
					woInfoEsbServiceDAOLocal.createWoInfoEsbParentDate(woInfoEsbParentDate);
				}catch(DAOSQLException daose ){
					log.warn("Se intentó crear un WoInfoEsbParentDate con la misma fecha. Se le asignará la fecha ya creada. Sucedió para el Customer de codigo :"+ customerCode +" y WorkOrder:"+ woId );
					woInfoEsbParentDate = woInfoEsbServiceDAOLocal.getDateForTheCountry(dateNow, countryId);
				}
				
				wies.setWoInfoEsbParentDate(woInfoEsbParentDate);
			}
			wies.setCreationDate(new Timestamp(dateNow.getTime()));
			wies.setLastDateProccess(null);
			wies.setCustomerCode(customerCode);
			wies.setStateWoInfo(woInfoEsbServiceDAOLocal.getWoInfoEsbStateByCode(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_PENDING.getCodeEntity()));
			wies.setTryNumbers(0L);
			wies.setWoCode(woCode);
			wies.setWoInfoEsbType(woInfoEsbServiceDAOLocal.getWoInfoEsbTypeByCode(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_ALLOCATOR.getCodeEntity()));
			wies.setIbsCreationDate(new Timestamp(dateNow.getTime()));
			wies.setNumIbsCreationDate(dateNow.getTime());
			woInfoEsbServiceDAOLocal.createWoInfoEsbService(wies);
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createWoInfoEsbServiceForAllocator/WoInfoEsbServiceBusinessBean ==");
        	throw this.manageException(ex);
        }finally {
	        log.debug("== Termina createWoInfoEsbServiceForAllocator/StateMachineWOBusiness ==");
	    }	

	}

	/**
	 * Crea un evento en la tabla de eventos de procesamiento en paralelo de core y asignador, para el proceso de asignador
	 * @param woibs objeto de la descarga de core
	 * @param countryId pais al que pertenece la Work order
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createWoInfoEsbServiceForCore(co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent publishWOEvent, Long countryId)throws BusinessException {
		log.debug("== Inicia createWoInfoEsbServiceForCore/StateMachineWOBusiness ==");
		try{
			log.info("Se importa evento de la WO: "+ publishWOEvent.getDataArea().getWorkOrder().getID());
			
			Date dateNow=UtilsBusiness.getDateLastChangeOfUser(countryId, userDao, systemParameterDAO);
			Date dateNowWithoutTime = UtilsBusiness.getFirstMomentOfDay(dateNow);
			WoInfoEsbService wies = new WoInfoEsbService();

			WoInfoEsbParentDate woInfoEsbParentDate = woInfoEsbServiceDAOLocal.getDateForTheCountry(dateNowWithoutTime, countryId);
			if(woInfoEsbParentDate == null){
				woInfoEsbParentDate = new WoInfoEsbParentDate(null,new Country(countryId),new Timestamp(dateNowWithoutTime.getTime()));
				try{
					woInfoEsbServiceDAOLocal.createWoInfoEsbParentDate(woInfoEsbParentDate);
				}catch(DAOSQLException daose ){
					log.warn("Se intentó crear un WoInfoEsbParentDate con la misma fecha. Se le asignará la fecha ya creada. Sucedió para el Customer:" + publishWOEvent.getDataArea().getCustomer().getID() +" y WorkOrder:"+ publishWOEvent.getDataArea().getWorkOrder().getID());
					woInfoEsbParentDate = woInfoEsbServiceDAOLocal.getDateForTheCountry(dateNowWithoutTime, countryId);
				}
			}
			wies.setWoInfoEsbParentDate(woInfoEsbParentDate);
			
			wies.setCreationDate(new Timestamp(dateNow.getTime()));
			wies.setLastDateProccess(null);
			wies.setCustomerCode(publishWOEvent.getDataArea().getCustomer().getID());
			
			wies.setTryNumbers(0L);
			wies.setWoCode(publishWOEvent.getDataArea().getWorkOrder().getID());
			wies.setWoInfoEsbType(woInfoEsbServiceDAOLocal.getWoInfoEsbTypeByCode(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_CORE.getCodeEntity()));
			
			//Usado por el WS de update
			Date createDate = dateNow;
			if(publishWOEvent.getDataArea().getHistoryEvent().getCreateDatetime() != null){
				createDate = UtilsBusiness.dateFromGregorianCalendar(publishWOEvent.getDataArea().getHistoryEvent().getCreateDatetime());
			}
			
			wies.setIbsCreationDate(new Timestamp(createDate.getTime()));
			wies.setNumIbsCreationDate(createDate.getTime());
			
			JAXBContext jc = JAXBContext.newInstance(co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent.class);
			Marshaller m = jc.createMarshaller();
			ByteArrayOutputStream baos = null;
	        baos = new ByteArrayOutputStream();
	        m.marshal(publishWOEvent, baos);
			
			//Inicio PR6008
			Date lastWOEventDate = woInfoEsbServiceDAOLocal.getLastWOEventDate(publishWOEvent.getDataArea().getWorkOrder().getID());
			if(lastWOEventDate == null ||
				wies.getIbsCreationDate().after(lastWOEventDate) ||
					wies.getIbsCreationDate().equals(lastWOEventDate)){
				wies.setStateWoInfo(woInfoEsbServiceDAOLocal.getWoInfoEsbStateByCode(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_PENDING.getCodeEntity()));
			}else{
				wies.setStateWoInfo(woInfoEsbServiceDAOLocal.getWoInfoEsbStateByCode(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED_WITH_ERRORS.getCodeEntity()));
			}
			
			woInfoEsbServiceDAOLocal.createWoInfoEsbService(wies, baos.toByteArray());
									
			if(wies.getStateWoInfo().getCode().equals(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED_WITH_ERRORS.getCodeEntity())){
				woInfoEsbServiceDAOLocal.updateWoInfoEsbServiceSatate(wies.getId(), CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED_WITH_ERRORS.getCodeEntity(), true ,dateNow);
				String description = "La fecha del nuevo evento recibido es anterior a la fecha del ultimo evento recibido.";
	        	woInfoEsbServiceDAOLocal.createWoInfoEsbServiceLog(wies.getId(), wies.getIbsCreationDate(), description, null);
			}
	        //Fin PR6008
			
		}catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createWoInfoEsbServiceForCore/WoInfoEsbServiceBusinessBean ==");
        	throw this.manageException(ex);
        }finally {
	        log.debug("== Termina createWoInfoEsbServiceForCore/StateMachineWOBusiness ==");
	    }	

	}
	
	/**
	 * Prepara los mensajes que debe enviar a procesar en paralelo de core y asignador y los envia a sus respectivas colas de mensajes
	 * @param countryId id del pais del cual se llevara a cabo el proceso de core
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void prepareAndSendMessageForAllocatorAndCore(Long countryId)
			throws BusinessException {
		log.debug("== Inicia prepareAndSendMessageForAllocatorAndCore/WoInfoEsbServiceBusinessBean ==");
		try{
			Date dateNow=UtilsBusiness.getDateLastChangeOfUser(countryId, userDao, systemParameterDAO);
			List<WoInfoEsbServiceDTO> WoInfoEsbServiceDTOs = woInfoEsbServiceDAOLocal.searchBlockInfoEsbService(countryId, dateNow);
			for(WoInfoEsbServiceDTO wiesDTO:WoInfoEsbServiceDTOs){
				woInfoEsbServiceDAOLocal.updateWoInfoEsbServiceSatate(wiesDTO.getId(), CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED_WITH_ERRORS.getCodeEntity(), true ,dateNow);
				woInfoEsbServiceDAOLocal.createWoInfoEsbServiceLog(wiesDTO.getId(), dateNow, ErrorBusinessMessages.CORE_ALLOCATOR_PARALLEL_2.getMessage(), null);
			}
			List<MessageCoreAllocatorDTO> returnValue = new ArrayList<MessageCoreAllocatorDTO>();
			
			Long maxRecords = Long.parseLong(CodesBusinessEntityEnum.PARALLEL_PROCCES_MAX_RECORDS.getCodeEntity());
				
			Long idStarted=woInfoEsbServiceDAOLocal.getWoInfoEsbStateByCode(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_STARTED.getCodeEntity()).getId();
			Long idReprocesing=woInfoEsbServiceDAOLocal.getWoInfoEsbStateByCode(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_PENDING_REPROCESSING.getCodeEntity()).getId();
			Long idPending=woInfoEsbServiceDAOLocal.getWoInfoEsbStateByCode(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_PENDING.getCodeEntity()).getId();
				
			List<Long> stateIds=new ArrayList<Long>();
			stateIds.add(idReprocesing);
			stateIds.add(idStarted);
			stateIds.add(idPending);
			
			List<Long> stateNoIds=new ArrayList<Long>();
			stateNoIds.add(idStarted);
			stateNoIds.add(idPending);
			
			
			String delayTimeMsStr = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SDII_WO_INFO_DELAY_TIME_MS.getCodeEntity(), countryId).getValue();
			Long delayTimeMs = new Long(delayTimeMsStr);
			
			//Buscando eventos de CORE para reprocesar.
			List<MessageCoreAllocatorDTO> messageCoreDTOsReproccesing = woInfoEsbServiceDAOLocal.findAllWoInfoEsbServiceMessageByParameters(
					stateIds, maxRecords, countryId,stateNoIds, true, dateNow, CodesBusinessEntityEnum.SDII_WO_INFO_ESB_CORE.getCodeEntity(), delayTimeMs);
			List<Long> beginIds=new ArrayList<Long>();
			for(MessageCoreAllocatorDTO mcad:messageCoreDTOsReproccesing){
				beginIds.add(mcad.getId());
			}
			woInfoEsbServiceDAOLocal.updateWoInfoEsbServiceStateInBlock(beginIds, idStarted);
			returnValue.addAll(messageCoreDTOsReproccesing);
			
			//Buscando eventos de ALLOCATOR para reprocesar.
			messageCoreDTOsReproccesing = woInfoEsbServiceDAOLocal.findAllWoInfoEsbServiceMessageByParameters(
					stateIds, maxRecords, countryId,stateNoIds, true, dateNow, CodesBusinessEntityEnum.SDII_WO_INFO_ESB_ALLOCATOR.getCodeEntity(), delayTimeMs);
			beginIds=new ArrayList<Long>();
			for(MessageCoreAllocatorDTO mcad:messageCoreDTOsReproccesing){
				beginIds.add(mcad.getId());
			}
			woInfoEsbServiceDAOLocal.updateWoInfoEsbServiceStateInBlock(beginIds, idStarted);
			returnValue.addAll(messageCoreDTOsReproccesing);
			
			stateIds=new ArrayList<Long>();
			stateIds.add(idPending);
			stateIds.add(idStarted);
			stateIds.add(idReprocesing);
			
			stateNoIds=new ArrayList<Long>();
			stateNoIds.add(idStarted);
			stateNoIds.add(idReprocesing);
			
			//Buscando eventos para procesar por primera vez.
			List<MessageCoreAllocatorDTO> messageCoreDTOsproccesing = woInfoEsbServiceDAOLocal.findAllWoInfoEsbServiceMessageByParameters(
					stateIds, maxRecords, countryId,stateNoIds, false, dateNow, null, delayTimeMs);
			beginIds=new ArrayList<Long>();
			for(MessageCoreAllocatorDTO mcad:messageCoreDTOsproccesing){
				beginIds.add(mcad.getId());
			}
			woInfoEsbServiceDAOLocal.updateWoInfoEsbServiceStateInBlock(beginIds, idStarted);
			returnValue.addAll(messageCoreDTOsproccesing);
			int coreCount = 0;
			int allocatorcount=0;
			for(MessageCoreAllocatorDTO messageDTO: returnValue){
				if(messageDTO.getProcessCode().equalsIgnoreCase(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_ALLOCATOR.getCodeEntity())){
			    	DistributedQueueMessage distributedQueueMessage = JMSLocator.getInstance().getQueueAllocatorParallel();
			    	distributedQueueMessage.sendMessage(messageDTO);
			    	allocatorcount++;
				}else if(messageDTO.getProcessCode().equalsIgnoreCase(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_CORE.getCodeEntity())){
			    	DistributedQueueMessage distributedQueueMessage = JMSLocator.getInstance().getQueueCoreParallel();
			    	distributedQueueMessage.sendMessage(messageDTO);
			    	coreCount++;
				}
			}
			log.info("Se enviaron a la cola "+coreCount+" mensajes de core y "+allocatorcount+" mensajes de asignador ");
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación prepareAndSendMessageForAllocatorAndCore/WoInfoEsbServiceBusinessBean ==");
        	throw this.manageException(ex);
        }finally {
	        log.debug("== Termina prepareAndSendMessageForAllocatorAndCore/WoInfoEsbServiceBusinessBean ==");
	    }
		
	}

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
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void generateLogCoreAndAllocator(Long woInfoEsbServiceId, String codeStatus, String reg, String codeRegisterLogType, String codeTypeProccess, Long countryId, boolean tryNum) throws BusinessException{
		log.debug("== Inicia generateLogCoreAndAllocator/StateMachineWOBusiness ==");
		try{
			Date dateNow=UtilsBusiness.getDateLastChangeOfUser(countryId, userDao, systemParameterDAO);
			woInfoEsbServiceDAOLocal.createWoInfoEsbServiceLog(woInfoEsbServiceId, dateNow, reg, codeRegisterLogType);
			woInfoEsbServiceDAOLocal.updateWoInfoEsbServiceSatate(woInfoEsbServiceId, codeStatus, tryNum, dateNow);
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación generateLogCoreAndAllocator/WoInfoEsbServiceBusinessBean ==");
			throw this.manageException(ex);
		}finally {
			log.debug("== Termina generateLogCoreAndAllocator/StateMachineWOBusiness ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void generateLogCoreAndAllocatorSuccesForAllocator(Long woInfoEsbServiceId, String codeStatus, String reg, Long countryId, boolean tryNum) throws BusinessException{
		log.debug("== Inicia generateLogCoreAndAllocatorSuccesForAllocator/StateMachineWOBusiness ==");
		try{
			Date dateNow=UtilsBusiness.getDateLastChangeOfUser(countryId, userDao, systemParameterDAO);
			woInfoEsbServiceDAOLocal.createWoInfoEsbServiceLog(woInfoEsbServiceId, dateNow, reg, null);
			woInfoEsbServiceDAOLocal.createWoInfoEsbServiceLog(woInfoEsbServiceId, dateNow, null, null);
			woInfoEsbServiceDAOLocal.updateWoInfoEsbServiceSatate(woInfoEsbServiceId, codeStatus, tryNum, dateNow);
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación generateLogCoreAndAllocatorSuccesForAllocator/WoInfoEsbServiceBusinessBean ==");
			throw this.manageException(ex);
		}finally {
			log.debug("== Termina generateLogCoreAndAllocatorSuccesForAllocator/StateMachineWOBusiness ==");
		}
	}
	
	/**
	 * Crea un objeto de la descarga de core a partir del xml guardado en base de datos
	 * @param message mensaje recojido de la cola de mensajes de core para el proceamiento en paralelo de core
	 * @return a partir del XML guardado en base de datos arma un objeto de la descarga de core
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent createWoIbsFromXmlData(
			MessageCoreAllocatorDTO message) throws BusinessException {
		log.debug("== Inicia createWoIbsFromXmlData/StateMachineWOBusiness ==");
		try {
			WoInfoEsbService woInfoEsbService = woInfoEsbServiceDAOLocal.findWoInfoEsbServiceById(message.getId());
			InputStream inStream = null;
			inStream = woInfoEsbService.getResponseXml().getBinaryStream();
	        JAXBContext jc = JAXBContext.newInstance(co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent.class);
	        Unmarshaller u = jc.createUnmarshaller();
	        co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent returnValue=(co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent)u.unmarshal(inStream);
			return returnValue;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación createWoIbsFromXmlData/WoInfoEsbServiceBusinessBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina createWoIbsFromXmlData/StateMachineWOBusiness ==");
		}
	}
	
	/**
	 * Metodo encargado del envio de los reportes de errores del procesamiento en paralelo de core y asignador
	 * @param countryId pais del cual se desean los reportes
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void sendEmailReportCoreAllocator(Long countryId) throws BusinessException {
		log.debug("== Inicia sendEmailReportCoreAllocator/StateMachineWOBusiness ==");
		String[] nameFilesAllocator = null;
		String[] nameFilesCore = null;
		try {
			
			File directory = new File(ExcelGenerator.getReportsPathTemp());
			
			if(!directory.exists()){
				directory.mkdir();
			}
			
			SystemParameter sysParam = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_WO_LOAD_REPORT_RECIPIENT_MAIL.getCodeEntity(), countryId);
			UtilsBusiness.assertNotNull(sysParam, ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage() + " No se encontró el parámetro del sistema con el código: " + CodesBusinessEntityEnum.SYSTEM_PARAM_WO_LOAD_REPORT_RECIPIENT_MAIL.getCodeEntity() + " que determina el correo electrónico al que se le notificarán los reportes de cargue de Work orders");
			String recipientMail = sysParam.getValue();
			
			String content=" ";

			ByteArrayOutputStream baos = null;
			List<WoInfoEsbServiceReportDTO> woInfoEsbServiceReportDTOCore = woInfoEsbServiceDAOLocal.searchInfoReport(countryId, 
					CodesBusinessEntityEnum.SDII_WO_INFO_ESB_NOTIFICATION_TYPE_CORE.getCodeEntity());
			List<WoInfoEsbServiceReportDTO> woInfoEsbServiceReportDTOCoreWarning = woInfoEsbServiceDAOLocal.searchInfoReport(countryId, 
					CodesBusinessEntityEnum.SDII_WO_INFO_ESB_NOTIFICATION_TYPE_CORE_WARNING.getCodeEntity());
			if((woInfoEsbServiceReportDTOCore!=null && !woInfoEsbServiceReportDTOCore.isEmpty()) || (woInfoEsbServiceReportDTOCoreWarning!=null && !woInfoEsbServiceReportDTOCoreWarning.isEmpty())){
				int count=0;
				String nameFileCore=null;
				String nameFileCoreWarning=null;
				if(woInfoEsbServiceReportDTOCore!=null && !woInfoEsbServiceReportDTOCore.isEmpty()){
					nameFileCore=excelGenerator.createExcelFileWithJasper(woInfoEsbServiceReportDTOCore, null, null, 
							CodesBusinessEntityEnum.SDII_JASPER_CORE_PROCES.getCodeEntity(), "Error", ExcelGenerator.getTempPath());
					count++;
				}
				if(woInfoEsbServiceReportDTOCoreWarning!=null && !woInfoEsbServiceReportDTOCoreWarning.isEmpty()){
					nameFileCoreWarning=excelGenerator.createExcelFileWithJasper(woInfoEsbServiceReportDTOCoreWarning, null, null, 
							CodesBusinessEntityEnum.SDII_JASPER_CORE_PROCES.getCodeEntity(), "Warning", ExcelGenerator.getTempPath());
					count++;
				}
				nameFilesCore=new String[count];
				if(nameFileCoreWarning!=null){
					nameFilesCore[count-1]=nameFileCoreWarning;
					count--;
				}
				if(nameFileCore!=null){
					nameFilesCore[count-1]=nameFileCore;
				}
				SendEmailDTO sendEmailDTO = createEmail(recipientMail, content, nameFilesCore, EmailTypesEnum.WO_LOAD_REPORT.getEmailTypecode(), ApplicationTextEnum.WO_LOAD.getApplicationTextValue(),ApplicationTextEnum.AUTOMATIC_LOADING_PROCESS.getApplicationTextValue());
				businessEmailType.sendEmailByEmailTypeCodeSinc(sendEmailDTO, countryId);
				
				for(int i=0; i<nameFilesCore.length; ++i){
					File file=new File(nameFilesCore[i]);
					file.delete();
				}
				
				woInfoEsbServiceDAOLocal.updateStateProcesbyReport(woInfoEsbServiceReportDTOCore, CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
				woInfoEsbServiceDAOLocal.updateStateProcesbyReport(woInfoEsbServiceReportDTOCoreWarning, CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
			}

			List<WoInfoEsbServiceReportDTO> woInfoEsbServiceReportDTOAllocator = woInfoEsbServiceDAOLocal.searchInfoReport(countryId, 
					CodesBusinessEntityEnum.SDII_WO_INFO_ESB_NOTIFICATION_TYPE_ALLOCATOR.getCodeEntity());
			if(woInfoEsbServiceReportDTOAllocator!=null && !woInfoEsbServiceReportDTOAllocator.isEmpty()){
				String nameFileAllocator = excelGenerator.createExcelFileWithJasper(woInfoEsbServiceReportDTOAllocator, null, null, 
						CodesBusinessEntityEnum.SDII_JASPER_ALLOCATOR_PROCES.getCodeEntity(), "", ExcelGenerator.getTempPath());
				
				nameFilesAllocator=new String[1];
				nameFilesAllocator[0]=nameFileAllocator;
				SendEmailDTO sendEmailDTOAllocator = createEmail(recipientMail, content, nameFilesAllocator, EmailTypesEnum.PROCESS_ALLOCATOR_REPORT.getEmailTypecode(), "Proceso de asignador", "Proceso de asignador");
				businessEmailType.sendEmailByEmailTypeCodeSinc(sendEmailDTOAllocator, countryId);
				
				for(int i=0; i<nameFilesAllocator.length; ++i){
					File file=new File(nameFilesAllocator[i]);
					file.delete();
				}
				
				woInfoEsbServiceDAOLocal.updateStateProcesbyReport(woInfoEsbServiceReportDTOAllocator, CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());				
			}
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación sendEmailReportCoreAllocator/WoInfoEsbServiceBusinessBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina sendEmailReportCoreAllocator/StateMachineWOBusiness ==");
			try{
				if(nameFilesCore!=null && nameFilesCore.length>0){
					for(int i=0; i<nameFilesCore.length; ++i){
						File file=new File(nameFilesCore[i]);
						file.delete();
					}
				}
			}catch (Throwable ex) {
			}
			try{
				if(nameFilesAllocator!=null && nameFilesAllocator.length>0){
					for(int i=0; i<nameFilesAllocator.length; ++i){
						File file=new File(nameFilesAllocator[i]);
						file.delete();
					}
				}
			}catch (Throwable ex) {
			}
		}
	}
	
	/**
	 * Metodo encargado de armar una DTO para el envio del correo de reporte de fallas del procesamiento en paralelo de core y asignador
	 * @param recipientMail a quien se le enviara el mail
	 * @param mailContent contenido en texto del mail
	 * @param fileNames nombres de los archivos adjuntos
	 * @param newsType tipo de notificacion
	 * @param newsDocument 
	 * @param newsSourceUser
	 * @return DTO necesaria para el envio de correo
	 * @throws BusinessException
	 * @author Aharker
	 */
	private SendEmailDTO createEmail(String recipientMail, String mailContent, String[] fileNames, String newsType, String newsDocument, String newsSourceUser) throws BusinessException {
		
		SendEmailDTO emailDto= new SendEmailDTO();
		List<String> recipients = new ArrayList<String>();
		recipients.add(recipientMail);
		emailDto.setRecipient(recipients);
		emailDto.setNewsType(newsType);
		emailDto.setNewsObservation(mailContent);
		emailDto.setNewsDocument(newsDocument);
		emailDto.setNewsSourceUser(newsSourceUser);
		File[] sendFiles=new File[fileNames.length];
		for(int i=0; i<fileNames.length; ++i){
			sendFiles[i]=new File(fileNames[i]);
		}
		emailDto.setAttachmentFiles(sendFiles);
		return emailDto;
	}

	/**
	 * Metodo encargado de eliminar un registro de eventos de la tabla de procesamiento en paralelo de core y asignador
	 * @param id id del registro de eventos de la tabla de procesamiento en paralelo que se desea eliminar
	 * @return si se logro eliminar o no
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Boolean deleteWoInfoEsbService(Long id) throws BusinessException {
		try{
			log.debug("== Inicia deleteWoInfoEsbService/StateMachineWOBusiness ==");
			return woInfoEsbServiceDAOLocal.deleteWoInfoEsbService(id);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación deleteWoInfoEsbService/WoInfoEsbServiceBusinessBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina createWoInfoEsbService/StateMachineWOBusiness ==");
		}
	}
	
	/**
	 * Metodo enfocado a consultar que codigo de work orders de un conjunto especifico esta pendiente por el proceso en paralelo para un tipo de proceso dado 
	 * @param workOrders consjunto de work orders que se desea validar
	 * @param status estados de las work order que se necesitan validar
	 * @param process oproceso para el cual se validara
	 * @return
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<String> getPendingProccessForCore(List<WorkOrderVO> workOrders, List<String> status, String process) throws BusinessException{
		try{
			log.debug("== Inicia getPendingProccessForCore/WoInfoEsbServiceBusinessBean ==");
			return woInfoEsbServiceDAOLocal.getPendingProccessForCore(workOrders,status,process);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getPendingProccessForCore/WoInfoEsbServiceBusinessBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getPendingProccessForCore/WoInfoEsbServiceBusinessBean ==");
		}
	}
}
