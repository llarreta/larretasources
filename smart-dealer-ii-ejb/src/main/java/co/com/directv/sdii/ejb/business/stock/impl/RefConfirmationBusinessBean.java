package co.com.directv.sdii.ejb.business.stock.impl;

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
import co.com.directv.sdii.ejb.business.stock.RefConfirmationBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.RefConfirmation;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.ReferenceStatus;
import co.com.directv.sdii.model.vo.RefConfirmationVO;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.persistence.dao.stock.RefConfirmationDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD RefConfirmation
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.RefConfirmationDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.RefConfirmationBusinessBeanLocal
 */
@Stateless(name="RefConfirmationBusinessBeanLocal",mappedName="ejb/RefConfirmationBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RefConfirmationBusinessBean extends BusinessBase implements RefConfirmationBusinessBeanLocal {

    @EJB(name="RefConfirmationDAOLocal", beanInterface=RefConfirmationDAOLocal.class)
    private RefConfirmationDAOLocal daoRefConfirmation;
    
    @EJB(name="ReferenceElementItemBusinessBeanLocal", beanInterface=ReferenceElementItemBusinessBeanLocal.class)
    private ReferenceElementItemBusinessBeanLocal businessReferenceElementItem;
    
    @EJB(name="ReferenceBusinessBeanLocal", beanInterface=ReferenceBusinessBeanLocal.class)
    private ReferenceBusinessBeanLocal businessReference;
    
    private final static Logger log = UtilsBusiness.getLog4J(RefConfirmationBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.RefConfirmationBusinessBeanLocal#getAllRefConfirmations()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<RefConfirmationVO> getAllRefConfirmations() throws BusinessException {
        log.debug("== Inicia getAllRefConfirmations/RefConfirmationBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoRefConfirmation.getAllRefConfirmations(), RefConfirmationVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllRefConfirmations/RefConfirmationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllRefConfirmations/RefConfirmationBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.RefConfirmationBusinessBeanLocal#getRefConfirmationsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public RefConfirmationVO getRefConfirmationByID(Long id) throws BusinessException {
        log.debug("== Inicia getRefConfirmationByID/RefConfirmationBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RefConfirmation objPojo = daoRefConfirmation.getRefConfirmationByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(RefConfirmationVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getRefConfirmationByID/RefConfirmationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRefConfirmationByID/RefConfirmationBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefConfirmationBusinessBeanLocal#createRefConfirmation(co.com.directv.sdii.model.vo.RefConfirmationVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createRefConfirmation(RefConfirmationVO obj) throws BusinessException {
        log.debug("== Inicia createRefConfirmation/RefConfirmationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RefConfirmation objPojo =  UtilsBusiness.copyObject(RefConfirmation.class, obj);
            daoRefConfirmation.createRefConfirmation(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createRefConfirmation/RefConfirmationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createRefConfirmation/RefConfirmationBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefConfirmationBusinessBeanLocal#updateRefConfirmation(co.com.directv.sdii.model.vo.RefConfirmationVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateRefConfirmation(RefConfirmationVO obj) throws BusinessException {
        log.debug("== Inicia updateRefConfirmation/RefConfirmationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RefConfirmation objPojo =  UtilsBusiness.copyObject(RefConfirmation.class, obj);
            daoRefConfirmation.updateRefConfirmation(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateRefConfirmation/RefConfirmationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateRefConfirmation/RefConfirmationBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefConfirmationBusinessBeanLocal#deleteRefConfirmation(co.com.directv.sdii.model.vo.RefConfirmationVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteRefConfirmation(RefConfirmationVO obj) throws BusinessException {
        log.debug("== Inicia deleteRefConfirmation/RefConfirmationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RefConfirmation objPojo =  UtilsBusiness.copyObject(RefConfirmation.class, obj);
            daoRefConfirmation.deleteRefConfirmation(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteRefConfirmation/RefConfirmationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteRefConfirmation/RefConfirmationBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefConfirmationBusinessBeanLocal#saveNotSerializedReferenceElementItemConfirmation(co.com.directv.sdii.model.vo.RefConfirmationVO,co.com.directv.sdii.model.vo.UserVO,java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveAllNotSerializedReferenceElementItemConfirmation(List<RefConfirmationVO> confirmations,UserVO user,Long referenceId) throws BusinessException {
		log.debug("== Inicia saveAllNotSerializedReferenceElementItemConfirmation/RefConfirmationBusinessBean ==");
		UtilsBusiness.assertNotNull(confirmations, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			if(confirmations == null || confirmations.size() == 0){
				log.debug("La lista de confirmacion viene vacia");
        		throw new BusinessException(ErrorBusinessMessages.REFERENCE_CONFIRMATIONS_EMPTY.getCode(), ErrorBusinessMessages.REFERENCE_CONFIRMATIONS_EMPTY.getMessage());
			}
			for(RefConfirmationVO confirmationVO : confirmations ){
				saveNotSerializedReferenceElementItemConfirmation(confirmationVO, user, referenceId);
			}
		}catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación saveAllNotSerializedReferenceElementItemConfirmation/RefConfirmationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina saveAllNotSerializedReferenceElementItemConfirmation/RefConfirmationBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefConfirmationBusinessBeanLocal#saveNotSerializedReferenceElementItemConfirmation(co.com.directv.sdii.model.vo.RefConfirmationVO,co.com.directv.sdii.model.vo.UserVO,java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveNotSerializedReferenceElementItemConfirmation(RefConfirmationVO confirmation,UserVO user,Long referenceId) throws BusinessException {
		log.debug("== Inicia saveNotSerializedReferenceElementItemConfirmation/RefConfirmationBusinessBean ==");
        UtilsBusiness.assertNotNull(confirmation, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(confirmation.getConfirmedQuantity(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(confirmation.getReferenceElementItem().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(user.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(referenceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	/*Date fechaActual = UtilsBusiness.obtenerUltimaHoraDia( UtilsBusiness.fechaActual() );
        	Date confirmationDate = UtilsBusiness.obtenerPrimeraHoraDia( confirmation.getComfirmDate() );*/
        	if(confirmation.getConfirmedQuantity().equals(0D)){
        		//Error porque la cantidad no puede ser cero
        		log.debug("La cantidad a confirmar no puede ser cero");
        		throw new BusinessException(ErrorBusinessMessages.REFERENCE_CONFIRMATIONS_IS_ZERO.getCode(), ErrorBusinessMessages.REFERENCE_CONFIRMATIONS_IS_ZERO.getMessage());
        	} /*else if(confirmationDate.after( fechaActual )){
        		//Error porque la fecha no puede ser superior a la actual
        		log.debug("La fecha de la confirmacion no puede ser superior a la actual");
        		throw new BusinessException(ErrorBusinessMessages.REFERENCE_CONFIRMATIONS_WRONG_DATE.getCode(), ErrorBusinessMessages.REFERENCE_CONFIRMATIONS_WRONG_DATE.getMessage());
        	}*/
        	confirmation.setComfirmDate( UtilsBusiness.fechaActual() );
        	RefConfirmation confirmationPojo = UtilsBusiness.copyObject(RefConfirmation.class, confirmation);
        	ReferenceVO reference = businessReference.getReferenceByID(referenceId);
            //Obtengo la suma de las confirmacion que se han hecho
			Double actualConfirmations = daoRefConfirmation.countElementConfirmedQuatity(confirmationPojo.getReferenceElementItem().getId());
			//Obtengo la cantidad del elemento en la remision
			ReferenceElementItemVO referenceElementItem =  businessReferenceElementItem.getReferenceElementItemByID(confirmationPojo.getReferenceElementItem().getId());
			if(referenceElementItem.getItemStatus().getStatusCode().equals(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity())){
				log.debug("La cantidad a ingresar en la confirmacion junto con las existentes supera la cantidad de la remision");
        		throw new BusinessException(ErrorBusinessMessages.REFERENCE_ELEMENT_ITEM_CONFIRMATION_ERROR.getCode(), ErrorBusinessMessages.REFERENCE_ELEMENT_ITEM_CONFIRMATION_ERROR.getMessage());					
			}
			Double referenceQuantity = referenceElementItem.getRefQuantity();
			Double diference = referenceQuantity - actualConfirmations - confirmationPojo.getConfirmedQuantity();
			ItemStatus newStatus = new ItemStatus();
        	if( diference == 0){
        		//Creo la confirmacion y se pone el elemento en recepcionado
        		newStatus.setId( CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getIdEntity( ItemStatus.class.getName() ));
        		// jvelez -- 10/06/2011 -- Realizar el llamado de movimiento adecuado
        		//Llama al CU 12 y CU 8 para mover elementos de bodega e informar a los responsables por correo
        		//businessWhElement.moveNotSerializedElementIntoWareHouseElement(businessReference.createNotSerializeWarehouseElementForPartialConfirmation(referenceElementItem, UtilsBusiness.copyObject(Reference.class, reference)), null);
        		businessReference.sendEmail(user, UtilsBusiness.copyObject(ReferenceVO.class, reference), EmailTypesEnum.REFERENCES_ELEMENTS_HAS_BEEN_RECEIVED.getEmailTypecode(), ApplicationTextEnum.CONFIRMATION_REFERRAL.getApplicationTextValue());
        	} else if( diference > 0 ){
        		//Creo la confirmacion y se pone el elemento en confirmado parcial
        		newStatus.setId( CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getIdEntity( ItemStatus.class.getName() ));
        		//Llama al CU 12 y CU 8 para mover elementos de bodega e informar a los responsables por correo
        		Double auxQuantity = referenceElementItem.getRefQuantity(); 
        		referenceElementItem.setRefQuantity(confirmationPojo.getConfirmedQuantity());
        		// jvelez -- 10/06/2011 -- Realizar el llamado de movimiento adecuado
        		//businessWhElement.moveNotSerializedElementIntoWareHouseElement(businessReference.createNotSerializeWarehouseElementForPartialConfirmation(referenceElementItem, UtilsBusiness.copyObject(Reference.class, reference)), null);
        		businessReference.sendEmail(user, UtilsBusiness.copyObject(ReferenceVO.class, reference), EmailTypesEnum.REFERENCES_ELEMENTS_HAS_BEEN_RECEIVED.getEmailTypecode(), ApplicationTextEnum.CONFIRMATION_REFERRAL.getApplicationTextValue());
        		referenceElementItem.setRefQuantity(auxQuantity);
        	} else if( diference < 0 ){
        		//Se lanza error ya que la cantidad de la nueva modificacion con las modificaciones existentes supera
        		//la cantidad del elemento especificada en la remision
        		log.debug("La cantidad a inngresar en la confirmacion junto con las existentes supera la cantidad de la remision");
        		throw new BusinessException(ErrorBusinessMessages.REFERENCE_CONFIRMATIONS_GREATER_THAN_MOV_QUANTITY.getCode(), ErrorBusinessMessages.REFERENCE_CONFIRMATIONS_GREATER_THAN_MOV_QUANTITY.getMessage());
        	}
        	saveConfirmation(confirmationPojo , user.getId() , diference);
        	referenceElementItem.setItemStatus(newStatus);
        	businessReferenceElementItem.updateReferenceElementItem(referenceElementItem);
        	//daoReferenceElementItem.updateReferenceElementItem(UtilsBusiness.copyObject(ReferenceElementItem.class, referenceElementItem));
			//Valida si todo los elementos de la remision esta en recepcionado para poner la remision en recepcionado
        	List<ReferenceElementItemVO> referenceElementItems = businessReferenceElementItem.getReferenceElementItemByReferenceIdAndByItemStatusAndElementType(referenceId, CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity(),CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity());
        	//Actualiza la remision
        	updateReferenceAfterUpdateElements(referenceElementItems,reference,user);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación saveNotSerializedReferenceElementItemConfirmation/RefConfirmationBusinessBean ==");
        	// Se captura el codigo "NOT_ELEMENT_EXIST" ya que el CU de movimiento de serializados lanza esa excepcion en caso 
        	// de no encontrar el elemento en la bodega definida
        	if (ex instanceof  BusinessException){			
    			if( ((BusinessException) ex).getMessageCode().equalsIgnoreCase( ErrorBusinessMessages.NOT_ELEMENTS_EXIST.getCode() ) ){
    				log.debug("== No hay elementos en la bodega para realizar la confirmacion de la remision ==");
    				throw new BusinessException( ErrorBusinessMessages.CANT_CONFIRM_REFERENCE.getCode() , ErrorBusinessMessages.CANT_CONFIRM_REFERENCE.getMessage());
    			}
    		}
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina saveNotSerializedReferenceElementItemConfirmation/RefConfirmationBusinessBean ==");
        }
	}
	
	/**
	 * Metodo: Almacena una confirmacion para el CU 34
	 * @param confirmationPojo
	 * @param userId
	 * @param pendQuantity
	 * @throws BusinessException
	 * @author jnova
	 */
	private void saveConfirmation(RefConfirmation confirmationPojo , Long userId , Double pendQuantity) throws BusinessException {
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
	 * @see co.com.directv.sdii.ejb.business.stock.RefConfirmationBusinessBeanLocal#saveNotSerializedReferenceElementItemConfirmation(co.com.directv.sdii.model.vo.RefConfirmationVO,co.com.directv.sdii.model.vo.UserVO,java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveSerializedReferenceElementItemConfirmation(List<RefConfirmationVO> confirmations,UserVO user,Long referenceId) throws BusinessException {
		log.debug("== Inicia saveSerializedReferenceElementItemConfirmation/RefConfirmationBusinessBean ==");
		UtilsBusiness.assertNotNull(confirmations, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			ReferenceVO reference = businessReference.getReferenceByID(referenceId);
			for(RefConfirmationVO vo : confirmations){
				RefConfirmation confirmationPojo = UtilsBusiness.copyObject(RefConfirmation.class, vo);
				ReferenceElementItemVO referenceElementItem =  businessReferenceElementItem.getReferenceElementItemByID(confirmationPojo.getReferenceElementItem().getId());
				if(referenceElementItem.getItemStatus().getStatusCode().equals(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity())){
					log.debug("La cantidad a ingresar en la confirmacion junto con las existentes supera la cantidad de la remision");
	        		throw new BusinessException(ErrorBusinessMessages.REFERENCE_ELEMENT_ITEM_CONFIRMATION_ERROR.getCode(), ErrorBusinessMessages.REFERENCE_ELEMENT_ITEM_CONFIRMATION_ERROR.getMessage());					
				}
				ItemStatus newStatus = new ItemStatus(Integer.valueOf(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity()).longValue());
				referenceElementItem.setItemStatus(newStatus);
				saveConfirmation(confirmationPojo, user.getId(), 0D);
				businessReferenceElementItem.updateReferenceElementItem(referenceElementItem);
        		// jvelez -- 10/06/2011 -- Realizar el llamado de movimiento adecuado
				//WarehouseElementVO whElement = businessReference.createSerializeWarehouseElement(referenceElementItem, reference);
				//businessWhElement.moveSerializedElementIntoWareHouseElement(whElement);
        		businessReference.sendEmail(user, UtilsBusiness.copyObject(ReferenceVO.class, reference), EmailTypesEnum.REFERENCES_ELEMENTS_HAS_BEEN_RECEIVED.getEmailTypecode(), ApplicationTextEnum.CONFIRMATION_REFERRAL.getApplicationTextValue());
			}
			List<ReferenceElementItemVO> referenceElementItems = businessReferenceElementItem.getReferenceElementItemByReferenceIdAndByItemStatusAndElementType(referenceId, CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity(),CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity());
			updateReferenceAfterUpdateElements(referenceElementItems,reference,user);
        	
		}catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación saveSerializedReferenceElementItemConfirmation/RefConfirmationBusinessBean ==");
        	// Se captura el codigo "NOT_ELEMENT_EXIST" ya que el CU de movimiento de serializados lanza esa excepcion en caso 
        	// de no encontrar el elemento en la bodega definida
        	if (ex instanceof  BusinessException){			
    			if( ((BusinessException) ex).getMessageCode().equalsIgnoreCase( ErrorBusinessMessages.NOT_ELEMENTS_EXIST.getCode() ) ){
    				log.debug("== No hay elementos en la bodega para realizar la confirmacion de la remision ==");
    				throw new BusinessException( ErrorBusinessMessages.CANT_CONFIRM_REFERENCE.getCode() , ErrorBusinessMessages.CANT_CONFIRM_REFERENCE.getMessage());
    			}
    		}
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina saveSerializedReferenceElementItemConfirmation/RefConfirmationBusinessBean ==");
        }
	}
	
	/**
	 * Metodo: Actualiza la remision despues de actualizar los elementos
	 * @param referenceElementItems Lista de elementos en la remision
	 * @param reference Remision
	 * @param user Usuario que realiza la remision
	 * @throws BusinessException En caso de error al actualizar la remision
	 * @author jnova
	 */
	public void updateReferenceAfterUpdateElements(List<ReferenceElementItemVO> referenceElementItems , ReferenceVO reference , UserVO user) throws BusinessException{
		log.debug("== Inicia updateReferenceAfterUpdateElements/RefConfirmationBusinessBean ==");
		try {
			ReferenceStatus newReferenceStatus = new ReferenceStatus();
        	String referenceModification = null ;
        	if( referenceElementItems == null || referenceElementItems.size() == 0 ){
        		//Se pone la remision en recepcionada
        		newReferenceStatus.setId(Integer.valueOf(CodesBusinessEntityEnum.REFERENCE_STATUS_RECEPTED.getCodeEntity()).longValue());
        		referenceModification = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_RECEPTED.getCodeEntity();
        	} else {
        		//Se pone en confirmado parcial
        		newReferenceStatus.setId(Integer.valueOf(CodesBusinessEntityEnum.REFERENCE_STATUS_PARTIALLY_CONFIRMED.getCodeEntity()).longValue());
        		referenceModification = CodesBusinessEntityEnum.REFERENCE_MODIFICATION_PARTIALLY_CONFIRMED.getCodeEntity();
        	}
        	reference.setReferenceStatus(newReferenceStatus);
        	businessReference.updateReference(reference);
        	businessReference.buildReferenceModification(UtilsBusiness.copyObject(Reference.class, reference), user, referenceModification);
		}catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateReferenceAfterUpdateElements/RefConfirmationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateReferenceAfterUpdateElements/RefConfirmationBusinessBean ==");
        }
	}
}
