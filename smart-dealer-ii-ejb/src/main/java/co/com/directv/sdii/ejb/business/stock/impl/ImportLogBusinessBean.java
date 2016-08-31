package co.com.directv.sdii.ejb.business.stock.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.EmailTypesEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.common.EmailTypeBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.file.UploadFileBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ImpLogModificationBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.HelperException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.FilterImportLogToPrintDTO;
import co.com.directv.sdii.model.dto.FilterUploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.dto.ModifyImportLogDTO;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.dto.SendEmailDTO;
import co.com.directv.sdii.model.dto.UploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.Element;
import co.com.directv.sdii.model.pojo.ElementStatus;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.ImpLogConfirmation;
import co.com.directv.sdii.model.pojo.ImpLogModification;
import co.com.directv.sdii.model.pojo.ImpLogModificationType;
import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.pojo.ImportLogInconsistency;
import co.com.directv.sdii.model.pojo.ImportLogItem;
import co.com.directv.sdii.model.pojo.ImportLogStatus;
import co.com.directv.sdii.model.pojo.InconsistencyStatus;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.MeasureUnit;
import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.Rol;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.UploadFile;
import co.com.directv.sdii.model.pojo.UploadFileParam;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.collection.ImportLogItemResponse;
import co.com.directv.sdii.model.pojo.collection.ImportLogResponse;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInSelectImportLogToPrintPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.ImpLogConfirmationVO;
import co.com.directv.sdii.model.vo.ImportLogInconsistencyVO;
import co.com.directv.sdii.model.vo.ImportLogItemVO;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.security.RolDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImpLogConfirmationDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImpLogModificationDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImportLogInconsistencyDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImportLogItemDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImportLogStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.InconsistencyStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.InconsistencyTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ItemStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.NotSerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.UploadFileParamDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.reports.dto.CountItemStatusImportLogDTO;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD ImportLog
 * 
 * Fecha de Creación: Mar 8, 2010
 * 
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal
 */
