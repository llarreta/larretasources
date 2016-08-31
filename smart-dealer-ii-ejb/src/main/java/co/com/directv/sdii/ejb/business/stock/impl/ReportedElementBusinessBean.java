package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.stock.NotSerializedBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReportedElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ReportedElement;
import co.com.directv.sdii.model.vo.ReportedElementVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.persistence.dao.stock.ReportedElementDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD ReportedElement
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ReportedElementDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ReportedElementBusinessBeanLocal
 */
@Stateless(name="ReportedElementBusinessBeanLocal",mappedName="ejb/ReportedElementBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReportedElementBusinessBean extends BusinessBase implements ReportedElementBusinessBeanLocal {

	private final static Logger log = UtilsBusiness.getLog4J(ReportedElementBusinessBean.class);
	
    @EJB(name="ReportedElementDAOLocal", beanInterface=ReportedElementDAOLocal.class)
    private ReportedElementDAOLocal daoReportedElement;
    
    @EJB(name="SerializedBusinessBeanLocal", beanInterface=SerializedBusinessBeanLocal.class)
    private SerializedBusinessBeanLocal businessSerialized;    
    
    @EJB(name="NotSerializedBusinessBeanLocal", beanInterface=NotSerializedBusinessBeanLocal.class)
    private NotSerializedBusinessBeanLocal businessNotSerialized;
   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ReportedElementBusinessBeanLocal#getAllReportedElements()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ReportedElementVO> getAllReportedElements() throws BusinessException {
        log.debug("== Inicia getAllReportedElements/ReportedElementBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoReportedElement.getAllReportedElements(), ReportedElementVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllReportedElements/ReportedElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllReportedElements/ReportedElementBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ReportedElementBusinessBeanLocal#getReportedElementsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ReportedElementVO getReportedElementByID(Long id) throws BusinessException {
        log.debug("== Inicia getReportedElementByID/ReportedElementBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReportedElement objPojo = daoReportedElement.getReportedElementByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ReportedElementVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getReportedElementByID/ReportedElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReportedElementByID/ReportedElementBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReportedElementBusinessBeanLocal#createReportedElement(co.com.directv.sdii.model.vo.ReportedElementVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createReportedElement(ReportedElementVO obj) throws BusinessException {
        log.debug("== Inicia createReportedElement/ReportedElementBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReportedElement objPojo =  UtilsBusiness.copyObject(ReportedElement.class, obj);
            daoReportedElement.createReportedElement(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createReportedElement/ReportedElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createReportedElement/ReportedElementBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReportedElementBusinessBeanLocal#updateReportedElement(co.com.directv.sdii.model.vo.ReportedElementVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateReportedElement(ReportedElementVO obj) throws BusinessException {
        log.debug("== Inicia updateReportedElement/ReportedElementBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReportedElement objPojo =  UtilsBusiness.copyObject(ReportedElement.class, obj);
            daoReportedElement.updateReportedElement(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateReportedElement/ReportedElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateReportedElement/ReportedElementBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReportedElementBusinessBeanLocal#deleteReportedElement(co.com.directv.sdii.model.vo.ReportedElementVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteReportedElement(ReportedElementVO obj) throws BusinessException {
        log.debug("== Inicia deleteReportedElement/ReportedElementBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReportedElement objPojo =  UtilsBusiness.copyObject(ReportedElement.class, obj);
            daoReportedElement.deleteReportedElement(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteReportedElement/ReportedElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteReportedElement/ReportedElementBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReportedElementBusinessBeanLocal#getReportedElementsByRefInconsistencyId(java.lang.Long, boolean, boolean)
	 */
	@Override
	public List<ReportedElementVO> getReportedElementsByRefInconsistencyId(
			Long refInconsistencyId, boolean incluirSobrantes,
			boolean incluirFaltantes) throws BusinessException {
		log.debug("== Inicia getReportedElementsByRefInconsistencyId/ReportedElementBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(refInconsistencyId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	
        	List<ReportedElementVO> reportedElements = UtilsBusiness.convertList(daoReportedElement.getReportedElementsByRefInconsistencyId(refInconsistencyId, incluirSobrantes,
        			incluirFaltantes), ReportedElementVO.class);

        	if(reportedElements != null) {
	        	for (ReportedElementVO reportedElementVO : reportedElements) {
	        		try {
		        		if(!(StringUtils.isEmpty(reportedElementVO.getSerialCode()))) {
							SerializedVO ser = businessSerialized.getSerializedBySerial(reportedElementVO.getSerialCode(),reportedElementVO.getUser().getCountry().getId());
							reportedElementVO.setSerialized(ser);
						}
	        		} catch (NullPointerException e) {
	        			log.error("no se pudo leer la estructura reportedElementVO.getReferenceElementItem().getElement() por un valor nulo");
	        		}
				}
        	}
        	
        	return reportedElements;
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getReportedElementsByRefInconsistencyId/ReportedElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReportedElementsByRefInconsistencyId/ReportedElementBusinessBean ==");
        }
		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReportedElementBusinessBeanLocal#deleteReportedElementsByRefInconsitencyId(java.lang.Long)
	 */
	@Override
	public void deleteReportedElementsByRefInconsitencyId(
			Long refInconsistencyId) throws BusinessException {
		log.debug("== Inicia deleteReportedElementsByRefInconsitencyId/ReportedElementBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(refInconsistencyId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            daoReportedElement.deleteReportedElementsByRefInconsitencyId(refInconsistencyId);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteReportedElementsByRefInconsitencyId/ReportedElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteReportedElementsByRefInconsitencyId/ReportedElementBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReportedElementBusinessBeanLocal#getCountReportedElementsActiveByRefElementItemId(java.lang.Long, java.lang.String, java.lang.Long, boolean, boolean)
	 */
	@Override
	public Double getCountReportedElementsByRefElementItemId(
			Long refElementItemId, boolean incluirSobrantes, boolean incluirFaltantes)
			throws BusinessException {
		log.debug("== Inicia getCountReportedElementsActiveByRefElementItemId/ReportedElementBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(refElementItemId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            return daoReportedElement.getQuantityReportedElementsByRefElementItemId(refElementItemId, incluirSobrantes, incluirFaltantes);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getCountReportedElementsActiveByRefElementItemId/ReportedElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCountReportedElementsActiveByRefElementItemId/ReportedElementBusinessBean ==");
        }
	}
	
}
