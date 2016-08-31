package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
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
import co.com.directv.sdii.ejb.business.stock.ItemStatusBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.NotSerializedBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.RefQuantityControlItemBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceModificationBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceStatusBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.MassiveMovementBetweenWareHouseDTO;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.dto.collection.ReferenceElementItemsDTO;
import co.com.directv.sdii.model.pojo.Element;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.RefConfirmation;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.ReferenceElementItem;
import co.com.directv.sdii.model.pojo.ReferenceStatus;
import co.com.directv.sdii.model.pojo.ReportedElement;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.collection.ElementMixedResponse;
import co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.RefConfirmationVO;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ItemStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.NotSerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.RefConfirmationDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReportedElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD ReferenceElementItem
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal
 */
@Stateless(name="ReferenceElementItemBusinessBeanLocal",mappedName="ejb/ReferenceElementItemBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReferenceElementItemBusinessBean extends BusinessBase implements ReferenceElementItemBusinessBeanLocal {

    @EJB(name="ReferenceElementItemDAOLocal", beanInterface=ReferenceElementItemDAOLocal.class)
    private ReferenceElementItemDAOLocal daoReferenceElementItem;
    
    @EJB(name="ItemStatusDAOLocal", beanInterface=ItemStatusDAOLocal.class)
    private ItemStatusDAOLocal daoItemStatus;
    
    @EJB(name="ItemStatusBusinessBeanLocal", beanInterface=ItemStatusBusinessBeanLocal.class)
    private ItemStatusBusinessBeanLocal businessItemStatus;
    
    @EJB(name="ReferenceStatusBusinessBeanLocal", beanInterface=ReferenceStatusBusinessBeanLocal.class)
    private ReferenceStatusBusinessBeanLocal businessRefStatus;
    
    @EJB(name="RefConfirmationDAOLocal", beanInterface=RefConfirmationDAOLocal.class)
    private RefConfirmationDAOLocal daoRefConfirmation;
    
    @EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
    private SerializedDAOLocal serializedDAO;
    
    @EJB(name="SerializedBusinessBeanLocal", beanInterface=SerializedBusinessBeanLocal.class)
    private SerializedBusinessBeanLocal serializedBusiness;
    
    @EJB(name="NotSerializedDAOLocal", beanInterface=NotSerializedDAOLocal.class)
    private NotSerializedDAOLocal notSerializedDAO;
    
    @EJB(name="NotSerializedBusinessBeanLocal", beanInterface=NotSerializedBusinessBeanLocal.class)
    private NotSerializedBusinessBeanLocal notSerializedBusiness;
    
    @EJB(name="ReferenceDAOLocal", beanInterface=ReferenceDAOLocal.class)
    private ReferenceDAOLocal daoRef;
    
    @EJB(name="ReferenceBusinessBeanLocal", beanInterface=ReferenceBusinessBeanLocal.class)
    private ReferenceBusinessBeanLocal businessRef;
    
    @EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
    private WarehouseElementDAOLocal daoWarehouseElement;
    
    @EJB(name="ElementTypeDAOLocal", beanInterface=ElementTypeDAOLocal.class)
    private ElementTypeDAOLocal daoElementType;
    
    @EJB(name="ElementDAOLocal", beanInterface=ElementDAOLocal.class)
    private ElementDAOLocal daoElement;
    
    @EJB (name="RefQuantityControlItemBusinessBeanLocal", beanInterface=RefQuantityControlItemBusinessBeanLocal.class)
    private RefQuantityControlItemBusinessBeanLocal businessRefQuantityControlItem;
    
    @EJB(name="SerializedDAOLocal",beanInterface=SerializedDAOLocal.class)
	private SerializedDAOLocal daoSerialized;
    
    @EJB(name="NotSerializedDAOLocal",beanInterface=NotSerializedDAOLocal.class)
	private NotSerializedDAOLocal daoNotSerialized;
    
    @EJB(name="ReferenceModificationBusinessBeanLocal", beanInterface=ReferenceModificationBusinessBeanLocal.class)
    private ReferenceModificationBusinessBeanLocal businessReferenceModification;
    
    @EJB(name="ReferenceStatusDAOLocal", beanInterface=ReferenceStatusDAOLocal.class)
    private ReferenceStatusDAOLocal referenceStatusDAO;
    
    @EJB(name="ReportedElementDAOLocal", beanInterface=ReportedElementDAOLocal.class)
    private ReportedElementDAOLocal daoReportedElement;
    
    @EJB(name="MovementElementBusinessBeanLocal", beanInterface=MovementElementBusinessBeanLocal.class)
    private MovementElementBusinessBeanLocal businessMovementElement;
    
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
    
    @EJB(name="WarehouseDAOLocal", beanInterface=WarehouseDAOLocal.class)
    private WarehouseDAOLocal daoWarehouse;
    
    private final static Logger log = UtilsBusiness.getLog4J(ReferenceElementItemBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#getAllReferenceElementItems()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ReferenceElementItemVO> getAllReferenceElementItems() throws BusinessException {
        log.debug("== Inicia getAllReferenceElementItems/ReferenceElementItemBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoReferenceElementItem.getAllReferenceElementItems(), ReferenceElementItemVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllReferenceElementItems/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllReferenceElementItems/ReferenceElementItemBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#getReferenceElementItemsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ReferenceElementItemVO getReferenceElementItemByID(Long id) throws BusinessException {
        log.debug("== Inicia getReferenceElementItemByID/ReferenceElementItemBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReferenceElementItem objPojo = daoReferenceElementItem.getReferenceElementItemByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ReferenceElementItemVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceElementItemByID/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceElementItemByID/ReferenceElementItemBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#createReferenceElementItem(co.com.directv.sdii.model.vo.ReferenceElementItemVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createReferenceElementItem(ReferenceElementItemVO obj) throws BusinessException {
        log.debug("== Inicia createReferenceElementItem/ReferenceElementItemBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReferenceElementItem objPojo = UtilsBusiness.copyObject(ReferenceElementItem.class, obj);
            daoReferenceElementItem.createReferenceElementItem(objPojo);
            obj.setId(objPojo.getId());
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createReferenceElementItem/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createReferenceElementItem/ReferenceElementItemBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#updateReferenceElementItem(co.com.directv.sdii.model.vo.ReferenceElementItemVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateReferenceElementItem(ReferenceElementItemVO obj) throws BusinessException {
        log.debug("== Inicia updateReferenceElementItem/ReferenceElementItemBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReferenceElementItem objPojo =  UtilsBusiness.copyObject(ReferenceElementItem.class, obj);
            daoReferenceElementItem.updateReferenceElementItem(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateReferenceElementItem/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateReferenceElementItem/ReferenceElementItemBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#deleteReferenceElementItem(co.com.directv.sdii.model.vo.ReferenceElementItemVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteReferenceElementItem(ReferenceElementItemVO obj) throws BusinessException {
        log.debug("== Inicia deleteReferenceElementItem/ReferenceElementItemBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReferenceElementItem objPojo =  UtilsBusiness.copyObject(ReferenceElementItem.class, obj);
            daoReferenceElementItem.deleteReferenceElementItem(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteReferenceElementItem/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteReferenceElementItem/ReferenceElementItemBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#getReferenceElementItemsByReferenceID(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceID(Long refID, RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getReferenceElementItemsByReferenceID/ReferenceElementItemBusinessBean ==");
        UtilsBusiness.assertNotNull(refID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	ReferenceElementItemsResponse response;
        	response =  daoReferenceElementItem.getReferenceElementItemsByReferenceID(refID, requestCollInfo);
        	List<ReferenceElementItem> objPojo = response.getReferenceElementItems();
        	List<ReferenceElementItemVO> result = UtilsBusiness.convertList(objPojo,ReferenceElementItemVO.class);
        	populateSerializedAndNotSerialized(result);    
        	//Paginacion
        	if( requestCollInfo != null){
        		List<ReferenceElementItemVO> pagerList = this.getPaginationList(result, requestCollInfo);
        		response.setReferenceElementItemsVO( pagerList );
        		this.populatePaginationInfo(response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), pagerList.size(), result.size());
        	}else{
        		response.setReferenceElementItemsVO(result);        		
        	}      
        	response.setReferenceElementItems(null);
            return response;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceElementItemsByReferenceID/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceElementItemsByReferenceID/ReferenceElementItemBusinessBean ==");
        }
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ReferenceElementItemsDTO getReferenceElementSerializedNotSerialized(Long refID, RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getReferenceElementSerializedNotSerialized/ReferenceElementItemBusinessBean ==");
        UtilsBusiness.assertNotNull(refID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	ReferenceElementItemsResponse response;
        	//Primer metodo sin paginacion, ya que se deben paginar solo de forma independiente los elementos serializados y no serializados.
        	RequestCollectionInfo collInfo = null;
        	response =  daoReferenceElementItem.getReferenceElementItemsByReferenceID(refID, collInfo);
        	List<ReferenceElementItem> objPojo = response.getReferenceElementItems();
        	List<ReferenceElementItemVO> result = UtilsBusiness.convertList(objPojo,ReferenceElementItemVO.class);
        	ReferenceElementItemsDTO responseDTO = populateSerializedAndNotSerialized(result,requestCollInfo);            	
            return responseDTO;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceElementSerializedNotSerialized/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceElementSerializedNotSerialized/ReferenceElementItemBusinessBean ==");
        }
	}

	private void populateSerializedAndNotSerialized(List<ReferenceElementItemVO> result) throws PropertiesException, DAOServiceException, DAOSQLException, BusinessException {
		Serialized serializedPojo;
		SerializedVO serialized;
		NotSerialized notSerPojo;
		NotSerializedVO notSerialized;
		Long souceWhId = null;
		WarehouseElement whElement;
		
		for (ReferenceElementItemVO referenceElementItemVO : result) {
			souceWhId = referenceElementItemVO.getReference().getWarehouseBySourceWh().getId();
			if(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity().equalsIgnoreCase(referenceElementItemVO.getElement().getIsSerialized())){
				List<Serialized> listSer = serializedDAO.getSerializedByElementId(referenceElementItemVO.getElement().getId());
				if(! listSer.isEmpty()){
					serializedPojo = listSer.get(0);
					serialized = UtilsBusiness.copyObject(SerializedVO.class, serializedPojo);
					referenceElementItemVO.setSerializeElement(serialized);
				}
			}else{
				notSerPojo = notSerializedDAO.getNotSerializedByID(referenceElementItemVO.getElement().getId());
				if(notSerPojo != null){
					notSerialized = UtilsBusiness.copyObject(NotSerializedVO.class, notSerPojo);
					whElement = daoWarehouseElement.getWhElementInWHByWarehouseIdAndElementID(souceWhId, CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity(), notSerialized.getElementId());
					if(whElement != null){
						notSerialized.setActualQuantity(whElement.getActualQuantity());
					}
					referenceElementItemVO.setNotSerializedElement(notSerialized);
				}
			}
			
			List<RefConfirmationVO> confirmations = UtilsBusiness.convertList(daoRefConfirmation.getConfirmationsByElementId(referenceElementItemVO.getId()), RefConfirmationVO.class);
			referenceElementItemVO.setRefConfirmations(confirmations);
			referenceElementItemVO.setRefConfirmationSum(getRefConfirmationsSum(confirmations));			
		}		
	}

	/**
	 * 
	 * Metodo: 
	 * @param result
	 * @param requestCollInfo
	 * @return ReferenceElementItemsDTO
	 * @throws PropertiesException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @author
	 */
	private ReferenceElementItemsDTO populateSerializedAndNotSerialized(List<ReferenceElementItemVO> result, RequestCollectionInfo requestCollInfo) throws PropertiesException, DAOServiceException, DAOSQLException, BusinessException {
		Serialized serializedPojo;
		SerializedVO serialized;
		NotSerialized notSerPojo;
		NotSerializedVO notSerialized;
		Long souceWhId = null;
		WarehouseElement whElement;
		
		ReferenceElementItemsDTO dtoResponse = new ReferenceElementItemsDTO();	
		List<ReferenceElementItemVO> listRefSer = new LinkedList<ReferenceElementItemVO>();
		List<ReferenceElementItemVO> listRefNotSer = new LinkedList<ReferenceElementItemVO>();
		
		Boolean isSerialized = null;
		
		for (ReferenceElementItemVO referenceElementItemVO : result) {
			souceWhId = referenceElementItemVO.getReference().getWarehouseBySourceWh().getId();
			if(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity().equalsIgnoreCase(referenceElementItemVO.getElement().getIsSerialized())){
				List<Serialized> listSer = serializedDAO.getSerializedByElementId(referenceElementItemVO.getElement().getId());
				if(! listSer.isEmpty()){
					serializedPojo = listSer.get(0);
					serialized = UtilsBusiness.copyObject(SerializedVO.class, serializedPojo);
					referenceElementItemVO.setSerializeElement(serialized);
				}
				isSerialized = true;
			}else{
				notSerPojo = notSerializedDAO.getNotSerializedByID(referenceElementItemVO.getElement().getId());
				if(notSerPojo != null){
					notSerialized = UtilsBusiness.copyObject(NotSerializedVO.class, notSerPojo);
					whElement = daoWarehouseElement.getWhElementInWHByWarehouseIdAndElementID(souceWhId, CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity(), notSerialized.getElementId());
					if(whElement != null){
						notSerialized.setActualQuantity(whElement.getActualQuantity());
					}
					referenceElementItemVO.setNotSerializedElement(notSerialized);
				}
				isSerialized = false;
			}			
			List<RefConfirmationVO> confirmations = UtilsBusiness.convertList(daoRefConfirmation.getConfirmationsByElementId(referenceElementItemVO.getId()), RefConfirmationVO.class);
			referenceElementItemVO.setRefConfirmations(confirmations);
			referenceElementItemVO.setRefConfirmationSum(getRefConfirmationsSum(confirmations));	
			
			//Separacion para paginacion individual de los elementos
			if(isSerialized != null)				
				if ( isSerialized.booleanValue() ){
					listRefSer.add(referenceElementItemVO);					
				}else{
					listRefNotSer.add(referenceElementItemVO);					
				}
		}
		
		//Paginacion
		if( requestCollInfo != null){
			ReferenceElementItemsResponse responsePag = new ReferenceElementItemsResponse();
			//Paginacion elementos Serializados
			if( !listRefSer.isEmpty() ){				
	    		List<ReferenceElementItemVO> pagerListSer = this.getPaginationList(listRefSer, requestCollInfo);
	    		responsePag.setReferenceElementItemsVO(pagerListSer);
	    		dtoResponse.setReferenceResponseSerializesVO(responsePag);
	    		this.populatePaginationInfo(responsePag, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), pagerListSer.size(), listRefSer.size());
			}
    		//Paginacion elementos No Serializados
			if(!listRefNotSer.isEmpty()){
	    		responsePag = new ReferenceElementItemsResponse();
	    		List<ReferenceElementItemVO> pagerListNotSer = this.getPaginationList(listRefNotSer, requestCollInfo);
	    		responsePag.setReferenceElementItemsVO(pagerListNotSer);
	    		dtoResponse.setReferenceResponseNotSerializesVO(responsePag);
	    		this.populatePaginationInfo(responsePag, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), pagerListNotSer.size(), listRefNotSer.size());
			}
    	}else{
    		ReferenceElementItemsResponse responseNPag = new ReferenceElementItemsResponse();
    		responseNPag.setReferenceElementItemsVO(listRefSer);
    		dtoResponse.setReferenceResponseSerializesVO(responseNPag);
    		
    		responseNPag = new ReferenceElementItemsResponse();
    		responseNPag.setReferenceElementItemsVO(listRefNotSer);
    		dtoResponse.setReferenceResponseNotSerializesVO(responseNPag);
    	}
		
		return dtoResponse;		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#changeStatusOfReferenceElements(java.lang.Long, java.lang.Long)
	 */
	public void changeStatusOfReferenceElements(Long referenceId, Long newStatusId) throws BusinessException {
		log.debug("== Inicia changeStatusOfReferenceElements/ReferenceElementItemBusinessBean ==");
        UtilsBusiness.assertNotNull(referenceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(newStatusId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	//operacion paginada, en este punto no es necesario paginar por lo tanto se envia null
    		RequestCollectionInfo requestCollInfo = null;
    		ReferenceElementItemsResponse response = daoReferenceElementItem.getReferenceElementItemsByReferenceID(referenceId, requestCollInfo);
        	List<ReferenceElementItem> objPojo = response.getReferenceElementItems();
            ItemStatus newStatus = daoItemStatus.getItemStatusByID(newStatusId);
        	
        	for (ReferenceElementItem referenceElementItem : objPojo) {
        		referenceElementItem.setItemStatus(newStatus);
        		daoReferenceElementItem.updateReferenceElementItem(referenceElementItem);
			}
        	
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación changeStatusOfReferenceElements/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina changeStatusOfReferenceElements/ReferenceElementItemBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#getNotSerializedReferenceElementItemByReferenceIdForDetails(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ReferenceElementItemVO> getNotSerializedReferenceElementItemByReferenceIdForDetails(Long referenceId) throws BusinessException{
		log.debug("== Inicia getNotSerializedReferenceElementItemByReferenceIdForDetails/ReferenceElementItemBusinessBean ==");
        try {
        	List<ReferenceElementItemVO> items = UtilsBusiness.convertList(daoReferenceElementItem.getNotSerializedReferenceElementItemByReferenceId(referenceId), ReferenceElementItemVO.class);
        	//Se valida que no este vacia la coleccion de items
        	if(items != null && !items.isEmpty()){
        		//Se asocia a cada item las confirmaciones
            	for(ReferenceElementItemVO vo : items){
            		List<RefConfirmationVO> confirmations = UtilsBusiness.convertList(daoRefConfirmation.getConfirmationsByElementId(vo.getId()), RefConfirmationVO.class);
            		vo.setRefConfirmations(confirmations);
            		vo.setRefConfirmationSum(getRefConfirmationsSum(confirmations));
            	}
        	}        	
            return items;

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getNotSerializedReferenceElementItemByReferenceIdForDetails/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getNotSerializedReferenceElementItemByReferenceIdForDetails/ReferenceElementItemBusinessBean ==");
        }
	}
	
	public Double getRefConfirmationsSum(List<RefConfirmationVO> confirmations) {
		Double sum = 0D;
		if(confirmations != null ){
			for(RefConfirmationVO vo:confirmations){
				sum += vo.getConfirmedQuantity();
			}
		}
		return sum;
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#getSerializedReferenceElementItemByReferenceIdForDetails(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ReferenceElementItemVO> getSerializedReferenceElementItemByReferenceIdForDetails(Long referenceId) throws BusinessException{
		log.debug("== Inicia getSerializedReferenceElementItemByReferenceIdForDetails/ReferenceElementItemBusinessBean ==");
        try {
        	List<ReferenceElementItemVO> items = UtilsBusiness.convertList(daoReferenceElementItem.getSerializedReferenceElementItemByReferenceId(referenceId), ReferenceElementItemVO.class);
        	if(items != null && items.size() > 0){
        		for(ReferenceElementItemVO vo : items){
        			List<RefConfirmationVO> confirmations = UtilsBusiness.convertList(daoRefConfirmation.getConfirmationsByElementId(vo.getId()), RefConfirmationVO.class);
            		vo.setRefConfirmations(confirmations);
            		vo.setRefConfirmationSum(getRefConfirmationsSum(confirmations));
            		List<Serialized> serList = this.serializedDAO.getSerializedByElementId( vo.getElement().getId() ) ;
            		Serialized ser = null;
            		if( serList != null && !serList.isEmpty() ){
            			ser = serList.get(0);
            			Element elementAux = new Element();
            			elementAux.setId(ser.getElementId());
            		}
            			
            		vo.setSerializeElement( UtilsBusiness.copyObject(SerializedVO.class, ser ) );
        		}
        	}
        	return items;

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getSerializedReferenceElementItemByReferenceIdForDetails/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSerializedReferenceElementItemByReferenceIdForDetails/ReferenceElementItemBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#updateElementItemsByReferenceId(java.lang.Long,java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateElementItemsByReferenceId(Long referenceId , Long newStatus) throws BusinessException{
		log.debug("== Inicia updateElementItemsByReferenceId/ReferenceElementItemBusinessBean ==");
        try {
        	//operacion paginada, en este punto no es necesario paginar por lo tanto se envia null
    		RequestCollectionInfo requestCollInfo = null;
    		ReferenceElementItemsResponse response = daoReferenceElementItem.getReferenceElementItemsByReferenceID(referenceId, requestCollInfo);
        	List<ReferenceElementItem> elementsList = response.getReferenceElementItems();
        	if(elementsList != null ){
        		for(ReferenceElementItem element : elementsList){
        			ItemStatus newItemStatus = new ItemStatus();
        			newItemStatus.setId(newStatus);
        			element.setItemStatus(newItemStatus);
        			daoReferenceElementItem.updateReferenceElementItem(element);
        		}
        	}
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateElementItemsByReferenceId/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateElementItemsByReferenceId/ReferenceElementItemBusinessBean ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#updateSerializeElementItemsByReferenceId(java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateSerializeElementItemsByReferenceId(Long referenceId , Long newStatus) throws BusinessException{
		log.debug("== Inicia updateSerializeElementItemsByReferenceId/ReferenceElementItemBusinessBean ==");
        try {
        	List<ReferenceElementItem> elementsList = daoReferenceElementItem.getSerializedReferenceElementItemByReferenceId(referenceId);
        	if(elementsList != null ){
        		for(ReferenceElementItem element : elementsList){
        			ItemStatus newItemStatus = new ItemStatus();
        			newItemStatus.setId(newStatus);
        			element.setItemStatus(newItemStatus);
        			daoReferenceElementItem.updateReferenceElementItem(element);
        		}
        	}
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateSerializeElementItemsByReferenceId/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSerializeElementItemsByReferenceId/ReferenceElementItemBusinessBean ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#updateNotSerializeElementItemsByReferenceId(java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateNotSerializeElementItemsByReferenceId(Long referenceId , Long newStatus) throws BusinessException{
		log.debug("== Inicia updateNotSerializeElementItemsByReferenceId/ReferenceElementItemBusinessBean ==");
        try {
        	List<ReferenceElementItem> elementsList = daoReferenceElementItem.getNotSerializedReferenceElementItemByReferenceId(referenceId);
        	if(elementsList != null ){
        		for(ReferenceElementItem element : elementsList){
        			ItemStatus newItemStatus = new ItemStatus();
        			newItemStatus.setId(newStatus);
        			element.setItemStatus(newItemStatus);
        			daoReferenceElementItem.updateReferenceElementItem(element);
        		}
        	}
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateNotSerializeElementItemsByReferenceId/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateNotSerializeElementItemsByReferenceId/ReferenceElementItemBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#getNotSerializedReferenceElementItemByReferenceId(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ReferenceElementItemVO> getNotSerializedReferenceElementItemByReferenceId(Long referenceId) throws BusinessException{
		log.debug("== Inicia getNotSerializedReferenceElementItemByReferenceId/ReferenceElementItemBusinessBean ==");
        try {
        	List<ReferenceElementItemVO> items = UtilsBusiness.convertList(daoReferenceElementItem.getNotSerializedReferenceElementItemByReferenceId(referenceId), ReferenceElementItemVO.class);
        	return items;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getNotSerializedReferenceElementItemByReferenceId/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getNotSerializedReferenceElementItemByReferenceId/ReferenceElementItemBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#getSerializedReferenceElementItemByReferenceId(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ReferenceElementItemVO> getSerializedReferenceElementItemByReferenceId(Long referenceId) throws BusinessException{
		log.debug("== Inicia getSerializedReferenceElementItemByReferenceId/ReferenceElementItemBusinessBean ==");
        try {
        	List<ReferenceElementItemVO> items = UtilsBusiness.convertList(daoReferenceElementItem.getSerializedReferenceElementItemByReferenceId(referenceId), ReferenceElementItemVO.class);
        	return items;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getSerializedReferenceElementItemByReferenceId/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSerializedReferenceElementItemByReferenceId/ReferenceElementItemBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#getReferenceElementItemByReferenceIdAndByItemStatusAndElementType(java.lang.Long,java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ReferenceElementItemVO> getReferenceElementItemByReferenceIdAndByItemStatusAndElementType(Long referenceId , String elementItemStatus,String elementType) throws BusinessException{
		log.debug("== Inicia getReferenceElementItemByReferenceIdAndByItemStatusAndElementType/ReferenceElementItemBusinessBean ==");
        try {
        	List<ReferenceElementItemVO> items = UtilsBusiness.convertList(daoReferenceElementItem.getReferenceElementItemByReferenceIdAndByItemStatusAndElementType(referenceId,elementItemStatus,elementType), ReferenceElementItemVO.class);
        	return items;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceElementItemByReferenceIdAndByItemStatusAndElementType/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceElementItemByReferenceIdAndByItemStatusAndElementType/ReferenceElementItemBusinessBean ==");
        }
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void receptRefSerialElementItem(List<ReferenceElementItemVO> refElements) throws BusinessException{
		log.debug("== Inicia receptRefSerialElementItem/ReferenceElementItemBusinessBean ==");
        try {
        	String listEmptyMessage = " No se ha especificado la lista de items a reportar como recibidos en la remisión";
        	UtilsBusiness.assertNotNull(refElements, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + listEmptyMessage);

        	if(refElements.isEmpty()){
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + listEmptyMessage);
        	}
        	
        	Reference ref = daoRef.getReferenceByID(refElements.get(0).getReference().getId());
        	UtilsBusiness.assertNotNull(ref, ErrorBusinessMessages.NO_REFERENCE_EXIT.getCode(),	ErrorBusinessMessages.NO_REFERENCE_EXIT.getMessage() + " No se ha encontrado la información de la remisión con id: " + refElements.get(0).getReference().getId());
        	
        	if(! ref.getReferenceStatus().getRefStatusCode().equals(CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity())
        	    && ! ref.getReferenceStatus().getRefStatusCode().equals(CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity())){
				throw new BusinessException(ErrorBusinessMessages.NO_REFERENCE_EXIT.getCode(),ErrorBusinessMessages.NO_REFERENCE_EXIT.getMessage());
			}
        	
        	ReferenceElementItem refItem = null;
        	//Valida los elementos de un registro de importación
        	List<ReferenceElementItemVO> refElements2Add =  this.fillreferenceElementItems(refElements, CodesBusinessEntityEnum.ITEM_STATUS_CREATED.getCodeEntity(), true);
        	
    		for(ReferenceElementItemVO refElement : refElements2Add){
    			refItem = UtilsBusiness.copyObject(ReferenceElementItem.class, refElement);
        		this.daoReferenceElementItem.createReferenceElementItem(refItem);
    		}
        	
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación receptRefSerialElementItem/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina receptRefSerialElementItem/ReferenceElementItemBusinessBean ==");
        }
	}
	
	/**
	 * Metodo: Permite validar los elementos de una remisión
	 * @param refElements - List<ReferenceElementItemVO> elementos del registro de importación
	 * @return  true si los elementos del registro están correctos; false en otro caso
	 * @throws BusinessException En caso de error en la validación
	 * @author gfandino
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ReferenceElementItemVO> fillreferenceElementItems(List<ReferenceElementItemVO> refElements, String itemStatusCode, boolean registeringReception) throws BusinessException{
		log.debug("== Inicia validateSerElementItems/ReferenceElementItemBusinessBean ==");
        try {
        	List<ReferenceElementItemVO> result = new ArrayList<ReferenceElementItemVO>();
        	
        	ItemStatus itemStatus =  daoItemStatus.getItemStatusByCode(itemStatusCode);
        	
        	if(itemStatus==null){
        		throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage() + " No se encontró el estado del item por el código especificado: " + itemStatusCode);
        	}
        	
        	if(refElements.isEmpty()){
        		String message = " No se han enviado elementos para validar en la remsión";
        		log.error(message);
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + message);
        	}
        	
        	//Valida que la remisión exista
    		Reference ref = this.daoRef.getReferenceByID(refElements.get(0).getReference().getId());
    		long sourceWhId = 0L;
    		if(ref ==null){
    			throw new BusinessException(ErrorBusinessMessages.NO_REFERENCE_EXIT.getCode(),ErrorBusinessMessages.NO_REFERENCE_EXIT.getMessage());
    		}
			
    		Serialized element = null;
    		WarehouseElement whElement = null;
    		ElementType elType = null;
    		
        	for(ReferenceElementItemVO refElement : refElements){
        		UtilsBusiness.assertNotNull(refElement, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		
        		//Valida que el elemento serializado sea correcto
        		elType = this.daoElementType.getElementTypeByCode(refElement.getSerializeElement().getElement().getElementType().getTypeElementCode().trim());
        		if(elType==null){
        			log.error("El tipo enviado en el archivo no existe");
            		throw new BusinessException(ErrorBusinessMessages.ERROR_FILE_INCORRECT.getCode(),ErrorBusinessMessages.ERROR_FILE_INCORRECT.getMessage());
            	}
        		//Si el tipo de elemento no es SERIALIZADO
        		if(! elType.getIsSerialized().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity())){
        			String message = " El código de tipo de elemento especificado: " + elType.getTypeElementCode() + " no corresponde con un tipo de elemento SERIALIZADO";
        			log.error(message);
        			throw new BusinessException(ErrorBusinessMessages.ELEMENT_ISNT_SERIALIZED.getCode(), ErrorBusinessMessages.ELEMENT_ISNT_SERIALIZED.getMessage() + message);
        		}
        		
        		//Si el tipo de elemento es serializado se debe validar que traiga el serial debido a que és obligatorio
        		if(refElement.getSerializeElement().getSerialCode() == null || refElement.getSerializeElement().getSerialCode().trim().length() == 0){
        			String message = "Se ha enviado la información de un ítem de remisión de tipo serializado sin serial, el código del tipo del elemento es: " + elType.getTypeElementCode();
        			log.error(message);
        			throw new BusinessException(ErrorBusinessMessages.ELEMENT_SERIAL_NULL.getCode(), ErrorBusinessMessages.ELEMENT_SERIAL_NULL.getMessage());
    			}
        		
        		element = this.serializedDAO.getSerializedBySerialAndElementType(refElement.getSerializeElement().getSerialCode(),elType.getId(),refElement.getReference().getCountryCodeId().getId() );
        		
        		if(element==null){
        			log.error("El serial y tipo no coincide con un elemento existente");
        			throw new BusinessException(ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(),ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
        		} 
    			
        		if(registeringReception){
        			sourceWhId = ref.getTargetTransitWh() == null? ref.getWarehouseBySourceWh().getId() : ref.getTargetTransitWh().getId();
        		}else{
        			sourceWhId = ref.getWarehouseBySourceWh().getId();
        		}
    			whElement = this.daoWarehouseElement.getWarehouseElementBySerialNumAndExitDateNull(element.getElementId(), sourceWhId);
    		
        		if(whElement==null){
        			String message ="No se ha encontrado el elemento con serial: "+refElement.getSerializeElement().getSerialCode()+" en la bodega de origen con id: " + sourceWhId + " "; 
        			log.error(message);
        			throw new BusinessException(ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(),ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage() + message);
        		}
        		
        		ReferenceElementItem refElementItem = new ReferenceElementItem();
        		refElementItem.setElement(element.getElement());
        		refElementItem.setItemStatus(itemStatus);
        		refElementItem.setReference(ref);
        		refElementItem.setRefQuantity(1D);
        		result.add(UtilsBusiness.copyObject(ReferenceElementItemVO.class, refElementItem));
        	}
        	
        	return result;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación validateSerElementItems/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina validateSerElementItems/ReferenceElementItemBusinessBean ==");
        }
		
	}
	
	
//	/*
//	 * (non-Javadoc)
//	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#addElementToReference(co.com.directv.sdii.model.vo.ReferenceElementItemVO, java.lang.String)
//	 */
//	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public void addElementToReference(ReferenceElementItemVO refElementVO) throws BusinessException {
//	 	log.debug("== Inicia addElementToReference/ReferenceBusinessBean ==");
//	 	UtilsBusiness.assertNotNull(refElementVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//        UtilsBusiness.assertNotNull(refElementVO.getReference(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//        UtilsBusiness.assertNotNull(refElementVO.getReference().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//        UtilsBusiness.assertNotNull(refElementVO.getElement(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//        UtilsBusiness.assertNotNull(refElementVO.getElement().getIsSerialized(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//        UtilsBusiness.assertNotNull(refElementVO.getRefQuantity(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//        
//        
//        try {
//        	//1) Consulta
//        	Reference ref = this.daoRef.getReferenceByID(refElementVO.getReference().getId());
//        	Element elmt = null;
//        	Serialized serialized = null;
//        	Serialized linkedSerialized = null;
//        	Element linkedElmt = null;
//        	if( refElementVO.getElement().getIsSerialized().equalsIgnoreCase( CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity() ) ){
//        		UtilsBusiness.assertNotNull(refElementVO.getSerializeElement(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//        		UtilsBusiness.assertNotNull(refElementVO.getSerializeElement().getSerialCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//        		serialized = this.daoSerialized.getSerializedBySerial(refElementVO.getSerializeElement().getSerialCode());
//        		if( serialized == null ){
//        			throw new BusinessException(ErrorBusinessMessages.STOCK_IN367.getCode(),ErrorBusinessMessages.STOCK_IN367.getMessage());
//        		}
//        		elmt = serialized.getElement();
//        	
//			} else if( refElementVO.getElement().getIsSerialized().equalsIgnoreCase( CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity() ) ){
//				UtilsBusiness.assertNotNull(refElementVO.getElement().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//				elmt =  this.businessElement.getElementByID(refElementVO.getElement().getId());
//			
//			}
//        	if(elmt==null){
//        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN367.getCode(),ErrorBusinessMessages.STOCK_IN367.getMessage());
//        	}
//        	
//        	//3. Consulta el tipo de elemento en el control de cantidades para la remisión
//        	this.businessRefQuantityControlItem.generateRefQuantiyControls( UtilsBusiness.copyObject(ElementVO.class, elmt) , UtilsBusiness.copyObject(ReferenceVO.class, ref),refElementVO.getRefQuantity());
//        	//En caso que tenga vinculado lo agrega
//        	if( linkedSerialized != null && linkedElmt != null ){
//        		//Como es un elemento serializado el vinculado se pone por defecto cantidad 1
//        		this.businessRefQuantityControlItem.generateRefQuantiyControls( UtilsBusiness.copyObject(ElementVO.class, linkedElmt) , UtilsBusiness.copyObject(ReferenceVO.class, ref),1D);
//        	}
//        		
//        	//5. Se agrega el item a la remisión
//        	ItemStatus itemStatus = this.businessItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_CREATED.getCodeEntity());
//        	refElementVO.setItemStatus(itemStatus);
//        	refElementVO.setElement(elmt);
//        	refElementVO.setReference(ref);
//        	this.createReferenceElementItem(refElementVO);
//        	//Si tiene vinculado se agrega a la remision
//        	if( linkedSerialized != null && linkedElmt != null ){
//        		ReferenceElementItemVO linkedRefElementVO = UtilsBusiness.copyObject(ReferenceElementItemVO.class, refElementVO);
//        		linkedRefElementVO.setItemStatus(itemStatus);
//        		linkedRefElementVO.setElement(linkedElmt);
//        		linkedRefElementVO.setReference(ref);
//            	this.createReferenceElementItem(linkedRefElementVO);
//        	}
//        	//6. Actualizar el estado de la remisión
//        	if( !ref.getReferenceStatus().getRefStatusCode().equals( CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() ) ){
//        		//Crea el registro en las modificaciones de remision
//                this.businessReferenceModification.createReferenceModification( ref.getId() , CodesBusinessEntityEnum.REFERENCE_MODIFICATION_TYPE_CREATED.getCodeEntity(), ref.getCreateUserId().getId());
//        	}
//        	ReferenceStatus refStatus = this.businessRefStatus.getReferenceStatusByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity());
//        	ref.setReferenceStatus(refStatus);
//        	this.businessRef.updateReference(UtilsBusiness.copyObject(ReferenceVO.class, ref));
//        	//7. Mover el elemento
//        	//Se obtiene la bodega de transito de origen
//        	if( ref != null && ( ref.getSourceTransitWh() == null || ref.getSourceTransitWh().getId().longValue() <= 0 ) ){
//        		//No se encontro la bodega de transito origen
//				Object[] params = {ref.getWarehouseBySourceWh().getWhCode()};
//				throw new BusinessException(ErrorBusinessMessages.STOCK_IN384.getCode(), ErrorBusinessMessages.STOCK_IN384.getMessage(params));
//        	}
//        	
//        	//Determina si es bodega de talle
//        	String movementTypeCodeE = getEntryTypeCodeByReference( ref );
//        	String movementTypeCodeS = getExitTypeCodeByReference( ref );
//
//        	//Si no se encontró causal de movimiento
//        	if(movementTypeCodeE==null || movementTypeCodeS== null){
//        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN380.getCode(),ErrorBusinessMessages.STOCK_IN380.getMessage());
//        	}
//        	
//        	
//        	ElementMovementDTO dto = null;
//        	if(elmt.getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity())){
//        		dto = new ElementMovementDTO(ref.getWarehouseBySourceWh().getId(), ref.getSourceTransitWh().getId(), serialized.getElementId(), movementTypeCodeE, movementTypeCodeS, ref.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), serialized.getSerialCode(), null, false, null,itemStatus);
//        		businessWhElement.moveElementToWareHouse(dto);
//        		
//        	}else{
//        		NotSerialized notSerialized = this.notSerializedBusiness.getNotSerializedByID(elmt.getId());
//        		dto = new ElementMovementDTO(ref.getWarehouseBySourceWh().getId(), ref.getSourceTransitWh().getId(), notSerialized.getElement().getElementType().getId() , refElementVO.getRefQuantity(), movementTypeCodeE, movementTypeCodeS, false, ref.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity());
//        		businessWhElement.moveNotSerializedElementBetweenWareHouses(dto);
//        	}
//        } catch(Throwable ex){
//        	log.debug("== Error al tratar de ejecutar la operación addElementToReference/ReferenceBusinessBean ==");
//        	throw this.manageException(ex);
//        } finally {
//            log.debug("== Termina addElementToReference/ReferenceBusinessBean ==");
//        }
//	}
	
	/**
	 * 
	 * Metodo: Consulta el elemento vinculado o al que esta vinculado un elemento serializado
	 * @param serialized elemento serializado al que se le busca el vinculado o al que esta vinculado
	 * @return Serialized elemento al que esta vinculado o que tiene vinculado
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private Serialized getLinkedElement(Serialized serialized) throws BusinessException{
		log.debug("== Inicia getLinkedElement/WarehouseElementBusinessBean ==");
        try{
        	Serialized response = null;
        	if( serialized.getSerialized() != null ){
        		response = serialized.getSerialized();
        	} else{
        		List<Serialized> linkedElements = this.daoSerialized.getLinkedSerializedBySerializedId(serialized.getElementId());
        		if( linkedElements != null && !linkedElements.isEmpty() )
        			response =  linkedElements.get(0);
        	} 
        	return response;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getLinkedElement/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getLinkedElement/ReferenceBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#getEntryTypeCodeByReference(co.com.directv.sdii.model.pojo.Reference)
	 */
	@Override
	public String getEntryTypeCodeByReference(Reference reference, boolean isBetweenDealers) throws BusinessException{
		log.debug("== Inicia getEntryTypeCodeByReference/WarehouseElementBusinessBean ==");
        try{
        	String movTypeCodeE = null;
        	//Se valida que la bodega destino sea de una cuadrilla
        	if(isBetweenDealers){
        		movTypeCodeE = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSLATE_BY_REF_BETWEEN_COMPANY_ENTRY.getCodeEntity();
        	}else{
        		if( reference.getWarehouseByTargetWh().getCrewId() != null ){
            		movTypeCodeE = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_CREW_ASSIGMENT_ENTRY.getCodeEntity();
            	} else{
            		movTypeCodeE = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSLATE_BY_REF_SAME_COMPANY_ENTRY.getCodeEntity();
            		//Revisa si se esta haciendo una remision a una bodega de tipo taller
            		//Se comenta el siguiente segmento de codigo por solicitud del usuario, Eliminar el tipo de movimiento a bodegas de taller
            		/*
            		if( reference.getWarehouseByTargetWh().getWarehouseType().getWhTypeCode().equals( CodesBusinessEntityEnum.WORKSHOP_WH_CODE_01.getCodeEntity() )
            				|| reference.getWarehouseByTargetWh().getWarehouseType().getWhTypeCode().equals( CodesBusinessEntityEnum.WORKSHOP_WH_CODE_02.getCodeEntity() ) 
            				|| reference.getWarehouseByTargetWh().getWarehouseType().getWhTypeCode().equals( CodesBusinessEntityEnum.WORKSHOP_WH_CODE_03.getCodeEntity() ) 
            				|| reference.getWarehouseByTargetWh().getWarehouseType().getWhTypeCode().equals( CodesBusinessEntityEnum.WORKSHOP_WH_CODE_04.getCodeEntity() ) 
            				|| reference.getWarehouseByTargetWh().getWarehouseType().getWhTypeCode().equals( CodesBusinessEntityEnum.WORKSHOP_WH_CODE_05.getCodeEntity() ) ){
            			movTypeCodeE = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_WORKSHOP_ENTRY.getCodeEntity();
            		} else {
            			movTypeCodeE = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSLATE_BY_REF_BETWEEN_COMPANY_ENTRY.getCodeEntity();
            		}*/
            	}
        	}
        
        	return movTypeCodeE;
        } catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación getEntryTypeCodeByReference/WarehouseElementBusinessBean ==");
	    	throw this.manageException(ex);
	   } finally {
	        log.debug("== Termina getEntryTypeCodeByReference/WarehouseElementBusinessBean ==");
	   }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#getExitTypeCodeByReference(co.com.directv.sdii.model.pojo.Reference)
	 */
	@Override
	public String getExitTypeCodeByReference(Reference reference, boolean isBetweenDealers) throws BusinessException{
		log.debug("== Inicia getEntryTypeCodeByReference/WarehouseElementBusinessBean ==");
        try{
        	String movTypeCodeS = null;
        	//Se valida que la bodega destino sea de una cuadrilla
        	if (isBetweenDealers){
        		movTypeCodeS = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSLATE_BY_REF_BETWEEN_COMPANY_EXIT.getCodeEntity();
        	}else{
        		if( reference.getWarehouseByTargetWh().getCrewId() != null ){
        			movTypeCodeS = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_CREW_ASSIGMENT_EXIT.getCodeEntity();
        		}else {
        			movTypeCodeS = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSLATE_BY_REF_SAME_COMPANY_EXIT.getCodeEntity();
        		
        		
	        		//Revisa si se esta haciendo una remision a una bodega de tipo taller
	        		//Se comenta el siguiente segmento de codigo por solicitud del usuario, Eliminar el tipo de movimiento a bodegas de taller
	        		/*
	        		if( reference.getWarehouseByTargetWh().getWarehouseType().getWhTypeCode().equals( CodesBusinessEntityEnum.WORKSHOP_WH_CODE_01.getCodeEntity() )
        				|| reference.getWarehouseByTargetWh().getWarehouseType().getWhTypeCode().equals( CodesBusinessEntityEnum.WORKSHOP_WH_CODE_02.getCodeEntity() ) 
        				|| reference.getWarehouseByTargetWh().getWarehouseType().getWhTypeCode().equals( CodesBusinessEntityEnum.WORKSHOP_WH_CODE_03.getCodeEntity() ) 
        				|| reference.getWarehouseByTargetWh().getWarehouseType().getWhTypeCode().equals( CodesBusinessEntityEnum.WORKSHOP_WH_CODE_04.getCodeEntity() ) 
        				|| reference.getWarehouseByTargetWh().getWarehouseType().getWhTypeCode().equals( CodesBusinessEntityEnum.WORKSHOP_WH_CODE_05.getCodeEntity() ) ){
        			movTypeCodeE = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_WORKSHOP_EXIT.getCodeEntity();
        			*/
        		} 
        	}
        	return movTypeCodeS;
        } catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación getEntryTypeCodeByReference/WarehouseElementBusinessBean ==");
	    	throw this.manageException(ex);
	   } finally {
	        log.debug("== Termina getEntryTypeCodeByReference/WarehouseElementBusinessBean ==");
	   }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#removeSerializedElementOfReference(java.lang.Long, java.lang.String)
	 */
	@Override
	public void removeElementOfReference(ReferenceElementItemVO item, 
			                             Long userId
	) throws BusinessException {
		log.debug("== Inicia removeSerializedElementOfReference/WarehouseElementBusinessBean ==");
        try{
        	UtilsBusiness.assertNotNull(item, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(item.getReference(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(item.getElement(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(item.getElement().getIsSerialized(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	
        	//Consulto el usuario 
        	User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
        	
        	if( item.getReference().getId() == null || item.getReference().getId().longValue() <= 0 ){
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	if( item.getElement().getId() == null || item.getElement().getId().longValue() <= 0 ){
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	//Consulta la remision a la que se le va a elminar el elemento serializado 
        	Reference reference = this.daoRef.getReferenceByID(item.getReference().getId().longValue());
        	if( reference != null ){
        	/*ialessan IN317430 - Inconsistencia en remisiones*/
        	if(                                                                                        //estado En creacion
        			reference.getReferenceStatus().getRefStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity()) 
					                                                                                      // estado Creada
					   ||
					   reference.getReferenceStatus().getRefStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() ) ){
        	
        	
		        	//if( reference != null ){
		        		//Se obtiene la bodega de transito de origen
		        		if( reference.getSourceTransitWh() != null && reference.getSourceTransitWh().getId().longValue() > 0 ){
		        			WarehouseVO sourceWh = new WarehouseVO();
		            		sourceWh.setId( reference.getSourceTransitWh().getId() );
		            		
		        			if( item.getElement().getIsSerialized().equalsIgnoreCase( CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity() ) ){
		        					this.removeSerializedelementFromReference(item.getElement().getId() , sourceWh , reference.getWarehouseBySourceWh().getId(),reference, user);
		        				} else if( item.getElement().getIsSerialized().equalsIgnoreCase( CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity() ) ){
		        					this.removeNotSerializedelementFromReference(item.getElement().getId() , sourceWh , reference.getWarehouseBySourceWh().getId(),reference,item.getRefQuantity(), user);
		        				}
		        		} else {
		    				//No se encontro la bodega de transito origen
		    				Object[] params = {reference.getWarehouseBySourceWh().getWhCode()};
		    				throw new BusinessException(ErrorBusinessMessages.STOCK_IN384.getCode(), ErrorBusinessMessages.STOCK_IN384.getMessage(params));
		    			}		    			
		        	
	        	}else{				 
	 				Object params[] = {reference.getId().toString()};		
	 				List<String> paramsList = new ArrayList<String>();
	 				paramsList.add(reference.getId().toString());
	 				throw new BusinessException(	ErrorBusinessMessages.STOCK_IN_REM_ELE.getCode(), 
	 					 		                    ErrorBusinessMessages.STOCK_IN_REM_ELE.getMessage(params),
	 					 		                    paramsList
	 					 		                     );
	
	 			} 	
        	} else {
	        	  
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN385.getCode(), ErrorBusinessMessages.STOCK_IN385.getMessage());
        	 }        	
		        	
        }  catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación removeSerializedElementOfReference/WarehouseElementBusinessBean ==");
	    	throw this.manageException(ex);
        } finally {
	        log.debug("== Termina removeSerializedElementOfReference/WarehouseElementBusinessBean ==");
        }
	}
	
	/**
	 * 
	 * Metodo: Elimina un elemento serializado de una remision
	 * @param elementId Identificador del elemento serializado que se va a eliminar de la remision
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private void removeSerializedelementFromReference(Long elementId , WarehouseVO whSource, Long whTargetId,Reference reference, User user) throws BusinessException {
		log.debug("== Inicia removeSerializedelementFromReference/WarehouseElementBusinessBean ==");
        try{
        	Serialized serializedElement = daoSerialized.getSerializedByID(elementId);
        	Serialized linkedSerialized = this.getLinkedElement(serializedElement);
        	
        	Reference ref = daoRef.getReferenceByID(reference.getId());
        	
        	boolean isBetweenDealers =  this.isReferenceBetweenCompany(ref);
        	
        	if(serializedElement != null){
        		//define el tipo de movimiento de entrada
    			String movTypeCodeE = this.getEntryTypeCodeByReference(reference, isBetweenDealers);
        		String movTypeCodeS = this.getExitTypeCodeByReference(reference, isBetweenDealers);
        		
        		/*
        		ElementMovementDTO dto = null;
        		dto = new ElementMovementDTO(whSource.getId(), whTargetId, elementId, movTypeCodeE, movTypeCodeS, reference.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), serializedElement.getSerialCode(), null, false, null,null);
        		businessWhElement.moveElementToWareHouse(dto);*/
        		
        		
        		MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movTypeCodeE, movTypeCodeS);
        		Warehouse warehouseTarget = daoWarehouse.getWarehouseByID(whTargetId);
				MovementElementDTO dtoMovement = new MovementElementDTO(user, 
						UtilsBusiness.copyObject(WarehouseVO.class, whSource), 
						UtilsBusiness.copyObject(WarehouseVO.class, warehouseTarget),
						serializedElement,
						reference.getId(),
						CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(),
						dtoGenerics.getMovTypeCodeE(),
						dtoGenerics.getMovTypeCodeS(),
						dtoGenerics.getRecordStatusU(), 
						dtoGenerics.getRecordStatusH(), 
						false, 
						CodesBusinessEntityEnum.PROCESS_CODE_IBS_REFERENCE.getCodeEntity(),
						dtoGenerics.getMovConfigStatusPending(),
						dtoGenerics.getMovConfigStatusNoConfig());
				businessMovementElement.moveSerializedElementBetweenWarehouse(dtoMovement);
				
				
        		//Se realizan operaciones sobre los controles de cantidades
        		this.businessRefQuantityControlItem.generateRefQuantiyControls( UtilsBusiness.copyObject(ElementVO.class, serializedElement.getElement()) , UtilsBusiness.copyObject(ReferenceVO.class, reference),-1D);
        		//Se realizan las operaciones sobre el elemento vinculado
        		if( linkedSerialized != null ){
//        			dto = new ElementMovementDTO(whSource.getId(), whTargetId, linkedSerialized.getElementId(), movTypeCodeE, movTypeCodeS, reference.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), linkedSerialized.getSerialCode(), null, false, null);
//        			businessWhElement.moveSerializedElementBetweenWareHouses(dto);
        			this.businessRefQuantityControlItem.generateRefQuantiyControls( UtilsBusiness.copyObject(ElementVO.class, linkedSerialized.getElement()) , UtilsBusiness.copyObject(ReferenceVO.class, reference),-1D);
        		}
        		//Se elmina el refElementItem asociado a la remision
        		ReferenceElementItem refElementitem = this.daoReferenceElementItem.getReferenceElementItemByElementIdAndReferenceId( serializedElement.getElementId(), reference.getId() );
        		if( refElementitem != null )
        			this.daoReferenceElementItem.deleteReferenceElementItem(refElementitem);
        		//Se elmina el refElementItem vinculado asociado a la remision
        		/*if( linkedSerialized != null ){
        			ReferenceElementItem linkedRefElementitem = this.daoReferenceElementItem.getReferenceElementItemByElementIdAndReferenceId( linkedSerialized.getElementId(), null );
            		if( linkedRefElementitem != null )
            			this.daoReferenceElementItem.deleteReferenceElementItem(linkedRefElementitem);
        		}*/
        	} else {
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN386.getCode(), ErrorBusinessMessages.STOCK_IN386.getMessage());
        	}
        }catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación removeSerializedelementFromReference/WarehouseElementBusinessBean ==");
	    	throw this.manageException(ex);
        } finally {
	        log.debug("== Termina removeSerializedelementFromReference/WarehouseElementBusinessBean ==");
        }
	}
	
	/**
	 * 
	 * Metodo: Elimina un elemento no serializado de una remision
	 * @param elementId Identificador del elemento no serializado que se va a eliminar de la remision
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private void removeNotSerializedelementFromReference(Long elementId , WarehouseVO whSource, Long whTargetId,Reference reference,Double refQuantity, User user) throws BusinessException {
		log.debug("== Inicia removeNotSerializedelementFromReference/WarehouseElementBusinessBean ==");
        try{
        	NotSerialized notSerialized = this.notSerializedBusiness.getNotSerializedByID(elementId);
        	Reference ref = daoRef.getReferenceByID(reference.getId());
        	boolean isBetweenDealers =  this.isReferenceBetweenCompany(reference);
    		if(notSerialized != null){
    			//define el tipo de movimiento de entrada
    			String movTypeCodeE = this.getEntryTypeCodeByReference(ref, isBetweenDealers);
        		String movTypeCodeS = this.getExitTypeCodeByReference(ref, isBetweenDealers);
        		
        		//Consultar el elemento
        		Element elementSelected = daoElement.getElementByID(elementId);
        		if(elementSelected==null){
        			throw new BusinessException(ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(),ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
        		}
        		
        		/*ElementMovementDTO dto = null;
        		dto = new ElementMovementDTO(whSource.getId(), whTargetId, elementSelected.getElementType().getId(), refQuantity, movTypeCodeE, movTypeCodeS, false, reference.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity());
        		businessWhElement.moveNotSerializedElementBetweenWareHouses(dto);*/
        		
        		Warehouse warehousetarget = daoWarehouse.getWarehouseByID(whTargetId);
        		MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movTypeCodeE, movTypeCodeS);
				MovementElementDTO dtoMovement = new MovementElementDTO(user,
						whSource, 
						UtilsBusiness.copyObject(WarehouseVO.class,  warehousetarget), 
						null, 
						elementSelected.getElementType().getId(), 
						null,
						reference.getId(), 
						CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), 
						dtoGenerics.getMovTypeCodeE(), 
						dtoGenerics.getMovTypeCodeS(), 
						dtoGenerics.getRecordStatusU(),
						dtoGenerics.getRecordStatusH(),
						refQuantity,
						dtoGenerics.getMovConfigStatusPending(),
						dtoGenerics.getMovConfigStatusNoConfig());
				businessMovementElement.moveNotSerializedElementBetweenWarehouse(dtoMovement);
        		
        		
        		//Se realizan operaciones sobre los controles de cantidades
        		this.businessRefQuantityControlItem.generateRefQuantiyControls( UtilsBusiness.copyObject(ElementVO.class, notSerialized.getElement()) , UtilsBusiness.copyObject(ReferenceVO.class, reference),refQuantity*-1);
        		//Se elmina el refElementItem asociado a la remision
        		ReferenceElementItem refElementitem = this.daoReferenceElementItem.getReferenceElementItemByElementIdAndReferenceId( notSerialized.getElementId(), reference.getId() );
        		if( refElementitem != null )
        			this.daoReferenceElementItem.deleteReferenceElementItem(refElementitem);
        	} else {
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN386.getCode(), ErrorBusinessMessages.STOCK_IN386.getMessage());
        	}
        }catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación removeNotSerializedelementFromReference/WarehouseElementBusinessBean ==");
	    	throw this.manageException(ex);
        } finally {
	        log.debug("== Termina removeNotSerializedelementFromReference/WarehouseElementBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#getReferenceElementsByReferenceIdAndSerialOrElementType(java.lang.Long, java.lang.String, java.lang.Long, boolean, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ElementMixedResponse getReferenceElementsByReferenceIdAndSerialOrElementType(
			Long refID, String serial, Long elementTypeId, boolean isSerialized, RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getReferenceElementsByReferenceIdAndSerialOrElementType/ReferenceElementItemBusinessBean ==");
        try {
        	
        	UtilsBusiness.assertNotNull(refID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(requestCollInfo, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	if(isSerialized){
        		return daoReferenceElementItem.getReferenceElementsSerializedByReferenceIdAndSerialOrElementType(refID, serial, elementTypeId, requestCollInfo);
        	}else{
        		return daoReferenceElementItem.getReferenceElementsNotSerializedByReferenceIdElementType(refID, elementTypeId, requestCollInfo);
        	}
        	
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceElementsByReferenceIdAndSerialOrElementType/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceElementsByReferenceIdAndSerialOrElementType/ReferenceElementItemBusinessBean ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#massiveMovementOfReferenceElementItems(co.com.directv.sdii.model.dto.MassiveMovementBetweenWareHouseDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void massiveMovementOfReferenceElementItems(MassiveMovementBetweenWareHouseDTO dto, Long userId) throws BusinessException {
		log.debug("== Inicia massiveMovementOfReferenceElementItems/ReferenceElementItemBusinessBean ==");
		UtilsBusiness.assertNotNull(dto, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getIsSerialized(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(userId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try{
			//Consulto el usuario 
        	User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
			
			if(dto.getReference() == null || dto.getReference().getId() == null || dto.getReference().getId().longValue() <= 0){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			List<ReferenceElementItem> elements = null;
			boolean isSerialized = dto.getIsSerialized().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity()) ? true : false;
			if(isSerialized){
				elements = this.daoReferenceElementItem.getSerializeReferenceElementItemByReferenceIdAndByNotItemStatus(dto.getReference().getId(), CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity());
			} else {
				elements = this.daoReferenceElementItem.getNotSerializeReferenceElementItemByReferenceIdAndByNotItemStatus(dto.getReference().getId(), CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity());
			}
			if((elements == null || elements.isEmpty()) && dto.isValidateElementsToConfirm()){
				if(isSerialized){
					throw new BusinessException(ErrorBusinessMessages.REFERENCE_ELEM_ITEMS_SERIALIZE_DOESNT_EXIST.getCode(),ErrorBusinessMessages.REFERENCE_ELEM_ITEMS_SERIALIZE_DOESNT_EXIST.getMessage());
				}else{
					throw new BusinessException(ErrorBusinessMessages.REFERENCE_ELEM_ITEMS_NOT_SERIALIZE_DOESNT_EXIST.getCode(),ErrorBusinessMessages.REFERENCE_ELEM_ITEMS_NOT_SERIALIZE_DOESNT_EXIST.getMessage());
				}
			}else{
				//define el tipo de movimiento de entrada
				String movTypeCodeE = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSLATE_BY_REF_BETWEEN_COMPANY_ENTRY.getCodeEntity();
	    		String movTypeCodeS = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSLATE_BY_REF_BETWEEN_COMPANY_EXIT.getCodeEntity();
	    		
	    		MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movTypeCodeE, movTypeCodeS);
	    		
	    		if(elements != null && !elements.isEmpty()){
	    			//Se actualiza el estado del elemento
	    			Long itemNewStatusId = CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getIdEntity(ItemStatus.class.getName());
	    			ItemStatus itemStatus = new ItemStatus(itemNewStatusId);
	    			for(ReferenceElementItem item : elements){
	    				RefConfirmation confirmation = new RefConfirmation();
						//Obtengo la suma de las confirmacion que se han hecho
	        			Double actualConfirmations = this.daoRefConfirmation.countElementConfirmedQuatity(item.getId());
	        			if(actualConfirmations != null  && item.getRefQuantity() > actualConfirmations){
	        				//Se crea una confirmacion con item.getRefQuantity() - deliveriesQuantity
	        				confirmation.setConfirmedQuantity(item.getRefQuantity() - actualConfirmations);
	        				confirmation.setReferenceElementItem(item);
	        				confirmation.setUserId(dto.getUserId());
	        				confirmation.setComfirmDate(new Date());
	        				this.businessRef.saveConfirmation(confirmation, dto.getUserId(), 0D);
	        			}
	        			//Se realiza movimiento de los elementos
	        			if(isSerialized){
	        				Serialized serializedElement = daoSerialized.getSerializedByID(item.getElement().getId());
	        	        	
	        	        	if(serializedElement != null){
	        	        		/*dtoMovement = new ElementMovementDTO(dto.getWareHouseSource().getId(), dto.getReference().getWarehouseByTargetWh().getId(), serializedElement.getElementId(), movTypeCodeE, movTypeCodeS, item.getReference().getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), serializedElement.getSerialCode(), null, true, CodesBusinessEntityEnum.PROCESS_CODE_IBS_REFERENCE.getCodeEntity(),itemStatus);
	        	        		businessWhElement.moveElementToWareHouse( dtoMovement);*/
	        	        		
	        	        		
								MovementElementDTO dtoMov = new MovementElementDTO(user, 
										UtilsBusiness.copyObject(WarehouseVO.class, dto.getReference().getTargetTransitWh()), 
										UtilsBusiness.copyObject(WarehouseVO.class, dto.getReference().getWarehouseByTargetWh()),
										serializedElement,
										item.getReference().getId(),
										CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(),
										dtoGenerics.getMovTypeCodeE(),
										dtoGenerics.getMovTypeCodeS(),
										dtoGenerics.getRecordStatusU(), 
										dtoGenerics.getRecordStatusH(), 
										true, 
										CodesBusinessEntityEnum.PROCESS_CODE_IBS_REFERENCE.getCodeEntity(),
										dtoGenerics.getMovConfigStatusPending(),
										dtoGenerics.getMovConfigStatusNoConfig());
								businessMovementElement.moveSerializedElementBetweenWarehouse(dtoMov);
	        	        		
	        	        	} else {
	        	        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN386.getCode(), ErrorBusinessMessages.STOCK_IN386.getMessage());
	        	        	}
	        	        	
	        			} else{
	        				/*dtoMovement = new ElementMovementDTO(dto.getReference().getTargetTransitWh().getId(), dto.getReference().getWarehouseByTargetWh().getId(), item.getElement().getElementType().getId(), confirmation.getConfirmedQuantity(), movTypeCodeE, movTypeCodeS, false, item.getReference().getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity());
	        				businessWhElement.moveNotSerializedElementBetweenWareHouses(dtoMovement);*/
	        				
							MovementElementDTO dtoMov = new MovementElementDTO(user,
									UtilsBusiness.copyObject(WarehouseVO.class, dto.getReference().getTargetTransitWh()), 
									UtilsBusiness.copyObject(WarehouseVO.class,  dto.getReference().getWarehouseByTargetWh()), 
									null, 
									item.getElement().getElementType().getId(), 
									null,
									item.getReference().getId(), 
									CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), 
									dtoGenerics.getMovTypeCodeE(), 
									dtoGenerics.getMovTypeCodeS(), 
									dtoGenerics.getRecordStatusU(),
									dtoGenerics.getRecordStatusH(),
									confirmation.getConfirmedQuantity(),
									dtoGenerics.getMovConfigStatusPending(),
									dtoGenerics.getMovConfigStatusNoConfig());
							businessMovementElement.moveNotSerializedElementBetweenWarehouse(dtoMov);
	        				
	        			}
					}
	    			if(isSerialized){
	                	this.updateSerializeElementItemsByReferenceId(dto.getReference().getId(),itemNewStatusId);
	    			} else{
	                	this.updateNotSerializeElementItemsByReferenceId(dto.getReference().getId(),itemNewStatusId);
	    			}
	    		}
			}
		} catch(Throwable ex){
        	log.debug("== Error massiveMovementOfReferenceElementItems/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina massiveMovementOfReferenceElementItems/ReferenceElementItemBusinessBean ==");
        }
	}


	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#partialReceptionOfReferenceElementItem(java.util.List, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void partialReceptionOfReferenceElementItem( List<ReferenceElementItemVO> elements,Long userId) throws BusinessException {
		log.debug("== Inicia partialReceptionOfReferenceElementItem/ReferenceElementItemBusinessBean ==");
		UtilsBusiness.assertNotNull(elements, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(userId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try{
			if( userId == null || userId.longValue() <= 0 ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			//Consulto el usuario 
        	User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
			
			if( elements != null && !elements.isEmpty() ){
				if( elements.get(0).getReference() == null || elements.get(0).getReference().getId() == null
						|| elements.get(0).getReference().getId().longValue() <= 0){
					throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
				}
				Reference reference = this.daoRef.getReferenceByID( elements.get(0).getReference().getId() );
				//Valida que la remision este en confirmado parcial o enviada
				if( reference.getReferenceStatus().getRefStatusCode().equalsIgnoreCase(  
						CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() ) 
						|| reference.getReferenceStatus().getRefStatusCode().equalsIgnoreCase(  
								CodesBusinessEntityEnum.REFERENCE_STATUS_SENDED.getCodeEntity() ) ){
					//Determina si es bodega de talle
					boolean isBetweenDealers =  this.isReferenceBetweenCompany(reference);
		        	String movementTypeCodeE = this.getEntryTypeCodeByReference(reference, isBetweenDealers);
		        	String movementTypeCodeS = this.getExitTypeCodeByReference(reference, isBetweenDealers);
					for( ReferenceElementItemVO vo : elements ){
						if( vo.getId() != null && vo.getId().longValue() > 0 ){
							//Obtengo la suma de las confirmacion que se han hecho
		        			Double actualConfirmations = this.daoRefConfirmation.countElementConfirmedQuatity(vo.getId());
		        			ReferenceElementItem itemPojo = this.daoReferenceElementItem.getReferenceElementItemByID( vo.getId() );
		        			if( actualConfirmations != null && itemPojo != null){
		        				if(vo.getElement().getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity())){
		        					vo.setRefQuantityPartial(new Double(1));
		        				}
		        				if(vo.getRefQuantityPartial()==null){
		        					throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		        				}else{
		        					if(vo.getRefQuantityPartial().longValue()<=0){
		        						throw new BusinessException(ErrorBusinessMessages.STOCK_IN447.getCode(), ErrorBusinessMessages.STOCK_IN447.getMessage());
		        					}
		        				}
		        				Double sumConfimation = actualConfirmations + vo.getRefQuantityPartial();
		        				//Se valida que la suma de las confirmaciones y la cantidad nueva que se confirma no sea mayor a la cantidad
		        				//de la remision
		        				if( itemPojo.getRefQuantity() < sumConfimation ){
		        					throw new BusinessException(ErrorBusinessMessages.STOCK_IN400.getCode(), ErrorBusinessMessages.STOCK_IN400.getMessage());
		        				}
		        				//Se crea la confirmacion
		        				RefConfirmation confirmation = new RefConfirmation();
		        				confirmation.setConfirmedQuantity(vo.getRefQuantityPartial());
		        				confirmation.setReferenceElementItem(vo);
		        				confirmation.setUserId(userId);
		        				this.businessRef.saveConfirmation(confirmation, userId, itemPojo.getRefQuantity() - sumConfimation);
		        				//Invoca CU INV 12 para mover la cantidad confirmada de la bodega de transito destino a la bodega destino
		        				Serialized serialized = null;
		        				if(itemPojo.getElement().getIsSerialized().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity() ) ){
		        					NotSerialized notSerialized = this.notSerializedBusiness.getNotSerializedByID(itemPojo.getElement().getId());
		        					/*dtoMovement = new ElementMovementDTO(reference.getTargetTransitWh().getId(), reference.getWarehouseByTargetWh().getId(), notSerialized.getElement().getElementType().getId(), vo.getRefQuantityPartial(), movementTypeCodeE, movementTypeCodeS, false, reference.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity());
			                		businessWhElement.moveNotSerializedElementBetweenWareHouses(dtoMovement);*/
			                		
			                		MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementTypeCodeE, movementTypeCodeS);
									MovementElementDTO dtoMov = new MovementElementDTO(user,
											UtilsBusiness.copyObject(WarehouseVO.class, reference.getTargetTransitWh()), 
											UtilsBusiness.copyObject(WarehouseVO.class,  reference.getWarehouseByTargetWh()), 
											notSerialized, 
											notSerialized.getElement().getElementType().getId(), 
											null,
											reference.getId(), 
											CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), 
											dtoGenerics.getMovTypeCodeE(), 
											dtoGenerics.getMovTypeCodeS(), 
											dtoGenerics.getRecordStatusU(),
											dtoGenerics.getRecordStatusH(),
											confirmation.getConfirmedQuantity(),
											dtoGenerics.getMovConfigStatusPending(),
											dtoGenerics.getMovConfigStatusNoConfig());
									businessMovementElement.moveNotSerializedElementBetweenWarehouse(dtoMov);
								//Invoca CU INV 14 para mover el elemento serializado de la bodega de transito destino a la bodega destino	
		        				}else {
		        					serialized = this.daoSerialized.getSerializedByID( itemPojo.getElement().getId() );
		        				}
		        				//Si la confirmacion actual mas las anteriores confirma toda la cantidad del elemento se pone en recepcionado
			        			String newRefItemStatusCode = null;
			        			if(sumConfimation.equals( itemPojo.getRefQuantity())){
			        				newRefItemStatusCode = CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity();
			        			} else {
			        				newRefItemStatusCode = CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getCodeEntity();
			        			}
			        			//Se actualiza el estado del elemento
			        			ItemStatus newItemStatus  = null;
			        			if(newRefItemStatusCode != null){
			        				newItemStatus = this.daoItemStatus.getItemStatusByCode(newRefItemStatusCode);
			        				itemPojo.setItemStatus(newItemStatus);
			        				this.daoReferenceElementItem.updateReferenceElementItem(itemPojo);
			        			}
			        			if(serialized != null){
			        				/*dtoMovement = new ElementMovementDTO(reference.getTargetTransitWh().getId(), reference.getWarehouseByTargetWh().getId(), serialized.getElementId(), movementTypeCodeE, movementTypeCodeS, reference.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), serialized.getSerialCode(),null, true, CodesBusinessEntityEnum.PROCESS_CODE_IBS_REFERENCE.getCodeEntity(),newItemStatus);
									businessWhElement.moveElementToWareHouse(dtoMovement);*/
									
									MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementTypeCodeE, movementTypeCodeS);
									MovementElementDTO dto = new MovementElementDTO(user, 
											UtilsBusiness.copyObject(WarehouseVO.class, reference.getTargetTransitWh()), 
											UtilsBusiness.copyObject(WarehouseVO.class, reference.getWarehouseByTargetWh()),
											serialized,
											reference.getId(),
											CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(),
											dtoGenerics.getMovTypeCodeE(),
											dtoGenerics.getMovTypeCodeS(),
											dtoGenerics.getRecordStatusU(), 
											dtoGenerics.getRecordStatusH(), 
											true, 
											CodesBusinessEntityEnum.PROCESS_CODE_IBS_REFERENCE.getCodeEntity(),
											dtoGenerics.getMovConfigStatusPending(),
											dtoGenerics.getMovConfigStatusNoConfig());
									businessMovementElement.moveSerializedElementBetweenWarehouse(dto);
			        			}
		        			}
						}
					}
					//Se modifica el estado de la remision de acuerdo a los estados de los elementos
					String refNewStatusCode = null;
					String refModNewStatusCode = null;
					List<ReferenceElementItem> refItems = this.daoReferenceElementItem.getReferenceElementItemByReferenceIdAndByNotItemStatus(elements.get(0).getReference().getId(), CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity());
					if(refItems != null && !refItems.isEmpty()){
						refNewStatusCode = CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity();
						refModNewStatusCode = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_PARTIALLY_CONFIRMED.getCodeEntity();
					} else {
						refNewStatusCode = CodesBusinessEntityEnum.REFERENCE_STATUS_RECEPTED.getCodeEntity();
						refModNewStatusCode = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_RECEPTED.getCodeEntity();
					}
					if(refNewStatusCode != null){
						reference.setReferenceStatus(referenceStatusDAO.getReferenceByCode(refNewStatusCode));
						this.daoRef.updateReference(reference);
						
						//Crea el registro en las modificaciones de remision
			            this.businessReferenceModification.createReferenceModification(reference.getId() , refModNewStatusCode, user.getId());
					}
				} else {
					Object params[] = {elements.get(0).getReference().getId()};
					List<String> paramsList = new ArrayList<String>();
					paramsList.add(elements.get(0).getReference().getId().toString());
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN399.getCode(), ErrorBusinessMessages.STOCK_IN399.getMessage(params),paramsList);
				}
			}
		} catch(Throwable ex){
        	log.debug("== Error partialReceptionOfReferenceElementItem/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina partialReceptionOfReferenceElementItem/ReferenceElementItemBusinessBean ==");
        }
	}
	
	/**
	 * Método encargado de validar si la remisión es entre diferentes compañias
	 * @param reference
	 * @throws BusinessException
	 */
	private boolean isReferenceBetweenCompany(Reference reference) throws BusinessException{
		log.debug("== Inicia isReferenceBetweenCompany/ReferenceElementItemBusinessBean ==");
		try{
			return reference.getWarehouseBySourceWh().getDealerId().getId().longValue() != reference.getWarehouseByTargetWh().getDealerId().getId().longValue();
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operación isReferenceBetweenCompany/ReferenceElementItemBusinessBean == " + ex.getMessage());
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina isReferenceBetweenCompany/ReferenceElementItemBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#getAddedElements(java.lang.Long)
	 */
	@Override
	public List<ReferenceElementItemVO> getReferenceElementsByRefInconsistencyId(Long refInconsistencyId)
			throws BusinessException {
		log.debug("== Inicia getAddedElements/ReferenceElementItemBusinessBean ==");
		UtilsBusiness.assertNotNull(refInconsistencyId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<ReferenceElementItemVO> items = UtilsBusiness.convertList(daoReferenceElementItem.getReferenceElementsByRefInconsistencyId(refInconsistencyId), ReferenceElementItemVO.class);
        	if(items != null) {
        		
	        	for (ReferenceElementItemVO referenceElementItemVO : items) {
	        		if(referenceElementItemVO.isSerialized()) {
						
	        			SerializedVO serialized = serializedBusiness.getSerializedByID(referenceElementItemVO.getElement().getId());
						referenceElementItemVO.setSerializeElement(serialized);
						referenceElementItemVO.setRefIncAddedQuantity(1D);
	        		
	        		} else {
	        			
	        			NotSerializedVO notSerialized = notSerializedBusiness.getNotSerializedByID(referenceElementItemVO.getElement().getId());
	        			referenceElementItemVO.setNotSerializedElement(notSerialized);
	        			
	        			//buscar todos los elementos reportados por mas elementos físicos para asignar la cantidad de la inconsistencia para mostrarla en el reporte al cierre de la inconsistencia
	        			//List<ReportedElement> reportedElements = daoReportedElement.getReportedElementsByRefInconsistencyId(refInconsistencyId, true, false);
	        			
	        			ReportedElement reportedElement = daoReportedElement.getReportedElementByRefIdAndElementTypeId(referenceElementItemVO.getReference().getId(), notSerialized.getElement().getElementType().getId(), true, false);
	        			
	        			if(reportedElement != null) {
							referenceElementItemVO.setRefIncAddedQuantity(reportedElement.getQty());
	        			}
	        			
	        		}
				}
        	}
        	return items;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAddedElements/ReferenceElementItemBusinessBean == " + ex.getMessage());
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAddedElements/ReferenceElementItemBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#getReferenceElementItemsByReferenceIDAndSerOrNotSer(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo, boolean)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceIDAndSerOrNotSer(Long refID, RequestCollectionInfo requestCollInfo,boolean isSerialized) throws BusinessException {
		log.debug("== Inicia getReferenceElementItemsByReferenceIDAndSerOrNotSer/ReferenceElementItemBusinessBean ==");
		try {
			if( refID == null || refID.longValue() <= 0 ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			String stringIsSerialized = isSerialized ? CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity() : CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity();
			ReferenceElementItemsResponse response = this.daoReferenceElementItem.getReferenceElementItemByReferenceId(refID,requestCollInfo,isSerialized);
			
			if( response != null && response.getReferenceElementItemsVO() != null && !response.getReferenceElementItemsVO().isEmpty()){
				//Para el caso de los elementos no serializados se consulta la cantidad en bodega
				Reference reference = this.daoRef.getReferenceByID(refID);
				if( reference != null ){
					for( ReferenceElementItemVO tempRefElm : response.getReferenceElementItemsVO() ){
						if( !isSerialized ){
							Double whQuantity = this.daoWarehouseElement.countWHElementByActualElementNotSerialized(reference.getWarehouseBySourceWh().getId(), reference.getWarehouseBySourceWh().getCountry().getId(), null, null, tempRefElm.getElement().getElementType().getTypeElementCode(), stringIsSerialized, null, null);
							tempRefElm.setWarehouseQuantity(whQuantity);
							
							//Cargar la cantidad confirmada
							tempRefElm.setRefConfirmationSum(daoRefConfirmation.countElementConfirmedQuatity(tempRefElm.getId()));
						}
						//Confirmaciones
						tempRefElm.setRefConfirmations( UtilsBusiness.convertList( daoRefConfirmation.getConfirmationsByReferenceElementId( tempRefElm.getId() ), RefConfirmationVO.class)  );
					}
				}
			}
			return response;
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceElementItemsByReferenceIDAndSerOrNotSer/ReferenceElementItemBusinessBean == " + ex.getMessage());
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceElementItemsByReferenceIDAndSerOrNotSer/ReferenceElementItemBusinessBean ==");
        }
	}


	@Override
	public void addElementSerialized(String serialCode, 
									 Long referenceID, 
									 boolean isPrepaid, 
									 Long userId
	) throws BusinessException {
		log.debug("== Inicia addElementSerialized/ReferenceBusinessBean ==");
	 	UtilsBusiness.assertNotNull(serialCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(referenceID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        try {
        	
        	//Consulto el usuario 
        	User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
        	
        	//1) Consulta la remisión
        	Reference ref = this.daoRef.getReferenceByID(referenceID);
        	
        	boolean isBetweenDealers =  this.isReferenceBetweenCompany(ref);
        	
        	Serialized serialized = null;
        	Serialized linkedSerialized = null;

        	//2 Consulta el estado de la remision /*ialessan IN317430 - Inconsistencia en remisiones*/
			//Valida que la remision este en confirmado parcial o enviada
			if(                                                                                        //estado En creacion
				   ref.getReferenceStatus().getRefStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity()) 
				                                                                                      // estado Creada
				   ||
				   ref.getReferenceStatus().getRefStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() ) ){
				
       	        	
		        	WarehouseElement warehouseElementSerialized = daoWarehouseElement.getWarehouseElementBySerialActive(serialCode,user.getCountry().getId());
		        	if(warehouseElementSerialized == null){
		        		Object params[] = {serialCode};
						List<String> paramsList = new ArrayList<String>();
						paramsList.add( serialCode);
						//Verifica que el elemento con serial digita exista en la bodega
						throw new BusinessException(ErrorBusinessMessages.STOCK_IN404.getCode(), ErrorBusinessMessages.STOCK_IN404.getMessage(params),paramsList);
		        	}else{
		        		
		        		//Verifica si el elemento ya se agrego a la remisión
		        		List<ReferenceElementItem> listaReferenceItems = daoReferenceElementItem.getReferenceElementsByReferenceIdAndSerial(referenceID, serialCode);
		        		if ( listaReferenceItems.size() > 0 ){
		        			throw new BusinessException(ErrorBusinessMessages.STOCK_IN442.getCode(),ErrorBusinessMessages.STOCK_IN442.getMessage());
		        		}
		        		
		        		//Verifica que el elemento adicionado exista en la bodega origen
		        		if( !warehouseElementSerialized.getWarehouseId().getId().equals( ref.getWarehouseBySourceWh().getId() ) ){
		        			throw new BusinessException(ErrorBusinessMessages.STOCK_IN367.getCode(),ErrorBusinessMessages.STOCK_IN367.getMessage());
		        		}
		        		
		        		//Validar si el elemento es del tipo solicitado
		        		if(isPrepaid){
		        			if(!warehouseElementSerialized.getSerialized().getElement().getElementType().getElementModel().getIsPrepaidModel().equals(CodesBusinessEntityEnum.ELEMENT_MODEL_IS_PREPAID.getCodeEntity())){
		        				throw new BusinessException(ErrorBusinessMessages.STOCK_IN436.getCode(),ErrorBusinessMessages.STOCK_IN436.getMessage());
		            		}
		        		}else{
		        			if(!warehouseElementSerialized.getSerialized().getElement().getElementType().getElementModel().getIsPrepaidModel().equals(CodesBusinessEntityEnum.ELEMENT_MODEL_IS_NOT_PREPAID.getCodeEntity())){
		        				throw new BusinessException(ErrorBusinessMessages.STOCK_IN437.getCode(),ErrorBusinessMessages.STOCK_IN437.getMessage());
		            		}
		        		}
		        		
		        		
		        		serialized = warehouseElementSerialized.getSerialized();
		        		if(serialized.getSerialized()!=null){
		        			linkedSerialized = serialized.getSerialized();
		        		}else{
		        			List<Serialized> listElementLinked = daoSerialized.getLinkedSerializedBySerializedId(serialized.getElementId());
		        			if(listElementLinked.size()>0){
		        				linkedSerialized= serialized;
		        				serialized = listElementLinked.get(0);
		        			}
		        		}
		        	}
		        	
		        	
		        	//3. Consulta el tipo de elemento en el control de cantidades para la remisión
		        	this.businessRefQuantityControlItem.generateRefQuantiyControls( UtilsBusiness.copyObject(ElementVO.class, serialized.getElement()) , UtilsBusiness.copyObject(ReferenceVO.class, ref),1D);
		        	//En caso que tenga vinculado lo agrega
		        	if( linkedSerialized != null){
		        		//Como es un elemento serializado el vinculado se pone por defecto cantidad 1
		        		this.businessRefQuantityControlItem.generateRefQuantiyControls( UtilsBusiness.copyObject(ElementVO.class, linkedSerialized.getElement()) , UtilsBusiness.copyObject(ReferenceVO.class, ref),1D);
		        	}
		        		
		        	//5. Se agrega el item a la remisión
		        	ReferenceElementItemVO refElementVO = new ReferenceElementItemVO();
		        	ItemStatus itemStatus = this.businessItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_CREATED.getCodeEntity());
		        	refElementVO.setItemStatus(itemStatus);
		        	refElementVO.setElement(serialized.getElement());
		        	refElementVO.setReference(ref);
		        	refElementVO.setRefQuantity(new Double(1));
		        	this.createReferenceElementItem(refElementVO);
		        	
		        	//6. Actualizar el estado de la remisión
		        	if( !ref.getReferenceStatus().getRefStatusCode().equals( CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() ) ){
		        		//Crea el registro en las modificaciones de remision
		                this.businessReferenceModification.createReferenceModification( ref.getId() , CodesBusinessEntityEnum.REFERENCE_MODIFICATION_TYPE_CREATED.getCodeEntity(), user.getId());
		        	}
		        	ReferenceStatus refStatus = this.businessRefStatus.getReferenceStatusByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity());
		        	ref.setReferenceStatus(refStatus);
		        	this.businessRef.updateReference(UtilsBusiness.copyObject(ReferenceVO.class, ref));
		        	//7. Mover el elemento
		        	//Se obtiene la bodega de transito de origen
		        	if( ref != null && ( ref.getSourceTransitWh() == null || ref.getSourceTransitWh().getId().longValue() <= 0 ) ){
		        		//No se encontro la bodega de transito origen
						Object[] params = {ref.getWarehouseBySourceWh().getWhCode()};
						throw new BusinessException(ErrorBusinessMessages.STOCK_IN384.getCode(), ErrorBusinessMessages.STOCK_IN384.getMessage(params));
		        	}
		        	
		        	//Determina si es bodega de talle
		        	String movementTypeCodeE = getEntryTypeCodeByReference( ref, isBetweenDealers);
		        	String movementTypeCodeS = getExitTypeCodeByReference( ref, isBetweenDealers);
		
		        	//Si no se encontró causal de movimiento
		        	if(movementTypeCodeE==null || movementTypeCodeS== null){
		        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN380.getCode(),ErrorBusinessMessages.STOCK_IN380.getMessage());
		        	}
		        	/*ElementMovementDTO dtoMovement = null;
		        	dtoMovement = new ElementMovementDTO(ref.getWarehouseBySourceWh().getId(), ref.getSourceTransitWh().getId(), serialized.getElementId(), movementTypeCodeE, movementTypeCodeS, ref.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), serialized.getSerialCode(), null, false, null,itemStatus); 
		        	businessWhElement.moveElementToWareHouse(dtoMovement);*/
		
		        	MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementTypeCodeE, movementTypeCodeS);
					MovementElementDTO dtoMovement = new MovementElementDTO(user, 
							UtilsBusiness.copyObject(WarehouseVO.class,  ref.getWarehouseBySourceWh()), 
							UtilsBusiness.copyObject(WarehouseVO.class, ref.getSourceTransitWh()), 
							serialized, 
							ref.getId(), 
							CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), 
							dtoGenerics.getMovTypeCodeE(), 
							dtoGenerics.getMovTypeCodeS(),
							dtoGenerics.getRecordStatusU(), 
							dtoGenerics.getRecordStatusH(), 
							false, 
							CodesBusinessEntityEnum.PROCESS_CODE_IBS_REFERENCE.getCodeEntity(),
							dtoGenerics.getMovConfigStatusPending(),
							dtoGenerics.getMovConfigStatusNoConfig());
					businessMovementElement.moveSerializedElementBetweenWarehouse(dtoMovement);
		}else{				 
				Object params[] = {ref.getId().toString()};		
				List<String> paramsList = new ArrayList<String>();
				paramsList.add(ref.getId().toString());
				throw new BusinessException(	ErrorBusinessMessages.STOCK_IN_ADD_ELE.getCode(), 
					 		                    ErrorBusinessMessages.STOCK_IN_ADD_ELE.getMessage(params),
					 		                    paramsList
					 		                     );

				} 			
        	
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación addElementSerialized/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina addElementSerialized/ReferenceBusinessBean ==");
        }
		
	}
	
	@Override
	public void addElementSerialized(String serialCode, 
			Long referenceID, Long userId) throws BusinessException {
		log.debug("== Inicia addElementSerialized/ReferenceBusinessBean ==");
	 	UtilsBusiness.assertNotNull(serialCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(referenceID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        try {
        	
        	//Consulto el usuario 
        	User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
        	
        	//1) Consulta la remisión
        	Reference ref = this.daoRef.getReferenceByID(referenceID);
        	boolean isPrepaid = ref.getIsPrepaidRef().equals(CodesBusinessEntityEnum.REFERENCE_PREPAID.getCodeEntity());
            boolean isBetweenDealers =  this.isReferenceBetweenCompany(ref);
        	
        	Serialized serialized = null;
        	Serialized linkedSerialized = null;

        	
        	WarehouseElement warehouseElementSerialized = daoWarehouseElement.getWarehouseElementBySerialActive(serialCode,user.getCountry().getId());
        	if(warehouseElementSerialized == null){
        		Object params[] = {serialCode};
				List<String> paramsList = new ArrayList<String>();
				paramsList.add( serialCode);
				//Verifica que el elemento con serial digita exista en la bodega
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN404.getCode(), ErrorBusinessMessages.STOCK_IN404.getMessage(params),paramsList);
        	}else{
        		
        		//Verifica si el elemento ya se agrego a la remisión
        		List<ReferenceElementItem> listaReferenceItems = daoReferenceElementItem.getReferenceElementsByReferenceIdAndSerial(referenceID, serialCode);
        		if ( listaReferenceItems.size() > 0 ){
        			throw new BusinessException(ErrorBusinessMessages.STOCK_IN442.getCode(),ErrorBusinessMessages.STOCK_IN442.getMessage());
        		}
        		
        		//Verifica que el elemento adicionado exista en la bodega origen
        		if( !warehouseElementSerialized.getWarehouseId().getId().equals( ref.getWarehouseBySourceWh().getId() ) ){
        			throw new BusinessException(ErrorBusinessMessages.STOCK_IN367.getCode(),ErrorBusinessMessages.STOCK_IN367.getMessage());
        		}
        		
        		//Validar si el elemento es del tipo solicitado
        		if(isPrepaid){
        			if(!warehouseElementSerialized.getSerialized().getElement().getElementType().getElementModel().getIsPrepaidModel().equals(CodesBusinessEntityEnum.ELEMENT_MODEL_IS_PREPAID.getCodeEntity())){
        				throw new BusinessException(ErrorBusinessMessages.STOCK_IN436.getCode(),ErrorBusinessMessages.STOCK_IN436.getMessage());
            		}
        		}else{
        			if(!warehouseElementSerialized.getSerialized().getElement().getElementType().getElementModel().getIsPrepaidModel().equals(CodesBusinessEntityEnum.ELEMENT_MODEL_IS_NOT_PREPAID.getCodeEntity())){
        				throw new BusinessException(ErrorBusinessMessages.STOCK_IN437.getCode(),ErrorBusinessMessages.STOCK_IN437.getMessage());
            		}
        		}
        		
        		
        		serialized = warehouseElementSerialized.getSerialized();
        		if(serialized.getSerialized()!=null){
        			linkedSerialized = serialized.getSerialized();
        		}else{
        			List<Serialized> listElementLinked = daoSerialized.getLinkedSerializedBySerializedId(serialized.getElementId());
        			if(listElementLinked.size()>0){
        				linkedSerialized= serialized;
        				serialized = listElementLinked.get(0);
        			}
        		}
        	}
        	
        	
        	//3. Consulta el tipo de elemento en el control de cantidades para la remisión
        	this.businessRefQuantityControlItem.generateRefQuantiyControls( UtilsBusiness.copyObject(ElementVO.class, serialized.getElement()) , UtilsBusiness.copyObject(ReferenceVO.class, ref),1D);
        	//En caso que tenga vinculado lo agrega
        	if( linkedSerialized != null){
        		//Como es un elemento serializado el vinculado se pone por defecto cantidad 1
        		this.businessRefQuantityControlItem.generateRefQuantiyControls( UtilsBusiness.copyObject(ElementVO.class, linkedSerialized.getElement()) , UtilsBusiness.copyObject(ReferenceVO.class, ref),1D);
        	}
        		
        	//5. Se agrega el item a la remisión
        	ReferenceElementItemVO refElementVO = new ReferenceElementItemVO();
        	ItemStatus itemStatus = this.businessItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_CREATED.getCodeEntity());
        	refElementVO.setItemStatus(itemStatus);
        	refElementVO.setElement(serialized.getElement());
        	refElementVO.setReference(ref);
        	refElementVO.setRefQuantity(new Double(1));
        	this.createReferenceElementItem(refElementVO);
        	
        	//6. Actualizar el estado de la remisión
        	if( !ref.getReferenceStatus().getRefStatusCode().equals( CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() ) ){
        		//Crea el registro en las modificaciones de remision
                this.businessReferenceModification.createReferenceModification( ref.getId() , CodesBusinessEntityEnum.REFERENCE_MODIFICATION_TYPE_CREATED.getCodeEntity(), user.getId());
        	}
        	ReferenceStatus refStatus = this.businessRefStatus.getReferenceStatusByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity());
        	ref.setReferenceStatus(refStatus);
        	this.businessRef.updateReference(UtilsBusiness.copyObject(ReferenceVO.class, ref));
        	//7. Mover el elemento
        	//Se obtiene la bodega de transito de origen
        	if( ref != null && ( ref.getSourceTransitWh() == null || ref.getSourceTransitWh().getId().longValue() <= 0 ) ){
        		//No se encontro la bodega de transito origen
				Object[] params = {ref.getWarehouseBySourceWh().getWhCode()};
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN384.getCode(), ErrorBusinessMessages.STOCK_IN384.getMessage(params));
        	}
        	
        	//Determina si es bodega de talle
        	String movementTypeCodeE = getEntryTypeCodeByReference( ref, isBetweenDealers);
        	String movementTypeCodeS = getExitTypeCodeByReference( ref, isBetweenDealers);

        	//Si no se encontró causal de movimiento
        	if(movementTypeCodeE==null || movementTypeCodeS== null){
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN380.getCode(),ErrorBusinessMessages.STOCK_IN380.getMessage());
        	}
        	/*ElementMovementDTO dtoMovement = null;
        	dtoMovement = new ElementMovementDTO(ref.getWarehouseBySourceWh().getId(), ref.getSourceTransitWh().getId(), serialized.getElementId(), movementTypeCodeE, movementTypeCodeS, ref.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), serialized.getSerialCode(), null, false, null,itemStatus); 
        	businessWhElement.moveElementToWareHouse(dtoMovement);*/

        	MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementTypeCodeE, movementTypeCodeS);
			MovementElementDTO dtoMovement = new MovementElementDTO(user, 
					UtilsBusiness.copyObject(WarehouseVO.class,  ref.getWarehouseBySourceWh()), 
					UtilsBusiness.copyObject(WarehouseVO.class, ref.getSourceTransitWh()), 
					serialized, 
					ref.getId(), 
					CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), 
					dtoGenerics.getMovTypeCodeE(), 
					dtoGenerics.getMovTypeCodeS(),
					dtoGenerics.getRecordStatusU(), 
					dtoGenerics.getRecordStatusH(), 
					false, 
					CodesBusinessEntityEnum.PROCESS_CODE_IBS_REFERENCE.getCodeEntity(),
					dtoGenerics.getMovConfigStatusPending(),
					dtoGenerics.getMovConfigStatusNoConfig());
			businessMovementElement.moveSerializedElementBetweenWarehouse(dtoMovement);
        	
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación addElementSerialized/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina addElementSerialized/ReferenceBusinessBean ==");
        }
		
	}

	@Override
	public void addElementSerialized(	String serialCode, 
										String serialCodeLinked, 
										Long referenceID, 
										User user
	) throws BusinessException {
		log.debug("== Inicia addElementSerialized/ReferenceBusinessBean ==");
	 	UtilsBusiness.assertNotNull(serialCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(referenceID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        try {
        	
        	//Consulto el usuario 
        	//User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
        	
        	//1) Consulta la remisión
        	Reference ref = this.daoRef.getReferenceByID(referenceID);
        	
        	//2 Consulta el estado de la remision /*ialessan IN317430 - Inconsistencia en remisiones*/
			//Valida que la remision este en confirmado parcial o enviada
			if(                                                                                        //estado En creacion
				   ref.getReferenceStatus().getRefStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity()) 
				                                                                                      // estado Creada
				   ||
				   ref.getReferenceStatus().getRefStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() ) ){

        	
        	
				        	boolean isPrepaid = ref.getIsPrepaidRef().equals(CodesBusinessEntityEnum.REFERENCE_PREPAID.getCodeEntity());
				            boolean isBetweenDealers =  this.isReferenceBetweenCompany(ref);
				        	
				        	Serialized serialized = null;
				        	Serialized linkedSerialized = null;
				
				        	
				        	WarehouseElement warehouseElementSerialized = daoWarehouseElement.getWarehouseElementBySerialActive(serialCode,user.getCountry().getId());
				        	if(warehouseElementSerialized == null){
				        		Object params[] = {serialCode};
								List<String> paramsList = new ArrayList<String>();
								paramsList.add( serialCode);
								//Verifica que el elemento con serial digita exista en la bodega
								throw new BusinessException(ErrorBusinessMessages.STOCK_IN404.getCode(), ErrorBusinessMessages.STOCK_IN404.getMessage(params),paramsList);
				        	}else{
				        		
				        		//Verifica si el elemento ya se agrego a la remisión
				        		List<ReferenceElementItem> listaReferenceItems = daoReferenceElementItem.getReferenceElementsByReferenceIdAndSerial(ref.getId(), serialCode);
				        		if ( listaReferenceItems.size() > 0 ){
				        			throw new BusinessException(ErrorBusinessMessages.STOCK_IN442.getCode(),ErrorBusinessMessages.STOCK_IN442.getMessage());
				        		}
				        		
				        		//Verifica que el elemento adicionado exista en la bodega origen
				        		if( !warehouseElementSerialized.getWarehouseId().getId().equals( ref.getWarehouseBySourceWh().getId() ) ){
				        			throw new BusinessException(ErrorBusinessMessages.STOCK_IN367.getCode(),ErrorBusinessMessages.STOCK_IN367.getMessage());
				        		}
				        		
				        		//Validar si el elemento es del tipo solicitado
				        		if(isPrepaid){
				        			if(!warehouseElementSerialized.getSerialized().getElement().getElementType().getElementModel().getIsPrepaidModel().equals(CodesBusinessEntityEnum.ELEMENT_MODEL_IS_PREPAID.getCodeEntity())){
				        				throw new BusinessException(ErrorBusinessMessages.STOCK_IN436.getCode(),ErrorBusinessMessages.STOCK_IN436.getMessage());
				            		}
				        		}else{
				        			if(!warehouseElementSerialized.getSerialized().getElement().getElementType().getElementModel().getIsPrepaidModel().equals(CodesBusinessEntityEnum.ELEMENT_MODEL_IS_NOT_PREPAID.getCodeEntity())){
				        				throw new BusinessException(ErrorBusinessMessages.STOCK_IN437.getCode(),ErrorBusinessMessages.STOCK_IN437.getMessage());
				            		}
				        		}
				        		
				        		
				        		serialized = warehouseElementSerialized.getSerialized();
				        		if(serialized.getSerialized()!=null){
				        			linkedSerialized = serialized.getSerialized();
				        		}else{
				        			List<Serialized> listElementLinked = daoSerialized.getLinkedSerializedBySerializedId(serialized.getElementId());
				        			if(listElementLinked.size()>0){
				        				linkedSerialized= serialized;
				        				serialized = listElementLinked.get(0);
				        			}
				        		}
				        	}
				        	
				        	//Validar los seriales ingresados por el usuario con respecto a lo encontrado en el sistema
				        	this.validaSerialLinked(linkedSerialized, serialized, serialCodeLinked);
				        	
				        	
				        	//3. Consulta el tipo de elemento en el control de cantidades para la remisión
				        	this.businessRefQuantityControlItem.generateRefQuantiyControls( UtilsBusiness.copyObject(ElementVO.class, serialized.getElement()) , UtilsBusiness.copyObject(ReferenceVO.class, ref),1D);
				        	//En caso que tenga vinculado lo agrega
				        	if( linkedSerialized != null){
				        		//Como es un elemento serializado el vinculado se pone por defecto cantidad 1
				        		this.businessRefQuantityControlItem.generateRefQuantiyControls( UtilsBusiness.copyObject(ElementVO.class, linkedSerialized.getElement()) , UtilsBusiness.copyObject(ReferenceVO.class, ref),1D);
				        	}
				        		
				        	//5. Se agrega el item a la remisión
				        	ReferenceElementItemVO refElementVO = new ReferenceElementItemVO();
				        	ItemStatus itemStatus = this.businessItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_CREATED.getCodeEntity());
				        	refElementVO.setItemStatus(itemStatus);
				        	refElementVO.setElement(serialized.getElement());
				        	refElementVO.setReference(ref);
				        	refElementVO.setRefQuantity(new Double(1));
				        	this.createReferenceElementItem(refElementVO);
				        	
				        	//6. Actualizar el estado de la remisión
				        	if( !ref.getReferenceStatus().getRefStatusCode().equals( CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() ) ){
				        		//Crea el registro en las modificaciones de remision
				                this.businessReferenceModification.createReferenceModification( ref.getId() , CodesBusinessEntityEnum.REFERENCE_MODIFICATION_TYPE_CREATED.getCodeEntity(), user.getId());
				        	}
				        	ReferenceStatus refStatus = this.businessRefStatus.getReferenceStatusByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity());
				        	ref.setReferenceStatus(refStatus);
				        	this.businessRef.updateReference(UtilsBusiness.copyObject(ReferenceVO.class, ref));
				        	//7. Mover el elemento
				        	//Se obtiene la bodega de transito de origen
				        	if( ref != null && ( ref.getSourceTransitWh() == null || ref.getSourceTransitWh().getId().longValue() <= 0 ) ){
				        		//No se encontro la bodega de transito origen
								Object[] params = {ref.getWarehouseBySourceWh().getWhCode()};
								throw new BusinessException(ErrorBusinessMessages.STOCK_IN384.getCode(), ErrorBusinessMessages.STOCK_IN384.getMessage(params));
				        	}
				        	
				        	//Determina si es bodega de talle
				        	String movementTypeCodeE = getEntryTypeCodeByReference( ref, isBetweenDealers);
				        	String movementTypeCodeS = getExitTypeCodeByReference( ref, isBetweenDealers);
				
				        	//Si no se encontró causal de movimiento
				        	if(movementTypeCodeE==null || movementTypeCodeS== null){
				        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN380.getCode(),ErrorBusinessMessages.STOCK_IN380.getMessage());
				        	}
				        	/*ElementMovementDTO dtoMovement = null;
				        	dtoMovement = new ElementMovementDTO(ref.getWarehouseBySourceWh().getId(), ref.getSourceTransitWh().getId(), serialized.getElementId(), movementTypeCodeE, movementTypeCodeS, ref.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), serialized.getSerialCode(), null, false, null,itemStatus); 
				        	businessWhElement.moveElementToWareHouse(dtoMovement);*/
				
				        	MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementTypeCodeE, movementTypeCodeS);
							MovementElementDTO dtoMovement = new MovementElementDTO(user, 
									UtilsBusiness.copyObject(WarehouseVO.class,  ref.getWarehouseBySourceWh()), 
									UtilsBusiness.copyObject(WarehouseVO.class, ref.getSourceTransitWh()), 
									serialized, 
									ref.getId(), 
									CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), 
									dtoGenerics.getMovTypeCodeE(), 
									dtoGenerics.getMovTypeCodeS(),
									dtoGenerics.getRecordStatusU(), 
									dtoGenerics.getRecordStatusH(), 
									false, 
									CodesBusinessEntityEnum.PROCESS_CODE_IBS_REFERENCE.getCodeEntity(),
									dtoGenerics.getMovConfigStatusPending(),
									dtoGenerics.getMovConfigStatusNoConfig());
							businessMovementElement.moveSerializedElementBetweenWarehouse(dtoMovement);
        	}else{				 
				Object params[] = {ref.getId().toString()};		
				List<String> paramsList = new ArrayList<String>();
				paramsList.add(ref.getId().toString());
				throw new BusinessException(	ErrorBusinessMessages.STOCK_IN_ADD_ELE.getCode(), 
					 		                    ErrorBusinessMessages.STOCK_IN_ADD_ELE.getMessage(params),
					 		                    paramsList
        			 		                     );
        	}
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación addElementSerialized/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina addElementSerialized/ReferenceBusinessBean ==");
        }
		
	}
	
	/**
	 * Metodo encargado de validar el serial vinculado recibido como parametro con el serial 
	 * principal o vinculado encontrado en el sistema 
	 * @throws BusinessException
	 * @author waguilera
	 */
	private void validaSerialLinked(Serialized linkedSerialized, Serialized principalSerialized, String serialCodeLinked) throws BusinessException{
		//Valida si el serial vinculados es el mismo al recibido como parametro
		//en caso de no serlo excepciona
		
		boolean isSpecified = serialCodeLinked != null && !(serialCodeLinked.trim()).equals("");
		List<String> listParams = new ArrayList<String>();
		Object[] params = new Object[1];
		
		if (isSpecified){
			if(linkedSerialized == null){
				listParams.add("El elemento que desea cargar a la remisión no esta vinculado con el serial "+serialCodeLinked);
				params[0] = "El elemento que desea cargar a la remisión no esta vinculado con el serial "+serialCodeLinked;
				throw new BusinessException(ErrorBusinessMessages.ERROR_VALIDATION.getCode(), ErrorBusinessMessages.ERROR_VALIDATION.getMessage(params), listParams);
			}else {
				if(!(serialCodeLinked.equals(principalSerialized.getSerialCode())||serialCodeLinked.equals(linkedSerialized.getSerialCode()))){
					listParams.add("El serial vinculado que se digitó no correponde con el serial vinculado encontrado en el sistema");
					params[0] = "El serial vinculado que se digitó no correponde con el serial vinculado encontrado en el sistema";
					throw new BusinessException(ErrorBusinessMessages.ERROR_VALIDATION.getCode(), ErrorBusinessMessages.ERROR_VALIDATION.getMessage(params), listParams);
				}
			}
		}else{
			if(linkedSerialized != null){
				params[0] = "El elemento que desea cargar está vinculado. Debe digitar el serial vinculado correspondiente";
				listParams.add("El elemento que desea cargar está vinculado. Debe digitar el serial vinculado correspondiente");
				throw new BusinessException(ErrorBusinessMessages.ERROR_VALIDATION.getCode(), ErrorBusinessMessages.ERROR_VALIDATION.getMessage(params), listParams);
			}
		}
	}
	
	

	@Override
	public void addElementNotSerialized(List<ReferenceElementItemVO> listElementNotSerializedToAdd, 
										Long referenceID, 
										Long userId
    ) throws BusinessException{
		log.debug("== Inicia addElementNotSerialized/ReferenceBusinessBean ==");
	 	UtilsBusiness.assertNotNull(listElementNotSerializedToAdd, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(referenceID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        
        try {
        	//1) Consulta
        	
        	//Consulto el usuario 
        	User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
        	
        	Reference reference = this.daoRef.getReferenceByID(referenceID);
        	/*ialessan IN317430 - Inconsistencia en remisiones*/
        	if(                                                                                        //estado En creacion
        			reference.getReferenceStatus().getRefStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity()) 
					                                                                                      // estado Creada
					   ||
					   reference.getReferenceStatus().getRefStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity() ) ){
        	
        	
				        	boolean isBetweenDealers =  this.isReferenceBetweenCompany(reference);
				        	
				        	ItemStatus itemStatus = this.businessItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_CREATED.getCodeEntity());
				        	for(ReferenceElementItemVO item: listElementNotSerializedToAdd){
				        		item.setRefQuantityPartial(item.getRefQuantity());
				        	}
				        	listElementNotSerializedToAdd = this.addElementNotSerializedToReference(listElementNotSerializedToAdd, reference, itemStatus, null, reference.getWarehouseBySourceWh().getId());
				        	
				        	for(ReferenceElementItemVO refElementVO: listElementNotSerializedToAdd){
				        		
				        		
				        		//Agrega control de cantidades si no lo tiene
				        		this.businessRefQuantityControlItem.generateRefQuantiyControls(UtilsBusiness.copyObject(ElementVO.class, refElementVO.getElement()) , UtilsBusiness.copyObject(ReferenceVO.class, reference),refElementVO.getRefQuantityPartial());
				        		
				            	
				            	
				            	
				            	//Actualizar el estado de la remisión
				            	if(!reference.getReferenceStatus().getRefStatusCode().equals(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity())){
				            		//Crea el registro en las modificaciones de remision
				                    this.businessReferenceModification.createReferenceModification(reference.getId() , CodesBusinessEntityEnum.REFERENCE_MODIFICATION_TYPE_CREATED.getCodeEntity(), user.getId());
				            	}
				            	
				            	ReferenceStatus refStatus = this.businessRefStatus.getReferenceStatusByCode(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity());
				            	reference.setReferenceStatus(refStatus);
				            	this.businessRef.updateReference(UtilsBusiness.copyObject(ReferenceVO.class, reference));
				            	//7. Mover el elemento
				            	//Se obtiene la bodega de transito de origen
				            	if(reference != null && (reference.getSourceTransitWh() == null || reference.getSourceTransitWh().getId().longValue() <= 0)){
				            		//No se encontro la bodega de transito origen
				    				Object[] params = {reference.getWarehouseBySourceWh().getWhCode()};
				    				throw new BusinessException(ErrorBusinessMessages.STOCK_IN384.getCode(), ErrorBusinessMessages.STOCK_IN384.getMessage(params));
				            	}
				            	
				            	//Determina si es bodega de taller
				            	String movementTypeCodeE = getEntryTypeCodeByReference(reference, isBetweenDealers);
				            	String movementTypeCodeS = getExitTypeCodeByReference(reference, isBetweenDealers);
				
				            	//Si no se encontró causal de movimiento
				            	if(movementTypeCodeE==null || movementTypeCodeS== null){
				            		throw new BusinessException(ErrorBusinessMessages.STOCK_IN380.getCode(),ErrorBusinessMessages.STOCK_IN380.getMessage());
				            	}
				            	
				            	//Realiza el movimiento de los elementos
				            	/*ElementMovementDTO dtoMovement = new ElementMovementDTO(reference.getWarehouseBySourceWh().getId(), reference.getSourceTransitWh().getId(), refElementVO.getElement().getElementType().getId(), refElementVO.getRefQuantityPartial(), movementTypeCodeE, movementTypeCodeS, false,  reference.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity());
				            	businessWhElement.moveNotSerializedElementBetweenWareHouses(dtoMovement);*/
				            	
				            	
				            	MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementTypeCodeE, movementTypeCodeS);
								MovementElementDTO dtoMov = new MovementElementDTO(user,
										UtilsBusiness.copyObject(WarehouseVO.class, reference.getWarehouseBySourceWh()), 
										UtilsBusiness.copyObject(WarehouseVO.class,  reference.getSourceTransitWh()), 
										null, 
										refElementVO.getElement().getElementType().getId(), 
										null,
										reference.getId(), 
										CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity(), 
										dtoGenerics.getMovTypeCodeE(), 
										dtoGenerics.getMovTypeCodeS(), 
										dtoGenerics.getRecordStatusU(),
										dtoGenerics.getRecordStatusH(),
										refElementVO.getRefQuantityPartial(),
										dtoGenerics.getMovConfigStatusPending(),
										dtoGenerics.getMovConfigStatusNoConfig());
								businessMovementElement.moveNotSerializedElementBetweenWarehouse(dtoMov);
				        	}
        	}else{				 
				Object params[] = {reference.getId().toString()};		
				List<String> paramsList = new ArrayList<String>();
				paramsList.add(reference.getId().toString());
				throw new BusinessException(	ErrorBusinessMessages.STOCK_IN_ADD_ELE.getCode(), 
					 		                    ErrorBusinessMessages.STOCK_IN_ADD_ELE.getMessage(params),
					 		                    paramsList
					 		                     );

				}     	
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación addElementNotSerialized/ReferenceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina addElementNotSerialized/ReferenceBusinessBean ==");
        }
		
	}


	@Override
	public ReferenceElementItemsResponse getElementNotSerializedFromWarehouse(RequestCollectionInfo requestCollInfo,Long referenceID,
			boolean isPrepaid, Long elementTypeId, Long modelId) throws BusinessException {
		log.debug("== Inicia getElementNotSerializedFromWarehouse/ReferenceElementItemBusinessBean ==");
		UtilsBusiness.assertNotNull(referenceID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(isPrepaid, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(isPrepaid, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			if( referenceID == null || referenceID.longValue() <= 0 ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			Reference ref = daoRef.getReferenceByID(referenceID);
			String isPrepaidModel = "";
			if(isPrepaid){
				isPrepaidModel = CodesBusinessEntityEnum.ELEMENT_MODEL_IS_PREPAID.getCodeEntity();
			}else{
				isPrepaidModel = CodesBusinessEntityEnum.ELEMENT_MODEL_IS_NOT_PREPAID.getCodeEntity();
			}
			
			ReferenceElementItemsResponse response = this.daoReferenceElementItem.getNotSerizalizedElementByWarehouseIdAndLastStatus(ref.getWarehouseBySourceWh().getId(), elementTypeId, isPrepaidModel, requestCollInfo, modelId);
			return response;
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getElementNotSerializedFromWarehouse/ReferenceElementItemBusinessBean == " + ex.getMessage());
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementNotSerializedFromWarehouse/ReferenceElementItemBusinessBean ==");
        }
		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#getReferenceElementItemByReferenceIdAndElementId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public ReferenceElementItemVO getReferenceElementItemByReferenceIdAndElementId(
			Long referenceId, Long elementId) throws BusinessException {
		log.debug("== Inicia getReferenceElementItemByReferenceIdAndElementId/ReferenceElementItemBusinessBean ==");
        UtilsBusiness.assertNotNull(referenceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(elementId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReferenceElementItem objPojo = daoReferenceElementItem.getReferenceElementItemByReferenceIdAndElementId(referenceId, elementId);
            return UtilsBusiness.copyObject(ReferenceElementItemVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceElementItemByReferenceIdAndElementId/ReferenceElementItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceElementItemByReferenceIdAndElementId/ReferenceElementItemBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal#addElementNotSerializedToReference(java.util.List, co.com.directv.sdii.model.pojo.Reference, co.com.directv.sdii.model.pojo.ItemStatus, java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<ReferenceElementItemVO> addElementNotSerializedToReference(
			List<ReferenceElementItemVO> listElementNotSerializedToAdd,
			Reference reference, ItemStatus itemStatus, Long globalElementTypeId, Long sourceWh) throws BusinessException {
		//Buscar el elemento en el sistema
		List <ReferenceElementItemVO> listGenerate = new ArrayList<ReferenceElementItemVO>();
		try{
			Long elementTypeId = null;
			
			for(ReferenceElementItemVO refElementVO: listElementNotSerializedToAdd){
				
				if(globalElementTypeId != null && globalElementTypeId.longValue() > 0) {
					elementTypeId = globalElementTypeId;
				} else {
					elementTypeId = refElementVO.getElement().getElementType().getId();
				}
				NotSerialized notSerialized = daoNotSerialized.getNotSerializedbyElementTypeID(elementTypeId, reference.getCountryCodeId().getId());

				if(notSerialized==null){
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN367.getCode(),ErrorBusinessMessages.STOCK_IN367.getMessage());
				}
				Double whQuantityElement = this.daoWarehouseElement.countWHElementByActualElementNotSerialized(sourceWh, reference.getWarehouseBySourceWh().getCountry().getId(), null, null, notSerialized.getElement().getElementType().getTypeElementCode(), CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity(), null, null);
				if(refElementVO.getRefQuantity().doubleValue()> whQuantityElement.doubleValue()){
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN449.getCode(), ErrorBusinessMessages.STOCK_IN449.getMessage());
				}

				ReferenceElementItem relExist = daoReferenceElementItem.getReferenceElementItemByElementIdAndReferenceId(notSerialized.getElementId(), reference.getId());
				if(relExist!=null){
					//Verifico si el elemento ya existe agregado a la remisión y si es asi lo actualizo
					relExist.setRefQuantity(relExist.getRefQuantity().doubleValue()+refElementVO.getRefQuantity().doubleValue());
					relExist.setItemStatus(itemStatus);
					daoReferenceElementItem.updateReferenceElementItem(relExist);
					ReferenceElementItemVO referenceElementItemVO = UtilsBusiness.copyObject(ReferenceElementItemVO.class, relExist);
					referenceElementItemVO.setRefQuantityPartial(refElementVO.getRefQuantityPartial());
					listGenerate.add(referenceElementItemVO);
				}else{
					//Se agrega el elemento a la remisión
					refElementVO.setItemStatus(itemStatus);
					refElementVO.setElement(notSerialized.getElement());
					refElementVO.setReference(reference);
					this.createReferenceElementItem(refElementVO);
					refElementVO.setRefQuantityPartial(refElementVO.getRefQuantity());
					listGenerate.add(refElementVO);
				}
			}
			return listGenerate;
		}catch (Throwable ex) {
			throw this.manageException(ex);
		}finally{
			 log.debug("== Termina addElementNotSerializedToReference/ReferenceElementItemBusinessBean ==");
		}

	}


	@Override
	public ReferenceElementItemsResponse getReferenceElementItemsByReferenceIDSerAndNotSerPending(
			Long refID, RequestCollectionInfo requestCollInfo, String serialCode, boolean isSerialized)
			throws BusinessException {
		log.debug("== Inicia getReferenceElementItemsByReferenceIDSerialized/ReferenceElementItemBusinessBean ==");
		try {
			if(refID == null || refID.longValue() <= 0){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			String stringIsSerialized = isSerialized ? CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity() : CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity();
			ReferenceElementItemsResponse response = this.daoReferenceElementItem.getReferenceElementItemsByReferenceIDSerAndNotSerPending(refID,requestCollInfo,serialCode, isSerialized);
			
			if(response != null && response.getReferenceElementItemsVO() != null && !response.getReferenceElementItemsVO().isEmpty()){
				//Para el caso de los elementos no serializados se consulta la cantidad en bodega
				Reference reference = this.daoRef.getReferenceByID(refID);
				if(reference != null){
					for(ReferenceElementItemVO tempRefElm : response.getReferenceElementItemsVO()){
						if( !isSerialized ){
							Double whQuantity = this.daoWarehouseElement.countWHElementByActualElementNotSerialized(reference.getWarehouseBySourceWh().getId(), reference.getWarehouseBySourceWh().getCountry().getId(), null, null, tempRefElm.getElement().getElementType().getTypeElementCode(), stringIsSerialized, null, null);
							tempRefElm.setWarehouseQuantity(whQuantity);
							
							//Cargar la cantidad confirmada
							tempRefElm.setRefConfirmationSum(daoRefConfirmation.countElementConfirmedQuatity(tempRefElm.getId()));
						}
						//Confirmaciones
						tempRefElm.setRefConfirmations(UtilsBusiness.convertList(daoRefConfirmation.getConfirmationsByReferenceElementId( tempRefElm.getId() ), RefConfirmationVO.class));
					}
				}
			}
			return response;
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceElementItemsByReferenceIDSerialized/ReferenceElementItemBusinessBean == " + ex.getMessage());
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceElementItemsByReferenceIDSerialized/ReferenceElementItemBusinessBean ==");
        }
	}
	
}
