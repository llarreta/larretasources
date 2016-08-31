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

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.stock.DeliveryBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Delivery;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.DeliveryVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.persistence.dao.stock.DeliveryDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceElementItemDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD Delivery
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.DeliveryDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.DeliveryBusinessBeanLocal
 */
@Stateless(name="DeliveryBusinessBeanLocal",mappedName="ejb/DeliveryBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DeliveryBusinessBean extends BusinessBase implements DeliveryBusinessBeanLocal {

    @EJB(name="DeliveryDAOLocal", beanInterface=DeliveryDAOLocal.class)
    private DeliveryDAOLocal daoDelivery;
    
    @EJB(name="ReferenceElementItemDAOLocal", beanInterface=ReferenceElementItemDAOLocal.class)
    private ReferenceElementItemDAOLocal daoReferenceElementItem;
    
    private final static Logger log = UtilsBusiness.getLog4J(DeliveryBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.DeliveryBusinessBeanLocal#getAllDeliverys()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DeliveryVO> getAllDeliverys() throws BusinessException {
        log.debug("== Inicia getAllDeliverys/DeliveryBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoDelivery.getAllDeliverys(), DeliveryVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDeliverys/DeliveryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDeliverys/DeliveryBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.DeliveryBusinessBeanLocal#getDeliverysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DeliveryVO getDeliveryByID(Long id) throws BusinessException {
        log.debug("== Inicia getDeliveryByID/DeliveryBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Delivery objPojo = daoDelivery.getDeliveryByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(DeliveryVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDeliveryByID/DeliveryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDeliveryByID/DeliveryBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.DeliveryBusinessBeanLocal#createDelivery(co.com.directv.sdii.model.vo.DeliveryVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createDelivery(DeliveryVO obj) throws BusinessException {
        log.debug("== Inicia createDelivery/DeliveryBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Delivery objPojo =  UtilsBusiness.copyObject(Delivery.class, obj);
            daoDelivery.createDelivery(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createDelivery/DeliveryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDelivery/DeliveryBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.DeliveryBusinessBeanLocal#updateDelivery(co.com.directv.sdii.model.vo.DeliveryVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDelivery(DeliveryVO obj) throws BusinessException {
        log.debug("== Inicia updateDelivery/DeliveryBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Delivery objPojo =  UtilsBusiness.copyObject(Delivery.class, obj);
            daoDelivery.updateDelivery(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDelivery/DeliveryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDelivery/DeliveryBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.DeliveryBusinessBeanLocal#deleteDelivery(co.com.directv.sdii.model.vo.DeliveryVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteDelivery(DeliveryVO obj) throws BusinessException {
        log.debug("== Inicia deleteDelivery/DeliveryBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Delivery objPojo =  UtilsBusiness.copyObject(Delivery.class, obj);
            daoDelivery.deleteDelivery(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteDelivery/DeliveryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDelivery/DeliveryBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.DeliveryBusinessBeanLocal#getDeliveriesByReferenceID(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DeliveryVO> getDeliveriesByReferenceID(Long referenceId) throws BusinessException{
		 log.debug("== Inicia getDeliveriesByReferenceID/DeliveryBusinessBean ==");
	        try {
	        	List<Object[]> deliveriesPojo = daoDelivery.getDeliveriesByReferenceID(referenceId);
	            List<DeliveryVO> deliveries = null;
	            if(deliveriesPojo != null && deliveriesPojo.size() > 0){
	            	deliveries = new ArrayList<DeliveryVO>();
	            	for (Object[] obj : deliveriesPojo) {
	            		DeliveryVO delivery = UtilsBusiness.copyObject(DeliveryVO.class, (Delivery)obj[1]);
	            		delivery.setRegisterUSer(UtilsBusiness.copyObject(UserVO.class, (User)obj[0]));
	            		deliveries.add(delivery);
	                }
	            }
	            return deliveries;
	        } catch(Throwable ex){
	        	log.error("== Error al tratar de ejecutar la operación getDeliveriesByReferenceID/DeliveryBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getDeliveriesByReferenceID/DeliveryBusinessBean ==");
	        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.DeliveryBusinessBeanLocal#createDeliveries(java.lang.Long,java.util.List<co.com.directv.sdii.model.vo.DeliveryVO>,java.util.List<co.com.directv.sdii.model.vo.SpecialCommentVO>)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createDeliveries(Long referenceId , List<DeliveryVO> deliveries) throws BusinessException{
		log.debug("== Inicia createDeliveries/DeliveryBusinessBean ==");
        try {
        	Double newDeliveriesQuantity = deliveries.get(0).getDeliveryQuantity();
        	Double actualDEeliveriesQuantities = daoDelivery.getTotalDeliveriesByElementIdAndReferenceId(referenceId, deliveries.get(0).getReferenceElementItem().getId());
        	Double referenceItemQuantity = daoReferenceElementItem.getReferenceElementItemQuantityByReferenceAndElement(referenceId, deliveries.get(0).getReferenceElementItem().getId());
        	
        	if( (newDeliveriesQuantity + actualDEeliveriesQuantities) > referenceItemQuantity ){
        		log.error("La cantidad a inngresar en la entrega junto con las existentes supera la cantidad de la remision");
        		throw new BusinessException(ErrorBusinessMessages.REFERENCE_DELIVERIES_GREATER_THAN_MOV_QUANTITY.getCode(), ErrorBusinessMessages.REFERENCE_DELIVERIES_GREATER_THAN_MOV_QUANTITY.getMessage());
        	}
        	if(deliveries.get(0).getDeliveryDate().after(UtilsBusiness.fechaActual()) ){
        		log.error("La fecha de la entrega es superior a la actual");
        		throw new BusinessException(ErrorBusinessMessages.REFERENCE_DELIVERIES_WRONG_DATE.getCode(), ErrorBusinessMessages.REFERENCE_DELIVERIES_WRONG_DATE.getMessage());
        	}
        	for(DeliveryVO vo : deliveries){
        		Delivery delivery = UtilsBusiness.copyObject(Delivery.class, vo);
        		Reference reference = new Reference();
        		reference.setId(referenceId);
        		delivery.setReference(reference);
        		daoDelivery.createDelivery(delivery);
        	}
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createDeliveries/DeliveryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDeliveries/DeliveryBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.DeliveryBusinessBeanLocal#getDeliveriesForDetailsByReferenceID(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DeliveryVO> getDeliveriesForDetailsByReferenceID(Long referenceId) throws BusinessException{
		 log.debug("== Inicia getDeliveriesByReferenceID/DeliveryBusinessBean ==");
	        try {
	        	List<Object[]> deliveriesPojo = daoDelivery.getDeliveriesByReferenceID(referenceId);
	            List<DeliveryVO> deliveries = null;
	            if(deliveriesPojo != null && deliveriesPojo.size() > 0){
	            	deliveries = new ArrayList<DeliveryVO>();
	            	for (Object[] obj : deliveriesPojo) {
	            		Delivery deliveryPojo = (Delivery)obj[1];
	            		Reference reference = new Reference();
	            		reference.setId(deliveryPojo.getReference().getId());
	            		deliveryPojo.setReference(reference);
	            		deliveryPojo.getReferenceElementItem().setReference(reference);
	            		DeliveryVO delivery = UtilsBusiness.copyObject(DeliveryVO.class, deliveryPojo);	            		
	            		delivery.setRegisterUSer(UtilsBusiness.copyObject(UserVO.class, (User)obj[0]));
	            		deliveries.add(delivery);
	                }
	            }
	            return deliveries;
	        } catch(Throwable ex){
	        	log.error("== Error al tratar de ejecutar la operación getDeliveriesByReferenceID/DeliveryBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getDeliveriesByReferenceID/DeliveryBusinessBean ==");
	        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.DeliveryBusinessBeanLocal#getTotalDeliveriesByElementIdAndReferenceId(java.lang.Long,java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Double getTotalDeliveriesByElementIdAndReferenceId(Long referenceId , Long elementId) throws BusinessException{
		log.debug("== Inicia getTotalDeliveriesByElementIdAndReferenceId/DeliveryBusinessBean ==");
        try {
        	return daoDelivery.getTotalDeliveriesByElementIdAndReferenceId(referenceId, elementId);
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getTotalDeliveriesByElementIdAndReferenceId/DeliveryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getTotalDeliveriesByElementIdAndReferenceId/DeliveryBusinessBean ==");
        }
	}
	
}
