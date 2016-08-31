package co.com.directv.sdii.ejb.business.stock.impl;

import static co.com.directv.sdii.common.util.UtilsBusiness.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.EmailTypesEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.ReferenceMailSenderLocal;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.common.EmailTypeBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.file.UploadFileBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.DeliveryBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.RefInconsistencyBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceModificationBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceStatusBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReportedElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.SpecialCommentBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.HelperException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.exceptions.ReferenceProcedureException;
import co.com.directv.sdii.model.dto.FilterReferencesToPrintDTO;
import co.com.directv.sdii.model.dto.FilterUploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.dto.InfoToPrintReferencesDTO;
import co.com.directv.sdii.model.dto.MassiveMovementBetweenWareHouseDTO;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.dto.NotSerRefElementItemDTO;
import co.com.directv.sdii.model.dto.ReferenceShipmentDTO;
import co.com.directv.sdii.model.dto.ReferencesFilterDTO;
import co.com.directv.sdii.model.dto.SendEmailDTO;
import co.com.directv.sdii.model.dto.UploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.Element;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.EmployeeCrew;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.Position;
import co.com.directv.sdii.model.pojo.RecordStatus;
import co.com.directv.sdii.model.pojo.RefConfirmation;
import co.com.directv.sdii.model.pojo.RefIncStatus;
import co.com.directv.sdii.model.pojo.RefInconsistency;
import co.com.directv.sdii.model.pojo.RefQuantityControlItem;
import co.com.directv.sdii.model.pojo.RefRecieveData;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.ReferenceElementItem;
import co.com.directv.sdii.model.pojo.ReferenceModType;
import co.com.directv.sdii.model.pojo.ReferenceModification;
import co.com.directv.sdii.model.pojo.ReferenceStatus;
import co.com.directv.sdii.model.pojo.Rol;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.SpecialComment;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.WarehouseType;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInReferencePaginationResponse;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInSelectReferenceToPrintPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.RefQuantityControlItemsResponse;
import co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse;
import co.com.directv.sdii.model.pojo.collection.ReferenceResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SerializedElementInReferencePaginationResponse;
import co.com.directv.sdii.model.vo.DeliveryVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.RefConfirmationVO;
import co.com.directv.sdii.model.vo.RefInconsistencyVO;
import co.com.directv.sdii.model.vo.RefRecieveDataVO;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;
import co.com.directv.sdii.model.vo.ReferenceModificationVO;
import co.com.directv.sdii.model.vo.ReferenceStatusVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.model.vo.ReportedElementVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.SpecialCommentVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.model.vo.WarehouseElementQuantityVO;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.model.vo.WarehouseTypeVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ItemStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.NotSerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.RefConfirmationDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.RefIncStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.RefInconsistencyDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.RefQuantityControlItemDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.RefRecieveDataDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceModTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceModificationDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SpecialCommentDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD Reference
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal
 */