@Stateless(name = "ImportLogBusinessBeanLocal", mappedName = "ejb/ImportLogBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImportLogBusinessBean extends BusinessBase implements
		ImportLogBusinessBeanLocal {
	
	@EJB(name = "ImportLogDAOLocal", beanInterface = ImportLogDAOLocal.class)
	private ImportLogDAOLocal daoImportLog;

	@EJB(name = "UserDAOLocal", beanInterface = UserDAOLocal.class)
	private UserDAOLocal daoUser;

	@EJB(name = "ImportLogItemDAOLocal", beanInterface = ImportLogItemDAOLocal.class)
	private ImportLogItemDAOLocal daoImportLogItem;

	@EJB(name = "SerializedDAOLocal", beanInterface = SerializedDAOLocal.class)
	private SerializedDAOLocal daoSerialized;
	
	@EJB(name = "NotSerializedDAOLocal", beanInterface = NotSerializedDAOLocal.class)
	private NotSerializedDAOLocal daoNotSerialized;

	@EJB(name = "ItemStatusDAOLocal", beanInterface = ItemStatusDAOLocal.class)
	private ItemStatusDAOLocal daoItemStatus;

	@EJB(name = "ImportLogStatusDAOLocal", beanInterface = ImportLogStatusDAOLocal.class)
	private ImportLogStatusDAOLocal daoImportLogStatus;

	@EJB(name = "EmailTypeBusinessBeanLocal", beanInterface = EmailTypeBusinessBeanLocal.class)
	private EmailTypeBusinessBeanLocal businessEmailType;

	@EJB(name = "ElementBusinessBeanLocal", beanInterface = ElementBusinessBeanLocal.class)
	private ElementBusinessBeanLocal elementBusinessBean;

	@EJB(name="ImpLogConfirmationDAOLocal",beanInterface=ImpLogConfirmationDAOLocal.class)
	private ImpLogConfirmationDAOLocal daoImportLogConfirmation;
	
	@EJB(name="ImportLogInconsistencyDAOLocal",beanInterface=ImportLogInconsistencyDAOLocal.class)
	private ImportLogInconsistencyDAOLocal daoImportLogInconsistency;
	
	@EJB(name="ElementStatusDAOLocal",beanInterface=ElementStatusDAOLocal.class)
	private ElementStatusDAOLocal daoElementStatus;
	
	@EJB(name="ImportLogItemBusinessBeanLocal",beanInterface=ImportLogItemBusinessBeanLocal.class)
	private ImportLogItemBusinessBeanLocal businessImportLogItem;
	
	@EJB(name="ImpLogModificationDAOLocal",beanInterface=ImpLogModificationDAOLocal.class)
	private ImpLogModificationDAOLocal daoImpLogModification;
	
	@EJB(name="InconsistencyTypeDAOLocal", beanInterface=InconsistencyTypeDAOLocal.class)
	private InconsistencyTypeDAOLocal daoInconsistencyType;
	
	@EJB(name="InconsistencyStatusDAOLocal", beanInterface=InconsistencyStatusDAOLocal.class)
	private InconsistencyStatusDAOLocal daoInconsistency;
	
	@EJB(name="RolDAOLocal", beanInterface=RolDAOLocal.class)
    private RolDAOLocal daoRol;
	
	@EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
    private DealersDAOLocal daoDealer;
	
	@EJB(name="ElementDAOLocal", beanInterface=ElementDAOLocal.class)
	private ElementDAOLocal daoElement;
	
	@EJB(name="WarehouseElementBusinessBeanLocal", beanInterface=WarehouseElementBusinessBeanLocal.class)
	private WarehouseElementBusinessBeanLocal warehouseElementBusiness;
	
	@EJB(name="WarehouseDAOLocal", beanInterface=WarehouseDAOLocal.class)
	private WarehouseDAOLocal daoWarehouse;
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal usersDao;
	
	@EJB(name="ImpLogModificationBusinessBeanLocal", beanInterface=ImpLogModificationBusinessBeanLocal.class)
	private ImpLogModificationBusinessBeanLocal impLogModBusiness;
	
	@EJB (name="MovementElementBusinessBeanLocal", beanInterface=MovementElementBusinessBeanLocal.class)
	private MovementElementBusinessBeanLocal businessMovementElement;
	
	@EJB(name="UploadFileBusinessBeanLocal",beanInterface=UploadFileBusinessBeanLocal.class)
	private UploadFileBusinessBeanLocal businessUploadFile;
	
	private final static Logger log = UtilsBusiness.getLog4J(ImportLogBusinessBean.class);
	
	@EJB(name="UploadFileParamDAOLocal", beanInterface=UploadFileParamDAOLocal.class)
	private UploadFileParamDAOLocal uploadFileParamDAO;
	
	@EJB(name="ElementTypeDAOLocal", beanInterface=ElementTypeDAOLocal.class)
	private ElementTypeDAOLocal daoElementType;
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#getAllImportLogs()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ImportLogVO> getAllImportLogs() throws BusinessException {
		log.debug("== Inicia getAllImportLogs/ImportLogBusinessBean ==");
		try {
			return UtilsBusiness.convertList(daoImportLog.getAllImportLogs(), ImportLogVO.class);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getAllImportLogs/ImportLogBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAllImportLogs/ImportLogBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#getAllImportLogsBylogisticOp(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ImportLogVO> getAllImportLogsBylogisticOp(Long logisticOpId) throws BusinessException {
		log.debug("== Inicia getAllImportLogsBylogisticOp/ImportLogBusinessBean ==");
		try {
			return UtilsBusiness.convertList(daoImportLog.getAllImportLogsByLogisticOp(logisticOpId), ImportLogVO.class);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getAllImportLogsBylogisticOp/ImportLogBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAllImportLogsBylogisticOp/ImportLogBusinessBean ==");
		}
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#getImportLogByIDAndByLogisticOp(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLogVO getImportLogByIDAndByLogisticOp(Long id, Long logisticOpId) throws BusinessException {
		log.debug("== Inicia getImportLogByIDAndByLogisticOp/ImportLogBusinessBean ==");
		UtilsBusiness.assertNotNull(id,	ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),		ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(logisticOpId,ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			ImportLog objPojo = daoImportLog.getImportLogByIDAndByLogisticOp(id, logisticOpId);
			if (objPojo == null) {
				throw new BusinessException( ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getCode(), ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getMessage());
			}
			return UtilsBusiness.copyObject(ImportLogVO.class, objPojo);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getImportLogByIDAndByLogisticOp/ImportLogBusinessBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getImportLogByIDAndByLogisticOp/ImportLogBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#getImportLogByID(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLogVO getImportLogByID(Long id) throws BusinessException {
		log.debug("== Inicia getImportLogByID/ImportLogBusinessBean ==");
		UtilsBusiness.assertNotNull(id,	ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),		ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			ImportLog objPojo = daoImportLog.getImportLogByID(id);
			if (objPojo == null) {
				throw new BusinessException( ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getCode(), ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getMessage());
			}
			return UtilsBusiness.copyObject(ImportLogVO.class, objPojo);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getImportLogByID/ImportLogBusinessBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getImportLogByID/ImportLogBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#createImportLog(co.com.directv.sdii.model.vo.ImportLogVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createImportLog(ImportLogVO obj) throws BusinessException {
		log.debug("== Inicia createImportLog/ImportLogBusinessBean ==");
		UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			ImportLog objPojo = UtilsBusiness.copyObject(ImportLog.class, obj);
			daoImportLog.createImportLog(objPojo);
			if ( objPojo.getImportLogStatus()!=null && objPojo.getImportLogStatus().getStatusCode()!=null )
				createImportLogItemModification(objPojo.getUser().getId(), objPojo, impLogModBusiness.importLogStatusToImportLogModification( objPojo.getImportLogStatus() ) );
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación createImportLog/ImportLogBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina createImportLog/ImportLogBusinessBean ==");
		}
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#registerImportLog(co.com.directv.sdii.model.vo.ImportLogVO, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long registerImportLog(ImportLogVO importLog, List<ImportLogItemVO> importLogItems, Long userId) throws BusinessException {
		log.debug("== Inicia registerImportLog/ImportLogBusinessBean ==");
		UtilsBusiness.assertNotNull(importLog, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(importLogItems,	ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(importLog.getImportLogStatus(),	ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(importLog.getImportLogStatus().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(userId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		Long importLogId = null;
		boolean isNationalBuy = isNationalBuy(importLog);
		
		// Por defecto el registro de importación no se crea en estado "enviado"
		boolean isLogStatusSent = false;
		try {
			
			User user = daoUser.getUserById(userId);
			if(daoUser == null){
				throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			}
			
			// Si el estado es enviado entonces se debe validar si tiene todos
			// los datos requeridos
			Long impLogStatusId = importLog.getImportLogStatus().getId();
			ImportLogStatus impLogStatus = daoImportLogStatus.getImportLogStatusByID(impLogStatusId);
			UtilsBusiness.assertNotNull(impLogStatus,ErrorBusinessMessages.IMPORT_LOG_STATUS_IS_NOT_VALID.getCode(), ErrorBusinessMessages.IMPORT_LOG_STATUS_IS_NOT_VALID.getMessage());
			importLog.setImportLogStatus(impLogStatus);
			
			//Validar que ingrese elementos al registro de importacion si el estado es enviado
			if (importLogItems.isEmpty() && importLog.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity())){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN418.getCode(), ErrorBusinessMessages.STOCK_IN418.getMessage());	
			}
			
			if (importLog.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity())) {
				isLogStatusSent = true;
				if (!BusinessRuleValidationManager.getInstance().isValid(importLog)) {
					throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
				}
			}
			
			// Invoca la validación de la creación del Caso de Uso Inv_01
			// Creación del Registro de importaciónV2
			this.validateImportLogToRegisterAndByCountry(importLog, user);
			importLog.setCreationDate(UtilsBusiness.getCurrentTimeZoneDateByUserId(importLog.getUser().getId(), daoUser));
			ImportLog impLog = UtilsBusiness.copyObject(ImportLog.class, importLog);
			daoImportLog.createImportLog(impLog);
			if (impLog.getImportLogStatus() != null && impLog.getImportLogStatus().getStatusCode()!=null){
				createImportLogItemModification(impLog.getUser().getId(), impLog, impLogModBusiness.importLogStatusToImportLogModification(importLog.getImportLogStatus()));
			}
			importLogId = impLog.getId();
			
			List<ImportLogItemVO> listSerialized = new ArrayList<ImportLogItemVO>();
			List<ImportLogItemVO> listaNoSerialized = new ArrayList<ImportLogItemVO>();
			
			//Agrupa los elementos en listas para determinar elemento serializados y no serializados
			Object params[] = {ApplicationTextEnum.ELEMENTS_NOT_SERIALIZED_QUANTITY.getApplicationTextValue()};
			for (ImportLogItemVO importLogItem : importLogItems) {
				if(importLogItem.getAmountExpected() <= 0D){
					throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_ITEM_QUANTITY_INVALID.getCode(),ErrorBusinessMessages.IMPORT_LOG_ITEM_QUANTITY_INVALID.getMessage());
				}
				if(importLogItem.getAmountExpected() > 99999999){
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN416.getCode(), ErrorBusinessMessages.STOCK_IN416.getMessage(params));
				}
				
				if(importLogItem.getSerializedVO()!=null){
					listSerialized.add(importLogItem);
				}else{
					listaNoSerialized.add(importLogItem);
				}
				
			}
			
			//Almacena los items del registro de importacion.
			this.saveItemsforNewImportLog(listaNoSerialized, listSerialized, isNationalBuy, impLog,  isLogStatusSent, true, user);
			
			//Se consulta el operador logistico
			Dealer logisticOperator = daoDealer.getDealerByID(impLog.getDealer().getId());
			UtilsBusiness.assertNotNull(logisticOperator, ErrorBusinessMessages.DEALER_LOGICTIC_OPERATOR_DOESNT_EXIST.getCode(), ErrorBusinessMessages.DEALER_LOGICTIC_OPERATOR_DOESNT_EXIST.getMessage());

			//Se consultan los usuarios analistas logisticos del dealer operador logistico
        	//List<User> users = daoUser.getUsersByDealerIdAndRoleTypeCode(logisticOperator.getId(), CodesBusinessEntityEnum.ROLE_TYPE_DEALER_LOGISTICS_ANALYST.getCodeEntity());
			List<User> users = daoUser.getUsersByDealerIdAndRoleTypeCode(logisticOperator.getId(), CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_OPERATOR.getCodeEntity());
        	
        	//La validacion se elimina, ya que un operador logistico de DirectTV, puede crear un
        	//registro de importacion sin tener dealer asociado. 
        	//UtilsBusiness.assertNotEmpty(users, ErrorBusinessMessages.DEALER_LOGISTIC_ANALYST_USER_DOESNT_EXIST.getCode(), ErrorBusinessMessages.DEALER_LOGISTIC_ANALYST_USER_DOESNT_EXIST.getMessage());
        	
        	User u = impLog.getUser();
        	u = daoUser.getUserById(u.getId());

        	if(isLogStatusSent){
            	//Invocar INV_08
            	if (users.size() > 0){
            		String usurioNotificaNovedad = u.getName();
            		sendMailToRegisterImportLogNotification(impLog, users, usurioNotificaNovedad);
            	}
        	}
        	
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación registerImportLog/ImportLogBusinessBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina registerImportLog/ImportLogBusinessBean ==");
		}
		return importLogId;
	}
	
	


	/**
    * Determina si una compra es nacional o internacional.
    * @param importLog
    * @return
    */
   @Override
   public boolean isNationalBuy(ImportLogVO importLog) throws BusinessException{
	   boolean isNationalBuy = false;
	   UtilsBusiness.assertNotNull(importLog.getDealer().getPostalCode().getCity().getState().getCountry().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
       UtilsBusiness.assertNotNull(importLog.getSupplier().getCountryId().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

	   Long CtyDealer   = importLog.getDealer().getPostalCode().getCity().getState().getCountry().getId();
	   Long CtySupplier = importLog.getSupplier().getCountryId().getId();
	   if (CtyDealer.longValue() == CtySupplier.longValue() ){
		   isNationalBuy = true;
	   }
	   return isNationalBuy;
   }
	
	
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#modifiedImportLog(co.com.directv.sdii.model.vo.UserVO, co.com.directv.sdii.model.vo.ImportLogVO, java.util.List, java.util.List, java.util.List, java.util.List, boolean)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void modifiedImportLog(UserVO user,ImportLogVO importLogId, List<ImportLogItemVO> crearblesVO,List<ImportLogItemVO> actualizablesVO,List<ImportLogItemVO> eliminablesVO,List<ImportLogInconsistencyVO> inconsistencyItems, boolean isFinished) throws BusinessException {
		log.debug("== Inicia modifiedImportLog/ImportLogBusinessBean ==");
        UtilsBusiness.assertNotNull(importLogId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(importLogId.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        
        try {
        	List<ImportLogItemVO> creables =  UtilsBusiness.convertList(crearblesVO, ImportLogItemVO.class)  ;
        	List<ImportLogItemVO> actualizables = actualizablesVO;
                       
            validateImportLogItemsList(creables);
            validateImportLogItemsListUpdate(actualizables);
        	//Valida el estado actual de registro de importacion
        	ImportLog importLog = daoImportLog.getImportLogByIDAndByLogisticOp(importLogId.getId(), importLogId.getDealer().getId());
        	if(importLog == null || !importLog.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_CREATED.getCodeEntity())){
        		throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getCode(),ErrorBusinessMessages.IMPORT_LOG_ITEM_IS_INVALID.getMessage());        		
        	}
        	
        	//1) Crear los elementos
        	ImportLog objPojo = UtilsBusiness.copyObject(ImportLog.class,
					importLog);
        	for(ImportLogItemVO crear:creables){
        		List<ImportLogItemVO> listaNoLinked = new ArrayList<ImportLogItemVO>();
    			List<ImportLogItemVO> listaLinked = new ArrayList<ImportLogItemVO>();
    			List<ImportLogItemVO> listaNoSerialized = new ArrayList<ImportLogItemVO>();
    			//Ordena los elementos entre linkueados y no linkeados
				if(crear.getSerializedVO()!=null){
					if(crear.getSerializedVO().getSerialized()==null){
						listaNoLinked.add(crear);
					}else{
						listaLinked.add(crear);
					}
				}else{
					listaNoSerialized.add(crear);
				}
        		this.createImportLogItem(objPojo,listaNoLinked,listaLinked,listaNoSerialized);
        	}
        	//2) Actualizar los elementos
        	for(ImportLogItemVO actualizar:actualizablesVO){
        	//for(ImportLogItemVO actualizar:actualizables){
        		//a. Validar que no se encuentren en estado confirmado
        		ImportLogItem importLogItemTmp = this.daoImportLogItem.getImportLogItemByID(actualizar.getId());
        		if(importLogItemTmp!=null && importLogItemTmp.getItemStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity())){
        			throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_ITEM_IS_INVALID.getCode(),ErrorBusinessMessages.IMPORT_LOG_ITEM_IS_INVALID.getMessage());
        		}
        		if(importLogItemTmp != null && importLogItemTmp.getElement().getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity())&& actualizar.getAmountExpected()!=1){
        			throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_ITEM_UPDATE_QUANTITY_ERROR.getCode(),ErrorBusinessMessages.IMPORT_LOG_ITEM_UPDATE_QUANTITY_ERROR.getMessage());
        		}
        		businessImportLogItem.updateImportLogItem(actualizar);
        	}
        	//3) Eliminar los elementos
        	for(ImportLogItemVO eliminar:eliminablesVO){
        		//a. Validar que no se encuentren en estado condifmrado
        		ImportLogItem importLogItemTmp = this.daoImportLogItem.getImportLogItemByID(eliminar.getId());
        		if(importLogItemTmp.getItemStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity())){
        			throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_ITEM_IS_INVALID.getCode(),ErrorBusinessMessages.IMPORT_LOG_ITEM_IS_INVALID.getMessage());
        		}
        		ImportLogItem tmp = this.daoImportLogItem.getImportLogItemByID(eliminar.getId());
        		Serialized serTmp =  this.daoSerialized.getSerializedByID(tmp.getElement().getId());
        		eliminar.setSerializedVO(UtilsBusiness.copyObject(SerializedVO.class, serTmp));
        		businessImportLogItem.deleteImportLogItem(eliminar);
        	}
        	//4) Actualiza las inconsistencias
        	boolean updatingInconsistencies = false;
        	boolean allIncClosed = true;
        	for(ImportLogInconsistencyVO inconsistencyItemsTmp: inconsistencyItems){
        		updatingInconsistencies = true;
        		ImportLogInconsistency impLogInco = this.daoImportLogInconsistency.getImportLogInconsistencyByID(inconsistencyItemsTmp.getId());
        		if(impLogInco==null){
        			throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_ITEM_IS_INVALID.getCode(),ErrorBusinessMessages.IMPORT_LOG_ITEM_IS_INVALID.getMessage());
        		}
        		InconsistencyStatus incTmp = this.daoInconsistency.getInconsistencyStatusByID(inconsistencyItemsTmp.getInconsistencyStatus().getId());
        		if(CodesBusinessEntityEnum.INCONSISTENCY_STATUS_ACTIVE.getCodeEntity().equalsIgnoreCase(incTmp.getIncStatusCode())){
        			allIncClosed = false;
        		}
        		impLogInco.setInconsistencyStatus(incTmp);
        		impLogInco.setComments(inconsistencyItemsTmp.getComments());
        		impLogInco.setAnswer(inconsistencyItemsTmp.getAnswer());
        		this.daoImportLogInconsistency.updateImportLogInconsistency(impLogInco);
        		
        		
        	}
        	importLog = daoImportLog.getImportLogByIDAndByLogisticOp(importLogId.getId(), importLogId.getDealer().getId());
            createImportLogItemModification(user.getId(), importLog, CodesBusinessEntityEnum.IMPORT_LOG_MODIFICATION_STATUS_SENDED.getIdEntity(ImpLogModificationType.class.getName()));
            if(isFinished){
            	this.validateToFinishImportLog(importLogId);
            }else if(updatingInconsistencies && allIncClosed){
            	ItemStatus itemStatusInTransit = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_CREATED.getCodeEntity()); 
            	ItemStatus itemStatusInconsitent = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_CREATED.getCodeEntity());
            	daoImportLogItem.updateImportLogItemsStatusByImportLogId(importLog.getId(), itemStatusInconsitent, itemStatusInTransit);
            	ImportLogStatus importLogStatus = daoImportLogStatus.getImportLogStatusByCode(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_RECEIVED.getCodeEntity());
            	importLog.setImportLogStatus(importLogStatus);
            	daoImportLog.updateImportLog(importLog);
            	if ( importLog.getImportLogStatus() != null && importLog.getImportLogStatus().getStatusCode()!=null )
            		createImportLogItemModification(importLog.getUser().getId(), importLog, impLogModBusiness.importLogStatusToImportLogModification( importLog.getImportLogStatus() ) );
            }
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación modifiedImportLog/ImportLogBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina modifiedImportLog/ImportLogBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#modifyImportLogWithInconsistencies(co.com.directv.sdii.model.vo.UserVO, co.com.directv.sdii.model.vo.ImportLogVO, java.util.List, java.util.List, java.util.List, java.util.List, boolean)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void modifyImportLogWithInconsistencies(UserVO user,ImportLogVO importLogId,	List<ImportLogItemVO> crearblesVO,List<ImportLogItemVO> actualizablesVO,List<ImportLogItemVO> eliminablesVO,List<ImportLogInconsistencyVO> inconsistencyItems, boolean isFinished) throws BusinessException{
		log.debug("== Inicia modifyImportLogWithInconsistencies/ImportLogBusinessBean ==");
        UtilsBusiness.assertNotNull(importLogId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(importLogId.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        try {
        	List<ImportLogItemVO> creables =  UtilsBusiness.convertList(crearblesVO, ImportLogItemVO.class)  ;
        	List<ImportLogItemVO> actualizables = actualizablesVO;
                       
            validateImportLogItemsList(creables);
            validateImportLogItemsListUpdate(actualizables);
        	//Valida el estado actual de registro de importacion
        	ImportLog importLog = daoImportLog.getImportLogByIDAndByLogisticOp(importLogId.getId(), importLogId.getDealer().getId());
        	if(importLog == null || !importLog.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_INCONSISTENCY.getCodeEntity())){
        		throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getCode(),ErrorBusinessMessages.IMPORT_LOG_ITEM_IS_INVALID.getMessage());        		
        	}
        	
        	//1) Crear los elementos
        	ImportLog objPojo = UtilsBusiness.copyObject(ImportLog.class,
					importLog);
        	for(ImportLogItemVO crear:creables){
        		List<ImportLogItemVO> listaNoLinked = new ArrayList<ImportLogItemVO>();
    			List<ImportLogItemVO> listaLinked = new ArrayList<ImportLogItemVO>();
    			List<ImportLogItemVO> listaNoSerialized = new ArrayList<ImportLogItemVO>();
    			//Ordena los elementos entre linkueados y no linkeados
				if(crear.getSerializedVO()!=null){
					if(crear.getSerializedVO().getSerialized()==null){
						listaNoLinked.add(crear);
					}else{
						listaLinked.add(crear);
					}
				}else{
					listaNoSerialized.add(crear);
				}
        		this.createImportLogItem(objPojo,listaNoLinked,listaLinked,listaNoSerialized);
        	}
        	//2) Actualizar los elementos
        	for(ImportLogItemVO actualizar:actualizablesVO){
        	//for(ImportLogItemVO actualizar:actualizables){
        		//a. Validar que no se encuentren en estado confirmado
        		ImportLogItem importLogItemTmp = this.daoImportLogItem.getImportLogItemByID(actualizar.getId());
        		if(importLogItemTmp != null && importLogItemTmp.getItemStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity())){
        			throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_ITEM_IS_INVALID.getCode(),ErrorBusinessMessages.IMPORT_LOG_ITEM_IS_INVALID.getMessage());
        		}
        		if(importLogItemTmp != null && importLogItemTmp.getElement().getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity())&& actualizar.getAmountExpected()!=1){
        			throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_ITEM_UPDATE_QUANTITY_ERROR.getCode(),ErrorBusinessMessages.IMPORT_LOG_ITEM_UPDATE_QUANTITY_ERROR.getMessage());
        		}
        		businessImportLogItem.updateImportLogItem(actualizar);
        	}
        	//3) Eliminar los elementos
        	for(ImportLogItemVO eliminar:eliminablesVO){
        		//a. Validar que no se encuentren en estado condifmrado
        		ImportLogItem importLogItemTmp = this.daoImportLogItem.getImportLogItemByID(eliminar.getId());
        		if(importLogItemTmp.getItemStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity())){
        			throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_ITEM_IS_INVALID.getCode(),ErrorBusinessMessages.IMPORT_LOG_ITEM_IS_INVALID.getMessage());
        		}
        		ImportLogItem tmp = this.daoImportLogItem.getImportLogItemByID(eliminar.getId());
        		Serialized serTmp =  this.daoSerialized.getSerializedByID(tmp.getElement().getId());
        		if(serTmp == null){
        			NotSerialized notSer = this.daoNotSerialized.getNotSerializedByID(tmp.getElement().getId());
        			if(notSer != null){
        				eliminar.setNotSerializedVO(UtilsBusiness.copyObject(NotSerializedVO.class, notSer));
        				eliminar.setSerializedVO(null);
        			}
        		}else{
        			eliminar.setSerializedVO(UtilsBusiness.copyObject(SerializedVO.class, serTmp));
        			eliminar.setNotSerializedVO(null);
        		}
        		businessImportLogItem.deleteImportLogItem(eliminar);
        	}
        	//4) Actualiza las inconsistencias
        	for(ImportLogInconsistencyVO inconsistencyItemsTmp:inconsistencyItems){
        		
        		ImportLogInconsistency impLogInco = this.daoImportLogInconsistency.getImportLogInconsistencyByID(inconsistencyItemsTmp.getId());
        		if(impLogInco==null){
        			throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_ITEM_IS_INVALID.getCode(),ErrorBusinessMessages.IMPORT_LOG_ITEM_IS_INVALID.getMessage());
        		}
        		InconsistencyStatus incTmp = this.daoInconsistency.getInconsistencyStatusByID(inconsistencyItemsTmp.getInconsistencyStatus().getId());
        		impLogInco.setInconsistencyStatus(incTmp);
        		impLogInco.setComments(inconsistencyItemsTmp.getComments());
        		if( inconsistencyItemsTmp.getAnswer() != null )
        			impLogInco.setAnswer( inconsistencyItemsTmp.getAnswer() );
        		this.daoImportLogInconsistency.updateImportLogInconsistency(impLogInco);
        	}
        	importLog = daoImportLog.getImportLogByIDAndByLogisticOp(importLogId.getId(), importLogId.getDealer().getId());
            createImportLogItemModification(user.getId(), importLog, CodesBusinessEntityEnum.IMPORT_LOG_MODIFICATION_STATUS_SENDED.getIdEntity(ImpLogModificationType.class.getName()));
            if(isFinished){
            	this.validateToFinishImportLogAndByCountryInconsistencyView(importLogId);
            }
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operacion modifyImportLogWithInconsistencies/ImportLogBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina modifyImportLogWithInconsistencies/ImportLogBusinessBean ==");
        }
	}
	
	/**
	 * @param importLog
	 * @param country
	 * @throws BusinessException
	 */
	private void validateToFinishImportLogAndByCountryInconsistencyView(ImportLogVO importLog) throws BusinessException{
		log.debug("== Inicia validateToFinishImportLogAndByCountryInconsistencyView/ImportLogBusinessBean ==");
		try {
			boolean cambiarEstado =  false;
			//operacion paginada, se envia el parametro en null ya que en este punto no es necesario paginar
			RequestCollectionInfo requestCollInfo = null;
			List<ImportLogItem> importLogItems =  this.daoImportLogItem.getImportLogItemsByImportLog(importLog.getId(),requestCollInfo).getImportLogItems();
			List<ImportLogInconsistency> impLogInconcistency =  this.daoImportLogInconsistency.getImporLogInconsistencysByImportLog(importLog.getId());
			boolean itemsRepted = true;
			boolean inconsistencysClosed = true;
			//Valida si todos los elementos estan confirmados
			for(ImportLogItem importLogItemTmp:importLogItems){
				if(!importLogItemTmp.getItemStatus().getStatusCode().equals(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity())){
					itemsRepted =  false;
					break;
				}
			}
			//Valida si todas las inconsistencias se encuentran cerradas
			for(ImportLogInconsistency impLogInconcistencyTmp:impLogInconcistency){
				if(! impLogInconcistencyTmp.getInconsistencyStatus().getIncStatusCode().equals( CodesBusinessEntityEnum.INCONSISTENCY_REFERENCE_STATUS_CLOSE.getCodeEntity() ) ){
					inconsistencysClosed =  false;
					break;
				}
			}
			//Modifica el estado del registro de importación
			ImportLogStatus tmp = new ImportLogStatus();
			ImportLog imporLogUpdate = new ImportLog();
			imporLogUpdate = this.daoImportLog.getImportLogByIDAndByLogisticOp(importLog.getId(), importLog.getDealer().getId());
			if(itemsRepted && inconsistencysClosed){
				tmp = this.daoImportLogStatus.getImportLogStatusByCode(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_RECEIVED.getCodeEntity());
				if(tmp==null){
					throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
				}
				cambiarEstado=true;
			}else{
				if(inconsistencysClosed){
					//Actualiza los elementos del importLog ya que todas las inconsistencias estan cerradas					
					List<ImportLogItem> impLogItems = daoImportLogItem.getImportLogItemsByImportLog( importLog.getId(),requestCollInfo ).getImportLogItems();
					if( impLogItems != null ){
						for( ImportLogItem item : impLogItems ){
							//Se consulatan las confirmaciones del elementos
							Double confirmedQuantity = daoImportLogConfirmation.getImportLogItemConfirmationSumByImpLogItemId( item.getId() );
							ItemStatus newStatus = null;
							if( confirmedQuantity != null && confirmedQuantity.compareTo( item.getAmountExpected() ) == 0 ){
								newStatus = daoItemStatus.getItemStatusByCode( CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity() );
							} else if( confirmedQuantity != null && confirmedQuantity.compareTo( item.getAmountExpected() ) < 0 ){
								newStatus = daoItemStatus.getItemStatusByCode( CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() );
							} else {
								newStatus = daoItemStatus.getItemStatusByCode( CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity() );
							}
							if( newStatus != null ){
								item.setItemStatus( newStatus );
								daoImportLogItem.updateImportLogItem(item);
							}
						}
					}
					tmp = this.daoImportLogStatus.getImportLogStatusByCode(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity());
					if(tmp==null){
						throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
					}
					cambiarEstado=true;
				} else{
					tmp = this.daoImportLogStatus.getImportLogStatusByCode(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_INCONSISTENCY.getCodeEntity());
					if(tmp==null){
						throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
					}
					cambiarEstado=true;
				}
			}
			if(cambiarEstado){
				imporLogUpdate.setImportLogStatus(tmp);
				this.daoImportLog.updateImportLog(imporLogUpdate);
				if ( imporLogUpdate.getImportLogStatus() != null && imporLogUpdate.getImportLogStatus().getStatusCode()!=null )
					createImportLogItemModification(imporLogUpdate.getUser().getId(), imporLogUpdate, impLogModBusiness.importLogStatusToImportLogModification( imporLogUpdate.getImportLogStatus() ) );
				//Se consulta el operador logistico del país
				Dealer logisticOperator = daoDealer.getDealerByID(importLog.getId());
				UtilsBusiness.assertNotNull(logisticOperator, ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getMessage());

				//Se consulta el rol de analista logistico
	        	Rol rol = this.daoRol.getRolByCode(CodesBusinessEntityEnum.ROLE_TYPE_DEALER_LOGISTICS_ANALYST.getCodeEntity());
	        	User userTmp = new User();
	        	userTmp.setDealer(logisticOperator);
	        	userTmp.setRol(rol);
	        	//Se consultan los usuarios analistas logisticos del dealer operador logistico
	        	List<User> users = daoUser.getUserBySample(userTmp);
				//Invocar INV_08

	    		// Por defecto el log no queda en estado "enviado"
	    		boolean isLogStatusSent = false;
	    		if (tmp.getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity()))
	    		{
	    			isLogStatusSent = true;
	    		}
				sendMailToUpdateImportLogNotification(imporLogUpdate,users, isLogStatusSent);
			}			
			
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación validateToFinishImportLogAndByCountryInconsistencyView/ImportLogBusinessBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina validateToFinishImportLogAndByCountryInconsistencyView/ImportLogBusinessBean ==");
		}
	}
	
	/**
	 * Metodo: Permite crear la modificacion a un import log
	 * @param userId
	 * @param imporLogId
	 * @throws BusinessException
	 */
	private void createImportLogItemModification(Long userId, ImportLog imporLog , Long importLogModification) throws BusinessException {
		log.debug("== Inicia createImportLogItemModification/ImportLogItemBusinessBean ==");
        UtilsBusiness.assertNotNull(userId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(imporLog, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(importLogModification, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	ImpLogModification impLogModification = new ImpLogModification();
        	
        	ImpLogModificationType impLogModType = new ImpLogModificationType();
        	impLogModType.setId(importLogModification);
        	
        	impLogModification.setImpLogModificationType(impLogModType);        	
        	impLogModification.setImportLog(imporLog);
        	impLogModification.setModificationDate(UtilsBusiness.getCurrentTimeZoneDateByUserId(userId, usersDao));
        	User user = this.daoUser.getUserById(userId);
        	impLogModification.setUser(user);
        	daoImpLogModification.createImpLogModification(impLogModification);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createImportLogItemModification/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createImportLogItemModification/ImportLogItemBusinessBean ==");
        }
	}
	
	/**
	 * Metodo: Permite validar para el caso de creacion una lista de items para que este de acuerdo a la planilla 153
	 * @param creables Items nuevos a validar
	 * @throws BusinessException
	 */
	private void validateImportLogItemsList(List<ImportLogItemVO> items) throws BusinessException{
		for(ImportLogItemVO itemVO : items){
			UtilsBusiness.assertNotNull(itemVO.getElementVO(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		}
	}
	
	/**
	 * Metodo: Permite validar para el caso de creacion una lista de items para que este de acuerdo a la planilla 153
	 * @param creables Items nuevos a validar
	 * @throws BusinessException
	 */
	private void validateImportLogItemsListUpdate(List<ImportLogItemVO> items) throws BusinessException{
		for(ImportLogItemVO itemVO : items){
			UtilsBusiness.assertNotNull(itemVO.getElementVO(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(itemVO.getElementVO().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		}
	}
	
	/**
	 * @param objPojo
	 * @param listaNoLinked
	 * @param listaLinked
	 * @param listaNoSerialized
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void createImportLogItem(ImportLog objPojo,List<ImportLogItemVO> listaNoLinked ,List<ImportLogItemVO> listaLinked,List<ImportLogItemVO> listaNoSerialized)throws BusinessException{
		try {
			ItemStatus itemStatus;
			itemStatus =  this.daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_CREATED.getCodeEntity());
			UtilsBusiness.assertNotNull(itemStatus,ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			ElementStatus elementStatus = this.daoElementStatus.getElementStatusByCode(CodesBusinessEntityEnum.ELEMENT_STATUS_T01.getCodeEntity());
			//Crea los elementos no serializados
			for (ImportLogItemVO importLogItem : listaNoSerialized) {
				//ElementVO elementTmp =  UtilsBusiness.copyObject(ElementVO.class, importLogItem.getElementVO())  ;
				ElementVO elementTmp = importLogItem.getElementVO();
				elementTmp.setElementStatus(elementStatus);
				elementTmp.setCountry(objPojo.getCountry());
				this.elementBusinessBean.createNotSerializedElement(elementTmp, importLogItem.getNotSerializedVO(), objPojo.getId());
				//importLogItem.setMeasureUnit(UtilsBusiness.copyObject(MeasureUnit.class, elementTmp.getMeasureUnit()));
				importLogItem.setMeasureUnit(UtilsBusiness.copyObject(MeasureUnit.class, elementTmp.getElementType().getMeasureUnit()));
				importLogItem.setElement(UtilsBusiness.copyObject(Element.class, elementTmp));
				importLogItem.setImportLog(objPojo);
				importLogItem.setItemStatus(itemStatus);
				daoImportLogItem.createImportLogItem(UtilsBusiness.copyObject(ImportLogItem.class,importLogItem));
			}
			
			//Crea los elementos serialziados no vinculados
			for (ImportLogItemVO importLogItem : listaNoLinked) {
				//ElementVO elementTmp =  UtilsBusiness.copyObject(ElementVO.class, importLogItem.getElementVO())  ;
				ElementVO elementTmp = importLogItem.getElementVO();
				elementTmp.setElementStatus(elementStatus);
				elementTmp.setCountry(objPojo.getCountry());
				this.elementBusinessBean.createSerializedElement(elementTmp, importLogItem.getSerializedVO());
				//importLogItem.setMeasureUnit(UtilsBusiness.copyObject(MeasureUnit.class, elementTmp.getMeasureUnit()));
				importLogItem.setMeasureUnit(UtilsBusiness.copyObject(MeasureUnit.class, elementTmp.getElementType().getMeasureUnit()));
				importLogItem.setElement(UtilsBusiness.copyObject(Element.class, elementTmp));
				importLogItem.setImportLog(objPojo);
				importLogItem.setItemStatus(itemStatus);
				importLogItem.setAmountExpected(1D);
				daoImportLogItem.createImportLogItem(UtilsBusiness.copyObject(ImportLogItem.class,importLogItem));
			}
			
			//Crea los elementos serializados  vinculados
			for (ImportLogItemVO importLogItem : listaLinked) {
				ElementVO elementTmp = importLogItem.getElementVO();
				elementTmp.setCountry(objPojo.getCountry());
				if( elementTmp.getElementType().getId().equals( importLogItem.getSerializedVO().getSerialized().getElement().getElementType().getId() ) ){
					throw new BusinessException(ErrorBusinessMessages.ELEMENTS_CANT_HAVE_THE_SAME_TYPE.getCode(),ErrorBusinessMessages.ELEMENTS_CANT_HAVE_THE_SAME_TYPE.getMessage());
				}
				elementTmp.setElementStatus(elementStatus);
				Serialized vinculado = this.daoSerialized.getSerializedBySerialAndElementType(importLogItem.getSerializedVO().getSerialized().getSerialCode(),importLogItem.getSerializedVO().getSerialized().getElement().getElementType().getId(),objPojo.getCountry().getId());
				
				//Si el vinculado no existe retorna error
				if(vinculado==null){
					throw new BusinessException(ErrorBusinessMessages.LINKED_ELEMENT_NOT_EXIST_OR_INVALID.getCode(),ErrorBusinessMessages.LINKED_ELEMENT_NOT_EXIST_OR_INVALID.getMessage());
				}else{
					List<Serialized> vinculadoTest = this.daoSerialized.getSerialLinkedBySerialAndElementType(vinculado.getSerialCode(),vinculado.getElement().getElementType().getId(),objPojo.getCountry().getId());
					//Si el elemento a vincular ya se encuentra vinculado a otro elemento genera error
					if(vinculadoTest.size()!=0){
						throw new BusinessException(ErrorBusinessMessages.LINKED_ELEMENT_NOT_EXIST_OR_INVALID.getCode(),ErrorBusinessMessages.LINKED_ELEMENT_NOT_EXIST_OR_INVALID.getMessage());
					}
				}
				
				importLogItem.getSerializedVO().setSerialized(vinculado);
				this.elementBusinessBean.createSerializedElement(elementTmp, importLogItem.getSerializedVO());
				//importLogItem.setMeasureUnit(UtilsBusiness.copyObject(MeasureUnit.class, elementTmp.getMeasureUnit()));
				importLogItem.setMeasureUnit(UtilsBusiness.copyObject(MeasureUnit.class, elementTmp.getElementType().getMeasureUnit()));
				importLogItem.setElement(UtilsBusiness.copyObject(Element.class, elementTmp));
				importLogItem.setImportLog(objPojo);
				importLogItem.setItemStatus(itemStatus);
				importLogItem.setAmountExpected(1D);
				daoImportLogItem.createImportLogItem(UtilsBusiness.copyObject(ImportLogItem.class,importLogItem));
				
			}
		}catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación saveImportLogItems/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina saveImportLogItems/ImportLogItemBusinessBean ==");
        }
		
	}
	
	/**
	 * Metodo: Permite enviar un email al operador logistico indicando el envio del registro de importación
	 * @param wareHouse - Bodega asociada al operador logistico
	 * @param importLog - ImportLog Registro de importación que fue generado 
	 * @throws BusinessException en caso de error al enviar email al operador logistico
	 * @throws DAOServiceException en caso de error al enviar email al operador logistico
	 * @throws DAOSQLException en caso de error al enviar email al operador logistico
	 * @author gfandino
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void sendMailToRegisterImportLogNotification(ImportLog importLog, List<User> usuarios, String userNot) throws BusinessException, DAOServiceException,
			DAOSQLException {
		for(User usuario : usuarios){
			SendEmailDTO email = new SendEmailDTO();
			StringBuffer observations = new StringBuffer("\n");
			EmailTypesEnum emailType = EmailTypesEnum.REGISTER_IMPORT_LOG_HAS_BEEN_SENT;
			observations.append(emailType.getDescription() + " \n\n"+ ApplicationTextEnum.NOTIFIES.getApplicationTextValue()+": " + userNot);
			observations.append(" \n\n");
			email.setNewsType(emailType.getEmailTypecode());
			email.setNewsDocument(importLog.getId().toString());
			email.setNewsObservation(observations.toString());
			email.setNewsSourceUser(usuario.getName());
			List<String> recipients = new ArrayList<String>();
			recipients.add(usuario.getEmail());
			email.setRecipient(recipients);
			businessEmailType.sendEmailByEmailTypeCodeAsic(email, importLog.getDealer().getPostalCode().getCity().getState().getCountry().getId());
		}
	}
	
	/**
	 * 
	 * Metodo: Envía un correo de notificación cuando se modifica un registro de importación con inconsistencias
	 * @param inconsistencyListCreate
	 * @param inconsistencyListUpdate
	 * @param importLog
	 * @param users
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void sendMailToNotifyInconsistenciesUpdate(
			List<ImportLogInconsistencyVO> inconsistencyListCreate,
			List<ImportLogInconsistencyVO> inconsistencyListUpdate,
			List<ImportLogInconsistencyVO> inconsistencyListClose,
			List<ImportLogInconsistencyVO> inconsistencyListDelete,
			List<ImportLogInconsistencyVO> addElementInconsistencyList,
			List<ImportLogInconsistencyVO> delElementInconsistencyList,
			List<ImportLogInconsistencyVO> listaUpdate,
			ImportLogVO importLog, 
			List<User> users) throws BusinessException, DAOServiceException,
			DAOSQLException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SendEmailDTO email = new SendEmailDTO();
		for(User usuario : users){
			EmailTypesEnum emailType = EmailTypesEnum.IMPORT_LOG_INCONSISTENCIES_UPDATED;
			email.setNewsType(emailType.getEmailTypecode());
			email.setNewsDocument(importLog.getId().toString());
			StringBuilder inconsistencies = new StringBuilder("\n");
			
			
			/************Inconsistencias creadas************/
			if (inconsistencyListCreate.size() > 0){
				inconsistencies.append(ApplicationTextEnum.INCONSISTENCIES_CREATED.getApplicationTextValue()+":");
				inconsistencies.append("\n\n");
				for(ImportLogInconsistencyVO newInconcistency : inconsistencyListCreate){
					appendInconsistencyInfo(newInconcistency, inconsistencies, sdf, importLog.getCountry().getId());
				}
			}
			
			
			/************Inconsistencias actualizadas************/
			if ( inconsistencyListUpdate.size() > 0 ){
				inconsistencies.append("\n\n");
				inconsistencies.append(ApplicationTextEnum.INCONSISTENCIES_UPDATE.getApplicationTextValue()+":");
				inconsistencies.append("\n\n");
				for(ImportLogInconsistencyVO updatedInconcistency : inconsistencyListUpdate){
					appendInconsistencyInfo(updatedInconcistency, inconsistencies, sdf, importLog.getCountry().getId());
				}
			}
			
			
			/************Inconsistencias cerradas************/
			if ( inconsistencyListClose.size() > 0 ){
				inconsistencies.append("\n\n");
				inconsistencies.append(ApplicationTextEnum.INCONSISTENCIES_CLOSED.getApplicationTextValue());
				inconsistencies.append("\n\n");
				for(ImportLogInconsistencyVO closedInconcistency : inconsistencyListClose){
					appendInconsistencyInfo(closedInconcistency, inconsistencies, sdf, importLog.getCountry().getId());
				}
			}
			
			
			/************Inconsistencias eliminadas************/
			if ( inconsistencyListDelete.size() > 0 ){
				inconsistencies.append("\n\n");
				inconsistencies.append(ApplicationTextEnum.INCONSISTENCIES_ELIMINATED.getApplicationTextValue());
				inconsistencies.append("\n\n");
				for(ImportLogInconsistencyVO deleteInconcistency : inconsistencyListDelete){
					appendInconsistencyInfo(deleteInconcistency, inconsistencies, sdf, importLog.getCountry().getId());
				}
			}
			
			
			/************Inconsistencias  por elemento adicionado************/
			if ( addElementInconsistencyList.size() > 0 ){
				inconsistencies.append("\n\n");
				inconsistencies.append(ApplicationTextEnum.INCONSISTENCIES_CREATED_ADDITION_ELEMENT.getApplicationTextValue());
				inconsistencies.append("\n\n");
				for(ImportLogInconsistencyVO addItemInconcistency : addElementInconsistencyList){
					appendInconsistencyInfo(addItemInconcistency, inconsistencies, sdf, importLog.getCountry().getId());
				}
			}
			
			
			/************Inconsistencias actualizadas************/
			if ( delElementInconsistencyList.size() > 0 ){
				inconsistencies.append("\n\n");
				inconsistencies.append(ApplicationTextEnum.INCONSISTENCIES_CREATED_ELIMINATION_ELEMENT.getApplicationTextValue());
				inconsistencies.append("\n\n");
				for(ImportLogInconsistencyVO deleItemInconcistency : delElementInconsistencyList){
					appendInconsistencyInfo(deleItemInconcistency, inconsistencies, sdf, importLog.getCountry().getId());
				}
			}
			
			
			/************Inconsistencias por elemento actualizado************/
			if ( listaUpdate.size() > 0 ){
				inconsistencies.append("\n\n");
				inconsistencies.append(ApplicationTextEnum.CREATED_INCONSISTENCIES_UPDATING_EXPECTED_NUMBER_ITEM.getApplicationTextValue());
				inconsistencies.append("\n\n");
				for(ImportLogInconsistencyVO updatedInconcistency : listaUpdate){
					appendInconsistencyInfo(updatedInconcistency, inconsistencies, sdf, importLog.getCountry().getId());
				}
			}
			
			
			inconsistencies.append("\n");
			email.setNewsObservation(inconsistencies.toString());
			email.setNewsSourceUser(importLog.getUser().getName());
			List<String> recipients = new ArrayList<String>();
			recipients.add(usuario.getEmail());
			email.setRecipient(recipients);
			businessEmailType.sendEmailByEmailTypeCodeAsic(email, importLog.getDealer().getPostalCode().getCity().getState().getCountry().getId());
		}
	}

	/**
	 * Metodo: Agrega a un objeto StringBuilder la información de una inconsistencia para envío de correo
	 * @param inconcistency
	 * @param sb
	 * @param sdf
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	//Modificado para Requerimiento: CC057
	private void appendInconsistencyInfo(ImportLogInconsistencyVO inconcistency, StringBuilder sb, SimpleDateFormat sdf, Long countryId)
		throws DAOServiceException, DAOSQLException, BusinessException{
		try {
			sb.append("\n");
			if(inconcistency.getImpLogItem().getElement().getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity())){
				sb.append(ApplicationTextEnum.ELEMENT.getApplicationTextValue()+"\t"+ApplicationTextEnum.SERIAL.getApplicationTextValue()+"\n");
				Serialized serialized = daoSerialized.getSerializedByID(inconcistency.getImpLogItem().getElement().getId() );
				sb.append("" + serialized.getElement().getElementType().getTypeElementCode() + "\t" + serialized.getElement().getElementType().getTypeElementName() + "\t"+ serialized.getSerialCode() +"\n");
				//Mira si tiene vinculado para concatenarle la información
				if (serialized.getSerialized() != null){
					sb.append("" + serialized.getSerialized().getElement().getElementType().getTypeElementCode() + "\t" + serialized.getSerialized().getElement().getElementType().getTypeElementName() + "\t"+ serialized.getSerialized().getSerialCode() +"\n");
				}
				
			}else{
				sb.append( ApplicationTextEnum.ELEMENT.getApplicationTextValue()+"\t"+ApplicationTextEnum.QUANTITY.getApplicationTextValue()+"\n" );
				NotSerialized notSerialized = daoNotSerialized.getNotSerializedbyElementTypeID( inconcistency.getImpLogItem().getElement().getElementType().getId(), countryId );
				sb.append( "" + notSerialized.getElement().getElementType().getTypeElementCode() + "\t" + notSerialized.getElement().getElementType().getTypeElementName() + "" );
				Double cantidad = 0D;
				if ( inconcistency.getExpectedAmount()!=null && inconcistency.getExpectedAmount()>0 ){
					cantidad = inconcistency.getExpectedAmount().doubleValue();
				}else{
					if ( inconcistency.getImpLogItem().getAmountExpected()!=null && inconcistency.getImpLogItem().getAmountExpected()>0 ){
						cantidad = inconcistency.getImpLogItem().getAmountExpected();
					}else{
						if ( notSerialized.getInitialQuantity()!=null && notSerialized.getInitialQuantity()>0 ){
							cantidad = notSerialized.getInitialQuantity();
						}
					}
				}
				sb.append( "\t" + cantidad.toString() + " \n" );
			}
			sb.append( "\n\n" );
			sb.append("\n");
			sb.append(ApplicationTextEnum.ELEMENT_ID.getApplicationTextValue()+": ");
			sb.append(inconcistency.getImportLogItem());
			sb.append("\n");
			sb.append(ApplicationTextEnum.TYPE.getApplicationTextValue()+": ");
			sb.append(inconcistency.getInconsistencyType().getIncTypeName());
			sb.append("\n");
			sb.append(ApplicationTextEnum.OBSERVATION.getApplicationTextValue()+": ");
			sb.append(inconcistency.getComments());
			sb.append("\n");
			User user = daoUser.getUserById(inconcistency.getUser());
			if(user != null){
				sb.append(ApplicationTextEnum.USER_REPORTING.getApplicationTextValue()+": ");
				sb.append(user.getName());
				sb.append("\n");
			}
			sb.append(ApplicationTextEnum.DATE.getApplicationTextValue()+": ");
			sb.append(sdf.format(inconcistency.getInconsistencyDate()));
			sb.append("\n");
			sb.append(ApplicationTextEnum.STATUS.getApplicationTextValue()+": ");
			sb.append(inconcistency.getInconsistencyStatus().getIncStatusName());
			sb.append("\n");
			sb.append(ApplicationTextEnum.RESPONSE.getApplicationTextValue()+": ");
			sb.append(inconcistency.getAnswer() != null ? inconcistency.getAnswer() : "");
			sb.append("\n--------------\n");
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación createImportLog/ImportLogBusinessBean ==");
			throw this.manageException(ex);
		}
	}
	
	/**
	 * Metodo: Permite realizar las validaciones para crear un registro de
	 * importación
	 * 
	 * @param obj - ImportLogVO objeto al que se le va a validar la información
	 * @throws BusinessException - Fallo alguna de las validaciones realizadas
	 * @author gfandino
	 */
	private void validateImportLogToRegisterAndByCountry(ImportLogVO obj, User user)
			throws BusinessException {
		try {
			// 1) Validar que el estado sea creado o enviado
			if (!obj.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity())
					&& !obj.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_CREATED.getCodeEntity())) {
				throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_STATUS_IS_NOT_VALID.getCode(),	ErrorBusinessMessages.IMPORT_LOG_STATUS_IS_NOT_VALID.getMessage());
			}

			Calendar fechaActual = Calendar.getInstance();
			fechaActual.add(Calendar.DAY_OF_YEAR, -1);
			// 3) Validar que la fecha de entraga sea mayor o igual a la actual
			Calendar fechaEntrega = new GregorianCalendar();
			fechaEntrega.setTime(obj.getDeliveryDate());
			if (!fechaEntrega.after(fechaActual)) {
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN381.getCode(), ErrorBusinessMessages.STOCK_IN381.getMessage());
			}
			
			// 6) Valida que no encuentre un numero de orden de compra repetido, con el mismo proveedor.
			List<ImportLog> iltv = daoImportLog.getImportLogByPurchaseOrder( obj.getPurchaseOrder(),obj.getCountry().getId() );
			for (ImportLog igt: iltv){
				if (igt.getDealer().getId() == obj.getDealer().getId()){
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN397.getCode(),	ErrorBusinessMessages.STOCK_IN397.getMessage());
				}
			}
			
			// 7) Validar tamaño del campo Orden de Compra y Documento de Importacion
			if (obj.getPurchaseOrder() != null && obj.getPurchaseOrder().length() > 100 ){
				Object params[] = {ApplicationTextEnum.PURCHASE_ORDER.getApplicationTextValue()};
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN416.getCode(),	ErrorBusinessMessages.STOCK_IN416.getMessage(params), UtilsBusiness.getParametersWS(params));
			}
			
			// 8) Valida que venga el documento de importación.
			if (obj.getImportDoc() != null && obj.getImportDoc().length() > 100 ){
				Object params[] = {ApplicationTextEnum.DOCUMENT_IMPORT.getApplicationTextValue()};
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN416.getCode(),	ErrorBusinessMessages.STOCK_IN416.getMessage(params), UtilsBusiness.getParametersWS(params));	
			}
			
			// 9) Validar que el registro de importacion no tenga un numero de orden con el mismo proveedor
			//    para estados que permitan crearlo nuevamente.
			ModifyImportLogDTO modifyImportLogCriteria = new ModifyImportLogDTO();
			modifyImportLogCriteria.setPurchaseOrder(obj.getPurchaseOrder());
			modifyImportLogCriteria.setSupplierId(obj.getSupplier().getId());
			
			List<String> importStatus = new ArrayList<String>();
			importStatus.add(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_DELETED.getCodeEntity());
			Long recordQty = daoImportLog.getImportLogByPurchaseOrderAndStatus(modifyImportLogCriteria, user.getCountry().getId(), importStatus);
			if (recordQty > 0L){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN432.getCode(),	ErrorBusinessMessages.STOCK_IN432.getMessage());
			}
			
					
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación createImportLog/ImportLogBusinessBean ==");
			throw this.manageException(ex);
		}
	}

	/**
	 * Asigna el estado a un registro de importación
	 * 
	 * @param codesBusinessEntityEnum
	 * @return ImportLogStatus
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private ImportLogStatus getImportLogStatus(CodesBusinessEntityEnum codesBusinessEntityEnum) throws BusinessException {
		ImportLogStatus importLogStatus = new ImportLogStatus();
		Long statusId = null;
		try {
			statusId = Long.parseLong(codesBusinessEntityEnum.getCodeEntity());
		} catch (Exception e) {
			throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		}
		importLogStatus.setId(statusId);
		return importLogStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#
	 * updateImportLog(co.com.directv.sdii.model.vo.ImportLogVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateImportLog(ImportLogVO obj,Long userId) throws BusinessException {
		log.debug("== Inicia updateImportLog/ImportLogBusinessBean ==");
		UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			ImportLog objPojo = UtilsBusiness.copyObject(ImportLog.class, obj);
			daoImportLog.updateImportLog(objPojo);
			if ( objPojo.getImportLogStatus() != null && objPojo.getImportLogStatus().getStatusCode()!=null ){
				boolean ingresa = true;
				if ( objPojo.getImportLogStatus().getStatusCode().equals( CodesBusinessEntityEnum.IMPORT_LOG_MODIFICATION_STATUS_SENDED.getCodeEntity() ) ){
					if ( daoImpLogModification.getImpLogModificationByImportLogIDAndStatusId( objPojo.getId(), objPojo.getImportLogStatus().getId() ).size() > 0 )
	        			ingresa = false;
				}
				if (ingresa)
					createImportLogItemModification(userId, objPojo, impLogModBusiness.importLogStatusToImportLogModification( objPojo.getImportLogStatus() ) );
			}
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación updateImportLog/ImportLogBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina updateImportLog/ImportLogBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#
	 * deleteImportLog(co.com.directv.sdii.model.vo.ImportLogVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteImportLog(ImportLogVO obj, Long userId) throws BusinessException {
		log.debug("== Inicia deleteImportLog/ImportLogBusinessBean ==");
		UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			//Consulto el usuario
			User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
			
			//Validar que el registro de importacion no este en estado 
			//Recepcionado, Inconsistente, Confirmado Parcial
			ImportLog ilTv = daoImportLog.getImportLogByID(obj.getId());
			if (ilTv == null){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN414.getCode(),	ErrorBusinessMessages.STOCK_IN414.getMessage());
			}
			if (ilTv.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_RECEIVED.getCodeEntity())
				|| ilTv.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_INCONSISTENCY.getCodeEntity())
				|| ilTv.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity())
				|| ilTv.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_PROCESS.getCodeEntity())
				|| ilTv.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity())
				|| ilTv.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_DELETED.getCodeEntity())){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN357.getCode(),	ErrorBusinessMessages.STOCK_IN357.getMessage());
			}
			
			
			
			ImportLog importLog = UtilsBusiness.copyObject(ImportLog.class, obj);
			ImportLogItemResponse ilir = daoImportLogItem.getImportLogItemsByImportLog(importLog.getId(), null);
			 
			
			
			Warehouse warehouse=daoWarehouse.getWarehousesByDealerIdAndWhTypeCode(importLog.getDealer().getId(),CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity());
			UtilsBusiness.assertNotNull(warehouse, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			//Eliminar los elementos 
			businessImportLogItem.deleteElementItemImportLog(ilTv, UtilsBusiness.convertList(ilir.getImportLogItems(), ImportLogItemVO.class), user);
				
			ImportLogStatus newImportLogStatus = new ImportLogStatus();
			newImportLogStatus = daoImportLogStatus.getImportLogStatusByCode(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_DELETED.getCodeEntity());
			ilTv.setImportLogStatus(newImportLogStatus);
			
			this.updateImportLog(UtilsBusiness.copyObject(ImportLogVO.class, ilTv),userId);
			
			this.createImportLogItemModification(userId, importLog, impLogModBusiness.importLogStatusToImportLogModification( importLog.getImportLogStatus() ) );
			
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación deleteImportLog/ImportLogBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina deleteImportLog/ImportLogBusinessBean ==");
		}
	}
	
	/**
	 * Metodo: Permite identificar si todos los items del registro de
	 * importacion se encuentran en estado </b>Recepcionado</b> - Caso de Uso
	 * Inv_05
	 * 
	 * @param originalList
	 *            la lista con los items a verificar
	 * @author garciniegas
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 * @throws PropertiesException
	 * @throws HelperException 
	 */
	public boolean allImportLogItemAreRecepted(List<ImportLogItem> originalList)
			throws PropertiesException, HelperException {
		
		long status = CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getIdEntity( ItemStatus.class.getName() );

		for (ImportLogItem item : originalList) {
			if (item.getItemStatus().getId().longValue() != status)
				return false;
		}

		return true;
	}

	/**
	 * Metodo: Actualiza el estado a Recepcionado de un item de un registro de importación
	 * 
	 * @param item
	 * @param user
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 * @throws PropertiesException
	 * @throws BusinessException
	 *  
	 * @author Jimmy Vélez Muñoz
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateItemStatusInImportLog(ImportLogItemVO item, UserVO user) throws BusinessException {
		log.debug("== Inicia updateItemStatusInImportLog/ImportLogBusinessBean ==");
		try{
			ItemStatus status = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity());
			Serialized serialized = null;
			
			serialized = daoSerialized.getSerializedElementByElementId( item.getElement().getId());

			
			ImportLogItem itemToUpdate = daoImportLogItem.getImportLogItemByElementId( serialized.getElementId() );
			
			if( itemToUpdate==null )
				throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

			itemToUpdate.setItemStatus(status);
			daoImportLogItem.updateImportLogItem(itemToUpdate);

			//Crea la confirmacion del elemento serializado
			ImpLogConfirmation itemConfirmation = new ImpLogConfirmation();
			itemConfirmation.setConfirmationDate( UtilsBusiness.fechaActual() );
			itemConfirmation.setConfirmedQuantity(1D);
			itemConfirmation.setImportLogItem(itemToUpdate);
			itemConfirmation.setPendQuantity(0D);
			itemConfirmation.setUser(user);
			daoImportLogConfirmation.createImpLogConfirmation(itemConfirmation);
			Element element = daoElement.getElementByID( item.getElement().getId() );
			ElementStatus elementStatus = new ElementStatus();
			elementStatus.setId(CodesBusinessEntityEnum.ELEMENT_STATUS_S01.getIdEntity(ElementStatus.class.getName()));
			element.setElementStatus(elementStatus);
			daoElement.updateElement(element);
		} catch(Throwable e){
			log.error("== Error updateItemStatusInImportLog/ImportLogBusinessBean ==",e);
			throw this.manageException(e);
		} finally {
			log.debug("== Finaliza updateItemStatusInImportLog/ImportLogBusinessBean ==");
		}
	}
	
	/**
	 * Metodo: Permite informar al analista de logistica que el registro ha sido
	 * recepcionado - Caso de Uso Inv_05
	 * 
	 * @param wareHouse
	 *            el objeto que Warehouse que indica la bodega de control de
	 *            calidad del operador logistico junto con la informacion del
	 *            mismo.
	 * @author garciniegas
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void sendMailToImportLogNotification(Warehouse wareHouse, UserVO user,Long importLogID) throws BusinessException, DAOServiceException, DAOSQLException {
		SendEmailDTO email = new SendEmailDTO();
		email.setNewsType(EmailTypesEnum.IMPORT_LOG_HAS_BEEN_RECEIVED.getEmailTypecode());
		email.setNewsDocument(importLogID.toString());
		email.setNewsObservation(ApplicationTextEnum.IMPORT_REGISTRATION.getApplicationTextValue());
		email.setNewsSourceUser(user.getName());
		List<String> recipients = new ArrayList<String>();
		recipients.add(wareHouse.getResponsibleEmail());
		email.setRecipient(recipients);
		businessEmailType.sendEmailByEmailTypeCodeAsic(email, user.getCountry().getId());
	}

	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void sendMailToImportLogNotification(User destinyUser,UserVO user,Long importLogID) throws BusinessException, DAOServiceException, DAOSQLException {
		SendEmailDTO email = new SendEmailDTO();
		email.setNewsType(EmailTypesEnum.IMPORT_LOG_HAS_BEEN_RECEIVED.getEmailTypecode());
		email.setNewsDocument(importLogID.toString());
		email.setNewsObservation(ApplicationTextEnum.IMPORT_REGISTRATION.getApplicationTextValue());
		email.setNewsSourceUser(user.getName());
		List<String> recipients = new ArrayList<String>();
		recipients.add(destinyUser.getEmail());
		email.setRecipient(recipients);
		businessEmailType.sendEmailByEmailTypeCodeAsic(email, user.getCountry().getId());
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#getPendingImportLogsByLogisticOp(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ImportLogVO> getPendingImportLogsByLogisticOp(Long logisticOperatorId) throws BusinessException {
		log.debug("== Inicia getPendingImportLogsByLogisticOp/ImportLogBusinessBean ==");
		try {
			List<ImportLogVO> resultVo = null;

			String sentStatusCode = CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity();
			String partialConfirmedStatusCode = CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity();
			List<ImportLog> sentImportLogs = daoImportLog.getImportLogsByStatusCodeAndLogisticOp(sentStatusCode,logisticOperatorId);
			List<ImportLog> partialConfirmedImportLogs = daoImportLog.getImportLogsByStatusCodeAndLogisticOp(partialConfirmedStatusCode,logisticOperatorId);

			resultVo = UtilsBusiness.convertList(sentImportLogs,ImportLogVO.class);
			resultVo.addAll(UtilsBusiness.convertList(partialConfirmedImportLogs, ImportLogVO.class));

			return resultVo;

		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getPendingImportLogsByLogisticOp/ImportLogBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getPendingImportLogsByLogisticOp/ImportLogBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#getImportLogByCriteria(co.com.directv.sdii.model.dto.ModifyImportLogDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLogResponse getImportLogByCriteria(ModifyImportLogDTO modifyImportLogCriteria, Long userId, RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getImportLogByCriteria/ImportLogBusinessBean ==");
		UtilsBusiness.assertNotNull(modifyImportLogCriteria, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(userId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			
			//Consulto el usuario 
			User user = daoUser.getUserById(userId);
			if(user == null){
				throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			}

			Calendar calCreationDate = null, calDeliveryDate = null, calShippingDate = null;
			//Se ajusta la lectura de fechas enviadas desde presentación
			if (modifyImportLogCriteria.getCreationDate() != null){
				calCreationDate = Calendar.getInstance();
				calCreationDate.setTime(modifyImportLogCriteria.getCreationDate());
			}
			if (modifyImportLogCriteria.getDeliveryDate() != null){
				calDeliveryDate = Calendar.getInstance();
				calDeliveryDate.setTime(modifyImportLogCriteria.getDeliveryDate());
			}
			if (modifyImportLogCriteria.getShippingDate() != null){
				calShippingDate = Calendar.getInstance();
				calShippingDate.setTime(modifyImportLogCriteria.getShippingDate());
			}
			if (calCreationDate != null && calCreationDate.get(Calendar.DAY_OF_MONTH) == 1 && calCreationDate.get(Calendar.MONTH) == 0 && calCreationDate.get(Calendar.YEAR) == 1900){
				modifyImportLogCriteria.setCreationDate(null);
			}
			if (calDeliveryDate != null && calDeliveryDate.get(Calendar.DAY_OF_MONTH) == 1 && calDeliveryDate.get(Calendar.MONTH) == 0 && calDeliveryDate.get(Calendar.YEAR) == 1900){
				modifyImportLogCriteria.setDeliveryDate(null);
			}
			if (calShippingDate != null && calShippingDate.get(Calendar.DAY_OF_MONTH) == 1 && calShippingDate.get(Calendar.MONTH) == 0 && calShippingDate.get(Calendar.YEAR) == 1900){
				modifyImportLogCriteria.setShippingDate(null);
			}
			// Valida que al menos un parametro no venga nulo
			//modifyImportLogCriteria.validateCiteria();			
			ImportLogResponse response = daoImportLog.getImportLogByCriteria(modifyImportLogCriteria, user.getCountry().getId(), requestCollInfo);
			response.setImportLog(null);

			return response;

		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getImportLogByCriteria/ImportLogBusinessBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getImportLogByCriteria/ImportLogBusinessBean ==");
		}
	}
	
	private List <ImportLogVO> completeFieldsSN(List <ImportLogVO> iltM) throws DAOServiceException, DAOSQLException{
		
		List <ImportLogVO> illtoReturn = new ArrayList<ImportLogVO>();
		for (ImportLogVO il: iltM){
				il.setHaveSerializedElements(!daoImportLogItem.getImportLogItemsByImportLogIdAndIsSerialized(il.getId(), true, null).getImportLogItems().isEmpty());
				il.setHaveNotSerializedElements(!daoImportLogItem.getImportLogItemsByImportLogIdAndIsSerialized(il.getId(), false, null).getImportLogItems().isEmpty());
				illtoReturn.add(il);
		}
		
		return illtoReturn;
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#getImportLogsToConfirm(java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Boolean, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ImportLogResponse getImportLogsToConfirm(ModifyImportLogDTO modifyImportLogCriteria, RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicio getImportLogsToConfirm/ImportLogBusinessBean ==");
		ImportLogResponse response = null;
		try {
			UtilsBusiness.assertNotNull(modifyImportLogCriteria.getDealerId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			if (modifyImportLogCriteria.getSerialNumber() != null){
				response = daoImportLog.getSerialCodeImportLogElement(modifyImportLogCriteria, requestCollInfo);
			}else{
				response = daoImportLog.getImportLogsElement(modifyImportLogCriteria, requestCollInfo);
			}
				
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getImportLogsToConfirm/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getImportLogsToConfirm/ImportLogBusinessBean ==");
		}
	}
	
		
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#getImportLogsByIdCreationDateAndLogisticOp(java.lang.Long, java.util.Date, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)		
	public ImportLogResponse getImportLogsByIdCreationDateAndLogisticOp(Long importLogId,Date creationDate,Long logisticOpId, RequestCollectionInfo requestCollInfo) throws BusinessException
	{
		log.debug("== Inicia getImportLogsByIdCreationDateAndLogisticOp/ImportLogBusinessBean ==");
		try {
			if( importLogId!=null && importLogId==-1 )
				importLogId = null;
			if( creationDate!=null && String.valueOf( creationDate ).indexOf( "1900" )!=-1 )
				 creationDate = null;
			
			ImportLogResponse response;
			response = daoImportLog.getImportLogsByIdCreationDateAndLogisticOp(importLogId, creationDate, logisticOpId, requestCollInfo);
			response.setImportLogVO( UtilsBusiness.convertList( response.getImportLog(),ImportLogVO.class) );
			response.setImportLog(null);
			return response;
		
		} catch (Throwable ex) {
			log.error("== Error getImportLogsByIdCreationDateAndLogisticOp/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getImportLogsByIdCreationDateAndLogisticOp/ImportLogBusinessBean ==");
		}
	
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#getElementsInImportLog(java.lang.Long, java.lang.Boolean, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)		
	public ImportLogItemResponse getElementsInImportLog(Long importLogId,Boolean serialized, RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getElementsInImportLog/ImportLogBusinessBean ==");
		try {
			String errorCode    = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
			String errorMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
			UtilsBusiness.assertNotNull(importLogId,errorCode,errorMessage);
			UtilsBusiness.assertNotNull(serialized,errorCode,errorMessage);
			ImportLogItemResponse response = daoImportLogItem.getElementsInImportLog(importLogId, serialized, requestCollInfo);
			List<ImportLogItem> imporlogItems = response.getImportLogItems();
			List<ImportLogItemVO> itemsVO = UtilsBusiness.convertList(imporlogItems ,ImportLogItemVO.class); 
			
			for(ImportLogItemVO item : itemsVO){
				if(item.getId() != null){
					item.setConfirmedQuantity( daoImportLogConfirmation.getImportLogItemConfirmationSumByImpLogItemId( item.getId() ) );
				}
			}
			
			response.setImportLogItemsVO(itemsVO);
			response.setImportLogItems(null);
			
			return response;		
		} catch (Throwable ex) {
			log.error("== Error == getElementsInImportLog/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementsInImportLog/ImportLogBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#getImpLogConfirmationsByImpLogItemId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)		
	public List<ImpLogConfirmationVO> getImpLogConfirmationsByImpLogItemId( Long importLogItemId )throws BusinessException
	{
		log.debug("== Inicia getImpLogConfirmationsByImpLogItemId/ImportLogBusinessBean ==");
		try {
			String errorCode    = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
			String errorMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
			UtilsBusiness.assertNotNull(importLogItemId,errorCode,errorMessage);
			return UtilsBusiness.convertList( daoImportLogConfirmation.getImpLogConfirmationsByImpLogItemId( importLogItemId ),ImpLogConfirmationVO.class);
		} catch (Throwable ex) {
			log.error("== Error == getImpLogConfirmationsByImpLogItemId/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getImpLogConfirmationsByImpLogItemId/ImportLogBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#getImportLogInconsistenciesByImportLogId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List<ImportLogInconsistencyVO> getImportLogInconsistenciesByImportLogId( Long importLogId ) throws BusinessException
	{
		log.debug("== Inicia getImportLogInconsistenciesByImportLogId/ImportLogBusinessBean ==");
		try {
			String errorCode    = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
			String errorMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
			UtilsBusiness.assertNotNull(importLogId,errorCode,errorMessage);
			List<ImportLogInconsistencyVO> listImportLogInc =  UtilsBusiness.convertList( daoImportLogInconsistency.getImportLogInconsistenciesByImportLogId(importLogId),ImportLogInconsistencyVO.class);
			
			for(ImportLogInconsistencyVO impLogInc : listImportLogInc){
				ImportLogItem impLogItem = daoImportLogItem.getImportLogItemByID(impLogInc.getImportLogItem());
				if ( impLogItem != null ){
					impLogInc.setImpLogItem(UtilsBusiness.copyObject(ImportLogItemVO.class, impLogItem));
					
					if( impLogItem.getElement().getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity())){
	    				//Busco el elemento serializado y lo asocio al VO
						impLogInc.getImpLogItem().setSerializedVO( UtilsBusiness.copyObject(SerializedVO.class, daoSerialized.getSerializedByID( impLogItem.getElement().getId() ) ) );
	    			} else if( impLogItem.getElement().getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity())){
	    				//busco el elemento no serializado
	    				impLogInc.getImpLogItem().setNotSerializedVO( UtilsBusiness.copyObject(NotSerializedVO.class, daoNotSerialized.getNotSerializedByID(  impLogItem.getElement().getId() ) ) );
	    				impLogInc.getImpLogItem().setConfirmedQuantity( daoImportLogConfirmation.getImportLogItemConfirmationSumByImpLogItemId( impLogItem.getId() ) );
	    			}
				}
			}
			
			fillAditionalIncData(listImportLogInc);
			return listImportLogInc;
		
		} catch (Throwable ex) {
			log.error("== Error == getImportLogInconsistenciesByImportLogId/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getImportLogInconsistenciesByImportLogId/ImportLogBusinessBean ==");
		}
	}
	
	  
	/**
	 * Metodo: Agrega información adicional de import log
	 * @param result
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jjimenezh
	 */
	private void fillAditionalIncData(List<ImportLogInconsistencyVO> result) throws DAOServiceException, DAOSQLException, BusinessException {
		Long userId = 0L;
		for (ImportLogInconsistencyVO importLogInconsistencyVO : result) {
			userId = importLogInconsistencyVO.getUser();
			if(userId != null){
				User user = daoUser.getUserById(userId);
				if(user != null){
					importLogInconsistencyVO.setUserName(user.getName());
				}
			}
		}
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#createImportLogInconsistencies(java.util.List, co.com.directv.sdii.model.vo.ImportLogVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public void createImportLogInconsistencies( List<ImportLogInconsistencyVO>inconsistencyList, ImportLogVO importLog ) throws BusinessException
	{
		createInconsistencies(inconsistencyList, importLog, true);
	}
	
	/**
	 * Crea una lista de inconsistencias en la base de datos
	 * Metodo: <Descripcion>
	 * @param inconsistencyList. Lista de inconsistencias a crear 
	 * @param importLog. Log de importación asociado a las inconsistencias
	 * @param sendNotificationMail. Flag para determinar si se envía correo de notificación
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	private void createInconsistencies( List<ImportLogInconsistencyVO>inconsistencyList, ImportLogVO importLog, boolean sendNotificationMail ) throws BusinessException
	{
		log.debug("== Inicia createImportLogInconsistencies/ImportLogBusinessBean ==");
		try {
			String errorCode    = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
			String errorMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
		    UtilsBusiness.assertNotNull( importLog,errorCode,errorMessage);
			UtilsBusiness.assertNotNull( importLog,errorCode,errorMessage);
			UtilsBusiness.assertNotNull( inconsistencyList,errorCode,errorMessage);   
			List<ImportLogInconsistency>listToCreate = new ArrayList<ImportLogInconsistency>();
			List<ElementVO> elementsVO = new ArrayList<ElementVO>();
			Long userId = null;
			for(ImportLogInconsistency inconsistency:inconsistencyList){
				ElementVO elementVO = new ElementVO();
				elementVO.setId(inconsistency.getImportLogItem());
				elementsVO.add(elementVO);
				UtilsBusiness.assertNotNull(inconsistency,errorCode,errorMessage);   
				UtilsBusiness.assertNotNull(inconsistency.getInconsistencyDate(),errorCode,errorMessage);   
				UtilsBusiness.assertNotNull(inconsistency.getComments(),errorCode,errorMessage);  
				UtilsBusiness.assertNotNull(inconsistency.getUser(),errorCode,errorMessage);   
				
				inconsistency.setInconsistencyStatus(daoInconsistency.getInconsistencyStatusByCode(CodesBusinessEntityEnum.INCONSISTENCY_STATUS_ACTIVE.getCodeEntity()));
				UtilsBusiness.assertNotNull(inconsistency.getInconsistencyStatus(),errorCode,errorMessage); 
				UtilsBusiness.assertNotNull(inconsistency.getInconsistencyStatus().getId(),errorCode,errorMessage);
				
				//inconsistency.setInconsistencyType( daoInconsistencyType.getInconsistencyTypeByCodeAndActive( CodesBusinessEntityEnum.INCONSISTENCY_TYPE_IMPORT_LOG_INCONSISTENCY.getCodeEntity() ) );
				UtilsBusiness.assertNotNull(inconsistency.getInconsistencyType(),errorCode,errorMessage);
				UtilsBusiness.assertNotNull(inconsistency.getInconsistencyType().getId(),errorCode,errorMessage);
				
				userId = inconsistency.getUser();
				inconsistency.setId(null);
				
				listToCreate.add( UtilsBusiness.copyObject( ImportLogInconsistency.class ,inconsistency ) );
				
				//cambiar el item inconsistente de forma individual.
				ImportLogItem objPojo = daoImportLogItem.getImportLogItemByID(inconsistency.getImportLogItem());
				ItemStatus newStatus = daoItemStatus.getItemStatusByCode( CodesBusinessEntityEnum.ITEM_STATUS_INCONSISTENCY_PROCESS.getCodeEntity() );
				if (inconsistency != null && inconsistency.getExpectedAmount() != null && inconsistency.getExpectedAmount().longValue() > 0 ){
				  objPojo.setAmountExpected( (double) inconsistency.getExpectedAmount().longValue() );
				}
				//Valida que el Elemento no este ya en estado inconsistente
				if (objPojo.getItemStatus().getId().longValue() == newStatus.getId().longValue()){
					//No puede registrar una inconsistencia a un elemento que ya esta inconsistente
					Object params[] = {objPojo.getElement().getId()+""};
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN433.getCode(),ErrorBusinessMessages.STOCK_IN433.getMessage(params), UtilsBusiness.getParametersWS(params));
				}
				objPojo.setItemStatus(newStatus);
				daoImportLogItem.updateImportLogItem(objPojo);
			
			}
			
			if (!elementsVO.isEmpty()){
				daoImportLogInconsistency.createImportLogInconsistenciesList(listToCreate,importLog);
				
				ImportLogStatus status = daoImportLogStatus.getImportLogStatusByCode( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_INCONSISTENCY.getCodeEntity() );
				
				//cambiar el item inconsistente de forma individual.
				
				ImportLog importLogP = daoImportLog.getImportLogByID(importLog.getId());
				importLogP.setImportLogStatus(status);
				daoImportLog.updateImportLog( importLogP );
				if ( importLogP.getImportLogStatus() != null && importLogP.getImportLogStatus().getStatusCode()!=null )
					createImportLogItemModification(importLogP.getUser().getId(), importLogP, impLogModBusiness.importLogStatusToImportLogModification( importLogP.getImportLogStatus() ) );
				
				Dealer dealer = daoDealer.getDealerByID(importLog.getDealer().getId());
				
				if(sendNotificationMail){
					//Se consultan los usuarios analistas logisticos del dealer operador logistico
			    	List<User> users = daoUser.getUsersByDealerIdAndRoleTypeCode(importLog.getDealer().getId(), CodesBusinessEntityEnum.ROLE_TYPE_DEALER_LOGISTICS_ANALYST.getCodeEntity());
			    	if (!users.isEmpty()){
			    		//Enviar correo de Notificación.
				    	//Invocar INV_08
						businessEmailType.sendMailByEmailType(elementsVO, users, dealer, EmailTypesEnum.REGISTER_IMPORT_LOG_INCONSISTENCY);	
			    	}
				}				
			}
			
			//Se calcula el estado del import log
			verificaEstadoImportLog( UtilsBusiness.copyObject(ImportLog.class, importLog), userId );
			
		} catch (Throwable ex) {
			log.error("== Error createImportLogInconsistencies/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina createImportLogInconsistencies/ImportLogBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#saveImportLogInconsistencies(java.util.List, java.util.List, co.com.directv.sdii.model.vo.ImportLogVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveImportLogInconsistencies(
			List<ImportLogInconsistencyVO> inconsistencyListCreate, 
			List<ImportLogInconsistencyVO> inconsistencyListUpdate, 
			List<ImportLogInconsistencyVO> inconsistencyListClose, 
			List<ImportLogInconsistencyVO> inconsistencyListDelete, 
			List<ImportLogInconsistencyVO> addElementInconsistencyList, 
			List<ImportLogInconsistencyVO> delElementInconsistencyList, 
			ImportLogVO importLog, Long userId) throws BusinessException {
		log.debug("== Inicia saveImportLogInconsistencies/ImportLogBusinessBean ==");
		UtilsBusiness.assertNotNull(userId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(importLog, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try{
			if (importLog.getId() == null){
				Object[] params = null;
				throw new BusinessException( ErrorBusinessMessages.STOCK_IN435.getCode(), ErrorBusinessMessages.STOCK_IN435.getMessage(params), UtilsBusiness.getParametersWS(params) );
			}
			importLog = UtilsBusiness.copyObject( ImportLogVO.class, daoImportLog.getImportLogByID(importLog.getId()) );
			
			//Validar que el usuario existe
			User user = daoUser.getUserById(userId);
			if(user==null){
				throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			}

			//Se crean las inconsistencias pero no se envía correo de notificación, ya que este se envía más adelante al final del método
			List<ImportLogInconsistencyVO> listaCreate = new ArrayList<ImportLogInconsistencyVO>();
			List<ImportLogInconsistencyVO> listaUpdate = new ArrayList<ImportLogInconsistencyVO>();
			if (inconsistencyListCreate!=null && !inconsistencyListCreate.isEmpty() ){
				//InconsistencyStatus inconsistencyStatus = daoInconsistency.getInconsistencyStatusByCode( CodesBusinessEntityEnum.INCONSISTENCY_STATUS_ACTIVE.getCodeEntity() );
				for (ImportLogInconsistencyVO importLogInconsistencyVO : inconsistencyListCreate) {
					if ( importLogInconsistencyVO.getExpectedAmount()==null ){
						listaCreate.add(importLogInconsistencyVO);
					}else{
						if (importLogInconsistencyVO.getExpectedAmount() > 0){
							listaUpdate.add(importLogInconsistencyVO);
						}else{
							throw new BusinessException(ErrorBusinessMessages.STOCK_IN446.getCode(),ErrorBusinessMessages.STOCK_IN446.getMessage());
						}
					}
				}
				
				if ( listaCreate.size() > 0 ){
					//Crea las que tengan expected amount en nulo o cero
					this.createInconsistencies(listaCreate, importLog, false);
				}
				
				if ( listaUpdate.size() > 0 ){
					//Actualizaa las que tienen un expected amount
					this.updateElementInconsistencies(importLog, listaUpdate);
				}
			}

			//Se actualizan items de inconsistencias
			List <ImportLogItemVO> importLogItemVO = new ArrayList<ImportLogItemVO>();
			if (inconsistencyListUpdate!=null && !inconsistencyListUpdate.isEmpty() ){
				for (ImportLogInconsistencyVO iliu: inconsistencyListUpdate){
					ImportLogItemVO ili = new ImportLogItemVO();
					ili.setId(iliu.getImportLogItem());
					ili.setAmountExpected( Double.valueOf( iliu.getExpectedAmount() ) );
					importLogItemVO.add(ili);
				}

				businessImportLogItem.updateQuantityItemImportLogNotSerializedElements(importLogItemVO);
			}
			
			//Se cierra la inconsistencia
			if ( inconsistencyListClose!=null && !inconsistencyListClose.isEmpty() ){
				this.closeInconsistencies(importLog, inconsistencyListClose, user);
			}
			
			//Se elimina la incosistencia
			if ( inconsistencyListDelete!=null && !inconsistencyListDelete.isEmpty() ){
				this.deleteInconsistencies(importLog, inconsistencyListDelete);
			}
			
			//Se agregan elementos inconsistente
			if ( addElementInconsistencyList!=null && !addElementInconsistencyList.isEmpty() ){
				this.addItemToInconsistencies(importLog, addElementInconsistencyList);
			}
			
			//Se borran elementos inconsistentes
			if ( delElementInconsistencyList!=null && !delElementInconsistencyList.isEmpty() ){
				this.delItemToInconsistencies(importLog, delElementInconsistencyList);
			}

			//Se envia mail a los usuario del dealer de tipo operador logistico
			if (
					( listaCreate!=null && !listaCreate.isEmpty() ) 
					|| ( inconsistencyListUpdate!=null && !inconsistencyListUpdate.isEmpty() )
					|| ( inconsistencyListClose!=null && !inconsistencyListClose.isEmpty() )
					|| ( inconsistencyListDelete!=null && !inconsistencyListDelete.isEmpty() )
					|| ( addElementInconsistencyList!=null && !addElementInconsistencyList.isEmpty() )
					|| ( delElementInconsistencyList!=null && !delElementInconsistencyList.isEmpty() )
					|| ( listaUpdate!=null && !listaUpdate.isEmpty() )
				){

				Dealer logisticOperator = daoDealer.getDealerByID( importLog.getDealer().getId() );
				
				List<User> users = new ArrayList<User>();
				if ( inconsistencyListClose!=null && !inconsistencyListClose.isEmpty() ){
					//Cuando es por cierre de inconsistencia se buscan los tipo Operador Logístico del Dealer
					users = daoUser.getUsersByDealerIdAndRoleTypeCode( logisticOperator.getId(), CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_OPERATOR.getCodeEntity() );
				}else{
					//Cuando es por creación de inconsistencia se buscan los usuarios tipo rol Analista de Logística del dealer
					users = daoUser.getUsersByDealerIdAndRoleTypeCode( logisticOperator.getId(), CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_ANALYST.getCodeEntity() );
				}

				if (users.size() > 0){
					for (User userDetail: users){
						log.debug("== user ==" + user.getEmail());
					}
					// Invocar INV_08 - Se envía correo notificando la actualización de inconsistencias
					sendMailToNotifyInconsistenciesUpdate(
							listaCreate, 
							inconsistencyListUpdate, 
							inconsistencyListClose, 
							inconsistencyListDelete,
							addElementInconsistencyList, 
							delElementInconsistencyList, 
							listaUpdate, 
							importLog, 
							users);
				}
			}
		} catch (Throwable ex) {
			log.error("== Error saveImportLogInconsistencies/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina saveImportLogInconsistencies/ImportLogBusinessBean ==");
		}			

	}
	
	/**
	 * 
	 * @param importLog
	 * @param inconsistencyList
	 * @throws BusinessException
	 */
	private void updateElementInconsistencies(ImportLogVO importLog, List<ImportLogInconsistencyVO> inconsistencyList) throws BusinessException {
		try{
			int i = 0;
			Long userId = null;
			for (ImportLogInconsistencyVO importLogInconsistencyVO : inconsistencyList) {
				
				//SerializedVO serializedVO = (SerializedVO) importLogInconsistencyVO.getImpLogItem().getSerializedVO();
				Serialized serialized = null;
				if ( importLogInconsistencyVO.getImpLogItem().getSerializedVO() != null ){
					serialized = daoSerialized.getSerializedByID( importLogInconsistencyVO.getImpLogItem().getSerializedVO().getElement().getId() );
				}
				ItemStatus itemStatus = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_INCONSISTENCY_PROCESS.getCodeEntity() );
				if ( serialized != null ){
					if ( serialized.getSerialized() != null ){
						//Se cambia el estado del import vinculado
						ImportLogItem importLogItemVinculado = daoImportLogItem.getImportLogItemByElementId( serialized.getSerialized().getElement().getId() );
						importLogItemVinculado.setOldItemStatus( importLogItemVinculado.getItemStatus() );
						importLogItemVinculado.setItemStatus( itemStatus );
						daoImportLogItem.updateImportLogItem(importLogItemVinculado);
					}
				}else{
					if ( importLogInconsistencyVO.getImpLogItem().getNotSerializedVO() != null ){
						NotSerialized notSerialized = daoNotSerialized.getNotSerializedbyElementTypeID( importLogInconsistencyVO.getImpLogItem().getNotSerializedVO().getElement().getElementType().getId(), importLog.getCountry().getId() );
						NotSerializedVO notSerializedVO = UtilsBusiness.copyObject(NotSerializedVO.class, notSerialized);
						notSerializedVO.setMovedQuantity( notSerialized.getInitialQuantity() );
						
						Double nuevaEsperada = 0D;
						if ( importLogInconsistencyVO.getExpectedAmount() != null ){
							nuevaEsperada = importLogInconsistencyVO.getExpectedAmount().doubleValue();
						}
						
						if ( nuevaEsperada <= 0 ){
							throw new BusinessException(ErrorBusinessMessages.STOCK_IN446.getCode(),ErrorBusinessMessages.STOCK_IN446.getMessage());
						}
						
						Double confirmadas = 0D;
						List<ImpLogConfirmation> listaConfirmaciones = daoImportLogConfirmation.getImpLogConfirmationsByImpLogItemId( importLogInconsistencyVO.getImpLogItem().getId() );
						for (ImpLogConfirmation impLogConfirmation : listaConfirmaciones) {
							confirmadas += impLogConfirmation.getConfirmedQuantity();
						}
						
						if ( confirmadas > nuevaEsperada ){
							Object[] params = new Object[2];
			    			params[0] = nuevaEsperada.toString();
							params[1] = confirmadas.toString();
							throw new BusinessException( ErrorBusinessMessages.STOCK_IN443.getCode(), ErrorBusinessMessages.STOCK_IN443.getMessage(params), UtilsBusiness.getParametersWS(params) );
						}
					}
				}
				//Se cambia el estado del import
				ImportLogItem importLogItem = daoImportLogItem.getImportLogItemByID(importLogInconsistencyVO.getImpLogItem().getId());// (ImportLogItem) UtilsBusiness.copyObject(ImportLogItem.class, importLogInconsistencyVO.getImpLogItem() );
				importLogItem.setOldItemStatus( importLogItem.getItemStatus() );
				importLogItem.setItemStatus( itemStatus );
				daoImportLogItem.updateImportLogItem(importLogItem);
				
				//Se crea la inconsistencia
				ImportLogInconsistency importLogInconsistency = new ImportLogInconsistency();
				importLogInconsistency.setImportLog( importLog.getId() );
				importLogInconsistency.setInconsistencyStatus( daoInconsistency.getInconsistencyStatusByCode( CodesBusinessEntityEnum.INCONSISTENCY_STATUS_ACTIVE.getCodeEntity() ) );
				importLogInconsistency.setInconsistencyType( daoInconsistencyType.getInconsistencyTypeByID( importLogInconsistencyVO.getInconsistencyType().getId() ) );
				importLogInconsistency.setInconsistencyDate( importLogInconsistencyVO.getInconsistencyDate() );
				importLogInconsistency.setComments( importLogInconsistencyVO.getComments() );
				importLogInconsistency.setUser( importLogInconsistencyVO.getUser() );
				importLogInconsistency.setAnswer( importLogInconsistencyVO.getAnswer() );
				importLogInconsistency.setImportLogItem( importLogItem.getId() );
				importLogInconsistency.setExpectedAmount( importLogInconsistencyVO.getExpectedAmount() );
				importLogInconsistency.setAdicion( CodesBusinessEntityEnum.INCONSISTENCY_UPD.getCodeEntity() );
				userId = importLogInconsistencyVO.getUser();
				daoImportLogInconsistency.createImportLogInconsistency(importLogInconsistency);
				
				inconsistencyList.set(i, UtilsBusiness.copyObject(ImportLogInconsistencyVO.class, importLogInconsistency) );
				inconsistencyList.get(i).setImpLogItem( UtilsBusiness.copyObject(ImportLogItemVO.class, importLogItem) );
				i++;
			}
			
			//Se calcula el estado del import log
			verificaEstadoImportLog( UtilsBusiness.copyObject(ImportLog.class, importLog), userId );
		} catch (Throwable ex) {
			log.error("== Error updateNoSerialized/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina updateNoSerialized/ImportLogBusinessBean ==");
		}
	}
	

	

	/**
	 * Cierra una inconsistencia
	 * @param importLog
	 * @param inconsistencyList
	 * @throws BusinessException
	 */
	private void closeInconsistencies(ImportLogVO importLog, List<ImportLogInconsistencyVO> inconsistencyList, User user) throws BusinessException {
		log.debug("== Inicia closeInconsistencies/ImportLogBusinessBean ==");
		try{
			List<SerializedVO> serializedListA = new ArrayList<SerializedVO>();
			List<NotSerializedVO> notSerializedListA = new ArrayList<NotSerializedVO>();
			
			int i = 0;
			Long userId = null;
			
			for (ImportLogInconsistencyVO importLogInconsistencyVO : inconsistencyList) {
				//Para evitar null pointer se carga antes los datos del ImportLogInconsistencyVO de la base
				ImportLogInconsistencyVO importLogInconsistencyVOtmp = UtilsBusiness.copyObject(ImportLogInconsistencyVO.class, daoImportLogInconsistency.getImportLogInconsistencyByID( importLogInconsistencyVO.getId() ) );
				importLogInconsistencyVOtmp.setImpLogItem( UtilsBusiness.copyObject(ImportLogItemVO.class, daoImportLogItem.getImportLogItemByID( importLogInconsistencyVOtmp.getImportLogItem() ) ) );
				importLogInconsistencyVOtmp.getImpLogItem().setSerializedVO(null);
				if ( importLogInconsistencyVO.getImpLogItem().getSerializedVO() != null ){
					importLogInconsistencyVOtmp.getImpLogItem().setSerializedVO( importLogInconsistencyVO.getImpLogItem().getSerializedVO() );
				}
				importLogInconsistencyVOtmp.getImpLogItem().setNotSerializedVO(null);
				if ( importLogInconsistencyVO.getImpLogItem().getNotSerializedVO() != null ){
					importLogInconsistencyVOtmp.getImpLogItem().setNotSerializedVO( importLogInconsistencyVO.getImpLogItem().getNotSerializedVO() );
				}
				importLogInconsistencyVOtmp.getImpLogItem().setElementVO( importLogInconsistencyVO.getImpLogItem().getElementVO() );
				importLogInconsistencyVOtmp.setInconsistencyType( importLogInconsistencyVO.getInconsistencyType() );
				importLogInconsistencyVOtmp.setComments( importLogInconsistencyVO.getComments() );
				importLogInconsistencyVOtmp.setAnswer( importLogInconsistencyVO.getAnswer() );
				importLogInconsistencyVOtmp.setUserClose( importLogInconsistencyVO.getUserClose() );
				importLogInconsistencyVO = importLogInconsistencyVOtmp;
				
				ItemStatus itemStatusNS = null;
				if ( importLogInconsistencyVO.getAdicion() != null ){

					Serialized serialized = null;
					if ( importLogInconsistencyVO.getImpLogItem().getSerializedVO() != null ){
						serialized = daoSerialized.getSerializedByID( importLogInconsistencyVO.getImpLogItem().getSerializedVO().getElement().getId() );
					}
					ImportLogItem importLogItemVinculado = null;
					if ( serialized != null ){
						if ( serialized.getSerialized() != null ){
							importLogItemVinculado = daoImportLogItem.getImportLogItemByElementId( serialized.getSerialized().getElement().getId() );
						}
						SerializedVO serializedVO = UtilsBusiness.copyObject(SerializedVO.class, serialized);
						if ( importLogInconsistencyVO.getAdicion().equals( CodesBusinessEntityEnum.INCONSISTENCY_ADD.getCodeEntity() ) ){
							serializedListA.add(serializedVO);
						}else{
							if (importLogInconsistencyVO.getAdicion().equals(CodesBusinessEntityEnum.INCONSISTENCY_DEL.getCodeEntity())){
								Warehouse warehouse = daoWarehouse.getWarehouseTypeByDealerId(importLog.getDealer().getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity());
								
								//Realiza el movimiento de salida del elemento
								MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(null, CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_DELETE_IMPORT_LOG.getCodeEntity()); 
								MovementElementDTO dto = new MovementElementDTO(user, 
										UtilsBusiness.copyObject(WarehouseVO.class, warehouse), 
										serialized,
										importLog.getId(), 
										CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity(),
										dtoGenerics.getMovTypeCodeS(),
										dtoGenerics.getRecordStatusU(),
										dtoGenerics.getRecordStatusH(), 
										true, 
										CodesBusinessEntityEnum.PROCESS_CODE_IBS_IMPORT_LOG.getCodeEntity(),
										dtoGenerics.getMovConfigStatusPending(),
										dtoGenerics.getMovConfigStatusNoConfig());
								businessMovementElement.deleteSerializedElementInWarehouse(dto);
							}
						}
					}else{
						if ( importLogInconsistencyVO.getImpLogItem().getNotSerializedVO() != null ){
							NotSerialized notSerialized = daoNotSerialized.getNotSerializedbyElementTypeID( importLogInconsistencyVO.getImpLogItem().getNotSerializedVO().getElement().getElementType().getId(), importLog.getCountry().getId() );
							NotSerializedVO notSerializedVO = UtilsBusiness.copyObject(NotSerializedVO.class, notSerialized);
							notSerializedVO.setMovedQuantity( notSerialized.getInitialQuantity() );
							
							ImportLogInconsistency importLogInconsistencyPojo = (ImportLogInconsistency) daoImportLogInconsistency.getImportLogInconsistencyByID( importLogInconsistencyVO.getId() );
							
							Double quantity = 0D;
							if ( importLogInconsistencyPojo.getExpectedAmount() != null ){
								quantity = importLogInconsistencyPojo.getExpectedAmount().doubleValue();
							}
							notSerializedVO.setActualQuantity(quantity);
							Double amountExpectedItem = 0D;
							if ( importLogInconsistencyVO.getImpLogItem().getAmountExpected() != null ){
								amountExpectedItem = importLogInconsistencyVO.getImpLogItem().getAmountExpected();
							}
							Double confirmadas = 0D;
							List<ImpLogConfirmation> listaConfirmaciones = daoImportLogConfirmation.getImpLogConfirmationsByImpLogItemId( importLogInconsistencyVO.getImpLogItem().getId() );
							for (ImpLogConfirmation impLogConfirmation : listaConfirmaciones) {
								confirmadas += impLogConfirmation.getConfirmedQuantity();
							}
							
							Double esperada = amountExpectedItem - confirmadas;
							if ( esperada<quantity && importLogInconsistencyVO.getInconsistencyType().getIncTypeCode().equals( CodesBusinessEntityEnum.INCONSISTENCY_TYPE_IMPORT_LOG_INCONSISTENCY_MINOR_ITEMS.getCodeEntity() ) ){
								Object[] params = new Object[2];
				    			params[0] = quantity.toString();
								params[1] = ((Double) (amountExpectedItem - confirmadas) ).toString();
								throw new BusinessException( ErrorBusinessMessages.STOCK_IN441.getCode(), ErrorBusinessMessages.STOCK_IN441.getMessage(params), UtilsBusiness.getParametersWS(params) );
							}
							
							if ( importLogInconsistencyVO.getAdicion().equals( CodesBusinessEntityEnum.INCONSISTENCY_ADD.getCodeEntity() ) ){
								notSerializedListA.add( notSerializedVO );
							}else{
								if ( importLogInconsistencyVO.getAdicion().equals( CodesBusinessEntityEnum.INCONSISTENCY_DEL.getCodeEntity() ) ){
									Warehouse warehouse = daoWarehouse.getWarehouseTypeByDealerId(importLog.getDealer().getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity());
									
									MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(null, CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_DEL.getCodeEntity());
									MovementElementDTO dto = new MovementElementDTO(user, 
											UtilsBusiness.copyObject(WarehouseVO.class, warehouse), 
											null,
											importLogInconsistencyVO.getImpLogItem().getNotSerializedVO().getElement().getElementType().getId(), 
											null, 
											importLog.getId(), 
											CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity(), 
											dtoGenerics.getMovTypeCodeS(), 
											dtoGenerics.getRecordStatusU(), 
											dtoGenerics.getRecordStatusH(), 
											amountExpectedItem,
											dtoGenerics.getMovConfigStatusPending(),
											dtoGenerics.getMovConfigStatusNoConfig());
									businessMovementElement.deleteNotSerializedElementInWarehouse(dto);
								
									//warehouseElementBusiness.updateQuantityForNotSerializedElementInWarehouse(null, warehouse, amountExpectedItem, null, importLogInconsistencyVO.getImpLogItem().getNotSerializedVO().getElement().getElementType().getId(), CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_ADD.getCodeEntity(), CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_DEL.getCodeEntity(), importLog.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity());
								}else{
									if ( importLogInconsistencyVO.getAdicion().equals( CodesBusinessEntityEnum.INCONSISTENCY_UPD.getCodeEntity() ) ){
										importLogInconsistencyVO.getImpLogItem().setNotSerializedVO(notSerializedVO);
										
										warehouseElementBusiness.moveNotSerializedElementBetweenWareHousesInconsistencies(importLog, importLogInconsistencyVO, CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity() );
									}
								}
							}
							
							
							Double real = quantity - confirmadas;
							//Estado del elemento
							if ( real  == 0 ){
								itemStatusNS = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity() );
							}else{
								if ( real>0 && confirmadas>0 ){//confirmadas>0 && confirmadas<amountExpectedItem
									itemStatusNS = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() );
								}else{
									if ( real>0 && confirmadas==0 ){
										itemStatusNS = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity() );
									}
								}
							}
						}
					}					

					//Se cambia el estado del import
					ImportLogItem importLogItem = (ImportLogItem) daoImportLogItem.getImportLogItemByID( importLogInconsistencyVO.getImpLogItem().getId() );
					//ImportLogItem importLogItem = (ImportLogItem) UtilsBusiness.copyObject(ImportLogItem.class, importLogInconsistencyVO.getImpLogItem() );
					if ( importLogInconsistencyVO.getAdicion().equals( CodesBusinessEntityEnum.INCONSISTENCY_ADD.getCodeEntity() ) ){
						ItemStatus itemStatus = itemStatusNS;
						if ( itemStatusNS == null ){
							itemStatus = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity() );
						}
						//Se actualiza el import log item vinculado
						if ( importLogItemVinculado != null){
							importLogItemVinculado.setOldItemStatus( importLogItemVinculado.getItemStatus() );
							importLogItemVinculado.setItemStatus( itemStatus );
							daoImportLogItem.updateImportLogItem(importLogItemVinculado);
						}
						
						//Se actualiza el import log  item
						importLogItem.setItemStatus( itemStatus );
						daoImportLogItem.updateImportLogItem(importLogItem);
						
						//Se modifica la inconsistencia
						ImportLogInconsistency importLogInconsistency = (ImportLogInconsistency) daoImportLogInconsistency.getImportLogInconsistencyByID( importLogInconsistencyVO.getId() );
						importLogInconsistency.setInconsistencyStatus( daoInconsistency.getInconsistencyStatusByCode( CodesBusinessEntityEnum.INCONSISTENCY_STATUS_CLOSE.getCodeEntity() ) );
						importLogInconsistency.setInconsistencyType( daoInconsistencyType.getInconsistencyTypeByID( importLogInconsistencyVO.getInconsistencyType().getId() ) );
						importLogInconsistency.setComments( importLogInconsistencyVO.getComments() );
						importLogInconsistency.setAnswer( importLogInconsistencyVO.getAnswer() );
						importLogInconsistency.setUserClose( importLogInconsistencyVO.getUserClose() );
						importLogInconsistency.setAnswerDate( new Date() );
						userId = importLogInconsistencyVO.getUserClose();
						daoImportLogInconsistency.updateImportLogInconsistency(importLogInconsistency);
						
						inconsistencyList.set(i, UtilsBusiness.copyObject(ImportLogInconsistencyVO.class, importLogInconsistency) );
						inconsistencyList.get(i).setImpLogItem( UtilsBusiness.copyObject(ImportLogItemVO.class, importLogItem) );
					}else{
						if ( importLogInconsistencyVO.getAdicion().equals( CodesBusinessEntityEnum.INCONSISTENCY_DEL.getCodeEntity() ) ){
							//Se elimina el import log item vinculado
							if ( importLogItemVinculado != null){
								daoImportLogItem.deleteImportLogItem(importLogItemVinculado);
							}
							//Se elimina el import item
							daoImportLogItem.deleteImportLogItem(importLogItem);
							
							//Se modifica la inconsistencia
							List<ImportLogInconsistency> listaActualizar = daoImportLogInconsistency.getImportLogInconsistenciesByImportLogItemId( importLogItem.getId() );
							for (ImportLogInconsistency importLogInconsistency : listaActualizar) {
								//Se modifica la inconsistencia
								importLogInconsistency.setInconsistencyStatus( daoInconsistency.getInconsistencyStatusByCode( CodesBusinessEntityEnum.INCONSISTENCY_STATUS_CLOSE.getCodeEntity() ) );
								importLogInconsistency.setInconsistencyType( daoInconsistencyType.getInconsistencyTypeByID( importLogInconsistencyVO.getInconsistencyType().getId() ) );
								importLogInconsistency.setComments( importLogInconsistencyVO.getComments() );
								importLogInconsistency.setAnswer( importLogInconsistencyVO.getAnswer() );
								importLogInconsistency.setUserClose( importLogInconsistencyVO.getUserClose() );
								importLogInconsistency.setAnswerDate( new Date() );
								importLogInconsistency.setImportLogItem(null);
								userId = importLogInconsistencyVO.getUserClose();
								daoImportLogInconsistency.updateImportLogInconsistency(importLogInconsistency);
								
								inconsistencyList.set(i, UtilsBusiness.copyObject(ImportLogInconsistencyVO.class, importLogInconsistency) );
								inconsistencyList.get(i).setImpLogItem( UtilsBusiness.copyObject(ImportLogItemVO.class, importLogItem) );
							}
						}else{
							if ( importLogInconsistencyVO.getAdicion().equals( CodesBusinessEntityEnum.INCONSISTENCY_UPD.getCodeEntity() ) ){
								ItemStatus itemStatus = itemStatusNS;
								if ( itemStatusNS == null ){
									itemStatus = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity() );
								}
								//Se actualiza el import log item vinculado
								if ( importLogItemVinculado != null){
									importLogItemVinculado.setItemStatus( itemStatus );
									importLogItemVinculado.setOldItemStatus(null);
									daoImportLogItem.updateImportLogItem(importLogItemVinculado);
								}
								
								if ( importLogInconsistencyVO.getExpectedAmount() != null ){
									importLogItem.setAmountExpected( importLogInconsistencyVO.getExpectedAmount().doubleValue() );
								}
								
								//Se actualiza el import log  item
								importLogItem.setItemStatus( itemStatus );
								importLogItem.setOldItemStatus(null);
								
								daoImportLogItem.updateImportLogItem(importLogItem);
								
								//Se modifica la inconsistencia
								ImportLogInconsistency importLogInconsistency = (ImportLogInconsistency) daoImportLogInconsistency.getImportLogInconsistencyByID( importLogInconsistencyVO.getId() );
								importLogInconsistency.setInconsistencyStatus( daoInconsistency.getInconsistencyStatusByCode( CodesBusinessEntityEnum.INCONSISTENCY_STATUS_CLOSE.getCodeEntity() ) );
								importLogInconsistency.setInconsistencyType( daoInconsistencyType.getInconsistencyTypeByID( importLogInconsistencyVO.getInconsistencyType().getId() ) );
								importLogInconsistency.setComments( importLogInconsistencyVO.getComments() );
								importLogInconsistency.setAnswer( importLogInconsistencyVO.getAnswer() );
								importLogInconsistency.setUserClose( importLogInconsistencyVO.getUserClose() );
								importLogInconsistency.setAnswerDate( new Date() );
								userId = importLogInconsistencyVO.getUserClose();
								daoImportLogInconsistency.updateImportLogInconsistency(importLogInconsistency);
								
								inconsistencyList.set(i, UtilsBusiness.copyObject(ImportLogInconsistencyVO.class, importLogInconsistency) );
								inconsistencyList.get(i).setImpLogItem( UtilsBusiness.copyObject(ImportLogItemVO.class, importLogItem) );
							}
						}
					}
				}
				i++;
			}
			
			/******************************************************ADD*********************************************************/
			
			if(!serializedListA.isEmpty() || !notSerializedListA.isEmpty()){
				Warehouse warehouseTarget  = this.warehouseElementBusiness.getWareHouseOrCreateIfNotExists(importLog.getDealer().getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity());
				//Insertar en la Bodega de Transito los elementos Serializados
				if (!serializedListA.isEmpty()){
					ItemStatus itemStatus = this.daoItemStatus.getItemStatusSended();
					this.addElementToWarehouseForImportLog(serializedListA, UtilsBusiness.copyObject(WarehouseVO.class, warehouseTarget), importLog.getId(), user, itemStatus);
					//ElementMovementDTO dto = new ElementMovementDTO(CodesBusinessEntityEnum.PROCESS_CODE_IBS_IMPORT_LOG.getCodeEntity(), null, targetWhId, serializedListA, true, importLog.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity(), CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_ADD.getCodeEntity(), null,itemStatus);
					//warehouseElementBusiness.moveSerializedElementsToWareHouse(dto);
				}
				//Insertar en la Bodega de Transito los elementos no serializados
				if ( !notSerializedListA.isEmpty() ){
					moveNotSerializedElementsToWareHouse(importLog.getId(),
														 warehouseTarget,
														 notSerializedListA,
														 user);
				}
			}
			
			//Se calcula el estado del import log
			verificaEstadoImportLog( UtilsBusiness.copyObject(ImportLog.class, importLog), userId );
			
		} catch (Throwable ex) {
			log.error("== Error closeInconsistencies/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina closeInconsistencies/ImportLogBusinessBean ==");
		}
	}
	
	private void moveNotSerializedElementsToWareHouse(Long importLogId,
													  Warehouse warehouseTarget,
			                                          List<NotSerializedVO> notSerializedListA,
			                                          User user) throws BusinessException {
			log.debug("== Inicia moveNotSerializedElementsToWareHouse/WarehouseElementBusinessBean ==");
			UtilsBusiness.assertNotNull(importLogId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(warehouseTarget, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(notSerializedListA, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			try {
				
				MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_ADD.getCodeEntity(),
																										 null);
				for(NotSerializedVO e : notSerializedListA){
					UtilsBusiness.assertNotNull(e, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
					MovementElementDTO dtoMovement = new MovementElementDTO(user,
							null, 
							UtilsBusiness.copyObject(WarehouseVO.class,  warehouseTarget), 
							null, 
							e.getElement().getElementType().getId(), 
							e.getElement().getElementType().getTypeElementCode(),
							importLogId, 
							CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity(), 
							dtoGenerics.getMovTypeCodeE(), 
							dtoGenerics.getMovTypeCodeS(), 
							dtoGenerics.getRecordStatusU(),
							dtoGenerics.getRecordStatusH(),
							e.getActualQuantity(),
							dtoGenerics.getMovConfigStatusPending(),
							dtoGenerics.getMovConfigStatusNoConfig());
					businessMovementElement.moveNotSerializedElementToWarehouse(dtoMovement);
				}
			} catch(Throwable ex){
				log.error("== Error al tratar de ejecutar la operación moveNotSerializedElementsToWareHouse/WarehouseElementBusinessBean ==");
				throw this.manageException(ex);
			} finally{
				log.debug("== Termina moveNotSerializedElementsToWareHouse/WarehouseElementBusinessBean ==");
			}
		}
	
	/**
	 * 
	 * Metodo: Metodo encargado de almacenar los nuevos items en bodega agregados por inconsistencia
	 * al registro de importación
	 * @param listElementToAdd
	 * @param warehouseTarget
	 * @param importLogId
	 * @throws BusinessException
	 * @author waguilera
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void addElementToWarehouseForImportLog(List<SerializedVO> listElementToAdd, WarehouseVO warehouseTarget, Long importLogId, User user, ItemStatus itemStatus)throws BusinessException{
		log.debug("== Inicia addElementToWarehouseForImportLog/ImportLogBusinessBean ==");
		try{
			MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_ADD.getCodeEntity(), null);
			for(SerializedVO element: listElementToAdd){
				MovementElementDTO dto = new MovementElementDTO(user, 
						warehouseTarget, 
						element.getElementId(), 
						importLogId, 
						CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity(), 
						dtoGenerics.getMovTypeCodeE(), 
						dtoGenerics.getRecordStatusU(), 
						dtoGenerics.getRecordStatusH(),  
						true, 
						CodesBusinessEntityEnum.PROCESS_CODE_IBS_IMPORT_LOG.getCodeEntity(),
						dtoGenerics.getMovConfigStatusPending(),
						dtoGenerics.getMovConfigStatusNoConfig());
				businessMovementElement.moveSerializedElementToWarehouse(dto);
			}
		} catch (Throwable ex) {
			log.error("== Error addElementToWarehouseForImportLog/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina addElementToWarehouseForImportLog/ImportLogBusinessBean ==");
		}
	}
	
	
	
	/**
	 * Borra una inconsistencia
	 * @param importLog
	 * @param inconsistencyList
	 * @throws BusinessException
	 */
	private void deleteInconsistencies(ImportLogVO importLog, List<ImportLogInconsistencyVO> inconsistencyList) throws BusinessException {
		log.debug("== Inicia closeInconsistencies/ImportLogBusinessBean ==");
		try{
			List<SerializedVO> serializedListA = new ArrayList<SerializedVO>();
			List<NotSerializedVO> notSerializedListA = new ArrayList<NotSerializedVO>();
			
			List<SerializedVO> serializedListE = new ArrayList<SerializedVO>();
			List<NotSerializedVO> notSerializedListE = new ArrayList<NotSerializedVO>();
			
			List<SerializedVO> serializedListU = new ArrayList<SerializedVO>();
			List<NotSerializedVO> notSerializedListU = new ArrayList<NotSerializedVO>();
			
			for (ImportLogInconsistencyVO importLogInconsistencyVO : inconsistencyList) {
				ImportLogInconsistency importLogInconsistencyTmp = daoImportLogInconsistency.getImportLogInconsistencyByID( importLogInconsistencyVO.getId() );
				if( importLogInconsistencyTmp.getAdicion() != null ){
					importLogInconsistencyVO.setAdicion( importLogInconsistencyTmp.getAdicion() );
				}
				if ( importLogInconsistencyVO.getAdicion() != null ){
					if ( importLogInconsistencyVO.getImpLogItem().getSerializedVO() != null ){
						if ( importLogInconsistencyVO.getAdicion().equals( CodesBusinessEntityEnum.INCONSISTENCY_ADD.getCodeEntity() ) ){
							serializedListA.add( (SerializedVO) importLogInconsistencyVO.getImpLogItem().getSerializedVO() );
						}else{
							if ( importLogInconsistencyVO.getAdicion().equals( CodesBusinessEntityEnum.INCONSISTENCY_DEL.getCodeEntity() ) ){
								serializedListE.add( (SerializedVO) importLogInconsistencyVO.getImpLogItem().getSerializedVO() );
							}else{
								if ( importLogInconsistencyVO.getAdicion().equals( CodesBusinessEntityEnum.INCONSISTENCY_UPD.getCodeEntity() ) ){
									serializedListU.add( (SerializedVO) importLogInconsistencyVO.getImpLogItem().getSerializedVO() );
								}
							}
						}
					}else{
						if ( importLogInconsistencyVO.getImpLogItem().getNotSerializedVO() != null ){
							if ( importLogInconsistencyVO.getAdicion().equals( CodesBusinessEntityEnum.INCONSISTENCY_ADD.getCodeEntity() ) ){
								notSerializedListA.add( (NotSerializedVO) importLogInconsistencyVO.getImpLogItem().getNotSerializedVO() );
							}else{
								if ( importLogInconsistencyVO.getAdicion().equals( CodesBusinessEntityEnum.INCONSISTENCY_DEL.getCodeEntity() ) ){
									notSerializedListE.add( (NotSerializedVO) importLogInconsistencyVO.getImpLogItem().getNotSerializedVO() );
								}else{
									if ( importLogInconsistencyVO.getAdicion().equals( CodesBusinessEntityEnum.INCONSISTENCY_UPD.getCodeEntity() ) ){
										notSerializedListU.add( (NotSerializedVO) importLogInconsistencyVO.getImpLogItem().getNotSerializedVO() );
									}
								}
							}
						}
					}
				}
			}
			
			Long userId = null;
			int i = 0;
			for (ImportLogInconsistencyVO importLogInconsistencyVO : inconsistencyList) {
				if ( importLogInconsistencyVO.getAdicion() != null ){
					
					//SerializedVO serializedVO = (SerializedVO) importLogInconsistencyVO.getImpLogItem().getSerializedVO();
					Serialized serialized = null;
					if ( importLogInconsistencyVO.getImpLogItem().getSerializedVO() != null ){
						serialized = daoSerialized.getSerializedByID( importLogInconsistencyVO.getImpLogItem().getSerializedVO().getElementId() );
					}

					ImportLogItem importLogItemVinculado = null;
					if ( serialized != null ){
						if ( serialized.getSerialized() != null ){
							importLogItemVinculado = daoImportLogItem.getImportLogItemByElementId( serialized.getSerialized().getElement().getId() );
						}
					}
					
					if ( serializedListE.size()>0 || notSerializedListE.size()>0 ){
						if ( serializedListE.contains( (SerializedVO) importLogInconsistencyVO.getImpLogItem().getSerializedVO() ) 
							|| notSerializedListE.contains( (NotSerializedVO) importLogInconsistencyVO.getImpLogItem().getNotSerializedVO() ) ){
							
							//se actualiza el import log item vinculado
							if ( importLogItemVinculado != null ){
								importLogItemVinculado.setItemStatus( importLogItemVinculado.getOldItemStatus() );
								importLogItemVinculado.setOldItemStatus( null );
								daoImportLogItem.updateImportLogItem(importLogItemVinculado);
							}
							
							//Se actualiza el import item
							ImportLogItem importLogItem = daoImportLogItem.getImportLogItemByID(importLogInconsistencyVO.getImpLogItem().getId()); //(ImportLogItem) UtilsBusiness.copyObject(ImportLogItem.class, importLogInconsistencyVO.getImpLogItem() );
							importLogItem.setItemStatus( importLogItem.getOldItemStatus() );
							importLogItem.setOldItemStatus( null );
							daoImportLogItem.updateImportLogItem(importLogItem);
							inconsistencyList.get(i).setImpLogItem( UtilsBusiness.copyObject(ImportLogItemVO.class, importLogItem)   );
						}
					}
					
					if ( serializedListA.size()>0 || notSerializedListA.size()>0 ){
						if ( serializedListA.contains( (SerializedVO) importLogInconsistencyVO.getImpLogItem().getSerializedVO() ) 
							|| notSerializedListA.contains( (NotSerializedVO) importLogInconsistencyVO.getImpLogItem().getNotSerializedVO() ) ){
							//se elimina el import log item vinculado
							if ( importLogItemVinculado != null ){
								daoImportLogItem.deleteImportLogItem(importLogItemVinculado);
							}
							ImportLogItem importLogItem = daoImportLogItem.getImportLogItemByID(importLogInconsistencyVO.getImpLogItem().getId()); //(ImportLogItem) UtilsBusiness.copyObject(ImportLogItem.class, importLogInconsistencyVO.getImpLogItem() );
							daoImportLogItem.deleteImportLogItem(importLogItem);
						}
					}
					
					if ( serializedListU.size()>0 || notSerializedListU.size()>0 ){
						if ( serializedListU.contains( (SerializedVO) importLogInconsistencyVO.getImpLogItem().getSerializedVO() ) 
							 || notSerializedListU.contains( (NotSerializedVO) importLogInconsistencyVO.getImpLogItem().getNotSerializedVO() ) ){
							//En teoria esto es solo para NotSerializedVO por lo cual no hay vinculado
							
							//Se actualiza el import item
							ImportLogItem importLogItem = daoImportLogItem.getImportLogItemByID(importLogInconsistencyVO.getImpLogItem().getId()); //(ImportLogItem) UtilsBusiness.copyObject(ImportLogItem.class, importLogInconsistencyVO.getImpLogItem() );
							importLogItem.setItemStatus( importLogItem.getOldItemStatus() );
							importLogItem.setOldItemStatus( null );
							daoImportLogItem.updateImportLogItem(importLogItem);
							
							inconsistencyList.get(i).setImpLogItem( UtilsBusiness.copyObject(ImportLogItemVO.class, importLogItem)   );
						}
					}
					
					userId = importLogInconsistencyVO.getUser()!=null ? importLogInconsistencyVO.getUser() : null;
					//Se elimina la inconsistencia
					daoImportLogInconsistency.deleteImportLogInconsistency( UtilsBusiness.copyObject(ImportLogInconsistency.class, importLogInconsistencyVO) );
					
					i++;
				}
			}
			
			//Se calcula el estado del import log
			verificaEstadoImportLog( UtilsBusiness.copyObject(ImportLog.class, importLog), userId );
			
		} catch (Throwable ex) {
			log.error("== Error closeInconsistencies/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina closeInconsistencies/ImportLogBusinessBean ==");
		}
	}
	
	

	
	/**
	 * Agrega un elemento de caracter inconsistente
	 * @param importLog
	 * @param inconsistencyList
	 * @throws BusinessException
	 * @author aquevedo
	 */
	private void addItemToInconsistencies(ImportLogVO importLog, List<ImportLogInconsistencyVO> inconsistencyList) throws BusinessException {
		log.debug("== Inicia addItemToInconsistencies/ImportLogBusinessBean ==");
		try{
			ItemStatus itemStatus = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_INCONSISTENCY_PROCESS.getCodeEntity());
			int i = 0;
			Long userId = null;
			ElementVO elementVOInter = null;
			SerializedVO serializedVOInter = null;
			ElementVO elementVOLinked = null;
			for (ImportLogInconsistencyVO importLogInconsistencyVO : inconsistencyList) {
				ElementVO elementVO = importLogInconsistencyVO.getImpLogItem().getElementVO();
				ElementStatus elementStatus = this.daoElementStatus.getElementStatusByCode(CodesBusinessEntityEnum.ELEMENT_STATUS_T01.getCodeEntity());
				elementVO.setElementStatus(elementStatus);
				elementVO.setCountry(importLog.getCountry());
				
				SerializedVO serializedVO = (SerializedVO) importLogInconsistencyVO.getImpLogItem().getSerializedVO();
				if (serializedVO != null){
					
					//Crear al elemento vunculado si se recibe como parametro
					SerializedVO serializedLinked = UtilsBusiness.copyObject(SerializedVO.class, serializedVO.getSerialized());
					if(serializedVO.getSerialized()!=null&&serializedVO.getSerialCode()!=null){
						elementVOLinked = UtilsBusiness.copyObject(ElementVO.class, serializedLinked.getElement());
						elementVOLinked.setElementStatus(elementStatus);
						elementVOLinked.setCountry(importLog.getCountry());
					}
					
					if(serializedVO.getSerialized()!=null && elementVO.getElementType().getElementModel().getElementClass().getElementClassCode().equals(CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity())){
						elementVOInter = elementVO;
						serializedVOInter = serializedVO;
						elementVO = elementVOLinked;
						serializedVO = serializedLinked;
						elementVOLinked = elementVOInter;
						serializedLinked = serializedVOInter;
						serializedVO.setSerialized(serializedLinked);
						serializedLinked.setSerialized(null);
						elementVO.setIsSerialized(CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity());
					}
					
						
					if(serializedVO.getSerialized()!=null&&serializedVO.getSerialCode()!=null){
						this.elementBusinessBean.createSerializedElement(elementVOLinked, serializedLinked);
					}
					serializedVO.setSerialized(serializedLinked);
					this.elementBusinessBean.createSerializedElement(elementVO, serializedVO);
					
					importLogInconsistencyVO.getImpLogItem().setSerializedVO(serializedVO);
					
				}else{
					NotSerializedVO notSerializedVO = (NotSerializedVO) importLogInconsistencyVO.getImpLogItem().getNotSerializedVO();
					if (notSerializedVO != null){
						notSerializedVO = assignMovedQuantity(notSerializedVO);
						elementBusinessBean.createNotSerializedElement(elementVO, notSerializedVO, importLog.getId() );
					}
				}

				Double esperada = 0D;
				if (importLogInconsistencyVO.getImpLogItem().getAmountExpected() != null){
					esperada = importLogInconsistencyVO.getImpLogItem().getAmountExpected();
				}
		
				//Se crea el log item
				ImportLogItem importLogItem = new ImportLogItem();
				importLogItem.setMeasureUnit(UtilsBusiness.copyObject(MeasureUnit.class, elementVO.getElementType().getMeasureUnit()));
				importLogItem.setElement(UtilsBusiness.copyObject(Element.class, elementVO));
				importLogItem.setImportLog(importLog);
				importLogItem.setItemStatus(itemStatus);
				importLogItem.setAmountExpected(esperada);
				importLogItem.setOldItemStatus(null);
				daoImportLogItem.createImportLogItem(importLogItem);
				
				//Se crea la inconsistencia
				ImportLogInconsistency importLogInconsistency = new ImportLogInconsistency();
				importLogInconsistency.setImportLog(importLog.getId());
				importLogInconsistency.setInconsistencyStatus(daoInconsistency.getInconsistencyStatusByCode(CodesBusinessEntityEnum.INCONSISTENCY_STATUS_ACTIVE.getCodeEntity()));
				importLogInconsistency.setInconsistencyType(daoInconsistencyType.getInconsistencyTypeByID(importLogInconsistencyVO.getInconsistencyType().getId()));
				importLogInconsistency.setInconsistencyDate(importLogInconsistencyVO.getInconsistencyDate());
				importLogInconsistency.setComments(importLogInconsistencyVO.getComments());
				importLogInconsistency.setUser(importLogInconsistencyVO.getUser());
				importLogInconsistency.setAnswer(importLogInconsistencyVO.getAnswer());
				importLogInconsistency.setImportLogItem(importLogItem.getId());
				importLogInconsistency.setExpectedAmount(esperada.longValue());
				importLogInconsistency.setAdicion(CodesBusinessEntityEnum.INCONSISTENCY_ADD.getCodeEntity());
				userId = importLogInconsistencyVO.getUser();
				daoImportLogInconsistency.createImportLogInconsistency(importLogInconsistency);
				importLogInconsistencyVO.setInconsistencyStatus(importLogInconsistency.getInconsistencyStatus());
				
				inconsistencyList.set(i, UtilsBusiness.copyObject(ImportLogInconsistencyVO.class, importLogInconsistency));
				inconsistencyList.get(i).setImpLogItem(UtilsBusiness.copyObject(ImportLogItemVO.class, importLogItem));
				i++;
			}
			
			//Se calcula el estado del import log
			verificaEstadoImportLog(UtilsBusiness.copyObject(ImportLog.class, importLog), userId);
			
		} catch (Throwable ex) {
			log.error("== Error addItemToInconsistencies/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina addItemToInconsistencies/ImportLogBusinessBean ==");
		}
	}
	
	/**
	 * Metodo encargado de asignar la cantidad a mover
	 * @param notSerializedVO
	 * @return
	 */
	private NotSerializedVO assignMovedQuantity(NotSerializedVO notSerializedVO){
		notSerializedVO.setMovedQuantity(notSerializedVO.getInitialQuantity());
		return notSerializedVO;
	}
	
	/**
	 * Borra un elemento inconsistente
	 * @param importLog
	 * @param inconsistencyList
	 * @throws BusinessException
	 * @author aquevedo
	 */
	private void delItemToInconsistencies(ImportLogVO importLog, List<ImportLogInconsistencyVO> inconsistencyList) throws BusinessException {
		log.debug("== Inicia delItemToInconsistencies/ImportLogBusinessBean ==");
		try{

			int i = 0;
			Long userId = null;
			for (ImportLogInconsistencyVO importLogInconsistencyVO : inconsistencyList) {
				Serialized serialized = null;
				if ( importLogInconsistencyVO.getImpLogItem().getSerializedVO() != null ){
					serialized = daoSerialized.getSerializedByID( importLogInconsistencyVO.getImpLogItem().getSerializedVO().getElementId() );
				}
				ImportLogItem importLogItemVinculado = null;
				if ( serialized != null ){
					if ( serialized.getSerialized() != null ){
						//Se cambia el estado del import vinculado
						importLogItemVinculado = daoImportLogItem.getImportLogItemByElementId( serialized.getSerialized().getElement().getId() );
					}
				}
				
				ItemStatus itemStatus = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_INCONSISTENCY_PROCESS.getCodeEntity() );
				if ( importLogItemVinculado != null ){
					importLogItemVinculado.setOldItemStatus( importLogItemVinculado.getItemStatus() );
					importLogItemVinculado.setItemStatus( itemStatus );
					daoImportLogItem.updateImportLogItem(importLogItemVinculado);
				}
				//Se cambia el estado del elemento
				ImportLogItem importLogItem = daoImportLogItem.getImportLogItemByID(importLogInconsistencyVO.getImpLogItem().getId()); //(ImportLogItem) UtilsBusiness.copyObject(ImportLogItem.class, importLogInconsistencyVO.getImpLogItem() );
				importLogItem.setOldItemStatus( importLogItem.getItemStatus() );
				importLogItem.setItemStatus( itemStatus );
				daoImportLogItem.updateImportLogItem(importLogItem);
				
				//Se crea la inconsistencia
				ImportLogInconsistency importLogInconsistency = new ImportLogInconsistency();
				importLogInconsistency.setImportLog( importLog.getId() );
				importLogInconsistency.setInconsistencyStatus( daoInconsistency.getInconsistencyStatusByCode( CodesBusinessEntityEnum.INCONSISTENCY_STATUS_ACTIVE.getCodeEntity() ) );
				importLogInconsistency.setInconsistencyType( daoInconsistencyType.getInconsistencyTypeByID( importLogInconsistencyVO.getInconsistencyType().getId() ) );
				importLogInconsistency.setInconsistencyDate( importLogInconsistencyVO.getInconsistencyDate() );
				importLogInconsistency.setComments( importLogInconsistencyVO.getComments() );
				importLogInconsistency.setUser( importLogInconsistencyVO.getUser() );
				importLogInconsistency.setAnswer( importLogInconsistencyVO.getAnswer() );
				importLogInconsistency.setImportLogItem( importLogItem.getId() );
				importLogInconsistency.setExpectedAmount( importLogInconsistencyVO.getExpectedAmount() );
				importLogInconsistency.setAdicion( CodesBusinessEntityEnum.INCONSISTENCY_DEL.getCodeEntity() );
				userId = importLogInconsistencyVO.getUser();
				daoImportLogInconsistency.createImportLogInconsistency(importLogInconsistency);
				importLogInconsistencyVO.setInconsistencyStatus( importLogInconsistency.getInconsistencyStatus() );
				
				inconsistencyList.set(i, UtilsBusiness.copyObject(ImportLogInconsistencyVO.class, importLogInconsistency) );
				inconsistencyList.get(i).setImpLogItem( UtilsBusiness.copyObject(ImportLogItemVO.class, importLogItem)   );
				i++;
			}
			
			//Se calcula el estado del import log
			verificaEstadoImportLog( UtilsBusiness.copyObject(ImportLog.class, importLog), userId );
			
		} catch (Throwable ex) {
			log.error("== Error delItemToInconsistencies/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina delItemToInconsistencies/ImportLogBusinessBean ==");
		}
	}
	
	
	/**
	 * Verifica cual debe ser el estado que se le pone al import log
	 * @param importLog
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 * @throws BusinessException 
	 */
	public void verificaEstadoImportLog(ImportLog importLog, Long userId) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException{
		log.debug("== Inicia verificaEstadoImportLog/ImportLogBusinessBean ==");
		try{
			
			long countRecepted = 0, countInc = 0, countSend = 0, countPartial = 0, countCreate = 0, countError = 0, countTotal = 0;
			
			List<CountItemStatusImportLogDTO> listStatuItems = daoImportLogItem.getCountItemStatusForImportLog(importLog.getId());
			
			for(CountItemStatusImportLogDTO count: listStatuItems){
				if(count!=null){
					if(count.getStatusCode().equals(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity())){
						countRecepted = count.getItemsQuatity();
					}else if(count.getStatusCode().equals(CodesBusinessEntityEnum.ITEM_STATUS_INCONSISTENCY_PROCESS.getCodeEntity())){
						countInc = count.getItemsQuatity();
					}else if(count.getStatusCode().equals(CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity())){
						countSend = count.getItemsQuatity();
					}else if(count.getStatusCode().equals(CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getCodeEntity())){
						countPartial = count.getItemsQuatity();
					}else if(count.getStatusCode().equals(CodesBusinessEntityEnum.ITEM_STATUS_CREATED.getCodeEntity())){
						countCreate = count.getItemsQuatity();
					}else if(count.getStatusCode().equals(CodesBusinessEntityEnum.ITEM_STATUS_ERROR_PROCESS.getCodeEntity())){
						countError = count.getItemsQuatity();
					}
					countTotal+=count.getItemsQuatity();
				}
			}
			
			ImportLogStatus importLogStatus = null;
			
			if(countInc>0){
				importLogStatus = daoImportLogStatus.getImportLogStatusByCode( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_INCONSISTENCY.getCodeEntity() );
			}else if(countPartial>0){
				importLogStatus = daoImportLogStatus.getImportLogStatusByCode( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() );
			}else if(countRecepted == 0){
				importLogStatus = daoImportLogStatus.getImportLogStatusByCode( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity() );
			}else if(countRecepted == countTotal){
				importLogStatus = daoImportLogStatus.getImportLogStatusByCode( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_RECEIVED.getCodeEntity() );
			}else if(countSend > 0 || countError > 0){
				importLogStatus = daoImportLogStatus.getImportLogStatusByCode( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() );
			}
			importLog.setImportLogStatus(importLogStatus);
			daoImportLog.updateImportLog(importLog);
			if ( importLog.getImportLogStatus()!=null && importLog.getImportLogStatus().getStatusCode()!=null && userId!=null )
				createImportLogItemModification(userId, importLog, impLogModBusiness.importLogStatusToImportLogModification( importLog.getImportLogStatus() ) );
		} catch (Throwable ex) {
			log.error("== Error verificaEstadoImportLog/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina verificaEstadoImportLog/ImportLogBusinessBean ==");
		}
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#getImportLogsInInconsistenceByIdAndCreationDate(java.lang.Long, java.util.Date, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ImportLogVO> getInconsisntencyImportLogByIdCreationDateAndLogisticOp(Long idImportLog, Date creationDate, Long logisticOpId) throws BusinessException{
		log.debug("== Inicia getInconsisntencyImportLogByIdCreationDateAndLogisticOp/ImportLogBusinessBean ==");
		
		try {
			List<ImportLog> importLogs;
			if(creationDate!=null){
				//Poner la hora inicial y final a la fecha
	        	Calendar horaInic =  new GregorianCalendar();
	        	Calendar horaFin =  new GregorianCalendar();
	        	horaInic.setTime(creationDate);
	        	horaFin.setTime(creationDate);
	        	horaInic.set(Calendar.HOUR_OF_DAY, 0);
	        	horaInic.set(Calendar.MINUTE, 0);
	        	horaInic.set(Calendar.SECOND, 0);
	        	horaInic.set(Calendar.MILLISECOND, 0);
	        	horaFin.set(Calendar.HOUR_OF_DAY, 23);
	        	horaFin.set(Calendar.MINUTE, 59);
	        	horaFin.set(Calendar.SECOND, 59);
	        	horaFin.set(Calendar.MILLISECOND, 0);
	        	importLogs = daoImportLog.getImportLogByIdCreationDateStatusAndLogisticOp(idImportLog, horaInic.getTime(), horaFin.getTime(),CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_INCONSISTENCY.getCodeEntity(), logisticOpId);
			}else{
				importLogs = daoImportLog.getImportLogByIdCreationDateStatusAndLogisticOp(idImportLog,null,null,CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_INCONSISTENCY.getCodeEntity(), logisticOpId);
			}
			return UtilsBusiness.convertList(importLogs, ImportLogVO.class);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getInconsisntencyImportLogByIdCreationDateAndLogisticOp/ImportLogBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getInconsisntencyImportLogByIdCreationDateAndLogisticOp/ImportLogBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#validateToFinishImportLog(co.com.directv.sdii.model.vo.ImportLogVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void validateToFinishImportLog(ImportLogVO importLog) throws BusinessException{
		log.debug("== Inicia validateToFinishImportLog/ImportLogBusinessBean ==");
		try {
			boolean cambiarEstado =  false;
			//operacion paginada, se envia el parametro en null ya que en este punto no es necesario paginar
			RequestCollectionInfo requestCollInfo = null;
			List<ImportLogItem> importLogItems =  this.daoImportLogItem.getImportLogItemsByImportLog(importLog.getId(),requestCollInfo).getImportLogItems();
			List<ImportLogInconsistency> impLogInconcistency =  this.daoImportLogInconsistency.getImporLogInconsistencysByImportLog(importLog.getId());
			boolean itemsRepted = true;
			boolean inconsistencysClosed = true;
			//Valida si todos los elementos estan confirmados
			for(ImportLogItem importLogItemTmp:importLogItems){
				if(!importLogItemTmp.getItemStatus().getStatusCode().equals(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity())){
					itemsRepted =  false;
					break;
				}
			}
			//Valida si todas las inconsistencias se encuentran cerradas
			for(ImportLogInconsistency impLogInconcistencyTmp:impLogInconcistency){
				if(! impLogInconcistencyTmp.getInconsistencyStatus().getIncStatusCode().equals(CodesBusinessEntityEnum.INCONSISTENCY_STATUS_INACTIVE.getCodeEntity()) ){
					inconsistencysClosed =  false;
					break;
				}
			}
			//Modifica el estado del registro de importación
			ImportLogStatus tmp = new ImportLogStatus();
			ImportLog imporLogUpdate = new ImportLog();
			imporLogUpdate = this.daoImportLog.getImportLogByIDAndByLogisticOp(importLog.getId(),importLog.getDealer().getId());
			if(itemsRepted && inconsistencysClosed){
				tmp = this.daoImportLogStatus.getImportLogStatusByCode(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_RECEIVED.getCodeEntity());
				if(tmp==null){
					throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
				}
				cambiarEstado=true;
			}else{
				if(inconsistencysClosed){
					tmp = this.daoImportLogStatus.getImportLogStatusByCode(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity());
					if(tmp==null){
						throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
					}
					cambiarEstado=true;
				}
			}
			if(cambiarEstado){
				imporLogUpdate.setImportLogStatus(tmp);
				this.daoImportLog.updateImportLog(imporLogUpdate);
				if ( imporLogUpdate.getImportLogStatus() != null && imporLogUpdate.getImportLogStatus().getStatusCode()!=null )
					createImportLogItemModification(imporLogUpdate.getUser().getId(), imporLogUpdate, impLogModBusiness.importLogStatusToImportLogModification( imporLogUpdate.getImportLogStatus() ) );
				//Se consulta el operador logistico del país
				Dealer logisticOperator = daoDealer.getDealerByID(importLog.getDealer().getId());
				List<User> users = usersDao.getUsersByDealerIdAndRoleTypeCode(logisticOperator.getId(), CodesBusinessEntityEnum.ROLE_TYPE_DEALER_LOGISTICS_ANALYST.getCodeEntity());
	        	
	    		// Por defecto el log no queda en estado "enviado"
	    		boolean isLogStatusSent = false;
	    		if (tmp.getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity()))
	    		{
	    			isLogStatusSent = true;
	    		}
				sendMailToUpdateImportLogNotification(imporLogUpdate,users, isLogStatusSent);
			}			

		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación validateToFinishImportLog/ImportLogBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina validateToFinishImportLog/ImportLogBusinessBean ==");
		}
	}
	
	/**
	 * Metodo: Permite enviar un email al operador logistico indicando las modificaciones a un registro de importación
	 * @param importLog - ImportLog Registro de importación generado
	 * @param usuarios - Lista de usuarios configurados para recibir el correo de dicho tipo
	 * @param isLogStatusSent - Flag que determina si el registro de importación se modificó con estado "Enviado"
	 * @throws BusinessException en caso de error al enviar email al operador logistico
	 * @throws DAOServiceException en caso de error al enviar email al operador logistico
	 * @throws DAOSQLException en caso de error al enviar email al operador logistico
	 * @author gfandino
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void sendMailToUpdateImportLogNotification(ImportLog importLog, List<User> usuarios, boolean isLogStatusSent) throws BusinessException, DAOServiceException,
			DAOSQLException {
		for(User usuario : usuarios){
			SendEmailDTO email = new SendEmailDTO();

			EmailTypesEnum emailType = EmailTypesEnum.IMPORT_LOG_HAS_BEEN_MODIFIED;
			if(isLogStatusSent){
				emailType = EmailTypesEnum.REGISTER_IMPORT_LOG_HAS_BEEN_MODIFIED_AND_SENT;
			}
			email.setNewsType(emailType.getEmailTypecode());
			email.setNewsDocument(importLog.getId().toString());
			email.setNewsObservation(EmailTypesEnum.IMPORT_LOG_HAS_BEEN_MODIFIED.getDescription());
			email.setNewsSourceUser(usuario.getName());
			List<String> recipients = new ArrayList<String>();
			recipients.add(usuario.getEmail());
			email.setRecipient(recipients);
			businessEmailType.sendEmailByEmailTypeCodeAsic(email, importLog.getDealer().getPostalCode().getCity().getState().getCountry().getId());
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#closeImportLogInconsistencies(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void closeImportLogInconsistencies(List<ImportLogInconsistencyVO>inconsistencyList)throws BusinessException
	{
		log.debug("== Inicia closeImportLogInconsistencies/ImportLogBusinessBean ==");
		try {
			  List<ImportLogInconsistency> listToUpdate = new ArrayList<ImportLogInconsistency>();
			  for(ImportLogInconsistencyVO item:inconsistencyList){
				  ImportLogInconsistency inconsistency = new ImportLogInconsistency();
				  inconsistency.setId(item.getId());
				  inconsistency.setAnswer(item.getAnswer());
				  listToUpdate.add(inconsistency);
			  }
			InconsistencyStatus statusClose = daoInconsistency.getInconsistencyStatusByCode( CodesBusinessEntityEnum.INCONSISTENCY_STATUS_INACTIVE.getCodeEntity() );
			daoImportLogInconsistency.closeImportLogInconsistencies( listToUpdate , statusClose );
			
		} catch (Throwable ex) {
			log.error("== Error == closeImportLogInconsistencies/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina closeImportLogInconsistencies/ImportLogBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#addImportLogItemsToImportLog(java.util.List, co.com.directv.sdii.model.vo.ImportLogVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addImportLogItemsToImportLog(List<ImportLogItemVO>itemList, ImportLogVO importLog)throws BusinessException
	{
		String errorCode    = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
		String errorMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
		log.debug("== Inicia addImportLogItemsToImportLog/ImportLogBusinessBean =="); 
		
		try {
			  UtilsBusiness.assertNotNull( itemList,errorCode,errorMessage);
			  UtilsBusiness.assertNotNull( importLog,errorCode,errorMessage);
			  
			  for(ImportLogItemVO item:itemList){
				  UtilsBusiness.assertNotNull(item.getElement() ,errorCode,errorMessage);   	
				  UtilsBusiness.assertNotNull(item.getElement().getId() ,errorCode,errorMessage);  
				  UtilsBusiness.assertNotNull(item.getMeasureUnit() ,errorCode,errorMessage);   
				  UtilsBusiness.assertNotNull(item.getMeasureUnit().getId() ,errorCode,errorMessage);   
				  UtilsBusiness.assertNotNull(item.getAmountExpected() ,errorCode,errorMessage);   
				  UtilsBusiness.assertNotNull(item.getItemStatus() ,errorCode,errorMessage);  
				  UtilsBusiness.assertNotNull(item.getItemStatus().getId() ,errorCode,errorMessage);   
			  }
			  
			  for(ImportLogItemVO item:itemList){
				  ImportLogItem itemPojo = UtilsBusiness.copyObject(ImportLogItem.class,item );
				  itemPojo.setImportLog(importLog);
                  daoImportLogItem.createImportLogItem(itemPojo);				  
			  }
			
		} catch (Throwable ex) {
			log.error("== Error addImportLogItemsToImportLog/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina addImportLogItemsToImportLog/ImportLogBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#deleteImportLogItemsInImportLog(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteImportLogItemsInImportLog( List<Long>itemList )throws BusinessException
	{
		String errorCode    = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
		String errorMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
		log.debug("== Inicia deleteImportLogItemsInImportLog/ImportLogBusinessBean =="); 
		
		try {
			
			  UtilsBusiness.assertNotNull( itemList,errorCode,errorMessage);
			  List<ImportLogItem>listToDelete = new ArrayList<ImportLogItem>();
			  
			  for(Long itemId:itemList){
				  ImportLogItem itemPojo = daoImportLogItem.getImportLogItemByID(itemId);
				  UtilsBusiness.assertNotNull(itemPojo,errorCode,errorMessage);   	
			      List<ImpLogConfirmation> confirmationsList = daoImportLogConfirmation.getImpLogConfirmationsByImpLogItemId( itemId );   
			      
			      if(confirmationsList==null || confirmationsList.size()==0){
			    	  listToDelete.add(itemPojo);
			      }
			  }
			  
			  for(ImportLogItem item:listToDelete){
				  daoImportLogItem.deleteImportLogItem(item);		  
			  }
			
		} catch (Throwable ex) {
			log.error("== Error deleteImportLogItemsInImportLog/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina deleteImportLogItemsInImportLog/ImportLogBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#modifyImportLogItemQuantity(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void modifyImportLogItemQuantity( List<ImportLogItemVO>itemList )throws BusinessException
	{
		
		String errorCode    = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
		String errorMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
		log.debug("== Inicia modifyImportLogItemQuantity/ImportLogBusinessBean =="); 
		
		try {
			
			  UtilsBusiness.assertNotNull( itemList,errorCode,errorMessage);
			  List<ImportLogItem>itemListToupdate = new ArrayList<ImportLogItem>();
			  
			  for(ImportLogItemVO item:itemList){
				  UtilsBusiness.assertNotNull(item.getId(),errorCode,errorMessage);   	
				  UtilsBusiness.assertNotNull(item.getAmountExpected(),errorCode,errorMessage);   
				  ImportLogItem verifiedItem = daoImportLogItem.getImportLogItemByID(item.getId());
				   
				  if(verifiedItem.getAmountExpected().doubleValue()<item.getAmountExpected().doubleValue()){
				       verifiedItem.setAmountExpected(item.getAmountExpected());
					   itemListToupdate.add(verifiedItem);
				  }
			  }
			  
			  for(ImportLogItem item:itemListToupdate){
                  daoImportLogItem.updateImportLogItem(item);			  
			  }
			
		} catch (Throwable ex) {
			log.error("== Error == modifyImportLogItemQuantity/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina modifyImportLogItemQuantity/ImportLogBusinessBean ==");
		}
		
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#getImportLogByElementId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLogVO getImportLogByElementId(Long elementId) throws BusinessException
	{
		String errorCode    = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
		String errorMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
		log.debug("== Inicia getImportLogByElementId/ImportLogBusinessBean =="); 
		
		try {
			UtilsBusiness.assertNotNull( elementId,errorCode,errorMessage);  
			return UtilsBusiness.copyObject( ImportLogVO.class,  daoImportLog.getImportLogByElementId( elementId ));
		} catch (Throwable ex) {
			log.error("== Error == getImportLogByElementId/ImportLogBusinessBean ==",	ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getImportLogByElementId/ImportLogBusinessBean ==");
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLogResponse getImportLogInconsistencesByCriteria(
			ModifyImportLogDTO modifyImportLogCriteria,
			RequestCollectionInfo requestCollInfo) throws BusinessException {
		
		log.debug("== Inicia getImportLogInconsistencesByCriteria/ImportLogBusinessBean ==");
		UtilsBusiness.assertNotNull(modifyImportLogCriteria, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			ImportLogResponse response = daoImportLog.getImportLogInconsistencesByCriteria(modifyImportLogCriteria, requestCollInfo);
			List<ImportLog> importLogsPojo = response.getImportLog();
			response.setImportLogVO( UtilsBusiness.convertList(importLogsPojo, ImportLogVO.class) );
			response.setImportLog(null);
			
			return response;

		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getImportLogInconsistencesByCriteria/ImportLogBusinessBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getImportLogInconsistencesByCriteria/ImportLogBusinessBean ==");
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ImportLogInconsistency> getImportLogInconsistencysByImportLog(Long importLog) throws BusinessException {
		log.debug("== Inicia getImportLogInconsistencysByImportLog/ImportLogBusinessBean ==");
		UtilsBusiness.assertNotNull(importLog, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			List<ImportLogInconsistency> impLogInconcistency =  this.daoImportLogInconsistency.getImporLogInconsistencysByImportLog(importLog);
			return impLogInconcistency;

		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getImportLogInconsistencysByImportLog/ImportLogBusinessBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getImportLogInconsistencysByImportLog/ImportLogBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal#getAllImportLogByIdDatesStatus(co.com.directv.sdii.model.dto.FilterImportLogToPrintDTO,co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public NotSerializedElementInSelectImportLogToPrintPaginationResponse getAllImportLogByIdDatesStatus(FilterImportLogToPrintDTO filterImportLogToPrintDTO, RequestCollectionInfo requestCollectionInfo)throws BusinessException{
		log.debug("== Inicia getAllImportLogByIdDatesStatus/ImportLogBusinessBean ==");
		try {
			return daoImportLog.getAllImportLogByIdDatesStatus(filterImportLogToPrintDTO, requestCollectionInfo);

		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getAllImportLogByIdDatesStatus/ImportLogBusinessBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAllImportLogByIdDatesStatus/ImportLogBusinessBean ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveItemsforNewImportLog(List<ImportLogItemVO> listaNoSerialized,
			List<ImportLogItemVO> listaSerialed, boolean isNationalBuy, ImportLog importLog,
			boolean isSendStatus, boolean isNewItems, User user)
			throws BusinessException {
		log.debug("== Inicia getWareHouseOrCreateIfNotExists/ImportLogItemBusinessBean ==");
		try{
			
			String movTypeCodeE = isNationalBuy?CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSIT_ENTRY.getCodeEntity():CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSIT_ENTRY.getCodeEntity();
			Warehouse warehouseTarget = null;
			ItemStatus itemStatus;
			if(importLog.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity())){
				itemStatus =  this.daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity());
				warehouseTarget= this.warehouseElementBusiness.getWareHouseOrCreateIfNotExists(importLog.getDealer().getId(),CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity());
			}else{
				itemStatus =  this.daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_CREATED.getCodeEntity());
			}
			
			//Se consulta el estado del elemento
			ElementStatus elementStatus = this.daoElementStatus.getElementStatusByCode(CodesBusinessEntityEnum.ELEMENT_STATUS_NA.getCodeEntity());
			
			MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movTypeCodeE, null);
			
			//Crea los elementos no serializados
			for (ImportLogItemVO importLogItem : listaNoSerialized) {
				businessImportLogItem.createElementNotSerializedForNewImport(importLogItem, elementStatus, importLog, itemStatus, isSendStatus, isNewItems, dtoGenerics, user, warehouseTarget);
			}
			
			//Crea los elementos serializados 
			for (ImportLogItemVO importLogItem :listaSerialed) {
				businessImportLogItem.createElementSerializedForNewImportLog(importLogItem, elementStatus, importLog, itemStatus, isSendStatus, isNewItems, dtoGenerics, user, warehouseTarget);
			}
			/*
			if(isSendStatus){
	        	this.finishImportLog(user, importLog.getId(), getWareHouseOrCreateIfNotExists(importLog.getDealer().getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity()), movTypeCodeE, null, CodesBusinessEntityEnum.PROCESS_CODE_IBS_IMPORT_LOG.getCodeEntity());
	    	}*/
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación getWareHouseOrCreateIfNotExists/ImportLogItemBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina getWareHouseOrCreateIfNotExists/ImportLogItemBusinessBean ==");
	    }
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void modifyImportLog(List<ImportLogItemVO> creables,
			List<ImportLogItemVO> actualizables,
			List<ImportLogItemVO> eliminables, Long userId,
			ImportLogVO importLogId, boolean isFinish) throws BusinessException {
		log.debug("== Inicia modifyImportLog/ImportLogItemBusinessBean ==");
		UtilsBusiness.assertNotNull(userId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());       
        UtilsBusiness.assertNotNull(importLogId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(importLogId.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
        Object params[] = {ApplicationTextEnum.ELEMENTS_NOT_SERIALIZED_QUANTITY.getApplicationTextValue()};
        for (ImportLogItemVO importLogItemCreables : creables) {
        	if(importLogItemCreables.getAmountExpected() > 99999999){
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN416.getCode(), ErrorBusinessMessages.STOCK_IN416.getMessage(params));
        	}        	
        }  
        for (ImportLogItemVO importLogItemActualizables : actualizables) {
        	if(importLogItemActualizables.getAmountExpected() > 99999999){
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN416.getCode(), ErrorBusinessMessages.STOCK_IN416.getMessage(params));
        	}        	
        }
        
        try{
			//consultar el usuario
			User user = UtilsBusiness.copyObject(UserVO.class, daoUser.getUserById(userId));
			
			//Valida la existencia del registro de importación
        	ImportLog importLogDB = daoImportLog.getImportLogByID(importLogId.getId());
        	importLogId.setUser(importLogDB.getUser());
        	if(importLogDB == null){
        		log.debug("El registro de importacion que se desea modificar no esta en estado creado");
        		throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getCode(),ErrorBusinessMessages.IMPORT_LOG_ITEM_IS_INVALID.getMessage());        		
        	}
        	
			this.saveItemsForImportLogModify(importLogId, importLogDB, creables, actualizables, eliminables, isFinish, user);
			
			importLogDB = daoImportLog.getImportLogByID(importLogId.getId());
			
			//Actualizar el estado del registro de importación al estado en proceso
			this.updateImportLogStatus(importLogDB, CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_PROCESS.getCodeEntity());
			
			//Ubicar los elementos en la bodega se transito si se finaliza el registro de importacion
			if(isFinish){
				boolean isNationalBuy = this.isNationalBuy(UtilsBusiness.copyObject(ImportLogVO.class, importLogDB));
				this.setImportLogItemsInWareHouseTransitFinishImportLog(importLogDB, user, isNationalBuy);
			}
			this.updateImportLogModify(importLogDB, isFinish);

		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación modifyImportLog/ImportLogItemBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina modifyImportLog/ImportLogItemBusinessBean ==");
		}
	}
	/**
	 * 
	 * @param importLog
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private void setImportLogItemsInWareHouseTransitFinishImportLog(ImportLog importLog, User user, boolean isNationalBuy) throws BusinessException{
		log.debug("== Termina setImportLogItemsInWareHouseTransit/WarehouseElementBusinessBean ==");
		try{
			ItemStatus itemStatus = new ItemStatus();
			itemStatus = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity());
			
			ItemStatus itemStatusError = new ItemStatus();
			itemStatusError = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_ERROR_PROCESS.getCodeEntity());
			
			String movTypeCodeE = isNationalBuy?CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSIT_ENTRY.getCodeEntity():CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSIT_ENTRY.getCodeEntity();
			MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movTypeCodeE, null);
			
			Warehouse warehouseTarget= this.warehouseElementBusiness.getWareHouseOrCreateIfNotExists(importLog.getDealer().getId(),CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity());
			
			ImportLogItemResponse notSerResponse = daoImportLogItem.getImportLogItemsByImportLogIdAndIsSerialized(importLog.getId(), false, null);
			if(notSerResponse != null && notSerResponse.getImportLogItems() != null && !notSerResponse.getImportLogItems().isEmpty()){
				for(ImportLogItem notSerItem : notSerResponse.getImportLogItems()){
					try{
						this.businessImportLogItem.setElementNotSerializedInWareHouseTransit(user, notSerItem, importLog, warehouseTarget, itemStatus, dtoGenerics);
						notSerItem.setItemStatus(itemStatus);
						this.businessImportLogItem.updateImportLogItemStatus(notSerItem);
					}catch (Throwable e) {
						notSerItem.setItemStatus(itemStatusError);
						this.businessImportLogItem.updateImportLogItemStatus(notSerItem);
					}
				}
			}
			
			
			//Se consultan los elementos serializados del registro de importacion
			ImportLogItemResponse serResponse = daoImportLogItem.getImportLogItemsByImportLogIdAndIsSerialized(importLog.getId(), true, null);
			if(serResponse != null && serResponse.getImportLogItems() != null && !serResponse.getImportLogItems().isEmpty()){
				for(ImportLogItem serItem : serResponse.getImportLogItems()){
					try{
						this.businessImportLogItem.setElementSerializedInWareHouseTransit(user, serItem, importLog, warehouseTarget, itemStatus, dtoGenerics);
						serItem.setItemStatus(itemStatus);
						this.businessImportLogItem.updateImportLogItemStatus(serItem);
					}catch (Throwable e) {
						serItem.setItemStatus(itemStatusError);
						this.businessImportLogItem.updateImportLogItemStatus(serItem);
					}
				}
			}
		} catch (Throwable t) {
			log.error(" == Error setImportLogItemsInWareHouseTransit/WarehouseElementBusinessBean ==");
			throw this.manageException(t);
		} finally {
			log.debug("== Termina setImportLogItemsInWareHouseTransit/WarehouseElementBusinessBean ==");
		}
	}
	
	/**
	 * Metodo encargado de la actualización del estado de un registro
	 * de importacion
	 * @param importLog
	 * @param statusCode
	 * @throws BusinessException
	 * @author waguilera
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void updateImportLogStatus(ImportLog importLog, String statusCode) throws BusinessException{
		log.debug("== Inicia updateImportLogStatus/ImportLogItemBusinessBean ==");
		UtilsBusiness.assertNotNull(importLog,ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(importLog.getId(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(statusCode,ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try{
			ImportLogStatus importLogStatus = daoImportLogStatus.getImportLogStatusByCode(statusCode);
			if(importLogStatus == null){
				throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_STATUS_IS_NOT_VALID.getCode(), ErrorBusinessMessages.IMPORT_LOG_STATUS_IS_NOT_VALID.getMessage());
			}
			importLog.setImportLogStatus(importLogStatus);
			daoImportLog.updateImportLog(importLog);
			
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación updateImportLogStatus/ImportLogItemBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina updateImportLogStatus/ImportLogItemBusinessBean ==");
		}
	}
	
	/**
	 * Operación encargada de la actualizacion de registro de importación en la pantalla de modificacion del documento
	 * y en caso de finalizacion el envió del mail
	 * @param importLogDB
	 * @param isSendStatus
	 * @author waguilera
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void updateImportLogModify(ImportLog importLogDB, boolean isSendStatus) throws BusinessException{
		log.debug("== Inicia updateImportLogModify/ImportLogItemBusinessBean ==");
		try{
			//Actualizar el estado del registro de importación al estado en enviado
            
            if(isSendStatus){
            	this.updateImportLogStatus(importLogDB, CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity());
            }else{
            	this.updateImportLogStatus(importLogDB, CodesBusinessEntityEnum.IMPORT_LOG_STATUS_CREATED.getCodeEntity());
            }
            
            //Se consulta el dealer asociado a la orden de importacion para recuperar los operadores logisticos
            Dealer logisticOperator = daoDealer.getDealerByID(importLogDB.getDealer().getId());
        	if(logisticOperator == null){
        		log.error("No se encontro dealer operador logístico del registro de importación con id: " + importLogDB.getId());
        		throw new BusinessException(ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getMessage());
        	}
        	
        	if (isSendStatus){
        		//Se consultan los usuarios operadores logisticos del dealer asociado al registro de importacion
        		List<User> users = daoUser.getUsersByDealerIdAndRoleTypeCode(logisticOperator.getId(), CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_OPERATOR.getCodeEntity());
        		
				//Invocar INV_08
	        	User u = importLogDB.getUser();
	        	u = daoUser.getUserById(u.getId());
	        	if(!users.isEmpty() && users.size() > 0){
	        		ImportLogStatus importLogStatus = daoImportLogStatus.getImportLogStatusByID(importLogDB.getImportLogStatus().getId());
	        		importLogDB.setImportLogStatus(importLogStatus);
	        		String usurioNotificaNovedad = u.getName();
	        		for (User usert: users){
	        			log.info("== user ==" + usert.getEmail());
	        		}
	        		sendMailToRegisterImportLogNotification(importLogDB, users, usurioNotificaNovedad);
	        	}else{
	        		String message = "No se encontraron usuarios con un rol del tipo codigo: \"" + CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_OPERATOR.getCodeEntity() +"\" asociados al dealer con id: \"" + logisticOperator.getId() + "\"\"";
	        		log.error(message);
	        	}
        	}
        	
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación updateImportLogModify/ImportLogItemBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina updateImportLogModify/ImportLogItemBusinessBean ==");
		}
	} 
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void saveItemsForImportLogModify(ImportLogVO importLogId, ImportLog importLogDB, List<ImportLogItemVO> newItems, 
			List<ImportLogItemVO> updateItems, List<ImportLogItemVO> deleteItems,  boolean isSendImportLog, User user)throws BusinessException{
		log.debug("== Inicia saveItemsForImportLogModify/ImportLogItemBusinessBean ==");
		
		try{
			ElementStatus elementStatus = this.daoElementStatus.getElementStatusByCode(CodesBusinessEntityEnum.ELEMENT_STATUS_NA.getCodeEntity());
			if (newItems != null && newItems.size() > 0){
            	validateImportLogItemsList(newItems);
            }
			
			//Validar si existen archivs pendientes de procesamiento para el registro de importación
			validatePendingItemsInFiles(importLogId.getId());
			
			
			//Validar el que no pueda modificar un registro de importacion que este en estado diferente al creado
        	if (importLogDB.getImportLogStatus().getId().longValue() != CodesBusinessEntityEnum.IMPORT_LOG_STATUS_CREATED.getIdEntity(ImportLogStatus.class.getName()).longValue() ){
        		log.error("El registro de importacion que se desea modificar no esta en estado creado");
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN426.getCode(),ErrorBusinessMessages.STOCK_IN426.getMessage());
        	}
			
        	// Validar que el registro de importacion no tenga un numero de orden con el mismo proveedor.
        	ModifyImportLogDTO modifyImportLogCriteria = new ModifyImportLogDTO();
			modifyImportLogCriteria.setPurchaseOrder(importLogId.getPurchaseOrder());
			modifyImportLogCriteria.setSupplierId(importLogId.getSupplier().getId());
			ImportLogResponse ilr = daoImportLog.getImportLogByCriteria(modifyImportLogCriteria, user.getCountry().getId(), null);
			if (!ilr.getImportLog().isEmpty() && ilr.getImportLog().get(0).getId().longValue() != importLogId.getId().longValue()){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN432.getCode(),	ErrorBusinessMessages.STOCK_IN432.getMessage());
			}
			
			ItemStatus newItemStatus = null;
			if(isSendImportLog){
        		validatePendingItemsInFiles(importLogId.getId());
        		//El registro de importacion y sus elementos quedan en "enviado"
        		newItemStatus = new ItemStatus(CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getIdEntity(ItemStatus.class.getName()));
        	} else {
        		//El registro de improtacion y sus elementos quedan en "creado"
        		newItemStatus = new ItemStatus(CodesBusinessEntityEnum.ITEM_STATUS_CREATED.getIdEntity(ItemStatus.class.getName()));
        	}
			
			//Actualiza los datos del registro de importacion
        	this.updateImportLog(importLogId,user.getId());
        	
        	//Actualizar los elementos (Aplica para no serializados)
        	if (updateItems!=null && updateItems.size()>0){
        		businessImportLogItem.updateElementItemImportLog(updateItems, elementStatus, newItemStatus, importLogDB);
        	}
        	
        	//borrar elementos del registro de importación
        	businessImportLogItem.deleteElementItemImportLog( importLogDB, deleteItems, user);
        	
        	//3) Crear los elementos nuevos
        	businessImportLogItem.createNewImportLogElementItems(newItems , importLogId, newItemStatus, isSendImportLog, user);
			
        	//validar que tenga elementos en el registro de importacion
        	if(isSendImportLog && daoImportLogItem.getImportLogItemsByImportLog(importLogId.getId(), null).getImportLogItems().isEmpty()){
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN418.getCode(), ErrorBusinessMessages.STOCK_IN418.getMessage());
        	}
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación saveItemsForImportLogModify/ImportLogItemBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina saveItemsForImportLogModify/ImportLogItemBusinessBean ==");
		}
	}
	
	/**
	 * Metodo: valida la existencia de archivos de cargue masivo de elementos a un registro de importaciÃ³n
	 * que no se han finalizado de procesar.
	 * @param importLogId identificador del registro de importaciÃ³n
	 * @throws BusinessException en caso que existan archivos con proceso sin finalizar
	 * @author wjimenez
	 */
	private void validatePendingItemsInFiles(Long importLogId) throws PropertiesException, BusinessException {
		List<FilterUploadFileParamByFileTypeParamNameAndCodeDTO> filters = new ArrayList<FilterUploadFileParamByFileTypeParamNameAndCodeDTO>();
		
		FilterUploadFileParamByFileTypeParamNameAndCodeDTO filterUploadFile = new FilterUploadFileParamByFileTypeParamNameAndCodeDTO();
		filterUploadFile.setFileTypeCode(CodesBusinessEntityEnum.FILE_TYPE_IMPORTLOG_SERIALIZEDELEMENTS.getCodeEntity());
		
		filterUploadFile.setParamName(CodesBusinessEntityEnum.FILE_PARAM_ID_IMPORT_LOG.getCodeEntity());
		filterUploadFile.setParamValue(importLogId.toString());
		
		filters.add(filterUploadFile);
		
		List<UploadFileParamByFileTypeParamNameAndCodeDTO> pendingOrProcessingFiles = businessUploadFile.getUploadFileParamByFileTypeParamNameAndCode(filters);
		if(pendingOrProcessingFiles != null && !pendingOrProcessingFiles.isEmpty()) {
			throw new BusinessException(ErrorBusinessMessages.STOCK_IN434.getCode(), ErrorBusinessMessages.STOCK_IN434.getMessage());
		}
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<Object> doGlobalValidationsFileProcessorImportLogSerializedAllElements(UploadFile uploadFile) throws BusinessException{
		
		ImportLog importLogD = null;
		List<Object> result = new ArrayList<Object>();
		
		ImportLog importLog = new ImportLog();
		ElementStatus elementStatus = new ElementStatus();
		ItemStatus itemStatus  = new ItemStatus();
		
		log.debug("== Termina doGlobalValidationsFileProcessorImportLogSerializedAllElements/ImportLogItemBusinessBean ==");
		try{
		    UploadFileParam pImportLogId = uploadFileParamDAO.getUploadFileParamByUploadFileAndName(uploadFile.getId(),CodesBusinessEntityEnum.FILE_PARAM_ID_IMPORT_LOG.getCodeEntity());
			
			if(pImportLogId != null && NumberUtils.isNumber(pImportLogId.getParamValue())) {
				importLog = daoImportLog.getImportLogByID( Long.parseLong( pImportLogId.getParamValue() ) );
				if(importLog == null) {
					throw new BusinessException("no se encontró el registro de importación con id = " + pImportLogId.getParamValue());
				}
				
			}else {
				throw new BusinessException("no se encontró un id válido de registro de importación para realizar la búsqueda.");
			}
			
			UploadFileParam pDealerId = uploadFileParamDAO.getUploadFileParamByUploadFileAndName(uploadFile.getId(),CodesBusinessEntityEnum.FILE_PARAM_DEALER_ID.getCodeEntity());
			
			if( pDealerId == null || !NumberUtils.isNumber(pDealerId.getParamValue()) ) {
				throw new BusinessException("no se encontró un valor válido para el parámetro con el identificador del dealer.");
			}
			
			Long dealerId = Long.valueOf(pDealerId.getParamValue());
			
			importLogD = new ImportLog();
			Dealer d = new Dealer();
			d.setId( dealerId );
			importLogD.setDealer(d);
			
			elementStatus = daoElementStatus.getElementStatusByID(CodesBusinessEntityEnum.ELEMENT_STATUS_T01.getIdEntity(ElementStatus.class.getName()));//En Transito desde Proveedor
			itemStatus = daoItemStatus.getItemStatusCreated();
			
			result.add(elementStatus);
			result.add(itemStatus);
			result.add(importLog);
			
			return result;
			
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación doGlobalValidationsFileProcessorImportLogSerializedAllElementsb/ImportLogItemBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina doGlobalValidationsFileProcessorImportLogSerializedAllElements/ImportLogItemBusinessBean ==");
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void doGlobalValidationsFileProcessorImportLogSerializedAllElementsPost(FileRecordDTO fileRecordDTO,
			                                                                       ElementStatus elementStatus,
			                                                                       ItemStatus itemStatus,
			                                                                       ImportLog importLog) throws BusinessException{

        int POS_ELEMENT_TYPE = 0;
		int POS_SERIAL = 1;
		int POS_LINKED_ELEMENT_TYPE = 3;	
		int POS_LINKED_SERIAL = 4;

		String PARAM_ITEM_STATUS = "itemS";
		String PARAM_ELEMENT_STATUS = "elemS";
		String PARAM_IMPORT_LOG = "imp_log";
		
		log.debug("== Termina doGlobalValidationsFileProcessorImportLogSerializedAllElementsPost/ImportLogItemBusinessBean ==");
		try {
			
				String[] record = fileRecordDTO.getRowData();	
				
				String elementType = record[POS_ELEMENT_TYPE];
				String serial = record[POS_SERIAL];
				String elementTypeLinked = record[POS_LINKED_ELEMENT_TYPE];
				String serialLinked = record[POS_LINKED_SERIAL];
				
				if (StringUtils.isBlank(serial)) {
					throw new BusinessException("Debe digitar un serial");
				}
				
				if (StringUtils.isBlank(elementType)){
					throw new BusinessException("El tipo de Elemento es obligatorio");
				}
				ElementType et = daoElementType.getElementTypeByCode(elementType);
				if (et == null){
					throw new BusinessException("El tipo de elemento con codigo: [" +elementType+ "] no existe en el sistema.");
				}
				
				if (!StringUtils.isBlank(elementTypeLinked) && StringUtils.isBlank(serialLinked)){
					throw new BusinessException("Si digito un tipo de elemento vinculado, debe digitar el serial del elemento vinculado");
				}
				
				if (StringUtils.isBlank(elementTypeLinked) && !StringUtils.isBlank(serialLinked)){
					throw new BusinessException("Si digito un serial vinculado, debe digitar el tipo de elemento");
				}
				
				if(!StringUtils.isBlank(elementTypeLinked)) {
					ElementType etv = daoElementType.getElementTypeByCode(elementTypeLinked);
					if (etv == null){
						throw new BusinessException("El tipo de elemento vinculado con codigo: [" +elementTypeLinked+ "] no existe en el sistema.");
					}
				}
				
				//Serialized tmp = this.daoSerialized.getSerializedIdBySerial(serial);
				//if (tmp != null){
				if (daoSerialized.isSerializedBySerial(serial)){
					throw new BusinessException("El elemento con serial ["+serial+"] ya existe en el sistema");
				}
				
				if (!StringUtils.isBlank(serialLinked)){
					//Serialized tmpL = this.daoSerialized.getSerializedIdBySerial(serialLinked);
					//if (tmpL != null){
					if (daoSerialized.isSerializedBySerial(serialLinked)){
						throw new BusinessException("El elemento vinculado con serial ["+serial+"] ya existe en el sistema");
					}
				}
				
				//almacenar los parámetros necesarios para el procesamiento
				fileRecordDTO.clearParams();
				fileRecordDTO.addParam(PARAM_ELEMENT_STATUS, elementStatus);
				fileRecordDTO.addParam(PARAM_ITEM_STATUS, itemStatus);
				fileRecordDTO.addParam(PARAM_IMPORT_LOG, importLog);
			
			
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación doGlobalValidationsFileProcessorImportLogSerializedAllElementsPost/ImportLogItemBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina doGlobalValidationsFileProcessorImportLogSerializedAllElementsPost/ImportLogItemBusinessBean ==");
		}
	}
	
}
