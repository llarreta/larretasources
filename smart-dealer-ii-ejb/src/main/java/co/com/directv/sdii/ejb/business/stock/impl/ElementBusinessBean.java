package co.com.directv.sdii.ejb.business.stock.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.NotSerializedBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.BuildingElementHistoryVO;
import co.com.directv.sdii.model.dto.CustomerElementsDTO;
import co.com.directv.sdii.model.dto.ElementSerializedLinkUnLinkFilterVO;
import co.com.directv.sdii.model.dto.ElementSerializedLinkUnLinkVO;
import co.com.directv.sdii.model.dto.ElementToLinkVO;
import co.com.directv.sdii.model.dto.LinkSerializedFilterVO;
import co.com.directv.sdii.model.dto.LinkSerializedVO;
import co.com.directv.sdii.model.dto.NotSerializedAjustmentVO;
import co.com.directv.sdii.model.dto.QAItemDTO;
import co.com.directv.sdii.model.dto.UpdateLinkedSerialsDTO;
import co.com.directv.sdii.model.dto.collection.CustomerElementsResponse;
import co.com.directv.sdii.model.dto.collection.NotSerializedAjustmentCollDTO;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.Element;
import co.com.directv.sdii.model.pojo.ElementStatus;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.pojo.ImportLogItem;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.MovementType;
import co.com.directv.sdii.model.pojo.NotSerPartialRetirement;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.collection.BuildingElementHistoryResponse;
import co.com.directv.sdii.model.pojo.collection.ElementResponse;
import co.com.directv.sdii.model.pojo.collection.ImportLogItemResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementResponse;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ElementModelVO;
import co.com.directv.sdii.model.vo.ElementStatusVO;
import co.com.directv.sdii.model.vo.ElementTypeVO;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.model.vo.NotSerPartialRetirementVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementClassDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImportLogItemDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.NotSerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.RecordStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;


/**
 * EJB que implementa las operaciones Tipo CRUD Element
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal
 */
