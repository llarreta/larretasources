package co.com.directv.sdii.ejb.business.file.impl.stock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.UploadFileParam;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.UploadFileParamDAOLocal;

/**
 * Clase que implementa la funcionalidad para procesar archivos planos y pasarlos de
 * la bodega de Calidad a la de disponibles o devoluciones, dependiendo del estado del 
 * elemento 1 - Bueno | 0 - Defectuoso
 * @author hcorredor
 *
 */
@Stateless(name="FileProcessorRegisterQASerializedElementsLocal",mappedName="ejb/FileProcessorRegisterQASerializedElementsLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FileProcessorRegisterQASerializedElements extends BasicFileProcessor implements FileProcessorRegisterQASerializedElementsLocal {

	private final static Logger log = UtilsBusiness.getLog4J(FileProcessorRegisterQASerializedElements.class);
	
	@EJB(name="WarehouseElementBusinessBeanLocal", beanInterface=WarehouseElementBusinessBeanLocal.class)
    private WarehouseElementBusinessBeanLocal businessWarehouseElement;
	
	@EJB(name="UploadFileParamDAOLocal", beanInterface=UploadFileParamDAOLocal.class)
	private UploadFileParamDAOLocal uploadFileParamDAO;
	
	@EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
	private DealersDAOLocal dealersDAO;
	
	@EJB(name="SerializedDAOLocal",beanInterface=SerializedDAOLocal.class)
	private SerializedDAOLocal daoSerialized;
	
	public FileProcessorRegisterQASerializedElements() {
		String[] columnTitles = new String[] {
				ApplicationTextEnum.TYPE_ELEMENT_CODE.getApplicationTextValue(),
				ApplicationTextEnum.SERIAL.getApplicationTextValue(),
				ApplicationTextEnum.SERIAL_LINKED_ELEMENT.getApplicationTextValue(),
				ApplicationTextEnum.STATUS.getApplicationTextValue() //1 - Bueno to WH Disponibles   0 - Defectuoso to WH Rechazados 
			};
		setColumnTitles(columnTitles);
	}
	
	private static int POS_SERIAL = 0;
	private static int POS_LINKED_SERIAL = 1;
	private static int POS_STATE = 2;
	
	private static String PARAM_DEALER = "dealer";
	private static String PARAM_ELEMENT = "ele";
	
	
	private Map<String, Object[]> warehouseElementVOItems;
	
	
	private List<ElementVO> elementsVOToAvalilable ;
	private List<ElementVO> elementsVOToReturn ;
	private int max;
	private int actualIndex;
	
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor#processData(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processRecord(FileRecordDTO fileRecordDTO) throws BusinessException {

		++actualIndex;
		
		List<ElementVO> elementsVOToAvalilable ;
		List<ElementVO> elementsVOToReturn ;
		elementsVOToAvalilable = new ArrayList<ElementVO>();
		elementsVOToReturn = new ArrayList<ElementVO>();
		
		Object[] dataElementCountry = null;
		BigDecimal countryId = null;
		BigDecimal warehouseId = null;
		
		try {
			String[] record = fileRecordDTO.getRowData();

			String serial = record[POS_SERIAL];
			int state = Integer.valueOf(record[POS_STATE]);
			
			//validar que el elemento se encuentre en el país correcto
			
			if(warehouseElementVOItems.containsKey(serial) && warehouseElementVOItems.get(serial)!=null){
				dataElementCountry = warehouseElementVOItems.get(serial);
				if(dataElementCountry!=null){
					countryId = (BigDecimal) dataElementCountry[1];
					warehouseId = (BigDecimal) dataElementCountry[2];
					if(warehouseId != null && countryId != null) {
						if(!getUploadFile().getCountry().getId().equals(countryId.longValue())) {
							throw new BusinessException("El elemento con serial " + serial + " se encuentra en una bodega que no está en el país desde el que se cargó el archivo");
						}
					}
				}
			}
			
			Dealer dealer = (Dealer) fileRecordDTO.getParam(PARAM_DEALER);
			ElementVO element = (ElementVO) fileRecordDTO.getParam(PARAM_ELEMENT);
			
			
			if (state == 1){
				elementsVOToAvalilable.add( element );
				
			}else if (state == 0 ){
				elementsVOToReturn.add( element );
				
			}
			if (elementsVOToAvalilable.size() > 0){
				//Llamar a Negocio que hace el llamado de pasar de la WH de QA a la de Disponibles
				businessWarehouseElement.moveSerializedElementBetweenWareHouseQualityAvailable(elementsVOToAvalilable, dealer, null, null, this.getUploadFile().getUser().getId());
			}
				
			if (elementsVOToReturn.size() > 0){
				//Llamar a negocio que hace el llamado de pasar de la WH de QA a la de Devoluciones
				businessWarehouseElement.moveSerializedElementBetweenWareHouseQualityReturn(elementsVOToReturn, dealer, null, null, this.getUploadFile().getUser().getId());
			}

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<FileDetailProcess> doGlobalValidationsAndDropNotValidRecords(List<FileRecordDTO> fileData) {
		max = fileData.size();
		actualIndex = -1;
		elementsVOToAvalilable = new ArrayList<ElementVO>();
		elementsVOToReturn = new ArrayList<ElementVO>();
		List<FileDetailProcess> errors = new ArrayList<FileDetailProcess>();
		Dealer dealer = null;
		try {
			UploadFileParam pDealerId = uploadFileParamDAO.getUploadFileParamByUploadFileAndName(this.getUploadFile().getId(),CodesBusinessEntityEnum.FILE_PARAM_DEALER_ID.getCodeEntity());
			
			
			if(pDealerId != null && NumberUtils.isNumber(pDealerId.getParamValue())) {
				dealer = dealersDAO.getDealerByID( Long.valueOf(pDealerId.getParamValue()) );
				if(dealer == null) {
					throw new BusinessException("no se encontró el dealer con identificador = " + pDealerId.getParamValue());	
				}
			} else {
				throw new BusinessException("no se encontró un valor válido para el parámetro con el identificador del Dealer.");
			}
			
		} catch(Exception e) {
			log.error(e);
			errors.add( buildFileDetailProcess(0, e.getMessage()) );			
			fileData.clear();//no se debe procesar ningún registro
		}
		
		List<String> serialCodes = new ArrayList<String>();
		
		for (Iterator<FileRecordDTO> iterator = fileData.iterator(); iterator.hasNext();) {
			
			FileRecordDTO fileRecordDTO = iterator.next();
			
			String[] record = fileRecordDTO.getRowData();	
			
			try {
				
				String serial = record[POS_SERIAL];
				String serialLinked = record[POS_LINKED_SERIAL];
				String state = record[POS_STATE];
				
				serialCodes.add(serial);
			
				if(StringUtils.isBlank(serial)) {
					throw new BusinessException("El serial del elemento es obligatorio");
				}
				
				if(StringUtils.isBlank(state) || !isNumber01(state)) {
					throw new BusinessException("El estado es obligatorio y su valor debe ser 0 o 1");
				}
				
		
				//validar que el elemento se encuentre en el país correcto
				/*WarehouseElementVO warehouseElementVO = businessWarehouseElement.getWarehouseElementBySerialActive(serial);				
				if(warehouseElementVO != null && warehouseElementVO.getWarehouseId() != null && warehouseElementVO.getWarehouseId().getCountry() != null) {
					Long elementCountryId = warehouseElementVO.getWarehouseId().getCountry().getId();
					if(!getUploadFile().getCountry().getId().equals(elementCountryId)) {
						throw new BusinessException("El elemento con serial " + serial + " se encuentra en una bodega que no está en el país desde el que se cargó el archivo");
					}
				}*/
				
				fileRecordDTO.clearParams();
				fileRecordDTO.addParam(PARAM_DEALER, dealer);
				//fileRecordDTO.addParam(PARAM_ELEMENT,  UtilsBusiness.copyObject( ElementVO.class, serialized.getElement()) );
				
				
			} catch (Exception e) {
				errors.add( buildFileDetailProcess(fileRecordDTO.getRow(), e.getMessage()) );
				iterator.remove();//eliminar el registro para que no sea procesado posteriormente
			}
		
		}
		try{
			warehouseElementVOItems = businessWarehouseElement.getWarehouseElementBySerialActive(serialCodes,this.getUploadFile().getCountry().getId());	
			Map<String, Object[]> validSerialCodes = daoSerialized.getSerialExists(serialCodes,this.getUploadFile().getCountry().getId());//la llkave es el serial, el valor es el codigo del tipo de elemento
			List<String> invalidSerialCodes = daoSerialized.getLinkedSerialExists(serialCodes,this.getUploadFile().getCountry().getId());
			
			for (Iterator<FileRecordDTO> iterator = fileData.iterator(); iterator.hasNext();) {
				
				FileRecordDTO fileRecordDTO = iterator.next();
				
				String[] record = fileRecordDTO.getRowData();	
				
				try {
					
					String serial = record[POS_SERIAL];
					String serialLinked = record[POS_LINKED_SERIAL];
					String state = record[POS_STATE];
					
					if(!validSerialCodes.containsKey(serial)) {
						throw new BusinessException("No se encontró el elemento con serial " + serial);
					}
					
					if(invalidSerialCodes != null && invalidSerialCodes.contains(serial)) {
						throw new BusinessException("El serial " + serial + " está vinculado a otro elemento. Si realmente se desea confirmar este elemento, se debe poner este serial en la columna de serial vinculado");
					}
					
					if(validSerialCodes.containsKey(serial) && validSerialCodes.get(serial)!=null && validSerialCodes.get(serial)[1]!=null && !validSerialCodes.get(serial)[1].equals("")) {
						if(StringUtils.isBlank(serialLinked)) {
							throw new BusinessException("Debe agregar el serial vinculado, ya que el elemento con serial " + serial + " tiene un elemento vinculado");
						}
						if(validSerialCodes.get(serial)[1]!=null && !validSerialCodes.get(serial)[1].equals(serialLinked)) {
							throw new BusinessException("El serial vinculado registrado no coincide con el serial vinculado del elemento");
						}
					}
					if(!StringUtils.isBlank(serialLinked) && validSerialCodes.get(serial)!=null && (validSerialCodes.get(serial)[1]==null || validSerialCodes.get(serial)[1].equals(""))){
						throw new BusinessException("El serial principal NO tiene un serial vinculado como indica el archivo");
					}
					
					ElementVO elementVO = new ElementVO();
					BigDecimal bDElement = (BigDecimal) validSerialCodes.get(serial)[2];
					elementVO.setId(bDElement.longValue());
					fileRecordDTO.addParam(PARAM_ELEMENT, elementVO);
					
				} catch (Exception e) {
					errors.add( buildFileDetailProcess(fileRecordDTO.getRow(), e.getMessage()) );
					iterator.remove();//eliminar el registro para que no sea procesado posteriormente
				}
			}
			
		}catch (Exception e) {
			errors.add( buildFileDetailProcess(0, e.getMessage()) );
		}
		return errors;
	}

	@Override
	public boolean validateFile() throws BusinessException {
		return true;
	}

}
