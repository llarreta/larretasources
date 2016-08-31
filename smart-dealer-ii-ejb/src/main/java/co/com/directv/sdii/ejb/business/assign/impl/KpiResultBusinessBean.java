package co.com.directv.sdii.ejb.business.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.kpi.dto.RequestSearchKpiResultsDTO;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.KpiResultBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.KpiResult;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.ResponseSearchKpiResultsResponse;
import co.com.directv.sdii.model.vo.KpiResultVO;
import co.com.directv.sdii.persistence.dao.assign.KpiResultDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD KpiResult
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.KpiResultDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.KpiResultBusinessBeanLocal
 */
@Stateless(name="KpiResultBusinessBeanLocal",mappedName="ejb/KpiResultBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KpiResultBusinessBean extends BusinessBase implements KpiResultBusinessBeanLocal {

    @EJB(name="KpiResultDAOLocal", beanInterface=KpiResultDAOLocal.class)
    private KpiResultDAOLocal daoKpiResult;
    
    private final static Logger log = UtilsBusiness.getLog4J(KpiResultBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.KpiResultBusinessBeanLocal#getAllKpiResults()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<KpiResultVO> getAllKpiResults() throws BusinessException {
        log.debug("== Inicia getAllKpiResults/KpiResultBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoKpiResult.getAllKpiResults(), KpiResultVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllKpiResults/KpiResultBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllKpiResults/KpiResultBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.KpiResultBusinessBeanLocal#getKpiResultsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public KpiResultVO getKpiResultByID(Long id) throws BusinessException {
        log.debug("== Inicia getKpiResultByID/KpiResultBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            KpiResult objPojo = daoKpiResult.getKpiResultByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(KpiResultVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getKpiResultByID/KpiResultBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getKpiResultByID/KpiResultBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiResultBusinessBeanLocal#createKpiResult(co.com.directv.sdii.model.vo.KpiResultVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createKpiResult(KpiResultVO obj) throws BusinessException {
        log.debug("== Inicia createKpiResult/KpiResultBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            KpiResult objPojo =  UtilsBusiness.copyObject(KpiResult.class, obj);
            daoKpiResult.createKpiResult(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createKpiResult/KpiResultBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createKpiResult/KpiResultBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiResultBusinessBeanLocal#updateKpiResult(co.com.directv.sdii.model.vo.KpiResultVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateKpiResult(KpiResultVO obj) throws BusinessException {
        log.debug("== Inicia updateKpiResult/KpiResultBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            KpiResult objPojo =  UtilsBusiness.copyObject(KpiResult.class, obj);
            daoKpiResult.updateKpiResult(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateKpiResult/KpiResultBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateKpiResult/KpiResultBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiResultBusinessBeanLocal#deleteKpiResult(co.com.directv.sdii.model.vo.KpiResultVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteKpiResult(KpiResultVO obj) throws BusinessException {
        log.debug("== Inicia deleteKpiResult/KpiResultBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            KpiResult objPojo =  UtilsBusiness.copyObject(KpiResult.class, obj);
            daoKpiResult.deleteKpiResult(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteKpiResult/KpiResultBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteKpiResult/KpiResultBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiResultBusinessBeanLocal#getDealerIndicatorResultByKpiConfigurationIdAndDealerId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public KpiResultVO getDealerIndicatorResultByKpiConfigurationIdAndDealerId(Long kpiConfigurationId, Long dealerId)
			throws BusinessException {
		log.debug("== Inicia getDealerIndicatorResultByConfigurationId/KpiResultBusinessBean ==");
        UtilsBusiness.assertNotNull(kpiConfigurationId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            KpiResult objPojo = daoKpiResult.getDealerIndicatorResultByKpiConfigurationIdAndDealerId(kpiConfigurationId, dealerId);
            return UtilsBusiness.copyObject(KpiResultVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerIndicatorResultByConfigurationId/KpiResultBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerIndicatorResultByConfigurationId/KpiResultBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiResultBusinessBeanLocal#getKpiResultByCriteria(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public KpiResultVO getKpiResultByCriteria(Long countryId,
			Long serviceSuperCategoryId, Long zoneTypeId, Long indicatorId,
			Long dealerId) throws BusinessException {
		log.debug("== Inicia getDealerIndicatorResultByConfigurationId/KpiResultBusinessBean ==");
        UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(serviceSuperCategoryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(zoneTypeId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(indicatorId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            KpiResult objPojo = daoKpiResult.getKpiResultByCriteria(countryId, serviceSuperCategoryId, zoneTypeId, indicatorId, dealerId);
            return UtilsBusiness.copyObject(KpiResultVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerIndicatorResultByConfigurationId/KpiResultBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerIndicatorResultByConfigurationId/KpiResultBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiResultBusinessBeanLocal#getKpiResultByDealerIdAndBetweenDate(co.com.directv.sdii.assign.kpi.dto.RequestSearchKpiResultsDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ResponseSearchKpiResultsResponse getKpiResultDealersByDealerIdAndBetweenDate(RequestSearchKpiResultsDTO request, RequestCollectionInfo requestCollInfo ) throws BusinessException {
		log.debug("== Inicia getKpiResultByDealerIdAndBetweenDate/KpiResultBusinessBean ==");
		try {
			return daoKpiResult.getKpiResultDealersByDealerIdAndBetweenDate(request,requestCollInfo);
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n getKpiResultByDealerIdAndBetweenDate/KpiResultBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getKpiResultByDealerIdAndBetweenDate/KpiResultBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiResultBusinessBeanLocal#getKpiResultByDealerIdAndBetweenDate(co.com.directv.sdii.assign.kpi.dto.RequestSearchKpiResultsDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ResponseSearchKpiResultsResponse getKpiResultByDealerIdAndBetweenDate(RequestSearchKpiResultsDTO request) throws BusinessException {
		log.debug("== Inicia getKpiResultByDealerIdAndBetweenDate/KpiResultBusinessBean ==");
		try {
			return daoKpiResult.getKpiResultByDealerIdAndBetweenDate(request);
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n getKpiResultByDealerIdAndBetweenDate/KpiResultBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getKpiResultByDealerIdAndBetweenDate/KpiResultBusinessBean ==");
        }
	}

}
