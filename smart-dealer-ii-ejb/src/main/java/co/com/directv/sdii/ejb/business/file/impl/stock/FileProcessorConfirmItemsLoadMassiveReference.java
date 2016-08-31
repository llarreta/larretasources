/**
 * Req 0067 Confirmacion masiva de remisiones en HSP
 */
package co.com.directv.sdii.ejb.business.file.impl.stock;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor;
import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Element;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse;
import co.com.directv.sdii.model.vo.ElementTypeVO;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;

/**
 * @author ialessan
 *
 */
@Stateless(name="FileProcessorConfirmItemsLoadMassiveReference",mappedName="ejb/FileProcessorConfirmItemsLoadMassiveReference")
@TransactionAttribute(TransactionAttributeType.REQUIRED) 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FileProcessorConfirmItemsLoadMassiveReference extends BasicFileProcessor implements FileProcessorConfirmItemsLoadMassiveReferenceLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(FileProcessorConfirmItemsLoadMassiveReference.class);
	
	@EJB(name="ReferenceElementItemBusinessBeanLocal", beanInterface=ReferenceElementItemBusinessBeanLocal.class)
	private ReferenceElementItemBusinessBeanLocal businessReferenceElementItem;

	@EJB(name="ReferenceBusinessBeanLocal", beanInterface=ReferenceBusinessBeanLocal.class)
	private ReferenceBusinessBeanLocal businessReference;
	
	@EJB(name="ElementTypeBusinessBeanLocal", beanInterface=ElementTypeBusinessBeanLocal.class)
	private ElementTypeBusinessBeanLocal businessElementType;
	
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
	
	private User user;

	private static int POS_NUM_RN = 0;
	private static int POS_ELEMENT_TYPE_CODE = 1;
	private static int POS_SERIAL = 2;
	private static int POS_LINKED_SERIAL = 3;
	private static int POS_QUANTITY = 4;
	
	public FileProcessorConfirmItemsLoadMassiveReference(){
		String[] columnTitles = new String[] {
				ApplicationTextEnum.CODE_REFERRAL.getApplicationTextValue(),
				ApplicationTextEnum.ELEMENT_TYPE_CODE.getApplicationTextValue(),
				ApplicationTextEnum.SERIAL.getApplicationTextValue(),
				ApplicationTextEnum.SERIAL_LINKED.getApplicationTextValue(),
				ApplicationTextEnum.QUANTITY.getApplicationTextValue()
		};
		setColumnTitles(columnTitles);
	}
	
	/** 
	 *@see co.com.directv.sdii.ejb.business.file.IBasicFileProcessor#processRecord(co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO)
	 * Procesa un registro del archivo. Cada fila puede contener elementos serializados como no serializados.<br/>
	 * Los Serializados deben contener: RN_NUMBER, ELEMENT_TYPE_CODE, SERIAL, SERIAL_LINKED.<br/>
	 * Los No Serializados deben contener: RN_NUMBER, ELEMENT_TYPE_CODE, QUANTITY.<br/>
	 */
	@Override
	public void processRecord(FileRecordDTO fileRecordDTO)
			throws BusinessException {
		log.debug("== Inicio processRecord/FileProcessorConfirmItemsLoadMassiveReference ==");
		
		int filaProcesada = 0;
		
		try{			
			String rnNumber = fileRecordDTO.getRowData()[POS_NUM_RN];
			String elementTypeCode = fileRecordDTO.getRowData()[POS_ELEMENT_TYPE_CODE];
			String serialCode = fileRecordDTO.getRowData()[POS_SERIAL];
			String serialCodeLinked = fileRecordDTO.getRowData()[POS_LINKED_SERIAL];
			String quantityStr = fileRecordDTO.getRowData()[POS_QUANTITY];
			
			if(rnNumber == null || rnNumber.trim().isEmpty()){
				throw new BusinessException("El Número RN es requerido");
			}
			if(elementTypeCode == null || elementTypeCode.trim().isEmpty()){
				throw new BusinessException("El tipo de elemento es requerido");
			}

			ElementTypeVO elementTypeVO = businessElementType.getElementTypeByCode(elementTypeCode);
			if(elementTypeVO==null){
				throw new BusinessException("El tipo de elemento informado es inválido");
			}
			
			//lista de remisiones segun RN, menos las que se encuentren eliminadas
			List<ReferenceVO> referenceVOs = businessReference.getReferenceByRNNumber(rnNumber,CodesBusinessEntityEnum.REFERENCE_STATUS_DELETED.getCodeEntity());
			if (referenceVOs.size()>1)
				throw new BusinessException("El número de RN ya se encuentra asociado a otra remisión");
			ReferenceVO referenceVO = referenceVOs.get(0);
			
			if (!referenceVO.getReferenceStatus().getRefStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity()) && 
				!referenceVO.getReferenceStatus().getRefStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity())	) 
				throw new BusinessException("El estado de la remisión debe ser EN CREACION o CREADA");
				
			if(elementTypeVO.getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity()) && serialCode != null && !serialCode.trim().isEmpty() ){
				//serializados.
				businessReferenceElementItem.addElementSerialized(serialCode, serialCodeLinked, referenceVO.getId(), user);
			}else if(elementTypeVO.getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity()) && quantityStr != null && !quantityStr.trim().isEmpty()){
				//No Serializados.
				Double quantity = 0D;
				if(!NumberUtils.isNumber(quantityStr)){
					throw new BusinessException("La cantidad debe ser numerica");
				}else{
					quantity = Double.parseDouble(quantityStr);
				}
				
				boolean isPrepaid = referenceVO.getIsPrepaidRef().equals(CodesBusinessEntityEnum.REFERENCE_PREPAID.getCodeEntity());
				ReferenceElementItemsResponse referenceElementItemsResponse = businessReferenceElementItem.getElementNotSerializedFromWarehouse(null,referenceVO.getId(),isPrepaid, elementTypeVO.getId(), null);
				List<ReferenceElementItemVO> refElementItemVOList = referenceElementItemsResponse.getReferenceElementItemsVO();
				if(!refElementItemVOList.isEmpty()){
					refElementItemVOList.get(0).setRefQuantity(quantity);
					Element elem = new Element();
					elem.setElementType(elementTypeVO);
					refElementItemVOList.get(0).setElement(elem);
					businessReferenceElementItem.addElementNotSerialized(referenceElementItemsResponse.getReferenceElementItemsVO(), referenceVO.getId(), user.getId());
				}
				
			} else{
				throw new BusinessException("No se puede procesar como Serializado ni como No Serializado");
			}
			
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación processRecord/FileProcessorConfirmItemsLoadMassiveReference ==", t);
			BusinessException businessException = new BusinessException( ""+filaProcesada ,t.getMessage());
			throw businessException;
		} finally {
		   log.debug("== Termina processRecord/FileProcessorConfirmItemsLoadMassiveReference ==");
		}
		 
	}

	@Override
	public List<FileDetailProcess> doGlobalValidationsAndDropNotValidRecords(
			List<FileRecordDTO> fileData) {
		List<FileDetailProcess> errors = new ArrayList<FileDetailProcess>();
		try{
			user = daoUser.getUserById(this.getUploadFile().getUser().getId());
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación doGlobalValidationsAndDropNotValidRecords/FileProcessorConfirmItemsLoadMassiveReference ==", t);
			errors.add( buildFileDetailProcess(0, t.getMessage()) );
		} finally {
		   log.debug("== Termina doGlobalValidationsAndDropNotValidRecords/FileProcessorConfirmItemsLoadMassiveReference ==");
		}
		
		return null;
	}

	@Override
	public boolean validateFile() throws BusinessException {
		return true;
	}
	
}
