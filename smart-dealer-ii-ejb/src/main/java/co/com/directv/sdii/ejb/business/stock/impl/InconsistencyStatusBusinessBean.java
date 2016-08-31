package co.com.directv.sdii.ejb.business.stock.impl;

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
import co.com.directv.sdii.ejb.business.stock.InconsistencyStatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.InconsistencyStatus;
import co.com.directv.sdii.model.vo.InconsistencyStatusVO;
import co.com.directv.sdii.persistence.dao.stock.InconsistencyStatusDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD InconsistencyStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.InconsistencyStatusDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.InconsistencyStatusBusinessBeanLocal
 */
@Stateless(name="InconsistencyStatusBusinessBeanLocal",mappedName="ejb/InconsistencyStatusBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class InconsistencyStatusBusinessBean extends BusinessBase implements InconsistencyStatusBusinessBeanLocal {

    @EJB(name="InconsistencyStatusDAOLocal", beanInterface=InconsistencyStatusDAOLocal.class)
    private InconsistencyStatusDAOLocal daoInconsistencyStatus;
    
    private final static Logger log = UtilsBusiness.getLog4J(InconsistencyStatusBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.InconsistencyStatusBusinessBeanLocal#getAllInconsistencyStatuss()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<InconsistencyStatusVO> getAllInconsistencyStatuss() throws BusinessException {
        log.debug("== Inicia getAllInconsistencyStatuss/InconsistencyStatusBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoInconsistencyStatus.getAllInconsistencyStatuss(), InconsistencyStatusVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllInconsistencyStatuss/InconsistencyStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllInconsistencyStatuss/InconsistencyStatusBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.InconsistencyStatusBusinessBeanLocal#getInconsistencyStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public InconsistencyStatusVO getInconsistencyStatusByID(Long id) throws BusinessException {
        log.debug("== Inicia getInconsistencyStatusByID/InconsistencyStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            InconsistencyStatus objPojo = daoInconsistencyStatus.getInconsistencyStatusByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(InconsistencyStatusVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getInconsistencyStatusByID/InconsistencyStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getInconsistencyStatusByID/InconsistencyStatusBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.InconsistencyStatusBusinessBeanLocal#createInconsistencyStatus(co.com.directv.sdii.model.vo.InconsistencyStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createInconsistencyStatus(InconsistencyStatusVO obj) throws BusinessException {
        log.debug("== Inicia createInconsistencyStatus/InconsistencyStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            InconsistencyStatus objPojo =  UtilsBusiness.copyObject(InconsistencyStatus.class, obj);
            daoInconsistencyStatus.createInconsistencyStatus(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createInconsistencyStatus/InconsistencyStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createInconsistencyStatus/InconsistencyStatusBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.InconsistencyStatusBusinessBeanLocal#updateInconsistencyStatus(co.com.directv.sdii.model.vo.InconsistencyStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateInconsistencyStatus(InconsistencyStatusVO obj) throws BusinessException {
        log.debug("== Inicia updateInconsistencyStatus/InconsistencyStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            InconsistencyStatus objPojo =  UtilsBusiness.copyObject(InconsistencyStatus.class, obj);
            daoInconsistencyStatus.updateInconsistencyStatus(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateInconsistencyStatus/InconsistencyStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateInconsistencyStatus/InconsistencyStatusBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.InconsistencyStatusBusinessBeanLocal#deleteInconsistencyStatus(co.com.directv.sdii.model.vo.InconsistencyStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteInconsistencyStatus(InconsistencyStatusVO obj) throws BusinessException {
        log.debug("== Inicia deleteInconsistencyStatus/InconsistencyStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            InconsistencyStatus objPojo =  UtilsBusiness.copyObject(InconsistencyStatus.class, obj);
            daoInconsistencyStatus.deleteInconsistencyStatus(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteInconsistencyStatus/InconsistencyStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteInconsistencyStatus/InconsistencyStatusBusinessBean ==");
        }
	}
}
