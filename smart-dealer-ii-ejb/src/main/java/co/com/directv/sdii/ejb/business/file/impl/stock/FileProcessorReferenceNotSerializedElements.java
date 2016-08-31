package co.com.directv.sdii.ejb.business.file.impl.stock;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.file.FileProcessor;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.UploadFileParam;
import co.com.directv.sdii.model.vo.LoadFileVO;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.UploadFileParamDAOLocal;


@Stateless(name="FileProcessorConfirmNotSerialItemsFromImportLog")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FileProcessorReferenceNotSerializedElements extends FileProcessor implements FileProcessorReferenceNotSerializedElementsLocal {

	private final static Logger log = UtilsBusiness.getLog4J(FileProcessorReferenceNotSerializedElements.class);
	
	@EJB(name="UploadFileParamDAOLocal", beanInterface=UploadFileParamDAOLocal.class)
	private UploadFileParamDAOLocal uploadFileParamDAO;
	
	@EJB(name="ReferenceDAOLocal", beanInterface=ReferenceDAOLocal.class)
    private ReferenceDAOLocal daoReference;
	
	@EJB(name="ElementTypeDAOLocal", beanInterface=ElementTypeDAOLocal.class)
	private ElementTypeDAOLocal elementTypeDao;

	@EJB (name="ReferenceBusinessBeanLocal", beanInterface=ReferenceBusinessBeanLocal.class)
    private ReferenceBusinessBeanLocal businessReference;
	
	@Override
	public void processFile(LoadFileVO loadFileVO) throws BusinessException {
		int filaProcesada = 0;
		try{
			
			//1. Consulta el parámetro del registro de importación
			UploadFileParam referenceId = uploadFileParamDAO.getUploadFileParamByUploadFileAndName(this.getUploadFile().getId(),CodesBusinessEntityEnum.FILE_PARAM_REFERENCE_ID.getCodeEntity());
			//Toma la accion de adicionar o eliminar elelementos no serializados a una remision.
			UploadFileParam action = uploadFileParamDAO.getUploadFileParamByUploadFileAndName(this.getUploadFile().getId(),CodesBusinessEntityEnum.FILE_PARAM_ACTION.getCodeEntity());
			
			Reference refPojo = daoReference.getReferenceByID( Double.valueOf(referenceId.getParamValue()).longValue() );
			
		//Tomar la remision
		
		/**
		 * Columnas: [0] --> Codigo tipo Elemento
		 *           [1] --> Cantidad Requerida
		 *           [2] --> Prepago
		 */
		
			List<FileRecordDTO> fileData = this.getFileData();
			for (FileRecordDTO fd : fileData){
				//La primera fila es de Titulos. 
				if (fd.getRow() == 0){
				   continue;
				}
				filaProcesada++;
				
				String codTipoElemento = fd.getRowData()[0];
				String cantidadRequerida = fd.getRowData()[1];
				Double quantity = Double.parseDouble(cantidadRequerida);
				ElementType elementType = elementTypeDao.getElementTypeByCode(codTipoElemento); 
				
				if (action.getParamValue().equals(ApplicationTextEnum.LOAD.getApplicationTextValue()) ){
					businessReference.addNotSerializedElementsToReference(quantity, refPojo, elementType, CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity(), null, null);
				}
//				else if (action.getParamValue().equals("eliminar")){
//					businessReference.removeNotSerializedElementsToReference(quantity, refPojo, elementType );
//				}
			}
		
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación processFiles/FileProcessorConfirmSerialItemsFromImportLog ==", t);
			BusinessException businessException = new BusinessException( ""+filaProcesada ,t.getMessage());
			throw businessException;
		} finally {
		   log.debug("== Termina processFiles/FileProcessorConfirmSerialItemsFromImportLog ==");
		}	
	}
	
	@Override
	public boolean validateFile() throws BusinessException {
		boolean isValidData = true;
		
		
		
		List<FileRecordDTO> fileData = this.getFileData();
		try{
			
			UploadFileParam referenceId = uploadFileParamDAO.getUploadFileParamByUploadFileAndName(this.getUploadFile().getId(),CodesBusinessEntityEnum.FILE_PARAM_REFERENCE_ID.getCodeEntity());
			
			UploadFileParam action = uploadFileParamDAO.getUploadFileParamByUploadFileAndName(this.getUploadFile().getId(),CodesBusinessEntityEnum.FILE_PARAM_ACTION.getCodeEntity());
			
			if (referenceId==null){
				isValidData = false;
				saveFileDetailProcess(0, "Debe especificar el parametro para el numero de Remision" );
			}
			
			if (action==null){
				isValidData = false;
				saveFileDetailProcess(0, "Debe especificar el parametro para la accion a realizar eliminar o adicionar elementos a la remision" );
			}
		
		/**
		 * Columnas: [0] --> Codigo tipo Elemento
		 *           [1] --> Cantidad Requerida
		 *           [2] --> Prepago
		 */
		
		for (FileRecordDTO fd : fileData){
			//La primera fila es de Titulos. 
			if (fd.getRow() == 0){
			   continue;
			}
			
			//Validar que el tipo de elemento sea no serializado.
			String codTipoElemento = fd.getRowData()[0];
			ElementType elementType = elementTypeDao.getElementTypeByCode(codTipoElemento);
			if (elementType.getIsSerialized().equals(ApplicationTextEnum.ELEMENT_IS_SERIALIZED.getApplicationTextValue()) ){
				isValidData = false;
				saveFileDetailProcess((long) fd.getRow(), ApplicationTextEnum.ELEMENT_TYPE_NOT_SERIALIZED.getApplicationTextValue()+" ["+codTipoElemento+" - "+elementType.getTypeElementDescription()+"]" );
			}
			
			
		}
		
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación validateFile/FileProcessorAddNoSerializedElementsToImportLog ==", t);
			throw this.manageException(t);
		} finally {
		   log.debug("== Termina validateFile/FileProcessorAddNoSerializedElementsToImportLog ==");
		}	
		
		return isValidData;
	}

}