@Stateless(name="ReferenceBusinessBeanLocal",mappedName="ejb/ReferenceBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReferenceBusinessBean extends BusinessBase implements ReferenceBusinessBeanLocal {
    
	@EJB(name="NotSerializedDAOLocal", beanInterface=NotSerializedDAOLocal.class)
	private NotSerializedDAOLocal daoNotSerialized;
	
	@EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
	private SerializedDAOLocal daoSerialized;
	
	@EJB(name="ElementDAOLocal", beanInterface=ElementDAOLocal.class)
	private ElementDAOLocal daoElement;
	
    @EJB(name="ReferenceDAOLocal", beanInterface=ReferenceDAOLocal.class)
    private ReferenceDAOLocal daoReference;
    
    @EJB(name="ReferenceStatusDAOLocal", beanInterface=ReferenceStatusDAOLocal.class)
    private ReferenceStatusDAOLocal daoReferenceStatus;
    
    @EJB(name="WarehouseDAOLocal", beanInterface=WarehouseDAOLocal.class)
    private WarehouseDAOLocal daoWarehouse;
    
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
    
    @EJB(name="ReferenceModTypeDAOLocal", beanInterface=ReferenceModTypeDAOLocal.class)
    private ReferenceModTypeDAOLocal daoReferenceModType;
    
    @EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
    private WarehouseElementDAOLocal daoWarehouseElement;
    
    @EJB(name="ReferenceModificationDAOLocal", beanInterface=ReferenceModificationDAOLocal.class)
    private ReferenceModificationDAOLocal daoReferenceModification;
    
    @EJB(name="ReferenceElementItemDAOLocal", beanInterface=ReferenceElementItemDAOLocal.class)
    private ReferenceElementItemDAOLocal daoReferenceElementItem;
    
    @EJB(name="EmailTypeBusinessBeanLocal", beanInterface=EmailTypeBusinessBeanLocal.class)
    private EmailTypeBusinessBeanLocal businessEmailType;
    
    @EJB(name="EmployeeDAOLocal", beanInterface=EmployeeDAOLocal.class)
    private EmployeeDAOLocal daoEmployee;

    @EJB(name="ItemStatusDAOLocal", beanInterface=ItemStatusDAOLocal.class)
    private ItemStatusDAOLocal daoItemStatus;

    @EJB(name="ReferenceElementItemBusinessBeanLocal", beanInterface=ReferenceElementItemBusinessBeanLocal.class)
    private ReferenceElementItemBusinessBeanLocal businessRefElementItems;
    
    @EJB(name="WarehouseElementBusinessBeanLocal", beanInterface=WarehouseElementBusinessBeanLocal.class)
    private WarehouseElementBusinessBeanLocal businessWhElement;
    
    @EJB(name="DeliveryBusinessBeanLocal", beanInterface=DeliveryBusinessBeanLocal.class)
    private DeliveryBusinessBeanLocal deliveryBusiness;
    
    @EJB(name="ReferenceModificationBusinessBeanLocal", beanInterface=ReferenceModificationBusinessBeanLocal.class)
    private ReferenceModificationBusinessBeanLocal businessReferenceModification;
    
    @EJB(name="RefInconsistencyBusinessBeanLocal", beanInterface=RefInconsistencyBusinessBeanLocal.class)
    private RefInconsistencyBusinessBeanLocal businessRefInconsistency;
    
    @EJB(name="RefInconsistencyDAOLocal", beanInterface=RefInconsistencyDAOLocal.class)
    private RefInconsistencyDAOLocal refInconsistencyDAO;
    
    @EJB(name="SpecialCommentBusinessBeanLocal", beanInterface=SpecialCommentBusinessBeanLocal.class)
    private SpecialCommentBusinessBeanLocal businessSpecialComment;
    
    @EJB(name="RefIncStatusDAOLocal", beanInterface=RefIncStatusDAOLocal.class)
    private RefIncStatusDAOLocal refIncStatusDAO;
    
    @EJB(name="RefConfirmationDAOLocal", beanInterface=RefConfirmationDAOLocal.class)
    private RefConfirmationDAOLocal refConfirmationDAO;
    
    @EJB(name="SpecialCommentDAOLocal", beanInterface=SpecialCommentDAOLocal.class)
    private SpecialCommentDAOLocal daoSpecialCommentDao;
    
    @EJB(name="RefConfirmationDAOLocal", beanInterface=RefConfirmationDAOLocal.class)
    private RefConfirmationDAOLocal daoRefConfirmation;
    
    @EJB (name="WarehouseTypeBusinessBeanLocal", beanInterface=WarehouseTypeBusinessBeanLocal.class)
    private WarehouseTypeBusinessBeanLocal businessWarehouseType;
    
    @EJB (name="WarehouseBusinessBeanLocal", beanInterface=WarehouseBusinessBeanLocal.class)
    private WarehouseBusinessBeanLocal businessWarehouse;
    
    @EJB (name="ReferenceStatusBusinessBeanLocal", beanInterface=ReferenceStatusBusinessBeanLocal.class)
    private ReferenceStatusBusinessBeanLocal businessRefStatus;
    
    @EJB(name="EmployeesCrewDAOLocal", beanInterface=EmployeesCrewDAOLocal.class)
	private EmployeesCrewDAOLocal employCrewDAO;
    
    @EJB(name="RefQuantityControlItemDAOLocal", beanInterface=RefQuantityControlItemDAOLocal.class)
	private RefQuantityControlItemDAOLocal daoRefQtyCtrlItem;
    
    @EJB(name="RefRecieveDataDAOLocal", beanInterface=RefRecieveDataDAOLocal.class)
    private RefRecieveDataDAOLocal refRecieveDataDAO;
    
    @EJB(name="ReportedElementBusinessBeanLocal", beanInterface=ReportedElementBusinessBeanLocal.class)
    private ReportedElementBusinessBeanLocal businessReportedElement;
    
    @EJB(name="ReferenceMailSenderLocal", beanInterface=ReferenceMailSenderLocal.class)
    private ReferenceMailSenderLocal referenceMailSender;
    
    @EJB(name="UploadFileBusinessBeanLocal",beanInterface=UploadFileBusinessBeanLocal.class)
	private UploadFileBusinessBeanLocal businessUploadFile;
    
    @EJB(name="MovementElementBusinessBeanLocal",beanInterface=MovementElementBusinessBeanLocal.class)
	private MovementElementBusinessBeanLocal businessMovementElement;
    
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
    private final static Logger log = UtilsBusiness.getLog4J(ReferenceBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getAllReferences()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ReferenceVO> getAllReferences() throws BusinessException {
        log.debug("== Inicia getAllReferences/ReferenceBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoReference.getAllReferences(), ReferenceVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllReferences/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllReferences/ReferenceBusinessBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ReferenceVO> getAllReferencesAndByCountry(Long country) throws BusinessException {
        log.debug("== Inicia getAllReferencesAndByCountry/ReferenceBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoReference.getAllReferencesAndByCountry(country), ReferenceVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllReferencesAndByCountry/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllReferencesAndByCountry/ReferenceBusinessBean ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ReferenceVO getReferenceByID(Long id) throws BusinessException {
        log.debug("== Inicia getReferenceByID/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Reference objPojo = daoReference.getReferenceByID(id);
            
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            ReferenceVO result = UtilsBusiness.copyObject(ReferenceVO.class, objPojo);
            fillReferenceAditionalData(result);
            WarehouseVO warehouseBySourceWhVO = UtilsBusiness.copyObject(WarehouseVO.class, objPojo.getWarehouseBySourceWh());
            this.businessWarehouse.genWareHouseName(warehouseBySourceWhVO);
            WarehouseVO warehouseByTargetWhVO = UtilsBusiness.copyObject(WarehouseVO.class, objPojo.getWarehouseByTargetWh());
            this.businessWarehouse.genWareHouseName(warehouseByTargetWhVO);
            result.setWhSource(warehouseBySourceWhVO.getWarehouseName());
            result.setWhTarget(warehouseByTargetWhVO.getWarehouseName());
            result.setCreationUserName(result.getCreateUserId().getName());
            RefRecieveDataVO vo = this.getRefRecieveDataByReferenceId(result.getId());
			if( vo != null ){
				result.setRefRecieveData(vo);
			}
            return result;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceByID/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceByID/ReferenceBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ReferenceVO> getReferenceByRNNumber(String rnNumber, String refStatus) throws BusinessException {
        log.debug("== Inicia getReferenceByRNNumber/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(rnNumber, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            List<Reference> objPojo = daoReference.getReferenceByRNNumber(rnNumber, refStatus);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            //ReferenceVO result = UtilsBusiness.copyObject(ReferenceVO.class, objPojo);

            /*fillReferenceAditionalData(result);
            WarehouseVO warehouseBySourceWhVO = UtilsBusiness.copyObject(WarehouseVO.class, objPojo.getWarehouseBySourceWh());
            this.businessWarehouse.genWareHouseName(warehouseBySourceWhVO);
            WarehouseVO warehouseByTargetWhVO = UtilsBusiness.copyObject(WarehouseVO.class, objPojo.getWarehouseByTargetWh());
            this.businessWarehouse.genWareHouseName(warehouseByTargetWhVO);
            result.setWhSource(warehouseBySourceWhVO.getWarehouseName());
            result.setWhTarget(warehouseByTargetWhVO.getWarehouseName());
            result.setCreationUserName(result.getCreateUserId().getName());
            RefRecieveDataVO vo = this.getRefRecieveDataByReferenceId(result.getId());
			if( vo != null ){
				result.setRefRecieveData(vo);
			}*/
            return UtilsBusiness.convertList(objPojo, ReferenceVO.class);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceByID/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceByID/ReferenceBusinessBean ==");
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ReferenceVO getReferenceByIDAndCreatedStatus(Long id) throws BusinessException {
        log.debug("== Inicia getReferenceByIDAndCreatedStatus/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Reference objPojo = daoReference.getReferenceByIDAndCreatedStatus(id);
            
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            ReferenceVO result = UtilsBusiness.copyObject(ReferenceVO.class, objPojo);
            fillReferenceAditionalData(result);
            return result;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceByIDAndCreatedStatus/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceByIDAndCreatedStatus/ReferenceBusinessBean ==");
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ReferenceVO getReferenceByIDAndCreatedStatusAndCreationStatus(Long id) throws BusinessException {
        log.debug("== Inicia getReferenceByIDAndCreatedStatus/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Reference objPojo = daoReference.getReferenceByIDAndCreatedStatusAndCreatedStatus(id);
            
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            ReferenceVO result = UtilsBusiness.copyObject(ReferenceVO.class, objPojo);
            fillReferenceAditionalData(result);
            return result;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceByIDAndCreatedStatus/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceByIDAndCreatedStatus/ReferenceBusinessBean ==");
        }
    }

	private void fillReferenceAditionalData(ReferenceVO reference) throws DAOServiceException, DAOSQLException, BusinessException {
		List<SpecialComment> specialComments = daoSpecialCommentDao.getSpecialCommentsByReferenceId(reference.getId());
		List<SpecialCommentVO>  specialCommentsVo = UtilsBusiness.convertList(specialComments, SpecialCommentVO.class);
		List<DeliveryVO> deliveriesVo = deliveryBusiness.getDeliveriesByReferenceID( reference.getId() );
		reference.setSpecialComments(specialCommentsVo);
		reference.setDeliveries(deliveriesVo);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#createReference(co.com.directv.sdii.model.vo.ReferenceVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createReference(ReferenceVO obj) throws BusinessException {
        log.debug("== Inicia createReference/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Reference objPojo =  UtilsBusiness.copyObject(Reference.class, obj);
            daoReference.createReference(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createReference/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createReference/ReferenceBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#updateReference(co.com.directv.sdii.model.vo.ReferenceVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateReference(ReferenceVO obj) throws BusinessException {
        log.debug("== Inicia updateReference/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Reference objPojo =  UtilsBusiness.copyObject(Reference.class, obj);
            daoReference.updateReference(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateReference/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateReference/ReferenceBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#deleteReference(co.com.directv.sdii.model.vo.ReferenceVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteReference(ReferenceVO obj) throws BusinessException {
        log.debug("== Inicia deleteReference/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Reference objPojo =  UtilsBusiness.copyObject(Reference.class, obj);
            daoReference.deleteReference(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteReference/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteReference/ReferenceBusinessBean ==");
        }
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long deleteReferenceLogic(Long referenceID, Long userDelete) throws BusinessException {
		log.debug("== Inicia deleteReferenceLogic/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(referenceID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Reference reference = daoReference.getReferenceByID( referenceID );
            //Se verifica que existe
            if ( reference != null ){
            	//Se verifica que este en el estado "en creacion"
            	// y en el estado "creada" (Mejora versión 7.0.1)
            	if ( reference.getReferenceStatus().getRefStatusCode().equals( CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity()  )   
            			|| reference.getReferenceStatus().getRefStatusCode().equals( CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() )  ){
            		//Se verifica si tiene elementos asociados
            		ReferenceElementItemsResponse referenceElementItemsResponse = daoReferenceElementItem.getReferenceElementItemsByReferenceID(referenceID, null);
            		if ( referenceElementItemsResponse.getReferenceElementItems().size() == 0 ){
            			reference.setReferenceStatus( daoReferenceStatus.getReferenceByCode( CodesBusinessEntityEnum.REFERENCE_STATUS_DELETED.getCodeEntity() ) );
            			daoReference.updateReference(reference);

            			if (userDelete != null){
                        	this.businessReferenceModification.createReferenceModification( reference.getId(), CodesBusinessEntityEnum.REFERENCE_MODIFICATION_DELETED.getCodeEntity(), userDelete );
            			}
            			
            			return reference.getId();
            		}else{
            			throw new BusinessException(ErrorBusinessMessages.STOCK_IN438.getCode(),ErrorBusinessMessages.STOCK_IN438.getMessage());
            		}
            	}else{
            		throw new BusinessException(ErrorBusinessMessages.STOCK_IN439.getCode(),ErrorBusinessMessages.STOCK_IN439.getMessage());
            	}
            }else{
            	throw new BusinessException(ErrorBusinessMessages.STOCK_IN440.getCode(),ErrorBusinessMessages.STOCK_IN440.getMessage());
            }
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteReferenceLogic/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteReferenceLogic/ReferenceBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#logicDeleteReference(co.com.directv.sdii.model.vo.ReferenceVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void logicDeleteReference(ReferenceVO obj) throws BusinessException{
		 log.debug("== Inicia logicDeleteReference/ReferenceBusinessBean ==");
	        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        try {
	        	//Consulta el objeto Remisión por el ID
	            Reference objPojo =  this.getReferenceByID(obj.getId());
	            
	            //Valida si la remisión se encuentra en estado creada o en creación
	            if(!objPojo.getReferenceStatus().getRefStatusCode().equals(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity()) 
	               && !objPojo.getReferenceStatus().getRefStatusCode().equals(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity())){
	            	//lanza excepción indicando que no se puede eliminar debido a que su estado es diferente a creado o en creación
	            	throw new BusinessException(ErrorBusinessMessages.STOCK_IN357.getCode(),ErrorBusinessMessages.STOCK_IN357.getMessage());
	            }
	            
	            //Valida que no tenga items asociados           
	            ReferenceElementItemsResponse refItemRes = businessRefElementItems.getReferenceElementItemsByReferenceID(objPojo.getId(),null);
	            if(refItemRes.getReferenceElementItemsVO()!=null&&refItemRes.getReferenceElementItemsVO().size()>0){
	            	//lanza excepción indicando que tiene elementos asociados
	            	throw new BusinessException(ErrorBusinessMessages.STOCK_IN356.getCode(),ErrorBusinessMessages.STOCK_IN356.getMessage());
	            }
	            
	            //Se asocia el estado eliminado
	            ReferenceStatus statusDeleted = daoReferenceStatus.getReferenceByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_DELETED.getCodeEntity());
	            objPojo.setReferenceStatus(statusDeleted);
	            daoReference.updateReference(UtilsBusiness.copyObject(Reference.class, objPojo) );
	            
	        } catch(Throwable ex){
	        	log.debug("== Error al tratar de ejecutar la operación logicDeleteReference/ReferenceBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina logicDeleteReference/ReferenceBusinessBean ==");
	        }
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByReferenceStatusAndByCountry(co.com.directv.sdii.model.vo.ReferenceStatusVO, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ReferenceVO> getReferencesByReferenceStatusAndByCountry(
			ReferenceStatusVO refStatus,Long country) throws BusinessException {
		 	log.debug("== Inicia getReferencesByReferenceStatusAndByCountry/ReferenceBusinessBean ==");
	        UtilsBusiness.assertNotNull(refStatus, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        List<ReferenceVO> references;
	        try {
	        	if(refStatus.getRefStatusCode()!=null){
	        		refStatus = UtilsBusiness.copyObject(ReferenceStatusVO.class, this.daoReferenceStatus.getReferenceByCode(refStatus.getRefStatusCode()));
	        	}else{
	        		UtilsBusiness.assertNotNull(refStatus.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        	}
	        	references = UtilsBusiness.convertList(this.daoReference.getReferencesByReferenceStatusAndByCountry(refStatus.getId(),country), ReferenceVO.class);
	        	
	            return references;
	        } catch(Throwable ex){
	        	log.debug("== Error al tratar de ejecutar la operación getReferencesByReferenceStatusAndByCountry/ReferenceBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getReferencesByReferenceStatusAndByCountry/ReferenceBusinessBean ==");
	        }
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByReferenceStatusAndWhAndByCountry(co.com.directv.sdii.model.vo.ReferenceStatusVO, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ReferenceVO> getReferencesByReferenceStatusAndWhAndByCountry(
			ReferenceStatusVO refStatus, WarehouseVO whSource,
			WarehouseVO whTarget,Long country) throws BusinessException {
		log.debug("== Inicia getReferencesByReferenceStatusAndWhAndByCountry/ReferenceBusinessBean ==");
        List<ReferenceVO> references = new ArrayList<ReferenceVO>();
        try {
        	if(refStatus==null){
        		if(whSource==null||whTarget==null){
        			if(whSource==null){
        				UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        				references = UtilsBusiness.convertList(this.daoReference.getReferencesByTargetWareHouse(whTarget.getId()), ReferenceVO.class);
        			}else{
        				UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        				references = UtilsBusiness.convertList(this.daoReference.getReferencesBySourceWareHouse(whSource.getId()), ReferenceVO.class);
        			}
        		}else{
        			UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			references = this.getReferencesBySourceAndTargetWareHouseAndByCountry(whSource, whTarget,country);
        		}
        	}else{
        		UtilsBusiness.assertNotNull(refStatus.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		if(whSource==null||whTarget==null){
        			if(whSource==null&&whTarget==null){
        				references = this.getReferencesByReferenceStatusAndByCountry(refStatus,country);
        			}else{
	        			if(whTarget==null){
	        				UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        				references = UtilsBusiness.convertList(this.daoReference.getReferencesByReferenceStatusAndSourceWh(refStatus.getId(),whSource.getId()), ReferenceVO.class);
	        			}else{
	        				UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        				references = UtilsBusiness.convertList(this.daoReference.getReferencesByReferenceStatusAndTargerWh(refStatus.getId(),whTarget.getId()), ReferenceVO.class);
	        			}
        			}
        		}else{
        			UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			references = UtilsBusiness.convertList(this.daoReference.getReferencesByReferenceStatusAndWh(refStatus.getId(),whSource.getId(),whTarget.getId()), ReferenceVO.class);
        		}   
        	}
            return references;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesByReferenceStatusAndWhAndByCountry/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesByReferenceStatusAndWhAndByCountry/ReferenceBusinessBean ==");
        }
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesBySourceAndTargetWareHouseAndByCountry(co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ReferenceVO> getReferencesBySourceAndTargetWareHouseAndByCountry(
			WarehouseVO whSource, WarehouseVO whTarget,Long country)
			throws BusinessException {
		log.debug("== Inicia getReferencesBySourceAndTargetWareHouseAndByCountry/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(whSource, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(whTarget, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        List<ReferenceVO> references;
        try {
        	if(whSource.getWhCode()!=null){
        		whSource = UtilsBusiness.copyObject(WarehouseVO.class, this.daoWarehouse.getWarehouseByCodeAndByCountry(whSource.getWhCode(),country));
        	}else{
        		UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	if(whTarget.getWhCode()!=null){
        		whTarget = UtilsBusiness.copyObject(WarehouseVO.class, this.daoWarehouse.getWarehouseByCodeAndByCountry(whTarget.getWhCode(),country));
        	}else{
        		UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	references = UtilsBusiness.convertList(this.daoReference.getReferencesBySourceAndTargetWareHouse(whSource.getId(),whTarget.getId()), ReferenceVO.class);
        	
            return references;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesBySourceAndTargetWareHouseAndByCountry/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesBySourceAndTargetWareHouseAndByCountry/ReferenceBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesBySourceAndTargetWareHouseAndByCountry(co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ReferenceVO> getReferencesBySourceAndTargetWareHouseAndByCountryAndCreatedStatus(
			WarehouseVO whSource, WarehouseVO whTarget,Long country)
			throws BusinessException {
		log.debug("== Inicia getReferencesBySourceAndTargetWareHouseAndByCountry/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(whSource, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(whTarget, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        List<ReferenceVO> references;
        try {
        	if(whSource.getWhCode()!=null){
        		whSource = UtilsBusiness.copyObject(WarehouseVO.class, this.daoWarehouse.getWarehouseByCodeAndByCountry(whSource.getWhCode(),country));
        	}else{
        		UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	if(whTarget.getWhCode()!=null){
        		whTarget = UtilsBusiness.copyObject(WarehouseVO.class, this.daoWarehouse.getWarehouseByCodeAndByCountry(whTarget.getWhCode(),country));
        	}else{
        		UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	references = UtilsBusiness.convertList(this.daoReference.getReferencesBySourceAndTargetWareHouseAndCreatedStatus(whSource.getId(),whTarget.getId()), ReferenceVO.class);
        	
            return references;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesBySourceAndTargetWareHouseAndByCountry/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesBySourceAndTargetWareHouseAndByCountry/ReferenceBusinessBean ==");
        }
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ReferenceVO> getReferencesBySourceAndTargetWareHouseAndByCountryAndCreatedStatusAndCreationStatus(
			WarehouseVO whSource, WarehouseVO whTarget,Long country)
			throws BusinessException {
		log.debug("== Inicia getReferencesBySourceAndTargetWareHouseAndByCountryAndCreatedStatusAndCreationStatus/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(whSource, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(whTarget, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        List<ReferenceVO> references;
        try {
        	if(whSource.getWhCode()!=null){
        		whSource = UtilsBusiness.copyObject(WarehouseVO.class, this.daoWarehouse.getWarehouseByCodeAndByCountry(whSource.getWhCode(),country));
        	}else{
        		UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	if(whTarget.getWhCode()!=null){
        		whTarget = UtilsBusiness.copyObject(WarehouseVO.class, this.daoWarehouse.getWarehouseByCodeAndByCountry(whTarget.getWhCode(),country));
        	}else{
        		UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	references = UtilsBusiness.convertList(this.daoReference.getReferencesBySourceAndTargetWareHouseAndCreatedStatusAndCreationStatus(whSource.getId(),whTarget.getId()), ReferenceVO.class);
        	
            return references;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesBySourceAndTargetWareHouseAndByCountryAndCreatedStatusAndCreationStatus/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesBySourceAndTargetWareHouseAndByCountryAndCreatedStatusAndCreationStatus/ReferenceBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByIdOrSourceWhOrTargetWh(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ReferenceVO> getReferencesByIdOrSourceWhOrTargetWh(
			Long referenceId, Long sourceWhId, Long targetWhId, Date referenceCreationDate, String recorderUserName)
			throws BusinessException {
		log.debug("== Inicia getReferencesByIdOrSourceWhOrTargetWh/ReferenceBusinessBean ==");
        
        try {
        	List<Reference> referencesPojo = new ArrayList<Reference>();
        	
        	if(referenceId != null && referenceId.longValue() > 0){
        		Reference reference = daoReference.getReferenceByID(referenceId);
        		referencesPojo.add(reference);
        	}else{
        		referencesPojo = this.daoReference.getReferencesBySourceAndTargetWareHouseAndCreatDateAndRecUserName(sourceWhId, targetWhId, referenceCreationDate, recorderUserName);
        	}
        	List<ReferenceVO> references = UtilsBusiness.convertList(referencesPojo, ReferenceVO.class);
        	
            return references;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesBySourceAndTargetWareHouse/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesByIdOrSourceWhOrTargetWh/ReferenceBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getAllReferencesByIdSourceTargetWareHouseStatus(co.com.directv.sdii.model.dto.ReferencesFilterToPrintDTO,co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public NotSerializedElementInSelectReferenceToPrintPaginationResponse getAllReferencesByIdSourceTargetWareHouseStatus(
			FilterReferencesToPrintDTO referencesFilterToPrintDTO, RequestCollectionInfo requestCollectionInfo )
			throws BusinessException{
				log.debug("== Inicia getAllReferencesByIdSourceTargetWareHouseStatus/ReferenceBusinessBean ==");
		        
		        try {
		        	
		        	return this.daoReference.getAllReferencesByIdSourceTargetWareHouseStatus(referencesFilterToPrintDTO,requestCollectionInfo);
		        	
		        } catch(Throwable ex){
		        	log.debug("== Error al tratar de ejecutar la operación getAllReferencesByIdSourceTargetWareHouseStatus/ReferenceBusinessBean ==");
		        	throw this.manageException(ex);
		        } finally {
		            log.debug("== Termina getAllReferencesByIdSourceTargetWareHouseStatus/ReferenceBusinessBean ==");
		        }
			}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getInfoToPrintReferencesById(java.lang.Long)
	 */
	public InfoToPrintReferencesDTO getInfoToPrintReferencesById(Long referenceId)throws BusinessException{
		log.debug("== Inicia getInfoToPrintReferencesById/ReferenceBusinessBean ==");
        
        try {
        	
        	return this.daoReference.getInfoToPrintReferencesById(referenceId);
        	
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getInfoToPrintReferencesById/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getInfoToPrintReferencesById/ReferenceBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesBySourceWareHouseAndRefStatusInconsistentAndByCountry(co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ReferenceVO> getReferencesBySourceWareHouseAndRefStatusInconsistentAndByCountry(
			WarehouseVO whSource,Long country) throws BusinessException {
		log.debug("== Inicia getReferencesBySourceWareHouseAndRefStatusInconsistentAndByCountry/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(whSource, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        List<ReferenceVO> references;
        try {
        	if(whSource.getWhCode()!=null){
        		whSource = UtilsBusiness.copyObject(WarehouseVO.class, this.daoWarehouse.getWarehouseByCodeAndByCountry(whSource.getWhCode(),country));
        	}else{
        		UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	ReferenceStatus	refStatus = this.daoReferenceStatus.getReferenceByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_INCONSISTENCY_PROCESS.getCodeEntity());
        	UtilsBusiness.assertNotNull(refStatus, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	references = UtilsBusiness.convertList(this.daoReference.getReferencesByReferenceStatusAndSourceWh(refStatus.getId(),whSource.getId()), ReferenceVO.class);
            return references;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesBySourceWareHouseAndRefStatusInconsistentAndByCountry/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesBySourceWareHouseAndRefStatusInconsistentAndByCountry/ReferenceBusinessBean ==");
        }
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesBySourceWareHouseAndByCountry(co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ReferenceVO> getReferencesBySourceWareHouseAndByCountry(WarehouseVO whSource,Long country)
			throws BusinessException {
		log.debug("== Inicia getReferencesBySourceWareHouseAndByCountry/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(whSource, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        List<ReferenceVO> references;
        try {
        	if(whSource.getWhCode()!=null){
        		whSource = UtilsBusiness.copyObject(WarehouseVO.class, this.daoWarehouse.getWarehouseByCodeAndByCountry(whSource.getWhCode(),country));
        	}else{
        		UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	references = UtilsBusiness.convertList(this.daoReference.getReferencesBySourceWareHouse(whSource.getId()), ReferenceVO.class);
        	
            return references;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesBySourceWareHouseAndByCountry/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesBySourceWareHouseAndByCountry/ReferenceBusinessBean ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByTargetWareHouseAndByCountry(co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ReferenceVO> getReferencesByTargetWareHouseAndByCountry(WarehouseVO whTarget,Long country)
			throws BusinessException {
		log.debug("== Inicia getReferencesBySourceAndTargetWareHouse/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(whTarget, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        List<ReferenceVO> references;
        try {
        	if(whTarget.getWhCode()!=null){
        		whTarget = UtilsBusiness.copyObject(WarehouseVO.class, this.daoWarehouse.getWarehouseByCodeAndByCountry(whTarget.getWhCode(),country));
        	}else{
        		UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	references = UtilsBusiness.convertList(this.daoReference.getReferencesByTargetWareHouse(whTarget.getId()), ReferenceVO.class);
        	
            return references;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesBySourceAndTargetWareHouse/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesBySourceAndTargetWareHouse/ReferenceBusinessBean ==");
        }
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#registerReferenceShipment(co.com.directv.sdii.model.dto.ReferenceShipmentDTO, co.com.directv.sdii.model.vo.UserVO)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void registerReferenceShipment( ReferenceShipmentDTO referencesShipment, UserVO user ) throws BusinessException {

		log.debug("== Inicia registerReferenceShipment/ReferenceBusinessBean ==");
		try {

			String errorCode    = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
			String errorMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
			String referenceModificationCode = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_SHIPMENT.getCodeEntity();
			UtilsBusiness.assertNotNull(user, errorCode,errorMessage);
			UtilsBusiness.assertNotNull(user.getName(), errorCode,errorMessage);
			UtilsBusiness.assertNotNull(user.getIdNumber() , errorCode,errorMessage);
			UtilsBusiness.assertNotNull(referencesShipment, errorCode,errorMessage);
			ReferenceVO reference = referencesShipment.getReference();
			UtilsBusiness.assertNotNull(reference, errorCode,errorMessage);
			UtilsBusiness.assertNotNull(reference.getId(), errorCode,errorMessage);
			UtilsBusiness.assertNotNull(reference.getCountryCodeId(), errorCode,errorMessage);
			UtilsBusiness.assertNotNull(reference.getWarehouseBySourceWh(), errorCode,errorMessage);
			UtilsBusiness.assertNotNull(reference.getWarehouseBySourceWh().getId(), errorCode,errorMessage);
			UtilsBusiness.assertNotNull(reference.getTransportCompany(), errorCode,errorMessage);
			UtilsBusiness.assertNotNull(reference.getTransportCompany().getId(), errorCode,errorMessage);
			UtilsBusiness.assertNotNull(reference.getTransportGuide(), errorCode,errorMessage);
			UtilsBusiness.assertNotNull(reference.getDriverName(), errorCode,errorMessage);
			List<SpecialCommentVO> specialComments = reference.getSpecialComments();
			UtilsBusiness.assertNotNull(reference.getVehiclePlate(), errorCode,errorMessage);
			UtilsBusiness.assertNotNull(reference.getVolume(), errorCode,errorMessage);
			
			Reference referencePojo = daoReference.getReferenceByID(reference.getId());
			UtilsBusiness.assertNotNull(referencePojo, ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage() + " No se encontró la remisión con el id: " + reference.getId());

			/** Se notifica del cambio de estado de la remision */
			buildReferenceModification(reference, user,referenceModificationCode );

			SendEmailDTO email = prepareSendEmailDTOToReferencesShipment(user, referencePojo);
			List<String> recipientsWHSource = new ArrayList<String>();
			List<String> recipientsWHTarget = new ArrayList<String>();

			//Se realizan los movimientos de los elementos a entre las bodegas y la notificación a IBS de dichos movimientos
			boolean isShipment = true;
			moveElementsInWarehouses(referencePojo, isShipment);

			// informar a los responsables de las bodegas origen y destino sobre el env�o de la remisi�n  

			if( referencePojo.getWarehouseBySourceWh().getResponsibleEmail()!=null )
				recipientsWHSource.add( referencePojo.getWarehouseBySourceWh().getResponsibleEmail() );

			if( referencePojo.getSourceTransitWh() != null && referencePojo.getSourceTransitWh().getResponsibleEmail()!=null ) 
				recipientsWHTarget.add( referencePojo.getSourceTransitWh().getResponsibleEmail() );

			if( recipientsWHSource.size()>0 ){
				email.setRecipient( recipientsWHSource );
				businessEmailType.sendEmailByEmailTypeCode( email, referencePojo.getCountryCodeId().getId());
			}

			if(recipientsWHTarget.size()>0){
				email.setRecipient(recipientsWHTarget);
				email.setNewsType(EmailTypesEnum.REFERENCES_ELEMENTS_HAS_BEEN_RECEIVED.getEmailTypecode());
				businessEmailType.sendEmailByEmailTypeCode(email,reference.getCountryCodeId().getId());
			}

			/* cambiar el estado de la remision y de sus elementos a enviado en la capa de base de datos */
			ReferenceStatus statusSended     = daoReferenceStatus.getReferenceByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity());
			daoReference.registerReferenceShipment(referencesShipment.getReference(),statusSended);
			

			//Creando los comentarios especiales
			createSpecialCommnets(specialComments, referencePojo);

			ItemStatus status = new ItemStatus();
			status.setStatusCode( CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity() );
			status.setId( CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getIdEntity( ItemStatus.class.getName() ) );

			RequestCollectionInfo requestCollInfo = null;
			ReferenceElementItemsResponse response = daoReferenceElementItem.getReferenceElementItemsByReferenceID(referencePojo.getId(), requestCollInfo);
			List<ReferenceElementItem> listToUpdate = response.getReferenceElementItems();
			for(ReferenceElementItem item:listToUpdate){
				item.setItemStatus(status);
	    	    daoReferenceElementItem.updateElementItemStatusToReferenceShipment(item);
	    	}      			

		}catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operacion registerReferenceShipment/ReferenceBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina registerReferenceShipment/ReferenceBusinessBean ==");
		}
	}
	
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    private SendEmailDTO prepareSendEmailDTOToReferencesShipment( UserVO user , Reference reference)throws BusinessException
    {
    	SendEmailDTO email = new SendEmailDTO();
        
    	email.setNewsType( EmailTypesEnum.REFERENCE_SHIPMENT.getEmailTypecode() );
        email.setNewsDocument( user.getIdNumber() );
        email.setNewsObservation( ApplicationTextEnum.REFERRAL_SHIPPING_RECORD.getApplicationTextValue()+" # " + reference.getId());
        email.setNewsSourceUser( user.getName() );
        
        return email;
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#updateReferenceAndElements(co.com.directv.sdii.model.vo.ReferenceVO, java.util.List, boolean, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateReferenceAndElements(ReferenceVO reference,
			List<ReferenceElementItemVO> referenceElements, boolean isFinished, Long userId)
			throws BusinessException {
		log.debug("== Inicia updateReferenceAndElements/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(reference, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(referenceElements, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        if(referenceElements.isEmpty()){
        	log.error("No se han especificado elementos para la remisión con id: " + reference.getId());
        	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        }
        
        try {
        	/*
        	 * Para actualizar la información de una remisión:
        	 * 1. Validar los campos obligatorios de la remisión
        	 * 2. Validar que la cantidad en bodega origen de la remisión no sea superada 
        	 * 	por la cantidad de cada uno de los elementos de la remisión y además en caso que
        	 * el usuario haya marcado los objetos como para remover, se borrarán de la remisión
        	 * 3. Si el usuario selecciona isFinished en true:
        	 * 		a. Se cambia el estado de la remisión a "enviado"
        	 * 		b. Se invoca el caso de uso INV-08 para informar a los responsables de las bodegas que una remisión ha sido creada
        	 * 4. Si el usuario selecciona isFinished en false:
        	 * 		a. se cambiará el estado de la wo a "creado",
        	 * 5. Se registra el campo fecha de terminación de creación con la fecha actual
        	 * 6. Se registra el usuario de creación de la remisión
        	 * 7. Se crea un registro en la tabla reference_modifications
        	 * 8. Se crea las nuevo specialComments asociados a la remision
        	 */
        	
        	//Realizando 1:
        	if(! BusinessRuleValidationManager.getInstance().isValid(reference)){
        		log.error("No se encontraron los datos obligatorios para actualizar una remisión");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	if(reference.getId() == null || reference.getId().longValue() <= 0){
        		log.error("Se trató de actualizar una remisión con el identificador nulo, cero o menor que cero");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	Reference referencePojo = daoReference.getReferenceByID(reference.getId());
        	
        	//Realizando 2:
        	validateElementQuantitiesInWh(referenceElements, referencePojo.getWarehouseBySourceWh().getId());
        	
        	updateReferenceElementItems(referenceElements, referencePojo);
        	
        	String newRefStatusCode = null;
        	//Realizando 3:
        	if(isFinished){
        		newRefStatusCode = CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity();
        		//jjimenezh 2010-08-09 Invocar el caso de uso INV - 08 Informar a responsable de remisión
        		User user = daoUser.getUserById(userId);
        		UtilsBusiness.assertNotEmpty(user, ErrorBusinessMessages.USER_NOT_EXIST.getCode(), ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        		UserVO userVo = UtilsBusiness.copyObject(UserVO.class, user);
        		sendEmail(userVo, referencePojo, EmailTypesEnum.REFERENCE_CREATION.getEmailTypecode(), ApplicationTextEnum.CREATED_REFERRAL_CODE.getApplicationTextValue()+": " + reference.getId() );
      		//Realizando 4:
        	}else{
        		newRefStatusCode = CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity();
        	}
        	
        	//Realizando 5 y 6:
        	ReferenceStatus newRefStatus = daoReferenceStatus.getReferenceByCode(newRefStatusCode);
        	UtilsBusiness.assertNotNull(newRefStatus, ErrorBusinessMessages.REFERENCE_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.REFERENCE_STATUS_DOES_NOT_EXIST.getMessage());
        	
        	referencePojo.setReferenceStatus(newRefStatus);
        	
        	User creationUser = daoUser.getUserById(userId);
        	daoReference.updateReference(referencePojo);
        	
        	//Realizando 7:
        	String refModTypeCode = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_TYPE_CREATION_PROCESS.getCodeEntity();
        	buildReferenceModification(referencePojo, creationUser, refModTypeCode);
        	
        	//Realizando 8:
        	if( reference.getSpecialComments() != null && !reference.getSpecialComments().isEmpty() ){
        		createSpecialCommnets(reference.getSpecialComments(), referencePojo);
        	}
        	
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateReferenceAndElements/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateReferenceAndElements/ReferenceBusinessBean ==");
        }
	}
	
	private void updateReferenceElementItems(
			List<ReferenceElementItemVO> referenceElements,
			Reference referencePojo) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
		ReferenceElementItem pojo = null; 
		for (ReferenceElementItemVO referenceElementItemVO : referenceElements) {
			if( referenceElementItemVO.isRemoveElement()){
				//No se borran los elementos porque ya fueron borrados en la validación de cantidades en bodega
				continue;
			}
			pojo = UtilsBusiness.copyObject(ReferenceElementItem.class, referenceElementItemVO);
			//Si se agregó u nuevo elemento:
			if(referenceElementItemVO.getId() == null || referenceElementItemVO.getId().longValue() <= 0){
				ItemStatus itemStatus = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_CREATED.getCodeEntity());
				pojo.setItemStatus(itemStatus);
				daoReferenceElementItem.createReferenceElementItem(pojo);
			//Se actualiza la cantidad requerida
			}else{
				pojo = daoReferenceElementItem.getReferenceElementItemByID(referenceElementItemVO.getId());
				pojo.setRefQuantity(referenceElementItemVO.getRefQuantity());
				daoReferenceElementItem.updateReferenceElementItem(pojo);
			}
		}
	}


	/**
	 * Metodo: Valida la cantidad de elementos en bodega y borra de la remisión los elementos
	 * que hayan sido marcados como para remover
	 * @param referenceElements
	 * @param warehouseId
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException <tipo> <descripcion>
	 * @throws PropertiesException 
	 * @author jjimenezh
	 */
	private void validateElementQuantitiesInWh(List<ReferenceElementItemVO> referenceElements, Long warehouseId) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException{
		
		ReferenceElementItem refElement;
		WarehouseElementQuantityVO whElementQty;
		Double requiredQty = 0D;
		List<Long> objectsToRemove = new ArrayList<Long>();
		Long modelId = 0L, elementTypeId = 0L;
		Element element;
		for (ReferenceElementItemVO refElementVo : referenceElements) {
			//Si el elemento no fué removido por el usuario
			if(! refElementVo.isRemoveElement()){
				
				if(log.isDebugEnabled()){
					log.debug("Consultando la cantidad de elemento con id: " + refElementVo.getElement().getId());
				}
				
				element = daoElement.getElementByID(refElementVo.getElement().getId());
				UtilsBusiness.assertNotNull(element, ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
				refElementVo.setElement(element);
				
				modelId = element.getElementType().getElementModel().getId();
				elementTypeId = element.getElementType().getId();
				
				//Si el elemento a crear es nuevo:
				if(refElementVo.getId() == null || refElementVo.getId().longValue() <= 0){
					refElementVo.setId(null);
					requiredQty = refElementVo.getRefQuantity();
				}else{
					refElement = daoReferenceElementItem.getReferenceElementItemByID(refElementVo.getId());
					requiredQty = refElementVo.isUseCustomQuantity() ? refElementVo.getRefQuantity() :  refElement.getRefQuantity();
				}
				whElementQty = businessWhElement.getCurrentQuantityInWarehouseByElementType(warehouseId, elementTypeId, modelId);
				
				if(whElementQty == null && log.isDebugEnabled()){
					log.debug("No se han encontrado existencias del tipo de elemento: \""+elementTypeId+"\"  y modelo solicitados: \""+modelId+"\" en la bodega especificada: \"" +warehouseId+ "\" ");
				}
				UtilsBusiness.assertNotNull(whElementQty, ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getMessage());
				
				if(whElementQty.getCurrentQuantity().doubleValue() < requiredQty){
					String message = "Cantidad de elementos del tipo id: \""+elementTypeId+"\" modelo id: \""+modelId+"\" en la bodega id: \""+warehouseId+"\" insuficiente para la remisión, del tipo ";
					log.error(message);
					throw new BusinessException(ErrorBusinessMessages.ACTUAL_QUANTITY_LOWER_THAN_REQUIRED_MOVEMENT.getCode(), ErrorBusinessMessages.ACTUAL_QUANTITY_LOWER_THAN_REQUIRED_MOVEMENT.getMessage() + message);
				}
			//Si el usuario marco los elementos para borrarlos:
			}else{
				if( refElementVo.getId() != null ){
					//Si el elemento marcado para remover tiene estado confirmado parcial o recepcionado, no podrá ser borrado
					validateRefElementItemStatus2Remove(refElementVo.getId());
					objectsToRemove.add(refElementVo.getId());
				}
			}
		}
		
		if(! objectsToRemove.isEmpty()){
			this.deleteReferenceElementItemInReference(objectsToRemove);
		}
	}


	/**
	 * Metodo: Valida que un item de una remisión no puede ser eliminado si se encuentra en estado:<br>
	 * parcialmente confirmado<br>
	 * recepcionado<br>
	 * enviado<br>
	 * @param refElementItemId Identificador del item de remisión a se validado
	 * @author jjimenezh
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws BusinessException 
	 * @throws PropertiesException 
	 */
	private void validateRefElementItemStatus2Remove(Long refElementItemId) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException {
		String itemStatusCode = daoReferenceElementItem.getItemStatusCodeByRefElementItemId(refElementItemId);
		if(CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getCodeEntity().equalsIgnoreCase(itemStatusCode)){
			String message = " No se puede eliminar un item de remisión que ha sido confirmado parcialmente el estado del item de remisión con id: " + refElementItemId + " tiene estado de item con código: " + itemStatusCode;
			log.error(message);
			throw new BusinessException(ErrorBusinessMessages.REFERENCE_ELEMENT_ITEM_REMOVE_ERROR.getCode(), ErrorBusinessMessages.REFERENCE_ELEMENT_ITEM_REMOVE_ERROR.getMessage() + message);
		}
	}

	/**
	 * Metodo: Permite crear una modificacion de una referencia 
	 * @param referencePojo referencia que se modifico
	 * @param creationUser usuario que realizo la modificaion
	 * @param refModTypeCode codigo de la modificacion realizada
	 * @throws BusinessException En caso de error al crear la modificacion de una referencia
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void buildReferenceModification(Reference referencePojo,
			User creationUser, String refModTypeCode) throws BusinessException {
		log.debug("== Inicia buildReferenceModification/ReferenceBusinessBean ==");
		try {
			ReferenceModType refModType = daoReferenceModType.getReferenceModTypeByCode(refModTypeCode);
			ReferenceModification referenceModification = new ReferenceModification();
			referenceModification.setModificationDate(UtilsBusiness.getCurrentTimeZoneDateByUserId(creationUser.getId(), daoUser));
			referenceModification.setReference(referencePojo);
			referenceModification.setReferenceModType(refModType);
			referenceModification.setUserModification(new User());
			referenceModification.getUserModification().setId(creationUser.getId());
			daoReferenceModification.createReferenceModification(referenceModification);
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación buildReferenceModification/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina buildReferenceModification/ReferenceBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#validateElementsInWHQuantities(java.util.List<co.com.directv.sdii.model.vo.ReferenceElementItemVO>, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void validateElementsInWHQuantities(List<ReferenceElementItemVO> referencesElementItems , Long sourceWareHouseId) throws BusinessException{
		log.debug("== Inicia validateElementsInWHQuantities/ReferenceBusinessBean ==");
		try{
			for(ReferenceElementItemVO elementItemVO : referencesElementItems){
				Double quantityInWarehouse = 0D;
				if(elementItemVO.getElement().getIsSerialized().compareTo(CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity()) == 0 ){
					quantityInWarehouse = daoWarehouseElement.getCurrentQuantityInWarehouseByElementType(sourceWareHouseId, elementItemVO.getElement().getElementType().getId(),elementItemVO.getElement().getElementType().getElementModel().getId());
				} else if(elementItemVO.getElement().getIsSerialized().compareTo(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity()) == 0 ){
					List<WarehouseElement> serializedList =  daoWarehouseElement.getWhElementByElementIdAndWhSource(sourceWareHouseId, CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity(), elementItemVO.getElement().getId());
					if( serializedList != null )
						quantityInWarehouse = new Double( String.valueOf( serializedList.size() ) );
					else
						quantityInWarehouse = 0D;
					//quantityInWarehouse = daoWarehouseElement.getCurrentQuantityInWarehouseByElementType(sourceWareHouseId, elementItemVO.getElement().getElementType().getId(),elementItemVO.getElement().getElementModel().getId());
				}
				if(quantityInWarehouse == null || elementItemVO.getRefQuantity() > quantityInWarehouse){
					log.debug("== Error al tratar de ejecutar la operación validateElementsInWHQuantities/WarehouseElementBusinessBean ==");
					throw new BusinessException(ErrorBusinessMessages.ACTUAL_QUANTITY_LOWER_THAN_REQUIRED_MOVEMENT.getCode(),ErrorBusinessMessages.ACTUAL_QUANTITY_LOWER_THAN_REQUIRED_MOVEMENT.getMessage());
				}
			}
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación validateElementsInWHQuantities/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina validateElementsInWHQuantities/ReferenceBusinessBean ==");
        }
	}
	
	
	/**
	 * Metodo: Regresa un <b>posible</b> conjunto de remisiones definidas por un fitro basado
	 * en el nm de remision y el codigo de pais
	 * @param referenceId Long - El identificador de la remision
	 * @param countryCode Long - El codigo del pais
	 * @throws BusinessException en caso de error al tratar de listar las remisiones	 
	 * @author garciniegas 
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ReferenceVO>getReferenceByIdAndCountryCode( Long referenceId,Long countryCode )throws BusinessException
    {
    	log.debug("== Inicia getReferenceByIdAndCountryCode/ReferenceBusinessBean ==");
		try{
			
			 List<Reference>partialList = daoReference.getReferenceByIdAndCountryCode( referenceId, countryCode );
			 return UtilsBusiness.convertList( partialList ,ReferenceVO.class );
			
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getReferenceByIdAndCountryCode/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceByIdAndCountryCode/ReferenceBusinessBean ==");
		}
    }
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#registerReferenceDeliveryAndElementMovement(java.lang.Long, java.util.Date, java.lang.Long, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unused")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void registerReferenceDeliveryAndElementMovement(Long referenceId,
			Date deliveryDate, Long targetEmployeeId, String comments, Double sentUnits, Long userId)
			throws BusinessException {
		log.debug("== Inicia registerReferenceDeliveryAndElementMovement/ReferenceBusinessBean ==");
		try{
			
			UtilsBusiness.assertNotNull(referenceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(deliveryDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(targetEmployeeId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(comments, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			/*
			 * Para ejecutar el caso de uso:
			 * 1. cambiar el estado de la remisión a "recepcionada"
			 * 2. 
			 */
			Reference reference = daoReference.getReferenceByID(referenceId);
			UtilsBusiness.assertNotNull(reference, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			Employee employee = daoEmployee.getEmployeeByID(targetEmployeeId);
			UtilsBusiness.assertNotNull(employee, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			deliveryDate = UtilsBusiness.dateTo12am(deliveryDate);
			Date now = new Date();
			now = UtilsBusiness.dateTo12am(now);
			
			if( now.compareTo(deliveryDate) < 0){
				log.error("No se puede registrar una remisión como entregada en una fecha diferente al día actual");
				throw new BusinessException(ErrorBusinessMessages.INVALID_DATE.getCode(),ErrorBusinessMessages.INVALID_DATE.getMessage());
			}
			
			//Obteniendo el estado de la remisión a recepcionada:
			ReferenceStatus refStatus = daoReferenceStatus.getReferenceByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_RECEPTED.getCodeEntity());
			UtilsBusiness.assertNotNull(refStatus, ErrorBusinessMessages.REFERENCE_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.REFERENCE_STATUS_DOES_NOT_EXIST.getMessage());
			
			reference.setReferenceStatus(refStatus);
			reference.setShippingDate(deliveryDate);
			reference.setDeliveryDate(deliveryDate);
			reference.setDriverName(employee.getLastName() + " " + employee.getFirstName());
			if(sentUnits != null && sentUnits > 0d){
				reference.setSendUnits(sentUnits);
			}
			
			daoReference.updateReference(reference);
			
			//cambiando el estado de todos los elementos a recepcionado
			ItemStatus statusReceived = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity()); 
			businessRefElementItems.changeStatusOfReferenceElements(referenceId, statusReceived.getId());
			
			//Invocando los casos de uso INV 12, 14, 15 y 16
			boolean isShipment = false;//Se asigna falso para informar al método que el movimiento es por registro de recibido de elementos
			moveElementsInWarehouses(reference, isShipment);
			
			//jjimenezh 2010-08-12 invocar el caso de uso INV - 08 Enviar correo electrónico a un destinatario indicando algún tipo de novedad
			User user = daoUser.getUserById(userId);
    		UtilsBusiness.assertNotEmpty(user, ErrorBusinessMessages.USER_NOT_EXIST.getCode(), ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
    		UserVO userVo = UtilsBusiness.copyObject(UserVO.class, user);
    		
    		//Registrando los comentarios especiales:
    		SpecialComment specialComment = reportReferenceSpecialComment(userId, comments, reference);
    		
			sendEmail(userVo, reference, EmailTypesEnum.REFERENCE_SHIPMENT.getEmailTypecode(), ApplicationTextEnum.CODE_REFERRAL.getApplicationTextValue()+": " + reference.getId() + " "+ApplicationTextEnum.SENT.getApplicationTextValue() );
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación registerReferenceDeliveryAndElementMovement/ReferenceBusinessBean ==");
        	if (ex instanceof  BusinessException){			
    			if( ((BusinessException) ex).getMessageCode().equalsIgnoreCase( ErrorBusinessMessages.NOT_ELEMENTS_EXIST.getCode() ) ){
    				log.debug("== No hay elementos en la bodega para registrar envio de la remision ==");
    				throw new BusinessException( ErrorBusinessMessages.CANT_CONFIRM_REFERENCE.getCode() , ErrorBusinessMessages.CANT_CONFIRM_REFERENCE.getMessage());
    			}
    		}
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina registerReferenceDeliveryAndElementMovement/ReferenceBusinessBean ==");
        }
	}

	/**
	 * Metodo: Crea un comentario especial sobre una remisión
	 * @param comments Comentario a ser almacenado sobre la remisión
	 * @param reference Objeto de remisión sobre el que se realizará el comentario
	 * @return Información del comentario que ha sido registrado
	 * @author jjimenezh
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	private SpecialComment reportReferenceSpecialComment(Long userId, String comments,
			Reference reference) throws DAOServiceException, DAOSQLException {
		SpecialComment specialComment = new SpecialComment();
		specialComment.setCommentDate(new Date());
		specialComment.setCommentDescription(comments);
		specialComment.setReference(reference);
		specialComment.setUserId(userId);
		daoSpecialCommentDao.createSpecialComment(specialComment);
		return specialComment;
	}

	/**
	 * Metodo: Se mueven los objetos de una remisión entre las bodegas origen y destino de la misma
	 * @param reference remisión a la que se moverán los objetos
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @throws PropertiesException en caso de error al ejecutar la operación
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenez
	 * @param isShipment 
	 * @throws HelperException 
	 */
	private void moveElementsInWarehouses(Reference reference, boolean isShipment) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException, HelperException {
		//Si la remisión tiene bodega de tránsito primero se mueven los elementos a la bodega de tránsito y luego a la bodega destino
		//Si el movimiento se debe a un envío es decir la variable isShipment viene en true, los elementos se mueven a la bodega de tránsito, en caso que no exista bodega de tránsito se dejan en la origen
		
		if(isShipment){
			
			if(reference.getSourceTransitWh() != null ){
				//Se mueven a la bodega origen
				moveReferenceElementsBetweenWhs(reference, reference.getWarehouseBySourceWh(), reference.getSourceTransitWh());
				return;
			}
		//Si el movimiento se da por registro del recibido y existe bodega de tránsito se mueven los elementos de la bodega de tránsito hacia la destino, en caso que no exista la de tránsito se mueven de la origen a la destino
		}else{
			if(reference.getSourceTransitWh() != null ){
				moveReferenceElementsBetweenWhs(reference, reference.getSourceTransitWh(), reference.getWarehouseByTargetWh());
			}else{
				moveReferenceElementsBetweenWhs(reference, reference.getWarehouseBySourceWh(), reference.getWarehouseByTargetWh());
			}
		}
	}


	/**
	 * Metodo: Mueve los elementos de una remisión entre las bodegas origen y destino especificadas
	 * @param reference información de la remisión
	 * @param sourceWh información de la bodega origen
	 * @param targetWh información de la bodega destino
	 * @throws DAOServiceException En caso de error al realizar el movimiento
	 * @throws DAOSQLException En caso de error al realizar el movimiento
	 * @throws PropertiesException En caso de error al realizar el movimiento
	 * @throws BusinessException En caso de error al realizar el movimiento
	 * @throws HelperException En caso de error al realizar el movimiento
	 * @author jjimenezh
	 * 
	 */
	private void moveReferenceElementsBetweenWhs(Reference reference, Warehouse sourceWh, Warehouse targetWh) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException, HelperException {
		
		//operacion paginada, en este punto no es necesario paginar por lo tanto se envia null
		RequestCollectionInfo requestCollInfo = null;
		ReferenceElementItemsResponse response = daoReferenceElementItem.getReferenceElementItemsByReferenceID(reference.getId(),requestCollInfo);
		List<ReferenceElementItem> refElements = response.getReferenceElementItems();
		WarehouseElementVO whElement = null;
		ReferenceVO referenceVo = UtilsBusiness.copyObject(ReferenceVO.class, reference);
		//MovementType movType = daoMovementType.getMovementTypeByCode(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_HIGH.getCodeEntity());
		for (ReferenceElementItem referenceElementItem : refElements) {
			whElement = new WarehouseElementVO();
			//whElement.setWarehouseBySourceWhId(sourceWh);
			//whElement.setWarehouseByTargetWhId(targetWh);
			//whElement.setMovementType(movType);
			
			//jjimenezh 2010-08-12 invocar el caso de uso INV - 14 mover elementos serializados entre bodegas
			if(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity().equalsIgnoreCase(referenceElementItem.getElement().getIsSerialized())){
				Serialized serialized = daoSerialized.getSerializedByID(referenceElementItem.getElement().getId());
				UtilsBusiness.assertNotNull(serialized, ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getMessage());
				whElement.setSerialized(serialized);
				//businessWhElement.moveSerializedElementIntoWareHouseElement(whElement);
				//jjimenezh 2010-08-12 invocar el caso de uso INV - 16 Informar a IBS movimiento de elementos serializados entre bodegas
				// jvelez 20/06/2011 -- Se debe grabar la notificación en la cola
				
				//notifySerializedElementMovementToIBS(referenceVo, referenceElementItem.getElement(), serialized, sourceWh.getId(), targetWh.getId());
			//jjimenezh 2010-08-12 invocar el caso de uso INV - 12 Mover elementos NO serializados entre bodegas
			}else if(CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity().equalsIgnoreCase(referenceElementItem.getElement().getIsSerialized())){
				NotSerialized notSer = daoNotSerialized.getNotSerializedByID(referenceElementItem.getElement().getId());
				UtilsBusiness.assertNotNull(notSer, ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getMessage());
				whElement.setNotSerialized(notSer);
				whElement.setActualQuantity(referenceElementItem.getRefQuantity());
				whElement.setRecordStatus(new RecordStatus( CodesBusinessEntityEnum.RECORD_STATUS_LAST.getIdEntity(RecordStatus.class.getName()) , CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity() ));
				//businessWhElement.moveNotSerializedElementIntoWareHouseElement(whElement, null);
				//jjimenezh 2010-08-12 invocar el caso de uso INV - 15 Informar a IBS movimiento de elementos NO serializados entre bodegas (jjimenezh 2010-08-26 no se realiza porque no se notificarán a ibs movimientos de no serializados)
			}
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferenceByIdAndWarhouseTarget(java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ReferenceVO> getReferenceByIdAndWarhouseTarget(Long referenceId, Long warehouseId )throws BusinessException{
		log.debug("== Inicia getReferencesBySourceAndTargetWareHouse/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(warehouseId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        List<ReferenceVO> references = null;
        try {
        	references = UtilsBusiness.convertList(this.daoReference.getReferenceByIdAndWarhouseTarget(referenceId,warehouseId), ReferenceVO.class);
        	if(references == null || !(references.size() > 0)){
        		log.debug("== No hay referencias con el filtro seleccionado ==");
        		throw new BusinessException(ErrorBusinessMessages.NO_REFERENCE_EXIT.getCode(),ErrorBusinessMessages.NO_REFERENCE_EXIT.getMessage());
        	}
        	for(ReferenceVO vo : references){
        		User creationUser = this.daoUser.getUserReferenceModificationsByReferenceIDAndModificationType(vo.getId(), CodesBusinessEntityEnum.REFERENCE_MODIFICATION_TYPE_CREATION_PROCESS.getCodeEntity());
        		User senderUser = this.daoUser.getUserReferenceModificationsByReferenceIDAndModificationType(vo.getId(), CodesBusinessEntityEnum.REFERENCE_MODIFICATION_SHIPMENT.getCodeEntity());
        		vo.setCreationUser(UtilsBusiness.copyObject(UserVO.class, creationUser));
        		vo.setSenderUser(UtilsBusiness.copyObject(UserVO.class, senderUser));
        	}        	
            return references;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesBySourceAndTargetWareHouse/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesBySourceAndTargetWareHouse/ReferenceBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferenceByIdAndWarhouseTarget(java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ReferenceVO> getReferenceByComplexFilterToFindInconsistenceReferences( 
			WarehouseVO whSource,WarehouseVO whTarget,ReferenceVO reference,Long country )throws BusinessException
    {
		log.debug("== Inicia getReferenceByComplexFilterToFindInconsistenceReferences/ReferenceBusinessBean ==");
		try {
			
			  if( country!=null && country<0 )
				  country = null;
			
			 return UtilsBusiness.convertList( daoReference.getReferenceByComplexFilterToFindInconsistenceReferences(whSource, whTarget, reference, country), ReferenceVO.class);
			
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getReferenceByComplexFilterToFindInconsistenceReferences/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceByComplexFilterToFindInconsistenceReferences/ReferenceBusinessBean ==");
        }	 
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferenceByIdAndWarhouseTarget(java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SerializedElementInReferencePaginationResponse getSerializedElementInReference( ReferenceVO reference, RequestCollectionInfo requestCollectionInfo )throws BusinessException
    {
		log.debug("== Inicia getSerializedElementInReference/ReferenceBusinessBean ==");
		SerializedElementInReferencePaginationResponse r = null;
		try {
			UtilsBusiness.assertNotNull(reference, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(reference.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			r = daoReference.getSerializedElementInReference(reference, requestCollectionInfo);
			r.setCollectionVO(UtilsBusiness.convertList( r.getCollection(), SerializedVO.class));
			r.setCollection(null);
			return r;
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operacion getSerializedElementInReference/ReferenceBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getSerializedElementInReference/ReferenceBusinessBean ==");
		}

    }
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferenceByIdAndWarhouseTarget(java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public NotSerializedElementInReferencePaginationResponse getNotSerializedElementInReference( ReferenceVO reference, RequestCollectionInfo requestCollectionInfo )throws BusinessException
	{
		log.debug("== Inicia getNotSerializedElementInReference/ReferenceBusinessBean ==");
		NotSerializedElementInReferencePaginationResponse r = null;
		try {
			UtilsBusiness.assertNotNull(reference, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(reference.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			r = daoReference.getNotSerializedElementInReferenceObjectsReturn(reference, requestCollectionInfo);
			List<NotSerializedVO> collectionVO = null;
			if( r.getCollectionObjects() != null & !r.getCollectionObjects().isEmpty() ){
				collectionVO = new ArrayList<NotSerializedVO>();
				for( NotSerRefElementItemDTO object : r.getCollectionObjects() ){
					NotSerializedVO vo = UtilsBusiness.copyObject(NotSerializedVO.class, object.getNotSerialized());
					vo.setReferenceElementItem( UtilsBusiness.copyObject(ReferenceElementItemVO.class, object.getRefElementItem()) );
					collectionVO.add(vo);
				}
			}
			r.setCollectionVO(collectionVO);
			r.setCollection(null);
			r.setCollectionObjects(null);
			return r;
		} catch(Throwable ex){
	   	       log.debug("== Error al tratar de ejecutar la operacion getNotSerializedElementInReference/ReferenceBusinessBean ==");
	   	       throw this.manageException(ex);
	   } finally {
	       log.debug("== Termina getNotSerializedElementInReference/ReferenceBusinessBean ==");
	     }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferenceByIdAndWarhouseTarget(java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ReferenceModificationVO> getReferenceModificationsByReferenceID(ReferenceVO refID)throws BusinessException
	{
		UtilsBusiness.assertNotNull(refID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(refID.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		log.debug("== Inicia getReferenceModificationsByReferenceID/ReferenceBusinessBean ==");
		try {
			
			 return UtilsBusiness.convertList( daoReferenceModification.getReferenceModificationsByReferenceID(refID.getId()), ReferenceModificationVO.class);
			
		} catch(Throwable ex){
	   	       log.debug("== Error al tratar de ejecutar la operacion getReferenceModificationsByReferenceID/ReferenceBusinessBean ==");
	   	       throw this.manageException(ex);
	   } finally {
	       log.debug("== Termina getReferenceModificationsByReferenceID/ReferenceBusinessBean ==");
	     }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferenceDetailsById(java.lang.Long,)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ReferenceVO getReferenceDetailsById(Long referenceId)throws BusinessException{
		log.debug("== Inicia getReferenceDetailsById/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(referenceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Reference objPojo = daoReference.getReferenceByID(referenceId);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            ReferenceVO response = UtilsBusiness.copyObject(ReferenceVO.class, objPojo);
            //Se adicionan los datos de ingreso asociados a la remision            
            response.setDeliveries(deliveryBusiness.getDeliveriesForDetailsByReferenceID(referenceId));
            //Se adicionan los comentarios sobre la remision
            response.setSpecialComments(businessSpecialComment.getSpecialCommentsByReferenceId(referenceId));
            //Seccion de elementos NO serializados
            response.setReferenceElementItemNotSerialize(businessRefElementItems.getNotSerializedReferenceElementItemByReferenceIdForDetails(referenceId));
            //Seccion de elementos serializados
            response.setReferenceElementItemSerialize(businessRefElementItems.getSerializedReferenceElementItemByReferenceIdForDetails(referenceId));
            //Seccion de modificaiones hechas a la remision
            response.setReferenceModifications(businessReferenceModification.getReferenceModificationsAndUsersModificationsByReferenceID(referenceId));
            //Seccion de inconsistencias presentadas en la remision
            response.setRefInconsistencyVO(businessRefInconsistency.getRefInconsistenciesAndUserCreationToDetailsByReferenceID(referenceId));
            
            removeReferenceDetails(response);
            
            return response;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceDetailsById/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceDetailsById/ReferenceBusinessBean ==");
        }
	}
	
	/**
	 * Metodo que elmina los detalles en la respuesta de consulta del CU 34 para evitar mandar mensaje SOAP
	 * cargado de informacion innecesaria
	 * @param reference
	 * @throws BusinessException 
	 */
	private void removeReferenceDetails(ReferenceVO reference) throws BusinessException{
		log.debug("== Inicia removeReferenceDetails/ReferenceBusinessBean ==");
		try{
			/*BLOQUE PARA ELIMINAR INFORMACION DEL CREATE USER*/
			if(reference.getCreateUserId() == null ){
				log.error("En el momento de consultar la informacion de la remision hay elementos nulos");
				throw new BusinessException(ErrorBusinessMessages.REFERENCE_DETAILS_NULL_VALUES.getCode(),ErrorBusinessMessages.REFERENCE_DETAILS_NULL_VALUES.getMessage());
			}
			if(reference.getCreateUserId().getDealer() != null  && reference.getCreateUserId().getDealer().getId() != null){
				Dealer dealer = new Dealer();
				dealer.setId(reference.getCreateUserId().getDealer().getId());
				reference.getCreateUserId().setDealer(dealer);
			}
			if(reference.getCreateUserId().getRol() != null && reference.getCreateUserId().getRol().getId() != null){
				Rol rol = new Rol();
				rol.setId( reference.getCreateUserId().getRol().getId() );
				reference.getCreateUserId().setRol(rol);
			}
			if(reference.getCreateUserId().getPosition() != null && reference.getCreateUserId().getPosition().getId() != null){
				Position position = new Position();
				position.setId( reference.getCreateUserId().getPosition().getId() );
				reference.getCreateUserId().setPosition(position);
			}
			
			/* BLOQUE PARA ELIMINAR INFORMACION*/
			if(reference.getWarehouseBySourceWh().getDealerId() != null && reference.getWarehouseBySourceWh().getDealerId().getId() != null){
				Dealer sourceDealer = new Dealer();
				sourceDealer.setId(reference.getWarehouseBySourceWh().getDealerId().getId());
				reference.getWarehouseBySourceWh().setDealerId(sourceDealer);
			}
			if(reference.getWarehouseByTargetWh().getDealerId() != null && reference.getWarehouseByTargetWh().getDealerId().getId() != null){
				Dealer targetDealer = new Dealer();
				targetDealer.setId(reference.getWarehouseByTargetWh().getDealerId().getId());
				reference.getWarehouseByTargetWh().setDealerId(targetDealer);
			}
			if(reference.getSourceTransitWh() != null && reference.getSourceTransitWh().getDealerId() != null && reference.getSourceTransitWh().getDealerId().getId() != null){
				Dealer transitDealer = new Dealer();
				transitDealer.setId(reference.getSourceTransitWh().getDealerId().getId());
				reference.getSourceTransitWh().setDealerId(transitDealer);
			}
			
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación removeReferenceDetails/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina removeReferenceDetails/ReferenceBusinessBean ==");
        }
		
		
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferenceByIdAndWarhouseTarget(java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<RefInconsistencyVO>getReferenceInconsistencyByReferenceID( Long id )throws BusinessException
	{

		log.debug("== Inicia getReferenceInconsistencyByReferenceID/ReferenceBusinessBean ==");
		try {
			
			 return businessRefInconsistency.getRefInconsistencysByReferenceID(id);
			
		} catch(Throwable ex){
	   	       log.debug("== Error al tratar de ejecutar la operacion getReferenceInconsistencyByReferenceID/ReferenceBusinessBean ==");
	   	       throw this.manageException(ex);
	   } finally {
	       log.debug("== Termina getReferenceInconsistencyByReferenceID/ReferenceBusinessBean ==");
	     }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#saveReferenceDeliveries(java.lang.Long,java.util.List<co.com.directv.sdii.model.vo.DeliveryVO>,java.util.List<co.com.directv.sdii.model.vo.SpecialCommentVO>)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveReferenceDeliveries(Long referenceId ,List<DeliveryVO> deliveriesList ,List<SpecialCommentVO> specialCommentsList)throws BusinessException {
		log.debug("== Inicia saveReferenceDeliveries/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(referenceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(deliveriesList, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(specialCommentsList, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	//Almacena las entregas
        	if(deliveriesList.size() > 0)
        		deliveryBusiness.createDeliveries(referenceId,deliveriesList);
        	//Almacena los comentarios especiales
        	if(specialCommentsList.size() > 0)
        		businessSpecialComment.createSpecialComments(referenceId, specialCommentsList);
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación saveReferenceDeliveries/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina saveReferenceDeliveries/ReferenceBusinessBean ==");
        }
	}
	
		
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#saveReferenceDeliveries(java.lang.Long,java.util.List<co.com.directv.sdii.model.vo.DeliveryVO>,java.util.List<co.com.directv.sdii.model.vo.SpecialCommentVO>)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void closeReferenceInconsistencyStatus( List<Long>inconsistenciesListIds )throws BusinessException
	{
		log.debug("== Inicia closeReferenceInconsistencyStatus/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(inconsistenciesListIds, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        try {
        	/** El estado cerrado a definir **/
        	RefIncStatus status = refIncStatusDAO.getRefIncStatusByID( CodesBusinessEntityEnum.INCONSISTENCY_REFERENCE_STATUS_CLOSE.getIdEntity( RefIncStatus.class.getName() ) );	
        	
        	refInconsistencyDAO.updateReferenceInconsistencyStatus( inconsistenciesListIds, status);
        	
        	Reference reference = null;
        	if( !inconsistenciesListIds.isEmpty() )
        		reference = refInconsistencyDAO.getReferenceByInconsistencyId( inconsistenciesListIds.get(0) );
        	if( reference != null ){
        		ReferenceStatus newRefStatus = reference.getReferenceStatus();
        		
            	List<RefInconsistency> openInconsistencies = refInconsistencyDAO.getReferenceInconsistencyByReferenceIdAndStatus(reference.getId(), CodesBusinessEntityEnum.INCONSISTENCY_REFERENCE_STATUS_OPEN.getCodeEntity(),null).getRefInconsistencys();
            	List<ReferenceElementItem> referenceConfirmedItems = this.daoReferenceElementItem.getReferenceElementItemByReferenceIdAndByNotItemStatus(reference.getId(),CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity());
            	
            	//Todos los elementos esten en confirmado y todas las inconsistencias cerradas --> remision recepcionada
            	if( openInconsistencies != null && openInconsistencies.isEmpty() 
            			&& referenceConfirmedItems != null && referenceConfirmedItems.isEmpty()){
            		newRefStatus = new ReferenceStatus( CodesBusinessEntityEnum.REFERENCE_STATUS_RECEPTED.getIdEntity( ReferenceStatus.class.getName() ) );
            	//No todos los elementos esten confirmados y todas las inconsistencias cerradas --> remision enviado
            	}else if( openInconsistencies != null && openInconsistencies.isEmpty() 
            			&& referenceConfirmedItems != null && !referenceConfirmedItems.isEmpty()){
            		newRefStatus = new ReferenceStatus( CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getIdEntity( ReferenceStatus.class.getName() ) );
            	}
            	reference.setReferenceStatus(newRefStatus);
            	daoReference.updateReference(reference);
        	}
        	
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion closeReferenceInconsistencyStatus/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina closeReferenceInconsistencyStatus/ReferenceBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#saveReferenceDeliveries(java.lang.Long,java.util.List<co.com.directv.sdii.model.vo.DeliveryVO>,java.util.List<co.com.directv.sdii.model.vo.SpecialCommentVO>)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void modifyReferenceElementItemCuantityByReferenceAndItemList( List<ReferenceElementItemVO>items,ReferenceVO reference ,Boolean finished,UserVO user)throws BusinessException
	{
		String errorCode    = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
		String errorMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
		
		log.debug("== Inicia modifyReferenceElementItemCuantityByReferenceAndItemList/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(items, errorCode, errorMessage);
        UtilsBusiness.assertNotNull( reference , errorCode, errorMessage);
        UtilsBusiness.assertNotNull( reference.getId() , errorCode, errorMessage);
	    
        /** por defaul se asocia al flujo de "guardar" no de "terminar" **/   
         if( finished==null )
        	 finished = Boolean.valueOf(false);
        
         /*List<ReferenceElementItem>listToRemove = new ArrayList<ReferenceElementItem>();
         
         for( ReferenceElementItemVO item:items )
         {
      	   UtilsBusiness.assertNotNull( item.getId() , errorCode, errorMessage);    
      	   listToRemove.add( UtilsBusiness.copyObject(ReferenceElementItem.class, item) );
         }*/
         
        try {
        	
        	Reference refPojo = daoReference.getReferenceByID( reference.getId() );
        	validateElementQuantitiesInWh(items, refPojo.getWarehouseBySourceWh().getId());
        	updateReferenceElementItems(items,refPojo);
            //daoReferenceElementItem.modifyReferenceElementItemCuantityByReferenceAndItemList(listToWork, refPojo,finished );       	
         	
            /** flujos alternos a la modificacion para guardar y terminar **/
             if(finished){
            	 validateFinishFlowInModifyReferenceElementItem(refPojo,user);
             }
            
             //Guardando los comentarios especiales
             List<SpecialCommentVO> specialComments = reference.getSpecialComments();
             createSpecialCommnets(specialComments, refPojo);
             
             
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion closeReferenceInconsistencyStatus/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina closeReferenceInconsistencyStatus/ReferenceBusinessBean ==");
        }
	
	}
	
	/**
	 * Metodo: Crea o actualiza los comentarios especiales asociados a una remisión
	 * @param specialComments lista de comentarios especiales a ser creada o actualizada
	 * @param refPojo información de la remisión
	 * @throws BusinessException en caso de error
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 * @author jjimenezh
	 */
	private void createSpecialCommnets(List<SpecialCommentVO> specialComments, Reference refPojo) throws BusinessException, DAOServiceException, DAOSQLException {
		if(specialComments != null && !specialComments.isEmpty()){
       	 SpecialComment sc;
       	 for (SpecialCommentVO specialCommentVO : specialComments) {
       		 	sc = UtilsBusiness.copyObject(SpecialComment.class, specialCommentVO);
				sc.setReference(refPojo);
				
				String errorCode = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
    			String errorMsj = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
    			UtilsBusiness.assertNotNull(sc, errorCode,errorMsj );
    			UtilsBusiness.assertNotNull(sc.getCommentDate(), errorCode,errorMsj );
    			UtilsBusiness.assertNotNull(sc.getCommentDescription(), errorCode,errorMsj );
    			UtilsBusiness.assertNotNull(sc.getReference(), errorCode,errorMsj );
    			UtilsBusiness.assertNotNull(sc.getUserId(), errorCode,errorMsj );
    			
				
       		 	if(sc.getId() == null || sc.getId().longValue() <= 0){
					daoSpecialCommentDao.createSpecialComment(sc);
				}else{
					daoSpecialCommentDao.updateSpecialComment(sc);
				}
			}
        }
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void validateFinishFlowInModifyReferenceElementItem( Reference reference ,UserVO user )throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException
	{
		
		boolean allElementsConfirmed     = true;
		boolean allInconsistenciesClosed = true;
		
		List<RefInconsistency>inconsistencies = refInconsistencyDAO.getReferenceInconsistencyByReferenceID( reference.getId() );
		String inconsistencyStatusClosedCode = CodesBusinessEntityEnum.INCONSISTENCY_REFERENCE_STATUS_CLOSE.getCodeEntity(); 
		//operacion paginada, en este punto no es necesario paginar por lo tanto se envia null
		RequestCollectionInfo requestCollInfo = null;
		ReferenceElementItemsResponse response = daoReferenceElementItem.getReferenceElementItemsByReferenceID( reference.getId(), requestCollInfo );
		List<ReferenceElementItem> items = response.getReferenceElementItems();
		
		/** Seria mejor cambiar el tipo de modificacion a realizar!! **/
		String referenceModificationCode = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_TYPE_CREATION_PROCESS.getCodeEntity();
		
		/** establece si alguna inconsistencia se encuentra abierta **/   
		for( RefInconsistency inconsyst :inconsistencies ){
			if(! inconsistencyStatusClosedCode.equalsIgnoreCase(inconsyst.getRefIncStatus().getRefIncStatusCode()))
			{
				allInconsistenciesClosed = false;
				break;
			}
		}
		
		/** establece si todos los elementos de la remision han sido confirmados **/
		String itemStatusCode;
		for(ReferenceElementItem item:items){
			/** se consultan las confirmaciones de cada elementos de la remision **/
			//Si el estado del item es diferente de recepcionado, no se ha confirmado
			itemStatusCode = daoReferenceElementItem.getItemStatusCodeByRefElementItemId(item.getId());
			if(!CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity().equalsIgnoreCase(itemStatusCode)){
				allElementsConfirmed = false;
				break;
			}
		}
		
		if(allElementsConfirmed && allInconsistenciesClosed){
			ReferenceStatus status = daoReferenceStatus.getReferenceByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_RECEPTED.getCodeEntity());
			reference.setReferenceStatus(status);
			daoReference.updateReference(reference);
		}
		
		if(!allElementsConfirmed && allInconsistenciesClosed){
			ReferenceStatus status = daoReferenceStatus.getReferenceByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity());
			reference.setReferenceStatus(status);
			daoReference.updateReference(reference);
		}
		
        SendEmailDTO email = prepareSendEmailDTOToReferencesShipment(user, reference);
        List<String> recipients = new ArrayList<String>();

           if(reference.getWarehouseBySourceWh().getResponsibleEmail()!=null){
        	   recipients.add(reference.getWarehouseBySourceWh().getResponsibleEmail());
           }
           if(reference.getWarehouseByTargetWh().getResponsibleEmail()!=null){
        	   recipients.add(reference.getWarehouseByTargetWh().getResponsibleEmail());
           }
           
           if(recipients.size()>0){
        	   email.setRecipient(recipients);
        	   email.setNewsDocument(user.getIdNumber());
               email.setNewsObservation(ApplicationTextEnum.SENDING_LOG_REMISSION.getApplicationTextValue());
               email.setNewsSourceUser(user.getName());
        	   email.setNewsType(EmailTypesEnum.MODIFICATIONS_TO_REFERENCE.getEmailTypecode());
        	   businessEmailType.sendEmailByEmailTypeCodeAsic(email, reference.getCountryCodeId().getId());
           }
           
           /** Se notifica del cambio de estado de la remision */
           buildReferenceModification(reference, user,referenceModificationCode);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#addReferenceElementItemToReference(java.util.List, co.com.directv.sdii.model.vo.ReferenceVO)
	 */
	@SuppressWarnings("unused")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addReferenceElementItemToReference( List<ReferenceElementItemVO>items,ReferenceVO reference )throws BusinessException
	{
		log.debug("== Inicia addReferenceElementItemToReference/ReferenceBusinessBean ==");
		String errorCode    = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
		String errorMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
		
        UtilsBusiness.assertNotNull(items, errorCode, errorMessage);
        UtilsBusiness.assertNotNull(reference, errorCode, errorMessage);
        UtilsBusiness.assertNotNull(reference.getId(), errorCode, errorMessage);
        
        List<ReferenceElementItem>listToWork = new ArrayList<ReferenceElementItem>();
        
           for(ReferenceElementItemVO item:items){
        	   UtilsBusiness.assertNotNull(item.getElement(),errorCode,errorMessage);    
        	   UtilsBusiness.assertNotNull(item.getElement().getId(),errorCode,errorMessage);    
        	   UtilsBusiness.assertNotNull(item.getRefQuantity() , errorCode, errorMessage);   
        	   UtilsBusiness.assertNotNull(item.getItemStatus() , errorCode, errorMessage);   
        	   UtilsBusiness.assertNotNull(item.getItemStatus().getId() , errorCode, errorMessage);  
        	   
        	   listToWork.add(UtilsBusiness.copyObject(ReferenceElementItem.class, item));
           }
        
        try {
        	
        	Reference refPojo = daoReference.getReferenceByID( reference.getId() );
        
            daoReferenceElementItem.addReferenceElementItemToReference( listToWork,reference );      	
         	
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion addReferenceElementItemToReference/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina addReferenceElementItemToReference/ReferenceBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#deleteReferenceElementItemInReference(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteReferenceElementItemInReference(List<Long> itemsId)
			throws BusinessException {
		
		log.debug("== Inicia deleteReferenceElementItemInReference/ReferenceBusinessBean ==");
         
		try {
			
			for(Long itemId:itemsId){
        		ReferenceElementItem item = daoReferenceElementItem.getReferenceElementItemByID( itemId );
        		   if(item!=null){
        			  List<RefConfirmation> confirmations = refConfirmationDAO.getConfirmationsByElementId( itemId ); 
                      /** si el elemento de la remision no ha sido confirmada (con registros en la tabla REF_CONFIRMATION) 
                       * entonces es candidato a ser eliminado **/
        			  
        			  if(confirmations==null || confirmations.size()==0){
        				  daoReferenceElementItem.deleteReferenceElementItem(item);   
                      }
        		   }
        	}
            
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion deleteReferenceElementItemInReference/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteReferenceElementItemInReference/ReferenceBusinessBean ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#moveElementsBetweenWarehouses(java.lang.Long, java.lang.Long, java.util.List, co.com.directv.sdii.model.vo.ReferenceVO, co.com.directv.sdii.model.pojo.ItemStatus)
	 */
	@Override
	public void moveElementsBetweenWarehouses(Long whIdSource, Long whIdTarget, List<ReportedElementVO> reportedElementVOs, ReferenceVO reference,ItemStatus itemStatus, boolean isFisrtMovementLessElements, User user, boolean reportIBS) throws BusinessException {
		
		Long elementId;
		String serial;
		try {
			
			Warehouse warehouseSource = daoWarehouse.getWarehouseByID(whIdSource);
			Warehouse warehouseTarget = daoWarehouse.getWarehouseByID(whIdTarget);
			for (ReportedElementVO reportedElementVO : reportedElementVOs) {
				
				List<ReferenceElementItemVO> refAddedElementIds = reportedElementVO.getAddedRefElements();
				
				String movementTypeCodeE = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSLATE_BY_REF_BETWEEN_COMPANY_ENTRY.getCodeEntity();
				String movementTypeCodeS = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSLATE_BY_REF_BETWEEN_COMPANY_EXIT.getCodeEntity();
				
				boolean isMovementCausedByMoreElementsInconsistency = false;
				if(reportedElementVO.getRefInconsistency().getRefIncType().getRefIncTypeCode().equals(CodesBusinessEntityEnum.INCONSISTENCY_TYPE_EXCEEDED_QUANTITY.getCodeEntity())){
					isMovementCausedByMoreElementsInconsistency = true;
				}else if(reportedElementVO.getRefInconsistency().getRefIncType().getRefIncTypeCode().equals(CodesBusinessEntityEnum.INCONSISTENCY_TYPE_INCOMPLETE_QUANTITY.getCodeEntity())){
					isMovementCausedByMoreElementsInconsistency = false;
				}
				
				
				boolean isSerialized = !StringUtils.isEmpty(reportedElementVO.getSerialCode());
				
				if(isSerialized) {
					// INVOCA CU 14 MOVIMIENTO DE ELEMENTOS SERIALIZADOS ENTRE BODEGAS
					
					if(isMovementCausedByMoreElementsInconsistency) {
						//se realiza el movimiento del elemento serializado y el elemento enlazado agregados a la remisión
						
						
						
						MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementTypeCodeE, movementTypeCodeS);
						for (ReferenceElementItemVO addedElementId : refAddedElementIds) {
							elementId = addedElementId.getElement().getId();
							/*ElementMovementDTO dto = new ElementMovementDTO(whIdSource, whIdTarget, elementId, movementTypeCodeE, movementTypeCodeS, reference.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), serial, null, true, null,itemStatus);
							businessWhElement.moveElementToWareHouse(dto);*/
							
							Serialized ser = daoSerialized.getSerializedByID(elementId);
							
							MovementElementDTO dtoMovement = new MovementElementDTO(user, 
									UtilsBusiness.copyObject(WarehouseVO.class, warehouseSource), 
									UtilsBusiness.copyObject(WarehouseVO.class, warehouseTarget),
									ser,
									reference.getId(),
									CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(),
									dtoGenerics.getMovTypeCodeE(),
									dtoGenerics.getMovTypeCodeS(),
									dtoGenerics.getRecordStatusU(), 
									dtoGenerics.getRecordStatusH(), 
									//true, 
									reportIBS,
									CodesBusinessEntityEnum.PROCESS_CODE_IBS_REFERENCE.getCodeEntity(),
									dtoGenerics.getMovConfigStatusPending(),
									dtoGenerics.getMovConfigStatusNoConfig());
							businessMovementElement.moveSerializedElementBetweenWarehouse(dtoMovement);
						}
					}else{
						//se mueve el elemento en su proceso de remisión de devolución
						elementId = reportedElementVO.getReferenceElementItem().getElement().getId();
						
						/*ElementMovementDTO dto = new ElementMovementDTO(whIdSource, whIdTarget, elementId, movementTypeCodeE, movementTypeCodeS, reference.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), serial, null, true, null,itemStatus);
						businessWhElement.moveElementToWareHouse(dto);*/
						
						Serialized ser = daoSerialized.getSerializedByID(elementId);
						
						MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementTypeCodeE, movementTypeCodeS);
						MovementElementDTO dtoMovement = new MovementElementDTO(user, 
								UtilsBusiness.copyObject(WarehouseVO.class, warehouseSource), 
								UtilsBusiness.copyObject(WarehouseVO.class, warehouseTarget),
								ser,
								reference.getId(),
								CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(),
								dtoGenerics.getMovTypeCodeE(),
								dtoGenerics.getMovTypeCodeS(),
								dtoGenerics.getRecordStatusU(), 
								dtoGenerics.getRecordStatusH(), 
								//true, 
								reportIBS,
								CodesBusinessEntityEnum.PROCESS_CODE_IBS_REFERENCE.getCodeEntity(),
								dtoGenerics.getMovConfigStatusPending(),
								dtoGenerics.getMovConfigStatusNoConfig());
						businessMovementElement.moveSerializedElementBetweenWarehouse(dtoMovement);
					
					}
					
				} else {// si es no serializado
					// INVOCA CU 12 MOVIMIENTO DE ELEMENTOS NO SERIALIZADOS ENTRE BODEGAS
					
					Long elementTypeId = reportedElementVO.getElementType().getId();
					
					if(isMovementCausedByMoreElementsInconsistency) {
						
						//se realiza el movimiento de todos los elementos no serializados (realmente es uno) que hayan sido necesarios para cubrir el elemento que se envió de mas
						for (ReferenceElementItemVO addedElementId : refAddedElementIds) {
							/*ElementMovementDTO dto = new ElementMovementDTO(whIdSource, whIdTarget, elementTypeId, addedElementId.getRefIncAddedQuantity(), movementTypeCodeE, movementTypeCodeS, false, reference.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity());
							businessWhElement.moveNotSerializedElementBetweenWareHouses(dto);*/
							
							MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementTypeCodeE, movementTypeCodeS);
							MovementElementDTO dtoMov = new MovementElementDTO(user,
									UtilsBusiness.copyObject(WarehouseVO.class, warehouseSource), 
									UtilsBusiness.copyObject(WarehouseVO.class,  warehouseTarget), 
									null, 
									elementTypeId, 
									null,
									reference.getId(), 
									CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), 
									dtoGenerics.getMovTypeCodeE(), 
									dtoGenerics.getMovTypeCodeS(), 
									dtoGenerics.getRecordStatusU(),
									dtoGenerics.getRecordStatusH(),
									addedElementId.getRefIncAddedQuantity(),
									dtoGenerics.getMovConfigStatusPending(),
									dtoGenerics.getMovConfigStatusNoConfig());
							businessMovementElement.moveNotSerializedElementBetweenWarehouse(dtoMov);
							
							
						}
						
					} else {
						
						Double quantityToMove = 0D;
						if(isFisrtMovementLessElements) {
							//la cantidad a mover a la bodega de destino debe ser todo lo que queda en transito destino de la remisión (es decir, sin lo ya confirmado), no solo lo de la inconsistencia
							//esto es así por que se supone que al cerrar la inconsistencia, el elemento queda en recepcionado, y no puede quedar ningún sobrante en la bodega de tránsito
							Long referenceElementId = reportedElementVO.getReferenceElementItem().getId();
							Double quantityConfirmed = daoRefConfirmation.countElementConfirmedQuatity(referenceElementId);
							Double quantityInReference = reportedElementVO.getReferenceElementItem().getRefQuantity();
							quantityToMove = quantityInReference - quantityConfirmed;
						} else {
							quantityToMove = reportedElementVO.getQty();
						}
						//se mueve el elemento en su proceso de remisión de devolución
						
						/*ElementMovementDTO dto = new ElementMovementDTO(whIdSource, whIdTarget, elementTypeId, quantityToMove, movementTypeCodeE, movementTypeCodeS, false, reference.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity());
						businessWhElement.moveNotSerializedElementBetweenWareHouses(dto);*/
						
						MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementTypeCodeE, movementTypeCodeS);
						MovementElementDTO dtoMov = new MovementElementDTO(user,
								UtilsBusiness.copyObject(WarehouseVO.class, warehouseSource), 
								UtilsBusiness.copyObject(WarehouseVO.class,  warehouseTarget), 
								null, 
								elementTypeId, 
								null,
								reference.getId(), 
								CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), 
								dtoGenerics.getMovTypeCodeE(), 
								dtoGenerics.getMovTypeCodeS(), 
								dtoGenerics.getRecordStatusU(),
								dtoGenerics.getRecordStatusH(),
								quantityToMove,
								dtoGenerics.getMovConfigStatusPending(),
								dtoGenerics.getMovConfigStatusNoConfig());
						businessMovementElement.moveNotSerializedElementBetweenWarehouse(dtoMov);
						
					}
					
				}
			}
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion moveElementsBetweenWarehouses/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina moveElementsBetweenWarehouses/ReferenceBusinessBean ==");
        }
	}
	
	/**
	 * Metodo: agrega a una remisión los elementos que hacen parte de una inconsistencia Mas Elementos
	 * @param refInconsistencyVO
	 * @param reportedElementVOs
	 * @author wjimenez
	 */
	private void addElementsToReference(RefInconsistencyVO refInconsistencyVO, List<ReportedElementVO> reportedElementVOs,ItemStatus itemStatus) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException {
		for (ReportedElementVO reportedElementVO : reportedElementVOs) {
			if(StringUtils.isEmpty(reportedElementVO.getSerialCode())) { // si es no serializado, se agrega el o los elementos en caso que la cantidad de un solo elemento no alcance
				this.addNotSerializedElementsToReference(reportedElementVO.getQty(), refInconsistencyVO.getReference(), reportedElementVO.getElementType(), CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), itemStatus, reportedElementVO);
			} else { //si el elemento es serializado, se agrega junto con los elementos enlazados
				this.addSerializedAndLinkedSerializedElementToReference(refInconsistencyVO.getReference(), reportedElementVO.getSerialized(), reportedElementVO,itemStatus);
			}
		}
	}
	
	/**
	 * Metodo: agrega a la remisión el elemento serializado y el serializados enlazado
	 * @param refInconsistencyVO
	 * @param serialCode
	 * @author wjimenez
	 */
	private void addSerializedAndLinkedSerializedElementToReference(Reference reference, Serialized serialized, ReportedElementVO reportedElementVO,ItemStatus itemStatus) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException {
		
		//si el elemento a agregar es uno vinculado (hijo), se busca al padre y se agrega el padre
		List<Serialized> parentElements = daoSerialized.getLinkedSerializedBySerializedId(serialized.getElementId());
		Serialized parentElement = null;
		if(parentElements != null && !parentElements.isEmpty()) {
			parentElement = parentElements.get(0);
		} else {
			parentElement = serialized;
		}
		
		ReferenceElementItemVO refElementItem = new ReferenceElementItemVO();
		refElementItem.setReference(reference);
		refElementItem.setRefQuantity(1.0);//todos los elementos serializados son de cantidad 1
		refElementItem.setItemStatus(itemStatus);
		
		refElementItem.setElement(parentElement.getElement());
		
		businessRefElementItems.createReferenceElementItem(refElementItem);
		
		//información almacenada para posteriormente realizar fácilmente los movimientos entre bodegas
		refElementItem.setSerializeElement( UtilsBusiness.copyObject(SerializedVO.class, parentElement) );
		reportedElementVO.addAddedElement(refElementItem);
		
		reportedElementVO.setReferenceElementItem(refElementItem);//ahora que si se sabe el ítem en la remisión, se agrega la referencia en el reported element
		businessReportedElement.updateReportedElement(reportedElementVO);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#closeRefInconsistency(co.com.directv.sdii.model.vo.RefInconsistencyVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long closeRefInconsistency(RefInconsistencyVO refInconsistencyVO, Long userId) throws BusinessException {
		try {
			log.debug("== Inicia closeRefInconsistency/ReferenceBusinessBean ==");
			UtilsBusiness.assertNotNull(refInconsistencyVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(refInconsistencyVO.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(refInconsistencyVO.getReference(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(refInconsistencyVO.getReference().getWarehouseBySourceWh(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(refInconsistencyVO.getReference().getWarehouseByTargetWh(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(refInconsistencyVO.getReference().getTargetTransitWh(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(refInconsistencyVO.getReference().getSourceTransitWh(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(refInconsistencyVO.getReference().getCountryCodeId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			//Consulto el usuario 
        	User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
			
			List<ReportedElementVO> reportedElementVOs = businessReportedElement.getReportedElementsByRefInconsistencyId(refInconsistencyVO.getId(), true, true);
			Long generatedReferenceId = 0L;
			
			if(refInconsistencyVO.isMoreElements()) {
				closeRefInconsistencyMoreElements(refInconsistencyVO, reportedElementVOs, user);
			} else if(refInconsistencyVO.isLessElements()){
				generatedReferenceId = closeRefInconsistencyLessElements(refInconsistencyVO, reportedElementVOs, user);
			} else {
				String msg = "no se pudo determinar el tipo de inconsistencia a ser cerrada. Id de la inconsistencia = " + refInconsistencyVO.getId();
				log.error(msg);
				throw new BusinessException(msg);
			}
			return generatedReferenceId;
			
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion closeRefInconsistency/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina closeRefInconsistency/ReferenceBusinessBean ==");
        }
	}
	
	private void validateReportedElementsLocationAndAvailability(Long referenceSourceWhId, List<ReportedElementVO> reportedElementVOs,Long countryId) throws DAOServiceException, DAOSQLException, BusinessException {
		if(reportedElementVOs != null) {
			for (ReportedElementVO reportedElementVO : reportedElementVOs) {
				String serial = reportedElementVO.getSerialCode();
				if(StringUtils.isBlank(serial)) {//si es NO serializado
					
					Long elementTypeId = reportedElementVO.getElementType().getId();
					Double elementQuantity = daoWarehouseElement.getWarehouseElementQuantityByElementType(referenceSourceWhId, elementTypeId);
					if(elementQuantity == null) {
						Object[] params = {elementTypeId, referenceSourceWhId};
						throw new BusinessException(ErrorBusinessMessages.STOCK_IN387.getCode(), ErrorBusinessMessages.STOCK_IN387.getMessageCode(params));
					}else if(elementQuantity < reportedElementVO.getQty()) {
						//no se encuentra la cantidad suficiente en la bodega
						Object[] params = {elementTypeId, referenceSourceWhId};
	        			throw new BusinessException(ErrorBusinessMessages.STOCK_IN392.getCode(), ErrorBusinessMessages.STOCK_IN392.getMessageCode(params));
					}
					
				} else {// si es serializado
					
					WarehouseElement whElement = daoWarehouseElement.getWarehouseElementBySerialActive(serial,countryId);
					if(whElement != null) {
						if(!whElement.getWarehouseId().getId().equals( referenceSourceWhId )) {
							//el elemento no se encuentra en la bodega origen de la remisión
							Object[] params = {reportedElementVO.getSerialCode()};
							throw new BusinessException(ErrorBusinessMessages.STOCK_IN383.getCode(), ErrorBusinessMessages.STOCK_IN383.getMessageCode(params));
						}
					} else {
						//el elemento no se encuentra en ninguna bodega
						Object[] params = {reportedElementVO.getSerialCode()};
						throw new BusinessException(ErrorBusinessMessages.STOCK_IN383.getCode(), ErrorBusinessMessages.STOCK_IN383.getMessageCode(params));
					}
					
				}
			}
		}
	}
	
	/**
	 * Metodo: Agregar nuevos elementos a la remisión. Los elementos agregados son los que llegaron
	 * de sobra y que no están registrados.
	 * @param refInconsistencyVO
	 * @param reportedElementVOs
	 * @author wjimenez
	 */
	private void closeRefInconsistencyMoreElements(RefInconsistencyVO refInconsistencyVO, List<ReportedElementVO> reportedElementVOs, User user) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException {
		
		//valida la ubicación y disponibilidad en bodega de origen en el momento de realizar el cierre de la inconsistencia
		
		// validar que los elementos estén en la bodega transito origen
		Long referenceSourceWhId = refInconsistencyVO.getReference().getSourceTransitWh().getId();
		this.validateReportedElementsLocationAndAvailability(referenceSourceWhId, reportedElementVOs,user.getCountry().getId());
		
		ItemStatus itemStatus = daoItemStatus.getItemStatusSended();
		
		//agregar los elementos a la remisión
		this.addElementsToReference(refInconsistencyVO, reportedElementVOs, itemStatus);
		
		Reference ref = refInconsistencyVO.getReference();
		
		//primer movimiento
		Long whIdSource = ref.getSourceTransitWh().getId();//ubicación  transito origen
		Long whIdTarget = ref.getTargetTransitWh().getId();//Ubicación  transito destino
		this.moveElementsBetweenWarehouses(whIdSource, whIdTarget, reportedElementVOs, UtilsBusiness.copyObject(ReferenceVO.class, refInconsistencyVO.getReference()),itemStatus, false, user, true);

		//cerrar la inconsistencia
		this.closeRefInconsistencyRecord(refInconsistencyVO, null);
		
		//actualizar el estado de la remisión de acuerdo a las reglas definidas en el flujo - Cerrar Inconsistencia –  “Más Elementos Físicos”
        ReferenceStatus refStatus = null;
        if(this.areAllRefInconsistenciesClosed(refInconsistencyVO.getReference().getId())) {
        	String refStatusMod = null;
        	
        	if(areAllReferenceElementsInState(refInconsistencyVO.getReference().getId(), CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity())) {
        		refStatus = daoReferenceStatus.getReferenceByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity());
        		refStatusMod = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_SHIPMENT.getCodeEntity();
        	} else {
        		refStatus = daoReferenceStatus.getReferenceByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity());
        		refStatusMod = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_PARTIALLY_CONFIRMED.getCodeEntity();
        	}
        	
        	if(refStatus != null) {
        		Reference reference = refInconsistencyVO.getReference();
	        	reference.setReferenceStatus(refStatus);
	            daoReference.updateReference(reference);
	            //Crea el registro en las modificaciones de remision
	            if (refInconsistencyVO.getAnswerUserId() != null){
	            	this.businessReferenceModification.createReferenceModification( reference.getId() , refStatusMod, user.getId());
	            }
        	}
            
        }

        //El sistema asocia los elementos agregados a la inconsistencia cerrada. Esto ya se hizo en addElementsToReference(refInconsistencyVO, reportedElementVOs);
		
        //invocar INV-08 informando del cierre de la inconsistencia
        referenceMailSender.sendRefInconsistencyMoreElementsClosedMail(refInconsistencyVO, reportedElementVOs);
        
	}
	
	/**
	 * Metodo: Crea una nueva remisión de devolución con los elementos que no llegaron
	 * @param refInconsistencyVO
	 * @param reportedElementVOs
	 * @return identificador de la remisión de devolución generada para poder cerrar una
	 * inconsistencia por menos elementos físicos
	 * @author wjimenez
	 */
	private Long closeRefInconsistencyLessElements(RefInconsistencyVO refInconsistencyVO,
			List<ReportedElementVO> reportedElementVOs, User user) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException {
		
		ItemStatus statusRecepted = daoItemStatus.getItemStatusRecepted();
		boolean reportIBS=true; 

		//Verficar si corresponde informar elementos a IBS durante el primer movimiento de elementos realizado al cerrar inconsistencia
		if (CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity().equalsIgnoreCase( 
		     systemParameterDAO.getSysParamByCodeAndCountryId(
		      CodesBusinessEntityEnum.SYSTEM_PARAM_REPORT_ELEMENT_TO_IBS.getCodeEntity(), user.getCountry().getId()).getValue() ) )
			reportIBS=false;
		
		//primer movimiento
		Reference ref = refInconsistencyVO.getReference();
		Long whIdSource = ref.getTargetTransitWh().getId();//ubicación  transito destino
		Long whIdTarget = ref.getWarehouseByTargetWh().getId();//la ubicación  destino
	
		this.moveElementsBetweenWarehouses(whIdSource, whIdTarget, reportedElementVOs, UtilsBusiness.copyObject(ReferenceVO.class, refInconsistencyVO.getReference()),statusRecepted, true, user, reportIBS);
		
		//actualizar estado a recepcionado
		this.updateReportedElements(reportedElementVOs, statusRecepted);
		//confirmar la recepción de todos los elementos en la remisión
		this.confirmReceptedElements(reportedElementVOs, refInconsistencyVO.getAnswerUserId());
		
		//Los movimientos distintos al primero deben continuar reportando a IBS
		reportIBS = true; 
		//crear una remisión de devolución con los elementos movidos
		Reference devolutionReference = this.createDevolutionReference(refInconsistencyVO, reportedElementVOs, user);
		
		//asociar los elementos a la remisión de devolución
		List<ReferenceElementItem> devolutionReferenceElements = this.addReportedElementsToDevolutionReference(reportedElementVOs, devolutionReference);
		
		//segundo movimiento
		ItemStatus statusCreated = daoItemStatus.getItemStatusCreated();
		String refStatusCreated = CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity();
		whIdSource = devolutionReference.getWarehouseBySourceWh().getId();//Ubicación  origen de la remisión generada
		whIdTarget = devolutionReference.getSourceTransitWh().getId();//Ubicación  transito origen de la remisión generada
		this.moveElementsBetweenWarehouses(whIdSource, whIdTarget, reportedElementVOs, UtilsBusiness.copyObject(ReferenceVO.class, refInconsistencyVO.getReference()),statusCreated, false, user, reportIBS);
		
		//actualizar estado de los elementos de la remisión de devolución a creado
		//this.updateReportedElements(reportedElementVOs, statusCreated);
		this.updateRefElementsStatus(devolutionReference, refStatusCreated, devolutionReferenceElements, statusCreated);
		
		//tercer movimiento
		ItemStatus statusSended = daoItemStatus.getItemStatusSended();
		String refStatusSended = CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity();
		whIdSource = devolutionReference.getSourceTransitWh().getId();//Ubicación  transito origen de la remisión generada
		whIdTarget = devolutionReference.getTargetTransitWh().getId();//Ubicación  transito destino de la remisión generada
		this.moveElementsBetweenWarehouses(whIdSource, whIdTarget, reportedElementVOs, UtilsBusiness.copyObject(ReferenceVO.class, refInconsistencyVO.getReference()),statusSended, false, user, reportIBS);
		this.businessReferenceModification.createReferenceModification(devolutionReference.getId() , CodesBusinessEntityEnum.REFERENCE_MODIFICATION_SHIPMENT.getCodeEntity(), user.getId());
		
		//actualizar estado de los elementos de la remisión de devolución a enviado
		//this.updateReportedElements(reportedElementVOs, statusSended);
		this.updateRefElementsStatus(devolutionReference, refStatusSended, devolutionReferenceElements, statusSended);
		
		//cuarto movimiento
		ItemStatus receptedStatus = daoItemStatus.getItemStatusRecepted();
		String refStatusRecepted = CodesBusinessEntityEnum.REFERENCE_STATUS_RECEPTED.getCodeEntity();
		whIdSource = devolutionReference.getTargetTransitWh().getId();//Ubicación  transito destino de la remisión generada
		whIdTarget = devolutionReference.getWarehouseByTargetWh().getId();//Ubicación  destino de la remisión generada
		this.moveElementsBetweenWarehouses(whIdSource, whIdTarget, reportedElementVOs, UtilsBusiness.copyObject(ReferenceVO.class, refInconsistencyVO.getReference()),receptedStatus, false, user, reportIBS);
		
		//actualizar estado de los elementos de la remisión de devolución a recepcionado
		//this.updateReportedElements(reportedElementVOs,receptedStatus);
		this.updateRefElementsStatus(devolutionReference, refStatusRecepted, devolutionReferenceElements, receptedStatus);
		this.businessReferenceModification.createReferenceModification( devolutionReference.getId() , CodesBusinessEntityEnum.REFERENCE_MODIFICATION_RECEPTED.getCodeEntity(), user.getId());
		
		//cerrar la inconsistencia y asociarla con la remisión generada
		this.closeRefInconsistencyRecord(refInconsistencyVO, devolutionReference);
		
        //actualizar el estado de la remisión de acuerdo a las reglas definidas
        ReferenceStatus refStatus = null;
        String refStatusMod = null;
        if(areAllRefInconsistenciesClosed(refInconsistencyVO.getReference().getId())) {
        	
        	if(this.areAllReferenceElementsInState(refInconsistencyVO.getReference().getId(), CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity())) {
        		refStatus = daoReferenceStatus.getReferenceByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_RECEPTED.getCodeEntity());
        		refStatusMod = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_RECEPTED.getCodeEntity();
        	}else if(this.areAllReferenceElementsInState(refInconsistencyVO.getReference().getId(), CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity())) {
        		refStatus = daoReferenceStatus.getReferenceByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity());
        		refStatusMod = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_SHIPMENT.getCodeEntity();
        	}else/*(areSomeReferenceElementsInState(refInconsistencyVO.getReference().getId(), CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getCodeEntity())) */{
        		refStatus = daoReferenceStatus.getReferenceByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity());
        		refStatusMod = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_PARTIALLY_CONFIRMED.getCodeEntity();
        	}
        	
        	/*
        	if(areSomeReportedElementsInState(reportedElementVOs, CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getCodeEntity())) {
        		refStatus = daoReferenceStatus.getReferenceByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity());
        		refStatusMod = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_PARTIALLY_CONFIRMED.getCodeEntity();
        	}
        	if(this.areAllReportedElementsInState(reportedElementVOs, CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity())) {
        		refStatus = daoReferenceStatus.getReferenceByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_RECEPTED.getCodeEntity());
        		refStatusMod = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_RECEPTED.getCodeEntity();
        	}
        	if(this.areAllReportedElementsInState(reportedElementVOs, CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity())) {
        		refStatus = daoReferenceStatus.getReferenceByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity());
        		refStatusMod = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_SHIPMENT.getCodeEntity();
        	} throw new BusinessException("");
        	*/
        	if(refStatus != null) {
	        	ref.setReferenceStatus(refStatus);
	            daoReference.updateReference(ref);
	            //Crea el registro en las modificaciones de remision
	            if ( refInconsistencyVO.getAnswerUserId() != null ){
	            	this.businessReferenceModification.createReferenceModification( ref.getId() , refStatusMod, refInconsistencyVO.getAnswerUserId() );
	            }
        	}
        }
        
        //invocar INV-08 informando del cierre de la inconsistencia
        referenceMailSender.sendRefInconsistencyLessElementsClosedMail(refInconsistencyVO, devolutionReference, reportedElementVOs);
		
		return devolutionReference.getId();
		
	}

	private void confirmReceptedElements(List<ReportedElementVO> reportedElementVOs, Long userId) throws BusinessException {
		
		if(reportedElementVOs != null) {
			for (ReportedElementVO reportedElementVO : reportedElementVOs) {
				RefConfirmation confirmation = new RefConfirmation();
				confirmation.setConfirmedQuantity(reportedElementVO.getReferenceElementItem().getRefQuantity());
				confirmation.setReferenceElementItem(reportedElementVO.getReferenceElementItem());
				saveConfirmation(confirmation, userId, 0D);
			}
		}
		
	}
	
	//retorna los elementos que se agregaron a la remisión
	private List<ReferenceElementItem> addReportedElementsToDevolutionReference(List<ReportedElementVO> reportedElementVOs,
			Reference devolutionReference) throws BusinessException, DAOServiceException, DAOSQLException {
		List<ReferenceElementItem> devolutionReferenceElements = new ArrayList<ReferenceElementItem>();
		if(reportedElementVOs != null) {
			for (ReportedElementVO reportedElementVO : reportedElementVOs) {
				ReferenceElementItem referenceElement = reportedElementVO.getReferenceElementItem();
				
				ReferenceElementItem devolutionReferenceElement = UtilsBusiness.copyObject(ReferenceElementItem.class, referenceElement);
				devolutionReferenceElement.setId(null);
				devolutionReferenceElement.setRefQuantity(reportedElementVO.getQty());
				if(referenceElement != null) {//puede ser nulo cuando la inconsistencia es por mas elementos
					
					if(devolutionReference != null) {
						devolutionReferenceElement.setReference(devolutionReference);
					}
					
					daoReferenceElementItem.createReferenceElementItem(devolutionReferenceElement);
					
					devolutionReferenceElements.add(devolutionReferenceElement);
					
				}
			}
		}
		return devolutionReferenceElements;
	}

	/**
	 * Metodo: identifica si ALGUN elemento de una inconsistencia tiene el estado especificado
	 * @param reportedElementVOs
	 * @param itemStatus
	 * @return verdadero si algún elemento de una inconsistencia tiene el estado especificado
	 * @author wjimenez
	 */
	private boolean areSomeReportedElementsInState(List<ReportedElementVO> reportedElementVOs, String itemStatus) throws BusinessException {
		try {
			for (ReportedElementVO reportedElementVO : reportedElementVOs) {
				if (reportedElementVO.getReferenceElementItem().getItemStatus().getStatusCode().equals(itemStatus)) {
					return true;
				}
			}
		} catch (NullPointerException e) {
			throw new BusinessException("la estructura reportedElementVO.getReferenceElementItem().getItemStatus().getStatusCode() no se pudo consultar por valores nulos");
		}
		return false;
	}

	public boolean areAllReferenceElementsInState(Long refId, String status) throws DAOServiceException, DAOSQLException {
		return daoReferenceElementItem.areAllReferenceElementsInState(refId, status);
	}
	
	public boolean areSomeReferenceElementsInState(Long refId, String status) throws DAOServiceException, DAOSQLException {
		return daoReferenceElementItem.areSomeReferenceElementsInState(refId, status);
	}
	
	/**
	 * Metodo: identifica si TODOS los elementos de una inconsistencia tienen el estado especificado
	 * @param reportedElementVOs
	 * @param itemStatus
	 * @return verdadero si TODOS los elementos de una inconsistencia tiene el estado especificado
	 * @author wjimenez
	 */
	private boolean areAllReportedElementsInState(List<ReportedElementVO> reportedElementVOs, String itemStatus) throws BusinessException {
		
		try {
			for (ReportedElementVO reportedElementVO : reportedElementVOs) {
				if(reportedElementVO.getReferenceElementItem() != null) {
					if (!reportedElementVO.getReferenceElementItem().getItemStatus().getStatusCode().equals(itemStatus)) {
						return false;
					}
				} else {
					//si reportedElementVO.getReferenceElementItem() es nulo, es por que la inconsistencia es "mas elementos".
					//esto causa que no se pueda seguir la regla: El sistema cambia el estado de la remisión a estado “Enviado” si todos los elementos se encuentran en estado enviado
					//wjimenez mientras se define lo anteriormente dicho, se asume que no todos los elementos corresponden con el estado especificado
					return false;
				}
			}
		} catch (NullPointerException e) {
			throw new BusinessException("la estructura reportedElementVO.getReferenceElementItem().getItemStatus().getStatusCode() no se pudo consultar por valores nulos");
		}
		return true;
	}
	
	private boolean areAllRefInconsistenciesClosed(Long refId) throws DAOServiceException, DAOSQLException, PropertiesException {
		return refInconsistencyDAO.areAllRefInconsistenciesInStatus(refId, CodesBusinessEntityEnum.INCONSISTENCY_REFERENCE_STATUS_CLOSE.getCodeEntity());
	}

	/**
	 * Metodo: generar una remisión de devolución para poder cerrar inconsistencias de remisiones
	 * de tipo menos elementos físicos
	 * @param refInconsistencyVO
	 * @param reportedElementVOs
	 * @return remisión de devolución
	 * @author wjimenez
	 * @throws BusinessException 
	 */
	private Reference createDevolutionReference(RefInconsistencyVO refInconsistencyVO, List<ReportedElementVO> reportedElementVOs, User user) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException {
		
		Reference devolutionReference = UtilsBusiness.copyObject(Reference.class, refInconsistencyVO.getReference());
		//asegurarse de crear una nueva remisión
		devolutionReference.setId(null);
		
		devolutionReference.setTransportCompany(null);
		devolutionReference.setTransportGuide(null);
		devolutionReference.setVehiclePlate(null);
		devolutionReference.setVolume(null);
		devolutionReference.setSendUnits(null);
		devolutionReference.setDeliveryDate(null);
		devolutionReference.setDriverName(null);
		
		devolutionReference.setCreationReferenceDate(UtilsBusiness.fechaActual());
		
		Reference referenceInconsistent = refInconsistencyVO.getReference();
		devolutionReference.setParentReferenceId(referenceInconsistent.getId());
		
		devolutionReference.setWarehouseBySourceWh(referenceInconsistent.getWarehouseByTargetWh());
		devolutionReference.setWarehouseByTargetWh(referenceInconsistent.getWarehouseBySourceWh());
		
		Warehouse sourceTransitWh = obtainSourceTransitWarehouse(devolutionReference.getWarehouseBySourceWh());
		Warehouse targetTransitWh = obtainTargetTransitWarehouse(devolutionReference.getWarehouseByTargetWh());
		
		devolutionReference.setSourceTransitWh(sourceTransitWh);
		devolutionReference.setTargetTransitWh(targetTransitWh);
		
		devolutionReference.setReferenceStatus(daoReferenceStatus.getReferenceByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity()));
		
		User createUserId = new User(refInconsistencyVO.getAnswerUserId());
		devolutionReference.setCreateUserId(createUserId);
		
		daoReference.createReference(devolutionReference);
		
		this.businessReferenceModification.createReferenceModification( devolutionReference.getId() , CodesBusinessEntityEnum.REFERENCE_MODIFICATION_TYPE_CREATED.getCodeEntity(), user.getId());
		
		return devolutionReference;
	}

	private void closeRefInconsistencyRecord(RefInconsistencyVO refInconsistencyVO, Reference devolutionReference) throws BusinessException, PropertiesException, DAOServiceException, DAOSQLException {
		refInconsistencyVO.setAnswerDate(UtilsBusiness.fechaActual());
		refInconsistencyVO.setRefIncStatus(refIncStatusDAO.getRefIncStatusByCode(CodesBusinessEntityEnum.INCONSISTENCY_REFERENCE_STATUS_CLOSE.getCodeEntity()));
		if(devolutionReference != null) {
			refInconsistencyVO.setGeneratedReference(devolutionReference);
		}
		businessRefInconsistency.updateRefInconsistency(refInconsistencyVO);
	}

	private void updateReportedElements(List<ReportedElementVO> reportedElementVOs, ItemStatus itemStatus) throws DAOServiceException, DAOSQLException {
		if(reportedElementVOs != null) {
			for (ReportedElementVO reportedElementVO : reportedElementVOs) {
				ReferenceElementItem referenceElement = reportedElementVO.getReferenceElementItem();
				if(referenceElement != null) {//puede ser nulo cuando la inconsistencia es por mas elementos
					if(itemStatus != null) {
						referenceElement.setItemStatus(itemStatus);
					}
					daoReferenceElementItem.updateReferenceElementItem(referenceElement);
				}
			}
		}
	}
	
	private void updateRefElementsStatus(Reference reference, String refStatusCode, List<ReferenceElementItem> devolutionReferenceElements,
			ItemStatus itemStatus) throws DAOServiceException, DAOSQLException {
		
		if(reference != null && !StringUtils.isBlank(refStatusCode)) {
			ReferenceStatus refStatus = daoReferenceStatus.getReferenceByCode(refStatusCode);
			reference.setReferenceStatus(refStatus);
			daoReference.updateReference(reference);
			
		}
		
		if(devolutionReferenceElements != null) {
			for (ReferenceElementItem referenceElement : devolutionReferenceElements) {
				if(referenceElement != null) {
					referenceElement.setItemStatus(itemStatus);
					daoReferenceElementItem.updateReferenceElementItem(referenceElement);
				}
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#confirmReferenceElementsReception(java.lang.Long,co.com.directv.sdii.model.vo.UserVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void confirmReferenceElementsReception(Long referenceId,UserVO user)throws BusinessException {
		log.debug("== Inicia confirmReferenceElementsReception/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(referenceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());        
        UtilsBusiness.assertNotNull(user, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(user.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	//Consulto el usuario para obtenera parametros adicionales
        	user = UtilsBusiness.copyObject(UserVO.class, daoUser.getUserById(user.getId()));
        	if(user==null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
        	
        	//Obtengo el objeto de la BD
        	Reference referencePojo = this.daoReference.getReferenceByID(referenceId);
        	if( referencePojo == null 
        			|| ( !referencePojo.getReferenceStatus().getRefStatusCode().equalsIgnoreCase( CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity() ) 
        					&& !referencePojo.getReferenceStatus().getRefStatusCode().equalsIgnoreCase( CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() ) ) ){
        		Object params[]={referenceId.toString()};
        		List<String> paramsList = new ArrayList<String>();
				paramsList.add(referenceId.toString());
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN393.getCode(), ErrorBusinessMessages.STOCK_IN393.getMessage(params), paramsList);
        	}
        	
        	/*BLOQUE PARA ACTUALIZAR EL ESTADO DE LA REMISION*/
        	ReferenceStatus newReferenceStatus = new ReferenceStatus();
        	newReferenceStatus.setId(CodesBusinessEntityEnum.REFERENCE_STATUS_RECEPTED.getIdEntity(ReferenceStatus.class.getName()));
        	//Actualizo el estado de la remision a recepcionado
        	referencePojo.setReferenceStatus(newReferenceStatus);        	
        	daoReference.updateReference(referencePojo);
        	//Crea el registro en las modificaciones de remision
            this.businessReferenceModification.createReferenceModification( referencePojo.getId() , CodesBusinessEntityEnum.REFERENCE_MODIFICATION_RECEPTED.getCodeEntity(), user.getId());
            
            if( referencePojo.getTargetTransitWh() != null 
        			&& referencePojo.getTargetTransitWh().getId().longValue() > 0 ){
        		WarehouseVO whSource = new WarehouseVO();
        		whSource.setId( referencePojo.getTargetTransitWh().getId() );
        		/*BLOQUE INVOCA CU 12 MOVIMIENTO DE NO SERIALIZADOS ENTRE BODEGAS*/
        		MassiveMovementBetweenWareHouseDTO dto = new MassiveMovementBetweenWareHouseDTO();
        		dto.setReference( referencePojo );
        		dto.setWareHouseSource( whSource );
        		dto.setIsSerialized( CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity() );
        		dto.setValidateElementsToConfirm( false );
        		dto.setUserId( user.getId() );
        		this.businessRefElementItems.massiveMovementOfReferenceElementItems(dto, user.getId());
        		
        		dto.setIsSerialized( CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity() );
        		this.businessRefElementItems.massiveMovementOfReferenceElementItems(dto, user.getId());
        	}        	
        	
        	/*BLOQUE INVOCA CU 08 ENVIAR CORREO ELECTRONICO*/
        	sendEmail(user, referencePojo, EmailTypesEnum.REFERENCES_ELEMENTS_HAS_BEEN_RECEIVED.getEmailTypecode(), ApplicationTextEnum.CONFIRMATION_REFERRAL.getApplicationTextValue());
        	
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación confirmReferenceElementsReception/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina confirmReferenceElementsReception/ReferenceBusinessBean ==");
        }
	}
	
	/**
	 * Metodo: Permite enviar un mail asincronamente
	 * @param user UserVO usuario que realiza la operación
	 * @param referencePojo Reference Remisión a la que se va a hacer referencia
	 * @param emailType Tipo de novedad
	 * @param mailObservation Observaciones que van en el correo
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void sendEmail(UserVO user, Reference referencePojo, String emailType,String mailObservation) throws BusinessException{
		log.debug("== Inicia sendEmail/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(user, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());        
        UtilsBusiness.assertNotNull(referencePojo, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(emailType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(mailObservation, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	final SendEmailDTO email = new SendEmailDTO();
            
        	email.setNewsType( emailType );
            email.setNewsDocument( referencePojo.getId().toString() );
            email.setNewsObservation( mailObservation );
            email.setNewsSourceUser( user.getName() );
            
            List<String> recipients = new ArrayList<String>();
            String sourceResponsableMail = daoWarehouse.getWarehouseByID(referencePojo.getWarehouseBySourceWh().getId()).getResponsibleEmail();
            String targetResponsableMail = daoWarehouse.getWarehouseByID(referencePojo.getWarehouseByTargetWh().getId()).getResponsibleEmail();
            if( sourceResponsableMail != null )
            	recipients.add(sourceResponsableMail);
            if( targetResponsableMail != null )
            	recipients.add(targetResponsableMail);
    		
    		email.setRecipient(recipients);
    		
    		businessEmailType.sendEmailByEmailTypeCodeAsic(email, referencePojo.getCountryCodeId().getId());
			
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación sendEmail/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina sendEmail/ReferenceBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#confirmNotSerializeReferenceElementsReception(java.lang.Long,co.com.directv.sdii.model.vo.UserVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void confirmNotSerializeReferenceElementsReception(Long referenceId,UserVO user)throws BusinessException {
		log.debug("== Inicia confirmNotSerializeReferenceElementsReception/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(referenceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());        
        UtilsBusiness.assertNotNull(user, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(user.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	//Consulto el usuario para obtenera parametros restantes
        	user = UtilsBusiness.copyObject(UserVO.class, daoUser.getUserById(user.getId()));
        	if(user==null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
        	
        	//Obtengo la remision de la BD
        	Reference referencePojo = this.daoReference.getReferenceByID(referenceId);
        	//Valido que no este recepcionada
        	if( referencePojo == null 
        			|| ( !referencePojo.getReferenceStatus().getRefStatusCode().equalsIgnoreCase( CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity() ) 
        					&& !referencePojo.getReferenceStatus().getRefStatusCode().equalsIgnoreCase( CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() ) ) ){
        		Object params[]={referenceId.toString()};
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN393.getCode(), ErrorBusinessMessages.STOCK_IN393.getMessage(params));
        	}
        	
        	//Se confirman los elementos no serializados
        	if( referencePojo.getTargetTransitWh() != null 
        			&& referencePojo.getTargetTransitWh().getId().longValue() > 0 ){
        		WarehouseVO whSource = new WarehouseVO();
        		whSource.setId( referencePojo.getTargetTransitWh().getId() );
        		/*BLOQUE INVOCA CU 12 MOVIMIENTO DE NO SERIALIZADOS ENTRE BODEGAS*/
        		MassiveMovementBetweenWareHouseDTO dto = new MassiveMovementBetweenWareHouseDTO();
        		dto.setReference( referencePojo );
        		dto.setWareHouseSource( whSource );
        		dto.setIsSerialized( CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity() );
        		dto.setValidateElementsToConfirm( true );
        		dto.setUserId( user.getId() );
        		this.businessRefElementItems.massiveMovementOfReferenceElementItems(dto, user.getId());
        	}
        	String referenceNewStatusCode = null;
        	String modificationStatusCode;
        	boolean allConfirmed = true;
        	//Se verifica si la remision tiene elementos que no esten en recepcionado
        	List<ReferenceElementItem> notReceptedElements = this.daoReferenceElementItem.getReferenceElementItemByReferenceIdAndByNotItemStatus( referencePojo.getId() , CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity());
        	
        	if( notReceptedElements != null && !notReceptedElements.isEmpty() ){
        		referenceNewStatusCode = CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity();
        		modificationStatusCode = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_PARTIALLY_CONFIRMED.getCodeEntity();
        		allConfirmed = false;
        	} else{
        		referenceNewStatusCode = CodesBusinessEntityEnum.REFERENCE_STATUS_RECEPTED.getCodeEntity();
        		modificationStatusCode = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_RECEPTED.getCodeEntity();        		
        	}
        	//Actualiza el estado de la remision
        	ReferenceStatus newReferenceStatus = this.daoReferenceStatus.getReferenceByCode(referenceNewStatusCode);
        	referencePojo.setReferenceStatus(newReferenceStatus);
        	this.daoReference.updateReference(referencePojo);
        	//Crea el registro en las modificaciones de remision
            this.businessReferenceModification.createReferenceModification( referencePojo.getId() , modificationStatusCode, user.getId());
            /*BLOQUE INVOCA CU 08 ENVIAR CORREO ELECTRONICO*/
            if(allConfirmed)
            	sendEmail(user, referencePojo, EmailTypesEnum.REFERENCES_ELEMENTS_HAS_BEEN_RECEIVED.getEmailTypecode(), ApplicationTextEnum.CONFIRMATION_REFERRAL.getApplicationTextValue());
            else
            	sendEmail(user, referencePojo, EmailTypesEnum.REFERENCES_ELEMENTS_HAS_BEEN_PARTIALLY_SENDED.getEmailTypecode(), ApplicationTextEnum.CONFIRMATION_REFERRAL.getApplicationTextValue());
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación confirmNotSerializeReferenceElementsReception/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina confirmNotSerializeReferenceElementsReception/ReferenceBusinessBean ==");
        }
	}
	
	/**
	 * Metodo: actualiza a recepcionada los elementos serializados de una remision
	 * @param referenceId Id de la remision
	 * @param user Usuario que esta confirmando la remision
	 * @param isSerialize True si es para actulaizar a recepcionado los elementos serializados, False para los NO serializados
	 * @throws BusinessException
	 */
	public void updateToReceptedNotSerailizeStatus(Long referenceId,UserVO user,boolean isSerialize) throws BusinessException{
		try{
			List<ReferenceElementItemVO> elements = null;
			if(isSerialize){
				elements = UtilsBusiness.convertList( daoReferenceElementItem.getSerializeReferenceElementItemByReferenceIdAndByNotItemStatus(referenceId, CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity()), ReferenceElementItemVO.class);
			} else {
				elements = UtilsBusiness.convertList( daoReferenceElementItem.getNotSerializeReferenceElementItemByReferenceIdAndByNotItemStatus(referenceId, CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity()), ReferenceElementItemVO.class);
			}
	    	//Actualizo el estado de los elementos y se hace el movimiento uno por uno
	    	if( elements != null ){
	    		for(ReferenceElementItemVO vo : elements){
	    			//Obtengo la suma de las confirmacion que se han hecho
	    			Double actualConfirmations = daoRefConfirmation.countElementConfirmedQuatity(vo.getId());
	    			if(actualConfirmations != null  && vo.getRefQuantity() > actualConfirmations){
	    				//Se crea una confirmacion con vo.getRefQuantity() - deliveriesQuantity
	    				RefConfirmation confirmation = new RefConfirmation();
	    				confirmation.setConfirmedQuantity(vo.getRefQuantity() - actualConfirmations);
	    				confirmation.setReferenceElementItem(UtilsBusiness.copyObject(ReferenceElementItem.class, vo));
	    				saveConfirmation(confirmation, user.getId(), 0D);
	    			} 
	    		}
	    	}
	    	/*BLOQUE PARA ACTUALIZAR EL ESTADO DE LOS ELEMENTOS*/
        	Long itemNewStatusId = CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getIdEntity( ItemStatus.class.getName() );
        	this.businessRefElementItems.updateElementItemsByReferenceId(referenceId,itemNewStatusId);
		}catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateToReceptedNotSerailizeStatus/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateToReceptedNotSerailizeStatus/ReferenceBusinessBean ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#saveConfirmation(co.com.directv.sdii.model.pojo.RefConfirmation, java.lang.Long, java.lang.Double)
	 */
	@Override
	public void saveConfirmation(RefConfirmation confirmationPojo , Long userId , Double pendQuantity) throws BusinessException {
		log.debug("== Inicia saveConfirmation/RefConfirmationBusinessBean ==");
		try {
			confirmationPojo.setComfirmDate(UtilsBusiness.fechaActual());
			confirmationPojo.setUserId(userId);
			confirmationPojo.setPendQuantity(pendQuantity);
			daoRefConfirmation.createRefConfirmation(confirmationPojo);
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación saveConfirmation/RefConfirmationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina saveConfirmation/RefConfirmationBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#confirmSerializeReferenceElementsReception(java.lang.Long,co.com.directv.sdii.model.vo.UserVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void confirmSerializeReferenceElementsReception(Long referenceId,UserVO user)throws BusinessException {
		log.debug("== Inicia confirmSerializeReferenceElementsReception/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(referenceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());        
        UtilsBusiness.assertNotNull(user, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(user.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	//Consulto el usuario para obtenera parametros adicionales
        	user = UtilsBusiness.copyObject(UserVO.class, daoUser.getUserById(user.getId()));
        	if(user==null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
        	
        	//Obtengo la remision de la BD
        	Reference referencePojo = this.daoReference.getReferenceByID(referenceId);
        	//Valido que no este recepcionada
        	if( referencePojo == null 
        			|| ( !referencePojo.getReferenceStatus().getRefStatusCode().equalsIgnoreCase( CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity() ) 
        					&& !referencePojo.getReferenceStatus().getRefStatusCode().equalsIgnoreCase( CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() ) ) ){
        		Object params[]={referenceId.toString()};
        		List<String> paramsList = new ArrayList<String>();
				paramsList.add(referenceId.toString());
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN393.getCode(), ErrorBusinessMessages.STOCK_IN393.getMessage(params), paramsList);
        	}
        	
        	//Se confirman los elementos no serializados
        	if( referencePojo.getTargetTransitWh() != null 
        			&& referencePojo.getTargetTransitWh().getId().longValue() > 0 ){
        		WarehouseVO whSource = new WarehouseVO();
        		whSource.setId( referencePojo.getTargetTransitWh().getId() );
        		/*BLOQUE INVOCA CU 12 MOVIMIENTO DE NO SERIALIZADOS ENTRE BODEGAS*/
        		MassiveMovementBetweenWareHouseDTO dto = new MassiveMovementBetweenWareHouseDTO();
        		dto.setReference( referencePojo );
        		dto.setWareHouseSource( whSource );
        		dto.setIsSerialized( CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity() );
        		dto.setValidateElementsToConfirm( true );
        		dto.setUserId( user.getId() );
        		this.businessRefElementItems.massiveMovementOfReferenceElementItems(dto, user.getId());
        	}
        	String referenceNewStatusCode = null;
        	String modificationStatusCode;
        	boolean allConfirmed = true;
        	//Se verifica si la remision tiene elementos que no esten en recepcionado
        	List<ReferenceElementItem> notReceptedElements = this.daoReferenceElementItem.getReferenceElementItemByReferenceIdAndByNotItemStatus( referencePojo.getId() , CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity());
        	
        	if( notReceptedElements != null && !notReceptedElements.isEmpty() ){
        		referenceNewStatusCode = CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity();
        		modificationStatusCode = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_PARTIALLY_CONFIRMED.getCodeEntity();
        		allConfirmed = false;
        	} else{
        		referenceNewStatusCode = CodesBusinessEntityEnum.REFERENCE_STATUS_RECEPTED.getCodeEntity();
        		modificationStatusCode = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_RECEPTED.getCodeEntity();        		
        	}
        	//Actualiza el estado de la remision
        	ReferenceStatus newReferenceStatus = this.daoReferenceStatus.getReferenceByCode(referenceNewStatusCode);
        	referencePojo.setReferenceStatus(newReferenceStatus);
        	this.daoReference.updateReference(referencePojo);
        	//Crea el registro en las modificaciones de remision
            this.businessReferenceModification.createReferenceModification( referencePojo.getId() , modificationStatusCode, user.getId());
            /*BLOQUE INVOCA CU 08 ENVIAR CORREO ELECTRONICO*/
            if(allConfirmed)
            	sendEmail(user, referencePojo, EmailTypesEnum.REFERENCES_ELEMENTS_HAS_BEEN_RECEIVED.getEmailTypecode(), ApplicationTextEnum.CONFIRMATION_REFERRAL.getApplicationTextValue());
            else
            	sendEmail(user, referencePojo, EmailTypesEnum.REFERENCES_ELEMENTS_HAS_BEEN_PARTIALLY_SENDED.getEmailTypecode(), ApplicationTextEnum.CONFIRMATION_REFERRAL.getApplicationTextValue());
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación confirmSerializeReferenceElementsReception/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina confirmSerializeReferenceElementsReception/ReferenceBusinessBean ==");
        }
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByReferenceAndWhAndByCountry(java.lang.Long, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountry(Long ref,
			WarehouseVO whSource, WarehouseVO whTarget,Long country)
			throws BusinessException {
		log.debug("== Inicia getReferencesByReferenceAndWhAndByCountry/ReferenceBusinessBean ==");
        List<ReferenceVO> references = new ArrayList<ReferenceVO>();
        try {
        	if(ref==0){
        		if(whSource==null||whTarget==null){
        			if(whSource==null&&whTarget!=null){
        				UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        				references = UtilsBusiness.convertList(this.daoReference.getReferencesByTargetWareHouse(whTarget.getId()), ReferenceVO.class);
        			}else{
        				if(whSource!=null){
            				UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            				references = UtilsBusiness.convertList(this.daoReference.getReferencesBySourceWareHouse(whSource.getId()), ReferenceVO.class);
        				}
        			}
        		}else{
        			UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			references = this.getReferencesBySourceAndTargetWareHouseAndByCountry(whSource, whTarget,country);
        		}
        	}else{
        		if(whSource==null||whTarget==null){
        			if(whSource==null&&whTarget==null){
        				Reference refTmp = this.getReferenceByID(ref);
        				if(refTmp!=null){
        					references.add(UtilsBusiness.copyObject(ReferenceVO.class,refTmp));
        				}
        			}else{
	        			if(whTarget==null){
	        				UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        				references = UtilsBusiness.convertList(this.daoReference.getReferencesByReferenceAndSourceWh(ref,whSource.getId()), ReferenceVO.class);
	        			}else{
	        				UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        				references = UtilsBusiness.convertList(this.daoReference.getReferencesByReferenceAndTargerWh(ref,whTarget.getId()), ReferenceVO.class);
	        			}
        			}
        		}else{
        			UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			references = UtilsBusiness.convertList(this.daoReference.getReferencesByReferenceAndWh(ref,whSource.getId(),whTarget.getId()), ReferenceVO.class);
        		}   
        	}
            return references;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesByReferenceAndWhAndByCountry/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesByReferenceAndWhAndByCountry/ReferenceBusinessBean ==");
        }
	}
	
	/**
	 * Metodo: Permite consultar las remisiones asociadas a un número de remisión y bodegas
	 * CU Inv-35
	 * @param ref - Long Identificador de la referencia; nulo si no hace parte de la consulta
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta 
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a la remisión con bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su identificador y bodegas
	 * @author gfadnino
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ReferenceVO> getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndDifferentDealerWhs(Long ref,WarehouseVO whSource,WarehouseVO whTarget,Long country)throws BusinessException{
		log.debug("== Inicia getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndDifferentDealerWhs/ReferenceBusinessBean ==");
        
        try {
        	List<ReferenceVO> references = getReferencesByReferenceAndWhAndByCountry(ref, whSource, whTarget, country);
        	List<Reference> referencesPojo = UtilsBusiness.convertListWithoutExtends(references, Reference.class);
        	List<Reference> finalResult = filterReferencesDifferentDealers(true, referencesPojo);
        	references = UtilsBusiness.convertList(finalResult, ReferenceVO.class);
        	
            return references;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndDifferentDealerWhs/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndDifferentDealerWhs/ReferenceBusinessBean ==");
        }
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByReferenceAndWh(java.lang.Long, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ReferenceResponse getReferencesByReferenceAndWhAndRefStatusRefStatus(Long ref,
			WarehouseVO whSource, WarehouseVO whTarget, ReferenceStatusVO refStatus, Long userId, RequestCollectionInfo requestCollectionInfo )
			throws BusinessException {
		log.debug("== Inicia getReferencesByReferenceAndWhAndRefStatus/ReferenceBusinessBean ==");
        
        try {
        	ReferenceResponse response = new ReferenceResponse();
        	User user = daoUser.getUserById(userId);
        	response = this.daoReference.getReferencesByReferenceAndWhRefStatus(ref,whSource.getId(),whTarget.getId(), refStatus.getId(),user, requestCollectionInfo);
        	response.setReferencesVO(UtilsBusiness.convertList(response.getReferences(), ReferenceVO.class));
        	response.setReferences(null);
        	
        	//Adicionar nombres de las ubicaciones
        	for(ReferenceVO referenceTmp: response.getReferencesVO()){
        		WarehouseVO sourceWh = UtilsBusiness.copyObject(WarehouseVO.class,referenceTmp.getWarehouseBySourceWh());
        		this.businessWarehouse.genWareHouseName(sourceWh);
        		referenceTmp.setWhSource(sourceWh.getWarehouseName());

        		WarehouseVO targetWh = UtilsBusiness.copyObject(WarehouseVO.class,referenceTmp.getWarehouseByTargetWh());
        		this.businessWarehouse.genWareHouseName(targetWh);
        		referenceTmp.setWhTarget(targetWh.getWarehouseName());
        		
        	}
            return response;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesByReferenceAndWhAndRefStatus/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesByReferenceAndWhAndRefStatus/ReferenceBusinessBean ==");
        }
	}
	
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByConfirmationDateAndByCountry(java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ReferenceResponse getReferencesByConfirmationDateAndByCountry(Long dealer,Long country, RequestCollectionInfo requestCollectionInfo) throws BusinessException{
		log.debug("== Inicia getReferencesByConfirmationDateAndByCountry/ReferenceBusinessBean ==");
		if(dealer == null || dealer <= 0){
			log.error("== ID del usuario requerido ==");
			throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		}
        try {
        	Warehouse warehouse=null;
        	WarehouseVO warehouseVO=null;
        	ReferenceResponse referenceResponse = daoReference.getReferencesByConfirmationDateAndByCountry(dealer, country,requestCollectionInfo);
        	List<Reference> references = referenceResponse.getReferences();
        	List<ReferenceVO> referencesVO=null;
        	if(references != null){
        		referencesVO = UtilsBusiness.convertList(references, ReferenceVO.class);
        		for (ReferenceVO referenceVO : referencesVO) {
        			
        			warehouse=referenceVO.getWarehouseByTargetWh();
        			if(warehouse != null){
        				warehouseVO=UtilsBusiness.copyObject(WarehouseVO.class, warehouse);
        				businessWarehouse.genWareHouseName(warehouseVO);
        				referenceVO.setWhTarget(warehouseVO.getWarehouseName());
        			}
        			
        			warehouse=referenceVO.getWarehouseBySourceWh();
        			if(warehouse != null){
	        			warehouseVO=UtilsBusiness.copyObject(WarehouseVO.class, warehouse);
	        			businessWarehouse.genWareHouseName(warehouseVO);
	        			referenceVO.setWhSource(warehouseVO.getWarehouseName());
        			}
        			

				}
        	} 
        	referenceResponse.setReferences(null);
        	referenceResponse.setReferencesVO(referencesVO);
        	return referenceResponse;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesByConfirmationDate/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesByConfirmationDate/ReferenceBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByConfirmationDate(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ReferenceVO>getReferenceByIdAndWarehouseTarget( Long referenceId,Long warehouseId )throws BusinessException
	{
		log.debug("== Inicia getReferenceByIdAndWarehouseTarget/ReferenceBusinessBean ==");
       
		try {
             
			return UtilsBusiness.convertList( daoReference.getReferenceByIdAndWarehouseTarget(referenceId, warehouseId) , ReferenceVO.class);
        
		} catch(Throwable ex){
        	log.debug("== Error getReferenceByIdAndWarehouseTarget/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceByIdAndWarehouseTarget/ReferenceBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByConfirmationDate(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<RefConfirmationVO> getConfirmationsByReferenceId( Long referenceId ) throws BusinessException
	 {
		log.debug("== Inicia getReferenceByIdAndWarehouseTarget/ReferenceBusinessBean ==");
	       
		try {
			
			UtilsBusiness.assertNotNull(referenceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage()); 
			return UtilsBusiness.convertList( refConfirmationDAO.getConfirmationsByReferenceId(referenceId), RefConfirmationVO.class);
        
		} catch(Throwable ex){
        	log.debug("== Error getReferenceByIdAndWarehouseTarget/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceByIdAndWarehouseTarget/ReferenceBusinessBean ==");
        }
	 }
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByConfirmationDate(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<RefConfirmationVO> getConfirmationsByReferenceIdAndElementId( Long referenceId,Long elementId ) throws BusinessException
	{
		log.debug("== Inicia getConfirmationsByReferenceIdAndElementId/ReferenceBusinessBean ==");
	       
		try {
			
			UtilsBusiness.assertNotNull(referenceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage()); 
			
			return UtilsBusiness.convertList( refConfirmationDAO.getConfirmationsByReferenceIdAndElementId( referenceId,elementId ), RefConfirmationVO.class);
        
		} catch(Throwable ex){
        	log.debug("== Error getConfirmationsByReferenceIdAndElementId/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getConfirmationsByReferenceIdAndElementId/ReferenceBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByConfirmationDate(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	  public List<ReferenceVO>getReferenceByIdAndWarehouseSourceAndWareHouseTarget( Long referenceId,Long whSourceId,Long whTargetId )throws BusinessException
	  {
		log.debug("== Inicia getReferenceByIdAndWarehouseSource/ReferenceBusinessBean ==");
	       
		try {
		       if( referenceId!=null && referenceId<0 )
		    	     referenceId = null;
			
		       if( whSourceId!=null && whSourceId<0 )
		    	   whSourceId = null;
		       
		       if( whTargetId!=null && whTargetId<0 )
		    	   whTargetId = null;
			
			return UtilsBusiness.convertList( daoReference.getReferenceByIdAndWarehouseSourceAndWareHouseTarget(referenceId, whSourceId,whTargetId, CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity()) , ReferenceVO.class);
        
		} catch(Throwable ex){
        	log.debug("== Error getConfirmationsByReferenceIdAndElementId/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceByIdAndWarehouseSource/ReferenceBusinessBean ==");
        }
	  }
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByConfirmationDate(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	  public List<ReferenceVO>getReferenceByIdAndWarehouseSource( Long referenceId,Long warehouseId )throws BusinessException
	  {
		log.debug("== Inicia getReferenceByIdAndWarehouseSource/ReferenceBusinessBean ==");
		try {
		       if( referenceId!=null && referenceId<0 )
		    	     referenceId = null;
			
		       if( warehouseId!=null && warehouseId<0 )
		    	     warehouseId = null;
			
			return UtilsBusiness.convertList( daoReference.getReferenceByIdAndWarehouseSource(referenceId, warehouseId, CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity()) , ReferenceVO.class);
        
		} catch(Throwable ex){
        	log.debug("== Error getConfirmationsByReferenceIdAndElementId/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceByIdAndWarehouseSource/ReferenceBusinessBean ==");
        }
	  }
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ReferenceVO> getReferencesByRefIdOrWhSourceIdAndDifferentDealerWhs( Long referenceId,Long warehouseId )throws BusinessException
	{
		log.debug("== Inicia getReferencesByRefIdOrWhSourceIdAndDifferentDealerWhs/ReferenceBusinessBean ==");
		try {
			if( referenceId!=null && referenceId<0 )
				referenceId = null;

			if( warehouseId!=null && warehouseId<0 )
				warehouseId = null;

			List<Reference> result = daoReference.getReferenceByIdAndWarehouseSource(referenceId, warehouseId, CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity());
			//Obteniendo las remisiones que corresponden solo a bodegas de dealers diferentes
			List<Reference> finalResult = filterReferencesDifferentDealers(true, result);
			return UtilsBusiness.convertList(finalResult , ReferenceVO.class);

		} catch(Throwable ex){
			log.debug("== Error getReferencesByRefIdOrWhSourceIdAndDifferentDealerWhs/ReferenceBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getReferencesByRefIdOrWhSourceIdAndDifferentDealerWhs/ReferenceBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#createReferenceByFile(java.lang.Long, java.util.List, java.lang.Long, java.lang.Long, co.com.directv.sdii.model.vo.UserVO)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Long createReferenceByFile(List<ReferenceElementItemVO> refElements, Long userId ) throws BusinessException{
		log.debug("== Inicia createReferenceByFile/ReferenceBusinessBean ==");
		UtilsBusiness.assertNotNull(refElements, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(userId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try{
			Reference referencePojo = daoReference.getReferenceByID(refElements.get(0).getReference().getId());
			
			UtilsBusiness.assertNotNull(referencePojo, ErrorBusinessMessages.NO_REFERENCE_EXIT.getCode(), ErrorBusinessMessages.NO_REFERENCE_EXIT.getMessage());
			//Se crean los elementos de tipo referenceElementItem
			List<ReferenceElementItemVO> referencesElementItems = businessRefElementItems.fillreferenceElementItems(refElements, CodesBusinessEntityEnum.ITEM_STATUS_CREATED.getCodeEntity(), false);
			validateElementsInWHQuantities(referencesElementItems, referencePojo.getWarehouseBySourceWh().getId());
			
			if( referencePojo == null )
				throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			else if( !referencePojo.getReferenceStatus().getRefStatusCode().equals( CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity() ) )
				throw new BusinessException(ErrorBusinessMessages.REFERENCE_STATUS_NOT_IN_CREATION_PROCESS.getCode(), ErrorBusinessMessages.REFERENCE_STATUS_NOT_IN_CREATION_PROCESS.getMessage());
			for(ReferenceElementItemVO itemVO : referencesElementItems){
				ReferenceElementItem item = UtilsBusiness.copyObject(ReferenceElementItem.class, itemVO);
	        	daoReferenceElementItem.createReferenceElementItem(item);
	        }
			return referencePojo.getId();
		} catch(Throwable ex){
        	log.debug("== Error createReferenceByFile/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createReferenceByFile/ReferenceBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#createReferenceByFile(java.util.List<co.com.directv.sdii.model.vo.SerializedVO>,java.lang.Long,java.lang.Long,co.com.directv.sdii.model.vo.UserVO)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ReferenceVO> getReferencesByElementId(Long elementId) throws BusinessException
	{
		log.debug("== Inicia getReferencesByElementId/ReferenceBusinessBean ==");
	       
		try {
			
			UtilsBusiness.assertNotNull(elementId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			return UtilsBusiness.convertList( daoReference.getReferencesByElementId( elementId ) , ReferenceVO.class);
        
		} catch(Throwable ex){
        	log.debug("== Error getReferencesByElementId/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesByElementIde/ReferenceBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByReferenceAndWhAndByCountry(java.lang.Long, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountryAndCreatedStatus(Long ref, WarehouseVO whSource, WarehouseVO whTarget,Long country)
			throws BusinessException {
		log.debug("== Inicia getReferencesByReferenceAndWhAndByCountryAndCreatedStatus/ReferenceBusinessBean ==");
        List<ReferenceVO> references = new ArrayList<ReferenceVO>();
        try {
        	if(ref==0){
        		if(whSource==null||whTarget==null){
        			if(whSource==null&&whTarget!=null){
        				UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        				references = UtilsBusiness.convertList(this.daoReference.getReferencesByTargetWareHouseAndCreatedStatus(whTarget.getId()), ReferenceVO.class);
        			}else{
        				if(whSource!=null){
            				UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            				references = UtilsBusiness.convertList(this.daoReference.getReferencesBySourceWareHouseAndCreatedStatus(whSource.getId()), ReferenceVO.class);
        				}
        			}
        		}else{
        			UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			references = this.getReferencesBySourceAndTargetWareHouseAndByCountryAndCreatedStatus(whSource, whTarget,country);
        		}
        	}else{
        		if(whSource==null||whTarget==null){
        			if(whSource==null&&whTarget==null){
        				Reference refTmp = this.getReferenceByIDAndCreatedStatus(ref);
        				if(refTmp!=null){
        					references.add(UtilsBusiness.copyObject(ReferenceVO.class,refTmp));
        				}
        			}else{
	        			if(whTarget==null){
	        				UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        				references = UtilsBusiness.convertList(this.daoReference.getReferencesByReferenceAndSourceWhAndCreatedStatus(ref,whSource.getId()), ReferenceVO.class);
	        			}else{
	        				UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        				references = UtilsBusiness.convertList(this.daoReference.getReferencesByReferenceAndTargerWhAndCreatedStatus(ref,whTarget.getId()), ReferenceVO.class);
	        			}
        			}
        		}else{
        			UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			references = UtilsBusiness.convertList(this.daoReference.getReferencesByReferenceAndWhAndCreatedStatus(ref,whSource.getId(),whTarget.getId()), ReferenceVO.class);
        		}   
        	}
            return references;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesByReferenceAndWhAndByCountryAndCreatedStatus/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesByReferenceAndWhAndByCountryAndCreatedStatus/ReferenceBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByReferenceAndWhAndByCountry(java.lang.Long, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ReferenceVO> getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndCreatedStatusSameWhDealerOwner(Long ref, WarehouseVO whSource, WarehouseVO whTarget,Long country)
			throws BusinessException {
		log.debug("== Inicia getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndCreatedStatusSameWhDealerOwner/ReferenceBusinessBean ==");
        try {
        	
        	List<ReferenceVO> referencesVo = getReferencesByReferenceAndWhAndByCountryAndCreatedStatus(ref, whSource, whTarget, country);
        	List<Reference> referencesPojo = UtilsBusiness.convertListWithoutExtends(referencesVo, Reference.class);
        	List<Reference> result = filterReferencesDifferentDealers(false, referencesPojo);
        	referencesVo = UtilsBusiness.convertList(result, ReferenceVO.class);
        	
            return referencesVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndCreatedStatusSameWhDealerOwner/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndCreatedStatusSameWhDealerOwner/ReferenceBusinessBean ==");
        }
	}
	
	/**
     * Metodo: Filtra las remisiones dado el criterio de dealers diferentes establecido por el parámetro boolean
     * @param differentDealer especifica si el dealer es diferente
     * @param result resultado del filtro
     * @return <tipo> <descripcion>
     * @author jjimenezh
     */
    private List<Reference> filterReferencesDifferentDealers(
			boolean differentDealer, List<Reference> result) {
    	
    	List<Reference> finalResult = new ArrayList<Reference>();
    	
    	//Se realiza la siguiente iteración para evitar hacer una consulta que use union
		for (Reference reference : result) {
			//Caso 1: Si las bodegas origen y destino corresponden a dealers:
			if(reference.getWarehouseBySourceWh().getDealerId() != null && reference.getWarehouseByTargetWh().getDealerId() != null){
				//Si las bodegas deben ser de diferentes compañías:
				if(differentDealer){
					//Si corresponden a dealers diferentes
					if(reference.getWarehouseBySourceWh().getDealerId().getId().longValue() != reference.getWarehouseByTargetWh().getDealerId().getId().longValue()){
						finalResult.add(reference);
					}
				//Si las bodegas origen y destino deben ser de la misma compañía:
				}else{
	        		if(reference.getWarehouseBySourceWh().getDealerId().getId().longValue() == reference.getWarehouseByTargetWh().getDealerId().getId().longValue()){
						finalResult.add(reference);
					}
	        	}
			//Caso 2: Si la bodega origen corresponde a dealer y la de destino a cuadrilla:
			}else if(reference.getWarehouseBySourceWh().getDealerId() != null && reference.getWarehouseByTargetWh().getCrewId() != null){
				if(differentDealer){
					//Si corresponden a dealers diferentes
					if(reference.getWarehouseBySourceWh().getDealerId().getId().longValue() != reference.getWarehouseByTargetWh().getCrewId().getDealer().getId().longValue()){
						finalResult.add(reference);
					}
				//Si las bodegas origen y destino deben ser de la misma compañía:
				}else{
	        		if(reference.getWarehouseBySourceWh().getDealerId().getId().longValue() == reference.getWarehouseByTargetWh().getCrewId().getDealer().getId().longValue()){
						finalResult.add(reference);
					}
	        	}
			//Caso 3: Si la bodega origen es de cuadrilla y la bodega de destino de dealer:
			}else if(reference.getWarehouseBySourceWh().getCrewId() != null && reference.getWarehouseByTargetWh().getDealerId() != null){
				if(differentDealer){
					//Si corresponden a dealers diferentes
					if(reference.getWarehouseBySourceWh().getCrewId().getDealer().getId().longValue() != reference.getWarehouseByTargetWh().getDealerId().getId().longValue()){
						finalResult.add(reference);
					}
				//Si las bodegas origen y destino deben ser de la misma compañía:
				}else{
	        		if(reference.getWarehouseBySourceWh().getCrewId().getDealer().getId().longValue() == reference.getWarehouseByTargetWh().getDealerId().getId().longValue()){
						finalResult.add(reference);
					}
	        	}
			//Caso 4: Si la bodega origen es de cuadrilla y la bodega de destino es de cuadrilla:
			}else if(reference.getWarehouseBySourceWh().getCrewId() != null && reference.getWarehouseByTargetWh().getCrewId() != null){
				if(differentDealer){
					//Si corresponden a dealers diferentes
					if(reference.getWarehouseBySourceWh().getCrewId().getDealer().getId().longValue() != reference.getWarehouseByTargetWh().getCrewId().getDealer().getId().longValue()){								
						finalResult.add(reference);
					}
				//Si las bodegas origen y destino deben ser de la misma compañía:
				}else{
	        		if(reference.getWarehouseBySourceWh().getCrewId().getDealer().getId().longValue() == reference.getWarehouseByTargetWh().getCrewId().getDealer().getId().longValue()){
						finalResult.add(reference);
					}
	        	}
			}
		}
		
		return finalResult;
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByReferenceAndWhAndByCountryAndCreatedStatusAndCreationstatus(java.lang.Long, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountryAndCreatedStatusAndCreationstatus(Long ref,
			WarehouseVO whSource, WarehouseVO whTarget,Long country)
			throws BusinessException {
		log.debug("== Inicia getReferencesByReferenceAndWhAndByCountryAndCreatedStatusAndCreationstatus/ReferenceBusinessBean ==");
        List<ReferenceVO> references = new ArrayList<ReferenceVO>();
        try {
        	if(ref==0){
        		if(whSource==null||whTarget==null){
        			if(whSource==null&&whTarget!=null){
        				UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        				references = UtilsBusiness.convertList(this.daoReference.getReferencesByTargetWareHouseAndCreatedStatusAndCreationStatus(whTarget.getId()), ReferenceVO.class);
        			}else{
        				if(whSource!=null){
            				UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            				references = UtilsBusiness.convertList(this.daoReference.getReferencesBySourceWareHouseAndCreatedStatusAndCreationStatus(whSource.getId()), ReferenceVO.class);
        				}
        			}
        		}else{
        			UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			references = this.getReferencesBySourceAndTargetWareHouseAndByCountryAndCreatedStatusAndCreationStatus(whSource, whTarget,country);
        		}
        	}else{
        		if(whSource==null||whTarget==null){
        			if(whSource==null&&whTarget==null){
        				Reference refTmp = this.getReferenceByIDAndCreatedStatusAndCreationStatus(ref);
        				if(refTmp!=null){
        					references.add(UtilsBusiness.copyObject(ReferenceVO.class,refTmp));
        				}
        			}else{
	        			if(whTarget==null){
	        				UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        				references = UtilsBusiness.convertList(this.daoReference.getReferencesByReferenceAndSourceWhAndCreatedStatusAndCreatedStatus(ref,whSource.getId()), ReferenceVO.class);
	        			}else{
	        				UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        				references = UtilsBusiness.convertList(this.daoReference.getReferencesByReferenceAndTargerWhAndCreatedStatusAndCreatedStatus(ref,whTarget.getId()), ReferenceVO.class);
	        			}
        			}
        		}else{
        			UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			references = UtilsBusiness.convertList(this.daoReference.getReferencesByReferenceAndWhAndCreatedStatusAndCreatedStatus(ref,whSource.getId(),whTarget.getId()), ReferenceVO.class);
        		}   
        	}
            return references;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesByReferenceAndWhAndByCountryAndCreatedStatusAndCreationstatus/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesByReferenceAndWhAndByCountryAndCreatedStatusAndCreationstatus/ReferenceBusinessBean ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByReferenceAndWhAndByCountryAndSendedOrPartialConfirmed(java.lang.Long, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountryAndSendedOrPartialConfirmed(Long ref,
			WarehouseVO whSource, WarehouseVO whTarget,Long country)
			throws BusinessException {
		log.debug("== Inicia getReferencesByReferenceAndWhAndByCountryAndSendedOrPartialConfirmed/ReferenceBusinessBean ==");
        List<ReferenceVO> references = new ArrayList<ReferenceVO>();
        try {
        	if(ref == null || ref.longValue() == 0 ){
        		if(whSource==null||whTarget==null){
        			if(whSource==null&&whTarget!=null){
        				UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        				references = UtilsBusiness.convertList(this.daoReference.getReferencesByTargetWareHouseAndSendedOrPartialConfirmed(whTarget.getId()), ReferenceVO.class);
        			}else{
        				if(whSource!=null){
            				UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            				references = UtilsBusiness.convertList(this.daoReference.getReferencesBySourceWareHouseAndSendedOrPartialConfirmed(whSource.getId()), ReferenceVO.class);
        				}
        			}
        		}else{
        			UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			references = this.getReferencesBySourceAndTargetWareHouseAndByCountryAndByCountryAndSendedOrPartialConfirmed(whSource, whTarget,country);
        		}
        	}else{
        		if(whSource==null||whTarget==null){
        			if(whSource==null&&whTarget==null){
        				Reference refTmp = this.getReferenceByIDAndSendedOrPartialConfirmed(ref);
        				if(refTmp!=null){
        					references.add(UtilsBusiness.copyObject(ReferenceVO.class,refTmp));
        				}
        			}else{
	        			if(whTarget==null){
	        				UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        				references = UtilsBusiness.convertList(this.daoReference.getReferencesByReferenceAndSourceWhAndSendedOrPartialConfirmed(ref,whSource.getId()), ReferenceVO.class);
	        			}else{
	        				UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        				references = UtilsBusiness.convertList(this.daoReference.getReferencesByReferenceAndTargerWhAndSendedOrPartialConfirmed(ref,whTarget.getId()), ReferenceVO.class);
	        			}
        			}
        		}else{
        			UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			references = UtilsBusiness.convertList(this.daoReference.getReferencesByReferenceAndWhAndSendedOrPartialConfirmed(ref,whSource.getId(),whTarget.getId()), ReferenceVO.class);
        		}   
        	}
        	if( references != null && !references.isEmpty() ){
        		for( ReferenceVO reference : references ){
        			RefRecieveDataVO vo = this.getRefRecieveDataByReferenceId(reference.getId());
        			if( vo != null ){
            			reference.setRefRecieveData(vo);
        			}
        		}
        	}
            return references;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesByReferenceAndWhAndByCountryAndSendedOrPartialConfirmed/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesByReferenceAndWhAndByCountryAndSendedOrPartialConfirmed/ReferenceBusinessBean ==");
        }
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ReferenceVO> getReferencesBySourceAndTargetWareHouseAndByCountryAndByCountryAndSendedOrPartialConfirmed(
			WarehouseVO whSource, WarehouseVO whTarget,Long country)
			throws BusinessException {
		log.debug("== Inicia getReferencesBySourceAndTargetWareHouseAndByCountryAndByCountryAndSendedOrPartialConfirmed/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(whSource, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(whTarget, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        List<ReferenceVO> references;
        try {
        	if(whSource.getWhCode()!=null){
        		whSource = UtilsBusiness.copyObject(WarehouseVO.class, this.daoWarehouse.getWarehouseByCodeAndByCountry(whSource.getWhCode(),country));
        	}else{
        		UtilsBusiness.assertNotNull(whSource.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	if(whTarget.getWhCode()!=null){
        		whTarget = UtilsBusiness.copyObject(WarehouseVO.class, this.daoWarehouse.getWarehouseByCodeAndByCountry(whTarget.getWhCode(),country));
        	}else{
        		UtilsBusiness.assertNotNull(whTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	references = UtilsBusiness.convertList(this.daoReference.getReferencesBySourceAndTargetWareHouseAndSendedOrPartialConfirmed(whSource.getId(),whTarget.getId()), ReferenceVO.class);
        	
            return references;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesBySourceAndTargetWareHouseAndByCountryAndByCountryAndSendedOrPartialConfirmed/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesBySourceAndTargetWareHouseAndByCountryAndByCountryAndSendedOrPartialConfirmed/ReferenceBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ReferenceVO getReferenceByIDAndSendedOrPartialConfirmed(Long id) throws BusinessException {
        log.debug("== Inicia getReferenceByIDAndSendedOrPartialConfirmed/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Reference objPojo = daoReference.getReferenceByIDAndSendedOrPartialConfirmed(id);
            
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            ReferenceVO result = UtilsBusiness.copyObject(ReferenceVO.class, objPojo);
            fillReferenceAditionalData(result);
            return result;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceByIDAndSendedOrPartialConfirmed/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceByIDAndSendedOrPartialConfirmed/ReferenceBusinessBean ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getCreatedReferencesByFilter(co.com.directv.sdii.model.dto.ReferencesFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ReferenceResponse getCreatedReferencesByFilter (ReferencesFilterDTO referenceDTO,RequestCollectionInfo requestCollInfo)throws BusinessException{
    	log.debug("== Inicia getCreatedReferencesByFilter/WarehouseBusinessBean ==");
    	UtilsBusiness.assertNotNull(referenceDTO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	ReferenceResponse response = this.daoReference.getCreatedReferencesByFilter(referenceDTO,requestCollInfo);
        	List<Reference> references = response.getReferences();
        	response.setReferencesVO( UtilsBusiness.convertList(references, ReferenceVO.class) );  
        	response.setReferences( null );
        	for(ReferenceVO tmp : response.getReferencesVO()){
        		tmp.setCreationUserName(tmp.getCreateUserId().getName());
        		//Datos ubicación de origen
        		StringBuffer bufferSource = new StringBuffer();
        		bufferSource.append(tmp.getWarehouseBySourceWh().getDealerId().getDepotCode() + " + ");
        		bufferSource.append(tmp.getWarehouseBySourceWh().getDealerId().getDealerName() + " + ");
        		if(tmp.getWarehouseBySourceWh().getWhResponsible()!=null){
        			bufferSource.append(tmp.getWarehouseBySourceWh().getWhResponsible()+ " + ");	
        		}
        		bufferSource.append(tmp.getWarehouseBySourceWh().getWarehouseType().getWhTypeName());
        		tmp.setWhSource(bufferSource.toString());
        		//Datos ubicación de destino
        		StringBuffer bufferTarget= new StringBuffer();
        		bufferTarget.append(tmp.getWarehouseByTargetWh().getDealerId().getDepotCode() + " + ");
        		bufferTarget.append(tmp.getWarehouseByTargetWh().getDealerId().getDealerName() + " + ");
        		if(tmp.getWarehouseByTargetWh().getWhResponsible()!=null){
        			bufferTarget.append(tmp.getWarehouseByTargetWh().getWhResponsible()+ " + ");	
        		}
        		bufferTarget.append(tmp.getWarehouseByTargetWh().getWarehouseType().getWhTypeName());
        		tmp.setWhTarget(bufferTarget.toString());
        		
        		//Eliminar relaciones
        		tmp.setWarehouseBySourceWh(null);
        		tmp.setWarehouseByTargetWh(null);
        		tmp.setTransportCompany(null);
        		tmp.setSourceTransitWh(null);
        		tmp.setTargetTransitWh(null);
        		tmp.setCreateUserId(null);
        		tmp.setShippingUserId(null);
        		tmp.setCountryCodeId(null);
        	}
            return response;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getCreatedReferencesByFilter/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCreatedReferencesByFilter/WarehouseBusinessBean ==");
        }
    }

    private Warehouse obtainSourceTransitWarehouse(Warehouse sourceWarehouseSelected) throws BusinessException, PropertiesException {
    	return obtainTransitWarehouse(sourceWarehouseSelected, CodesBusinessEntityEnum.WAREHOUSE_TYPE_WHTRO722.getCodeEntity());
    }
    
    private Warehouse obtainTargetTransitWarehouse(Warehouse sourceWarehouseSelected) throws BusinessException, PropertiesException {
    	return obtainTransitWarehouse(sourceWarehouseSelected, CodesBusinessEntityEnum.WAREHOUSE_TYPE_WHTRD723.getCodeEntity());
    }
    
    /**
     * Metodo: busca una bodega de tránsito; si no la encuentra, la crea
     * @param sourceWarehouseSelected bodega de origen o de destino a la que se le quiere obtener la bodega de tránsito
     * @param warehouseTypeCode bodega de entrada o de salida
     * @return bodega de tránsito
     */
    private Warehouse obtainTransitWarehouse(Warehouse sourceWarehouseSelected, String warehouseTypeCode) throws BusinessException, PropertiesException {
    	WarehouseType whTypeTransSource = this.businessWarehouseType.getWarehouseTypeByCode(warehouseTypeCode);
    	WarehouseVO transitWh = null;
    	//Validar ubicación
		//Valido si es una ubicacion de una caudrilla
    	if(sourceWarehouseSelected.getCrewId()!=null){
			List<WarehouseVO> listSourceTransit = this.businessWarehouse.getWhByCrewAndDealerAndWhType(null, sourceWarehouseSelected.getCrewId().getId(), whTypeTransSource.getId());
			if(listSourceTransit.size()>0){
				transitWh = listSourceTransit.get(0);
			}else{
				if(transitWh==null){
					transitWh = this.businessWarehouse.createWarehouseTransit( UtilsBusiness.copyObject(WarehouseVO.class, sourceWarehouseSelected), UtilsBusiness.copyObject(WarehouseTypeVO.class, whTypeTransSource));	
				}
			}
		}else{
			List<WarehouseVO> listSourceTransit = this.businessWarehouse.getWhByCrewAndDealerAndWhType(sourceWarehouseSelected.getDealerId().getId(),null, whTypeTransSource.getId());
			if(listSourceTransit.size()>0){
				transitWh = listSourceTransit.get(0);
			}else{
				if(transitWh==null){
					transitWh = this.businessWarehouse.createWarehouseTransit( UtilsBusiness.copyObject(WarehouseVO.class, sourceWarehouseSelected), UtilsBusiness.copyObject(WarehouseTypeVO.class, whTypeTransSource));	
				}
			}
		} 
    	return transitWh;
    }
    
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#generateReference(co.com.directv.sdii.model.vo.ReferenceVO)
	 */
	@Override
	  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ReferenceVO generateReference(ReferenceVO reference)
			throws BusinessException {
		 log.debug("== Inicia generateReference/ReferenceBusinessBean ==");
	        UtilsBusiness.assertNotNull(reference, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        UtilsBusiness.assertNotNull(reference.getWarehouseBySourceWh(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        UtilsBusiness.assertNotNull(reference.getWarehouseBySourceWh().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        UtilsBusiness.assertNotNull(reference.getWarehouseByTargetWh(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        UtilsBusiness.assertNotNull(reference.getWarehouseByTargetWh().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        UtilsBusiness.assertNotNull(reference.getCountryCodeId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        UtilsBusiness.assertNotNull(reference.getCountryCodeId().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        UtilsBusiness.assertNotNull(reference.getCreateUserId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        UtilsBusiness.assertNotNull(reference.getCreateUserId().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        UtilsBusiness.assertNotNull(reference.getIsPrepaidRef(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        UtilsBusiness.assertNotNull(reference.getIsPreloadRef(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        /*
	         * release_correctiva_4.1.4_Validación para evitar la creación de remisiones con RN Existente
	         * RN (REFERENCES.RN_NUMBER) puede ser null si la remision se crea manualmente desde pantalla
	         */
	        try {
	        	WarehouseType whTypeTransSource = this.businessWarehouseType.getWarehouseTypeByCode(CodesBusinessEntityEnum.WAREHOUSE_TYPE_WHTRO722.getCodeEntity());
	        	WarehouseType whTypeTransTarget = this.businessWarehouseType.getWarehouseTypeByCode(CodesBusinessEntityEnum.WAREHOUSE_TYPE_WHTRD723.getCodeEntity());
	        	WarehouseVO sourceTransit = null;
        		WarehouseVO targetTransit = null;
        		
        		//Validar que la remisión no se pueda crear sobre la misma ubicación
        		if(reference.getWarehouseBySourceWh().getId().longValue()==reference.getWarehouseByTargetWh().getId().longValue()){
        			throw new BusinessException(ErrorBusinessMessages.STOCK_IN444.getCode(), ErrorBusinessMessages.STOCK_IN444.getMessage());
        		}
        		
        		Warehouse sourceWarehouseSelected = daoWarehouse.getWarehouseByID(reference.getWarehouseBySourceWh().getId());
        		Warehouse targetWarehouseSelected = daoWarehouse.getWarehouseByID(reference.getWarehouseByTargetWh().getId()); 
        		
        		
        		//Validar ubicación origen
        		//Valido si es una ubicacion de una caudrilla
        		if(sourceWarehouseSelected.getCrewId()!=null){
        			List<WarehouseVO> listSourceTransit = this.businessWarehouse.getWhByCrewAndDealerAndWhType(null, sourceWarehouseSelected.getCrewId().getId(), whTypeTransSource.getId());
        			if(listSourceTransit.size()>0){
        				sourceTransit = listSourceTransit.get(0);
        			}else{
        				if(sourceTransit==null){
        					sourceTransit = this.businessWarehouse.createWarehouseTransit( UtilsBusiness.copyObject(WarehouseVO.class, sourceWarehouseSelected), UtilsBusiness.copyObject(WarehouseTypeVO.class, whTypeTransSource));	
        				}
        			}
        		}else{
        			List<WarehouseVO> listSourceTransit = this.businessWarehouse.getWhByCrewAndDealerAndWhType(sourceWarehouseSelected.getDealerId().getId(),null, whTypeTransSource.getId());
        			if(listSourceTransit.size()>0){
        				sourceTransit = listSourceTransit.get(0);
        			}else{
        				if(sourceTransit==null){
        					sourceTransit = this.businessWarehouse.createWarehouseTransit( UtilsBusiness.copyObject(WarehouseVO.class, sourceWarehouseSelected), UtilsBusiness.copyObject(WarehouseTypeVO.class, whTypeTransSource));	
        				}
        			}
        		}
        		
        		//Validar ubicación destino
        		//Valido si es una ubicacion de una cuadrilla
        		if(targetWarehouseSelected.getCrewId()!=null){
        			List<WarehouseVO> listTargetTransit = this.businessWarehouse.getWhByCrewAndDealerAndWhType(null, targetWarehouseSelected.getCrewId().getId(), whTypeTransTarget.getId());
        			if(listTargetTransit.size()>0){
        				targetTransit = listTargetTransit.get(0);
        			}else{
        				if(targetTransit==null){
        					targetTransit = this.businessWarehouse.createWarehouseTransit( UtilsBusiness.copyObject(WarehouseVO.class, targetWarehouseSelected), UtilsBusiness.copyObject(WarehouseTypeVO.class, whTypeTransTarget));	
        				}
        			}
        		}else{
        			List<WarehouseVO> listTargetTransit = this.businessWarehouse.getWhByCrewAndDealerAndWhType(targetWarehouseSelected.getDealerId().getId(),null, whTypeTransTarget.getId());
        			if(listTargetTransit.size()>0){
        				targetTransit = listTargetTransit.get(0);
        			}else{
        				if(targetTransit==null){
        					targetTransit = this.businessWarehouse.createWarehouseTransit( UtilsBusiness.copyObject(WarehouseVO.class, targetWarehouseSelected), UtilsBusiness.copyObject(WarehouseTypeVO.class, whTypeTransTarget));	
        				}
        			}
        		}
        		
        		//Asugnar bodegas de transito a la remisión
        		reference.setSourceTransitWh( UtilsBusiness.copyObject( Warehouse.class , sourceTransit) );
        		reference.setTargetTransitWh( UtilsBusiness.copyObject( Warehouse.class , targetTransit) );
        		
	        	//Pone el estado de remisión en "En creación"
	        	ReferenceStatusVO refStatus = this.businessRefStatus.getReferenceStatusByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity());
	        	reference.setReferenceStatus(UtilsBusiness.copyObject(ReferenceStatus.class, refStatus));
	        	reference.setCreationReferenceDate(UtilsBusiness.getCurrentTimeZoneDateByUserId(reference.getCreateUserId().getId(),daoUser));
	            Reference objPojo =  UtilsBusiness.copyObject(Reference.class, reference);
	            daoReference.createReference(objPojo);
	            //Crea el registro en las modificaciones de remision
	            this.businessReferenceModification.createReferenceModification( objPojo.getId() , CodesBusinessEntityEnum.REFERENCE_MODIFICATION_TYPE_CREATION_PROCESS.getCodeEntity(), objPojo.getCreateUserId().getId());
	            return UtilsBusiness.copyObject(ReferenceVO.class, objPojo);
	        } catch(Throwable ex){
	        	log.debug("== Error al tratar de ejecutar la operación generateReference/ReferenceBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina generateReference/ReferenceBusinessBean ==");
	        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getPreloadedReferences(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ReferenceResponse getPreloadedReferences(Long sourceWhId,RequestCollectionInfo requestCollInfo)throws BusinessException{
		log.debug("== Inicia getPreloadedReferences/ReferenceBusinessBean ==");
		try{
			if( sourceWhId == null || sourceWhId.longValue() <= 0 ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			ReferenceResponse response = this.daoReference.getReferencesBySourceWhIdAndPreloadStatus(sourceWhId,CodesBusinessEntityEnum.REFERENCE_PRELOAD.getCodeEntity(),CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity(),requestCollInfo);
			if( response != null && response.getReferences() != null && !response.getReferences().isEmpty() ){
				List<ReferenceVO> referencesVO = new ArrayList<ReferenceVO>();
				for( Reference pojo : response.getReferences() ){
					ReferenceVO vo = UtilsBusiness.copyObject(ReferenceVO.class, pojo);
					Warehouse source = new Warehouse();
					source.setId( pojo.getWarehouseBySourceWh().getId() );
					vo.setWarehouseBySourceWh( source );
					Warehouse target = new Warehouse();
					target.setId( pojo.getWarehouseBySourceWh().getId() );
					vo.setWarehouseByTargetWh( target );
					StringBuffer bufferWhSource = new StringBuffer();
	        		bufferWhSource.append(pojo.getWarehouseBySourceWh().getDealerId().getDepotCode() + " + ");
	        		bufferWhSource.append(pojo.getWarehouseBySourceWh().getDealerId().getDealerName() + " + ");
	        		if(pojo.getWarehouseBySourceWh().getCrewId()!=null){
	        			List<EmployeeCrew> listTmp= this.employCrewDAO.getEmployeesCrewByCrewID(pojo.getWarehouseBySourceWh().getCrewId().getId());
	            		if(listTmp.size()>0){
	            			Employee employee = listTmp.get(0).getEmployee();
	            			bufferWhSource.append(employee.getFirstName()+" " +employee.getLastName()+" + ");
	            		}
	        		}
	        		
					vo.setWhSource( StringUtils.removeEnd(bufferWhSource.toString()," + ") );
					StringBuffer bufferWhTarget = new StringBuffer();
	        		bufferWhTarget.append(pojo.getWarehouseByTargetWh().getDealerId().getDepotCode() + " + ");
	        		bufferWhTarget.append(pojo.getWarehouseByTargetWh().getDealerId().getDealerName() + " + ");
	        		if(pojo.getWarehouseByTargetWh().getCrewId()!=null){
	        			List<EmployeeCrew> listTmp1 = this.employCrewDAO.getEmployeesCrewByCrewID(pojo.getWarehouseByTargetWh().getCrewId().getId());
	            		if(listTmp1.size()>0){
	            			Employee employee = listTmp1.get(0).getEmployee();
	            			bufferWhTarget.append(employee.getFirstName()+" " +employee.getLastName()+" + ");
	            		}	
	        		}
	        		bufferWhTarget.append(pojo.getWarehouseByTargetWh().getWarehouseType().getWhTypeName());
	        		vo.setWhTarget( StringUtils.removeEnd(bufferWhTarget.toString()," + ") );
					vo.setCreationUser(null);
					vo.setCreateUserId(null);
					vo.setShippingUserId(null);
					referencesVO.add( vo );
				}
				response.setReferences(null);
				response.setReferencesVO(referencesVO);
			}
			return response;
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getPreloadedReferences/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPreloadedReferences/ReferenceBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#isReferenceFromSameDealer(java.lang.Long)
	 */
	@Override
	public boolean isReferenceFromSameDealer(Long referenceId) throws BusinessException {
		log.debug("== Inicia isReferenceFromSameDealer/ReferenceBusinessBean ==");
		try{
			boolean response = false;
			Reference reference = this.daoReference.getReferenceByID(referenceId);
			if( reference != null ){
				if( reference.getWarehouseBySourceWh().getDealerId() != null ){
					if( reference.getWarehouseByTargetWh().getCrewId() != null 
							|| ( reference.getWarehouseBySourceWh().getDealerId().getId().equals( reference.getWarehouseByTargetWh().getDealerId().getId() ) ) ){
						response = true;
					}
				}
			}
			return response;
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación isReferenceFromSameDealer/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina isReferenceFromSameDealer/ReferenceBusinessBean ==");
        }
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#sendReference(co.com.directv.sdii.model.vo.ReferenceVO, boolean, boolean)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void sendReference(ReferenceVO referenceVO,boolean isBetweenDifDealers,boolean validateQuantityControl, Long userId) throws BusinessException {
		log.debug("== Inicia sendReference/ReferenceBusinessBean ==");
		try{			
			Reference reference = this.daoReference.getReferenceByID(referenceVO.getId());

			//Valido que la remisión no tenga ningun archivo procesandose en este momento.
			String uploadFileParamReferenceId = CodesBusinessEntityEnum.FILE_PARAM_REFERENCE_ID.getCodeEntity();
			String fileStatusProcessing = CodesBusinessEntityEnum.FILE_STATUS_PROCESSING.getCodeEntity();
			String fileStatusPending = CodesBusinessEntityEnum.FILE_STATUS_PENDING.getCodeEntity();
			Long countUploadFilesInProcess = this.daoReference.getCountUploadFilesByFileStatusAndReference(fileStatusPending, fileStatusProcessing, uploadFileParamReferenceId, reference.getId().toString());
			if(countUploadFilesInProcess > 0){
				throw new BusinessException(ErrorBusinessMessages.REFERENCE_HAS_FILES_IN_PROCESSING_STATUS.getCode(),	ErrorBusinessMessages.REFERENCE_HAS_FILES_IN_PROCESSING_STATUS.getMessage());
			}
			
			//Valido que la remisión se encuentre en estado "Creada"			
			if(!CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity().equals(reference.getReferenceStatus().getRefStatusCode())){
				throw new BusinessException(ErrorBusinessMessages.REFERENCE_IS_NOT_IN_CREATED_STATE.getCode(),	ErrorBusinessMessages.REFERENCE_IS_NOT_IN_CREATED_STATE.getMessage());
			}
			
			//Se actualiza el estado de la remision a "Enviando"
			ReferenceStatus sendingStatus = this.daoReferenceStatus.getReferenceByCode( CodesBusinessEntityEnum.REFERENCE_STATUS_SENDING.getCodeEntity() );
			reference.setReferenceStatus(sendingStatus);
			this.daoReference.updateReference( reference );
			
			if(log.isInfoEnabled())
				log.info("Enviando remision: "+ referenceVO.getId()+ " por el user: " + userId);
			
			//Se invoca el procedimiento que envía la remisión
			try{
				sendReferenceTransact(referenceVO, isBetweenDifDealers, validateQuantityControl, userId);
			}
			catch(Throwable ex){
				String errorMessage = ex.getMessage();
				//INICIO PR4756
				//Se actualiza el estado de la remision a "Error"
				ReferenceStatus finalStatus = this.daoReferenceStatus.getReferenceByCode( CodesBusinessEntityEnum.REFERENCE_STATUS_ERROR.getCodeEntity() );
				reference.setReferenceStatus(finalStatus);
				reference.setShippingDate(referenceVO.getShippingDate());
				log.info("No se pudo enviar la remision de id " + reference.getId()+ " se le asignara un valor centinela a shipping date tener esto en cuenta cuando se cambie el estado de dicha remision de manera manual" );
				this.daoReference.updateReference( reference );
				throw new ReferenceProcedureException(errorMessage,ex);
				//FIN PR4756
			}

		
	} catch(Throwable ex){
    	log.debug("== Error al tratar de ejecutar la operación sendReference/ReferenceBusinessBean ==");
    	ex.printStackTrace();
    	throw this.manageException(ex);
    } finally {
        log.debug("== Termina sendReference/ReferenceBusinessBean ==");
    }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#sendReference(co.com.directv.sdii.model.vo.ReferenceVO, boolean, boolean)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void sendReferenceTransact(ReferenceVO referenceVO,boolean isBetweenDifDealers,boolean validateQuantityControl, Long userId) throws BusinessException {
		log.debug("== Inicia sendReferenceTransact/ReferenceBusinessBean ==");
		try{
			
			//Consulto el usuario 
        	User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
			validatePendingItemsInFiles(referenceVO.getId());
			
			boolean isBetweenDealers = false;
			if( referenceVO == null || referenceVO.getId().longValue() <= 0 ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}

			Reference ref = daoReference.getReferenceByID(referenceVO.getId());
			isBetweenDealers = isReferenceBetweenCompany(ref); 
			
			//Valida que los elementos agregados no superen la cantidad del control de cantidad
			this.validateQuantityControls( referenceVO.getId() , validateQuantityControl );
			if( isBetweenDealers ){
				this.sendReferenceBetweenDifDealers(referenceVO, user);
			} else{
				Reference reference = this.daoReference.getReferenceByID(referenceVO.getId());
				
				if(reference != null){
					//Se le da valor al usuario que registra el envio
					if(referenceVO.getShippingUserId() != null && referenceVO.getShippingUserId().getId() != null 
							&& referenceVO.getShippingUserId().getId().longValue() > 0){
						if(user != null){
							reference.setShippingUserId(user);
						}
					}
					
					assertNotNull(reference.getShippingUserId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
					assertNotNull(reference.getShippingUserId().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());


					//Determina si es bodega de talle
		        	String movementTypeCodeE = this.businessRefElementItems.getEntryTypeCodeByReference( reference, isBetweenDealers);
		        	String movementTypeCodeS = this.businessRefElementItems.getExitTypeCodeByReference( reference,isBetweenDealers );
					if( reference.getSourceTransitWh() != null && reference.getSourceTransitWh().getId().longValue() > 0 ){
						//Se consulta el nuevo estado de los elementos
						ItemStatus newItemStatus = this.daoItemStatus.getItemStatusByCode( CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity() );
						//Se hace el movimiento de los elementos no serializados a la bodega de destino de la remision
						List<ReferenceElementItem> itemsNotSerialized = this.daoReferenceElementItem.getNotSerializedReferenceElementItemByReferenceId( referenceVO.getId() );
						if(itemsNotSerialized != null && !itemsNotSerialized.isEmpty()){
							MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementTypeCodeE, movementTypeCodeS);
							for(ReferenceElementItem item : itemsNotSerialized){
								/*ElementMovementDTO dto = new ElementMovementDTO(reference.getSourceTransitWh().getId(), reference.getWarehouseByTargetWh().getId(), item.getElement().getElementType().getId(), item.getRefQuantity(), movementTypeCodeE, movementTypeCodeS, false, referenceVO.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity());
								businessWhElement.moveNotSerializedElementBetweenWareHouses(dto);*/
								MovementElementDTO dtoMov = new MovementElementDTO(user,
										UtilsBusiness.copyObject(WarehouseVO.class, reference.getSourceTransitWh()), 
										UtilsBusiness.copyObject(WarehouseVO.class,  reference.getWarehouseByTargetWh()), 
										null, 
										item.getElement().getElementType().getId(), 
										null,
										item.getReference().getId(), 
										CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), 
										dtoGenerics.getMovTypeCodeE(), 
										dtoGenerics.getMovTypeCodeS(), 
										dtoGenerics.getRecordStatusU(),
										dtoGenerics.getRecordStatusH(),
										item.getRefQuantity(),
										dtoGenerics.getMovConfigStatusPending(),
										dtoGenerics.getMovConfigStatusNoConfig());
								businessMovementElement.moveNotSerializedElementBetweenWarehouse(dtoMov);
								
								item.setItemStatus(newItemStatus);
								this.daoReferenceElementItem.updateReferenceElementItem(item);
							}
						}
						//Se hace el movimiento de los elementos serializados a la bodega de destino de la remision
						List<ReferenceElementItem> itemsSerialized = this.daoReferenceElementItem.getSerializedReferenceElementItemByReferenceIdSQL(referenceVO.getId());
						if(itemsSerialized != null && !itemsSerialized.isEmpty()){
							MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementTypeCodeE, movementTypeCodeS);
							String documentClass = CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity();
							WarehouseVO copyWarehouseTransit = UtilsBusiness.copyObject(WarehouseVO.class,  reference.getSourceTransitWh());
							WarehouseVO copyWarehouseTarget = UtilsBusiness.copyObject(WarehouseVO.class, reference.getWarehouseByTargetWh());


							UtilsBusiness.assertNotNull(copyWarehouseTarget, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
							UtilsBusiness.assertNotNull(copyWarehouseTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
							UtilsBusiness.assertNotNull(copyWarehouseTransit, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
							UtilsBusiness.assertNotNull(copyWarehouseTransit.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
							UtilsBusiness.assertNotNull(dtoGenerics.getMovTypeCodeE(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
							UtilsBusiness.assertNotNull(dtoGenerics.getMovTypeCodeE().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
							UtilsBusiness.assertNotNull(dtoGenerics.getMovTypeCodeS(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
							UtilsBusiness.assertNotNull(dtoGenerics.getMovTypeCodeS().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
							UtilsBusiness.assertNotNull(user, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
							UtilsBusiness.assertNotNull(user.getSdiiTimeZone(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
							UtilsBusiness.assertNotNull(user.getSdiiTimeZone().getTimeZoneKey(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

							
							/*Se invoca el store procedure encargado de realizar el movimiento de los elementos serializados*/
							List<Object[]> resultado = daoWarehouseElement.executeUpdateWarehousePackage(referenceVO.getId(),copyWarehouseTransit.getId(),copyWarehouseTarget.getId(),
									dtoGenerics.getMovTypeCodeE().getId(),dtoGenerics.getMovTypeCodeS().getId(), dtoGenerics.getRecordStatusU().getRecordStatusCode(),
									dtoGenerics.getRecordStatusH().getRecordStatusCode(),
									CodesBusinessEntityEnum.MOV_CMD_STATUS_PENDING.getCodeEntity(),
									CodesBusinessEntityEnum.TYPE_COMUNICATION_HSP_TO_IBS.getCodeEntity(),
									CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(),
									user.getCountry().getId(),
									CodesBusinessEntityEnum.PROCESS_CODE_IBS_REFERENCE.getCodeEntity(),
									CodesBusinessEntityEnum.COMUNICATION_HSP_IBS_IS_NOT_COMMENT_ERROR.getCodeEntity(),
									CodesBusinessEntityEnum.MOV_CMD_STATUS_NO_CONFIG.getCodeEntity(),
									user.getId()
									);
							
							
							
							if (resultado.get(0)[0].toString().equals("OK")){
								System.out.println(resultado.get(0)[0].toString());
								//Update masivo del estado de la remision
								this.daoReferenceElementItem.updateMasiveReferenceElementItem(referenceVO,newItemStatus.getId());
							}else{
								//ERROR
								String errorMessage="";
								if (resultado.get(0)[1].toString().equals("-20000")){
									throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getCode(),ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getMessage());
								}
								else if (resultado.get(0)[1].toString().equals("-20001")){
									throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_ELEMENT_ALREADY_IN_WH.getCode(),ErrorBusinessMessages.WAREHOUSE_ELEMENT_ALREADY_IN_WH.getMessage());
								}
								else if (resultado.get(0)[1].toString().equals("-20002")){
									throw new BusinessException(ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getMessage());
								}
								else if (resultado.get(0)[1].toString().equals("-20003")){
									throw new BusinessException(ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getMessage());
								}
								else if (resultado.get(0)[1].toString().equals("-20004")){
									throw new BusinessException(ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getCode(),ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getMessage());
								}
								else if (resultado.get(0)[1].toString().equals("-20005")){
									throw new BusinessException(ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getCode(),ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getMessage());
								}
								else{
									errorMessage=ErrorBusinessMessages.UNKNOW_ERROR.getMessage();
									throw new ReferenceProcedureException(errorMessage);
								}
							}
						}
					}
					
					//Se actualiza el estado de la remision a "Recepcionado"
					ReferenceStatus receptedStatus = this.daoReferenceStatus.getReferenceByCode( CodesBusinessEntityEnum.REFERENCE_STATUS_RECEPTED.getCodeEntity() );
					reference.setReferenceStatus(receptedStatus);					
					
					this.daoReference.updateReference( reference );
					
					//Crea el registro en las modificaciones de remision
		            this.businessReferenceModification.createReferenceModification( reference.getId() , CodesBusinessEntityEnum.REFERENCE_MODIFICATION_SHIPMENT.getCodeEntity(), reference.getShippingUserId().getId());
		            
					//Crea el registro en las modificaciones de remision para el estado Recepcionada
		            this.businessReferenceModification.createReferenceModification( reference.getId() , CodesBusinessEntityEnum.REFERENCE_MODIFICATION_RECEPTED.getCodeEntity(), reference.getShippingUserId().getId());		            
		            
		            //Invoca CU-08 informar a los responsables de las bodegas origen y destino sobre el envío de la remisión
		            this.sendEmail( UtilsBusiness.copyObject(UserVO.class, reference.getCreateUserId()) , reference, EmailTypesEnum.REFERENCE_SHIPMENT.getEmailTypecode(), EmailTypesEnum.REFERENCE_SHIPMENT.getEmailTypeName());
				}
			}
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operación sendReferenceTransact/ReferenceBusinessBean ==");
			ex.printStackTrace();
			if (ex instanceof ReferenceProcedureException){				
				throw new ReferenceProcedureException(ex.getMessage());
			}
			else{	        	
	        	throw this.manageException(ex);
			}
        } finally {
            log.debug("== Termina sendReferenceTransact/ReferenceBusinessBean ==");
        }
	}

	/**
	 * Método encargado de validar si la fecha enviada como parametro 
	 * es mayor o igual a la fecha del sistema teniendo en cuenta la 
	 * zona horaria de usuario autenticado 
	 * @param dateSendReference
	 * @param user
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	private boolean isDateEqualsorHigherDateCurrent(Date dateSendReference, User user) throws BusinessException{
		log.debug("== Inicia validateDateSendReference/ReferenceBusinessBean ==");
		try{
			Date dateCurrent = UtilsBusiness.getCurrentTimeZoneDateByUserId(user.getId(), daoUser);
			dateCurrent = UtilsBusiness.dateWithoutHour(dateCurrent);
			return !(dateSendReference.getTime()<dateCurrent.getTime());
		}catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación validateDateSendReference/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina validateDateSendReference/ReferenceBusinessBean ==");
        }
	} 
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#sendReferenceBetweenDifDealers(co.com.directv.sdii.model.vo.ReferenceVO)
	 */
	@Override
	public void sendReferenceBetweenDifDealers(ReferenceVO referenceVO, User user) throws BusinessException {
		log.debug("== Inicia sendReferenceBetweenDifDealers/ReferenceBusinessBean ==");
		try{
			
			//Validar que la fecha de envío de la remisión sea igual o mayor a la fecha del
			//sistema según el usuario autenticado
			if(!isDateEqualsorHigherDateCurrent(referenceVO.getShippingDate(), user)){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN488.getCode(), ErrorBusinessMessages.STOCK_IN488.getMessage());
			}
			
			Reference reference = this.daoReference.getReferenceByID(referenceVO.getId());
			if(reference != null){
				//Se le agregan los valores para el caso de envio entre diferentes companias
				reference.setShippingDate(referenceVO.getShippingDate());
				reference.setTransportCompany(referenceVO.getTransportCompany());
				reference.setTransportGuide(referenceVO.getTransportGuide());
				reference.setDriverName(referenceVO.getDriverName());
				reference.setVehiclePlate(referenceVO.getVehiclePlate());
				reference.setSendUnits(referenceVO.getSendUnits());
				reference.setVolume(referenceVO.getVolume());
				//Se le da valor al usuario que registra el envio
				if(referenceVO.getShippingUserId() != null && referenceVO.getShippingUserId().getId() != null 
						&& referenceVO.getShippingUserId().getId().longValue() > 0){
					if(user != null){
						reference.setShippingUserId(user);
					}
				}
				
				assertNotNull(reference.getShippingUserId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
				assertNotNull(reference.getShippingUserId().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
				
				reference.setDeliveryDate(UtilsBusiness.getCurrentTimeZoneDateByUserId(reference.getShippingUserId().getId(), daoUser));	
				

				//Determina si es bodega de talle
	        	String movementTypeCodeE = this.businessRefElementItems.getEntryTypeCodeByReference(reference, true);
	        	String movementTypeCodeS = this.businessRefElementItems.getExitTypeCodeByReference(reference, true);
				if(reference.getSourceTransitWh() != null && reference.getSourceTransitWh().getId().longValue() > 0
						&& reference.getTargetTransitWh() != null && reference.getTargetTransitWh().getId().longValue() > 0 ){
					//Se consulta el nuevo estado de los elementos
					//jnova verificar en que estado deben quedar los elementos
					ItemStatus newItemStatus = this.daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity());
					//Se hace el movimiento de los elementos no serializados a la bodega de destino de la remision
					List<ReferenceElementItem> itemsNotSerialized = this.daoReferenceElementItem.getNotSerializedReferenceElementItemByReferenceId(referenceVO.getId());
					if(itemsNotSerialized != null && !itemsNotSerialized.isEmpty()){
						MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementTypeCodeE, movementTypeCodeS);
						for(ReferenceElementItem item : itemsNotSerialized){
							/*ElementMovementDTO dto = new ElementMovementDTO(reference.getSourceTransitWh().getId(), reference.getTargetTransitWh().getId(), item.getElement().getElementType().getId(), item.getRefQuantity(), movementTypeCodeE, movementTypeCodeS, false, referenceVO.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity());
							businessWhElement.moveNotSerializedElementBetweenWareHouses(dto);*/
							MovementElementDTO dtoMov = new MovementElementDTO(user,
									UtilsBusiness.copyObject(WarehouseVO.class, reference.getSourceTransitWh()), 
									UtilsBusiness.copyObject(WarehouseVO.class,  reference.getTargetTransitWh()), 
									null, 
									item.getElement().getElementType().getId(), 
									null,
									item.getReference().getId(), 
									CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), 
									dtoGenerics.getMovTypeCodeE(), 
									dtoGenerics.getMovTypeCodeS(), 
									dtoGenerics.getRecordStatusU(),
									dtoGenerics.getRecordStatusH(),
									item.getRefQuantity(),
									dtoGenerics.getMovConfigStatusPending(),
									dtoGenerics.getMovConfigStatusNoConfig());
							businessMovementElement.moveNotSerializedElementBetweenWarehouse(dtoMov);
							
							item.setItemStatus(newItemStatus);
							this.daoReferenceElementItem.updateReferenceElementItem(item);
							
							item.setItemStatus(newItemStatus);
							this.daoReferenceElementItem.updateReferenceElementItem(item);
						}
					}
					//Se hace el movimiento de los elementos serializados a la bodega de destino de la remision
					List<ReferenceElementItem> itemsSerialized = this.daoReferenceElementItem.getSerializedReferenceElementItemByReferenceIdSQL(referenceVO.getId());
					if(itemsSerialized != null && !itemsSerialized.isEmpty()){
						MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementTypeCodeE, movementTypeCodeS);
						String documentClass = CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity();
						WarehouseVO copyWarehouseTransit = UtilsBusiness.copyObject(WarehouseVO.class,  reference.getSourceTransitWh());
						WarehouseVO copyWarehouseTarget = UtilsBusiness.copyObject(WarehouseVO.class, reference.getTargetTransitWh());
						
						
						UtilsBusiness.assertNotNull(copyWarehouseTarget, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
						UtilsBusiness.assertNotNull(copyWarehouseTarget.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
						UtilsBusiness.assertNotNull(copyWarehouseTransit, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
						UtilsBusiness.assertNotNull(copyWarehouseTransit.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
						UtilsBusiness.assertNotNull(dtoGenerics.getMovTypeCodeE(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
						UtilsBusiness.assertNotNull(dtoGenerics.getMovTypeCodeE().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
						UtilsBusiness.assertNotNull(dtoGenerics.getMovTypeCodeS(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
						UtilsBusiness.assertNotNull(dtoGenerics.getMovTypeCodeS().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
						UtilsBusiness.assertNotNull(user, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
						UtilsBusiness.assertNotNull(user.getSdiiTimeZone(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
						UtilsBusiness.assertNotNull(user.getSdiiTimeZone().getTimeZoneKey(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
						
						
						/*Se invoca el store procedure encargado de realizar el movimiento de los elementos serializados*/
						List<Object[]> resultado = daoWarehouseElement.executeUpdateWarehousePackage(referenceVO.getId(),copyWarehouseTransit.getId(),copyWarehouseTarget.getId(),
								dtoGenerics.getMovTypeCodeE().getId(),dtoGenerics.getMovTypeCodeS().getId(), dtoGenerics.getRecordStatusU().getRecordStatusCode(),
								dtoGenerics.getRecordStatusH().getRecordStatusCode(),
								CodesBusinessEntityEnum.MOV_CMD_STATUS_PENDING.getCodeEntity(),
								CodesBusinessEntityEnum.TYPE_COMUNICATION_HSP_TO_IBS.getCodeEntity(),
								CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(),
								user.getCountry().getId(),
								CodesBusinessEntityEnum.PROCESS_CODE_IBS_REFERENCE.getCodeEntity(),
								CodesBusinessEntityEnum.COMUNICATION_HSP_IBS_IS_NOT_COMMENT_ERROR.getCodeEntity(),
								CodesBusinessEntityEnum.MOV_CMD_STATUS_NO_CONFIG.getCodeEntity(),
								user.getId()
								);
						
						if (resultado.get(0)[0].toString().equals("OK")){
							System.out.println(resultado.get(0)[0].toString());
							//Update masivo del estado de la remision
							this.daoReferenceElementItem.updateMasiveReferenceElementItem(referenceVO,newItemStatus.getId());
						}else{
							//ERROR
							String errorMessage="";
							if (resultado.get(0)[1].toString().equals("-20000")){
								throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getCode(),ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getMessage());
							}
							else if (resultado.get(0)[1].toString().equals("-20001")){
								throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_ELEMENT_ALREADY_IN_WH.getCode(),ErrorBusinessMessages.WAREHOUSE_ELEMENT_ALREADY_IN_WH.getMessage());
							}
							else if (resultado.get(0)[1].toString().equals("-20002")){
								throw new BusinessException(ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getMessage());
							}
							else if (resultado.get(0)[1].toString().equals("-20003")){
								throw new BusinessException(ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getMessage());
							}
							else if (resultado.get(0)[1].toString().equals("-20004")){
								throw new BusinessException(ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getCode(),ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getMessage());
							}
							else if (resultado.get(0)[1].toString().equals("-20005")){
								throw new BusinessException(ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getCode(),ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getMessage());
							}
							else{
								errorMessage=ErrorBusinessMessages.UNKNOW_ERROR.getMessage();
								throw new ReferenceProcedureException(errorMessage);
							}
						}
					}
				}				
				//Se actualiza el estado de la remision a "Enviada"
				ReferenceStatus sendedStatus = this.daoReferenceStatus.getReferenceByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity());
				reference.setReferenceStatus(sendedStatus);
				
				this.daoReference.updateReference(reference);
				
				//Crea el registro en las modificaciones de remision
	            this.businessReferenceModification.createReferenceModification(reference.getId() , CodesBusinessEntityEnum.REFERENCE_MODIFICATION_SHIPMENT.getCodeEntity(), reference.getShippingUserId().getId());
				
	            //Invoca CU-08 informar a los responsables de las bodegas origen y destino sobre el envío de la remisión
				this.sendEmail(UtilsBusiness.copyObject(UserVO.class, reference.getCreateUserId()) , reference, EmailTypesEnum.REFERENCE_SHIPMENT.getEmailTypecode(), EmailTypesEnum.REFERENCE_SHIPMENT.getEmailTypeName());
			}
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación sendReferenceBetweenDifDealers/ReferenceBusinessBean ==");
        	ex.printStackTrace();
			if (ex instanceof ReferenceProcedureException){				
				throw new ReferenceProcedureException(ex.getMessage());
			}
			else{	        	
	        	throw this.manageException(ex);
			}
			
        } finally {
            log.debug("== Termina sendReferenceBetweenDifDealers/ReferenceBusinessBean ==");
        }
	}
	
	/**
	 * 
	 * Metodo: Valida que los controles de cantidad esten de acuerdo a los elementos agregado en la remision
	 * @param reference identificador de la remision a validar
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private void validateQuantityControls(Long referenceId, boolean validateQuantityControl) throws BusinessException{
		log.debug("== Inicia validateQuantityControls/ReferenceBusinessBean ==");
		try{
			if(validateQuantityControl){
				RefQuantityControlItemsResponse quantityControls = this.daoRefQtyCtrlItem.getRefQtyCtrlItemsByReference(referenceId, null);
				if(quantityControls != null && quantityControls.getRefQuantityControlItems() != null && !quantityControls.getRefQuantityControlItems().isEmpty()){
					for (RefQuantityControlItem quantityControl : quantityControls.getRefQuantityControlItems()) {
						//Si la cantidad requerida es superior a cero, es decir, que se usa control de cantidad
						if(quantityControl.getRequiredQty().doubleValue() > 0){
							if(quantityControl.getIncludedQty() > quantityControl.getRequiredQty()){
								Object params[]={quantityControl.getElementType().getTypeElementName()};
								List<String> paramsList = new ArrayList<String>();
								paramsList.add(quantityControl.getElementType().getTypeElementName());
								throw new BusinessException(ErrorBusinessMessages.STOCK_IN389.getCode(), ErrorBusinessMessages.STOCK_IN389.getMessage(params),paramsList);
							} else if(quantityControl.getIncludedQty() < quantityControl.getRequiredQty()){
								Object params[]={quantityControl.getElementType().getTypeElementName()};
								List<String> paramsList = new ArrayList<String>();
								paramsList.add(quantityControl.getElementType().getTypeElementName());
								throw new BusinessException(ErrorBusinessMessages.STOCK_IN390.getCode(), ErrorBusinessMessages.STOCK_IN390.getMessage(params),paramsList);
							}
						}
					}
				}
			}
		}catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación validateQuantityControls/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina validateQuantityControls/ReferenceBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByParentReferenceId(java.lang.Long)
	 */
	@Override
	public List<ReferenceVO> getReferencesByParentReferenceId(
			Long parentReferenceId) throws BusinessException {
		log.debug("== Inicia getReferencesByParentReferenceId/ReferenceBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(parentReferenceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			return UtilsBusiness.convertList( daoReference.getReferencesByParentReferenceId( parentReferenceId ) , ReferenceVO.class);
		} catch(Throwable ex){
        	log.debug("== Error getReferencesByParentReferenceId/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesByParentReferenceId/ReferenceBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getReferencesByFilter(co.com.directv.sdii.model.dto.ReferencesFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ReferenceResponse getReferencesByFilter(ReferencesFilterDTO referenceDTO,RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getReferencesByFilter/WarehouseBusinessBean ==");
    	UtilsBusiness.assertNotNull(referenceDTO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	ReferenceResponse response = this.daoReference.getReferencesByFilter(referenceDTO,requestCollInfo);
        	List<Reference> references = response.getReferences();
        	response.setReferencesVO( UtilsBusiness.convertList(references, ReferenceVO.class) );  
        	response.setReferences( null );
        	for(ReferenceVO tmp : response.getReferencesVO()){
        		tmp.setCreationUserName(tmp.getCreateUserId().getName());
        		if( tmp.getShippingUserId() != null )
        			tmp.setShippmentUserName( tmp.getShippingUserId().getName() );
        		
        		//Datos ubicación de origen
        		WarehouseVO warehouseVoSourceTemp =UtilsBusiness.copyObject(WarehouseVO.class, tmp.getWarehouseBySourceWh()); 
        		businessWarehouse.genWareHouseName(warehouseVoSourceTemp);
        		tmp.setWhSource(warehouseVoSourceTemp.getWarehouseName());

        		//Recupera el id del dealer de la bodega origen
        		tmp.setDealerIdWHSource(warehouseVoSourceTemp.getDealerId().getId());
        		
        		//Datos ubicación de destino
        		WarehouseVO warehouseVOTargetTemp = UtilsBusiness.copyObject(WarehouseVO.class, tmp.getWarehouseByTargetWh());
        		businessWarehouse.genWareHouseName(warehouseVOTargetTemp);
        		tmp.setWhTarget(warehouseVOTargetTemp.getWarehouseName());
        		
        		//Recupera el id del dealer de la bodega destino
        		tmp.setDealerIdWHTarget(warehouseVOTargetTemp.getDealerId().getId());

        		
        		//Eliminar relaciones
        		tmp.setWarehouseBySourceWh(null);
        		tmp.setWarehouseByTargetWh(null);
        		tmp.setSourceTransitWh(null);
        		tmp.setTargetTransitWh(null);
        		tmp.setCreateUserId(null);
        		tmp.setShippingUserId(null);
        		tmp.setCountryCodeId(null);
        	}
            return response;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesByFilter/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesByFilter/WarehouseBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#validateReferenceReceiveData(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public boolean validateReferenceReceiveData(Long referenceId) throws BusinessException {
		log.debug("== Inicia validateReferenceReceiveData/WarehouseBusinessBean ==");
    	try {
        	if( referenceId == null || referenceId.longValue() <= 0 ){
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	boolean response = false;
        	RefRecieveData refRecieveData = this.refRecieveDataDAO.getRefRecieveDataByReferenceId(referenceId);
        	if( refRecieveData != null )
        		response = true;
        	return response;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación validateReferenceReceiveData/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina validateReferenceReceiveData/WarehouseBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#createRefRecieveData(co.com.directv.sdii.model.vo.RefRecieveDataVO)
	 */
	@Override
	public void createRefRecieveData(RefRecieveDataVO refRecieveDataVO, Long userId) throws BusinessException {
		log.debug("== Inicia createRefRecieveData/WarehouseBusinessBean ==");
		UtilsBusiness.assertNotNull(refRecieveDataVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(refRecieveDataVO.getComments(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(refRecieveDataVO.getArrivalDate(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(refRecieveDataVO.getRecieveQty(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(refRecieveDataVO.getReference(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(refRecieveDataVO.getReference().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    	try {
    		
    		//Consulto el usuario 
        	User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
    		
    		if(refRecieveDataVO.getReference().getId() == null || refRecieveDataVO.getReference().getId().longValue() <= 0){
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
    		
    		//Validar si ya existen datos de recibido
    		RefRecieveData refActual = refRecieveDataDAO.getRefRecieveDataByReferenceId(refRecieveDataVO.getReference().getId());
    		if(refActual !=null){
    			throw new BusinessException(ErrorBusinessMessages.STOCK_IN453.getCode(), ErrorBusinessMessages.STOCK_IN453.getMessage());
    		}
    		
    		//Consultar la remisión para obtener la fecha de envió de la misma
    		Reference reference = daoReference.getReferenceByID(refRecieveDataVO.getReference().getId());
    		if(reference == null){
    			throw new BusinessException(ErrorBusinessMessages.STOCK_IN440.getCode(), ErrorBusinessMessages.STOCK_IN440.getMessage());
    		}
    		
    		Date sendDateReference = reference.getShippingDate();
    		if(sendDateReference == null){
    			throw new BusinessException(ErrorBusinessMessages.STOCK_IN489.getCode(), ErrorBusinessMessages.STOCK_IN489.getMessage());
    		}
    		

    		//Validar que la fecha de recibido sea mayor o igual a la fecha de envío
    		if(refRecieveDataVO.getArrivalDate().getTime() < sendDateReference.getTime()){
    			throw new BusinessException(ErrorBusinessMessages.STOCK_IN490.getCode(), ErrorBusinessMessages.STOCK_IN490.getMessage());
    		}
    		
    		RefRecieveData pojo = UtilsBusiness.copyObject( RefRecieveData.class , refRecieveDataVO);
    		pojo.setId( pojo.getReference().getId() );
    		this.refRecieveDataDAO.createRefRecieveData(pojo);
    	} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createRefRecieveData/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createRefRecieveData/WarehouseBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#getRefRecieveDataByReferenceId(java.lang.Long)
	 */
	public RefRecieveDataVO getRefRecieveDataByReferenceId(Long referenceId)throws BusinessException {
		log.debug("== Inicia getRefRecieveDataByReferenceId/WarehouseBusinessBean ==");
		try {
			RefRecieveData pojo =  this.refRecieveDataDAO.getRefRecieveDataByReferenceId(referenceId);
			return UtilsBusiness.copyObject(RefRecieveDataVO.class, pojo);
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getRefRecieveDataByReferenceId/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRefRecieveDataByReferenceId/WarehouseBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#addNotSerializedElementsToReference(java.lang.Double, co.com.directv.sdii.model.pojo.Reference, co.com.directv.sdii.model.pojo.ElementType)
	 */
	@Override
	public void addNotSerializedElementsToReference(Double quantity, Reference reference, ElementType elementType, String isAdded, ItemStatus itemStatus, ReportedElementVO reportedElementVO) throws BusinessException{
		ReferenceElementItemVO refElementItem = new ReferenceElementItemVO();
		refElementItem.setReference(reference);
		refElementItem.setRefQuantity(quantity);
		
		
		refElementItem.setItemStatus(itemStatus);
		
		List<ReferenceElementItemVO> listElementNotSerializedToAdd = new ArrayList<ReferenceElementItemVO>();
		listElementNotSerializedToAdd.add(refElementItem);
		Long sourceWh = reference.getSourceTransitWh().getId();//los elementos se encuentran en la bodega transito de origen
		List<ReferenceElementItemVO> elementsAdded = businessRefElementItems.addElementNotSerializedToReference(listElementNotSerializedToAdd, reference, itemStatus, elementType.getId(), sourceWh);
		
		//información almacenada para posteriormente realizar fácilmente los movimientos entre bodegas
		refElementItem.setRefIncAddedQuantity(quantity);//cantidad de elementos agregados a causa de la inconsistencia
		
		ReferenceElementItemVO elementAdded = null;
		if(elementsAdded != null && !elementsAdded.isEmpty()) {
			elementAdded = elementsAdded.get(0);
		}
		
		reportedElementVO.setReferenceElementItem(elementAdded);//ahora que si se sabe el ítem en la remisión, se agrega la referencia en el reported element
		businessReportedElement.updateReportedElement(reportedElementVO);
		
		reportedElementVO.addAddedElement(refElementItem);
	}
	
	
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

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal#isReferenceRequiresValidationProcess(java.lang.Long, java.lang.Long)
	 */
	@Override
	public boolean isReferenceRequiresValidationProcess(Long sourceWhId, Long targetWhId) throws BusinessException {
		boolean referenceRequiresValidationProcess = ! isTargetACrewOfSourceWhDealer(sourceWhId, targetWhId);
		return referenceRequiresValidationProcess;
	}
	
	/**
	 * Metodo: indica si la bodega destino es una cuadrilla perteneciente al dealer de la bodega origen
	 * @param sourceWhId identificador de la bodega de origen
	 * @param targetWhId identificador de la bodega destino
	 * @return verdadero si la bodega destino es una cuadrilla perteneciente al dealer de la bodega origen
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	private boolean isTargetACrewOfSourceWhDealer(Long sourceWhId, Long targetWhId)
			throws BusinessException {
		log.debug("== Inicia isTargetACrewOfSourceWh/ReferenceBusinessBean ==");
        UtilsBusiness.assertNotNull(sourceWhId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(targetWhId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	Warehouse sourceWh = daoWarehouse.getWarehouseByID(sourceWhId);
        	Warehouse targetWh = daoWarehouse.getWarehouseByID(targetWhId);
        	
        	UtilsBusiness.assertNotNull(sourceWh, ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
        	UtilsBusiness.assertNotNull(targetWh, ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
        	
        	if(sourceWh.getDealerId() == null || sourceWh.getDealerId().getId() == null) {
        		throw new BusinessException("la bodega de origen especificada no tiene un dealer asociado");
        	}
        	
        	if(targetWh.getDealerId() == null || targetWh.getDealerId().getId() ==  null) {
        		throw new BusinessException("la bodega destino especificada no tiene un dealer asociado");
        	}
        	
        	//si las dos bodegas son del mismo dealer y la bodega origen no es cuadrilla y la bodega destino es cuadrilla
        	if(sourceWh.getDealerId().getId().longValue()==targetWh.getDealerId().getId()){
        		return true;
    		}
            return false;
            
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación isTargetACrewOfSourceWh/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina isTargetACrewOfSourceWh/ReferenceBusinessBean ==");
        }
	}
	
	/**
	 * Metodo: valida la existencia de archivos de cargue masivo de elementos a un registro de importación
	 * que no se han finalizado de procesar.
	 * @param importLogId identificador del registro de importación
	 * @throws BusinessException en caso que existan archivos con proceso sin finalizar
	 * @author wjimenez
	 */
	private void validatePendingItemsInFiles(Long referenceId) throws PropertiesException, BusinessException {
		List<FilterUploadFileParamByFileTypeParamNameAndCodeDTO> filters = new ArrayList<FilterUploadFileParamByFileTypeParamNameAndCodeDTO>();
		
		FilterUploadFileParamByFileTypeParamNameAndCodeDTO filterUploadFile = new FilterUploadFileParamByFileTypeParamNameAndCodeDTO();
		filterUploadFile.setFileTypeCode(CodesBusinessEntityEnum.FILE_TYPE_REFERENCE_UPLOAD_DELETE_ELEMENTS.getCodeEntity());
		
		filterUploadFile.setParamName(CodesBusinessEntityEnum.FILE_PARAM_REFERENCE_ID.getCodeEntity());
		filterUploadFile.setParamValue(referenceId.toString());
		
		filters.add(filterUploadFile);
		
		List<UploadFileParamByFileTypeParamNameAndCodeDTO> pendingOrProcessingFiles = businessUploadFile.getUploadFileParamByFileTypeParamNameAndCode(filters);
		if(pendingOrProcessingFiles != null && !pendingOrProcessingFiles.isEmpty()) {
			throw new BusinessException(ErrorBusinessMessages.STOCK_IN434.getCode(), ErrorBusinessMessages.STOCK_IN434.getMessage());
		}
		
	}
	
	/**
	 * Método encargado de validar si la remisión es entre diferentes compañias
	 * @param reference
	 * @throws BusinessException
	 */
	private boolean isReferenceBetweenCompany(Reference reference) throws BusinessException{
		log.debug("== Inicia getAddedElements/ReferenceElementItemBusinessBean ==");
		try{
			return reference.getWarehouseBySourceWh().getDealerId().getId().longValue() != reference.getWarehouseByTargetWh().getDealerId().getId().longValue();
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operación getAddedElements/ReferenceElementItemBusinessBean == " + ex.getMessage());
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAddedElements/ReferenceElementItemBusinessBean ==");
		}
	}
	
}
