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
import co.com.directv.sdii.ejb.business.stock.AdjustmentModificationBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Adjustment;
import co.com.directv.sdii.model.pojo.AdjustmentModification;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.AdjustmentModificationVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentModificationDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentStatusDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD AdjustmentModification 
 * 
 * Fecha de Creación: 28/11/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="AdjustmentModificationBusinessBeanLocal",mappedName="ejb/AdjustmentModificationBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AdjustmentModificationBusinessBean extends BusinessBase implements AdjustmentModificationBusinessBeanLocal {

    @EJB(name="AdjustmentModificationDAOLocal", beanInterface=AdjustmentModificationDAOLocal.class)
    private AdjustmentModificationDAOLocal adjustmentModificationDAO;
    
    @EJB(name="AdjustmentStatusDAOLocal", beanInterface=AdjustmentStatusDAOLocal.class)
    private AdjustmentStatusDAOLocal adjustmentStatusDAO;
   
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
    
    private final static Logger log = UtilsBusiness.getLog4J(AdjustmentModificationBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.AdjustmentModificationBusinessBeanLocal#getAllAdjustmentModifications()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AdjustmentModificationVO> getAllAdjustmentModifications() throws BusinessException {
        log.debug("== Inicia getAllAdjustmentModifications/AdjustmentModificationBusinessBean ==");
        try {
            return UtilsBusiness.convertList(adjustmentModificationDAO.getAllAdjustmentModifications(), AdjustmentModificationVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllAdjustmentModifications/AdjustmentModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllAdjustmentModifications/AdjustmentModificationBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.AdjustmentModificationBusinessBeanLocal#getAdjustmentModificationByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public AdjustmentModificationVO getAdjustmentModificationByID(Long id) throws BusinessException {
        log.debug("== Inicia getAdjustmentModificationByID/AdjustmentModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	AdjustmentModification objPojo = adjustmentModificationDAO.getAdjustmentModificationByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(AdjustmentModificationVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAdjustmentModificationByID/AdjustmentModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAdjustmentModificationByID/AdjustmentModificationBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.AdjustmentModificationBusinessBeanLocal#createAdjustmentModification(co.com.directv.sdii.model.vo.AdjustmentModificationVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createAdjustmentModification(AdjustmentModificationVO obj) throws BusinessException {
        log.debug("== Inicia createAdjustmentModification/AdjustmentModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	AdjustmentModification objPojo =  UtilsBusiness.copyObject(AdjustmentModification.class, obj);
        	adjustmentModificationDAO.createAdjustmentModification(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createAdjustmentModification/AdjustmentModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentModification/AdjustmentModificationBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.AdjustmentModificationBusinessBeanLocal#updateAdjustmentModification(co.com.directv.sdii.model.vo.AdjustmentModificationVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateAdjustmentModification(AdjustmentModificationVO obj) throws BusinessException {
        log.debug("== Inicia updateAdjustmentModification/AdjustmentModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	AdjustmentModification objPojo =  UtilsBusiness.copyObject(AdjustmentModification.class, obj);
        	adjustmentModificationDAO.updateAdjustmentModification(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateAdjustmentModification/AdjustmentModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateAdjustmentModification/AdjustmentModificationBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.AdjustmentModificationBusinessBeanLocal#deleteAdjustmentModification(co.com.directv.sdii.model.vo.AdjustmentModificationVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteAdjustmentModification(AdjustmentModificationVO obj) throws BusinessException {
        log.debug("== Inicia deleteAdjustmentModification/AdjustmentModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	AdjustmentModification objPojo =  UtilsBusiness.copyObject(AdjustmentModification.class, obj);
        	adjustmentModificationDAO.deleteAdjustmentModification(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteAdjustmentModification/AdjustmentModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteAdjustmentModification/AdjustmentModificationBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.AdjustmentModificationBusinessBeanLocal#getAdjustmentModificationsByAdjustmentID(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<AdjustmentModificationVO> getAdjustmentModificationsByAdjustmentID(
			Long adjID) throws BusinessException {
		log.debug("== Inicia deleteAdjustmentModification/AdjustmentModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(adjID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<AdjustmentModification> objPojo = adjustmentModificationDAO.getAdjustmentModificationsByAdjustmentID(adjID);
        	
        	List<AdjustmentModificationVO> listAdjustmentModificationVOs = UtilsBusiness.convertList(objPojo, AdjustmentModificationVO.class);
            return listAdjustmentModificationVOs;
            
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteAdjustmentModification/AdjustmentModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteAdjustmentModification/AdjustmentModificationBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.AdjustmentModificationBusinessBeanLocal#getAdjustmentModificationsAndUsersModificationsByAdjustmentID(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<AdjustmentModificationVO> getAdjustmentModificationsAndUsersModificationsByAdjustmentID(
			Long adjID) throws BusinessException {
		log.debug("== Inicia getAdjustmentModificationsAndUsersModificationsByAdjustmentID/AdjustmentModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(adjID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<Object[]> objPojo = adjustmentModificationDAO.getAdjustmentModificationsAndUsersModificationsByAdjustmentID(adjID);
        	List<AdjustmentModificationVO> response = null;
        	if(objPojo != null && objPojo.size() > 0){
        		response = new ArrayList<AdjustmentModificationVO>();
        		for (Object[] obj : objPojo) {        			
        			AdjustmentModificationVO adjustmentModificationVO = UtilsBusiness.copyObject(AdjustmentModificationVO.class, (AdjustmentModification)obj[0]);
        			adjustmentModificationVO.setModificationUser(UtilsBusiness.copyObject(UserVO.class, (User)obj[1]));
        			response.add(adjustmentModificationVO);
        		}
        	}
            return response;            
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAdjustmentModificationsAndUsersModificationsByAdjustmentID/AdjustmentModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAdjustmentModificationsAndUsersModificationsByAdjustmentID/AdjustmentModificationBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.AdjustmentModificationBusinessBeanLocal#getAdjustmentModificationsAndUsersModificationsForDetailsByAdjustmentID(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<AdjustmentModificationVO> getAdjustmentModificationsAndUsersModificationsForDetailsByAdjustmentID(
			Long adjID) throws BusinessException {
		log.debug("== Inicia getAdjustmentModificationsAndUsersModificationsByAdjustmentID/AdjustmentModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(adjID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<Object[]> objPojo = adjustmentModificationDAO.getAdjustmentModificationsAndUsersModificationsByAdjustmentID(adjID);
        	List<AdjustmentModificationVO> response = null;
        	if(objPojo != null && objPojo.size() > 0){
        		response = new ArrayList<AdjustmentModificationVO>();
        		for (Object[] obj : objPojo) {   
        			AdjustmentModification pojo = (AdjustmentModification)obj[0];
        			Adjustment adjustment = new Adjustment();
        			adjustment.setId(pojo.getAdjustment().getId());
        			pojo.setAdjustment(adjustment);
        			AdjustmentModificationVO adjustmentModificationVO = UtilsBusiness.copyObject(AdjustmentModificationVO.class, pojo);
        			adjustmentModificationVO.setModificationUser(UtilsBusiness.copyObject(UserVO.class, (User)obj[1]));
        			response.add(adjustmentModificationVO);
        		}
        	}
            return response;            
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAdjustmentModificationsAndUsersModificationsByAdjustmentID/AdjustmentModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAdjustmentModificationsAndUsersModificationsByAdjustmentID/AdjustmentModificationBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.AdjustmentModificationBusinessBeanLocal#createAdjustmentModification(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	@Override
	public void createAdjustmentModification(Long adjId,String adjModificationCode,Long userId) throws BusinessException {
		log.debug("== Inicia createAdjustmentModification/AdjustmentModificationBusinessBean ==");
		try {
			AdjustmentModification modification = new AdjustmentModification();
			Adjustment adjustment = new Adjustment();
			adjustment.setId(adjId);
			modification.setAdjustment(adjustment);
			modification.setAdjustmentStatus( adjustmentStatusDAO.getAdjustmentStatusByCode( adjModificationCode ) );
			modification.setUserModification(new User());
			modification.getUserModification().setId(userId);
			modification.setModificationDate( UtilsBusiness.getCurrentTimeZoneDateByUserId(userId, daoUser) );
			this.adjustmentModificationDAO.createAdjustmentModification(modification); 
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createAdjustmentModification/AdjustmentModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentModification/AdjustmentModificationBusinessBean ==");
        }
	}
}
