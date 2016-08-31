/**
 * 
 */
package co.com.directv.sdii.ejb.business.file.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.assign.assignment.fileprocessor.DealerBuildingsFileProcessorLocal;
import co.com.directv.sdii.assign.assignment.fileprocessor.DealerConfCoverageFileProcessorLocal;
import co.com.directv.sdii.assign.assignment.fileprocessor.DealerCoverageFileProcessorLocal;
import co.com.directv.sdii.assign.assignment.fileprocessor.DealerDetailsFileProcessorLocal;
import co.com.directv.sdii.assign.assignment.fileprocessor.DealerServiceSubcatWthPcFileProcessorLocal;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.ejb.business.file.ClientBasicFileProcessor;
import co.com.directv.sdii.ejb.business.file.IFileProcessor;
import co.com.directv.sdii.ejb.business.file.IFileProcessorFactory;
import co.com.directv.sdii.ejb.business.file.impl.stock.FileProcessorChangeTypeSerializedElementsLocal;
import co.com.directv.sdii.ejb.business.file.impl.stock.FileProcessorCompareWarehousePhysicalInventorySmartdealerLocal;
import co.com.directv.sdii.ejb.business.file.impl.stock.FileProcessorConfirmItemsLoadMassiveReferenceLocal;
import co.com.directv.sdii.ejb.business.file.impl.stock.FileProcessorConfirmSerialItemsFromImportLogLocal;
import co.com.directv.sdii.ejb.business.file.impl.stock.FileProcessorConfirmSerialItemsFromReferenceLocal;
import co.com.directv.sdii.ejb.business.file.impl.stock.FileProcessorImportLogSerializedElementsLocal;
import co.com.directv.sdii.ejb.business.file.impl.stock.FileProcessorLoadMassiveSerializedElementsAdjustmentOutputLocal;
import co.com.directv.sdii.ejb.business.file.impl.stock.FileProcessorLoadMassiveSerializedElementsAdjustmentTransferLocal;
import co.com.directv.sdii.ejb.business.file.impl.stock.FileProcessorMassiveLinkSerializedElementsLocal;
import co.com.directv.sdii.ejb.business.file.impl.stock.FileProcessorMassiveUnLinkSerializedElementsLocal;
import co.com.directv.sdii.ejb.business.file.impl.stock.FileProcessorMassiveUpReferencesLocal;
import co.com.directv.sdii.ejb.business.file.impl.stock.FileProcessorReferenceNotSerializedElementsLocal;
import co.com.directv.sdii.ejb.business.file.impl.stock.FileProcessorReferenceSerializedElementsLocal;
import co.com.directv.sdii.ejb.business.file.impl.stock.FileProcessorRegisterQASerializedElementsLocal;
import co.com.directv.sdii.exceptions.BusinessException;


/**
 * @author jvelezmu
 *
 */
@Stateless(name="FileProcessorFactory", mappedName="ejb/FileProcessorFactory")
public class FileProcessorFactory implements IFileProcessorFactory {

	private ClientBasicFileProcessor clientBasicFileProcessor =  new ClientBasicFileProcessor();
	
	@EJB(name="FileProcessorRegisterQASerializedElementsLocal",beanInterface=FileProcessorRegisterQASerializedElementsLocal.class)
	private FileProcessorRegisterQASerializedElementsLocal fileRegisterQaSerializedProc;
	
	@EJB(name="FileProcessorConfirmSerialItemsFromImportLog",beanInterface=FileProcessorConfirmSerialItemsFromImportLogLocal.class)
	private FileProcessorConfirmSerialItemsFromImportLogLocal fileProcessorConfirmSerialItemsFromImportLog;
	
	@EJB(name="FileProcessorImportLogSerializedElementsLocal",beanInterface=FileProcessorImportLogSerializedElementsLocal.class)
	private FileProcessorImportLogSerializedElementsLocal fileProcessorImportLogSerializedElementsLocal;
	
	@EJB(name="FileProcessorReferenceSerializedElementsLocal",beanInterface=FileProcessorReferenceSerializedElementsLocal.class)
	private FileProcessorReferenceSerializedElementsLocal fileProcessorReferenceSerializedElementsLocal;
	
	@EJB(name="FileProcessorMassiveUpReferencesLocal",beanInterface=FileProcessorMassiveUpReferencesLocal.class)
	private FileProcessorMassiveUpReferencesLocal fileProcessorMassiveUpReferencesLocal;
	
	@EJB(name="FileProcessorReferenceNotSerializedElementsLocal",beanInterface=FileProcessorReferenceNotSerializedElementsLocal.class)
	private FileProcessorReferenceNotSerializedElementsLocal fileProcessorReferenceNotSerializedElementsLocal;
	
	@EJB(name="FileProcessorConfirmSerialItemsFromReferenceLocal",beanInterface=FileProcessorConfirmSerialItemsFromReferenceLocal.class)
	private FileProcessorConfirmSerialItemsFromReferenceLocal fileProcessorConfirmSerialItemsFromReferenceLocal;
	
