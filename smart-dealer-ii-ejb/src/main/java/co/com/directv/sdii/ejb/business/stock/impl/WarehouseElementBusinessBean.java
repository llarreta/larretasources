package co.com.directv.sdii.ejb.business.stock.impl;

import static co.com.directv.sdii.common.util.UtilsBusiness.assertNotNull;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.EmailTypesEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.common.EmailTypeBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.AdjustmentBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MoveElementsFromWHCrewToWHCompanyLocal;
import co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.CustomerElementsDTO;
import co.com.directv.sdii.model.dto.MassiveMovementBetweenWareHouseDTO;
import co.com.directv.sdii.model.dto.MovedElementSerializedDTO;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO;
import co.com.directv.sdii.model.dto.SendEmailDTO;
import co.com.directv.sdii.model.dto.WareHouseElementClientFilterRequestDTO;
import co.com.directv.sdii.model.dto.WareHouseElementHistoricalDTO;
import co.com.directv.sdii.model.dto.WareHouseRequestDTO;
import co.com.directv.sdii.model.dto.collection.CustomerElementsResponse;
import co.com.directv.sdii.model.dto.collection.QuantityWarehouseElementResponse;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DocumentClass;
import co.com.directv.sdii.model.pojo.ElementModel;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.pojo.ImportLogItem;
import co.com.directv.sdii.model.pojo.InconsistencyType;
import co.com.directv.sdii.model.pojo.MassiveMovement;
import co.com.directv.sdii.model.pojo.MeasureUnit;
import co.com.directv.sdii.model.pojo.MovementType;
import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.ProcessStatus;
import co.com.directv.sdii.model.pojo.RecordStatus;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.ReferenceElementItem;
import co.com.directv.sdii.model.pojo.ReportedElement;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.WarehouseElementQuantity;
import co.com.directv.sdii.model.pojo.WarehouseType;
import co.com.directv.sdii.model.pojo.WhElementSearchFilter;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.collection.MovedElementSerializedResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SerializedWhElementsByCriteriaPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementCustomerResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementHistoricalResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementResponse;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ElementModelVO;
import co.com.directv.sdii.model.vo.ElementTypeVO;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.ImportLogInconsistencyVO;
import co.com.directv.sdii.model.vo.MassiveMovementVO;
import co.com.directv.sdii.model.vo.MeasureUnitVO;
import co.com.directv.sdii.model.vo.NotSerPartialRetirementVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.model.vo.WHElementQtySummaryVO;
import co.com.directv.sdii.model.vo.WarehouseElementQuantityVO;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.model.vo.WarehouseTypeVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.model.vo.WhElementSearchFilterVO;
import co.com.directv.sdii.persistence.dao.allocator.ProcessStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.config.CustomerDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.DocumentClassDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementModelDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImportLogItemDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MassiveMovementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovCmdConfigDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovCmdStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.NotSerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.RecordStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReportedElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseTypeDAOLocal;
import co.com.directv.sdii.reports.dto.FilterSerializedElementDTO;
import co.com.directv.sdii.reports.dto.WarehouseElementCustomerDTO;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD WarehouseElement
 * 
 * Fecha de CreaciÃ³n: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal
 */
