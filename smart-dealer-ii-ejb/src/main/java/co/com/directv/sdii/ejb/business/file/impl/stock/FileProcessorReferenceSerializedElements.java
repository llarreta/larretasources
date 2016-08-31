package co.com.directv.sdii.ejb.business.file.impl.stock;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor;
import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.UploadFileParam;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.UploadFileParamDAOLocal;

/**
 * Clase: Encargada de Cargar elementos serializados de una remision desde una archivo Plano.
 * Uc Inv 50
 * 
 * 18/10/2011 - Se cambia la implementación a IBasicFileProcessor.
 * 18/10/2011 - Se elimina la funcionalidad de eliminar.
 * @author hcorredor
 * 
 */
@Stateless(name="FileProcessorReferenceSerializedElementsLocal",mappedName="ejb/FileProcessorReferenceSerializedElementsLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FileProcessorReferenceSerializedElements extends BasicFileProcessor implements FileProcessorReferenceSerializedElementsLocal {

	private final static Logger log = UtilsBusiness.getLog4J(FileProcessorReferenceSerializedElements.class);

	@EJB(name="UploadFileParamDAOLocal", beanInterface=UploadFileParamDAOLocal.class)
	private UploadFileParamDAOLocal uploadFileParamDAO;
	
    @EJB(name="ReferenceElementItemBusinessBeanLocal", beanInterface=ReferenceElementItemBusinessBeanLocal.class)
    private ReferenceElementItemBusinessBeanLocal businessReferenceElementItem;
	
    private UploadFileParam referenceId;
    
    private Long refId;
    
    private Long userId;
    
    private User user;
    
    @EJB(name="ReferenceDAOLocal", beanInterface=ReferenceDAOLocal.class)
    private ReferenceDAOLocal daoRef;
    
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
    
	public FileProcessorReferenceSerializedElements() {
		/**
		 * Columnas: [0] --> Serial 
		 *           [1] --> Serial Vinculado
		 */

		String[] columnTitles = new String[] {
				ApplicationTextEnum.SERIAL.getApplicationTextValue(),
				ApplicationTextEnum.SERIAL_LINKED.getApplicationTextValue(),
			};
		setColumnTitles(columnTitles);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor#processData(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processRecord(FileRecordDTO fileRecordDTO)
			throws BusinessException {
		log.debug("== Inicio processFiles/FileProcessorReferenceSerializedElements ==");
		//List<FileRecordDTO> fileData = this.getFileData();
		int filaProcesada = 0;
		
		try{			
			
			//UploadFileParam referenceId = uploadFileParamDAO.getUploadFileParamByUploadFileAndName(this.getUploadFile().getId(),CodesBusinessEntityEnum.FILE_PARAM_REFERENCE_ID.getCodeEntity());
			String serialCode = fileRecordDTO.getRowData()[0];
			String serialCodeLinked = fileRecordDTO.getRowData()[1];
			if(serialCode == null || serialCode.trim().isEmpty()){
				throw new BusinessException("El serial es requerido");
			}
			businessReferenceElementItem.addElementSerialized(serialCode, serialCodeLinked, refId, user);
			
			
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación processFiles/FileProcessorReferenceSerializedElements ==", t);
			BusinessException businessException = new BusinessException( ""+filaProcesada ,t.getMessage());
			throw businessException;
		} finally {
		   log.debug("== Termina processFiles/FileProcessorReferenceSerializedElements ==");
		}
		
	}

	@Override
	public boolean validateFile() throws BusinessException {
		return true;
	}
	
	@Override
	public List<FileDetailProcess> doGlobalValidationsAndDropNotValidRecords(
			List<FileRecordDTO> fileData) {
		List<FileDetailProcess> errors = new ArrayList<FileDetailProcess>();
		try{
			referenceId = null;
			referenceId = uploadFileParamDAO.getUploadFileParamByUploadFileAndName(this.getUploadFile().getId(),CodesBusinessEntityEnum.FILE_PARAM_REFERENCE_ID.getCodeEntity());
			refId = Double.valueOf( referenceId.getParamValue()).longValue();
			userId = this.getUploadFile().getUser().getId();
			
			user = daoUser.getUserById(userId);
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación doGlobalValidationsAndDropNotValidRecords/FileProcessorReferenceSerializedElements ==", t);
			errors.add( buildFileDetailProcess(0, t.getMessage()) );
		} finally {
		   log.debug("== Termina doGlobalValidationsAndDropNotValidRecords/FileProcessorReferenceSerializedElements ==");
		}
		
		return null;
	}
}

