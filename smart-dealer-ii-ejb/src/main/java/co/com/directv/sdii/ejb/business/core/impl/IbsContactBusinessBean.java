package co.com.directv.sdii.ejb.business.core.impl;

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
import co.com.directv.sdii.ejb.business.core.IbsContactBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.IbsContactDTO;
import co.com.directv.sdii.model.pojo.IbsContact;
import co.com.directv.sdii.model.pojo.IbsContactReason;
import co.com.directv.sdii.model.vo.IbsContactReasonVO;
import co.com.directv.sdii.model.vo.IbsContactVO;
import co.com.directv.sdii.persistence.dao.core.IbsContactDAOLocal;
import co.com.directv.sdii.persistence.dao.core.IbsContactReasonDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD AdjustmentModification 
 * 
 * Fecha de Creación: 28/11/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="IbsContactBusinessBeanLocal",mappedName="ejb/IbsContactBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class IbsContactBusinessBean extends BusinessBase implements IbsContactBusinessBeanLocal {

    @EJB(name="IbsContactDAOLocal", beanInterface=IbsContactDAOLocal.class)
    private IbsContactDAOLocal ibsContactDAO;
    
    @EJB(name="IbsContactReasonDAOLocal", beanInterface=IbsContactReasonDAOLocal.class)
    private IbsContactReasonDAOLocal ibsContactReasonDAO;
    
    private final static Logger log = UtilsBusiness.getLog4J(IbsContactBusinessBean.class);

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.IbsContactBusinessBeanLocal#createIbsContact(co.com.directv.sdii.model.vo.IbsContactVO)
     */
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createIbsContact(IbsContactVO obj) throws BusinessException {
        log.debug("== Inicia createIbsContact/IbsContactBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	IbsContact objPojo =  UtilsBusiness.copyObject(IbsContact.class, obj);
        	ibsContactDAO.createIbsContact(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createIbsContact/IbsContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createIbsContact/IbsContactBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.IbsContactBusinessBeanLocal#updateIbsContact(co.com.directv.sdii.model.vo.IbsContactVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateIbsContact(IbsContactVO obj) throws BusinessException {
        log.debug("== Inicia updateIbsContact/IbsContactBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	IbsContact objPojo =  UtilsBusiness.copyObject(IbsContact.class, obj);
        	ibsContactDAO.updateIbsContact(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateIbsContact/IbsContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateIbsContact/IbsContactBusinessBean ==");
        }
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteIbsContact(IbsContactVO obj) throws BusinessException {
        log.debug("== Inicia deleteIbsContact/IbsContactBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	IbsContact objPojo =  UtilsBusiness.copyObject(IbsContact.class, obj);
        	ibsContactDAO.deleteIbsContact(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteIbsContact/IbsContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteIbsContact/IbsContactBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.AdjustmentModificationBusinessBeanLocal#getAdjustmentModificationsByAdjustmentID(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IbsContactVO getIbsContactByID(
			Long id) throws BusinessException {
		log.debug("== Inicia getIbsContactByID/IbsContactBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	IbsContact objPojo = ibsContactDAO.getIbsContactByID(id);
            return UtilsBusiness.copyObject(IbsContactVO.class, objPojo);
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getIbsContactByID/IbsContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getIbsContactByID/IbsContactBusinessBean ==");
        }
	} 
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.IbsContactBusinessBeanLocal#getIbsContactReasonByCode(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IbsContactReasonVO getIbsContactReasonByCode(String code,Long countryId) throws BusinessException{
		log.debug("== Inicia getIbsContactReasonByCode/IbsContactBusinessBean ==");
        UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	IbsContactReason objPojo = ibsContactReasonDAO.getIbsContactReasonByCode(code,countryId);
            return UtilsBusiness.copyObject(IbsContactReasonVO.class, objPojo);
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getIbsContactReasonByCode/IbsContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getIbsContactReasonByCode/IbsContactBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.IbsContactBusinessBeanLocal#getIbsContactReasonByCode(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IbsContactVO getIbsContactByIbsContactCode(String ibsContactCode,Long countryId) throws BusinessException{
		log.debug("== Inicia getIbsContactByIbsContactCode/IbsContactBusinessBean ==");
        UtilsBusiness.assertNotNull(ibsContactCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	IbsContact objPojo = ibsContactDAO.getIbsContactByIbsContactCode(ibsContactCode,countryId);
            return UtilsBusiness.copyObject(IbsContactVO.class, objPojo);
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getIbsContactByIbsContactCode/IbsContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getIbsContactByIbsContactCode/IbsContactBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.IbsContactBusinessBeanLocal#mergeIbsContact(co.com.directv.sdii.model.vo.IbsContactVO)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void mergeIbsContact(IbsContactVO obj) throws BusinessException{
		log.debug("== Inicia mergeIbsContact/IbsContactBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	IbsContact objPojo = ibsContactDAO.getIbsContactByIbsContactCode(obj.getIbsContactCode(),obj.getCountry().getId());
        	if(objPojo==null){
        		this.createIbsContact(obj);
        	}else{
        		obj.setId(objPojo.getId());
        		this.updateIbsContact(obj);
        	}
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación mergeIbsContact/IbsContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina mergeIbsContact/IbsContactBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.IbsContactBusinessBeanLocal#getIbsContactDTOByWorkOrderId(java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<IbsContactDTO> getIbsContactDTOByWorkOrderId(Long workOrderId,Long countryId) throws BusinessException{
		log.debug("== Inicia getIbsContactDTOByWorkOrderId/IbsContactBusinessBean ==");
        UtilsBusiness.assertNotNull(workOrderId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	return ibsContactDAO.getIbsContactDTOByWorkOrderId(workOrderId,countryId);
           
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getIbsContactByIbsContactCode/IbsContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getIbsContactByIbsContactCode/IbsContactBusinessBean ==");
        }
	}
		
	
}