@Stateless(name="WarehouseElementBusinessBeanLocal",mappedName="ejb/WarehouseElementBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class WarehouseElementBusinessBean extends BusinessBase implements WarehouseElementBusinessBeanLocal {

	@EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal daoWarehouseElement;
	
	@EJB(name="ElementTypeDAOLocal", beanInterface=ElementTypeDAOLocal.class)
	private ElementTypeDAOLocal daoElementType;

	@EJB(name="WarehouseTypeDAOLocal", beanInterface=WarehouseTypeDAOLocal.class)
	private WarehouseTypeDAOLocal daoWhType;

	@EJB(name="NotSerPartialRetirementDAOLocal", beanInterface=NotSerPartialRetirementDAOLocal.class)
	private NotSerPartialRetirementDAOLocal daoNotSerPartialRetirement;

	@EJB(name="NotSerializedDAOLocal", beanInterface=NotSerializedDAOLocal.class)
	private NotSerializedDAOLocal daoNotSerialized;

	@EJB(name="RecordStatusDAOLocal", beanInterface=RecordStatusDAOLocal.class)
	private RecordStatusDAOLocal daoRecordStatus;

	@EJB(name="MovementTypeDAOLocal", beanInterface=MovementTypeDAOLocal.class)
	private MovementTypeDAOLocal daoMovementType;

	@EJB(name="SerializedBusinessBeanLocal", beanInterface=SerializedBusinessBeanLocal.class)
	private SerializedBusinessBeanLocal serializedBusinessBean;

	@EJB(name="SerializedDAOLocal",beanInterface=SerializedDAOLocal.class)
	private SerializedDAOLocal daoSerialized;

	@EJB(name="WarehouseDAOLocal",beanInterface=WarehouseDAOLocal.class)
	private WarehouseDAOLocal daoWarehouse;

	@EJB(name="ElementModelDAOLocal",beanInterface=ElementModelDAOLocal.class)
	private ElementModelDAOLocal daoElementModel;

	@EJB(name="ImportLogItemDAOLocal",beanInterface=ImportLogItemDAOLocal.class)
	private ImportLogItemDAOLocal daoImportLogItem;
	
	@EJB(name="ImportLogItemBusinessBeanLocal",beanInterface=ImportLogItemBusinessBeanLocal.class)
	private ImportLogItemBusinessBeanLocal businessImportLogItem;

	@EJB(name="ReferenceElementItemDAOLocal",beanInterface=ReferenceElementItemDAOLocal.class)
	private ReferenceElementItemDAOLocal daoReferenceElementItem;

	@EJB(name="WorkordeerDAOLocal",beanInterface=WorkOrderDAOLocal.class)
	private WorkOrderDAOLocal daoWorkOrder;

	@EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
	private DealersDAOLocal dealersDao;

	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal usersDao;

	@EJB(name="EmailTypeBusinessBeanLocal", beanInterface=EmailTypeBusinessBeanLocal.class)
	private EmailTypeBusinessBeanLocal emailTypeBusinessBean;

	@EJB(name="CustomerDAOLocal", beanInterface=CustomerDAOLocal.class)
	private CustomerDAOLocal daoCustomer;

	@EJB(name="ImportLogDAOLocal",beanInterface=ImportLogDAOLocal.class)
	private ImportLogDAOLocal daoImportLog;

	@EJB(name="MovCmdConfigDAOLocal",beanInterface=MovCmdConfigDAOLocal.class)
	private MovCmdConfigDAOLocal daoMovCmdConfig;

	@EJB(name="MovCmdStatusDAOLocal",beanInterface=MovCmdStatusDAOLocal.class)
	private MovCmdStatusDAOLocal daoMovCmdStatus;

	@EJB(name="ElementDAOLocal",beanInterface=ElementDAOLocal.class)
	private ElementDAOLocal daoElement;
	
	@EJB(name="MovementConfigBusinessBeanLocal",beanInterface=MovementConfigBusinessBeanLocal.class)
	private MovementConfigBusinessBeanLocal movementConfigBusiness;
	
	@EJB(name="MoveElementsFromWHCrewToWHCompanyLocal",beanInterface=MoveElementsFromWHCrewToWHCompanyLocal.class)
    private MoveElementsFromWHCrewToWHCompanyLocal beanMoveCrewToComp;
	
	@EJB(name="ProcessStatusDAOLocal", beanInterface=ProcessStatusDAOLocal.class)
	private ProcessStatusDAOLocal processStatusDao;
	
	@EJB(name="MassiveMovementDAOLocal", beanInterface=MassiveMovementDAOLocal.class)
    private MassiveMovementDAOLocal daoMassiveMovement;
	
	@EJB(name="EmployeesCrewDAOLocal", beanInterface=EmployeesCrewDAOLocal.class)
    private EmployeesCrewDAOLocal daoEmployeesCrew;
	
	@EJB(name = "WarehouseBusinessBeanLocal", beanInterface = WarehouseBusinessBeanLocal.class)
	private WarehouseBusinessBeanLocal warehouseBusinessBean;
	
	@EJB(name = "ReportedElementDAOLocal", beanInterface = ReportedElementDAOLocal.class)
	private ReportedElementDAOLocal daoReportedElement;
	
	@EJB(name = "ReferenceDAOLocal", beanInterface = ReferenceDAOLocal.class)
	private ReferenceDAOLocal daoReference;
	
	@EJB(name = "AdjustmentBusinessBeanLocal", beanInterface = AdjustmentBusinessBeanLocal.class)
	private AdjustmentBusinessBeanLocal adjustmentBusinessBean;
	
	@EJB(name = "MovementElementBusinessBeanLocal", beanInterface = MovementElementBusinessBeanLocal.class)
	private MovementElementBusinessBeanLocal businessMovementElement;
	
	@EJB(name = "DocumentClassDAOLocal", beanInterface = DocumentClassDAOLocal.class)
	private DocumentClassDAOLocal daoDocumentClass;
	
	@EJB (name="WarehouseTypeBusinessBeanLocal", beanInterface=WarehouseTypeBusinessBeanLocal.class)
	private WarehouseTypeBusinessBeanLocal businessWarehouseType;
	
	private final static Logger log = UtilsBusiness.getLog4J(WarehouseElementBusinessBean.class);


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getAllWarehouseElements()
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseElementVO> getAllWarehouseElements() throws BusinessException {
		log.debug("== Inicia getAllWarehouseElements/WarehouseElementBusinessBean ==");
		try {
			return UtilsBusiness.convertList(daoWarehouseElement.getAllWarehouseElements(), WarehouseElementVO.class);

		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getAllWarehouseElements/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAllWarehouseElements/WarehouseElementBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getWarehouseElementsByID(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)

	public WarehouseElementVO getWarehouseElementByID(Long id) throws BusinessException {
		log.debug("== Inicia getWarehouseElementByID/WarehouseElementBusinessBean ==");
		UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			WarehouseElement objPojo = daoWarehouseElement.getWarehouseElementByID(id);
			if (objPojo == null) {
				throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			}
			return UtilsBusiness.copyObject(WarehouseElementVO.class, objPojo);
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getWarehouseElementByID/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementByID/WarehouseElementBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#createWarehouseElement(co.com.directv.sdii.model.vo.WarehouseElementVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createWarehouseElement(WarehouseElementVO obj) throws BusinessException {
		log.debug("== Inicia createWarehouseElement/WarehouseElementBusinessBean ==");
		UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			if (!BusinessRuleValidationManager.getInstance().isValid(obj)) {
				log.error("== Error en la Capa de Negocio debido a una Validacion de negocio ==");
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			WarehouseElement objPojo =  UtilsBusiness.copyObject(WarehouseElement.class, obj);
			
			// asigna el elemento vinculado
			Serialized serialized = objPojo.getSerialized();			
			if(serialized != null){
				objPojo.setLinkedSerId(serialized.getSerialized());
			}
			
			daoWarehouseElement.createWarehouseElement(objPojo);
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n createWarehouseElement/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina createWarehouseElement/WarehouseElementBusinessBean ==");
		}

	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#updateWarehouseElement(co.com.directv.sdii.model.vo.WarehouseElementVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateWarehouseElement(WarehouseElementVO obj) throws BusinessException {
		log.debug("== Inicia updateWarehouseElement/WarehouseElementBusinessBean ==");
		UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			WarehouseElement objPojo =  UtilsBusiness.copyObject(WarehouseElement.class, obj);
			daoWarehouseElement.updateWarehouseElement(objPojo);
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n updateWarehouseElement/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina updateWarehouseElement/WarehouseElementBusinessBean ==");
		}

	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#deleteWarehouseElement(co.com.directv.sdii.model.vo.WarehouseElementVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteWarehouseElement(WarehouseElementVO obj) throws BusinessException {
		log.debug("== Inicia deleteWarehouseElement/WarehouseElementBusinessBean ==");
		UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			WarehouseElement objPojo =  UtilsBusiness.copyObject(WarehouseElement.class, obj);
			daoWarehouseElement.deleteWarehouseElement(objPojo);
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n deleteWarehouseElement/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina deleteWarehouseElement/WarehouseElementBusinessBean ==");
		}
	}

	/**
	 * Metodo: devuelve los elementos historicos en la bodega cliente
	 * @param result Lista de WarehouseElement historicas
	 * @param customerId Customer 
	 * @return
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author cduarte
	 */
	private List<CustomerElementsDTO> fillHistoryElementOfCustomer(List<WarehouseElementVO> result,
                                              Long customerId) throws BusinessException, 
                                                                        DAOServiceException, 
                                                                        DAOSQLException, 
                                                                        IllegalAccessException, 
                                                                        InvocationTargetException, PropertiesException {
		
		UtilsBusiness.assertNotNull(customerId, ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getMessage());
		
		//Declaracion de atributos locales
		List<CustomerElementsDTO> historyElementsOfCustomerDTO = new ArrayList<CustomerElementsDTO>();
		Serialized serialized;

		/**
		 * ID IBS Cliente	
		 */
		String customerCode;//Cliente
		
		/**
		 * ID IBS Edificio	
		 */
		String buildingCode;//Edificio
		
		/**
		 * Ciudad del IBS	
		 */
		String postalCodeCity;//Cliente

		/**
		 * DirecciÃ³n del IBS	
		 */
		String woAddress;//Cliente

		/**
		 * Numero de WorkOrder	
		 */
		String woCode;//WorkOrder

		/**
		 * Tipo de elemento	
		 */
		String typeElement;//ElementType

		/**
		 * Coigo Tipo de elemento	
		 */
		String typeElementCode;
		
		/**
		 * Nombre Tipo de elemento	
		 */
		String typeElementName;
		
		/**
		 * Unidad de medida del elemento	
		 */
		String unitName;//Element

		/**
		 * Lote	Elementos
		 */
		String lote;//Element

		/**
		 * Cantidad actual	Elementos en Bodega
		 */
		Double actualQuantity;//WarehouseElement
		
		/**
		 * Modelo	Elementos
		 */
		String modelName;//ElementModel

		/**
		 * Serial del elemento	
		 */
		String serialCode;//Elementos

		/**
		 * RID	
		 */
		String rid;//Elementos

		/**
		 * Serial del elemento vinculado	
		 */
		String serialCodeLinked;//Element

		/**
		 * Fecha de ingreso del elemento	
		 */
		Date registrationDateIn;//Elementos en Bodega
		
		/**
		 * Fecha de salida del elemento	
		 */
		Date registrationDateOut;//Elementos en Bodega
		
		String movementCause;
		
		//Tipo numero documento
		String typeDocument;
		

		
		//Loop para los elementos en una bodega
		for (WarehouseElementVO warehouseElementVO : result) {
			
			//Se obtienen los Work Orders por elementos usados
			//List<WoUsedElement> woUsedElements = daoWoUsedElement.getWoUsedElementsByWhElementIdAndCustId(warehouseElementVO.getId(), customerId);
			//if(! woUsedElements.isEmpty()){
				
					//Loop para llenar las historias de elementos por cliente
					//for (WoUsedElement woUsedElement : woUsedElements) {
			
						customerCode="";
						buildingCode="";
						postalCodeCity="";
						woAddress="";
						woCode="";
						typeElement="";
						typeElementCode="";
						typeElementName="";
						unitName="";
						lote="";
						actualQuantity=0d;
						modelName="";
						serialCode="";
						rid="";
						serialCodeLinked="";
						registrationDateIn=null;
						registrationDateOut=null;
						movementCause="";
						
						//Si es un elemento Serializado
						if(warehouseElementVO.getSerialized() == null){
							
							unitName= warehouseElementVO.getElementType() == null ? "" : warehouseElementVO.getElementType().getMeasureUnit() == null ? "" : warehouseElementVO.getElementType().getMeasureUnit().getUnitName();
							lote=warehouseElementVO.getNotSerialized() == null ? "" : warehouseElementVO.getNotSerialized().getElement() == null ? "" : warehouseElementVO.getNotSerialized().getElement().getLote();
							typeElement=warehouseElementVO.getElementType().getTypeElementCode() + " " + warehouseElementVO.getElementType().getTypeElementName();
							typeElementCode=warehouseElementVO.getElementType().getTypeElementCode();
							typeElementName=warehouseElementVO.getElementType().getTypeElementName();
						//Si es un elemento no serializado
						}else{
							
							serialized=warehouseElementVO.getSerialized();
							if(serialized!=null){
								
								serialCode=serialized.getSerialCode();
								rid="";
								if(serialized.getIrd()!=null)
									rid=serialized.getIrd();
								serialCodeLinked=serialized.getSerialized() == null ? "" : serialized.getSerialized().getSerialCode();
									
							}
							
							typeElement=warehouseElementVO.getElementType().getTypeElementCode() + " " + warehouseElementVO.getElementType().getTypeElementName();
							typeElementCode=warehouseElementVO.getElementType().getTypeElementCode();
							typeElementName=warehouseElementVO.getElementType().getTypeElementName();
							modelName=warehouseElementVO.getElementType() == null ? "" : warehouseElementVO.getElementType().getElementModel() == null ? "" : warehouseElementVO.getElementType().getElementModel().getModelName();
							
						}
						
						if(warehouseElementVO.getMovementType() != null)
							if(warehouseElementVO.getMovementType().getMovClass().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_TYPE_EXIT.getCodeEntity()) ) {	
								registrationDateOut=warehouseElementVO.getMovementDate();
								if(warehouseElementVO.getMovedQuantity().doubleValue()!=0)
									actualQuantity = -1 * warehouseElementVO.getMovedQuantity();//si el tipo de movimiento es de salida, la cantidad es negativa
							} else {
								registrationDateIn=warehouseElementVO.getMovementDate();
								actualQuantity = warehouseElementVO.getMovedQuantity();
							}
						
						if(warehouseElementVO != null && warehouseElementVO.getMovementType() != null) {
							movementCause = warehouseElementVO.getMovementType().getMovTypeName();
						}
						
						typeDocument= "";
						if(warehouseElementVO.getDocumentClass() != null){
							if(warehouseElementVO.getDocumentClass().getDocumentClassCode().equals(CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity()))
							{	
								if(warehouseElementVO.getDocumentId() != null){
									woCode = daoWorkOrder.getCodeWorkOrderByID(warehouseElementVO.getDocumentId());
								}
							}
							typeDocument= warehouseElementVO.getDocumentClass().getDocumentClassName();
							if(woCode != null && !woCode.equals("")){
								typeDocument= typeDocument + " - " +woCode;
							}else if(warehouseElementVO.getDocumentId() != null){
								typeDocument= typeDocument + " - " + warehouseElementVO.getDocumentId();
							}
						}
						
						CustomerElementsDTO historyElementOfCustomerDTO = new CustomerElementsDTO(
								customerCode, buildingCode, postalCodeCity,
								woAddress, woCode, typeElement,typeElementCode,typeElementName, unitName, lote,
								actualQuantity, modelName, serialCode, rid,
								serialCodeLinked, registrationDateIn,registrationDateOut, movementCause,typeDocument);
						
						//Se ingresa El elemento historico en la bodega cliente
						historyElementsOfCustomerDTO.add(historyElementOfCustomerDTO);
						
					//}
			//}
			
		}
		
		//devuelve los elementos historicos en la bodega cliente
		return historyElementsOfCustomerDTO;

	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getWarehouseElementsByFilters(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WareHouseElementResponse getWarehouseElementsByFilters(
			Long wareHouseId, Long dealerId, Long branchDealerId, Long crewId,
			Long warehouseTypeId, String elementTypeCode, Long elementModelId, RequestCollectionInfo requestCollInfo)
	throws BusinessException {
		log.debug("== Inicia getWarehouseElementsByFilters/WarehouseElementBusinessBean ==");
		try {
			List<WarehouseElementVO> warehouseElVOs = new ArrayList<WarehouseElementVO>();

			if(wareHouseId == null && dealerId == null && branchDealerId == null && crewId == null 
			   && warehouseTypeId == null && elementTypeCode == null && elementModelId == null){
				log.debug("No se ha especificado ningÃºn criterio de filtro para relaizar la consulta");
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}

			WhElementSearchFilter whElementSearchFilter = new WhElementSearchFilter();
			whElementSearchFilter.setWareHouseId(wareHouseId);
			whElementSearchFilter.setDealerId(dealerId);
			whElementSearchFilter.setBranchDealerId(branchDealerId);
			whElementSearchFilter.setCrewId(crewId);
			whElementSearchFilter.setWarehouseTypeId(warehouseTypeId);
			whElementSearchFilter.setElementTypeCode(elementTypeCode);
			whElementSearchFilter.setElementModelId(elementModelId);

			WareHouseElementResponse response = daoWarehouseElement.getWarehouseElementsByFilters(whElementSearchFilter, requestCollInfo);
			List<WarehouseElement> whePojo = response.getWareHouseElements();
			//Se poblan los datos relacionados con la remisiÃ³n y el registro de importaciÃ³n:
			warehouseElVOs = UtilsBusiness.convertList(whePojo, WarehouseElementVO.class);        	
			populateReferenceAndImportLogData(warehouseElVOs);
			response.setWareHouseElements(null);
			response.setWareHouseElementsVO(warehouseElVOs);

			return response;
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getWarehouseElementsByFilters/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementsByFilters/WarehouseElementBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getWarehouseElementsByFiltersAndIsSerialized(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, boolean, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	


	/**
	 * @param warehouseElVOs
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */

	private void populateReferenceAndImportLogData(
			List<WarehouseElementVO> warehouseElVOs) throws DAOServiceException, DAOSQLException {
		Long elementId = null;
		for (WarehouseElementVO warehouseElement : warehouseElVOs) {
			//Si el elemento de bodega es serializado 
			elementId = null;
			if(warehouseElement.getSerialized() != null && warehouseElement.getNotSerialized() == null){
				elementId = warehouseElement.getSerialized().getElement().getId();
			//Si el elemento de bodega es NO serializado
			}else if(warehouseElement.getNotSerialized() != null && warehouseElement.getSerialized() == null ){
				elementId = warehouseElement.getNotSerialized().getElement().getId();
			}

			if(elementId == null){
				continue;
			}

			//obteniendo el nÃºmero de registro de importaciÃ³n:
			ImportLogItem impLogItem = daoImportLogItem.getImportLogItemByElementId(elementId);
			if(impLogItem != null){
				warehouseElement.setImportLogPurchaseOrder(impLogItem.getImportLog().getPurchaseOrder());
				warehouseElement.setImportLogId(impLogItem.getImportLog().getId());
				warehouseElement.setImportDoc(impLogItem.getImportLog().getImportDoc());
			}

			//Obteniendo la remisiÃ³n:
			List<ReferenceElementItem> refElements = daoReferenceElementItem.getReferenceElementItemByRefTargetWhIdAndElementId(warehouseElement.getWarehouseId().getId(), elementId);
			if(refElements!=null && !refElements.isEmpty()){
				warehouseElement.setReferenceId(refElements.get(0).getReference().getId());
			}

			//Obteniendo la work order asociada al elemento:
			WorkOrder workOrder = daoWorkOrder.getWorkOrderByElementId(elementId);
			if(workOrder != null){
				warehouseElement.setWorkOrderCode(workOrder.getWoCode());
				warehouseElement.setWorkOrderId(workOrder.getId());
			}
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getHistoryElementsByCustomerIBSCodeDocTypeIdAndDocNumber(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public CustomerElementsResponse getElementsByCustomerIBSCodeDocTypeIdAndDocNumber(
			String customerIbsCode, Long documentTypeId, String customerDocumentNumber,
			Long countryId, RequestCollectionInfo requestCollInfo)
			throws BusinessException {

				log.debug("== Inicia getHistoryElementsByCustomerIBSCodeDocTypeIdAndDocNumber/WarehouseElementBusinessBean ==");
				CustomerElementsResponse customerElementsResponseDTO = new CustomerElementsResponse();
				try {
		
					if ((customerIbsCode == null || customerIbsCode.trim().length() == 0)
							&& ((documentTypeId == null || documentTypeId.longValue() < 0) || (customerDocumentNumber == null || customerDocumentNumber
									.trim().length() == 0))) {
		
						log.debug("Error en la validaciÃ³n de los parÃ¡metros obligatorios, "
										+ "debe venir por lo menos uno de los dos: cÃ³digo de cliente o "
										+ "nÃºmero de identificaciÃ³n, si viene el nÃºmero de identificaciÃ³n, "
										+ "debe venir el tipo de identificaciÃ³n, los parÃ¡metros recibidos fueron: cÃ³digo de cliente: "
										+ customerIbsCode
										+ " tipo de id: "
										+ documentTypeId
										+ " nÃºmero de id: "
										+ customerDocumentNumber);
		
						throw new BusinessException(
								ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
								ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
										.getMessage());
		
					}
		
					WareHouseElementResponse response = daoWarehouseElement
							.getWarehouseElementsByCustomerIBSCodeDocTypeIdDocNumberTypeWarehouseAndRecordStatus(
									customerIbsCode, documentTypeId,
									customerDocumentNumber, countryId,
									CodesBusinessEntityEnum.WAREHOUSE_TYPE_CLIENTE
											.getCodeEntity(),
									null,
									CodesBusinessEntityEnum.MOVEMENT_TYPE_TYPE_ENTRY
											.getCodeEntity(), requestCollInfo);
		
					List<WarehouseElement> whElPojos = response.getWareHouseElements();
					List<WarehouseElementVO> whElVos = UtilsBusiness.convertList(
							whElPojos, WarehouseElementVO.class);
		
					Customer cus = null;
		
					if (documentTypeId != null && documentTypeId.longValue() > 0
							&& customerDocumentNumber != null
							&& customerDocumentNumber.trim().length() > 0) {
		
						cus = daoCustomer.getCustomerByDocTypeIdAndDocNumber(
								documentTypeId, customerDocumentNumber, countryId);
		
					} else {
		
						cus = daoCustomer.getCustomerByCodeAndCountryId(
								customerIbsCode, countryId);
		
					}
					
					customerElementsResponseDTO.setCustomerElementDTO(fillHistoryElementOfCustomer(whElVos, cus.getId()));
					if(response!=null){
						customerElementsResponseDTO.setPageCount(response.getPageCount());
						customerElementsResponseDTO.setPageIndex(response.getPageIndex());
						customerElementsResponseDTO.setPageSize(response.getPageSize());
						customerElementsResponseDTO.setRowCount(response.getRowCount());
						customerElementsResponseDTO.setTotalRowCount(response.getTotalRowCount());
					}
					return customerElementsResponseDTO;
		
				} catch (Throwable ex) {
		
					log.debug(
									"== Error al tratar de ejecutar la operaciÃ³n getHistoryElementsByCustomerIBSCodeDocTypeIdAndDocNumber/WarehouseElementBusinessBean ==",
									ex);
					throw this.manageException(ex);
		
				} finally {
		
					log.debug("== Termina getHistoryElementsByCustomerIBSCodeDocTypeIdAndDocNumber/WarehouseElementBusinessBean ==");
		
				}
	}

	/**
	 * Metodo: Obtiene los registros de todos los objetos NotSerialized propios de un WareHouse
	 * @return Lista con los registros de todos los objetos NotSerialized propios de un WareHouse
	 * @throws BusinessException en caso de error al tratar de ejecutar la tarea
	 * @author garciniegas
	 */

	public List<NotSerializedVO> getNotSerializedsByWareHouseId( WarehouseVO warehouseId ) throws BusinessException
	{
		log.debug("==> Inicia getNotSerializedsByWareHouseId/WarehouseElementBusinessBean ==");		

		UtilsBusiness.assertNotNull(warehouseId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(warehouseId.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<NotSerialized> lista = null;

		try {

			Warehouse objPojo = UtilsBusiness.copyObject(Warehouse.class, warehouseId);
			lista = daoNotSerialized.getNotSerializedsByWareHouseId(objPojo);

			if(lista==null){
				lista = new ArrayList<NotSerialized>();
			}

			List<NotSerializedVO> listaVO = UtilsBusiness.convertList(lista,NotSerializedVO.class);

			for(int i=0;i<listaVO.size();++i){
				Long notSerId = listaVO.get(i).getElementId();
				listaVO.get(i).setActualQuantity(daoNotSerialized.getNotSerializedQuantityByWarehouseIdAndNotSerializedId(warehouseId.getId(),notSerId));  
			}

			return listaVO;

		}catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la tarea getNotSerializedsByWareHouseId/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		}finally{
			log.debug("== Termina getNotSerializedsByWareHouseId/WarehouseElementBusinessBean ==");		
		}
	}
	
	/**
	 * Metodo: UC Inv 11
	 * @param dealerId
	 * @return
	 * @throws BusinessException
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public WareHouseElementResponse getWarehouseQANOTSerializedElements( Long dealerId, RequestCollectionInfo requestCollInfo ) throws BusinessException{
		
		log.debug("==> Inicia getWarehouseQANOTSerializedElements/WarehouseElementBusinessBean ==");
		UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			List<WarehouseElementVO> warehouseElVOs = new ArrayList<WarehouseElementVO>();

			WareHouseElementResponse response = daoWarehouseElement.getNOTSerializedElementsByWHTypeIdDealerId(CodesBusinessEntityEnum.WAREHOUSE_TYPE_CALIDAD.getCodeEntity(), dealerId, requestCollInfo);
			List<WarehouseElement> whePojo = response.getWareHouseElements();
			//Se poblan los datos relacionados con la remisiÃ³n y el registro de importaciÃ³n:
			warehouseElVOs = UtilsBusiness.convertList(whePojo, WarehouseElementVO.class);        	
			//populateReferenceAndImportLogData(warehouseElVOs);
			response.setWareHouseElements(null);
			response.setWareHouseElementsVO(warehouseElVOs);

			return response;
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getWarehouseQANOTSerializedElements/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseQANOTSerializedElements/WarehouseElementBusinessBean ==");
		}

	}
	
	/**
	 * Metodo: UC Inv 13
	 * @param dealerId
	 * @return
	 * @throws BusinessException
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public WareHouseElementResponse getWarehouseQASerializedElements(Dealer dealer, String elementType, String serialCode, RequestCollectionInfo requestCollInfo ) throws BusinessException{
		
		log.debug("==> Inicia getWarehouseQASerializedElements/WarehouseElementBusinessBean ==");
		UtilsBusiness.assertNotNull(dealer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			List<WarehouseElementVO> warehouseElVOs = new ArrayList<WarehouseElementVO>();

			WareHouseElementResponse response = daoWarehouseElement.getSerializedElementsByWHTypeDealerElementTypeSerialCode(CodesBusinessEntityEnum.WAREHOUSE_TYPE_CALIDAD.getCodeEntity(), dealer, elementType, serialCode, requestCollInfo);
			List<WarehouseElement> whePojo = response.getWareHouseElements();
			//Se poblan los datos relacionados con la remisiÃ³n y el registro de importaciÃ³n:
			warehouseElVOs = UtilsBusiness.convertList(whePojo, WarehouseElementVO.class);        	
			//populateReferenceAndImportLogData(warehouseElVOs);
			response.setWareHouseElements(null);
			response.setWareHouseElementsVO(warehouseElVOs);

			return response;
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getWarehouseQASerializedElements/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseQASerializedElements/WarehouseElementBusinessBean ==");
		}

	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getWhouseSerializedElementsByElementTypeAndQAWh(co.com.directv.sdii.model.vo.DealerVO, co.com.directv.sdii.model.vo.ElementTypeVO, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseElementVO> getWhouseSerializedElementsByElementTypeAndQAWh(DealerVO dealer,
			ElementTypeVO elementType, String serialCode) throws BusinessException {
		log.debug("== Inicia getWhouseSerializedElementsByElementTypeAndQAWh/WarehouseElementBusinessBean ==");
		try {
			
			WarehouseType tipoWh;
			tipoWh = this.daoWhType.getWarehouseTypeByCode(CodesBusinessEntityEnum.WAREHOUSE_TYPE_CALIDAD.getCodeEntity());
			return UtilsBusiness.convertList(daoWarehouseElement.getWarehouseElementsByElementTypeAndWhType(dealer.getId(),elementType.getId(),serialCode,tipoWh.getId()), WarehouseElementVO.class) ;
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getWhouseSerializedElementsByElementTypeAndQAWh/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWhouseSerializedElementsByElementTypeAndQAWh/WarehouseElementBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getWhElementsByChriteria(java.lang.Long, java.lang.String,java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WareHouseElementResponse getWhElementsByCriteria(
			Long warehouseId, String isSerialized, Long elementId, RequestCollectionInfo requestCollInfo)
	throws BusinessException {
		log.debug("== Inicia getWhElementsByCriteria/WarehouseElementBusinessBean ==");
		UtilsBusiness.assertNotNull(warehouseId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(isSerialized, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

		try {
			WareHouseElementResponse response = this.daoWarehouseElement.getWhElementsByCriteriaAndGroupByElementId(warehouseId, isSerialized, elementId, requestCollInfo);
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
						BigDecimal id = (BigDecimal) object[3];
						vo.setId( id.longValue() );
					}
					vo.setMovementDate( new Date( ((Timestamp)object[1]).getTime() ) );
					BigDecimal quantity = (BigDecimal) object[2];
					vo.setActualQuantity( quantity.doubleValue() );
					elementsList.add(vo);
				}
				response.setWareHouseElementsVO(elementsList);
				response.setWareHouseElements(null);
			}
			if(response.getWareHouseElementsVO() != null && !response.getWareHouseElementsVO().isEmpty() ){
				return  response;
			} else{
				log.debug("== Error al tratar de ejecutar la operaciÃ³n getWhElementsByCriteria/WarehouseElementBusinessBean ==");
				throw new BusinessException(ErrorBusinessMessages.NOT_ELEMENTS_EXIST.getCode(),ErrorBusinessMessages.NOT_ELEMENTS_EXIST.getMessage());
			}        	        	
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getWhElementsByCriteria/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWhElementsByCriteria/WarehouseElementBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getWarehouseElementsByDealerAndCrewAndTypeAndSerial(java.lang.String, java.lang.Long, java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElementVO getWarehouseElementByDealerAndCrewAndTypeAndSerial(String dealerCode, Long crewId, String elementTypeCode, String serial)
	throws BusinessException {
		log.debug("== Inicia getWarehouseElementsByDealerAndCrewAndTypeAndSerial/WarehouseElementBusinessBean ==");
		WarehouseElementVO warehouseElVO = null;
		try {
			WarehouseElement whePojo = daoWarehouseElement.getWarehouseElementByDealerAndCrewAndTypeAndSerial(dealerCode, crewId, elementTypeCode, serial);
			warehouseElVO = UtilsBusiness.copyObject(WarehouseElementVO.class,whePojo);
			return warehouseElVO;
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getWarehouseElementsByDealerAndCrewAndTypeAndSerial/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementsByDealerAndCrewAndTypeAndSerial/WarehouseElementBusinessBean ==");
		}

	}


	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#updateLinkedElementSerialUpdate(java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.Long, java.lang.String)
	 */
	public void updateLinkedElementSerialUpdate(Long whId, String elementSerial, Long elementId, 
			String oldLinkedElementSerial, String newLinkedElementSerial,Long whIdLinked,String newLinkedTypeCode) throws BusinessException{
		log.debug("== Inicia linkedElementSerialUpdate/WarehouseElementBusinessBean ==");
		List<WarehouseElementVO> listWarehouseElementVo = null;
		WarehouseElementVO warehouseElementVo = null;
		//WarehouseElementVO warehouseElementVoNuevo = null;
		WarehouseElementVO warehouseElementVoLinked = null;
		Serialized serialized = null;
		Serialized linkedSerializedNuevo = null;
		List<WarehouseElement>  listWarehouseElement = null;
		try{
			assertNotNull(whId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			assertNotNull(elementSerial, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			assertNotNull(elementId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			//assertNotNull(oldLinkedElementSerial, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			assertNotNull(newLinkedElementSerial, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			assertNotNull(newLinkedTypeCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			//buscar elementos de bodega que sean de la bodega, serializados y con el identificador dado
			//Metodo paginado, se envia el request null para que no realice paginacion.
			RequestCollectionInfo request=null;
			WareHouseElementResponse response = getWhElementsByCriteria(whId,CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity(),elementId, request);
			listWarehouseElementVo = response.getWareHouseElementsVO();
			//si encontro elementos
			if (listWarehouseElementVo != null && listWarehouseElementVo.size() > 0){
				warehouseElementVo = listWarehouseElementVo.get(0);
				//Si encontro el elemento de bodega 
				//y si el serial del elemento de bodega tiene el serial indicado
				//y si el serial del linked anterior corresponde
				if (warehouseElementVo != null && warehouseElementVo.getId() != null 
					&& warehouseElementVo.getSerialized().getSerialCode().equals(elementSerial)){

					//copia elemento para insertar el nuevo registro
					//warehouseElementVoNuevo = UtilsBusiness.copyObject(WarehouseElementVO.class, warehouseElementVo);

					//linkedSerializedNuevo = serializedBusinessBean.getSerializedBySerial(newLinkedElementSerial);
					Long newElementTypeId = Long.valueOf( newLinkedTypeCode );
					linkedSerializedNuevo = daoSerialized.getSerializedBySerialAndTypeId(newLinkedElementSerial,newElementTypeId,warehouseElementVo.getSerialized().getElement().getCountry().getId());
					if( linkedSerializedNuevo != null && linkedSerializedNuevo.getElementId() != null && linkedSerializedNuevo.getElementId() > 0L )
						listWarehouseElement = daoWarehouseElement.getWhElementByElementIdAndWhSource(whId,CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity(),linkedSerializedNuevo.getElementId());

					if (listWarehouseElement != null && listWarehouseElement.size() > 0 
						&& listWarehouseElement.get(0) != null){
						warehouseElementVoLinked = UtilsBusiness.copyObject(WarehouseElementVO.class, listWarehouseElement.get(0)) ;
					}else{
						log.debug("== Error al tratar de ejecutar la operacion updateLinkedElementSerialUpdate/WarehouseElementBusinessBean ==");
						throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getCode(),ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getMessage());
					}

					//si encontro el nuevo serializado linked
					//y que el elemento de bodega existe
					//y valida que esta en la bodega correspondiente
					if (linkedSerializedNuevo != null && linkedSerializedNuevo.getElementId() != null 
						&& warehouseElementVoLinked != null && warehouseElementVoLinked.getId() != null){

						List<Serialized> linkedElements =  daoSerialized.getLinkedSerializedBySerializedId(linkedSerializedNuevo.getElementId());

						if( linkedElements != null && !linkedElements.isEmpty() ){
							log.debug("== Error al tratar de ejecutar la operacion updateLinkedElementSerialUpdate/WarehouseElementBusinessBean ==");
							throw new BusinessException(ErrorBusinessMessages.ELEMENT_ALREADY_HAVE_LINKED_ELEMENT.getCode(),ErrorBusinessMessages.ELEMENT_ALREADY_HAVE_LINKED_ELEMENT.getMessage());
						}

						//actualizar el registro anterior a historico
						//warehouseElementVo.setRecordStatus(new RecordStatus(2L,"H","Historico"));
						//mergeWarehouseElement(warehouseElementVo);			

						//serialized = warehouseElementVoNuevo.getSerialized();
						serialized = warehouseElementVo.getSerialized();
						//asignar el nuevo linked al serializado del element
						//serialized.setSerialized(linkedSerializedNuevo);
						//actualizar el serializado con su nuevo linked
						SerializedVO serializedVo = UtilsBusiness.copyObject(SerializedVO.class, serialized);
						serializedVo.setSerialized(linkedSerializedNuevo);
						serializedBusinessBean.updateSerialized(serializedVo);
				    //si NO encontro el nuevo serializado linked
					}else{
						log.debug("== Error al tratar de ejecutar la operacion updateLinkedElementSerialUpdate/WarehouseElementBusinessBean ==");
						throw new BusinessException(ErrorBusinessMessages.ELEMENT_SERIAL_NULL.getCode(),ErrorBusinessMessages.ELEMENT_SERIAL_NULL.getMessage());
					}
				//Si NO encontro el elemento de bodega
				}else{
					log.debug("== Error al tratar de ejecutar la operacion updateLinkedElementSerialUpdate/WarehouseElementBusinessBean ==");
					throw new BusinessException(ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(),ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
				}
			//si NO encontro elementos
			}else{
				log.debug("== Error al tratar de ejecutar la operacion updateLinkedElementSerialUpdate/WarehouseElementBusinessBean ==");
				throw new BusinessException(ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(),ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
			}
		}catch(PropertiesException ex){
			log.error("== Error en linkedElementSerialUpdate/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		}catch(DAOServiceException ex){
			log.error("== Error en linkedElementSerialUpdate/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		}catch(DAOSQLException ex){
			log.error("== Error en linkedElementSerialUpdate/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		}catch(BusinessException ex){
			log.error("== Error en linkedElementSerialUpdate/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		}
		log.debug("== Termina linkedElementSerialUpdate/WarehouseElementBusinessBean ==");
	}



	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getSerializedsByWareHouseId(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	public List<SerializedVO> getSerializedsByWareHouseId( WarehouseVO warehouseId ) throws BusinessException
	{
		log.debug("== Inicia getSerializedsByWareHouseId/WarehouseElementBusinessBean ==");		

		UtilsBusiness.assertNotNull(warehouseId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(warehouseId.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<SerializedVO> lista = null;

		try {

			Warehouse objPojo = UtilsBusiness.copyObject(Warehouse.class, warehouseId);
			lista = UtilsBusiness.convertList(daoSerialized.getSerializedsByWareHouseId( objPojo ), SerializedVO.class) ;
			if(lista==null){
				lista = new ArrayList<SerializedVO>();
			}
			
		}catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la tarea getSerializedsByWareHouseId/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		}

		return UtilsBusiness.convertList(lista,SerializedVO.class);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getCurrentQuantityInWarehouseByElementType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElementQuantityVO getCurrentQuantityInWarehouseByElementType(
			Long warehouseId, Long elementTypeId, Long elementModelId)
	throws BusinessException {
		log.debug("== Inicia getCurrentQuantityInWarehouseByElementType/WarehouseElementBusinessBean ==");
		WarehouseElementQuantityVO obj = null;
		ElementTypeVO elementTypeVO = null;
		ElementModelVO elementModelVO = null;
		MeasureUnitVO measureUnitVO = null;
		Warehouse warehousePojo = null;
		ElementType elementTypePojo = null;
		ElementModel elementModelPojo = null;
		MeasureUnit measureUnitPojo = null;
		Double currentQuantity;

		try {
			assertNotNull(warehouseId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			assertNotNull(elementTypeId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			assertNotNull(elementModelId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

			obj = new WarehouseElementQuantityVO();

			// Se consultan los Pojo a partir de los ID y se valida que existan
			elementTypePojo = daoElementType.getElementTypeByID(elementTypeId);
			assertNotNull(elementTypePojo, ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			elementModelPojo = daoElementModel.getElementModelByID(elementModelId);
			assertNotNull(elementModelPojo, ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			warehousePojo = daoWarehouse.getWarehouseByID(warehouseId);
			assertNotNull(warehousePojo, ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			measureUnitPojo = elementTypePojo.getMeasureUnit();
			assertNotNull(measureUnitPojo, ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());

			// Se consulta la cantidad actual
			currentQuantity = daoWarehouseElement.getCurrentQuantityInWarehouseByElementType(warehouseId, elementTypeId, elementModelId);

			if(currentQuantity == null){
				currentQuantity = 0D;
			}

			// Se convierten los Pojo a VO
			elementTypeVO = UtilsBusiness.copyObject(ElementTypeVO.class,elementTypePojo);
			elementModelVO = UtilsBusiness.copyObject(ElementModelVO.class,elementModelPojo);
			measureUnitVO = UtilsBusiness.copyObject(MeasureUnitVO.class,measureUnitPojo);

			// Se asignan los datos consultados al VO
			obj.setElementModel(elementModelVO);
			obj.setElementType(elementTypeVO);
			obj.setMeasureUnit(measureUnitVO);
			obj.setCurrentQuantity(currentQuantity);

			return obj;
		} catch (Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getCurrentQuantityInWarehouseByElementType/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getCurrentQuantityInWarehouseByElementType/WarehouseElementBusinessBean ==");
		}
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getWhElementQuantitySummariesByFilters(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.util.Date, java.util.Date)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WHElementQtySummaryVO> getWhElementQuantitySummariesByFilters(Long dealerId, Long warehouseId, Long crewId, Long warehouseTypeId, 
			Long elementTypeId, Long elementModelId, Date initialDate, Date finalDate)throws BusinessException{

		log.debug("== Inicia getWhElementQuantitySummariesByFilters/WarehouseElementBusinessBean ==");
		try {
			String keySeparator = "#$%&";
			List<WHElementQtySummaryVO> result = new ArrayList<WHElementQtySummaryVO>();
			WHElementQtySummaryVO whElementQtySum;

			List<WarehouseElementQuantity> resultPojo = this.daoWarehouseElement.getCurrentElementQuantityInWHByFilters(dealerId, warehouseId, crewId, warehouseTypeId, elementTypeId, elementModelId, initialDate, finalDate);
			Map<String, WHElementQtySummaryVO> resultMap = new HashMap<String, WHElementQtySummaryVO>();

			for (WarehouseElementQuantity whElementQty : resultPojo) {
				String elementKey = "" + whElementQty.getElementTypeId() + keySeparator + whElementQty.getWarehouseId();
				whElementQtySum = resultMap.get(elementKey);

				if(whElementQtySum == null){
					whElementQtySum = buildBasicWHElementQtySummaryInfo(whElementQty);
					resultMap.put(elementKey, whElementQtySum);
				}
				//Si la estadÃ­stica es por cantidad actual
				if(WarehouseElementQuantity.WH_ELEMENT_QTY_TYPE_TOTAL_ACTUAL_QUANTITY.equalsIgnoreCase(whElementQty.getWhElWtyType())){
					whElementQtySum.setActualQuantity(whElementQty.getQuantity());
				//Si la estadÃ­stica es por total de entradas
				}else if(WarehouseElementQuantity.WH_ELEMENT_QTY_TYPE_TOTAL_ENTRANCE_QUANTITY.equalsIgnoreCase(whElementQty.getWhElWtyType())){
					whElementQtySum.setEntriesQuantity(whElementQty.getQuantity());
				//Si la estadÃ­stica es por total de salidas
				}else if(WarehouseElementQuantity.WH_ELEMENT_QTY_TYPE_TOTAL_OUTPUT_QUANTITY.equalsIgnoreCase(whElementQty.getWhElWtyType())){
					whElementQtySum.setOutputsQuantity(whElementQty.getQuantity());
				}
			}

			result.addAll(resultMap.values());
			//Para calcuar la cantidad inicial: (cantidad actual + salidas) - entradas
			Double initialQty = 0D;
			for (WHElementQtySummaryVO element : result) {
				initialQty = (element.getActualQuantity().doubleValue() + element.getOutputsQuantity().doubleValue()) - element.getEntriesQuantity().doubleValue();
				element.setInitialQuantity(initialQty);
			}

			return result;
		}catch( Throwable ex ){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getWhElementQuantitySummariesByFilters/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		}finally{
			log.debug("== Termina getWhElementQuantitySummariesByFilters/WarehouseElementBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getQuantityWarehouseElementsSummariesByFilters(co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	public QuantityWarehouseElementResponse getQuantityWarehouseElementsSummariesByFilters(QuantityWarehouseElementsDTO quantityWarehouseElementsDTO,RequestCollectionInfoDTO requestCollInfo) throws BusinessException{
		
		log.debug("== Inicia getQuantityWarehouseElementsSummariesByFilters/WarehouseElementBusinessBean ==");
		WarehouseElement we;
		
		try {
			QuantityWarehouseElementResponse response=this.daoWarehouseElement.getQuantityWarehouseElementsSummariesByFilters(quantityWarehouseElementsDTO,requestCollInfo);

			//loop para recorrer el dto y setear la cantidad inial y la cantidad actual
			for (QuantityWarehouseElementsDTO dto : response.getQuantityWarehouseElementsDTO()) {
				 //we = daoWarehouseElement.getWarehouseElementByID(dto.getMinWarehouseElementId());
				 //dto.setInitialQuantity(we.getInitialQuantity());
				 //we = daoWarehouseElement.getWarehouseElementByID(dto.getMaxWarehouseElementId());
				double initialQuantity=0d;
				if(dto.getInitialQuantity()!=null)
					initialQuantity=dto.getInitialQuantity();
				double inQuantity=0d;
				if(dto.getInQuantity()!=null)
					inQuantity=dto.getInQuantity();
				double outQuantity=0d;
				if(dto.getOutQuantity()!=null)
					outQuantity=dto.getOutQuantity();
				dto.setActualQuantity(initialQuantity+inQuantity-outQuantity);

				WarehouseVO warehouse = UtilsBusiness.copyObject(WarehouseVO.class, daoWarehouse.getWarehouseByID(dto.getWhId()));
				warehouseBusinessBean.genWareHouseName(warehouse);
				dto.setWhName(warehouse.getWarehouseName());
			}
			
			return response;
			
		}catch( Throwable ex ){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getQuantityWarehouseElementsSummariesByFilters/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		}finally{
			log.debug("== Termina getQuantityWarehouseElementsSummariesByFilters/WarehouseElementBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getQuantityWarehouseElementsDetailsByFilters(co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	public QuantityWarehouseElementResponse getQuantityWarehouseElementsDetailsByFilters(QuantityWarehouseElementsDTO quantityWarehouseElementsDTO,RequestCollectionInfoDTO requestCollInfo) throws BusinessException{
		
		log.debug("== Inicia getQuantityWarehouseElementsSummariesByFilters/WarehouseElementBusinessBean ==");

		try {
			
			QuantityWarehouseElementResponse response = this.daoWarehouseElement.getQuantityWarehouseElementsDetailsByFilters(quantityWarehouseElementsDTO,requestCollInfo);
			//loop para recorrer el dto y setear la cantidad inial y la cantidad actual
			
			DocumentClass documentClass = this.daoDocumentClass.getDocumentClassByCode(CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity());
			for (QuantityWarehouseElementsDTO dto : response.getQuantityWarehouseElementsDTO()) {
				WarehouseVO warehouse = UtilsBusiness.copyObject(WarehouseVO.class, daoWarehouse.getWarehouseByID(dto.getWhId()));
				warehouseBusinessBean.genWareHouseName(warehouse);
				dto.setWhName(warehouse.getWarehouseName());
				
				//Colocar el cÃ³digo de la workorder
				if(documentClass != null){
					if(documentClass.getId().equals(dto.getDocumentClassId())){
						WorkOrder wo = this.daoWorkOrder.getWorkOrderByID(dto.getDocumentId());
						dto.setDocumentClassName(documentClass.getDocumentClassName()+" "+wo.getWoCode());
						dto.setDocumentId(Long.valueOf(wo.getWoCode()));
					}
				}
			}
			return response;
			
		}catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getQuantityWarehouseElementsSummariesByFilters/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		}finally{
			log.debug("== Termina getQuantityWarehouseElementsSummariesByFilters/WarehouseElementBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getQuantityWarehouseElementsDetailsByFilters(co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	public QuantityWarehouseElementResponse getWarehouseElementsByWarehouse(WhElementSearchFilter whElementSearchFilter,RequestCollectionInfo requestCollInfo) throws BusinessException{
		log.debug("== Inicia getWarehouseElementsByWarehouse/WarehouseElementBusinessBean ==");
		try {
			if( whElementSearchFilter.getUserId() == null || whElementSearchFilter.getUserId().longValue() <= 0 ){
				throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			}
			//CC053
			QuantityWarehouseElementResponse resp = this.daoWarehouseElement.getWarehouseElementsByWarehouse(whElementSearchFilter,requestCollInfo,true);
			List<QuantityWarehouseElementsDTO> elements=resp.getQuantityWarehouseElementsDTO();
			int tam = elements.size();
			log.info("Hay "+tam+" elementos en la consulta");
			for (int i=0; i<tam ; ++i) {
				QuantityWarehouseElementsDTO element = elements.get(i);
				element.setWhName(resp.getWareHouseElementsReportDTO().get(i).getUbicacion());
			}
			return resp;
		}catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getWarehouseElementsByWarehouse/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		}finally{
			log.debug("== Termina getWarehouseElementsByWarehouse/WarehouseElementBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getQuantityWarehouseElementsDetailsByFilters(co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	public WareHouseElementCustomerResponse getWarehouseElementsByWarehouseCustomerActual(WareHouseElementClientFilterRequestDTO request,RequestCollectionInfo requestCollInfo) throws BusinessException{
		
		log.debug("== Inicia getWarehouseElementsByWarehouseCustomerActual/WarehouseElementBusinessBean ==");
		UtilsBusiness.assertNotNull(request, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(request.getCustomerId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(request.isActualElements(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			WareHouseElementCustomerResponse response = this.daoWarehouseElement.getWarehouseElementsByWarehouseCustomerActual(request,requestCollInfo);
			List<WarehouseElementCustomerDTO> listWarehouseVOs = new ArrayList<WarehouseElementCustomerDTO>();
			
			for(WarehouseElement warehouseElement: response.getWareHouseElements()){
				WarehouseElementCustomerDTO dto = new WarehouseElementCustomerDTO();
				WarehouseVO warehouseVO = UtilsBusiness.copyObject(WarehouseVO.class, warehouseElement.getWarehouseId());
				this.warehouseBusinessBean.genWareHouseName(warehouseVO);
				
				dto.setWareHouseName(warehouseVO.getWarehouseName());
				dto.setQuantity(warehouseElement.getActualQuantity());
				if(warehouseElement.getSerialized()!=null){
					String model = warehouseElement.getSerialized().getElement().getElementType().getElementModel().getModelCode()+" / ";
					model += warehouseElement.getSerialized().getElement().getElementType().getElementModel().getModelName();
					dto.setModelName(model);
					
					String elementTypeName = warehouseElement.getSerialized().getElement().getElementType().getTypeElementCode()+" / ";
					elementTypeName += warehouseElement.getSerialized().getElement().getElementType().getTypeElementName();
					dto.setElementTypeName(elementTypeName);
					
					dto.setSerial(warehouseElement.getSerialized().getSerialCode());
					dto.setRid(warehouseElement.getSerialized().getIrd());
					if(warehouseElement.getSerialized().getSerialized() != null){
						dto.setSerialLinked(warehouseElement.getSerialized().getSerialized().getSerialCode());
					}
				}else{
					String model = warehouseElement.getNotSerialized().getElement().getElementType().getElementModel().getModelCode()+" / ";
					model += warehouseElement.getNotSerialized().getElement().getElementType().getElementModel().getModelName();
					dto.setModelName(model);
					
					String elementTypeName = warehouseElement.getNotSerialized().getElement().getElementType().getTypeElementCode()+" / ";
					elementTypeName += warehouseElement.getNotSerialized().getElement().getElementType().getTypeElementName();
					dto.setElementTypeName(elementTypeName);
				}
				listWarehouseVOs.add(dto);
			}
			response.setWareHouseElements(null);
			response.setWareHouseElementsDto(listWarehouseVOs);
			return response;
		}catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getWarehouseElementsByWarehouseCustomerActual/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		}finally{
			log.debug("== Termina getWarehouseElementsByWarehouseCustomerActual/WarehouseElementBusinessBean ==");
		}
	}
	/**
	 * Metodo: Construye el objeto que encapsula la informaciÃ³n de las cantidades de un objeto
	 * @param whElementQty objeto que encapsula la informaciÃ³n consultada de la persistencia
	 * @return Objeto con la informaciÃ³n general del objeto en bodega
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 * @author jjimenezh
	 */
	private WHElementQtySummaryVO buildBasicWHElementQtySummaryInfo(
			WarehouseElementQuantity whElementQty) throws DAOServiceException, DAOSQLException {
		WHElementQtySummaryVO result = new WHElementQtySummaryVO();
		Long elementTypeId = whElementQty.getElementTypeId();
		Long warehouseId = whElementQty.getWarehouseId();

		Warehouse wh = daoWarehouse.getWarehouseByID(warehouseId);
		ElementType et = daoElementType.getElementTypeByID(elementTypeId);

		result.setDealerCode("" + wh.getDealerId().getDealerCode());
		result.setDealerCountryCode(wh.getDealerId().getPostalCode().getCity().getState().getCountry().getCountryCode());
		result.setDealerCountryId(wh.getDealerId().getPostalCode().getCity().getState().getCountry().getId());
		result.setDealerId(wh.getDealerId().getId());
		result.setDealerName(wh.getDealerId().getDealerName());
		result.setElementTypeCode(et.getTypeElementCode());
		result.setElementTypeId(elementTypeId);
		result.setElementTypeName(et.getTypeElementName());
		result.setMeasureUnitCode(et.getMeasureUnit().getUnitCode());
		result.setMeasureUnitName(et.getMeasureUnit().getUnitName());
		result.setWarehouseCode(wh.getWhCode());
		result.setWarehouseId(warehouseId);
		if(wh.getDealerId().getDealer() != null){
			result.setBranchCode("" + wh.getDealerId().getDealer().getDealerCode());
			result.setBranchCountryCode(wh.getDealerId().getDealer().getPostalCode().getCity().getState().getCountry().getCountryCode());
			result.setBranchCountryId(wh.getDealerId().getDealer().getPostalCode().getCity().getState().getCountry().getId());
			result.setBranchId(wh.getDealerId().getDealer().getId());
			result.setBranchName(wh.getDealerId().getDealer().getDealerName());
		} else {
			result.setBranchCode(null);
			result.setBranchCountryCode(null);
			result.setBranchCountryId(null);
			result.setBranchId(null);
			result.setBranchName(null);
		}

		return result;
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getWhElementsByWhIdAndElementTypeCodeAndSerials(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseElementVO> getWhElementsByWhIdAndElementTypeCodeAndSerials(
			Long warehouseId, Long elementTypeId1, Long elementTypeId2,
			String serialEl1, String serialEl2) throws BusinessException {
		log.debug("== Inicia getWhElementsByWhIdAndElementTypeCodeAndSerials/WarehouseElementBusinessBean ==");		
		try {
			UtilsBusiness.assertNotNull(warehouseId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(elementTypeId1, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(elementTypeId2, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(serialEl1, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(serialEl2, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

			List<WarehouseElement> whElementPojos = daoWarehouseElement.getWhElementsByWhIdAndElementTypeCodeAndSerials(warehouseId, elementTypeId1, elementTypeId2, serialEl1, serialEl2);

			return UtilsBusiness.convertList(whElementPojos, WarehouseElementVO.class);
		}catch( Throwable ex ){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getWhElementsByWhIdAndElementTypeCodeAndSerials/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#separateLinkedSerializedElementsInWh(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void separateLinkedSerializedElementsInWh(Long warehouseId,
			Long whElementMasterId, Long whElementLinkedId)
	throws BusinessException {
		log.debug("== Inicia separateLinkedSerializedElementsInWh/WarehouseElementBusinessBean ==");		
		try {
			
			UtilsBusiness.assertNotNull(whElementMasterId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(whElementLinkedId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

			//Ajuste realizado para acoplar el metodo el elementId de los elementos que el lo que viene como parametro
			Serialized firstSerializedElement = daoSerialized.getSerializedElementByElementId(whElementMasterId);
			Serialized secondSerializedElement = daoSerialized.getSerializedElementByElementId(whElementLinkedId);

			UtilsBusiness.assertNotNull(firstSerializedElement, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(secondSerializedElement, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			if(firstSerializedElement.getSerialized()!=null&&firstSerializedElement.getSerialized().getSerialCode().equalsIgnoreCase(secondSerializedElement.getSerialCode())){
				firstSerializedElement.setSerialized(null);
				daoSerialized.updateSerialized(firstSerializedElement);
			}else if(secondSerializedElement.getSerialized()!=null && secondSerializedElement.getSerialized().getSerialCode().equalsIgnoreCase(firstSerializedElement.getSerialCode())){
				secondSerializedElement.setSerialized(null);
				daoSerialized.updateSerialized(secondSerializedElement);
			}else{
				log.error("Lo elementos de bodega especificados con ids: "+whElementMasterId+" y "+whElementLinkedId+"  no estÃ¡n vinculados");
				throw new BusinessException(ErrorBusinessMessages.WH_ELELEMTS_ARENT_LINKED.getCode(), ErrorBusinessMessages.WH_ELELEMTS_ARENT_LINKED.getMessage());
			}

		}catch( Throwable ex ){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n separateLinkedSerializedElementsInWh/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		}
	}
	//Modificado para Requerimiento: CC057
	private void sendMailNotSerializedElementsQualityControl(List<ElementVO> allElements, List<String> recipients, String mailTypeCode, String sourceUser, Warehouse targetWareouse) throws BusinessException {
		StringBuffer elementInfo = new StringBuffer("\n");

		if(allElements == null || allElements.isEmpty()){
			log.error("No se enviarÃ¡ el correo debido a que se recibiÃ³ la lista de elementos de bodega vacÃ­a");
			return;
		}

		for (ElementVO whElement : allElements) {
			elementInfo.append(ApplicationTextEnum.TYPE_ELEMENT_CODE.getApplicationTextValue()+": ");
			elementInfo.append(whElement.getElementType().getTypeElementCode());
			elementInfo.append("\n");
			elementInfo.append(ApplicationTextEnum.ELEMENT_TYPE.getApplicationTextValue()+": ");
			elementInfo.append(whElement.getElementType().getTypeElementName());
			elementInfo.append("\n");
			elementInfo.append(ApplicationTextEnum.QUANTITY_MOVE.getApplicationTextValue()+": ");
			elementInfo.append(whElement.getMovedQuantity());
			elementInfo.append("\n");
			elementInfo.append(ApplicationTextEnum.WAREHOUSE_DESTINY_CODE.getApplicationTextValue()+": ");
			elementInfo.append(targetWareouse.getWhCode());
			elementInfo.append("\n");
			elementInfo.append(ApplicationTextEnum.WAREHOUSE_DESTINY_COUNTRY.getApplicationTextValue()+": ");
			elementInfo.append(targetWareouse.getCountry().getCountryName());
			elementInfo.append("\n\n");
		}
		elementInfo.append("\n");
		SendEmailDTO sendEmailDTO = new SendEmailDTO();
		sendEmailDTO.setNewsDocument(elementInfo.toString());
		sendEmailDTO.setNewsObservation(elementInfo.toString());
		sendEmailDTO.setNewsSourceUser(sourceUser);
		sendEmailDTO.setNewsType(mailTypeCode);
		sendEmailDTO.setRecipient(recipients);
		emailTypeBusinessBean.sendEmailByEmailTypeCodeAsic(sendEmailDTO, targetWareouse.getCountry().getId());
	}

	/**
	 * Metodo: <Descripcion>
	 * @param dealerId
	 * @param whTypeCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	private Warehouse getDealerWarehouse(Long dealerId,
			String whTypeCode) throws DAOServiceException, DAOSQLException, BusinessException {
		Warehouse qualityControlWhs = daoWarehouse.getWarehousesByDealerIdAndWhTypeCode(dealerId, whTypeCode);

		//Si no existen bodegas del tipo especificado asociadas al dealer especificado se lanza excepciÃ³n
		if(qualityControlWhs == null){
			log.error("No se encontraron bodegas del tipo: " + whTypeCode + " asociadas al dealer con id: " + dealerId);
			throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
		}
		
		return qualityControlWhs;
	}

	

	/**
	 * Metodo: <Descripcion>
	 * @param sourceWarehouseId
	 * @param targetWarehouseId
	 * @return
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	private MassiveMovementBetweenWareHouseDTO buildMasivMovDto(
			long sourceWarehouseId, long targetWarehouseId) throws BusinessException, DAOServiceException, DAOSQLException {
		MassiveMovementBetweenWareHouseDTO result = new MassiveMovementBetweenWareHouseDTO();
		Warehouse sourceWh = daoWarehouse.getWarehouseByID(sourceWarehouseId);
		assertNotNull(sourceWh, ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());

		result.setWareHouseSource(UtilsBusiness.copyObject(WarehouseVO.class, sourceWh));

		Warehouse targetWh = daoWarehouse.getWarehouseByID(targetWarehouseId);
		assertNotNull(sourceWh, ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());

		result.setWareHouseTarget(UtilsBusiness.copyObject(WarehouseVO.class, targetWh));

		List<ElementVO> elements = new ArrayList<ElementVO>();
		result.setListObjectToMove(elements);

		return result;
	}

	/**
	 * Metodo: <Descripcion>
	 * @param whElement
	 * @param warehouse
	 * @throws BusinessException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 */
	private void validateWhElement(WarehouseElement whElement, Warehouse warehouse) throws BusinessException, PropertiesException {
		assertNotNull(whElement, ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getMessage());
		//Si el estado del registro de elemento en bodega es histÃ³rico se lanza un error
		if(whElement.getRecordStatus().getRecordStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.RECORD_STATUS_HISTORIC.getCodeEntity())){
			log.error("El estado del registro actual del elemento de bodega es histÃ³rico, por tanto invÃ¡lido para esta operaciÃ³n");
			throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_ELEMENT_ISNT_THE_FINAL.getCode(), ErrorBusinessMessages.WAREHOUSE_ELEMENT_ISNT_THE_FINAL.getMessage());
		}
		//Si la bodega destino del elemento es diferente de la que deberÃ­a ser, se lanza un error
		if(whElement.getWarehouseId().getId().longValue() != warehouse.getId().longValue()){
			log.error("La bodega destino del elemento de bodega es diferente de la bodega especificada");
			throw new BusinessException(ErrorBusinessMessages.INVALID_TARGET_WAREHOUSE.getCode(), ErrorBusinessMessages.INVALID_TARGET_WAREHOUSE.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getQualityControlWhNotSerElementsByDealerId(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseElementVO> getQualityControlWhNotSerElementsByDealerId(
			Long dealerId) throws BusinessException {
		log.debug("== Inicia getQualityControlWhNotSerElementsByDealerId/WarehouseElementBusinessBean ==");		
		try {
			assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			String qualityControlWhTypeCode = CodesBusinessEntityEnum.WAREHOUSE_TYPE_CALIDAD.getCodeEntity();

			Warehouse wh = getDealerWarehouse(dealerId, qualityControlWhTypeCode);
			List<WarehouseElement> whElementPojos = daoWarehouseElement.getLastWarehouseElementsByWHIdTarget(wh.getId());

			filterNotSerialized(whElementPojos);

			return UtilsBusiness.convertList(whElementPojos, WarehouseElementVO.class);
		}catch( Throwable ex ){
			log.error("== Error al tratar de ejecutar la operaciÃ³n getQualityControlWhNotSerElementsByDealerId/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		}
	}


	private void filterNotSerialized(List<WarehouseElement> whElementPojos) {
		CollectionUtils.filter(whElementPojos, new Predicate() {
			@Override
			public boolean evaluate(Object arg0) {
				if(arg0 instanceof WarehouseElement){
					return ((WarehouseElement)arg0).getNotSerialized() != null;
				}
				return false;
			}
		});
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getWarehouseElementsBySearchFilter(co.com.directv.sdii.model.vo.WhElementSearchFilterVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WareHouseElementResponse getWarehouseElementsBySearchFilter(WhElementSearchFilterVO whElementSearchFilterVO, RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getWarehouseElementsBySearchFilter/WarehouseElementBusinessBean ==");	
		UtilsBusiness.assertNotNull(whElementSearchFilterVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			if(whElementSearchFilterVO.getWareHouseId() == null && whElementSearchFilterVO.getDealerId() == null 
			   && whElementSearchFilterVO.getBranchDealerId() == null && whElementSearchFilterVO.getCrewId() == null 
			   && whElementSearchFilterVO.getWarehouseTypeId() == null && whElementSearchFilterVO.getElementTypeCode() == null 
			   && whElementSearchFilterVO.getElementModelId() == null && whElementSearchFilterVO.getElementTypeId() == null 
			   && whElementSearchFilterVO.getInitialDate() == null && whElementSearchFilterVO.getFinalDate() == null 
			   && whElementSearchFilterVO.getSerialElement() == null){
				log.debug("No se ha especificado ningÃºn criterio de filtro para relaizar la consulta");
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}

			WareHouseElementResponse response = daoWarehouseElement.getWarehouseElementsByFilters(whElementSearchFilterVO, requestCollInfo);
			List<WarehouseElement> whePojo = response.getWareHouseElements();        	
			List<WarehouseElementVO> warehouseElVOs = UtilsBusiness.convertList(whePojo, WarehouseElementVO.class);        	
			populateReferenceAndImportLogData(warehouseElVOs);
			response.setWareHouseElementsVO( warehouseElVOs );
			response.setWareHouseElements(null);

			return response;
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getWarehouseElementsBySearchFilter/ReferenceBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementsBySearchFilter/ReferenceBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getWareHouseElementHistoricalForSerializedElement(co.com.directv.sdii.model.vo.WhElementSearchFilterVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WareHouseElementHistoricalResponse getWareHouseElementHistoricalForSerializedElement( Long serializedId, RequestCollectionInfo requestCollInfo )throws BusinessException
	{
		log.debug("== Inicia getWareHouseElementHistoricalForSerializedElement/WarehouseElementBusinessBean ==");	
		UtilsBusiness.assertNotNull(serializedId,ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

		try {		
			WareHouseElementHistoricalResponse response = new WareHouseElementHistoricalResponse();
			WareHouseElementResponse responseDAO = daoWarehouseElement.getWareHouseElementHistoricalForSerializedElement( serializedId, requestCollInfo,false );
			List<WarehouseElement> listaPojos = responseDAO.getWareHouseElements();
			List<WareHouseElementHistoricalDTO> listToReturn = new ArrayList<WareHouseElementHistoricalDTO>();

			ImportLog importLog = daoImportLog.getImportLogByElementId(serializedId);

			for(WarehouseElement item:listaPojos){
				WareHouseElementHistoricalDTO dto = new WareHouseElementHistoricalDTO(UtilsBusiness.copyObject(WarehouseElementVO.class,item));

				dto.setImportLogId(importLog == null ? null : importLog.getId());
				if(importLog != null){
					dto.getWareHouseElement().setImportLogPurchaseOrder(importLog.getPurchaseOrder());
				}

				listToReturn.add(dto);  
			}
			if(requestCollInfo != null){
				populatePaginationInfo(response, responseDAO.getPageSize(), responseDAO.getPageIndex(), responseDAO.getRowCount(), responseDAO.getTotalRowCount());
			}
			response.setWareHouseHistorical(listToReturn);

			return response; 		
		} catch(Throwable ex){
			log.debug("== Error getWareHouseElementHistoricalForSerializedElement/ReferenceBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWareHouseElementHistoricalForSerializedElement/ReferenceBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal
	 * #getWareHouseElementHistoricalForSerializedElement(java.lang.String,
	 * co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MovedElementSerializedResponse getMovedWareHouseElementSerializedByLinkedOrSerialCode(
			String linkedOrSerialCode,Long countryId, RequestCollectionInfo requestCollInfo )
			throws BusinessException {
		
		log.debug("== Inicia MovedElementSerializedResponse/WarehouseElementBusinessBean ==");

		// Definicion de objetos y variables

		// Objeto utilizado para almacenar los MovedElementSerializedDTO que se
		// van a retornar
		MovedElementSerializedResponse response = new MovedElementSerializedResponse();
		List<MovedElementSerializedDTO> movedElementsSerializedDTO = new ArrayList<MovedElementSerializedDTO>();

		// Serial
		String serialCode;

		// RID
		String ird;

		// Serial Vinculado
		String serialCodeLinked;

		// Codigo Modelo
		String modelCode;

		// Nombre Modelo
		String modelName;

		// Codigo Tipo Elemento
		String typeElementCode;

		// nombre Tipo Elemento
		String typeElementName;

		// Causal de Movimiento
		String movTypeNameOut;

		// Tipo de Movimiento
		String movClassOut;

		// CompaÃ±Ã­a Origen
		String dealerNameCompanyOut;

		// Sucursal Origen
		String dealerNameBranchOut;

		// Cuadrilla Origen
		String isResponsibleOut;

		// Codigo / nombre Bodega origen
		String whCodeOut;

		// Fecha y hora de salida
		Date movementDateOut;

		// Tipo de Movimeinto
		String movTypeNameIn;

		// Tipo de Movimiento
		String movClassIn;

		// CompaÃ±Ã­a Destino
		String dealerNameCompanyIn;

		// Sucursal
		String dealerNameBranchIn;

		// Cuadrilla destino
		String isResponsibleIn;

		// Codigo / nombre Bodega destino
		String whCodeIn;

		// Fecha y hora de entrada
		Date movementDateIn;

		// Cantidad
		Double movedQuantity;
		
		//Tipo numero documento Entrada
		String typeDocumentIn;
		
		//Tipo numero documento Salida
		String typeDocumentOut;
		
		String woCode="";


		// Objetos Temporales
		Employee employee = null;
		Dealer dealer = null;
		Dealer dealerOfDealer = null;
		Serialized serialized = null;
		//ElementModel elementModel = null;
		//ElementType elementType = null;
		MovementType movementType = null;
		Warehouse warehouse = null;
		Customer customer = null;
		WarehouseVO warehouseVO = null;
		WareHouseElementResponse wareHouseElementResponse = null;

		try {

			// Se obtienen los elementos por bodega dependiendo de el serial o
			// el codigo del serial vinculado
			Serialized respons = daoSerialized.getSerializedBySerial(linkedOrSerialCode,countryId);
	
			// Condicion utilizado para almacenar los WarehouseElementVO segun si es
			// de entrada o salida
			if (respons != null) {

				wareHouseElementResponse = daoWarehouseElement
						.getWareHouseElementHistoricalForSerializedElement(respons.getElementId(), requestCollInfo,true);
				
				for (WarehouseElement warehouseElement : wareHouseElementResponse.getWareHouseElements()) {
					
				if (warehouseElement
							.getMovementType()
							.getMovClass()
							.equals(
									CodesBusinessEntityEnum.MOVEMENT_TYPE_TYPE_ENTRY
											.getCodeEntity())) {
					
					serialized = warehouseElement.getSerialized();
					
					movementType = warehouseElement.getMovementType();
					warehouse = warehouseElement.getWarehouseId();
	
					serialCode = serialized.getSerialCode();
					ird = serialized.getIrd();
					//serialCodeLinked = serialized.getSerialized() != null ? serialized.getSerialized().getSerialCode() : "";
					serialCodeLinked = warehouseElement.getLinkedSerId() != null ? warehouseElement.getLinkedSerId().getSerialCode() : "";
					modelCode = "";
					modelName = "";
					if(warehouseElement.getElementType() != null)
						if(warehouseElement.getElementType().getElementModel() != null){
							modelCode = warehouseElement.getElementType().getElementModel().getModelCode();
							modelName = warehouseElement.getElementType().getElementModel().getModelName();
						}
					typeElementCode = warehouseElement.getElementType() != null ? warehouseElement.getElementType().getTypeElementCode() : "";
					typeElementName = warehouseElement.getElementType() != null ? warehouseElement.getElementType().getTypeElementName() : "";
	
					movTypeNameIn = movementType.getMovTypeName();
					movClassIn = movementType.getMovClass();
	
					dealerNameCompanyIn = "";
					dealerNameBranchIn = "";
	
					// Si el dealer es el dueÃ±o de la bodega
					if (warehouse.getDealerId() != null) {
	
						dealer = warehouse.getDealerId();
						dealerOfDealer = warehouse.getDealerId().getDealer();
	
						if (dealerOfDealer == null) {
							dealerNameCompanyIn = dealer.getDepotCode() + " " + dealer.getDealerName();
							dealerNameBranchIn = "";
						} else {
							dealerNameCompanyIn = dealerOfDealer.getDepotCode() + " " + dealerOfDealer.getDealerName();
							dealerNameBranchIn = dealer.getDepotCode() + " " + dealer.getDealerName();
						}
					// Si la crew es el dueÃ±o de la bodega
					} else if (warehouse.getCrewId() != null) {
						dealer = warehouse.getCrewId().getDealer();
						dealerOfDealer = warehouse.getCrewId().getDealer()
								.getDealer();
	
						if (dealerOfDealer == null) {
							dealerNameCompanyIn = dealer.getDepotCode() + " " + dealer.getDealerName();
							dealerNameBranchIn = "";
						} else {
							dealerNameCompanyIn = dealerOfDealer.getDepotCode() + " " + dealerOfDealer.getDealerName();
							dealerNameBranchIn = dealer.getDepotCode() + " " + dealer.getDealerName();
						}
					//Si la Customer es el dueÃ±o de la bodega
					} else if (warehouse.getCustomerId() != null) {
						customer = warehouse.getCustomerId();
						dealerNameCompanyIn = customer.getCustomerCode() + " "
								+ customer.getFirstName() + " "
								+ customer.getLastName();
						dealerNameBranchIn = "";
					}
	
					isResponsibleIn="";
					if(warehouse.getCrewId() != null){
						employee = daoEmployeesCrew.getEmployeeResponsibleByCrewId(warehouse.getCrewId().getId());
						
						isResponsibleIn = employee.getFirstName() + " "
								+ employee.getLastName();
					}
					
					warehouseVO=UtilsBusiness.copyObject(WarehouseVO.class, warehouse);
					warehouseBusinessBean.genWareHouseName(warehouseVO);
					whCodeIn = warehouseVO.getWarehouseName();
					
					movementDateIn = warehouseElement.getMovementDate();
					
					typeDocumentIn= "";
					woCode = "";
					if(warehouseElement.getDocumentClass() != null){
						
						if(warehouseElement.getDocumentClass().getDocumentClassCode().equals(CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity()))
						{	
							if(warehouseElement.getDocumentId() != null){
								woCode = daoWorkOrder.getCodeWorkOrderByID(warehouseElement.getDocumentId());
							}
						}
						
						typeDocumentIn= warehouseElement.getDocumentClass().getDocumentClassName();
						if(woCode != null && !woCode.equals("")){
							typeDocumentIn= typeDocumentIn + " - " +woCode;
						}else if(warehouseElement.getDocumentId() != null){
							typeDocumentIn= typeDocumentIn + " - " + warehouseElement.getDocumentId();
						}
					}
	
					movTypeNameOut="";
					movClassOut="";
					dealerNameCompanyOut="";
					dealerNameBranchOut="";
					isResponsibleOut="";
					whCodeOut="";
					movementDateOut=null;
					typeDocumentOut= "";
					woCode = "";
	
					// Si el elemento por bodega de salida contiene una entrada de
					// bodega
					if (warehouseElement.getSourceRecord() != null) {
	
						serialized = warehouseElement.getSourceRecord()
								.getSerialized();
						movementType = warehouseElement.getSourceRecord()
								.getMovementType();
						warehouse = warehouseElement.getSourceRecord()
								.getWarehouseId();
	
						movTypeNameOut = movementType.getMovTypeName();
						movClassOut = movementType.getMovClass();
	
						dealerNameCompanyOut = "";
						dealerNameBranchOut = "";
	
						//Si el dealer es el dueÃ±o de la bodega
						if (warehouse.getDealerId() != null) {
	
							dealer = warehouse.getDealerId();
							dealerOfDealer = warehouse.getDealerId().getDealer();
	
							if (dealerOfDealer == null) {
								dealerNameCompanyOut = dealer.getDepotCode() + " " + dealer.getDealerName();
								dealerNameBranchOut = "";
							} else {
								dealerNameCompanyOut = dealerOfDealer.getDepotCode() + " " + dealerOfDealer
										.getDealerName();
								dealerNameBranchOut = dealer.getDepotCode() + " " + dealer.getDealerName();
							}
						//Si la crew es el dueÃ±o de la bodega
						} else if (warehouse.getCrewId() != null) {
							dealer = warehouse.getCrewId().getDealer();
							dealerOfDealer = warehouse.getCrewId().getDealer()
									.getDealer();
	
							if (dealerOfDealer == null) {
								dealerNameCompanyOut = dealer.getDepotCode() + " " + dealer.getDealerName();
								dealerNameBranchOut = "";
							} else {
								dealerNameCompanyOut = dealerOfDealer.getDepotCode() + " " + dealerOfDealer
										.getDealerName();
								dealerNameBranchOut = dealer.getDepotCode() + " " + dealer.getDealerName();
							}
						//Si la Customer es el dueÃ±o de la bodega
						} else if (warehouse.getCustomerId() != null) {
							customer = warehouse.getCustomerId();
							dealerNameCompanyOut = customer.getCustomerCode() + " "
									+ customer.getFirstName() + " "
									+ customer.getLastName();
							dealerNameBranchOut = "";
						}
	
						isResponsibleOut="";
						if(warehouse.getCrewId() != null){
							employee = daoEmployeesCrew.getEmployeeResponsibleByCrewId(warehouse.getCrewId().getId());
							isResponsibleOut = employee.getFirstName() + " "
									+ employee.getLastName();
						}
						
						warehouseVO=UtilsBusiness.copyObject(WarehouseVO.class, warehouse);
						warehouseBusinessBean.genWareHouseName(warehouseVO);
						whCodeOut = warehouseVO.getWarehouseName();

						movementDateOut = warehouseElement.getSourceRecord().getMovementDate();
						
						typeDocumentOut= "";
						woCode = "";
						if(warehouseElement.getSourceRecord().getDocumentClass() != null){
							
							if(warehouseElement.getSourceRecord().getDocumentClass().getDocumentClassCode().equals(CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity()))
							{	
								if(warehouseElement.getSourceRecord().getDocumentId() != null){
									woCode = daoWorkOrder.getCodeWorkOrderByID(warehouseElement.getSourceRecord().getDocumentId());
								}
							}
							
							typeDocumentOut= warehouseElement.getSourceRecord().getDocumentClass().getDocumentClassName();
							if(woCode != null && !woCode.equals("")){
								typeDocumentOut= typeDocumentOut + " - " +woCode;
							}else if(warehouseElement.getSourceRecord().getDocumentId() != null){
								typeDocumentOut= typeDocumentOut + " - " + warehouseElement.getSourceRecord().getDocumentId();
							}
						}
						
					}
					movedQuantity = warehouseElement.getMovedQuantity();
	
					MovedElementSerializedDTO movedElementSerializedDTO = new MovedElementSerializedDTO(
							serialCode, ird, serialCodeLinked, modelCode,
							modelName, typeElementCode, typeElementName,
							movTypeNameOut, movClassOut, dealerNameCompanyOut,
							dealerNameBranchOut, isResponsibleOut, whCodeOut,
							movementDateOut, movTypeNameIn, movClassIn,
							dealerNameCompanyIn, dealerNameBranchIn,
							isResponsibleIn, whCodeIn, movementDateIn,
							movedQuantity,typeDocumentIn,typeDocumentOut);
	
					//Se almacena el movimiento del elemento en bodega
					movedElementsSerializedDTO.add(movedElementSerializedDTO);
				}
	
				if (warehouseElement
						.getMovementType()
						.getMovClass()
						.equals(
								CodesBusinessEntityEnum.MOVEMENT_TYPE_TYPE_EXIT
										.getCodeEntity())) {
					movTypeNameOut = "";
					movClassOut = "";
					dealerNameCompanyOut = "";
					dealerNameBranchOut = "";
					isResponsibleOut = "";
					whCodeOut = "";
					movementDateOut = null;
					typeDocumentOut="";
					woCode = "";
					movTypeNameIn = "";
					movClassIn = "";
					dealerNameCompanyIn = "";
					dealerNameBranchIn = "";
					isResponsibleIn = "";
					whCodeIn = "";
					movementDateIn = null;
					woCode = "";
					typeDocumentIn="";
	
					serialized = warehouseElement.getSerialized();
					movementType = warehouseElement.getMovementType();
					warehouse = warehouseElement.getWarehouseId();
	
					serialCode = serialized.getSerialCode();
					ird = serialized.getIrd();
					//serialCodeLinked = serialized.getSerialized() != null ? serialized.getSerialized().getSerialCode() : "";
					serialCodeLinked = warehouseElement.getLinkedSerId() != null ? warehouseElement.getLinkedSerId().getSerialCode() : "";
					
					modelCode = "";
					modelName = "";
					if(warehouseElement.getElementType() != null)
						if(warehouseElement.getElementType().getElementModel() != null){
							modelCode = warehouseElement.getElementType().getElementModel().getModelCode();
							modelName = warehouseElement.getElementType().getElementModel().getModelName();
						}
					typeElementCode = warehouseElement.getElementType() != null ? warehouseElement.getElementType().getTypeElementCode() : "";
					typeElementName = warehouseElement.getElementType() != null ? warehouseElement.getElementType().getTypeElementName() : "";
	
					movTypeNameOut = movementType.getMovTypeName();
					movClassOut = movementType.getMovClass();
	
					dealerNameCompanyOut = "";
					dealerNameBranchOut = "";
	
					//Si el dealer es el dueÃ±o de la bodega
					if (warehouse.getDealerId() != null) {
	
						dealer = warehouse.getDealerId();
						dealerOfDealer = warehouse.getDealerId().getDealer();
	
						if (dealerOfDealer == null) {
							dealerNameCompanyOut = dealer.getDepotCode() + " " + dealer.getDealerName();
							dealerNameBranchOut = "";
						} else {
							dealerNameCompanyOut = dealerOfDealer.getDepotCode() + " " + dealerOfDealer.getDealerName();
							dealerNameBranchOut = dealer.getDepotCode() + " " + dealer.getDealerName();
						}
					//Si la crew es el dueÃ±o de la bodega
					} else if (warehouse.getCrewId() != null) {
						dealer = warehouse.getCrewId().getDealer();
						dealerOfDealer = warehouse.getCrewId().getDealer()
								.getDealer();
	
						if (dealerOfDealer == null) {
							dealerNameCompanyOut = dealer.getDepotCode() + " " + dealer.getDealerName();
							dealerNameBranchOut = "";
						} else {
							dealerNameCompanyOut = dealerOfDealer.getDepotCode() + " " + dealerOfDealer.getDealerName();
							dealerNameBranchOut = dealer.getDepotCode() + " " + dealer.getDealerName();
						}
					//Si la Customer es el dueÃ±o de la bodega
					} else if (warehouse.getCustomerId() != null) {
						customer = warehouse.getCustomerId();
						dealerNameCompanyOut = customer.getCustomerCode() + " "
								+ customer.getFirstName() + " "
								+ customer.getLastName();
						dealerNameBranchOut = "";
					}
	
					isResponsibleOut="";
					if(warehouse.getCrewId() != null){
						employee = daoEmployeesCrew.getEmployeeResponsibleByCrewId(warehouse.getCrewId().getId());
						isResponsibleOut = employee.getFirstName() + " "
								+ employee.getLastName();
					}
					
					warehouseVO=UtilsBusiness.copyObject(WarehouseVO.class, warehouse);
					warehouseBusinessBean.genWareHouseName(warehouseVO);
					whCodeOut = warehouseVO.getWarehouseName();

					movementDateOut = warehouseElement.getMovementDate();
					
					typeDocumentOut= "";
					woCode = "";
					if(warehouseElement.getDocumentClass() != null){
						
						if(warehouseElement.getDocumentClass().getDocumentClassCode().equals(CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity()))
						{	
							if(warehouseElement.getDocumentId() != null){
								woCode = daoWorkOrder.getCodeWorkOrderByID(warehouseElement.getDocumentId());
							}
						}
						
						typeDocumentOut= warehouseElement.getDocumentClass().getDocumentClassName();
						if(woCode != null && !woCode.equals("")){
							typeDocumentOut= typeDocumentOut + " - " +woCode;
						}else if(warehouseElement.getDocumentId() != null){
							typeDocumentOut= typeDocumentOut + " - " + warehouseElement.getDocumentId();
						}
					}
					
					movedQuantity = warehouseElement.getMovedQuantity();
	
					MovedElementSerializedDTO movedElementSerializedDTO = new MovedElementSerializedDTO(
							serialCode, ird, serialCodeLinked, modelCode,
							modelName, typeElementCode, typeElementName,
							movTypeNameOut, movClassOut, dealerNameCompanyOut,
							dealerNameBranchOut, isResponsibleOut, whCodeOut,
							movementDateOut, movTypeNameIn, movClassIn,
							dealerNameCompanyIn, dealerNameBranchIn,
							isResponsibleIn, whCodeIn, movementDateIn,
							movedQuantity,typeDocumentIn,typeDocumentOut);
					//Se almacena el movimiento del elemento en bodega
					movedElementsSerializedDTO.add(movedElementSerializedDTO);
				}
			}
			}
		} catch (Throwable ex) {
			log.debug(
							"== Error MovedElementSerializedResponse/WarehouseElementBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina MovedElementSerializedResponse/WarehouseElementBusinessBean ==");
		}
		
		if(requestCollInfo != null && wareHouseElementResponse!= null)
			populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), movedElementsSerializedDTO.size(), wareHouseElementResponse.getTotalRowCount() );
		Collections.sort(movedElementsSerializedDTO);
		response.setMovedElementSerialized(movedElementsSerializedDTO);
		return response;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal#getSerializedWhElementsByCriteria(java.lang.Long,java.lang.Long,java.lang.Long,java.lang.Long,java.lang.Double)
	 */
	public SerializedWhElementsByCriteriaPaginationResponse getSerializedWhElementsByCriteria(Long warehouseId , Long typeId, Long modelId, RequestCollectionInfo requestCollectionInfo)throws BusinessException{
		log.debug("== Inicia getSerializedWhElementsByCriteria/WarehouseElementBusinessBean ==");
		SerializedWhElementsByCriteriaPaginationResponse r = null;
		List<WarehouseElementVO> wheVO;
		UtilsBusiness.assertNotNull(warehouseId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(typeId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(modelId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			r = this.daoWarehouseElement.getSerializedWhElementsByCriteria(warehouseId ,typeId, modelId, requestCollectionInfo);
			if(r != null && r.getCollection() != null && !r.getCollection().isEmpty()){
				wheVO = UtilsBusiness.convertList(r.getCollection(), WarehouseElementVO.class) ;
				r.setCollection(null);
				r.setCollectionVO(wheVO);
			} else{
				log.debug("== Error al tratar de ejecutar la operaciÃ³n getWhElementsByCriteria/WarehouseElementBusinessBean ==");
				throw new BusinessException(ErrorBusinessMessages.NOT_ELEMENTS_EXIST.getCode(),ErrorBusinessMessages.NOT_ELEMENTS_EXIST.getMessage());
			}        	        	
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getSerializedWhElementsByCriteria/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getSerializedWhElementsByCriteria/WarehouseElementBusinessBean ==");
		}
		return r;
	}



	/**
	 * @param obj
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void mergeWarehouseElement(WarehouseElementVO obj) throws BusinessException {
		log.debug("== Inicia mergeWarehouseElement/WarehouseElementBusinessBean ==");
		UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			WarehouseElement objPojo =  UtilsBusiness.copyObject(WarehouseElement.class, obj);
			daoWarehouseElement.mergeWarehouseElement(objPojo);
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n mergeWarehouseElement/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina mergeWarehouseElement/WarehouseElementBusinessBean ==");
		}

	}

	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getSerializedsAndWhEntryDateByWareHouseId(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<SerializedVO> getSerializedsAndWhEntryDateByWareHouseId( WarehouseVO warehouseId ) throws BusinessException
	{
		log.debug("== Inicia getSerializedsAndWhEntryDateByWareHouseId/WarehouseElementBusinessBean ==");		

		UtilsBusiness.assertNotNull(warehouseId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(warehouseId.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<SerializedVO> lista = null;

		try {
			List<Object[]> daoResponse = null;
			Warehouse objPojo = UtilsBusiness.copyObject(Warehouse.class, warehouseId);
			daoResponse = daoSerialized.getSerializedsAndWhEntryDateByWareHouseId(objPojo);
			if(daoResponse != null){
				lista = new ArrayList<SerializedVO>();
				for(Object [] obj : daoResponse){
					SerializedVO serializedVO = UtilsBusiness.copyObject(SerializedVO.class, (Serialized) obj[0]);
					serializedVO.setWarehouseEntryDate((Date) obj[1]);
					lista.add(serializedVO);
				}
			}
		}catch(Throwable ex) {
			log.debug("== Error al tratar de ejecutar la tarea getSerializedsAndWhEntryDateByWareHouseId/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		}
		return lista;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getSerializedsAndWhEntryDateByWareHouseId(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<NotSerPartialRetirementVO> getNotSerPartialRetirementByElementId(Long elementId)  throws BusinessException
	{
		log.debug("== Inicia getNotSerPartialRetirementByElementId/WarehouseElementBusinessBean ==");		
		UtilsBusiness.assertNotNull(elementId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		String strTemp;
		try{
			List<NotSerPartialRetirementVO> lista = UtilsBusiness.convertList( daoNotSerPartialRetirement.getNotSerPartialRetirementByElementId( elementId ), NotSerPartialRetirementVO.class);
			for (NotSerPartialRetirementVO notSerPartialRetirement : lista) {
				//Bodega Origen
				WarehouseVO warehouseSVO = UtilsBusiness.copyObject(WarehouseVO.class, notSerPartialRetirement.getWarehouseSource());
				warehouseBusinessBean.genWareHouseName( warehouseSVO );
				strTemp="";
				if(warehouseSVO != null)
					strTemp=warehouseSVO.getWarehouseName();
				notSerPartialRetirement.setWarehouseNameSource( strTemp );
				//Bodega destino
				WarehouseVO warehouseTVO = UtilsBusiness.copyObject(WarehouseVO.class, notSerPartialRetirement.getWarehouseTarget());
				warehouseBusinessBean.genWareHouseName( warehouseTVO );
				strTemp="";
				if(warehouseTVO != null)
					strTemp=warehouseTVO.getWarehouseName();
				notSerPartialRetirement.setWarehouseNameTarget( strTemp );
			}
			return lista;
		}catch( Throwable ex ) {
			log.debug("== Error al tratar de ejecutar la tarea getNotSerPartialRetirementByElementId/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#moveNotSerElementBetweenCustomerWhAndDealerWh(java.lang.String, java.lang.Long, java.lang.Long, java.lang.Double, co.com.directv.sdii.model.pojo.Warehouse)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void moveNotSerElementBetweenCustomerWhAndDealerWh (MovementElementDTO dto, boolean customerIsSource)throws BusinessException{
		log.debug("== Inicia moveNotSerElementBetweenCustomerWhAndDealerWh/WarehouseElementBusinessBean ==");
		UtilsBusiness.assertNotNull(dto, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getQuantity(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getTargetWs(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getElementTypeId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	
		try {
			if(customerIsSource){
				try{
					Double quantity = this.daoWarehouseElement.getWarehouseElementQuantityByElementType(dto.getSourceWh().getId(), dto.getElementTypeId());
				
					if(quantity > 0){
						this.businessMovementElement.moveNotSerializedElementBetweenWarehouse(dto);
					}else{
						this.adjustmentBusinessBean.createAdjustmentForEntry(dto);
					}
				} catch (Throwable e) {
					throw this.manageException(e);
				}
			}else{
				businessMovementElement.moveNotSerializedElementBetweenWarehouse(dto);
			}
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n moveNotSerElementBetweenCustomerWhAndDealerWh/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina moveNotSerElementBetweenCustomerWhAndDealerWh/WarehouseElementBusinessBean ==");
		}
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#moveSerElementBetweenCustomerWhAndDealerWh(java.lang.String, java.lang.Long, java.lang.String, java.lang.String, co.com.directv.sdii.model.pojo.Warehouse)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void moveSerElementBetweenCustomerWhAndDealerWh (MovementElementDTO dto, boolean customerIsSource)throws BusinessException{
		log.debug("== Inicia moveSerElementBetweenCustomerWhAndDealerWh/WarehouseElementBusinessBean ==");
		UtilsBusiness.assertNotNull(dto, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getSourceWh(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getTargetWs(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getSerialized(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		try {
			dto.setQuantity( 1D );
			businessMovementElement.moveSerializedElementBetweenWarehouse(dto);
		
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n moveSerElementBetweenCustomerWhAndDealerWh/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina moveSerElementBetweenCustomerWhAndDealerWh/WarehouseElementBusinessBean ==");
		}
	}
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MassiveMovementVO moveElementsFromWHCrewToWHCompany(List<DealerVO> companies, UserVO user) throws BusinessException {
		
		final List<DealerVO> companiesf = companies;
	
		log.debug("== Inicia moveElementsFromWHCrewToWHCompany/WarehouseElementBusinessBean ==");
		UtilsBusiness.assertNotNull(companies, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		final User userf;
		try {
		
			List<Long> dealerIds = new ArrayList<Long>();
			Long quantity=0L;
			
			for (DealerVO dealer : companies) {
				dealerIds.add(dealer.getId());
			}
			
			List<String> whTypeCode = UtilsBusiness.getListWarehouseAvailable();
			
			quantity= daoWarehouse.getQuantityWarehouseDealerByDealersAndWarehouseType(dealerIds,whTypeCode);
			//Si el dealer no contiene bodegas disponibles
			if(quantity.longValue() == 0){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN459.getCode(),ErrorBusinessMessages.STOCK_IN459.getMessage());
			}
			
			quantity= daoWarehouse.getQuantityWarehouseCrewByDealersAndWarehouseType(dealerIds,whTypeCode);
			//Si las cuadrillas del dealer no contiene bodegas disponibles
			if(quantity.longValue() == 0){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN460.getCode(),ErrorBusinessMessages.STOCK_IN460.getMessage());
			}

			quantity= daoWarehouseElement.getQuantityWarehouseElementByDealersAndWarehouseType(dealerIds,whTypeCode);
			//Si no existen elementos en las bodegas disponibles de las cuadrillas 
			if(quantity.longValue() == 0){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN461.getCode(),ErrorBusinessMessages.STOCK_IN461.getMessage());
			}
			
			userf = usersDao.getUserById(user.getId());
			//Comienza el proceso, almacena la informacion en massive_movements
			Date iniDateProcess = new Date();
			ProcessStatus processStatusI = processStatusDao.getProcessStatusByCode(CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_STARTED.getCodeEntity());
			final MassiveMovement process = storeMassiveMoveTransactions(userf, iniDateProcess, processStatusI);
		
				Runnable runnable = new Runnable() {
					public void run() {
						try {
							//Se especifica el tipo de bodega, en el caso en que el movimiento de elementos sea diferente a la Bodega de Tipo Stock 01
							moveElementsFromWHCrewToWHCompanyAvailable(companiesf, userf, process);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				new Thread(runnable).start();
		 
				return UtilsBusiness.copyObject(MassiveMovementVO.class, process);
		 
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n moveElementsFromWHCrewToWHCompany/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally{
			log.debug("== Termina moveElementsFromWHCrewToWHCompany/WarehouseElementBusinessBean ==");
		}
		
	}
	
	/**
	 * 
	 * Metodo: Almacena los mensajes y el estado del proceso del 
	 * movimiento masivo de elementos de las bodegas de las cuadrillas
	 * a el Stock de la Compania.
	 * @throws BusinessException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private MassiveMovement storeMassiveMoveTransactions(User user, Date iniP, ProcessStatus processStatus) throws BusinessException, PropertiesException, DAOServiceException, DAOSQLException{
		
		MassiveMovement objPojo = new MassiveMovement();
		objPojo.setUser(user);
		objPojo.setCreationDate(iniP);
		objPojo.setStartProcessDate(new Timestamp(new Date().getTime()));
		objPojo.setProcessStatus(processStatus);
		
		objPojo = daoMassiveMovement.createMassiveMovement(objPojo);
		
		return objPojo;
		
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#massiveMovementOfNotSerializedElementsBetweenWareHouse(co.com.directv.sdii.model.dto.MassiveMovementBetweenWareHouseDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void massiveMovementOfNotSerializedElementsBetweenWareHouse( MassiveMovementBetweenWareHouseDTO moveObject, Long documentId, String documentClass,Long userId)throws BusinessException {
		massiveMovementOfNotSerializedElementsBetweenWareHouse(moveObject, null, null, documentId, documentClass,userId);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void massiveMovementOfNotSerializedElementsBetweenWareHouse(MassiveMovementBetweenWareHouseDTO moveObject, 
			                                                           String movementCauseIn, 
			                                                           String movementCauseOut, 
			                                                           Long documentId, 
			                                                           String documentClass,
			                                                           Long userId)throws BusinessException {
		log.debug("== Inicia massiveMovementOfNotSerializedElementsBetweenWareHouse/WarehouseElementBusinessBean ==");

		try {
			
			User user = usersDao.getUserById(userId);
	       	if(user == null){
	       		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
	       	}
	       	
	       	Long sourceWhId = moveObject.getWareHouseSource() != null ? moveObject.getWareHouseSource().getId() : null;
			Long targetWhID = moveObject.getWareHouseTarget() != null ? moveObject.getWareHouseTarget().getId() : null;
			
	       	Warehouse warehouseSource = daoWarehouse.getWarehouseByID(sourceWhId);
	       	Warehouse warehouseTarget = daoWarehouse.getWarehouseByID(targetWhID);
	       	if(StringUtils.isBlank(movementCauseIn)) {
				movementCauseIn = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_BETWEEN_WH_ENTRY.getCodeEntity();
			}
			if(StringUtils.isBlank(movementCauseOut)) {
				movementCauseOut = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_BETWEEN_WH_EXIT.getCodeEntity();
			}
	       	MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementCauseIn,
	       																							 movementCauseOut);

			for( ElementVO whElement : moveObject.getListObjectToMove() ){
				
				if (whElement.getMovedQuantity() == null || whElement.getMovedQuantity() < 1 ){
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN431.getCode(), ErrorBusinessMessages.STOCK_IN431.getMessage());
				}
				
				
				
				Long elementTypeId = whElement.getElementType() != null ? whElement.getElementType().getId() : null;
				
				MovementElementDTO dtoMovement = new MovementElementDTO(user,
						UtilsBusiness.copyObject(WarehouseVO.class,  warehouseSource), 
						UtilsBusiness.copyObject(WarehouseVO.class,  warehouseTarget), 
						null, 
						elementTypeId, 
						null,
						documentId, 
						documentClass, 
						dtoGenerics.getMovTypeCodeE(), 
						dtoGenerics.getMovTypeCodeS(), 
						dtoGenerics.getRecordStatusU(),
						dtoGenerics.getRecordStatusH(),
						whElement.getMovedQuantity(),
						dtoGenerics.getMovConfigStatusPending(),
						dtoGenerics.getMovConfigStatusNoConfig());
				businessMovementElement.moveNotSerializedElementBetweenWarehouse(dtoMovement);
			}
		} catch (Throwable t){
			throw this.manageException(t);
		} finally {
			log.debug("== Termina massiveMovementOfNotSerializedElementsBetweenWareHouse/WarehouseElementBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#massiveMovementOfSerializedElementsBetweenWareHouse(co.com.directv.sdii.model.dto.MassiveMovementBetweenWareHouseDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void massiveMovementOfSerializedElementsBetweenWareHouse(MassiveMovementBetweenWareHouseDTO moveObject, String movementCauseIn, String movementCauseOut, Long documentId, String documentClass, User user)throws BusinessException{
		log.debug("== Inicia massiveMovementOfSerializedElementsBetweenWareHouse/WarehouseElementBusinessBean ==");
		try {
			String errorCode    = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
			String errorMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();

			UtilsBusiness.assertNotNull(moveObject,errorCode,errorMessage);
			UtilsBusiness.assertNotNull(moveObject.getListObjectToMove(),errorCode,errorMessage);
			UtilsBusiness.assertNotNull(moveObject.getWareHouseSource(),errorCode,errorMessage);
			UtilsBusiness.assertNotNull(moveObject.getWareHouseTarget(),errorCode,errorMessage);

			boolean reportIBS = false;
			String processCodeIBS = null;
			if(moveObject.getProcessCodeIBS()!=null){
				reportIBS = true;
				processCodeIBS = moveObject.getProcessCodeIBS();
			}
			if(StringUtils.isBlank(movementCauseIn)) {
				movementCauseIn = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_BETWEEN_WH_ENTRY.getCodeEntity();
			}
			if(StringUtils.isBlank(movementCauseOut)) {
				movementCauseOut = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_BETWEEN_WH_EXIT.getCodeEntity();
			}
			
			MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementCauseIn, movementCauseOut);
			for(ElementVO whElement : moveObject.getListObjectToMove()){
				
				MovementElementDTO dtoMovement = new MovementElementDTO(user, 
						                                                moveObject.getWareHouseSource(), 
						                                                moveObject.getWareHouseTarget(), 
						                                                new Serialized(whElement.getId()), 
						                                                documentId, 
						                                                documentClass, 
						                                                dtoGenerics.getMovTypeCodeE(), 
						                                                dtoGenerics.getMovTypeCodeS(),
						                                                dtoGenerics.getRecordStatusU(), 
						                                                dtoGenerics.getRecordStatusH(), 
						                                                reportIBS, 
						                                                processCodeIBS,
						                        						dtoGenerics.getMovConfigStatusPending(),
						                        						dtoGenerics.getMovConfigStatusNoConfig());
				businessMovementElement.moveSerializedElementBetweenWarehouse(dtoMovement);
			}
		}catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n massiveMovementOfSerializedElementsBetweenWareHouse/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		}finally{
			log.debug("== Termina massiveMovementOfSerializedElementsBetweenWareHouse/WarehouseElementBusinessBean ==");
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void massiveMovementOfSerializedAndLinkedElementsBetweenWareHouse(MassiveMovementBetweenWareHouseDTO moveObject, String movementCauseIn, String movementCauseOut, Long documentId, String documentClass, Long userId)throws BusinessException
	{
		log.debug("== Inicia massiveMovementOfSerializedElementsBetweenWareHouse/WarehouseElementBusinessBean ==");
		UtilsBusiness.assertNotNull(moveObject, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(moveObject.getListObjectToMove(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(moveObject.getWareHouseSource(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(moveObject.getWareHouseTarget(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(userId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		Warehouse warehouseSource = null;
		Warehouse warehouseTarget = null;
		User user = null;
		try {
			if(StringUtils.isBlank(movementCauseIn)) {
				movementCauseIn = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_BETWEEN_WH_ENTRY.getCodeEntity();
			}
			if(StringUtils.isBlank(movementCauseOut)) {
				movementCauseOut = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_BETWEEN_WH_EXIT.getCodeEntity();
			}
			
			warehouseSource = daoWarehouse.getWarehouseByID(moveObject.getWareHouseSource().getId());
			if(warehouseSource == null){
				throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(),ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
			}
				
			warehouseTarget = daoWarehouse.getWarehouseByID(moveObject.getWareHouseTarget().getId());
			if(warehouseTarget == null){
				throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(),ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
			}
			user = usersDao.getUserById(userId);
			if(user == null){
				throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			}
			
			MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementCauseIn, movementCauseOut);
			for(ElementVO whElement : moveObject.getListObjectToMove()){
				MovementElementDTO dtoMovement = new MovementElementDTO(user,
						UtilsBusiness.copyObject(WarehouseVO.class, warehouseSource), 
						UtilsBusiness.copyObject(WarehouseVO.class, warehouseTarget), 
						new Serialized(whElement.getSerializedElement().getElementId()), 
						documentId, 
						documentClass,
						dtoGenerics.getMovTypeCodeE(), 
						dtoGenerics.getMovTypeCodeS(),
						dtoGenerics.getRecordStatusU(), 
						dtoGenerics.getRecordStatusH(), 
						true, 
						CodesBusinessEntityEnum.PROCESS_CODE_IBS_OTHER.getCodeEntity(),
						dtoGenerics.getMovConfigStatusPending(),
						dtoGenerics.getMovConfigStatusNoConfig());
				businessMovementElement.moveSerializedElementBetweenWarehouse(dtoMovement);
				
			}
		}catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n massiveMovementOfSerializedElementsBetweenWareHouse/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		}finally{
			log.debug("== Termina massiveMovementOfSerializedElementsBetweenWareHouse/WarehouseElementBusinessBean ==");
		}
	}

	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void massiveMovementOfSerializedAndLinkedElementsBetweenWareHouse(MassiveMovementBetweenWareHouseDTO moveObject, Long documentId, String documentClass, Long userId)throws BusinessException {
		massiveMovementOfSerializedAndLinkedElementsBetweenWareHouse(moveObject, null, null, documentId, documentClass, userId);
	}

	
	

	
	@Override
	public void changeElementTypeSerializedElement(WarehouseElement warehouseElementMain, WarehouseElement warehouseElementLink, MovementType movementTypeE, MovementType movementTypeS, ElementType elementTypeNewMainElement, ElementType elementTypeNewLinkElement) throws BusinessException {
		log.debug("== Inicia changeElementTypeSerializedElement/WarehouseElementBusinessBean ==");

		WarehouseElement warehouseElement = null;
		WarehouseElement sourceWarehouseElementUMain = null;
		WarehouseElement sourceWarehouseElementULink = null;
		RecordStatus recordStatusU = null;
		RecordStatus recordStatusH = null;


		UtilsBusiness.assertNotNull(warehouseElementMain, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(elementTypeNewMainElement, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(movementTypeE, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(movementTypeS, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

		try {

			// Se realiza la validaciÃ³n que exista el estado del registro
			recordStatusU = daoRecordStatus.getRecordStatusByCode(CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			if (recordStatusU == null)
				throw new BusinessException(ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getCode(),ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getMessage());

			recordStatusH = daoRecordStatus.getRecordStatusByCode(CodesBusinessEntityEnum.RECORD_STATUS_HISTORIC.getCodeEntity());
			if (recordStatusH == null)
				throw new BusinessException(ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getCode(),ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getMessage());



			//Proceso de elemento principal
			// Se busca el registro en warehouse_elements principal
			sourceWarehouseElementUMain = daoWarehouseElement.getWarehouseElementByID(warehouseElementMain.getId());
			

			// Se actualiza el estado del registro en la bodega de origen a 'H'
			sourceWarehouseElementUMain.setRecordStatus(recordStatusH);
			daoWarehouseElement.updateWarehouseElement(sourceWarehouseElementUMain);

			// Se crea un nuevo registro en la bodega de origen es estado 'H' que represente la salida del elemento.
			// ActualQuantity=1 e InitialQuantity=1
			warehouseElement = this.createWarehouseElementRecord(sourceWarehouseElementUMain.getSerialized(), null, 0D, 1D, sourceWarehouseElementUMain.getWarehouseId(), movementTypeS, recordStatusH, sourceWarehouseElementUMain.getSerialized().getElement().getElementType(), 1D, null,null, null);

			// Se crea un nuevo registro en la bodega de destino es estado 'U' que represente la entrada del elemento.
			// ActualQuantity=1 e InitialQuantity=1
			sourceWarehouseElementUMain.getSerialized().getElement().setElementType(elementTypeNewMainElement);
			this.createWarehouseElementRecord(sourceWarehouseElementUMain.getSerialized(), null, 1D, 0D, sourceWarehouseElementUMain.getWarehouseId(), movementTypeE, recordStatusU, elementTypeNewMainElement, 1D, warehouseElement, null, null);


			//Proceso de elemento vinculado
			if(warehouseElementLink!=null&&!warehouseElementLink.getSerialized().getElement().getElementType().getTypeElementCode().equalsIgnoreCase(elementTypeNewLinkElement.getTypeElementCode())){
				// Se busca el registro en warehouse_elements principal
				sourceWarehouseElementULink = daoWarehouseElement.getWarehouseElementByID(warehouseElementLink.getId());
				

				// Se actualiza el estado del registro en la bodega de origen a 'H'
				sourceWarehouseElementULink.setRecordStatus(recordStatusH);
				daoWarehouseElement.updateWarehouseElement(sourceWarehouseElementULink);

				// Se crea un nuevo registro en la bodega de origen es estado 'H' que represente la salida del elemento.
				// ActualQuantity=1 e InitialQuantity=1
				warehouseElement = this.createWarehouseElementRecord(sourceWarehouseElementULink.getSerialized(), null, 0D, 1D, sourceWarehouseElementULink.getWarehouseId(), movementTypeS, recordStatusH, sourceWarehouseElementULink.getSerialized().getElement().getElementType(), 1D, null, null, null);

				// Se crea un nuevo registro en la bodega de destino es estado 'U' que represente la entrada del elemento.
				// ActualQuantity=1 e InitialQuantity=1
				sourceWarehouseElementULink.getSerialized().getElement().setElementType(elementTypeNewLinkElement);
				this.createWarehouseElementRecord(sourceWarehouseElementULink.getSerialized(), null, 1D, 0D, sourceWarehouseElementULink.getWarehouseId(), movementTypeE, recordStatusU, elementTypeNewLinkElement, 1D, warehouseElement, null, null);
			}


		} catch (Throwable t) {
			log.error(" == Error changeElementTypeSerializedElement/WarehouseElementBusinessBean ==");
			throw this.manageException(t);
		} finally {
			log.debug("== Termina changeElementTypeSerializedElement/WarehouseElementBusinessBean ==");
		}
	}
	
	
	/**
	 * MÃ©todo utilitario para actualizar la cantidad de una bodega de un elemento no serializado
	 * 
	 * Fecha de creaciÃ³n: 07/06/2011
	 * 
	 * @param targetWh
	 * @param quantity
	 * @param notSerializedElement
	 * @param movTypeCodeE
	 * @param movTypeCodeS
	 * @throws BusinessException
	 * 
	 * @author Jimmy VÃ©lez MuÃ±oz
	 */
	private void updateNotSerializedElementInWarehouse(WarehouseVO targetWh, Double quantityUpdate, NotSerializedVO notSerializedElement, String movTypeCodeE, String movTypeCodeS, Long documentId, String documentClass) throws BusinessException {
		log.debug("== Inicia moveNotSerializedElementToWarehouse/WarehouseElementBusinessBean ==");
		
		WarehouseElement warehouseElementU = null;
		MovementType movementType = null;
		RecordStatus recordStatusU = null;
		RecordStatus recordStatusH = null;
		Double initialQ = 0D;
		Double actualQ = 0D;
		String movementNew;

		UtilsBusiness.assertNotNull(targetWh, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(quantityUpdate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(notSerializedElement, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(movTypeCodeE, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

		try {
			
			if(quantityUpdate.longValue()>0){
				movementNew = movTypeCodeE;
			}else{
				movementNew = movTypeCodeS;
			}
			
			// Se realiza la validaciÃ³n que exista el tipo de movimiento
			movementType = daoMovementType.getMovementTypeByCode(movementNew);
			if (movementType == null)
				throw new BusinessException(ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getMessage());

			// Se realiza la validaciÃ³n que exista el estado del registro
			recordStatusU = daoRecordStatus.getRecordStatusByCode(CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			if (recordStatusU == null)
				throw new BusinessException(ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getCode(),ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getMessage());

			recordStatusH = daoRecordStatus.getRecordStatusByCode(CodesBusinessEntityEnum.RECORD_STATUS_HISTORIC.getCodeEntity());
			if (recordStatusH == null)
				throw new BusinessException(ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getCode(),ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getMessage());

			// Se consulta el elemento en la bodega de destino cuyo estado de registro sea 'U' 
			// y sea el de mayor fecha para calcular la cantidad actual del elemento
			warehouseElementU = daoWarehouseElement.getWareHouseElementByActualWhAndWhAndElement(targetWh.getId(), notSerializedElement.getElementId());
			
			if (warehouseElementU != null){
				initialQ = warehouseElementU.getActualQuantity();
				// Se cambia el estado del registro de 'U' a 'H'
				warehouseElementU.setRecordStatus(recordStatusH);
				daoWarehouseElement.updateWarehouseElement(warehouseElementU);
			}
			actualQ = initialQ + quantityUpdate;
			
			
			// Se crea un nuevo registro en la bodega de destino en estado 'U' que represente la entrada del elemento.
			this.createWarehouseElementRecord(null, notSerializedElement, actualQ, initialQ, targetWh, movementType, recordStatusU, null, Math.abs(quantityUpdate), null, documentId, documentClass);
			
		} catch (Throwable t) {
			log.error("== Error moveNotSerializedElementToWarehouse/WarehouseElementBusinessBean ==");
			throw this.manageException(t);
		} finally {
			log.debug("== Termina moveNotSerializedElementToWarehouse/WarehouseElementBusinessBean ==");
		}
	}

	
	/**
	 * MÃ©todo utilitario para determinar los WarehouseElement que pueden participar en un movimiento
	 * de elementos NO serializados.
	 * 
	 * Fecha de creaciÃ³n: 07/06/2011
	 * 
	 * @param warehouseElementList
	 * @param quantity
	 * @return
	 * @throws BusinessException
	 * 
	 * @author Jimmy VÃ©lez MuÃ±oz
	 */
	private List<WarehouseElement> getMovementRecordsFromList(List<WarehouseElement> warehouseElementList, Double quantity) throws BusinessException {
		
		List<WarehouseElement> warehouseElementListDef = new ArrayList<WarehouseElement>();
		UtilsBusiness.assertNotNull(warehouseElementList, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

		if(warehouseElementList.size() >= 1){
			//Se determina si con alguno de los elementos encontrados se puede satisfacer la cantidad a mover:
			boolean foundElement = false;
			for(WarehouseElement warehouseElement : warehouseElementList){
				if( warehouseElement.getActualQuantity().doubleValue() >= quantity.doubleValue()){
					warehouseElementListDef.add(warehouseElement);
					foundElement = true;
					break;
				}
			}

			//Si con ninguno de los elementos en la bodega del tipo especificado se surte el requerimiento de movimiento:
			//Se encuentran los elementos que deben ser movidos para superar la cantidad:
			if(! foundElement ){
				double totalQty = 0D;
				for (WarehouseElement warehouseElement : warehouseElementList) {
					if(totalQty >= quantity.doubleValue()){
						foundElement = true;
						break;
					}
					totalQty += warehouseElement.getActualQuantity();
					warehouseElementListDef.add(warehouseElement);
				}
			}

			//Valida que la cantidad pueda ser movida
			if(! foundElement){
				throw new BusinessException(ErrorBusinessMessages.ACTUAL_QUANTITY_LOWER_THAN_REQUIRED_MOVEMENT.getCode(), ErrorBusinessMessages.ACTUAL_QUANTITY_LOWER_THAN_REQUIRED_MOVEMENT.getMessage());
			}
		}
		return warehouseElementListDef;
	}
	
	
	
	
	/**
	 * MÃ©todo utilitario para crear un nuevo registro de WarehouseElement.
	 * 
	 * Fecha de creaciÃ³n: 07/06/2011
	 * 
	 * @param serializedElement
	 * @param notSerializedElement
	 * @param actualQ
	 * @param initialQ
	 * @param warehouse
	 * @param movementType
	 * @param recordStatus
	 * @param causeAdjustment
	 * @param elementType
	 * @param quantity
	 * @param sourceWarehouseElementRecord
	 * @throws BusinessException
	 * 
	 * @author Jimmy VÃ©lez MuÃ±oz
	 */
	private WarehouseElement createWarehouseElementRecord(Serialized serializedElement, NotSerialized notSerializedElement, Double actualQ, Double initialQ, Warehouse warehouse, 
			MovementType movementType, RecordStatus recordStatus, 
			ElementType elementType, Double quantity, WarehouseElement sourceWarehouseElementRecord, Long documentId, String documentClass) throws BusinessException {
		log.debug("== Inicia createWarehouseElementRecord/WarehouseElementBusinessBean ==");
		WarehouseElement warehouseElement;
		try {
			warehouseElement = new WarehouseElement();
			warehouseElement.setNotSerialized(notSerializedElement);
			warehouseElement.setSerialized(serializedElement);
			warehouseElement.setActualQuantity(actualQ);
			warehouseElement.setInitialQuantity(initialQ);
			warehouseElement.setWarehouseId(warehouse);
			warehouseElement.setMovementType(movementType);
			warehouseElement.setMovementDate(new Date());
			warehouseElement.setRecordStatus(recordStatus);
			
			// asigna el elemento vinculado
			if(serializedElement != null){
			    warehouseElement.setLinkedSerId(serializedElement.getSerialized());
			}
			
			DocumentClass documentClassPojo = getDocumentClassByCode(documentClass);
			if(documentClassPojo!=null){
				warehouseElement.setDocumentId(documentId);
				warehouseElement.setDocumentClass(documentClassPojo);
			}
			
			if (notSerializedElement != null &&notSerializedElement.getElement()!=null && (elementType == null || elementType.getId() == null || elementType.getId().longValue() == 0)){
				elementType = notSerializedElement.getElement().getElementType(); 
			}
			
			if (serializedElement != null && serializedElement.getElement() != null && (elementType == null || elementType.getId() == null || elementType.getId().longValue() == 0)){
				elementType = serializedElement.getElement().getElementType(); 
			}
			warehouseElement.setElementType(elementType);
			warehouseElement.setMovedQuantity(quantity);
			warehouseElement.setSourceRecord(sourceWarehouseElementRecord);
		
			warehouseElement = daoWarehouseElement.createWarehouseElement(warehouseElement);
			return warehouseElement;
		} catch (Throwable t){
			log.debug("== Error createWarehouseElementRecord/WarehouseElementBusinessBean ==");
			throw this.manageException(t);
		} finally {
			log.debug("== Termina createWarehouseElementRecord/WarehouseElementBusinessBean ==");
		}
	}


	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#moveNotSerializedElementBetweenWareHouseQuality(java.util.List, java.util.List, co.com.directv.sdii.model.pojo.Dealer)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void moveNotSerializedElementBetweenWareHouseQuality(
			List<ElementVO> elementsAvailables,
			List<ElementVO> elementsReturns, Dealer dealer, UserVO user, Long documentId, String documentClass) throws BusinessException {
		//Valida que existan elementos para poder llamar al movimiento de
		//elementos.
		if (!elementsAvailables.isEmpty()){
			moveNotSerializedElementBetweenWareHouseQualityAvailable(elementsAvailables, dealer, user, documentId, documentClass);
		}
		if (!elementsReturns.isEmpty()){
			moveNotSerializedElementBetweenWareHouseQualityReturns(elementsReturns, dealer, user, documentId, documentClass);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#moveNotSerializedElementBetweenWareHouseQualityAvailable(java.util.List, co.com.directv.sdii.model.pojo.Dealer)
	 */
	@Override
	public void moveNotSerializedElementBetweenWareHouseQualityAvailable(List<ElementVO> elementVO, Dealer dealer, UserVO user, Long documentId, String documentClass) 
	  throws BusinessException {
		
		log.debug("== Inicia moveNotSerializedElementBetweenWareHouseQualityAvailable/WarehouseElementBusinessBean ==");
        UtilsBusiness.assertNotNull(elementVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
	    try{
	
	    	dealer =  dealersDao.getDealerByID(dealer.getId());
	    		
			Warehouse logisticOperatorQtyCtrlWh = businessImportLogItem.getLogisticOperatorWarehouse(dealer.getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_CALIDAD.getCodeEntity());
	    	Warehouse logisticOperatorDisponiWh = businessImportLogItem.getLogisticOperatorWarehouse(dealer.getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_DISPONIBILIDADES.getCodeEntity());
	    	
	    	
	       	WarehouseVO whVo = UtilsBusiness.copyObject(WarehouseVO.class, logisticOperatorQtyCtrlWh);
	       	WarehouseVO whVd = UtilsBusiness.copyObject(WarehouseVO.class, logisticOperatorDisponiWh);
	
	        MassiveMovementBetweenWareHouseDTO movementDto = null;
	       	movementDto = businessImportLogItem.buildMassiveMovementDTO(elementVO,whVo,whVd);
	       	
	    	massiveMovementOfNotSerializedElementsBetweenWareHouse(movementDto, CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_QA_ENTRY.getCodeEntity(), CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_QA_EXIT.getCodeEntity(), documentId, documentClass,user.getId());
	    	
	    	//Se consultan los usuarios analistas logisticos del dealer operador logistico
	    	List<User> users = usersDao.getUsersByDealerIdAndRoleTypeCode(dealer.getId(), CodesBusinessEntityEnum.ROLE_TYPE_DEALER_LOGISTICS_ANALYST.getCodeEntity());
	    	if (user != null && user.getId() != null && user.getId().longValue() > 0){
	    		user=UtilsBusiness.copyObject(UserVO.class, usersDao.getUserById(user.getId()));
	    	}
	    	
	    	if(! users.isEmpty()){
	    		for (User usert: users){
	    			log.info("== user ==" + usert.getEmail());
	    		}
	        	//Invocar INV_08
	    		try {	
	    			emailTypeBusinessBean.sendMailByEmailType(elementVO, users, dealer, EmailTypesEnum.REGISTER_QA_NOASERIALIZED_ELEMENTS,user,whVd);
		    	} catch (Throwable e) {
	    			log.error("No se pudo enviar un correo electrÃ³nico. " + e.getMessage(), e);
	    		}
	        	
	    	}else{
	    		log.error("No existen usuarios. ");
	    	}
		
	   } catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operaciÃ³n moveNotSerializedElementBetweenWareHouseQualityAvailable/WarehouseElementBusinessBean ==");
	    	throw this.manageException(ex);
	   } finally {
	        log.debug("== Termina moveNotSerializedElementBetweenWareHouseQualityAvailable/WarehouseElementBusinessBean ==");
	   }
		
	}
	
	/**
	 * 
	 */
	@Override
	public void moveNotSerializedElementBetweenWareHouseQualityReturns(List<ElementVO> elementVO, Dealer dealer, UserVO user, Long documentId, String documentClass) 
	  throws BusinessException {
		
		log.debug("== Inicia moveNotSerializedElementBetweenWareHouseQualityReturns/WarehouseElementBusinessBean ==");
        UtilsBusiness.assertNotNull(elementVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
    try{
    	dealer = dealersDao.getDealerByID(dealer.getId());

		Warehouse logisticOperatorQtyCtrlWh = businessImportLogItem.getLogisticOperatorWarehouse(dealer.getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_CALIDAD.getCodeEntity());
    	Warehouse logisticOperatorDevolucWh = businessImportLogItem.getLogisticOperatorWarehouse(dealer.getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_DEVOLUCIONES.getCodeEntity());
    	
    	
       	WarehouseVO whVo = UtilsBusiness.copyObject(WarehouseVO.class, logisticOperatorQtyCtrlWh);
       	WarehouseVO whVd = UtilsBusiness.copyObject(WarehouseVO.class, logisticOperatorDevolucWh);

        MassiveMovementBetweenWareHouseDTO movementDto = null;
       	movementDto = businessImportLogItem.buildMassiveMovementDTO(elementVO, whVo, whVd);
       	
    	massiveMovementOfNotSerializedElementsBetweenWareHouse(movementDto, CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_QA_ENTRY.getCodeEntity(), CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_QA_EXIT.getCodeEntity(), documentId, documentClass,user.getId());
    	
    	//Se consultan los usuarios analistas logisticos del dealer operador logistico
    	List<User> users = usersDao.getUsersByDealerIdAndRoleTypeCode(dealer.getId(), CodesBusinessEntityEnum.ROLE_TYPE_DEALER_LOGISTICS_ANALYST.getCodeEntity());
    	user=UtilsBusiness.copyObject(UserVO.class, usersDao.getUserById(user.getId()));
    	if(! users.isEmpty()){
    		for (User usert: users){
    			log.info("== user ==" + usert.getEmail());
    		}
        	//Invocar INV_08
    		try {
    			emailTypeBusinessBean.sendMailByEmailType(elementVO, users, dealer, EmailTypesEnum.REGISTER_QA_NOASERIALIZED_ELEMENTS,user,whVd);
    		} catch (Throwable e) {
    			log.error("No se pudo enviar un correo electrÃ³nico. " + e.getMessage(), e);
    		}
    	}else{
    		log.error("No existen usuarios. ");
    		
    	}

    	
		
	   } catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operaciÃ³n moveNotSerializedElementBetweenWareHouseQualityReturns/WarehouseElementBusinessBean ==");
	    	throw this.manageException(ex);
	   } finally {
	        log.debug("== Termina moveNotSerializedElementBetweenWareHouseQualityReturns/WarehouseElementBusinessBean ==");
	   }
		
	}	
	
	

	@Override
	public void moveSerializedElementBetweenWareHouseQualityAvailable(List<ElementVO> elementsVO, Dealer dealer, Long documentId, String documentClass, Long userId) throws BusinessException {
		log.debug("== Inicia moveSerializedElementBetweenWareHouseQualityAvailable/WarehouseElementBusinessBean ==");
        UtilsBusiness.assertNotNull(elementsVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(userId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	    try{
	
			Warehouse logisticOperatorQtyCtrlWh = businessImportLogItem.getLogisticOperatorWarehouse(dealer.getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_CALIDAD.getCodeEntity());
	    	Warehouse logisticOperatorDisponiWh = businessImportLogItem.getLogisticOperatorWarehouse(dealer.getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_DISPONIBILIDADES.getCodeEntity());
	    	Warehouse logisticOperatorDisponiBulkWh = businessImportLogItem.getLogisticOperatorWarehouse(dealer.getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_S02.getCodeEntity());
	    	
	    	
	       	WarehouseVO whVo = UtilsBusiness.copyObject(WarehouseVO.class, logisticOperatorQtyCtrlWh);
	       	WarehouseVO whVdStock = UtilsBusiness.copyObject(WarehouseVO.class, logisticOperatorDisponiWh);
	       	WarehouseVO whVdStockBulk = UtilsBusiness.copyObject(WarehouseVO.class, logisticOperatorDisponiBulkWh);
	       	
	       	List<ElementVO> listElementsToStock = new ArrayList<ElementVO>();
	       	List<ElementVO> listElementsToStockBulk = new ArrayList<ElementVO>();
	       	ElementVO elementVO = null;
	       	for(ElementVO elementActual: elementsVO){
	       		Serialized serialized = daoSerialized.getSerializedByID(elementActual.getId());
				if(serialized!=null){
					elementVO = UtilsBusiness.copyObject(ElementVO.class,  serialized.getElement());
					if(serialized.getSerialized()==null && serialized.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity())){
						elementVO.setSerializedElement(UtilsBusiness.copyObject(SerializedVO.class,  serialized));
						listElementsToStockBulk.add(elementVO);
					}else{
						elementVO.setSerializedElement(UtilsBusiness.copyObject(SerializedVO.class,  serialized));
						listElementsToStock.add(elementVO);
					}
				}else{
					throw new BusinessException(ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
				}
	       	}
	       	
	       	User user = usersDao.getUserById(userId);
	       	if(user == null){
	       		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
	       	}
	       	
	       	
	       	if(listElementsToStock.size()>0){
	       		MassiveMovementBetweenWareHouseDTO movementDtoStock = null;
	       		movementDtoStock = businessImportLogItem.buildMassiveMovementDTO(listElementsToStock,whVo,whVdStock);
	       		movementDtoStock.setProcessCodeIBS(CodesBusinessEntityEnum.PROCESS_CODE_IBS_QUALITY_CONTROL.getCodeEntity());
	       		massiveMovementOfSerializedElementsBetweenWareHouse(movementDtoStock, CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_QA_TO_STOCK_ENTRY.getCodeEntity(), CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_QA_TO_STOCK_EXIT.getCodeEntity(), documentId, documentClass, user);
	       	}
	       	
	       	if(listElementsToStockBulk.size()>0){
	       		MassiveMovementBetweenWareHouseDTO movementDtoStockBulk = null;
	       		movementDtoStockBulk = businessImportLogItem.buildMassiveMovementDTO(listElementsToStockBulk,whVo,whVdStockBulk);
	       		movementDtoStockBulk.setProcessCodeIBS(CodesBusinessEntityEnum.PROCESS_CODE_IBS_QUALITY_CONTROL.getCodeEntity());
	       		massiveMovementOfSerializedElementsBetweenWareHouse(movementDtoStockBulk, CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_QA_TO_STOCK_ENTRY.getCodeEntity(), CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_QA_TO_STOCK_EXIT.getCodeEntity(), documentId, documentClass, user);
	       	}
	    	
	    	try{
	    		long dealerCountryId = dealer.getPostalCode().getCity().getState().getCountry().getId();
	    	}catch(Exception e){
	    		dealer = dealersDao.getDealerByID(dealer.getId());
	    	}
	    	
	    	//Se consultan los usuarios analistas logisticos del dealer operador logistico
	    	List<User> users = usersDao.getUsersByDealerIdAndRoleTypeCode(dealer.getId(), CodesBusinessEntityEnum.ROLE_TYPE_DEALER_LOGISTICS_ANALYST.getCodeEntity());
	    	
	    	if (!users.isEmpty()){
	    		
	    		//Invocar INV_08
	    		try {
	    			if(listElementsToStock.size()>0){
	    				emailTypeBusinessBean.sendMailByEmailType(listElementsToStock, users, dealer, EmailTypesEnum.REGISTER_QA_SERIALIZED_ELEMENTS, UtilsBusiness.copyObject(UserVO.class, user), whVdStock);
	    			}
	    		} catch (Throwable e) {
	    			log.error("No se pudo enviar un correo electrÃ³nico. " + e.getMessage(), e);
	    		}
	    		
	    		//Invocar INV_08
	    		try {
	    			if(listElementsToStockBulk.size()>0){
	    				emailTypeBusinessBean.sendMailByEmailType(listElementsToStockBulk, users, dealer, EmailTypesEnum.REGISTER_QA_SERIALIZED_ELEMENTS, UtilsBusiness.copyObject(UserVO.class,user), whVdStockBulk);
	    			}
	    		} catch (Throwable e) {
	    			log.error("No se pudo enviar un correo electrÃ³nico. " + e.getMessage(), e);
	    		}
	    	}
		
	   } catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operaciÃ³n moveSerializedElementBetweenWareHouseQualityAvailable/WarehouseElementBusinessBean ==");
	    	throw this.manageException(ex);
	   } finally {
	        log.debug("== Termina moveSerializedElementBetweenWareHouseQualityAvailable/WarehouseElementBusinessBean ==");
	   }
		
	}

	@Override
	public void moveSerializedElementBetweenWareHouseQualityReturn(
			List<ElementVO> elementsVO, Dealer dealer, Long documentId, String documentClass, Long userId) throws BusinessException {
		log.debug("== Inicia moveSerializedElementBetweenWareHouseQualityReturn/WarehouseElementBusinessBean ==");
        UtilsBusiness.assertNotNull(elementsVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	    try{
	
			Warehouse logisticOperatorQtyCtrlWh = businessImportLogItem.getLogisticOperatorWarehouse(dealer.getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_CALIDAD.getCodeEntity());
			Warehouse logisticOperatorDevolucWh = businessImportLogItem.getLogisticOperatorWarehouse(dealer.getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_DEVOLUCIONES.getCodeEntity());
	    	
	    	
	       	WarehouseVO whVo = UtilsBusiness.copyObject(WarehouseVO.class, logisticOperatorQtyCtrlWh);
	       	WarehouseVO whVd = UtilsBusiness.copyObject(WarehouseVO.class, logisticOperatorDevolucWh);
	       	
	        
	
	        MassiveMovementBetweenWareHouseDTO movementDto = null;
	       	movementDto = businessImportLogItem.buildMassiveMovementDTO(elementsVO,whVo,whVd);
	        movementDto.setProcessCodeIBS(CodesBusinessEntityEnum.PROCESS_CODE_IBS_QUALITY_CONTROL.getCodeEntity());
	       	
	       	User user = usersDao.getUserById(userId);
	       	if(user == null){
	       		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
	       	}
	       	
	    	massiveMovementOfSerializedElementsBetweenWareHouse(movementDto, CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_QA_TO_RETURNS_ENTRY.getCodeEntity(), CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_QA_TO_RETURNS_EXIT.getCodeEntity(), documentId, documentClass, user);
	    	
	    	try{
	    		long dealerCountryId = dealer.getPostalCode().getCity().getState().getCountry().getId();
	    	}catch(Exception e){
	    		dealer = dealersDao.getDealerByID(dealer.getId());
	    	}
	    	
	    	//Se consultan los usuarios analistas logisticos del dealer operador logistico
	    	List<User> users = usersDao.getUsersByDealerIdAndRoleTypeCode(dealer.getId(), CodesBusinessEntityEnum.ROLE_TYPE_DEALER_LOGISTICS_ANALYST.getCodeEntity());
	    	if (!users.isEmpty()){
	    		
	    		try {
		    	//Invocar INV_08
	    			emailTypeBusinessBean.sendMailByEmailType(elementsVO, users, dealer, EmailTypesEnum.REGISTER_QA_SERIALIZED_ELEMENTS, UtilsBusiness.copyObject(UserVO.class, user), whVd);
		    	} catch (Throwable e) {
	    			log.error("No se pudo enviar un correo electrÃ³nico. " + e.getMessage(), e);
	    		}
	    	}
	    	
	   } catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operaciÃ³n moveSerializedElementBetweenWareHouseQualityReturn/WarehouseElementBusinessBean ==");
	    	throw this.manageException(ex);
	   } finally {
	        log.debug("== Termina moveSerializedElementBetweenWareHouseQualityReturn/WarehouseElementBusinessBean ==");
	   }
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getWareHouseElementByActualWhAndElementID(java.lang.Long, java.lang.String, java.lang.String, java.lang.Long)
	 */
	public WarehouseElementVO getWareHouseElementByActualWhAndElementID(Long whID, String serialCode, String ird , Long elementId) throws BusinessException{
		log.debug("== Inicia getWareHouseElementByActualWhAndElementID/WarehouseElementBusinessBean ==");
        try{

        	WarehouseElementVO whElement = UtilsBusiness.copyObject(WarehouseElementVO.class, this.daoWarehouseElement.getWareHouseElementByActualWhAndElementID( whID,  serialCode,  ird ,  elementId));
			return whElement;
		
	   } catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operaciÃ³n getWareHouseElementByActualWhAndElementID/WarehouseElementBusinessBean ==");
	    	throw this.manageException(ex);
	   } finally {
	        log.debug("== Termina getWareHouseElementByActualWhAndElementID/WarehouseElementBusinessBean ==");
	   }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getWarehouseElementValidatingExistenceInMoreElementsRefInc(java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Double)
	 */
	@Override
	public WarehouseElementVO getWarehouseElementValidatingExistenceInMoreElementsRefInc(
			Long refId, Long warehouseId, String serial, Long elementTypeId, Double quantity, Long countryId) throws BusinessException {
		
		log.debug("== Inicia getWarehouseElementValidatingExistenceInMoreElementsRefInc/WarehouseElementBusinessBean ==");
		WarehouseElementVO whElement = null;
		try {
		
			//validar que el elemento no tenga una inconsistencia de mas o menos elementos fÃ­sicos en cualquier estado
			if(StringUtils.isBlank(serial)) {
				
				NotSerialized notSerialized = daoNotSerialized.getNotSerializedbyElementTypeID(elementTypeId, countryId);
				
				if(notSerialized != null) {
					Long count = daoReportedElement.getCountReportedElementsByRefIdAndElementTypeId(refId, elementTypeId, true, true);
					if(count > 0) {//ya existen inconsistencias en la remisiÃ³n con el mismo tipo de elemento no serializado
						throw new BusinessException(ErrorBusinessMessages.STOCK_IN458.getCode(),ErrorBusinessMessages.STOCK_IN458.getMessage());
					}
				}
			}else {
				
				//si el elemento a agregar es uno vinculado (hijo), se busca al padre y se agrega el padre
				List<Serialized> parentElements = daoSerialized.getLinkedSerializedBySerialCode(serial,countryId);
				Serialized parentElement = null;
				if(parentElements != null && !parentElements.isEmpty()) {
					parentElement = parentElements.get(0);
					serial = parentElement.getSerialCode();//ahora el serial a usar es el del elemento padre
				}
				
				ReportedElement reportedElement = daoReportedElement.getReportedElementByReferenceIdAndSerial(refId, serial);//el elemento ya estÃ¡ reportado
				if(reportedElement != null) {
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN458.getCode(),ErrorBusinessMessages.STOCK_IN458.getMessage());
				}
				
			}
		
			//obtener el elemento de la bodega indicada
			whElement = getWarehouseElementBySerialOrElementType(warehouseId, serial, elementTypeId, quantity);
			WarehouseVO whName = UtilsBusiness.copyObject(WarehouseVO.class, whElement.getWarehouseId());
			this.warehouseBusinessBean.genWareHouseName(whName);
			whElement.setWarehouseName(whName.getWarehouseName());
			//validar que el elemento sea prepago si la remisiÃ³n es prepago, e igualmente con postpago
			if(whElement != null && whElement.getElementType() != null && whElement.getElementType().getElementModel() != null && whElement.getElementType().getElementModel().getModelCode() != null) {
				
				Reference reference = daoReference.getReferenceByID(refId);
				
				boolean isPrepaidReference = false;
				if(reference != null && reference.getIsPrepaidRef() != null) {
					isPrepaidReference = reference.getIsPrepaidRef().equals(CodesBusinessEntityEnum.REFERENCE_PREPAID.getCodeEntity());
				}
				
				if(isPrepaidReference 
				   && whElement.getElementType().getElementModel().getIsPrepaidModel().equals(CodesBusinessEntityEnum.ELEMENT_MODEL_IS_NOT_PREPAID.getCodeEntity())) {
					//IN436 - No se puede agregar el elemento porque su modelo no es de tipo prepago.
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN436.getCode(),ErrorBusinessMessages.STOCK_IN436.getMessage());
				}else if(!isPrepaidReference 
						&& whElement.getElementType().getElementModel().getIsPrepaidModel().equals(CodesBusinessEntityEnum.ELEMENT_MODEL_IS_PREPAID.getCodeEntity())) {
					//IN437 - No se puede agregar el elemento porque su modelo no es de tipo postpago.
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN437.getCode(),ErrorBusinessMessages.STOCK_IN437.getMessage());
				}
			
			}
			
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operaciÃ³n getWarehouseElementValidatingExistenceInMoreElementsRefInc/WarehouseElementBusinessBean ==" + ex.getMessage());
	    	throw this.manageException(ex);
		} finally {
	        log.debug("== Termina getWarehouseElementValidatingExistenceInMoreElementsRefInc/WarehouseElementBusinessBean ==");
		}
	    
		return whElement;
	   
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getWarehouseElementBySerialOrElementType(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	@Override
	public WarehouseElementVO getWarehouseElementBySerialOrElementType(
			Long warehouseId, String serial, Long elementTypeId, Double quantity)
			throws BusinessException {
		log.debug("== Inicia getWarehouseElementBySerialOrElementType/WarehouseElementBusinessBean ==");
        try{

        	boolean isSerializedSearch = !StringUtils.isBlank(serial);
        	
        	WarehouseElementVO whElement = UtilsBusiness.copyObject(WarehouseElementVO.class, daoWarehouseElement.getWarehouseElementBySerialOrElementType(warehouseId, serial, elementTypeId));
        	
        	if(isSerializedSearch) {
        		if (whElement == null || whElement.getActualQuantity() == null || whElement.getActualQuantity() <= 0) {
            		Object[] params = {serial};
            		throw new BusinessException(ErrorBusinessMessages.STOCK_IN383.getCode(), ErrorBusinessMessages.STOCK_IN383.getMessageCode(params), UtilsBusiness.getParametersWS(params));
            	}
        	}else {
        		if(whElement == null || whElement.getNotSerialized() == null) {
        			throw new BusinessException(ErrorBusinessMessages.STOCK_IN455.getCode(), ErrorBusinessMessages.STOCK_IN455.getMessageCode());
        		}else {
        			Double elementQuantity = daoWarehouseElement.getWarehouseElementQuantityByElementType(warehouseId, elementTypeId);
        			if(elementQuantity < quantity) {
	        			Object[] params = {elementTypeId+"", warehouseId+""};
	        			throw new BusinessException(ErrorBusinessMessages.STOCK_IN392.getCode(), ErrorBusinessMessages.STOCK_IN392.getMessageCode(params), UtilsBusiness.getParametersWS(params));
        			}
        		}
        		
        	}
        	
			return whElement;
		
	   } catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operaciÃ³n getWarehouseElementBySerialOrElementType/WarehouseElementBusinessBean ==" + ex.getMessage());
	    	throw this.manageException(ex);
	   } finally {
	        log.debug("== Termina getWarehouseElementBySerialOrElementType/WarehouseElementBusinessBean ==");
	   }
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WarehouseElementVO getWarehouseElementBySerialActive(
			String serialCode,Long countryId) throws BusinessException {
		log.debug("== Inicia getWarehouseElementBySerialActive/WarehouseElementBusinessBean ==");
		UtilsBusiness.assertNotNull(serialCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			WarehouseElement objPojo = daoWarehouseElement.getWarehouseElementBySerialActive(serialCode,countryId);
			if (objPojo == null) {
				Object[] params = {serialCode};
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN383.getCode(), ErrorBusinessMessages.STOCK_IN383.getMessageCode(params));
			}
			return UtilsBusiness.copyObject(WarehouseElementVO.class, objPojo);
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getWarehouseElementByID/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementBySerialActive/WarehouseElementBusinessBean ==");
		}
	}

	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Map<String, Object[]> getWarehouseElementBySerialActive(List<String> multipleSerialCode,Long countryId) throws BusinessException {
		log.debug("== Inicia getWarehouseElementBySerialActive/WarehouseElementBusinessBean ==");
		UtilsBusiness.assertNotNull(multipleSerialCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			List<Object[]> listObject = daoWarehouseElement.getWarehouseElementBySerialActive(multipleSerialCode,countryId);
			Map<String, Object[]> returnValue = new HashMap<String, Object[]>();
			for(Object[] object : listObject){
				returnValue.put((String) object[0], object);
			}
			if (listObject == null) {
				Object[] params = {multipleSerialCode.toString()};
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN383.getCode(), ErrorBusinessMessages.STOCK_IN383.getMessageCode(params));
			}
			return returnValue;
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getWarehouseElementByID/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementBySerialActive/WarehouseElementBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#moveNotSerElementBetweenCustomerWhAndDealerWh(java.lang.String, java.lang.Long, java.lang.Long, java.lang.Double, co.com.directv.sdii.model.pojo.Warehouse)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void moveNotSerElementBetweenCustomerWhAndDealerWh (MovementElementDTO dto)throws BusinessException{
		log.debug("== Inicia moveNotSerElementBetweenCustomerWhAndDealerWh/WarehouseElementBusinessBean ==");
		UtilsBusiness.assertNotNull(dto, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		//UtilsBusiness.assertNotNull(dto.getIbsCustomerCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getQuantity(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getTargetWs(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

		try {
			
			businessMovementElement.moveNotSerializedElementBetweenWarehouse(dto);
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n moveNotSerElementBetweenCustomerWhAndDealerWh/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina moveNotSerElementBetweenCustomerWhAndDealerWh/WarehouseElementBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#getWarehouseElementsByCustomerIdSerialAndDatesRange(java.lang.Long, java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public CustomerElementsResponse getWarehouseElementsByCustomerIdSerialAndDatesRange(WareHouseElementClientFilterRequestDTO request, RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getWarehouseElementsByCustomerIdSerialAndDatesRange/WarehouseElementBusinessBean ==");
		try {
			
			CustomerElementsResponse customerElementsResponse = new CustomerElementsResponse();
			WareHouseElementResponse response = daoWarehouseElement.getWarehouseElementsByCustomerIdSerialAndDatesRange(request, requestCollInfo);

			List<WarehouseElement> whElPojos = response.getWareHouseElements();
			List<WarehouseElementVO> whElVos = UtilsBusiness.convertList(
					whElPojos, WarehouseElementVO.class);
		
			if(response != null){
				customerElementsResponse.setPageCount(response.getPageCount());
				customerElementsResponse.setPageIndex(response.getPageIndex());
				customerElementsResponse.setPageSize(response.getPageSize());
				customerElementsResponse.setRowCount(response.getRowCount());
				customerElementsResponse.setTotalRowCount(response.getTotalRowCount());
			}
			customerElementsResponse.setCustomerElementDTO(fillHistoryElementOfCustomer(whElVos, request.getCustomerId()));
			return customerElementsResponse;
			
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getWarehouseElementsByCustomerIdSerialAndDatesRange/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementsByCustomerIdSerialAndDatesRange/WarehouseElementBusinessBean ==");
		}
	}
	
	

	@Override
	public List<WarehouseElement> getWhElementByElementIdAndNotWhType(
			Long elementId, String whType) throws BusinessException {
		log.debug("== Inicia getWhElementByElementIdAndNotWhType/WarehouseElementBusinessBean ==");
		try {
			
					
			return daoWarehouseElement.getWhElementByElementIdAndNotWhType(elementId, whType);
			
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operaciÃ³n getWhElementByElementIdAndNotWhType/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWhElementByElementIdAndNotWhType/WarehouseElementBusinessBean ==");
		}
	}

	
	@Override
	public void moveNotSerializedElementBetweenWareHousesInconsistencies(ImportLog importLog, ImportLogInconsistencyVO importLogInconsistencyVO, String documentClass) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
		log.debug("== Inicia moveNotSerializedElementBetweenWareHousesInconsistencies/WarehouseElementBusinessBean ==");
		try{
			//Variables
			Double anteriorEsperada = importLogInconsistencyVO.getImpLogItem().getAmountExpected();
			Double nuevaEsperada = importLogInconsistencyVO.getExpectedAmount().doubleValue();
			InconsistencyType inconsistencyType = importLogInconsistencyVO.getInconsistencyType();
			NotSerializedVO notSerializedVO = (NotSerializedVO) importLogInconsistencyVO.getImpLogItem().getNotSerializedVO();
			
			//Validaciones
			assertNotNull(nuevaEsperada, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			//Tipo de movimiento
			String movEntrada = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_MOD_ENTRY.getCodeEntity();
			String movSalida = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_MOD_EXIT.getCodeEntity();
			
			MovementType movementTypeE = daoMovementType.getMovementTypeByCode(movEntrada);
			if(movementTypeE == null){
				throw new BusinessException(ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getMessage());
			}
			
			MovementType movementTypeS = daoMovementType.getMovementTypeByCode(movSalida);
			if(movementTypeS == null){
				throw new BusinessException(ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getMessage());
			}
			
			RecordStatus recordStatusUlt = daoRecordStatus.getRecordStatusByCode(CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			RecordStatus recordStatusHis = daoRecordStatus.getRecordStatusByCode(CodesBusinessEntityEnum.RECORD_STATUS_HISTORIC.getCodeEntity());
			
			Warehouse warehouseTarget = daoWarehouse.getWarehousesByDealerIdAndWhTypeCode(importLog.getDealer().getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity());
			if (warehouseTarget == null){
				Object[] params = {""+importLog.getDealer().getId()};
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN401.getCode(), ErrorBusinessMessages.STOCK_IN401.getMessage(params), UtilsBusiness.getParametersWS(params));
			}
			
			List<WarehouseElement> warehouseElementList = daoWarehouseElement.getWhElementByWhAndElementType(warehouseTarget.getId(), notSerializedVO.getElement().getElementType().getId(), "", null, CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity(),importLog.getCountry().getId() );
			

			
			
			
			Double totalQty = 0D;
			
			//Se actualiza los records anteriores y se traen las cantidades
			for (WarehouseElement warehouseElementU : warehouseElementList) {
				if (warehouseElementU != null){
					totalQty += warehouseElementU.getActualQuantity();
					warehouseElementU.setRecordStatus(recordStatusHis);
					daoWarehouseElement.updateWarehouseElement(warehouseElementU);
				}
			}
			
			Double actualQ = nuevaEsperada;
			Double movida = Math.abs(anteriorEsperada - nuevaEsperada);
			//Se crean en warehouse segÃºn tipo
			if ( inconsistencyType.getIncTypeCode().equals( CodesBusinessEntityEnum.INCONSISTENCY_TYPE_IMPORT_LOG_INCONSISTENCY_MINOR_ITEMS.getCodeEntity() ) ){ //Si es por menor cantidad de items
				//Se crea un record por la cantidad de faltante
				this.createWarehouseElementRecord(null, notSerializedVO, (totalQty-movida), totalQty, warehouseTarget, movementTypeS, recordStatusHis, null, movida, null, importLog.getId(), documentClass);
				
				//Se crea un record por la cantidad real
				this.createWarehouseElementRecord(null, notSerializedVO, nuevaEsperada, 0D, warehouseTarget, movementTypeS, recordStatusUlt, null, nuevaEsperada, null, importLog.getId(), documentClass);
			}else{
				if ( inconsistencyType.getIncTypeCode().equals( CodesBusinessEntityEnum.INCONSISTENCY_TYPE_IMPORT_LOG_INCONSISTENCY_MAJOR_ITEMS.getCodeEntity() ) ){
					if( totalQty.doubleValue() >= anteriorEsperada.doubleValue() ){
						actualQ = Math.abs( totalQty - anteriorEsperada + nuevaEsperada );
					}
					// Se crea un nuevo registro en la bodega de destino en estado 'U' que represente la entrada del elemento.
					this.createWarehouseElementRecord(null, notSerializedVO, actualQ, totalQty, warehouseTarget, movementTypeE, recordStatusUlt, null, movida, null, importLog.getId(), documentClass);
				}
			}
		} catch (Throwable ex) {
			log.debug("== Error moveNotSerializedElementBetweenWareHousesInconsistencies/WarehouseElementBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina moveNotSerializedElementBetweenWareHousesInconsistencies/WarehouseElementBusinessBean ==");
		}
	}

	/**
	 * 
	 * Metodo: MÃ©todo utilitario encargado de Crear el objeto DocumentClass a partir de el cÃ³digo 
	 * @param codeDocumentClass
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	private DocumentClass getDocumentClassByCode(String codeDocumentClass) throws BusinessException{
		DocumentClass documentClass = new DocumentClass();
		try{
			
			if(codeDocumentClass==null || codeDocumentClass.equals("")){
				return null;
			}else{
				if(codeDocumentClass.equals(CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity())){
					documentClass.setId(new Long(CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT_ID.getCodeEntity()));
				}else if(codeDocumentClass.equals(CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity())){
					documentClass.setId(new Long(CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG_ID.getCodeEntity()));
				}else if(codeDocumentClass.equals(CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity())){
					documentClass.setId(new Long(CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE_ID.getCodeEntity()));
				}else if(codeDocumentClass.equals(CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity())){
					documentClass.setId(new Long(CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity()));
				}
				
			}
		}catch (Throwable e) {
			this.manageException(e);
		}
		return documentClass;
	}

	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WareHouseElementResponse getSerializedElementsLastByCriteria(
			WareHouseRequestDTO wareHouseRequestDTO)
		throws BusinessException {
		log.debug("== Inicia getSerializedElementsLastByCriteria/WarehouseElementBusinessBean ==");
		try {
			
			if(wareHouseRequestDTO.getCustomerCode() != null && wareHouseRequestDTO.getCustomerCode().length() > 0){
				Customer customer = daoCustomer.getCustomerByCodeAndCountryId(wareHouseRequestDTO.getCustomerCode(), wareHouseRequestDTO.getCountryId());
				if(customer != null){
				    wareHouseRequestDTO.setCustomerId(customer.getId());
				}else{
					throw new BusinessException(ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getMessage() + " " + wareHouseRequestDTO.getCustomerCode());
				}
			}
			WareHouseElementResponse response = daoWarehouseElement.getSerializedElementsLastByCriteria(wareHouseRequestDTO);
			response.setWareHouseElementsVO(UtilsBusiness.convertList(response.getWareHouseElements(), WarehouseElementVO.class));
			response.setWareHouseElements(null);
			for(WarehouseElementVO warehouseElementVO: response.getWareHouseElementsVO()){
				if(warehouseElementVO.getWarehouseId().getDealerId()!=null){
					if(warehouseElementVO.getWarehouseId().getDealerId().getDealer()!=null){
						DealerVO dealerMain = UtilsBusiness.copyObject(DealerVO.class,  warehouseElementVO.getWarehouseId().getDealerId().getDealer());
						dealerMain.generateDepotPlusName();
						warehouseElementVO.setCompanyName(dealerMain.getDepotPlusName());
						DealerVO dealerBranch = UtilsBusiness.copyObject(DealerVO.class,  warehouseElementVO.getWarehouseId().getDealerId());
						dealerBranch.generateDepotPlusName();
						warehouseElementVO.setBranchName(dealerBranch.getDepotPlusName());
					}else{
						DealerVO dealerMain = UtilsBusiness.copyObject(DealerVO.class,  warehouseElementVO.getWarehouseId().getDealerId());
						dealerMain.generateDepotPlusName();
						warehouseElementVO.setCompanyName(dealerMain.getDepotPlusName());
					}
					warehouseElementVO.setDocumentClass(null);
					//warehouseElementVO.setElementType(null);
					warehouseElementVO.setMovementType(null);
					warehouseElementVO.setRecordStatus(null);
					warehouseElementVO.setSourceRecord(null);
				}
				WarehouseVO warehouse = UtilsBusiness.copyObject(WarehouseVO.class, warehouseElementVO.getWarehouseId());
				warehouseBusinessBean.genWareHouseName(warehouse);
				warehouseElementVO.setWarehouseName(warehouse.getWarehouseName());
			}
			
			return response;
		} catch (Throwable t) {
			log.error(" == Error getSerializedElementsLastByCriteria/WarehouseElementBusinessBean ==");
			throw this.manageException(t);
		} finally {
			log.debug("== Termina getSerializedElementsLastByCriteria/WarehouseElementBusinessBean ==");
		}
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WareHouseElementResponse getNotSerializedElementsLastByCriteria(WareHouseRequestDTO wareHouseRequestDTO)
	throws BusinessException {
		log.debug("== Inicia getNotSerializedElementsLastByCriteria/WarehouseElementBusinessBean ==");
		try {
			if(wareHouseRequestDTO.getCustomerCode() != null && wareHouseRequestDTO.getCustomerCode().length() > 0){
				Customer customer = daoCustomer.getCustomerByCodeAndCountryId(wareHouseRequestDTO.getCustomerCode(), wareHouseRequestDTO.getCountryId());
				if(customer != null){
				    wareHouseRequestDTO.setCustomerId(customer.getId());
				}else{
					throw new BusinessException(ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getMessage() + " " + wareHouseRequestDTO.getCustomerCode());
				}
			}
			
			WareHouseElementResponse response = daoWarehouseElement.getNotSerializedElementsLastByCriteria(wareHouseRequestDTO);
			response.setWareHouseElementsVO(UtilsBusiness.convertList(response.getWareHouseElements(), WarehouseElementVO.class));
			response.setWareHouseElements(null);
			for(WarehouseElementVO warehouseElementVO: response.getWareHouseElementsVO()){
				if(warehouseElementVO.getWarehouseId().getDealerId()!=null){
					if(warehouseElementVO.getWarehouseId().getDealerId().getDealer()!=null){
						DealerVO dealerMain = UtilsBusiness.copyObject(DealerVO.class,  warehouseElementVO.getWarehouseId().getDealerId().getDealer());
						dealerMain.generateDepotPlusName();
						warehouseElementVO.setCompanyName(dealerMain.getDepotPlusName());
						DealerVO dealerBranch = UtilsBusiness.copyObject(DealerVO.class,  warehouseElementVO.getWarehouseId().getDealerId());
						dealerBranch.generateDepotPlusName();
						warehouseElementVO.setBranchName(dealerBranch.getDepotPlusName());
					}else{
						DealerVO dealerMain = UtilsBusiness.copyObject(DealerVO.class,  warehouseElementVO.getWarehouseId().getDealerId());
						dealerMain.generateDepotPlusName();
						warehouseElementVO.setCompanyName(dealerMain.getDepotPlusName());
					}
					warehouseElementVO.setDocumentClass(null);
					//warehouseElementVO.setElementType(null);
					warehouseElementVO.setMovementType(null);
					warehouseElementVO.setRecordStatus(null);
					warehouseElementVO.setSourceRecord(null);
				}
				WarehouseVO warehouse = UtilsBusiness.copyObject(WarehouseVO.class, warehouseElementVO.getWarehouseId());
				warehouseBusinessBean.genWareHouseName(warehouse);
				warehouseElementVO.setWarehouseName(warehouse.getWarehouseName());
			}
			
			return response;
		} catch (Throwable t) {
			log.error(" == Error getNotSerializedElementsLastByCriteria/WarehouseElementBusinessBean ==");
			throw this.manageException(t);
		} finally {
			log.debug("== Termina getNotSerializedElementsLastByCriteria/WarehouseElementBusinessBean ==");
		}
	}
	
	
	private void moveElementsFromWHCrewToWHCompanyAvailable(List<DealerVO> companiesf,User userf,MassiveMovement process) throws BusinessException, PropertiesException{
		
		beanMoveCrewToComp.moveElementsFromWHCrewToWHCompany(companiesf, userf, CodesBusinessEntityEnum.WAREHOUSE_TYPE_DISPONIBILIDADES.getCodeEntity(), process);
		beanMoveCrewToComp.moveElementsFromWHCrewToWHCompany(companiesf, userf, CodesBusinessEntityEnum.WAREHOUSE_TYPE_S02.getCodeEntity(), process);
		beanMoveCrewToComp.moveElementsFromWHCrewToWHCompany(companiesf, userf, CodesBusinessEntityEnum.WAREHOUSE_TYPE_S03.getCodeEntity(), process);
		beanMoveCrewToComp.moveElementsFromWHCrewToWHCompany(companiesf, userf, CodesBusinessEntityEnum.WAREHOUSE_TYPE_CALIDAD.getCodeEntity(), process);
		beanMoveCrewToComp.moveElementsFromWHCrewToWHCompany(companiesf, userf, CodesBusinessEntityEnum.WAREHOUSE_TYPE_S05.getCodeEntity(), process);
		beanMoveCrewToComp.moveElementsFromWHCrewToWHCompany(companiesf, userf, CodesBusinessEntityEnum.WAREHOUSE_TYPE_S06.getCodeEntity(), process);
		beanMoveCrewToComp.moveElementsFromWHCrewToWHCompany(companiesf, userf, CodesBusinessEntityEnum.WAREHOUSE_TYPE_S01P.getCodeEntity(), process);
		
	}

	@Override
	public WareHouseElementResponse getWarehouseElementsByFiltersAndIsSerializedLast(
			FilterSerializedElementDTO filterSerializedElement,
			RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getWarehouseElementsByFiltersAndIsSerializedLast/WarehouseElementBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(filterSerializedElement, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(filterSerializedElement.getCountryId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			WareHouseElementResponse response = daoWarehouseElement.getWarehouseElementsByFiltersAndIsSerializedLast(filterSerializedElement, requestCollInfo);
			response.setWareHouseElementsVO(UtilsBusiness.convertList(response.getWareHouseElements(), WarehouseElementVO.class));
			response.setWareHouseElements(null);
			for(WarehouseElementVO warehouseElementVO: response.getWareHouseElementsVO()){
				if(warehouseElementVO.getWarehouseId().getDealerId()!=null){
					if(warehouseElementVO.getWarehouseId().getDealerId().getDealer()!=null){
						DealerVO dealerMain = UtilsBusiness.copyObject(DealerVO.class,  warehouseElementVO.getWarehouseId().getDealerId().getDealer());
						dealerMain.generateDepotPlusName();
						warehouseElementVO.setCompanyName(dealerMain.getDepotPlusName());
						DealerVO dealerBranch = UtilsBusiness.copyObject(DealerVO.class,  warehouseElementVO.getWarehouseId().getDealerId());
						dealerBranch.generateDepotPlusName();
						warehouseElementVO.setBranchName(dealerBranch.getDepotPlusName());
					}else{
						DealerVO dealerMain = UtilsBusiness.copyObject(DealerVO.class,  warehouseElementVO.getWarehouseId().getDealerId());
						dealerMain.generateDepotPlusName();
						warehouseElementVO.setCompanyName(dealerMain.getDepotPlusName());
					}
					warehouseElementVO.setDocumentClass(null);
					warehouseElementVO.setMovementType(null);
					warehouseElementVO.setRecordStatus(null);
					warehouseElementVO.setSourceRecord(null);
				}
				WarehouseVO warehouse = UtilsBusiness.copyObject(WarehouseVO.class, warehouseElementVO.getWarehouseId());
				warehouseBusinessBean.genWareHouseName(warehouse);
				warehouseElementVO.setWarehouseName(warehouse.getWarehouseName());
			}
			
			return response;
		} catch (Throwable t) {
			log.error(" == Error getWarehouseElementsByFiltersAndIsSerializedLast/WarehouseElementBusinessBean ==");
			throw this.manageException(t);
		} finally {
			log.debug("== Termina getWarehouseElementsByFiltersAndIsSerializedLast/WarehouseElementBusinessBean ==");
		}
	}
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Warehouse getWareHouseOrCreateIfNotExists(Long dealerId, String whTypeCode) throws BusinessException{
		log.debug("== Inicia getWareHouseOrCreateIfNotExists/ImportLogItemBusinessBean ==");
		try{
			Warehouse warehouseFind =  daoWarehouse.getWarehousesByDealerIdAndWhTypeCode(dealerId, whTypeCode);
			if (warehouseFind == null){
				Dealer actualDealer = dealersDao.getDealerByID(dealerId);
				Warehouse warehouseCreate = new Warehouse();
				Warehouse warehouseTemp = new Warehouse();
				warehouseTemp.setDealerId(new Dealer());
				warehouseTemp.getDealerId().setId(dealerId);
				warehouseTemp.setWhResponsible(ApplicationTextEnum.EMPTY.getApplicationTextValue());
				warehouseTemp.setResponsibleEmail(ApplicationTextEnum.EMPTY.getApplicationTextValue());
				warehouseTemp.setIsActive(CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity());
				warehouseTemp.setCountry(actualDealer.getPostalCode().getCity().getState().getCountry());
				WarehouseType whTypeTrans = this.businessWarehouseType.getWarehouseTypeByCode(CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity()); 
				warehouseCreate = this.warehouseBusinessBean.createWarehouseTransit( UtilsBusiness.copyObject(WarehouseVO.class, warehouseTemp), UtilsBusiness.copyObject(WarehouseTypeVO.class, whTypeTrans));
				return warehouseCreate;
			}else{
				return warehouseFind;
			}
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operaciÃ³n getWareHouseOrCreateIfNotExists/ImportLogItemBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina getWareHouseOrCreateIfNotExists/ImportLogItemBusinessBean ==");
	    }
		
	}
	
}
