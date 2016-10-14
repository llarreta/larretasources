package co.com.directv.sdii.ejb.business.file.impl;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.locator.JMSLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.file.FileDetailProcessBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.file.FileProcessorBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.file.FileProcessorBusinessBeanRemote;
import co.com.directv.sdii.ejb.business.file.IFileProcessorFactory;
import co.com.directv.sdii.ejb.business.file.LoadFileBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.file.UploadFileBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.jms.common.util.DistributedQueueMessage;
import co.com.directv.sdii.model.dto.UploadFileFilterDTO;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.UploadFileResponse;
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
	
	private final static Logger log = UtilsBusiness.getLog4J(FileProcessorBusinessBean.class);
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO ;
	
	@EJB(name="UploadFileBusinessBeanLocal", beanInterface=UploadFileBusinessBeanLocal.class)
	private UploadFileBusinessBeanLocal uploadFileBusiness ;
	
	@EJB(name="FileDetailProcessBusinessBeanLocal", beanInterface=FileDetailProcessBusinessBeanLocal.class)
	private FileDetailProcessBusinessBeanLocal fileDetailBusiness;
	
	@EJB(name="LoadFileBusinessBeanLocal", beanInterface=LoadFileBusinessBeanLocal.class)
	private LoadFileBusinessBeanLocal loadFileBusiness ;
	
	@EJB
	private UploadFileBusinessBeanLocal uploadFileBusinessBean;
	
	@EJB
	private IFileProcessorFactory fileProcessorFactory;
	
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
			
			UploadFileResponse response = getFilePending(filter, request);
			 if(log.isDebugEnabled())log.debug("hay " + response.getTotalRowCount()  + " archivos disponibles para procesar");
			
			List<UploadFileVO> listUploadFileVO =  response.getUploadFileVO();
						
			Long maxThreadsConfig = getMaxAmmountOfThreads(idCountry);
			// if(log.isDebugEnabled())log.debug( "La cantidad maxima de archivos en paralelo que se pueden procesar es " + maxThreadsConfig );
			log.debug( "La cantidad maxima de archivos en paralelo que se pueden procesar es " + maxThreadsConfig );
			
			Long currentAmmountOfFilesProcessing = getAmmountOfProcessing(idCountry);
			 //if(log.isDebugEnabled())log.debug( "Actualmente se estan procesando " + currentAmmountOfFilesProcessing + " archivos " );
			log.debug( "Actualmente se estan procesando " + currentAmmountOfFilesProcessing + " archivos " );
			
			Long maxThreadsAvailable = maxThreadsConfig - currentAmmountOfFilesProcessing;
			log.debug( "La cantidad de archivos que van a ser poder ser procesados en esta tanda es "+ maxThreadsAvailable );
		
			for(int i = 0; (i < maxThreadsAvailable) && (i < listUploadFileVO.size()) ; i++ ){
				
				UploadFileVO uploadfile = listUploadFileVO.get(i);
				// if(log.isDebugEnabled())log.debug( "Archivo "+ uploadfile.getName() + " va a ser enviado a la cola" );
				log.debug( "Archivo "+ uploadfile.getName() + " va a ser enviado a la cola" );
				//send the uploadFiles via JMS
				DistributedQueueMessage distributedQueueMessage = JMSLocator.getInstance().getQueueFileProcessor();
			    //if(log.isDebugEnabled())log.debug( "Archivo "+ uploadfile.getName() + " esta siendo enviado a la cola de mensajes para ser procesado" );
				log.debug( "Archivo "+ uploadfile.getName() + " esta siendo enviado a la cola de mensajes para ser procesado" );
				
				//XXX: este fix posiblemente mejore la performance y evite procear en el mismo archivo en paralelo en simultaneo
				// cuando un archivo empieza a procesarse pero se ejecuta el schedulerTasks antes de que el procesador haya cambiado de estados los archivos
				// entonce puede suceder que se registren las cosas
				//uploadFileBusinessBean.updateUploadFile(uploadfile, CodesBusinessEntityEnum.FILE_STATUS_PROCESSING.getCodeEntity());
				
			    distributedQueueMessage.sendMessage(uploadfile);
			    
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
	
	private Long getMaxAmmountOfThreads(Long idCountry) throws DAOServiceException, DAOSQLException, PropertiesException {
		
		SystemParameter sysParam = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_MAX_THREAD_FILES.getCodeEntity(), idCountry);
		Long maxThreads = Long.parseLong(sysParam.getValue());
		//Si es cero seteo un thread
		maxThreads = (maxThreads==0) ? 1L : maxThreads;
		
		return maxThreads;
	}
	
	private Long getAmmountOfProcessing(Long idCountry) throws PropertiesException, BusinessException{
		
		UploadFileFilterDTO  filterProcessing = new UploadFileFilterDTO();
		filterProcessing.setFileStatusCode(CodesBusinessEntityEnum.FILE_STATUS_PROCESSING.getCodeEntity());
		filterProcessing.setCountries(idCountry);
		RequestCollectionInfo requestProcessing = new RequestCollectionInfo();
		requestProcessing.setPageIndex(1);
		requestProcessing.setPageSize(getPageSizeFileProcessor(idCountry));

		UploadFileResponse responseProcessing = getFilePending(filterProcessing, requestProcessing);
		
		return new Long(responseProcessing.getUploadFileVO().size());
	}
}
