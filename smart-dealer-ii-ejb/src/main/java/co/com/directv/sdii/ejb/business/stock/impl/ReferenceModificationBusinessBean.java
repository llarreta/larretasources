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
import co.com.directv.sdii.ejb.business.stock.ReferenceModificationBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.ReferenceModification;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.ReferenceModificationVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceModTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceModificationDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD ReferenceModification
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceModificationDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ReferenceModificationBusinessBeanLocal
 */
@Stateless(name="ReferenceModificationBusinessBeanLocal",mappedName="ejb/ReferenceModificationBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReferenceModificationBusinessBean extends BusinessBase implements ReferenceModificationBusinessBeanLocal {

    @EJB(name="ReferenceModificationDAOLocal", beanInterface=ReferenceModificationDAOLocal.class)
    private ReferenceModificationDAOLocal daoReferenceModification;
    
    @EJB(name="ReferenceModTypeDAOLocal", beanInterface=ReferenceModTypeDAOLocal.class)
    private ReferenceModTypeDAOLocal referenceModTypeDAO;
   
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
    
    private final static Logger log = UtilsBusiness.getLog4J(ReferenceModificationBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ReferenceModificationBusinessBeanLocal#getAllReferenceModifications()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ReferenceModificationVO> getAllReferenceModifications() throws BusinessException {
        log.debug("== Inicia getAllReferenceModifications/ReferenceModificationBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoReferenceModification.getAllReferenceModifications(), ReferenceModificationVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllReferenceModifications/ReferenceModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllReferenceModifications/ReferenceModificationBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ReferenceModificationBusinessBeanLocal#getReferenceModificationsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ReferenceModificationVO getReferenceModificationByID(Long id) throws BusinessException {
        log.debug("== Inicia getReferenceModificationByID/ReferenceModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReferenceModification objPojo = daoReferenceModification.getReferenceModificationByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ReferenceModificationVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceModificationByID/ReferenceModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceModificationByID/ReferenceModificationBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceModificationBusinessBeanLocal#createReferenceModification(co.com.directv.sdii.model.vo.ReferenceModificationVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createReferenceModification(ReferenceModificationVO obj) throws BusinessException {
        log.debug("== Inicia createReferenceModification/ReferenceModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReferenceModification objPojo =  UtilsBusiness.copyObject(ReferenceModification.class, obj);
            daoReferenceModification.createReferenceModification(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createReferenceModification/ReferenceModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createReferenceModification/ReferenceModificationBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceModificationBusinessBeanLocal#updateReferenceModification(co.com.directv.sdii.model.vo.ReferenceModificationVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateReferenceModification(ReferenceModificationVO obj) throws BusinessException {
        log.debug("== Inicia updateReferenceModification/ReferenceModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReferenceModification objPojo =  UtilsBusiness.copyObject(ReferenceModification.class, obj);
            daoReferenceModification.updateReferenceModification(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateReferenceModification/ReferenceModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateReferenceModification/ReferenceModificationBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceModificationBusinessBeanLocal#deleteReferenceModification(co.com.directv.sdii.model.vo.ReferenceModificationVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteReferenceModification(ReferenceModificationVO obj) throws BusinessException {
        log.debug("== Inicia deleteReferenceModification/ReferenceModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReferenceModification objPojo =  UtilsBusiness.copyObject(ReferenceModification.class, obj);
            daoReferenceModification.deleteReferenceModification(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteReferenceModification/ReferenceModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteReferenceModification/ReferenceModificationBusinessBean ==");
        }
	}


	 /* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceModificationBusinessBeanLocal#getReferenceModificationsByReferenceID(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ReferenceModificationVO> getReferenceModificationsByReferenceID(
			Long refID) throws BusinessException {
		log.debug("== Inicia deleteReferenceModification/ReferenceModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(refID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<ReferenceModification> objPojo = daoReferenceModification.getReferenceModificationsByReferenceID(refID);
        	
        	List<ReferenceModificationVO> listReferenceModificationVOs = UtilsBusiness.convertList(objPojo, ReferenceModificationVO.class);
            return listReferenceModificationVOs;
            
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteReferenceModification/ReferenceModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteReferenceModification/ReferenceModificationBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceModificationBusinessBeanLocal#getReferenceModificationsAndUsersModificationsByReferenceID(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ReferenceModificationVO> getReferenceModificationsAndUsersModificationsByReferenceID(
			Long refID) throws BusinessException {
		log.debug("== Inicia getReferenceModificationsAndUsersModificationsByReferenceID/ReferenceModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(refID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<Object[]> objPojo = daoReferenceModification.getReferenceModificationsAndUsersModificationsByReferenceID(refID);
        	List<ReferenceModificationVO> response = null;
        	if(objPojo != null && objPojo.size() > 0){
        		response = new ArrayList<ReferenceModificationVO>();
        		for (Object[] obj : objPojo) {        			
        			ReferenceModificationVO reference = UtilsBusiness.copyObject(ReferenceModificationVO.class, (ReferenceModification)obj[0]);
        			reference.setModificationUser(UtilsBusiness.copyObject(UserVO.class, (User)obj[1]));
        			response.add(reference);
        		}
        	}
            return response;            
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceModificationsAndUsersModificationsByReferenceID/ReferenceModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceModificationsAndUsersModificationsByReferenceID/ReferenceModificationBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceModificationBusinessBeanLocal#getReferenceModificationsAndUsersModificationsForDetailsByReferenceID(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ReferenceModificationVO> getReferenceModificationsAndUsersModificationsForDetailsByReferenceID(
			Long refID) throws BusinessException {
		log.debug("== Inicia getReferenceModificationsAndUsersModificationsByReferenceID/ReferenceModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(refID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<Object[]> objPojo = daoReferenceModification.getReferenceModificationsAndUsersModificationsByReferenceID(refID);
        	List<ReferenceModificationVO> response = null;
        	if(objPojo != null && objPojo.size() > 0){
        		response = new ArrayList<ReferenceModificationVO>();
        		for (Object[] obj : objPojo) {   
        			ReferenceModification pojo = (ReferenceModification)obj[0];
        			Reference reference = new Reference();
        			reference.setId(pojo.getReference().getId());
        			pojo.setReference(reference);
        			ReferenceModificationVO referenceModification = UtilsBusiness.copyObject(ReferenceModificationVO.class, pojo);
        			referenceModification.setModificationUser(UtilsBusiness.copyObject(UserVO.class, (User)obj[1]));
        			response.add(referenceModification);
        		}
        	}
            return response;            
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceModificationsAndUsersModificationsByReferenceID/ReferenceModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceModificationsAndUsersModificationsByReferenceID/ReferenceModificationBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceModificationBusinessBeanLocal#createReferenceModification(java.lang.Long, java.lang.String)
	 */
	@Override
	public void createReferenceModification(Long refId,String refModificationCode,Long userId) throws BusinessException {
		log.debug("== Inicia createReferenceModification/ReferenceModificationBusinessBean ==");
		try {
			ReferenceModification modification = new ReferenceModification();
			modification.setReference( new Reference( refId ) );
			modification.setReferenceModType( referenceModTypeDAO.getReferenceModTypeByCode( refModificationCode ) );
			modification.setUserModification(new User());
			modification.getUserModification().setId(userId);
			modification.setModificationDate( UtilsBusiness.getCurrentTimeZoneDateByUserId(userId, daoUser) );
			this.daoReferenceModification.createReferenceModification(modification); 
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createReferenceModification/ReferenceModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createReferenceModification/ReferenceModificationBusinessBean ==");
        }
	}
}
