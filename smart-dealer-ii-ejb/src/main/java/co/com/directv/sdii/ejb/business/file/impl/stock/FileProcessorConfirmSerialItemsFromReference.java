package co.com.directv.sdii.ejb.business.file.impl.stock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor;
import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.UploadFileParam;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.UploadFileParamDAOLocal;


/**
 * 
 * Clase encargada de validar y confirmar los elementos serializados de una remision, cargados desde un archivo plano. 
 * 
 * Fecha de Creación: 16/08/2011
 * @author hcorredor <a href="mailto:hcorredor@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="FileProcessorConfirmSerialItemsFromReferenceLocal",mappedName="ejb/FileProcessorConfirmSerialItemsFromReferenceLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FileProcessorConfirmSerialItemsFromReference extends BasicFileProcessor implements FileProcessorConfirmSerialItemsFromReferenceLocal {

	private final static Logger log = UtilsBusiness.getLog4J(FileProcessorConfirmSerialItemsFromReference.class);
	
	@EJB(name="UploadFileParamDAOLocal", beanInterface=UploadFileParamDAOLocal.class)
	private UploadFileParamDAOLocal uploadFileParamDAO;
	
	@EJB(name="ReferenceDAOLocal", beanInterface=ReferenceDAOLocal.class)
    private ReferenceDAOLocal daoReference;
	
	@EJB(name="WarehouseElementBusinessBeanLocal", beanInterface=WarehouseElementBusinessBeanLocal.class)
    private WarehouseElementBusinessBeanLocal businessWarehouseElement;
	
	@EJB(name="ReferenceElementItemBusinessBeanLocal", beanInterface=ReferenceElementItemBusinessBeanLocal.class)
    private ReferenceElementItemBusinessBeanLocal businessReferenceElementItem;
	
	@EJB(name="SerializedDAOLocal",beanInterface=SerializedDAOLocal.class)
	private SerializedDAOLocal daoSerialized;
	
	public FileProcessorConfirmSerialItemsFromReference() {
		String[] columnTitles = new String[] {				
				ApplicationTextEnum.SERIAL.getApplicationTextValue(),
				ApplicationTextEnum.SERIAL_LINKED_ELEMENT.getApplicationTextValue()
			};
		setColumnTitles(columnTitles);
	}
	
	private static int POS_SERIAL = 0;
	private static int POS_LINKED_SERIAL = 1;
	
	private static String PARAM_REFERENCE_ID = "refId";
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.IBasicFileProcessor#processRecord(co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processRecord(FileRecordDTO fileRecordDTO)
			throws BusinessException {
		try {

			String serial = fileRecordDTO.getRowData()[POS_SERIAL];
			String linkedSerial = fileRecordDTO.getRowData()[POS_LINKED_SERIAL];
			
			if(StringUtils.isBlank(serial)) {
				throw new BusinessException("El campo " + getColumnTitles()[POS_SERIAL] + " es obligatorio");
			}
			
			Serialized serialized = daoSerialized.getSerializedBySerial(serial,this.getUploadFile().getCountry().getId());
			
			if (serialized == null) {
				throw new BusinessException("El elemento con serial [" + serial + "] no existe en el sistema.");
			}
			
			//validar que el valor de la columna serial no esté vinculado a otro elemento
			List<Serialized> linkedElements = daoSerialized.getLinkedSerializedBySerializedId(serialized.getElementId());
			if(linkedElements != null && !linkedElements.isEmpty()) {
				throw new BusinessException("El serial " + serial + " está vinculado a otro elemento. Si realmente se desea confirmar este elemento, se debe poner este serial en la columna de serial vinculado");
			}
			
			//validar que el serial vinculado del elemento coincida con el registrado en el archivo
			Serialized linkedSerialized = serialized.getSerialized();
			if(linkedSerialized != null) {
				if(StringUtils.isBlank(linkedSerial)) {
					throw new BusinessException("Debe agregar el serial vinculado, ya que el elemento con serial " + serial + " tiene un elemento vinculado");
				}
				if(!linkedSerialized.getSerialCode().equals(linkedSerial)) {
					throw new BusinessException("El serial vinculado registrado no coincide con el serial vinculado del elemento");
				}
			}
			
			//validar que el elemento se encuentre en el país correcto
			WarehouseElementVO warehouseElementVO = businessWarehouseElement.getWarehouseElementBySerialActive(serial,this.getUploadFile().getCountry().getId());
			if(warehouseElementVO != null && warehouseElementVO.getWarehouseId() != null && warehouseElementVO.getWarehouseId().getCountry() != null) {
				Long elementCountryId = warehouseElementVO.getWarehouseId().getCountry().getId();
				if(!getUploadFile().getCountry().getId().equals(elementCountryId)) {
					throw new BusinessException("El elemento con serial " + serial + " se encuentra en una bodega que no está en el país desde el que se cargó el archivo");
				}
			}
			
			List<ReferenceElementItemVO> referenceItems = new ArrayList<ReferenceElementItemVO>();
			
			Long referenceId = (Long) fileRecordDTO.getParam(PARAM_REFERENCE_ID);
			ReferenceElementItemVO item = businessReferenceElementItem.getReferenceElementItemByReferenceIdAndElementId(referenceId, serialized.getElementId());
			
			if(item == null) {
				throw new BusinessException("el elemento con serial " + serial + " no se encuentra en la remisión con id " + referenceId);
			}
			
			referenceItems.add(item);
			
			businessReferenceElementItem.partialReceptionOfReferenceElementItem(referenceItems, getUploadFile().getUser().getId());
			
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor#doGlobalValidationsAndDropNotValidRecords(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<FileDetailProcess> doGlobalValidationsAndDropNotValidRecords(List<FileRecordDTO> fileData) {
		List<FileDetailProcess> errors = new ArrayList<FileDetailProcess>();
		
		Reference reference = null;
		try {
			UploadFileParam paramRefId = uploadFileParamDAO.getUploadFileParamByUploadFileAndName(this.getUploadFile().getId(), CodesBusinessEntityEnum.FILE_PARAM_REFERENCE_ID.getCodeEntity());
			if(paramRefId != null && NumberUtils.isNumber(paramRefId.getParamValue())) {
				reference = daoReference.getReferenceByID( Long.parseLong( paramRefId.getParamValue()) );
			}else {
				String refId = (paramRefId != null && !StringUtils.isEmpty(paramRefId.getParamValue()) ) ? paramRefId.getParamValue() : "null";
				throw new BusinessException("el parámetro " + CodesBusinessEntityEnum.FILE_PARAM_REFERENCE_ID.getCodeEntity()
						+ " no tiene un valor válido. Valor actual = " + refId);
			}
			
			if(reference == null) {
				throw new BusinessException("no se encontró la remisión con identificador " + paramRefId.getParamValue());
			}
			
			//Validar que la remision este en estado enviado o confirmado parcial
			if ( !(reference.getReferenceStatus().getRefStatusCode().equals(CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity())
					|| reference.getReferenceStatus().getRefStatusCode().equals(CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity())) ){
				throw new BusinessException("el estado de la remisión con identificador " + paramRefId.getParamValue() + " no está en enviado o confirmado parcial");	
			}
			
		} catch(Exception e) {
			log.error(e);
			errors.add( buildFileDetailProcess(0, e.getMessage()) );
			fileData.clear();//no se debe procesar ningún registro
		}
		
		for (Iterator<FileRecordDTO> iterator = fileData.iterator(); iterator.hasNext();) {
			FileRecordDTO fileRecordDTO = iterator.next();
			fileRecordDTO.addParam(PARAM_REFERENCE_ID, reference.getId());
		}
		
		return errors;
	}
	
	@Override
	public boolean validateFile() throws BusinessException {
		return true;
	}

}