	@EJB(name="FileProcessorLinkSerializedElementsLocal",beanInterface=FileProcessorMassiveLinkSerializedElementsLocal.class)
	private FileProcessorMassiveLinkSerializedElementsLocal fileProcessorMassiveLinkSerializedElementsLocal;
	
	@EJB(name="FileProcessorMassiveUnLinkSerializedElementsLocal",beanInterface=FileProcessorMassiveUnLinkSerializedElementsLocal.class)
	private FileProcessorMassiveUnLinkSerializedElementsLocal fileProcessorMassiveUnLinkSerializedElementsLocal;
	
	@EJB(name="FileProcessorLoadMassiveSerializedElementsAdjustmentOutputLocal",beanInterface=FileProcessorLoadMassiveSerializedElementsAdjustmentOutputLocal.class)
	private FileProcessorLoadMassiveSerializedElementsAdjustmentOutputLocal fileProcessorLoadMassiveSerializedElementsAdjustmentOutputLocal;
	
	@EJB(name="FileProcessorLoadMassiveSerializedElementsAdjustmentTransferLocal",beanInterface=FileProcessorLoadMassiveSerializedElementsAdjustmentTransferLocal.class)
	private FileProcessorLoadMassiveSerializedElementsAdjustmentTransferLocal fileProcessorLoadMassiveSerializedElementsAdjustmentTransferLocal;
	
	@EJB(name="FileProcessorChangeTypeSerializedElementsLocal",beanInterface=FileProcessorChangeTypeSerializedElementsLocal.class)
	private FileProcessorChangeTypeSerializedElementsLocal fileProcessorChangeTypeSerializedElementsLocal;

	//inicio procesadores de archivos para asignador
	@EJB(name="DealerDetailsFileProcessorLocal",beanInterface=DealerDetailsFileProcessorLocal.class)
	private DealerDetailsFileProcessorLocal dealerDetailsProc;
	
	@EJB(name="DealerCoverageFileProcessorLocal",beanInterface=DealerCoverageFileProcessorLocal.class)
	private DealerCoverageFileProcessorLocal dealerCoverageProc;
	
	@EJB(name="DealerConfCoverageFileProcessorLocal",beanInterface=DealerConfCoverageFileProcessorLocal.class)
	private DealerConfCoverageFileProcessorLocal dealerConfCoverageProc;
	
	@EJB(name="DealerBuildingsFileProcessorLocal",beanInterface=DealerBuildingsFileProcessorLocal.class)
	private DealerBuildingsFileProcessorLocal dealerBuildingssProc;
	
	@EJB(name="DealerServiceSubcatWthPcFileProcessorLocal",beanInterface=DealerServiceSubcatWthPcFileProcessorLocal.class)
	private DealerServiceSubcatWthPcFileProcessorLocal dealerServiceSubcatWthPcProc;
	
	@EJB(name="FileProcessorCompareWarehousePhysicalInventorySmartdealerLocal",beanInterface=FileProcessorCompareWarehousePhysicalInventorySmartdealerLocal.class)
	private FileProcessorCompareWarehousePhysicalInventorySmartdealerLocal fileProcessorCompareWarehousePhysicalInventorySmartdealer;
	
	@EJB(name="FileProcessorConfirmItemsLoadMassiveReferenceLocal",beanInterface=FileProcessorConfirmItemsLoadMassiveReferenceLocal.class)
	private FileProcessorConfirmItemsLoadMassiveReferenceLocal FileProcessorConfirmItemsLoadMassiveReference;
	
	
	//fin procesadores de archivos para asignador
	
