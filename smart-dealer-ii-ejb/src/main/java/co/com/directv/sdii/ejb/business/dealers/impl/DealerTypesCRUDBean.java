package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.DealerTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerType;
import co.com.directv.sdii.model.vo.DealerTypeVO;
import co.com.directv.sdii.persistence.dao.dealers.DealerTypesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD DealerType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.DealerTypesDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.DealerTypesCRUDBeanLocal
 */
@Stateless(name="DealerTypesCRUDBeanLocal",mappedName="ejb/DealerTypesCRUDBeanLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerTypesCRUDBean extends BusinessBase implements DealerTypesCRUDBeanLocal {

    @EJB(name="DealerTypesDAOLocal",beanInterface=DealerTypesDAOLocal.class)
    private DealerTypesDAOLocal daoDealerTypes;
    
    @EJB(name="DealersDAOLocal",beanInterface=DealersDAOLocal.class)
    private DealersDAOLocal daoDealers;
    
    private final static Logger log = UtilsBusiness.getLog4J(DealerTypesCRUDBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.dealers.DealerTypesCRUDBeanLocal#getAllDealerTypes()
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<DealerTypeVO> getAllDealerTypes() throws BusinessException {
        log.debug("== Inicia getAllDealerTypes/DealerTypesCRUDBean ==");
        try {
            return UtilsBusiness.convertList(daoDealerTypes.getAllDealerTypes(), DealerTypeVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDealerTypes/DealerTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDealerTypes/CrewStatusCRUDBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.dealers.DealerTypesCRUDBeanLocal#getDealerTypesByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DealerTypeVO getDealerTypesByCode(String code) throws BusinessException {
        log.debug("== Inicia getDealerTypesByCode/DealerTypesCRUDBean ==");

        if (code == null || code.equals("")) {
            throw new BusinessException("Parametro code nulo o vacio");
        }
        try {

            DealerType dealerType = daoDealerTypes.getDealerTypesByCode(code);
            if (dealerType == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(DealerTypeVO.class, dealerType);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerTypesByCode/DealerTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerTypesByCode/CrewStatusCRUDBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.dealers.DealerTypesCRUDBeanLocal#getDealerTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DealerTypeVO getDealerTypesByID(Long id) throws BusinessException {
        log.debug("== Inicia getDealerTypesByCode/DealerTypesCRUDBean ==");
        if (id == null) {
            throw new BusinessException("Parametro id nulo");
        }

        try {
            DealerType dTypes = daoDealerTypes.getDealerTypesByID(id);
            if (dTypes == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(DealerTypeVO.class, dTypes);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerTypesByID/DealerTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerTypesByID/CrewStatusCRUDBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.DealerTypesCRUDBeanLocal#createDealerType(co.com.directv.sdii.model.vo.DealerTypeVO)
	 */
	@Override
	public void createDealerType(DealerTypeVO obj) throws BusinessException {
        log.debug("== Inicia createDealerType/DealerTypesCRUDBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	DealerType dealerType = daoDealerTypes.getDealerTypesByCode(obj.getDealerTypeCode());
        	if(dealerType != null){
        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
            DealerType dealerTypePojo =  UtilsBusiness.copyObject(DealerType.class, obj);
            daoDealerTypes.createDealerType(dealerTypePojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createDealerType/DealerTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerType/CrewStatusCRUDBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.DealerTypesCRUDBeanLocal#updateDealerType(co.com.directv.sdii.model.vo.DealerTypeVO)
	 */
	@Override
	public void updateDealerType(DealerTypeVO obj) throws BusinessException {
        log.debug("== Inicia updateDealerType/DealerTypesCRUDBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getDealerTypeCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        try {
        	//Evitar problemas de integridad referencial
        	DealerType dealerType = daoDealerTypes.getDealerTypesByCode(obj.getDealerTypeCode());
        	if(dealerType != null && dealerType.getId().longValue() != obj.getId().longValue()){
        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
            DealerType dealerTypePojo =  UtilsBusiness.copyObject(DealerType.class, obj);
            daoDealerTypes.updateDealerType(dealerTypePojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDealerType/DealerTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerType/CrewStatusCRUDBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.DealerTypesCRUDBeanLocal#deleteDealerType(co.com.directv.sdii.model.vo.DealerTypeVO)
	 */
	@Override
	public void deleteDealerType(DealerTypeVO obj) throws BusinessException {
        log.debug("== Inicia deleteDealerType/DealerTypesCRUDBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerType dealerTypePojo =  UtilsBusiness.copyObject(DealerType.class, obj);
            
            List<Dealer> dealersBydealerType = daoDealers.getDealersByDealerTypeId(dealerTypePojo.getId());
            
            if(! dealersBydealerType.isEmpty()){
            	String message = "No se puede eliminar el tipo de dealer con id: " + obj.getId() + " porque existen " + dealersBydealerType.size() +  " dealers de ese tipo, primero debe cambiar el tipo de dealer a todas las compañías";
            	log.error(message);
            	throw new BusinessException(ErrorBusinessMessages.DEALER_TYPE_HAS_RELATED_DATA.getCode(), ErrorBusinessMessages.DEALER_TYPE_HAS_RELATED_DATA.getMessage() + message);
            }
            
            daoDealerTypes.deleteDealerType(dealerTypePojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteDealerType/DealerTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerType/CrewStatusCRUDBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.DealerTypesCRUDBeanLocal#getDealerTypesByIsAlloc(java.lang.String)
	 */
	@Override
	public List<DealerTypeVO> getDealerTypesByIsAlloc(String isAlloc)
			throws BusinessException {
		log.debug("== Inicia getDealerTypesByIsAlloc/DealerTypesCRUDBean ==");
        try {
        	List<DealerType> dealerTypesPojo = daoDealerTypes.getDealerTypesByIsAlloc(isAlloc);
            return UtilsBusiness.convertList(dealerTypesPojo, DealerTypeVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerTypesByIsAlloc/DealerTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerTypesByIsAlloc/CrewStatusCRUDBean ==");
        }
	}
}
