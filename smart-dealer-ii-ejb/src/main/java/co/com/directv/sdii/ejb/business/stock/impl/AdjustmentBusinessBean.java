package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.ArrayList;

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
import co.com.directv.sdii.ejb.business.stock.AdjustmentBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.AdjustmentElementsBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.AdjustmentHelperBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.AdjustmentModificationBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.AdjustmenElementsRequestDTO;
import co.com.directv.sdii.model.dto.AdjustmenElementsResponseDTO;
import co.com.directv.sdii.model.dto.AdjustmentRequestDTO;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.dto.collection.AdjustmentElementCollDTO;
import co.com.directv.sdii.model.pojo.Adjustment;
import co.com.directv.sdii.model.pojo.AdjustmentElements;
import co.com.directv.sdii.model.pojo.AdjustmentElementsStatus;
import co.com.directv.sdii.model.pojo.AdjustmentStatus;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.TransferReason;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.collection.AdjustmentElementsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.AdjustmentElementsVO;
import co.com.directv.sdii.model.vo.AdjustmentStatusVO;
import co.com.directv.sdii.model.vo.AdjustmentTypeVO;
import co.com.directv.sdii.model.vo.AdjustmentVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.persistence.dao.config.CustomerDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentElementsDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentElementsStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.NotSerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.TransferReasonDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;
import co.com.directv.sdii.reports.dto.AdjustmentElementDTO;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD Adjustment
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.AdjustmentBusinessBeanLocal
 */
