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
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.Serialized;
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
@Stateless(name="FileProcessorMassiveUnLinkSerializedElementsLocal",mappedName="ejb/FileProcessorMassiveUnLinkSerializedElementsLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FileProcessorMassiveUnLinkSerializedElements extends BasicFileProcessor implements FileProcessorMassiveUnLinkSerializedElementsLocal {

	private final static Logger log = UtilsBusiness.getLog4J(FileProcessorMassiveUnLinkSerializedElements.class);

	@EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
	private SerializedDAOLocal serializedDAO;

	@EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal warehouseElementDAO;

	@EJB(name="CountriesCRUDBeanLocal", beanInterface=CountriesCRUDBeanLocal.class)
	private CountriesCRUDBeanLocal countriesCRUDBean;

	@EJB(name="WarehouseDAOLocal", beanInterface=WarehouseDAOLocal.class)
	private WarehouseDAOLocal warehouseDAO;
	
	@EJB(name="UploadFileParamDAOLocal", beanInterface=UploadFileParamDAOLocal.class)
	private UploadFileParamDAOLocal uploadFileParamDAO;

	private Country country;

	private static int POS_SERIAL_ELEMENT_1 = 0;
	private static int POS_SERIAL_ELEMENT_2 = 1;
	private static int POS_COD_BODEGA = 2;

	public FileProcessorMassiveUnLinkSerializedElements(){
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
				throw new BusinessException("Registro no valido. Se requiere Código de tipo de elemento 1, Serial elemento 1, Código de tipo de elemento 2, Serial elemento 2, Código de la ubicación");
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
				throw new BusinessException("Debe digitar el código de la ubicación");
			}
			
			//VALIDACIONES BASICAS ELEMENTO 1
			//Se valida que existe el elemento1 de tipo serializado
			Serialized serializedElement1 = null;

			serializedElement1 = serializedDAO.getSerializedBySerial(fileRecordDTO.getRowData()[POS_SERIAL_ELEMENT_1],this.getUploadFile().getCountry().getId());
			if(serializedElement1==null){
				throw new BusinessException( "Elemento 1 con serial ["+fileRecordDTO.getRowData()[POS_SERIAL_ELEMENT_1]+"] no encontrado");
				
			}
			
			//FIN VALIDACIONES BASICAS ELEMENTO 1
			
			
			

			//VALIDACIONES  BASICAS ELEMENTO 2
			//Se valida que existe el elemento2 de tipo  serializado
			Serialized serializedElement2 = null;
			serializedElement2 = serializedDAO.getSerializedBySerial(fileRecordDTO.getRowData()[POS_SERIAL_ELEMENT_2],this.getUploadFile().getCountry().getId());
			if(serializedElement2==null){
				throw new BusinessException( "Elemento 2 con serial ["+fileRecordDTO.getRowData()[POS_SERIAL_ELEMENT_2]+"] no encontrado");
			}

			//FIN VALIDACIONES BASICAS ELEMENTO 2
			
			//INICIO VALIDACIONES DE CRUZE ENTRE ELEMENTOS
			//Validaciones elemento 1
			if(serializedElement1.getSerialized()!=null){
				if(!serializedElement1.getSerialized().getSerialCode().trim().equals(serializedElement2.getSerialCode().trim())){
					throw new BusinessException( "El elemento 1 no esta vinculado con el elemento 2. Se encuentra vinculado con el elemento [serial: "+serializedElement1.getSerialized().getSerialCode()+"]");
			
				}
			}
			List<Serialized> listSerializedTop = new ArrayList<Serialized>();  
			listSerializedTop = serializedDAO.getLinkedSerializedBySerialCode(serializedElement1.getSerialCode(),this.getUploadFile().getCountry().getId());
			
			//limpiar lista en caso de que existe el elemento 2 en la lista de elementos vinculantes del elemento 1
			for(int j=0;j<listSerializedTop.size();j++){
				Serialized serializedIter = listSerializedTop.get(j);
				if(serializedIter.getSerialCode().trim().equals(serializedElement2.getSerialCode().trim())){
					listSerializedTop.remove(j);
				}
			}
			if(listSerializedTop!=null && listSerializedTop.size()>0){
				throw new BusinessException( "El elemento 1 no esta vinculado con el elemento 2. Lo esta vinculando el elemento [serial: "+listSerializedTop.get(0).getSerialCode()+"]");
			}
			//Validaciones elemento 2
			if(serializedElement2.getSerialized()!=null){
				if(!serializedElement2.getSerialized().getSerialCode().trim().equals(serializedElement1.getSerialCode())){
					throw new BusinessException( "El elemento 2 no esta vinculado con el elemento 1. Se encuentra vinculado con el elemento [serial: "+serializedElement2.getSerialized().getSerialCode()+"]");
				}
			}
			listSerializedTop = new ArrayList<Serialized>();  
			listSerializedTop = serializedDAO.getLinkedSerializedBySerialCode(serializedElement2.getSerialCode(),this.getUploadFile().getCountry().getId());
			
			//limpiar lista en caso de que existe el elemento 1 en la lista de elementos vinculantes del elemento 2
			for(int j=0;j<listSerializedTop.size();j++){
				Serialized serializedIter = listSerializedTop.get(j);
				if(serializedIter.getSerialCode().trim().equals(serializedElement1.getSerialCode().trim())){
					listSerializedTop.remove(j);
				}
			}
			
			if(listSerializedTop!=null && listSerializedTop.size()>0){
				throw new BusinessException( "El elemento 2 no esta vinculado con el elemento 1. Lo esta vinculando el elemento [serial: "+listSerializedTop.get(0).getSerialCode()+"]");
			}
			//FIN VALIDACIONES DE CRUZE ENTRE ELEMENTOS
			
			
			
			//Verificar si existe la bodega en el pais actual
			Warehouse bodegaRegistro = warehouseDAO.getWarehouseByCountryIdAndCodeAndNotVirtual(fileRecordDTO.getRowData()[POS_COD_BODEGA], country.getId());
			if(bodegaRegistro==null){
				throw new BusinessException( "La ubicación con código "+fileRecordDTO.getRowData()[POS_COD_BODEGA]+" no existe en el pais "+country.getCountryName());
			}


			//Se consulta y se valida la bodega del elemento 1
			List<WarehouseElement> listWarehouseElement1 = warehouseElementDAO.getWhElementsByWhSourceIdAndSerializedAndElementIdInWH(bodegaRegistro.getId(), CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity(), serializedElement1.getElementId());
			if(listWarehouseElement1.size() == 0){
				throw new BusinessException( "No se encuentra el elemento 1 con serial "+serializedElement1.getSerialCode()+" en la ubicación con código "+bodegaRegistro.getWhCode());
				
			}

			//Se consulta y se valida la bodega del elemento 2
			List<WarehouseElement> listWarehouseElement2 = warehouseElementDAO.getWhElementsByWhSourceIdAndSerializedAndElementIdInWH(bodegaRegistro.getId(), CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity(), serializedElement2.getElementId());
			if(listWarehouseElement2.size() == 0){
				throw new BusinessException( "No se encuentra el elemento 2 con serial "+serializedElement2.getSerialCode()+" en la ubicación con código "+bodegaRegistro.getWhCode());
				
			}


			//Valida pais de la bodega
			if(!bodegaRegistro.getCountry().getCountryCode().equals(country.getCountryCode())){
				throw new BusinessException( "El bodega con código"+ fileRecordDTO.getRowData()[POS_COD_BODEGA] +" no es de "+country.getCountryName());
				
			}
			
			boolean vinculados = false;
			//Valido si el elemento 2 esta vinculado al elemento 1
			if(serializedElement1.getSerialized() != null && serializedElement1.getSerialized().getSerialCode().trim().equals(serializedElement2.getSerialCode().trim())){
				vinculados = true;
				serializedElement1.setSerialized(null);
				serializedDAO.updateSerialized(serializedElement1);
			}
			
			//Valido si el elemento 1 esta vinculado al elemento 2
			if(serializedElement2.getSerialized() != null && serializedElement2.getSerialized().getSerialCode().trim().equals(serializedElement1.getSerialCode().trim())){
				vinculados = true;
				serializedElement2.setSerialized(null);
				serializedDAO.updateSerialized(serializedElement2);
			}
			
			if(!vinculados){
				throw new BusinessException( "El elemento 1 [serial:"+serializedElement1.getSerialCode()+"]  no esta vinculado con el elemento 2 [serial:"+serializedElement2.getSerialCode()+"]");
				
			}
			
		}catch (Throwable e) {
			throw new BusinessException(e.getMessage(), e);
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

			//1. Consulta el pais encontrado en el registro correspondiente al archivo
			country = this.getUploadFile().getCountry();
		}catch (Exception e) {
			log.error(e);
			errors.add( buildFileDetailProcess(0, e.getMessage()));			
			fileData.clear();//no se debe procesar ningún registro
		}
		return errors;
	}
	


}