/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 7/09/2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="ElementBusinessBeanLocal",mappedName="ejb/ElementBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ElementBusinessBean extends BusinessBase implements ElementBusinessBeanLocal {

    @EJB(name="ElementDAOLocal", beanInterface=ElementDAOLocal.class)
    private ElementDAOLocal daoElement;
    
    @EJB(name="ElementClassDAOLocal", beanInterface=ElementClassDAOLocal.class)
    private ElementClassDAOLocal daoElementClass;
    
    @EJB(name="ElementTypeDAOLocal", beanInterface=ElementTypeDAOLocal.class)
    private ElementTypeDAOLocal daoElementType;
    
    @EJB(name="NotSerializedBusinessBeanLocal", beanInterface=NotSerializedBusinessBeanLocal.class)
    private NotSerializedBusinessBeanLocal businessNotSerialized;
    
    @EJB(name="SerializedBusinessBeanLocal", beanInterface=SerializedBusinessBeanLocal.class)
    private SerializedBusinessBeanLocal businessSerialized;
    
	@EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal warehouseElementDAO;
	
	@EJB(name="ImportLogDAOLocal", beanInterface=ImportLogDAOLocal.class)
	private ImportLogDAOLocal importLogDAO;
	
	@EJB(name="WarehouseDAOLocal", beanInterface=WarehouseDAOLocal.class)
	private WarehouseDAOLocal warehouseDAO;
	
	@EJB(name="WarehouseBusinessBeanLocal", beanInterface=WarehouseBusinessBeanLocal.class)
	private WarehouseBusinessBeanLocal warehouseBusinessBean;
	
	@EJB(name="NotSerPartialRetirementDAOLocal", beanInterface=NotSerPartialRetirementDAOLocal.class)
	private NotSerPartialRetirementDAOLocal notSerPartialRetirementDAO;
	
	@EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
	private SerializedDAOLocal daoSerialized;
	
	@EJB(name="WarehouseElementBusinessBeanLocal", beanInterface=WarehouseElementBusinessBeanLocal.class)
	private WarehouseElementBusinessBeanLocal warehouseElementBusiness;
	
	@EJB(name="NotSerializedDAOLocal", beanInterface=NotSerializedDAOLocal.class)
    private NotSerializedDAOLocal daoNotSerialized;
	
	@EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
	private DealersDAOLocal daoDealers;
	
	@EJB(name="RecordStatusDAOLocal", beanInterface=RecordStatusDAOLocal.class)
	private RecordStatusDAOLocal daoRecordStatus;
	
	@EJB(name="MovementTypeDAOLocal", beanInterface=MovementTypeDAOLocal.class)
	private MovementTypeDAOLocal daoMovementType;
	
	@EJB(name="ImportLogItemDAOLocal", beanInterface=ImportLogItemDAOLocal.class)
    private ImportLogItemDAOLocal daoImportLogItem;
	
	@EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
    private WarehouseElementDAOLocal daoWarehouseElement;
	
	@EJB(name="WorkordeerDAOLocal",beanInterface=WorkOrderDAOLocal.class)
	private WorkOrderDAOLocal daoWorkOrder;
	
    private final static Logger LOG = UtilsBusiness.getLog4J(ElementBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal#getAllElements()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ElementVO> getAllElements(Long countryId) throws BusinessException {
        LOG.info("== Inicia getAllElements/ElementBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoElement.getAllElements(countryId), ElementVO.class);

        } catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación getAllElements/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina getAllElements/ElementBusinessBean ==");
        }
    }
    
		
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal#registerSerializedElementQualityControlAndByCountry(co.com.directv.sdii.model.vo.ImportLogVO, co.com.directv.sdii.model.vo.DealerVO, java.util.List, co.com.directv.sdii.model.vo.UserVO, java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void registerSerializedElementQualityControlAndByCountry(ImportLogVO importLog, DealerVO logisticOperator, List<QAItemDTO> qaItemDTOs,UserVO user,Long country) throws BusinessException{
    	LOG.info("== Inicia registerSerializedElementQualityControlAndByCountry/ElementBusinessBean ==");
    	try{
    		//Validacion 1
    		UtilsBusiness.validateParameters(importLog,importLog.getId(),logisticOperator,logisticOperator.getId(),qaItemDTOs);
    		
    		//logisticOperator.getDealerType(),logisticOperator.getDealerType().getDealerTypeCode()
    		Dealer dealer = daoDealers.getDealerByID(logisticOperator.getId());
    		UtilsBusiness.validateParameters(dealer);
    		if(!dealer.getDealerType().getDealerTypeCode().equals(CodesBusinessEntityEnum.CODE_DEALER_TYPE_LOGISTIC_OPERATOR.getCodeEntity())){
    			LOG.debug("El dealer no es un operador logistico");
    			throw new BusinessException(ErrorBusinessMessages.DEALER_ISNT_LOGISTIC_OPERATOR.getCode(),ErrorBusinessMessages.DEALER_ISNT_LOGISTIC_OPERATOR.getMessage());
    		}
    		
    		ImportLog importLogPojo = importLogDAO.getImportLogByIDAndByLogisticOp(importLog.getId(),importLog.getDealer().getId());

    		UtilsBusiness.validateParameters(importLogPojo);
    		
			UtilsBusiness.assertNotNull(importLogPojo.getImportLogStatus(), ErrorBusinessMessages.IMPORT_LOG_STATUS_IS_NOT_VALID.getCode(), ErrorBusinessMessages.IMPORT_LOG_STATUS_IS_NOT_VALID.getMessage());
			
			final String statusCode = importLogPojo.getImportLogStatus().getStatusCode();
			
			//El Registro de Importación existe (Entrada 1) y se encuentra en estado "Enviado" o "Confirmado parcial" ?
			if(!CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity().equals(statusCode) 
			   && !CodesBusinessEntityEnum.IMPORT_LOG_STATUS_RECEIVED.getCodeEntity().equals(statusCode)){
				throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_STATUS_IS_NOT_VALID.getCode(),ErrorBusinessMessages.IMPORT_LOG_STATUS_IS_NOT_VALID.getMessage());
			}
			
			List<Serialized> okElements = new ArrayList<Serialized>(); //elementos que han sido marcados con estado OK  
			List<Serialized> damagedElements = new ArrayList<Serialized>();//elementos que han sido marcados con estado defectuoso
			List<Serialized> serializedElements = daoSerialized.getSerializedElementsByImportLogId(importLogPojo.getId());
			for (QAItemDTO item : qaItemDTOs) { //validacion de que el contenido del archivo plano contenga informacion del elemento correcta
	    		if(serializedElements != null ){
	    			elementsCycle:for (Serialized serialized : serializedElements){
	    				//Valido que el elemento enviado en el archivo este dentro de los elementos del registro de importacion
	    				if(serialized.getSerialCode()!=null && item!=null && serialized.getSerialCode().equals(item.getElementSerial()) 
								&& serialized.getElement().getElementType().getTypeElementCode().equals(item.getElementTypeCode())){
	    					if(item.getElementStatus().toString().equals("1")){
								okElements.add(serialized);
							}else if(item.getElementStatus().toString().equals("0")){
								damagedElements.add(serialized);
							}
	    					break elementsCycle;
	    				}
	    			}
	    		}
			}
	    	if(okElements.size() + damagedElements.size() < qaItemDTOs.size()){
    			LOG.error("Un elemento del archivo no se encuentra dentro del registro de importacion");
    			throw new BusinessException(ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(),ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
    		}
	    	LOG.debug("Validacion del archivo plano y del registro de importacion correcto");
	    	//Fin V1
	    	
	    	//Se consulta la bodega de calidad para obtener los elementos a los cuales se les esta realizando el control de calidad y moverlos
	    	//de esta bodega a disponibilidades o a devoluciones
	    	Warehouse logisticOperatorQtyCtrlWh = getLogisticOperatorWarehouse(logisticOperator.getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_CALIDAD.getCodeEntity());
	    	
	    	if(logisticOperatorQtyCtrlWh == null){
	    		LOG.error("El dealer no tiene asociado bodega de transito operador logistico");
	    		throw new BusinessException(ErrorBusinessMessages.DEALER_DONT_HAVE_LOGISITIC_OPERATOR_WH.getCode(),ErrorBusinessMessages.DEALER_DONT_HAVE_DEVOLUTION_WH.getMessage());
	    	}
	    	
	    	//Se le pone el nuevo estado a los elementos OK, se mueven de bodega y se informa a IBS
	    	List<WarehouseElementVO> disponible = new ArrayList<WarehouseElementVO>();
	    	ElementStatus statusOk = new ElementStatus();
	    	statusOk.setId(Integer.valueOf(CodesBusinessEntityEnum.ELEMENT_STATUS_S04.getCodeEntity()).longValue());
	    	for (Serialized serialized : okElements) {
	    		serialized.getElement().setElementStatus(statusOk);
	    		daoElement.updateElement(serialized.getElement());
	    		WarehouseElementVO element = new WarehouseElementVO();
	    		element.setWarehouseId(logisticOperatorQtyCtrlWh);
	    		element.setSerialized(serialized);
	    		disponible.add(element);
			}
	    	
	    	//Se le pone el nuevo estado a los elementos defectuosos, se mueven de bodega y se informa a IBS
	    	List<WarehouseElementVO> devolucion = new ArrayList<WarehouseElementVO>();
	    	ElementStatus statusDamaged = new ElementStatus();
	    	statusDamaged.setId(Integer.valueOf(CodesBusinessEntityEnum.ELEMENT_STATUS_DUMMY.getCodeEntity()).longValue());
	    	for (Serialized serialized : damagedElements) {
	    		serialized.getElement().setElementStatus(statusDamaged);
	    		daoElement.updateElement(serialized.getElement());
	    		WarehouseElementVO element = new WarehouseElementVO();
	    		element.setWarehouseId(logisticOperatorQtyCtrlWh);
	    		element.setSerialized(serialized);
	    		devolucion.add(element);
			}
	    	
	    	/*
	    	 * El llamado al CU 14 y CU 08 se hace en el paso anterior cuando realizo el control de calidad y ese CU hace llamado a esos
	    	 * CU
	    	 * */
	    	Country userCountry = new Country();
	    	userCountry.setId(country);
	    	user.setCountry(userCountry);
	    	
        } catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación getElementByID/ElementBusinessBean ==");
        	// Se captura el codigo "NOT_ELEMENT_EXIST" ya que el CU de movimiento de serializados lanza esa excepcion en caso 
        	// de no encontrar el elemento en la bodega definida
        	if (ex instanceof  BusinessException){			
    			if(((BusinessException) ex).getMessageCode().equalsIgnoreCase(ErrorBusinessMessages.NOT_ELEMENTS_EXIST.getCode())){
    				LOG.debug("== La bodega de transito del operador logitico no tiene elementos para el control de calidad ==");
    				throw new BusinessException(ErrorBusinessMessages.ELEMENT_DOESNT_EXIST_ON_LOGISTIC_OPERATOR_WH.getCode() , ErrorBusinessMessages.ELEMENT_DOESNT_EXIST_ON_LOGISTIC_OPERATOR_WH.getMessage());
    			}
    		}
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina getElementByID/ElementBusinessBean ==");
        }
    	
    }
    
    /**
     * @param logisticOperatorDealerId
     * @param whTypeCode
     * @return
     * @throws BusinessException
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @throws PropertiesException
     */
    public Warehouse getLogisticOperatorWarehouse(
			Long logisticOperatorDealerId, String whTypeCode) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
		Dealer logOperDealer = daoDealers.getDealerByID(logisticOperatorDealerId);
		UtilsBusiness.assertNotNull(logOperDealer, ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getMessage());
		
		//Si el tipo de dealer no es operador logístico:
		if(!CodesBusinessEntityEnum.CODE_DEALER_TYPE_LOGISTIC_OPERATOR.getCodeEntity().equalsIgnoreCase(logOperDealer.getDealerType().getDealerTypeCode())){
			LOG.error("El dealer especificador no es operador logístico");
			throw new BusinessException(ErrorBusinessMessages.DEALER_ISNT_LOGISTIC_OPERATOR.getCode(), ErrorBusinessMessages.DEALER_ISNT_LOGISTIC_OPERATOR.getMessage());
		}
		
		Warehouse whs = warehouseDAO.getWarehousesByDealerIdAndWhTypeCode(logOperDealer.getId(), whTypeCode);
		if(whs == null){
			LOG.error("El dealer operador logístico con id: \""+logOperDealer.getId()+"\" no tiene bodegas del código tipo: \""+whTypeCode+"\"");
			throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
		}
		return whs;
	}

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal#getElementsHistoryOnBuildingCode(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public CustomerElementsResponse getElementsHistoryOnBuildingCode(String ibsCode,Long countryId, RequestCollectionInfo requestCollInfo) throws BusinessException{
    	LOG.info("== Inicia getElementsHistoryOnBuildingCode/ElementBusinessBean ==");
    	try{
    		UtilsBusiness.validateParameters(ibsCode);
    		CustomerElementsResponse customerElementsResponse = new CustomerElementsResponse();
    		BuildingElementHistoryResponse responseSer = daoElement.getElementsHistoryOnBuildingCode(ibsCode,countryId, requestCollInfo);
    		
    		if(responseSer != null){
				customerElementsResponse.setPageCount(responseSer.getPageCount());
				customerElementsResponse.setPageIndex(responseSer.getPageIndex());
				customerElementsResponse.setPageSize(responseSer.getPageSize());
				customerElementsResponse.setRowCount(responseSer.getRowCount());
				customerElementsResponse.setTotalRowCount(responseSer.getTotalRowCount());
			}
    		customerElementsResponse.setCustomerElementDTO(fillHistoryElementOfCustomerByBuildingCode(responseSer.getBuildingElements()));
    		return customerElementsResponse;
        } catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación getElementsHistoryOnBuildingCode/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina getElementsHistoryOnBuildingCode/ElementBusinessBean ==");
        }
    }
    
	/**
	 * Metodo: devuelve los elementos historicos de un edificio
	 * @param List<Object[]> Lista de WorkOrder, WoUsedElement y WarehouseElement 
	 * @return
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws PropertiesException 
	 * @author cduarte
	 */
	private List<CustomerElementsDTO> fillHistoryElementOfCustomerByBuildingCode(
			List<Object[]> response) throws BusinessException,
			DAOServiceException, DAOSQLException, IllegalAccessException,
			PropertiesException {

			// Declaracion de atributos locales
			List<CustomerElementsDTO> historyElementsOfCustomerDTO = null;
			WarehouseElement warehouseElement = null;
			WorkOrder workOrder = null;
			Serialized serialized = null;
	
			/**
			 * ID IBS Cliente
			 */
			String customerCode;// Cliente
	
			/**
			 * ID IBS Edificio
			 */
			String buildingCode;// Edificio
	
			/**
			 * Ciudad del IBS
			 */
			String postalCodeCity;// Cliente
	
			/**
			 * Dirección del IBS
			 */
			String woAddress;// Cliente
	
			/**
			 * Numero de WorkOrder
			 */
			String woCode;// WorkOrder
	
			/**
			 * Tipo de elemento
			 */
			String typeElement;// ElementType
	
			/**
			 * Unidad de medida del elemento
			 */
			String unitName;// Element
	
			/**
			 * Lote Elementos
			 */
			String lote;// Element
	
			/**
			 * Cantidad actual Elementos en Bodega
			 */
			Double actualQuantity;// WarehouseElement
	
			/**
			 * Modelo Elementos
			 */
			String modelName;// ElementModel
	
			/**
			 * Serial del elemento
			 */
			String serialCode;// Elementos
	
			/**
			 * RID
			 */
			String rid;// Elementos
	
			/**
			 * Serial del elemento vinculado
			 */
			String serialCodeLinked;// Element
	
			/**
			 * Fecha de ingreso del elemento
			 */
			Date registrationDateIn;// Elementos en Bodega
	
			/**
			 * Fecha de salida del elemento
			 */
			Date registrationDateOut;// Elementos en Bodega
			
			String movementCause;
			
			//Tipo numero documento
			String typeDocument="";
			
			// Loop para los elementos en una bodega
			for (Object vo : response) {
	
				warehouseElement = (WarehouseElement) vo;
	
				// Se crea la primera ves el DTO historyElementsOfCustomer
				if (historyElementsOfCustomerDTO == null)
					historyElementsOfCustomerDTO = new ArrayList<CustomerElementsDTO>();
	
				customerCode = "";
				buildingCode = "";
				postalCodeCity = "";
				woAddress = "";
				woCode = "";
				typeElement = "";
				unitName = "";
				lote = "";
				actualQuantity = 0d;
				modelName = "";
				serialCode = "";
				rid = "";
				serialCodeLinked = "";
				registrationDateIn = null;
				registrationDateOut = null;
				movementCause="";
	
				customerCode = warehouseElement.getWarehouseId().getCustomerId().getCustomerCode();
				
				// Si es un elemento Serializado
				if (warehouseElement.getSerialized() == null) {
	
					typeElement = warehouseElement.getElementType() == null ? ""
							: warehouseElement.getElementType().getTypeElementCode() + " - " + warehouseElement.getElementType().getTypeElementName();
					
					unitName = warehouseElement.getElementType() == null ? ""
							: warehouseElement.getElementType().getMeasureUnit() == null ? ""
									: warehouseElement.getElementType()
											.getMeasureUnit().getUnitName();
					lote = warehouseElement.getNotSerialized() == null ? ""
							: warehouseElement.getNotSerialized().getElement() == null ? ""
									: warehouseElement.getNotSerialized()
											.getElement().getLote();
	
					// Si es un elemento no serializado
				} else {
	
					typeElement = warehouseElement.getElementType() == null ? ""
									: warehouseElement.getElementType().getTypeElementCode() + " - " + warehouseElement.getElementType().getTypeElementName();
	
					serialized = warehouseElement.getSerialized();
					if (serialized != null) {
	
						serialCode = serialized.getSerialCode();
						rid = serialized.getIrd();
						serialCodeLinked = serialized.getSerialized() == null ? ""
								: serialized.getSerialized().getSerialCode();
	
					}
	
					modelName = warehouseElement.getElementType() == null ? ""
							: warehouseElement.getElementType().getElementModel() == null ? ""
									: warehouseElement.getElementType().getElementModel().getModelCode() + " - " + warehouseElement.getElementType().getElementModel().getModelName();
	
				}
	
				if(warehouseElement.getMovementType() != null)
					if(warehouseElement.getMovementType().getMovClass().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_TYPE_EXIT.getCodeEntity()) ) {	
						registrationDateOut=warehouseElement.getMovementDate();
						if(warehouseElement.getMovedQuantity().doubleValue()!=0)
							actualQuantity = -1 * warehouseElement.getMovedQuantity();//si el tipo de movimiento es de salida, la cantidad es negativa
					} else {
						registrationDateIn=warehouseElement.getMovementDate();
						actualQuantity = warehouseElement.getMovedQuantity();
					}
				
				if(warehouseElement != null && warehouseElement.getMovementType() != null) {
					movementCause = warehouseElement.getMovementType().getMovTypeName();
				}
				
				typeDocument= "";
				if(warehouseElement.getDocumentClass() != null){
					if(warehouseElement.getDocumentClass().getDocumentClassCode().equals(CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity()))
					{	
						workOrder = daoWorkOrder.getWorkOrderByID(warehouseElement.getDocumentId());
						if (workOrder != null) {
							
							buildingCode = workOrder.getBuildingCode();
							postalCodeCity = workOrder.getPostalCode().getPostalCodeCode() + " - " + workOrder.getPostalCode().getPostalCodeName();
							woAddress = workOrder.getWoAddress();
							woCode = workOrder.getWoCode();
			
						}
							
					}
					
					typeDocument= warehouseElement.getDocumentClass().getDocumentClassName();
					if(!woCode.equals("")){
						typeDocument= typeDocument + " - " +woCode;
					}else if(warehouseElement.getDocumentId() != null){
						typeDocument= typeDocument + " - " + warehouseElement.getDocumentId();
					}
				}
				
				if(woAddress.equals("")){
					if(warehouseElement.getWarehouseId() != null && warehouseElement.getWarehouseId().getCustomerId() != null){
						woAddress = warehouseElement.getWarehouseId().getCustomerId().getCustomeraddress();
					}
				}
				if(postalCodeCity.equals(""))
					if(warehouseElement.getWarehouseId() != null) 
						if(warehouseElement.getWarehouseId().getCustomerId() != null){
							if(warehouseElement.getWarehouseId().getCustomerId().getPostalCode() != null)
								postalCodeCity=warehouseElement.getWarehouseId().getCustomerId().getPostalCode().getPostalCodeCode()+ " - " +warehouseElement.getWarehouseId().getCustomerId().getPostalCode().getPostalCodeName();
						
				}
	
				CustomerElementsDTO historyElementOfCustomerDTO = new CustomerElementsDTO(
						customerCode, buildingCode, postalCodeCity, woAddress,
						woCode, typeElement, unitName, lote, actualQuantity,
						modelName, serialCode, rid, serialCodeLinked,
						registrationDateIn, registrationDateOut, movementCause,typeDocument);
				
				// Se ingresa El elemento historico en la bodega cliente
				historyElementsOfCustomerDTO.add(historyElementOfCustomerDTO);
	
			}
	
			// devuelve los elementos historicos en la bodega cliente
			return historyElementsOfCustomerDTO;

	}
    
    /**
     * Metodo: Permite eliminar la informacion innecesaria para la respuesta de consulta de historial de elementos por codigo de edificio
     * @param response List<BuildingElementHistoryVO> Lista en la que se va a elminar la informacion innecesaria
     * @throws BusinessException En caso de error en el momento de eliminar la informaicon
     */
    public void deleteUnnecessaryInformationGetElementsHistoryOnBuilding(List<BuildingElementHistoryVO> response) throws BusinessException{
    	LOG.info("== Inicia deleteUnnecessaryInformationGetElementsHistoryOnBuilding/ElementBusinessBean ==");
    	try{
    		for(BuildingElementHistoryVO vo : response){
    			if(vo.getSerialized() != null){
    				vo.getSerialized().setElement(null);
    			} else if(vo.getNotSerialized() != null){
    				vo.getNotSerialized().setElement(null);
    			}
    		}
    	}catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación deleteUnnecessaryInformationGetElementsHistoryOnBuilding/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina deleteUnnecessaryInformationGetElementsHistoryOnBuilding/ElementBusinessBean ==");
        }
    }    
 
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal#getElementsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ElementVO getElementByID(Long id) throws BusinessException {
        LOG.info("== Inicia getElementByID/ElementBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Element objPojo = daoElement.getElementByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ElementVO.class, objPojo);
        } catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación getElementByID/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina getElementByID/ElementBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal#createElement(co.com.directv.sdii.model.vo.ElementVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createElement(ElementVO obj) throws BusinessException {
        LOG.info("== Inicia createElement/ElementBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		LOG.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
            Element objPojo =  UtilsBusiness.copyObject(Element.class, obj);
            daoElement.createElement(objPojo);
        } catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación createElement/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina createElement/ElementBusinessBean ==");
        }
		
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal#createNotSerializedElement(co.com.directv.sdii.model.vo.ElementVO, co.com.directv.sdii.model.vo.NotSerializedVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createNotSerializedElement(ElementVO element, NotSerializedVO noSerializado, Long impLogID)
			throws BusinessException {
		LOG.info("== Inicia createNotSerializedElement/ElementBusinessBean ==");
		UtilsBusiness.assertNotNull(element, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	    UtilsBusiness.assertNotNull(noSerializado, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
			//Valida que la cantidad no sea inferior a 0
        	/*if (noSerializado.getInitialQuantity()!= null && noSerializado.getInitialQuantity() < 1 ){
        		throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_ITEM_QUANTITY_INVALID.getCode(),ErrorBusinessMessages.IMPORT_LOG_ITEM_QUANTITY_INVALID.getMessage());
        	}*/
        		
        	if(! BusinessRuleValidationManager.getInstance().isValid(element) ||! BusinessRuleValidationManager.getInstance().isValid(noSerializado)){
        		LOG.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	Element elementPojo =  UtilsBusiness.copyObject(Element.class, element);
        	//Valida que el tipo de elemento no este en el registro de importacion.
        	if(impLogID!=null){
	    		if (isElementTypeInImpLog(elementPojo.getElementType().getId(), impLogID)){
	    			throw new BusinessException(ErrorBusinessMessages.STOCK_IN428.getCode(),ErrorBusinessMessages.STOCK_IN428.getMessage());
	    		}
        	}
    		
            elementPojo.setIsSerialized(CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity());
            
            //Validar si el elemento no serializado ya existe creado en el sistema
            ElementType elementTypePojo = daoElementType.getElementTypeByID(elementPojo.getElementType().getId());
            Element searchElement = daoElement.getElementsByElementTypeCodeAndIsSerialized(elementTypePojo.getTypeElementCode(),false,element.getCountry().getId());
            if(searchElement==null){
            	daoElement.createElement(elementPojo);
            	noSerializado.setRegistrationDate(Calendar.getInstance().getTime());
                noSerializado.setElementId(elementPojo.getId());
                noSerializado.setElement(elementPojo);
                this.businessNotSerialized.createNotSerialized(noSerializado);
            }else{
            	elementPojo = searchElement;
            }
            element.setId(elementPojo.getId());
            
        } catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación createNotSerializedElement/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina createNotSerializedElement/ElementBusinessBean ==");
        }
		
	}
	
	/**
	 * 
	 * Metodo: Valida que los tipos de elementos en un registro de importacion no se repitan.
	 * Si se repiten en el registro de importación, genera error en la confirmación de elementos, ya que la lógica de los
	 * movimientos entre bodegas se hace es por tipo y no por id del elemento. 
	 * @param elemType
	 * @param impLogId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
   private Boolean isElementTypeInImpLog(Long elemType, Long impLogId) throws DAOServiceException, DAOSQLException{
		Boolean isElementTypeInImpLog = false;
		
		ImportLogItemResponse ilir = daoImportLogItem.getImportLogItemsByImportLog(impLogId, null);
		List<ImportLogItem> ilts = ilir.getImportLogItems();
		for (ImportLogItem ili: ilts){
			if (ili.getElement().getElementType().getId().longValue() == elemType.longValue()){
				isElementTypeInImpLog = true;
				break;
			}
		}
		
		return isElementTypeInImpLog;
		
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal#createSerializedElement(co.com.directv.sdii.model.vo.ElementVO, co.com.directv.sdii.model.vo.SerializedVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createSerializedElement(ElementVO element,SerializedVO serializado)
			throws BusinessException {
		LOG.info("== Inicia createSerializedElement/ElementBusinessBean ==");
		UtilsBusiness.assertNotNull(element, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	    UtilsBusiness.assertNotNull(serializado, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	if(! BusinessRuleValidationManager.getInstance().isValid(element)){
        		LOG.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	if( ( serializado.getSerialCode() == null || serializado.getSerialCode().equals("") ) && ( serializado.getIrd() == null || serializado.getIrd().equals("") ) ){
        		LOG.error("El elemento que se desea crear no tiene ni serial ni IRD");
        		throw new BusinessException(ErrorBusinessMessages.INVENTORY_SERIAL_RID_REQUIRED.getCode(), ErrorBusinessMessages.INVENTORY_SERIAL_RID_REQUIRED.getMessage());
        	}
        	if (!isValidSerial(element, serializado)){
        		LOG.error("Error de validación, el codigo serial no cumple con el formato");
        		Object[] obj = {serializado.getSerialCode()};
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN355.getCode(), ErrorBusinessMessages.STOCK_IN355.getMessage(obj), UtilsBusiness.getParametersWS(obj));
        	}
        	Serialized serTmp = null;
        	if( serializado.getSerialCode() != null ){
        		serTmp =   this.daoSerialized.getSerializedBySerial(serializado.getSerialCode(),element.getCountry().getId());
        		if(serTmp!=null){
            		Object params[] = {serializado.getSerialCode()};
    				throw new BusinessException(ErrorBusinessMessages.STOCK_IN417.getCode(),ErrorBusinessMessages.STOCK_IN417.getMessage(params), UtilsBusiness.getParametersWS(params));
            	}
        	} else if ( serializado.getIrd() != null ){
        		serTmp =   this.daoSerialized.getSerializedByIRD(serializado.getIrd());
        	} 
        	if(serTmp!=null && serTmp.getElement().getElementType().getId().equals(element.getElementType().getId())){
        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
        	
        	
        	Element elementPojo =  UtilsBusiness.copyObject(Element.class, element);
        	elementPojo.setIsSerialized(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity());
        	
            daoElement.createElement(elementPojo);
            serializado.setRegistrationDate(Calendar.getInstance().getTime());
            serializado.setElementId(elementPojo.getId());
            serializado.setElement(elementPojo);
            if(! BusinessRuleValidationManager.getInstance().isValid(serializado)){
            	LOG.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            this.businessSerialized.createSerialized(serializado);
            element.setId(elementPojo.getId());
        } catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación createSerializedElement/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina createSerializedElement/ElementBusinessBean ==");
        }
		
	}
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLogItem createSerializedLinkedElement(Serialized vinculado, ImportLog objPojo, ItemStatus itemStatus)
			throws BusinessException {
		LOG.info("== Inicia createSerializedLinkedElement/ElementBusinessBean ==");
		UtilsBusiness.assertNotNull(vinculado, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {        	
        	Element elementPojo =  new Element();
        	elementPojo.setIsSerialized(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity());
        	ElementStatus elementStatus = new ElementStatus();
        	elementStatus.setId(CodesBusinessEntityEnum.ELEMENT_STATUS_T01.getIdEntity(ElementStatus.class.getName()));
	    	elementPojo.setElementStatus(elementStatus);
	    	elementPojo.setCountry(objPojo.getCountry());
	    	ElementType elementType = daoElementType.getElementTypeByCode(vinculado.getElement().getElementType().getTypeElementCode());
	    	elementPojo.setElementType(elementType);
	    	daoElement.createElement(elementPojo);
	    	vinculado.setRegistrationDate(Calendar.getInstance().getTime());
	    	vinculado.setElementId(elementPojo.getId());
	    	vinculado.setElement(elementPojo);
	    	daoSerialized.createSerialized(vinculado);
	    	ImportLogItem importLogItem = new ImportLogItem();
            importLogItem.setMeasureUnit(vinculado.getElement().getElementType().getMeasureUnit());
			importLogItem.setElement(elementPojo);
			importLogItem.setImportLog(objPojo);
			importLogItem.setItemStatus(itemStatus);
			importLogItem.setAmountExpected(1d);
			daoImportLogItem.createImportLogItem(importLogItem);
			return importLogItem;
        } catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación createSerializedLinkedElement/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina createSerializedLinkedElement/ElementBusinessBean ==");
        }
		
	}
	
	/*
	 * validar Expression Regular CasoUso Inv_01, entrada 2. Serial:
	 * - El serial es validado de acuerdo a la expresión regular definida para el Tipo de elemento
	 * Tabla: element_types --> campo type_regex
	 */
	private boolean isValidSerial(ElementVO element, SerializedVO serializado) throws BusinessException{
		boolean isValidSerial = false;
		try{	
			String expresionRegular = null;
			ElementType et = daoElementType.getElementTypeByID(element.getElementType().getId());
			if (et == null){
				Object[] params = new Object[1];			
				params[0] = element.getElementType().getId();
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN422.getCode(), ErrorBusinessMessages.STOCK_IN422.getMessage(params));
			}
			
			expresionRegular = et.getTypeRegEx();
			if (expresionRegular == null){
				isValidSerial = true;
			}else{
				isValidSerial = serializado.getSerialCode().matches(expresionRegular);
			}
		 } catch(Throwable ex){
			isValidSerial = true;
	     	LOG.debug("== Error al tratar de ejecutar la operación isValidSerial/ElementBusinessBean ==");
	     	throw this.manageException(ex);
	     } finally {
	         LOG.info("== Termina isValidSerial/ElementBusinessBean ==");
	     }
		
		return isValidSerial;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal#getElementsByElementType(co.com.directv.sdii.model.vo.ElementTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementResponse getElementsByElementType(ElementTypeVO elementType, RequestCollectionInfo requestCollInfo) throws BusinessException {
		LOG.info("== Inicia getElementsByElementType/ElementBusinessBean ==");
        UtilsBusiness.assertNotNull(elementType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        List<Element> elementList;
        try {
        	//Si el código de elementType no es nulo se consulta por código
        	//De lo contrario por ID del elementType
        	if(elementType.getTypeElementCode()!=null){
        		elementType =   UtilsBusiness.copyObject(ElementTypeVO.class, daoElementType.getElementTypeByCode(elementType.getTypeElementCode()));
        		if (elementType == null) {
                	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
                }
        	}else{
        		UtilsBusiness.assertNotNull(elementType.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	//Realiza la consulta por el ID del elementType
        	ElementResponse response = daoElement.getElementsByElementType(elementType.getId(), requestCollInfo);
        	elementList = response.getElements();
        	response.setElementsVO( UtilsBusiness.convertList(elementList, ElementVO.class) );
        	response.setElements(null);
            return response;
        } catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación getElementsByElementType/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina getElementsByElementType/ElementBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal#getWhElementsAndNotSerPartRetByWarehouseId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public NotSerializedAjustmentCollDTO getWhElementsAndNotSerPartRetByWarehouseId(Long warehouseId, RequestCollectionInfoDTO requestCollInfo)throws BusinessException{
		LOG.info("== Inicia getWhElementsAndNotSerPartRetByWarehouseId/ElementBusinessBean ==");
        UtilsBusiness.assertNotNull(warehouseId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        List<NotSerializedAjustmentVO> notSerializedAjustmentDTOList = new ArrayList<NotSerializedAjustmentVO>();
        try {
        	WareHouseElementResponse warehouseBusinessResponse = this.getWhElementsByCriteria(warehouseId, CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity(), null, requestCollInfo);
        	
        	//WhElementPaginationResponse whPaginationResp = warehouseElementDAO.getWhElByTargetWHIdExitDateNullAndLastRecordPaged(warehouseId, false, requestCollInfo);
        	List<WarehouseElementVO> elementList = UtilsBusiness.convertList(warehouseBusinessResponse.getWareHouseElementsVO(), WarehouseElementVO.class);
        	if(elementList != null && elementList.size() > 0){
        		for(WarehouseElementVO vo : elementList){
        			if(vo.getNotSerialized() != null){
        				NotSerializedAjustmentVO obj = new NotSerializedAjustmentVO();
        				vo.setInitialQuantity( vo.getNotSerialized().getInitialQuantity() );
        				obj.setElement(vo);
            			vo.setAjustmentQauntity(0D);
            			List<NotSerPartialRetirement> notSerPartPojo = notSerPartialRetirementDAO.getNotSerPartialRetirementByElementId(vo.getNotSerialized().getElementId());
            			obj.setElementPartialRetirement(UtilsBusiness.convertList(notSerPartPojo, NotSerPartialRetirementVO.class));
            			notSerializedAjustmentDTOList.add(obj);
        			}
        		}
        	}
        	NotSerializedAjustmentCollDTO result = buildNotSerializedAjustmentColl(notSerializedAjustmentDTOList, requestCollInfo, warehouseBusinessResponse.getTotalRowCount());
        	return result;
        } catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación getWhElementsAndNotSerPartRetByWarehouseId/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina getWhElementsAndNotSerPartRetByWarehouseId/ElementBusinessBean ==");
        }		
	}
	
	/**
	 * @param warehouseId
	 * @param isSerialized
	 * @param elementId
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 */
	private WareHouseElementResponse getWhElementsByCriteria(
			Long warehouseId, String isSerialized, Long elementId, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		LOG.info("== Inicia getWhElementsByCriteria/WarehouseElementBusinessBean ==");
		UtilsBusiness.assertNotNull(warehouseId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(isSerialized, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	WareHouseElementResponse response = this.warehouseElementDAO.getWhElementsByCriteriaAndGroupByElementIdAndWh(warehouseId, isSerialized, elementId, requestCollInfo);
        	List<Object[]> objectsResponse = response.getWarehouseElementObjects();
        	List<WarehouseElementVO> elementsList = null;
        	if( objectsResponse != null && !objectsResponse.isEmpty() ){
        		elementsList = new ArrayList<WarehouseElementVO>();
        		for( Object[] object : objectsResponse ){
        			WarehouseElementVO vo = new WarehouseElementVO();
        			if(isSerialized.compareTo(CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity()) == 0){
        				BigDecimal elementIdFromBd = (BigDecimal) object[0];
        				vo.setNotSerialized( this.daoNotSerialized.getNotSerializedByID( elementIdFromBd.longValue() ) );        				
        			}
        			if(isSerialized.compareTo(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity()) == 0){
        				BigDecimal elementIdFromBd = (BigDecimal) object[0];
        				List<Serialized> list = daoSerialized.getSerializedByElementId( elementIdFromBd.longValue() ) ;
        				if( list != null )
        					vo.setSerialized( list.get(0) );
        				BigDecimal id = (BigDecimal) object[5];
        				vo.setId( id.longValue() );
        			}
        			vo.setMovementDate( new Date( ((Timestamp)object[1]).getTime() ) );
        			BigDecimal quantity = (BigDecimal) object[2];
        			vo.setActualQuantity( quantity.doubleValue() );
        			BigDecimal targetWh = (BigDecimal) object[3];
        			BigDecimal recordStatus = (BigDecimal) object[4];
        			vo.setWarehouseId( warehouseDAO.getWarehouseByID(targetWh.longValue()) );
        			vo.setRecordStatus( daoRecordStatus.getRecordStatusByID( recordStatus.longValue() ) );
        			elementsList.add(vo);
        		}
        		response.setWareHouseElementsVO(elementsList);
        		response.setWareHouseElements(null);
        	}
        	if(response.getWareHouseElementsVO() != null && !response.getWareHouseElementsVO().isEmpty() ){
        		return  response;
        	} else{
        		LOG.debug("== Error al tratar de ejecutar la operación getWhElementsByCriteria/WarehouseElementBusinessBean ==");
				throw new BusinessException(ErrorBusinessMessages.NOT_ELEMENTS_EXIST.getCode(),ErrorBusinessMessages.NOT_ELEMENTS_EXIST.getMessage());
        	}        	        	
        } catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación getWhElementsByCriteria/WarehouseElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina getWhElementsByCriteria/WarehouseElementBusinessBean ==");
        }
	}
	
	/**
	 * Metodo: <Descripcion>
	 * @param notSerializedAjustmentDTOList
	 * @param requestCollInfo
	 * @return <tipo> <descripcion>
	 * @author jjimenezh
	 */
	private NotSerializedAjustmentCollDTO buildNotSerializedAjustmentColl(List<NotSerializedAjustmentVO> notSerializedAjustmentDTOList, RequestCollectionInfo requestCollInfo, int totalRecordCount) {
		NotSerializedAjustmentCollDTO result = new NotSerializedAjustmentCollDTO();
		int sizeColl = 0;
		if( notSerializedAjustmentDTOList != null && !notSerializedAjustmentDTOList.isEmpty() )
			sizeColl = notSerializedAjustmentDTOList.size();
		if( requestCollInfo != null )
			populatePaginationInfo(result, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), sizeColl, totalRecordCount);
		result.setCollection(notSerializedAjustmentDTOList);
		return result;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal#getElementsToLinkSerializedElements(co.com.directv.sdii.model.dto.LinkSerializedFilterVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public LinkSerializedVO getElementsToLinkSerializedElements(LinkSerializedFilterVO criteria) throws BusinessException{
		LOG.info("== Inicia getElementsToLinkSerializedElements/ElementBusinessBean ==");
		if(criteria.getWarehouseId() == null || criteria.getWarehouseId() <= 0 ){
			LOG.debug("El identificador de la bodega es obligatorio");
    		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		}
		try {
        	LinkSerializedVO response = new LinkSerializedVO();
        	//Se buscan los elementos del elemento 1
        	response.setElementOneList(getElementsToLinksHelper(daoElement.getSerializedElementsByElementTypeCodeAndSerial(criteria.getElementOneCode(), criteria.getElementOneSerial(),criteria.getWarehouseId())));
        	//Se buscan los elementos del elemento 2
        	response.setElementTwoList(getElementsToLinksHelper(daoElement.getSerializedElementsByElementTypeCodeAndSerial(criteria.getElementTwoCode(), criteria.getElementTwoSerial(),criteria.getWarehouseId())));
        	return response;
        }catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación getElementsToLinkSerializedElements/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina getElementsToLinkSerializedElements/ElementBusinessBean ==");
        }
	}
	
	/**
	 * Metodo: Arma la respuesta a la consulta de elementos para poder vincularlos
	 * @param objectList
	 * @return
	 * @throws BusinessException
	 * @author jnova
	 */
	public List<ElementToLinkVO> getElementsToLinksHelper(List<Serialized> objectList) throws BusinessException{
		LOG.info("== Inicia getElementsToLinksHelper/ElementBusinessBean ==");
		try {
			List<ElementToLinkVO> response = null; 
        	if(objectList != null && objectList.size() > 0){
        		response = new ArrayList<ElementToLinkVO>();
        		for(Serialized obj : objectList){
        			ElementToLinkVO vo = new ElementToLinkVO();
        			vo.setElementEntryDate(obj.getRegistrationDate());
        			vo.setElementID(obj.getElementId());
        			vo.setElementModel(UtilsBusiness.copyObject(ElementModelVO.class,obj.getElement().getElementType().getElementModel()));
        			vo.setElementSerial(obj.getSerialCode());
        			vo.setElementStatus(UtilsBusiness.copyObject(ElementStatusVO.class,obj.getElement().getElementStatus()));
        			vo.setElementType(UtilsBusiness.copyObject(ElementTypeVO.class,obj.getElement().getElementType()));
        			vo.setLinkedElementSerialCode((obj.getSerialized()!= null && obj.getSerialized().getSerialCode()!=null)?obj.getSerialized().getSerialCode():null);
        			vo.setRid((obj.getSerialized() != null && obj.getSerialized().getIrd()!=null)?obj.getSerialized().getIrd():null);
        			response.add(vo);
        		}
        	}
        	return response;
        }catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación getElementsToLinksHelper/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina getElementsToLinksHelper/ElementBusinessBean ==");
        }
	}
	
	/**
	 * Verifica que un elemento es Deco o card. Validación V-02 CU-INV44
	 * **/
	private boolean isDecoOrCard(Serialized element) throws BusinessException{
		boolean status = false;
		try{
			String classDecoder = CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity();
			String classCard = CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity();
				
			status =  (element.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equals(classDecoder) || element.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equals(classCard));
		}catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación isDecoOrCard/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina isDecoOrCard/ElementBusinessBean ==");
        }
        return status;
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal#linkSerializedElements(java.lang.Long,java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void linkSerializedElements(Long elementOneId, Long elementTwoId) throws BusinessException{
		LOG.info("== Inicia linkSerializedElements/ElementBusinessBean ==");
		UtilsBusiness.validateParameters(elementOneId,elementTwoId);
		if(elementOneId.equals(elementTwoId)){
			LOG.debug("No puede vincular un elemento con el mismo");
    		throw new BusinessException(ErrorBusinessMessages.ELEMENT_IS_THE_SAME.getCode(),ErrorBusinessMessages.ELEMENT_IS_THE_SAME.getMessage());
		}
		try {
			
			Serialized elementOne = daoSerialized.getSerializedByID(elementOneId);
			if(elementOne != null){				
				if(elementOne.getSerialized() == null){
					Serialized elementTwo = daoSerialized.getSerializedByID(elementTwoId);
					if(elementTwo != null ){
						if(elementTwo.getSerialized() == null){
							//V-02: Uno se los elementos no es ni IRD ni SC						
							if(!(isDecoOrCard(elementOne) && isDecoOrCard(elementOne))){
								throw new BusinessException(ErrorBusinessMessages.STOCK_IN450.getCode(),ErrorBusinessMessages.STOCK_IN450.getMessage());
							//V-02: Los elementos son de la misma clase
							}else if(elementOne.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equals(elementTwo.getElement().getElementType().getElementModel().getElementClass().getElementClassCode())){
								throw new BusinessException(ErrorBusinessMessages.STOCK_IN450.getCode(),ErrorBusinessMessages.STOCK_IN450.getMessage());
							}else if(!(elementOne.getElement().getElementType().getElementModel().getIsPrepaidModel().equals(elementTwo.getElement().getElementType().getElementModel().getIsPrepaidModel()))){
								throw new BusinessException(ErrorBusinessMessages.STOCK_IN445.getCode(),ErrorBusinessMessages.STOCK_IN445.getMessage());
							}else{
								String isSerialized = CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity();
								WarehouseElement warehouseElementOne =  warehouseElementDAO.getWarehouseElementBySerialActive(elementOne.getSerialCode(),elementOne.getElement().getCountry().getId());
								WarehouseElement warehouseElementTwo =  warehouseElementDAO.getWarehouseElementBySerialActive(elementTwo.getSerialCode(),elementTwo.getElement().getCountry().getId());
								if(warehouseElementOne.getWarehouseId().getId().longValue() != warehouseElementTwo.getWarehouseId().getId().longValue()){
									throw new BusinessException(ErrorBusinessMessages.STOCK_IN448.getCode(),ErrorBusinessMessages.STOCK_IN448.getMessage());
								}else{			
									if(elementTwo.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity())){
										elementTwo.setSerialized(elementOne);
										daoSerialized.updateSerialized(elementTwo);
									}else{
										elementOne.setSerialized(elementTwo);
										daoSerialized.updateSerialized(elementOne);
									}
									
								}
							}
						} else{
							LOG.debug("El elemento con id "+elementTwoId +" ya tiene un elemento vinculado");
			        		throw new BusinessException(ErrorBusinessMessages.ELEMENT_ALREADY_HAVE_LINKED_ELEMENT.getCode(),ErrorBusinessMessages.ELEMENT_ALREADY_HAVE_LINKED_ELEMENT.getMessage());
						}
					} else{
						LOG.debug("El elemento con id "+elementTwoId +" no es un elemento serializado");
		        		throw new BusinessException(ErrorBusinessMessages.ELEMENT_ISNT_SERIALIZED.getCode(),ErrorBusinessMessages.ELEMENT_ISNT_SERIALIZED.getMessage());
					}
				} else{
					LOG.debug("El elemento con id "+elementOneId +" ya tiene un elemento vinculado");
	        		throw new BusinessException(ErrorBusinessMessages.ELEMENT_ALREADY_HAVE_LINKED_ELEMENT.getCode(),ErrorBusinessMessages.ELEMENT_ALREADY_HAVE_LINKED_ELEMENT.getMessage());
				}
			} else{
				LOG.debug("El elemento con id "+elementOneId +" no es un elemento serializado");
        		throw new BusinessException(ErrorBusinessMessages.ELEMENT_ISNT_SERIALIZED.getCode(),ErrorBusinessMessages.ELEMENT_ISNT_SERIALIZED.getMessage());
			}
		}catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación linkSerializedElements/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina linkSerializedElements/ElementBusinessBean ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal#getWhElementsAndNotSerPartRetByWarehouseIdMinInfo(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<NotSerializedAjustmentVO> getWhElementsAndNotSerPartRetByWarehouseIdMinInfo(Long warehouseId)throws BusinessException{
		LOG.info("== Inicia getWhElementsAndNotSerPartRetByWarehouseId/ElementBusinessBean ==");
        UtilsBusiness.assertNotNull(warehouseId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        List<NotSerializedAjustmentVO> notSerializedAjustmentDTOList = null;
        try {
        	List<WarehouseElementVO> elementList = UtilsBusiness.convertList(warehouseElementDAO.getWarehouseElementsByWHIdTargetExitDateNullAndRecordLast(warehouseId), WarehouseElementVO.class);
        	if(elementList != null && elementList.size() > 0){
        		notSerializedAjustmentDTOList = new ArrayList<NotSerializedAjustmentVO>();
        		for(WarehouseElementVO vo : elementList){
        			if(vo.getNotSerialized() != null){
        				NotSerializedAjustmentVO obj = new NotSerializedAjustmentVO();
            			obj.setElement(vo);
            			vo.setAjustmentQauntity(0D);
            			List<NotSerPartialRetirement> notSerPartPojo = notSerPartialRetirementDAO.getNotSerPartialRetirementByElementId(vo.getNotSerialized().getElementId());
            			obj.setElementPartialRetirement(UtilsBusiness.convertList(notSerPartPojo, NotSerPartialRetirementVO.class));
            			notSerializedAjustmentDTOList.add(obj);
        			}        			
        		}
        	}
        	return notSerializedAjustmentDTOList;
        } catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación getWhElementsAndNotSerPartRetByWarehouseId/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina getWhElementsAndNotSerPartRetByWarehouseId/ElementBusinessBean ==");
        }		
	}
	

	
	


	@Override
	public ElementSerializedLinkUnLinkVO getElementSerializedToLinkUnLink(
			ElementSerializedLinkUnLinkFilterVO elementSerializedLinkUnLinkFilterVO)
			throws BusinessException {
		LOG.info("== Inicio getElementSerializedToLinkUnLink/ElementBusinessBean ==");
		UtilsBusiness.assertNotNull(elementSerializedLinkUnLinkFilterVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try{
			UtilsBusiness.assertNotNull(elementSerializedLinkUnLinkFilterVO.getSerialCodeElement(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(elementSerializedLinkUnLinkFilterVO.getCodeTypeElement(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(elementSerializedLinkUnLinkFilterVO.getIdCountry(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			//Verificamos si es un vinculado
			if ( elementSerializedLinkUnLinkFilterVO.getSerialCodeElement()!=null && elementSerializedLinkUnLinkFilterVO.getSerialCodeElement().length()>0){
				List<Serialized> lista = daoSerialized.getLinkedSerializedBySerialCode( elementSerializedLinkUnLinkFilterVO.getSerialCodeElement(),elementSerializedLinkUnLinkFilterVO.getIdCountry() );
				if ( lista.size() > 0 ){
					//Si es un vinculado se hace la busqueda por el principal
					elementSerializedLinkUnLinkFilterVO.setSerialCodeElement( lista.get(0).getSerialCode() );
					elementSerializedLinkUnLinkFilterVO.setCodeTypeElement( lista.get(0).getElement().getElementType().getTypeElementCode() );
					elementSerializedLinkUnLinkFilterVO.setIdCountry( lista.get(0).getElement().getCountry().getId() );
				}
			}
			
			ElementSerializedLinkUnLinkVO elementSerializedLinkUnLinkResult = daoElement.getElementSerializedToLinkUnLink(elementSerializedLinkUnLinkFilterVO);
			
			if(elementSerializedLinkUnLinkResult != null){
				WarehouseVO warehouseVO =  warehouseBusinessBean.getWarehouseByID(elementSerializedLinkUnLinkResult.getWarehouseId());
				if(warehouseVO!=null){
					elementSerializedLinkUnLinkResult.setNameWareHouse(warehouseVO.getWarehouseName());
				}
			}
			
			return elementSerializedLinkUnLinkResult;
		} catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación getElementSerializedToLinkUnLink/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina getElementSerializedToLinkUnLink/ElementBusinessBean ==");
        }
		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal#updateLinkedSerials(co.com.directv.sdii.model.dto.UpdateLinkedSerialsDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateLinkedSerials(UpdateLinkedSerialsDTO updateLinkedSerialsDTO)throws BusinessException {
		LOG.info("== Inicia updateLinkedSerials/ElementBusinessBean ==");
		try{
			UtilsBusiness.assertNotNull(updateLinkedSerialsDTO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(updateLinkedSerialsDTO.getElementTypeCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(updateLinkedSerialsDTO.getLinkedSerialCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(updateLinkedSerialsDTO.getNewLinkedSerialCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(updateLinkedSerialsDTO.getSerialCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(updateLinkedSerialsDTO.getCountryId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			if( updateLinkedSerialsDTO.getWarehouseId() == null || updateLinkedSerialsDTO.getWarehouseId().longValue() <= 0 ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			ElementSerializedLinkUnLinkFilterVO filter = new ElementSerializedLinkUnLinkFilterVO();
			filter.setCodeTypeElement( updateLinkedSerialsDTO.getElementTypeCode() );
			filter.setIdWarehouse( updateLinkedSerialsDTO.getWarehouseId() );
			filter.setSerialCodeElement( updateLinkedSerialsDTO.getSerialCode() );
			filter.setIdCountry( updateLinkedSerialsDTO.getCountryId() );
			ElementSerializedLinkUnLinkVO elementSerializedLinkUnLinkResult = this.getElementSerializedToLinkUnLink(filter);
			if( elementSerializedLinkUnLinkResult != null ){
				//Se valida que el elemento enviado tengo un vinculado y sea el enviado por parametro
				if( elementSerializedLinkUnLinkResult.getElementIdLink() != null && elementSerializedLinkUnLinkResult.getElementIdLink().longValue() > 0 
						&& elementSerializedLinkUnLinkResult.getSerialCodeLink() != null && elementSerializedLinkUnLinkResult.getSerialCodeLink().equals( updateLinkedSerialsDTO.getLinkedSerialCode() ) ){
					//Desvincula los elementos
					this.warehouseElementBusiness.separateLinkedSerializedElementsInWh(updateLinkedSerialsDTO.getWarehouseId(),elementSerializedLinkUnLinkResult.getElementId(),elementSerializedLinkUnLinkResult.getElementIdLink());
					//Realiza la nueva vinculacion
					Serialized newSerialized = this.daoSerialized.getSerializedBySerial( updateLinkedSerialsDTO.getNewLinkedSerialCode(), updateLinkedSerialsDTO.getCountryId() );
					if( newSerialized != null ){
						ElementSerializedLinkUnLinkFilterVO filterNewElement = new ElementSerializedLinkUnLinkFilterVO();
						filterNewElement.setCodeTypeElement( newSerialized.getElement().getElementType().getTypeElementCode() );
						filterNewElement.setIdWarehouse( updateLinkedSerialsDTO.getWarehouseId() );
						filterNewElement.setSerialCodeElement( newSerialized.getSerialCode() );
						ElementSerializedLinkUnLinkVO newElementSerializedLinkUnLinkResult = this.getElementSerializedToLinkUnLink(filterNewElement);
						if( newElementSerializedLinkUnLinkResult != null ){
							this.linkSerializedElements(newSerialized.getElementId(),elementSerializedLinkUnLinkResult.getElementId());
						} else{
							Object params[] = {updateLinkedSerialsDTO.getNewLinkedSerialCode()};
							List<String> paramsList = new ArrayList<String>();
							paramsList.add( updateLinkedSerialsDTO.getNewLinkedSerialCode() );
							throw new BusinessException(ErrorBusinessMessages.STOCK_IN404.getCode(), ErrorBusinessMessages.STOCK_IN404.getMessage(params),paramsList);
						}
					} else {
						Object params[] = {updateLinkedSerialsDTO.getNewLinkedSerialCode()};
						List<String> paramsList = new ArrayList<String>();
						paramsList.add( updateLinkedSerialsDTO.getNewLinkedSerialCode() );
						throw new BusinessException(ErrorBusinessMessages.STOCK_IN404.getCode(), ErrorBusinessMessages.STOCK_IN404.getMessage(params),paramsList);
					}
				} else{
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN403.getCode(), ErrorBusinessMessages.STOCK_IN403.getMessage());
				}
			} else{
				Object params[] = {updateLinkedSerialsDTO.getSerialCode()};
				List<String> paramsList = new ArrayList<String>();
				paramsList.add( updateLinkedSerialsDTO.getSerialCode() );
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN402.getCode(), ErrorBusinessMessages.STOCK_IN402.getMessage(params),paramsList);
			}
		} catch(Throwable ex){
        	LOG.debug("== Error al tratar de ejecutar la operación updateLinkedSerials/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            LOG.info("== Termina updateLinkedSerials/ElementBusinessBean ==");
        }
	}


	@Override
	public void changeElementModel(Long idElement,
			String codTypeElement, Long idElementLinkElement,
			String codTypeElementLinkElement) throws BusinessException {
		LOG.debug("== Inicia changeElementModel/ElementBusinessBean ==");
		try{
			processChangeElementTypeForElement(idElement, codTypeElement, idElementLinkElement, codTypeElementLinkElement);
		} catch (Throwable t){
        	LOG.error("== Error al tratar de ejecutar la operación changeElementModel/ElementBusinessBean ==", t);
        	throw this.manageException(t);			
		} finally {
			LOG.debug("== Termina changeElementModel/ElementBusinessBean ==");
		}
	}
	
	
	private WarehouseElement validateElementbyElementID(Long elementId, ElementType elementType) throws BusinessException{
		LOG.debug("== Inicia changeElementModel/ElementBusinessBean ==");
		try{
			WarehouseElement warehouseElement = warehouseElementDAO.getWarehouseElementSerializedLastByElementID(elementId);
			if(warehouseElement==null){
				throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getCode(),ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getMessage());
			}
			
			if(warehouseElement.getSerialized()==null){
				//El elemento no es serializado
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN408.getCode(), ErrorBusinessMessages.STOCK_IN408.getMessage());
			}
			
			//Valido que le tipo enviado no sea no serializado
			if(elementType.getIsSerialized().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity())){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN474.getCode(), ErrorBusinessMessages.STOCK_IN474.getMessage());
			}
			
			
			//Se valida si el pais tiene habilitado o no el cambio de PrePago a PostPago
			if (CodesBusinessEntityEnum.CODE_SYSTEM_PARAM_ENABLE_PREPAGO_TO_POSTPAGO.getCodeEntity().equals("N") ){
				//Valido el que el cambio de modelo no sea de prepago hacia postpago
				if(warehouseElement.getSerialized().getElement().getElementType().getElementModel().getIsPrepaidModel().equals(CodesBusinessEntityEnum.ELEMENT_MODEL_IS_PREPAID.getCodeEntity())
				   && elementType.getElementModel().getIsPrepaidModel().equals(CodesBusinessEntityEnum.ELEMENT_MODEL_IS_NOT_PREPAID.getCodeEntity())){
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN405.getCode(), ErrorBusinessMessages.STOCK_IN405.getMessage());
				}
			}
			return warehouseElement;
		} catch (Throwable t){
        	LOG.error("== Error al tratar de ejecutar la operación validateElementbyElementID/ElementBusinessBean ==", t);
        	throw this.manageException(t);			
		} finally {
			LOG.debug("== Termina validateElementbyElementID/ElementBusinessBean ==");
		}
	}
	
	/**
	 * 
	 * Metodo: Método encargado de realizar las validaciones para el cambio de modelo de un elemento.
	 * Fecha: 01/09/2011
	 * @param idWarehouseElement
	 * @param codTypeElement
	 * @param idWarehouseElementLinkElement
	 * @param codTypeElementLinkElement
	 * @param confirm
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	private void processChangeElementTypeForElement(Long idElement,
			String codTypeElement, Long idElementLinkElement,
			String codTypeElementLinkElement) throws BusinessException {
		LOG.debug("== Inicia processChangeElementTypeForElement/ElementBusinessBean ==");
		
		//Valida que el elemento que se desea modificar y el tipo no sea nulo
		UtilsBusiness.assertNotNull(idElement, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(codTypeElement, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());	
		MovementType movementTypeE = null;
		MovementType movementTypeS = null;
		ElementType elementTypeNewMainElement= null;
		ElementType elementTypeNewLinkElement= null;
		
		WarehouseElement warehouseElementMainElement = null;
		WarehouseElement warehouseElementLinkElement = null;
		WarehouseElement warehouseElementLinkElementFound = null;
		
		boolean existChangeType = false;
		boolean isLinkElement = false;
		try {
			
			// Se valida que el movimiento exista en el sistema
			movementTypeS = daoMovementType.getMovementTypeByCode(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_MODEL_CHANGE_EXIT.getCodeEntity());
			if(movementTypeS==null){
				throw new BusinessException(ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getMessage());
			}
			movementTypeE = daoMovementType.getMovementTypeByCode(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_MODEL_CHANGE_ENTRY.getCodeEntity());
			if(movementTypeE==null){
				throw new BusinessException(ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getMessage());
			}
			
			//Valido que exista el tipo de elemento del elemento principal
			elementTypeNewMainElement = daoElementType.getElementTypeByCode(codTypeElement);
			if(elementTypeNewMainElement==null){
				throw new BusinessException(ErrorBusinessMessages.ELEMENT_TYPE_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_TYPE_NOT_EXIST.getMessage());
			}
			
			warehouseElementMainElement = validateElementbyElementID(idElement, elementTypeNewMainElement);
			if(!(warehouseElementMainElement.getSerialized().getElement().getElementType().getId().longValue() == elementTypeNewMainElement.getId().longValue())){
				existChangeType = true;
			}
			if(idElementLinkElement!=null && idElementLinkElement.longValue()>0){
				//Valido que exista el nuevo tipo de elemento del elemento vinculado
				elementTypeNewLinkElement = daoElementType.getElementTypeByCode(codTypeElementLinkElement);
				if(elementTypeNewLinkElement==null){
					throw new BusinessException(ErrorBusinessMessages.ELEMENT_TYPE_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_TYPE_NOT_EXIST.getMessage());
				}
				warehouseElementLinkElement = validateElementbyElementID(idElementLinkElement, elementTypeNewLinkElement);
				if(!(warehouseElementLinkElement.getSerialized().getElement().getElementType().getId().longValue() == elementTypeNewLinkElement.getId().longValue())){
					existChangeType = true;
				}
			}else{
				//Asegurar que el elemento no este vinculado
				if(warehouseElementMainElement.getSerialized().getSerialized()!=null){
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN478.getCode(), ErrorBusinessMessages.STOCK_IN478.getMessage());
				}else{
					List<Serialized> listLinkElement = daoSerialized.getLinkedSerializedBySerialCode(warehouseElementMainElement.getSerialized().getSerialCode(),warehouseElementMainElement.getSerialized().getElement().getCountry().getId());
					if(listLinkElement.size()>0){
						throw new BusinessException(ErrorBusinessMessages.STOCK_IN478.getCode(), ErrorBusinessMessages.STOCK_IN478.getMessage());
					}
				}
			}

			//Valido que el elemento principal no tenga el mismo tipo al enviado
			if(!existChangeType){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN406.getCode(), ErrorBusinessMessages.STOCK_IN406.getMessage());
			}
			
			
			//Valido que la vinculación sea valida
			if(warehouseElementLinkElement != null){
				if((warehouseElementMainElement.getSerialized().getSerialized()!=null
					&& warehouseElementMainElement.getSerialized().getSerialized().getElementId().longValue()==warehouseElementLinkElement.getSerialized().getElementId())
					|| (warehouseElementLinkElement.getSerialized().getSerialized()!=null 
					&& warehouseElementLinkElement.getSerialized().getSerialized().getElementId().longValue()==warehouseElementMainElement.getSerialized().getElementId())){
					isLinkElement = true;
				}else{
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN477.getCode(), ErrorBusinessMessages.STOCK_IN477.getMessage());
				}
			}
			
			
			

			//Validaciones de modelo en caso de que los elementos esten vinculados
			if(isLinkElement){
				
				//Valido que los modelos sean del mismo tipo
				if(!elementTypeNewMainElement.getElementModel().getIsPrepaidModel().equals(elementTypeNewLinkElement.getElementModel().getIsPrepaidModel())){
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN412.getCode(), ErrorBusinessMessages.STOCK_IN412.getMessage());
				}
				
				boolean existDeco = false;
				boolean existSmartCard = false;
				//Validar que los nuevos tipos de elemento sean uno de smartcard y otro de decodificador
				if(elementTypeNewLinkElement.getElementModel().getElementClass().getElementClassCode().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity())){
					existDeco = true;
				}else if(elementTypeNewLinkElement.getElementModel().getElementClass().getElementClassCode().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity())){
					existSmartCard = true;
				}
				
				if(elementTypeNewMainElement.getElementModel().getElementClass().getElementClassCode().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity())){
					existDeco = true;
				}else if(elementTypeNewMainElement.getElementModel().getElementClass().getElementClassCode().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity())){
					existSmartCard = true;
				}
				if(!existDeco||!existSmartCard){
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN413.getCode(), ErrorBusinessMessages.STOCK_IN413.getMessage());
				}
				
			}
			
			warehouseElementBusiness.changeElementTypeSerializedElement(warehouseElementMainElement, warehouseElementLinkElement, movementTypeE, movementTypeS, elementTypeNewMainElement, elementTypeNewLinkElement);
			
			
		} catch (Throwable t){
        	LOG.debug("== Error al tratar de ejecutar la operación processChangeElementTypeForElement/ElementBusinessBean ==", t);
        	throw this.manageException(t);			
		} finally {
			LOG.info("== Termina processChangeElementTypeForElement/ElementBusinessBean ==");
		}
	}



	@Override
	public SerializedVO orderLinkedElement(SerializedVO serializedVO)
			throws BusinessException {
		SerializedVO returnSerializedVO=serializedVO;
		try{
			if(serializedVO!=null && serializedVO.getSerialized()!=null){
				if(serializedVO.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equals(CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity())){
					Serialized elementLinked = serializedVO;
					SerializedVO elementMain = UtilsBusiness.copyObject(SerializedVO.class, serializedVO.getSerialized());
					elementLinked.setSerialized(null);
					elementMain.setSerialized(elementLinked);
					returnSerializedVO= elementMain;
				}
				
			}
		}catch (Throwable e) {
			this.manageException(e);
		}
		return returnSerializedVO;
	}


	

}
