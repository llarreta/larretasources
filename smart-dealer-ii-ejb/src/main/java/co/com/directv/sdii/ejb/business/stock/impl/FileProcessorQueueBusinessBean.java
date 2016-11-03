package co.com.directv.sdii.ejb.business.stock.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.file.FileDetailProcessBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.file.IFileProcessor;
import co.com.directv.sdii.ejb.business.file.IFileProcessorFactory;
import co.com.directv.sdii.ejb.business.file.LoadFileBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.file.UploadFileBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.file.exception.FileProcessException;
import co.com.directv.sdii.ejb.business.stock.FileProcessorQueueBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.LoadFileVO;
import co.com.directv.sdii.model.vo.UploadFileVO;

@Stateless(name = "FileProcessorQueueBusinessLocal", mappedName = "ejb/FileProcessorQueueBusinessLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FileProcessorQueueBusinessBean implements FileProcessorQueueBusinessLocal{
	
	private final static Logger log = UtilsBusiness.getLog4J(FileProcessorQueueBusinessBean.class);

	
	@EJB(name="UploadFileBusinessBeanLocal", beanInterface=UploadFileBusinessBeanLocal.class)
	private UploadFileBusinessBeanLocal uploadFileBusiness ;
	
	@EJB(name="FileDetailProcessBusinessBeanLocal", beanInterface=FileDetailProcessBusinessBeanLocal.class)
	private FileDetailProcessBusinessBeanLocal fileDetailBusiness;
	
	@EJB(name="LoadFileBusinessBeanLocal", beanInterface=LoadFileBusinessBeanLocal.class)
	private LoadFileBusinessBeanLocal loadFileBusiness ;
	
	@EJB
	private IFileProcessorFactory fileProcessorFactory;
	
	private UploadFileVO uploadFileVOWorking;
	
	@Override
	public void processFile(UploadFileVO uploadFileVO) throws BusinessException{
		
		try{
	        //Procesamiento del archivo
	        uploadFileVOWorking = uploadFileVO;
	        String name = uploadFileVO.getName();
	      //  if(log.isDebugEnabled())log.debug("Empezando a procesar archivo "+name);
	        log.debug("Empezando a procesar archivo "+name);
	        
	        IFileProcessor processor = fileProcessorFactory.getFileProcessor(uploadFileVO.getFileType().getCode());
			LoadFileVO loadFileVO = loadFileBusiness.getLoadFileByIdUploadFileAndFileIn(uploadFileVO.getId());
			processor.readFile(loadFileVO);
			boolean isValidFile = processor.validateFile();
			if (isValidFile) {
				try {
					updateFileStatus(CodesBusinessEntityEnum.FILE_STATUS_PROCESSING.getCodeEntity(), uploadFileVO);
					
					log.debug("Archivo procesado " +name + "pasado a estado procesando");
					 //Thread.sleep(60000);
					 
					 processor.processFile(loadFileVO);
					 updateFileStatus(CodesBusinessEntityEnum.FILE_STATUS_PROCESS_ENDED_WITHOUT_ERRORS.getCodeEntity(), uploadFileVO);
					 //if(log.isDebugEnabled())log.debug("Archivo procesado " +name);
					 log.debug("Archivo procesado " +name);
				} catch (BusinessException be) {
					updateFileStatus(CodesBusinessEntityEnum.FILE_STATUS_PROCESS_ENDED_WITH_ERRORS.getCodeEntity(), uploadFileVO);
					// if(log.isDebugEnabled())log.debug("Archivo "+ name+ " fue procesado con error: " + be.getMessage());
					log.debug("Archivo "+ name+ " fue procesado con error: " + be.getMessage());
					throw be;
				} catch (FileProcessException fpe) {
					updateFileStatus(CodesBusinessEntityEnum.FILE_STATUS_PROCESS_ENDED_WITH_ERRORS.getCodeEntity(), uploadFileVO);
					 //if(log.isDebugEnabled())log.debug("Archivo "+ name+ " fue procesado con error: " + fpe.getMessage());
					log.debug("Archivo "+ name+ " fue procesado con error: " + fpe.getMessage());
				}
			} else {
				updateFileStatus(CodesBusinessEntityEnum.FILE_STATUS_PROCESS_ENDED_WITH_ERRORS.getCodeEntity(), uploadFileVO);
				 //if(log.isDebugEnabled())log.debug("Archivo "+ name+ " no es un archivo valido");
				log.debug("Archivo "+ name+ " no es un archivo valido");
			}
	        
			processor.deleteFile(loadFileVO.getPathFile());
			 //if(log.isDebugEnabled())log.debug("Archivo "+ name+ " va ser borrado");
			log.debug("Archivo "+ name+ " va ser borrado");
	        
		} catch (Exception e) {
			manageFileProcessingException(e, uploadFileVO);
			try {
				updateFileStatus(CodesBusinessEntityEnum.FILE_STATUS_PROCESS_ENDED_WITH_ERRORS.getCodeEntity(), uploadFileVO);
			}catch(Exception e1) {
				log.error("Error: no se pudo actualizar a procesado con error el archivo "+ uploadFileVO.getName()+ " debera hacerse manualmente.");
				throw new BusinessException("Error al intentar actualizar con error el archivo" +uploadFileVO.getName() + " " +e.getMessage());			
			}
		} finally {
			 if(log.isDebugEnabled())log.debug("finaliza proceso de archivo con nombre: " + uploadFileVO.getName());
		}
	}
		
		
	
	private void updateFileStatus(String newFileStatusCode, UploadFileVO file2Update) throws DAOServiceException, DAOSQLException, BusinessException{
		uploadFileBusiness.updateUploadFile(file2Update, newFileStatusCode);
	}
	
	/**
	 * Metodo: persiste en base de datos la información de error general en el procesamiento de un archivo específico.
	 * Si no puede persistir esa información, envía los datos a log 
	 */
	private void manageFileProcessingException(Exception e, UploadFileVO uploadFileVO) {
		Long rowWithError = 0L;
		if (e instanceof BusinessException) {
			BusinessException be = (BusinessException) e;
			if( NumberUtils.isNumber(be.getMessageCode()) ) {
				rowWithError = Long.parseLong( be.getMessageCode() );
			}
		}
		try {
			String message = "";
			if(e.getMessage() != null && !e.getMessage().equals("")){
				message = e.getMessage() != null ? e.getMessage() : "";
			}else if(e.getCause() != null && e.getCause().getMessage() != null && !e.getCause().getMessage().equals("")){
				message = e.getCause().getMessage();
			}else{
				message = "Ocurrio un error Inesperado:" + e.getClass().getName();
			}
			
			saveFileDetailProces(rowWithError, message, uploadFileVOWorking.getId() );
		} catch (Exception persistErrorException) {
			log.error("No fue posible guardar la información del error generado en el procesamiento del archivo con id = " + uploadFileVOWorking.getId() != null ? uploadFileVOWorking.getId() : "null" + ".\n El error con el archivo fue: " + e.getMessage() );
			log.error(persistErrorException);
		}
	}
	
	private void saveFileDetailProces(long row, String message, Long uploafFileWorkingId) throws DAOServiceException, DAOSQLException, BusinessException{
		fileDetailBusiness.saveFileDetailProces(row, message,  uploafFileWorkingId);
	}

	
	
	
}