@Stateless(name="AdjustmentBusinessBeanLocal",mappedName="ejb/AdjustmentBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AdjustmentBusinessBean extends BusinessBase implements AdjustmentBusinessBeanLocal {

    @EJB(name="AdjustmentDAOLocal", beanInterface=AdjustmentDAOLocal.class)
    private AdjustmentDAOLocal daoAdjustment;
    
    @EJB(name="AdjustmentStatusDAOLocal", beanInterface=AdjustmentStatusDAOLocal.class)
    private AdjustmentStatusDAOLocal daoAdjustmentStatus;
    
    @EJB(name="AdjustmentElementsDAOLocal", beanInterface=AdjustmentElementsDAOLocal.class)
    private AdjustmentElementsDAOLocal daoAdjustmentElements;
    
    @EJB(name="AdjustmentTypeDAOLocal", beanInterface=AdjustmentTypeDAOLocal.class)
    private AdjustmentTypeDAOLocal daoAdjustmentType;
    
    @EJB(name="AdjustmentElementsBusinessBeanLocal", beanInterface=AdjustmentElementsBusinessBeanLocal.class)
    private AdjustmentElementsBusinessBeanLocal adjustmentElementsBusinessBean;
    
    @EJB(name="WarehouseDAOLocal", beanInterface=WarehouseDAOLocal.class)
    private WarehouseDAOLocal daoWarehouse;
    
    @EJB(name="ElementTypeDAOLocal", beanInterface=ElementTypeDAOLocal.class)
    private ElementTypeDAOLocal daoElementType;
    
    @EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal daoWarehouseElement;
    
    @EJB(name="TransferReasonDAOLocal", beanInterface=TransferReasonDAOLocal.class)
	private TransferReasonDAOLocal daoTransferReason;
    
    @EJB(name="CustomerDAOLocal", beanInterface=CustomerDAOLocal.class)
	private CustomerDAOLocal daoCustomer;
    
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal daoUser;
    
    @EJB(name = "TransferReasonDAOLocal", beanInterface = TransferReasonDAOLocal.class)
	private TransferReasonDAOLocal transferReasonDAO;
    
    @EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
    private SerializedDAOLocal daoSerialized;
    
    @EJB(name = "NotSerializedDAOLocal", beanInterface = NotSerializedDAOLocal.class)
	private NotSerializedDAOLocal daoNotSerialized;
    
    @EJB(name = "WarehouseBusinessBeanLocal", beanInterface = WarehouseBusinessBeanLocal.class)
	private WarehouseBusinessBeanLocal warehouseBusinessBean;   
    
    @EJB(name = "MovementElementBusinessBeanLocal", beanInterface = MovementElementBusinessBeanLocal.class)
	private MovementElementBusinessBeanLocal businessMovementElement;
    
    @EJB(name = "AdjustmentElementsStatusDAOLocal", beanInterface = AdjustmentElementsStatusDAOLocal.class)
	private AdjustmentElementsStatusDAOLocal daoAdjustmentElementsStatus;
    
    @EJB(name = "AdjustmentModificationBusinessBeanLocal", beanInterface = AdjustmentModificationBusinessBeanLocal.class)
	private AdjustmentModificationBusinessBeanLocal adjustmentModificationBusinessBean;
    
    @EJB(name = "AdjustmentHelperBusinessBeanLocal", beanInterface = AdjustmentHelperBusinessBeanLocal.class)
	private AdjustmentHelperBusinessBeanLocal adjustmentHelperBusinessBean;
    
    private final static Logger log = UtilsBusiness.getLog4J(AdjustmentBusinessBean.class);
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.AdjustmentBusinessBeanLocal#getAllAdjustments()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AdjustmentVO> getAllAdjustments() throws BusinessException {
        log.debug("== Inicia getAllAdjustments/AdjustmentBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoAdjustment.getAllAdjustments(), AdjustmentVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllAdjustments/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllAdjustments/AdjustmentBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.AdjustmentBusinessBeanLocal#getAdjustmentsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public AdjustmentVO getAdjustmentByID(Long id) throws BusinessException {
        log.debug("== Inicia getAdjustmentByID/AdjustmentBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Adjustment objPojo = daoAdjustment.getAdjustmentByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(AdjustmentVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAdjustmentByID/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAdjustmentByID/AdjustmentBusinessBean ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.AdjustmentBusinessBeanLocal#getAdjustmentsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AdjustmentVO getAdjustmentByIDNew(Long id) throws BusinessException {
        log.debug("== Inicia getAdjustmentByID/AdjustmentBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Adjustment objPojo = daoAdjustment.getAdjustmentByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(AdjustmentVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAdjustmentByID/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAdjustmentByID/AdjustmentBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.AdjustmentBusinessBeanLocal#createAdjustment(co.com.directv.sdii.model.vo.AdjustmentVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long createAdjustment(AdjustmentVO obj, Long userId) throws BusinessException {
        log.debug("== Inicia createAdjustment/AdjustmentBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        Long adjId = 0l;
        try {
        	//Leer identificador del tipo
        	AdjustmentStatus adjustmentStatus = daoAdjustmentStatus.getAdjustmentStatusByCode(obj.getAdjustmentStatus().getCode());
        	UtilsBusiness.assertNotNull(adjustmentStatus, ErrorBusinessMessages.STOCK_IN394.getCode(), ErrorBusinessMessages.STOCK_IN394.getMessage() + " - Código : "+obj.getAdjustmentStatus().getCode());
        	
        	TransferReason transferReason = daoTransferReason.getTransferReasonByID(obj.getTransferReason().getId());
        	UtilsBusiness.assertNotNull(transferReason, ErrorBusinessMessages.STOCK_IN395.getCode(), ErrorBusinessMessages.STOCK_IN394.getMessage() + " - Código : "+obj.getTransferReason().getId());
        	
        	
        	obj.setAdjustmentStatus(adjustmentStatus);
        	obj.setTransferReason(transferReason);
        	
        	if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	obj.setAuthorized(obj.getTransferReason().getTransferReasonAuthorized());
        	
            Adjustment objPojo =  UtilsBusiness.copyObject(Adjustment.class, obj);
            objPojo.setCreationDate(UtilsBusiness.getCurrentTimeZoneDateByUserId(userId, daoUser));
            adjId = daoAdjustment.createAdjustment(objPojo);
            adjustmentModificationBusinessBean.createAdjustmentModification(adjId, adjustmentStatus.getCode(), userId);
            return adjId;
            
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createAdjustment/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustment/AdjustmentBusinessBean ==");
        }
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.AdjustmentBusinessBeanLocal#updateAdjustment(co.com.directv.sdii.model.vo.AdjustmentVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateAdjustment(AdjustmentVO obj, Long userId) throws BusinessException {
		
		log.debug("== Inicia updateAdjustment/AdjustmentBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            Adjustment objPojo =  UtilsBusiness.copyObject(Adjustment.class, obj);
            daoAdjustment.updateAdjustment(objPojo);
            adjustmentModificationBusinessBean.createAdjustmentModification(objPojo.getId(), objPojo.getAdjustmentStatus().getCode(), userId);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateAdjustment/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateAdjustment/AdjustmentBusinessBean ==");
        }
        
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.AdjustmentBusinessBeanLocal#deleteAdjustment(co.com.directv.sdii.model.vo.AdjustmentVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteAdjustment(AdjustmentVO obj) throws BusinessException {
        log.debug("== Inicia deleteAdjustment/AdjustmentBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Adjustment objPojo =  UtilsBusiness.copyObject(Adjustment.class, obj);
            daoAdjustment.deleteAdjustment(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteAdjustment/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteAdjustment/AdjustmentBusinessBean ==");
        }
	}


	@Override
	public List<AdjustmentTypeVO> getAllAdjustmentsTypes()
			throws BusinessException {
		log.debug("== Inicia getAllAdjustmentsTypes/AdjustmentBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoAdjustmentType.getAllAdjustmentTypes(), AdjustmentTypeVO.class);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllAdjustmentsTypes/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllAdjustmentsTypes/AdjustmentBusinessBean ==");
        }
	}

	/**
	 * 
	 * Metodo: Utilitario para consultar la ubicación de un cliente
	 * @param ibsCode codigo ibs del cliente
	 * @param countryId pais del cliente
	 * @return id de la bodega encontrada
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	private Long getWarehouseIDFromCustomer(String ibsCode, Long countryId) throws BusinessException{
		Long warehouseID = new Long(-1);
		try{
			ibsCode = ibsCode.trim();
			Customer customer = daoCustomer.getCustomerByCode(ibsCode);
			if(customer==null){
				throw new BusinessException(ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getMessage());
			}
			Warehouse warehouse = daoWarehouse.getCustomerWarehouseByCountry(customer.getId(), countryId);
			if(warehouse== null){
				throw new BusinessException(ErrorBusinessMessages.CUSTOMER_DONT_HAVE_WH.getCode(), ErrorBusinessMessages.CUSTOMER_DONT_HAVE_WH.getMessage());
			}
			warehouseID = warehouse.getId();
		}catch (Throwable e) {
			throw this.manageException(e);
		}
		return warehouseID;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public AdjustmentVO createAdjustmentSerializedElement(List<AdjustmentElementDTO> listAdjustmentElementsVO, AdjustmentVO adjustmentVO, Long userId) throws BusinessException {
		log.debug("== Inicia createAdjustment	InSerializedElement/AdjustmentBusinessBean ==");
		UtilsBusiness.assertNotNull(adjustmentVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(adjustmentVO.getComent(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(adjustmentVO.getTransferReason(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(adjustmentVO.getTransferReason().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(listAdjustmentElementsVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(adjustmentVO.getCountry(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(adjustmentVO.getCountry().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(adjustmentVO.getCreationUser(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(adjustmentVO.getCreationUser().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
        try {
        	
        	if(listAdjustmentElementsVO.size()==0){
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN465.getCode(),ErrorBusinessMessages.STOCK_IN465.getMessage());
        	}
        	
        	//Valido el se llena el objeto de TransferReason
        	TransferReason transferReason = daoTransferReason.getTransferReasonByID(adjustmentVO.getTransferReason().getId());
        	if(transferReason==null){
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	adjustmentVO.setTransferReason(transferReason);
        	
        	//Setear de manera automatica el estado del ajuste a creado
        	adjustmentVO.setAdjustmentStatus(new AdjustmentStatus(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_CREATE.getCodeEntity()));
        	adjustmentVO.setIsSerialized(CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity());
        	
        	if(adjustmentVO.getTransferReason().getAdjustmentType().getCode().equals(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_INPUT.getCodeEntity())){
        		this.createAdjustmentInputSerializedElement(listAdjustmentElementsVO, adjustmentVO, userId);
        	}else if(adjustmentVO.getTransferReason().getAdjustmentType().getCode().equals(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_OUTPUT.getCodeEntity())){
        		this.createAdjustmentOutputSerializedElement(listAdjustmentElementsVO, adjustmentVO,  userId);
        	}else if(adjustmentVO.getTransferReason().getAdjustmentType().getCode().equals(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_TRANSFER.getCodeEntity())){
        		this.createAdjustmentTransferSerializedElement(listAdjustmentElementsVO, adjustmentVO,  userId);
        	}else{
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN420.getCode(), ErrorBusinessMessages.STOCK_IN420.getMessage());
        	}
        	adjustmentVO = getAdjustmentByID(adjustmentVO.getId());
        	return adjustmentVO;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createAdjustmentInSerializedElement/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentInSerializedElement/AdjustmentBusinessBean ==");
        }
		
	}

	/**
	 * Metodo: Método utilitario encargado de crear ajustes de translado de elementos serializados
	 * @param listAdjustmentElementsVO
	 * @param adjustmentVO
	 * @param userId
	 * @throws BusinessException
	 * @author waguilera
	 */
	private void createAdjustmentTransferSerializedElement(List<AdjustmentElementDTO> listAdjustmentElementsVO, AdjustmentVO adjustmentVO, Long userId) throws BusinessException{
		log.debug("== Inicia createAdjustmentTransferSerializedElement/AdjustmentBusinessBean ==");
		try {
			
			User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
			
			//Valido si viene IBS
			if(adjustmentVO.getIbsCodeCustomer()!=null&&adjustmentVO.getIbsCodeCustomer().length()>0){
				adjustmentVO.setWarehouseTargetID(getWarehouseIDFromCustomer(adjustmentVO.getIbsCodeCustomer(), adjustmentVO.getCountry().getId()));
			}
			
			//Controlar movimiento entre dos codigos IBS
			if((adjustmentVO.getIbsCodeCustomerSource() != null 
				&& adjustmentVO.getIbsCodeCustomerSource().length() > 0) 
			   && (adjustmentVO.getIbsCodeCustomer() != null 
				   && adjustmentVO.getIbsCodeCustomer().length() > 0)) {
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN504.getCode(), ErrorBusinessMessages.STOCK_IN504.getMessage());
			}
			
			//realiza el fill de ubicacion origen y destino a nivel de elemento
			this.asignarSourceAndTarget(listAdjustmentElementsVO, adjustmentVO);
			
			//Crea el registro del ajuste
			Long idAdjustment = createAdjustment(adjustmentVO, userId);
        	adjustmentVO.setId(idAdjustment);  
        	
        	MovementElementDTO dtoGenerics=null;
        	if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){
        		dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(adjustmentVO.getTransferReason().getMovTypeIn().getMovTypeCode(),
					                                                                                 adjustmentVO.getTransferReason().getMovTypeOut().getMovTypeCode());
        	}else{
        		dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(CodesBusinessEntityEnum.MOVEMENT_TYPE_WAREHOUSE_TYPE_TRANSIT_IN.getCodeEntity(),
        																			  CodesBusinessEntityEnum.MOVEMENT_TYPE_WAREHOUSE_TYPE_TRANSIT_OUT.getCodeEntity());
        	}
			
        	Warehouse warehouse = warehouseBusinessBean.getWareHouseAdjusmentTransit(user.getCountry().getId());
        	//Itera los elementos recibidos en el servicio
        	for(AdjustmentElementDTO obj: listAdjustmentElementsVO){
        		adjustmentElementsBusinessBean.adjustmentTransferElementSerialized(adjustmentVO, obj, user,warehouse,dtoGenerics);
        	}
        	
        	//Consulta del ajuste creado para llenar todos los datos
        	adjustmentVO = getAdjustmentByID(idAdjustment);
        	if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equalsIgnoreCase(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){
        		AdjustmentStatus adjustmentStatusProcessCompleted = daoAdjustmentStatus.getAdjustmentStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PROCESS.getCodeEntity());
        		adjustmentVO.setAdjustmentStatus(adjustmentStatusProcessCompleted);
        		updateAdjustment(adjustmentVO,userId);
        	}else{
        		AdjustmentStatus adjustmentStatusProcessPending = daoAdjustmentStatus.getAdjustmentStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PENDING.getCodeEntity());
        		adjustmentVO.setAdjustmentStatus(adjustmentStatusProcessPending);
        		updateAdjustment(adjustmentVO,userId);
        	}
        	
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createAdjustmentTransferSerializedElement/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentTransferSerializedElement/AdjustmentBusinessBean ==");
        }
		
	}

	/**
	 * Metodo: Método encargado de la creación de ajustes de entrada para elementos serializados
	 * Esta operación crea el ajuste, el detalle y el serial 
	 * @param listAdjustmentElementsVO
	 * @param adjustmentVO
	 * @param userId
	 * @throws BusinessException
	 * @author waguilera
	 */
	private void createAdjustmentInputSerializedElement(List<AdjustmentElementDTO> listAdjustmentElementsVO, AdjustmentVO adjustmentVO, Long userId) throws BusinessException{
		log.debug("== Inicia createAdjustmentInputSerializedElement/AdjustmentBusinessBean ==");
		try {
        	asignarTarget(listAdjustmentElementsVO, adjustmentVO);
        	
        	//Se envia la petición de creación del ajuste (Maestro del documento)
        	Long idAdjustment = createAdjustment(adjustmentVO, userId);
        	adjustmentVO.setId(idAdjustment);
        	adjustmentVO = getAdjustmentByID(idAdjustment);
        	
        	//Consulto el usuario 
        	User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
        	
        	MovementElementDTO dtoGenerics=null;
        	if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){
        		dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(adjustmentVO.getTransferReason().getMovTypeIn().getMovTypeCode(), 
                        															  null);
        	}else{
        		dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(CodesBusinessEntityEnum.MOVEMENT_TYPE_WAREHOUSE_TYPE_TRANSIT_IN.getCodeEntity(),
        																			  null);
        	}
        	Warehouse warehouse = warehouseBusinessBean.getWareHouseAdjusmentTransit(user.getCountry().getId());
        	//Itero la lista de detalle de los elementos para realizar el ajuste de salida uno a uno
        	for(AdjustmentElementDTO obj: listAdjustmentElementsVO){
        		adjustmentElementsBusinessBean.createInputElementSerialized(adjustmentVO, obj, user,warehouse,dtoGenerics);
        	}
        	
        	//si el causal requiere o no autorización genera el estado del documento
        	//Procesado o pendiente
        	if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equalsIgnoreCase(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){
        		AdjustmentStatus adjustmentStatusProcessCompleted = daoAdjustmentStatus.getAdjustmentStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PROCESS.getCodeEntity());
        		adjustmentVO.setAdjustmentStatus(adjustmentStatusProcessCompleted);
        		updateAdjustment(adjustmentVO,userId);
        	}else{
        		AdjustmentStatus adjustmentStatusProcessPending = daoAdjustmentStatus.getAdjustmentStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PENDING.getCodeEntity());
        		adjustmentVO.setAdjustmentStatus(adjustmentStatusProcessPending);
        		updateAdjustment(adjustmentVO,userId);
        	}
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createAdjustmentInputSerializedElement/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentInputSerializedElement/AdjustmentBusinessBean ==");
        }
		
	}
	
	public AdjustmentElementsResponse searchAdjustmentsBySearchParameters(AdjustmentRequestDTO params, RequestCollectionInfo requestCollInfo) throws BusinessException{
		log.debug("== Inicia searchAdjustmentsBySearchParameters/AdjustmentBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(params.getCountryId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			AdjustmentElementsResponse responseDB=daoAdjustment.searchAdjustmentsBySearchParameters(params, requestCollInfo);

			return responseDB;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación searchAdjustmentsBySearchParameters/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina searchAdjustmentsBySearchParameters/AdjustmentBusinessBean ==");
        }
	}
	
	/**
	 * Metodo: Operación encargada de la realización de los ajustes de 
	 * salida de elementos serializados
	 * @param listAdjustmentElementsVO
	 * @param adjustmentVO
	 * @param isLoadFile
	 * @param userId
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	private void createAdjustmentOutputSerializedElement(List<AdjustmentElementDTO> listAdjustmentElementsVO, AdjustmentVO adjustmentVO, Long userId) throws BusinessException{
		log.debug("== Inicia createAdjustmentOutputSerializedElement/AdjustmentBusinessBean ==");
		try {
			
			User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
			
			asignarSource(listAdjustmentElementsVO, adjustmentVO);
			
        	Long idAdjustment = createAdjustment(adjustmentVO, userId);
        	adjustmentVO.setId(idAdjustment);  
        	//Consulta del ajuste creado para llenar todos los datos
        	adjustmentVO = getAdjustmentByID(idAdjustment);
        	
        	MovementElementDTO dtoGenerics=null;
        	if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){
        		dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(null, 
                        															  adjustmentVO.getTransferReason().getMovTypeOut().getMovTypeCode());
        	}else{
        		dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(CodesBusinessEntityEnum.MOVEMENT_TYPE_WAREHOUSE_TYPE_TRANSIT_IN.getCodeEntity(),
        																			  CodesBusinessEntityEnum.MOVEMENT_TYPE_WAREHOUSE_TYPE_TRANSIT_OUT.getCodeEntity());
        	}
        	
			Warehouse warehouse = warehouseBusinessBean.getWareHouseAdjusmentTransit(user.getCountry().getId());
        	//Se iteran los elementos que se desean sacar de inventario y vienen en el request
        	for(AdjustmentElementDTO obj: listAdjustmentElementsVO){
        		adjustmentElementsBusinessBean.adjustmentOutputElementSerialized(adjustmentVO, obj, user,warehouse,dtoGenerics);
        	}
        	
        	//Se define el nuevo estado del documento  a partir de la marca de autorización
        	if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equalsIgnoreCase(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){
        		AdjustmentStatus adjustmentStatusProcessCompleted = daoAdjustmentStatus.getAdjustmentStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PROCESS.getCodeEntity());
        		adjustmentVO.setAdjustmentStatus(adjustmentStatusProcessCompleted);
        		updateAdjustment(adjustmentVO,userId);
        	}else{
        		AdjustmentStatus adjustmentStatusProcessPending = daoAdjustmentStatus.getAdjustmentStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PENDING.getCodeEntity());
        		adjustmentVO.setAdjustmentStatus(adjustmentStatusProcessPending);
        		updateAdjustment(adjustmentVO,userId);
        	}
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createAdjustmentOutputSerializedElement/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentOutputSerializedElement/AdjustmentBusinessBean ==");
        }
		
	}
	
	/**
	 * Método encargado de llenar los datos faltantes de ubicación origen y ubicación destino
	 * para el detalle de los elementos. Esto se hace porque la ubicacion lo envian para todos
	 * los elementos a nivel de una dto maestra 
	 * @param listAdjustmentElementsVO
	 * @param adjustmentVO
	 * @throws BusinessException
	 * @author waguilera 
	 */
	private void asignarSourceAndTarget(List<AdjustmentElementDTO> listAdjustmentElementsVO, AdjustmentVO adjustmentVO) throws BusinessException{
		UtilsBusiness.assertNotNull(adjustmentVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(adjustmentVO.getWarehouseTargetID(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		for(AdjustmentElementDTO dto : listAdjustmentElementsVO){
			dto.setIdWarehouse(adjustmentVO.getWarehouseSourceID());
			dto.setIdWarehouseDestination(adjustmentVO.getWarehouseTargetID());
		}
	}
	
	/**
	 * Método encargado de llenar los datos faltantes de ubicación origen
	 * para el detalle de los elementos. Esto se hace porque la ubicacion lo envian para todos
	 * los elementos a nivel de una dto maestra 
	 * @param listAdjustmentElementsVO
	 * @param adjustmentVO
	 * @throws BusinessException
	 * @author waguilera 
	 */
	private void asignarSource(List<AdjustmentElementDTO> listAdjustmentElementsVO, AdjustmentVO adjustmentVO) throws BusinessException{
		UtilsBusiness.assertNotNull(adjustmentVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		for(AdjustmentElementDTO dto : listAdjustmentElementsVO){
			dto.setIdWarehouse(adjustmentVO.getWarehouseSourceID());
		}
	}
	
	/**
	 * Método encargado de llenar los datos faltantes de ubicación destino
	 * para el detalle de los elementos. Esto se hace porque la ubicacion lo envian para todos
	 * los elementos  
	 * @param listAdjustmentElementsVO
	 * @param adjustmentVO
	 * @throws BusinessException
	 * @author waguilera 
	 */
	private void asignarTarget(List<AdjustmentElementDTO> listAdjustmentElementsVO, AdjustmentVO adjustmentVO) throws BusinessException{
		UtilsBusiness.assertNotNull(adjustmentVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(adjustmentVO.getWarehouseTargetID(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		for(AdjustmentElementDTO dto : listAdjustmentElementsVO){
			dto.setIdWarehouseDestination(adjustmentVO.getWarehouseTargetID());
		}
	}

	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public AdjustmentVO createAdjustmentNotSerializedElement(List<AdjustmentElementDTO> listAdjustmentElementsVO, AdjustmentVO adjustmentVO, Long userId) throws BusinessException {
		log.debug("== Inicia createAdjustment	InSerializedElement/AdjustmentBusinessBean ==");
		UtilsBusiness.assertNotNull(adjustmentVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(adjustmentVO.getComent(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(adjustmentVO.getTransferReason(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(adjustmentVO.getTransferReason().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(listAdjustmentElementsVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(adjustmentVO.getCountry(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(adjustmentVO.getCountry().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(adjustmentVO.getCreationUser(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(adjustmentVO.getCreationUser().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
        try {
        	
        	if(listAdjustmentElementsVO.size()==0){
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN465.getCode(),ErrorBusinessMessages.STOCK_IN465.getMessage());
        	}
        	
        	//Valido el se llena el objeto de TransferReason
        	TransferReason transferReason = daoTransferReason.getTransferReasonByID(adjustmentVO.getTransferReason().getId());
        	if(transferReason==null){
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	adjustmentVO.setTransferReason(transferReason);
        	adjustmentVO.setIsSerialized(CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity());
        	
        	//Setear de manera automatica el estado del ajuste a creado
        	adjustmentVO.setAdjustmentStatus(new AdjustmentStatus(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_CREATE.getCodeEntity()));
        	
        	if(adjustmentVO.getTransferReason().getAdjustmentType().getCode().equals(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_INPUT.getCodeEntity())){
        		this.createAdjustmentInputNotSerializedElement(listAdjustmentElementsVO, adjustmentVO, false, userId);
        	}else if(adjustmentVO.getTransferReason().getAdjustmentType().getCode().equals(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_OUTPUT.getCodeEntity())){
        		this.createAdjustmentOutputNotSerializedElement(listAdjustmentElementsVO, adjustmentVO, false, userId);
        	}else if(adjustmentVO.getTransferReason().getAdjustmentType().getCode().equals(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_TRANSFER.getCodeEntity())){
        		this.createAdjustmentTransferNotSerializedElement(listAdjustmentElementsVO, adjustmentVO, false, userId);
        	}else{
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN420.getCode(), ErrorBusinessMessages.STOCK_IN420.getMessage());
        	}
        	adjustmentVO = getAdjustmentByID(adjustmentVO.getId());
        	return adjustmentVO;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createAdjustmentInSerializedElement/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentInSerializedElement/AdjustmentBusinessBean ==");
        }
		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public SerializedVO findSerializedElement(String serialCode, Long userId) throws BusinessException {
		log.debug("== Inicia findSerializedElement/AdjustmentBusinessBean ==");
		UtilsBusiness.assertNotNull(serialCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		Long recordQty;
		SerializedVO serializedVO = null;
		try {
        	User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
        	
       		Serialized objPojo = daoSerialized.getSerializedBySerial(serialCode,user.getCountry().getId());
       		serializedVO = UtilsBusiness.copyObject(SerializedVO.class, objPojo);
        	
        	if(serializedVO != null){
        	    recordQty = daoWarehouseElement.existsWarehouseElementByCountryAndSerial(serialCode, user.getCountry().getId());
        	    // si el elemento existe y tiene una ubicacion se lanza excepcion
        	    if(recordQty > 0L){
        	    	Object[] params = new Object[1];
        	    	params[0] = serialCode;
        	    	throw new BusinessException(
        	    			ErrorBusinessMessages.STOCK_IN482.getCode(), 
        	    			ErrorBusinessMessages.STOCK_IN482.getMessage(params), 
        	    			UtilsBusiness.getParametersWS(params));
        	    }
        	}
        	
        	return serializedVO;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación findSerializedElement/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina findSerializedElement/AdjustmentBusinessBean ==");
        }
		
	}
	
	@Override
	public void createAdjustmentElementsAuthorization(AdjustmenElementsRequestDTO request) throws BusinessException {
		log.debug("== Inicia createAdjustmentElementsAuthorization/AdjustmentBusinessBean ==");
		
		Adjustment adjustmentPojo = null;
		AdjustmentStatus previousAdjustStatus = null;
		try {
			
			AdjustmentElements adjustmentElement;
			MovementElementDTO dtoGenerics;
        	
			adjustmentPojo = daoAdjustment.getAdjustmentByID(request.getAdjustmentId());
			previousAdjustStatus = adjustmentPojo.getAdjustmentStatus();
			if (!CodesBusinessEntityEnum.ADJUSTMENT_STATUS_AUTHORIZING.getCodeEntity().equals(adjustmentPojo.getAdjustmentStatus().getCode())){
				
				//begin - Update status to Authorizing
				AdjustmentStatus adjustmentStatus = null;
				AdjustmentVO adjustmentVO = UtilsBusiness.copyObject(AdjustmentVO.class, adjustmentPojo);
		       	
				adjustmentStatus = daoAdjustmentStatus.getAdjustmentStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_AUTHORIZING.getCodeEntity());
				adjustmentVO.setAdjustmentStatus(adjustmentStatus);
				adjustmentHelperBusinessBean.updateAdjustment(adjustmentVO, request.getUserId());
				//end - Update status to Authorizing
				
				
				//continue with the previous logic	
				User user = daoUser.getUserById(request.getUserId());
	        	if(user == null){
	        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
	        	}
	
				if((!user.getRol().getRoleType().getRoleTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum.ROLE_TYPE_BACKOFFICE.getCodeEntity())) 
					&& (!user.getRol().getRoleType().getRoleTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_ANALYST.getCodeEntity())) 
					&& (!user.getRol().getRoleType().getRoleTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum.ROLE_TYPE_ROOT.getCodeEntity()))){
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN509.getCode(), ErrorBusinessMessages.STOCK_IN509.getMessage());
				}
	        	
				if(adjustmentPojo.getTransferReason().getAdjustmentType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_INPUT.getCodeEntity()) 
				   || adjustmentPojo.getTransferReason().getAdjustmentType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_TRANSFER.getCodeEntity())){
	
		        	dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(
		        			      adjustmentPojo.getTransferReason().getMovTypeIn().getMovTypeCode(), 
		        			      CodesBusinessEntityEnum.MOVEMENT_TYPE_WAREHOUSE_TYPE_TRANSIT_OUT.getCodeEntity());				
				}else{
						
		        	dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(null, adjustmentPojo.getTransferReason().getMovTypeOut().getMovTypeCode());				
				}
					
		       	for(Long adjustmentId : request.getAdjustmentIdList()){
		
		       		adjustmentElement = daoAdjustmentElements.getAdjustmentElementsByID(adjustmentId);
		   			if(adjustmentPojo.getIsSerialized().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity())){
		       		    createAdjustmentSerializedElementsAuthorization(adjustmentElement, dtoGenerics, user);
		   			}else{
		   				createAdjustmentNotSerializedElementsAuthorization(adjustmentElement, dtoGenerics, user);
		   			}
		       	}
		       	
		       	//consulta la cantidad de elementos por estado para asignar estado al ajuste maestro
		       	/*
		       	 * 1. pendientes
		       	 * 2. procesados
		       	 * 3. autorizado
		       	 * */
		       	Object[] countAllAdjusmentElement = daoAdjustmentElements.countAdjustmentElementsByAllStatus(request.getAdjustmentId());
		       	Long quantityPending = (Long) countAllAdjusmentElement[0];
		       	Long quantityAuthorized = (Long) countAllAdjusmentElement[2];
		       	
		       	if(quantityPending > 0L && quantityAuthorized > 0L){
		       		adjustmentStatus = daoAdjustmentStatus.getAdjustmentStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PARTIAL_AUTHORIZED.getCodeEntity());
		        	adjustmentVO.setAdjustmentStatus(adjustmentStatus);
		        	adjustmentHelperBusinessBean.updateAdjustment(adjustmentVO,request.getUserId());
		       	}else if(quantityAuthorized > 0L){
	        		adjustmentStatus = daoAdjustmentStatus.getAdjustmentStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_AUTHORIZED.getCodeEntity());
	            	adjustmentVO.setAdjustmentStatus(adjustmentStatus);
	            	adjustmentHelperBusinessBean.updateAdjustment(adjustmentVO,request.getUserId());
		       	}else{
	        		//Para que no quede autorizando.
	        		adjustmentVO.setAdjustmentStatus(previousAdjustStatus);
	        		adjustmentHelperBusinessBean.updateAdjustment(adjustmentVO,request.getUserId());
	        	}
		       	
			}
			
        } catch(Throwable ex){
        	AdjustmentVO adjustmentVO = UtilsBusiness.copyObject(AdjustmentVO.class, adjustmentPojo);
			adjustmentVO.setAdjustmentStatus(previousAdjustStatus);
			adjustmentHelperBusinessBean.updateAdjustment(adjustmentVO,request.getUserId());
        	log.debug("== Error al tratar de ejecutar la operación createAdjustmentElementsAuthorization/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentElementsAuthorization/AdjustmentBusinessBean ==");
        }
	}

	/**
	 * Metodo encargado de registrar el ajuste para el elemento indicado
	 * @param adjustmentElement
	 * @param dtoGenerics
	 * @param user
	 * @throws BusinessException
	 */
	private void createAdjustmentSerializedElementsAuthorization(
			AdjustmentElements adjustmentElement, MovementElementDTO dtoGenerics, User user) throws BusinessException {
		log.debug("== Inicia createAdjustmentSerializedElementsAuthorization/AdjustmentBusinessBean ==");
		try {
			MovementElementDTO dtoMovement;
	        if(adjustmentElement != null && adjustmentElement.getAdjustment() != null){
        		if(adjustmentElement.getAdjustmentElementsStatus().getCode()
        				.equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PENDING.getCodeEntity())){
	
    				if(adjustmentElement.getAdjustment().getTransferReason().getAdjustmentType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_INPUT.getCodeEntity()) 
    				   || adjustmentElement.getAdjustment().getTransferReason().getAdjustmentType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_TRANSFER.getCodeEntity())){
	    							
						    dtoMovement = new MovementElementDTO(user,
								UtilsBusiness.copyObject(WarehouseVO.class, warehouseBusinessBean.getWareHouseAdjusmentTransit(user.getCountry().getId())),
								UtilsBusiness.copyObject(WarehouseVO.class, adjustmentElement.getWarehouseDestination()), 
								adjustmentElement.getSerialized(), 
								adjustmentElement.getAdjustment().getId(), 
								CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity(), 
								dtoGenerics.getMovTypeCodeE(),
								dtoGenerics.getMovTypeCodeS(),
								dtoGenerics.getRecordStatusU(),
								dtoGenerics.getRecordStatusH(),
								true,
								CodesBusinessEntityEnum.PROCESS_CODE_IBS_ADJUSMENT.getCodeEntity(),
								dtoGenerics.getMovConfigStatusPending(),
								dtoGenerics.getMovConfigStatusNoConfig());
								    
					    businessMovementElement.moveSerializedElementBetweenWarehouse(dtoMovement);
						
    				}else if(adjustmentElement.getAdjustment().getTransferReason().getAdjustmentType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_OUTPUT.getCodeEntity())){

    					MovementElementDTO dto = new MovementElementDTO(user, 
    						UtilsBusiness.copyObject(WarehouseVO.class, warehouseBusinessBean.getWareHouseAdjusmentTransit(user.getCountry().getId())), 
    						adjustmentElement.getSerialized(),
    						adjustmentElement.getAdjustment().getId(), 
    						CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity(),
    						dtoGenerics.getMovTypeCodeS(),
    						dtoGenerics.getRecordStatusU(),
    						dtoGenerics.getRecordStatusH(), 
    						true,
    						CodesBusinessEntityEnum.PROCESS_CODE_IBS_ADJUSMENT.getCodeEntity(),
    						dtoGenerics.getMovConfigStatusPending(),
    						dtoGenerics.getMovConfigStatusNoConfig());
    							
    					businessMovementElement.deleteSerializedElementInWarehouse(dto);    							
    				}
	    					
    				AdjustmentElementsStatus adjustmentElementsStatus = daoAdjustmentElementsStatus.getAdjustmentElementsStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_AUTHORIZED.getCodeEntity());
    				adjustmentElement.setAdjustmentElementsStatus(adjustmentElementsStatus);
    				adjustmentElement.setAuthorizedUser(user);
    				adjustmentElement.setAuthorizationDate(UtilsBusiness.getCurrentTimeZoneDateByUserId(user.getId(), daoUser));
    				daoAdjustmentElements.updateAdjustmentElements(adjustmentElement);

	    		}else{
	    			throw new BusinessException(ErrorBusinessMessages.STOCK_IN510.getCode(), ErrorBusinessMessages.STOCK_IN510.getMessage());
	    		}
	        }
			
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createAdjustmentSerializedElementsAuthorization/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentSerializedElementsAuthorization/AdjustmentBusinessBean ==");
        }
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void createAdjustmentSerializedElementsAuthorizationMassive(
			AdjustmentElements adjustmentElement, MovementElementDTO dtoGenerics, User user) throws BusinessException {
		log.debug("== Inicia createAdjustmentSerializedElementsAuthorizationMassive/AdjustmentBusinessBean ==");
		try {
			MovementElementDTO dtoMovement;
	        if(adjustmentElement != null && adjustmentElement.getAdjustment() != null){
        		if(adjustmentElement.getAdjustmentElementsStatus().getCode()
        				.equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PENDING.getCodeEntity())){
	
    				if(adjustmentElement.getAdjustment().getTransferReason().getAdjustmentType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_INPUT.getCodeEntity()) 
    				   || adjustmentElement.getAdjustment().getTransferReason().getAdjustmentType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_TRANSFER.getCodeEntity())){
	    							
						    dtoMovement = new MovementElementDTO(user,
								UtilsBusiness.copyObject(WarehouseVO.class, warehouseBusinessBean.getWareHouseAdjusmentTransit(user.getCountry().getId())),
								UtilsBusiness.copyObject(WarehouseVO.class, adjustmentElement.getWarehouseDestination()), 
								adjustmentElement.getSerialized(), 
								adjustmentElement.getAdjustment().getId(), 
								CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity(), 
								dtoGenerics.getMovTypeCodeE(),
								dtoGenerics.getMovTypeCodeS(),
								dtoGenerics.getRecordStatusU(),
								dtoGenerics.getRecordStatusH(),
								true,
								CodesBusinessEntityEnum.PROCESS_CODE_IBS_ADJUSMENT.getCodeEntity(),
								dtoGenerics.getMovConfigStatusPending(),
								dtoGenerics.getMovConfigStatusNoConfig());
								    
					    businessMovementElement.moveSerializedElementBetweenWarehouse(dtoMovement);
						
    				}else if(adjustmentElement.getAdjustment().getTransferReason().getAdjustmentType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_OUTPUT.getCodeEntity())){

    					MovementElementDTO dto = new MovementElementDTO(user, 
    						UtilsBusiness.copyObject(WarehouseVO.class, warehouseBusinessBean.getWareHouseAdjusmentTransit(user.getCountry().getId())), 
    						adjustmentElement.getSerialized(),
    						adjustmentElement.getAdjustment().getId(), 
    						CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity(),
    						dtoGenerics.getMovTypeCodeS(),
    						dtoGenerics.getRecordStatusU(),
    						dtoGenerics.getRecordStatusH(), 
    						true,
    						CodesBusinessEntityEnum.PROCESS_CODE_IBS_ADJUSMENT.getCodeEntity(),
    						dtoGenerics.getMovConfigStatusPending(),
    						dtoGenerics.getMovConfigStatusNoConfig());
    							
    					businessMovementElement.deleteSerializedElementInWarehouse(dto);    							
    				}
	    					
    				AdjustmentElementsStatus adjustmentElementsStatus = daoAdjustmentElementsStatus.getAdjustmentElementsStatusByCodeMassive(CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_AUTHORIZED.getCodeEntity());
    				adjustmentElement.setAdjustmentElementsStatus(adjustmentElementsStatus);
    				adjustmentElement.setAuthorizedUser(user);
    				adjustmentElement.setAuthorizationDate(UtilsBusiness.getCurrentTimeZoneDateByUserId(user.getId(), daoUser));
    				daoAdjustmentElements.updateAdjustmentElements(adjustmentElement);

	    		}else{
	    			throw new BusinessException(ErrorBusinessMessages.STOCK_IN510.getCode(), ErrorBusinessMessages.STOCK_IN510.getMessage());
	    		}
	        }
			
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createAdjustmentSerializedElementsAuthorizationMassive/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentSerializedElementsAuthorizationMassive/AdjustmentBusinessBean ==");
        }
	}
	
	/**
	 * Metodo encargado de registrar el ajuste para elementos no serializados
	 * @param adjustmentElement
	 * @param dtoGenerics
	 * @param user
	 * @throws BusinessException
	 */	
	private void createAdjustmentNotSerializedElementsAuthorization(AdjustmentElements adjustmentElement, MovementElementDTO dtoGenerics, User user) throws BusinessException {
		log.debug("== Inicia createAdjustmentNotSerializedElementsAuthorization/AdjustmentBusinessBean ==");
		try {
			MovementElementDTO dtoMovement;
        	AdjustmentVO adjustmentVO = UtilsBusiness.copyObject(AdjustmentVO.class, adjustmentElement);
        	if(adjustmentElement != null && adjustmentElement.getAdjustment() != null){
        		if(adjustmentElement.getAdjustmentElementsStatus().getCode()
        				.equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PENDING.getCodeEntity())){
        			
   					ElementType elementType = adjustmentElement.getNotSerialized().getElement().getElementType();
 					if(adjustmentElement.getAdjustment().getTransferReason().getAdjustmentType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_INPUT.getCodeEntity()) 
 					   || adjustmentElement.getAdjustment().getTransferReason().getAdjustmentType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_TRANSFER.getCodeEntity())){    						
    						
    					dtoMovement = new MovementElementDTO(user,
    								UtilsBusiness.copyObject(WarehouseVO.class, warehouseBusinessBean.getWareHouseAdjusmentTransit(user.getCountry().getId())), 
	    							UtilsBusiness.copyObject(WarehouseVO.class, adjustmentElement.getWarehouseDestination()), 
	    							null,
	    							elementType.getId(),
	    							elementType.getTypeElementCode(),
	    							adjustmentElement.getAdjustment().getId(), 
	    							CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity(), 
	    							dtoGenerics.getMovTypeCodeE(),
	    							dtoGenerics.getMovTypeCodeS(),
	    							dtoGenerics.getRecordStatusU(),
	    							dtoGenerics.getRecordStatusH(),
	    							adjustmentElement.getInitialQuantity(),
	    							dtoGenerics.getMovConfigStatusPending(),
	    							dtoGenerics.getMovConfigStatusNoConfig());
	    						
    					businessMovementElement.moveNotSerializedElementBetweenWarehouse(dtoMovement);
   					}else if(adjustmentElement.getAdjustment().getTransferReason().getAdjustmentType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_OUTPUT.getCodeEntity())){
   						MovementElementDTO dto = new MovementElementDTO(user, 
   							UtilsBusiness.copyObject(WarehouseVO.class, warehouseBusinessBean.getWareHouseAdjusmentTransit(user.getCountry().getId())), 
   							null, elementType.getId(), 
   							null,
   							adjustmentVO.getId(), 
   							CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity(), 
   							dtoGenerics.getMovTypeCodeS(), 
   							dtoGenerics.getRecordStatusU(), 
   							dtoGenerics.getRecordStatusH(), 
   							adjustmentElement.getInitialQuantity(),
   							dtoGenerics.getMovConfigStatusPending(),
   							dtoGenerics.getMovConfigStatusNoConfig());
   						
   						businessMovementElement.deleteNotSerializedElementInWarehouse(dto);    							
   					}
   						
					AdjustmentElementsStatus adjustmentElementsStatus = daoAdjustmentElementsStatus.getAdjustmentElementsStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_AUTHORIZED.getCodeEntity());
					adjustmentElement.setAdjustmentElementsStatus(adjustmentElementsStatus);
					adjustmentElement.setAuthorizedUser(user);
					adjustmentElement.setAuthorizationDate(UtilsBusiness.getCurrentTimeZoneDateByUserId(user.getId(), daoUser));
					daoAdjustmentElements.updateAdjustmentElements(adjustmentElement);   						
   				}else{
   					throw new BusinessException(ErrorBusinessMessages.STOCK_IN510.getCode(), ErrorBusinessMessages.STOCK_IN510.getMessage());
   				}
       		}
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createAdjustmentNotSerializedElementsAuthorization/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentNotSerializedElementsAuthorization/AdjustmentBusinessBean ==");
        }
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void createAdjustmentNotSerializedElementsAuthorizationMassive(AdjustmentElements adjustmentElement, MovementElementDTO dtoGenerics, User user) throws BusinessException {
		log.debug("== Inicia createAdjustmentNotSerializedElementsAuthorizationMassive/AdjustmentBusinessBean ==");
		try {
			MovementElementDTO dtoMovement;
        	AdjustmentVO adjustmentVO = UtilsBusiness.copyObject(AdjustmentVO.class, adjustmentElement);
        	if(adjustmentElement != null && adjustmentElement.getAdjustment() != null){
        		if(adjustmentElement.getAdjustmentElementsStatus().getCode()
        				.equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PENDING.getCodeEntity())){
        			
   					ElementType elementType = adjustmentElement.getNotSerialized().getElement().getElementType();
 					if(adjustmentElement.getAdjustment().getTransferReason().getAdjustmentType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_INPUT.getCodeEntity()) 
 					   || adjustmentElement.getAdjustment().getTransferReason().getAdjustmentType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_TRANSFER.getCodeEntity())){    						
    						
    					dtoMovement = new MovementElementDTO(user,
    								UtilsBusiness.copyObject(WarehouseVO.class, warehouseBusinessBean.getWareHouseAdjusmentTransit(user.getCountry().getId())), 
	    							UtilsBusiness.copyObject(WarehouseVO.class, adjustmentElement.getWarehouseDestination()), 
	    							null,
	    							elementType.getId(),
	    							elementType.getTypeElementCode(),
	    							adjustmentElement.getAdjustment().getId(), 
	    							CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity(), 
	    							dtoGenerics.getMovTypeCodeE(),
	    							dtoGenerics.getMovTypeCodeS(),
	    							dtoGenerics.getRecordStatusU(),
	    							dtoGenerics.getRecordStatusH(),
	    							adjustmentElement.getInitialQuantity(),
	    							dtoGenerics.getMovConfigStatusPending(),
	    							dtoGenerics.getMovConfigStatusNoConfig());
	    						
    					businessMovementElement.moveNotSerializedElementBetweenWarehouse(dtoMovement);
   					}else if(adjustmentElement.getAdjustment().getTransferReason().getAdjustmentType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_OUTPUT.getCodeEntity())){
   						MovementElementDTO dto = new MovementElementDTO(user, 
   							UtilsBusiness.copyObject(WarehouseVO.class, warehouseBusinessBean.getWareHouseAdjusmentTransit(user.getCountry().getId())), 
   							null, elementType.getId(), 
   							null,
   							adjustmentVO.getId(), 
   							CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity(), 
   							dtoGenerics.getMovTypeCodeS(), 
   							dtoGenerics.getRecordStatusU(), 
   							dtoGenerics.getRecordStatusH(), 
   							adjustmentElement.getInitialQuantity(),
   							dtoGenerics.getMovConfigStatusPending(),
   							dtoGenerics.getMovConfigStatusNoConfig());
   						
   						businessMovementElement.deleteNotSerializedElementInWarehouse(dto);    							
   					}
   						
					AdjustmentElementsStatus adjustmentElementsStatus = daoAdjustmentElementsStatus.getAdjustmentElementsStatusByCodeMassive(CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_AUTHORIZED.getCodeEntity());
					adjustmentElement.setAdjustmentElementsStatus(adjustmentElementsStatus);
					adjustmentElement.setAuthorizedUser(user);
					adjustmentElement.setAuthorizationDate(UtilsBusiness.getCurrentTimeZoneDateByUserId(user.getId(), daoUser));
					daoAdjustmentElements.updateAdjustmentElements(adjustmentElement);   						
   				}else{
   					throw new BusinessException(ErrorBusinessMessages.STOCK_IN510.getCode(), ErrorBusinessMessages.STOCK_IN510.getMessage());
   				}
       		}
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createAdjustmentNotSerializedElementsAuthorizationMassive/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentNotSerializedElementsAuthorizationMassive/AdjustmentBusinessBean ==");
        }
		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void approvalAllElementsOfAdjustment(AdjustmenElementsRequestDTO request) throws BusinessException {
		log.debug("== Inicia approvalAllElementsOfAdjustment/AdjustmentBusinessBean ==");
		UtilsBusiness.assertNotNull(request.getAdjustmentId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
				
        Adjustment adjustment = null;
        AdjustmentStatus previousAdjustStatus = null;
        boolean hayExcepcion = false;
        try {
			
			adjustment = daoAdjustment.getAdjustmentByIDMassive(request.getAdjustmentId());
			previousAdjustStatus = adjustment.getAdjustmentStatus();
			
			if (!CodesBusinessEntityEnum.ADJUSTMENT_STATUS_AUTHORIZING.getCodeEntity().equals(adjustment.getAdjustmentStatus().getCode())){
				
				//begin - Update status to Authorizing
				AdjustmentStatus adjustmentStatus = null;
				AdjustmentVO adjustmentVO = UtilsBusiness.copyObject(AdjustmentVO.class, adjustment);
		       	
				adjustmentStatus = daoAdjustmentStatus.getAdjustmentStatusByCodeMassive(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_AUTHORIZING.getCodeEntity());
				adjustmentVO.setAdjustmentStatus(adjustmentStatus);
				adjustmentHelperBusinessBean.updateAdjustment(adjustmentVO,request.getUserId());
				//end - Update status to Authorizing
				
				//continue with the previous logic				
				User user = daoUser.getUserByIdMassive(request.getUserId());
	        	if(user == null){
	        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
	        	}    	
	        	List<AdjustmentElements> responseDB = daoAdjustmentElements.getAdjustmentElementsForAuthorizationMassive(request.getAdjustmentId());
				
				MovementElementDTO dtoGenerics = null;
				if((adjustment.getTransferReason() != null && adjustment.getTransferReason().getMovTypeIn()!=null) && (adjustment.getTransferReason()!=null && adjustment.getTransferReason().getMovTypeOut()!=null)){
					dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatusMassive(adjustment.getTransferReason().getMovTypeIn().getMovTypeCode(),
							adjustment.getTransferReason().getMovTypeOut().getMovTypeCode());				
				}else if((adjustment.getTransferReason()!=null && adjustment.getTransferReason().getMovTypeOut()!=null)){
					dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatusMassive(null, adjustment.getTransferReason().getMovTypeOut().getMovTypeCode());
				}else if((adjustment.getTransferReason() != null && adjustment.getTransferReason().getMovTypeIn()!=null)){
					dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatusMassive(adjustment.getTransferReason().getMovTypeIn().getMovTypeCode(),
							CodesBusinessEntityEnum.MOVEMENT_TYPE_WAREHOUSE_TYPE_TRANSIT_OUT.getCodeEntity());				
				}
	
				boolean serialized = false;
				boolean canToApprove=false;
				if(adjustment.getIsSerialized().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity())){
					serialized = true;
				}
				if(user.getRol().getRoleType().getRoleTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum.ROLE_TYPE_BACKOFFICE.getCodeEntity()) 
				   || user.getRol().getRoleType().getRoleTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_ANALYST.getCodeEntity()) 
				   || user.getRol().getRoleType().getRoleTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum.ROLE_TYPE_DTV_ADMIN.getCodeEntity()) 
				   || user.getRol().getRoleType().getRoleTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum.ROLE_TYPE_ROOT.getCodeEntity())){
					
					canToApprove=true;
					
				}
				if(canToApprove && dtoGenerics != null){
					for(AdjustmentElements adjustmentElements:responseDB){
						if(serialized){
				    		try {
				    			//Metodo que realiza la aprovacion de serializados
				    			createAdjustmentSerializedElementsAuthorizationMassive(adjustmentElements, dtoGenerics, user);
				    		} catch(Exception e) {
				    			log.error("ERROR AL PROCESAR EL ELEMENTO SERIALIZADO [" + adjustmentElements.getId() + "]:" + e.getMessage(),e);
				    			if(!hayExcepcion){
				    				hayExcepcion = true;
				    			}
				    		}	    		
				    	}else{
				    		try{
				    			//Metodo que realiza la aprovacion de NO serializados
				    			createAdjustmentNotSerializedElementsAuthorizationMassive(adjustmentElements, dtoGenerics, user);
				    		}catch(Exception e){
				    			log.error("ERROR AL PROCESAR EL ELEMENTO NO SERIALIZADO [" + adjustmentElements.getId() + "]:" + e.getMessage(),e);
				    			if(!hayExcepcion){
				    				hayExcepcion = true;
				    			}
				    		}	
				    	}
					}
			       	//consulta la cantidad de elementos por estado para asignar estado al ajuste maestro
			       	/*
			       	 * 1. pendientes
			       	 * 2. procesados
			       	 * 3. autorizado
			       	 * */
			       	Object[] countAllAdjusmentElement = daoAdjustmentElements.countAdjustmentElementsByAllStatusMassive(request.getAdjustmentId());
			       	Long quantityPending = (Long) countAllAdjusmentElement[0];
			       	Long quantityAuthorized = (Long) countAllAdjusmentElement[2];
			       				       				       	
			       	if(quantityPending > 0L && quantityAuthorized > 0L){
			       		adjustmentStatus = daoAdjustmentStatus.getAdjustmentStatusByCodeMassive(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PARTIAL_AUTHORIZED.getCodeEntity());
			        	adjustmentVO.setAdjustmentStatus(adjustmentStatus);
			        	//updateAdjustment(adjustmentVO,request.getUserId());
			        	adjustmentHelperBusinessBean.updateAdjustment(adjustmentVO,request.getUserId());
			       	}else if(quantityAuthorized > 0L){
		        		adjustmentStatus = daoAdjustmentStatus.getAdjustmentStatusByCodeMassive(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_AUTHORIZED.getCodeEntity());
		            	adjustmentVO.setAdjustmentStatus(adjustmentStatus);
		            	//updateAdjustment(adjustmentVO,request.getUserId());
		            	adjustmentHelperBusinessBean.updateAdjustment(adjustmentVO,request.getUserId());
		        	}else{
		        		//Para que no quede autorizando.
		        		adjustmentVO.setAdjustmentStatus(previousAdjustStatus);
		        		adjustmentHelperBusinessBean.updateAdjustment(adjustmentVO,request.getUserId());
		        	}
	            	
				}
				
			}
			
		}catch(Exception  ex){
        	log.debug("== Error al tratar de ejecutar la operación approvalAllElementsOfAdjustment/AdjustmentBusinessBean ==");
        
			AdjustmentVO adjustmentVO = UtilsBusiness.copyObject(AdjustmentVO.class, adjustment);
			adjustmentVO.setAdjustmentStatus(previousAdjustStatus);
			adjustmentHelperBusinessBean.updateAdjustment(adjustmentVO,request.getUserId());
		    log.debug("== Error al tratar de ejecutar la operación approvalAllElementsOfAdjustment/AdjustmentBusinessBean cuando se quiso hacer rollback del estado del ajuste ==");
		    throw this.manageException(ex);
        } finally {
        	log.debug("== Termina approvalAllElementsOfAdjustment/AdjustmentBusinessBean con errores. ==");
            if(hayExcepcion){
            	BusinessException bex  = new BusinessException(null, "BL49", "Fallaron algunos elementos al autorizar el ajuste.", null);
				throw this.manageException(bex);
            }
        }
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public AdjustmentElementCollDTO getCheckAdjustmentElementsForAuthorization(AdjustmenElementsRequestDTO request, RequestCollectionInfo requestCollInfo) throws BusinessException {
		
		log.debug("== Inicia getCheckAdjustmentElementsForAuthorization/AdjustmentBusinessBean ==");
		UtilsBusiness.assertNotNull(request.getAdjustmentId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			
        	User user = daoUser.getUserById(request.getUserId());
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
        	
        	Adjustment adjustmentPojo = daoAdjustment.getAdjustmentByID(request.getAdjustmentId());
        	
        	//Check whether the adjustment status is 'AUTH_ING'.
        	if (CodesBusinessEntityEnum.ADJUSTMENT_STATUS_AUTHORIZING.getCodeEntity().equals(adjustmentPojo.getAdjustmentStatus().getCode())){
        		throw new BusinessException(ErrorBusinessMessages.ADJUSTMENT_AUTHORIZING.getCode(),	ErrorBusinessMessages.ADJUSTMENT_AUTHORIZING.getMessage());
        	}
        	
        	return getAdjustmentElementsForAuthorization(request, requestCollInfo);
        	
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAdjustmentElementsForAuthorization/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCheckAdjustmentElementsForAuthorization/AdjustmentBusinessBean ==");
        }
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	//Modificado para Requerimiento: CC057
	public AdjustmentElementCollDTO getAdjustmentElementsForAuthorization(AdjustmenElementsRequestDTO request, RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getAdjustmentElementsForAuthorization/AdjustmentBusinessBean ==");
		UtilsBusiness.assertNotNull(request.getAdjustmentId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {

        	User user = daoUser.getUserById(request.getUserId());
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}

        	AdjustmenElementsResponseDTO adjustmenElementsResponseDTO;
        	List<AdjustmenElementsResponseDTO> adjustmentElementsResponse = new ArrayList<AdjustmenElementsResponseDTO>();        	
			AdjustmentElementCollDTO response = daoAdjustmentElements.getAdjustmentElementsForAuthorization(request, requestCollInfo);
        	
        	Adjustment adjustmentPojo = daoAdjustment.getAdjustmentByID(request.getAdjustmentId());
        	
        	if(adjustmentPojo.getIsSerialized().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity())){
        		response.setSerialized(true);
        	}
        	
        	//Se verifica si el boton de ajustar debe mostrarse como activo para el proceso
			if((user.getRol().getRoleType().getRoleTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum.ROLE_TYPE_BACKOFFICE.getCodeEntity())) 
			    || (user.getRol().getRoleType().getRoleTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_ANALYST.getCodeEntity())) 
			    || (user.getRol().getRoleType().getRoleTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum.ROLE_TYPE_ROOT.getCodeEntity()))){

				response.setAdjust(true);
				Long quantityPending = daoAdjustmentElements.countAdjustmentElementsByStatus(request.getAdjustmentId(), CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PENDING.getCodeEntity());
				if(quantityPending.longValue()==0){
					response.setAdjust(false);
				}
				
			}

			for(AdjustmentElements adjustmentElement : response.getAdjustmentElements()){
				adjustmenElementsResponseDTO = new AdjustmenElementsResponseDTO();
				
				adjustmenElementsResponseDTO.setAdjustmentElementId(adjustmentElement.getId());

				if(response.isAdjust() && adjustmentElement.getAdjustmentElementsStatus().
					getCode().equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PENDING.getCodeEntity())){
						adjustmenElementsResponseDTO.setAdjust(true);
				}
				
				adjustmenElementsResponseDTO.setStatusName(adjustmentElement.getAdjustmentElementsStatus().getName());
				adjustmenElementsResponseDTO.setAuthorizationDate(adjustmentElement.getAuthorizationDate());
				if(adjustmentElement.getAuthorizedUser() != null){
				    adjustmenElementsResponseDTO.setAuthorizedUser(adjustmentElement.getAuthorizedUser().getName());
				}
				if(adjustmentElement.getSerialized() != null){
					
					adjustmenElementsResponseDTO.setSerialized(true);
					adjustmenElementsResponseDTO.setSerialCode(adjustmentElement.getSerialized().getSerialCode());

					if(adjustmentElement.getSerialized().getSerialized() != null){
					    adjustmenElementsResponseDTO.setSerialLinked(adjustmentElement.getSerialized().getSerialized().getSerialCode());
						if(adjustmentElement.getSerialized().getSerialized().getElement() != null && adjustmentElement.getSerialized().getElement().getElementType() != null){
					        adjustmenElementsResponseDTO.setTypeElementLynked(adjustmentElement.getSerialized().getSerialized().getElement().getElementType().getTypeElementCode() + " / " 
					        		+ adjustmentElement.getSerialized().getSerialized().getElement().getElementType().getTypeElementName());
						}
					}
					if(adjustmentElement.getSerialized().getElement() != null && adjustmentElement.getSerialized().getElement().getElementType() != null){
					    adjustmenElementsResponseDTO.setTypeElement(adjustmentElement.getSerialized().getElement().getElementType().getTypeElementCode() + " / " 
					    		+ adjustmentElement.getSerialized().getElement().getElementType().getTypeElementName());
					    adjustmenElementsResponseDTO.setTypeElementCode(adjustmentElement.getSerialized().getElement().getElementType().getTypeElementCode());
					    adjustmenElementsResponseDTO.setTypeElementName(adjustmentElement.getSerialized().getElement().getElementType().getTypeElementName());
						
					    if(adjustmentElement.getSerialized().getElement().getElementType().getElementModel() != null){
						    adjustmenElementsResponseDTO.setElementModel(adjustmentElement.getSerialized().getElement().getElementType().getElementModel().getModelCode() +" / " 
								       + adjustmentElement.getSerialized().getElement().getElementType().getElementModel().getModelName());
						
							if(adjustmentElement.getSerialized().getElement().getElementType().getElementModel().getElementClass() != null){
								adjustmenElementsResponseDTO.setElementClass(adjustmentElement.getSerialized().getElement().getElementType().getElementModel().getElementClass().getElementClassCode() + " / "
									+ adjustmentElement.getSerialized().getElement().getElementType().getElementModel().getElementClass().getElementClassName());
							}
					    }
					}
				}
				if(adjustmentElement.getNotSerialized() != null){
					
					adjustmenElementsResponseDTO.setActualQuantity(adjustmentElement.getInitialQuantity());
					
					if(adjustmentElement.getNotSerialized().getElement() != null && adjustmentElement.getNotSerialized().getElement().getElementType() != null){
					    adjustmenElementsResponseDTO.setTypeElement(adjustmentElement.getNotSerialized().getElement().getElementType().getTypeElementCode() + " / " 
					    		+ adjustmentElement.getNotSerialized().getElement().getElementType().getTypeElementName());
					    adjustmenElementsResponseDTO.setTypeElementCode(adjustmentElement.getNotSerialized().getElement().getElementType().getTypeElementCode());
					    adjustmenElementsResponseDTO.setTypeElementName(adjustmentElement.getNotSerialized().getElement().getElementType().getTypeElementName());
						
					    if(adjustmentElement.getNotSerialized().getElement().getElementType().getElementModel() != null){
						    adjustmenElementsResponseDTO.setElementModel(adjustmentElement.getNotSerialized().getElement().getElementType().getElementModel().getModelCode() + " / "
						    		+ adjustmentElement.getNotSerialized().getElement().getElementType().getElementModel().getModelName());
						
							if(adjustmentElement.getNotSerialized().getElement().getElementType().getElementModel().getElementClass() != null){
								adjustmenElementsResponseDTO.setElementClass(adjustmentElement.getNotSerialized().getElement().getElementType().getElementModel().getElementClass().getElementClassCode() + " / "
										+ adjustmentElement.getNotSerialized().getElement().getElementType().getElementModel().getElementClass().getElementClassName());
							}
					    }
					}					
				}		
				
				if(adjustmentElement.getWarehouseSource()!=null){
					WarehouseVO warehouseSource = UtilsBusiness.copyObject(WarehouseVO.class, adjustmentElement.getWarehouseSource());
					warehouseBusinessBean.genWareHouseName(warehouseSource);
					adjustmenElementsResponseDTO.setNameWarehouseSource(warehouseSource.getWarehouseName());
				}else{
					adjustmenElementsResponseDTO.setNameWarehouseSource(" ");
				}
				if(adjustmentElement.getWarehouseDestination()!=null){
					WarehouseVO warehouseDestination = UtilsBusiness.copyObject(WarehouseVO.class, adjustmentElement.getWarehouseDestination());
					warehouseBusinessBean.genWareHouseName(warehouseDestination);
					adjustmenElementsResponseDTO.setNameWarehouseTarget(warehouseDestination.getWarehouseName());
				}else{
					adjustmenElementsResponseDTO.setNameWarehouseTarget(" ");
				}
				
				adjustmentElementsResponse.add(adjustmenElementsResponseDTO);
			}
			
			response.setAdjustmentElements(null);
			response.setAdjustmentElementsResponse(adjustmentElementsResponse);
			return response;
			
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAdjustmentElementsForAuthorization/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAdjustmentElementsForAuthorization/AdjustmentBusinessBean ==");
        }
		
	}
	
	/**
	 * 
	 * @param listAdjustmentElementsVO
	 * @param adjustmentVO
	 * @param isLoadFile
	 * @param userId
	 * @throws BusinessException
	 */
	private void createAdjustmentInputNotSerializedElement(List<AdjustmentElementDTO> listAdjustmentElementsVO, AdjustmentVO adjustmentVO, boolean isLoadFile, Long userId) throws BusinessException{
		log.debug("== Inicia createAdjustmentInputSerializedElement/AdjustmentBusinessBean ==");
		try {
			
			if(!isLoadFile){
        		asignarTarget(listAdjustmentElementsVO, adjustmentVO);
        	}
			
        	Long idAdjustment = createAdjustment(adjustmentVO, userId);
        	adjustmentVO.setId(idAdjustment);
        	
        	//Consulto el usuario 
        	User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
        	
			MovementElementDTO dtoGenerics=null;
        	if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){
        		dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(adjustmentVO.getTransferReason().getMovTypeIn().getMovTypeCode(),
        																			  null);
        	}else{
        		dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(CodesBusinessEntityEnum.MOVEMENT_TYPE_WAREHOUSE_TYPE_TRANSIT_IN.getCodeEntity(),
        																			  null);
        	}

        	Warehouse warehouse = warehouseBusinessBean.getWareHouseAdjusmentTransit(user.getCountry().getId());
        	//Itero la lista de detalle de los elementos
        	for(AdjustmentElementDTO obj: listAdjustmentElementsVO){
        		//Valida que exista la bodega
        		UtilsBusiness.assertNotNull(obj.getIdWarehouseDestination(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		//Valida que el serial no sea nulo
            	UtilsBusiness.assertNotNull(obj.getQuantity(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		
            	//Valida que venga el tipo de elemento
            	UtilsBusiness.assertNotNull(obj.getElementTypeCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            	
            	Warehouse warehouseDestination = daoWarehouse.getWarehouseByID(obj.getIdWarehouseDestination());
            	if(warehouseDestination==null){
            		throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(),ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
            	}
            	
            	//Valida el tipo de elemento
            	ElementType elementType = daoElementType.getElementTypeByCode(obj.getElementTypeCode());
            	if(elementType==null){
            		throw new BusinessException(ErrorBusinessMessages.STOCK_IN370.getCode(), ErrorBusinessMessages.STOCK_IN370.getMessage());
            	}
            	
            	//Valido que el tipo de elemento sea no serializado
            	if(elementType.getIsSerialized().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity())){
            		throw new BusinessException(ErrorBusinessMessages.STOCK_IN462.getCode(), ErrorBusinessMessages.STOCK_IN462.getMessage());
            	}
            	
            	
            	//Crea el detalle del elemento
            	AdjustmentElementsVO detailRegister = new AdjustmentElementsVO();
            	detailRegister.setAdjustment(adjustmentVO);
            	detailRegister.setWarehouseDestination(warehouseDestination);
            	detailRegister.setSerialized(null);
            	detailRegister.setInitialQuantity(obj.getQuantity());
            	this.adjustmentElementsBusinessBean.executeAdjustmentInputNotSerialized(detailRegister, adjustmentVO, elementType, user,warehouse,dtoGenerics);
        	}
        	
        	adjustmentVO = getAdjustmentByID(idAdjustment);
        	if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equalsIgnoreCase(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){        		
        		AdjustmentStatus adjustmentStatusProcessCompleted = daoAdjustmentStatus.getAdjustmentStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PROCESS.getCodeEntity());
        		adjustmentVO.setAdjustmentStatus(adjustmentStatusProcessCompleted);
        		updateAdjustment(adjustmentVO,userId);
        	}else{
        		AdjustmentStatus adjustmentStatusProcessPending = daoAdjustmentStatus.getAdjustmentStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PENDING.getCodeEntity());
        		adjustmentVO.setAdjustmentStatus(adjustmentStatusProcessPending);
        		updateAdjustment(adjustmentVO,userId);
        	}
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createAdjustmentInputSerializedElement/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentInputSerializedElement/AdjustmentBusinessBean ==");
        }
		
	}
	
	/**
	 * Crea un ajuste de salida para elementos no serializados
	 * @param listAdjustmentElementsVO ajustes de elementos para crear
	 * @param adjustmentVO
	 * @param isLoadFile
	 * @param userId
	 * @throws BusinessException
	 */
	private void createAdjustmentOutputNotSerializedElement(List<AdjustmentElementDTO> listAdjustmentElementsVO, AdjustmentVO adjustmentVO, boolean isLoadFile, Long userId) throws BusinessException{
		log.debug("== Inicia createAdjustmentOutputSerializedElement/AdjustmentBusinessBean ==");
		try {
			
        	Long idAdjustment = createAdjustment(adjustmentVO, userId);
        	adjustmentVO.setId(idAdjustment);
        	
        	
        	//Consulto el usuario 
        	User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
        	
			MovementElementDTO dtoGenerics=null;
        	if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){
        		dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(null,adjustmentVO.getTransferReason().getMovTypeOut().getMovTypeCode());
        	}else{
        		dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(CodesBusinessEntityEnum.MOVEMENT_TYPE_WAREHOUSE_TYPE_TRANSIT_IN.getCodeEntity(),
        				                                                              CodesBusinessEntityEnum.MOVEMENT_TYPE_WAREHOUSE_TYPE_TRANSIT_OUT.getCodeEntity());
        	}
        	Warehouse warehouse = warehouseBusinessBean.getWareHouseAdjusmentTransit(user.getCountry().getId());
        	for(AdjustmentElementDTO obj: listAdjustmentElementsVO){
        		
        		UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		UtilsBusiness.assertNotNull(obj.getIdWarehouse(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		UtilsBusiness.assertNotNull(obj.getElementTypeCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		
        		//Consulto la ubicación origen
        		Warehouse warehouseSource = daoWarehouse.getWarehouseByID(obj.getIdWarehouse());
            	if(warehouseSource==null){
            		throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
            	}
        		
            	//Validar que la cantidad en la bodega origen
        		Double whQuantityElement = this.daoWarehouseElement.countWHElementByActualElementNotSerialized(obj.getIdWarehouse(), warehouseSource.getCountry().getId(), null, null, obj.getElementTypeCode(), CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity(), null, null);
				if(obj.getQuantity().doubleValue()> whQuantityElement.doubleValue()){
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN464.getCode(), ErrorBusinessMessages.STOCK_IN464.getMessage());
				}
            	
            	//Consulto el element Type
            	ElementType elementType = daoElementType.getElementTypeByCode(obj.getElementTypeCode());
            	if(elementType==null){
            		throw new BusinessException(ErrorBusinessMessages.ELEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.ELEMENT_TYPE_NOT_EXIST.getMessage());
            	}
            	
            	//Valido que el tipo de elemento sea no serializado
            	if(elementType.getIsSerialized().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity())){
            		throw new BusinessException(ErrorBusinessMessages.STOCK_IN462.getCode(), ErrorBusinessMessages.STOCK_IN462.getMessage());
            	}
            	
            	//Crea el detalle del elemento
            	AdjustmentElementsVO detailRegister = new AdjustmentElementsVO();
            	detailRegister.setAdjustment(adjustmentVO);
            	
            	NotSerialized notSerialized = daoNotSerialized.getNotSerializedbyElementTypeID(elementType.getId(), adjustmentVO.getCountry().getId());
            	
            	detailRegister.setNotSerialized(notSerialized);
            	detailRegister.setWarehouseDestination(null);
            	detailRegister.setWarehouseSource(warehouseSource);
            	detailRegister.setInitialQuantity(obj.getQuantity());
            	this.adjustmentElementsBusinessBean.executeAdjustmentOutputNotSerialized(detailRegister, adjustmentVO, user,warehouse,dtoGenerics);
        	}
        	
        	//Consulta del ajuste creado para llenar todos los datos
        	adjustmentVO = getAdjustmentByID(idAdjustment);
        	
        	if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equalsIgnoreCase(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){
        		AdjustmentStatus adjustmentStatusProcessCompleted = daoAdjustmentStatus.getAdjustmentStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PROCESS.getCodeEntity());
        		adjustmentVO.setAdjustmentStatus(adjustmentStatusProcessCompleted);
        		updateAdjustment(adjustmentVO,userId);
        	}else{
        		AdjustmentStatus adjustmentStatusProcessPending = daoAdjustmentStatus.getAdjustmentStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PENDING.getCodeEntity());
        		adjustmentVO.setAdjustmentStatus(adjustmentStatusProcessPending);
        		updateAdjustment(adjustmentVO,userId);
        	}
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createAdjustmentOutputSerializedElement/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentOutputSerializedElement/AdjustmentBusinessBean ==");
        }
		
	}
	
	/**
	 * Metodo: Método utilitario encargado de crear ajustes de translado de elementos serializados
	 * @param listAdjustmentElementsVO
	 * @param adjustmentVO
	 * @param isFileLoad
	 * @param userId
	 * @throws BusinessException
	 * @author waguilera
	 */
	private void createAdjustmentTransferNotSerializedElement(List<AdjustmentElementDTO> listAdjustmentElementsVO, AdjustmentVO adjustmentVO, boolean isFileLoad, Long userId) throws BusinessException{
		log.debug("== Inicia createAdjustmentTransferSerializedElement/AdjustmentBusinessBean ==");
		try {
			
			//Valido si viene IBS
			if(adjustmentVO.getIbsCodeCustomer()!=null&&adjustmentVO.getIbsCodeCustomer().length()>0){
				adjustmentVO.setWarehouseTargetID(getWarehouseIDFromCustomer(adjustmentVO.getIbsCodeCustomer(), adjustmentVO.getCountry().getId()));
			}
			
			if(!isFileLoad){
				asignarTarget(listAdjustmentElementsVO, adjustmentVO);
			}
			
			//Se consulta el usuario
			User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
			
			//Crea el ajuste
			Long idAdjustment = createAdjustment(adjustmentVO, userId);
        	adjustmentVO.setId(idAdjustment);  
        	
			MovementElementDTO dtoGenerics=null;
        	if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){
        		dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(adjustmentVO.getTransferReason().getMovTypeIn().getMovTypeCode(),
        																			  adjustmentVO.getTransferReason().getMovTypeOut().getMovTypeCode());
        	}else{
        		dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(CodesBusinessEntityEnum.MOVEMENT_TYPE_WAREHOUSE_TYPE_TRANSIT_IN.getCodeEntity(),
        				                                                              CodesBusinessEntityEnum.MOVEMENT_TYPE_WAREHOUSE_TYPE_TRANSIT_OUT.getCodeEntity());
        	}

        	Warehouse warehouse = warehouseBusinessBean.getWareHouseAdjusmentTransit(user.getCountry().getId());
        	for(AdjustmentElementDTO obj: listAdjustmentElementsVO){
        		
        		UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		UtilsBusiness.assertNotNull(obj.getIdWarehouseDestination(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		UtilsBusiness.assertNotNull(obj.getElementTypeCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		UtilsBusiness.assertNotNull(obj.getIdWarehouse(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		
            	
        		//Consulto la ubicación origen
        		Warehouse warehouseSource = daoWarehouse.getWarehouseByID(obj.getIdWarehouse());
            	if(warehouseSource==null){
            		throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
            	}
        		
            	//Validar que la cantidad en la bodega origen
        		Double whQuantityElement = this.daoWarehouseElement.countWHElementByActualElementNotSerialized(obj.getIdWarehouse(), warehouseSource.getCountry().getId(), null, null, obj.getElementTypeCode(), CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity(), null, null);
				if(obj.getQuantity().doubleValue()> whQuantityElement.doubleValue()){
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN464.getCode(), ErrorBusinessMessages.STOCK_IN464.getMessage());
				}
            	
            	//Consulto la bodega destino
            	Warehouse warehouseDestination = daoWarehouse.getWarehouseByID(obj.getIdWarehouseDestination());
            	if(warehouseDestination==null){
            		throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(),ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
            	}
            	
            	//Valido que no sea la misma bodega
            	if(warehouseDestination.getId().longValue()==warehouseSource.getId().longValue()){
            		throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_ELEMENT_TARGET_ARE_REPEATED.getCode(),ErrorBusinessMessages.WAREHOUSE_ELEMENT_TARGET_ARE_REPEATED.getMessage());
            	}
            	
            	//Consulto el element Type
            	ElementType elementType = daoElementType.getElementTypeByCode(obj.getElementTypeCode());
            	if(elementType==null){
            		throw new BusinessException(ErrorBusinessMessages.ELEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.ELEMENT_TYPE_NOT_EXIST.getMessage());
            	}
            	
            	//Valido que el tipo de elemento sea no serializado
            	if(elementType.getIsSerialized().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity())){
            		throw new BusinessException(ErrorBusinessMessages.STOCK_IN462.getCode(), ErrorBusinessMessages.STOCK_IN462.getMessage());
            	}
            	
            	//Crea el detalle del elemento
            	AdjustmentElementsVO detailRegister = new AdjustmentElementsVO();
            	detailRegister.setAdjustment(adjustmentVO);
            	
            	NotSerialized notSerialized = daoNotSerialized.getNotSerializedbyElementTypeID(elementType.getId(), adjustmentVO.getCountry().getId());
            	detailRegister.setNotSerialized(notSerialized);
            	detailRegister.setWarehouseDestination(warehouseDestination);
            	detailRegister.setWarehouseSource(warehouseSource);
            	detailRegister.setInitialQuantity(obj.getQuantity());
            	this.adjustmentElementsBusinessBean.executeAdjustmentTransferNotSerialized(detailRegister, adjustmentVO, user,warehouse,dtoGenerics);
        	}
        	
        	
        	//Consulta del ajuste creado para llenar todos los datos
        	adjustmentVO = getAdjustmentByID(idAdjustment);
        	if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equalsIgnoreCase(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){
        		AdjustmentStatus adjustmentStatusProcessCompleted = daoAdjustmentStatus.getAdjustmentStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PROCESS.getCodeEntity());
        		adjustmentVO.setAdjustmentStatus(adjustmentStatusProcessCompleted);
        		updateAdjustment(adjustmentVO,userId);
        	}else{
        		AdjustmentStatus adjustmentStatusProcessPending = daoAdjustmentStatus.getAdjustmentStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PENDING.getCodeEntity());
        		adjustmentVO.setAdjustmentStatus(adjustmentStatusProcessPending);
        		updateAdjustment(adjustmentVO,userId);
        	}
        	
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createAdjustmentTransferNotSerializedElement/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentTransferSerializedElement/AdjustmentBusinessBean ==");
        }
		
	}
	
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createAdjustmentForEntry(MovementElementDTO dto) throws BusinessException{
		log.debug("== Inicia createAdjustmentForEntry/WarehouseElementBusinessBean ==");
		UtilsBusiness.assertNotNull(dto.getUser(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getElementTypeId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<AdjustmentElementDTO> listAdjustmentElementsVO = new ArrayList<AdjustmentElementDTO>();
		AdjustmentVO adjustmentVO = new AdjustmentVO();
		try{
			//Se consulta que en la bodega del cliente no haya ninguna unidad del elemento 
			Double totalQ = daoWarehouseElement.getCurrentQuantityInWarehouseByElementType(dto.getSourceWh().getId(), 
					                                                                       dto.getElementTypeId(), 
					                                                                       null);
			//En el caso que encuentre por lo menos un elemento no se crea el ajuste sino que se lanza excepcion
			if( totalQ != null && totalQ.doubleValue() > 0 ){
				throw new BusinessException(ErrorBusinessMessages.ACTUAL_QUANTITY_LOWER_THAN_REQUIRED_MOVEMENT.getCode(),ErrorBusinessMessages.ACTUAL_QUANTITY_LOWER_THAN_REQUIRED_MOVEMENT.getMessage());
			}
			//consulta la causal de entrada con la que se va a generar el ajuste
			List<TransferReason> transferReasons = transferReasonDAO.getTransferReasonByMovementTypeInCode( CodesBusinessEntityEnum.MOVEMENT_TYPE_TYPE_DEVOLUTION_FROM_CUST.getCodeEntity() , CodesBusinessEntityEnum.TRANSFER_REASON_ACTIVE.getCodeEntity());
			if( transferReasons == null || transferReasons.isEmpty() ){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN473.getCode(),ErrorBusinessMessages.STOCK_IN473.getMessage());
			}
			adjustmentVO.setTransferReason(transferReasons.get(0));
			adjustmentVO.setCountry(dto.getUser().getCountry());
			adjustmentVO.setComent(transferReasons.get(0).getTransferReasonName());
			adjustmentVO.setCreationUser( new User( dto.getUser().getId() ) );
			adjustmentVO.setWarehouseTargetID( dto.getTargetWs().getId() );
			
			//Se le da valor a los campos requeridos del objeto AdjustmentElementDTO para crear el ajuste
			AdjustmentElementDTO adjustmentElementDTO = new AdjustmentElementDTO();
			adjustmentElementDTO.setIdWarehouseDestination( dto.getTargetWs().getId() );
			adjustmentElementDTO.setQuantity( dto.getQuantity() );
			adjustmentElementDTO.setElementTypeCode( dto.getElementTypeCode() );
			listAdjustmentElementsVO.add(adjustmentElementDTO);
			
			//Se crea el ajuste
			this.createAdjustmentNotSerializedElement(listAdjustmentElementsVO,adjustmentVO,dto.getUser().getId());
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operación createAdjustmentForEntry/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina createAdjustmentForEntry/WarehouseElementBusinessBean ==");
		}
	}
	
	/**
	 * Metodo encargado de consultar los estados para un ajuste
	 * @throws BusinessException
	 * @return posibles estados de ajustes de inventario
	 */	
	public List<AdjustmentStatusVO> getAllAdjustmentStatus() throws BusinessException {
		log.debug("== Inicia getAllAdjustmentStatus/AdjustmentBusinessBean ==");
		try{
			List<AdjustmentStatusVO> listVO = UtilsBusiness.convertList(daoAdjustmentStatus.getAllAdjustmentStatuss(), AdjustmentStatusVO.class);
			return listVO;
		}catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllAdjustmentStatus/AdjustmentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllAdjustmentStatus/AdjustmentBusinessBean ==");
        }		
	}
	
}
