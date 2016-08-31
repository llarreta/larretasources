package co.com.directv.sdii.ejb.business.file.impl.stock;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor;
import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.dealers.CountriesCRUDBeanLocal;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.UploadFileParam;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.vo.CountryVO;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.UploadFileParamDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;

/**
 * Clase encargada del cambio de modelos de varios elementos
 * mediante la carga de un archivo
 * 
 * 
 * Fecha de Creación: 02/09/2011
 * @author waguilera <a href="mailto:waguilera@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="FileProcessorChangeTypeSerializedElementsLocal",mappedName="ejb/FileProcessorChangeTypeSerializedElementsLocal")
public class FileProcessorChangeTypeSerializedElements extends BasicFileProcessor implements FileProcessorChangeTypeSerializedElementsLocal {

	private final static Logger log = UtilsBusiness.getLog4J(FileProcessorChangeTypeSerializedElementsLocal.class);

	@EJB(name="UploadFileParamDAOLocal", beanInterface=UploadFileParamDAOLocal.class)
	private UploadFileParamDAOLocal uploadFileParamDAO;

	@EJB(name="CountriesCRUDBeanLocal", beanInterface=CountriesCRUDBeanLocal.class)
	private CountriesCRUDBeanLocal countriesCRUDBean;
	
	@EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal warehouseElementDAO;
	
	@EJB(name="ElementBusinessBeanLocal", beanInterface=ElementBusinessBeanLocal.class)
	private ElementBusinessBeanLocal elementBusinessBean;
	
	@EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal daoWarehouseElement;
	
	@EJB(name="ElementTypeDAOLocal", beanInterface=ElementTypeDAOLocal.class)
    private ElementTypeDAOLocal daoElementType;
	
	
	/*
	 * Forma de leer el archivo
	 * Posición 0: Serial del elemento						(Alfanumérico)
	 */
	private static int SERIAL_ELEMENTO = 0;
	
	/*
	 * Posicion 1: Codigo del modelo del elemento nuevo (Alfanumérico)
	 */
	private static int CODIGO_MODELO_ELEMENTO_NUEVO = 1;
	
	 /*
	 * Posición 1: Código del tipo de elemento nuevo		(Alfanumérico)
	 */
	private static int CODIGO_TIPO_ELEMENTO_NUEVO = 2;
	
	/*
	 * Posición 2: Serial del elemento vinculado 			(Alfanumérico)
	 */
	private static int SERIAL_ELEMENTO_VINCULADO = 3;
	
	 /*
	 * Posición 4: Codigo del modelo nuevo del elemento vinculado (Alfanumérico)
	 */
	private static int CODIGO_MODELO_ELEMENTO_VINCULADO_NUEVO = 4;
	
	 /*
	 * Posición 3: Código del tipo de elemento vinculado 	(Alfanumérico)
	 */
	private static int CODIGO_TIPO_ELEMENTO_VINCULADO = 5;
	
	private CountryVO country = null;
	
	public FileProcessorChangeTypeSerializedElements(){
		String[] columnTitles = new String[] {
				ApplicationTextEnum.ELEMENT_SERIAL.getApplicationTextValue(),
				ApplicationTextEnum.NEW_ELEMENT_MODEL_CODE.getApplicationTextValue(),
				ApplicationTextEnum.NEW_ELEMENT_TYPE_CODE.getApplicationTextValue(),
				ApplicationTextEnum.SERIAL_LINKED_ELEMENT.getApplicationTextValue(),
				ApplicationTextEnum.MODEL_CODE_NEW_LINKED_ELEMENT.getApplicationTextValue(),
				ApplicationTextEnum.TYPE_CODE_LINKED_ELEMENT.getApplicationTextValue()
			};
		setColumnTitles(columnTitles);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processRecord(FileRecordDTO fileRecordDTO)
			throws BusinessException {
		try{
			WarehouseElement warehouseElementMain=null;
			WarehouseElement warehouseElementLink=null;
			
			ElementType elementTypeNewMainElement= null;
			ElementType elementTypeNewLinkElement= null;
			
			
			
			
			String serialElemento=fileRecordDTO.getRowData()[SERIAL_ELEMENTO];//fileRecordDTO.getRowData()[0]
			String codigoModeloElementoNuevo=fileRecordDTO.getRowData()[CODIGO_MODELO_ELEMENTO_NUEVO];
			String codigoTipoElementoNuevo=fileRecordDTO.getRowData()[CODIGO_TIPO_ELEMENTO_NUEVO];//fileRecordDTO.getRowData()[1]
			String serialElementoVinculado=fileRecordDTO.getRowData()[SERIAL_ELEMENTO_VINCULADO];//fileRecordDTO.getRowData()[2]
			String codigoModeloElementoVinculadoNuevo=fileRecordDTO.getRowData()[CODIGO_MODELO_ELEMENTO_VINCULADO_NUEVO];
			String codigoTipoElementoVinculado=fileRecordDTO.getRowData()[CODIGO_TIPO_ELEMENTO_VINCULADO];//fileRecordDTO.getRowData()[3]

			boolean existSerialElement = serialElemento !=null && !serialElemento.isEmpty();
			boolean existModelCodeElement = codigoModeloElementoNuevo !=null && !codigoModeloElementoNuevo.isEmpty();
			boolean existTypeCodeElement = codigoTipoElementoNuevo !=null && !codigoTipoElementoNuevo.isEmpty();
			boolean existSerialLinkedElement = serialElementoVinculado !=null && !serialElementoVinculado.isEmpty();
			boolean existModelCodeLinkedElement = codigoModeloElementoVinculadoNuevo !=null && !codigoModeloElementoVinculadoNuevo.isEmpty();
			boolean existTypeCodeLinkedElement = codigoTipoElementoVinculado !=null && !codigoTipoElementoVinculado.isEmpty();
			
			boolean existLinkElement = false;
			
			if(existSerialLinkedElement || existModelCodeLinkedElement || existTypeCodeLinkedElement){
				if(existSerialLinkedElement && existModelCodeLinkedElement && existTypeCodeLinkedElement){
					existLinkElement = true;
				}else{
					throw new BusinessException("Si diligencia algún dato del elemento vinculado tambien se debe diligenciar serial, código del tipo del elemento y su modelo)");
				}
			}
			
			if(!existSerialElement){
				throw new BusinessException("El serial del elemento es obligatorio");
			}
			
			if(!existModelCodeElement){
				throw new BusinessException("El nuevo modelo del elemento es obligatorio");
			}
			
			if(!existTypeCodeElement){
				throw new BusinessException("El nuevo tipo del elemento es obligatorio");
			}
			
			

			
		
			//Se obtiene el warehouse_elements
			warehouseElementMain = warehouseElementDAO.getWarehouseElementBySerialActive(serialElemento,getCountry().getId());
			if(warehouseElementMain==null){
				throw new BusinessException("Serial ["+serialElemento+"] no encontrado");
			}


			if(existLinkElement){
				warehouseElementLink = warehouseElementDAO.getWarehouseElementBySerialActive(serialElementoVinculado,getCountry().getId());
				if(warehouseElementLink==null){
					throw new BusinessException("Serial ["+serialElementoVinculado+"] no encontrado");
				}
			}

			//Se valida el pais de la bodega donde se encontró el elemento 
			if(warehouseElementMain.getWarehouseId().getCountry().getId().longValue()!=this.getCountry().getId().longValue()){
				throw new BusinessException("El elemento con Serial ["+warehouseElementMain.getSerialized().getSerialCode()+"] no se encontró en el pais "+this.getCountry().getCountryName());
			}

			//Se valida el pais de la bodega donde se encontró el elemento vinculado
			if(warehouseElementLink!=null){
				if(warehouseElementLink.getWarehouseId().getCountry().getId().longValue()!=this.getCountry().getId().longValue()){
					throw new BusinessException("El elemento con Serial ["+warehouseElementLink.getSerialized().getSerialCode()+"] no se encontró en el pais "+this.getCountry().getCountryName());
				}
			}

			

			
			//Valido que exista el tipo de elemento del elemento principal
			elementTypeNewMainElement = daoElementType.getElementTypeByCode(codigoTipoElementoNuevo);
			if(elementTypeNewMainElement==null){
				throw new BusinessException(ErrorBusinessMessages.ELEMENT_TYPE_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_TYPE_NOT_EXIST.getMessage());
			}
			
			if(elementTypeNewMainElement.getElementModel().getModelCode().toUpperCase().equals(warehouseElementMain.getSerialized().getElement().getElementType().getElementModel().getModelCode())){
				throw new BusinessException("(Elemento principal) No se puede realizar el cambio de modelo  ya que es el mismo");
			}
			
			if(existLinkElement){
				warehouseElementLink = daoWarehouseElement.getWarehouseElementBySerialActive(serialElementoVinculado,getCountry().getId());
				elementTypeNewLinkElement = daoElementType.getElementTypeByCode(codigoTipoElementoVinculado);
				if(elementTypeNewLinkElement==null){
					throw new BusinessException(ErrorBusinessMessages.ELEMENT_TYPE_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_TYPE_NOT_EXIST.getMessage());
				}
				
				if(elementTypeNewLinkElement.getElementModel().getModelCode().toUpperCase().equals(warehouseElementLink.getSerialized().getElement().getElementType().getElementModel().getModelCode())){
					throw new BusinessException("(Elemento vinculado) No se puede realizar el cambio de modelo  ya que es el mismo");
				}
			}
			
			if(existLinkElement){
				elementBusinessBean.changeElementModel(warehouseElementMain.getSerialized().getElementId(), elementTypeNewMainElement.getTypeElementCode(), warehouseElementLink.getSerialized().getElementId(), elementTypeNewLinkElement.getTypeElementCode());
			}else{
				elementBusinessBean.changeElementModel(warehouseElementMain.getSerialized().getElementId(), elementTypeNewMainElement.getTypeElementCode(), null, null);
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
			this.setCountry(null);
			//1. Consulta parametro de pais
			UploadFileParam param = uploadFileParamDAO.getUploadFileParamByUploadFileAndName(this.getUploadFile().getId(),CodesBusinessEntityEnum.FILE_PARAM_COUNTRY_ID.getCodeEntity());
			if (param==null){
				throw new BusinessException("Parametro de pais no encontrado");
			}

			//2. Consulta el pais encontrado en el parametro
			this.setCountry(countriesCRUDBean.getCountriesByID(Long.valueOf(param.getParamValue())));
			if (country==null){
				throw new BusinessException("Parametro de pais no encontrado");
			}
			
		}catch (Exception e) {
			log.error(e);
			errors.add(buildFileDetailProcess(0, e.getMessage()));			
			fileData.clear();//no se debe procesar ningún registro
		}
		return errors;
	}

	public CountryVO getCountry() {
		return country;
	}

	public void setCountry(CountryVO country) {
		this.country = country;
	}
	
}
