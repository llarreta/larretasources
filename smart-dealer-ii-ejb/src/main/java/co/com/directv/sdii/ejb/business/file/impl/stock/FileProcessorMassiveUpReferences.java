package co.com.directv.sdii.ejb.business.file.impl.stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import co.com.directv.sdii.ejb.business.dealers.CountriesCRUDBeanLocal;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.ejb.business.stock.RefQuantityControlItemBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.RefQuantityControlItem;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.ReferenceStatus;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.vo.RefQuantityControlItemVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.RefQuantityControlItemDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
/**
 * 
 * <Descripcion> 
 * 
 * Fecha de Creación: 11/08/2011
 * @author hcorredor <a href="mailto:hcorredor@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="FileProcessorMassiveUpReferencesLocal",mappedName="ejb/FileProcessorMassiveUpReferencesLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FileProcessorMassiveUpReferences extends BasicFileProcessor implements FileProcessorMassiveUpReferencesLocal{
	
	private final static Logger log = UtilsBusiness.getLog4J(FileProcessorMassiveUpReferences.class);
	
	@EJB(name="WarehouseDAOLocal", beanInterface=WarehouseDAOLocal.class)
    private WarehouseDAOLocal daoWarehouse;
	
	@EJB(name="ReferenceStatusDAOLocal", beanInterface=ReferenceStatusDAOLocal.class)
    private ReferenceStatusDAOLocal daoReferenceStatus;
	
	@EJB(name="UserDAOLocal",beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
	
	@EJB(name = "ElementTypeDAOLocal", beanInterface = ElementTypeDAOLocal.class)
	private ElementTypeDAOLocal daoElementType;
	
	@EJB(name="RefQuantityControlItemDAOLocal", beanInterface=RefQuantityControlItemDAOLocal.class)
	private RefQuantityControlItemDAOLocal daoRefQtyCtrlItem;
	
	@EJB(name="CountriesCRUDBeanLocal", beanInterface=CountriesCRUDBeanLocal.class)
	private CountriesCRUDBeanLocal countriesCRUDBean;
	
	@EJB(name="ReferenceBusinessBeanLocal", beanInterface=ReferenceBusinessBeanLocal.class)
	private ReferenceBusinessBeanLocal referenceBusinessBean;
	
	@EJB(name="RefQuantityControlItemBusinessBeanLocal", beanInterface=RefQuantityControlItemBusinessBeanLocal.class)
	private RefQuantityControlItemBusinessBeanLocal refQuantityControlItemBusiness;
	
	@EJB(name="ReferenceBusinessBeanLocal", beanInterface=ReferenceBusinessBeanLocal.class)
	private ReferenceBusinessBeanLocal businessReference;
	
	private static int POS_CODE_WAREHOUSE_SOURCE = 0;
	private static int POS_CODE_WAREHOUSE_TARGET = 1;
	private static int POS_ELEMENT_TYPE_CODE = 2;
	private static int POS_QUANTITY = 3;
	private static int POS_IS_PREPAID = 4;
	private static int POS_NRO_RN = 5;

	
	private ReferenceVO reference;
	
	private User user;
	
	private Country country;
	
	private ReferenceStatus referenceStatus;
	
	public FileProcessorMassiveUpReferences(){
		String[] columnTitles = new String[] {
				ApplicationTextEnum.ORIGIN_LOCATION_CODE.getApplicationTextValue(),
				ApplicationTextEnum.DESTINATION_LOCATION_CODE.getApplicationTextValue(),
				ApplicationTextEnum.TYPE_ELEMENT_CODE.getApplicationTextValue(),
				ApplicationTextEnum.AMOUNT_REQUIRED.getApplicationTextValue(),
				ApplicationTextEnum.PREPAID.getApplicationTextValue(),
				ApplicationTextEnum.RN_NUMBER.getApplicationTextValue()
			};
		setColumnTitles(columnTitles);
	}
	
	
	
	/**
	 * 
	 * @param data
	 * @param reference
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException 
	 * @throws PropertiesException 
	 */
	private void saveRefQuantityControlItem(
			ElementType elementType, 
			Double quantityRequired, 
			Reference thisReference, 
			boolean isFirstQuantityCtrl
		) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException{
		
		RefQuantityControlItem refQuantityControlItem = new RefQuantityControlItem();
		refQuantityControlItem.setElementType(elementType);
		refQuantityControlItem.setReference(thisReference);
		refQuantityControlItem.setRequiredQty(quantityRequired);
		refQuantityControlItem.setIncludedQty(0d);
		
		//Valido si el tipo de elemento es del mismo tipo que la remision (Prepago - Postpago)  
		if(!reference.getIsPrepaidRef().equalsIgnoreCase(elementType.getElementModel().getIsPrepaidModel())){
			if(reference.getIsPrepaidRef().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_MODEL_IS_PREPAID.getCodeEntity())){
				if(isFirstQuantityCtrl){
					reference = null;
				}
				throw new BusinessException("La remisión es de tipo prepago, los tipos de elementos agregados al control de cantidades deben ser del mismo tipo");
			}else{
				if(isFirstQuantityCtrl){
					reference = null;
				}
				throw new BusinessException("La remisión es de tipo postpago, los tipos de elementos agregados al control de cantidades deben ser del mismo tipo");
			}
		}
		
		RefQuantityControlItem refQuantityControlItemFound = daoRefQtyCtrlItem.getRefQuantityControlItemByElmtTypeAndRef(elementType.getId(), thisReference.getId());
		if(refQuantityControlItemFound!=null){
			throw new BusinessException("Ya existe un control de cantidades para el elemento con código "+elementType.getTypeElementCode()+ " para la remisión Nro. "+thisReference.getId());
		}
		refQuantityControlItemBusiness.createRefQuantityControlItem(UtilsBusiness.copyObject(RefQuantityControlItemVO.class,refQuantityControlItem));
	}
	
	/**
	 * 
	 * @param data
	 * @param userId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 * @throws BusinessException 
	 */
	private ReferenceVO createReference(Warehouse whSource, Warehouse whTarget, String isPrepaid, String rnNumber ) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException{
		Reference referenceNew = new Reference();
		
		//Crea la remision...
		referenceNew.setWarehouseBySourceWh(whSource);
		referenceNew.setWarehouseByTargetWh(whTarget);
		referenceNew.setIsPrepaidRef(isPrepaid);
		referenceNew.setRnNumber(rnNumber);
		referenceNew.setReferenceStatus(referenceStatus);
		referenceNew.setCreateUserId(user);
		referenceNew.setCreationReferenceDate(new Date());
		referenceNew.setIsPreloadRef(CodesBusinessEntityEnum.REFERENCE_PRELOAD.getCodeEntity());
		referenceNew.setIsQuantityControl(CodesBusinessEntityEnum.REFERENCE_QUALITY_CTRL.getCodeEntity());
		referenceNew.setCountryCodeId(country);
		ReferenceVO referenceVO = referenceBusinessBean.generateReference(UtilsBusiness.copyObject(ReferenceVO.class, referenceNew));
		
		return referenceVO;
	}

	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<FileDetailProcess> doGlobalValidationsAndDropNotValidRecords(
			List<FileRecordDTO> fileData) {
		List<FileDetailProcess> errors = new ArrayList<FileDetailProcess>();
		reference = null;
		user = null;
		country = null;
		referenceStatus = null;
		try{

			//Capturar el usuario que realiza el proceso
			Long idUser = this.getUploadFile().getUser().getId();
			if(idUser==null){
				throw new BusinessException("Parametro de usuario no encontrado");
			}
			this.user =  daoUser.getUserById(idUser);
			if(this.user==null){
				throw new BusinessException("Parametro de usuario no encontrado");
			}
			
			
			//Capturar el pais donde se realiza el proceso
			Long idCountry = this.getUploadFile().getCountry().getId();
			if(idCountry==null){
				throw new BusinessException("Parametro de pais no encontrado");
			}
			country = countriesCRUDBean.getCountriesByID(idCountry);
			if(country==null){
				throw new BusinessException("Parametro de pais no encontrado");
			}
			
			//Capturar y leer el estado de la remisión
			String referenceStatusCode = CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity();
			referenceStatus =  daoReferenceStatus.getReferenceByCode(referenceStatusCode);
	
			
			//Validaciones basicas de campos requeridos
			for(Iterator<FileRecordDTO> iterator = fileData.iterator(); iterator.hasNext();){
				FileRecordDTO dto = iterator.next();
				try{
					if(dto.getRowData().length<6){
						throw new BusinessException("Registro no valido. Se requiere Código de tipo de elemento 1, Serial elemento 1, Código de tipo de elemento 2, Serial elemento 2, Código de la Bodega");
					}

					String codeWarehouseSource = dto.getRowData()[POS_CODE_WAREHOUSE_SOURCE];
					String codeWarehouseTarget = dto.getRowData()[POS_CODE_WAREHOUSE_TARGET];
					String codeElementType = dto.getRowData()[POS_ELEMENT_TYPE_CODE];
					String quantity = dto.getRowData()[POS_QUANTITY];
					String isPrepaid = dto.getRowData()[POS_IS_PREPAID];
					String nroRN = dto.getRowData()[POS_NRO_RN];

					//Validaciones
					if (StringUtils.isBlank(codeWarehouseSource)) {
						throw new BusinessException("Debe digitar el código de la ubicación origen");
					}
					if (StringUtils.isBlank(codeWarehouseTarget)) {
						throw new BusinessException("Debe digitar el código de la ubicación destino");
					}
					if (StringUtils.isBlank(codeElementType)) {
						throw new BusinessException("Debe digitar un código del tipo de elemento");
					}
					if (StringUtils.isBlank(quantity.trim())) {
						throw new BusinessException("Debe digitar la cantidad esperada");
					}
					if (StringUtils.isBlank(isPrepaid.trim())) {
						throw new BusinessException("Debe digitar si es prepago o no (S/N)");
					}else{
						if(!(isPrepaid.trim().equalsIgnoreCase(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())||isPrepaid.trim().equalsIgnoreCase(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity()))){
							throw new BusinessException("Debe digitar si es prepago o no (S/N)");
						}
					}
					if (!StringUtils.isNumeric(quantity.trim())) {
						throw new BusinessException("La cantidad debe ser numerica");
					}
					if (StringUtils.isBlank(nroRN)) {
						throw new BusinessException("Debe digitar el número RN");
					}
					/*
					 * release_correctiva_4.1.4_Validación para evitar la creación de remisiones con RN Existente
					 * 
					 * esta validacion se hace antes de insertar la REM por tanto no importa que se haga registro por registro del excel
					 * todos los registros con RN igual se considerarán parte de la misma REM
					 * 
					 */
					//lista de remisiones segun RN, menos las que se encuentren eliminadas
					List<ReferenceVO> referenceVOs = businessReference.getReferenceByRNNumber(nroRN,CodesBusinessEntityEnum.REFERENCE_STATUS_DELETED.getCodeEntity());
					if (referenceVOs.size()>0)
						throw new BusinessException("Archivo "+ this.getUploadFile().getName()+"-El número de RN "+nroRN+" ya se encuentra asociado a otra remisión");					
					
				}catch (Exception e) {
					errors.add(buildFileDetailProcess(dto.getRow(), e.getMessage()));
					iterator.remove();//eliminar el registro para que no sea procesado posteriormente
				}
			}

			//Ordenar la lista
			Collections.sort(fileData, new Comparator(){
				 
	            public int compare(Object o1, Object o2) {
	            	FileRecordDTO p1 = (FileRecordDTO) o1;
	            	FileRecordDTO p2 = (FileRecordDTO) o2;
	            	int compareSource =  p1.getRowData()[POS_CODE_WAREHOUSE_SOURCE].trim().compareToIgnoreCase(p2.getRowData()[POS_CODE_WAREHOUSE_SOURCE].trim());
	            	int compareTarget = p1.getRowData()[POS_CODE_WAREHOUSE_TARGET].trim().compareToIgnoreCase(p2.getRowData()[POS_CODE_WAREHOUSE_TARGET].trim()); 
	            	return (compareSource!=0?compareSource:compareTarget!=0?compareTarget:p1.getRowData()[POS_NRO_RN].trim().compareToIgnoreCase(p2.getRowData()[POS_NRO_RN].trim()));
	            }
	 
	        });
			
			for(FileRecordDTO f1: fileData){
				System.out.println(f1.getRow()+" "+ f1.getRowData()[POS_NRO_RN]+", "+f1.getRowData()[POS_CODE_WAREHOUSE_SOURCE]+f1.getRowData()[POS_CODE_WAREHOUSE_TARGET]);
			}
			
		}catch (Exception e) {
			log.error(e);
			errors.add( buildFileDetailProcess(0, e.getMessage()));			
			fileData.clear();//no se debe procesar ningún registro
		}
		return errors;
	}

	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processRecord(FileRecordDTO fileRecordDTO)
			throws BusinessException {
		
		log.debug("== Inicio processRecord/FileProcessorMassiveUpReferences ==");
		try{
			Double quantityRequired = 0D;
			String codeWarehouseSource = fileRecordDTO.getRowData()[POS_CODE_WAREHOUSE_SOURCE];
			String codeWarehouseTarget = fileRecordDTO.getRowData()[POS_CODE_WAREHOUSE_TARGET];
			String codeElementType = fileRecordDTO.getRowData()[POS_ELEMENT_TYPE_CODE];
			String quantity = fileRecordDTO.getRowData()[POS_QUANTITY];
			String isPrepaid = fileRecordDTO.getRowData()[POS_IS_PREPAID];
			String nroRN = fileRecordDTO.getRowData()[POS_NRO_RN];
			
			//Valido si es prepago
			if(isPrepaid!=null&&isPrepaid!=""){
				if(isPrepaid.trim().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_MODEL_IS_PREPAID.getCodeEntity())){
					isPrepaid= CodesBusinessEntityEnum.ELEMENT_MODEL_IS_PREPAID.getCodeEntity();
				}
			}else{
				isPrepaid= CodesBusinessEntityEnum.ELEMENT_MODEL_IS_NOT_PREPAID.getCodeEntity();
			}
			
			
			
			//Validar que existan las ubicaciones de origen y destino
			Warehouse warehouseSource = daoWarehouse.getWarehouseByCountryIdAndCodeAndNotVirtualAndNotCustomer(codeWarehouseSource, country.getId());
			if(warehouseSource==null){
				throw new BusinessException("La ubicación con código "+codeWarehouseSource+" no existe");
			}
			Warehouse warehouseTarget = daoWarehouse.getWarehouseByCountryIdAndCodeAndNotVirtualAndNotCustomer(codeWarehouseTarget, country.getId());
			if(warehouseTarget==null){
				throw new BusinessException("La ubicación con código "+codeWarehouseTarget+" no existe");
			}
			
			//Validar que el tipo de elemento exista
			ElementType elementType = daoElementType.getElementTypeByCode(codeElementType);
			if(elementType==null){
				throw new BusinessException("El tipo de elemento con código "+codeElementType+" no existe");
			}
			
			//Validar cantidad
			if(!NumberUtils.isNumber(quantity)){
				throw new BusinessException("La cantidad debe ser numerica");
			}else{
				quantityRequired = Double.parseDouble(quantity);
			}
			
			List<ReferenceVO> referenceVOs = new ArrayList<ReferenceVO>();
			
			//Validar si es la misma remisión
			if(reference==null){
				/*
				 * release_correctiva_4.1.4_Validación para evitar la creación de remisiones con RN Existente
				 * 
				 * esta validacion se hace antes de insertar la REM por tanto no importa que se haga registro por registro del excel
				 * todos los registros con RN igual se considerarán parte de la misma REM
				 * 
				 */
				 referenceVOs = businessReference.getReferenceByRNNumber(nroRN,CodesBusinessEntityEnum.REFERENCE_STATUS_DELETED.getCodeEntity());
				 if (referenceVOs.size()>0){
					    referenceVOs.clear();
						throw new BusinessException( "-El número de RN "+nroRN+" ya se encuentra asociado a otra remisión");
				  }
				
				  reference = createReference(warehouseSource, warehouseTarget, isPrepaid, nroRN);
				  saveRefQuantityControlItem(elementType, quantityRequired, reference, true);
				  
			}else{
				//Evaluo si es de la misma remisión
				boolean isSameReference = false;
				if(       reference.getWarehouseBySourceWh().getId().longValue()==warehouseSource.getId()
						&&reference.getWarehouseByTargetWh().getId().longValue()==warehouseTarget.getId()
						&&reference.getRnNumber().equalsIgnoreCase(nroRN)){
					isSameReference = true;
				}else{
					isSameReference = false;
				}
				if(isSameReference){
					if(reference.getIsPrepaidRef().equalsIgnoreCase(isPrepaid)){
						saveRefQuantityControlItem(elementType, quantityRequired, reference, false);
					}else{
						throw new BusinessException("El modelo del elemento que desea agregar al control de cantidades debe ser igual al de la remisión (Prepago o postpago)");
					}
				}else{
					/*
					 * release_correctiva_4.1.4_Validación para evitar la creación de remisiones con RN Existente
					 * 
					 * esta validacion se hace antes de insertar la REM por tanto no importa que se haga registro por registro del excel
					 * todos los registros con RN igual se considerarán parte de la misma REM
					 * 
					 */
					 referenceVOs = businessReference.getReferenceByRNNumber(nroRN,CodesBusinessEntityEnum.REFERENCE_STATUS_DELETED.getCodeEntity());
					 if (referenceVOs.size()>0){
						    referenceVOs.clear();
							throw new BusinessException( "-El número de RN "+nroRN+" ya se encuentra asociado a otra remisión");
					  }
						
					 reference = createReference(warehouseSource, warehouseTarget, isPrepaid, nroRN);
					 saveRefQuantityControlItem(elementType, quantityRequired, reference, true);
				}
			}
			if (reference != null)
				fileRecordDTO.addParam("REF_CREATED", reference.getId());
			else
				fileRecordDTO.addParam("REF_CREATED", null);
			
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación processRecord/FileProcessorAddNoSerializedElementsToImportLog ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina processRecord/FileProcessorMassiveUpReferences ==");
		}
	}

	@Override
	public boolean validateFile() throws BusinessException {
		return true;
	}
	
	
	

}
