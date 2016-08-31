package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.Date;
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
import co.com.directv.sdii.ejb.business.stock.SpecialCommentBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.SpecialComment;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SpecialCommentResponse;
import co.com.directv.sdii.model.vo.SpecialCommentVO;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SpecialCommentDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD SpecialComment
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.SpecialCommentDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.SpecialCommentBusinessBeanLocal
 */
@Stateless(name="SpecialCommentBusinessBeanLocal",mappedName="ejb/SpecialCommentBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SpecialCommentBusinessBean extends BusinessBase implements SpecialCommentBusinessBeanLocal {

    @EJB(name="SpecialCommentDAOLocal", beanInterface=SpecialCommentDAOLocal.class)
    private SpecialCommentDAOLocal daoSpecialComment;
    @EJB(name="SpecialCoUserDAOLocalmmentDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
    
    private final static Logger log = UtilsBusiness.getLog4J(SpecialCommentBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.SpecialCommentBusinessBeanLocal#getAllSpecialComments()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<SpecialCommentVO> getAllSpecialComments() throws BusinessException {
        log.debug("== Inicia getAllSpecialComments/SpecialCommentBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoSpecialComment.getAllSpecialComments(), SpecialCommentVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllSpecialComments/SpecialCommentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllSpecialComments/SpecialCommentBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.SpecialCommentBusinessBeanLocal#getSpecialCommentsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SpecialCommentVO getSpecialCommentByID(Long id) throws BusinessException {
        log.debug("== Inicia getSpecialCommentByID/SpecialCommentBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            SpecialComment objPojo = daoSpecialComment.getSpecialCommentByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(SpecialCommentVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getSpecialCommentByID/SpecialCommentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSpecialCommentByID/SpecialCommentBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SpecialCommentBusinessBeanLocal#createSpecialComment(co.com.directv.sdii.model.vo.SpecialCommentVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createSpecialComment(SpecialCommentVO obj) throws BusinessException {
        log.debug("== Inicia createSpecialComment/SpecialCommentBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            SpecialComment objPojo =  UtilsBusiness.copyObject(SpecialComment.class, obj);
            daoSpecialComment.createSpecialComment(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createSpecialComment/SpecialCommentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSpecialComment/SpecialCommentBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SpecialCommentBusinessBeanLocal#updateSpecialComment(co.com.directv.sdii.model.vo.SpecialCommentVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateSpecialComment(SpecialCommentVO obj) throws BusinessException {
        log.debug("== Inicia updateSpecialComment/SpecialCommentBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            SpecialComment objPojo =  UtilsBusiness.copyObject(SpecialComment.class, obj);
            daoSpecialComment.updateSpecialComment(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateSpecialComment/SpecialCommentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSpecialComment/SpecialCommentBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SpecialCommentBusinessBeanLocal#deleteSpecialComment(co.com.directv.sdii.model.vo.SpecialCommentVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteSpecialComment(SpecialCommentVO obj) throws BusinessException {
        log.debug("== Inicia deleteSpecialComment/SpecialCommentBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            SpecialComment objPojo =  UtilsBusiness.copyObject(SpecialComment.class, obj);
            daoSpecialComment.deleteSpecialComment(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteSpecialComment/SpecialCommentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSpecialComment/SpecialCommentBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SpecialCommentBusinessBeanLocal#createSpecialComments(java.lang.Long, java.util.list<co.com.directv.sdii.model.vo.SpecialCommentVO>)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createSpecialComments(Long referenceId , List<SpecialCommentVO> specialCommentsList) throws BusinessException{
		log.debug("== Inicia createSpecialComments/SpecialCommentBusinessBean ==");
        try {
        	if( referenceId == null || referenceId.longValue() <= 0 ){
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	for(SpecialCommentVO vo : specialCommentsList){
        		if(vo.getCommentDescription() == null || vo.getCommentDescription().trim().length() == 0){
        			log.debug("La descripcion del comentario no puede ser nula");
            		throw new BusinessException(ErrorBusinessMessages.SPECIAL_COMMENT_NOT_NULL.getCode(), ErrorBusinessMessages.SPECIAL_COMMENT_NOT_NULL.getMessage());
        		}
        		SpecialComment objPojo =  UtilsBusiness.copyObject(SpecialComment.class, vo);
        		Reference reference = new Reference();
        		reference.setId(referenceId);
        		objPojo.setReference(reference);
        		objPojo.setCommentDate(new Date());
        		daoSpecialComment.createSpecialComment(objPojo);
        	}
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createSpecialComments/SpecialCommentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSpecialComments/SpecialCommentBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SpecialCommentBusinessBeanLocal#getSpecialCommentsByReferenceId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<SpecialCommentVO> getSpecialCommentsByReferenceId(Long referenceId) throws BusinessException{
		log.debug("== Inicia getSpecialCommentsByReferenceId/SpecialCommentBusinessBean ==");
        UtilsBusiness.assertNotNull(referenceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<SpecialComment> pojoList = daoSpecialComment.getSpecialCommentsByReferenceId(referenceId);
        	if(pojoList != null){
        		for(SpecialComment pojo : pojoList){
        			Reference reference = new Reference();
        			reference.setId(pojo.getReference().getId());
        			pojo.setReference(reference);
        		}
        	}
        	//List<SpecialCommentVO> response = UtilsBusiness.convertList(, SpecialCommentVO.class);
        	/*if(response != null ){
        		for(SpecialCommentVO vo : response ){
        			Reference
        		}
        	}*/
            return UtilsBusiness.convertList(pojoList, SpecialCommentVO.class);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getSpecialCommentsByReferenceId/SpecialCommentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSpecialCommentsByReferenceId/SpecialCommentBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.SpecialCommentBusinessBeanLocal#getSpecialCommentsByReferenceId(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public SpecialCommentResponse getSpecialCommentsByReferenceId(
			Long referenceId, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		log.debug("== Inicia getSpecialCommentsByReferenceId/SpecialCommentBusinessBean ==");
        UtilsBusiness.assertNotNull(referenceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        List<SpecialComment> tmpList;
        try {
        	SpecialCommentResponse response = daoSpecialComment.getSpecialCommentsByReferenceId(referenceId,requestCollInfo);
        	tmpList = response.getSpecialComment();
        	response.setSpecialCommentVO(UtilsBusiness.convertList(tmpList, SpecialCommentVO.class));
        	response.setSpecialComment(null);
        	for(SpecialCommentVO tmp : response.getSpecialCommentVO()){
        		User user = daoUser.getUserById(tmp.getUserId());
        		if( user != null && user.getName() != null ){
        			tmp.setUserName( user.getName() );
        		}
        	}
            return response;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getSpecialCommentsByReferenceId/SpecialCommentBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSpecialCommentsByReferenceId/SpecialCommentBusinessBean ==");
        }
	}
}
