package co.com.directv.sdii.ejb.business.file.impl;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.file.FileDetailProcessBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.file.FileProcessorBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.file.FileProcessorBusinessBeanRemote;
import co.com.directv.sdii.ejb.business.file.IFileProcessor;
import co.com.directv.sdii.ejb.business.file.IFileProcessorFactory;
import co.com.directv.sdii.ejb.business.file.LoadFileBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.file.UploadFileBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.file.exception.FileProcessException;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.UploadFileFilterDTO;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.UploadFileResponse;
import co.com.directv.sdii.model.vo.LoadFileVO;
import co.com.directv.sdii.model.vo.UploadFileVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;

/**
 * Permite manejar el procesamiento de los archivos cargas a la aplicación
 * @author gfandino
 *
 */
@Stateless(name="FileProcessorBusinessBeanLocal")
@Local({FileProcessorBusinessBeanLocal.class})
@Remote({FileProcessorBusinessBeanRemote.class})
public class FileProcessorBusinessBean extends BusinessBase implements
		FileProcessorBusinessBeanLocal, FileProcessorBusinessBeanRemote {
	
	private final static Logger log = UtilsBusiness.getLog4J(FileDetailProcessBusinessBean.class);
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO ;
	
	@EJB(name="UploadFileBusinessBeanLocal", beanInterface=UploadFileBusinessBeanLocal.class)
	private UploadFileBusinessBeanLocal uploadFileBusiness ;
	
	@EJB(name="FileDetailProcessBusinessBeanLocal", beanInterface=FileDetailProcessBusinessBeanLocal.class)
	private FileDetailProcessBusinessBeanLocal fileDetailBusiness;
	
	@EJB(name="LoadFileBusinessBeanLocal", beanInterface=LoadFileBusinessBeanLocal.class)
	private LoadFileBusinessBeanLocal loadFileBusiness ;
	
	@EJB
	private IFileProcessorFactory fileProcessorFactory;
	
	private UploadFileVO uploadFileVOWorking;

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.FileProcessorBusinessBeanLocal#processFiles()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void processFiles(Long idCountry) throws BusinessException {
		log.debug("== Inicio processFiles/FileDetailProcessBusinessBean ==");
		//1. Consultar los archivos pendientes por procesar
		//2. Recorrer los archivos pendientes e invocar su respectivo procesador
		
		try{
			//1. Consultar los archivos pendientes por procesar
			UploadFileFilterDTO  filter = new UploadFileFilterDTO();
			filter.setFileStatusCode(CodesBusinessEntityEnum.FILE_STATUS_PENDING.getCodeEntity());
			filter.setCountries(idCountry);
			RequestCollectionInfo request = new RequestCollectionInfo();
			request.setPageIndex(1);
			request.setPageSize(getPageSizeFileProcessor(idCountry));
			UploadFileResponse response = getFilePending(filter, request);//  uploadFileBusiness.findByTypeAndStatusAndUploadDate (filter, request);
			log.debug("se procesarán " + response.getTotalRowCount()  + " archivos");
			List<UploadFileVO> listUploadFileVO =  response.getUploadFileVO();
			//2. Recorrer los archivos pendientes e invocar su respectivo procesador
			for(UploadFileVO uploadFileVO : listUploadFileVO) {
				try {
					log.debug("inicia proceso de archivo con nombre: " + uploadFileVO.getName());
					uploadFileVOWorking = uploadFileVO;
					IFileProcessor processor = fileProcessorFactory.getFileProcessor(uploadFileVO.getFileType().getCode());
					LoadFileVO loadFileVO = loadFileBusiness.getLoadFileByIdUploadFileAndFileIn(uploadFileVO.getId());
					processor.readFile(loadFileVO);
					boolean isValidFile = processor.validateFile();
					if (isValidFile) {
						try {
							updateFileStatus(CodesBusinessEntityEnum.FILE_STATUS_PROCESSING.getCodeEntity(), uploadFileVO);
							processor.processFile(loadFileVO);
							updateFileStatus(CodesBusinessEntityEnum.FILE_STATUS_PROCESS_ENDED_WITHOUT_ERRORS.getCodeEntity(), uploadFileVO);
						} catch (BusinessException be) {
							updateFileStatus(CodesBusinessEntityEnum.FILE_STATUS_PROCESS_ENDED_WITH_ERRORS.getCodeEntity(), uploadFileVO);
							throw be;
						} catch (FileProcessException fpe) {
							updateFileStatus(CodesBusinessEntityEnum.FILE_STATUS_PROCESS_ENDED_WITH_ERRORS.getCodeEntity(), uploadFileVO);
						}
					} else {
						updateFileStatus(CodesBusinessEntityEnum.FILE_STATUS_PROCESS_ENDED_WITH_ERRORS.getCodeEntity(), uploadFileVO);
					}
					processor.deleteFile(loadFileVO.getPathFile());
				} catch (Exception e) {
					manageFileProcessingException(e, uploadFileVO);
					updateFileStatus(CodesBusinessEntityEnum.FILE_STATUS_PROCESS_ENDED_WITH_ERRORS.getCodeEntity(), uploadFileVO);
				} finally {
					log.debug("finaliza proceso de archivo con nombre: " + uploadFileVO.getName());
				}
			}
		} catch (Throwable t) {
			log.debug("== Error al tratar de ejecutar la operación processFiles/FileDetailProcessBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
		   log.debug("== Termina processFiles/FileDetailProcessBusinessBean ==");
		}	

	}
	
	/**
	 * 
	 * Metodo: Peración que obtiene los archivos pendientes por procesar
	 * Genera una nueva transaccion
	 * @param filter
	 * @param request
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private UploadFileResponse getFilePending(UploadFileFilterDTO filter, RequestCollectionInfo request) throws BusinessException{
		log.debug("== Inicio getFilePending/FileDetailProcessBusinessBean ==");
		try{
			return uploadFileBusiness.findByTypeAndStatusAndUploadDate (filter, request);
		} catch (Throwable t) {
			log.debug("== Error al tratar de ejecutar la operación getFilePending/FileDetailProcessBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
		   log.debug("== Termina getFilePending/FileDetailProcessBusinessBean ==");
		}	
	}
	
	/**
	 * 
	 * Metodo: Operación encargada de obtener lel parametro para limitar la consulta de archivos pendientes 
	 * por procesar.
	 * @param idCountry
	 * @return 
	 * @throws BusinessException 
	 * @author waguilera
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private int getPageSizeFileProcessor(Long idCountry) throws BusinessException{
		try{
			return UtilsBusiness.getNumericSystemParameter(CodesBusinessEntityEnum.CODE_SYSTEM_PARAM_MAX_FILE_TO_PROCESS.getCodeEntity(), idCountry, systemParameterDAO).intValue();
		}catch (Exception e) {
			log.debug("== Error al tratar de ejecutar la operación getPageSizeFileProcessor/FileDetailProcessBusinessBean ==", e);
			throw this.manageException(e);
		} finally {
		   log.debug("== Termina getPageSizeFileProcessor/FileDetailProcessBusinessBean ==");
		}	
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
			String message = e.getMessage() != null ? e.getMessage() : "";
			saveFileDetailProces(rowWithError, message, uploadFileVOWorking.getId() );
		} catch (Exception persistErrorException) {
			log.error("No fue posible guardar la información del error generado en el procesamiento del archivo con id = " + uploadFileVOWorking.getId() != null ? uploadFileVOWorking.getId() : "null" + ".\n El error con el archivo fue: " + e.getMessage() );
			log.error(persistErrorException);
		}
	}
	
	private void updateFileStatus(String newFileStatusCode, UploadFileVO file2Update) throws DAOServiceException, DAOSQLException, BusinessException{
		uploadFileBusiness.updateUploadFile(file2Update, newFileStatusCode);
	}
	
	private void saveFileDetailProces(long row, String message, Long uploafFileWorkingId) throws DAOServiceException, DAOSQLException, BusinessException{
		fileDetailBusiness.saveFileDetailProces(row, message,  uploafFileWorkingId);
	}

}
