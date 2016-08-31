package co.com.directv.sdii.ejb.business.file.impl.stock;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor;
import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.dealers.CountriesCRUDBeanLocal;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.UploadFileParam;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.UploadFileParamDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;

/**
 * Clase encargada de la serilizacion masiva de elementos mediante
 * la carga de un archivo
 * 
 * 
 * Fecha de Creación: 18/08/2011
 * @author waguilera <a href="mailto:waguilera@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="FileProcessorMassiveLinkSerializedElements",mappedName="ejb/FileProcessorMassiveLinkSerializedElements")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FileProcessorMassiveLinkSerializedElements extends BasicFileProcessor implements FileProcessorMassiveLinkSerializedElementsLocal {

	private final static Logger log = UtilsBusiness.getLog4J(FileProcessorMassiveLinkSerializedElements.class);

	@EJB(name="UploadFileParamDAOLocal", beanInterface=UploadFileParamDAOLocal.class)
	private UploadFileParamDAOLocal uploadFileParamDAO;

	@EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
	private SerializedDAOLocal serializedDAO;

	@EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal warehouseElementDAO;

	@EJB(name="CountriesCRUDBeanLocal", beanInterface=CountriesCRUDBeanLocal.class)
	private CountriesCRUDBeanLocal countriesCRUDBean;

	@EJB(name="WarehouseDAOLocal", beanInterface=WarehouseDAOLocal.class)
	private WarehouseDAOLocal warehouseDAO;

	@EJB(name="ElementBusinessBeanLocal", beanInterface=ElementBusinessBeanLocal.class)
    private ElementBusinessBeanLocal businessElement;
	

	private Country country;

	private static int POS_SERIAL_ELEMENT_1 = 0;
	private static int POS_SERIAL_ELEMENT_2 = 1;
	private static int POS_COD_BODEGA = 2;
	
	
	public FileProcessorMassiveLinkSerializedElements(){
		String[] columnTitles = new String[] {
				ApplicationTextEnum.ELEMENT_TYPE.getApplicationTextValue()+" 1",
				ApplicationTextEnum.SERIAL_ELEMENT.getApplicationTextValue()+" 1",
				ApplicationTextEnum.ELEMENT_TYPE.getApplicationTextValue()+" 2",
				ApplicationTextEnum.SERIAL_ELEMENT.getApplicationTextValue()+" 2",
				ApplicationTextEnum.WAREHOUSE_CODE.getApplicationTextValue()
			};
		setColumnTitles(columnTitles);
	}

	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processRecord(FileRecordDTO fileRecordDTO)
			throws BusinessException {
		try{
			
			if(fileRecordDTO.getRowData().length<5){
				throw new BusinessException("Registro no valido. Se requiere Código de tipo de elemento 1, Serial elemento 1, Código de tipo de elemento 2, Serial elemento 2, Código de la Bodega");
			}
			
			String serialElement1 = fileRecordDTO.getRowData()[POS_SERIAL_ELEMENT_1];
			String serialElement2 = fileRecordDTO.getRowData()[POS_SERIAL_ELEMENT_2];
			String codeWarehouse = fileRecordDTO.getRowData()[POS_COD_BODEGA];
			
			//Validaciones
			if (StringUtils.isBlank(serialElement1)) {
				throw new BusinessException("Debe digitar un serial para el elemento 1");
			}
			if (StringUtils.isBlank(serialElement2)) {
				throw new BusinessException("Debe digitar un serial para el elemento 2");
			}
			if (StringUtils.isBlank(codeWarehouse)) {
				throw new BusinessException("Debe digitar el código de la bodega");
			}
			
			//VALIDACIONES ELEMENTO 1
			//Se valida que existe el elemento1 de tipo serializado
			Serialized serializedElement1 = null;

			serializedElement1 = serializedDAO.getSerializedBySerial(fileRecordDTO.getRowData()[POS_SERIAL_ELEMENT_1],this.getUploadFile().getCountry().getId());
			if(serializedElement1==null){
				throw new BusinessException("Serial ["+fileRecordDTO.getRowData()[POS_SERIAL_ELEMENT_1]+"] no encontrado");
			}

			//Valida si el elemento ya se encuentra vinculado
			if(serializedElement1.getSerialized()!=null){			
				throw new BusinessException("Elemento PRINCIPAL con Serial ["+fileRecordDTO.getRowData()[POS_SERIAL_ELEMENT_1]+"] ya se encuentra vinculado");
			}
			
			//Valida si el elemento 1 esta vinculado por otro elemento
			List<Serialized> listElementLinked1 =  serializedDAO.getLinkedSerializedBySerialCode(fileRecordDTO.getRowData()[POS_SERIAL_ELEMENT_1],this.getUploadFile().getCountry().getId());
			if(listElementLinked1!=null && listElementLinked1.size()>0){
				throw new BusinessException("El elemento PRINCIPAL con Serial ["+fileRecordDTO.getRowData()[POS_SERIAL_ELEMENT_1]+"] ya se encuentra vinculado por el elemento con serial "+listElementLinked1.get(0).getSerialCode());
			}
			listElementLinked1 = null;

			//VALIDACIONES ELEMENTO 2
			//Se valida que existe el elemento2 de tipo  serializado
			Serialized serializedElement2 = null;
			serializedElement2 = serializedDAO.getSerializedBySerial(fileRecordDTO.getRowData()[POS_SERIAL_ELEMENT_2],this.getUploadFile().getCountry().getId());
			if(serializedElement2==null){
				throw new BusinessException("Serial ["+fileRecordDTO.getRowData()[POS_SERIAL_ELEMENT_2]+"] no encontrado");
			}

			//Valida si el elemento 2 ya se ecuentra vinculado
			if(serializedElement2.getSerialized()!=null){
				throw new BusinessException("Elemento VINCULADO con Serial ["+fileRecordDTO.getRowData()[POS_SERIAL_ELEMENT_2]+"] ya se encuentra vinculado");
			}
			
			//Valida si el elemento 2 esta vinculado por otro elemento
			List<Serialized> listElementLinked2 =  serializedDAO.getLinkedSerializedBySerialCode(fileRecordDTO.getRowData()[POS_SERIAL_ELEMENT_2],this.getUploadFile().getCountry().getId());
			if(listElementLinked2!=null && listElementLinked2.size()>0){
				throw new BusinessException("El elemento VINCULADO con Serial ["+fileRecordDTO.getRowData()[POS_SERIAL_ELEMENT_2]+"] ya se encuentra vinculado por el elemento con serial "+listElementLinked2.get(0).getSerialCode());
			}
			listElementLinked2 = null;
			
			//Validar la clase de elemento para permitir la vinculacion
			boolean existDeco = false;
			boolean existSmartCard = false;
			//Validar que los nuevos tipos de elemento sean uno de smartcard y otro de decodificador
			if(serializedElement1.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity())){
				existDeco = true;
			}else if(serializedElement1.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity())){
				existSmartCard = true;
			}
			
			if(serializedElement2.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity())){
				existDeco = true;
			}else if(serializedElement2.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity())){
				existSmartCard = true;
			}
			if(!existDeco||!existSmartCard){					
				throw new BusinessException("Para vincular dos elementos se requiere que uno sea de clase SmartCard y el otro de tipo Decodificador");
			}
			
			
			//Verificar si existe la bodega
			Warehouse bodegaRegistro = warehouseDAO.getWarehouseByCountryIdAndCodeAndNotVirtual(fileRecordDTO.getRowData()[POS_COD_BODEGA], country.getId());
			if(bodegaRegistro==null){
				throw new BusinessException("La bodega "+fileRecordDTO.getRowData()[POS_COD_BODEGA]+" no existe en el pais "+country.getCountryName());
			}


			//Se consulta y se valida la bodega del elemento 1
			List<WarehouseElement> listWarehouseElement1 = warehouseElementDAO.getWhElementsByWhSourceIdAndSerializedAndElementIdInWH(bodegaRegistro.getId(), CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity(), serializedElement1.getElementId());
			if(listWarehouseElement1.size() == 0){
				throw new BusinessException("No se encuentra el elemento 1 con serial "+serializedElement1.getSerialCode()+" en la bodega con código "+bodegaRegistro.getWhCode());
			}

			//Se consulta y se valida la bodega del elemento 2
			List<WarehouseElement> listWarehouseElement2 = warehouseElementDAO.getWhElementsByWhSourceIdAndSerializedAndElementIdInWH(bodegaRegistro.getId(), CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity(), serializedElement2.getElementId());
			if(listWarehouseElement2.size() == 0){
				throw new BusinessException("No se encuentra el elemento 2 con serial "+serializedElement2.getSerialCode()+" en la bodega con código "+bodegaRegistro.getWhCode());
			}


			//Valida pais de la bodega
			if(!bodegaRegistro.getCountry().getCountryCode().equals(country.getCountryCode())){
				throw new BusinessException("El bodega con código"+ fileRecordDTO.getRowData()[POS_COD_BODEGA] +" no es de "+country.getCountryName());
			}
			
			businessElement.linkSerializedElements(serializedElement1.getElementId(), serializedElement2.getElementId());
			
		}catch (Throwable e) {
			throw new BusinessException(e.getMessage(), e);
		}
		
	}
	
	


	
	public boolean validateFile() throws BusinessException {
		return true;	
	}


	@Override
	public List<FileDetailProcess> doGlobalValidationsAndDropNotValidRecords(
			List<FileRecordDTO> fileData) {
		List<FileDetailProcess> errors = new ArrayList<FileDetailProcess>();
		try{

			//1. Consulta parametro de pais
			UploadFileParam param = uploadFileParamDAO.getUploadFileParamByUploadFileAndName(this.getUploadFile().getId(),CodesBusinessEntityEnum.FILE_PARAM_COUNTRY_ID.getCodeEntity());
			if (param==null){
				throw new BusinessException("Parametro de pais no encontrado");
			}

			//2. Consulta el pais encontrado en el parametro
			country = countriesCRUDBean.getCountriesByID(Long.valueOf(param.getParamValue()));
		}catch (Exception e) {
			log.error(e);
			errors.add( buildFileDetailProcess(0, e.getMessage()));			
			fileData.clear();//no se debe procesar ningún registro
		}
		return errors;
	}

}