	public FileProcessorFactory(){
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.IFileProcessorFactory#getFileProcessor(java.lang.String)
	 */
	@Override
	public IFileProcessor getFileProcessor(String fileType) throws BusinessException {
        
		IFileProcessor fileProcessor = null;
		try {
        	//procesadores para registros de importación
			if (fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_ELEMENT_MOV_BET_WHS.getCodeEntity())){
				clientBasicFileProcessor.setRemoteFileProcessor(fileRegisterQaSerializedProc);
        		fileProcessor = clientBasicFileProcessor;
        	} else if (fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_ELEMENT_CONFIRM.getCodeEntity())){
        		clientBasicFileProcessor.setRemoteFileProcessor(fileProcessorConfirmSerialItemsFromImportLog);
        		fileProcessor = clientBasicFileProcessor;
        	}else if (fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_IMPORTLOG_SERIALIZEDELEMENTS.getCodeEntity())){
        		clientBasicFileProcessor.setRemoteFileProcessor(fileProcessorImportLogSerializedElementsLocal);
        		fileProcessor = clientBasicFileProcessor;
        	}else if (fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_REFERENCE_UPLOAD_DELETE_ELEMENTS.getCodeEntity())){
        		clientBasicFileProcessor.setRemoteFileProcessor(fileProcessorReferenceSerializedElementsLocal);
        		fileProcessor = clientBasicFileProcessor;
        	}else if (fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_REFERENCE_NOT_SERIALIZED.getCodeEntity())){
        		fileProcessor = fileProcessorReferenceNotSerializedElementsLocal;
        	}else if (fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_REFERENCE_CONFIRM_SERIALIZED.getCodeEntity())){
        		clientBasicFileProcessor.setRemoteFileProcessor(fileProcessorConfirmSerialItemsFromReferenceLocal);
        		fileProcessor = clientBasicFileProcessor;
        	//Procesadores para Vinculación y desvinculación de elementos serializados
        	}else if (fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_LINK_SERIALIZED_ELEMENT.getCodeEntity())){
        		clientBasicFileProcessor.setRemoteFileProcessor(fileProcessorMassiveLinkSerializedElementsLocal);
        		fileProcessor = clientBasicFileProcessor;
        	}else if (fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_UNLINK_SERIALIZED_ELEMENT.getCodeEntity())){
        		clientBasicFileProcessor.setRemoteFileProcessor(fileProcessorMassiveUnLinkSerializedElementsLocal);
        		fileProcessor = clientBasicFileProcessor;
        	}else if (fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_CHANGE_ELEMENT_TYPE_SERIALIZED_ELEMENTS.getCodeEntity())){
        		clientBasicFileProcessor.setRemoteFileProcessor(fileProcessorChangeTypeSerializedElementsLocal);
        		fileProcessor = clientBasicFileProcessor;
        	//Procesadores de archivos de remisiones
        	}else if (fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_REFERENCE_UPLOAD_MASSIVE.getCodeEntity())){
        		clientBasicFileProcessor.setRemoteFileProcessor(fileProcessorMassiveUpReferencesLocal);
        		fileProcessor = clientBasicFileProcessor;
        	}else if (fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_SERIALIZED_ELEMENTS_ADJUSTMENT_OUTPUT.getCodeEntity())){
        		clientBasicFileProcessor.setRemoteFileProcessor(fileProcessorLoadMassiveSerializedElementsAdjustmentOutputLocal);
        		fileProcessor = clientBasicFileProcessor;
        	}else if (fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_SERIALIZED_ELEMENTS_ADJUSTMENT_TRANSFER.getCodeEntity())){
        		clientBasicFileProcessor.setRemoteFileProcessor(fileProcessorLoadMassiveSerializedElementsAdjustmentTransferLocal);
        		fileProcessor = clientBasicFileProcessor;
        	//procesadores para asignador
        	}else if (fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_DEALER_DETAILS.getCodeEntity())){
        		clientBasicFileProcessor.setRemoteFileProcessor(dealerDetailsProc);
        		fileProcessor = clientBasicFileProcessor;
        	} else if (fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_DEALER_COVERAGE.getCodeEntity())) {
        		clientBasicFileProcessor.setRemoteFileProcessor(dealerCoverageProc);
        		fileProcessor = clientBasicFileProcessor;
        	} else if (fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_DEALER_CONF_COVERAGE.getCodeEntity())) {
        		clientBasicFileProcessor.setRemoteFileProcessor(dealerConfCoverageProc);
        		fileProcessor = clientBasicFileProcessor;
        	} else if (fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_DEALER_BUILDINGS.getCodeEntity())) {
        		clientBasicFileProcessor.setRemoteFileProcessor(dealerBuildingssProc);
        		fileProcessor = clientBasicFileProcessor;
        	} else if (fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_DEALER_SERV_SUBCAT_WTH_PC.getCodeEntity())) {
        		clientBasicFileProcessor.setRemoteFileProcessor(dealerServiceSubcatWthPcProc);
        		fileProcessor = clientBasicFileProcessor;
        	} else if(fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_COMPARE_WAREHOUSE_PHYSICAL_INVENTORY_WITH_SMARTDEALER.getCodeEntity())){
        		clientBasicFileProcessor.setRemoteFileProcessor(fileProcessorCompareWarehousePhysicalInventorySmartdealer);
        		fileProcessor = clientBasicFileProcessor;
        	} else if (fileType.equals(CodesBusinessEntityEnum.FILE_TYPE_CONFIRM_ELEMENTS_MASSIVE_REFERENCES.getCodeEntity())){
        		 clientBasicFileProcessor.setRemoteFileProcessor(FileProcessorConfirmItemsLoadMassiveReference);
                 fileProcessor = clientBasicFileProcessor;
        	}else {      		
        		throw new BusinessException("No existe la operacion para el comando que se trata de ejecutar [" + fileType + "]");
        	}
        	
        } catch(Throwable e){
			throw new BusinessException("No existe el comando que se trata de ejecutar [" + fileType + "]");
		}
		
		return fileProcessor;
	}
}
