package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.SpecialCommentBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.SpecialCommentFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SpecialCommentResponse;
import co.com.directv.sdii.model.vo.SpecialCommentVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD SpecialComment
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.SpecialCommentFacadeLocal
 */
@Stateless(name="SpecialCommentFacadeLocal",mappedName="ejb/SpecialCommentFacadeLocal")
public class SpecialCommentFacadeBean implements SpecialCommentFacadeBeanLocal {

		
    @EJB(name="SpecialCommentBusinessBeanLocal", beanInterface=SpecialCommentBusinessBeanLocal.class)
    private SpecialCommentBusinessBeanLocal businessSpecialComment;
    
  
    /* (non-Javadoc)
     * @see #{$business_package_name$}.SpecialCommentFacadeLocal#getAllSpecialComments()
     */
    public List<SpecialCommentVO> getAllSpecialComments() throws BusinessException {
    	return businessSpecialComment.getAllSpecialComments();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.SpecialCommentFacadeLocal#getSpecialCommentsByID(java.lang.Long)
     */
    public SpecialCommentVO getSpecialCommentByID(Long id) throws BusinessException {
    	return businessSpecialComment.getSpecialCommentByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SpecialCommentFacadeLocal#createSpecialComment(co.com.directv.sdii.model.vo.SpecialCommentVO)
	 */
	public void createSpecialComment(SpecialCommentVO obj) throws BusinessException {
		businessSpecialComment.createSpecialComment(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SpecialCommentFacadeLocal#updateSpecialComment(co.com.directv.sdii.model.vo.SpecialCommentVO)
	 */
	public void updateSpecialComment(SpecialCommentVO obj) throws BusinessException {
		businessSpecialComment.updateSpecialComment(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SpecialCommentFacadeLocal#deleteSpecialComment(co.com.directv.sdii.model.vo.SpecialCommentVO)
	 */
	public void deleteSpecialComment(SpecialCommentVO obj) throws BusinessException {
		businessSpecialComment.deleteSpecialComment(obj);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.SpecialCommentFacadeBeanLocal#createSpecialComments(java.lang.Long, java.util.List)
	 */
	@Override
	public void createSpecialComments(Long referenceId,
			List<SpecialCommentVO> specialCommentsList)
			throws BusinessException {
		businessSpecialComment.createSpecialComments(referenceId,specialCommentsList);
		
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.SpecialCommentFacadeBeanLocal#getSpecialCommentsByReferenceId(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public SpecialCommentResponse getSpecialCommentsByReferenceId(
			Long referenceId, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return businessSpecialComment.getSpecialCommentsByReferenceId(referenceId,requestCollInfo);
	}
}
