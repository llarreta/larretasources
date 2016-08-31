package co.com.directv.sdii.ejb.business.stock.impl;

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

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.EmailTypesEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.common.EmailTypeBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ImpLogModificationBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ImportLogInconsistencyBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.SendEmailDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.ImpLogModification;
import co.com.directv.sdii.model.pojo.ImpLogModificationType;
import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.pojo.ImportLogInconsistency;
import co.com.directv.sdii.model.pojo.ImportLogItem;
import co.com.directv.sdii.model.pojo.ImportLogStatus;
import co.com.directv.sdii.model.pojo.InconsistencyStatus;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.collection.ImportLogInconsistencyResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ImportLogInconsistencyVO;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImpLogModificationDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImportLogInconsistencyDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImportLogItemDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImportLogStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.InconsistencyStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ItemStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD ImportLogInconsistency
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogInconsistencyDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ImportLogInconsistencyBusinessBeanLocal
 */
@Stateless(name="ImportLogInconsistencyBusinessBeanLocal",mappedName="ejb/ImportLogInconsistencyBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImportLogInconsistencyBusinessBean extends BusinessBase implements ImportLogInconsistencyBusinessBeanLocal {

    @EJB(name="ImportLogInconsistencyDAOLocal", beanInterface=ImportLogInconsistencyDAOLocal.class)
    private ImportLogInconsistencyDAOLocal daoImportLogInconsistency;
    
    @EJB(name="InconsistencyStatusDAOLocal", beanInterface=InconsistencyStatusDAOLocal.class)
    private InconsistencyStatusDAOLocal daoInconsistencyStatusDAOLocal;
	
	@EJB(name="ImportLogItemDAOLocal",beanInterface=ImportLogItemDAOLocal.class)
	private ImportLogItemDAOLocal daoImportLogItem;
	
	@EJB(name = "ImportLogStatusDAOLocal", beanInterface = ImportLogStatusDAOLocal.class)
	private ImportLogStatusDAOLocal daoImportLogStatus;
	
	@EJB(name="ImportLogDAOLocal",beanInterface=ImportLogDAOLocal.class)
	private ImportLogDAOLocal daoImportLog;
	
	@EJB(name = "ItemStatusDAOLocal", beanInterface = ItemStatusDAOLocal.class)
	private ItemStatusDAOLocal daoItemStatus;
	
	@EJB(name="ImpLogModificationDAOLocal",beanInterface=ImpLogModificationDAOLocal.class)
	private ImpLogModificationDAOLocal daoImpLogModification;
	
	@EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
    private DealersDAOLocal daoDealer;
	
	@EJB(name = "UserDAOLocal", beanInterface = UserDAOLocal.class)
	private UserDAOLocal daoUser;

	@EJB(name = "EmailTypeBusinessBeanLocal", beanInterface = EmailTypeBusinessBeanLocal.class)
	private EmailTypeBusinessBeanLocal businessEmailType;
	
	@EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal warehouseElementDAO;
	
	@EJB(name="ImpLogModificationBusinessBeanLocal", beanInterface=ImpLogModificationBusinessBeanLocal.class)
	private ImpLogModificationBusinessBeanLocal impLogModBusiness;
    
    private final static Logger log = UtilsBusiness.getLog4J(ImportLogInconsistencyBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ImportLogInconsistencyBusinessBeanLocal#getAllImportLogInconsistencys()
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ImportLogInconsistencyVO> getAllImportLogInconsistencys() throws BusinessException {
        log.debug("== Inicia getAllImportLogInconsistencys/ImportLogInconsistencyBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoImportLogInconsistency.getAllImportLogInconsistencys(), ImportLogInconsistencyVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllImportLogInconsistencys/ImportLogInconsistencyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllImportLogInconsistencys/ImportLogInconsistencyBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ImportLogInconsistencyBusinessBeanLocal#getImportLogInconsistencysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ImportLogInconsistencyVO getImportLogInconsistencyByID(Long id) throws BusinessException {
        log.debug("== Inicia getImportLogInconsistencyByID/ImportLogInconsistencyBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImportLogInconsistency objPojo = daoImportLogInconsistency.getImportLogInconsistencyByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ImportLogInconsistencyVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getImportLogInconsistencyByID/ImportLogInconsistencyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getImportLogInconsistencyByID/ImportLogInconsistencyBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogInconsistencyBusinessBeanLocal#createImportLogInconsistency(co.com.directv.sdii.model.vo.ImportLogInconsistencyVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createImportLogInconsistency(ImportLogInconsistencyVO obj) throws BusinessException {
        log.debug("== Inicia createImportLogInconsistency/ImportLogInconsistencyBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImportLogInconsistency objPojo =  UtilsBusiness.copyObject(ImportLogInconsistency.class, obj);
            daoImportLogInconsistency.createImportLogInconsistency(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createImportLogInconsistency/ImportLogInconsistencyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createImportLogInconsistency/ImportLogInconsistencyBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogInconsistencyBusinessBeanLocal#updateImportLogInconsistency(co.com.directv.sdii.model.vo.ImportLogInconsistencyVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateImportLogInconsistency(ImportLogInconsistencyVO obj) throws BusinessException {
        log.debug("== Inicia updateImportLogInconsistency/ImportLogInconsistencyBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
         	//Cambiar el estado del item del elemento
        	ItemStatus itemStatus = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity());
        	ImportLogItem importLogItem = daoImportLogItem.getImportLogItemByID(obj.getImportLogItem());
        	importLogItem.setItemStatus(itemStatus);
        	daoImportLogItem.updateImportLogItem(importLogItem);
        	//Verificar si todos los elementos cambiaron de estado para cambiar el estado del Registro de Importacion
            boolean isImportLogItemInconsistent =  daoImportLogItem.isImportLogItemStatus(obj.getImportLogItem(), CodesBusinessEntityEnum.ITEM_STATUS_INCONSISTENCY_PROCESS.getCodeEntity() );
        	
        	if (!isImportLogItemInconsistent){
        		ImportLogStatus status = daoImportLogStatus.getImportLogStatusByCode( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity() );
        		ImportLog importLogP = daoImportLog.getImportLogByID(obj.getImportLog());
    			importLogP.setImportLogStatus(status);
    			daoImportLog.updateImportLog( importLogP );
    			if ( importLogP.getImportLogStatus() != null && importLogP.getImportLogStatus().getStatusCode()!=null )
    				createImportLogItemModification(importLogP.getUser().getId(), importLogP, impLogModBusiness.importLogStatusToImportLogModification( importLogP.getImportLogStatus() ) );
        	}
        	
        	//Verificar si hay algun elemento en estado confirmacion parcial
        	boolean isImportLogParcialConfirm = daoImportLogItem.isImportLogItemStatus(obj.getImportLogItem(), CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() );
        	if (!isImportLogParcialConfirm){
        		ImportLogStatus status = daoImportLogStatus.getImportLogStatusByCode( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() );
        		ImportLog importLogP = daoImportLog.getImportLogByID(obj.getImportLog());
    			importLogP.setImportLogStatus(status);
    			daoImportLog.updateImportLog( importLogP );
        	}
        	
        	//Validar si existieron movimientos fuera de la bodega de transito
    		if ( !warehouseElementDAO.getWhElementByElementIdAndNotWhType(importLogItem.getElement().getId().longValue(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity()).isEmpty() ){
    			ImportLogStatus status = daoImportLogStatus.getImportLogStatusByCode( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() );
        		ImportLog importLogP = daoImportLog.getImportLogByID(obj.getImportLog());
    			importLogP.setImportLogStatus(status);
    			daoImportLog.updateImportLog( importLogP );
    		}
        	
            ImportLogInconsistency objPojo =  daoImportLogInconsistency.getImportLogInconsistencyByID(obj.getId());
            InconsistencyStatus inconsistencyStatus = daoInconsistencyStatusDAOLocal.getInconsistencyStatusByCode(CodesBusinessEntityEnum.INCONSISTENCY_STATUS_CLOSE.getCodeEntity());
            objPojo.setInconsistencyStatus(inconsistencyStatus);
            
            daoImportLogInconsistency.updateImportLogInconsistency(objPojo);
            if(inconsistencyStatus.getIncStatusCode().equals(CodesBusinessEntityEnum.INCONSISTENCY_STATUS_CLOSE.getCodeEntity()))
            {
    			Dealer logisticOperator = daoDealer.getDealerByID(importLogItem.getImportLog().getDealer().getId());
    			//Se consultan los usuarios analistas logisticos del dealer operador logistico
            	List<User> users = daoUser.getUsersByDealerIdAndRoleTypeCode(logisticOperator.getId(), CodesBusinessEntityEnum.ROLE_TYPE_DEALER_LOGISTICS_ANALYST.getCodeEntity());

            	if (users.size() > 0){
            		for (User user: users){
            			log.info("== user ==" + user.getEmail());
            		}
        			// Invocar INV_08 - Se envía correo notificando el cierre de una inconsistencia
                	sendMailToNotifyInconsistencyClosing(obj, importLogItem.getImportLog(), users);
            	}
            }
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateImportLogInconsistency/ImportLogInconsistencyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateImportLogInconsistency/ImportLogInconsistencyBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogInconsistencyBusinessBeanLocal#deleteImportLogInconsistency(co.com.directv.sdii.model.vo.ImportLogInconsistencyVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteImportLogInconsistency(ImportLogInconsistencyVO obj) throws BusinessException {
        log.debug("== Inicia deleteImportLogInconsistency/ImportLogInconsistencyBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	//Cambiar el estado del item del elemento
        	ItemStatus itemStatus = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity());
        	ImportLogItem importLogItem = daoImportLogItem.getImportLogItemByID(obj.getImportLogItem());
        	importLogItem.setItemStatus(itemStatus);
        	daoImportLogItem.updateImportLogItem(importLogItem);
        	//Verificar si todos los elementos cambiaron de estado para cambiar el estado del Registro de Importacion
            boolean isImportLogItemInconsistent =  daoImportLogItem.isImportLogItemStatus(obj.getImportLogItem(), CodesBusinessEntityEnum.ITEM_STATUS_INCONSISTENCY_PROCESS.getCodeEntity());
        	
        	if (!isImportLogItemInconsistent){
        		ImportLogStatus status = daoImportLogStatus.getImportLogStatusByCode( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity() );
        		ImportLog importLogP = daoImportLog.getImportLogByID(obj.getImportLog());
    			importLogP.setImportLogStatus(status);
    			daoImportLog.updateImportLog( importLogP );
    			if ( importLogP.getImportLogStatus() != null && importLogP.getImportLogStatus().getStatusCode()!=null )
    				createImportLogItemModification(importLogP.getUser().getId(), importLogP, impLogModBusiness.importLogStatusToImportLogModification( importLogP.getImportLogStatus() ) );
        	}
        	
        	//Verificar si hay algun elemento en estado confirmacion parcial
        	// validar en WarehouseElement si se realizo un movimiento a una bodega diferente a
        	// a la de transito.
        	boolean isImportLogParcialConfirm = daoImportLogItem.isImportLogItemStatus(obj.getImportLogItem(), CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() );
        	if (!isImportLogParcialConfirm){
        		ImportLogStatus status = daoImportLogStatus.getImportLogStatusByCode( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() );
        		ImportLog importLogP = daoImportLog.getImportLogByID(obj.getImportLog());
    			importLogP.setImportLogStatus(status);
    			daoImportLog.updateImportLog( importLogP );
        	}
        	
            ImportLogInconsistency objPojo =  UtilsBusiness.copyObject(ImportLogInconsistency.class, obj);
            daoImportLogInconsistency.deleteImportLogInconsistency(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteImportLogInconsistency/ImportLogInconsistencyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImportLogInconsistency/ImportLogInconsistencyBusinessBean ==");
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
        	impLogModification.setModificationDate(UtilsBusiness.getCurrentTimeZoneDateByUserId(userId, daoUser));
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


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogInconsistencyBusinessBeanLocal#getImportLogInconsitencysByCreationDate(java.util.Date)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ImportLogInconsistencyVO> getImportLogInconsitencysByCreationDate(
			Date inic) throws BusinessException {
		log.debug("== Inicia deleteImportLogInconsistency/ImportLogInconsistencyBusinessBean ==");
        UtilsBusiness.assertNotNull(inic, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	//Poner la hora inicial y final a la fecha
        	Calendar horaInic =  new GregorianCalendar();
        	Calendar horaFin =  new GregorianCalendar();
        	horaInic.setTime(inic);
        	horaFin.setTime(inic);
        	horaInic.set(Calendar.HOUR_OF_DAY, 0);
        	horaInic.set(Calendar.MINUTE, 0);
        	horaInic.set(Calendar.SECOND, 0);
        	horaInic.set(Calendar.MILLISECOND, 0);
        	horaFin.set(Calendar.HOUR_OF_DAY, 23);
        	horaFin.set(Calendar.MINUTE, 59);
        	horaFin.set(Calendar.SECOND, 59);
        	horaFin.set(Calendar.MILLISECOND, 0);
            return UtilsBusiness.convertList(daoImportLogInconsistency.getImportLogInconsitencysByCreationDate(horaInic.getTime(),horaFin.getTime()), ImportLogInconsistencyVO.class) ;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteImportLogInconsistency/ImportLogInconsistencyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImportLogInconsistency/ImportLogInconsistencyBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogInconsistencyBusinessBeanLocal#getImportLogInconsitencysByCreationDate(java.util.Date, java.util.Date)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ImportLogInconsistencyVO> getImportLogInconsitencysByCreationDate(
			Date inic, Date end) throws BusinessException {
		log.debug("== Inicia getImportLogInconsitencysByCreationDate/ImportLogInconsistencyBusinessBean ==");
        UtilsBusiness.assertNotNull(inic, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	//Poner la hora inicial y final a las fechas fecha
        	Calendar horaInic =  new GregorianCalendar();
        	Calendar horaFin =  new GregorianCalendar();
        	horaInic.setTime(inic);
        	horaFin.setTime(end);
        	horaInic.set(Calendar.HOUR_OF_DAY, 0);
        	horaInic.set(Calendar.MINUTE, 0);
        	horaInic.set(Calendar.SECOND, 0);
        	horaInic.set(Calendar.MILLISECOND, 0);
        	horaFin.set(Calendar.HOUR_OF_DAY, 23);
        	horaFin.set(Calendar.MINUTE, 59);
        	horaFin.set(Calendar.SECOND, 59);
        	horaFin.set(Calendar.MILLISECOND, 0);
            return UtilsBusiness.convertList(daoImportLogInconsistency.getImportLogInconsitencysByCreationDate(horaInic.getTime(),horaFin.getTime()), ImportLogInconsistencyVO.class) ;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getImportLogInconsitencysByCreationDate/ImportLogInconsistencyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getImportLogInconsitencysByCreationDate/ImportLogInconsistencyBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogInconsistencyBusinessBeanLocal#getImportLogInconsitencysByInconsistenState()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ImportLogInconsistencyVO> getImportLogInconsitencysByInconsistenState()
			throws BusinessException {
		log.debug("== Inicia getImportLogInconsitencysByInconsistenState/ImportLogInconsistencyBusinessBean ==");
        try {
        	//Consulta el estado que se va a consultar 
        	InconsistencyStatus inconsistenciaAbiertas = this.daoInconsistencyStatusDAOLocal.getInconsistencyStatusByCode(CodesBusinessEntityEnum.INCONSISTENCY_STATUS_ACTIVE.getCodeEntity());
            return UtilsBusiness.convertList(daoImportLogInconsistency.getImportLogInconsitencysByStatus(inconsistenciaAbiertas.getId()),ImportLogInconsistencyVO.class) ;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getImportLogInconsitencysByInconsistenState/ImportLogInconsistencyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getImportLogInconsitencysByInconsistenState/ImportLogInconsistencyBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogInconsistencyBusinessBeanLocal#registerImportLogInconsitencysClosed(co.com.directv.sdii.model.vo.ImportLogInconsistencyVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void registerImportLogInconsitencysClosed(
			ImportLogInconsistencyVO obj) throws BusinessException {
		log.debug("== Inicia registerImportLogInconsitencysClosed/ImportLogInconsistencyBusinessBean ==");
        try {
        	//Consulta el estado cerrado 
        	InconsistencyStatus inconsistenciaCerrada = this.daoInconsistencyStatusDAOLocal.getInconsistencyStatusByCode(CodesBusinessEntityEnum.INCONSISTENCY_STATUS_INACTIVE.getCodeEntity());
        	obj.setInconsistencyStatus(inconsistenciaCerrada);
            this.updateImportLogInconsistency(obj);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación registerImportLogInconsitencysClosed/ImportLogInconsistencyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina registerImportLogInconsitencysClosed/ImportLogInconsistencyBusinessBean ==");
        }
		
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogInconsistencyBusinessBeanLocal#registerImportLogInconsitencysFinished(co.com.directv.sdii.model.vo.ImportLogInconsistencyVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void registerImportLogInconsitencysFinished(
			ImportLogInconsistencyVO obj) throws BusinessException {
		log.debug("== Inicia registerImportLogInconsitencysFinished/ImportLogInconsistencyBusinessBean ==");
        try {
        	this.updateImportLogInconsistency(obj);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación registerImportLogInconsitencysFinished/ImportLogInconsistencyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina registerImportLogInconsitencysFinished/ImportLogInconsistencyBusinessBean ==");
        }
		
	}
	
	/**
	 * 
	 * Metodo: Envía un correo de notificación cuando se cierra una inconsistencia perteneciente a un registro de importación
	 * @param inconsistency
	 * @param users
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void sendMailToNotifyInconsistencyClosing(ImportLogInconsistencyVO inconsistency, ImportLog importLog, List<User> users) throws BusinessException, DAOServiceException,
			DAOSQLException {

		SendEmailDTO email = new SendEmailDTO();
		for(User usuario : users){
			EmailTypesEnum emailType = EmailTypesEnum.IMPORT_LOG_INCONSISTENCY_CLOSED;
			email.setNewsType(emailType.getEmailTypecode());
			email.setNewsDocument(importLog.getId().toString());
			StringBuilder inconsistencyDesc = new StringBuilder("\n");
			inconsistencyDesc.append(emailType.getDescription());
			inconsistencyDesc.append("\n");
			inconsistencyDesc.append(ApplicationTextEnum.INCONSISTENCIES_CLOSED.getApplicationTextValue()+":");
			inconsistencyDesc.append("\n\n");
			appendInconsistencyInfo(inconsistency, inconsistencyDesc);
			inconsistencyDesc.append("\n");
			email.setNewsObservation(inconsistencyDesc.toString());
			email.setNewsSourceUser(importLog.getUser().getName());
			List<String> recipients = new ArrayList<String>();
			recipients.add(usuario.getEmail());
			email.setRecipient(recipients);
			businessEmailType.sendEmailByEmailTypeCodeAsic(email, importLog.getDealer().getPostalCode().getCity().getState().getCountry().getId());
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public ImportLogInconsistencyResponse getImportLogInconsistenciesByImportLogId( Long importLogId, RequestCollectionInfo requestCollInfo ) throws BusinessException
	{
		log.debug("== Inicia getImportLogInconsistenciesByImportLogId/ImportLogBusinessBean ==");
		try {
			String errorCode    = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
			String errorMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
			UtilsBusiness.assertNotNull(importLogId,errorCode,errorMessage);
			List<ImportLogInconsistencyVO> result =  UtilsBusiness.convertList( daoImportLogInconsistency.getImportLogInconsistenciesByImportLogId(importLogId),ImportLogInconsistencyVO.class);
			fillAditionalIncData(result);
			
			ImportLogInconsistencyResponse response = daoImportLogInconsistency.getImportLogInconsistenciesByImportLogId(importLogId, requestCollInfo);
			List<ImportLogInconsistency> importLogInconsistencyPojo = response.getImportLogInconsistency();
			response.setImportLogInconsistencyVO( UtilsBusiness.convertList(importLogInconsistencyPojo, ImportLogInconsistencyVO.class) );
			
			return response;
		
		} catch (Throwable ex) {
			log.debug("== Error == getImportLogInconsistenciesByImportLogId/ImportLogBusinessBean ==",	ex);
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
	
	/**
	 * Metodo: Agrega a un objeto StringBuilder la información de una inconsistencia para envío de correo
	 * @param inconcistency
	 * @param sb
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	private void appendInconsistencyInfo(ImportLogInconsistencyVO inconcistency, StringBuilder sb) throws DAOServiceException, DAOSQLException
	{
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
		}
		sb.append("\n--------------\n\n");
	}
}
